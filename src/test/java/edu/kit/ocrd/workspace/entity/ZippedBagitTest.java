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
package edu.kit.ocrd.workspace.entity;

import edu.kit.ocrd.workspace.entity.ZippedBagit;
import java.util.Date;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test class ZippedBagit
 */
public class ZippedBagitTest {

    private String id = "id";
    private String resourceId = "resourceId";
    private String ocrdIdentifier = "ocrdIdentifier";
    private Integer version = 1;
    private Date uploadDate = new Date();
    private Boolean latest = Boolean.TRUE;
    private String url = "http://any.valid.url/test";

    public ZippedBagitTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getId method, of class ZippedBagit.
     */
    @Test
    public void testConstructor() {
        System.out.println("constructor");
        Date before = new Date();
        ZippedBagit instance = new ZippedBagit(resourceId, ocrdIdentifier, url);
        Date after = new Date();
        assertEquals(resourceId, instance.getResourceId());
        assertEquals(ocrdIdentifier, instance.getOcrdIdentifier());
        assertEquals(url, instance.getUrl());
        Integer expectedVersion = 1;
        assertEquals(expectedVersion, instance.getVersion());
        assertTrue(instance.getUploadDate().compareTo(before) >= 0);
        assertTrue(instance.getUploadDate().compareTo(after) <= 0);
        assertTrue(instance.getLatest());
    }

    /**
     * Test of getId method, of class ZippedBagit.
     */
    @Test
    public void testSetAndGetId() {
        System.out.println("getId");
        Date before = new Date();
        ZippedBagit instance = new ZippedBagit(resourceId, ocrdIdentifier, url);
        Date after = new Date();
        instance.setId(id);
        String expResult = id;
        String result = instance.getId();
        assertEquals(expResult, result);
        assertEquals(resourceId, instance.getResourceId());
        assertEquals(ocrdIdentifier, instance.getOcrdIdentifier());
        assertEquals(url, instance.getUrl());
        Integer expectedVersion = 1;
        assertEquals(expectedVersion, instance.getVersion());
        assertTrue(instance.getUploadDate().compareTo(before) >= 0);
        assertTrue(instance.getUploadDate().compareTo(after) <= 0);
        assertTrue(instance.getLatest());
    }

    /**
     * Test of getResourceId method, of class ZippedBagit.
     */
    @Test
    public void testSetAndGetResourceId() {
        System.out.println("getResourceId");
        Date before = new Date();
        ZippedBagit instance = new ZippedBagit(resourceId, ocrdIdentifier, url);
        Date after = new Date();
        String expResult = resourceId;
        String result = instance.getResourceId();
        assertEquals(expResult, result);
        expResult = "newResourceId";
        instance.setResourceId(expResult);
        result = instance.getResourceId();
        assertEquals(expResult, result);
        assertEquals(ocrdIdentifier, instance.getOcrdIdentifier());
        assertEquals(url, instance.getUrl());
        Integer expectedVersion = 1;
        assertEquals(expectedVersion, instance.getVersion());
        assertTrue(instance.getUploadDate().compareTo(before) >= 0);
        assertTrue(instance.getUploadDate().compareTo(after) <= 0);
        assertTrue(instance.getLatest());
    }

    /**
     * Test of getUrl method, of class ZippedBagit.
     */
    @Test
    public void testSetAndGetUrl() {
        System.out.println("getUrl");
        Date before = new Date();
        ZippedBagit instance = new ZippedBagit(resourceId, ocrdIdentifier, url);
        Date after = new Date();
        String expResult = url;
        String result = instance.getUrl();
        assertEquals(expResult, result);
        expResult = "newUrl";
        instance.setUrl(expResult);
        result = instance.getUrl();
        assertEquals(expResult, result);
        assertEquals(resourceId, instance.getResourceId());
        assertEquals(ocrdIdentifier, instance.getOcrdIdentifier());
        Integer expectedVersion = 1;
        assertEquals(expectedVersion, instance.getVersion());
        assertTrue(instance.getUploadDate().compareTo(before) >= 0);
        assertTrue(instance.getUploadDate().compareTo(after) <= 0);
        assertTrue(instance.getLatest());
    }

    /**
     * Test of getLatest method, of class ZippedBagit.
     */
    @Test
    public void testSetAndGetLatest() {
        System.out.println("getLatest");
        Date before = new Date();
        ZippedBagit instance = new ZippedBagit(resourceId, ocrdIdentifier, url);
        Date after = new Date();
        Boolean expResult = Boolean.TRUE;
        Boolean result = instance.getLatest();
        assertEquals(expResult, result);
        expResult = Boolean.FALSE;
        instance.setLatest(expResult);
        result = instance.getLatest();
        assertEquals(expResult, result);
        assertEquals(resourceId, instance.getResourceId());
        assertEquals(ocrdIdentifier, instance.getOcrdIdentifier());
        assertEquals(url, instance.getUrl());
        Integer expectedVersion = 1;
        assertEquals(expectedVersion, instance.getVersion());
        assertTrue(instance.getUploadDate().compareTo(before) >= 0);
        assertTrue(instance.getUploadDate().compareTo(after) <= 0);
    }

    /**
     * Test of getOcrdIdentifier method, of class ZippedBagit.
     */
    @Test
    public void testSetAndGetOcrdIdentifier() {
        System.out.println("SetOcrdIdentifier");
        Date before = new Date();
        ZippedBagit instance = new ZippedBagit(resourceId, ocrdIdentifier, url);
        Date after = new Date();
        String expResult = url;
        String result = instance.getUrl();
        assertEquals(expResult, result);
        expResult = "newOcrdIdentifier";
        instance.setOcrdIdentifier(expResult);
        result = instance.getOcrdIdentifier();
        assertEquals(expResult, result);
        assertEquals(resourceId, instance.getResourceId());
        assertEquals(url, instance.getUrl());
        Integer expectedVersion = 1;
        assertEquals(expectedVersion, instance.getVersion());
        assertTrue(instance.getUploadDate().compareTo(before) >= 0);
        assertTrue(instance.getUploadDate().compareTo(after) <= 0);
        assertTrue(instance.getLatest());
    }

    /**
     * Test of getVersion method, of class ZippedBagit.
     */
    @Test
    public void testSetAndGetVersion() {
        System.out.println("SetVersion");
        Date before = new Date();
        ZippedBagit instance = new ZippedBagit(resourceId, ocrdIdentifier, url);
        Date after = new Date();
        Integer expResult = 1;
        Integer result = instance.getVersion();
        assertEquals(expResult, result);
        expResult = new Integer(3);
        instance.setVersion(expResult);
        result = instance.getVersion();
        assertEquals(expResult, result);
        assertEquals(resourceId, instance.getResourceId());
        assertEquals(ocrdIdentifier, instance.getOcrdIdentifier());
        assertEquals(url, instance.getUrl());
        assertTrue(instance.getUploadDate().compareTo(before) >= 0);
        assertTrue(instance.getUploadDate().compareTo(after) <= 0);
        assertTrue(instance.getLatest());
    }

    /**
     * Test of getUploadDate method, of class ZippedBagit.
     */
    @Test
    public void testSetAndGetUploadDate() {
        System.out.println("SetUploadDate");
        Date before = new Date();
        ZippedBagit instance = new ZippedBagit(resourceId, ocrdIdentifier, url);
        Date after = new Date();
        Date expResult = new Date();
        assertTrue(instance.getUploadDate().compareTo(before) >= 0);
        assertTrue(instance.getUploadDate().compareTo(after) <= 0);
        expResult = new Date();
        instance.setUploadDate(expResult);
        Date result = instance.getUploadDate();
        assertEquals(expResult, result);
        assertEquals(resourceId, instance.getResourceId());
        assertEquals(ocrdIdentifier, instance.getOcrdIdentifier());
        assertEquals(url, instance.getUrl());
        Integer expectedVersion = 1;
        assertEquals(expectedVersion, instance.getVersion());
        assertTrue(instance.getLatest());
    }

    /**
     * Test of getUploadDate method, of class ZippedBagit.
     */
    @Test
    public void testUpdateZippedBagit() {
        System.out.println("updateZippedBagit");
        String newResourceId = "newResourceId";
        String newUrl = "newUrl";
        Date before = new Date();
        ZippedBagit instance = new ZippedBagit(resourceId, ocrdIdentifier, url);
        Date after = new Date();
        assertEquals(resourceId, instance.getResourceId());
        assertEquals(ocrdIdentifier, instance.getOcrdIdentifier());
        assertEquals(url, instance.getUrl());
        Integer expectedVersion = 1;
        assertEquals(expectedVersion, instance.getVersion());
        assertTrue(instance.getUploadDate().compareTo(before) >= 0);
        assertTrue(instance.getUploadDate().compareTo(after) <= 0);
        assertTrue(instance.getLatest());
        ZippedBagit updateZippedBagit = instance.updateZippedBagit(newResourceId, newUrl);
        assertEquals(resourceId, instance.getResourceId());
        assertEquals(ocrdIdentifier, instance.getOcrdIdentifier());
        assertEquals(url, instance.getUrl());
        expectedVersion = 1;
        assertEquals(expectedVersion, instance.getVersion());
        assertTrue(instance.getUploadDate().compareTo(before) >= 0);
        assertTrue(instance.getUploadDate().compareTo(after) <= 0);
        assertFalse(instance.getLatest());
        before = after;
        after = new Date();
        assertEquals(newResourceId, updateZippedBagit.getResourceId());
        assertEquals(ocrdIdentifier, updateZippedBagit.getOcrdIdentifier());
        assertEquals(newUrl, updateZippedBagit.getUrl());
        expectedVersion = 2;
        assertEquals(expectedVersion, updateZippedBagit.getVersion());
        assertTrue(updateZippedBagit.getUploadDate().compareTo(before) >= 0);
        assertTrue(updateZippedBagit.getUploadDate().compareTo(after) <= 0);
        assertTrue(updateZippedBagit.getLatest());
    }

}
