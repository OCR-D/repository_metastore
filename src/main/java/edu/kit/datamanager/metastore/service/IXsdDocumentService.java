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

import edu.kit.datamanager.metastore.entity.XmlSchemaDefinition;
import java.util.List;

/**
 * Interface defining XSD document service.
 */
public interface IXsdDocumentService {

  /**
   * Get all MetsDocuments/DigitalObjects.
   *
   * @return List holding all METS Documents.
   */
  List<XmlSchemaDefinition> getAllDocuments();

  /**
   * Get all versions with given resource ID.
   *
   * @param resourceId Resource ID of METS document.
   * 
   * @return List holding all versions.
   */
  List<String> getAllPrefixes();

  /**
   * Get XSD document with given namespace.
   *
   * @param namespace Namespace of the XSD document.
   *
   * @return XSD document.
   */
  XmlSchemaDefinition getDocumentByNamespace(String namespace);

  /**
   * Get XSD document with given prefix.
   *
   * @param prefix Resource ID of METS document.
   *
   * @return XSD document.
   */
  XmlSchemaDefinition getDocumentByPrefix(String prefix);

  /**
   * Create METS document.
   *
   * @param prefix prefix for XSD target namespace.
   * @param file METS file holding all information.
   */
  void createXsdDocument(String prefix, String file);
}
