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
import edu.kit.ocrd.workspace.entity.ProvenanceMetadata;
import edu.kit.datamanager.metastore.exception.InvalidFormatException;
import edu.kit.datamanager.metastore.repository.ProvenanceMetadataRepository;
import edu.kit.datamanager.metastore.runner.CrudRunner;
import java.io.File;
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
public class ProvenanceMetadataServiceTest {

  @Autowired
  private ArangoOperations operations;

  @Autowired
  private ProvenanceMetadataRepository repository;

  private Date startDate;

  private ProvenanceMetadataService metsDocumentService;

  public ProvenanceMetadataServiceTest() {
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
    repository.saveAll(CrudRunner.createProvenanceMetadata());
    metsDocumentService = new ProvenanceMetadataService();
    metsDocumentService.setProvenanceMetadataRepository(repository);
  }

  @After
  public void tearDown() {
  }

  /**
   * Test of getProvenanceMetadataByResourceId method, of class
   * ProvenanceMetadataService.
   */
  @Test
  public void testGetProvenanceMetadataByResourceId() {
    System.out.println("testGetProvenanceMetadataByResourceId");
    String resourceId = "id_0002";
    ProvenanceMetadataService instance = metsDocumentService;
    List<ProvenanceMetadata> result = instance.getProvenanceMetadataByResourceId(resourceId);
    assertEquals(5, result.size());
    ProvenanceMetadata pmd = result.get(0);
    Date startProcessor = pmd.getStartProcessor();
    assertEquals(resourceId, pmd.getResourceId());
    pmd = result.get(1);
    assertTrue(pmd.getStartProcessor().compareTo(startProcessor) > 0);
    startProcessor = pmd.getStartProcessor();
    assertEquals(resourceId, pmd.getResourceId());
    pmd = result.get(2);
    assertTrue(pmd.getStartProcessor().compareTo(startProcessor) > 0);
    startProcessor = pmd.getStartProcessor();
    assertEquals(resourceId, pmd.getResourceId());
    pmd = result.get(3);
    assertTrue(pmd.getStartProcessor().compareTo(startProcessor) > 0);
    startProcessor = pmd.getStartProcessor();
    assertEquals(resourceId, pmd.getResourceId());
    pmd = result.get(4);
    assertTrue(pmd.getStartProcessor().compareTo(startProcessor) > 0);
  }

  /**
   * Test of getProvenanceMetadataByResourceId method, of class
   * ProvenanceMetadataRepository.
   */
  @Test
  public void testFindByUnknownResourceId() {
    System.out.println("testFindByUnknownResourceId");
    String resourceId = "unknown_id";
    ProvenanceMetadataService instance = metsDocumentService;
    List<ProvenanceMetadata> result = instance.getProvenanceMetadataByResourceId(resourceId);
    assertEquals(0, result.size());
  }

  /**
   * Test of getProvenanceMetadataByResourceIdAndWorkflowId method, of class
   * ProvenanceMetadataRepository.
   */
  @Test
  public void testFindByResourceIdAndWorkflowIs() {
    System.out.println("testFindByResourceIdAndWorkflowIs");
    String resourceId = "id_0002";
    String workflowId = "any workflow ID";
    ProvenanceMetadataService instance = metsDocumentService;
    List<ProvenanceMetadata> result = instance.getProvenanceMetadataByResourceIdAndWorkflowId(resourceId, workflowId);
    assertEquals(3, result.size());
    ProvenanceMetadata pmd = result.get(0);
    Date startProcessor = pmd.getStartProcessor();
    assertEquals(resourceId, pmd.getResourceId());
    assertEquals(workflowId, pmd.getWorkflowId());
    pmd = result.get(1);
    assertTrue(pmd.getStartProcessor().compareTo(startProcessor) > 0);
    startProcessor = pmd.getStartProcessor();
    assertEquals(resourceId, pmd.getResourceId());
    assertEquals(workflowId, pmd.getWorkflowId());
    pmd = result.get(2);
    assertTrue(pmd.getStartProcessor().compareTo(startProcessor) > 0);
    startProcessor = pmd.getStartProcessor();
  }

  /**
   * Test of getProvenanceMetadataByResourceIdAndWorkflowId method, of class
   * ProvenanceMetadataRepository.
   */
  @Test
  public void testFindByUnknownResourceIdAndWorkflowIs() {
    System.out.println("testFindByUnknownResourceIdAndWorkflowIs");
    String resourceId = "unknownId";
    String workflowId = "any workflow ID";
    ProvenanceMetadataService instance = metsDocumentService;
    List<ProvenanceMetadata> result = instance.getProvenanceMetadataByResourceIdAndWorkflowId(resourceId, workflowId);
    assertTrue(result.isEmpty());
  }

  /**
   * Test of getProvenanceMetadataByResourceIdAndWorkflowId method, of class
   * ProvenanceMetadataRepository.
   */
  @Test
  public void testFindByResourceIdAndUnknownWorkflowIs() {
    System.out.println("testFindByResourceIdAndUnknownWorkflowIs");
    String resourceId = "id_0002";
    String workflowId = "unknown workflow ID";
    ProvenanceMetadataService instance = metsDocumentService;
    List<ProvenanceMetadata> result = instance.getProvenanceMetadataByResourceIdAndWorkflowId(resourceId, workflowId);
    assertTrue(result.isEmpty());
  }

  /**
   * Test of getProvenanceMetadataByResourceIdAndWorkflowId method, of class
   * ProvenanceMetadataRepository.
   */
  @Test
  public void testCreateProvenanceMetadata() throws Exception {
    System.out.println("testCreateProvenanceMetadata");
    File metsFile = new File("src/test/resources/provenance/data/mets.xml");
    assertTrue("File exists!", metsFile.exists());
    File provFile = new File("src/test/resources/provenance/data/metadata/ocrd_provenance.xml");
    assertTrue("File exists!", provFile.exists());
    String resourceId = "OnlyForTests";
    ProvenanceMetadataService instance = metsDocumentService;
    instance.createProvenanceMetadata(resourceId, metsFile, provFile);
    List<ProvenanceMetadata> result = instance.getProvenanceMetadataByResourceId(resourceId);
    assertEquals(3, result.size());
    for (ProvenanceMetadata pmd : result) {
      assertTrue(result.get(0).getProcessorLabel().startsWith("ocrd-"));
    }
  }

  /**
   * Test of getProvenanceMetadataByResourceIdAndWorkflowId method, of class
   * ProvenanceMetadataRepository.
   */
  @Test
  public void testCreateNoProvenanceMetadata() throws Exception {
    System.out.println("testCreateNoProvenanceMetadata");
    File metsFile = new File("src/test/resources/provenance/data/mets.xml");
    assertTrue("File exists!", metsFile.exists());
    String resourceId = "OnlyForTests";
    ProvenanceMetadataService instance = metsDocumentService;
    instance.createProvenanceMetadata(resourceId, metsFile, metsFile);
    List<ProvenanceMetadata> result = instance.getProvenanceMetadataByResourceId(resourceId);
    assertEquals(0, result.size());
  }

  /**
   * Test of getProvenanceMetadataByResourceIdAndWorkflowId method, of class
   * ProvenanceMetadataRepository.
   */
  @Test
  public void testInvalidCreateProvenanceMetadata() throws Exception {
    System.out.println("testInvalidCreateProvenanceMetadata");
    File invalidMetsFile = new File("src/test/resources/provenance/data/mets_doesnt_exist.xml");
    assertFalse("File not exists!", invalidMetsFile.exists());
    File provFile = new File("src/test/resources/provenance/data/metadata/ocrd_provenance.xml");
    assertTrue("File exists!", provFile.exists());
    String resourceId = "OnlyForTests2";
    ProvenanceMetadataService instance = metsDocumentService;
    instance.createProvenanceMetadata(resourceId, invalidMetsFile, provFile);
    List<ProvenanceMetadata> result = instance.getProvenanceMetadataByResourceId(resourceId);
    assertEquals(0, result.size());
    resourceId = "OnlyForTests3";
    instance.createProvenanceMetadata(resourceId, invalidMetsFile, null);
    result = instance.getProvenanceMetadataByResourceId(resourceId);
    assertEquals(0, result.size());
    resourceId = "OnlyForTests4";
    instance.createProvenanceMetadata(resourceId, null, provFile);
    result = instance.getProvenanceMetadataByResourceId(resourceId);
    assertEquals(0, result.size());
  }

  /**
   * Test of getProvenanceMetadataByResourceIdAndWorkflowId method, of class
   * ProvenanceMetadataRepository.
   */
  @Test
  public void testInvalidFormatCreateProvenanceMetadata1() throws Exception {
    System.out.println("testInvalidFormatCreateProvenanceMetadata1");
    File invalidMetsFile = new File("src/test/resources/provenance/data/mets.xml");
    assertTrue("File exists!", invalidMetsFile.exists());
    File provFile = new File("src/test/resources/provenance/data/metadata/stderr.txt");
    assertTrue("File exists!", provFile.exists());
    String resourceId = "OnlyForTests2";
    ProvenanceMetadataService instance = metsDocumentService;
    try {
    instance.createProvenanceMetadata(resourceId, invalidMetsFile, provFile);
    assertFalse(true);
    } catch (InvalidFormatException iex) {
      assertTrue(iex.getMessage().startsWith("File 'stderr.txt': Error"));
    }
  }

  /**
   * Test of getProvenanceMetadataByResourceIdAndWorkflowId method, of class
   * ProvenanceMetadataRepository.
   */
  @Test
  public void testInvalidFormatCreateProvenanceMetadata2() throws Exception {
    System.out.println("testInvalidFormatCreateProvenanceMetadata2");
    File invalidMetsFile = new File("src/test/resources/provenance/data/metadata/stderr.txt");
    assertTrue("File exists!", invalidMetsFile.exists());
    File provFile = new File("src/test/resources/provenance/data/metadata/ocrd_provenance.xml");
    assertTrue("File exists!", provFile.exists());
    String resourceId = "OnlyForTests2";
    ProvenanceMetadataService instance = metsDocumentService;
    try {
    instance.createProvenanceMetadata(resourceId, invalidMetsFile, provFile);
    assertFalse(true);
    } catch (InvalidFormatException iex) {
      assertTrue(iex.getMessage().startsWith("File 'stderr.txt': Error"));
    }
  }

}
