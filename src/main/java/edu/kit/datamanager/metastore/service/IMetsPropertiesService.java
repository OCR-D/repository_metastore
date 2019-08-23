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

import edu.kit.datamanager.metastore.entity.ClassificationMetadata;
import edu.kit.datamanager.metastore.entity.GenreMetadata;
import edu.kit.datamanager.metastore.entity.LanguageMetadata;
import edu.kit.datamanager.metastore.entity.MetsIdentifier;
import edu.kit.datamanager.metastore.entity.MetsProperties;
import edu.kit.datamanager.metastore.entity.PageMetadata;
import java.util.List;
import java.util.Set;
import org.springframework.ui.Model;

/**
 * Interface defining section document service.
 */
public interface IMetsPropertiesService {
  /**
   * Get all resource identifiers of METS document with given
   * title.
   * @param title Title of METS document.
   * @return List holding all resourceIDs of selected METS document(s).
   */
  List<String> getResourceIdsByTitle(String title);

  /**
   * Get all resource identifiers of METS document with given
   * PPN.
   * @param ppn PPN of METS document.
   * @return List holding all resourceIDs of selected METS document(s).
   */
  List<String> getResourceIdsByPpn(String ppn);

  /**
   * Get all resource identifiers of METS document with given
   * ground truth label(s).
   * @param label Ground truth label(s) of METS document.
   * @param pageOnly Search for labels inside one page.
   * @return List holding all resourceIDs of selected METS document(s).
   */
  List<String> getResourceIdsByGtLabel(String[] label, boolean pageOnly);

  /**
   * Get all resource identifiers of METS document with given
   * classification(s).
   * @param classification Classification(s) of METS document.
   * @return List holding all resourceIDs of selected METS document(s).
   */
  List<String> getResourceIdsByClassification(String[] classification);

  /**
   * Get all resource identifiers of METS document with given
   * language(s).
   * @param language Language(s) of METS document.
   * @return List holding all resourceIDs of selected METS document(s).
   */
  List<String> getResourceIdsByLanguage(String[] language);

  /**
   * Get all resource identifiers of METS document with given
   * identifier.
   * @param identifier Identifier of METS document.
   * @param type Type of identifier.
   * @return List holding all resourceIDs of selected METS document(s).
   */
  List<String> getResourceIdsByIdentifier(String identifier, String type);

  /**
   * Get metadata of METS document with given
   * resource identifier.
   * @param resourceId resource identifier of METS document.
   * @return Object holding metadata of selected METS document.
   */
  MetsProperties getMetadataByResourceId(String resourceId);

  /**
   * Get classification metadata of METS document with given
   * resource identifier.
   * @param resourceId resource identifier of METS document.
   * @return Object holding classification metadata of selected METS document.
   */
  List<ClassificationMetadata> getClassificationMetadataByResourceId(String resourceId);

  /**
   * Get language metadata of METS document with given
   * resource identifier.
   * @param resourceId resource identifier of METS document.
   * @return Object holding language metadata of selected METS document.
   */
  List<LanguageMetadata> getLanguageMetadataByResourceId(String resourceId);

  /**
   * Get genre metadata of METS document with given
   * resource identifier.
   * @param resourceId resource identifier of METS document.
   * @return Object holding genre metadata of selected METS document.
   */
  List<GenreMetadata> getGenreMetadataByResourceId(String resourceId);

  /**
   * Get page metadata of METS document with given
   * resource identifier.
   * @param resourceId resource identifier of METS document.
   * @return Object holding page metadata of selected METS document.
   */
  List<PageMetadata> getPageMetadataByResourceId(String resourceId);

  /**
   * Get mets identifier of METS document with given
   * resource identifier.
   * @param resourceId resource identifier of METS document.
   * @return Object holding page metadata of selected METS document.
   */
  List<MetsIdentifier> getIdentifierByResourceId(String resourceId);

  /**
   * Get mets identifier of METS document with given
   * resource identifier and type.
   * @param resourceId resource identifier of METS document.
   * @param type Type of the mets/mods identifier.
   * @return Object holding page metadata of selected METS document.
   */
  List<MetsIdentifier> getIdentifierByResourceIdAndType(String resourceId, String type);

    /**
     * Add all possible values to model.
     * @param model model holding all values
     */
    public void addFeaturesToModel(Model model); 
}
