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
import edu.kit.datamanager.metastore.storageservice.StorageException;
import edu.kit.datamanager.util.ZipUtils;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import org.apache.commons.io.FileUtils;
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
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import org.springframework.web.util.NestedServletException;

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
    private ArangoOperations operations;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TestRestTemplate template;

    public IBagItUploadControllerTest() {
    }

    @BeforeClass
    public static void setUpClass() throws IOException {
        File srcDir = new File("src/test/resources/profile/bagit-profile-repeatable.json");
        File profileDir = new File("/tmp/bagit-profile-repeatable.json");
        java.nio.file.Files.copy(
                srcDir.toPath(),
                profileDir.toPath(),
                java.nio.file.StandardCopyOption.REPLACE_EXISTING,
                java.nio.file.StandardCopyOption.COPY_ATTRIBUTES,
                java.nio.file.LinkOption.NOFOLLOW_LINKS);
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
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of handleFileUpload method, of class IBagItUploadController.
     */
    @Test
    @WithMockUser(username = "ingest", password = "forTestOnly", roles = "USER")
    public void testHandleFileUploadEmptyBag() throws Exception {
        System.out.println("handleFileUploadWithEmptyBag");
        byte[] content = {};
        try {
            MockMultipartFile bagitContainer = new MockMultipartFile("file", "empty.zip", "application/octet-stream", content);
            this.mockMvc.perform(MockMvcRequestBuilders.multipart("/api/v1/metastore/bagit")
                    .file(bagitContainer))
                    .andExpect(status().is4xxClientError());
            assertTrue(false);
        } catch (Throwable iae) {
            StorageException storageException = new StorageException("Invalid argument");
            System.out.println(iae.getClass());
            assertTrue(iae.getClass().isInstance(new NestedServletException("nothing")));
            assertTrue(iae.getCause().getClass().isInstance(storageException));
            assertEquals("Failed to store empty file empty.zip", iae.getCause().getMessage());
        }

    }

    /**
     * Test of handleFileUpload method, of class IBagItUploadController.
     */
    @Test
    @WithMockUser(username = "ingest", password = "forTestOnly", roles = "USER")
    public void testHandleFileUploadRelativePathToBag() throws Exception {
        System.out.println("handleFileUploadWithRelativePathToBag");
        byte[] content = {32};
        try {
            MockMultipartFile bagitContainer = new MockMultipartFile("file", "../nonempty.zip", "application/octet-stream", content);
            this.mockMvc.perform(MockMvcRequestBuilders.multipart("/api/v1/metastore/bagit")
                    .file(bagitContainer))
                    .andExpect(status().is4xxClientError());
            assertTrue(false);
        } catch (Throwable iae) {
            StorageException storageException = new StorageException("Invalid argument");
            System.out.println(iae.getClass());
            assertTrue(iae.getClass().isInstance(new NestedServletException("nothing")));
            assertTrue(iae.getCause().getClass().isInstance(storageException));
            assertEquals("Cannot store file with relative path outside current directory ../nonempty.zip", iae.getCause().getMessage());
        }
    }

    /**
     * Test of handleFileUpload method, of class IBagItUploadController.
     */
    @Test
    @WithMockUser(username = "ingest", password = "forTestOnly", roles = "USER")
    public void testHandleFileUploadInvalidUrl() throws Exception {
        System.out.println("handleFileUploadWithInvalidPathToBag");
        byte[] content = {32};
        try {
            MockMultipartFile bagitContainer = new MockMultipartFile("file", "url:/wrongUrl.zip", "application/octet-stream", content);
            this.mockMvc.perform(MockMvcRequestBuilders.multipart("/api/v1/metastore/bagit")
                    .file(bagitContainer))
                    .andExpect(status().is4xxClientError());
            assertTrue(false);
        } catch (Throwable iae) {
            StorageException storageException = new StorageException("Invalid argument");
            System.out.println(iae.getClass());
            assertTrue(iae.getClass().isInstance(new NestedServletException("nothing")));
            assertTrue(iae.getCause().getClass().isInstance(storageException));
            assertEquals("Failed to store file url:/wrongUrl.zip", iae.getCause().getMessage());
        }
    }

    /**
     * Test of handleFileUpload method, of class IBagItUploadController.
     */
    @Test
    @WithMockUser(username = "ingest", password = "forTestOnly", roles = "USER")
    public void testHandleFileUploadInvalidPathToMets() throws Exception {
        System.out.println("handleFileUploadInvalidPathToMets");
        String filename = "invalidPathToMets.ocrd.zip";
        File ocrdZip = new File("/tmp/" + filename);
        if (ocrdZip.exists()) {
            ocrdZip.delete();
        }
        ZipUtils.zip(Paths.get("src/test/resources/bagit/", "invalidPathToMets").toFile(), ocrdZip);
        MockMultipartFile bagitContainer = new MockMultipartFile("file", filename, "application/octet-stream", FileUtils.openInputStream(ocrdZip));
        this.mockMvc.perform(MockMvcRequestBuilders.multipart("/api/v1/metastore/bagit")
                .file(bagitContainer))
                .andExpect(status().is4xxClientError());
    }

    /**
     * Test of handleFileUpload method, of class IBagItUploadController.
     */
    @Test
    @WithMockUser(username = "ingest", password = "forTestOnly", roles = "USER")
    public void testHandleFileUploadTwoMets() throws Exception {
        System.out.println("handleFileUploadWithTwoMets");
        String filename = "twoMetsWithProfile.ocrd.zip";
        File ocrdZip = new File("/tmp/" + filename);
        if (ocrdZip.exists()) {
            ocrdZip.delete();
        }
        ZipUtils.zip(Paths.get("src/test/resources/bagit/", "twoMetsWithProfile").toFile(), ocrdZip);
        MockMultipartFile bagitContainer = new MockMultipartFile("file", filename, "application/octet-stream", FileUtils.openInputStream(ocrdZip));
        this.mockMvc.perform(MockMvcRequestBuilders.multipart("/api/v1/metastore/bagit")
                .file(bagitContainer))
                .andExpect(status().is4xxClientError());
    }

    /**
     * Test of handleFileUpload method, of class IBagItUploadController.
     */
    @Test
    @WithMockUser(username = "ingest", password = "forTestOnly", roles = "USER")
    public void testHandleFileUploadInvalidBag() throws Exception {
        System.out.println("handleFileUploadInvalidChecksum");
        String filename = "invalidChecksum.ocrd.zip";
        File ocrdZip = new File("/tmp/" + filename);
        if (ocrdZip.exists()) {
            ocrdZip.delete();
        }
        ZipUtils.zip(Paths.get("src/test/resources/bagit/", "invalidChecksum").toFile(), ocrdZip);
        MockMultipartFile bagitContainer = new MockMultipartFile("file", filename, "application/octet-stream", FileUtils.openInputStream(ocrdZip));
        this.mockMvc.perform(MockMvcRequestBuilders.multipart("/api/v1/metastore/bagit")
                .file(bagitContainer))
                .andExpect(status().is4xxClientError());
    }

    /**
     * Test of handleFileUpload method, of class IBagItUploadController.
     */
    @Test
    @WithMockUser(username = "ingest", password = "forTestOnly", roles = "USER")
    public void testHandleFileUploadTwoMetsDefined() throws Exception {
        System.out.println("handleFileUploadWithTwoMetsDefined");
        String filename = "twoMets.ocrd.zip";
        File ocrdZip = new File("/tmp/" + filename);
        if (ocrdZip.exists()) {
            ocrdZip.delete();
        }
        ZipUtils.zip(Paths.get("src/test/resources/bagit/", "twoMets").toFile(), ocrdZip);
        MockMultipartFile bagitContainer = new MockMultipartFile("file", filename, "application/octet-stream", FileUtils.openInputStream(ocrdZip));
        this.mockMvc.perform(MockMvcRequestBuilders.multipart("/api/v1/metastore/bagit")
                .file(bagitContainer))
                .andExpect(status().is4xxClientError());
    }

    /**
     * Test of handleFileUpload method, of class IBagItUploadController.
     */
    @Test
    @WithMockUser(username = "ingest", password = "forTestOnly", roles = "USER")
    public void testHandleFileUploadTwoOcrdIdentifierDefined() throws Exception {
        System.out.println("testHandleFileUploadTwoOcrdIdentifierDefined");
        String filename = "twoOcrdIdentifier.ocrd.zip";
        File ocrdZip = new File("/tmp/" + filename);
        if (ocrdZip.exists()) {
            ocrdZip.delete();
        }
        ZipUtils.zip(Paths.get("src/test/resources/bagit/", "twoOcrdIdentifier").toFile(), ocrdZip);
        MockMultipartFile bagitContainer = new MockMultipartFile("file", filename, "application/octet-stream", FileUtils.openInputStream(ocrdZip));
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
        System.out.println("handleFileUploadWithWrongUserCredentials");
        String filename = "validBagWrongUser.ocrd.zip";
        File ocrdZip = new File("/tmp/" + filename);
        if (ocrdZip.exists()) {
            ocrdZip.delete();
        }
        ResponseEntity<String> result = template.withBasicAuth("ingest", "wrongPasswort")
                .postForEntity("/api/v1/metastore/bagit", ocrdZip, String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
        result = template.withBasicAuth("wrongUser", "forTestOnly")
                .postForEntity("/api/v1/metastore/bagit", ocrdZip, String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
    }

    /**
     * Test of handleFileUpload method, of class IBagItUploadController.
     */
    @Test
    @WithMockUser(username = "ingest", password = "forTestOnly", roles = "USER")
    public void testHandleFileUploadWithPathToMets() throws Exception {
        System.out.println("*****************************************************");
        System.out.println("handleFileUploadWithPathToMets");
        String filename = "pathToMets.ocrd.zip";
        File ocrdZip = new File("/tmp/" + filename);
        if (ocrdZip.exists()) {
            ocrdZip.delete();
        }
        ZipUtils.zip(Paths.get("src/test/resources/bagit/", "pathToMets").toFile(), ocrdZip);
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
                .andExpect(MockMvcResultMatchers.xpath("/html/body/div/table/tbody/tr/td/a[text() = '" + redirectedUrl + "']").exists())
                .andReturn();
        // Upload second 'version' of bag
        String redirectedUrl2 = this.mockMvc.perform(MockMvcRequestBuilders.multipart("/api/v1/metastore/bagit")
                .file(bagitContainer))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.redirectedUrlPattern("http://localhost:8090/api/v1/dataresources/*/data/" + filename))
                .andReturn()
                .getResponse()
                .getRedirectedUrl();
        split = redirectedUrl2.split("/");
        String dataObjectId2 = split[split.length - 3];
        System.out.println(dataObjectId);
        System.out.println("*****************************************************");
        System.out.println("listUploadedFiles");
        this.mockMvc.perform(get("/api/v1/metastore/bagit"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*]", Matchers.hasItem(redirectedUrl2)))
                .andReturn();
        System.out.println("*****************************************************");
        System.out.println("listUploadedFilesAsHtml");
        this.mockMvc.perform(get("/api/v1/metastore/bagit").accept(MediaType.TEXT_HTML))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.xpath("/html/body/div/table/tbody/tr/td/a[text() = '" + redirectedUrl + "']").doesNotExist())
                .andExpect(MockMvcResultMatchers.xpath("/html/body/div/table/tbody/tr/td/a[text() = '" + redirectedUrl2 + "']").exists())
                .andReturn();
    }

    /**
     * Test of handleFileUpload method, of class IBagItUploadController.
     */
    @Test
    @WithMockUser(username = "ingest", password = "forTestOnly", roles = "USER")
    public void testHandleFileUpload() throws Exception {
        System.out.println("*****************************************************");
        System.out.println("handleFileUploadWithDefaultPathToMets");
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
                .andExpect(MockMvcResultMatchers.xpath("/html/body/div/table/tbody/tr/td/a[text() = '" + redirectedUrl + "']").exists())
                .andReturn();
        System.out.println("*****************************************************");
        System.out.println("listFilteredUploadedFilesAsHtml");
        this.mockMvc.perform(get("/api/v1/metastore/bagit/search").accept(MediaType.TEXT_HTML))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.xpath("/html/body/div/table/tbody/tr/td/a[text() = '" + redirectedUrl + "']").exists())
                .andReturn();
        System.out.println("*****************************************************");
        System.out.println("listFilteredUploadedFilesByClassificationAsHtml");
        String[] classification = {"Lyrik"};
        this.mockMvc.perform(get("/api/v1/metastore/mets/classification")
                .param("class", classification)
                .accept(MediaType.TEXT_HTML))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.xpath("/html/body/div/table/tbody/tr/td/a[text() = '" + redirectedUrl + "']").exists())
                .andReturn();
        System.out.println("*****************************************************");
        System.out.println("listFilteredUploadedFilesByUnknownClassificationAsHtml");
        String[] unknownClassification = {"Comic"};
        this.mockMvc.perform(get("/api/v1/metastore/mets/classification")
                .param("class", unknownClassification)
                .accept(MediaType.TEXT_HTML))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.xpath("/html/body/div/table/tbody/tr/td/a[text() = '" + redirectedUrl + "']").doesNotExist())
                .andReturn();
        System.out.println("*****************************************************");
        System.out.println("listFilteredUploadedFilesByLanguageAsHtml");
        String[] language = {"deu"};
        this.mockMvc.perform(get("/api/v1/metastore/mets/language")
                .param("lang", language)
                .accept(MediaType.TEXT_HTML))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.xpath("/html/body/div/table/tbody/tr/td/a[text() = '" + redirectedUrl + "']").exists())
                .andReturn();
        System.out.println("*****************************************************");
        System.out.println("listFilteredUploadedFilesByUnknownLanguageAsHtml");
        String[] unknownLanguage = {"jpn"};
        this.mockMvc.perform(get("/api/v1/metastore/mets/language")
                .param("lang", unknownLanguage)
                .accept(MediaType.TEXT_HTML))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.xpath("/html/body/div/table/tbody/tr/td/a[text() = '" + redirectedUrl + "']").doesNotExist())
                .andReturn();
        System.out.println("*****************************************************");
        System.out.println("listFilteredUploadedFilesBySemanticLabelAsHtml");
        String[] semanticLabel = {"granularity/physical/document-related/word"};
        this.mockMvc.perform(get("/api/v1/metastore/mets/labeling")
                .param("label", semanticLabel)
                .accept(MediaType.TEXT_HTML))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.xpath("/html/body/div/table/tbody/tr/td/a[text() = '" + redirectedUrl + "']").exists())
                .andReturn();
        System.out.println("*****************************************************");
        System.out.println("listFilteredUploadedFilesByUnknownSemanticLabelAsHtml");
        String[] unknownSemanticLabel = {"granularity/physical/document-related/region"};
        this.mockMvc.perform(get("/api/v1/metastore/mets/labeling")
                .param("label", unknownSemanticLabel)
                .accept(MediaType.TEXT_HTML))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.xpath("/html/body/div/table/tbody/tr/td/a[text() = '" + redirectedUrl + "']").doesNotExist())
                .andReturn();
        System.out.println("*****************************************************");
        System.out.println("listFilteredUploadedFilesByOcrdIdentifier");
        String[] ocrdIdentifier = {"bagForTest"};
        this.mockMvc.perform(get("/api/v1/metastore/bagit/ocrdidentifier")
                .param("ocrdidentifier", ocrdIdentifier))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*]", Matchers.hasItem(dataObjectId)))
                .andReturn();
        System.out.println("*****************************************************");
        System.out.println("listFilteredUploadedFilesByOcrdIdentifierAsHtml");
        this.mockMvc.perform(get("/api/v1/metastore/bagit/ocrdidentifier")
                .param("ocrdidentifier", ocrdIdentifier)
                .accept(MediaType.TEXT_HTML))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.xpath("/html/body/div/table/tbody/tr/td/a[text() = '" + redirectedUrl + "']").exists())
                .andReturn();
        System.out.println("*****************************************************");
        System.out.println("listFilteredUploadedFilesByUnknownOcrdIdentifierAsHtml");
        this.mockMvc.perform(get("/api/v1/metastore/bagit/ocrdidentifier")
                .param("ocrdidentifier", "unknownOcrdIdentifier")
                .accept(MediaType.TEXT_HTML))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.xpath("/html/body/div/table/tbody/tr/td/a[text() = '" + redirectedUrl + "']").doesNotExist())
                .andReturn();
    }

}
