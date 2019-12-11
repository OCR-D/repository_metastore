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
import edu.kit.datamanager.metastore.runner.CrudRunner;
import edu.kit.ocrd.workspace.entity.ProvenanceMetadata;
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
import org.springframework.dao.DataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProvenanceMetadataRepositoryTest {

    @Autowired
    private ArangoOperations operations;
    /**
     * Repository persisting METS identifiers.
     */
    @Autowired
    private ProvenanceMetadataRepository provenanceMetadataRepository;

    public ProvenanceMetadataRepositoryTest() {
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
        System.out.println("*******************************          ProvenanceMetadata        ***************************************************");
        Collection<ProvenanceMetadata> provenanceMetadataList = CrudRunner.createProvenanceMetadata();
        for (ProvenanceMetadata provenanceMetadata : provenanceMetadataList) {
            provenanceMetadataRepository.save(provenanceMetadata);
        }
     }

    @After
    public void tearDown() {
    }

    @Test
    public void testCountDataSets() {
        System.out.println("repo.count()");
        assertEquals(6, provenanceMetadataRepository.count());
    }

    /**
     * Test of findByResourceIdOrderByStartProcessorAsc method, of class ProvenanceMetadataRepository.
     */
    @Test
    public void testFindByResourceId() {
        System.out.println("findByResourceId");
        String resourceId = "id_0002";
        ProvenanceMetadataRepository instance = provenanceMetadataRepository;
        Iterator<ProvenanceMetadata> result = instance.findByResourceIdOrderByStartProcessorAsc(resourceId).iterator();
        ProvenanceMetadata mi = result.next();
        Date startProcessor = mi.getStartProcessor();
        assertTrue(result.hasNext());
        assertEquals(resourceId, mi.getResourceId());
        mi = result.next();
        assertTrue(mi.getStartProcessor().compareTo(startProcessor)> 0);
        startProcessor = mi.getStartProcessor();
        assertTrue(result.hasNext());
        assertEquals(resourceId, mi.getResourceId());
        mi = result.next();
        assertTrue(mi.getStartProcessor().compareTo(startProcessor)> 0);
        startProcessor = mi.getStartProcessor();
        assertTrue(result.hasNext());
        assertEquals(resourceId, mi.getResourceId());
        mi = result.next();
        assertTrue(mi.getStartProcessor().compareTo(startProcessor)> 0);
        startProcessor = mi.getStartProcessor();
        assertTrue(result.hasNext());
        assertEquals(resourceId, mi.getResourceId());
        mi = result.next();
        assertTrue(mi.getStartProcessor().compareTo(startProcessor)> 0);
        assertFalse(result.hasNext());
    }

    /**
     * Test of findByResourceIdOrderByStartProcessorAsc method, of class ProvenanceMetadataRepository.
     */
    @Test
    public void testFindByUnknownResourceId() {
        System.out.println("findByResourceId");
        String resourceId = "unknown_id";
        ProvenanceMetadataRepository instance = provenanceMetadataRepository;
        Iterator<ProvenanceMetadata> result = instance.findByResourceIdOrderByStartProcessorAsc(resourceId).iterator();
        assertFalse(result.hasNext());
    }

    /**
     * Test of findByResourceIdOrderByStartProcessorAsc method, of class ProvenanceMetadataRepository.
     */
    @Test
    public void testFindByResourceIdAndWorkflowIs() {
        System.out.println("findByResourceId");
        String resourceId = "id_0002";
        String workflowId = "any workflow ID";
        ProvenanceMetadataRepository instance = provenanceMetadataRepository;
        Iterator<ProvenanceMetadata> result = instance.findByResourceIdAndWorkflowIdOrderByStartProcessorAsc(resourceId, workflowId).iterator();
        ProvenanceMetadata mi = result.next();
        Date startProcessor = mi.getStartProcessor();
        assertTrue(result.hasNext());
        assertEquals(resourceId, mi.getResourceId());
        assertEquals(workflowId, mi.getWorkflowId());
        mi = result.next();
        assertTrue(mi.getStartProcessor().compareTo(startProcessor)> 0);
        startProcessor = mi.getStartProcessor();
        assertTrue(result.hasNext());
        assertEquals(resourceId, mi.getResourceId());
        assertEquals(workflowId, mi.getWorkflowId());
        mi = result.next();
        assertTrue(mi.getStartProcessor().compareTo(startProcessor)> 0);
        startProcessor = mi.getStartProcessor();
        assertFalse(result.hasNext());
    }

    /**
     * Test of findByResourceIdOrderByStartProcessorAsc method, of class ProvenanceMetadataRepository.
     */
    @Test
    public void testFindByUnknownResourceIdAndWorkflowIs() {
        System.out.println("findByResourceId");
        String resourceId = "unknownId";
        String workflowId = "any workflow ID";
        ProvenanceMetadataRepository instance = provenanceMetadataRepository;
        Iterator<ProvenanceMetadata> result = instance.findByResourceIdAndWorkflowIdOrderByStartProcessorAsc(resourceId, workflowId).iterator();
        assertFalse(result.hasNext());
    }

    /**
     * Test of findByResourceIdOrderByStartProcessorAsc method, of class ProvenanceMetadataRepository.
     */
    @Test
    public void testFindByResourceIdAndUnknownWorkflowIs() {
        System.out.println("findByResourceId");
        String resourceId = "id_0002";
        String workflowId = "unknown workflow ID";
        ProvenanceMetadataRepository instance = provenanceMetadataRepository;
        Iterator<ProvenanceMetadata> result = instance.findByResourceIdAndWorkflowIdOrderByStartProcessorAsc(resourceId, workflowId).iterator();
        assertFalse(result.hasNext());
    }
    /**
     * Test of save method, of class ProvenanceMetadataRepository.
     */
    @Test
    public void testSaveInstance() {
        System.out.println("testSaveInstance");

        ProvenanceMetadataRepository instance = provenanceMetadataRepository;
        long count = instance.count();

        // Try to store same instance twice
        ProvenanceMetadata mi = new ProvenanceMetadata();
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
