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
package edu.kit.datamanager.metastore.service;

import edu.kit.datamanager.metastore.entity.MetsDocument;
import edu.kit.datamanager.metastore.exception.ResourceAlreadyExistsException;
import java.util.List;

/**
 * Interface defining METS service.
 * 
 */
public interface IMetsDocumentService {
  /**
   * Get all current MetsDocuments/DigitalObjects.
   * 
   * @return List holding all METS Documents. 
   */
  List<MetsDocument> getAllDocuments();
  
  /**
   * Get most recent content of METS document with given resource ID.
   * 
   * @param resourceId Resource ID of METS document.
   * @return METS document.
   */
  MetsDocument getMostRecentMetsDocumentByResourceId(String resourceId);

  /**
   * Get all versions with given resource ID.
   * 
   * @param resourceId Resource ID of METS document.
   * @return List holding all versions.
   */
  List<Integer> getAllVersionsByResourceId(String resourceId);
  /**
   * Get METS document with given resource ID and version.
   * 
   * @param resourceId Resource ID of METS document.
   * @param version Version of METS document.
   * @return List holding all versions of METS document.
   */
  MetsDocument getDocumentByResourceIdAndVersion(String resourceId, Integer version);
  
  /**
   * Create METS document.
   * 
   * @param resourceId Resource ID of the document.
   * @param fileContent METS file holding all information.
   */
  void createMetsDocument(String resourceId, String fileContent) throws ResourceAlreadyExistsException ;
  /**
   * Create METS document.
   * 
   * @param resourceId Resource ID of the document.
   * @param fileContent METS file holding all information.
   */
  void updateMetsDocument(String resourceId, String fileContent);
 
}
