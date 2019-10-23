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
import edu.kit.datamanager.metastore.entity.ZippedBagit;
import edu.kit.datamanager.metastore.repository.ZippedBagitRepository;
import edu.kit.datamanager.metastore.runner.CrudRunner;
import java.util.Date;
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
public class ZippedBagitServiceTest {

    @Autowired
    private ArangoOperations operations;

    @Autowired
    private ZippedBagitRepository zippedBagitRepository;

    private ZippedBagitService bagitService;

    private Date before;

    private Date after;

    public ZippedBagitServiceTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        dropDatabase();
        before = new Date();
        zippedBagitRepository.saveAll(CrudRunner.createZippedBagits());
        after = new Date();

        bagitService = new ZippedBagitService();
        bagitService.setBagitRepository(zippedBagitRepository);
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getAllLatestZippedBagits method, of class ZippedBagitService.
     */
    @Test
    public void testGetAllLatestZippedBagits() {
        System.out.println("testGetAllLatestZippedBagits");
        ZippedBagitService instance = bagitService;
        String resourceId = "resource_0010";
        String ocrdIdentifier = "id_0002";
        String url = "url10";
        Integer expectedVersion = 15;
        List<ZippedBagit> mi = instance.getAllLatestZippedBagits();
        assertEquals(6, mi.size());
        ZippedBagit firstResult = mi.get(0);
        assertEquals(resourceId, firstResult.getResourceId());
        assertEquals(ocrdIdentifier, firstResult.getOcrdIdentifier());
        assertEquals(url, firstResult.getUrl());
        assertEquals(expectedVersion, firstResult.getVersion());
        assertTrue(firstResult.getUploadDate().compareTo(before) >= 0);
        assertTrue(firstResult.getUploadDate().compareTo(after) <= 0);
        assertTrue(firstResult.getLatest());
    }

    /**
     * Test of getAllLatestZippedBagits method, of class ZippedBagitService.
     */
    @Test
    public void testGetAllLatestZippedBagitsOfEmptyDatabase() {
        System.out.println("testGetAllLatestZippedBagitsOfEmptyDatabase");
        dropDatabase();
        ZippedBagitService instance = bagitService;
        List<ZippedBagit> mi = instance.getAllLatestZippedBagits();
        assertEquals(0, mi.size());
    }

    /**
     * Test of getAllLatestZippedBagits method, of class ZippedBagitService.
     */
    @Test
    public void testSaveBagitContainer() {
        System.out.println("saveBagitContainer");
        ZippedBagitService instance = bagitService;
        List<ZippedBagit> result = instance.getAllLatestZippedBagits();
        assertEquals(6, result.size());
        instance.save(new ZippedBagit("newid", "newocrdidentifier", "newurl"));
        result = instance.getAllLatestZippedBagits();
        assertEquals(7, result.size());
        ZippedBagit buffer = result.get(4);
        instance.save(buffer.updateZippedBagit("updateid", "updateurl"));
        instance.save(buffer);
        result = instance.getAllLatestZippedBagits();
        assertEquals(7, result.size());
    }

    /**
     * Test of getMostRecentZippedBagitByOcrdIdentifier method, of class
     * ZippedBagitService.
     */
    @Test
    public void testGetMostRecentBagitByOcrdIdentifier() {
        System.out.println("testGetMostRecentBagitByOcrdIdentifier");
        ZippedBagitService instance = bagitService;
        String resourceId = "resource_0007";
        String ocrdIdentifier = "id_0017";
        String url = "url7";
        Integer expectedVersion = 2;
        ZippedBagit mi = instance.getMostRecentZippedBagitByOcrdIdentifier(ocrdIdentifier);
        assertEquals(resourceId, mi.getResourceId());
        assertEquals(ocrdIdentifier, mi.getOcrdIdentifier());
        assertEquals(url, mi.getUrl());
        assertEquals(expectedVersion, mi.getVersion());
        assertTrue(mi.getUploadDate().compareTo(before) >= 0);
        assertTrue(mi.getUploadDate().compareTo(after) <= 0);
        assertTrue(mi.getLatest());
    }

    /**
     * Test of getMostRecentZippedBagitByOcrdIdentifier method, of class
     * ZippedBagitService.
     */
    @Test
    public void testGetMostRecentBagitByInvalidOcrdIdentifier() {
        System.out.println("testGetMostRecentBagitByInvalidOcrdIdentifier");
        ZippedBagitService instance = bagitService;
        String ocrdIdentifier = "unknownOcrdIdentifier";
        ZippedBagit mi = instance.getMostRecentZippedBagitByOcrdIdentifier(ocrdIdentifier);
        assertNull(mi);
    }

    /**
     * Test of getMostRecentZippedBagitByOcrdIdentifier method, of class
     * ZippedBagitService.
     */
    @Test
    public void testGetMostRecentBagitByInvalidOcrdIdentifierOfEmptyDatabase() {
        System.out.println("testGetMostRecentBagitByInvalidOcrdIdentifierOfEmptyDatabase");
        dropDatabase();
        ZippedBagitService instance = bagitService;
        String ocrdIdentifier = "unknownOcrdIdentifier";
        ZippedBagit mi = instance.getMostRecentZippedBagitByOcrdIdentifier(ocrdIdentifier);
        assertNull(mi);
    }

    /**
     * Test of getZippedBagitByOcrdIdentifierAndVersion method, of class
     * ZippedBagitService.
     */
    @Test
    public void testGetAllVersionsOfOcrdIdentifier() {
        System.out.println("testGetAllVersionsOfOcrdIdentifier");
        ZippedBagitService instance = bagitService;
        String resourceId = "resource_0010";
        String ocrdIdentifier = "id_0002";
        String url = "url10";
        Integer expectedVersion = 15;
        String firstResourceId = "resource_0001";
        String firstUrl = "url1";
        Integer firstExpectedVersion = 1;
        List<ZippedBagit> mi = instance.getZippedBagitsByOcrdIdentifierOrderByVersionDesc(ocrdIdentifier);
        assertEquals(4, mi.size());
        ZippedBagit lastVersion = mi.get(0);
        assertEquals(resourceId, lastVersion.getResourceId());
        assertEquals(ocrdIdentifier, lastVersion.getOcrdIdentifier());
        assertEquals(url, lastVersion.getUrl());
        assertEquals(expectedVersion, lastVersion.getVersion());
        assertTrue(lastVersion.getUploadDate().compareTo(before) >= 0);
        assertTrue(lastVersion.getUploadDate().compareTo(after) <= 0);
        assertTrue(lastVersion.getLatest());
        ZippedBagit firstVersion = mi.get(3);
        assertEquals(firstResourceId, firstVersion.getResourceId());
        assertEquals(ocrdIdentifier, firstVersion.getOcrdIdentifier());
        assertEquals(firstUrl, firstVersion.getUrl());
        assertEquals(firstExpectedVersion, firstVersion.getVersion());
        assertTrue(firstVersion.getUploadDate().compareTo(before) >= 0);
        assertTrue(firstVersion.getUploadDate().compareTo(after) <= 0);
        assertFalse(firstVersion.getLatest());
    }

    /**
     * Test of getZippedBagitByOcrdIdentifierAndVersion method, of class
     * ZippedBagitService.
     */
    @Test
    public void testGetAllVersionsOfInvalidOcrdIdentifier() {
        System.out.println("testGetAllVersionsOfInvalidOcrdIdentifier");
        ZippedBagitService instance = bagitService;
        String ocrdIdentifier = "unknownOcrdIdentifier";
        List<ZippedBagit> mi = instance.getZippedBagitsByOcrdIdentifierOrderByVersionDesc(ocrdIdentifier);
        assertEquals(0, mi.size());
    }

    /**
     * Test of getZippedBagitByOcrdIdentifierAndVersion method, of class
     * ZippedBagitService.
     */
    @Test
    public void testGetAllVersionsOfOcrdIdentifierOfEmptyDatabase() {
        System.out.println("testGetAllVersionsOfOcrdIdentifierOfEmptyDatabase");
        dropDatabase();
        ZippedBagitService instance = bagitService;
        String ocrdIdentifier = "unknownOcrdIdentifier";
        List<ZippedBagit> mi = instance.getZippedBagitsByOcrdIdentifierOrderByVersionDesc(ocrdIdentifier);
        assertEquals(0, mi.size());
    }

    /**
     * Test of getZippedBagitByOcrdIdentifierAndVersion method, of class
     * ZippedBagitService.
     */
    @Test
    public void testGetOcrdIdentifierAndVersion() {
        System.out.println("testGetOcrdIdentifierAndVersion");
        ZippedBagitService instance = bagitService;
        String resourceId = "resource_0003";
        String ocrdIdentifier = "id_0002";
        String url = "url3";
        Integer expectedVersion = 3;
        ZippedBagit mi = instance.getZippedBagitByOcrdIdentifierAndVersion(ocrdIdentifier, expectedVersion);
        assertEquals(resourceId, mi.getResourceId());
        assertEquals(ocrdIdentifier, mi.getOcrdIdentifier());
        assertEquals(url, mi.getUrl());
        assertEquals(expectedVersion, mi.getVersion());
        assertTrue(mi.getUploadDate().compareTo(before) >= 0);
        assertTrue(mi.getUploadDate().compareTo(after) <= 0);
        assertFalse(mi.getLatest());
    }

    /**
     * Test of getZippedBagitByOcrdIdentifierAndVersion method, of class
     * ZippedBagitService.
     */
    @Test
    public void testInvalidGetOcrdIdentifierAndVersion() {
        System.out.println("testInvalidGetOcrdIdentifierAndVersion");
        ZippedBagitService instance = bagitService;
        String ocrdIdentifier = "id_0002";
        String unknownOcrdIdentifier = "unknownOcrdIdentifier";
        Integer expectedVersion = 4;
        ZippedBagit mi = instance.getZippedBagitByOcrdIdentifierAndVersion(ocrdIdentifier, expectedVersion);
        assertNull(mi);
        mi = instance.getZippedBagitByOcrdIdentifierAndVersion(unknownOcrdIdentifier, Integer.valueOf(1));
        assertNull(mi);
    }

    /**
     * Test of getZippedBagitByOcrdIdentifierAndVersion method, of class
     * ZippedBagitService.
     */
    @Test
    public void testGetOcrdIdentifierAndVersionOfEmptyDatabase() {
        System.out.println("testGetOcrdIdentifierAndVersionOfEmptyDatabase");
        dropDatabase();
        ZippedBagitService instance = bagitService;
        String ocrdIdentifier = "id_0002";
        Integer expectedVersion = 4;
        ZippedBagit mi = instance.getZippedBagitByOcrdIdentifierAndVersion(ocrdIdentifier, expectedVersion);
        assertNull(mi);
    }

    /**
     * Test of getZippedBagitByResourceId method, of class ZippedBagitService.
     */
    @Test
    public void testGetZippedBagitByResourceId() {
        System.out.println("testGetZippedBagitByResourceId");
        ZippedBagitService instance = bagitService;
        String resourceId = "resource_0001";
        String ocrdIdentifier = "id_0002";
        String url = "url1";
        Integer expectedVersion = 1;
        ZippedBagit mi = instance.getZippedBagitByResourceId(resourceId);
        assertEquals(resourceId, mi.getResourceId());
        assertEquals(ocrdIdentifier, mi.getOcrdIdentifier());
        assertEquals(url, mi.getUrl());
        assertEquals(expectedVersion, mi.getVersion());
        assertTrue(mi.getUploadDate().compareTo(before) >= 0);
        assertTrue(mi.getUploadDate().compareTo(after) <= 0);
        assertFalse(mi.getLatest());
    }

    /**
     * Test of getZippedBagitByResourceId method, of class ZippedBagitService.
     */
    @Test
    public void testGetZippedBagitByInvalidResourceId() {
        System.out.println("testGetZippedBagitByInvalidResourceId");
        ZippedBagitService instance = bagitService;
        String resourceId = "unknownResourceId";
        ZippedBagit mi = instance.getZippedBagitByResourceId(resourceId);
        assertNull(mi);
    }

    /**
     * Test of getZippedBagitByResourceId method, of class ZippedBagitService.
     */
    @Test
    public void testGetZippedBagitByResourceIdOfEmptyDatabase() {
        System.out.println("testGetZippedBagitByResourceIdOfEmptyDatabase");
        dropDatabase();
        ZippedBagitService instance = bagitService;
        String resourceId = "unknownResourceId";
        ZippedBagit mi = instance.getZippedBagitByResourceId(resourceId);
        assertNull(mi);
    }

    /**
     * Test of getAllVersionsByOcrdIdentifier method, of class
     * ZippedBagitService.
     */
    @Test
    public void testGetAllVersionsByOcrdIdentifier() {
        System.out.println("testGetAllVersionsByOcrdIdentifier");
        ZippedBagitService instance = bagitService;
        String ocrdIdentifier = "id_0002";
        List<Integer> mi = instance.getAllVersionsByOcrdIdentifier(ocrdIdentifier);
        assertEquals(4, mi.size());
        assertTrue(mi.contains(Integer.valueOf(1)));
        assertTrue(mi.contains(Integer.valueOf(2)));
        assertTrue(mi.contains(Integer.valueOf(3)));
        assertTrue(mi.contains(Integer.valueOf(15)));
    }

    /**
     * Test of getAllVersionsByOcrdIdentifier method, of class
     * ZippedBagitService.
     */
    @Test
    public void testGetAllVersionsByInvalidOcrdIdentifier() {
        System.out.println("testGetAllVersionsByOcrdIdentifier");
        ZippedBagitService instance = bagitService;
        String ocrdIdentifier = "unknownOcrdIdentifier";
        List<Integer> mi = instance.getAllVersionsByOcrdIdentifier(ocrdIdentifier);
        assertEquals(0, mi.size());
    }

    /**
     * Test of getAllVersionsByOcrdIdentifier method, of class
     * ZippedBagitService.
     */
    @Test
    public void testGetAllVersionsByOcrdIdentifierOfEmptyDatabase() {
        System.out.println("testGetAllVersionsByOcrdIdentifier");
        dropDatabase();
        ZippedBagitService instance = bagitService;
        String ocrdIdentifier = "id_0002";
        List<Integer> mi = instance.getAllVersionsByOcrdIdentifier(ocrdIdentifier);
        assertEquals(0, mi.size());
    }

    private void dropDatabase() {
        try {
            operations.dropDatabase();
        } catch (DataAccessException dae) {
            System.out.println("This message should be printed only once!");
            System.out.println(dae.toString());
        }
    }

}
