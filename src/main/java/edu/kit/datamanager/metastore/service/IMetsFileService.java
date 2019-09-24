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

import edu.kit.datamanager.metastore.entity.MetsFile;
import java.util.List;

/**
 * Interface defining METS file service.
 */
public interface IMetsFileService {
  
  /**
   * Get all PAGEIDs defined inside METS.
   * 
   * @param resourceId Resource ID of METS document.
   * @return List holding all PAGEIDs.
   */
  List<String> getAllPageIds(String resourceId);
  
  /**
   * Get all USEs defined inside METS.
   * 
   * @param resourceId Resource ID of METS document.
   * @return List holding all USEs.
   */
  List<String> getAllUses(String resourceId);
  
  /**
   * Get all files referenced in the fileGrps inside METS.
   * 
   * @param resourceId Resource ID of METS document.
   * @return List holding all files.
   */
  List<MetsFile> getAvailableMetsFilesOfCurrentVersion(String resourceId);
  
  /**
   * Get all files referenced in the fileGrps inside METS filtered by
   * USE and PAGEID.
   * 
   * @param resourceId Resource ID of METS document.
   * @param use USEs of the fileGrp element.
   * @param pageId PAGEID of the FLocat element
   * @return List holding all files.
   */
  List<MetsFile> getAvailableMetsFilesByUseAndPageId(String resourceId, String[] use, String[] pageId);
    
  /**
   * Get file referenced by its IDs inside METS.
   * 
   * @param resourceId Resource ID of METS document.
   * @param fileId IDs of the FLocat element.
   * @return Selected file.
   */
  List<MetsFile> getAvailableMetsFilesByFileIds(String resourceId, String[] fileId);
}
