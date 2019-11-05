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

import edu.kit.ocrd.workspace.entity.XmlSchemaDefinition;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test XmlSchemaDefinition.
 */
public class XmlSchemaDefinitionTest {

  private String namespace = "namespace";
  private String prefix = "prefix";
  private String xsdFile = "xsdFile";
  private String id = "id";

  public XmlSchemaDefinitionTest() {
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
   * Test of getId method, of class XmlSchemaDefinition.
   */
  @Test
  public void testSetAndGetId() {
    System.out.println("getId");
    XmlSchemaDefinition instance = new XmlSchemaDefinition(prefix, namespace, xsdFile);
    String expResult = id;
    instance.setId(id);
    String result = instance.getId();
    assertEquals(expResult, result);
  }

  /**
   * Test of getPrefix method, of class XmlSchemaDefinition.
   */
  @Test
  public void testSetAndGetPrefix() {
    System.out.println("getPrefix");
    String newPrefix = "newPrefix";
    XmlSchemaDefinition instance = new XmlSchemaDefinition(prefix, namespace, xsdFile);
    String expResult = prefix;
    String result = instance.getPrefix();
    assertEquals(expResult, result);
    instance.setPrefix(newPrefix);
    result = instance.getPrefix();
    assertEquals(newPrefix, result);
  }

  /**
   * Test of getNamespace method, of class XmlSchemaDefinition.
   */
  @Test
  public void testSetAndGetNamespace() {
    System.out.println("getNamespace");
    String newNamespace = "newNamespace";
    XmlSchemaDefinition instance = new XmlSchemaDefinition(prefix, namespace, xsdFile);
    String expResult = namespace;
    String result = instance.getNamespace();
    assertEquals(expResult, result);
    instance.setNamespace(newNamespace);
    result = instance.getNamespace();
    assertEquals(newNamespace, result);
  }

  /**
   * Test of getXsdFile method, of class XmlSchemaDefinition.
   */
  @Test
  public void testSetAndGetXsdFile() {
    System.out.println("getXsdFile");
    String newXsdFile = "new content of XSD file.";
    XmlSchemaDefinition instance = new XmlSchemaDefinition(prefix, namespace, xsdFile);
    String expResult = xsdFile;
    String result = instance.getXsdFile();
    assertEquals(expResult, result);
    instance.setXsdFile(newXsdFile);
    result = instance.getXsdFile();
    assertEquals(newXsdFile, result);
  }

}
