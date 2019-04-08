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
package edu.kit.datamanager.metastore.entity;

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
  public void testSetAndGetId() {
    System.out.println("getId");
    ZippedBagit instance = new ZippedBagit(resourceId, url);
    instance.setId(id);
    String expResult = id;
    String result = instance.getId();
    assertEquals(expResult, result);
  }

  /**
   * Test of getResourceId method, of class ZippedBagit.
   */
  @Test
  public void testSetAndGetResourceId() {
    System.out.println("getResourceId");
    ZippedBagit instance = new ZippedBagit(resourceId, url);
    String expResult = resourceId;
    String result = instance.getResourceId();
    assertEquals(expResult, result);
  }

  /**
   * Test of getUrl method, of class ZippedBagit.
   */
  @Test
  public void testSetAndGetUrl() {
    System.out.println("getUrl");
    ZippedBagit instance = new ZippedBagit(resourceId, url);
    String expResult = url;
    String result = instance.getUrl();
    assertEquals(expResult, result);
  }
}
