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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.Id;

/**
 * This class contains all important information a METS document. These are:
 * <p>
 * <ul>
 * <li>Title</li>
 * <li>Subtitle</li>
 * <li>PPN</li>
 * <li>Year</li>
 * <li>License</li>
 * <li>Author</li>
 * <li>Publisher</li>
 * <li>No of pages</li>
 * <li>Physical description</li>
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
   * Placeholder if no PPN is available.
   */
  public static final String NO_PPN = "No PPN available!";
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
   * Sub title of the document.
   */
  private String subTitle;

  /**
   * Year of publication.
   */
  private String year;

  /**
   * License of document.
   */
  private String license;
  
  /** 
   * Author of the document.
   */
  private String author;
  
  /**
   * Number of pages (images).
   */
  private int noOfPages;
  
  /**
   * Publisher.
   */
  private String publisher;
  
  /**
   * Physical description.
   */
  private String physicalDescription;
  
 /**
   * PPN of resource.
   */
  private String ppn;

  /**
   * Default constructor for MetsFile.
   */
  public MetsProperties() {
    super();
      title = NO_TITLE;
      ppn = NO_PPN;
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
   * Get subtitle of document.
   *
   * @return the subTitle
   */
  public String getSubTitle() {
    return subTitle;
  }

  /**
   * Set subtitle of document.
   *
   * @param subTitle the subTitle to set
   */
  public void setSubTitle(String subTitle) {
    this.subTitle = subTitle;
  }

  /**
   * Get year of publication.
   * @return the year
   */
  public String getYear() {
    return year;
  }

  /**
   * Set year of publication.
   * @param year the year to set
   */
  public void setYear(String year) {
    this.year = year;
  }

  /**
   * Get license of the document.
   * @return the license
   */
  public String getLicense() {
    return license;
  }

  /**
   * Set license of the document.
   * @param license the license to set
   */
  public void setLicense(String license) {
    this.license = license;
  }

  /**
   * Get author of document.
   * @return the author
   */
  public String getAuthor() {
    return author;
  }

  /**
   * Set author of document.
   * @param author the author to set
   */
  public void setAuthor(String author) {
    this.author = author;
  }

  /**
   * Get number of pages.
   * @return the noOfPages
   */
  public int getNoOfPages() {
    return noOfPages;
  }

  /**
   * Set number of pages.
   * @param noOfPages the noOfPages to set
   */
  public void setNoOfPages(int noOfPages) {
    this.noOfPages = noOfPages;
  }

  /**
   * Get publisher.
   * @return the publisher
   */
  public String getPublisher() {
    return publisher;
  }

  /**
   * Set publisher.
   * @param publisher the publisher to set
   */
  public void setPublisher(String publisher) {
    this.publisher = publisher;
  }

  /**
   * Get physical description.
   * @return the physicalDescription
   */
  public String getPhysicalDescription() {
    return physicalDescription;
  }

  /**
   * Set physical description.
   * @param physicalDescription the physicalDescription to set
   */
  public void setPhysicalDescription(String physicalDescription) {
    this.physicalDescription = physicalDescription;
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
