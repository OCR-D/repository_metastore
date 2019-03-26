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

import edu.kit.datamanager.metastore.dao.MetsMetadata;
import edu.kit.datamanager.metastore.dao.ModsIdentifier;
import edu.kit.datamanager.metastore.dao.PageFeatures;
import edu.kit.datamanager.metastore.entity.ClassificationMetadata;
import edu.kit.datamanager.metastore.entity.GenreMetadata;
import edu.kit.datamanager.metastore.entity.GroundTruthProperties;
import edu.kit.datamanager.metastore.entity.LanguageMetadata;
import edu.kit.datamanager.metastore.entity.MetsFile;
import edu.kit.datamanager.metastore.entity.MetsIdentifier;
import edu.kit.datamanager.metastore.entity.MetsProperties;
import edu.kit.datamanager.metastore.entity.PageMetadata;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    Namespace.getNamespace("xlink", "http://www.w3.org/1999/xlink"),
    Namespace.getNamespace("gt", "http://www.ocr-d.de/GT/"),
    Namespace.getNamespace("page2017", "http://schema.primaresearch.org/PAGE/gts/pagecontent/2017-07-15")
  };
  /**
   * Title of document.
   */
  private static final String TITLE = "title";
  /**
   * Subtitle of document.
   */
  private static final String SUB_TITLE = "subtitle";
  /**
   * Author of document.
   */
  private static final String AUTHOR = "author";
  /**
   * License of document.
   */
  private static final String LICENSE = "license";
  /**
   * Language of document.
   */
  private static final String LANGUAGE = "language";
  /**
   * Year of document.
   */
  private static final String YEAR = "year";
  /**
   * Number of pages of document.
   */
  private static final String NUMBER_OF_IMAGES = "number_of_images";
  /**
   * Classifications of document.
   */
  private static final String CLASSIFICATION = "classification";
  /**
   * Genres of document.
   */
  private static final String GENRE = "genre";
  /**
   * Publisher of document.
   */
  private static final String PUBLISHER = "publisher";
  /**
   * Physical description of document.
   */
  private static final String PHYSICAL_DESCRIPTION = "physical_description";
  /**
   * Record identifier (PPN) of document.
   */
  private static final String PPN = "PPN";

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
        String pageId;
        try {
          pageId = JaxenUtil.getAttributeValue(metsDocument, "//mets:div[./mets:fptr/@FILEID='" + id + "']/@ID", namespaces);
        } catch (ArrayIndexOutOfBoundsException aioobe) {
          // Try to find pageId using old style
          pageId = JaxenUtil.getAttributeValue(fileElement, "./@GROUPID");
        }
        String mimetype = JaxenUtil.getAttributeValue(fileElement, "./@MIMETYPE");
        String url = JaxenUtil.getAttributeValue(fileElement, "./mets:FLocat/@xlink:href", namespaces);
        LOGGER.trace("Found file with id: {}, pageId: {}, mimetype: {}, url: {}", id, pageId, mimetype, url);
        metsFiles.add(new MetsFile(resourceId, version, id, mimetype, pageId, use, url));
      }
    }
    return metsFiles;
  }

  /**
   * Extract all metadata from METS.
   *
   * @param metsDocument METS file.
   * @param resourceId Resource ID of METS document.
   * @return MetsMetadata holding all metadata.
   *
   * @throws Exception An error occurred during parsing METS file.
   */
  public static MetsProperties extractMetadataFromMets(final Document metsDocument, String resourceId) throws Exception {
    MetsProperties metsMetadata = new MetsProperties();
    metsMetadata.setResourceId(resourceId);
    // define XPaths
    Map<String, String> metsMap = new HashMap<>();
    metsMap.put(TITLE, "/mets:mets/mets:dmdSec/mets:mdWrap/mets:xmlData/mods:mods/mods:titleInfo/mods:title[not(@type)]");
    metsMap.put(SUB_TITLE, "/mets:mets/mets:dmdSec/mets:mdWrap/mets:xmlData/mods:mods/mods:titleInfo/mods:subTitle[not(@type)]");
    metsMap.put(YEAR, "/mets:mets/mets:dmdSec/mets:mdWrap/mets:xmlData/mods:mods/mods:originInfo/mods:dateIssued");
    metsMap.put(LICENSE, "//mets:rightsMD/descendant::*");
    metsMap.put(AUTHOR, "//mods:name/mods:role/mods:roleTerm[text()='aut']/../../mods:displayForm");
    metsMap.put(NUMBER_OF_IMAGES, "/mets:mets/mets:fileSec/mets:fileGrp[@USE='OCR-D-IMG']/mets:file");
    metsMap.put(PUBLISHER, "//mods:publisher[not(@keydate = 'yes')]");
    metsMap.put(PHYSICAL_DESCRIPTION, "//mods:physicalDescription/mods:extent");
    metsMap.put(PPN, "//mods:mods/mods:recordInfo/mods:recordIdentifier[1]");
    Element root = metsDocument.getRootElement();
    String[] values = JaxenUtil.getValues(root, metsMap.get(TITLE), namespaces);
    if (values.length >= 1) {
      metsMetadata.setTitle(values[0]);
    }
    values = JaxenUtil.getValues(root, metsMap.get(SUB_TITLE), namespaces);
    if (values.length >= 1) {
      metsMetadata.setSubTitle(values[0]);
    }
    values = JaxenUtil.getValues(root, metsMap.get(YEAR), namespaces);
    if (values.length >= 1) {
      metsMetadata.setYear(values[0]);
    }
    values = JaxenUtil.getValues(root, metsMap.get(LICENSE), namespaces);
    if (values.length >= 1) {
      StringBuilder builder = new StringBuilder();
      for (int i = 0; i < values.length; i++) {
        if (values[i].trim().length() > 0) {
          if (builder.length() > 0) {
            builder.append(", ");
          }
          builder.append(values[i]);
        }
      }
      metsMetadata.setLicense(builder.toString());
    }
    values = JaxenUtil.getValues(root, metsMap.get(AUTHOR), namespaces);
    if (values.length >= 1) {
      metsMetadata.setAuthor(values[0]);
    }
    values = JaxenUtil.getValues(root, metsMap.get(NUMBER_OF_IMAGES), namespaces);
    metsMetadata.setNoOfPages(values.length);

    values = JaxenUtil.getValues(root, metsMap.get(PUBLISHER), namespaces);
    if (values.length >= 1) {
      metsMetadata.setPublisher(values[0]);
    }
    values = JaxenUtil.getValues(root, metsMap.get(PHYSICAL_DESCRIPTION), namespaces);
    if (values.length >= 1) {
      metsMetadata.setPhysicalDescription(values[0]);
    }
    values = JaxenUtil.getValues(root, metsMap.get(PPN), namespaces);
    if (values.length >= 1) {
      metsMetadata.setPpn(values[0]);
    }
    return metsMetadata;
  }

  /**
   * Extract all identifiers from METS.
   *
   * @param metsDocument METS file.
   * @param resourceId Resource ID of METS document.
   * @return List of MetsIdentifier holding all identifiers.
   *
   * @throws Exception An error occurred during parsing METS file.
   */
  public static List<MetsIdentifier> extractIdentifierFromMets(final Document metsDocument, final String resourceId) throws Exception {
    List<MetsIdentifier> metsIdentifierList = new ArrayList<>();
    Element root = metsDocument.getRootElement();
    List identifierList = JaxenUtil.getNodes(root, "//mods:identifier", namespaces);
    if (!identifierList.isEmpty()) {
      for (Object identifierObject : identifierList) {
        // Determine type and id 
        Element identifier = (Element) identifierObject;
        String type = identifier.getAttribute("type").getValue();
        String id = identifier.getValue();
        metsIdentifierList.add(new MetsIdentifier(resourceId, type, id));
      }
    }
    return metsIdentifierList;
  }

  /**
   * Extract all language metadata from METS.
   *
   * @param metsDocument METS file.
   * @param resourceId Resource ID of METS document.
   * @return List of LanguageMetadata holding all language metadata.
   *
   * @throws Exception An error occurred during parsing METS file.
   */
  public static List<LanguageMetadata> extractLanguageMetadataFromMets(final Document metsDocument, final String resourceId) throws Exception {
    List<LanguageMetadata> languageList = new ArrayList<>();
    Map<String, String> metsMap = new HashMap<>();
    // define XPaths
    metsMap.put(LANGUAGE, "//mods:languageTerm");
    Element root = metsDocument.getRootElement();
    String[] values = JaxenUtil.getValues(root, metsMap.get(LANGUAGE), namespaces);
    if (values.length >= 1) {
      for (String language : values) {
        if (language.trim().length() > 1) {
          languageList.add(new LanguageMetadata(resourceId, language.trim()));
        }
      }
    }
    return languageList;
  }

  /**
   * Extract all classification metadata from METS.
   *
   * @param metsDocument METS file.
   * @param resourceId Resource ID of METS document.
   * @return List of ClassificationMetadata holding all classification metadata.
   *
   * @throws Exception An error occurred during parsing METS file.
   */
  public static List<ClassificationMetadata> extractClassificationMetadataFromMets(final Document metsDocument, final String resourceId) throws Exception {
    List<ClassificationMetadata> classificationList = new ArrayList<>();
    Map<String, String> metsMap = new HashMap<>();
    // define XPaths
    metsMap.put(CLASSIFICATION, "//mods:classification");
    Element root = metsDocument.getRootElement();
    String[] values = JaxenUtil.getValues(root, metsMap.get(CLASSIFICATION), namespaces);
    if (values.length >= 1) {
      for (String classification : values) {
        if (classification.trim().length() > 1) {
          classificationList.add(new ClassificationMetadata(resourceId, classification.trim()));
        }
      }
    }
    return classificationList;
  }

  /**
   * Extract all genre metadata from METS.
   *
   * @param metsDocument METS file.
   * @param resourceId Resource ID of METS document.
   * @return List of ClassificationMetadata holding all genre metadata.
   *
   * @throws Exception An error occurred during parsing METS file.
   */
  public static List<GenreMetadata> extractGenreMetadataFromMets(final Document metsDocument, final String resourceId) throws Exception {
    List<GenreMetadata> genreList = new ArrayList<>();
    Map<String, String> metsMap = new HashMap<>();
    // define XPaths
    metsMap.put(GENRE, "//mods:genre");
    Element root = metsDocument.getRootElement();
    String[] values = JaxenUtil.getValues(root, metsMap.get(GENRE), namespaces);
    if (values.length >= 1) {
      for (String genre : values) {
        if (genre.trim().length() > 1) {
          genreList.add(new GenreMetadata(resourceId, genre.trim()));
        }
      }
    }
    return genreList;
  }

  /**
   * Extract all ground truth metadata from METS.
   *
   * @param metsDocument METS file.
   * @param resourceId Resource ID of METS document.
   * @return List of PageMetadata holding all ground truth metadata.
   *
   * @throws Exception An error occurred during parsing METS file.
   */
  public static List<PageMetadata> extractGroundTruthFeaturesFromMets(final Document metsDocument, final String resourceId) throws Exception {
    List<PageMetadata> pageMetadataList = new ArrayList<>();
    Element root = metsDocument.getRootElement();
    List physicalList = JaxenUtil.getNodes(root, "//mets:structMap[@TYPE='PHYSICAL']", namespaces);
    if (!physicalList.isEmpty()) {
      Element structMap = (Element) physicalList.get(0);
      List pageList = JaxenUtil.getNodes(structMap, "//mets:div[@TYPE='page']", namespaces);
      if (!pageList.isEmpty()) {
        for (Object pageObject : pageList) {
          // Determine order, id and dmdid. 
          Element pageNode = (Element) pageObject;
          String order = pageNode.getAttribute("ORDER").getValue();
          String id = pageNode.getAttribute("ID").getValue();
          String dmdId = pageNode.getAttribute("DMDID").getValue();
          String[] features = JaxenUtil.getValues(root, "//mets:dmdSec[@ID='" + dmdId + "']/mets:mdWrap[@OTHERMDTYPE='GT']/mets:xmlData/gt:gt/gt:state/@prop", namespaces);
          for (String feature : features) {
            pageMetadataList.add(new PageMetadata(resourceId, order, id, GroundTruthProperties.get(feature)));
          }
        }
      }
    }
    return pageMetadataList;
  }

  /**
   * Convert entities to dao served to client.
   *
   * @param metadataEntity Metadata of METS document.
   * @param languages List with all languages of document.
   * @param classifications List of all classifications of document.
   * @param genres List of all genres of document.
   * @param pages List of all ground truth features of document.
   * @param identifiers List of all identifiers of document.
   * @return Metadata as a DAO
   */
  public static MetsMetadata convertEntityToDao(MetsProperties metadataEntity,
          List<LanguageMetadata> languages,
          List<ClassificationMetadata> classifications,
          List<GenreMetadata> genres,
          List<PageMetadata> pages,
          List<MetsIdentifier> identifiers) {
    MetsMetadata dao = new MetsMetadata();
    if (metadataEntity != null) {
      dao.setTitle(metadataEntity.getTitle());
      dao.setSubTitle(metadataEntity.getSubTitle());
      dao.setYear(metadataEntity.getYear());
      dao.setAuthor(metadataEntity.getAuthor());
      dao.setPublisher(metadataEntity.getPublisher());
      dao.setLicence(metadataEntity.getLicense());
      dao.setNoOfPages(metadataEntity.getNoOfPages());
      dao.setPhysicalDescription(metadataEntity.getPhysicalDescription());
    }
    if (languages != null) {
      List<String> languageList = new ArrayList<>();
      languages.forEach((language) -> {
        languageList.add(language.getLanguage());
      });
      dao.setLanguage(languageList);
    }
    if (classifications != null) {
      List<String> classificationList = new ArrayList<>();
      classifications.forEach((classification) -> {
        classificationList.add(classification.getClassification());
      });
      dao.setLanguage(classificationList);
    }
    if (genres != null) {
      List<String> genreList = new ArrayList<>();
      genres.forEach((genre) -> {
        genreList.add(genre.getGenre());
      });
      dao.setLanguage(genreList);
    }
    if (pages != null) {
      List<PageFeatures> pageList = new ArrayList<>();
      Map<String, PageFeatures> pageMap = new HashMap<>();
      pages.forEach((page) -> {
        PageFeatures item = pageMap.get(page.getPageId());
        if (item == null) {
          item = new PageFeatures();
          item.setPageId(page.getPageId());
          item.setOrder(page.getOrder());
          pageMap.put(page.getPageId(), item);
          item.setFeatures(new ArrayList<>());
        }
        item.getFeatures().add(page.getFeature().toString());
      });
      pageMap.values().forEach((item) -> {
        pageList.add(item);
      });
      dao.setPages(pageList);
    }
    if (identifiers != null) {
      List<ModsIdentifier> modsIdentifierList = new ArrayList<>();
      identifiers.forEach((identifier) -> {
        modsIdentifierList.add(new ModsIdentifier(identifier.getType(), identifier.getIdentifier()));
      });
      dao.setModsIdentifier(modsIdentifierList);
    }
    return dao;
  }

  /**
   * Get all namespaces used inside METS document. (Do not contain namespaces
   * used only inside special section documents.)
   *
   * @return Namespaces used inside METS document.
   */
  public static Namespace[] getNamespaces() {
    return namespaces;
  }
}
