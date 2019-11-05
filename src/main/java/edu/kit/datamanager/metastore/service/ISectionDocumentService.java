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

import edu.kit.ocrd.workspace.entity.SectionDocument;
import java.util.List;

/**
 * Interface defining section document service.
 */
public interface ISectionDocumentService {

  /**
   * Get prefix of all section documents stored inside the METS document with given
   * resource ID.
   * 
   * @param resourceId Resource ID of METS document.
   * 
   * @return List holding all prefixes of selected METS document.
   */
  List<String> getPrefixOfAvailableSectionDocuments(String resourceId);

  /**
   * Get all section documents stored inside the METS document with given
   * resource ID.
   * 
   * @param resourceId Resource ID of METS document.
   * 
   * @return List holding all section documents of METS document.
   */
  List<SectionDocument> getAllSectionDocuments(String resourceId);

  /**
   * Get section document stored inside the METS document with given
   * resource ID.
   * 
   * @param resourceId Resource ID of METS document.
   * @param prefix Prefix of the section document.
   * 
   * @return Section document of METS document with given prefix.
   */
  SectionDocument getSectionDocument(String resourceId, String prefix);
}
