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
package edu.kit.datamanager.metastore.dao;

import java.util.Arrays;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test for PageFeatures.
 */
public class PageFeaturesTest {

  public PageFeaturesTest() {
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
   * Test of set/getOrder method, of class PageFeatures.
   */
  @Test
  public void testSetAndGetOrder() {
    System.out.println("getOrder");
    PageFeatures instance = new PageFeatures();
    String expResult = "order";
    instance.setOrder(expResult);
    String result = instance.getOrder();
    assertEquals(expResult, result);
    assertNull(instance.getFeatures());
    assertEquals(0, instance.getNoOfImages());
    assertNull(instance.getPageId());
  }

  /**
   * Test of set/getFeatures method, of class PageFeatures.
   */
  @Test
  public void testSetAndGetFeatures() {
    System.out.println("getFeatures");
    PageFeatures instance = new PageFeatures();
    List<String> expResult = Arrays.asList("feature1", "feature2");
    instance.setFeatures(expResult);
    List<String> result = instance.getFeatures();
    assertEquals(expResult, result);
    assertNull(instance.getOrder());
    assertEquals(0, instance.getNoOfImages());
    assertNull(instance.getPageId());
  }

  /**
   * Test of set/getNoOfImages method, of class PageFeatures.
   */
  @Test
  public void testSetAndGetNoOfImages() {
    System.out.println("getNoOfImages");
    PageFeatures instance = new PageFeatures();
    int expResult = 3;
    instance.setNoOfImages(expResult);
    int result = instance.getNoOfImages();
    assertEquals(expResult, result);
    assertNull(instance.getOrder());
    assertNull(instance.getFeatures());
    assertNull(instance.getPageId());
  }

  /**
   * Test of set/getPageId method, of class PageFeatures.
   */
  @Test
  public void testGetPageId() {
    System.out.println("getPageId");
    PageFeatures instance = new PageFeatures();
    String expResult = "pageId";
    instance.setPageId(expResult);
    String result = instance.getPageId();
    assertEquals(expResult, result);
    assertNull(instance.getOrder());
    assertNull(instance.getFeatures());
    assertEquals(0, instance.getNoOfImages());
  }

}
