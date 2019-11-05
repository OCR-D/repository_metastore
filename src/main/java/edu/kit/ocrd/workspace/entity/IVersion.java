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
package edu.kit.ocrd.workspace.entity;

/**
 * Interface for the version of METS document.
 * The initial version starts by 1 and will be incremented for each new version.
 */
public interface IVersion {
  /**
   * Set version of the METS document.
   * 
   * @param version Version of the METS document.
   */
  public void setVersion(Integer version);
  
  /**
   * Get version of the METS document.
   * 
   * @return Version of the METS document. 
   */
  public Integer getVersion();
  
}
