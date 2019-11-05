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
import edu.kit.ocrd.workspace.entity.MetsDocument;
import edu.kit.datamanager.metastore.exception.InvalidFormatException;
import edu.kit.datamanager.metastore.exception.ResourceAlreadyExistsException;
import edu.kit.datamanager.metastore.repository.ClassificationMetadataRepository;
import edu.kit.datamanager.metastore.repository.GenreMetadataRepository;
import edu.kit.datamanager.metastore.repository.LanguageMetadataRepository;
import edu.kit.datamanager.metastore.repository.MetsDocumentRepository;
import edu.kit.datamanager.metastore.repository.MetsFileRepository;
import edu.kit.datamanager.metastore.repository.MetsIdentifierRepository;
import edu.kit.datamanager.metastore.repository.MetsPropertiesRepository;
import edu.kit.datamanager.metastore.repository.PageMetadataRepository;
import edu.kit.datamanager.metastore.runner.CrudRunner;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class MetsDocumentServiceTest {

    @Autowired
    private ArangoOperations operations;

    @Autowired
    private MetsDocumentRepository repository;

    /**
     * Repository persisting METS files.
     */
    @Autowired
    private MetsFileRepository metsFileRepository;

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

    private Date startDate;

    private MetsDocumentService metsDocumentService;

    public MetsDocumentServiceTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        startDate = new Date();
        try {
            operations.dropDatabase();
        } catch (DataAccessException dae) {
            System.out.println("This message should be printed only once!");
            System.out.println(dae.toString());
        }
        repository.saveAll(CrudRunner.createMetsDocuments());
        metsDocumentService = new MetsDocumentService();
        metsDocumentService.setMetsDocumentRepository(repository);
        metsDocumentService.setClassificationMetadataRepository(classificationMetadataRepository);
        metsDocumentService.setGenreMetadataRepository(genreMetadataRepository);
        metsDocumentService.setLanguageMetadataRepository(languageMetadataRepository);
        metsDocumentService.setMetsFileRepository(metsFileRepository);
        metsDocumentService.setMetsIdentifierRepository(metsIdentifierRepository);
        metsDocumentService.setMetsPropertiesRepository(metsPropertiesRepository);
        metsDocumentService.setPageMetadataRepository(pageMetadataRepository);
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getAllDocuments method, of class MetsDocumentService.
     */
    @Test
    public void testGetAllDocuments() {
        System.out.println("getAllDocuments");
        MetsDocumentService instance = metsDocumentService;
        List<MetsDocument> result = instance.getAllDocuments();
        assertEquals(22, result.size());
    }

    /**
     * Test of getMostRecentMetsDocumentByResourceId method, of class
     * MetsDocumentService.
     */
    @Test
    public void testGetMostRecentMetsDocumentByResourceId() {
        System.out.println("getMostRecentMetsDocumentByResourceId");
        String resourceId = "id_0002";
        MetsDocumentService instance = metsDocumentService;
        MetsDocument expResult = null;
        MetsDocument result = instance.getMostRecentMetsDocumentByResourceId(resourceId);
        assertEquals(resourceId, result.getResourceId());
        assertEquals("stark", result.getMetsContent());
        assertEquals(true, result.getCurrent());
        Date now = new Date();
        assertEquals(true, result.getLastModified().after(startDate));
        assertEquals(true, result.getLastModified().before(now));
        assertEquals(new Integer(4), result.getVersion());
    }

    /**
     * Test of getAllVersionsByResourceId method, of class MetsDocumentService.
     */
    @Test
    public void testGetAllVersionsByResourceId() {
        System.out.println("getAllVersionsByResourceId");
        String resourceId = "id_0002";
        MetsDocumentService instance = metsDocumentService;
        List<Integer> result = instance.getAllVersionsByResourceId(resourceId);
        assertEquals(4, result.size());
        assertTrue(result.contains(1));
        assertTrue(result.contains(2));
        assertTrue(result.contains(3));
        assertTrue(result.contains(4));
        assertFalse(result.contains(5));
    }

    /**
     * Test of getDocumentByResourceIdAndVersion method, of class
     * MetsDocumentService.
     */
    @Test
    public void testGetDocumentByResourceIdAndVersion() {
        System.out.println("getDocumentByResourceIdAndVersion");
        String resourceId = "id_0002";
        Integer version = 2;
        MetsDocumentService instance = metsDocumentService;
        MetsDocument expResult = null;
        MetsDocument result = instance.getDocumentByResourceIdAndVersion(resourceId, version);
        assertEquals(resourceId, result.getResourceId());
        assertEquals("noch staerker", result.getMetsContent());
        assertEquals(false, result.getCurrent());
        Date now = new Date();
        assertEquals(true, result.getLastModified().after(startDate));
        assertEquals(true, result.getLastModified().before(now));
        assertEquals(version, result.getVersion());
        MetsDocument result2 = instance.getMostRecentMetsDocumentByResourceId(resourceId);
        assertTrue(result2.getLastModified().getTime() >= result.getLastModified().getTime());
    }

    /**
     * Test of createMetsDocument method, of class MetsDocumentService.
     *
     * @throws java.io.IOException
     */
    @Test
    public void testCreateInvalidMetsDocument() throws IOException {
        System.out.println("createMetsDocument");
        String resourceId = "newResourceId";
        String fileContent = "<Invalid></xml>";
        MetsDocumentService instance = metsDocumentService;
        int noOfDocuments = instance.getAllDocuments().size();
        try {
            instance.createMetsDocument(resourceId, fileContent);
        } catch (InvalidFormatException ife) {
            assertEquals("Invalid METS file!", ife.getMessage());
        }
        int noOfDocumentsAfter = instance.getAllDocuments().size();
        assertTrue(noOfDocuments == noOfDocumentsAfter);
    }

    /**
     * Test of createMetsDocument method, of class MetsDocumentService.
     *
     * @throws java.io.IOException
     */
    @Test
    public void testCreateMetsDocument() throws IOException {
        System.out.println("createMetsDocument");
        String resourceId = "newResourceId";
        String fileContent = FileUtils.readFileToString(new File("src/test/resources/mets/validMets_newFormat.xml"), "UTF-8");
        MetsDocumentService instance = metsDocumentService;
        int noOfDocuments = instance.getAllDocuments().size();
        instance.createMetsDocument(resourceId, fileContent);
        int noOfDocumentsAfter = instance.getAllDocuments().size();
        assertTrue(noOfDocuments + 1 == noOfDocumentsAfter);
        MetsDocument result = instance.getMostRecentMetsDocumentByResourceId(resourceId);
        assertEquals(resourceId, result.getResourceId());
        assertEquals(fileContent, result.getMetsContent());
        assertEquals(true, result.getCurrent());
        assertEquals(new Integer(1), result.getVersion());
    }

    /**
     * Test of createMetsDocument method, of class MetsDocumentService.
     *
     * @throws java.io.IOException
     */
    @Test
    public void createMetsDocumentWithSameResourceId() throws IOException {
        System.out.println("createMetsDocumentWithSameResourceId");
        String resourceId = "newResourceId";
        String fileContent = FileUtils.readFileToString(new File("src/test/resources/mets/validMets_newFormat.xml"), "UTF-8");
        MetsDocumentService instance = metsDocumentService;
        try {
            instance.createMetsDocument(resourceId, fileContent);
            instance.createMetsDocument(resourceId, fileContent);
            assertFalse("Try to store same same resourceID", true);
        } catch (ResourceAlreadyExistsException raee) {
            assertTrue("Resource already exists", true);
        }
    }

    /**
     * Test of updateMetsDocument method, of class MetsDocumentService.
     */
    @Test
    public void testUpdateMetsDocument() {
        System.out.println("updateMetsDocument");
        String resourceId = "id_0022";
        String fileContent = "new content";
        MetsDocumentService instance = metsDocumentService;
        try {
            instance.updateMetsDocument(resourceId, fileContent);
            assertTrue("Please update according test", false);
            MetsDocument result = instance.getMostRecentMetsDocumentByResourceId(resourceId);
            assertEquals(resourceId, result.getResourceId());
            assertEquals(fileContent, result.getMetsContent());
            assertEquals(true, result.getCurrent());
            assertEquals(new Integer(2), result.getVersion());
        } catch (UnsupportedOperationException uoe) {
            assertTrue("Not supported yet!", true);
        }
    }

    /**
     * Test of updateMetsDocument method, of class MetsDocumentService.
     */
    @Test
    public void testUpdateMetsDocument2() {
        System.out.println("updateMetsDocument2");
        String resourceId = "id_0002";
        String fileContent = "new content";
        MetsDocumentService instance = metsDocumentService;
        try {
            instance.updateMetsDocument(resourceId, fileContent);
            assertTrue("Please update according test", false);
            MetsDocument result = instance.getMostRecentMetsDocumentByResourceId(resourceId);
            assertEquals(resourceId, result.getResourceId());
            assertEquals(fileContent, result.getMetsContent());
            assertEquals(true, result.getCurrent());
            assertEquals(new Integer(5), result.getVersion());
        } catch (UnsupportedOperationException uoe) {
            assertTrue("Not supported yet!", true);
        }
    }

}
