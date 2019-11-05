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
import edu.kit.ocrd.workspace.entity.MetsFile;
import edu.kit.datamanager.metastore.repository.MetsFileRepository;
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
public class MetsFileServiceTest {

    @Autowired
    private ArangoOperations operations;

    /**
     * Repository persisting METS files.
     */
    @Autowired
    private MetsFileRepository metsFileRepository;

    MetsFileService metsFileService;

    public MetsFileServiceTest() {
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
        metsFileRepository.saveAll(CrudRunner.createMetsFiles());

        metsFileService = new MetsFileService();
        metsFileService.setMetsFileRepository(metsFileRepository);
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getAllPageIds method, of class MetsFileService.
     */
    @Test
    public void testGetAllPageIds() {
        System.out.println("getAllPageIds");
        String resourceId = "id_0002";
        MetsFileService instance = metsFileService;
        List<String> result = instance.getAllPageIds(resourceId);
        assertTrue(result.contains("PAGE-0001"));
        assertTrue(result.contains("PAGE-0002"));
        assertFalse(result.contains("PAGE-0003"));
    }

    /**
     * Test of getAllUses method, of class MetsFileService.
     */
    @Test
    public void testGetAllUses() {
        System.out.println("getAllUses");
        String resourceId = "id_0015";
        MetsFileService instance = metsFileService;
        List<String> expResult = null;
        List<String> result = instance.getAllUses(resourceId);
        assertTrue(result.contains("OCR-D-GT-IMG-BIN"));
        assertTrue(result.contains("OCR-D-GT-IMG-DESPEC"));
        assertTrue(result.contains("OCR-D-GT-IMG-CROP"));
        assertTrue(result.contains("OCR-D-GT-IMG-DEWARP"));
        resourceId = "id_0016";
        result = instance.getAllUses(resourceId);
        assertTrue(result.contains("OCR-D-GT-IMG-BIN"));
        assertFalse(result.contains("OCR-D-GT-IMG-DESPEC"));
        assertFalse(result.contains("OCR-D-GT-IMG-CROP"));
        assertFalse(result.contains("OCR-D-GT-IMG-DEWARP"));
    }

    /**
     * Test of getAvailableMetsFilesOfCurrentVersion method, of class
     * MetsFileService.
     */
    @Test
    public void testGetAvailableMetsFilesOfCurrentVersion() {
        System.out.println("getAvailableMetsFilesOfCurrentVersion");
        String resourceId = "id_0017";
        MetsFileService instance = metsFileService;
        List<MetsFile> result = instance.getAvailableMetsFilesOfCurrentVersion(resourceId);
        assertEquals(2, result.size());
        for (MetsFile metsFile : result) {
            if (metsFile.getPageId().contains("0001")) {
                assertEquals(resourceId, metsFile.getResourceId());
                assertTrue(metsFile.getCurrent());
                assertEquals(new Integer(1), metsFile.getVersion());
                assertEquals("PAGE-0001_IMG-CROP", metsFile.getFileId());
                assertEquals("image/png", metsFile.getMimetype());
                assertEquals("PAGE-0001", metsFile.getPageId());
                assertEquals("OCR-D-GT-IMG-CROP", metsFile.getUse());
                assertEquals("url17", metsFile.getUrl());
            } else {
                assertEquals(resourceId, metsFile.getResourceId());
                assertTrue(metsFile.getCurrent());
                assertEquals(new Integer(1), metsFile.getVersion());
                assertEquals("PAGE-0002_IMG-CROP", metsFile.getFileId());
                assertEquals("image/png", metsFile.getMimetype());
                assertEquals("PAGE-0002", metsFile.getPageId());
                assertEquals("OCR-D-GT-IMG-CROP", metsFile.getUse());
                assertEquals("url2_17", metsFile.getUrl());
            }
        }
    }

    /**
     * Test of getAvailableMetsFilesByUseAndPageId method, of class
     * MetsFileService.
     */
    @Test
    public void testGetAvailableMetsFilesByUse() {
        System.out.println("getAvailableMetsFilesByUseAndPageId");
        String resourceId = "id_0018";
        String[] use = {"OCR-D-GT-IMG-DESPEC"};
        String[] pageId = null;
        MetsFileService instance = metsFileService;
        List<MetsFile> result = instance.getAvailableMetsFilesByUseAndPageId(resourceId, use, pageId);
        assertEquals(2, result.size());
    }

    /**
     * Test of getAvailableMetsFilesByUseAndPageId method, of class
     * MetsFileService.
     */
    @Test
    public void testGetAvailableMetsFilesPageId() {
        System.out.println("getAvailableMetsFilesByUseAndPageId");
        String resourceId = "id_0018";
        String[] use = null;
        String[] pageId = {"PAGE-0001"};
        MetsFileService instance = metsFileService;
        List<MetsFile> result = instance.getAvailableMetsFilesByUseAndPageId(resourceId, use, pageId);
        assertEquals(1, result.size());
    }

    /**
     * Test of getAvailableMetsFilesByUseAndPageId method, of class
     * MetsFileService.
     */
    @Test
    public void testGetAvailableMetsFilesByUseAndPageId() {
        System.out.println("getAvailableMetsFilesByUseAndPageId");
        String resourceId = "id_0018";
        String[] use = {"OCR-D-GT-IMG-DESPEC"};
        String[] pageId = {"PAGE-0001"};
        MetsFileService instance = metsFileService;
        List<MetsFile> result = instance.getAvailableMetsFilesByUseAndPageId(resourceId, use, pageId);
        assertEquals(1, result.size());
        MetsFile metsFile = result.get(0);
        assertEquals(resourceId, metsFile.getResourceId());
        assertTrue(metsFile.getCurrent());
        assertEquals(new Integer(1), metsFile.getVersion());
        assertEquals("PAGE-0001_IMG-DESPEC", metsFile.getFileId());
        assertEquals("image/png", metsFile.getMimetype());
        assertEquals("PAGE-0001", metsFile.getPageId());
        assertEquals("OCR-D-GT-IMG-DESPEC", metsFile.getUse());
        assertEquals("url18", metsFile.getUrl());
    }

    /**
     * Test of getAvailableMetsFilesByUseAndPageId method, of class
     * MetsFileService.
     */
    @Test
    public void testGetAvailableMetsFilesByUseAndMultiplePageId() {
        System.out.println("getAvailableMetsFilesByUseAndMultiplePageId");
        String resourceId = "id_0015";
        String[] use = {"OCR-D-GT-IMG-DESPEC"};
        String[] pageId = {"PAGE-0001", "PAGE-0002"};
        MetsFileService instance = metsFileService;
        List<MetsFile> result = instance.getAvailableMetsFilesByUseAndPageId(resourceId, use, pageId);
        assertEquals(2, result.size());
        for (MetsFile metsFile : result) {
            if (metsFile.getPageId().contains("0001")) {
                assertEquals(resourceId, metsFile.getResourceId());
                assertTrue(metsFile.getCurrent());
                assertEquals(new Integer(1), metsFile.getVersion());
                assertEquals("PAGE-0001_IMG-DESPEC", metsFile.getFileId());
                assertEquals("image/png", metsFile.getMimetype());
                assertEquals(pageId[0], metsFile.getPageId());
                assertEquals(use[0], metsFile.getUse());
                assertEquals("url31", metsFile.getUrl());
            } else {
                assertEquals(resourceId, metsFile.getResourceId());
                assertTrue(metsFile.getCurrent());
                assertEquals(new Integer(1), metsFile.getVersion());
                assertEquals("PAGE-0002_IMG-DESPEC", metsFile.getFileId());
                assertEquals("image/png", metsFile.getMimetype());
                assertEquals(pageId[1], metsFile.getPageId());
                assertEquals(use[0], metsFile.getUse());
                assertEquals("url2_31", metsFile.getUrl());
            }
        }
    }

    /**
     * Test of getAvailableMetsFilesByUseAndPageId method, of class
     * MetsFileService.
     */
    @Test
    public void testGetAvailableMetsFilesByMultipleUseAndPageId() {
        System.out.println("getAvailableMetsFilesByMultipleUseAndPageId");
        String resourceId = "id_0015";
        String[] use = {"OCR-D-GT-IMG-BIN", "OCR-D-GT-IMG-DESPEC"};
        String[] pageId = {"PAGE-0001"};
        MetsFileService instance = metsFileService;
        List<MetsFile> result = instance.getAvailableMetsFilesByUseAndPageId(resourceId, use, pageId);
        assertEquals(2, result.size());
        for (MetsFile metsFile : result) {
            if (metsFile.getUse().contains("BIN")) {
                assertEquals(resourceId, metsFile.getResourceId());
                assertTrue(metsFile.getCurrent());
                assertEquals(new Integer(1), metsFile.getVersion());
                assertEquals("PAGE-0001_IMG_BIN", metsFile.getFileId());
                assertEquals("image/png", metsFile.getMimetype());
                assertEquals(pageId[0], metsFile.getPageId());
                assertEquals(use[0], metsFile.getUse());
                assertEquals("url11", metsFile.getUrl());
            } else {
                assertEquals(resourceId, metsFile.getResourceId());
                assertTrue(metsFile.getCurrent());
                assertEquals(new Integer(1), metsFile.getVersion());
                assertEquals("PAGE-0001_IMG-DESPEC", metsFile.getFileId());
                assertEquals("image/png", metsFile.getMimetype());
                assertEquals(pageId[0], metsFile.getPageId());
                assertEquals(use[1], metsFile.getUse());
                assertEquals("url31", metsFile.getUrl());
            }
        }
    }

    /**
     * Test of getAvailableMetsFilesByUseAndPageId method, of class
     * MetsFileService.
     */
    @Test
    public void testGetAvailableMetsFilesByMultipleUseAndMultiplePageId() {
        System.out.println("getAvailableMetsFilesByMultipleUseAndMultiplePageId");
        String resourceId = "id_0015";
        String[] use = {"OCR-D-GT-IMG-BIN", "OCR-D-GT-IMG-DESPEC"};
        String[] pageId = {"PAGE-0001", "PAGE-0002"};
        MetsFileService instance = metsFileService;
        List<MetsFile> result = instance.getAvailableMetsFilesByUseAndPageId(resourceId, use, pageId);
        assertEquals(4, result.size());
    }

    /**
     * Test of getAvailableMetsFilesByFileIds method, of class MetsFileService.
     */
    @Test
    public void testGetAvailableMetsFilesByFileIds() {
        System.out.println("getAvailableMetsFilesByFileIds");
        String resourceId = "id_0019";
        String[] fileId = {"PAGE-0002_IMG-DEWARP"};
        MetsFileService instance = metsFileService;
        List<MetsFile> result = instance.getAvailableMetsFilesByFileIds(resourceId, fileId);
        assertEquals(1, result.size());
        MetsFile metsFile = result.get(0);
        assertEquals(resourceId, metsFile.getResourceId());
        assertTrue(metsFile.getCurrent());
        assertEquals(new Integer(1), metsFile.getVersion());
        assertEquals(fileId[0], metsFile.getFileId());
        assertEquals("image/png", metsFile.getMimetype());
        assertEquals("PAGE-0002", metsFile.getPageId());
        assertEquals("OCR-D-GT-IMG-DEWARP", metsFile.getUse());
        assertEquals("url2_19", metsFile.getUrl());
    }

    /**
     * Test of getAvailableMetsFilesByFileIds method, of class MetsFileService.
     */
    @Test
    public void testGetAvailableMetsFilesByMultipleFileIds() {
        System.out.println("getAvailableMetsFilesByMultipleFileIds");
        String resourceId = "id_0019";
        String[] fileId = {"PAGE-0001_IMG-DEWARP", "PAGE-0002_IMG-DEWARP"};
        MetsFileService instance = metsFileService;
        List<MetsFile> result = instance.getAvailableMetsFilesByFileIds(resourceId, fileId);
        assertEquals(2, result.size());
        MetsFile metsFile = result.get(0);
        if (metsFile.getPageId().contains("0001")) {
            assertEquals(resourceId, metsFile.getResourceId());
            assertTrue(metsFile.getCurrent());
            assertEquals(new Integer(1), metsFile.getVersion());
            assertEquals(fileId[0], metsFile.getFileId());
            assertEquals("image/png", metsFile.getMimetype());
            assertEquals("PAGE-0001", metsFile.getPageId());
            assertEquals("OCR-D-GT-IMG-DEWARP", metsFile.getUse());
            assertEquals("url19", metsFile.getUrl());
        } else {
            assertEquals(resourceId, metsFile.getResourceId());
            assertTrue(metsFile.getCurrent());
            assertEquals(new Integer(1), metsFile.getVersion());
            assertEquals(fileId[1], metsFile.getFileId());
            assertEquals("image/png", metsFile.getMimetype());
            assertEquals("PAGE-0002", metsFile.getPageId());
            assertEquals("OCR-D-GT-IMG-DEWARP", metsFile.getUse());
            assertEquals("url2_19", metsFile.getUrl());
        }
    }

}
