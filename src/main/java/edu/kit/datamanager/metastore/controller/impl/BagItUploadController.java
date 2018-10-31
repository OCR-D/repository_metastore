/*
 * Copyright 2018 Karlsruhe Institute of Technology.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.kit.datamanager.metastore.controller.impl;

import edu.kit.datamanager.metastore.MetastoreApplication;
import edu.kit.datamanager.metastore.exception.InvalidFormatException;
import edu.kit.datamanager.metastore.service.IMetsDocumentService;
import edu.kit.datamanager.metastore.storageservice.ArchiveService;
import edu.kit.datamanager.metastore.storageservice.StorageFileNotFoundException;
import edu.kit.datamanager.metastore.storageservice.StorageService;
import edu.kit.datamanager.metastore.util.BagItUtil;
import edu.kit.datamanager.metastore.util.ZipUtils;
import gov.loc.repository.bagit.Bag;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import edu.kit.datamanager.metastore.controller.IBagItUploadController;
import java.util.ArrayList;
import java.util.List;

/**
 * REST service handling upload of zipped Bagit containers.
 */
@Controller
@RequestMapping("/api/v1/metastore/bagit")
public class BagItUploadController implements IBagItUploadController {

  /**
   * Logger for this class.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(MetastoreApplication.class);

  /**
   * Services for handling METS documents.
   */
  @Autowired
  private IMetsDocumentService metsDocumentService;

  /**
   * Properties for storing uploaded files.
   */
  private final StorageService storageService;
  /**
   * Properties for archiving unzipped files.
   */
  private final ArchiveService archiveService;

  /**
   * Constructor setting up controller for upload of BagIt containers.
   *
   * @param storageService Properties for storing zipped BagIt container.
   * @param archiveService Properties for storing unzipped BagIt container.
   */
  @Autowired
  public BagItUploadController(StorageService storageService, ArchiveService archiveService) {
    this.storageService = storageService;
    this.archiveService = archiveService;
  }

  @Override
  public String listUploadedFilesAsHtml(Model model) throws IOException {
    LOGGER.info("listUploadedFilesAsHtml - " + model);

    model.addAttribute("files", storageService.listAll().map(path -> MvcUriComponentsBuilder.fromMethodName(BagItUploadController.class,
                    "serveFile", path.getFileName().toString()).build().toString())
            .collect(Collectors.toList()));

    return "uploadForm";
  }
  @Override
  public ResponseEntity<List<String>> listUploadedFiles(Model model) throws IOException {
    LOGGER.info("listUploadedFiles - " + model);
    List<String> listOfAllUrls = new ArrayList<>();
    
    Object[] toArray = storageService.listAll().toArray();
    for (Object item: toArray) {
      Path path = (Path)item;
    String location = ServletUriComponentsBuilder
            .fromCurrentRequest().path("files/" + path.getFileName()).build().toUri().toString();
     listOfAllUrls.add(location); 
    }

    return ResponseEntity.ok(listOfAllUrls);
  }

  @Override
  public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
    LOGGER.info("serveFile - " + filename);

    Resource file = storageService.loadAsResource(filename);
    return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
            "attachment; filename=\"" + file.getFilename() + "\"").body(file);
  }

  /**
   * Handle upload of file (zipped BagIt container)
   *
   * @param file Instance holding content of file and attributes of the file.
   * @param redirectAttributes Attributes storing internal information.
   *
   * @return Website displaying information about uploaded files.
   * @throws IOException Error during upload.
   */
  @Override
  public ResponseEntity<?> handleFileUpload(@RequestParam("file") MultipartFile file,
          RedirectAttributes redirectAttributes) throws IOException {
    LOGGER.info("handleFileUpload");
    storageService.store(file);
    redirectAttributes.addFlashAttribute("message",
            "You successfully uploaded " + file.getOriginalFilename() + "!");

    LOGGER.trace("Unpack bag and validate");
    LOGGER.trace("Archive to: " + archiveService.getBasePath());
    LOGGER.trace("file getOriginalName = " + file.getOriginalFilename());
    if (file.getOriginalFilename().endsWith(".zip")) {
      String baseName = FilenameUtils.getBaseName(file.getOriginalFilename());
      Path pathToArchive = Paths.get(archiveService.getBasePath(), baseName);
      Path createDirectory = Files.createDirectory(pathToArchive);
      ZipUtils.unzip(storageService.getPath(file.getOriginalFilename()).toFile(), createDirectory.toFile());
      Bag bag = BagItUtil.createBag(pathToArchive.toFile());
      String xOcrdMets = bag.getBagInfoTxt().getOrDefault("X-Ocrd-Mets", "data/mets.xml");
      File metsFile = Paths.get(pathToArchive.toString(), xOcrdMets).toFile();
      if (metsFile.exists()) {
        String metsFileAsString = FileUtils.readFileToString(metsFile, "UTF-8");
        metsDocumentService.createMetsDocument(baseName, metsFileAsString);
      } else {
        throw new InvalidFormatException("METS file doesn't exist or isn't specified");
      }
    }

    URI location = ServletUriComponentsBuilder
            .fromCurrentRequest().path("files/" + file.getOriginalFilename()).build().toUri();
    LOGGER.info(location.toString());
    return ResponseEntity.created(location).build();
  }

  /**
   * Handler for Exceptions.
   *
   * @param exc Exception reading/writing file.
   *
   * @return Error status.
   */
  @ExceptionHandler(StorageFileNotFoundException.class)
  public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
    return ResponseEntity.notFound().build();
  }
}
