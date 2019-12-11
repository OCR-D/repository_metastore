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
package edu.kit.datamanager.metastore.repository;

import com.arangodb.springframework.repository.ArangoRepository;
import edu.kit.ocrd.workspace.entity.ProvenanceMetadata;

/**
 * Repository holding all ProvenanceMetadata.
 */
public interface ProvenanceMetadataRepository extends ArangoRepository<ProvenanceMetadata, String> {

  /**
   * Find all ProvenanceMetadata of a METS document.
   *
   * @param resourceId Resource ID of the METS document.
   *
   * @return All provenance of a METS document.
   */
  Iterable<ProvenanceMetadata> findByResourceIdOrderByStartProcessorAsc(String resourceId);

  /**
   * Find all ProvenanceMetadata of a METS document.
   *
   * @param resourceId Resource ID of the METS document.
   * @param workflowId Workflow ID of the workflow.
   *
   * @return Provenance of one workflow of a METS document.
   */
  Iterable<ProvenanceMetadata> findByResourceIdAndWorkflowIdOrderByStartProcessorAsc(String resourceId, String workflowId);

}
