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

import edu.kit.datamanager.metastore.entity.ClassificationMetadata;
import edu.kit.datamanager.metastore.entity.GenreMetadata;
import edu.kit.datamanager.metastore.entity.IResourceId;
import edu.kit.datamanager.metastore.entity.LanguageMetadata;
import edu.kit.datamanager.metastore.entity.MetsIdentifier;
import edu.kit.datamanager.metastore.entity.MetsProperties;
import edu.kit.datamanager.metastore.entity.PageMetadata;
import edu.kit.datamanager.metastore.repository.ClassificationMetadataRepository;
import edu.kit.datamanager.metastore.repository.GenreMetadataRepository;
import edu.kit.datamanager.metastore.repository.LanguageMetadataRepository;
import edu.kit.datamanager.metastore.repository.MetsIdentifierRepository;
import edu.kit.datamanager.metastore.repository.MetsPropertiesRepository;
import edu.kit.datamanager.metastore.repository.PageMetadataRepository;
import edu.kit.datamanager.metastore.service.IMetsPropertiesService;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.collections4.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Class implementing METS Properties services.
 *
 */
@Service
public class MetsPropertiesService implements IMetsPropertiesService {

  /**
   * Repository persisting properties of METS document.
   */
  @Autowired
  private MetsPropertiesRepository metsPropertiesRepository;

  /**
   * Repository persisting METS identifiers.
   */
  @Autowired
  private MetsIdentifierRepository metsIdentifierRepository;

  /**
   * Repository persisting page metadata..
   */
  @Autowired
  private PageMetadataRepository pageMetadataRepository;

  /**
   * Repository persisting classification metadata.
   */
  @Autowired
  private ClassificationMetadataRepository classificationMetadataRepository;

  /**
   * Repository persisting genre metadata.
   */
  @Autowired
  private GenreMetadataRepository genreMetadataRepository;

  /**
   * Repository persisting language metadata.
   */
  @Autowired
  private LanguageMetadataRepository languageMetadataRepository;

  @Override
  public List<String> getResourceIdsByTitle(String title) {
    Iterable<IResourceId> allResources = metsPropertiesRepository.findResourceIdByTitle(title);

    return createResourceIdList(allResources);
  }

  @Override
  public List<String> getResourceIdsByPpn(String ppn) {
    Iterable<IResourceId> allResources = metsPropertiesRepository.findResourceIdByPpn(ppn);

    return createResourceIdList(allResources);
  }

  @Override
  public MetsProperties getMetadataByResourceId(String resourceId) {
    MetsProperties metsProperties = null;
    Iterable<MetsProperties> findByResourceId = metsPropertiesRepository.findByResourceId(resourceId);
    Iterator<MetsProperties> iterator = findByResourceId.iterator();
    if (iterator.hasNext()) {
      metsProperties = iterator.next();
    }
    return metsProperties;
  }

  @Override
  public List<ClassificationMetadata> getClassificationMetadataByResourceId(String resourceId) {
    Iterable<ClassificationMetadata> findByResourceId = classificationMetadataRepository.findByResourceId(resourceId);
    Iterator<ClassificationMetadata> iterator = findByResourceId.iterator();

    return IteratorUtils.toList(iterator);
  }

  @Override
  public List<LanguageMetadata> getLanguageMetadataByResourceId(String resourceId) {
    Iterable<LanguageMetadata> findByResourceId = languageMetadataRepository.findByResourceId(resourceId);
    Iterator<LanguageMetadata> iterator = findByResourceId.iterator();

    return IteratorUtils.toList(iterator);
  }

  @Override
  public List<GenreMetadata> getGenreMetadataByResourceId(String resourceId) {
    Iterable<GenreMetadata> findByResourceId = genreMetadataRepository.findByResourceId(resourceId);
    Iterator<GenreMetadata> iterator = findByResourceId.iterator();

    return IteratorUtils.toList(iterator);
  }

  @Override
  public List<PageMetadata> getPageMetadataByResourceId(String resourceId) {
    Iterable<PageMetadata> findByResourceId = pageMetadataRepository.findByResourceId(resourceId);
    Iterator<PageMetadata> iterator = findByResourceId.iterator();

    return IteratorUtils.toList(iterator);
  }

  @Override
  public List<MetsIdentifier> getIdentifierByResourceId(String resourceId) {
    return getIdentifierByResourceIdAndType(resourceId, null);
  }

  @Override
  public List<MetsIdentifier> getIdentifierByResourceIdAndType(String resourceId, String type) {
    Iterable<MetsIdentifier> findByResourceId;
    if (type == null) {
      findByResourceId = metsIdentifierRepository.findByResourceId(resourceId);
    } else {
      findByResourceId = metsIdentifierRepository.findByResourceIdAndType(resourceId, type);
    }
    Iterator<MetsIdentifier> iterator = findByResourceId.iterator();

    return IteratorUtils.toList(iterator);
  }

  /**
   * Create a list of resource IDs.
   *
   * @param allResources Resources implementing IResourceId interface.
   *
   * @return List with resource IDs.
   */
  private List<String> createResourceIdList(Iterable<IResourceId> allResources) {
    List<String> resourceList = new ArrayList<>();
    allResources.forEach((index) -> {
      resourceList.add(index.getResourceId());
    });
    return resourceList;
  }
}
