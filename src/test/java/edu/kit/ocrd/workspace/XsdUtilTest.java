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
package edu.kit.ocrd.workspace;

import edu.kit.ocrd.workspace.XsdUtil;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * Test for XsdUtil.
 */
public class XsdUtilTest {

  public XsdUtilTest() {
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
   * Test of getNamespaceAndVersionFromXsd method, of class XsdUtil.
   */
  @Test
  public void testGetNamespaceAndVersionFromXsd() throws IOException {
    System.out.println("getNamespaceAndVersionFromXsd");
    XsdUtil instance = new XsdUtil();
    String expResult = "http://schema.primaresearch.org/PAGE/gts/pagecontent/2018-07-15";
    String fileContent = FileUtils.readFileToString(new File("src/test/resources/xsd/2018.xsd"), Charset.defaultCharset());
    String result = instance.getNamespaceAndVersionFromXsd(fileContent);
    assertEquals(expResult, result);
  }

  /**
   * Test of getNamespaceAndVersionFromXsd method, of class XsdUtil.
   */
  @Test
  public void testGetNoNamespaceAndVersionFromXsd() throws IOException {
    System.out.println("getNoNamespaceAndVersionFromXsd");
    XsdUtil instance = new XsdUtil();
    String expResult = XsdUtil.NO_NAMESPACE_DEFINED;
    String fileContent = FileUtils.readFileToString(new File("src/test/resources/xsd/invalid.xsd"), Charset.defaultCharset());
    String result = instance.getNamespaceAndVersionFromXsd(fileContent);
    assertEquals(expResult, result);
  }

  /**
   * Test of getNamespace method, of class XsdUtil.
   */
  @Test
  public void testGetNamespace() throws IOException {
    System.out.println("getNamespace");
    XsdUtil instance = new XsdUtil();
    String expResult = "http://schema.primaresearch.org/PAGE/gts/pagecontent/2018-07-15";
    String fileContent = FileUtils.readFileToString(new File("src/test/resources/xsd/2018.xsd"), Charset.defaultCharset());
    instance.getNamespaceAndVersionFromXsd(fileContent);
    String result = instance.getNamespace();
    assertEquals(expResult, result);
  }

}
