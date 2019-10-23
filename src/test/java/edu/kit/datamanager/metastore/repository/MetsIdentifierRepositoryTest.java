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
package edu.kit.datamanager.metastore.repository;

import com.arangodb.springframework.core.ArangoOperations;
import edu.kit.datamanager.metastore.entity.MetsIdentifier;
import edu.kit.datamanager.metastore.runner.CrudRunner;
import java.util.Collection;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MetsIdentifierRepositoryTest {
   @Autowired
  private ArangoOperations operations;
  /**
   * Repository persisting METS identifiers.
   */
  @Autowired
  private MetsIdentifierRepository metsIdentifierRepository;
 
  public MetsIdentifierRepositoryTest() {
  }
  
  @BeforeClass
  public static void setUpClass() {
  }
  
  @AfterClass
  public static void tearDownClass() {
  }
  
  @Before
  public void setUp() {
    try {
      operations.dropDatabase();
    } catch (DataAccessException dae) {
      System.out.println("This message should be printed only once!");
      System.out.println(dae.toString());
    }
      System.out.println("*******************************          Identifier  Metadata         ***************************************************");
      Collection<MetsIdentifier> metsIdentifierList = CrudRunner.createMetsIdentifier();
      for (MetsIdentifier metsIdentifier : metsIdentifierList) {
        metsIdentifierRepository.save(metsIdentifier);
      }
  }
  
  @After
  public void tearDown() {
  }

  @Test
  public void testCountDataSets() {
    System.out.println("repo.count()");
    assertEquals(8, metsIdentifierRepository.count());
  }
  /**
   * Test of findByResourceId method, of class MetsIdentifierRepository.
   */
  @Test
  public void testFindByResourceId() {
    System.out.println("findByResourceId");
    String resourceId = "id_0015";
    MetsIdentifierRepository instance = metsIdentifierRepository;
    Iterable<MetsIdentifier> result = instance.findByResourceId(resourceId);
    MetsIdentifier mi = result.iterator().next();
    assertEquals(resourceId, mi.getResourceId());
    assertEquals("urn", mi.getType());
    assertEquals("urn1", mi.getIdentifier());
  }
  
  /**
   * Test of findByResourceIdAndType method, of class MetsIdentifierRepository.
   */
  @Test
  public void testFindByResourceIdAndType() {
    System.out.println("findByResourceIdAndType");
    String resourceId = "id_0017";
    String type = "handle";
    MetsIdentifierRepository instance = metsIdentifierRepository;
    Iterable<MetsIdentifier> result = instance.findByResourceIdAndType(resourceId, type);
    MetsIdentifier mi = result.iterator().next();
    assertEquals(resourceId, mi.getResourceId());
    assertEquals("handle", mi.getType());
    assertEquals("handle2", mi.getIdentifier());
  }

  /**
   * Test of findByIdentifier method, of class MetsIdentifierRepository.
   */
  @Test
  public void testFindByIdentifier() {
    System.out.println("findByIdentifier");
    String identifier = "purl1";
    MetsIdentifierRepository instance = metsIdentifierRepository;
    Iterable<MetsIdentifier> result = instance.findByIdentifier(identifier);
    MetsIdentifier mi = result.iterator().next();
    assertEquals("id_0002", mi.getResourceId());
    assertEquals("purl", mi.getType());
    assertEquals(identifier, mi.getIdentifier());
  }

  /**
   * Test of findByIdentifierAndType method, of class MetsIdentifierRepository.
   */
  @Test
  public void testFindByIdentifierAndType() {
    System.out.println("findByIdentifierAndType");
    String identifier = "url2";
    String type = "url";
    MetsIdentifierRepository instance = metsIdentifierRepository;
    Iterable<MetsIdentifier> result = instance.findByIdentifierAndType(identifier, type);
    MetsIdentifier mi = result.iterator().next();
    assertEquals("id_0017", mi.getResourceId());
    assertEquals(type, mi.getType());
    assertEquals(identifier, mi.getIdentifier());
  }
}
