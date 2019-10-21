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
package edu.kit.datamanager.metastore.repository;

import com.arangodb.springframework.core.ArangoOperations;
import edu.kit.datamanager.metastore.entity.ZippedBagit;
import edu.kit.datamanager.metastore.runner.CrudRunner;
import edu.kit.datamanager.util.AuthenticationHelper;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

/**
 *
 */
@RunWith(SpringRunner.class)
@PowerMockRunnerDelegate(SpringJUnit4ClassRunner.class)
@PowerMockIgnore({"javax.crypto.*", "javax.management.*"})
@PrepareForTest(AuthenticationHelper.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestExecutionListeners(listeners = {
    DependencyInjectionTestExecutionListener.class,
    TransactionalTestExecutionListener.class
})
public class ZippedBagitRepositoryTest {

    @Autowired
    private ArangoOperations operations;
    /**
     * Repository persisting METS identifiers.
     */
    @Autowired
    private ZippedBagitRepository zippedBagitRepository;

    private Date before;

    private Date after;

    public ZippedBagitRepositoryTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        before = new Date();
        try {
            operations.dropDatabase();
        } catch (DataAccessException dae) {
            System.out.println("This message should be printed only once!");
            System.out.println(dae.toString());
        }
        System.out.println("*******************************          Identifier  Metadata         ***************************************************");
        Collection<ZippedBagit> zippedBagitList = CrudRunner.createZippedBagits();
        for (ZippedBagit zippedBagit : zippedBagitList) {
            zippedBagitRepository.save(zippedBagit);
        }
        after = new Date();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testCountDataSets() {
        System.out.println("repo.count()");
        assertEquals(10, zippedBagitRepository.count());
    }

    /**
     * Test of findByResourceId method, of class ZippedBagitRepository.
     */
    @Test
    public void testFindByResourceId() {
        System.out.println("findByResourceId");
        String resourceId = "resource_0004";
        Integer expectedVersion = 1;
        ZippedBagitRepository instance = zippedBagitRepository;
        Iterator<ZippedBagit> result = instance.findByResourceId(resourceId).iterator();
        ZippedBagit mi = result.next();
        assertFalse(result.hasNext());
        assertEquals(resourceId, mi.getResourceId());
        assertEquals("id_0015", mi.getOcrdIdentifier());
        assertEquals("url4", mi.getUrl());
        assertEquals(expectedVersion, mi.getVersion());
        assertTrue(mi.getUploadDate().compareTo(before) >= 0);
        assertTrue(mi.getUploadDate().compareTo(after) <= 0);
        assertTrue(mi.getLatest());
    }
    /**
     * Test of findByResourceId method, of class ZippedBagitRepository.
     */
    @Test
    public void testFindByResourceIdWithNewerVersion() {
        System.out.println("findByResourceIdWithNewerVersion");
        String resourceId = "resource_0002";
        Integer expectedVersion = 2;
        ZippedBagitRepository instance = zippedBagitRepository;
        Iterator<ZippedBagit> result = instance.findByResourceId(resourceId).iterator();
        ZippedBagit mi = result.next();
        assertFalse(result.hasNext());
        assertEquals(resourceId, mi.getResourceId());
        assertEquals("id_0002", mi.getOcrdIdentifier());
        assertEquals("url2", mi.getUrl());
        assertEquals(expectedVersion, mi.getVersion());
        assertTrue(mi.getUploadDate().compareTo(before) >= 0);
        assertTrue(mi.getUploadDate().compareTo(after) <= 0);
        assertFalse(mi.getLatest());
    }

    /**
     * Test of findByResourceId method, of class ZippedBagitRepository.
     */
    @Test
    public void testFindLatestOrderByUpload() {
        System.out.println("findByLatestOrderByUpload");
        Date date;
        Integer expectedVersion;
        String resourceId;
        String ocrdIdentifier;
        String url;
        
        ZippedBagitRepository instance = zippedBagitRepository;
        Iterator<ZippedBagit> result = instance.findByLatestTrueOrderByUploadDateDesc().iterator();
        date = after;
        resourceId = "resource_0010";
        ocrdIdentifier = "id_0002";
        url = "url10";
        expectedVersion = 15;
        ZippedBagit mi = result.next();
        assertTrue(result.hasNext());
        assertEquals(resourceId, mi.getResourceId());
        assertEquals(ocrdIdentifier, mi.getOcrdIdentifier());
        assertEquals(url, mi.getUrl());
        assertEquals(expectedVersion, mi.getVersion());
        assertTrue(mi.getUploadDate().compareTo(before) >= 0);
        assertTrue(mi.getUploadDate().compareTo(after) <= 0);
        assertTrue(mi.getUploadDate().compareTo(date) <= 0);
        assertTrue(mi.getLatest());
        
        date = mi.getUploadDate();
        resourceId = "resource_0009";
        ocrdIdentifier = "id_0019";
        url = "url9";
        expectedVersion = 1;
        mi = result.next();
        assertTrue(result.hasNext());
        assertEquals(resourceId, mi.getResourceId());
        assertEquals(ocrdIdentifier, mi.getOcrdIdentifier());
        assertEquals(url, mi.getUrl());
        assertEquals(expectedVersion, mi.getVersion());
        assertTrue(mi.getUploadDate().compareTo(before) >= 0);
        assertTrue(mi.getUploadDate().compareTo(after) <= 0);
        assertTrue(mi.getUploadDate().compareTo(date) <= 0);
        assertTrue(mi.getLatest());
        
        date = mi.getUploadDate();
        resourceId = "resource_0008";
        ocrdIdentifier = "id_0018";
        url = "url8";
        expectedVersion = 1;
        mi = result.next();
        assertTrue(result.hasNext());
        assertEquals(resourceId, mi.getResourceId());
        assertEquals(ocrdIdentifier, mi.getOcrdIdentifier());
        assertEquals(url, mi.getUrl());
        assertEquals(expectedVersion, mi.getVersion());
        assertTrue(mi.getUploadDate().compareTo(before) >= 0);
        assertTrue(mi.getUploadDate().compareTo(after) <= 0);
        assertTrue(mi.getUploadDate().compareTo(date) <= 0);
        assertTrue(mi.getLatest());
        
        date = mi.getUploadDate();
        resourceId = "resource_0007";
        ocrdIdentifier = "id_0017";
        url = "url7";
        expectedVersion = 2;
        mi = result.next();
        assertTrue(result.hasNext());
        assertEquals(resourceId, mi.getResourceId());
        assertEquals(ocrdIdentifier, mi.getOcrdIdentifier());
        assertEquals(url, mi.getUrl());
        assertEquals(expectedVersion, mi.getVersion());
        assertTrue(mi.getUploadDate().compareTo(before) >= 0);
        assertTrue(mi.getUploadDate().compareTo(after) <= 0);
        assertTrue(mi.getUploadDate().compareTo(date) <= 0);
        assertTrue(mi.getLatest());
        
        date = mi.getUploadDate();
        resourceId = "resource_0005";
        ocrdIdentifier = "id_0016";
        url = "url5";
        expectedVersion = 1;
        mi = result.next();
        assertTrue(result.hasNext());
        assertEquals(resourceId, mi.getResourceId());
        assertEquals(ocrdIdentifier, mi.getOcrdIdentifier());
        assertEquals(url, mi.getUrl());
        assertEquals(expectedVersion, mi.getVersion());
        assertTrue(mi.getUploadDate().compareTo(before) >= 0);
        assertTrue(mi.getUploadDate().compareTo(after) <= 0);
        assertTrue(mi.getUploadDate().compareTo(date) <= 0);
        assertTrue(mi.getLatest());
        
        date = mi.getUploadDate();
        resourceId = "resource_0004";
        ocrdIdentifier = "id_0015";
        url = "url4";
        expectedVersion = 1;
        mi = result.next();
        assertFalse(result.hasNext());
        assertEquals(resourceId, mi.getResourceId());
        assertEquals(ocrdIdentifier, mi.getOcrdIdentifier());
        assertEquals(url, mi.getUrl());
        assertEquals(expectedVersion, mi.getVersion());
        assertTrue(mi.getUploadDate().compareTo(before) >= 0);
        assertTrue(mi.getUploadDate().compareTo(after) <= 0);
        assertTrue(mi.getUploadDate().compareTo(date) <= 0);
        assertTrue(mi.getLatest());
    }


    /**
     * Test of findByResourceId method, of class ZippedBagitRepository.
     */
    @Test
    public void testFindByOcrdIdentifierOrderByVersion() {
        System.out.println("findByOcrdIdentifierOrderByVersion");
        Date date;
        Integer expectedVersion;
        String resourceId;
        String ocrdIdentifier = "id_0002";
        String url;
        
        ZippedBagitRepository instance = zippedBagitRepository;
        Iterator<ZippedBagit> result = instance.findByOcrdIdentifierOrderByVersionDesc(ocrdIdentifier).iterator();
        date = after;
        resourceId = "resource_0010";
        ocrdIdentifier = "id_0002";
        url = "url10";
        expectedVersion = 15;
        ZippedBagit mi = result.next();
        assertTrue(result.hasNext());
        assertEquals(resourceId, mi.getResourceId());
        assertEquals(ocrdIdentifier, mi.getOcrdIdentifier());
        assertEquals(url, mi.getUrl());
        assertEquals(expectedVersion, mi.getVersion());
        assertTrue(mi.getUploadDate().compareTo(before) >= 0);
        assertTrue(mi.getUploadDate().compareTo(after) <= 0);
        assertTrue(mi.getUploadDate().compareTo(date) <= 0);
        assertTrue(mi.getLatest());
        
        date = mi.getUploadDate();
        resourceId = "resource_0003";
        url = "url3";
        expectedVersion = 3;
        mi = result.next();
        assertTrue(result.hasNext());
        assertEquals(resourceId, mi.getResourceId());
        assertEquals(ocrdIdentifier, mi.getOcrdIdentifier());
        assertEquals(url, mi.getUrl());
        assertEquals(expectedVersion, mi.getVersion());
        assertTrue(mi.getUploadDate().compareTo(before) >= 0);
        assertTrue(mi.getUploadDate().compareTo(after) <= 0);
        assertTrue(mi.getUploadDate().compareTo(date) <= 0);
        assertFalse(mi.getLatest());
        
        date = mi.getUploadDate();
        resourceId = "resource_0002";
        url = "url2";
        expectedVersion = 2;
        mi = result.next();
        assertTrue(result.hasNext());
        assertEquals(resourceId, mi.getResourceId());
        assertEquals(ocrdIdentifier, mi.getOcrdIdentifier());
        assertEquals(url, mi.getUrl());
        assertEquals(expectedVersion, mi.getVersion());
        assertTrue(mi.getUploadDate().compareTo(before) >= 0);
        assertTrue(mi.getUploadDate().compareTo(after) <= 0);
        assertTrue(mi.getUploadDate().compareTo(date) <= 0);
        assertFalse(mi.getLatest());
        
        date = mi.getUploadDate();
        resourceId = "resource_0001";
        url = "url1";
        expectedVersion = 1;
        mi = result.next();
        assertFalse(result.hasNext());
        assertEquals(resourceId, mi.getResourceId());
        assertEquals(ocrdIdentifier, mi.getOcrdIdentifier());
        assertEquals(url, mi.getUrl());
        assertEquals(expectedVersion, mi.getVersion());
        assertTrue(mi.getUploadDate().compareTo(before) >= 0);
        assertTrue(mi.getUploadDate().compareTo(after) <= 0);
        assertTrue(mi.getUploadDate().compareTo(date) <= 0);
        assertFalse(mi.getLatest());
    }

    /**
     * Test of findByResourceId method, of class ZippedBagitRepository.
     */
    @Test
    public void testFindByOcrdIdentifierAndVersion() {
        System.out.println("findByOcrdIdentifierAndVersion");
        Integer expectedVersion = 2;
        String resourceId;
        String ocrdIdentifier = "id_0002";
        String url;
        
        ZippedBagitRepository instance = zippedBagitRepository;
        Iterator<ZippedBagit> result = instance.findByOcrdIdentifierAndVersion(ocrdIdentifier, expectedVersion).iterator();
        resourceId = "resource_0002";
        ocrdIdentifier = "id_0002";
        url = "url2";
        ZippedBagit mi = result.next();
        assertFalse(result.hasNext());
        assertEquals(resourceId, mi.getResourceId());
        assertEquals(ocrdIdentifier, mi.getOcrdIdentifier());
        assertEquals(url, mi.getUrl());
        assertEquals(expectedVersion, mi.getVersion());
        assertTrue(mi.getUploadDate().compareTo(before) >= 0);
        assertTrue(mi.getUploadDate().compareTo(after) <= 0);
        assertFalse(mi.getLatest());
    }
}
