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
import java.util.Date;

/**
 * This class contains information about the current version of a digital
 * object. These are:
 * <p>
 * <ul>
 * <li>Resource Identifier
 * <li>Version Number
 * <li>Date of last modification
 * </ul><p>
 *
 * Mets file contains several section documents. These are:
 * <p>
 * <ul>
 * <li>descriptive metadata
 * <ul><li>dublin core metadata
 * <li>content metadata
 * <li>files</ul>
 * <li>administrative metadata
 * </ul><p>
 * In case of OCR-D data there may be an optional section document holding all
 * metadata about the Ground-Truth.
 *
 */
@Document("metsDocument")
@HashIndex(fields = {"resourceId", "version"}, unique = true)
public class MetsDocument implements IBaseEntity {

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
   * Id inside KITDM repo for Document.
   */
  private String repoId;
  /**
   * Version of the document. (Start with version 1 increment version number.)
   */
  private Integer version;
  /**
   * Date of the last change.
   */
  private Date lastModified;
  /**
   * Is the current version.
   */
  private Boolean current;
  /**
   * Holds the content of the METS file.
   */
  private String metsContent;

  /**
   * Constructor.
   */
  public MetsDocument() {
    super();
  }

  /**
   * Constructor with resourceId and the original METS document.
   *
   * @param resourceId ResourceId of the digital object referenced by the METS
   * document. (has to be unique)
   * @param metsDocument METS document.
   */
  public MetsDocument(final String resourceId, final String metsDocument) {
    this(resourceId, 1, metsDocument);
  }

  /**
   * Constructor with resourceId, version number and the original METS document.
   *
   * @param resourceId ResourceId of the digital object referenced by the METS
   * document. (has to be unique)
   * @param version Version of the document. Initial version is version 1.
   * @param metsDocument Content of METS file.
   */
  public MetsDocument(final String resourceId, final Integer version, final String metsDocument) {
    super();
    this.resourceId = resourceId;
    this.version = version;
    this.lastModified = new Date();
    this.metsContent = metsDocument;
    this.current = true;
  }

  @Override
  public String getId() {
    return id;
  }

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
   * Get the id of the KIT DM repository.
   * 
   * @return the repoId
   */
  public String getRepoId() {
    return repoId;
  }

  /**
   * Set the id of the KIT DM repository.
   * 
   * @param repoId the repoId to set
   */
  public void setRepoId(String repoId) {
    this.repoId = repoId;
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
   * Get date of last modification.
   *
   * @return Last modification date.
   */
  public Date getLastModified() {
    return lastModified;
  }

  /**
   * Set date of last modification.
   *
   * @param lastModified Last modification date.
   */
  public void setLastModified(Date lastModified) {
    this.lastModified = lastModified;
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

  /**
   * Get content of METS file.
   *
   * @return the metsContent
   */
  public String getMetsContent() {
    return metsContent;
  }

  /**
   * Set content of METS file.
   *
   * @param metsContent the metsContent to set
   */
  public void setMetsContent(String metsContent) {
    this.metsContent = metsContent;
  }

  /**
   * Update Metsdocument. Creates a new version of the METS document and
   * increment version number. Set old version to be no longer current.
   *
   * @param metsContent Content of the new METS file.
   *
   * @return Updated METS document.
   */
  public MetsDocument updateMetsContent(String metsContent) {
    MetsDocument newMetsDoc = new MetsDocument(resourceId, version + 1, metsContent);
    this.current = false;
    return newMetsDoc;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("MetsDocument [");
    sb.append("id=").append(id).append(", ");
    sb.append("resourceId=").append(resourceId).append(", ");
    sb.append("repoId=").append(repoId).append(", ");
    sb.append("version=").append(version).append(", ");
    sb.append("lastModified=").append(lastModified).append(", ");
    sb.append("current=").append(current).append(", \n");
    sb.append("metsDocument=").append(metsContent);
    sb.append("]");
    return sb.toString();
  }
}
