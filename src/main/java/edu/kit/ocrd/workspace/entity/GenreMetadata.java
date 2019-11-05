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
 * See the License for the specific genre governing permissions and
 * limitations under the License.
 */
package edu.kit.ocrd.workspace.entity;

import org.springframework.data.annotation.Id;

import com.arangodb.springframework.annotation.Document;
import com.arangodb.springframework.annotation.HashIndex;

/**
 * This class contains information about genres assigned to a METS document.
 * These are: <p><ul>
 *    <li>Resource Identifier</li>
 *    <li>Genre</li>
 *    </ul></p>
 */
@Document("genreMetadata")
@HashIndex(fields = {"resourceId"})
@HashIndex(fields = {"genre"})
public class GenreMetadata {
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
   * genre of the document.
   */
  private String genre;

  /**
   * Default constructor for GenreMetadata.
   */
  public GenreMetadata() {
    super();
  }

  /**
   * Constructor for GenreMetadata
   * 
   * @param resourceId ResourceID of the METS document.
   * @param genre Genre of the document.
   */
  public GenreMetadata(final String resourceId,final String genre) {
    super();
    this.resourceId = resourceId;
    this.genre = genre;
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
   * Get genre of document.
   * 
   * @return the genre
   */
  public String getGenre() {
    return genre;
  }

  /**
   * Set genre of document.
   * 
   * @param genre the genre to set
   */
  public void setGenre(String genre) {
    this.genre = genre;
  }

  
  @Override
  public String toString() {
     return "GenreMetadata [id=" + id + ", resourceId=" + resourceId + ", genre=" + genre + "]";
  }
}
