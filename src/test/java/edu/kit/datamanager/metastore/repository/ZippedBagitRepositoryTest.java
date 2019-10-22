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

import com.arangodb.ArangoDBException;
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
import org.springframework.dao.InvalidDataAccessApiUsageException;
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

    /**
     * Test of save method, of class ZippedBagitRepository.
     */
    @Test
    public void testDoubleEntries() {
        System.out.println("testDoubleEntries");
        Integer expectedVersion = 1;
        String ocrdIdentifier = "id_0002";

        ZippedBagitRepository instance = zippedBagitRepository;
        long count = instance.count();
        Iterator<ZippedBagit> result = instance.findByOcrdIdentifierAndVersion(ocrdIdentifier, expectedVersion).iterator();
        ocrdIdentifier = "id_0002";
        assertTrue(result.hasNext());
        ZippedBagit old = result.next();
        // Try to store same instance twice
        ZippedBagit mi = new ZippedBagit(old.getResourceId(), old.getOcrdIdentifier(), old.getUrl());
        try {
            instance.save(mi);
            assertTrue(Boolean.FALSE);
        } catch (InvalidDataAccessApiUsageException ade) {
            assertTrue(Boolean.TRUE);
        }
        long count2 = instance.count();
        assertEquals(count, count2);
    }

    /**
     * Test of save method, of class ZippedBagitRepository.
     */
    @Test
    public void testDoubleEntriesOcrdAndVersion() {
        System.out.println("testDoubleEntriesOcrdAndVersion");
        Integer expectedVersion = 1;
        String ocrdIdentifier = "id_0002";

        ZippedBagitRepository instance = zippedBagitRepository;
        long count = instance.count();
        Iterator<ZippedBagit> result = instance.findByOcrdIdentifierAndVersion(ocrdIdentifier, expectedVersion).iterator();
        ocrdIdentifier = "id_0002";
        assertTrue(result.hasNext());
        ZippedBagit old = result.next();
        // try to store ocrdIdentifier & version twice
        ZippedBagit mi = new ZippedBagit("totallyNewResourceId", old.getOcrdIdentifier(), old.getUrl());
        try {
            instance.save(mi);
            assertTrue(Boolean.FALSE);
        } catch (InvalidDataAccessApiUsageException ade) {
            assertTrue(Boolean.TRUE);
        }
        long count2 = instance.count();
        assertEquals(count, count2);
    }

    /**
     * Test of save method, of class ZippedBagitRepository.
     */
    @Test
    public void testSameResourceId() {
        System.out.println("testSameResourceId");
        Integer expectedVersion = 1;
        String ocrdIdentifier = "id_0002";

        ZippedBagitRepository instance = zippedBagitRepository;
        long count = instance.count();
        Iterator<ZippedBagit> result = instance.findByOcrdIdentifierAndVersion(ocrdIdentifier, expectedVersion).iterator();
        ocrdIdentifier = "id_0002";
        assertTrue(result.hasNext());
        ZippedBagit old = result.next();
        // try to store same resourceID twice
        ZippedBagit mi = new ZippedBagit(old.getResourceId(), "totallyNewOcrdIdentifier", old.getUrl());
        try {
            instance.save(mi);
            assertTrue(Boolean.FALSE);
        } catch (InvalidDataAccessApiUsageException ade) {
            assertTrue(Boolean.TRUE);
        }
        long count2 = instance.count();
        assertEquals(count, count2);
    }

    /**
     * Test of save method, of class ZippedBagitRepository.
     */
    @Test
    public void testSaveInstance() {
        System.out.println("testSaveInstance");

        ZippedBagitRepository instance = zippedBagitRepository;
        long count = instance.count();

        // Try to store same instance twice
        ZippedBagit mi = new ZippedBagit("totallyNewResourceId", "newOcrdIdentifier", "anyUrl");
        try {
            instance.save(mi);
            assertTrue(Boolean.TRUE);
        } catch (InvalidDataAccessApiUsageException ade) {
            assertTrue(Boolean.FALSE);
        }
        long count2 = instance.count();
        assertEquals(count + 1, count2);

    }

    /**
     * Test of save method, of class ZippedBagitRepository.
     */
    @Test
    public void testSaveUpdatedZippedBagit() {
        System.out.println("testSaveUpdatedZippedBagit");
        Integer expectedVersion = 1;
        String ocrdIdentifier = "onlyForUpdateTest";
        String resourceId = "anotherResourceId";
        String url = "any";

        ZippedBagitRepository instance = zippedBagitRepository;
        long count = instance.count();
        long countLatest = getNumberOfItems(instance.findByLatestTrueOrderByUploadDateDesc().iterator());

        // Try to store same instance twice
        ZippedBagit mi = new ZippedBagit(resourceId, ocrdIdentifier, url);
        instance.save(mi);
        long count2 = instance.count();
        long count2Latest = getNumberOfItems(instance.findByLatestTrueOrderByUploadDateDesc().iterator());
        assertEquals(count + 1, count2);
        assertEquals(countLatest + 1, count2Latest);
        ZippedBagit newBag = mi.updateZippedBagit(resourceId + "1", url);
        instance.save(mi);
        instance.save(newBag);
        long count3 = instance.count();
        long count3Latest = getNumberOfItems(instance.findByLatestTrueOrderByUploadDateDesc().iterator());
        assertEquals(count2 + 1, count3);
        assertEquals(count2Latest, count3Latest);
        ZippedBagit thirdBag = newBag.updateZippedBagit(resourceId + "2", url);
        instance.save(newBag);
        instance.save(thirdBag);
        long count4 = instance.count();
        long count4Latest = getNumberOfItems(instance.findByLatestTrueOrderByUploadDateDesc().iterator());
        assertEquals(count3 + 1, count4);
        assertEquals(count3Latest, count4Latest);
    }

    private int getNumberOfItems(Iterator iterator) {
        int numberOfItems = 0;
        while (iterator.hasNext()) {
            iterator.next();
            numberOfItems++;
        }
        return numberOfItems;
    }
}
