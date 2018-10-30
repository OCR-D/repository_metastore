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
import edu.kit.datamanager.metastore.entity.IResourceId;
import edu.kit.datamanager.metastore.entity.MetsProperties;

/**
 * Repository holding all MetsDocuments.
 */
public interface MetsPropertiesRepository extends ArangoRepository<MetsProperties, String> {

  /**
   * Find METS documents by Title.
   *
   * @param title Title of the resource.
   *
   * @return List of METS documents with given title.
   */
  Iterable<IResourceId> findResourceIdByTitle(String title);

  /**
   * Find METS documents by PPN.
   *
   * @param ppn PPN of the resource.
   *
   * @return List of METS documents with given PPN.
   */
  Iterable<IResourceId> findResourceIdByPpn(String ppn);
}
