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

import java.util.List;

/**
 * Interface defining section document service.
 */
public interface IMetsPropertiesService {
  /**
   * Get all resource Identifiers of METS document with given
   * title.
   * @param title Title of METS document.
   * @return List holding all prefixes of selected METS document.
   */
  List<String> getResourceIdsByTitle(String title);


  /**
   * Get all resource Identifiers of METS document with given
   * PPN.
   * @param ppn PPN of METS document.
   * @return List holding all prefixes of selected METS document.
   */
  List<String> getResourceIdsByPpn(String ppn);

}
