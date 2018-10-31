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
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Interface for METS document REST service.
 */
public interface IMetsDocumentController {

  /**
   * Create a new resource with a given resourceID.
   *
   * @param resourceId ResourceID of the METS document.
   * @param fileContent Content of the METS document.
   *
   * @return URL to resource.
   */
  @ApiOperation(value = "Upload METS document for a already registered data resource.",
          notes = "This endpoint allows to upload METS documents for a data "
          + "resource with a known resource ID.")
  @RequestMapping(path = "{resourceId}", method = RequestMethod.POST)
  @ResponseBody
  ResponseEntity<?> createMetsDocument(@ApiParam(value = "The numeric resource identifier.", required = true) @PathVariable(value = "resourceId") String resourceId, @ApiParam(value = "The file to upload.", required = true) @RequestParam(value = "fileContent") String fileContent);

  /**
   * Get all METS documents.
   *
   * @return all METS documents.
   */
  @ApiOperation(value = "Get all METS documents.",
          notes = "List of all METS documents.")
  @RequestMapping(path = "", method = RequestMethod.GET)
  @ResponseBody
  ResponseEntity<List<MetsDocument>> getAllDocuments();

  /**
   * Get current METS document with given resourceID.
   *
   * @param resourceId ResourceID of the METS document.
   *
   * @return METS document with given resourceID.
   */
  @ApiOperation(value = "Get currrent METS documents with given resourceID.",
          notes = "Only the current METS document is supplied.")
  @RequestMapping(path = "{resourceId}", method = RequestMethod.GET)
  @ResponseBody
  ResponseEntity<MetsDocument> getLatestMetsDocument(@ApiParam(value = "The numeric resource identifier.", required = true) @PathVariable(value = "resourceId") String resourceId);

  /**
   * Get all METS documents with given PPN.
   *
   * @param ppn PPN of the METS document.
   *
   * @return List of URLs with given PPN.
   */
  @ApiOperation(value = "Get all URLs to access METS documents with given PPN.",
          notes = "Only the current METS documents are supplied.")
  @RequestMapping(path = "ppn", method = RequestMethod.GET)
  @ResponseBody
  ResponseEntity<List<String>> getResourceIdByPpn(@ApiParam(value = "The PPN of the METS document.", required = true)@RequestParam(value = "ppn") String ppn);

  /**
   * Get all METS documents with given title.
   *
   * @param title Title of the METS document.
   *
   * @return List of URLs with given title.
   */
  @ApiOperation(value = "Get all URLs to access METS documents with given title.",
          notes = "Only the current METS documents are supplied.")
  @RequestMapping(path = "title", method = RequestMethod.GET)
  @ResponseBody
  ResponseEntity<List<String>> getResourceIdByTitle(@ApiParam(value = "The title of the METS document.", required = true)@RequestParam(value = "title") String title);

  /**
   * Get all versions of METS document with given resourceID.
   *
   * @param resourceId ResourceID of the METS document.
   *
   * @return Versions of the METS document.
   */
  @ApiOperation(value = "Get all versions of METS document with given resourceID.",
          notes = "List all versions available for given resourceID.")
  @RequestMapping(path = "{resourceId}/version", method = RequestMethod.GET)
  @ResponseBody
  ResponseEntity<List<Integer>> getVersionsOfMetsDocument(@ApiParam(value = "The numeric resource identifier.", required = true)@PathVariable(value = "resourceId") String resourceId);

  /**
   * Get METS document with given resourceID and version.
   *
   * @param resourceId ResourceID of the METS document.
   * @param version Version of the METS document.
   *
   * @return METS document with given resourceID and version.
   */
  @ApiOperation(value = "Get specific version of METS documents with given resourceID.")
  @RequestMapping(path = "{resourceId}/version/{version}", method = RequestMethod.GET)
  @ResponseBody
  ResponseEntity<MetsDocument> getMetsDocumentByVersion(@ApiParam(value = "The numeric resource identifier.", required = true) @PathVariable(value = "resourceId") String resourceId,
          @ApiParam(value = "The version of the METS document.", required = true) @PathVariable(value = "version") Integer version);

}
