/*
 * Copyright 2018 Karlsruhe Institute of Technology.
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

import edu.kit.ocrd.workspace.entity.MdType;
import edu.kit.ocrd.workspace.entity.SectionDocument;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * Test for SectionDocument.
 */
public class SectionDocumentTest {

  public SectionDocumentTest() {
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

  @Test
  public void testSectionDocumentCreation() {
    System.out.println("Test default constructor.");
    SectionDocument sectionDocument = new SectionDocument();
    assertTrue(true);
  }

  @Test
  public void testSectionDocumentCreationWithParamter1() throws Exception {
    System.out.println("Test constructor with parameters");
    String resourceId = "id_0001";
    String prefix = "oai_dc";
    String sectionId = "section_id_0001";
    MdType mdType = MdType.DC;
    String sectionOtherMdType = "any type";
    String sectionDocument = "content of section document";
    SectionDocument secDocument;
    secDocument = new SectionDocument(resourceId, null, null, null, null, null, null);
    testAllAttributes(secDocument, null, resourceId, 1, null, null, null, null, null);
  }

  @Test
  public void testSectionDocumentCreationWithParamter2() throws Exception {
    System.out.println("Test constructor with parameters");
    String resourceId = "id_0001";
    String prefix = "oai_dc";
    String sectionId = "section_id_0001";
    MdType mdType = MdType.DC;
    String sectionOtherMdType = "any type";
    String sectionDocument = "content of section document";
    SectionDocument secDocument;
    secDocument = new SectionDocument(resourceId, 1, null, null, null, null, null);
    testAllAttributes(secDocument, null, resourceId, 1, null, null, null, null, null);
  }

  @Test
  public void testSectionDocumentCreationWithParamter3() throws Exception {
    System.out.println("Test constructor with parameters");
    String resourceId = "id_0001";
    String prefix = "oai_dc";
    String sectionId = "section_id_0001";
    MdType mdType = MdType.DC;
    String sectionOtherMdType = "any type";
    String sectionDocument = "content of section document";
    SectionDocument secDocument;
    secDocument = new SectionDocument(resourceId, 1, prefix, null, null, null, null);
    testAllAttributes(secDocument, null, resourceId, 1, prefix, null, null, null, null);
  }

  @Test
  public void testSectionDocumentCreationWithParamter4() throws Exception {
    System.out.println("Test constructor with parameters");
    String resourceId = "id_0001";
    String prefix = "oai_dc";
    String sectionId = "section_id_0001";
    MdType mdType = MdType.DC;
    String sectionOtherMdType = "any type";
    String sectionDocument = "content of section document";
    SectionDocument secDocument;
    secDocument = new SectionDocument(resourceId, 1, prefix, sectionId, null, null, null);
    testAllAttributes(secDocument, null, resourceId, 1, prefix, sectionId, null, null, null);
  }

  @Test
  public void testSectionDocumentCreationWithParamter5() throws Exception {
    System.out.println("Test constructor with parameters");
    String resourceId = "id_0001";
    String prefix = "oai_dc";
    String sectionId = "section_id_0001";
    MdType mdType = MdType.DC;
    String sectionOtherMdType = "any type";
    String sectionDocument = "content of section document";
    SectionDocument secDocument;
    secDocument = new SectionDocument(resourceId, 1, prefix, sectionId, mdType, null, null);
    testAllAttributes(secDocument, null, resourceId, 1, prefix, sectionId, mdType, null, null);
  }

  @Test
  public void testSectionDocumentCreationWithParamter6() throws Exception {
    System.out.println("Test constructor with parameters");
    String resourceId = "id_0001";
    String prefix = "oai_dc";
    String sectionId = "section_id_0001";
    MdType mdType = MdType.DC;
    String sectionOtherMdType = "any type";
    String sectionDocument = "content of section document";
    SectionDocument secDocument;
    secDocument = new SectionDocument(resourceId, 1, prefix, sectionId, mdType, sectionOtherMdType, null);
    testAllAttributes(secDocument, null, resourceId, 1, prefix, sectionId, mdType, sectionOtherMdType, null);
  }

  @Test
  public void testSectionDocumentCreationWithParamter7() throws Exception {
    System.out.println("Test constructor with parameters");
    String resourceId = "id_0001";
    String prefix = "oai_dc";
    String sectionId = "section_id_0001";
    MdType mdType = MdType.DC;
    String sectionOtherMdType = "any type";
    String sectionDocument = "content of section document";
    SectionDocument secDocument;
    secDocument = new SectionDocument(resourceId, 1, prefix, sectionId, mdType, sectionOtherMdType, sectionDocument);
    testAllAttributes(secDocument, null, resourceId, 1, prefix, sectionId, mdType, sectionOtherMdType, sectionDocument);
  }

  /**
   * Test of setId method, of class SectionDocument.
   */
  @Test
  public void testSetId() {
    System.out.println("setId");
    String id = "id_0002";
    SectionDocument instance = new SectionDocument();
    instance.setId(id);
    testAllAttributes(instance, id, null, 1, null, null, null, null, null);
  }

  /**
   * Test of setResourceId method, of class SectionDocument.
   */
  @Test
  public void testSetResourceId() {
    System.out.println("setResourceId");
    String resourceId = "resourceid";
    SectionDocument instance = new SectionDocument();
    instance.setResourceId(resourceId);
    testAllAttributes(instance, null, resourceId, 1, null, null, null, null, null);
  }

  /**
   * Test of setVersion method, of class SectionDocument.
   */
  @Test
  public void testSetVersion() {
    System.out.println("setVersion");
    Integer version = 5;
    SectionDocument instance = new SectionDocument();
    instance.setVersion(version);
    testAllAttributes(instance, null, null, version, null, null, null, null, null);
  }

  /**
   * Test of setPrefix method, of class SectionDocument.
   */
  @Test
  public void testSetPrefix() {
    System.out.println("setPrefix");
    String prefix = "prefix";
    SectionDocument instance = new SectionDocument();
    instance.setPrefix(prefix);
    testAllAttributes(instance, null, null, 1, prefix, null, null, null, null);
  }

  /**
   * Test of setSectionId method, of class SectionDocument.
   */
  @Test
  public void testSetSectionId() {
    System.out.println("setSectionId");
    String sectionId = "sectionid";
    SectionDocument instance = new SectionDocument();
    instance.setSectionId(sectionId);
    testAllAttributes(instance, null, null, 1, null, sectionId, null, null, null);
  }

  /**
   * Test of setSectionMdType method, of class SectionDocument.
   */
  @Test
  public void testSetSectionMdType() {
    System.out.println("setSectionMdType");
    MdType sectionMdType = MdType.PREMIS;
    SectionDocument instance = new SectionDocument();
    instance.setSectionMdType(sectionMdType);
    testAllAttributes(instance, null, null, 1, null, null, sectionMdType, null, null);
  }

  /**
   * Test of setSectionOtherMdType method, of class SectionDocument.
   */
  @Test
  public void testSetSectionOtherMdType() {
    System.out.println("setSectionOtherMdType");
    String sectionOtherMdType = "sectionOtherMdType";
    SectionDocument instance = new SectionDocument();
    instance.setSectionOtherMdType(sectionOtherMdType);
    testAllAttributes(instance, null, null, 1, null, null, null, sectionOtherMdType, null);
  }

  /**
   * Test of setSectionDocument method, of class SectionDocument.
   */
  @Test
  public void testSetSectionDocument() {
    System.out.println("setSectionDocument");
    String sectionDocument = "any section content";
    SectionDocument instance = new SectionDocument();
    instance.setSectionDocument(sectionDocument);
    testAllAttributes(instance, null, null, 1, null, null, null, null, sectionDocument);
  }

  /**
   * Test of updateSectionDocument method, of class SectionDocument.
   */
  @Test
  public void testUpdateSectionDocument() {
    System.out.println("updateSectionDocument");
    String sectionDocument = "new section content";
    SectionDocument instance = new SectionDocument();
    testAllAttributes(instance, null, null, 1, null, null, null, null, null);

    SectionDocument result = instance.updateSectionDocument(sectionDocument);
    assertEquals(result.getSectionDocument(), sectionDocument);
    testAllAttributes(result, null, null, 2, null, null, null, null, sectionDocument);
  }

  /**
   * Test all attribute at once.
   *
   * @param document instance to test.
   * @param id expected id.
   * @param resourceId expected resourceId
   * @param version expected version
   * @param prefix expected prefix
   * @param sectionId expected sectionId
   * @param mdType expected MdType
   * @param otherType expected other type
   * @param sectionDocument expected section document
   */
  public void testAllAttributes(SectionDocument document, String id, String resourceId, Integer version, String prefix, String sectionId,
          MdType mdType, String otherType, String sectionDocument) {
    if (document.getId() != null) {
      assertTrue(document.getId().equals(id));
    } else {
      assertNull(id);
    }
    compare(document.getResourceId(), resourceId);
    compare(document.getVersion(), version);
    compare(document.getPrefix(), prefix);
    compare(document.getSectionId(), sectionId);
    compare(document.getSectionMdType(), mdType);
    compare(document.getSectionOtherMdType(), otherType);
    compare(document.getSectionDocument(), sectionDocument);
    String docString = document.toString();
    contains(docString, id);
    contains(docString, resourceId);
    contains(docString, version);
    contains(docString, prefix);
    contains(docString, sectionId);
    contains(docString, mdType);
    contains(docString, otherType);
    contains(docString, sectionDocument);
  }

  /**
   * Compare expected value with result.
   *
   * @param expected expected value.
   * @param result result.
   */
  public void compare(Object expected, Object result) {
    if (expected != null) {
      assertTrue(expected.equals(result));
    } else {
      assertNull(result);
    }

  }

  /**
   * Compare expected value with result.
   *
   * @param expected expected value.
   * @param result result.
   */
  public void contains(String expected, Object result) {
    if (result != null) {
      assertTrue(expected.contains(result.toString()));
    } else {
      assertTrue(expected.contains("null"));
    }

  }

}
