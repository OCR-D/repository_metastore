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

import edu.kit.datamanager.metastore.exception.InvalidFormatException;
import edu.kit.datamanager.metastore.service.IMetsDocumentService;
import edu.kit.datamanager.metastore.storageservice.StorageService;
import edu.kit.datamanager.metastore.util.BagItUtil;
import edu.kit.datamanager.util.ZipUtils;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import org.springframework.web.bind.annotation.RequestMapping;
import edu.kit.datamanager.metastore.controller.IBagItUploadController;
import edu.kit.datamanager.metastore.entity.MetsFile;
import edu.kit.datamanager.metastore.entity.MetsProperties;
import edu.kit.datamanager.metastore.entity.ZippedBagit;
import edu.kit.datamanager.metastore.exception.BagItException;
import edu.kit.datamanager.metastore.exception.ResourceAlreadyExistsException;
import edu.kit.datamanager.metastore.kitdm.KitDmProperties;
import edu.kit.datamanager.metastore.repository.ClassificationMetadataRepository;
import edu.kit.datamanager.metastore.repository.GenreMetadataRepository;
import edu.kit.datamanager.metastore.repository.LanguageMetadataRepository;
import edu.kit.datamanager.metastore.repository.MetsFileRepository;
import edu.kit.datamanager.metastore.repository.MetsIdentifierRepository;
import edu.kit.datamanager.metastore.repository.MetsPropertiesRepository;
import edu.kit.datamanager.metastore.repository.PageMetadataRepository;
import edu.kit.datamanager.metastore.repository.ZippedBagitRepository;
import edu.kit.datamanager.metastore.util.RegisterFilesInRepo;
import edu.kit.datamanager.metastore.util.RepositoryUtil;
import gov.loc.repository.bagit.domain.Bag;
import io.swagger.client.ApiException;
import io.swagger.client.model.DataResource;
import java.net.URISyntaxException;
import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import org.springframework.util.FileSystemUtils;

/**
 * REST service handling upload of zipped Bagit containers.
 */
@Controller
@RequestMapping("/api/v1/metastore/bagit")
public class BagItUploadController implements IBagItUploadController {

  /**
   * Logger for this class.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(BagItUploadController.class);
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
   * Subdirectory for storing unzipped files.
   */
  public static final String SUB_DIR_UNZIP = "bagit";

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
   * Repository persisting METS files.
   */
  @Autowired
  private MetsFileRepository metsFileRepository;

  /**
   * Properties for storing uploaded files.
   */
  private final StorageService storageService;
  /**
   * Properties of KIT DM 2.0
   */
  private final KitDmProperties repositoryProperties;
  /**
   * Properties for the zipped BagIt containers.
   */
  @Autowired
  private ZippedBagitRepository bagitRepository;

  /**
   * Handler for repository.
   */
  private RepositoryUtil repository;

  /**
   * Constructor setting up controller for upload of BagIt containers.
   *
   * @param storageService Properties for storing zipped BagIt container.
   * @param repositoryProperties Properties for access to KIT DM repository.
   */
  @Autowired
  public BagItUploadController(StorageService storageService, KitDmProperties repositoryProperties) {
    this.repositoryProperties = repositoryProperties;
    this.storageService = storageService;
    storageService.init();
    repository = new RepositoryUtil(repositoryProperties);
  }

  @Override
  public String listUploadedFilesAsHtml(Model model) throws IOException {
    LOGGER.info("listUploadedFilesAsHtml - " + model);

    Iterable<ZippedBagit> findAllZippedFiles = bagitRepository.findAll();
    List<ZippedBagit> allZippedFiles = new ArrayList<>();
    findAllZippedFiles.forEach(allZippedFiles::add);
    model.addAttribute("bagitFiles", allZippedFiles);

    return "uploadForm";
  }

  @Override
  public ResponseEntity<List<String>> listUploadedFiles(Model model) throws IOException {
    LOGGER.info("listUploadedFiles - " + model);
    List<String> listOfAllUrls = getAllUrlsOfBagItContainers();

    return ResponseEntity.ok(listOfAllUrls);
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
    redirectAttributes.addFlashAttribute("message",
            "You successfully uploaded " + file.getOriginalFilename() + "!");

    //*************************************************************************
    // Workflow 
    //  1. Test for zip file 
    //  2. Create resourceID 
    //  3. Store zip file 
    //  4. unzip 
    //  5. validate BagIt container 
    //  6. extract METS and properties and files (resourceIdentifier needed)
    //  7. Create resource.
    //  8. Upload files to repo
    //  9. Adapt download URLs for metsfiles and repoId.
    // 10. Remove local files.
    // 11. Create download URL for zipped file.
    // 12. Register Bagit container
    LOGGER.trace("Unpack bag and validate");
    LOGGER.trace("Upload to: " + storageService.getBasePath());
    LOGGER.trace("file getOriginalName = " + file.getOriginalFilename());
    // 1. Test for zip file
    if (file.getOriginalFilename().endsWith(".zip")) {
      String resourceId;
      // 2. Create resourceID
      do {
        LOGGER.trace("Create resourceIdentifier...");
        resourceId = UUID.randomUUID().toString();
      } while (repository.existsResourceIdentifier(resourceId));
      // 3. Store zip file
      storageService.store(file, resourceId);
      // 4. unzip 
      Path pathToResource = Paths.get(storageService.getBasePath(), resourceId);
      Path pathToBagIt = Paths.get(pathToResource.toString(), SUB_DIR_UNZIP);
      try {
        pathToBagIt = Files.createDirectories(pathToBagIt);
      } catch (FileAlreadyExistsException faee) {
        LOGGER.error("Directory'{}' already exists!", pathToBagIt.toString());
        throw new ResourceAlreadyExistsException("Directory '" + pathToBagIt.toString() + "' already exists!");
      }
      ZipUtils.unzip(storageService.getPath(file.getOriginalFilename(), resourceId).toFile(), pathToBagIt.toFile());
      // 5. validate BagIt container 
      Bag bag = BagItUtil.readBag(pathToBagIt);
      // 6. extract METS and properties and files (resourceIdentifier needed)
      String xOcrdMets = getPathToMets(bag);
      File metsFile = Paths.get(pathToBagIt.toString(), xOcrdMets).toFile();
      if (metsFile.exists()) {
        try {
          String metsFileAsString = FileUtils.readFileToString(metsFile, "UTF-8");
          metsDocumentService.createMetsDocument(resourceId, metsFileAsString);
          // 7. Create resource.
          Iterable<MetsProperties> findByResourceId = metsPropertiesRepository.findByResourceId(resourceId);
          Iterator<MetsProperties> iterator = findByResourceId.iterator();
          DataResource dataResource = null;
          if (iterator.hasNext()) {
            dataResource = repository.createDataResource(resourceId, iterator.next().getTitle(), OCR_D_DATA_TYPE);
          }
          if (dataResource != null) {
            String repoIdentifier = resourceId;
            // 8. Upload files to repo
            RegisterFilesInRepo registerFilesInRepo = new RegisterFilesInRepo(repository, pathToResource, repoIdentifier, Boolean.FALSE);
            Files.walkFileTree(pathToResource, registerFilesInRepo);
            // 9. Adapt download URLs for metsfiles and repoId.
            Iterable<MetsFile> findMetsFilesByResourceId = metsFileRepository.findByResourceIdAndCurrentTrue(resourceId);
            for (MetsFile item : findMetsFilesByResourceId) {
              URI uri = new URI(item.getUrl());
              if (!uri.isAbsolute()) {
                Path pathToFile = Paths.get(SUB_DIR_UNZIP, "data", item.getUrl());
                String downloadUrl = repository.toDownloadUrl(repoIdentifier, pathToFile);
                LOGGER.trace("Change URL for Metsfile from '{}' to '{}'", item.getUrl(), downloadUrl);
                item.setUrl(downloadUrl);
                item.setResourceId(repoIdentifier);
                metsFileRepository.save(item);
              }
            }

            findMetsFilesByResourceId = metsFileRepository.findByResourceIdAndCurrentTrue(resourceId);
            if (LOGGER.isTraceEnabled()) {
              LOGGER.trace("***********************************************************");
              for (MetsFile item : findMetsFilesByResourceId) {
                LOGGER.trace(item.toString());
              }
              LOGGER.trace("***********************************************************");
            }
            // 10. Remove local temporary files.
            FileSystemUtils.deleteRecursively(pathToResource);
            // 11. Create download URL for zipped file.
            Path metsPath = Paths.get(file.getOriginalFilename());
            String locationString = repository.toDownloadUrl(repoIdentifier, metsPath);
            location = new URI(locationString);
            // 12. Register Bagit container
            ZippedBagit bagit = new ZippedBagit(resourceId, locationString);
            bagitRepository.save(bagit);
          }

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

  /**
   * List all existing bagitContainers.
   *
   * @return List with URL to all containers.
   */
  private List<String> getAllUrlsOfBagItContainers() {
    List<String> listOfAllUrls = new ArrayList<>();

    Iterable<ZippedBagit> findAllZippedFiles = bagitRepository.findAll();
    for (ZippedBagit item : findAllZippedFiles) {
      listOfAllUrls.add(item.getUrl());
    }
    return listOfAllUrls;
  }
}
