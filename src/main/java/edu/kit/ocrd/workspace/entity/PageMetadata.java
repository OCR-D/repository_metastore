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
 * This class contains ground truth metadata about one page inside a METS document.
 * These are: <p><ul>
 *    <li>Resource Identifier</li>
 *    <li>Order</li>
 *    <li>PAGEID of page</li>
 *    <li>FEATURE of page</li>
 *    </ul></p>
 */
@Document("pageMetadata")
@HashIndex(fields = {"resourceId", "pageId"})
@HashIndex(fields = {"feature"})
public class PageMetadata {
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
   * Order of the page.
   */
  private String order;
  /**
   * PAGEID of the page. 
   */
  private String pageId;
  
  /** 
   * Feature of the page.
   */
  private GroundTruthProperties feature;

  /**
   * Default constructor for PageMetadata.
   */
  public PageMetadata() {
    super();
  }

  /**
   * Constructor for PageMetadata
   * 
   * @param resourceId ResourceID of the METS document.
   * @param order Order of the page.
   * @param pageId ID of the page.
   * @param feature One Feature of the page.
   */
  public PageMetadata(final String resourceId,final String order,  
          final String pageId, final GroundTruthProperties feature) {
    super();
    this.resourceId = resourceId;
    this.pageId = pageId;
    this.order = order;
    this.feature = feature;
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
   * Get PAGEID of page.
   * 
   * @return the pageId
   */
  public String getPageId() {
    return pageId;
  }

  /**
   * Set PAGEID of page.
   * 
   * @param pageId the pageId to set
   */
  public void setPageId(String pageId) {
    this.pageId = pageId;
  }

  /**
   * Get oder of page.
   * 
   * @return the order
   */
  public String getOrder() {
    return order;
  }

  /**
   * Set order of page.
   * 
   * @param order the order to set
   */
  public void setOrder(String order) {
    this.order = order;
  }

  /**
   * Get one feature of the page.
   * @return the feature
   */
  public GroundTruthProperties getFeature() {
    return feature;
  }

  /**
   * Set one Feature of the page.
   * @param feature the feature to set
   */
  public void setFeature(GroundTruthProperties feature) {
    this.feature = feature;
  }
  
  @Override
  public String toString() {
     return "PageMetadata [id=" + id + ", resourceId=" + resourceId + ", PAGEID=" + pageId + ", order=" + order + ", feature=" + feature + "]";
  }
}
