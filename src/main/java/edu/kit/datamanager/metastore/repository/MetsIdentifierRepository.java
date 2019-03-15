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
import edu.kit.datamanager.metastore.entity.MetsIdentifier;

/**
 * Repository holding all MetsIdentifier.
 */
public interface MetsIdentifierRepository extends ArangoRepository<MetsIdentifier, String> {

  /**
   * Find MetsIdentifiers by ResourceIdentifier.
   *
   * @param resourceId ResourceIdentifier of the resource.
   *
   * @return List of MetsIdentifiers with given resourceIdentifier.
   */
  Iterable<MetsIdentifier> findByResourceId(String resourceId);

  /**
   * Find MetsIdentifiers by ResourceIdentifier.
   *
   * @param resourceId ResourceIdentifier of the resource.
   * @param type Type of the document.
   *
   * @return List of MetsIdentifiers with given resourceIdentifier.
   */
  Iterable<MetsIdentifier> findByResourceIdAndType(String resourceId, String type);

  /**
   * Find MetsIdentifiers by identifier.
   *
   * @param identifier Identifier of the resource.
   *
   * @return List of MetsIdentifiers with given title.
   */
  Iterable<MetsIdentifier> findByIdentifier(String identifier);

}
