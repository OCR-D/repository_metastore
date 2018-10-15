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
import edu.kit.datamanager.metastore.entity.MetsFile;
import edu.kit.datamanager.metastore.entity.SectionDocument;
import edu.kit.datamanager.metastore.repository.MetsDocumentRepository;
import edu.kit.datamanager.metastore.service.IMetsDocumentService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.collections4.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * Class implementing METS services.
 * 
 * @author hartmann-v
 */
@Service
public class MetsDocumentService implements IMetsDocumentService {
  @Autowired
	private MetsDocumentRepository metsRepository;

  @Override
  public List<MetsDocument> getAllDocuments() {
    Iterator<MetsDocument> metsIterator = metsRepository.findAll().iterator();
    List<MetsDocument> metsList = IteratorUtils.toList(metsIterator);
    return metsList;
  }

  @Override
  public MetsDocument getMostRecentDocumentByResourceId(String resourceId) {
    MetsDocument metsDocument = metsRepository.findTop1DistinctByResourceIdOrderByVersionDesc(resourceId);
    return metsDocument;
  }

  @Override
  public List<MetsDocument> getDocumentByResourceId(String resourceId) {
    Iterator<MetsDocument> metsIterator = metsRepository.findByResourceId(resourceId).iterator();
    List<MetsDocument> metsList = IteratorUtils.toList(metsIterator);
    return metsList;
  }

  @Override
  public MetsDocument getDocumentByResourceIdAndVersion(String resourceId, Integer version) {
    MetsDocument document = null;
    Iterable<MetsDocument> metsIterator = metsRepository.findByResourceId(resourceId);
    for (MetsDocument documentIndex : metsIterator) {
      if (documentIndex.getVersion().compareTo(version) == 0) {
        document = documentIndex;
        break;
      }
    }
    return document;
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
  public List<String> getPrefixOfAvailableSectionDocuments(String resourceId) {
    List<String> prefixList = new ArrayList<>();
    MetsDocument metsDocument = getMostRecentDocumentByResourceId(resourceId);
    Collection<SectionDocument> sectionDocuments = metsDocument.getSectionDocuments();
    for (SectionDocument indexElement : sectionDocuments) {
      prefixList.add(indexElement.getPrefix());
    }
    return prefixList;
  }

  @Override
  public List<SectionDocument> getAllSectionDocuments(String resourceId) {
    List<SectionDocument> sectionList = new ArrayList<>();
    MetsDocument metsDocument = getMostRecentDocumentByResourceId(resourceId);
    Collection<SectionDocument> sectionDocuments = metsDocument.getSectionDocuments();
    sectionList.addAll(sectionDocuments);
    return sectionList;
  }

  @Override
  public SectionDocument getSectionDocument(String resourceId, String prefix) {
    SectionDocument sectionDocument = null;
    List<SectionDocument> allSectionDocuments = getAllSectionDocuments(resourceId);
    
    for (SectionDocument indexElement : allSectionDocuments) {
      if (indexElement.getPrefix().equals(prefix)) {
        sectionDocument = indexElement;
        break;
      }
    }
    return sectionDocument;
  }

  @Override
  public List<MetsFile> getAvailableMetsFiles(String resourceId) {
    List<MetsFile> metsFiles = new ArrayList<>();
    MetsDocument metsDocument = getMostRecentDocumentByResourceId(resourceId);
    Collection<MetsFile> allMetsFiles = metsDocument.getMetsFiles();
    metsFiles.addAll(allMetsFiles);
    return metsFiles;
  }

  @Override
  public List<MetsFile> getAvailableMetsFilesByUseAndGroupId(String resourceId, String[] use, String[] groupId) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public List<MetsFile> getAvailableMetsFilesByFileId(String resourceId, String fileId) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public void createMetsDocument(MultipartFile file) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }
}
