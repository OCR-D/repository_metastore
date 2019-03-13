/*
 * Copyright 2019 Karlsruhe Institute of Technology.
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
package edu.kit.datamanager.metastore.dao;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains all metadata of one document.
 */
public class MetsMetadata {

  /**
   * List of all identifiers.
   */
  private List<ModsIdentifier> modsIdentifier = new ArrayList<>();

  /**
   * Title of the document.
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
   * List of metadata of the pages.
   */
  private List<Page> pages = new ArrayList<>();

  /**
   * Licence of document.
   */
  private String licence;
  
  /**
   * Languages of document.
   */
  private List<String> language; 
  
  /** 
   * Author of the document.
   */
  private String author;
  
  /**
   * Number of pages (images).
   */
  private int noOfPages;
  
  /**
   * Classification.
   * @see #genre
   */
  private List<String> classification;
  
  /**
   * Genre.
    * @see #classification
  */
  private List<String> genre;
  
  /**
   * Publisher.
   */
  private String publisher;
  
  /**
   * Physical description.
   */
  private String physicalDescription;
  
  /**
   * Get title of document.
   *
   * @return the title
   */
  public String getTitle() {
    return title;
  }

  /**
   * Set title of document.
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
   * Get list of identifiers.
   *
   * @return the modsIdentifier
   */
  public List<ModsIdentifier> getModsIdentifier() {
    return modsIdentifier;
  }

  /**
   * Set list of identifiers.
   *
   * @param modsIdentifier the modsIdentifier to set
   */
  public void setModsIdentifier(List<ModsIdentifier> modsIdentifier) {
    this.modsIdentifier = modsIdentifier;
  }

  /**
   * Get metadata of the pages.
   *
   * @return the pages
   */
  public List<Page> getPages() {
    return pages;
  }

  /**
   * Set metadata of the pages.
   *
   * @param pages the pages to set
   */
  public void setPages(List<Page> pages) {
    this.pages = pages;
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
   * Get licence of the document.
   * @return the licence
   */
  public String getLicence() {
    return licence;
  }

  /**
   * Set licence of the document.
   * @param licence the licence to set
   */
  public void setLicence(String licence) {
    this.licence = licence;
  }

  /**
   * Get list of contained languages.
   * @return the language
   */
  public List<String> getLanguage() {
    return language;
  }

  /**
   * Set list of contained languages.
   * @param language the language to set
   */
  public void setLanguage(List<String> language) {
    this.language = language;
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
   * Get a list of classifications. 
   * @return the classification
   */
  public List<String> getClassification() {
    return classification;
  }

  /**
   * Set a list of classifications.
   * @param classification the classification to set
   */
  public void setClassification(List<String> classification) {
    this.classification = classification;
  }

  /**
   * Set list of genres.
   * @return the genre
   */
  public List<String> getGenre() {
    return genre;
  }

  /**
   * Get list of genres.
   * @param genre the genre to set
   */
  public void setGenre(List<String> genre) {
    this.genre = genre;
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

}
