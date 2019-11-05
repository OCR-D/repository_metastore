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

import edu.kit.ocrd.workspace.entity.MetsDocument;
import java.security.SecureRandom;
import java.util.Date;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * Tests for MetsDocument.
 */
public class MetsDocumentTest {

  public MetsDocumentTest() {
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
  public void testMetsDocumentCreation() {
    MetsDocument metsFile = new MetsDocument();
    assertTrue(true);
  }

  @Test
  public void testMetsDocumentWithResourceAndId() {
    String resourceId = "testid_0001";
    String content = "any content";
    Date before = new Date();
    MetsDocument metsFile = new MetsDocument(resourceId, content);
    assertTrue(true);
    Date after = new Date();
    testAllAttributes(metsFile, null, null, Boolean.TRUE, 1, content, resourceId, before, after);
  }

  @Test
  public void testMetsDocumentWithResourceAndIdAndVersion() {
    String resourceId = "testid_0002";
    String content = "any other content";
    Date before = new Date();
    MetsDocument metsFile = new MetsDocument(resourceId, 4, content);
    assertTrue(true);
    Date after = new Date();
    testAllAttributes(metsFile, null, null, Boolean.TRUE, 4, content, resourceId, before, after);
  }

  @Test
  public void testMetsDocumentSetGetId() {
    String resourceId = "testid_0003";
    String content = "special content";
    String id = "some very special id";
    Date before = new Date();
    MetsDocument metsFile = new MetsDocument(resourceId, content);
    metsFile.setId(id);
    assertTrue(true);
    Date after = new Date();
    testAllAttributes(metsFile, id, null, Boolean.TRUE, 1, content, resourceId, before, after);
  }

  @Test
  public void testMetsDocumentSetGetRepoId() {
    String resourceId = "testid_0003a";
    String content = "repoid content";
    String repoId = "repoId";
    Date before = new Date();
    MetsDocument metsFile = new MetsDocument(resourceId, content);
    metsFile.setRepoId(repoId);
    assertTrue(true);
    Date after = new Date();
    testAllAttributes(metsFile, null, repoId, Boolean.TRUE, 1, content, resourceId, before, after);
  }

  @Test
  public void testMetsDocumentSetGetCurrent() {
    String resourceId = "testid_0004";
    String content = "short content";
    Boolean current = false;
    Date before = new Date();
    MetsDocument metsFile = new MetsDocument(resourceId, content);
    metsFile.setCurrent(current);
    assertTrue(true);
    Date after = new Date();
    testAllAttributes(metsFile, null, null, current, 1, content, resourceId, before, after);
  }

  @Test
  public void testMetsDocumentSetGetLastModified() {
    String resourceId = "testid_0005";
    String content = "<xml>Content</xml>";
    Date lastModified = new Date();
    Date before = lastModified;
    MetsDocument metsFile = new MetsDocument(resourceId, content);
    metsFile.setLastModified(lastModified);
    assertTrue(true);
    Date after = lastModified;
    testAllAttributes(metsFile, null, null, Boolean.TRUE, 1, content, resourceId, before, after);
  }

  @Test
  public void testMetsDocumentSetGetContent() {
    String resourceId = "testid_0006";
    String content = "old content";
    String newContent = "totally new content";
    Date before = new Date();
    MetsDocument metsFile = new MetsDocument(resourceId, content);
    metsFile.setMetsContent(newContent);
    assertTrue(true);
    Date after = new Date();
    testAllAttributes(metsFile, null, null, Boolean.TRUE, 1, newContent, resourceId, before, after);
  }

  @Test
  public void testMetsDocumentSetGetLargeContent() {
    String ALL_POSSIBLE_CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz/,;><!@#$%^&*()_-=+\\:'\"|\n\t";
    SecureRandom rnd = new SecureRandom();
    String resourceId = "testid_0007";
    String content = "old content";
    StringBuilder stringBuffer = new StringBuilder();
    for (int i = 0; i < 20000; i++) {
      stringBuffer.append(ALL_POSSIBLE_CHARS.charAt(rnd.nextInt(ALL_POSSIBLE_CHARS.length())));
    }
    String newContent = stringBuffer.toString();
    Date before = new Date();
    MetsDocument metsFile = new MetsDocument(resourceId, content);
    metsFile.setMetsContent(newContent);
    assertTrue(true);
    Date after = new Date();
    testAllAttributes(metsFile, null, null, Boolean.TRUE, 1, newContent, resourceId, before, after);
  }

  @Test
  public void testMetsDocumentSetGetResourceId() {
    String resourceId = "testid_0008";
    String content = ".k<jshowtg.zjkhseotlkSH";
    String newResourceId = "aherhoioher38y49t";
    Date before = new Date();
    MetsDocument metsFile = new MetsDocument(resourceId, content);
    metsFile.setResourceId(newResourceId);
    assertTrue(true);
    Date after = new Date();
    testAllAttributes(metsFile, null, null, Boolean.TRUE, 1, content, newResourceId, before, after);
  }

  @Test
  public void testMetsDocumentSetGetVersion() throws InterruptedException {
    String resourceId = "testid_0009";
    String content = "&^#$^HGYFM<BLvkcj07874&^%*ugkg#&";
    Integer version = 1982634;
    Date before = new Date();
    MetsDocument metsFile = new MetsDocument(resourceId, content);
    metsFile.setVersion(version);
    Date after = new Date();
    assertTrue(true);
    testAllAttributes(metsFile, null, null, Boolean.TRUE, version, content, resourceId, before, after);
  }

  @Test
  public void testMetsDocumentUpdateDocument() throws InterruptedException {
    String resourceId = "testid_0010";
    String content = "ioewr9828645^&^%*&^$";
    String newContent = "aherhoioher38y49t";
    Date before = new Date();
    MetsDocument metsFile = new MetsDocument(resourceId, content);
    Date after = new Date();
    Thread.sleep(1000);
    Date before2 = new Date();
    MetsDocument metsFile2 = metsFile.updateMetsContent(newContent);
    assertTrue(true);
    Date after2 = new Date();
    testAllAttributes(metsFile, null, null, Boolean.FALSE, 1, content, resourceId, before, after);
    testAllAttributes(metsFile2, null, null, Boolean.TRUE, 2, newContent, resourceId, before2, after2);
    Thread.sleep(1000);
    Date before3 = new Date();
    MetsDocument metsFile3 = metsFile2.updateMetsContent(content);
    assertTrue(true);
    Date after3 = new Date();
    testAllAttributes(metsFile, null, null, Boolean.FALSE, 1, content, resourceId, before, after);
    testAllAttributes(metsFile2, null, null, Boolean.FALSE, 2, newContent, resourceId, before2, after2);
    testAllAttributes(metsFile3, null, null, Boolean.TRUE, 3, content, resourceId, before3, after3);
  }

  /**
   * Test all attribute at once.
   *
   * @param document instance to test.
   * @param id expected id.
   * @param repoId expected repoId
   * @param current expected current
   * @param version expected version
   * @param content expected content
   * @param resourceId expected resourceId
   * @param before expected date before this date
   * @param after expected date after this date
   */
  public void testAllAttributes(MetsDocument document, String id, String repoId, Boolean current, Integer version,
          String content, String resourceId, Date before, Date after) {
    assertTrue(document.getCurrent().equals(current));
    if (document.getId() != null) {
      assertTrue(document.getId().equals(id));
    } else {
      assertNull(id);
    }
    if (document.getRepoId() != null) {
      assertTrue(document.getRepoId().equals(repoId));
    } else {
      assertNull(repoId);
    }
    assertTrue(document.getMetsContent().equals(content));
    assertTrue(document.getResourceId().equals(resourceId));
    if (before != null) {
      assertTrue(document.getLastModified().compareTo(before) >= 0);
    }
    if (after != null) {
      assertTrue(document.getLastModified().compareTo(after) <= 0);
    }
    assertTrue(document.getVersion().equals(version));
    assertTrue(document.toString().contains(current.toString()));
    assertTrue(document.toString().contains(version.toString()));
    assertTrue(document.toString().contains(content));
    assertTrue(document.toString().contains(resourceId));
  }

}
