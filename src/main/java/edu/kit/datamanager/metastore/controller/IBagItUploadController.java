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

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.client.ApiException;
import java.io.IOException;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Interface for REST service handling upload of zipped Bagit containers.
 */
public interface IBagItUploadController {

  /**
   * Handle upload of file (zipped BagIt container)
   *
   * @param file Instance holding content of file and attributes of the file.
   * @param redirectAttributes Attributes storing internal information.
   *
   * @return Website displaying information about uploaded files.
   * @throws IOException Error during upload.
   */
  @ApiOperation(value = "Upload zipped BagIt container.",
          notes = "This endpoint allows to upload zipped BagIt container holding"
                  + "OCR-D data.")
  @RequestMapping(path = "", method = RequestMethod.POST)
  @ResponseBody
  ResponseEntity<?> handleFileUpload(@ApiParam(value = "Zipped BagIt container.")@RequestParam(value = "file") MultipartFile file, 
          RedirectAttributes redirectAttributes) throws IOException, ApiException;


  /**
   * Listing of uploaded files.
   *
   * @param model Model holding information about uploaded files.
   *
   * @return Website displaying information about uploaded files.
   * @throws IOException Error while storing/reading file.
   */
  @ApiOperation(value = "List all uploaded containers.",
          notes = "List of all zipped BagIt containers with Form to upload new BagIt container.")
  @RequestMapping(path = "", method = RequestMethod.GET, produces = "text/html")
  String listUploadedFilesAsHtml(Model model) throws IOException;
  /**
   * Listing of uploaded files.
   *
   * @param model Model holding information about uploaded files.
   *
   * @return List with URLs of all zipped BagIt containers.
   * @throws IOException Error while storing/reading file.
   */
  @ApiOperation(value = "List all uploaded containers.",
          notes = "List with URLs of all zipped BagIt containers.")
  @RequestMapping(path = "", method = RequestMethod.GET, produces = "application/json")
  @ResponseBody
  ResponseEntity<List<String>> listUploadedFiles(Model model) throws IOException;

  /**
   * Filtered listing of uploaded files.
   *
   * @param model Model holding information about filtered files.
   *
   * @return Website displaying information about uploaded files.
   * @throws IOException Error while storing/reading file.
   */
  @ApiOperation(value = "List find containers.",
          notes = "List filtered zipped BagIt containers with Form to search for BagIt container.")
  @RequestMapping(path = "search", method = RequestMethod.GET, produces = "text/html")
  String listFilteredFilesAsHtml(Model model) throws IOException;

  /**
   * Get all METS documents with given ocrd identifier.
   *
   * @param ocrdIdentifier OCRD identifier of the METS document.
   *
   * @return List of resourceIDs with given ocrd identifier.
   */
  @ApiOperation(value = "Get all resourceIds with given ocrd identifier.")
  @RequestMapping(path = "ocrdidentifier", method = RequestMethod.GET)
  @ResponseBody
  ResponseEntity<List<String>> getResourceIdByOcrdIdentifier(@ApiParam(value = "The ocrd identifier of the METS document.", required = true)@RequestParam(value = "ocrdidentifier") String ocrdIdentifier);

  /**
   * Get all METS documents with given ocrd identifier.
   *
   * @param ocrdIdentifier OCRD identifier of the METS document.
   *
   * @return List of resourceIDs with given ocrd identifier.
   */
  @ApiOperation(value = "Get all resourceIds with given ocrd identifier.")
  @RequestMapping(path = "ocrdidentifier", method = RequestMethod.GET, produces = "text/html")
  String getResourceIdByOcrdIdentifierAsHtml(@ApiParam(value = "The ocrd identifier of the METS document.", required = true)@RequestParam(value = "ocrdidentifier") String ocrdIdentifier, Model model);

  
}
