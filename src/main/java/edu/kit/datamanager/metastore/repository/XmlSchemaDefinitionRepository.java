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
import edu.kit.datamanager.metastore.entity.XmlSchemaDefinition;

/**
 * Repository holding all XSD documents. (Holds only the root document. All
 * other documents has to be accessible via URL)
 */
public interface XmlSchemaDefinitionRepository extends ArangoRepository<XmlSchemaDefinition, String> {

  /**
   * Find all xsd-files given by namespace.
   *
   * @param namespace Namespace of the xsd-file (should be unique)
   * @return Instances of XML schema definition
   */
  XmlSchemaDefinition findByNamespace(String namespace);

  /**
   * Find all xsd-files given by prefix.
   *
   * @param prefix Prefix of the xsd-file (should be unique)
   * @return Instances of XML schema definition.
   */
  XmlSchemaDefinition findByPrefix(String prefix);
}
