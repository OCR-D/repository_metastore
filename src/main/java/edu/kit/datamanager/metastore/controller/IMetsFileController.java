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

import edu.kit.ocrd.workspace.entity.MetsFile;
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
public interface IMetsFileController {

  /**
   * Get all referenced files of a METS document filtered by PAGEID AND/OR USE or ID.
   * 
   *
   * @param resourceId ResourceID of the METS document.
   * @param pageId PAGEIDs of the files.
   * @param use USEs of the files.
   * @param fileId fileIds of the file.
   * @param model Model holding all information about collected files.
   * 
   * @return all METS documents.
   */
  @ApiOperation(value = "Get all files referenced in METS document.",
          notes = "List of all files optionally filtered by PAGEID AND USE or by ID.")
  @RequestMapping(path = "{resourceId}/files", method = RequestMethod.GET, produces = "application/json")
  @ResponseBody
  ResponseEntity<List<MetsFile>> getAllFilesOfMetsDocument(@ApiParam(value = "The numeric resource identifier.", required = true) @PathVariable(value = "resourceId") String resourceId,
                    @ApiParam(value = "The PAGEID of the files.", required = false) @RequestParam(value = "pageId") String[] pageId,
                    @ApiParam(value = "The USE of the files.", required = false) @RequestParam(value = "use") String[] use,
                    @ApiParam(value = "The IDs of the files.", required = false) @RequestParam(value = "fileId") String[] fileId);

  /**
   * Get an HTML page with all referenced files of a METS document filtered by PAGEID AND/OR USE or ID.
   * 
   *
   * @param resourceId ResourceID of the METS document.
   * @param pageId PAGEIDs of the files.
   * @param use USEs of the files.
   * @param fileId fileIds of the file.
   * @param model Model holding all information about collected files.
   * 
   * @return all METS documents.
   */
  @ApiOperation(value = "Get all files referenced in METS document.",
          notes = "List of all files optionally filtered by PAGEID AND USE or by ID.")
  @RequestMapping(path = "{resourceId}/files", method = RequestMethod.GET, produces = "text/html")
  String getAllFilesOfMetsDocumentAsHtml(@ApiParam(value = "The numeric resource identifier.", required = true) @PathVariable(value = "resourceId") String resourceId,
                    @ApiParam(value = "The PAGEID of the files.", required = false) @RequestParam(value = "pageId") String[] pageId,
                    @ApiParam(value = "The USE of the files.", required = false) @RequestParam(value = "use") String[] use,
                    @ApiParam(value = "The IDs of the files.", required = false) @RequestParam(value = "fileId") String[] fileId,
                    Model model);

  /**
   * Get all URLs of referenced files of a METS document.
   *
   * @param resourceId ResourceID of the METS document.
   * @param pageId PAGEIDs of the files.
   * @param use USEs of the files.
   * @param fileId fileIds of the file.
   * 
   * @return all METS documents.
   */
  @ApiOperation(value = "Get all URLs of files referenced in METS document.",
          notes = "List of all URLs optionally filtered by PAGEID AND USE or by ID.")
  @RequestMapping(path = "{resourceId}/files/url", method = RequestMethod.GET)
  @ResponseBody
  ResponseEntity<List<String>> getUrlOfAllFilesOfMetsDocument(@ApiParam(value = "The numeric resource identifier.", required = true) @PathVariable(value = "resourceId") String resourceId,
                    @ApiParam(value = "The PAGEID of the files.", required = false) @RequestParam(value = "pageId") String[] pageId,
                    @ApiParam(value = "The USE of the files.", required = false) @RequestParam(value = "use") String[] use,
                    @ApiParam(value = "The IDs of the files.", required = false) @RequestParam(value = "fileId") String[] fileId);

  /**
   * Get all PAGEIDs of a METS document.
   * 
   *
   * @param resourceId ResourceID of the METS document.
   * 
   * @return all PAGEIDs of METS documents.
   */
  @ApiOperation(value = "Get all PAGEIDs defined in METS document.")
  @RequestMapping(path = "{resourceId}/pageid", method = RequestMethod.GET)
  @ResponseBody
  ResponseEntity<List<String>> getAllPageIdsOfMetsDocument(@ApiParam(value = "The numeric resource identifier.", required = true) @PathVariable(value = "resourceId") String resourceId);

  /**
   * Get all USEs of a METS document.
   * 
   *
   * @param resourceId ResourceID of the METS document.
   * 
   * @return all USEs of METS documents.
   */
  @ApiOperation(value = "Get all USEs defined in METS document.")
  @RequestMapping(path = "{resourceId}/use", method = RequestMethod.GET)
  @ResponseBody
  ResponseEntity<List<String>> getAllUsesOfMetsDocument(@ApiParam(value = "The numeric resource identifier.", required = true) @PathVariable(value = "resourceId") String resourceId);
}
