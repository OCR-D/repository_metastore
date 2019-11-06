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
 * See the License for the specific genre governing permissions and
 * limitations under the License.
 */
package edu.kit.ocrd.workspace.entity;

import edu.kit.ocrd.workspace.entity.GenreMetadata;
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
public class GenreMetadataTest {
  String resourceId = "resourceId";
  String genre = "genre";
  String id = "id";
  
  public GenreMetadataTest() {
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
   * Test of getId method, of class GenreMetadata.
   */
  @Test
  public void testSetAndGetId() {
    System.out.println("set and getId");
    GenreMetadata instance = new GenreMetadata();
    String expResult = id;
    instance.setId(id);
    String result = instance.getId();
    assertEquals(expResult, result);
  }

  /**
   * Test of getResourceId method, of class GenreMetadata.
   */
  @Test
  public void testSetAndGetResourceId() {
    System.out.println("set and getResourceId");
    GenreMetadata instance = new GenreMetadata();
    String expResult = resourceId;
    instance.setResourceId(resourceId);
    String result = instance.getResourceId();
    assertEquals(expResult, result);
  }

  /**
   * Test of getGenre method, of class GenreMetadata.
   */
  @Test
  public void testSetAndGetGenre() {
    System.out.println("set and getGenre");
    GenreMetadata instance = new GenreMetadata();
    String expResult = genre;
    instance.setGenre(genre);
    String result = instance.getGenre();
    assertEquals(expResult, result);
  }

  /**
   * Test of toString method, of class GenreMetadata.
   */
  @Test
  public void testToString() {
    System.out.println("toString");
    GenreMetadata instance = new GenreMetadata(resourceId, genre);
    String result = instance.toString();
    assertTrue(result.contains("resourceId=" + resourceId));
    assertTrue(result.contains("genre=" + genre));
  }
  
  @Test
  public void testConstructor() {
    System.out.println("constructor");
    GenreMetadata instance = new GenreMetadata(resourceId, genre);
    assertEquals(resourceId, instance.getResourceId());
    assertEquals(genre, instance.getGenre());
  }

 @Test
  public void testMetsPropertiesWithDocument_validMets_newFormat() throws Exception {
    System.out.println("Test with valid METS file with new format");
    File file = new File("src/test/resources/mets/validMets_newFormat.xml");
    assertTrue("File exists!", file.exists());
    Document document = JaxenUtil.getDocument(file);
    List<GenreMetadata> extractGenreMetadataFromMets = MetsDocumentUtil.extractGenreMetadataFromMets(document, resourceId);
    assertTrue(extractGenreMetadataFromMets.size() == 1);
    GenreMetadata cmd = extractGenreMetadataFromMets.get(0);
    assertEquals(cmd.getResourceId(), resourceId);
    assertEquals(cmd.getGenre(),"Gedichte");
  }
  
}
