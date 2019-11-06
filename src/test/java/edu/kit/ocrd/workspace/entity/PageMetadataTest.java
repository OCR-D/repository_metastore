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

import edu.kit.ocrd.workspace.entity.PageMetadata;
import edu.kit.ocrd.workspace.entity.GroundTruthProperties;
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
 * Tests for class PageMetadata
 */
public class PageMetadataTest {

  String id = "id";
  GroundTruthProperties feature = GroundTruthProperties.FEATURES;
  String order = "order";
  String pageId = "pageId";
  String resourceId = "resourceId";

  public PageMetadataTest() {
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
   * Test of getId method, of class PageMetadata.
   */
  @Test
  public void testSetAndGetId() {
    System.out.println("set and getId");
    PageMetadata instance = new PageMetadata();
    String expResult = id;
    instance.setId(id);
    String result = instance.getId();
    assertEquals(expResult, result);
  }

  /**
   * Test of getResourceId method, of class PageMetadata.
   */
  @Test
  public void testSetAndGetResourceId() {
    System.out.println("set and getResourceId");
    PageMetadata instance = new PageMetadata();
    String expResult = resourceId;
    instance.setResourceId(resourceId);
    String result = instance.getResourceId();
    assertEquals(expResult, result);
  }

  /**
   * Test of getPageId method, of class PageMetadata.
   */
  @Test
  public void testSetAndGetPageId() {
    System.out.println("set and getPageId");
    PageMetadata instance = new PageMetadata();
    String expResult = pageId;
    instance.setPageId(pageId);
    String result = instance.getPageId();
    assertEquals(expResult, result);
  }

  /**
   * Test of getOrder method, of class PageMetadata.
   */
  @Test
  public void testSetAndGetOrder() {
    System.out.println("set and getOrder");
    PageMetadata instance = new PageMetadata();
    String expResult = order;
    instance.setOrder(order);
    String result = instance.getOrder();
    assertEquals(expResult, result);
  }

  /**
   * Test of getFeature method, of class PageMetadata.
   */
  @Test
  public void testSetAndGetFeature() {
    System.out.println("set and getFeature");
    PageMetadata instance = new PageMetadata();
    GroundTruthProperties expResult = GroundTruthProperties.FEATURES;
    instance.setFeature(expResult);
    GroundTruthProperties result = instance.getFeature();
    assertEquals(expResult, result);
  }

  /**
   * Test of toString method, of class PageMetadata.
   */
  @Test
  public void testToString() {
    System.out.println("toString");
    PageMetadata instance = new PageMetadata(resourceId, order, pageId, GroundTruthProperties.FEATURES);
    String result = instance.toString();
    assertTrue(result.contains("resourceId=" + resourceId));
    assertTrue(result.contains("order=" + order));
    assertTrue(result.contains("PAGEID=" + pageId));
    assertTrue(result.contains("feature=" + feature));

  }
  @Test
  public void testConstructor(){
    System.out.println("Test constructor");
    PageMetadata instance = new PageMetadata(resourceId, order, pageId, feature);
    assertEquals(instance.getResourceId(), resourceId);
    assertEquals(instance.getFeature(), feature);
    assertEquals(instance.getOrder(), order);
    assertEquals(instance.getPageId(), pageId);
  }

 @Test
  public void testSemanticLabelingWithDocument_validMets_newFormat() throws Exception {
    System.out.println("Test semantic labeling with valid METS file with new format");
    File file = new File("src/test/resources/mets/validMets_newFormat.xml");
    assertTrue("File exists!", file.exists());
    Document document = JaxenUtil.getDocument(file);
    List<PageMetadata> pageMdList = MetsDocumentUtil.extractGroundTruthFeaturesFromMets(document, resourceId);
    assertTrue(pageMdList.size() == 20);
    PageMetadata pmd = pageMdList.get(19);
    assertEquals(pmd.getResourceId(), resourceId);
    assertEquals(pmd.getOrder(),"2");
    assertEquals(pmd.getPageId(), "phys_0002");
    assertEquals(pmd.getFeature(),GroundTruthProperties.UNEVEN_ILLUMINATION);
    assertEquals(pmd.getOrder(),"2");
  }

}
