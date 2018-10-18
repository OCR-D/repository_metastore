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

import edu.kit.datamanager.metastore.entity.MetsDocument;
import edu.kit.datamanager.metastore.entity.MetsFile;
import edu.kit.datamanager.metastore.entity.SectionDocument;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

/**
 * Interface defining section document service.
 * 
 * @author hartmann-v
 */
public interface ISectionDocumentService {
  /**
   * Get all MetsDocuments/DigitalObjects.
   * 
   * @return List holding all METS Documents. 
   */
  List<MetsDocument> getAllDocuments();
  
  /**
   * Get most recent content of METS document with given resource ID.
   * 
   * @param resourceId Resource ID of METS document.
   * @return METS document.
   */
  MetsDocument getMostRecentMetsDocumentByResourceId(String resourceId);
  
  /**
   * Get most recent METS document with given resource ID.
   * 
   * @param resourceId Resource ID of METS document.
   * @return METS document.
   */
  MetsDocument getMostRecentDocumentByResourceId(String resourceId);

  /**
   * Get all versions of METS document with given resource ID.
   * 
   * @param resourceId Resource ID of METS document.
   * @return List holding all versions of METS document.
   */
  List<MetsDocument> getDocumentByResourceId(String resourceId);

  /**
   * Get METS document with given resource ID and version.
   * 
   * @param resourceId Resource ID of METS document.
   * @param version Version of METS document.
   * @return List holding all versions of METS document.
   */
  MetsDocument getDocumentByResourceIdAndVersion(String resourceId, Integer version);

  /**
   * Get all versions with given resource ID.
   * 
   * @param resourceId Resource ID of METS document.
   * @return List holding all versions.
   */
  List<Integer> getAllVersionsByResourceId(String resourceId);

  /**
   * Get prefix of all section documents stored inside the METS document with given
   * resource ID.
   * @param resourceId Resource ID of METS document.
   * @return List holding all prefixes of selected METS document.
   */
  List<String> getPrefixOfAvailableSectionDocuments(String resourceId);

  /**
   * Get all section documents stored inside the METS document with given
   * resource ID.
   * 
   * @param resourceId Resource ID of METS document.
   * @return List holding all section documents of METS document.
   */
  List<SectionDocument> getAllSectionDocuments(String resourceId);

  /**
   * Get section document stored inside the METS document with given
   * resource ID.
   * 
   * @param resourceId Resource ID of METS document.
   * @param prefix Prefix of the section document.
   * @return Section document of METS document with given prefix.
   */
  SectionDocument getSectionDocument(String resourceId, String prefix);
  
  /**
   * Get all files referenced in the fileGrps inside METS.
   * 
   * @param resourceId Resource ID of METS document.
   * @param version Version of METS document.
   * @return List holding all files.
   */
  List<MetsFile> getAvailableMetsFiles(String resourceId, Integer version);
  
  /**
   * Get all files referenced in the fileGrps inside METS filtered by
   * USE and GROUPID.
   * 
   * @param resourceId Resource ID of METS document.
   * @param use USE of the fileGrp element.
   * @param groupId GROUPID of the FLocat element
   * @return List holding all files.
   */
  List<MetsFile> getAvailableMetsFilesByUseAndGroupId(String resourceId, String[] use, String[] groupId);
    
  /**
   * Get file referenced by its ID inside METS.
   * 
   * @param resourceId Resource ID of METS document.
   * @param fileId ID of the FLocat element.
   * @return Selected file.
   */
  List<MetsFile> getAvailableMetsFilesByFileId(String resourceId, String fileId);
  
  /**
   * Create METS document.
   * 
   * @param file METS file holding all information.
   */
  void createMetsDocument(MultipartFile file);
}
