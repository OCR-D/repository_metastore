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

import edu.kit.ocrd.workspace.entity.ClassificationMetadata;
import edu.kit.ocrd.workspace.entity.GenreMetadata;
import edu.kit.ocrd.workspace.entity.GroundTruthProperties;
import edu.kit.ocrd.workspace.entity.IResourceId;
import edu.kit.ocrd.workspace.entity.LanguageMetadata;
import edu.kit.ocrd.workspace.entity.MetsIdentifier;
import edu.kit.ocrd.workspace.entity.MetsProperties;
import edu.kit.ocrd.workspace.entity.PageMetadata;
import edu.kit.datamanager.metastore.repository.ClassificationMetadataRepository;
import edu.kit.datamanager.metastore.repository.GenreMetadataRepository;
import edu.kit.datamanager.metastore.repository.LanguageMetadataRepository;
import edu.kit.datamanager.metastore.repository.MetsIdentifierRepository;
import edu.kit.datamanager.metastore.repository.MetsPropertiesRepository;
import edu.kit.datamanager.metastore.repository.PageMetadataRepository;
import edu.kit.datamanager.metastore.service.IMetsPropertiesService;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.apache.commons.collections4.IteratorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

/**
 * Class implementing METS Properties services.
 *
 */
@Service
public class MetsPropertiesService implements IMetsPropertiesService {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MetsPropertiesService.class);

    /**
     * Repository persisting properties of METS document.
     */
    private MetsPropertiesRepository metsPropertiesRepository;

    /**
     * Repository persisting METS identifiers.
     */
    private MetsIdentifierRepository metsIdentifierRepository;

    /**
     * Repository persisting page metadata..
     */
    private PageMetadataRepository pageMetadataRepository;

    /**
     * Repository persisting classification metadata.
     */
    private ClassificationMetadataRepository classificationMetadataRepository;

    /**
     * Repository persisting genre metadata.
     */
    private GenreMetadataRepository genreMetadataRepository;

    /**
     * Repository persisting language metadata.
     */
    private LanguageMetadataRepository languageMetadataRepository;

    /**
     * Set repository via autowired, to allow initialization.
     *
     * @param metsPropertiesRepository
     */
    @Autowired
    public void setMetsPropertiesRepository(MetsPropertiesRepository metsPropertiesRepository) {
        this.metsPropertiesRepository = metsPropertiesRepository;
        long count = metsPropertiesRepository.count();
        LOGGER.debug("No of entities in MetsPropertiesRepository: {}", count);
    }

    /**
     * Set repository via autowired, to allow initialization.
     *
     * @param metsIdentifierRepository
     */
    @Autowired
    public void setMetsIdentifierRepository(MetsIdentifierRepository metsIdentifierRepository) {
        this.metsIdentifierRepository = metsIdentifierRepository;
        long count = metsIdentifierRepository.count();
        LOGGER.debug("No of entities in MetsIdentifierRepository: {}", count);
    }

    /**
     * Set repository via autowired, to allow initialization.
     *
     * @param pageMetadataRepository
     */
    @Autowired
    public void setPageMetadataRepository(PageMetadataRepository pageMetadataRepository) {
        this.pageMetadataRepository = pageMetadataRepository;
        long count = pageMetadataRepository.count();
        LOGGER.debug("No of entities in PageMetadataRepository: {}", count);
    }

    /**
     * Set repository via autowired, to allow initialization.
     *
     * @param classificationMetadataRepository
     */
    @Autowired
    public void setClassificationMetadataRepository(ClassificationMetadataRepository classificationMetadataRepository) {
        this.classificationMetadataRepository = classificationMetadataRepository;
        long count = classificationMetadataRepository.count();
        LOGGER.debug("No of entities in ClassificationMetadataRepository: {}", count);
    }

    /**
     * Set repository via autowired, to allow initialization.
     *
     * @param genreMetadataRepository
     */
    @Autowired
    public void setGenreMetadataRepository(GenreMetadataRepository genreMetadataRepository) {
        this.genreMetadataRepository = genreMetadataRepository;
        long count = genreMetadataRepository.count();
        LOGGER.debug("No of entities in GenreMetadataRepository: {}", count);
    }

    /**
     * Set repository via autowired, to allow initialization.
     *
     * @param languageMetadataRepository
     */
    @Autowired
    public void setLanguageMetadataRepository(LanguageMetadataRepository languageMetadataRepository) {
        this.languageMetadataRepository = languageMetadataRepository;
        long count = languageMetadataRepository.count();
        LOGGER.debug("No of entities in LanguageMetadataRepository: {}", count);
    }

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
    public List<String> getResourceIdsByGtLabel(String[] label, boolean pageOnly) {
        HashSet<String> resultSet = null;
        for (String gtLabeling : label) {
            GroundTruthProperties property = GroundTruthProperties.get(gtLabeling);
            // Label is a valid label
            if (property != null) {
                HashSet<String> intermediateSet = new HashSet<>();
                Iterable<PageMetadata> findByResourceId = pageMetadataRepository.findByFeature(property);
                for (PageMetadata pmd : findByResourceId) {
                    StringBuilder sb = new StringBuilder();
                    if (pageOnly) {
                        sb.append(String.format("%1$5s", pmd.getOrder()));
                    }
                    sb.append(pmd.getResourceId());
                    intermediateSet.add(sb.toString());
                }
                if (resultSet == null) {
                    resultSet = intermediateSet;
                } else {
                    resultSet.retainAll(intermediateSet);
                }
            } else {
                throw new IllegalArgumentException(String.format("'%s' is not a valid ground truth label!", gtLabeling));
            }
        }
        List<String> resultList = new ArrayList<>();
        for (String key : resultSet) {
            if (pageOnly) {
                key = key.substring(5);
            }
            if (!resultList.contains(key)) {
                resultList.add(key);
            }
        }
        return resultList;
    }

    @Override
    public List<String> getResourceIdsByClassification(String[] classification) {
        HashSet<String> resultSet = new HashSet<>();
        for (String item : classification) {
            Iterable<ClassificationMetadata> findByClassification = classificationMetadataRepository.findByClassification(item);
            for (ClassificationMetadata cmd : findByClassification) {
                resultSet.add(cmd.getResourceId());
            }
        }
        List<String> resultList = new ArrayList<>(resultSet);
        return resultList;
    }

    @Override
    public List<String> getResourceIdsByLanguage(String[] language) {
        HashSet<String> resultSet = new HashSet<>();
        for (String item : language) {
            Iterable<LanguageMetadata> findByLanguage = languageMetadataRepository.findByLanguage(item);
            for (LanguageMetadata lmd : findByLanguage) {
                resultSet.add(lmd.getResourceId());
            }
        }
        List<String> resultList = new ArrayList<>(resultSet);
        return resultList;
    }

    @Override
    public List<String> getResourceIdsByIdentifier(String identifier, String type) {
        HashSet<String> resultSet = new HashSet<>();
        Iterable<MetsIdentifier> findByIdentifier = null;
        if (type != null) {
            findByIdentifier = metsIdentifierRepository.findByIdentifierAndType(identifier, type);
        } else {
            findByIdentifier = metsIdentifierRepository.findByIdentifier(identifier);
        }
        for (MetsIdentifier mid : findByIdentifier) {
            resultSet.add(mid.getResourceId());
        }
        List<String> resultList = new ArrayList<>(resultSet);
        return resultList;
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

    /**
     * Create a list of all semantic labels.
     *
     * @return List with all semantic labels.
     */
    private Set<String> getAllSemanticLabels() {
        Iterable<PageMetadata> findAll = pageMetadataRepository.findAll();
        Set<String> semanticLabels = new HashSet<>();
        findAll.forEach((index) -> {
            semanticLabels.add(index.getFeature().toString());
        });
        return semanticLabels;
    }

    /**
     * Create a list of all classifications.
     *
     * @return List with all classifications.
     */
    private Set<String> getAllClassifications() {
        Iterable<ClassificationMetadata> findAll = classificationMetadataRepository.findAll();
        Set<String> classifications = new HashSet<>();
        findAll.forEach((index) -> {
            classifications.add(index.getClassification());
        });
        return classifications;
    }

    /**
     * Create a list of all languges.
     *
     * @return List with all languages.
     */
    private Set<String> getAllLanguages() {
        Iterable<LanguageMetadata> findAll = languageMetadataRepository.findAll();
        Set<String> languages = new HashSet<>();
        findAll.forEach((index) -> {
            languages.add(index.getLanguage());
        });
        return languages;
    }

    /**
     * Create a list of all identifiers.
     *
     *
     * @return List with all IDs.
     */
    private Set<String> getAllIdentifiers() {
        Iterable<MetsIdentifier> findAll = metsIdentifierRepository.findAll();
        Set<String> identifiers = new HashSet<>();
        findAll.forEach((index) -> {
            identifiers.add(index.getIdentifier());
        });
        return identifiers;
    }

    @Override
    public void addFeaturesToModel(Model model) {
        model.addAttribute("semanticLabels", getAllSemanticLabels());
        model.addAttribute("classes", getAllClassifications());
        model.addAttribute("languages", getAllLanguages());
        model.addAttribute("identifiers", getAllIdentifiers());
    }
}
