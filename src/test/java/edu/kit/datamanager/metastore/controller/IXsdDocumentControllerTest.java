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
package edu.kit.datamanager.metastore.controller;

import com.arangodb.springframework.core.ArangoOperations;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import edu.kit.datamanager.metastore.repository.XmlSchemaDefinitionRepository;
import edu.kit.datamanager.metastore.runner.CrudRunner;
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithSecurityContextTestExecutionListener;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.ServletTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 *
 * Integration test for MetsFileController
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestExecutionListeners(listeners = {ServletTestExecutionListener.class,
  DependencyInjectionTestExecutionListener.class,
  DirtiesContextTestExecutionListener.class,
  TransactionalTestExecutionListener.class,
  WithSecurityContextTestExecutionListener.class})
@ActiveProfiles("test")
public class IXsdDocumentControllerTest {

  @Autowired
  private ArangoOperations operations;
  @Autowired
  private TestRestTemplate template;
  /**
   * Repository persisting METS files.
   */
  @Autowired
  private XmlSchemaDefinitionRepository xsdRepository;

  @Autowired
  private MockMvc mockMvc;

  public IXsdDocumentControllerTest() {
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
     xsdRepository.saveAll(CrudRunner.createSchemaDefinitions());
 }

  @After
  public void tearDown() {
  }

  /**
   * Test of createMetsDocument method, of class IXsdDocumentController.
   */
  @Test
  @WithMockUser(username = "ingest", password = "forTestOnly", roles = "USER")
  public void testCreateMetsDocument() throws IOException, Exception {
    System.out.println("createMetsDocument");
    String prefix = "page";
    File xsdFile = new File("src/test/resources/xsd/2018.xsd");
    MockMultipartFile bagitContainer = new MockMultipartFile("fileContent", xsdFile.getName(), "application/octet-stream", FileUtils.openInputStream(xsdFile));
    this.mockMvc.perform(MockMvcRequestBuilders.multipart("/api/v1/metastore/xsd/prefix/" + prefix)
            .file(bagitContainer))
            .andExpect(status().isOk());
  }

  /**
   * Test of createMetsDocument method, of class IXsdDocumentController.
   */
  @Test
  @WithMockUser(username = "ingest", password = "wrongPassword", roles = "USER")
  public void testCreateMetsDocumentWithWrongCredentials() throws IOException, Exception {
    System.out.println("createMetsDocument");
    String prefix = "page";
    File xsdFile = new File("src/test/resources/xsd/2018.xsd");
    ResponseEntity<String> result = template.withBasicAuth("ingest", "wrongPasswort")
            .postForEntity("/api/v1/metastore/xsd/prefix/" + prefix, xsdFile, String.class);
    assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
    result = template.withBasicAuth("wrongUser", "forTestOnly")
            .postForEntity("/api/v1/metastore/xsd/prefix/" + prefix, xsdFile, String.class);
    assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
  }

  /**
   * Test of getAllDocuments method, of class IXsdDocumentController.
   */
  @Test
  public void testGetAllDocuments() throws Exception {
    System.out.println("getAllDocuments");
    String prefix[] = {"bmd", "tei", "dc", "file"};

    this.mockMvc.perform(get("/api/v1/metastore/xsd" ))//"/api/v1/metastore/mets/" + resourceId + "/files"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(4)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.[*].prefix", Matchers.hasItems(prefix)))
            .andReturn();
  }

  /**
   * Test of getAllPrefixes method, of class IXsdDocumentController.
   */
  @Test
  public void testGetAllPrefixes() throws Exception {
    System.out.println("getAllPrefixes");
    String prefix[] = {"bmd", "tei", "dc", "file"};

    this.mockMvc.perform(get("/api/v1/metastore/xsd/prefix" ))//"/api/v1/metastore/mets/" + resourceId + "/files"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(4)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.[*]", Matchers.hasItems(prefix)))
            .andReturn();
  
  }

  /**
   * Test of getXsdDocumentByNamespace method, of class IXsdDocumentController.
   */
  @Test
  public void testGetXsdDocumentByNamespace() throws Exception {
    System.out.println("getXsdDocumentByNamespace");
    String prefix= "dc";
    String namespace = "namespace3";
    String content = "someDCContent";

    this.mockMvc.perform(get("/api/v1/metastore/xsd/ns")
            .param("namespace", namespace))//"/api/v1/metastore/mets/" + resourceId + "/files"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.prefix").value(prefix))
            .andExpect(MockMvcResultMatchers.jsonPath("$.namespace").value(namespace))
            .andExpect(MockMvcResultMatchers.jsonPath("$.xsdFile").value(content))
            .andReturn();
  }

  /**
   * Test of getXsdDocumentByPrefix method, of class IXsdDocumentController.
   */
  @Test
  public void testGetXsdDocumentByPrefix() throws Exception {
    System.out.println("getXsdDocumentByPrefix");
    String prefix= "tei";
    String namespace = "namespace2";
    String content = "someTeiContent";

    this.mockMvc.perform(get("/api/v1/metastore/xsd/prefix/" + prefix))//"/api/v1/metastore/mets/" + resourceId + "/files"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.prefix").value(prefix))
            .andExpect(MockMvcResultMatchers.jsonPath("$.namespace").value(namespace))
            .andExpect(MockMvcResultMatchers.jsonPath("$.xsdFile").value(content))
            .andReturn();
  }

}
