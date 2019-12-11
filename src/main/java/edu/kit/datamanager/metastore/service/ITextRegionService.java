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

import edu.kit.datamanager.metastore.exception.InvalidFormatException;
import edu.kit.ocrd.workspace.entity.TextRegion;
import java.io.File;
import java.util.List;

/**
 * Interface defining METS service.
 */
public interface ITextRegionService {
  
  /**
   * Get text regions of METS document with given resource ID.
   * 
   * @param resourceId Resource ID of METS document.
   * @return List holding all text regions of METS document.
   */
  List<TextRegion> getTextRegionByResourceId(String resourceId);
 
  /**
   * Create text regions of METS document.
   * Extracts text regions and persists them in the 
   * local repositories.
   * 
   * @param resourceId Resource ID of the document.
   * @param metsFile METS file holding all information.
   */
  void createTextRegion(String resourceId, File metsFile) throws InvalidFormatException;
  
}
