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
 * See the License for the specific classification governing permissions and
 * limitations under the License.
 */
package edu.kit.ocrd.workspace.entity;

import org.springframework.data.annotation.Id;

import com.arangodb.springframework.annotation.Document;
import com.arangodb.springframework.annotation.HashIndex;

/**
 * This class contains information about classification assigned to a METS document.
 * These are: <p><ul>
 *    <li>Resource Identifier</li>
 *    <li>Classification</li>
 *    </ul></p>
 */
@Document("classificationMetadata")
@HashIndex(fields = {"resourceId"})
@HashIndex(fields = {"classification"})
public class ClassificationMetadata {
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
   * classification of the document.
   */
  private String classification;

  /**
   * Default constructor for ClassificationMetadata.
   */
  public ClassificationMetadata() {
    super();
  }

  /**
   * Constructor for ClassificationMetadata.
   * 
   * @param resourceId ResourceID of the METS document.
   * @param classification Classification of the document.
   */
  public ClassificationMetadata(final String resourceId,final String classification) {
    super();
    this.resourceId = resourceId;
    this.classification = classification;
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
   * Get classification of document.
   * 
   * @return the classification
   */
  public String getClassification() {
    return classification;
  }

  /**
   * Set classification of document.
   * 
   * @param classification the classification to set
   */
  public void setClassification(String classification) {
    this.classification = classification;
  }

  
  @Override
  public String toString() {
     return "ClassificationMetadata [id=" + id + ", resourceId=" + resourceId + ", classification=" + classification + "]";
  }
}
