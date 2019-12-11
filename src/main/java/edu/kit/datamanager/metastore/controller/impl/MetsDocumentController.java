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
package edu.kit.datamanager.metastore.controller.impl;

import edu.kit.datamanager.metastore.controller.IMetsDocumentController;
import com.arangodb.ArangoDBException;
import edu.kit.datamanager.metastore.kitdm.KitDmProperties;
import edu.kit.ocrd.dao.MetsMetadata;
import edu.kit.ocrd.workspace.entity.ClassificationMetadata;
import edu.kit.ocrd.workspace.entity.GenreMetadata;
import edu.kit.ocrd.workspace.entity.LanguageMetadata;
import edu.kit.ocrd.workspace.entity.MetsDocument;
import edu.kit.ocrd.workspace.entity.MetsIdentifier;
import edu.kit.ocrd.workspace.entity.MetsProperties;
import edu.kit.ocrd.workspace.entity.PageMetadata;
import edu.kit.ocrd.workspace.entity.ZippedBagit;
import edu.kit.datamanager.metastore.service.IMetsDocumentService;
import edu.kit.datamanager.metastore.service.IMetsPropertiesService;
import edu.kit.datamanager.metastore.service.IProvenanceMetadataService;
import edu.kit.datamanager.metastore.service.ITextRegionService;
import edu.kit.datamanager.metastore.service.IZippedBagitService;
import edu.kit.ocrd.workspace.MetsDocumentUtil;
import edu.kit.ocrd.workspace.entity.ProvenanceMetadata;
import edu.kit.ocrd.workspace.entity.TextRegion;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * REST services for METS documents (fka Metastore).
 */
@Controller
@RequestMapping("api/v1/metastore/mets")
public class MetsDocumentController implements IMetsDocumentController {

    /**
     * Logger for this class.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MetsDocumentController.class);

    /**
     * Services for handling METS documents.
     */
    @Autowired
    private IMetsDocumentService metastoreService;

    /**
     * Services for handling properties of METS documents.
     */
    @Autowired
    private IMetsPropertiesService metastoreResourceService;

    /**
     * Services for handling properties of METS documents.
     */
    @Autowired
    private ITextRegionService textRegionService;

    /**
     * Services for handling properties of METS documents.
     */
    @Autowired
    private IProvenanceMetadataService provenanceMetadataService;

    /**
     * Properties for the zipped BagIt containers.
     */
    @Autowired
    private IZippedBagitService bagitService;
  /**
   * Properties of KIT DM 2.0
   */
  private final KitDmProperties repositoryProperties;
  /**
   * Constructor setting up KIT DM properties.
   *
   * @param repositoryProperties Properties for access to KIT DM repository.
   */
  @Autowired
  public MetsDocumentController(KitDmProperties repositoryProperties) {
    this.repositoryProperties = repositoryProperties;
  }

    @Override
    public ResponseEntity<List<MetsDocument>> getAllDocuments() {
        List<MetsDocument> allDocuments = metastoreService.getAllDocuments();
        LOGGER.trace("Get all METS documents");
        return new ResponseEntity<>(allDocuments, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<MetsDocument> getLatestMetsDocument(@PathVariable("resourceId") String resourceId) {
        LOGGER.trace("Get latest METS documents with ID '{}'", resourceId);
        MetsDocument metsDoc;
        metsDoc = metastoreService.getMostRecentMetsDocumentByResourceId(resourceId);
        return new ResponseEntity<>(metsDoc, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<MetsDocument> getMetsDocumentByVersion(@PathVariable("resourceId") String resourceId, @PathVariable("version") Integer version) {
        LOGGER.trace("Get METS documents with ID '{}' and version {}", resourceId, version);
        MetsDocument metsDoc;
        metsDoc = metastoreService.getDocumentByResourceIdAndVersion(resourceId, version);
        return new ResponseEntity<>(metsDoc, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> createMetsDocument(@PathVariable("resourceId") String resourceId, @RequestParam("fileContent") String fileContent) {
        LOGGER.trace("Create METS document!");
        metastoreService.createMetsDocument(resourceId, fileContent);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().build().toUri();
        return ResponseEntity.created(location).build();
    }

    @Override
    public ResponseEntity<List<Integer>> getVersionsOfMetsDocument(@PathVariable("resourceId") String resourceId) {
        LOGGER.trace("Get versions of METS documents with ID '{}'", resourceId);
        List<Integer> versionList;
        versionList = metastoreService.getAllVersionsByResourceId(resourceId);
        return new ResponseEntity<>(versionList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<String>> getResourceIdByTitle(@RequestParam("title") String title) {
        LOGGER.trace("Get resourceID of METS documents with title '{}'", title);
        List<String> resourceIdList;
        resourceIdList = metastoreResourceService.getResourceIdsByTitle(title);
        return new ResponseEntity<>(resourceIdList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<String>> getResourceIdByPpn(@RequestParam("ppn") String ppn) {
        LOGGER.trace("Get resourceID of METS documents with PPN '{}'", ppn);
        List<String> resourceIdList;
        resourceIdList = metastoreResourceService.getResourceIdsByPpn(ppn);
        return new ResponseEntity<>(resourceIdList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<MetsMetadata> getLatestMetadataOfDocument(@PathVariable("resourceId") String resourceId, Model model) {
        LOGGER.trace("Get metadata of METS documents with resourceID '{}'", resourceId);

        MetsMetadata metsMetadata;
        MetsProperties metsProperties = metastoreResourceService.getMetadataByResourceId(resourceId);
        List<ClassificationMetadata> classifications = metastoreResourceService.getClassificationMetadataByResourceId(resourceId);
        List<GenreMetadata> genres = metastoreResourceService.getGenreMetadataByResourceId(resourceId);
        List<MetsIdentifier> identifiers = metastoreResourceService.getIdentifierByResourceId(resourceId);
        List<LanguageMetadata> languages = metastoreResourceService.getLanguageMetadataByResourceId(resourceId);
        List<PageMetadata> pages = metastoreResourceService.getPageMetadataByResourceId(resourceId);

        metsMetadata = MetsDocumentUtil.convertEntityToDao(metsProperties, languages, classifications, genres, pages, identifiers);

        return new ResponseEntity<>(metsMetadata, HttpStatus.OK);
    }

    @Override
    public String getLatestMetadataOfDocumentAsHtml(@PathVariable("resourceId") String resourceId, Model model) {
        LOGGER.trace("Get metadata of METS documents with resourceID '{}' as HTML", resourceId);
        // Collect metadata
        MetsProperties metsProperties = metastoreResourceService.getMetadataByResourceId(resourceId);
        List<MetsIdentifier> identifiers = metastoreResourceService.getIdentifierByResourceId(resourceId);
        List<LanguageMetadata> languages = metastoreResourceService.getLanguageMetadataByResourceId(resourceId);
        List<ClassificationMetadata> classifications = metastoreResourceService.getClassificationMetadataByResourceId(resourceId);
        List<GenreMetadata> genres = metastoreResourceService.getGenreMetadataByResourceId(resourceId);

        // Add all to model 
        model.addAttribute("metsMetadata", metsProperties);
        model.addAttribute("metsIdentifier", identifiers);
        model.addAttribute("metsLanguages", languages);
        model.addAttribute("metsClassifications", classifications);
        model.addAttribute("metsGenres", genres);

        return "metadata";
    }

    @Override
    public String getLatestGroundTruthMetadataOfDocumentAsHtml(@PathVariable("resourceId") String resourceId, Model model) {
        LOGGER.trace("Get ground truth of METS documents with resourceID '{}' as HTML", resourceId);
        // Collect metadata
        List<PageMetadata> pages = metastoreResourceService.getPageMetadataByResourceId(resourceId);
        // Add all to model
        model.addAttribute("pageMetadata", pages);

        return "groundTruth";
    }

    @Override
    public String getLatestTextRegionsOfDocumentAsHtml(@PathVariable("resourceId") String resourceId, Model model) {
        LOGGER.trace("Get text regions of METS documents with resourceID '{}' as HTML", resourceId);
        // Collect metadata
        List<TextRegion> textRegions = textRegionService.getTextRegionByResourceId(resourceId);
        // Add all to model
        model.addAttribute("paths", repositoryProperties);
        model.addAttribute("textRegion", textRegions);

        return "textRegion";
    }

    @Override
    public String getLatestProvenanceMetadataOfDocumentAsHtml(@PathVariable("resourceId") String resourceId, Model model) {
        LOGGER.trace("Get provenance of METS documents with resourceID '{}' as HTML", resourceId);
        // Collect metadata
        List<ProvenanceMetadata> provenance = provenanceMetadataService.getProvenanceMetadataByResourceId(resourceId);
        // Add all to model
        model.addAttribute("provenanceMetadata", provenance);

        return "provenance";
    }

    @Override
    public ResponseEntity<List<String>> getResourceIdBySemanticLabel(@RequestParam(value = "label") String[] label) {
        List<String> resourceIdList = metastoreResourceService.getResourceIdsByGtLabel(label, false);
        return new ResponseEntity<>(resourceIdList, HttpStatus.OK);
    }

    @Override
    public String getResourceIdBySemanticLabelAsHtml(@RequestParam(value = "label") String[] label, Model model) {
        List<String> resourceIdList = metastoreResourceService.getResourceIdsByGtLabel(label, false);

        buildModel(resourceIdList, model);

        return "searchForm";
    }

    @Override
    public ResponseEntity<List<String>> getResourceIdBySemanticLabelOfPage(@RequestParam(value = "label") String[] label) {
        List<String> resourceIdList = metastoreResourceService.getResourceIdsByGtLabel(label, true);
        return new ResponseEntity<>(resourceIdList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<String>> getResourceIdByClassification(@RequestParam(value = "class") String[] classification) {
        List<String> resourceIdList = metastoreResourceService.getResourceIdsByClassification(classification);
        return new ResponseEntity<>(resourceIdList, HttpStatus.OK);
    }

    @Override
    public String getResourceIdByClassificationAsHtml(@RequestParam(value = "class") String[] classification, Model model) {
        List<String> resourceIdList = metastoreResourceService.getResourceIdsByClassification(classification);

        buildModel(resourceIdList, model);

        return "searchForm";
    }

    @Override
    public ResponseEntity<List<String>> getResourceIdByLanguage(@RequestParam(value = "lang") String[] language) {
        List<String> resourceIdList = metastoreResourceService.getResourceIdsByLanguage(language);
        return new ResponseEntity<>(resourceIdList, HttpStatus.OK);
    }

    @Override
    public String getResourceIdByLanguageAsHtml(@RequestParam(value = "lang") String[] language, Model model) {
        List<String> resourceIdList = metastoreResourceService.getResourceIdsByLanguage(language);

        buildModel(resourceIdList, model);

        return "searchForm";
    }

    @Override
    public ResponseEntity<List<String>> getResourceIdByIdentifier(@RequestParam(value = "identifier") String identifier,
            @RequestParam(value = "type", required = false) String type) {
        List<String> resourceIdList = metastoreResourceService.getResourceIdsByIdentifier(identifier, type);
        return new ResponseEntity<>(resourceIdList, HttpStatus.OK);
    }

    @Override
    public String getResourceIdByIdentifierAsHtml(@RequestParam(value = "identifier") String identifier,
            @RequestParam(value = "type", required = false) String type, Model model) {
        List<String> resourceIdList = metastoreResourceService.getResourceIdsByIdentifier(identifier, type);

        buildModel(resourceIdList, model);

        return "searchForm";
    }

    /**
     * Build model for response.
     *
     * @param resourceIdList List with all bagit containers.
     * @param model holding all values.
     */
    private void buildModel(List<String> resourceIdList, Model model) {
        List<ZippedBagit> bagitList = new ArrayList<>();
        for (String resourceId : resourceIdList) {

            ZippedBagit bagit = bagitService.getZippedBagitByResourceId(resourceId);
            if (bagit.getLatest()) {
                // Add only latest versions
                bagitList.add(bagit);
            }

        }
        model.addAttribute("bagitFiles", bagitList);

        metastoreResourceService.addFeaturesToModel(model);
    }

    /**
     * Handler for Exceptions.
     *
     * @param ade Exception accessing database.
     *
     * @return Error status.
     */
    @ExceptionHandler(ArangoDBException.class)
    public ResponseEntity<?> handleArangoDBException(ArangoDBException ade) {
        ResponseEntity<?> responseEntity = ResponseEntity.status(ade.getResponseCode()).body(ade.getErrorMessage());
        return responseEntity;
    }
}
