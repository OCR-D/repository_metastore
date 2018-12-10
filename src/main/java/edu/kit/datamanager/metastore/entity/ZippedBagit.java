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
package edu.kit.datamanager.metastore.entity;

import com.arangodb.springframework.annotation.Document;
import com.arangodb.springframework.annotation.HashIndex;
import org.springframework.data.annotation.Id;

/**
 * This class contains information about the zipped Bagit containers. This is:
 * <p>
 * <ul>
 * <li>url
 * </ul><p>
 */
@Document("zippedBagitContainer")
@HashIndex(fields = {"resourceId"}, unique = true)
public class ZippedBagit {

  /**
   * ID of XSD document.
   */
  @Id
  private String id;
  /**
   * Resource Identifier for Document.
   */
  private String resourceId;
  /**
   * URL of zipped Bagit container.
   */
  private String url;

  /**
   * Constructor setting all attributes.
   *
   * @param resourceId Resource Identifier for Document.
   * @param repoId Id inside KITDM repo for Document.
   * @param url URL of zipped Bagit container.
   */
  public ZippedBagit(final String resourceId, final String url) {
    super();
    this.resourceId = resourceId;
    this.url = url;
  }

  /**
   * Get database ID.
   *
   * @return the id
   */
  public String getId() {
    return id;
  }

  /**
   * Set database ID. (Shouldn't be used.)
   *
   * @param id the id to set
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * Get resourceId of the METS document.
   *
   * @return Resource ID of the METS document.
   */
  public String getResourceId() {
    return resourceId;
  }

  /**
   * Set resourceId of the METS document.
   *
   * @param resourceId Resource ID of the METS document.
   */
  public void setResourceId(String resourceId) {
    this.resourceId = resourceId;
  }

  /**
   * Get content of XSD file.
   *
   * @return the url
   */
  public String getUrl() {
    return url;
  }

  /**
   * Set content of XSD file.
   *
   * @param url the url to set
   */
  public void setUrl(String url) {
    this.url = url;
  }
}
