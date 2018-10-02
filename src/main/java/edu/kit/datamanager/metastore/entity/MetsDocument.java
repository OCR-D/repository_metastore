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
@HashIndex(fields = {"resourceId"}, unique = true)
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

  public MetsDocument(final String resourceId, final String metsDocument) {
    super();
    this.resourceId = resourceId;
    this.metsDocument = metsDocument;
    this.version = 1;
    this.lastModified = new Date();
    metsFiles = new ArrayList<>();
  }

//	public Collection<MetsDocument> getChilds() {
//		return childs;
//	}
//
//	public void setChilds(final Collection<MetsDocument> childs) {
//		this.childs = childs;
//	}
//
//	@Override
//	public String toString() {
//		return "Character [id=" + id + ", name=" + name + ", surname=" + surname + ", alive=" + alive + ", age=" + age
//				+ "]";
//	}
  /**
   * @return the id
   */
  public String getId() {
    return id;
  }

  /**
   * @param id the id to set
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * @return the resourceId
   */
  public String getResourceId() {
    return resourceId;
  }

  /**
   * @param resourceId the resourceId to set
   */
  public void setResourceId(String resourceId) {
    this.resourceId = resourceId;
  }

  /**
   * @return the metsDocument
   */
  public String getMetsDocument() {
    return metsDocument;
  }

  /**
   * @param metsDocument the metsDocument to set
   */
  public void setMetsDocument(String metsDocument) {
    this.metsDocument = metsDocument;
  }

  /**
   * @return the version
   */
  public Integer getVersion() {
    return version;
  }

  /**
   * @param version the version to set
   */
  public void setVersion(Integer version) {
    this.version = version;
  }

  /**
   * @return the lastModified
   */
  public Date getLastModified() {
    return lastModified;
  }

  /**
   * @param lastModified the lastModified to set
   */
  public void setLastModified(Date lastModified) {
    this.lastModified = lastModified;
  }

  /**
   * @return the sectionDocuments
   */
  public Collection<SectionDocument> getSectionDocuments() {
    return sectionDocuments;
  }

  /**
   * @param sectionDocuments the sectionDocuments to set
   */
  public void setSectionDocuments(Collection<SectionDocument> sectionDocuments) {
    this.sectionDocuments = sectionDocuments;
  }

  /**
   * @return the metsFiles
   */
  public Collection<MetsFile> getMetsFiles() {
    return metsFiles;
  }

  /**
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
