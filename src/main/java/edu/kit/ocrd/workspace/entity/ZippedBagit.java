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

import com.arangodb.springframework.annotation.Document;
import com.arangodb.springframework.annotation.HashIndex;
import java.util.Date;
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
@HashIndex(fields = {"ocrdIdentifier","version"}, unique = true)
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
   * OCR-D identifier of bagit container.
   */
  private String ocrdIdentifier;
  /**
   * URL of zipped Bagit container.
   */
  private String url;
  /**
   * Date of upload.
   */
  private Date uploadDate;
  /**
   * Version number starting with 1.
   */
  private Integer version;
  /**
   * Latest version.
   */
  private Boolean latest;

  /**
   * Constructor setting all attributes.
   *
   * @param resourceId Resource Identifier for document.
   * @param ocrdIdentifier OCRD Identifier of bagit container.
   * @param repoId Id inside KITDM repo for document.
   * @param url URL of zipped Bagit container.
   */
  public ZippedBagit(final String resourceId, final String ocrdIdentifier, final String url) {
    super();
    this.resourceId = resourceId;
    this.ocrdIdentifier = ocrdIdentifier;
    this.url = url;
    this.version = 1;
    this.uploadDate = new Date();
    this.latest = Boolean.TRUE;
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
   * Get url of bagIt container.
   *
   * @return the url
   */
  public String getUrl() {
    return url;
  }

  /**
   * Set url of bagIt container.
   *
   * @param url the url to set
   */
  public void setUrl(String url) {
    this.url = url;
  }

    /**
     * Get OCRD identifier of bag.
     * 
     * @return the ocrdIdentifier
     */
    public String getOcrdIdentifier() {
        return ocrdIdentifier;
    }

    /**
     * Set OCRD identifier of bag.
     * 
     * @param ocrdIdentifier the ocrdIdentifier to set
     */
    public void setOcrdIdentifier(String ocrdIdentifier) {
        this.ocrdIdentifier = ocrdIdentifier;
    }

    /**
     * Get upload date.
     * 
     * @return the uploadDate
     */
    public Date getUploadDate() {
        return uploadDate;
    }

    /**
     * Set upload date.
     * 
     * @param uploadDate the uploadDate to set
     */
    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    /**
     * Get version number of the bag.
     * 
     * @return the version
     */
    public Integer getVersion() {
        return version;
    }

    /**
     * Set version number of the bag.
     * 
     * @param version the version to set
     */
    public void setVersion(Integer version) {
        this.version = version;
    }

    /**
     * Is latest version.
     * 
     * @return the latest
     */
    public Boolean getLatest() {
        return latest;
    }

    /**
     * Set latest version.
     * 
     * @param latest the latest to set
     */
    public void setLatest(Boolean latest) {
        this.latest = latest;
    }
  /** 
   * Update file of METS document.
   * Creates a new version of the mets file and increment version number.
   * 
   * @param resourceId resourceId of the new version.
   * @param url URL of the new version.
   * 
   * @return Updated ZippedBagit.
   */
  public ZippedBagit updateZippedBagit(String resourceId, String url) {
    ZippedBagit newZippedBagit = new ZippedBagit(resourceId, ocrdIdentifier, url);
    newZippedBagit.version = version + 1;
    newZippedBagit.latest = true;
    latest = false;
    newZippedBagit.uploadDate = new Date();

    return newZippedBagit;
  }
}
