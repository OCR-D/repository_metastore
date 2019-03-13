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

import edu.kit.datamanager.metastore.controller.IMetsDocumentController;
import com.arangodb.ArangoDBException;
import edu.kit.datamanager.metastore.controller.IMetsFileController;
import edu.kit.datamanager.metastore.dao.MetsMetadata;
import edu.kit.datamanager.metastore.entity.MetsDocument;
import edu.kit.datamanager.metastore.entity.MetsFile;
import edu.kit.datamanager.metastore.service.IMetsDocumentService;
import edu.kit.datamanager.metastore.service.IMetsFileService;
import edu.kit.datamanager.metastore.service.IMetsPropertiesService;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * REST services for METS documents (fka Metastore).
 */
@Controller
@RequestMapping("api/v1/metastore/mets")
public class MetsDocumentController implements IMetsDocumentController {

  /**
   * Logger for this class.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(MetsDocumentController.class);

  /**
   * Services for handling METS documents.
   */
  @Autowired
  private IMetsDocumentService metastoreService;

  /**
   * Services for handling properties of METS documents.
   */
  @Autowired
  private IMetsPropertiesService metastoreResourceService;

  @Override
  public ResponseEntity<List<MetsDocument>> getAllDocuments() {
    List<MetsDocument> allDocuments = metastoreService.getAllDocuments();
    LOGGER.trace("Get all METS documents");
    return new ResponseEntity<>(allDocuments, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<MetsDocument> getLatestMetsDocument(@PathVariable("resourceId") String resourceId) {
    LOGGER.trace("Get latest METS documents with ID " + resourceId);
    MetsDocument metsDoc;
    metsDoc = metastoreService.getMostRecentMetsDocumentByResourceId(resourceId);
    return new ResponseEntity<>(metsDoc, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<MetsDocument> getMetsDocumentByVersion(@PathVariable("resourceId") String resourceId, @PathVariable("version") Integer version) {
    LOGGER.trace("Get METS documents with ID '{}' and version {}", resourceId, version);
    MetsDocument metsDoc;
    metsDoc = metastoreService.getDocumentByResourceIdAndVersion(resourceId, version);
    return new ResponseEntity<>(metsDoc, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<?> createMetsDocument(@PathVariable("resourceId") String resourceId, @RequestParam("fileContent") String fileContent) {
    LOGGER.trace("Create METS document!");
    metastoreService.createMetsDocument(resourceId, fileContent);
    URI location = ServletUriComponentsBuilder
            .fromCurrentRequest().build().toUri();
    return ResponseEntity.created(location).build();
  }

  @Override
  public ResponseEntity<List<Integer>> getVersionsOfMetsDocument(@PathVariable("resourceId") String resourceId) {
    LOGGER.trace("Get versions of METS documents with ID " + resourceId);
    List<Integer> versionList;
    versionList = metastoreService.getAllVersionsByResourceId(resourceId);
    return new ResponseEntity<>(versionList, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<List<String>> getResourceIdByTitle(@RequestParam("title") String title) {
    LOGGER.trace("Get resourceID of METS documents with title " + title);
    List<String> resourceIdList;
    resourceIdList = metastoreResourceService.getResourceIdsByTitle(title);
    return new ResponseEntity<>(resourceIdList, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<List<String>> getResourceIdByPpn(@RequestParam("ppn") String ppn) {
    LOGGER.trace("Get resourceID of METS documents with PPN " + ppn);
    List<String> resourceIdList;
    resourceIdList = metastoreResourceService.getResourceIdsByPpn(ppn);
    return new ResponseEntity<>(resourceIdList, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<MetsMetadata> getLatestMetadataOfDocument(String resourceId) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public ResponseEntity<MetsMetadata> getLatestMetadataOfDocumentAsHtml(String resourceId) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public ResponseEntity<MetsMetadata> getLatestGroundTruthMetadataOfDocumentAsHtml(String resourceId) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
