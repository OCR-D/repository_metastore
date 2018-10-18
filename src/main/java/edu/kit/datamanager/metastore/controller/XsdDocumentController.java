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
import edu.kit.datamanager.metastore.entity.XmlSchemaDefinition;
import edu.kit.datamanager.metastore.service.IXsdDocumentService;
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
public class XsdDocumentController {
  /**
   * Logger for this class.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(XsdDocumentController.class);

  @Autowired
  private IXsdDocumentService xsdService;

  @GetMapping("xsd")
  public ResponseEntity<List<XmlSchemaDefinition>> getAllDocuments() {
    List<XmlSchemaDefinition> allDocuments = xsdService.getAllDocuments();
    LOGGER.trace("Get all METS documents");
    return new ResponseEntity<>(allDocuments, HttpStatus.OK);
  }
  @GetMapping("xsd/prefix")
  public ResponseEntity<List<String>> getAllPrefixes() {
    LOGGER.trace("Get prefixes of  all XSD documents");
    System.out.println("Get prefixes of  all XSD documents.");
    XmlSchemaDefinition xsdDocument;
    List<String> allPrefixes = xsdService.getAllPrefixes();
    return new ResponseEntity<>(allPrefixes, HttpStatus.OK);
  }
  @GetMapping("xsd/prefix/{prefix}")
  public ResponseEntity<XmlSchemaDefinition> getXsdDocumentByPrefix(@PathVariable("prefix")String prefix) {
    LOGGER.trace("Get XSD document with prefix " + prefix);
    System.out.println("Get XSD document with prefix " + prefix);
    XmlSchemaDefinition xsdDocument;
    xsdDocument = xsdService.getDocumentByPrefix(prefix);
    return new ResponseEntity<>(xsdDocument, HttpStatus.OK);
  }
  @GetMapping("xsd/ns")
  public ResponseEntity<XmlSchemaDefinition> getXsdDocumentByNamespace(@RequestParam("namespace")String namespace) {
    LOGGER.trace("Get XSD document with namespace " + namespace);
    System.out.println("Get XSD document with namespace " + namespace);
    XmlSchemaDefinition xsdDocument;
    xsdDocument = xsdService.getDocumentByNamespace(namespace);
    return new ResponseEntity<>(xsdDocument, HttpStatus.OK);
  }
  @PutMapping("xsd/prefix/{prefix}")
  public ResponseEntity<?> createMetsDocument(@PathVariable("prefix")String prefix, @RequestParam("fileContent")String fileContent) {
    LOGGER.trace("Create XSD document!");
    System.out.println("Create XSD document with id " + prefix + "!" + fileContent);
    xsdService.createXsdDocument(prefix, fileContent);
    return ResponseEntity.ok("XSD document created!");
  }
  
}
