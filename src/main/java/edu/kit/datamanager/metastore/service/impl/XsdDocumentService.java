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
package edu.kit.datamanager.metastore.service.impl;

import edu.kit.ocrd.workspace.entity.XmlSchemaDefinition;
import edu.kit.datamanager.metastore.repository.XmlSchemaDefinitionRepository;
import edu.kit.datamanager.metastore.service.IXsdDocumentService;
import edu.kit.datamanager.metastore.util.XsdUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.collections4.IteratorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Class implementing XSD services.
 */
@Service
public class XsdDocumentService implements IXsdDocumentService {

  /**
   * Logger.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(XsdDocumentService.class);
  /**
   * Repository persisting XSD documents.
   */
  private XmlSchemaDefinitionRepository xsdRepository;

  /**
   * Set repository via autowired, to allow initialization.
   *
   * @param xsdRepository
   */
  @Autowired
  public void setXmlSchemaDefinitionRepository(XmlSchemaDefinitionRepository xsdRepository) {
    this.xsdRepository = xsdRepository;
    long count = xsdRepository.count();
    LOGGER.debug("No of entities in XmlSchemaDefinitionRepository: {}", count);
  }

  @Override
  public List<XmlSchemaDefinition> getAllDocuments() {
    Iterator<XmlSchemaDefinition> xsdIterator = xsdRepository.findAll().iterator();
    List<XmlSchemaDefinition> xsdList = IteratorUtils.toList(xsdIterator);
    return xsdList;
  }

  @Override
  public List<String> getAllPrefixes() {
    List<XmlSchemaDefinition> allDocuments = getAllDocuments();
    List<String> prefixList = new ArrayList<>();
    allDocuments.forEach((index) -> {
      prefixList.add(index.getPrefix());
    });
    return prefixList;
  }

  @Override
  public XmlSchemaDefinition getDocumentByNamespace(String namespace) {
    XmlSchemaDefinition xsdDoc = xsdRepository.findByNamespace(namespace);
    return xsdDoc;
  }

  @Override
  public XmlSchemaDefinition getDocumentByPrefix(String prefix) {
    XmlSchemaDefinition xsdDoc = xsdRepository.findByPrefix(prefix);
    return xsdDoc;
  }

  @Override
  public void createXsdDocument(String prefix, String file) {
    XsdUtil util = new XsdUtil();
    util.getNamespaceAndVersionFromXsd(file);
    String namespace = util.getNamespace();
    XmlSchemaDefinition xsdFile = new XmlSchemaDefinition(prefix, namespace, file);
    xsdRepository.save(xsdFile);
  }
}
