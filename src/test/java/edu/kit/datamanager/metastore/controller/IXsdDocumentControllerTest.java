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
package edu.kit.datamanager.metastore.controller;

import edu.kit.datamanager.metastore.entity.XmlSchemaDefinition;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author hartmann-v
 */
public class IXsdDocumentControllerTest {
  
  public IXsdDocumentControllerTest() {
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
   * Test of createMetsDocument method, of class IXsdDocumentController.
   */
  @Test
  public void testCreateMetsDocument() {
    System.out.println("createMetsDocument");
    String prefix = "";
    String fileContent = "";
    IXsdDocumentController instance = new IXsdDocumentControllerImpl();
    ResponseEntity expResult = null;
    ResponseEntity result = instance.createMetsDocument(prefix, fileContent);
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of getAllDocuments method, of class IXsdDocumentController.
   */
  @Test
  public void testGetAllDocuments() {
    System.out.println("getAllDocuments");
    IXsdDocumentController instance = new IXsdDocumentControllerImpl();
    ResponseEntity<List<XmlSchemaDefinition>> expResult = null;
    ResponseEntity<List<XmlSchemaDefinition>> result = instance.getAllDocuments();
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of getAllPrefixes method, of class IXsdDocumentController.
   */
  @Test
  public void testGetAllPrefixes() {
    System.out.println("getAllPrefixes");
    IXsdDocumentController instance = new IXsdDocumentControllerImpl();
    ResponseEntity<List<String>> expResult = null;
    ResponseEntity<List<String>> result = instance.getAllPrefixes();
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of getXsdDocumentByNamespace method, of class IXsdDocumentController.
   */
  @Test
  public void testGetXsdDocumentByNamespace() {
    System.out.println("getXsdDocumentByNamespace");
    String namespace = "";
    IXsdDocumentController instance = new IXsdDocumentControllerImpl();
    ResponseEntity<XmlSchemaDefinition> expResult = null;
    ResponseEntity<XmlSchemaDefinition> result = instance.getXsdDocumentByNamespace(namespace);
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of getXsdDocumentByPrefix method, of class IXsdDocumentController.
   */
  @Test
  public void testGetXsdDocumentByPrefix() {
    System.out.println("getXsdDocumentByPrefix");
    String prefix = "";
    IXsdDocumentController instance = new IXsdDocumentControllerImpl();
    ResponseEntity<XmlSchemaDefinition> expResult = null;
    ResponseEntity<XmlSchemaDefinition> result = instance.getXsdDocumentByPrefix(prefix);
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  public class IXsdDocumentControllerImpl implements IXsdDocumentController {

    public ResponseEntity<?> createMetsDocument(String prefix, String fileContent) {
      return null;
    }

    public ResponseEntity<List<XmlSchemaDefinition>> getAllDocuments() {
      return null;
    }

    public ResponseEntity<List<String>> getAllPrefixes() {
      return null;
    }

    public ResponseEntity<XmlSchemaDefinition> getXsdDocumentByNamespace(String namespace) {
      return null;
    }

    public ResponseEntity<XmlSchemaDefinition> getXsdDocumentByPrefix(String prefix) {
      return null;
    }
  }
  
}
