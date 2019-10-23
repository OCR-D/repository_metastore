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
import edu.kit.datamanager.metastore.repository.MetsFileRepository;
import edu.kit.datamanager.metastore.runner.CrudRunner;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithSecurityContextTestExecutionListener;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.ServletTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
public class IMetsFileControllerTest {

  @Autowired
  private ArangoOperations operations;
  /**
   * Repository persisting METS files.
   */
  @Autowired
  private MetsFileRepository metsFileRepository;

  @Autowired
  private MockMvc mockMvc;

  public IMetsFileControllerTest() {
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
    metsFileRepository.saveAll(CrudRunner.createMetsFiles());
  }

  @After
  public void tearDown() {
  }

  /**
   * Test of getAllFilesOfMetsDocument method, of class IMetsFileController.
   */
  @Test
  public void testGetAllFilesOfMetsDocument() throws Exception {
    System.out.println("getAllFilesOfMetsDocument");
    String resourceId = "id_0002";

    this.mockMvc.perform(get("/api/v1/metastore/mets/" + resourceId + "/files"))//"/api/v1/metastore/mets/" + resourceId + "/files"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(12)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.[*].resourceId", Matchers.hasItem(resourceId)))
            .andReturn();
  }

  /**
   * Test of getAllFilesOfMetsDocument method, of class IMetsFileController.
   */
  @Test
  public void testGetAllFilesOfMetsDocumentWithPageId() throws Exception {
    System.out.println("getAllFilesOfMetsDocument");
    String resourceId = "id_0015";
    String pageId = "PAGE-0001";

    this.mockMvc.perform(get("/api/v1/metastore/mets/" + resourceId + "/files")
            .param("pageId", pageId))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(4)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.[*].resourceId", Matchers.hasItem(resourceId)))
            .andReturn();
  }

  /**
   * Test of getAllFilesOfMetsDocument method, of class IMetsFileController.
   */
  @Test
  public void testGetAllFilesOfMetsDocumentWithUse() throws Exception {
    System.out.println("getAllFilesOfMetsDocument");
    String resourceId = "id_0015";
    String use = "OCR-D-GT-IMG-CROP";

    this.mockMvc.perform(get("/api/v1/metastore/mets/" + resourceId + "/files")
            .param("use", use))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.[*].resourceId", Matchers.hasItem(resourceId)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.[*].use", Matchers.hasItem(use)))
            .andReturn();
  }

  /**
   * Test of getAllFilesOfMetsDocument method, of class IMetsFileController.
   */
  @Test
  public void testGetAllFilesOfMetsDocumentWithFileId() throws Exception {
    System.out.println("getAllFilesOfMetsDocument");
    String resourceId = "id_0015";
    String fileId = "PAGE-0001_IMG-DESPEC";

    this.mockMvc.perform(get("/api/v1/metastore/mets/" + resourceId + "/files")
            .param("fileId", fileId))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.[*].resourceId", Matchers.hasItem(resourceId)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.[*].fileId", Matchers.hasItem(fileId)))
            .andReturn();
  }

  /**
   * Test of getAllFilesOfMetsDocumentAsHtml method, of class
   * IMetsFileController.
   */
  @Test
  public void testGetAllFilesOfMetsDocumentAsHtml() throws Exception {
    System.out.println("getAllFilesOfMetsDocumentAsHtml");
    String resourceId = "id_0015";
    String url = "url41";

    this.mockMvc.perform(get("/api/v1/metastore/mets/" + resourceId + "/files")
            .accept(MediaType.TEXT_HTML))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.xpath("/html/body/table/tbody/tr/td[text() = '" + resourceId + "']").exists())
            .andExpect(MockMvcResultMatchers.xpath("/html/body/table/tbody/tr/td/a[text() = '" + url + "']").exists())
            .andExpect(MockMvcResultMatchers.xpath("/html/body/table/tbody/tr/td[text() = '" + resourceId + "']").nodeCount(8))
            .andReturn();
  }

  /**
   * Test of getUrlOfAllFilesOfMetsDocument method, of class
   * IMetsFileController.
   */
  @Test
  public void testGetUrlOfAllFilesOfMetsDocument() throws Exception {
    System.out.println("getUrlOfAllFilesOfMetsDocument");
    String resourceId = "id_0016";
    String items[] = {"url16", "url2_16"};

    this.mockMvc.perform(get("/api/v1/metastore/mets/" + resourceId + "/files/url"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.[*]", Matchers.hasItems(items)))
            .andReturn();
  }

  /**
   * Test of getAllPageIdsOfMetsDocument method, of class IMetsFileController.
   */
  @Test
  public void testGetAllPageIdsOfMetsDocument() throws Exception {
    System.out.println("getAllPageIdsOfMetsDocument");
    String resourceId = "id_0017";
    String items[] = {"PAGE-0001", "PAGE-0002"};

    this.mockMvc.perform(get("/api/v1/metastore/mets/" + resourceId + "/pageid"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.[*]", Matchers.hasItems(items)))
            .andReturn();
  }

  /**
   * Test of getAllUsesOfMetsDocument method, of class IMetsFileController.
   */
  @Test
  public void testGetAllUsesOfMetsDocument() throws Exception {
    System.out.println("getAllUsesOfMetsDocument");
    String resourceId = "id_0015";
    String items[] = {"OCR-D-GT-IMG-BIN", "OCR-D-GT-IMG-CROP", "OCR-D-GT-IMG-DESPEC", "OCR-D-GT-IMG-DEWARP"};

    this.mockMvc.perform(get("/api/v1/metastore/mets/" + resourceId + "/use"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(4)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.[*]", Matchers.hasItems(items)))
            .andReturn();
  }

}
