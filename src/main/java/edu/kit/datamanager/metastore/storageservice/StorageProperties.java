*
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
package edu.kit.datamanager.metastore.storageservice;

import org.springframework.boot.context.properties.ConfigurationProperties;
/**
 * Storing configuration of upload directory.
 */
@ConfigurationProperties("storage")
public class StorageProperties {

  /**
   * Folder location for storing files
   */
  private String location = "/tmp/upload";
  /**
   * Folder location for archiving uploaded files
   */
  private String archive = "/tmp/archive";

  /**
   * Get the base path for uploading files.
   *
   * @return base path as string.
   */
  public String getLocation() {
    return location;
  }

  /**
   * Set the base path for uploading files.
   *
   * @param location base path as string.
   */
  public void setLocation(String location) {
    this.location = location;
  }

  /**
   * Get the path for archiving data.
   *
   * @return the archive
   */
  public String getArchive() {
    return archive;
  }

  /**
   * Set the path for archiving data.
   *
   * @param archive the archive to set
   */
  public void setArchive(String archive) {
    this.archive = archive;
  }

}
