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

import com.arangodb.ArangoDBException;
import edu.kit.datamanager.metastore.controller.IMetsFileController;
import edu.kit.datamanager.metastore.entity.MetsFile;
import edu.kit.datamanager.metastore.service.IMetsFileService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * REST services for METS documents (fka Metastore).
 */
@Controller
@RequestMapping("api/v1/metastore/mets")
public class MetsFileController implements IMetsFileController {

  /**
   * Logger for this class.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(MetsFileController.class);

  /**
   * Services for handling files inside METS documents.
   */
  @Autowired
  private IMetsFileService metastoreFileService;

  @Override
  public ResponseEntity<List<MetsFile>> getAllFilesOfMetsDocument(@PathVariable("resourceId") String resourceId,
          @RequestParam(name = "pageId", required = false) String[] pageId,
          @RequestParam(name = "use", required = false) String[] use,
          @RequestParam(name = "fileId", required = false) String[] fileId) {
    LOGGER.trace("Get METS files...");

    List<MetsFile> allFiles = getListOfAllFilesOfMetsDocument(resourceId, pageId, use, fileId);

    return new ResponseEntity<>(allFiles, HttpStatus.OK);
  }

  @Override
  public String getAllFilesOfMetsDocumentAsHtml(@PathVariable("resourceId") String resourceId,
          @RequestParam(name = "pageId", required = false) String[] pageId,
          @RequestParam(name = "use", required = false) String[] use,
          @RequestParam(name = "fileId", required = false) String[] fileId,
          Model model) {
    LOGGER.trace("Get METS files as HTML...");

    List<MetsFile> allFiles = getListOfAllFilesOfMetsDocument(resourceId, pageId, use, fileId);
    model.addAttribute("metsFiles", allFiles);

    return "listOfFiles";
  }

  @Override
  public ResponseEntity<List<String>> getUrlOfAllFilesOfMetsDocument(@PathVariable("resourceId") String resourceId,
          @RequestParam(name = "pageId", required = false) String[] pageId,
          @RequestParam(name = "use", required = false) String[] use,
          @RequestParam(name = "fileId", required = false) String[] fileId) {
    LOGGER.trace("Get URL of METS files...");

    List<MetsFile> allFiles = getListOfAllFilesOfMetsDocument(resourceId, pageId, use, fileId);
    List<String> urlOfAllFiles = new ArrayList<>();
    for (MetsFile metsFile : allFiles) {
      urlOfAllFiles.add(metsFile.getUrl());
    }

    return new ResponseEntity<>(urlOfAllFiles, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<List<String>> getAllPageIdsOfMetsDocument(@PathVariable("resourceId") String resourceId) {
    LOGGER.trace("Get all PAGEIDs of METS files");

    List<String> allPageIds = metastoreFileService.getAllPageIds(resourceId);

    return new ResponseEntity<>(allPageIds, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<List<String>> getAllUsesOfMetsDocument(@PathVariable("resourceId") String resourceId) {
    LOGGER.trace("Get all USEs of METS files");

    List<String> allUses = metastoreFileService.getAllUses(resourceId);

    return new ResponseEntity<>(allUses, HttpStatus.OK);
  }

  /**
   * Get referenced files of a METS document optionally filtered by PAGEID
   * AND/OR USE or ID.
   *
   *
   * @param resourceId ResourceID of the METS document.
   * @param pageId PAGEIDs of the files.
   * @param use USEs of the files.
   * @param fileId fileIds of the file.
   *
   * @return all METS documents as List.
   */
  private List<MetsFile> getListOfAllFilesOfMetsDocument(String resourceId, String[] pageId, String[] use, String[] fileId) {
    List<MetsFile> allFiles;
    if (fileId != null) {
      LOGGER.trace("...filtered by fileIDs: '{}'", Arrays.toString(fileId));
      allFiles = metastoreFileService.getAvailableMetsFilesByFileIds(resourceId, fileId);

    } else {
      if ((use != null) || (pageId != null)) {
        LOGGER.trace("...filtered by PAGEID: '{}' and USE: '{}'", Arrays.toString(pageId), Arrays.toString(use));
        allFiles = metastoreFileService.getAvailableMetsFilesByUseAndPageId(resourceId, use, pageId);
      } else {
        LOGGER.trace("...without any filter.", pageId, use);
        allFiles = metastoreFileService.getAvailableMetsFilesOfCurrentVersion(resourceId);
      }
    }
    return allFiles;
  }

  /**
   * Handler for Exceptions.
   *
   * @param exc Exception accessing database.
   *
   * @return Error status.
   */
  @ExceptionHandler(ArangoDBException.class)
  public ResponseEntity<?> handleArangoDBException(ArangoDBException ade) {
    ResponseEntity<?> responseEntity = ResponseEntity.status(ade.getResponseCode()).body(ade.getErrorMessage());
    return responseEntity;
  }
}
