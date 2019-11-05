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
 * This class contains information about one identifier of a METS document.
 * These are: <p><ul>
 *    <li>Resource Identifier</li>
 *    <li>Type</li>
 *    <li>Identifier</li>
 *    </ul></p>
 */
@Document("metsIdentifier")
@HashIndex(fields = {"resourceId"})
@HashIndex(fields = {"identifier"})
public class MetsIdentifier {
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
   * Type of identifier (one of: purl, urn, handle, url).
   */
  private String type;
  /**
   * Identifier of given type.
   */
  private String identifier;


  /**
   * Default constructor for MetsFile.
   */
  public MetsIdentifier() {
    super();
  }

  /**
   * Constructor for MetsFile
   * 
   * @param resourceId ResourceID of the METS document.
   * @param type Type of identifier.
   * @param identifier Identifier.
   */
  public MetsIdentifier(final String resourceId, final String type, final String identifier) {
    super();
    this.resourceId = resourceId;
    this.type = type;
    this.identifier = identifier;
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
   * Get type of identifier.
   * @return the type
   */
  public String getType() {
    return type;
  }

  /**
   * Set type of identifier.
   * @param type the type to set
   */
  public void setType(String type) {
    this.type = type;
  }

  /**
   * Get identifier.
   * @return the identifier
   */
  public String getIdentifier() {
    return identifier;
  }

  /**
   * Set identifier.
   * @param identifier the identifier to set
   */
  public void setIdentifier(String identifier) {
    this.identifier = identifier;
  }
  
  @Override
  public String toString() {
     return "MetsIdentifier [id=" + id + ", resourceId=" + resourceId + ", type=" + type + ", identifier=" + identifier + "]";
  }
}
