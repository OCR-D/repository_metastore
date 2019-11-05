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
import edu.kit.ocrd.workspace.entity.GroundTruthProperties;
import edu.kit.ocrd.workspace.entity.MetsProperties;
import edu.kit.datamanager.metastore.repository.ClassificationMetadataRepository;
import edu.kit.datamanager.metastore.repository.GenreMetadataRepository;
import edu.kit.datamanager.metastore.repository.LanguageMetadataRepository;
import edu.kit.datamanager.metastore.repository.MetsDocumentRepository;
import edu.kit.datamanager.metastore.repository.MetsIdentifierRepository;
import edu.kit.datamanager.metastore.repository.MetsPropertiesRepository;
import edu.kit.datamanager.metastore.repository.PageMetadataRepository;
import edu.kit.datamanager.metastore.runner.CrudRunner;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
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
import org.springframework.web.util.NestedServletException;

/**
 *
 * Integration test for MetsDocumentController
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
public class IMetsDocumentControllerTest {

  @Autowired
  private ArangoOperations operations;
  
  @Autowired
  private MetsDocumentRepository repository;

  /**
   * Repository persisting METS properties.
   */
  @Autowired
  private MetsPropertiesRepository metsPropertiesRepository;

  /**
   * Repository persisting METS identifiers.
   */
  @Autowired
  private MetsIdentifierRepository metsIdentifierRepository;

  /**
   * Repository persisting page metadata..
   */
  @Autowired
  private PageMetadataRepository pageMetadataRepository;

  /**
   * Repository persisting classification metadata.
   */
  @Autowired
  private ClassificationMetadataRepository classificationMetadataRepository;

  /**
   * Repository persisting genre metadata.
   */
  @Autowired
  private GenreMetadataRepository genreMetadataRepository;

  /**
   * Repository persisting language metadata.
   */
  @Autowired
  private LanguageMetadataRepository languageMetadataRepository;
  @Autowired
  private MockMvc mockMvc;

  public IMetsDocumentControllerTest() {
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
    repository.saveAll(CrudRunner.createMetsDocuments());
    classificationMetadataRepository.saveAll(CrudRunner.createClassifications());
    genreMetadataRepository.saveAll(CrudRunner.createGenre());
    languageMetadataRepository.saveAll(CrudRunner.createLanguageMetadata());
    metsIdentifierRepository.saveAll(CrudRunner.createMetsIdentifier());
    metsPropertiesRepository.saveAll(CrudRunner.createMetsProperties());
    pageMetadataRepository.saveAll(CrudRunner.createPageMetadata());
  }

  @After
  public void tearDown() {
  }

  /**
   * Test of createMetsDocument method, of class IMetsDocumentController.
   */
//  @Test
//  public void testCreateMetsDocument() {
//    System.out.println("createMetsDocument");
//    String resourceId = "";
//    String fileContent = "";
//    IMetsDocumentController instance = new IMetsDocumentControllerImpl();
//    ResponseEntity expResult = null;
//    ResponseEntity result = instance.createMetsDocument(resourceId, fileContent);
//    assertEquals(expResult, result);
//    // TODO review the generated test code and remove the default call to fail.
//    fail("The test case is a prototype.");
//  }
  /**
   * Test of getAllDocuments method, of class IMetsDocumentController.
   */
  @Test
  public void testGetAllDocuments() throws Exception {
    System.out.println("getAllDocuments");
    String[] allIds = {"id_0001",
      "id_0002",
      "id_0003me",
      "id_0004sei",
      "id_0005ah",
      "id_0006e6rys",
      "id_0007sa",
      "id_0008b",
      "id_0009n",
      "id_0010dor",
      "id_0001l",
      "id_0012os",
      "id_0013nnis",
      "id_00140gaery",
      "id_0015",
      "id_0016",
      "id_0017",
      "id_0018",
      "id_0019",
      "id_0020",
      "id_0021",
      "id_0022"};
    this.mockMvc.perform(get("/api/v1/metastore/mets").header(HttpHeaders.AUTHORIZATION, "Bearer NoBearerToken"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(22)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.[0].resourceId", Matchers.isOneOf(allIds)))
            .andReturn();
  }

  /**
   * Test of getLatestMetsDocument method, of class IMetsDocumentController.
   */
  @Test
  public void testGetLatestMetsDocument() throws Exception {
    System.out.println("getLatestMetsDocument");
    String resourceId = "id_0002";
    this.mockMvc.perform(get("/api/v1/metastore/mets/" + resourceId).header(HttpHeaders.AUTHORIZATION, "Bearer NoBearerToken"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.current").value("true"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.version").value("4"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.metsContent").value("stark"))
            .andReturn();
  }

  /**
   * Test of getLatestMetsDocument method, of class IMetsDocumentController.
   */
  @Test
  public void testGetLatestMetsDocumentNotExists() throws Exception {
    System.out.println("getLatestMetsDocument");
    String resourceId = "invalidResourceId";
    this.mockMvc.perform(get("/api/v1/metastore/mets/" + resourceId).header(HttpHeaders.AUTHORIZATION, "Bearer NoBearerToken"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$").doesNotExist())
            .andReturn();
  }

  /**
   * Test of getLatestMetadataOfDocument method, of class
   * IMetsDocumentController.
   */
  @Test
  public void testGetLatestMetadataOfDocument() throws Exception {
    System.out.println("getLatestMetadataOfDocument");
    String resourceId = "id_0016";
    String title = "Some Title";
    String subTitle = "Any Sub Title";
    String year = "1984";
    String author = "Ernest Twain";
    String licence = "Apache 2.0";
    int noOfPages = 1;
    String physicalDescription = "book";
    String publisher = "Gruener und Tag";
    MetsProperties mp = new MetsProperties();
    mp.setTitle(title);
    mp.setSubTitle(subTitle);
    mp.setResourceId(resourceId);
    mp.setLicense(licence);
    mp.setNoOfPages(noOfPages);
    mp.setPhysicalDescription(physicalDescription);
    mp.setPublisher(publisher);
    mp.setAuthor(author);
    mp.setYear(year);
    metsPropertiesRepository.save(mp);

    this.mockMvc.perform(get("/api/v1/metastore/mets/" + resourceId + "/metadata").header(HttpHeaders.AUTHORIZATION, "Bearer NoBearerToken"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(title))
            .andExpect(MockMvcResultMatchers.jsonPath("$.subTitle").value(subTitle))
            .andExpect(MockMvcResultMatchers.jsonPath("$.year").value(year))
            .andExpect(MockMvcResultMatchers.jsonPath("$.pages", Matchers.hasSize(1)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.pages[0].order").value("1"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.pages[0].pageId").value("phys_0001"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.pages[0].features", Matchers.hasSize(1)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.pages[0].features[0]").value(GroundTruthProperties.TOC.toString()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.licence").value(licence))
            .andExpect(MockMvcResultMatchers.jsonPath("$.language", Matchers.hasSize(1)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.language[0]").value("deu"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.author").value(author))
            .andExpect(MockMvcResultMatchers.jsonPath("$.noOfPages").value(noOfPages))
            .andExpect(MockMvcResultMatchers.jsonPath("$.classification", Matchers.hasSize(1)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.classification[0]").value("Novelle"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.genre", Matchers.hasSize(1)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.genre[0]").value("Thriller"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.publisher").value(publisher))
            .andExpect(MockMvcResultMatchers.jsonPath("$.physicalDescription").value(physicalDescription))
            .andExpect(MockMvcResultMatchers.jsonPath("$.modsIdentifier", Matchers.hasSize(1)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.modsIdentifier[0].type").value("handle"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.modsIdentifier[0].identifier").value("handle1"))
            .andReturn();
  }

  /**
   * Test of getLatestMetadataOfDocumentAsHtml method, of class
   * IMetsDocumentController.
   */
  @Test
  public void testGetLatestMetadataOfDocumentAsHtml() throws Exception {
    System.out.println("getLatestMetadataOfDocumentAsHtml");
    String resourceId = "id_0016";
    String title = "Some Title";
    String subTitle = "Any Sub Title";
    String year = "1984";
    String author = "Ernest Twain";
    String licence = "Apache 2.0";
    int noOfPages = 1;
    String physicalDescription = "book";
    String publisher = "Gruener und Tag";
    MetsProperties mp = new MetsProperties();
    mp.setTitle(title);
    mp.setSubTitle(subTitle);
    mp.setResourceId(resourceId);
    mp.setLicense(licence);
    mp.setNoOfPages(noOfPages);
    mp.setPhysicalDescription(physicalDescription);
    mp.setPublisher(publisher);
    mp.setAuthor(author);
    mp.setYear(year);
    metsPropertiesRepository.save(mp);

    this.mockMvc.perform(get("/api/v1/metastore/mets/" + resourceId + "/metadata").accept(MediaType.TEXT_HTML)
            .header(HttpHeaders.AUTHORIZATION, "Bearer NoBearerToken"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.xpath("/html/body/table/tbody/tr").nodeCount(6))
            .andExpect(MockMvcResultMatchers.xpath("/html/body/table/tbody/tr/td[text() = '" + title + "']").exists())
            .andExpect(MockMvcResultMatchers.xpath("/html/body/table/tbody/tr/td[text() = '" + subTitle + "']").exists())
            .andExpect(MockMvcResultMatchers.xpath("/html/body/table/tbody/tr/td[text() = '" + author + "']").exists())
            .andExpect(MockMvcResultMatchers.xpath("/html/body/table/tbody/tr/td[text() = '" + licence + "']").exists())
            .andExpect(MockMvcResultMatchers.xpath("/html/body/table/tbody/tr/td[text() = '" + noOfPages + "']").exists())
            .andReturn();
  }

  /**
   * Test of getLatestGroundTruthMetadataOfDocumentAsHtml method, of class
   * IMetsDocumentController.
   */
  @Test
  public void testGetLatestGroundTruthMetadataOfDocumentAsHtml() throws Exception {
    System.out.println("getLatestGroundTruthMetadataOfDocumentAsHtml");
    String resourceId = "id_0017";
    String[] orderList = {"1", "2"};
    String[] pageIdList = {"phys_0001", "phys_0002"};
    String[] gtList = {GroundTruthProperties.FAX.toString(), GroundTruthProperties.DIA.toString()};

    this.mockMvc.perform(get("/api/v1/metastore/mets/" + resourceId + "/groundtruth").header(HttpHeaders.AUTHORIZATION, "Bearer NoBearerToken"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.xpath("/html/body/table/tbody/tr").nodeCount(2))
            .andExpect(MockMvcResultMatchers.xpath("/html/body/table/tbody/tr/td[text() = '" + gtList[1] + "']").string(gtList[1]))
            .andReturn();
  }

  /**
   * Test of getResourceIdByPpn method, of class IMetsDocumentController.
   */
  @Test
  public void testGetResourceIdByPpn() throws Exception {
    System.out.println("getResourceIdByPpn");
    String ppn = "ppn1";
    String[] allIds = {"id_1",
      "id_4"};
    this.mockMvc.perform(get("/api/v1/metastore/mets/ppn").header(HttpHeaders.AUTHORIZATION, "Bearer NoBearerToken")
            .param("ppn", ppn))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.[0]", Matchers.isOneOf(allIds)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.[1]", Matchers.isOneOf(allIds)))
            .andReturn();
  }

  /**
   * Test of getResourceIdByTitle method, of class IMetsDocumentController.
   */
  @Test
  public void testGetResourceIdByTitle() throws Exception {
    System.out.println("getResourceIdByTitle");
    String title = "Titel";
    String[] allIds = {"id_1",
      "id_3"};
    this.mockMvc.perform(get("/api/v1/metastore/mets/title").header(HttpHeaders.AUTHORIZATION, "Bearer NoBearerToken")
            .param("title", title))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.[0]", Matchers.isOneOf(allIds)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.[1]", Matchers.isOneOf(allIds)))
            .andReturn();
  }

  /**
   * Test of getResourceIdBySemanticLabel method, of class
   * IMetsDocumentController.
   */
  @Test
  public void testGetResourceIdByOneSemanticLabel() throws Exception {
    System.out.println("getResourceIdByOneSemanticLabel");
    String[] label = {GroundTruthProperties.ANDROID.toString()};
    String[] allIds = {"id_0015"};
    this.mockMvc.perform(get("/api/v1/metastore/mets/labeling").header(HttpHeaders.AUTHORIZATION, "Bearer NoBearerToken")
            .param("label", label))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.[0]", Matchers.isOneOf(allIds)))
            .andReturn();
  }

  /**
   * Test of getResourceIdBySemanticLabel method, of class
   * IMetsDocumentController.
   */
  @Test
  public void testGetResourceIdByInvalidSemanticLabel() throws Exception {
    System.out.println("getResourceIdByInvalidSemanticLabel");
    String[] label = {"invalid.semantic.label"};
    String[] allIds = {"id_0015"};
    try {
      this.mockMvc.perform(get("/api/v1/metastore/mets/labeling").header(HttpHeaders.AUTHORIZATION, "Bearer NoBearerToken")
              .param("label", label))
              .andDo(print())
              .andExpect(status().isInternalServerError())
              .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
              .andExpect(MockMvcResultMatchers.jsonPath("$.[0]", Matchers.isOneOf(allIds)))
              .andReturn();
      assertTrue(false);
    } catch (Throwable iae) {
      IllegalArgumentException illegalArgument = new IllegalArgumentException("Invalid argument");
      System.out.println(iae.getClass());
      assertTrue(iae.getClass().isInstance(new NestedServletException("nothing")));
      assertTrue(iae.getCause().getClass().isInstance(illegalArgument));
      assertEquals("'" + label[0] + "' is not a valid ground truth label!",iae.getCause().getMessage());
    }
  }

  /**
   * Test of getResourceIdBySemanticLabel method, of class
   * IMetsDocumentController.
   */
  @Test
  public void testGetResourceIdByTwoSemanticLabels() throws Exception {
    System.out.println("getResourceIdByTwoSemanticLabels");
    String[] label = {GroundTruthProperties.FAX.toString(), GroundTruthProperties.DIA.toString()};
    String[] allIds = {"id_0017"};
    this.mockMvc.perform(get("/api/v1/metastore/mets/labeling").header(HttpHeaders.AUTHORIZATION, "Bearer NoBearerToken")
            .param("label", label))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.[0]", Matchers.isOneOf(allIds)))
            .andReturn();
  }

  /**
   * Test of getResourceIdBySemanticLabelOfPage method, of class
   * IMetsDocumentController.
   */
  @Test
  public void testGetResourceIdBySemanticLabelOfPage() throws Exception {
    System.out.println("getResourceIdBySemanticLabelOfPage");
    String[] label = {GroundTruthProperties.ADMINS.toString(), GroundTruthProperties.ACQUISITION.toString()};
    String[] allIds = {"id_0002"};
    this.mockMvc.perform(get("/api/v1/metastore/mets/labeling/page").header(HttpHeaders.AUTHORIZATION, "Bearer NoBearerToken")
            .param("label", label))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.[0]", Matchers.isOneOf(allIds)))
            .andReturn();
  }

  /**
   * Test of getResourceIdByClassification method, of class
   * IMetsDocumentController.
   */
  @Test
  public void testGetResourceIdByClassification() throws Exception {
    System.out.println("getResourceIdByClassification");
    String[] classification = {"Gedicht"};
    String[] allIds = {"id_0017", "id_0019"};
    this.mockMvc.perform(get("/api/v1/metastore/mets/classification").header(HttpHeaders.AUTHORIZATION, "Bearer NoBearerToken")
            .param("class", classification))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.[0]", Matchers.isOneOf(allIds)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.[1]", Matchers.isOneOf(allIds)))
            .andReturn();
  }

  /**
   * Test of getResourceIdByClassification method, of class
   * IMetsDocumentController.
   */
  @Test
  public void testGetResourceIdByMultipleClassifications() throws Exception {
    System.out.println("getResourceIdByMultipleClassifications");
    String[] classification = {"Jahrestag", "Tagebuch"};
    String[] allIds = {"id_0002", "id_0015"};
    this.mockMvc.perform(get("/api/v1/metastore/mets/classification").header(HttpHeaders.AUTHORIZATION, "Bearer NoBearerToken")
            .param("class", classification))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.[0]", Matchers.isOneOf(allIds)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.[1]", Matchers.isOneOf(allIds)))
            .andReturn();
  }

  /**
   * Test of getResourceIdByLanguage method, of class IMetsDocumentController.
   */
  @Test
  public void testGetResourceIdByLanguage() throws Exception {
    System.out.println("getResourceIdByLanguage");
    String[] language = {"en"};
    String[] allIds = {"id_0002", "id_0017", "id_0018", "id_0019"};
    this.mockMvc.perform(get("/api/v1/metastore/mets/language").header(HttpHeaders.AUTHORIZATION, "Bearer NoBearerToken")
            .param("lang", language))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(4)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.[0]", Matchers.isOneOf(allIds)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.[1]", Matchers.isOneOf(allIds)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.[2]", Matchers.isOneOf(allIds)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.[3]", Matchers.isOneOf(allIds)))
            .andReturn();
  }

  /**
   * Test of getResourceIdByIdentifier method, of class IMetsDocumentController.
   */
  @Test
  public void testGetResourceIdByIdentifier() throws Exception {
    System.out.println("getResourceIdByIdentifier");
    String identifier = "url3";
    String type = "";
    String[] allIds = {"id_0019"};
    this.mockMvc.perform(get("/api/v1/metastore/mets/identifier").header(HttpHeaders.AUTHORIZATION, "Bearer NoBearerToken")
            .param("identifier", identifier))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.[0]", Matchers.isOneOf(allIds)))
            .andReturn();
  }

  /**
   * Test of getResourceIdByIdentifier method, of class IMetsDocumentController.
   */
  @Test
  public void testGetResourceIdByIdentifierAndType() throws Exception {
    System.out.println("getResourceIdByIdentifierAndType");
    String identifier = "url2";
    String type = "url";
    String[] allIds = {"id_0017"};
    this.mockMvc.perform(get("/api/v1/metastore/mets/identifier").header(HttpHeaders.AUTHORIZATION, "Bearer NoBearerToken")
            .param("identifier", identifier)
            .param("type", type))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.[0]", Matchers.isOneOf(allIds)))
            .andReturn();
  }

  /**
   * Test of getResourceIdByIdentifier method, of class IMetsDocumentController.
   */
  @Test
  public void testGetResourceIdByType() throws Exception {
    System.out.println("getResourceIdByIdentifierAndType");
    String type = "url";
    String[] allIds = {"id_0017"};
    this.mockMvc.perform(get("/api/v1/metastore/mets/identifier").header(HttpHeaders.AUTHORIZATION, "Bearer NoBearerToken")
            .param("type", type))
            .andDo(print())
            .andExpect(status().is4xxClientError())
            .andReturn();
  }

  /**
   * Test of getVersionsOfMetsDocument method, of class IMetsDocumentController.
   */
  @Test
  public void testGetVersionsOfMetsDocument() throws Exception {
    System.out.println("getVersionsOfMetsDocument");
    String resourceId = "id_0002";
    Integer[] versionList = {1, 2, 3, 4};
    this.mockMvc.perform(get("/api/v1/metastore/mets/" + resourceId + "/version").header(HttpHeaders.AUTHORIZATION, "Bearer NoBearerToken"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(4)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.[0]", Matchers.isOneOf(versionList)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.[1]", Matchers.isOneOf(versionList)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.[2]", Matchers.isOneOf(versionList)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.[3]", Matchers.isOneOf(versionList)))
            .andReturn();
  }

  /**
   * Test of getMetsDocumentByVersion method, of class IMetsDocumentController.
   */
  @Test
  public void testGetMetsDocumentByVersion() throws Exception {
    System.out.println("getMetsDocumentByVersion");
    String resourceId = "id_0002";
    Integer version = 3;
    this.mockMvc.perform(get("/api/v1/metastore/mets/" + resourceId + "/version/" + version).header(HttpHeaders.AUTHORIZATION, "Bearer NoBearerToken"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.current").value("false"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.version").value("3"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.metsContent").value("staerker"))
            .andReturn();
  }
}
