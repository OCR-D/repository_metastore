/*
 * DISCLAIMER
 *
 * Copyright 2017 ArangoDB GmbH, Cologne, Germany
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Copyright holder is ArangoDB GmbH, Cologne, Germany
 */
package edu.kit.datamanager.metastore.entity;

import java.util.Collection;

import org.springframework.data.annotation.Id;

import com.arangodb.springframework.annotation.Document;
import com.arangodb.springframework.annotation.HashIndex;
import com.arangodb.springframework.annotation.Relations;
import java.util.ArrayList;
import java.util.Date;

/**
 * This class contains all information about a digital object.
 * These are: <p><ul>
 *    <li>descriptive metadata
 *       <ul><li>dublin core metadata
 *       <li>content metadata
 *       <li>files</ul>
 *    <li>administrative metadata
 *    </ul><p>
 * In case of OCR-D data there may be an optional attribute holding
 * all metadata about the Ground-Truth. 
 * 
 */
@Document("metsDocument")
@HashIndex(fields = {"resourceId", "version"}, unique = true)
public class MetsDocument {

  /** 
   * ID of the document.
   */
  @Id
  private String id;
  /** 
   * Resource ID of the document extracted from mets document. 
   */
  private String resourceId;
  /**
   * Original Mets document. 
   * Does not contain updates in sections.
   */
  private String metsDocument;
  /**
   * Version number of the document.
   */
  private Integer version;
  /** 
   * Last modification in any section.
   */
  private Date lastModified;
  /**
   * Collection with all sections of the administrative and descriptive metadata.
   */
  @Relations(edges = SectionDocumentOf.class, lazy = true)
  private Collection<SectionDocument> sectionDocuments;
  /** 
   * Collection holding all referenced files inside the file section.
   */
  @Relations(edges = MetsFileOf.class, lazy = true)
  private Collection<MetsFile> metsFiles;

  /**
   * Constructor.
   */
  public MetsDocument() {
    super();
  }
  /**
   * Constructor with resourceId and the original METS document.
   * 
   * @param resourceId ResourceId of the digital object referenced by the METS document. (has to be unique)
   * @param metsDocument METS document.
   */
  public MetsDocument(final String resourceId, final String metsDocument) {
    this(resourceId, metsDocument, 1);
  }

  /**
   * Constructor with resourceId and the original METS document.
   * 
   * @param resourceId ResourceId of the digital object referenced by the METS document. (has to be unique)
   * @param metsDocument METS document.
   * @param version Version of the document. Initial version is version 1.
   */
  public MetsDocument(final String resourceId, final String metsDocument, final Integer version) {
    super();
    this.resourceId = resourceId;
    this.metsDocument = metsDocument;
    this.version = version;
    this.lastModified = new Date();
    metsFiles = new ArrayList<>();
  }

  /**
   * Get internal ID of the database.
   * 
   * @return the id
   */
  public String getId() {
    return id;
  }

  /**
   *  Set internal ID of the database.
   *  Don't use this method.
   * 
   * @param id the id to set
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * Get resource ID of the digital object. 
   * (Resource ID has to be unique at least inside a repository)
   * 
   * @return the resourceId
   */
  public String getResourceId() {
    return resourceId;
  }

  /**
   * Set resource ID of the digital object.
   * Don't use this method. Resource ID should be set during instantiation.
   * 
   * @param resourceId the resourceId to set
   */
  public void setResourceId(String resourceId) {
    this.resourceId = resourceId;
  }

  /**
   * Get the original METS document.
   * The most current version has to be build dynamically.
   * 
   * @return the metsDocument
   */
  public String getMetsDocument() {
    return metsDocument;
  }

  /**
   * Set the original METS document.
   * Don't use this method. METS document should be set during instantiation.
   * 
   * @param metsDocument the metsDocument to set
   */
  public void setMetsDocument(String metsDocument) {
    this.metsDocument = metsDocument;
  }

  /**
   * Get version of the METS document.
   * Version number has to be inceremented during each update of any section document.
   * 
   * @return the version
   */
  public Integer getVersion() {
    return version;
  }

  /**
   * Set version of the METS document.
   * Don't use this method. The version should be handled by the class itself.
   * 
   * @param version the version to set
   */
  public void setVersion(Integer version) {
    this.version = version;
  }

  /**
   * Get the date of the last modification.
   * 
   * @return the lastModified
   */
  public Date getLastModified() {
    return lastModified;
  }

  /**
   * Set the date of the last modification.
   * Don't use this method. The last modification should be handled by the class itself.
   * 
   * @param lastModified the lastModified to set
   */
  public void setLastModified(Date lastModified) {
    this.lastModified = lastModified;
  }

  /**
   * Get all section documents.
   * 
   * @return the sectionDocuments
   */
  public Collection<SectionDocument> getSectionDocuments() {
    return sectionDocuments;
  }

  /**
   * Set all section Documents.
   * 
   * @param sectionDocuments the sectionDocuments to set
   */
  public void setSectionDocuments(Collection<SectionDocument> sectionDocuments) {
    this.sectionDocuments = sectionDocuments;
  }

  /**
   * Get all mets files defined in the file section.
   * 
   * @return the metsFiles
   */
  public Collection<MetsFile> getMetsFiles() {
    return metsFiles;
  }

  /**
   * Set all mets files defined in the file section.
   * 
   * @param metsFiles the metsFiles to set
   */
  public void setMetsFiles(Collection<MetsFile> metsFiles) {
    this.metsFiles = metsFiles;
  }

  @Override
  public String toString() {
    StringBuffer sb = new StringBuffer("MetsDocument [");
    sb.append("id=").append(id).append(", ");
    sb.append("lastModified=").append(lastModified).append(", ");
    sb.append("metsDocument=").append(metsDocument).append(", ");
    sb.append("resourceId=").append(resourceId).append(", ");
    sb.append("version=").append(version).append(", \n");
    sb.append("metsFiles: \n");
    for (MetsFile file : metsFiles) {
      sb.append("  ").append(file.toString()).append(", \n");
    }
    if (sectionDocuments != null) {
    sb.append("sectionDocuments: \n");
      for (SectionDocument secDoc : sectionDocuments) {
        sb.append("  ").append(secDoc.toString()).append(", \n");
      }
    }
    sb.append("]");
    return sb.toString();
  }

}
