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

import edu.kit.ocrd.workspace.entity.MetsIdentifier;
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
 * Test MetsIdentifier
 */
public class MetsIdentifierTest {
  String resourceId = "resourceId";
  String identifier = "identifier";
  String type = "type";
  String id = "id";
  
  public MetsIdentifierTest() {
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
   * Test of getId method, of class MetsIdentifier.
   */
  @Test
  public void testSetAndGetId() {
    System.out.println("setId and getId");
    MetsIdentifier instance = new MetsIdentifier();
    instance.setId(id);
    String expResult = id;
    String result = instance.getId();
    assertEquals(expResult, result);
  }


  /**
   * Test of getResourceId method, of class MetsIdentifier.
   */
  @Test
  public void testSetAndGetResourceId() {
    System.out.println("set and getResourceId");
    MetsIdentifier instance = new MetsIdentifier();
    String expResult = resourceId;
    instance.setResourceId(resourceId);
    String result = instance.getResourceId();
    assertEquals(expResult, result);
  }

  /**
   * Test of getType method, of class MetsIdentifier.
   */
  @Test
  public void testSetAndGetType() {
    System.out.println("set and getType");
    MetsIdentifier instance = new MetsIdentifier();
    String expResult = type;
    instance.setType(type);
    String result = instance.getType();
    assertEquals(expResult, result);
  }

  /**
   * Test of getIdentifier method, of class MetsIdentifier.
   */
  @Test
  public void testSetAndGetIdentifier() {
    System.out.println("set and getIdentifier");
    MetsIdentifier instance = new MetsIdentifier();
    String expResult = identifier;
    instance.setIdentifier(identifier);
    String result = instance.getIdentifier();
    assertEquals(expResult, result);
  }

  /**
   * Test of toString method, of class MetsIdentifier.
   */
  @Test
  public void testToString() {
    System.out.println("toString");
    MetsIdentifier instance = new MetsIdentifier(resourceId, type, identifier);
    String result = instance.toString();
    assertTrue(result.contains("identifier=" + identifier));
    assertTrue(result.contains("resourceId=" + resourceId));
    assertTrue(result.contains("type=" + type));
  }
  /**
   * Test of constructor of class MetsIdentifier.
   */
  @Test
  public void testConstructor() {
    System.out.println("Constructor");
    MetsIdentifier instance = new MetsIdentifier(resourceId, type, identifier);
    assertEquals(instance.getIdentifier(), identifier);
    assertEquals(instance.getType(), type);
    assertEquals(instance.getResourceId(), resourceId);
  }

 @Test
  public void testMetsPropertiesWithDocument_validMets_newFormat() throws Exception {
    System.out.println("Test with valid METS file with new format");
    File file = new File("src/test/resources/mets/validMets_newFormat.xml");
    assertTrue("File exists!", file.exists());
    Document document = JaxenUtil.getDocument(file);
    List<MetsIdentifier> extractIdentifierFromMets = MetsDocumentUtil.extractIdentifierFromMets(document, resourceId);
    assertTrue(extractIdentifierFromMets.size() == 3);
    MetsIdentifier mid = extractIdentifierFromMets.get(0);
    assertEquals(mid.getResourceId(), resourceId);
    assertEquals(mid.getType(),"urn");
    assertEquals(mid.getIdentifier(),"urn:nbn:de:kobv:b4-20090519672");
    mid = extractIdentifierFromMets.get(1);
    assertEquals(mid.getResourceId(), resourceId);
    assertEquals(mid.getType(),"purl");
    assertEquals(mid.getIdentifier(),"http://www.deutschestextarchiv.de/buerger_gedichte_1778");
    mid = extractIdentifierFromMets.get(2);
    assertEquals(mid.getResourceId(), resourceId);
    assertEquals(mid.getType(),"dtaid");
    assertEquals(mid.getIdentifier(),"16348");
  }
  
}
