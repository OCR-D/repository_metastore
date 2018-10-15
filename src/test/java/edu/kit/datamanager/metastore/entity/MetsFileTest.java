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
public class MetsFileTest {

  String id = "ThisIsAnID";
  String fileId = "ThisIsAnFileID";
  String resourceId = "ThisIsAResourceID";
  Integer version = 1;
  String groupId = "ThisIsAGroupID";
  String mimetype = "ThisIsAMimetype";
  String use = "ThisIsAnUse";
  String url = "ThisIsAnURL";

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
    assertNull(metsFile.getFileId());
    assertNull(metsFile.getResourceId());
    assertNull(metsFile.getMimetype());
    assertNull(metsFile.getGroupId());
    assertNull(metsFile.getUse());
    assertNull(metsFile.getUrl());
    assertTrue(metsFile.toString().contains(id));
  }

  @Test
  public void testMetsFileSetGetFileId() {
    MetsFile metsFile = new MetsFile();
    metsFile.setFileId(fileId);
    assertTrue(metsFile.getFileId().equals(fileId));
    assertNull(metsFile.getId());
    assertNull(metsFile.getResourceId());
    assertNull(metsFile.getMimetype());
    assertNull(metsFile.getGroupId());
    assertNull(metsFile.getUse());
    assertNull(metsFile.getUrl());
    assertTrue(metsFile.toString().contains(fileId));
  }

  @Test
  public void testMetsFileSetGetResourceId() {
    MetsFile metsFile = new MetsFile();
    metsFile.setResourceId(resourceId);
    assertTrue(metsFile.getResourceId().equals(resourceId));
    assertNull(metsFile.getId());
    assertNull(metsFile.getFileId());
    assertNull(metsFile.getMimetype());
    assertNull(metsFile.getGroupId());
    assertNull(metsFile.getUse());
    assertNull(metsFile.getUrl());
    assertTrue(metsFile.toString().contains(resourceId));
  }

  @Test
  public void testMetsFileSetGetVersion() {
    MetsFile metsFile = new MetsFile();
    metsFile.setVersion(version);
    assertTrue(metsFile.getVersion().equals(version));
    assertNull(metsFile.getId());
    assertNull(metsFile.getResourceId());
    assertNull(metsFile.getFileId());
    assertNull(metsFile.getMimetype());
    assertNull(metsFile.getGroupId());
    assertNull(metsFile.getUse());
    assertNull(metsFile.getUrl());
    assertTrue(metsFile.toString().contains("version=" + version.intValue()));
  }

  @Test
  public void testMetsFileSetGetGroupId() {
    MetsFile metsFile = new MetsFile();
    metsFile.setGroupId(groupId);
    assertTrue(metsFile.getGroupId().equals(groupId));
    assertNull(metsFile.getId());
    assertNull(metsFile.getFileId());
    assertNull(metsFile.getResourceId());
    assertNull(metsFile.getVersion());
    assertNull(metsFile.getMimetype());
    assertNull(metsFile.getUse());
    assertNull(metsFile.getUrl());
    assertTrue(metsFile.toString().contains(groupId));
  }

  @Test
  public void testMetsFileSetGetMimetype() {
    MetsFile metsFile = new MetsFile();
    metsFile.setMimetype(mimetype);
    assertTrue(metsFile.getMimetype().equals(mimetype));
    assertNull(metsFile.getId());
    assertNull(metsFile.getFileId());
    assertNull(metsFile.getResourceId());
    assertNull(metsFile.getGroupId());
    assertNull(metsFile.getUse());
    assertNull(metsFile.getUrl());
    assertTrue(metsFile.toString().contains(mimetype));
  }

  @Test
  public void testMetsFileSetGetUse() {
    MetsFile metsFile = new MetsFile();
    metsFile.setUse(use);
    assertTrue(metsFile.getUse().equals(use));
    assertNull(metsFile.getId());
    assertNull(metsFile.getFileId());
    assertNull(metsFile.getResourceId());
    assertNull(metsFile.getGroupId());
    assertNull(metsFile.getMimetype());
    assertNull(metsFile.getUrl());
    assertTrue(metsFile.toString().contains(use));
  }

  @Test
  public void testMetsFileSetGetUrl() {
    MetsFile metsFile = new MetsFile();
    metsFile.setUrl(url);
    assertTrue(metsFile.getUrl().equals(url));
    assertNull(metsFile.getId());
    assertNull(metsFile.getFileId());
    assertNull(metsFile.getResourceId());
    assertNull(metsFile.getGroupId());
    assertNull(metsFile.getMimetype());
    assertNull(metsFile.getUse());
    assertTrue(metsFile.toString().contains(url));
  }

  @Test
  public void testMetsFileCompleteConstructorAndToString() {
    MetsFile metsFile = new MetsFile(fileId, resourceId, version, mimetype, groupId, use, url);
    metsFile.setUrl(url);
    assertNull(metsFile.getId());
    assertTrue(metsFile.toString().contains("null"));
    assertTrue(metsFile.getFileId().equals(fileId));
    assertTrue(metsFile.toString().contains(fileId));
    assertTrue(metsFile.getResourceId().equals(resourceId));
    assertTrue(metsFile.toString().contains(resourceId));
    assertTrue(metsFile.getVersion().equals(version));
    assertTrue(metsFile.toString().contains("version=" + version));
    assertTrue(metsFile.getGroupId().equals(groupId));
    assertTrue(metsFile.toString().contains(groupId));
    assertTrue(metsFile.getMimetype().equals(mimetype));
    assertTrue(metsFile.toString().contains(mimetype));
    assertTrue(metsFile.getUse().equals(use));
    assertTrue(metsFile.toString().contains(use));
    assertTrue(metsFile.getUrl().equals(url));
    assertTrue(metsFile.toString().contains(url));
  }

}
