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

import edu.kit.ocrd.workspace.entity.LanguageMetadata;
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
public class LanguageMetadataTest {
  String resourceId = "resourceId";
  String language = "language";
  String id = "id";
  
  public LanguageMetadataTest() {
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
   * Test of getId method, of class LanguageMetadata.
   */
  @Test
  public void testSetAndGetId() {
    System.out.println("set and getId");
    LanguageMetadata instance = new LanguageMetadata();
    String expResult = id;
    instance.setId(id);
    String result = instance.getId();
    assertEquals(expResult, result);
  }

  /**
   * Test of getResourceId method, of class LanguageMetadata.
   */
  @Test
  public void testSetAndGetResourceId() {
    System.out.println("set and getResourceId");
    LanguageMetadata instance = new LanguageMetadata();
    String expResult = resourceId;
    instance.setResourceId(resourceId);
    String result = instance.getResourceId();
    assertEquals(expResult, result);
  }

  /**
   * Test of getLanguage method, of class LanguageMetadata.
   */
  @Test
  public void testSetAndGetLanguage() {
    System.out.println("set and getLanguage");
    LanguageMetadata instance = new LanguageMetadata();
    String expResult = language;
    instance.setLanguage(language);
    String result = instance.getLanguage();
    assertEquals(expResult, result);
  }

  /**
   * Test of toString method, of class LanguageMetadata.
   */
  @Test
  public void testToString() {
    System.out.println("toString");
    LanguageMetadata instance = new LanguageMetadata(resourceId, language);
    String result = instance.toString();
    assertTrue(result.contains("resourceId=" + resourceId));
    assertTrue(result.contains("language=" + language));
  }
  
  @Test
  public void testConstructor() {
    System.out.println("constructor");
    LanguageMetadata instance = new LanguageMetadata(resourceId, language);
    assertEquals(resourceId, instance.getResourceId());
    assertEquals(language, instance.getLanguage());
  }

 @Test
  public void testMetsPropertiesWithDocument_validMets_newFormat() throws Exception {
    System.out.println("Test with valid METS file with new format");
    File file = new File("src/test/resources/mets/validMets_newFormat.xml");
    assertTrue("File exists!", file.exists());
    Document document = JaxenUtil.getDocument(file);
    List<LanguageMetadata> extractLanguageMetadataFromMets = MetsDocumentUtil.extractLanguageMetadataFromMets(document, resourceId);
    assertTrue(extractLanguageMetadataFromMets.size() == 1);
    LanguageMetadata lmd = extractLanguageMetadataFromMets.get(0);
    assertEquals(lmd.getResourceId(), resourceId);
    assertEquals(lmd.getLanguage(),"deu");
  }
  
}
