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
import edu.kit.ocrd.workspace.entity.ZippedBagit;

/**
 * Repository holding all zipped BagIt containers.
 */
public interface ZippedBagitRepository extends ArangoRepository<ZippedBagit, String> {

  /**
   * Find bagIt containers by ResourceIdentifier.
   *
   * @param resourceId ResourceIdentifier of the resource.
   *
   * @return List of bagIt containers with given resourceIdentifier.
   */
  Iterable<ZippedBagit> findByResourceId(String resourceId);

  /**
   * Find all bagIt containers by OCRD identifier.
   *
   * @param ocrdIdentifier OCRD Identifier of the resource.
   *
   * @return List of bagIt containers with given OCRD Identifier.
   */
  Iterable<ZippedBagit> findByOcrdIdentifierOrderByVersionDesc(String ocrdIdentifier);

  /**
   * Find all bagIt containers by OCRD identifier.
   *
   * @param ocrdIdentifier OCRD Identifier of the resource.
   * @param version  Version of the resource.
   *
   * @return List of bagIt containers with given OCRD Identifier.
   */
  Iterable<ZippedBagit> findByOcrdIdentifierAndVersion(String ocrdIdentifier, Integer version);

  /**
   * Find all latest bagIt containers sorted by date.
   *
   * @param ocrdIdentifier OCRD Identifier of the resource.
   *
   * @return List of bagIt containers with given OCRD Identifier.
   */
  Iterable<ZippedBagit> findByLatestTrueOrderByUploadDateDesc();
}
