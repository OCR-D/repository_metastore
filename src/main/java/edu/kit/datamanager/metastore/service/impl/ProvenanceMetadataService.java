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

import edu.kit.datamanager.metastore.exception.InvalidFormatException;
import edu.kit.ocrd.workspace.entity.ProvenanceMetadata;
import edu.kit.datamanager.metastore.repository.ProvenanceMetadataRepository;
import edu.kit.datamanager.metastore.service.IProvenanceMetadataService;
import edu.kit.ocrd.workspace.provenance.ProvenanceUtil;
import java.io.File;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.collections4.IteratorUtils;
import org.fzk.tools.xml.JaxenUtil;
import org.jdom.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Class implementing METS services.
 */
@Service
public class ProvenanceMetadataService implements IProvenanceMetadataService {

  /**
   * Logger.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(ProvenanceMetadataService.class);

  /**
   * Repository persisting Mets documents.
   */
  private ProvenanceMetadataRepository provenanceMetadataRepository;

  /**
   * Set repository via autowired, to allow initialization.
   *
   * @param provenanceMetadataRepository
   */
  @Autowired
  public void setProvenanceMetadataRepository(ProvenanceMetadataRepository provenanceMetadataRepository) {
    this.provenanceMetadataRepository = provenanceMetadataRepository;
    long count = provenanceMetadataRepository.count();
    LOGGER.debug("No of entities in ProvenanceMetadataRepository: {}", count);
  }

  @Override
  public List<ProvenanceMetadata> getProvenanceMetadataByResourceId(String resourceId) {
    Iterator<ProvenanceMetadata> provenanceIterator = provenanceMetadataRepository.findByResourceIdOrderByStartProcessorAsc(resourceId).iterator();
    List<ProvenanceMetadata> provenanceList = IteratorUtils.toList(provenanceIterator);
    return provenanceList;
  }

  @Override
  public List<ProvenanceMetadata> getProvenanceMetadataByResourceIdAndWorkflowId(String resourceId, String workflowId) {
    Iterator<ProvenanceMetadata> provenanceIterator = provenanceMetadataRepository.findByResourceIdAndWorkflowIdOrderByStartProcessorAsc(resourceId, workflowId).iterator();
    List<ProvenanceMetadata> provenanceList = IteratorUtils.toList(provenanceIterator);
    return provenanceList;
  }

  @Override
  public void createProvenanceMetadata(String resourceId, File metsFile, File provenanceFile) throws InvalidFormatException {
    if (metsFile != null && provenanceFile != null && metsFile.exists() && provenanceFile.exists()) {
      Document metsDocument = null;
      Document provenanceDocument = null;
      try {
        metsDocument = JaxenUtil.getDocument(metsFile);
        provenanceDocument = JaxenUtil.getDocument(provenanceFile);
        List<ProvenanceMetadata> provenanceMetadata = ProvenanceUtil.extractWorkflows(provenanceDocument, metsDocument, resourceId);
        LOGGER.debug("Store #{} processors in database!", provenanceMetadata.size());
        if (provenanceMetadata.size() > 0) {
          provenanceMetadataRepository.saveAll(provenanceMetadata);
        }
      } catch (Exception ex) {
        StringBuilder message = new StringBuilder("File '");
        if (metsDocument == null) {
          message.append(metsFile.getName());
        } else {
          message.append(provenanceFile.getName());
        }
        message.append("': ");
        message.append(ex.getMessage());
        throw new InvalidFormatException(message.toString());
      }
    }
  }
}
