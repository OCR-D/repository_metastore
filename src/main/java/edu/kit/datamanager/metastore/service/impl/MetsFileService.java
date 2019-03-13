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

import edu.kit.datamanager.metastore.entity.MetsFile;
import edu.kit.datamanager.metastore.repository.MetsFileRepository;
import edu.kit.datamanager.metastore.service.IMetsFileService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.apache.commons.collections4.IteratorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Class implementing METS services.
 */
@Service
public class MetsFileService implements IMetsFileService {

  /**
   * Logger.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(MetsFileService.class);

  /**
   * Repository persisting METS files.
   */
  @Autowired
  private MetsFileRepository metsFileRepository;

  @Override
  public List<String> getAllPageIds(String resourceId) {
    Iterable<MetsFile> metsFileIterator = metsFileRepository.findByResourceIdAndCurrentTrue(resourceId);

    Set<String> setOfPageIds = new HashSet<>();
    for (MetsFile metsFile : metsFileIterator) {
      setOfPageIds.add(metsFile.getPageId());
    }
    List<String> listOfPageIds = new ArrayList<>(setOfPageIds);

    return listOfPageIds;
  }

  @Override
  public List<String> getAllUses(String resourceId) {
    Iterable<MetsFile> metsFileIterator = metsFileRepository.findByResourceIdAndCurrentTrue(resourceId);

    Set<String> setOfPageIds = new HashSet<>();
    for (MetsFile metsFile : metsFileIterator) {
      setOfPageIds.add(metsFile.getUse());
    }
    List<String> listOfUses = new ArrayList<>(setOfPageIds);

    return listOfUses;
  }

  @Override
  public List<MetsFile> getAvailableMetsFilesOfCurrentVersion(String resourceId) {
    Iterator<MetsFile> metsFileIterator = metsFileRepository.findByResourceIdAndCurrentTrue(resourceId).iterator();
    List<MetsFile> metsFileList = IteratorUtils.toList(metsFileIterator);
    return metsFileList;
  }

  @Override
  public List<MetsFile> getAvailableMetsFilesByUseAndPageId(String resourceId, String[] use, String[] pageId) {
    List<String> pageIdList;
    List<String> useList;
    Iterator<MetsFile> metsFileIterator;
    
    pageIdList = (pageId != null) ? Arrays.asList(pageId) : null;
    useList = (use != null) ? Arrays.asList(use) : null;
    
    if ((pageIdList != null) && (useList != null)) {
      metsFileIterator = metsFileRepository.findByResourceIdAndUseInAndPageIdInAndCurrentTrue(resourceId, useList, pageIdList).iterator();
    } else {
      if (pageIdList != null) {
        metsFileIterator = metsFileRepository.findByResourceIdAndPageIdInAndCurrentTrue(resourceId, pageIdList).iterator();
      } else {
        metsFileIterator = metsFileRepository.findByResourceIdAndUseInAndCurrentTrue(resourceId, useList).iterator();
      }
    }
    List<MetsFile> metsFileList = IteratorUtils.toList(metsFileIterator);
    return metsFileList;
  }

  @Override
  public List<MetsFile> getAvailableMetsFilesByFileIds(String resourceId, String[] fileId) {
    Iterator<MetsFile> metsFileIterator = metsFileRepository.findByResourceIdAndFileIdInAndCurrentTrue(resourceId, fileId).iterator();
    List<MetsFile> metsFileList = IteratorUtils.toList(metsFileIterator);
    return metsFileList;
  }
}
