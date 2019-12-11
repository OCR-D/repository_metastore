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
import edu.kit.datamanager.metastore.exception.InvalidFormatException;
import edu.kit.ocrd.workspace.entity.TextRegion;
import edu.kit.datamanager.metastore.repository.TextRegionRepository;
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
public class TextRegionServiceTest {

  @Autowired
  private ArangoOperations operations;

  @Autowired
  private TextRegionRepository repository;

  private Date startDate;

  private TextRegionService textRegionService;

  public TextRegionServiceTest() {
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
    repository.saveAll(CrudRunner.createTextRegion());
    textRegionService = new TextRegionService();
    textRegionService.setTextRegionRepository(repository);
  }

  @After
  public void tearDown() {
  }

  /**
   * Test of getTextRegionByResourceId method, of class TextRegionService.
   */
  @Test
  public void testGetTextRegionByResourceId() {
    System.out.println("testGetTextRegionByResourceId");
    String resourceId = "id_0002";
    TextRegionService instance = textRegionService;
    List<TextRegion> result = instance.getTextRegionByResourceId(resourceId);
    assertEquals(4, result.size());
    String previousOrder = "0";
    TextRegion pmd = result.get(0);
    for (int index = 0; index < result.size(); index++) {
      pmd = result.get(index);
      assertEquals(resourceId, pmd.getResourceId());
      assertTrue(pmd.getOrder().compareTo(previousOrder) > 0);
      previousOrder = pmd.getOrder();

    }
  }

  /**
   * Test of getTextRegionByResourceId method, of class TextRegionRepository.
   */
  @Test
  public void testFindByUnknownResourceId() {
    System.out.println("testFindByUnknownResourceId");
    String resourceId = "unknown_id";
    TextRegionService instance = textRegionService;
    List<TextRegion> result = instance.getTextRegionByResourceId(resourceId);
    assertEquals(0, result.size());
  }

  /**
   * Test of getTextRegionByResourceIdAndWorkflowId method, of class
   * TextRegionRepository.
   */
  @Test
  public void testCreateTextRegion() throws Exception {
    System.out.println("testCreateTextRegion");
    File metsFile = new File("src/test/resources/all/data/mets.xml");
    assertTrue("File exists!", metsFile.exists());
    String resourceId = "OnlyForTests";
    TextRegionService instance = textRegionService;
    instance.createTextRegion(resourceId, metsFile);
    List<TextRegion> result = instance.getTextRegionByResourceId(resourceId);
    assertEquals(5, result.size());
    String previousOrder = "/"; // predecessor of '0'
    for (TextRegion pmd : result) {
      assertTrue(pmd.getRegion().startsWith("region000"));
      assertTrue(pmd.getOrder().compareTo(previousOrder) > 0);
      previousOrder = pmd.getOrder();
      assertTrue(pmd.getPageUrl().startsWith("OCR-D-OCR-"));
      assertEquals("OCR-D-IMG/OCR-D-IMG_0001.jpg", pmd.getImageUrl());
      assertEquals(resourceId, pmd.getResourceId());
      assertNull(pmd.getVersion());
    }
  }

  /**
   * Test of getTextRegionByResourceIdAndWorkflowId method, of class
   * TextRegionRepository.
   */
  @Test
  public void testCreateTextRegionUnknownFile() throws Exception {
    System.out.println("testCreateTextRegionUnknownFile");
    File metsFile = new File("src/test/resources/all/data/unknown_mets.xml");
    assertFalse("File not exists!", metsFile.exists());
    String resourceId = "OnlyForTests";
    TextRegionService instance = textRegionService;
    instance.createTextRegion(resourceId, metsFile);
    List<TextRegion> result = instance.getTextRegionByResourceId(resourceId);
    assertEquals(0, result.size());
    instance.createTextRegion(null, metsFile);
    result = instance.getTextRegionByResourceId(resourceId);
    assertEquals(0, result.size());
    instance.createTextRegion(resourceId, null);
    result = instance.getTextRegionByResourceId(resourceId);
    assertEquals(0, result.size());
  }

  /**
   * Test of getTextRegionByResourceIdAndWorkflowId method, of class
   * TextRegionRepository.
   */
  @Test
  public void testCreateTextRegionInvalidFile() throws Exception {
    System.out.println("testCreateTextRegionInvalidFile");
    File metsFile = new File("src/test/resources/all/metadata/stderr.txt");
    assertTrue("File exists!", metsFile.exists());
    String resourceId = "OnlyForTests";
    TextRegionService instance = textRegionService;
    try {
    instance.createTextRegion(resourceId, metsFile);
    assertFalse(true);
    } catch (InvalidFormatException iex) {
      assertTrue(iex.getMessage().startsWith("Error"));
    }
  }

}
