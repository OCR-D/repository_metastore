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
import java.util.Date;

/**
 * @author Volker Hartmann
 *
 */
@Document("metsDocument")
@HashIndex(fields = { "resourceId"}, unique = true)
public class MetsDocument {

	@Id
	private String id;

	private String resourceId;
	private String metsDocument;
	private Integer version;
  private Date lastModified;
//	@Relations(edges = ChildOf.class, lazy = true)
//	private Collection<MetsDocument> childs;

	public MetsDocument() {
		super();
	}

	public MetsDocument(final String resourceId, final String metsDocument) {
		super();
		this.resourceId = resourceId;
		this.metsDocument = metsDocument;
		this.version = 1;
    this.lastModified = new Date();
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

}
