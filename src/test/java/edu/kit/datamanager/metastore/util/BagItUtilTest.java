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
package edu.kit.datamanager.metastore.util;

import edu.kit.datamanager.metastore.exception.BagItException;
import gov.loc.repository.bagit.domain.Bag;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
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
public class BagItUtilTest {

  public BagItUtilTest() {
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
   * Test of buildBag method, of class BagItUtil.
   */
  @Test
  public void testBuildBag() {
    System.out.println("buildBagInvalidDirectory");
    File payLoadPath = new File("/file/not/exists.ocrd.zip");
    try {
      Bag result = BagItUtil.buildBag(payLoadPath);
      assertTrue(false);
    } catch (BagItException bie) {
      assertTrue(bie.getMessage().contains("/file/not/exists"));
    }
  }

  /**
   * Test of buildBag method, of class BagItUtil.
   */
  @Test
  public void testBuildBag1() {
    System.out.println("buildBag");
    File payLoadPath = new File("src/test/resources/bagit/data");
    try {
      Bag result = BagItUtil.buildBag(payLoadPath);
      assertTrue(true);
    } catch (BagItException bie) {
      assertTrue(false);
    }
  }

  /**
   * Test of readBag method, of class BagItUtil.
   */
  @Test
  public void testReadValidBag() {
    System.out.println("readBag");
    Path pathToBag = Paths.get("src/test/resources/bagit/", "validBag");
    Bag result = BagItUtil.readBag(pathToBag);
    assertTrue(true);
  }

  /**
   * Test of readBag method, of class BagItUtil.
   */
  @Test
  public void testReadInvalidBag1() {
    System.out.println("readBag");

    try {
      Path pathToBag = Paths.get("src/test/resources/bagit/", "invalidChecksum");
      Bag result = BagItUtil.readBag(pathToBag);
      assertTrue(false);
    } catch (BagItException bie) {
      System.out.println(bie.getMessage());
      assertTrue(true);
    }
  }

  /**
   * Test of readBag method, of class BagItUtil.
   */
  @Test
  public void testReadInvalidBag2() {
    System.out.println("readBag");

    try {
      Path pathToBag = Paths.get("src/test/resources/bagit/", "invalidOxum");
      Bag result = BagItUtil.readBag(pathToBag);
      assertTrue(false);
    } catch (BagItException bie) {
      System.out.println(bie.getMessage());
      assertTrue(true);
    }
  }

  /**
   * Test of readBag method, of class BagItUtil.
   */
  @Test
  public void testReadInvalidBag3() {
    System.out.println("readBag");

    try {
      Path pathToBag = Paths.get("src/test/resources/bagit/", "invalidVersion");
      Bag result = BagItUtil.readBag(pathToBag);
      assertTrue(false);
    } catch (BagItException bie) {
      System.out.println(bie.getMessage());
      assertTrue(true);
    }
  }

  /**
   * Test of readBag method, of class BagItUtil.
   */
  @Test
  public void testReadInvalidBag4() {
    System.out.println("readBag");

    try {
      Path pathToBag = Paths.get("src/test/resources/bagit/", "notComplete");
      Bag result = BagItUtil.readBag(pathToBag);
      assertTrue(false);
    } catch (BagItException bie) {
      System.out.println(bie.getMessage());
      assertTrue(true);
    }
  }

  /**
   * Test of readBag method, of class BagItUtil.
   */
  @Test
  public void testReadInvalidBag5() {
    System.out.println("readBag");

    try {
      Path pathToBag = Paths.get("src/test/resources/bagit/", "notExists");
      Bag result = BagItUtil.readBag(pathToBag);
      assertTrue(false);
    } catch (BagItException bie) {
      System.out.println(bie.getMessage());
      assertTrue(true);
    }
  }
//
//  /**
//   * Test of validateBagit method, of class BagItUtil.
//   */
//  @Test
//  public void testValidateBagit() {
//    System.out.println("validateBagit");
//    Bag bag = null;
//    boolean expResult = false;
//    boolean result = BagItUtil.validateBagit(bag);
//    assertEquals(expResult, result);
//    // TODO review the generated test code and remove the default call to fail.
//    fail("The test case is a prototype.");
//  }

  /**
   * Test of printBagItInformation method, of class BagItUtil.
   */
  @Test
  public void testPrintBagItInformation() {
    System.out.println("printBagItInformation");
    Path pathToBag = Paths.get("src/test/resources/bagit/", "validBag");
    Bag bag = BagItUtil.readBag(pathToBag);
    BagItUtil.printBagItInformation(bag);
    assertTrue(true);
  }

  /**
   * Test of printBagItInformation method, of class BagItUtil.
   */
  @Test
  public void testBagItUtilClass() {
    System.out.println("create Instance");
    BagItUtil util = new BagItUtil();
    assertNotNull(util);
  }

}
