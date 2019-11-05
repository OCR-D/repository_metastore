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

import edu.kit.ocrd.workspace.entity.MetsFile;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * Test for MetsFile.
 */
public class MetsFileTest {

  String id = "ThisIsAnID";
  String fileId = "ThisIsAnFileID";
  String resourceId = "ThisIsAResourceID";
  Integer version = 1;
  String pageId = "ThisIsAPageId";
  String mimetype = "ThisIsAMimetype";
  String use = "ThisIsAnUse";
  String url = "ThisIsAnURL";
  Boolean current = Boolean.FALSE;

  public MetsFileTest() {
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
  public void testMetsFileCreation() {
    MetsFile metsFile = new MetsFile();
    assertTrue(true);
  }

  @Test
  public void testMetsFileSetGetId() {
    MetsFile metsFile = new MetsFile();
    metsFile.setId(id);
    assertTrue(metsFile.getId().equals(id));
    assertTrue(metsFile.getCurrent());
    assertNull(metsFile.getFileId());
    assertNull(metsFile.getResourceId());
    assertNull(metsFile.getMimetype());
    assertNull(metsFile.getPageId());
    assertNull(metsFile.getUse());
    assertNull(metsFile.getUrl());
    assertTrue(metsFile.toString().contains(id));
  }

  @Test
  public void testMetsFileSetGetFileId() {
    MetsFile metsFile = new MetsFile();
    metsFile.setFileId(fileId);
    assertTrue(metsFile.getFileId().equals(fileId));
    assertTrue(metsFile.getCurrent());
    assertNull(metsFile.getId());
    assertNull(metsFile.getResourceId());
    assertNull(metsFile.getMimetype());
    assertNull(metsFile.getPageId());
    assertNull(metsFile.getUse());
    assertNull(metsFile.getUrl());
    assertTrue(metsFile.toString().contains(fileId));
  }

  @Test
  public void testMetsFileSetGetResourceId() {
    MetsFile metsFile = new MetsFile();
    metsFile.setResourceId(resourceId);
    assertTrue(metsFile.getResourceId().equals(resourceId));
    assertTrue(metsFile.getCurrent());
    assertNull(metsFile.getId());
    assertNull(metsFile.getFileId());
    assertNull(metsFile.getMimetype());
    assertNull(metsFile.getPageId());
    assertNull(metsFile.getUse());
    assertNull(metsFile.getUrl());
    assertTrue(metsFile.toString().contains(resourceId));
  }

  @Test
  public void testMetsFileSetGetCurrent() {
    MetsFile metsFile = new MetsFile();
    metsFile.setCurrent(Boolean.FALSE);
    assertTrue(metsFile.getVersion().equals(1));
    assertFalse(metsFile.getCurrent());
    assertNull(metsFile.getResourceId());
    assertNull(metsFile.getId());
    assertNull(metsFile.getFileId());
    assertNull(metsFile.getMimetype());
    assertNull(metsFile.getPageId());
    assertNull(metsFile.getUse());
    assertNull(metsFile.getUrl());
  }

  @Test
  public void testMetsFileSetGetVersion() {
    MetsFile metsFile = new MetsFile();
    metsFile.setVersion(version);
    assertTrue(metsFile.getVersion().equals(version));
    assertTrue(metsFile.getCurrent());
    assertNull(metsFile.getId());
    assertNull(metsFile.getResourceId());
    assertNull(metsFile.getFileId());
    assertNull(metsFile.getMimetype());
    assertNull(metsFile.getPageId());
    assertNull(metsFile.getUse());
    assertNull(metsFile.getUrl());
    assertTrue(metsFile.toString().contains("version=" + version.intValue()));
  }

  @Test
  public void testMetsFileSetGetPageId() {
    MetsFile metsFile = new MetsFile();
    metsFile.setPageId(pageId);
    assertTrue(metsFile.getPageId().equals(pageId));
    assertTrue(metsFile.getCurrent());
    assertNull(metsFile.getId());
    assertNull(metsFile.getFileId());
    assertNull(metsFile.getResourceId());
    assertTrue(metsFile.getVersion() == 1);
    assertNull(metsFile.getMimetype());
    assertNull(metsFile.getUse());
    assertNull(metsFile.getUrl());
    assertTrue(metsFile.toString().contains(pageId));
  }

  @Test
  public void testMetsFileSetGetMimetype() {
    MetsFile metsFile = new MetsFile();
    metsFile.setMimetype(mimetype);
    assertTrue(metsFile.getMimetype().equals(mimetype));
    assertTrue(metsFile.getCurrent());
    assertNull(metsFile.getId());
    assertNull(metsFile.getFileId());
    assertNull(metsFile.getResourceId());
    assertNull(metsFile.getPageId());
    assertNull(metsFile.getUse());
    assertNull(metsFile.getUrl());
    assertTrue(metsFile.toString().contains(mimetype));
  }

  @Test
  public void testMetsFileSetGetUse() {
    MetsFile metsFile = new MetsFile();
    metsFile.setUse(use);
    assertTrue(metsFile.getUse().equals(use));
    assertTrue(metsFile.getCurrent());
    assertNull(metsFile.getId());
    assertNull(metsFile.getFileId());
    assertNull(metsFile.getResourceId());
    assertNull(metsFile.getPageId());
    assertNull(metsFile.getMimetype());
    assertNull(metsFile.getUrl());
    assertTrue(metsFile.toString().contains(use));
  }

  @Test
  public void testMetsFileSetGetUrl() {
    MetsFile metsFile = new MetsFile();
    metsFile.setUrl(url);
    assertTrue(metsFile.getUrl().equals(url));
    assertTrue(metsFile.getCurrent());
    assertNull(metsFile.getId());
    assertNull(metsFile.getFileId());
    assertNull(metsFile.getResourceId());
    assertNull(metsFile.getPageId());
    assertNull(metsFile.getMimetype());
    assertNull(metsFile.getUse());
    assertTrue(metsFile.toString().contains(url));
  }

  @Test
  public void testMetsFileCompleteConstructorAndToString() {
    MetsFile metsFile = new MetsFile(resourceId, version, fileId, mimetype, pageId, use, url);
    metsFile.setUrl(url);
    assertNull(metsFile.getId());
    assertTrue(metsFile.getCurrent());
    assertTrue(metsFile.toString().contains("null"));
    assertTrue(metsFile.getFileId().equals(fileId));
    assertTrue(metsFile.toString().contains(fileId));
    assertTrue(metsFile.getResourceId().equals(resourceId));
    assertTrue(metsFile.toString().contains(resourceId));
    assertTrue(metsFile.getVersion().equals(version));
    assertTrue(metsFile.toString().contains("version=" + version));
    assertTrue(metsFile.getPageId().equals(pageId));
    assertTrue(metsFile.toString().contains(pageId));
    assertTrue(metsFile.getMimetype().equals(mimetype));
    assertTrue(metsFile.toString().contains(mimetype));
    assertTrue(metsFile.getUse().equals(use));
    assertTrue(metsFile.toString().contains(use));
    assertTrue(metsFile.getUrl().equals(url));
    assertTrue(metsFile.toString().contains(url));
  }

  @Test
  public void testUpdateMetsFile() {
    MetsFile metsFile = new MetsFile(resourceId, version, fileId, mimetype, pageId, use, url);
    metsFile.setUrl(url);
    String url2 = "new URL";
    MetsFile metsFile2 = metsFile.updateMetsFile(url2);
    // Old version            
    assertNull(metsFile.getId());
    assertFalse(metsFile.getCurrent());
    assertTrue(metsFile.toString().contains("null"));
    assertTrue(metsFile.getFileId().equals(fileId));
    assertTrue(metsFile.toString().contains(fileId));
    assertTrue(metsFile.getResourceId().equals(resourceId));
    assertTrue(metsFile.toString().contains(resourceId));
    assertTrue(metsFile.getVersion().equals(version));
    assertTrue(metsFile.toString().contains("version=" + version));
    assertTrue(metsFile.getPageId().equals(pageId));
    assertTrue(metsFile.toString().contains(pageId));
    assertTrue(metsFile.getMimetype().equals(mimetype));
    assertTrue(metsFile.toString().contains(mimetype));
    assertTrue(metsFile.getUse().equals(use));
    assertTrue(metsFile.toString().contains(use));
    assertTrue(metsFile.getUrl().equals(url));
    assertTrue(metsFile.toString().contains(url));
    // New version
    Integer version2 = new Integer(version + 1);
    assertNull(metsFile2.getId());
    assertTrue(metsFile2.getCurrent());
    assertTrue(metsFile2.toString().contains("null"));
    assertTrue(metsFile2.getFileId().equals(fileId));
    assertTrue(metsFile2.toString().contains(fileId));
    assertTrue(metsFile2.getResourceId().equals(resourceId));
    assertTrue(metsFile2.toString().contains(resourceId));
    assertTrue(metsFile2.getVersion().equals(version2));
    assertTrue(metsFile2.toString().contains("version=" + version2));
    assertTrue(metsFile2.getPageId().equals(pageId));
    assertTrue(metsFile2.toString().contains(pageId));
    assertTrue(metsFile2.getMimetype().equals(mimetype));
    assertTrue(metsFile2.toString().contains(mimetype));
    assertTrue(metsFile2.getUse().equals(use));
    assertTrue(metsFile2.toString().contains(use));
    assertTrue(metsFile2.getUrl().equals(url2));
    assertTrue(metsFile2.toString().contains(url2));

  }

}
