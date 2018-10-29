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
package edu.kit.datamanager.metastore.entity;

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
 * @author hartmann-v
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
    testAllAttributes(metsFile, null, Boolean.TRUE, 1, content, resourceId, before, after);
  }

  @Test
  public void testMetsDocumentWithResourceAndIdAndVersion() {
    String resourceId = "testid_0002";
    String content = "any other content";
    Date before = new Date();
    MetsDocument metsFile = new MetsDocument(resourceId, 4, content);
    assertTrue(true);
    Date after = new Date();
    testAllAttributes(metsFile, null, Boolean.TRUE, 4, content, resourceId, before, after);
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
    testAllAttributes(metsFile, id, Boolean.TRUE, 1, content, resourceId, before, after);
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
    testAllAttributes(metsFile, null, current, 1, content, resourceId, before, after);
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
    testAllAttributes(metsFile, null, Boolean.TRUE, 1, content, resourceId, before, after);
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
    testAllAttributes(metsFile, null, Boolean.TRUE, 1, newContent, resourceId, before, after);
  }

  @Test
  public void testMetsDocumentSetGetLargeContent() {
  String ALL_POSSIBLE_CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz/,;><!@#$%^&*()_-=+\\:'\"|\n\t";
 SecureRandom rnd = new SecureRandom();
    String resourceId = "testid_0007";
    String content = "old content";
    StringBuffer stringBuffer = new StringBuffer();
    for (int i = 0; i < 20000; i++) {
      stringBuffer.append(ALL_POSSIBLE_CHARS.charAt( rnd.nextInt(ALL_POSSIBLE_CHARS.length()) ));
    }
    String newContent = stringBuffer.toString();
    Date before = new Date();
    MetsDocument metsFile = new MetsDocument(resourceId, content);
    metsFile.setMetsContent(newContent);
    assertTrue(true);
    Date after = new Date();
    testAllAttributes(metsFile, null, Boolean.TRUE, 1, newContent, resourceId, before, after);
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
    testAllAttributes(metsFile, null, Boolean.TRUE, 1, content, newResourceId, before, after);
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
    testAllAttributes(metsFile, null, Boolean.TRUE, version, content, resourceId, before, after);
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
    testAllAttributes(metsFile, null, Boolean.FALSE, 1, content, resourceId, before, after);
    testAllAttributes(metsFile2, null, Boolean.TRUE, 2, newContent, resourceId, before2, after2);
    Thread.sleep(1000);
    Date before3 = new Date();
    MetsDocument metsFile3 = metsFile2.updateMetsContent(content);
    assertTrue(true);
    Date after3 = new Date();
    testAllAttributes(metsFile, null, Boolean.FALSE, 1, content, resourceId, before, after);
    testAllAttributes(metsFile2, null, Boolean.FALSE, 2, newContent, resourceId, before2, after2);
    testAllAttributes(metsFile3, null, Boolean.TRUE, 3, content, resourceId, before3, after3);
  }
//
//  @Test
//  public void testMetsDocumentSetGetFileId() {
//    MetsDocument metsFile = new MetsDocument();
//    metsFile.setFileId(fileId);
//    assertTrue(metsFile.getFileId().equals(fileId));
//    assertNull(metsFile.getId());
//    assertNull(metsFile.getResourceId());
//    assertNull(metsFile.getMimetype());
//    assertNull(metsFile.getGroupId());
//    assertNull(metsFile.getUse());
//    assertNull(metsFile.getUrl());
//    assertTrue(metsFile.toString().contains(fileId));
//  }
//
//  @Test
//  public void testMetsDocumentSetGetResourceId() {
//    MetsDocument metsFile = new MetsDocument();
//    metsFile.setResourceId(resourceId);
//    assertTrue(metsFile.getResourceId().equals(resourceId));
//    assertNull(metsFile.getId());
//    assertNull(metsFile.getFileId());
//    assertNull(metsFile.getMimetype());
//    assertNull(metsFile.getGroupId());
//    assertNull(metsFile.getUse());
//    assertNull(metsFile.getUrl());
//    assertTrue(metsFile.toString().contains(resourceId));
//  }
//
//  @Test
//  public void testMetsDocumentSetGetVersion() {
//    MetsDocument metsFile = new MetsDocument();
//    metsFile.setVersion(version);
//    assertTrue(metsFile.getVersion().equals(version));
//    assertNull(metsFile.getId());
//    assertNull(metsFile.getResourceId());
//    assertNull(metsFile.getFileId());
//    assertNull(metsFile.getMimetype());
//    assertNull(metsFile.getGroupId());
//    assertNull(metsFile.getUse());
//    assertNull(metsFile.getUrl());
//    assertTrue(metsFile.toString().contains("version=" + version.intValue()));
//  }
//
//  @Test
//  public void testMetsDocumentSetGetGroupId() {
//    MetsDocument metsFile = new MetsDocument();
//    metsFile.setGroupId(groupId);
//    assertTrue(metsFile.getGroupId().equals(groupId));
//    assertNull(metsFile.getId());
//    assertNull(metsFile.getFileId());
//    assertNull(metsFile.getResourceId());
//    assertNull(metsFile.getVersion());
//    assertNull(metsFile.getMimetype());
//    assertNull(metsFile.getUse());
//    assertNull(metsFile.getUrl());
//    assertTrue(metsFile.toString().contains(groupId));
//  }
//
//  @Test
//  public void testMetsDocumentSetGetMimetype() {
//    MetsDocument metsFile = new MetsDocument();
//    metsFile.setMimetype(mimetype);
//    assertTrue(metsFile.getMimetype().equals(mimetype));
//    assertNull(metsFile.getId());
//    assertNull(metsFile.getFileId());
//    assertNull(metsFile.getResourceId());
//    assertNull(metsFile.getGroupId());
//    assertNull(metsFile.getUse());
//    assertNull(metsFile.getUrl());
//    assertTrue(metsFile.toString().contains(mimetype));
//  }
//
//  @Test
//  public void testMetsDocumentSetGetUse() {
//    MetsDocument metsFile = new MetsDocument();
//    metsFile.setUse(use);
//    assertTrue(metsFile.getUse().equals(use));
//    assertNull(metsFile.getId());
//    assertNull(metsFile.getFileId());
//    assertNull(metsFile.getResourceId());
//    assertNull(metsFile.getGroupId());
//    assertNull(metsFile.getMimetype());
//    assertNull(metsFile.getUrl());
//    assertTrue(metsFile.toString().contains(use));
//  }
//
//  @Test
//  public void testMetsDocumentSetGetUrl() {
//    MetsDocument metsFile = new MetsDocument();
//    metsFile.setUrl(url);
//    assertTrue(metsFile.getUrl().equals(url));
//    assertNull(metsFile.getId());
//    assertNull(metsFile.getFileId());
//    assertNull(metsFile.getResourceId());
//    assertNull(metsFile.getGroupId());
//    assertNull(metsFile.getMimetype());
//    assertNull(metsFile.getUse());
//    assertTrue(metsFile.toString().contains(url));
//  }
//
//  @Test
//  public void testMetsDocumentCompleteConstructorAndToString() {
//    MetsDocument metsFile = new MetsDocument(resourceId, version, fileId, mimetype, groupId, use, url);
//    metsFile.setUrl(url);
//    assertNull(metsFile.getId());
//    assertTrue(metsFile.toString().contains("null"));
//    assertTrue(metsFile.getFileId().equals(fileId));
//    assertTrue(metsFile.toString().contains(fileId));
//    assertTrue(metsFile.getResourceId().equals(resourceId));
//    assertTrue(metsFile.toString().contains(resourceId));
//    assertTrue(metsFile.getVersion().equals(version));
//    assertTrue(metsFile.toString().contains("version=" + version));
//    assertTrue(metsFile.getGroupId().equals(groupId));
//    assertTrue(metsFile.toString().contains(groupId));
//    assertTrue(metsFile.getMimetype().equals(mimetype));
//    assertTrue(metsFile.toString().contains(mimetype));
//    assertTrue(metsFile.getUse().equals(use));
//    assertTrue(metsFile.toString().contains(use));
//    assertTrue(metsFile.getUrl().equals(url));
//    assertTrue(metsFile.toString().contains(url));
//  }
  public void testAllAttributes(MetsDocument document, String id, Boolean current, Integer version,
          String content, String resourceId, Date before, Date after) {
    assertTrue(document.getCurrent().equals(current));
    if (document.getId() != null) {
    assertTrue(document.getId().equals(id));
    } else {
      assertNull(id);
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
