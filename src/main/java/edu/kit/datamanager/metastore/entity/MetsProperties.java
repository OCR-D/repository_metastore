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
import edu.kit.datamanager.metastore.util.MetsDocumentUtil;
import org.fzk.tools.xml.JaxenUtil;
import org.jdom.Namespace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.Id;

/**
 * This class contains all important information a METS document. These are:
 * <p>
 * <ul>
 * <li>Title
 * <li>PPN
 * <li>TODO
 * </ul><p>
 */
@Document("metsProperties")
@HashIndex(fields = {"resourceId", "version"}, unique = true)
public class MetsProperties {

  /**
   * Logger.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(MetsProperties.class);
  /** 
   * Placeholder if no title is available.
   */
  public static final String NO_TITLE = "No title available!";
  /**
   * X-Path expression for title of document.
   */
  private static final String X_PATH_TITLE = "//mods:mods/mods:titleInfo/mods:title[1]";
  /** 
   * X-Path expression for identifier.
   */
  private static final String X_PATH_PPN = "//mods:mods/mods:recordInfo/mods:recordIdentifier[1]";

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
   * Title of resource.
   */
  private String title;
  /**
   * PPN of resource.
   */
  private String ppn;

  /**
   * Default constructor for MetsFile.
   */
  public MetsProperties() {
    super();
  }

  /**
   * Constructor for MetsFile
   *
   * @param metsDocument Document holding all information about Digital Object.
   */
  public MetsProperties(org.jdom.Document metsDocument) {
    super();
    extractProperties(metsDocument);
  }
  /** 
   * Extract properties from METS document via XPath. 
   * 
   * @param metsDocument METS document;
   */
  private void extractProperties(org.jdom.Document metsDocument) {
    LOGGER.info("Extract properties from METS document.");
    Namespace[] namespaces = MetsDocumentUtil.getNamespaces();
    try {
    title = JaxenUtil.getNodeValue(metsDocument, "//mods:title[1]", namespaces);
    } catch (Exception ex) {
      LOGGER.trace(ex.getMessage());
      title = null;
    }
    if (title == null) {
      title = NO_TITLE;
    }
    try {
    ppn = JaxenUtil.getNodeValue(metsDocument, "//mods:recordIdentifier[1]", namespaces);
    } catch (Exception ex) {
      LOGGER.trace(ex.getMessage());
      ppn = null;
    }
    if (ppn == null) {
      ppn = "No PPN available";
    }
   LOGGER.info("Properties:\nTitle: {}\nPPN: {}", title, ppn);
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

  /**
   * Get title of resource.
   *
   * @return the title
   */
  public String getTitle() {
    return title;
  }

  /**
   * Set title of resource.
   *
   * @param title the title to set
   */
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * Get the ppn of resource.
   *
   * @return the ppn
   */
  public String getPpn() {
    return ppn;
  }

  /**
   * Set ppn of resource.
   *
   * @param ppn the ppn to set
   */
  public void setPpn(String ppn) {
    this.ppn = ppn;
  }

  @Override
  public String toString() {
    return "MetsProperties [id=" + id + ", resourceId=" + resourceId + ", title=" + title + ", ppn=" + ppn + "]";
  }
}
