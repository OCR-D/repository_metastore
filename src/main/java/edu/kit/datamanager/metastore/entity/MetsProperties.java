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
import org.fzk.tools.xml.JaxenUtil;
import org.jdom.Namespace;
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
   * ID of the document.
   */
  @Id
  private String id;
  /**
   * Resource Identifier for Document.
   */
  private String resourceId;

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
    Namespace namespace = Namespace.getNamespace("mods", "http://www.loc.gov/mods/v3");
    Namespace[] namespaces = {namespace};
    title = JaxenUtil.getNodeValue(metsDocument, "//mods:title[1]", namespaces);
    ppn = JaxenUtil.getNodeValue(metsDocument, "//mods:recordIdentifier[1]", namespaces);
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
