/*
 * Copyright 2017 Karlsruhe Institute of Technology.
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
package edu.kit.ocrd.workspace;

import org.fzk.tools.xml.JaxenUtil;
import org.jdom.Document;
import org.jdom.Namespace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class for determining namespace and namespace/version
 */
public class XsdUtil {

  /**
   * Invalid namespace message.
   */
  public static final String NO_NAMESPACE_DEFINED = "Error: no namespace defined!";

  /**
   * Logger for this class.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(XsdUtil.class);
  /**
   * Namespace of the xsd.
   */
  String namespace = NO_NAMESPACE_DEFINED;
  /**
   * Namespace + '/' + version.
   */
  String namespacePlusVersion = namespace;

  /**
   * Get target namespace and version from XSD file.
   *
   * @param fileContent Content of XSD file as string.
   * 
   * @return String containing target namespace and version.
   */
  public String getNamespaceAndVersionFromXsd(String fileContent) {

    try {
      Document document = JaxenUtil.getDocument(fileContent);
      Namespace[] namespaces = {Namespace.getNamespace("xs", "http://www.w3.org/2001/XMLSchema")};
      namespace = JaxenUtil.getAttributeValue(document, "/xs:schema/@targetNamespace", namespaces);
//     Currently we donot support the version as a part as version attribute has to be present in XML file to which will be additional attribute 
//      String version = JaxenUtil.getAttributeValue(document, "/xs:schema/@version", namespaces);
      /*if (version != null) {
        namespacePlusVersion = namespace + "/" + version;
      }*/
      namespacePlusVersion = namespace;
    } catch (Exception ex) {
      namespacePlusVersion = namespace;
      LOGGER.error(null, ex);
    }
    return namespacePlusVersion;
  }

  /**
   * Get target namespace from parsed XSD file.
   *
   * @return Target namespace.
   */
  public String getNamespace() {
    return namespace;
  }

}
