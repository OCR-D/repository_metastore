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
import edu.kit.ocrd.workspace.entity.TextRegion;
import edu.kit.datamanager.metastore.runner.CrudRunner;
import edu.kit.ocrd.workspace.entity.TextRegion;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.springframework.boot.test.mock.mockito.MockReset.after;
import static org.springframework.boot.test.mock.mockito.MockReset.before;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TextRegionRepositoryTest {

    @Autowired
    private ArangoOperations operations;
    /**
     * Repository persisting METS identifiers.
     */
    @Autowired
    private TextRegionRepository textRegionRepository;

    public TextRegionRepositoryTest() {
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
        System.out.println("*******************************          TextRegion        ***************************************************");
        Collection<TextRegion> textRegionsList = CrudRunner.createTextRegion();
        for (TextRegion textRegion : textRegionsList) {
            textRegionRepository.save(textRegion);
        }
     }

    @After
    public void tearDown() {
    }

    @Test
    public void testCountDataSets() {
        System.out.println("repo.count()");
        assertEquals(5, textRegionRepository.count());
    }

    /**
     * Test of findByResourceId method, of class TextRegionRepository.
     */
    @Test
    public void testFindByResourceId() {
        System.out.println("findByResourceId");
        String resourceId = "id_0002";
        TextRegionRepository instance = textRegionRepository;
        Iterator<TextRegion> result = instance.findByResourceIdOrderByOrderAsc(resourceId).iterator();
        TextRegion mi = result.next();
        String previousOrder = mi.getOrder();
        assertTrue(result.hasNext());
        assertEquals(resourceId, mi.getResourceId());
        mi = result.next();
        assertTrue(mi.getOrder().compareTo(previousOrder)> 0);
        previousOrder = mi.getOrder();
        assertTrue(result.hasNext());
        assertEquals(resourceId, mi.getResourceId());
        mi = result.next();
        assertTrue(mi.getOrder().compareTo(previousOrder)> 0);
        previousOrder = mi.getOrder();
        assertTrue(result.hasNext());
        assertEquals(resourceId, mi.getResourceId());
        mi = result.next();
        assertTrue(mi.getOrder().compareTo(previousOrder)> 0);
        previousOrder = mi.getOrder();
        assertFalse(result.hasNext());
    }

    /**
     * Test of save method, of class TextRegionRepository.
     */
    @Test
    public void testSaveInstance() {
        System.out.println("testSaveInstance");

        TextRegionRepository instance = textRegionRepository;
        long count = instance.count();

        // Try to store same instance twice
        TextRegion mi = new TextRegion();
        mi.setResourceId("id_0003");
        try {
            instance.save(mi);
            assertTrue(Boolean.TRUE);
        } catch (InvalidDataAccessApiUsageException ade) {
            assertTrue(Boolean.FALSE);
        }
        long count2 = instance.count();
        assertEquals(count + 1, count2);
    }
}
