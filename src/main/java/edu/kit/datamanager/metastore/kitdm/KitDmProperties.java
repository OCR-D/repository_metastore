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
package edu.kit.datamanager.metastore.kitdm;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
/**
 * Storing configuration of upload directory.
 */
@Configuration
@ConfigurationProperties("kitdm20")
public class KitDmProperties {

  /**
   * Folder basePath for storing files
   */
  private String basePath = "http://localhost:8090";
  /**
   * Show debug messages.
   */
  private String debug = "false";

  /**
   * Get the base path for KIT DM 2.0.
   *
   * @return base path as string.
   */
  public String getBasePath() {
    return basePath;
  }

  /**
   * Set the base path for KIT DM 2.0.
   *
   * @param basePath base path as string.
   */
  public void setBasePath(String basePath) {
    this.basePath = basePath;
  }

  /**
   * Get debug status.
   * 
   * @return the debug
   */
  public String getDebug() {
    return debug;
  }

  /**
   * Set debug status.
   * 
   * @param debug the debug status to set
   */
  public void setDebug(String debug) {
    this.debug = debug;
  }
}
