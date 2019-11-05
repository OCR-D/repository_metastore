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
 * This class holds all information about section documents.
 */
@Document("sectionDocument")
@HashIndex(fields = {"resourceId", "prefix"}, unique = true)
public class SectionDocument implements IBaseEntity {

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
   * Version of the document. (Start with version 1 increment version number.)
   */
  private Integer version;
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

  /**
   * Default constructor.
   */
  public SectionDocument() {
    super();
    version = 1;
  }

  /**
   * Constructor setting all attributes.
   *
   * @param resourceId ResourceId of the digital object referenced by the METS
   * document. (has to be unique)
   * @param version Version of the document. Initial version is version 1.
   * @param prefix Prefix of the registered namespace.
   * @param sectionId ID of the section.
   * @param sectionMdType MDType of the section.
   * @param sectionOtherMdType Special metadata type if MDType is 'OTHER'.
   * @param sectionDocument Content of the section document.
   */
  public SectionDocument(final String resourceId, final Integer version, final String prefix, final String sectionId, final MdType sectionMdType,
          final String sectionOtherMdType, final String sectionDocument) {
    super();
    this.resourceId = resourceId;
    this.prefix = prefix;
    this.sectionId = sectionId;
    this.sectionMdType = sectionMdType;
    this.sectionOtherMdType = sectionOtherMdType;
    this.sectionDocument = sectionDocument;
    if (version == null) {
      this.version = 1;
    } else {
      this.version = version;
    }
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

  @Override
  public Integer getVersion() {
    return version;
  }

  @Override
  public void setVersion(Integer version) {
    this.version = version;
  }

  /**
   * Get the prefix of the registered namespace.
   *
   * @return the prefix
   */
  public String getPrefix() {
    return prefix;
  }

  /**
   * Set the prefix of the registered namespace.
   *
   * @param prefix the prefix to set
   */
  public void setPrefix(String prefix) {
    this.prefix = prefix;
  }

  /**
   * Get the section ID of the document.
   *
   * @return the sectionId
   */
  public String getSectionId() {
    return sectionId;
  }

  /**
   * Set the section ID of the document.
   *
   * @param sectionId the sectionId to set
   */
  public void setSectionId(String sectionId) {
    this.sectionId = sectionId;
  }

  /**
   * Get the MDTYPE of the section document.
   *
   * @return the sectionMdType
   */
  public MdType getSectionMdType() {
    return sectionMdType;
  }

  /**
   * Set the MDTYPE of the section document.
   *
   * @param sectionMdType the sectionMdType to set
   */
  public void setSectionMdType(MdType sectionMdType) {
    this.sectionMdType = sectionMdType;
  }

  /**
   * Get special metadata type.
   *
   * @return the sectionOtherMdType
   */
  public String getSectionOtherMdType() {
    return sectionOtherMdType;
  }

  /**
   * Set special metadata type.
   *
   * @param sectionOtherMdType the sectionOtherMdType to set
   */
  public void setSectionOtherMdType(String sectionOtherMdType) {
    this.sectionOtherMdType = sectionOtherMdType;
  }

  /**
   * Get content of section document.
   *
   * @return the sectionDocument
   */
  public String getSectionDocument() {
    return sectionDocument;
  }

  /**
   * Set content of section document.
   *
   * @param sectionDocument the sectionDocument to set
   */
  public void setSectionDocument(String sectionDocument) {
    this.sectionDocument = sectionDocument;
  }

  /**
   * Update section document. Creates a new version of the section document and
   * increment version number.
   *
   * @param sectionDocument Content of the new section document.
   *
   * @return Updated section document.
   */
  public SectionDocument updateSectionDocument(String sectionDocument) {
    SectionDocument newSecDoc = new SectionDocument(resourceId, version + 1, prefix, sectionId, sectionMdType, sectionOtherMdType, sectionDocument);
    return newSecDoc;
  }

  @Override
  public String toString() {
    return "SectionDocument [id=" + id + ", prefix=" + prefix + ", version=" + version + ", resourceId=" + resourceId + ", sectionDocument=" + sectionDocument + ", sectionId=" + sectionId + ", sectionMD=" + sectionMdType + ", sectionOtherMD=" + sectionOtherMdType + "]";

  }
}
