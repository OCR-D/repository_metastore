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
import edu.kit.ocrd.workspace.entity.LanguageMetadata;

/**
 * Repository holding all LanguageMetadata.
 */
public interface LanguageMetadataRepository extends ArangoRepository<LanguageMetadata, String> {

  /**
   * Find LanguageMetadata by ResourceIdentifier.
   *
   * @param resourceId ResourceIdentifier of the resource.
   *
   * @return List of LanguageMetadatas with given resourceIdentifier.
   */
  Iterable<LanguageMetadata> findByResourceId(String resourceId);

  /**
   * Find LanguageMetadata by language.
   *
   * @param language Language of the document.
   *
   * @return List of LanguageMetadata with given language.
   */
  Iterable<LanguageMetadata> findByLanguage(String language);

}
