/*
 * DISCLAIMER
 *
 * Copyright 2017 ArangoDB GmbH, Cologne, Germany
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Copyright holder is ArangoDB GmbH, Cologne, Germany
 */
package edu.kit.datamanager.metastore.runner;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;

import com.arangodb.springframework.core.ArangoOperations;
import edu.kit.datamanager.metastore.entity.IResourceId;
import edu.kit.datamanager.metastore.entity.IUrl;
import edu.kit.datamanager.metastore.entity.IVersion;
import edu.kit.datamanager.metastore.entity.MdType;
import edu.kit.datamanager.metastore.entity.MetsDocument;
import edu.kit.datamanager.metastore.entity.MetsFile;
import edu.kit.datamanager.metastore.entity.SectionDocument;
import edu.kit.datamanager.metastore.entity.XmlSchemaDefinition;
import edu.kit.datamanager.metastore.repository.MetsDocumentRepository;
import edu.kit.datamanager.metastore.repository.MetsFileRepository;
import edu.kit.datamanager.metastore.repository.SectionDocumentRepository;
import edu.kit.datamanager.metastore.repository.XmlSchemaDefinitionRepository;
import java.util.Iterator;
import java.util.List;
import org.springframework.dao.DataAccessException;

/**
 * @author Volker Hartmann
 *
 */
@ComponentScan("edu.kit.datamanager.metastore")
public class CrudRunner implements CommandLineRunner {

  @Autowired
  private ArangoOperations operations;
  @Autowired
  private MetsDocumentRepository repository;
  @Autowired
  private SectionDocumentRepository secRepository;
  @Autowired
  private XmlSchemaDefinitionRepository xsdRepository;
  @Autowired
  private MetsFileRepository metsFileRepository;

  @Override
  public void run(final String... args) throws Exception {
    final String METS_DOCUMENT = "mets";
    final String SECTION_DOCUMENT = "sec";
    final String METS_FILES = "file";
    final String XSD = "xsd";
    List<String> argumentList = Arrays.asList(args);
    System.out.println("Run CRUD Runner!");
    // first drop the database so that we can run this multiple times with the same dataset
    try {
      operations.dropDatabase();
    } catch (DataAccessException dae) {
      System.out.println("This message should be printed only once!");
      System.out.println(dae.toString());
    }

    System.out.println("# CRUD operations");

    // save a single entity in the database
    // there is no need of creating the collection first. This happen automatically
    System.out.println("********************************************************************************************************************");
    System.out.println("*******************************          Build  Database         ***************************************************");
    System.out.println("********************************************************************************************************************");
    if (argumentList.contains(METS_DOCUMENT)) {
      System.out.println("*******************************          MetsDocuments         ***************************************************");
      final MetsDocument metsDocument = new MetsDocument("id_0001", "someXML");
      repository.save(metsDocument);
      System.out.println(String.format("metsDocument saved in the database with id: '%s' with version: '%d' at %tD", metsDocument.getId(), metsDocument.getVersion(), metsDocument.getLastModified()));
      for (MetsDocument document : createMetsDocuments()) {
        System.out.println(document);
        repository.save(document);
        System.out.println(String.format("metsDocument saved in the database with id: '%s' with version: '%d' at %tD", document.getId(), document.getVersion(), document.getLastModified()));
      }
    }
    if (argumentList.contains(SECTION_DOCUMENT)) {
      System.out.println("*******************************          SectionDocuments         ***************************************************");
      for (SectionDocument document : createSectionDocuments()) {
        secRepository.save(document);
        System.out.println(String.format("secDocument saved in the database with id: '%s' with prefix: '%s' resourceId %s", document.getId(), document.getPrefix(), document.getResourceId()));
      }
    }
    if (argumentList.contains(METS_FILES)) {
      System.out.println("*******************************          MetsFiles         ***************************************************");
      Collection<MetsFile> createMetsFiles = createMetsFiles();
      for (MetsFile metsFile : createMetsFiles) {
        metsFileRepository.save(metsFile);
        System.out.println("MetsFile saved! " + metsFile);
      }
    }
    if (argumentList.contains(XSD)) {
      for (XmlSchemaDefinition document : createSchemaDefinitions()) {
        xsdRepository.save(document);
        System.out.println(String.format("xsdDocument saved in the database with id: '%s' with prefix: '%s' namespace %s", document.getId(), document.getPrefix(), document.getNamespace()));
      }
    }
    System.out.println("********************************************************************************************************************");
    System.out.println("************************         START TESTS            ************************************************************");
    System.out.println("********************************************************************************************************************");

    if (argumentList.contains(SECTION_DOCUMENT)) {
      System.out.println("********************************************************************************************************************");
      System.out.println("************************       SectionDocument         ************************************************************");
      System.out.println("********************************************************************************************************************");
      // the generated id from the database is set in the original entity
      //Thread.sleep(2000);
      System.out.println("secRepository.findByResourceIdAndPrefix(\"id_0002\", \"dc\")");
      Iterable<SectionDocument> findByResourceIdAndPrefix = secRepository.findByResourceIdAndPrefix("id_0002", "dc");
      System.out.println(findByResourceIdAndPrefix.iterator().next().getSectionDocument());

    }

    if (argumentList.contains(METS_DOCUMENT)) {
      System.out.println("********************************************************************************************************************");
      System.out.println("*********** METSDOCUMENT  METSDOCUMENT  METSDOCUMENT  METSDOCUMENT  METSDOCUMENT ***********************************");
      System.out.println("********************************************************************************************************************");
      Iterable<MetsDocument> metsDoc;
      Iterator<MetsDocument> metsDocIterator;
      Iterable<IResourceId> resourceIdOfMetsDoc;
      Iterator<IResourceId> resourceIdIterator;
      Iterable<IVersion> version;
      Iterator<IVersion> versionIterator;

      System.out.println("********************************************************************************************************************");
      System.out.println("************************      Find all current versions of MetsDocuments        ************************************");
      System.out.println("********************************************************************************************************************");
      System.out.println("repository.findByCurrentTrue()");
      Iterable<MetsDocument> xmlOnly = repository.findByCurrentTrue();
      Iterator<MetsDocument> xmlOnlyIterator = xmlOnly.iterator();

      while (xmlOnlyIterator.hasNext()) {
        System.out.println(xmlOnlyIterator.next());
      }

      System.out.println("********************************************************************************************************************");
      System.out.println("************************      Find latest MetsDocument by resourceId    ********************************************");
      System.out.println("********************************************************************************************************************");
      System.out.println("repository.findByResourceIdAndCurrentTrue(\"id_0002\")");
      MetsDocument metsDocSingle = repository.findByResourceIdAndCurrentTrue("id_0002");

      System.out.println(metsDocSingle);

      System.out.println("repository.findByResourceIdAndCurrentTrue(\"id_0015\")");
      metsDocSingle = repository.findByResourceIdAndCurrentTrue("id_0015");

      System.out.println(metsDocSingle);

      System.out.println("********************************************************************************************************************");
      System.out.println("************************      Find all versions of MetsDocument by resourceId   ************************************");
      System.out.println("********************************************************************************************************************");
      System.out.println("repository.findVersionByResourceIdOrderByVersionDesc(\"id_0002\")");
      version = repository.findVersionByResourceIdOrderByVersionDesc("id_0002");
      versionIterator = version.iterator();
      Integer oldVersion = -1;
      while (versionIterator.hasNext()) {
        IVersion version2 = versionIterator.next();
        System.out.println(version2.getVersion());
        System.out.println(version2);
        oldVersion = version2.getVersion();
      }
      System.out.println("********************************************************************************************************************");
      System.out.println("************************      Find MetsDocument by resourceId and version    ***************************************");
      System.out.println("********************************************************************************************************************");
      System.out.println("repository.findByResourceIdAndVersion(\"id_0002\", oldVersion);");
      metsDocSingle = repository.findByResourceIdAndVersion("id_0002", oldVersion);

      System.out.println(metsDocSingle);
    }
    
    if (argumentList.contains(METS_FILES)) {
      System.out.println("********************************************************************************************************************");
      System.out.println("*********** METSFILE METSFILE METSFILE METSFILE METSFILE METSFILE METSFILE *****************************************");
      System.out.println("********************************************************************************************************************");
      Iterable<MetsFile> metsFile;
      Iterator<MetsFile> metsFileIterator;
      Iterable<IUrl> urlOfMetsFile;
      Iterator<IUrl> urlIterator;
      System.out.println("********************************************************************************************************************");
      System.out.println("************************      Find one MetsFile by resourceId and fileID (highest version)     ******************************************************");
      System.out.println("********************************************************************************************************************");
      MetsFile metsFile1 = metsFileRepository.findTop1DistinctByResourceIdAndFileIdOrderByVersionDesc("id_0002", "PAGE-0001_IMG_BIN");
      System.out.println(metsFile1);
      System.out.println("********************************************************************************************************************");
      System.out.println("************************      Find MetsFile by resourceId and USE      ******************************************************");
      System.out.println("********************************************************************************************************************");
      System.out.println("metsFileRepository.findByResourceIdAndUse(\"id_0002\", \"OCR-D-GT-IMG-BIN\")");
      metsFile = metsFileRepository.findByResourceIdAndUse("id_0002", "OCR-D-GT-IMG-BIN");
      metsFileIterator = metsFile.iterator();
      while (metsFileIterator.hasNext()) {
        System.out.println(metsFileIterator.next());
      }
      System.out.println("********************************************************************************************************************");
      System.out.println("************************      Find MetsFile by resourceId and USE IN      ******************************************************");
      System.out.println("********************************************************************************************************************");
      System.out.println("metsFileRepository.findByResourceIdAndUseIn(\"id_0002\", Arrays.asList(\"OCR-D-GT-IMG-BIN\", \"OCR-D-GT-IMG-CROP\")");
      metsFile = metsFileRepository.findByResourceIdAndUseIn("id_0002", Arrays.asList("OCR-D-GT-IMG-BIN", "OCR-D-GT-IMG-CROP"));
      metsFileIterator = metsFile.iterator();
      while (metsFileIterator.hasNext()) {
        System.out.println(metsFileIterator.next());
      }
      System.out.println("********************************************************************************************************************");
      System.out.println("************************      Find MetsFile URL by resourceId and USE      ******************************************************");
      System.out.println("********************************************************************************************************************");
      System.out.println("metsFileRepository.findUrlByResourceIdAndUse(\"id_0002\", \"OCR-D-GT-IMG-BIN\")");
      urlOfMetsFile = metsFileRepository.findUrlByResourceIdAndUse("id_0002", "OCR-D-GT-IMG-BIN");
      urlIterator = urlOfMetsFile.iterator();
      while (urlIterator.hasNext()) {
        System.out.println(urlIterator.next().getUrl());
      }
      System.out.println("********************************************************************************************************************");
      System.out.println("************************      Find MetsFile URL by resourceId and USE IN      ******************************************************");
      System.out.println("********************************************************************************************************************");
      System.out.println("metsFileRepository.findUrlByResourceIdAndUseIn(\"id_0002\", Arrays.asList(\"OCR-D-GT-IMG-BIN\", \"OCR-D-GT-IMG-CROP\")");
      urlOfMetsFile = metsFileRepository.findUrlByResourceIdAndUseIn("id_0002", Arrays.asList("OCR-D-GT-IMG-BIN", "OCR-D-GT-IMG-CROP"));
      urlIterator = urlOfMetsFile.iterator();
      while (urlIterator.hasNext()) {
        System.out.println(urlIterator.next().getUrl());
      }
      System.out.println("********************************************************************************************************************");
      System.out.println("************************      Find MetsFile by resourceId and GROUPID     ******************************************************");
      System.out.println("********************************************************************************************************************");
      System.out.println("metsFileRepository.findByResourceIdAndGroupId(\"id_0002\", \"PAGE-0001\")");
      metsFile = metsFileRepository.findByResourceIdAndGroupId("id_0002", "PAGE-0001");
      metsFileIterator = metsFile.iterator();
      while (metsFileIterator.hasNext()) {
        System.out.println(metsFileIterator.next());
      }
      System.out.println("********************************************************************************************************************");
      System.out.println("************************      Find MetsFile by resourceId and GROUPID IN      ******************************************************");
      System.out.println("********************************************************************************************************************");
      System.out.println("metsFileRepository.findByResourceIdAndGroupIdIn(\"id_0002\", Arrays.asList(\"PAGE-0001\", \"PAGE-0002\")");
      metsFile = metsFileRepository.findByResourceIdAndGroupIdIn("id_0002", Arrays.asList("PAGE-0001", "PAGE-0002"));
      metsFileIterator = metsFile.iterator();
      while (metsFileIterator.hasNext()) {
        System.out.println(metsFileIterator.next());
      }
      System.out.println("********************************************************************************************************************");
      System.out.println("************************      Find MetsFile URL by resourceId and GROUPID      ******************************************************");
      System.out.println("********************************************************************************************************************");
      System.out.println("metsFileRepository.findUrlByResourceIdAndGroupId(\"id_0002\", \"PAGE-0001\")");
      urlOfMetsFile = metsFileRepository.findUrlByResourceIdAndGroupId("id_0002", "PAGE-0001");
      urlIterator = urlOfMetsFile.iterator();
      while (urlIterator.hasNext()) {
        System.out.println(urlIterator.next().getUrl());
      }
      System.out.println("********************************************************************************************************************");
      System.out.println("************************      Find MetsFile URL by resourceId and GROUPID IN      ******************************************************");
      System.out.println("********************************************************************************************************************");
      System.out.println("metsFileRepository.findUrlByResourceIdAndGroupIdIn(\"id_0002\", Arrays.asList(\"PAGE-0001\", \"PAGE-0002\")");
      urlOfMetsFile = metsFileRepository.findUrlByResourceIdAndGroupIdIn("id_0002", Arrays.asList("PAGE-0001", "PAGE-0002"));
      urlIterator = urlOfMetsFile.iterator();
      while (urlIterator.hasNext()) {
        System.out.println(urlIterator.next().getUrl());
      }
      System.out.println("********************************************************************************************************************");
      System.out.println("************************      Find MetsFile by resourceId and USE AND GROUPID     ******************************************************");
      System.out.println("********************************************************************************************************************");
      System.out.println("metsFileRepository.findByResourceIdAndUseAndGroupId(\"id_0002\", \"OCR-D-GT-IMG-BIN\", \"PAGE-0001\")");
      metsFile = metsFileRepository.findByResourceIdAndUseAndGroupId("id_0002", "OCR-D-GT-IMG-BIN", "PAGE-0001");
      metsFileIterator = metsFile.iterator();
      while (metsFileIterator.hasNext()) {
        System.out.println(metsFileIterator.next());
      }
      System.out.println("********************************************************************************************************************");
      System.out.println("************************      Find MetsFile by resourceId and USE AND GROUPID IN      ******************************************************");
      System.out.println("********************************************************************************************************************");
      System.out.println("metsFileRepository.findByResourceIdAndUseInAndGroupIdIn(\"id_0002\", Arrays.asList(\"OCR-D-GT-IMG-BIN\", \"OCR-D-GT-IMG-DESPEC\"), Arrays.asList(\"PAGE-0001\", \"PAGE-0002\")");
      metsFile = metsFileRepository.findByResourceIdAndUseInAndGroupIdIn("id_0002", Arrays.asList("OCR-D-GT-IMG-BIN", "OCR-D-GT-IMG-DESPEC"), Arrays.asList("PAGE-0001", "PAGE-0002"));
      metsFileIterator = metsFile.iterator();
      while (metsFileIterator.hasNext()) {
        System.out.println(metsFileIterator.next());
      }
      System.out.println("********************************************************************************************************************");
      System.out.println("************************      Find MetsFile URL by resourceId USE AND and GROUPID      ******************************************************");
      System.out.println("********************************************************************************************************************");
      System.out.println("metsFileRepository.findUrlByResourceIdAndUseAndGroupId(\"id_0002\", \"OCR-D-GT-IMG-BIN\", \"PAGE-0001\")");
      urlOfMetsFile = metsFileRepository.findUrlByResourceIdAndUseAndGroupId("id_0002", "OCR-D-GT-IMG-BIN", "PAGE-0001");
      urlIterator = urlOfMetsFile.iterator();
      while (urlIterator.hasNext()) {
        System.out.println(urlIterator.next().getUrl());
      }
      System.out.println("********************************************************************************************************************");
      System.out.println("************************      Find MetsFile URL by resourceId USE AND and GROUPID IN      ******************************************************");
      System.out.println("********************************************************************************************************************");
      System.out.println("metsFileRepository.findUrlByResourceIdAndUseInAndGroupIdIn(\"id_0002\", Arrays.asList(\"OCR-D-GT-IMG-BIN\", \"OCR-D-GT-IMG-DESPEC\"), Arrays.asList(\"PAGE-0001\", \"PAGE-0002\")");
      urlOfMetsFile = metsFileRepository.findUrlByResourceIdAndUseInAndGroupIdIn("id_0002", Arrays.asList("OCR-D-GT-IMG-BIN", "OCR-D-GT-IMG-DESPEC"), Arrays.asList("PAGE-0001", "PAGE-0002"));
      urlIterator = urlOfMetsFile.iterator();
      while (urlIterator.hasNext()) {
        System.out.println(urlIterator.next().getUrl());
      }
    }
    //
//		// lets take a look whether we can find Ned Stark in the database
//		final Character foundNed = repository.findById(nedStark.getId()).get();
//		System.out.println(String.format("Found %s", foundNed));
//
//		// as everyone probably knows Ned Stark died in the first season.
//		// So we have to update his 'alive' flag
//		nedStark.setAlive(false);
//		repository.save(nedStark);
//		final Character deadNed = repository.findById(nedStark.getId()).get();
//		System.out.println(String.format("Ned Stark after 'alive' flag was updated: %s", deadNed));
//
//		// lets save some additional characters
//		final Collection<Character> createCharacters = createCharacters();
//		System.out.println(String.format("Save %s additional characters", createCharacters.size()));
//		repository.saveAll(createCharacters);
//
//		final Iterable<Character> all = repository.findAll();
//		final long count = StreamSupport.stream(Spliterators.spliteratorUnknownSize(all.iterator(), 0), false).count();
//		System.out.println(String.format("A total of %s characters are persisted in the database", count));
//
//		System.out.println("## Return all characters sorted by name");
//		final Iterable<Character> allSorted = repository.findAll(new Sort(Sort.Direction.ASC, "name"));
//		allSorted.forEach(System.out::println);
//
//		System.out.println("## Return the first 5 characters sorted by name");
//		final Page<Character> first5Sorted = repository
//				.findAll(PageRequest.of(0, 5, new Sort(Sort.Direction.ASC, "name")));
//		first5Sorted.forEach(System.out::println);
  }

  public static Collection<MetsDocument> createMetsDocuments() {
    MetsDocument eins = new MetsDocument("id_0002", "Am staerksten");
    MetsDocument zwei = eins.updateMetsContent("noch staerker");
    MetsDocument drei = zwei.updateMetsContent("staerker");
    MetsDocument vier = drei.updateMetsContent("stark");
    return Arrays.asList(
            eins,
            zwei,
            drei, 
            vier,
            new MetsDocument("id_0003me", "Lannister"),
            new MetsDocument("id_0004sei", "Lannister"),
            new MetsDocument("id_0005ah", "Mormont"),
            new MetsDocument("id_0006e6rys", "Targaryen"),
            new MetsDocument("id_0007sa", "Stark"),
            new MetsDocument("id_0008b", "Stark"),
            new MetsDocument("id_0009n", "Stark"),
            new MetsDocument("id_0010dor", "Clegane"),
            new MetsDocument("id_0001l", "Drogo"),
            new MetsDocument("id_0012os", "Seaworth"),
            new MetsDocument("id_0013nnis", "Baratheon"),
            new MetsDocument("id_00140gaery", "Tyrell"),
            new MetsDocument("id_0015", "XML 00015"),
            new MetsDocument("id_0016", "Maegyr"),
            new MetsDocument("id_0017", "xml 0017"),
            new MetsDocument("id_0018", "xml00018"),
            new MetsDocument("id_0019", "Bolton"),
            new MetsDocument("id_0020", "Naharis"),
            new MetsDocument("id_0021", "Baratheon"),
            new MetsDocument("id_0022", "Bolton"));
  }

  public static Collection<SectionDocument> createSectionDocuments() {
    return Arrays.asList(
            new SectionDocument("id_0002", 1, "bmd", "secId", MdType.OTHER, null, "someBMDContent"),
            new SectionDocument("id_0002", 1, "tei", "secId", MdType.OTHER, null, "someTeiContent"),
            new SectionDocument("id_0002", 1, "dc", "secId", MdType.OTHER, null, "someDCContent"),
            new SectionDocument("id_0002", 1, "file", "secId", MdType.OTHER, null, "someFileContent"),
            new SectionDocument("id_0002", 1, "gt_ocr-d", "GT_OCR-D", MdType.OTHER, null, "<ocrd><groundTruth><language>deutsch</language></groundTruth></ocrd>someFileContent"));
//            new SectionDocument("id_0003me", "Lannister"),
//            new SectionDocument("id_0004sei", "Lannister"),
//            new SectionDocument("id_0005ah", "Mormont"),
//            new SectionDocument("id_0006e6rys", "Targaryen"),
//            new SectionDocument("id_0007sa", "Stark"),
//            new SectionDocument("id_0008b", "Stark"),
//            new SectionDocument("id_0009n", "Stark"),
//            new SectionDocument("id_0010dor", "Clegane"),
//            new SectionDocument("id_0001l", "Drogo"),
//            new SectionDocument("id_0012os", "Seaworth"),
//            new SectionDocument("id_0013nnis", "Baratheon"),
//            new SectionDocument("id_00140gaery", "Tyrell"),
//            new SectionDocument("id_0015", "XML 00015"),
//            new SectionDocument("id_0016", "Maegyr"),
//            new SectionDocument("id_0017", "xml 0017"),
//            new SectionDocument("id_0018", "xml00018"),
//            new SectionDocument("id_0019", "Bolton"),
//            new SectionDocument("id_0020", "Naharis"),
//            new SectionDocument("id_0021", "Baratheon"),
//            new SectionDocument("id_0022", "Bolton"));
  }

  public static Collection<XmlSchemaDefinition> createSchemaDefinitions() {
    return Arrays.asList(
            new XmlSchemaDefinition("bmd", "namespace1", "someBMDContent"),
            new XmlSchemaDefinition("tei", "namespace2", "someTeiContent"),
            new XmlSchemaDefinition("dc", "namespace3", "someDCContent"),
            new XmlSchemaDefinition("file", "namespace4", "someFileContent"));
  }

  public static Collection<MetsFile> createMetsFiles() {
    return Arrays.asList(
            new MetsFile("PAGE-0001_IMG_BIN",    1, "id_0002", "image/png", "PAGE-0001", "OCR-D-GT-IMG-BIN",    "url1"),
            new MetsFile("PAGE-0001_IMG-CROP",   1, "id_0002", "image/png", "PAGE-0001", "OCR-D-GT-IMG-CROP",   "url2"),
            new MetsFile("PAGE-0001_IMG-DESPEC", 1, "id_0002", "image/png", "PAGE-0001", "OCR-D-GT-IMG-DESPEC", "url3"),
            new MetsFile("PAGE-0001_IMG-DEWARP", 1, "id_0002", "image/png", "PAGE-0001", "OCR-D-GT-IMG-DEWARP", "url4"),
            new MetsFile("PAGE-0001_IMG_BIN",    2, "id_0002", "image/png", "PAGE-0001", "OCR-D-GT-IMG-BIN",    "url1_v2"),
            new MetsFile("PAGE-0001_IMG-CROP",   2, "id_0002", "image/png", "PAGE-0001", "OCR-D-GT-IMG-CROP",   "url2_v2"),
            new MetsFile("PAGE-0001_IMG-DESPEC", 2, "id_0002", "image/png", "PAGE-0001", "OCR-D-GT-IMG-DESPEC", "url3_v2"),
            new MetsFile("PAGE-0001_IMG-DEWARP", 2, "id_0002", "image/png", "PAGE-0001", "OCR-D-GT-IMG-DEWARP", "url4_v2"),
            new MetsFile("PAGE-0001_IMG_BIN",    1, "id_0015", "image/png", "PAGE-0001", "OCR-D-GT-IMG-BIN",    "url11"),
            new MetsFile("PAGE-0001_IMG-CROP",   1, "id_0015", "image/png", "PAGE-0001", "OCR-D-GT-IMG-CROP",   "url21"),
            new MetsFile("PAGE-0001_IMG-DESPEC", 1, "id_0015", "image/png", "PAGE-0001", "OCR-D-GT-IMG-DESPEC", "url31"),
            new MetsFile("PAGE-0001_IMG-DEWARP", 1, "id_0015", "image/png", "PAGE-0001", "OCR-D-GT-IMG-DEWARP", "url41"),
            new MetsFile("PAGE-0001_IMG_BIN",    1, "id_0016", "image/png", "PAGE-0001", "OCR-D-GT-IMG-BIN",    "url16"),
            new MetsFile("PAGE-0001_IMG-CROP",   1, "id_0017", "image/png", "PAGE-0001", "OCR-D-GT-IMG-CROP",   "url17"),
            new MetsFile("PAGE-0001_IMG-DESPEC", 1, "id_0018", "image/png", "PAGE-0001", "OCR-D-GT-IMG-DESPEC", "url18"),
            new MetsFile("PAGE-0001_IMG-DEWARP", 1, "id_0019", "image/png", "PAGE-0001", "OCR-D-GT-IMG-DEWARP", "url19"),
            new MetsFile("PAGE-0002_IMG_BIN",    1, "id_0002", "image/png", "PAGE-0002", "OCR-D-GT-IMG-BIN",    "url2_1"),
            new MetsFile("PAGE-0002_IMG-CROP",   1, "id_0002", "image/png", "PAGE-0002", "OCR-D-GT-IMG-CROP",   "url2_2"),
            new MetsFile("PAGE-0002_IMG-DESPEC", 1, "id_0002", "image/png", "PAGE-0002", "OCR-D-GT-IMG-DESPEC", "url2_3"),
            new MetsFile("PAGE-0002_IMG-DEWARP", 1, "id_0002", "image/png", "PAGE-0002", "OCR-D-GT-IMG-DEWARP", "url2_4"),
            new MetsFile("PAGE-0002_IMG_BIN",    1, "id_0015", "image/png", "PAGE-0002", "OCR-D-GT-IMG-BIN",    "url2_11"),
            new MetsFile("PAGE-0002_IMG-CROP",   1, "id_0015", "image/png", "PAGE-0002", "OCR-D-GT-IMG-CROP",   "url2_21"),
            new MetsFile("PAGE-0002_IMG-DESPEC", 1, "id_0015", "image/png", "PAGE-0002", "OCR-D-GT-IMG-DESPEC", "url2_31"),
            new MetsFile("PAGE-0002_IMG-DEWARP", 1, "id_0015", "image/png", "PAGE-0002", "OCR-D-GT-IMG-DEWARP", "url2_41"),
            new MetsFile("PAGE-0002_IMG_BIN",    1, "id_0016", "image/png", "PAGE-0002", "OCR-D-GT-IMG-BIN",    "url2_16"),
            new MetsFile("PAGE-0002_IMG-CROP",   1, "id_0017", "image/png", "PAGE-0002", "OCR-D-GT-IMG-CROP",   "url2_17"),
            new MetsFile("PAGE-0002_IMG-DESPEC", 1, "id_0018", "image/png", "PAGE-0002", "OCR-D-GT-IMG-DESPEC", "url2_18"),
            new MetsFile("PAGE-0002_IMG-DEWARP", 1, "id_0019", "image/png", "PAGE-0002", "OCR-D-GT-IMG-DEWARP", "url2_19"));
  }

}
