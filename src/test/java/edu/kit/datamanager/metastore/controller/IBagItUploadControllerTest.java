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

import edu.kit.datamanager.util.ZipUtils;
import java.io.File;
import java.nio.file.Paths;
import org.apache.commons.io.FileUtils;
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
import org.springframework.http.MediaType;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

/**
 *
 * Integration test for BagItUploadController
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
public class IBagItUploadControllerTest {
  @Autowired
  private MockMvc mockMvc;
  
  public IBagItUploadControllerTest() {
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
   * Test of handleFileUpload method, of class IBagItUploadController.
   */
  @Test
 @WithMockUser(username = "ingest", password = "forTestOnly", roles = "USER")
 public void testHandleFileUploadInvalidBag() throws Exception {
    System.out.println("handleFileUpload");
    File ocrdZip = new File("/tmp/invalidChecksum.ocrd.zip");
    if (ocrdZip.exists()) {
      ocrdZip.delete();
    }
    ZipUtils.zip(Paths.get("src/test/resources/bagit/", "invalidChecksum").toFile(), ocrdZip);
    MockMultipartFile bagitContainer = new MockMultipartFile("file", "invalidChecksum.ocrd.zip", "application/octet-stream", FileUtils.openInputStream(ocrdZip));
    this.mockMvc.perform(MockMvcRequestBuilders.multipart("/api/v1/metastore/bagit")
                         .file(bagitContainer))
            .andExpect(status().is4xxClientError());
   }

  /**
   * Test of handleFileUpload method, of class IBagItUploadController.
   */
  @Test
 @WithMockUser(username = "ingest", password = "wrongPasswort", roles = "USER")
 public void testHandleFileUploadInvalidUser() throws Exception {
    System.out.println("handleFileUpload");
    File ocrdZip = new File("/tmp/invalidChecksum.ocrd.zip");
    if (ocrdZip.exists()) {
      ocrdZip.delete();
    }
    ZipUtils.zip(Paths.get("src/test/resources/bagit/", "invalidChecksum").toFile(), ocrdZip);
    MockMultipartFile bagitContainer = new MockMultipartFile("file", "invalidChecksum.ocrd.zip", "application/octet-stream", FileUtils.openInputStream(ocrdZip));
    this.mockMvc.perform(MockMvcRequestBuilders.multipart("/api/v1/metastore/bagit")
                         .file(bagitContainer))
            .andExpect(status().is4xxClientError());
   }

  /**
   * Test of handleFileUpload method, of class IBagItUploadController.
   */
  @Test
 @WithMockUser(username = "ingest", password = "forTestOnly", roles = "USER")
 public void testHandleFileUpload() throws Exception {
    System.out.println("*****************************************************");
    System.out.println("handleFileUpload");
    String filename = "validBag.ocrd.zip";
    File ocrdZip = new File("/tmp/" + filename);
    if (ocrdZip.exists()) {
      ocrdZip.delete();
    }
    ZipUtils.zip(Paths.get("src/test/resources/bagit/", "validBag").toFile(), ocrdZip);
    MockMultipartFile bagitContainer = new MockMultipartFile("file", filename, "application/octet-stream", FileUtils.openInputStream(ocrdZip));
    String redirectedUrl = this.mockMvc.perform(MockMvcRequestBuilders.multipart("/api/v1/metastore/bagit")
            .file(bagitContainer))
            .andExpect(status().isCreated())
            .andExpect(MockMvcResultMatchers.redirectedUrlPattern("http://localhost:8090/api/v1/dataresources/*/data/" + filename))
            .andReturn()
            .getResponse()
            .getRedirectedUrl();
    String[] split = redirectedUrl.split("/");
    String dataObjectId = split[split.length - 3];
    System.out.println(dataObjectId);
    System.out.println("*****************************************************");
    System.out.println("listUploadedFiles");
    this.mockMvc.perform(get("/api/v1/metastore/bagit"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.[*]", Matchers.hasItem(redirectedUrl)))
            .andReturn();
    System.out.println("*****************************************************");
    System.out.println("listUploadedFilesAsHtml");
    this.mockMvc.perform(get("/api/v1/metastore/bagit").accept(MediaType.TEXT_HTML))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.xpath("/html/body/div/table/tbody/tr/td[text() = '" + dataObjectId + "']").exists())
            .andExpect(MockMvcResultMatchers.xpath("/html/body/div/table/tbody/tr/td/a[text() = '" + redirectedUrl + "']").exists())
            .andReturn();
 }
  
}
