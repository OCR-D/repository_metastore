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

import edu.kit.ocrd.dao.MetsMetadata;
import edu.kit.ocrd.workspace.entity.MetsDocument;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
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
   * Get metadata of current METS document with given resourceID.
   *
   * @param resourceId ResourceID of the METS document.
   * @param model Model holding information about metadata.
   *
   * @return Metadata of METS document with given resourceID.
   */
  @ApiOperation(value = "Get metadata of currrent METS documents with given resourceID.",
          notes = "Only the current METS document is supplied.")
  @RequestMapping(path = "{resourceId}/metadata", method = RequestMethod.GET, produces = "application/json")
  @ResponseBody
  ResponseEntity<MetsMetadata> getLatestMetadataOfDocument(@ApiParam(value = "The numeric resource identifier.", required = true) @PathVariable(value = "resourceId") String resourceId, Model model);

  /**
   * Get metadata of current METS document with given resourceID.
   *
   * @param resourceId ResourceID of the METS document.
   * @param model Model holding information about metadata.
   *
   * @return Metadata of METS document with given resourceID.
   */
  @ApiOperation(value = "Get metadata of currrent METS documents with given resourceID.",
          notes = "Only the current METS document is supplied.")
  @RequestMapping(path = "{resourceId}/metadata", method = RequestMethod.GET, produces = "text/html")
  String getLatestMetadataOfDocumentAsHtml(@ApiParam(value = "The numeric resource identifier.", required = true) @PathVariable(value = "resourceId") String resourceId, Model model);

  /**
   * Get (ground truth) metadata of pages of current METS document with given resourceID.
   *
   * @param resourceId ResourceID of the METS document.
   * @param model Model holding information about metadata.
   *
   * @return Metadata of METS document with given resourceID.
   */
  @ApiOperation(value = "Get (ground truth) metadata of pages of currrent METS documents with given resourceID.",
          notes = "Only the current METS document is supplied.")
  @RequestMapping(path = "{resourceId}/groundtruth", method = RequestMethod.GET, produces = "text/html")
  String getLatestGroundTruthMetadataOfDocumentAsHtml(@ApiParam(value = "The numeric resource identifier.", required = true) @PathVariable(value = "resourceId") String resourceId, Model model);

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
   * Get all METS documents with given ground truth label(s).
   *
   * @param label Ground truth labels of the METS document.
   *
   * @return List of resourceIDs with given label.
   */
  @ApiOperation(value = "Get all resourceIds with given ground truth label(s).",
          notes = "Only the current METS documents are supplied.")
  @RequestMapping(path = "labeling", method = RequestMethod.GET)
  @ResponseBody
  ResponseEntity<List<String>> getResourceIdBySemanticLabel(@ApiParam(value = "The ground truth label(s) of the METS document.", required = true)@RequestParam(value = "label") String[] label);

  /**
   * Get all METS documents with given ground truth label(s).
   *
   * @param label Ground truth labels of the METS document.
   *
   * @return List of resourceIDs with given label.
   */
  @ApiOperation(value = "Get all resourceIds with given ground truth label(s).",
          notes = "Only the current METS documents are supplied.")
  @RequestMapping(path = "labeling", method = RequestMethod.GET, produces = "text/html")
  String getResourceIdBySemanticLabelAsHtml(@ApiParam(value = "The ground truth label(s) of the METS document.", required = true)@RequestParam(value = "label") String[] label, Model model);

  /**
   * Get all METS documents with given ground truth label(s).
   *
   * @param label Ground truth labels of the METS document.
   *
   * @return List of resourceIDs with given label.
   */
  @ApiOperation(value = "Get all resourceIds with given semantic label(s) of a single page.")
  @RequestMapping(path = "labeling/page", method = RequestMethod.GET)
  @ResponseBody
  ResponseEntity<List<String>> getResourceIdBySemanticLabelOfPage(@ApiParam(value = "The semantic label(s) of a single page inside the METS document.", required = true)@RequestParam(value = "label") String[] label);

  /**
   * Get all METS documents with given classification(s).
   *
   * @param label Classifications of the METS document.
   *
   * @return List of resourceIDs with given classifications.
   */
  @ApiOperation(value = "Get all resourceIds with given classification(s).")
  @RequestMapping(path = "classification", method = RequestMethod.GET)
  @ResponseBody
  ResponseEntity<List<String>> getResourceIdByClassification(@ApiParam(value = "The classification(s) of the METS document.", required = true)@RequestParam(value = "class") String[] classification);

  /**
   * Get all METS documents with given classification(s).
   *
   * @param label Classifications of the METS document.
   *
   * @return List of resourceIDs with given classifications.
   */
  @ApiOperation(value = "Get all resourceIds with given classification(s).")
  @RequestMapping(path = "classification", method = RequestMethod.GET, produces = "text/html")
  String getResourceIdByClassificationAsHtml(@ApiParam(value = "The classification(s) of the METS document.", required = true)@RequestParam(value = "class") String[] classification, Model model);

  /**
   * Get all METS documents with given language(s).
   *
   * @param language Language(s) of the METS document.
   *
   * @return List of resourceIDs with given language(s).
   */
  @ApiOperation(value = "Get all resourceIds with given language(s).")
  @RequestMapping(path = "language", method = RequestMethod.GET)
  @ResponseBody
  ResponseEntity<List<String>> getResourceIdByLanguage(@ApiParam(value = "The language(s) of the METS document.", required = true)@RequestParam(value = "lang") String[] language);

  /**
   * Get all METS documents with given language(s).
   *
   * @param language Language(s) of the METS document.
   *
   * @return List of resourceIDs with given language(s).
   */
  @ApiOperation(value = "Get all resourceIds with given language(s).")
  @RequestMapping(path = "language", method = RequestMethod.GET, produces = "text/html")
  String getResourceIdByLanguageAsHtml(@ApiParam(value = "The language(s) of the METS document.", required = true)@RequestParam(value = "lang") String[] language, Model model);

  /**
   * Get all METS documents with given identifier.
   *
   * @param identifier Identifier of the METS document.
   * @param type Type of the identifier.
   *
   * @return List of resourceIDs with given identifier.
   */
  @ApiOperation(value = "Get all resourceIds with given identifier.")
  @RequestMapping(path = "identifier", method = RequestMethod.GET)
  @ResponseBody
  ResponseEntity<List<String>> getResourceIdByIdentifier(@ApiParam(value = "The identifier of the METS document.", required = true)@RequestParam(value = "identifier") String identifier,
          @ApiParam(value = "The type of the identifier.", required = false)@RequestParam(value = "type") String type);

  /**
   * Get all METS documents with given identifier.
   *
   * @param identifier Identifier of the METS document.
   * @param type Type of the identifier.
   *
   * @return List of resourceIDs with given identifier.
   */
  @ApiOperation(value = "Get all resourceIds with given identifier.")
  @RequestMapping(path = "identifier", method = RequestMethod.GET, produces = "text/html")
  String getResourceIdByIdentifierAsHtml(@ApiParam(value = "The identifier of the METS document.", required = true)@RequestParam(value = "identifier") String identifier,
          @ApiParam(value = "The type of the identifier.", required = false)@RequestParam(value = "type") String type, Model model);

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
