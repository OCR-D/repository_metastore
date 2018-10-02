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
import edu.kit.datamanager.metastore.entity.XmlSchemaDefinition;

/**
 * @author Volker Hartmann
 *
 */
public interface XmlSchemaDefinitionRepository extends ArangoRepository<XmlSchemaDefinition, String> {
  /**
   * Find all xsd-files given by namespace.
   * 
   * @param namespace Namespace of the xsd-file (should be unique)
   * @return Instances of XML schema definition
   */
	Iterable<XmlSchemaDefinition> findByNamespace(String namespace);
  /**
   * Find all xsd-files given by prefix.
   * @param prefix Prefix of the xsd-file (should be unique)
   * @return  Instances of XML schema definition.
   */
	Iterable<XmlSchemaDefinition> findByPrefix(String prefix);

//	Collection<Character> findTop2DistinctBySurnameIgnoreCaseOrderByAgeDesc(String surname);
//
//	List<Character> findBySurnameEndsWithAndAgeBetweenAndNameInAllIgnoreCase(
//		String suffix,
//		int lowerBound,
//		int upperBound,
//		String[] nameList);
//
//	Optional<Character> findByNameAndSurname(String name, String surname);
//
//	Integer countByAliveTrue();
//
//	void removeBySurnameNotLikeOrAliveFalse(String surname);
//
//	Iterable<Character> findByChildsName(String name);
//
//	Iterable<Character> findByChildsAgeBetween(int lowerBound, int upperBound);
//
//	@Query("FOR c IN characters FILTER c.age > @0 SORT c.age DESC RETURN c")
//	Iterable<Character> getOlderThan(int value);
//
//	@Query("FOR c IN characters FILTER c.surname == @surname SORT c.age ASC RETURN c")
//	Iterable<Character> getWithSurname(@Param("surname") String value);
//
//	@Query("FOR c IN @@col FILTER c.surname == @surname AND c.age > @age RETURN c")
//	@QueryOptions(count = true)
//	ArangoCursor<Character> getWithSurnameOlderThan(@Param("age") int value, @BindVars Map<String, Object> bindvars);
//
//	@Query("FOR v IN 1..2 INBOUND @id @@edgeCol SORT v.age DESC RETURN DISTINCT v")
//	Set<Character> getAllChildsAndGrandchilds(@Param("id") String id, @Param("@edgeCol") Class<?> edgeCollection);

}
