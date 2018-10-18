/*
 * DISCLAIMER
 *
 * Copyright 2017 ArangoDB GmbH, Cologne, Germany
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Copyright holder is ArangoDB GmbH, Cologne, Germany
 */

package edu.kit.datamanager.metastore.repository;

import com.arangodb.springframework.repository.ArangoRepository;
import edu.kit.datamanager.metastore.entity.IVersion;
import edu.kit.datamanager.metastore.entity.MetsDocument;
import java.util.Collection;

/**
 * Repository holding all MetsDocuments.
 * 
 * @author Volker Hartmann
 *
 */
public interface MetsDocumentRepository extends ArangoRepository<MetsDocument, String> {
  /**
   * Find all current versions of METS documents.
   * 
   * @return Current versions of all METS documents.
   */
	Iterable<MetsDocument> findByCurrentTrue();
  /** 
   * Find most recent version of a METS document.
   * 
   * @param resourceId Resource ID of the METS document.
   * @return  Most recent version of METS document.
   */
	MetsDocument findByResourceIdAndCurrentTrue(String resourceId);
  /**
   * Find all versions of a METS document.
   * 
   * @param resourceId Resource ID of the METS document.
   * @param version Version of the METS document.
   * @return All versions of a METS document.
   */
	MetsDocument findByResourceIdAndVersion(String resourceId, Integer version);
  /**
   * Find all versions of a METS document.
   * Most recent version first.
   * 
   * @param resourceId Resource ID of the METS document.
   * @return All versions of given METS document.
   */
	Iterable<IVersion> findVersionByResourceIdOrderByVersionDesc(String resourceId);

}
