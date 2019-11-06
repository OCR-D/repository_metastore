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
 * See the License for the specific classification governing permissions and
 * limitations under the License.
 */
package edu.kit.ocrd.workspace.entity;

import edu.kit.ocrd.workspace.entity.ClassificationMetadata;
import edu.kit.ocrd.workspace.MetsDocumentUtil;
import java.io.File;
import java.util.List;
import org.fzk.tools.xml.JaxenUtil;
import org.jdom.Document;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 */
public class ClassificationMetadataTest {
  String resourceId = "resourceId";
  String classification = "classification";
  String id = "id";
  
  public ClassificationMetadataTest() {
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
   * Test of getId method, of class ClassificationMetadata.
   */
  @Test
  public void testSetAndGetId() {
    System.out.println("set and getId");
    ClassificationMetadata instance = new ClassificationMetadata();
    String expResult = id;
    instance.setId(id);
    String result = instance.getId();
    assertEquals(expResult, result);
  }

  /**
   * Test of getResourceId method, of class ClassificationMetadata.
   */
  @Test
  public void testSetAndGetResourceId() {
    System.out.println("set and getResourceId");
    ClassificationMetadata instance = new ClassificationMetadata();
    String expResult = resourceId;
    instance.setResourceId(resourceId);
    String result = instance.getResourceId();
    assertEquals(expResult, result);
  }

  /**
   * Test of getClassification method, of class ClassificationMetadata.
   */
  @Test
  public void testSetAndGetClassification() {
    System.out.println("set and getClassification");
    ClassificationMetadata instance = new ClassificationMetadata();
    String expResult = classification;
    instance.setClassification(classification);
    String result = instance.getClassification();
    assertEquals(expResult, result);
  }

  /**
   * Test of toString method, of class ClassificationMetadata.
   */
  @Test
  public void testToString() {
    System.out.println("toString");
    ClassificationMetadata instance = new ClassificationMetadata(resourceId, classification);
    String result = instance.toString();
    assertTrue(result.contains("resourceId=" + resourceId));
    assertTrue(result.contains("classification=" + classification));
  }
  
  @Test
  public void testConstructor() {
    System.out.println("constructor");
    ClassificationMetadata instance = new ClassificationMetadata(resourceId, classification);
    assertEquals(resourceId, instance.getResourceId());
    assertEquals(classification, instance.getClassification());
  }

 @Test
  public void testMetsPropertiesWithDocument_validMets_newFormat() throws Exception {
    System.out.println("Test with valid METS file with new format");
    File file = new File("src/test/resources/mets/validMets_newFormat.xml");
    assertTrue("File exists!", file.exists());
    Document document = JaxenUtil.getDocument(file);
    List<ClassificationMetadata> extractClassificationMetadataFromMets = MetsDocumentUtil.extractClassificationMetadataFromMets(document, resourceId);
    assertTrue(extractClassificationMetadataFromMets.size() == 2);
    ClassificationMetadata cmd = extractClassificationMetadataFromMets.get(0);
    assertEquals(cmd.getResourceId(), resourceId);
    assertEquals(cmd.getClassification(),"Belletristik");
    cmd = extractClassificationMetadataFromMets.get(1);
    assertEquals(cmd.getResourceId(), resourceId);
    assertEquals(cmd.getClassification(),"Lyrik");
  }
  
}
