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
@Document("sectionDocument")
@HashIndex(fields = {"resourceId", "prefix"}, unique = true)
public class SectionDocument {

  @Id
  private String id;
  /**
   * Prefix of the registered namespace.
   */
  private String resourceId;
  /**
   * Prefix of the registered namespace.
   */
  private String prefix;
  /**
   * ID of the section. It has to be unique inside METS file.
   */
  private String sectionId;
  /**
   * Metadata type. One of some predefined types. OTHER for unknown types.
   */
  private MdType sectionMdType;
  /**
   * Special metadata type.
   */
  private String sectionOtherMdType;
  /**
   * Content of the section document.
   */
  private String sectionDocument;

  public SectionDocument() {
    super();
  }

  public SectionDocument(final String resourceId, final String prefix, final String sectionId, final MdType sectionMdType,
          final String sectionOtherMdType, final String sectionDocument) {
    super();
    this.resourceId = resourceId;
    this.prefix = prefix;
    this.sectionId = sectionId;
    this.sectionMdType = sectionMdType;
    this.sectionOtherMdType = sectionOtherMdType;
    this.sectionDocument = sectionDocument;
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
   * @return the prefix
   */
  public String getPrefix() {
    return prefix;
  }

  /**
   * @param prefix the prefix to set
   */
  public void setPrefix(String prefix) {
    this.prefix = prefix;
  }

  /**
   * @return the sectionId
   */
  public String getSectionId() {
    return sectionId;
  }

  /**
   * @param sectionId the sectionId to set
   */
  public void setSectionId(String sectionId) {
    this.sectionId = sectionId;
  }

  /**
   * @return the sectionMdType
   */
  public MdType getSectionMdType() {
    return sectionMdType;
  }

  /**
   * @param sectionMdType the sectionMdType to set
   */
  public void setSectionMdType(MdType sectionMdType) {
    this.sectionMdType = sectionMdType;
  }

  /**
   * @return the sectionOtherMdType
   */
  public String getSectionOtherMdType() {
    return sectionOtherMdType;
  }

  /**
   * @param sectionOtherMdType the sectionOtherMdType to set
   */
  public void setSectionOtherMdType(String sectionOtherMdType) {
    this.sectionOtherMdType = sectionOtherMdType;
  }

  /**
   * @return the sectionDocument
   */
  public String getSectionDocument() {
    return sectionDocument;
  }

  /**
   * @param sectionDocument the sectionDocument to set
   */
  public void setSectionDocument(String sectionDocument) {
    this.sectionDocument = sectionDocument;
  }
}
