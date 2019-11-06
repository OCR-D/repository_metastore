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
package edu.kit.ocrd.workspace.entity;

import edu.kit.ocrd.workspace.entity.MetsProperties;
import edu.kit.ocrd.workspace.entity.ClassificationMetadata;
import edu.kit.ocrd.workspace.entity.LanguageMetadata;
import edu.kit.ocrd.workspace.entity.PageMetadata;
import edu.kit.ocrd.workspace.MetsDocumentUtil;
import java.io.File;
import java.util.List;
import org.fzk.tools.xml.JaxenUtil;
import org.jdom.Document;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * Test for MetsProperties.
 */
public class MetsPropertiesTest {

    public MetsPropertiesTest() {
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
    public void testMetsPropertiesCreation() {
        MetsProperties metsProperties = new MetsProperties();
        assertTrue(true);
    }

    @Test
    public void testMetsPropertiesWithDocument_validMets() throws Exception {
        System.out.println("Test with valid METS file");
        File file = new File("src/test/resources/mets/validMets.xml");
        assertTrue("File exists!", file.exists());
        Document document = JaxenUtil.getDocument(file);
        MetsProperties metsProperties = MetsDocumentUtil.extractMetadataFromMets(document, "resourceID");
        assertTrue(metsProperties.getTitle().equalsIgnoreCase("Der Herold"));
        assertTrue(metsProperties.getSubTitle().equalsIgnoreCase("Beispiel Untertitel"));
        assertTrue(metsProperties.getYear().equalsIgnoreCase("1839"));
        assertTrue(metsProperties.getLicense().contains("Staatsbibliothek zu Berlin"));
        assertTrue(metsProperties.getAuthor().equalsIgnoreCase("Name of author"));
        assertTrue(metsProperties.getNoOfPages() == 2);
        assertTrue(metsProperties.getPublisher().equalsIgnoreCase("Staatsbibliothek zu Berlin – Preußischer Kulturbesitz, Germany"));
        assertTrue(metsProperties.getPhysicalDescription().equalsIgnoreCase("physical description"));
        assertTrue(metsProperties.getPpn().equalsIgnoreCase("PPN767137728"));
    }

    @Test
    public void testMetsPropertiesWithDocument_validMets_newFormat() throws Exception {
        System.out.println("Test with valid METS file with new format");
        File file = new File("src/test/resources/mets/validMets_newFormat.xml");
        assertTrue("File exists!", file.exists());
        Document document = JaxenUtil.getDocument(file);
        MetsProperties metsProperties = MetsDocumentUtil.extractMetadataFromMets(document, "resourceID");
        assertTrue(metsProperties.getTitle().equalsIgnoreCase("Gedichte"));
        assertTrue(metsProperties.getSubTitle().equalsIgnoreCase(""));
        assertTrue(metsProperties.getYear().equalsIgnoreCase("1778"));
        assertTrue(metsProperties.getLicense().contains("Koordinierte"));
        assertTrue(metsProperties.getAuthor().equalsIgnoreCase("Gottfried August Bürger"));
        assertTrue(metsProperties.getNoOfPages() == 2);
        assertTrue(metsProperties.getPublisher().equalsIgnoreCase("Dieterich"));
        assertTrue(metsProperties.getPhysicalDescription().equalsIgnoreCase("XXII, [4], 328 S."));
    }

    @Test
    public void testMetsPropertiesWithDocument_validMetsWithoutTitle() throws Exception {
        System.out.println("Test with valid METS file without title");
        File file = new File("src/test/resources/mets/validMets_withoutTitle.xml");
        assertTrue("File exists!", file.exists());
        Document document = JaxenUtil.getDocument(file);
        MetsProperties metsProperties = MetsDocumentUtil.extractMetadataFromMets(document, "resourceID");
        assertTrue(metsProperties.getTitle().equalsIgnoreCase(MetsProperties.NO_TITLE));
        assertTrue(metsProperties.getPpn().equalsIgnoreCase("PPN767137728"));
    }

    @Test
    public void testMetsPropertiesWithDocument_invalidMets() throws Exception {
        System.out.println("Test with invalid METS file");
        File file = new File("src/test/resources/mets/invalidMetsWithoutPPN.xml");
        assertTrue("File exists!", file.exists());
        Document document = JaxenUtil.getDocument(file);
        MetsProperties metsProperties = MetsDocumentUtil.extractMetadataFromMets(document, "resourceID");
        assertEquals(metsProperties.getTitle(), "First title");
        assertEquals(metsProperties.getPpn(), MetsProperties.NO_PPN);
    }

    @Test
    public void testMetsPropertiesWithDocument_validMetsWith2PPN() throws Exception {
        System.out.println("Test with valid METS file with 2 PPNs");
        File file = new File("src/test/resources/mets/validMetsWith2PPN.xml");
        assertTrue("File exists!", file.exists());
        Document document = JaxenUtil.getDocument(file);
        MetsProperties metsProperties = MetsDocumentUtil.extractMetadataFromMets(document, "resourceID");
        assertTrue(metsProperties.getTitle().equalsIgnoreCase("Der Herold"));
        assertTrue(metsProperties.getPpn().equalsIgnoreCase("PPN767137728"));
    }

    @Test
    public void testMetsPropertiesWithDocument_validMetsWith2Title() throws Exception {
        System.out.println("Test with valid METS file with 2 titles");
        File file = new File("src/test/resources/mets/validMets_2titles.xml");
        assertTrue("File exists!", file.exists());
        Document document = JaxenUtil.getDocument(file);
        MetsProperties metsProperties = MetsDocumentUtil.extractMetadataFromMets(document, "resourceID");
        assertTrue(metsProperties.getTitle().equalsIgnoreCase("Der Herold"));
        assertTrue(metsProperties.getPpn().equalsIgnoreCase("PPN767137728"));
    }

    @Test
    public void testMetsPropertiesWithDocument_validMetsNewFormat_semanticLabels() throws Exception {
        System.out.println("Test with valid METS file with new format get semantic labels");
        File file = new File("src/test/resources/mets/validMetsNewFormat.xml");
        assertTrue("File exists!", file.exists());
        Document document = JaxenUtil.getDocument(file);
        List<PageMetadata> gtFeatures = MetsDocumentUtil.extractGroundTruthFeaturesFromMets(document, "resourceID");
        assertTrue(gtFeatures.size() == 1);
        assertTrue(gtFeatures.get(0).getFeature().toString().equalsIgnoreCase("granularity/physical/document-related/word"));
    }

    @Test
    public void testMetsPropertiesWithDocument_validMetsNewFormat_language() throws Exception {
        System.out.println("Test with valid METS file with new format get language");
        File file = new File("src/test/resources/mets/validMetsNewFormat.xml");
        assertTrue("File exists!", file.exists());
        Document document = JaxenUtil.getDocument(file);
        List<LanguageMetadata> languages = MetsDocumentUtil.extractLanguageMetadataFromMets(document, "resourceID");
        assertTrue(languages.size() == 1);
        assertTrue(languages.get(0).getLanguage().equalsIgnoreCase("deu"));
    }

    @Test
    public void testMetsPropertiesWithDocument_validMetsNewFormat_classification() throws Exception {
        System.out.println("Test with valid METS file with new format get classification");
        File file = new File("src/test/resources/mets/validMetsNewFormat.xml");
        assertTrue("File exists!", file.exists());
        Document document = JaxenUtil.getDocument(file);
        List<ClassificationMetadata> classifications = MetsDocumentUtil.extractClassificationMetadataFromMets(document, "resourceID");
        assertTrue(classifications.size() == 2);
    }

    /**
     * Test of getId method, of class MetsProperties.
     */
    @Test
    public void testSetGetId() {
        System.out.println("setGetId");
        MetsProperties instance = new MetsProperties();
        String expResult = "id_0001";
        instance.setId(expResult);
        String result = instance.getId();
        assertEquals(expResult, result);
    }

    /**
     * Test of getResourceId method, of class MetsProperties.
     */
    @Test
    public void testSetGetResourceId() {
        System.out.println("setGetResourceId");
        MetsProperties instance = new MetsProperties();
        String expResult = "resourceid_0001";
        instance.setResourceId(expResult);
        String result = instance.getResourceId();
        assertEquals(expResult, result);
    }

    /**
     * Test of setTitle method, of class MetsProperties.
     */
    @Test
    public void testSetGetTitle() {
        System.out.println("setGetTitle");
        String title = "First title";
        MetsProperties instance = new MetsProperties();
        instance.setTitle(title);
        String result = instance.getTitle();
        assertEquals(title, result);
    }

    /**
     * Test of getPpn method, of class MetsProperties.
     */
    @Test
    public void testSetGetPpn() {
        System.out.println("getPpn");
        MetsProperties instance = new MetsProperties();
        String expResult = "anyPPN";
        instance.setPpn(expResult);
        String result = instance.getPpn();
        assertEquals(expResult, result);
    }

    /**
     * Test of toString method, of class MetsProperties.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        MetsProperties instance = new MetsProperties();
        System.out.println(instance);
        String expResult = "MetsProperties [id=null, resourceId=null, title=No title available!, ppn=No PPN available!]";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }

}
