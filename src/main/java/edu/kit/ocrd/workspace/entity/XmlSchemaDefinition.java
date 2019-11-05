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
import org.springframework.data.annotation.Id;

/**
 * This class contains information about the registered XSD files.
 * These are: <p><ul>
 *    <li>namespace
 *       <li>prefix of the namespace
 *       <li>content of XSD file
 *    </ul><p>
 */
@Document("xmlSchemaDefinition")
@HashIndex(fields = {"prefix"}, unique = true)
@HashIndex(fields = {"namespace"}, unique = true)
public class XmlSchemaDefinition {

  /** 
   * ID of XSD document.
   */
  @Id
  private String id;
  /**
   * Prefix of namespace. 
   */	
	private String prefix;
  /** 
   * (Target) Namespace of XSD document.
   */
	private String namespace;
  /** 
   * Content of XSD document.
   */
	private String xsdFile;

  /** 
   * Constructor setting all attributes. 
   * 
   * @param prefix Prefix of namespace.
   * @param namespace (Target) Namespace of XSD document.
   * @param xsdFile Content of XSD document.
   */
  public XmlSchemaDefinition(final String prefix, final String namespace, final String xsdFile) {
    super();
    this.prefix = prefix;
    this.namespace = namespace;
    this.xsdFile = xsdFile;
  }
  /**
   * Get database ID.
   * @return the id
   */
  public String getId() {
    return id;
  }

  /**
   * Set database ID.
   * (Shouldn't be used.)
   * 
   * @param id the id to set
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * Get prefix of namespace.
   * 
   * @return the prefix
   */
  public String getPrefix() {
    return prefix;
  }

  /**
   * Set prefix of namespace.
   * 
   * @param prefix the prefix to set
   */
  public void setPrefix(String prefix) {
    this.prefix = prefix;
  }

  /**
   * Get namespace. 
   * 
   * @return the namespace
   */
  public String getNamespace() {
    return namespace;
  }

  /**
   * Set namespace.
   * 
   * @param namespace the namespace to set
   */
  public void setNamespace(String namespace) {
    this.namespace = namespace;
  }

  /**
   * Get content of XSD file.
   * 
   * @return the xsdFile
   */
  public String getXsdFile() {
    return xsdFile;
  }

  /**
   * Set content of XSD file.
   * 
   * @param xsdFile the xsdFile to set
   */
  public void setXsdFile(String xsdFile) {
    this.xsdFile = xsdFile;
  }
}
