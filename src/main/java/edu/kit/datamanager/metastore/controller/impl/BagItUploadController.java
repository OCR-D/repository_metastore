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
import edu.kit.datamanager.metastore.storageservice.StorageService;
import edu.kit.datamanager.metastore.util.BagItUtil;
import edu.kit.datamanager.metastore.util.ZipUtils;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import edu.kit.datamanager.metastore.controller.IBagItUploadController;
import edu.kit.datamanager.metastore.entity.MetsProperties;
import edu.kit.datamanager.metastore.exception.BagItException;
import edu.kit.datamanager.metastore.exception.ResourceAlreadyExistsException;
import edu.kit.datamanager.metastore.kitdm.KitDmProperties;
import edu.kit.datamanager.metastore.repository.MetsPropertiesRepository;
import edu.kit.datamanager.metastore.util.MetsDocumentUtil;
import edu.kit.datamanager.metastore.util.RepositoryUtil;
import gov.loc.repository.bagit.domain.Bag;
import io.swagger.client.ApiException;
import io.swagger.client.ApiResponse;
import io.swagger.client.model.DataResource;
import java.net.URISyntaxException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileSystem;
import java.nio.file.LinkOption;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import org.fzk.tools.xml.JaxenUtil;
import org.jdom.Document;

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
   * Key inside Bagit container defining location of the METS file.
   */
  public static final String X_OCRD_METS = "X-Ocrd-Mets";
  /**
   * Default location of the METS file.
   */
  public static final String METS_LOCATION_DEFAULT = "data/mets.xml";
  /**
   * Default data type for OCR-D data resource.
   */
  public static final String OCR_D_DATA_TYPE = "OCR-D GT Data";

  /**
   * Services for handling METS documents.
   */
  @Autowired
  private IMetsDocumentService metsDocumentService;

  /**
   * Repository persisting METS properties.
   */
  @Autowired
  private MetsPropertiesRepository metsPropertiesRepository;

  /**
   * Properties for storing uploaded files.
   */
  private final StorageService storageService;
  /**
   * Properties of KIT DM 2.0
   */
  private final KitDmProperties repositoryProperties;

  /**
   * Properties for archiving unzipped files.
   */
  private final ArchiveService archiveService;
  /**
   * Handler for repository.
   */
  private RepositoryUtil repository;

  /**
   * Constructor setting up controller for upload of BagIt containers.
   *
   * @param storageService Properties for storing zipped BagIt container.
   * @param archiveService Properties for storing unzipped BagIt container.
   */
  @Autowired
  public BagItUploadController(StorageService storageService, ArchiveService archiveService, KitDmProperties repositoryProperties) {
    this.repositoryProperties = repositoryProperties;
    this.storageService = storageService;
    this.archiveService = archiveService;
    repository = new RepositoryUtil(repositoryProperties);
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
    for (Object item : toArray) {
      Path path = (Path) item;
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
          RedirectAttributes redirectAttributes) throws IOException, BagItException, ApiException {
    URI location = null;
    LOGGER.info("handleFileUpload");
    storageService.store(file);
    redirectAttributes.addFlashAttribute("message",
            "You successfully uploaded " + file.getOriginalFilename() + "!");

    LOGGER.trace("Unpack bag and validate");
    LOGGER.trace("Archive to: " + archiveService.getBasePath());
    LOGGER.trace("file getOriginalName = " + file.getOriginalFilename());
    if (file.getOriginalFilename().endsWith(".zip")) {
      String baseName = FilenameUtils.getBaseName(file.getOriginalFilename());
      Path pathToArchive = Paths.get(archiveService.getBasePath(), baseName, "bagit");
      Path createDirectory;
      try {
        createDirectory = Files.createDirectory(pathToArchive);
      } catch (FileAlreadyExistsException faee) {
        LOGGER.error("File '{}' already exists!", baseName, faee);
        throw new ResourceAlreadyExistsException("File '" + baseName + "' already exists!");
      }
      ZipUtils.unzip(storageService.getPath(file.getOriginalFilename()).toFile(), createDirectory.toFile());
      // Create data resource.
      Bag bag = BagItUtil.readBag(pathToArchive);
      String xOcrdMets = getPathToMets(bag);
      File metsFile = Paths.get(pathToArchive.toString(), xOcrdMets).toFile();
      if (metsFile.exists()) {
        try {
          String metsFileAsString = FileUtils.readFileToString(metsFile, "UTF-8");
          metsDocumentService.createMetsDocument(baseName, metsFileAsString);
          Document document;
          try {
            document = JaxenUtil.getDocument(metsFile);
          } catch (Exception ex) {
            throw new InvalidFormatException(ex.getMessage());
          }
          MetsProperties extractProperties = MetsDocumentUtil.extractProperties(document, "noResourceIdSet", 1);
          DataResource dataResource = repository.createDataResource(extractProperties.getTitle(), OCR_D_DATA_TYPE);
          extractProperties.setResourceId(dataResource.getId().toString());
          metsPropertiesRepository.save(extractProperties);

          ApiResponse<io.swagger.client.model.ResponseEntity> postFileToResource = repository.postFileToResource(dataResource.getId(), Boolean.FALSE, Paths.get(""), metsFile);
          String uriLocation = postFileToResource.getHeaders().get(RepositoryUtil.LOCATION_HEADER).get(0);
          location = new URI(uriLocation);

        } catch (URISyntaxException ex) {
          LOGGER.error("URI for METS file is invalid!", ex);
        }

      } else {
        throw new InvalidFormatException("METS file doesn't exist or isn't specified");
      }
    }
    if (location != null) {
      LOGGER.info(location.toString());
    }
    return ResponseEntity.created(location).build();
  }

  /**
   * Determines the path to the METS file.
   *
   * @param bag BagIt container.
   *
   * @return Relative path to METS file.
   */
  private String getPathToMets(Bag bag) {
    List<String> listOfEntries = bag.getMetadata().get(X_OCRD_METS);
    String pathToMets = METS_LOCATION_DEFAULT;
    if (listOfEntries != null) {
      if (listOfEntries.size() > 1) {
        LOGGER.warn("There are multiple location for METS defined!");
      }
      pathToMets = listOfEntries.get(0);
    }
    return pathToMets;
  }
}
