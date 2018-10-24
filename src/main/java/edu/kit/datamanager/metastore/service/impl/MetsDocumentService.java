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

import edu.kit.datamanager.metastore.entity.IVersion;
import edu.kit.datamanager.metastore.entity.MetsDocument;
import edu.kit.datamanager.metastore.exception.ResourceAlreadyExistsException;
import edu.kit.datamanager.metastore.repository.MetsDocumentRepository;
import edu.kit.datamanager.metastore.service.IMetsDocumentService;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.collections4.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Class implementing METS services.
 * 
 */
@Service
public class MetsDocumentService implements IMetsDocumentService {
  @Autowired
	private MetsDocumentRepository metsRepository;

  @Override
  public List<MetsDocument> getAllDocuments() {
    Iterator<MetsDocument> metsIterator = metsRepository.findByCurrentTrue().iterator();
    List<MetsDocument> metsList = IteratorUtils.toList(metsIterator);
    return metsList;
  }

  @Override
  public MetsDocument getMostRecentMetsDocumentByResourceId(String resourceId) {
     MetsDocument metsDocument = metsRepository.findByResourceIdAndCurrentTrue(resourceId);
    return metsDocument;
  }

  @Override
  public List<Integer> getAllVersionsByResourceId(String resourceId) {
    List<Integer> versionList = new ArrayList<>();
    Iterable<IVersion> versionIterator = metsRepository.findVersionByResourceIdOrderByVersionDesc(resourceId);
    for (IVersion indexElement : versionIterator) {
      versionList.add(indexElement.getVersion());
    }
    return versionList;
  }

  @Override
  public MetsDocument getDocumentByResourceIdAndVersion(String resourceId, Integer version) {
    MetsDocument document = metsRepository.findByResourceIdAndVersion(resourceId, version);
    return document;
  }
  @Override
  public void createMetsDocument(String resourceId, String fileContent) throws ResourceAlreadyExistsException {
    MetsDocument metsDocExists = metsRepository.findByResourceIdAndCurrentTrue(resourceId);
    // Check for existing resource ID
    if (metsDocExists != null) {
      throw new ResourceAlreadyExistsException("METS document with resourceid '" + resourceId + "' already exists!");
    }
    // 
    // Validate METS (priority low)
    // Extract section documents (priority low)
    //   Validate section documents (priority low)
    // Extract fileGrps
    //   Change URL if neccessary
    // If everything is valid:
    // Store METS document
    // Store section documents (priority low)
    // Store METS files
        MetsDocument metsDoc = new MetsDocument(resourceId, fileContent);
    metsRepository.save(metsDoc);
  }

  @Override
  public void updateMetsDocument(String resourceId, String fileContent) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }
}
