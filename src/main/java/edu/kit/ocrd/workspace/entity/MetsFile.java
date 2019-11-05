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
 * This class contains information about one file inside a METS document referenced
 * in the fileGrp section.
 * These are: <p><ul>
 *    <li>Resource Identifier
 *    <li>Version Number
 *    <li>ID of file
 *    <li>PAGEID of file
 *    <li>USE of file
 *    <li>MIMETYPE of file
 *    <li>URL of file
 *    </ul><p>
 */
@Document("metsFile")
@HashIndex(fields = {"resourceId", "version", "fileId"}, unique = true)
public class MetsFile implements IBaseEntity, IMimetype, IPageId, IUrl, IUse {
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
   * Is the current version.
   */
  private Boolean current;
  /**
   * Version of the document. (Start with version 1 increment version number.)
   */
  private Integer version;

  /** File ID is only unique inside of a METS file. */
  private String fileId;
  /**
   * Mimetype of file.
   */
  private String mimetype;
  /**
   * PAGEID of the file.
   */
  private String pageId;
  /**
   * USE of the fileGrp.
   */
  private String use;
  /**
   * URL of the file.
   */
  private String url;

  /**
   * Default constructor for MetsFile.
   */
  public MetsFile() {
    super();
    version = 1;
    current = Boolean.TRUE;
  }

  /**
   * Constructor for MetsFile
   * 
   * @param resourceId ResourceID of the METS document.
   * @param version Version of the METS document.
   * @param fileId ID of the file.
   * @param mimetype Mimetype of the file.
   * @param pageId PAGEID of the file.
   * @param use USE of the fileGrp.
   * @param url URL of the file (maybe relative to METS document)
   */
  public MetsFile(final String resourceId, final Integer version,final String fileId,  
          final String mimetype, final String pageId, final String use, 
          final String url) {
    super();
    this.resourceId = resourceId;
    this.version = version;
    this.fileId = fileId;
    this.mimetype = mimetype;
    this.pageId = pageId;
    this.use = use;
    this.url = url;
    current = Boolean.TRUE;
  }

  @Override
  public String getId() {
    return id;
  }

  @Override
  public void setId(String id) {
    this.id = id;
  }

  @Override
  public String getResourceId() {
    return resourceId;
  }

  @Override
  public void setResourceId(String resourceId) {
    this.resourceId = resourceId;
  }

  /**
   * Get flag for current version.
   *
   * @return the current version.
   */
  public Boolean getCurrent() {
    return current;
  }

  /**
   * Set flag for current version.
   *
   * @param current the current to set
   */
  public void setCurrent(Boolean current) {
    this.current = current;
  }

  @Override
  public Integer getVersion() {
    return version;
  }

  @Override
  public void setVersion(Integer version) {
    this.version = version;
  }

  /**
   * Get the file ID.
   * 
   * @return the fileId
   */
  public String getFileId() {
    return fileId;
  }

  /**
   * Set the file ID.
   * 
   * @param fileId the fileId to set
   */
  public void setFileId(String fileId) {
    this.fileId = fileId;
  }

  /**
   * Get the mimetype of file.
   * 
   * @return the mimetype
   */
  public String getMimetype() {
    return mimetype;
  }

  /**
   * Set mimetype of file.
   * 
   * @param mimetype the mimetype to set
   */
  public void setMimetype(String mimetype) {
    this.mimetype = mimetype;
  }

  /**
   * Get PAGEID of file.
   * 
   * @return the pageId
   */
  public String getPageId() {
    return pageId;
  }

  /**
   * Set PAGEID of file.
   * 
   * @param pageId the pageId to set
   */
  public void setPageId(String pageId) {
    this.pageId = pageId;
  }

  /**
   * Get USE of file.
   * 
   * @return the use
   */
  public String getUse() {
    return use;
  }

  /**
   * Set USE of file.
   * 
   * @param use the use to set
   */
  public void setUse(String use) {
    this.use = use;
  }

  /**
   * Get URL of file.
   * 
   * @return the url
   */
  public String getUrl() {
    return url;
  }

  /**
   * Set URL of file.
   * 
   * @param url the url to set
   */
  public void setUrl(String url) {
    this.url = url;
  }
  /** 
   * Update file of METS document.
   * Creates a new version of the mets file and increment version number.
   * 
   * @param url URL of the new mets file.
   * 
   * @return Updated mets file.
   */
  public MetsFile updateMetsFile(String url) {
    MetsFile newMetsFile = new MetsFile(resourceId, version + 1, fileId, mimetype, pageId, use, url);
    current = Boolean.FALSE;
    return newMetsFile;
  }
  
  @Override
  public String toString() {
     return "MetsFile [id=" + id + ", resourceId=" + resourceId + ", version=" + version + ", fileId=" + fileId + ", pageId=" + pageId + ", mimetype=" + mimetype + ", use=" + use + ", url=" + url + "]";
  }
}
