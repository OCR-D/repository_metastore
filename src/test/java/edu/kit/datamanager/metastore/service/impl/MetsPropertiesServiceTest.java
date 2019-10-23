/*
 * Copyright 2019 Karlsruhe Institute of Technology.
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

import com.arangodb.springframework.core.ArangoOperations;
import edu.kit.datamanager.metastore.entity.ClassificationMetadata;
import edu.kit.datamanager.metastore.entity.GenreMetadata;
import edu.kit.datamanager.metastore.entity.GroundTruthProperties;
import edu.kit.datamanager.metastore.entity.LanguageMetadata;
import edu.kit.datamanager.metastore.entity.MetsIdentifier;
import edu.kit.datamanager.metastore.entity.MetsProperties;
import edu.kit.datamanager.metastore.entity.PageMetadata;
import edu.kit.datamanager.metastore.repository.ClassificationMetadataRepository;
import edu.kit.datamanager.metastore.repository.GenreMetadataRepository;
import edu.kit.datamanager.metastore.repository.LanguageMetadataRepository;
import edu.kit.datamanager.metastore.repository.MetsDocumentRepository;
import edu.kit.datamanager.metastore.repository.MetsIdentifierRepository;
import edu.kit.datamanager.metastore.repository.MetsPropertiesRepository;
import edu.kit.datamanager.metastore.repository.PageMetadataRepository;
import edu.kit.datamanager.metastore.runner.CrudRunner;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

/**
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class MetsPropertiesServiceTest {

    @Autowired
    private ArangoOperations operations;
    @Autowired
    private MetsDocumentRepository repository;

    @Autowired
    private MockMvc mockMvc;

    /**
     * Repository persisting METS properties.
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

    private MetsPropertiesService metsPropertiesService;

    public MetsPropertiesServiceTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        try {
            operations.dropDatabase();
        } catch (DataAccessException dae) {
            System.out.println("This message should be printed only once!");
            System.out.println(dae.toString());
        }
        repository.saveAll(CrudRunner.createMetsDocuments());

        classificationMetadataRepository.saveAll(CrudRunner.createClassifications());
        genreMetadataRepository.saveAll(CrudRunner.createGenre());
        languageMetadataRepository.saveAll(CrudRunner.createLanguageMetadata());
        metsIdentifierRepository.saveAll(CrudRunner.createMetsIdentifier());
        metsPropertiesRepository.saveAll(CrudRunner.createMetsProperties());
        pageMetadataRepository.saveAll(CrudRunner.createPageMetadata());
        metsPropertiesService = new MetsPropertiesService();
        metsPropertiesService.setClassificationMetadataRepository(classificationMetadataRepository);
        metsPropertiesService.setGenreMetadataRepository(genreMetadataRepository);
        metsPropertiesService.setLanguageMetadataRepository(languageMetadataRepository);
        metsPropertiesService.setMetsIdentifierRepository(metsIdentifierRepository);
        metsPropertiesService.setMetsPropertiesRepository(metsPropertiesRepository);
        metsPropertiesService.setPageMetadataRepository(pageMetadataRepository);
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getResourceIdsByTitle method, of class MetsPropertiesService.
     */
    @Test
    public void testGetResourceIdsByTitle() {
        System.out.println("getResourceIdsByTitle");
        String title = "Titel";
        MetsPropertiesService instance = metsPropertiesService;
        List<String> result = instance.getResourceIdsByTitle(title);
        assertEquals(2, result.size());
        MetsProperties metadataByResourceId = instance.getMetadataByResourceId(result.get(0));
        assertEquals(title, metadataByResourceId.getTitle());
        metadataByResourceId = instance.getMetadataByResourceId(result.get(1));
        assertEquals(title, metadataByResourceId.getTitle());
    }

    /**
     * Test of getResourceIdsByPpn method, of class MetsPropertiesService.
     */
    @Test
    public void testGetResourceIdsByPpn() {
        System.out.println("getResourceIdsByPpn");
        String ppn = "ppn2";
        MetsPropertiesService instance = metsPropertiesService;
        List<String> result = instance.getResourceIdsByPpn(ppn);
        assertEquals(1, result.size());
        MetsProperties metadataByResourceId = instance.getMetadataByResourceId(result.get(0));
        assertEquals(ppn, metadataByResourceId.getPpn());
    }

    /**
     * Test of getResourceIdsByGtLabel method, of class MetsPropertiesService.
     */
    @Test
    public void testGetResourceIdsByGtLabel() {
        System.out.println("getResourceIdsByGtLabel");
        String[] label = {GroundTruthProperties.ADMINS.toString()};
        boolean pageOnly = false;
        MetsPropertiesService instance = metsPropertiesService;
        List<String> result = instance.getResourceIdsByGtLabel(label, pageOnly);
        assertEquals(2, result.size());
        assertTrue(result.contains("id_0002"));
        assertTrue(result.contains("id_0018"));
        List<PageMetadata> metadataByResourceId = instance.getPageMetadataByResourceId("id_0002");
        assertEquals(3, metadataByResourceId.size());
    }

    /**
     * Test of getResourceIdsByGtLabel method, of class MetsPropertiesService.
     */
    @Test
    public void testGetResourceIdsByMultipleGtLabel() {
        System.out.println("getResourceIdsByGtLabel");
        String[] label = {GroundTruthProperties.FAX.toString(), GroundTruthProperties.DIA.toString()};
        boolean pageOnly = false;
        MetsPropertiesService instance = metsPropertiesService;
        List<String> result = instance.getResourceIdsByGtLabel(label, pageOnly);
        assertEquals(1, result.size());
    }

    /**
     * Test of getResourceIdsByGtLabel method, of class MetsPropertiesService.
     */
    @Test
    public void testGetResourceIdsByMultipleGtLabelOfSamePage() {
        System.out.println("getResourceIdsByGtLabel");
        String[] label = {GroundTruthProperties.FAX.toString(), GroundTruthProperties.DIA.toString()};
        boolean pageOnly = true;
        MetsPropertiesService instance = metsPropertiesService;
        List<String> result = instance.getResourceIdsByGtLabel(label, pageOnly);
        assertEquals(0, result.size());
    }

    /**
     * Test of getResourceIdsByClassification method, of class
     * MetsPropertiesService.
     */
    @Test
    public void testGetResourceIdsByClassification() {
        System.out.println("getResourceIdsByClassification");
        String[] classification = {"Novelle"};
        MetsPropertiesService instance = metsPropertiesService;
        List<String> result = instance.getResourceIdsByClassification(classification);
        List<ClassificationMetadata> metadataByResourceId = instance.getClassificationMetadataByResourceId(result.get(0));
        assertEquals(1, metadataByResourceId.size());
        ClassificationMetadata cmd = metadataByResourceId.get(0);

        assertEquals(cmd.getResourceId(), "id_0016");
        assertEquals(cmd.getClassification(), classification[0]);

        classification[0] = "Jahrestag";
        result = instance.getResourceIdsByClassification(classification);
        metadataByResourceId = instance.getClassificationMetadataByResourceId(result.get(0));
        assertEquals(2, metadataByResourceId.size());

        classification[0] = "Geburtstag";
        result = instance.getResourceIdsByClassification(classification);
        assertEquals(3, result.size());
    }

    /**
     * Test of getResourceIdsByClassification method, of class
     * MetsPropertiesService.
     */
    @Test
    public void testGetResourceIdsByMultipleClassification() {
        System.out.println("getResourceIdsByClassification");
        String[] classification = {"Tagebuch", "Geburtstag"};
        MetsPropertiesService instance = metsPropertiesService;
        List<String> result = instance.getResourceIdsByClassification(classification);
        assertEquals(3, result.size());
        assertTrue(result.contains("id_0002"));
        assertTrue(result.contains("id_0015"));
        assertTrue(result.contains("id_0018"));
    }

    /**
     * Test of getResourceIdsByLanguage method, of class MetsPropertiesService.
     */
    @Test
    public void testGetResourceIdsByLanguage() {
        System.out.println("getResourceIdsByLanguage");
        String[] language = {"deu"};
        MetsPropertiesService instance = metsPropertiesService;
        List<String> result = instance.getResourceIdsByLanguage(language);
        assertEquals(4, result.size());
        assertTrue(result.contains("id_0002"));
        assertTrue(result.contains("id_0015"));
        assertTrue(result.contains("id_0016"));
        assertTrue(result.contains("id_0017"));
    }

    /**
     * Test of getResourceIdsByLanguage method, of class MetsPropertiesService.
     */
    @Test
    public void testGetResourceIdsByMultipleLanguage() {
        System.out.println("getResourceIdsByLanguage");
        String[] language = {"deu", "en"};
        MetsPropertiesService instance = metsPropertiesService;
        List<String> result = instance.getResourceIdsByLanguage(language);
        assertEquals(6, result.size());
    }

    /**
     * Test of getResourceIdsByIdentifier method, of class
     * MetsPropertiesService.
     */
    @Test
    public void testGetResourceIdsByIdentifier() {
        System.out.println("getResourceIdsByIdentifier");
        String identifier = "handle2";
        String type = "handle";
        MetsPropertiesService instance = metsPropertiesService;
        List<String> result = instance.getResourceIdsByIdentifier(identifier, type);
        assertEquals(1, result.size());
        assertEquals("id_0017", result.get(0));
    }

    /**
     * Test of getMetadataByResourceId method, of class MetsPropertiesService.
     */
    @Test
    public void testGetMetadataByResourceId() {
        System.out.println("getMetadataByResourceId");
        String resourceId = "id_3";
        MetsPropertiesService instance = metsPropertiesService;
        MetsProperties result = instance.getMetadataByResourceId(resourceId);
        assertEquals("Titel", result.getTitle());
        assertEquals("ppn3", result.getPpn());
    }

    /**
     * Test of getClassificationMetadataByResourceId method, of class
     * MetsPropertiesService.
     */
    @Test
    public void testGetClassificationMetadataByResourceId() {
        System.out.println("getClassificationMetadataByResourceId");
        String resourceId = "id_0015";
        MetsPropertiesService instance = metsPropertiesService;
        List<ClassificationMetadata> result = instance.getClassificationMetadataByResourceId(resourceId);
        assertEquals(4, result.size());
    }

    /**
     * Test of getLanguageMetadataByResourceId method, of class
     * MetsPropertiesService.
     */
    @Test
    public void testGetLanguageMetadataByResourceId() {
        System.out.println("getLanguageMetadataByResourceId");
        String resourceId = "id_0017";
        MetsPropertiesService instance = metsPropertiesService;
        List<LanguageMetadata> result = instance.getLanguageMetadataByResourceId(resourceId);
        assertEquals(2, result.size());
    }

    /**
     * Test of getGenreMetadataByResourceId method, of class
     * MetsPropertiesService.
     */
    @Test
    public void testGetGenreMetadataByResourceId() {
        System.out.println("getGenreMetadataByResourceId");
        String resourceId = "id_0017";
        MetsPropertiesService instance = metsPropertiesService;
        List<GenreMetadata> result = instance.getGenreMetadataByResourceId(resourceId);
        assertEquals(1, result.size());
        assertEquals("Krimi", result.get(0).getGenre());
    }

    /**
     * Test of getPageMetadataByResourceId method, of class
     * MetsPropertiesService.
     */
    @Test
    public void testGetPageMetadataByResourceId() {
        System.out.println("getPageMetadataByResourceId");
        String resourceId = "id_0019";
        MetsPropertiesService instance = metsPropertiesService;
        List<PageMetadata> result = instance.getPageMetadataByResourceId(resourceId);
        assertEquals(1, result.size());
        PageMetadata pm = result.get(0);
        assertEquals(resourceId, pm.getResourceId());
        assertEquals("1", pm.getOrder());
        assertEquals("phys_0001", pm.getPageId());
        assertEquals(GroundTruthProperties.LATIN, pm.getFeature());
    }

    /**
     * Test of getIdentifierByResourceId method, of class MetsPropertiesService.
     */
    @Test
    public void testGetIdentifierByResourceId() {
        System.out.println("getIdentifierByResourceId");
        String resourceId = "id_0017";
        MetsPropertiesService instance = metsPropertiesService;
        List<MetsIdentifier> result = instance.getIdentifierByResourceId(resourceId);
        assertEquals(2, result.size());
        for (MetsIdentifier mi : result) {
            if (mi.getType().equals("url")) {
                assertEquals("url2", mi.getIdentifier());
            } else {
                assertEquals("handle", mi.getType());
                assertEquals("handle2", mi.getIdentifier());
            }
        }
    }

    /**
     * Test of getIdentifierByResourceIdAndType method, of class
     * MetsPropertiesService.
     */
    @Test
    public void testGetIdentifierByResourceIdAndType() {
        System.out.println("getIdentifierByResourceIdAndType");
        String resourceId = "id_0002";
        String type = "url";
        MetsPropertiesService instance = metsPropertiesService;
        List<MetsIdentifier> expResult = null;
        List<MetsIdentifier> result = instance.getIdentifierByResourceIdAndType(resourceId, type);
        assertEquals(1, result.size());
        MetsIdentifier mi = result.get(0);
        assertEquals("url", mi.getType());
        assertEquals("url1", mi.getIdentifier());
    }
}
