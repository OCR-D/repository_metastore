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
   * Folder basePath for authentication
   */
  private String basePathAuth = "http://localhost:8070";
  /**
   * Folder basePath for authentication
   */
  private String username = "ocrd";
  /**
   * Folder basePath for authentication
   */
  private String password = "setPasswordViaConfig";
  /**
   * Show debug messages.
   */
  private String debug = "false";
  /** 
   * Use authentication or not.
   */
  private String authentication = "false";

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
   * Get the base path for Authentication.
   *
   * @return base path as string.
   */
  public String getBasePathAuth() {
    return basePathAuth;
  }

  /**
   * Set the base path for Authentication.
   *
   * @param basePath base path as string.
   */
  public void setBasePathAuth(String basePathAuth) {
    this.basePathAuth = basePathAuth;
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

  /**
   * Get authentication flag.
   * 
   * @return the authentication
   */
  public String getAuthentication() {
    return authentication;
  }

  /**
   * Set authentication (true/false).
   * 
   * @param authentication the authentication to set
   */
  public void setAuthentication(String authentication) {
    this.authentication = authentication;
  }

  /**
   * Get name of the user for authentication.
   * 
   * @return the username
   */
  public String getUsername() {
    return username;
  }

  /**
   * Set name of the user for authentication.
   * 
   * @param username the username to set
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * Get password of the user for authentication.
   * 
   * @return the password
   */
  public String getPassword() {
    return password;
  }

  /**
   * Set password of the user for authentication.
   * @param password the password to set
   */
  public void setPassword(String password) {
    this.password = password;
  }
}
