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

import java.util.List;

/**
 * Contains all metadata of a single page.
 */
public class PageFeatures {
   /**
    * Order of the page.
    */
   private String order;
   
  /** 
   * DMDID of the page.
   */ 
  private String dmdId;
   
  /** 
   * Number of images/graphics.
   */ 
  private int noOfImages;
  /** 
   * Features of the GroundTruth metadata format.
   */
  private List<String> features;

  /**
   * Get Order of the page.
   * @return the order
   */
  public String getOrder() {
    return order;
  }

  /**
   * Set Order of the page.
   * @param order the order to set
   */
  public void setOrder(String order) {
    this.order = order;
  }

  /**
   * Get features of the page.
   * @return the features
   */
  public List<String> getFeatures() {
    return features;
  }

  /**
   * Set features of the page.
   * @param features the features to set
   */
  public void setFeatures(List<String> features) {
    this.features = features;
  }

  /**
   * Get number of images.
   * @return the noOfImages
   */
  public int getNoOfImages() {
    return noOfImages;
  }

  /**
   * Set number of images.
   * @param noOfImages the noOfImages to set
   */
  public void setNoOfImages(int noOfImages) {
    this.noOfImages = noOfImages;
  }

  /**
   * Get DMDID of page.
   * @return the dmdId
   */
  public String getDmdId() {
    return dmdId;
  }

  /**
   * Set DMDID of page.
   * @param dmdId the dmdId to set
   */
  public void setDmdId(String dmdId) {
    this.dmdId = dmdId;
  }
  
  
}
