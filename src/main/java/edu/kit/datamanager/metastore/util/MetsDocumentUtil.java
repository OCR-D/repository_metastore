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
package edu.kit.datamanager.metastore.util;

import edu.kit.datamanager.metastore.entity.MetsFile;
import edu.kit.datamanager.metastore.entity.MetsProperties;
import java.util.ArrayList;
import java.util.List;
import org.fzk.tools.xml.JaxenUtil;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility handling METS document.
 */
public class MetsDocumentUtil {

  /**
   * Logger.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(MetsDocumentUtil.class);
  /**
   * Namespaces used inside METS documents.
   */
  private static Namespace[] namespaces = {
    Namespace.getNamespace("mets", "http://www.loc.gov/METS/"),
    Namespace.getNamespace("mods", "http://www.loc.gov/mods/v3"),
    Namespace.getNamespace("xlink", "http://www.w3.org/1999/xlink")
  };

  /**
   * Extract MetsFile instances from METS document.
   *
   * @param metsDocument METS document.
   * @param resourceId Resource ID of METS document.
   * @param version Version of METS document.
   * 
   * @return List with all found files.
   */
  public static List<MetsFile> extractMetsFiles(Document metsDocument, String resourceId, Integer version) {
    LOGGER.info("Extract files from METS document. ResourceID: {}, Version: {}", resourceId, version);
    List<MetsFile> metsFiles = new ArrayList<>();
    List nodes = JaxenUtil.getNodes(metsDocument, "//mets:fileGrp", namespaces);
    LOGGER.trace("Found {} fileGrp(s)", nodes.size());
    for (Object node : nodes) {
      Element fileGrpElement = (Element) node;
      String use = JaxenUtil.getAttributeValue(fileGrpElement, "./@USE");
      List fileNodes = JaxenUtil.getNodes(fileGrpElement, "./mets:file", namespaces);
      LOGGER.trace("Found fileGrp with USE: {} containing {} file(s)", use, fileNodes.size());
      for (Object node2 : fileNodes) {
        Element fileElement = (Element) node2;
        String id = JaxenUtil.getAttributeValue(fileElement, "./@ID");
        String groupId;
        try {
          groupId = JaxenUtil.getAttributeValue(metsDocument, "//mets:div[./mets:fptr/@FILEID='" + id + "']/@ID", namespaces);
        } catch (ArrayIndexOutOfBoundsException aioobe) {
          // Try to find groupId using old style
         groupId = JaxenUtil.getAttributeValue(fileElement, "./@GROUPID");
        }
        String mimetype = JaxenUtil.getAttributeValue(fileElement, "./@MIMETYPE");
        String url = JaxenUtil.getAttributeValue(fileElement, "./mets:FLocat/@xlink:href", namespaces);
        LOGGER.trace("Found file with id: {}, groupId: {}, mimetype: {}, url: {}", id, groupId, mimetype, url);
        metsFiles.add(new MetsFile(resourceId, version, id, mimetype, groupId, use, url));
      }
    }
    return metsFiles;
  }

  /**
   * Extract MetsProperties instances from METS document.
   *
   * @param metsDocument METS document.
   * @param resourceId Resource ID of METS document.
   * @param version Version of METS document.
   * 
   * @return METS properties.
   */
  public static MetsProperties extractProperties(Document metsDocument, String resourceId, Integer version) {
    LOGGER.info("Extract MetsProperties from METS document. ResourceID: {}, Version: {}", resourceId, version);
    
    MetsProperties metsProperties = new MetsProperties(metsDocument);
    metsProperties.setResourceId(resourceId);
    
    return metsProperties;
  }
  /**
   * Get all namespaces used inside METS document.
   * (Do not contain namespaces used only inside special section documents.)
   * 
   * @return Namespaces used inside METS document.
   */
  public static Namespace[] getNamespaces() {
    return namespaces;
  }
}
