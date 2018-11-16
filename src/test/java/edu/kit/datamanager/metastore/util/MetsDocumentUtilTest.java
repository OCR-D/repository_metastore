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
package edu.kit.datamanager.metastore.util;

import edu.kit.datamanager.metastore.entity.MetsFile;
import edu.kit.datamanager.metastore.entity.MetsProperties;
import java.io.File;
import java.util.List;
import org.fzk.tools.xml.JaxenUtil;
import org.jdom.Document;
import org.jdom.Namespace;
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
public class MetsDocumentUtilTest {
  
  public MetsDocumentUtilTest() {
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
   * Test of extractMetsFiles method, of class MetsDocumentUtil.
   * @throws java.lang.Exception
   */
  @Test
  public void testConstructor() {
    MetsDocumentUtil metsDocumentUtil = new MetsDocumentUtil();
    assertNotNull(metsDocumentUtil);
  }
  /**
   * Test of extractMetsFiles method, of class MetsDocumentUtil.
   * @throws java.lang.Exception
   */
  @Test
  public void testExtractMetsFiles() throws Exception {
    System.out.println("extractMetsFiles");
    File file = new File("src/test/resources/mets/validMets.xml");
    assertTrue("File exists!", file.exists());
    String resourceId = "resourceId";
    Integer version = 3;
    Document metsDocument = JaxenUtil.getDocument(file);
    List<MetsFile> expResult = null;
    List<MetsFile> result = MetsDocumentUtil.extractMetsFiles(metsDocument, resourceId, version);
    System.out.println(result.size());
//    assertEquals(result);
    // TODO review the generated test code and remove the default call to fail.
  }

  /**
   * Test of extractProperties method, of class MetsDocumentUtil.
   */
  @Test
  public void testExtractProperties() throws Exception {
    System.out.println("extractProperties");
    File file = new File("src/test/resources/mets/validMets.xml");
    assertTrue("File exists!", file.exists());
    String resourceId = "resourceId";
    Integer version = 3;
    Document metsDocument = JaxenUtil.getDocument(file);
    MetsProperties metsProperties = MetsDocumentUtil.extractProperties(metsDocument, resourceId, version);
    assertEquals(metsProperties.getTitle(),"Der Herold");
    assertEquals(metsProperties.getPpn(),"PPN767137728");
    assertEquals(metsProperties.getResourceId(), resourceId);
  }
  
}
