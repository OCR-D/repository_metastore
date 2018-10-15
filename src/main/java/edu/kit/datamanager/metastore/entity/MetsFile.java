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

import org.springframework.data.annotation.Id;

import com.arangodb.springframework.annotation.Document;
import com.arangodb.springframework.annotation.HashIndex;
import java.util.Date;

/**
 * @author Volker Hartmann
 *
 */
@Document("metsFile")
@HashIndex(fields = {"resourceId", "fileId", "version"}, unique = true)
public class MetsFile implements IMimetype, IGroupId, IUse, IUrl {
  /** ID of MetsFile used for indexing. */
  @Id
  private String id;

  /** Id is only unique inside of a METS file. */
  private String fileId;
  /**
   * Prefix of the registered namespace.
   */
  private String resourceId;
  /**
   * Version number of the document.
   */
  private Integer version;
  /**
   * Mimetype of file.
   */
  private String mimetype;
  /**
   * GROUPID of the file.
   */
  private String groupId;
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
  }

  /**
   * Constructor for MetsFile
   * 
   * @param id ID of the file.
   * @param resourceId ResourceID of the METS document.
   * @param version Version of the METS document.
   * @param mimetype Mimetype of the file.
   * @param groupId GROUPID of the file.
   * @param use USE of the fileGrp.
   * @param url URL of the file (maybe relative to METS document)
   */
  public MetsFile(final String id, final String resourceId, final Integer version, 
          final String mimetype, final String groupId, final String use, 
          final String url) {
    super();
    this.resourceId = resourceId;
    this.version = version;
    this.fileId = id;
    this.mimetype = mimetype;
    this.groupId = groupId;
    this.use = use;
    this.url = url;
  }

  /**
   * @return the fileId
   */
  public String getFileId() {
    return fileId;
  }

  /**
   * @param fileId the fileId to set
   */
  public void setFileId(String fileId) {
    this.fileId = fileId;
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
   * @return the version of the METS file.
   */
  public Integer getVersion() {
    return version;
  }

  /**
   * @param resourceId the version to set
   */
  public void setVersion(Integer version) {
    this.version = version;
  }

  /**
   * @return the mimetype
   */
  public String getMimetype() {
    return mimetype;
  }

  /**
   * @param mimetype the mimetype to set
   */
  public void setMimetype(String mimetype) {
    this.mimetype = mimetype;
  }

  /**
   * @return the groupId
   */
  public String getGroupId() {
    return groupId;
  }

  /**
   * @param groupId the groupId to set
   */
  public void setGroupId(String groupId) {
    this.groupId = groupId;
  }

  /**
   * @return the use
   */
  public String getUse() {
    return use;
  }

  /**
   * @param use the use to set
   */
  public void setUse(String use) {
    this.use = use;
  }

  /**
   * @return the url
   */
  public String getUrl() {
    return url;
  }

  /**
   * @param url the url to set
   */
  public void setUrl(String url) {
    this.url = url;
  }

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
  @Override
  public String toString() {
     return "MetsFile [id=" + id + ", fileId=" + fileId + ", groupId=" + groupId + ", mimetype=" + mimetype + ", resourceId=" + resourceId + ", version=" + version + ", use=" + use + ", url=" + url + "]";
  }
}
