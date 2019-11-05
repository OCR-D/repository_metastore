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
 * See the License for the specific genre governing permissions and
 * limitations under the License.
 */
package edu.kit.datamanager.metastore.repository;

import com.arangodb.springframework.repository.ArangoRepository;
import edu.kit.ocrd.workspace.entity.GenreMetadata;

/**
 * Repository holding all GenreMetadata.
 */
public interface GenreMetadataRepository extends ArangoRepository<GenreMetadata, String> {

  /**
   * Find GenreMetadata by ResourceIdentifier.
   *
   * @param resourceId ResourceIdentifier of the resource.
   *
   * @return List of GenreMetadatas with given resourceIdentifier.
   */
  Iterable<GenreMetadata> findByResourceId(String resourceId);

  /**
   * Find GenreMetadata by genre.
   *
   * @param genre Genre of the document.
   *
   * @return List of GenreMetadata with given genre.
   */
  Iterable<GenreMetadata> findByGenre(String genre);

}
