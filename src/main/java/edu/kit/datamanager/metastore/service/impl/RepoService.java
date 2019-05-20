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

import edu.kit.datamanager.metastore.service.IRepoService;
import edu.kit.datamanager.metastore.entity.MetsDocument;
import edu.kit.datamanager.metastore.repository.MetsDocumentRepository;
import java.io.File;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.collections4.IteratorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Class implementing METS services.
 *
 * @TODO
 */
@Service
public class RepoService implements IRepoService {

  /**
   * Logger.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(RepoService.class);

  /**
   * Repository persisting METS documents.
   */
  private MetsDocumentRepository metsRepository;

  /**
   * Set repository via autowired, to allow initialization.
   *
   * @param metsDocumentRepository
   */
  @Autowired
  public void setMetsDocumentRepository(MetsDocumentRepository metsDocumentRepository) {
    this.metsRepository = metsDocumentRepository;
    long count = metsRepository.count();
    LOGGER.debug("No of entities in MetsDocumentRepository: {}", count);
  }

  @Override
  public String createDigitalObject(File bagItFile) {
    // Not implemented yet!
//    Iterator<MetsDocument> metsIterator = metsRepository.findByCurrentTrue().iterator();
//    List<MetsDocument> metsList = IteratorUtils.toList(metsIterator);
    return "http://anyURL";
  }
}
