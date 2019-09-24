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

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * Tests for ModsIdentifier
 */
public class ModsIdentifierTest {
  
  public ModsIdentifierTest() {
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
   * Test of constructor method, of class ModsIdentifier.
   */
  @Test
  public void testConstructorWithType() {
    System.out.println("testConstructorWithType");
    String expResult = "type";
    ModsIdentifier instance = new ModsIdentifier(expResult, null);
    String result = instance.getType();
    assertEquals(expResult, result);
    assertNull(instance.getIdentifier());
  }

  /**
   * Test of constructor method, of class ModsIdentifier.
   */
  @Test
  public void testConstructorWithIdentifier() {
    System.out.println("testConstructorWithIdentifier");
    String expResult = "identifier";
    ModsIdentifier instance = new ModsIdentifier(null, expResult);
    String result = instance.getIdentifier();
    assertEquals(expResult, result);
    assertNull(instance.getType());
  }

  /**
   * Test of constructor method, of class ModsIdentifier.
   */
  @Test
  public void testConstructorWithBoth() {
    System.out.println("testConstructorWithBoth");
    String expIdentifier = "identifier";
    String expType = "type";
    ModsIdentifier instance = new ModsIdentifier(expType, expIdentifier);
    String result = instance.getIdentifier();
    assertEquals(expIdentifier, result);
    result = instance.getType();
    assertEquals(expType, result);
  }

  /**
   * Test of set/getType method, of class ModsIdentifier.
   */
  @Test
  public void testSetAndGetType() {
    System.out.println("setAndGetType");
    ModsIdentifier instance = new ModsIdentifier(null, null);
    String expResult = "type";
    instance.setType(expResult);
    String result = instance.getType();
    assertEquals(expResult, result);
    assertNull(instance.getIdentifier());
  }

  /**
   * Test of set/getIdentifier method, of class ModsIdentifier.
   */
  @Test
  public void testSetAndGetIdentifier() {
    System.out.println("getIdentifier");
    ModsIdentifier instance = new ModsIdentifier(null, null);
    String expResult = "identifier";
    instance.setIdentifier(expResult);
    String result = instance.getIdentifier();
    assertEquals(expResult, result);
    assertNull(instance.getType());
  }

  
}
