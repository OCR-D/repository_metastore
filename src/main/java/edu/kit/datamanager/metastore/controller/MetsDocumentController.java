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
package edu.kit.datamanager.metastore.controller;

import edu.kit.datamanager.metastore.entity.MetsDocument;
import edu.kit.datamanager.metastore.service.IMetsDocumentService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * REST services for METS documents (fka Metastore).
 *
 * @author hartmann-v
 */
@Controller
@RequestMapping("metastore")
public class MetsDocumentController {
  /**
   * Logger for this class.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(MetsDocumentController.class);

  @Autowired
  private IMetsDocumentService metastoreService;

  @GetMapping("mets")
  public ResponseEntity<List<MetsDocument>> getAllDocuments() {
    List<MetsDocument> allDocuments = metastoreService.getAllDocuments();
    LOGGER.trace("Get all METS documents");
    return new ResponseEntity<>(allDocuments, HttpStatus.OK);
  }
  @GetMapping("mets/{resourceId}")
  public ResponseEntity<MetsDocument> getLatestMetsDocument(@PathVariable("resourceId")String resourceId) {
    LOGGER.trace("Get latest METS documents with ID " + resourceId);
    System.out.println("Get latest METS documents with ID " + resourceId);
    MetsDocument metsDoc;
    metsDoc = metastoreService.getMostRecentMetsDocumentByResourceId(resourceId);
    return new ResponseEntity<>(metsDoc, HttpStatus.OK);
  }
  @GetMapping("mets/{resourceId}/version/{version}")
  public ResponseEntity<MetsDocument> getLatestMetsDocument(@PathVariable("resourceId")String resourceId, @PathVariable("version")Integer version) {
    LOGGER.trace("Get METS documents with ID '{}' and version {}",resourceId, version);
    System.out.println("Get METS documents with ID " + resourceId + " and version " + version);
    MetsDocument metsDoc;
    metsDoc = metastoreService.getDocumentByResourceIdAndVersion(resourceId, version);
    return new ResponseEntity<>(metsDoc, HttpStatus.OK);
  }
  @PutMapping("mets/{resourceId}")
  public ResponseEntity<?> createMetsDocument(@PathVariable("resourceId")String resourceId, @RequestParam("fileContent")String fileContent) {
    LOGGER.trace("Create METS document!");
    System.out.println("Create METS document with id " + resourceId + "!" + fileContent);
    metastoreService.createMetsDocument(resourceId, fileContent);
    return ResponseEntity.ok("Document created!");
  }
  
}
