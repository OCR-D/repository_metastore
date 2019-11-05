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

import org.springframework.data.annotation.Id;

import com.arangodb.springframework.annotation.Document;
import com.arangodb.springframework.annotation.HashIndex;

/**
 * This class contains information about languages used inside a METS document.
 * These are: <p><ul>
 *    <li>Resource Identifier</li>
 *    <li>Language</li>
 *    </ul></p>
 */
@Document("languageMetadata")
@HashIndex(fields = {"resourceId"})
@HashIndex(fields = {"language"})
public class LanguageMetadata {
  /** 
   * ID of the document.
   */
  @Id
  private String id;
  /**
   * Resource Identifier for Document.
   */
  private String resourceId;
  /**
   * language of the document.
   */
  private String language;

  /**
   * Default constructor for LanguageMetadata.
   */
  public LanguageMetadata() {
    super();
  }

  /**
   * Constructor for LanguageMetadata
   * 
   * @param resourceId ResourceID of the METS document.
   * @param language Language of the document.
   */
  public LanguageMetadata(final String resourceId,final String language) {
    super();
    this.resourceId = resourceId;
    this.language = language;
  }

  /**
   * Get ID.
   * @return ID
   */
  public String getId() {
    return id;
  }
  /**
   * Set ID
   * @param id ID
   */
  public void setId(String id) {
    this.id = id;
  }
  /**
   * Get resourceID of document.
   * @return resourceID of document.
   */
  public String getResourceId() {
    return resourceId;
  }
  /**
   * Set resourceID of document.
   * @param resourceId ResourceID of document
   */
  public void setResourceId(String resourceId) {
    this.resourceId = resourceId;
  }

  /**
   * Get language of document.
   * 
   * @return the language
   */
  public String getLanguage() {
    return language;
  }

  /**
   * Set language of document.
   * 
   * @param language the language to set
   */
  public void setLanguage(String language) {
    this.language = language;
  }

  
  @Override
  public String toString() {
     return "LanguageMetadata [id=" + id + ", resourceId=" + resourceId + ", language=" + language + "]";
  }
}
