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
import edu.kit.ocrd.workspace.entity.TextRegion;
import edu.kit.datamanager.metastore.repository.TextRegionRepository;
import edu.kit.datamanager.metastore.service.ITextRegionService;
import edu.kit.ocrd.workspace.PageExtractorUtil;
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
 */
@Service
public class TextRegionService implements ITextRegionService {

  /**
   * Logger.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(TextRegionService.class);

  /**
   * Repository persisting Mets documents.
   */
  private TextRegionRepository textRegionRepository;

  /**
   * Set repository via autowired, to allow initialization.
   *
   * @param textRegionRepository
   */
  @Autowired
  public void setTextRegionRepository(TextRegionRepository textRegionRepository) {
    this.textRegionRepository = textRegionRepository;
    long count = textRegionRepository.count();
    LOGGER.debug("No of entities in TextRegionRepository: {}", count);
  }

  @Override
  public List<TextRegion> getTextRegionByResourceId(String resourceId) {
    Iterator<TextRegion> provenanceIterator = textRegionRepository.findByResourceIdOrderByOrderAsc(resourceId).iterator();
    List<TextRegion> provenanceList = IteratorUtils.toList(provenanceIterator);
    return provenanceList;
  }

  @Override
  public void createTextRegion(String resourceId, File metsFile) throws InvalidFormatException {
    if (metsFile != null && metsFile.exists()) {
      try {
        List<TextRegion> extractAllTextRegions = PageExtractorUtil.extractAllTextRegions(metsFile, resourceId);
        LOGGER.debug("Store #{} text regions in database!", extractAllTextRegions.size());
        if (extractAllTextRegions.size() > 0) {
          textRegionRepository.saveAll(extractAllTextRegions);
        }
      } catch (Exception ex) {
        throw new InvalidFormatException(ex.getMessage());
      }
    }
  }
}
