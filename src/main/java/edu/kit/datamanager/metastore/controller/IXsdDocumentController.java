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

import edu.kit.datamanager.metastore.entity.XmlSchemaDefinition;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.io.IOException;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * Interface for XSD documents REST interface (fka Metastore).
 */
public interface IXsdDocumentController {

  /**
   * Create new XSD document with given prefix.
   *
   * @param prefix Prefix of the XSD document.
   * @param fileContent Content of the XSD file.
   *
   * @return Document created.
   */
  @ApiOperation(value = "Create new XSD document with given prefix.",
          notes = "The prefix has to be unique. The XSD document has to use only global"
                  + "references to other XSD document.")
  @RequestMapping(path = "prefix/{prefix}", method = RequestMethod.POST)
  @ResponseBody
  ResponseEntity<?> createMetsDocument(@ApiParam(value = "The prefix for the XSD document.", required = true)@PathVariable(value = "prefix") String prefix, 
          @ApiParam(value = "The content of the XSD document.", required = true)@RequestParam(value = "fileContent") MultipartFile fileContent) throws IOException;

  /**
   * Get all XSD documents.
   *
   * @return Array holding all XSD documents.
   */
  @ApiOperation(value = "Get all XSD documents.",
          notes = "List of all XSD documents.")
  @RequestMapping(path = "", method = RequestMethod.GET)
  @ResponseBody
  ResponseEntity<List<XmlSchemaDefinition>> getAllDocuments();

  /**
   * Get all registered prefixes.
   *
   * @return Array with all prefixes.
   */
  @ApiOperation(value = "Get all registered prefixes.",
          notes = "Get a JSON list containing all registered prefixes.")
  @RequestMapping(path = "prefix", method = RequestMethod.GET)
  @ResponseBody
  ResponseEntity<List<String>> getAllPrefixes();

  /**
   * Get document registered with given namespace.
   *
   * @param namespace Namespace of the XSD document.
   *
   * @return XSD document.
   */
  @ApiOperation(value = "Get XSD document with given target namespace.")
  @RequestMapping(path = "ns", method = RequestMethod.GET)
  @ResponseBody
  ResponseEntity<XmlSchemaDefinition> getXsdDocumentByNamespace(@ApiParam(value = "The target namespace of the XSD document.", required = true)@RequestParam(value = "namespace") String namespace);

  /**
   * Get document registered with given prefix.
   *
   * @param prefix Prefix of the XSD document.
   *
   * @return XSD document.
   */
  @ApiOperation(value = "Get XSD document with given prefix.")
  @RequestMapping(path = "prefix/{prefix}", method = RequestMethod.GET)
  @ResponseBody
  ResponseEntity<XmlSchemaDefinition> getXsdDocumentByPrefix(@ApiParam(value = "The prefix of the XSD document.", required = true)@PathVariable(value = "prefix") String prefix);
  
}
