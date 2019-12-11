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

import edu.kit.datamanager.metastore.exception.InvalidFormatException;
import edu.kit.ocrd.workspace.entity.ProvenanceMetadata;
import java.io.File;
import java.util.List;

/**
 * Interface defining METS service.
 */
public interface IProvenanceMetadataService {
  
  /**
   * Get provenance of METS document with given resource ID.
   * 
   * @param resourceId Resource ID of METS document.
   * @return List holding all provenance of METS document.
   */
  List<ProvenanceMetadata> getProvenanceMetadataByResourceId(String resourceId);
  
  /**
   * Get provenance of METS document with given resource ID and workflow ID.
   * 
   * @param resourceId Resource ID of METS document.
   * @param workflowId ID of the workflow.
   * @return List holding all provenance of METS document with given workflow ID.
   */
  List<ProvenanceMetadata> getProvenanceMetadataByResourceIdAndWorkflowId(String resourceId, String workflowId);
  
  /**
   * Create METS document.
   * Extracts provenance metadata and persists them in the 
   * local repositories.
   * 
   * @param resourceId Resource ID of the document.
   * @param metsFile METS file holding all information.
   * @param provenanceFile Provenance file holding all provenance.
   */
  void createProvenanceMetadata(String resourceId, File metsFile, File provenanceFile) throws InvalidFormatException;
  
}
