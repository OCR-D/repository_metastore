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
import edu.kit.datamanager.metastore.entity.IUrlOfMetsFile;
import edu.kit.datamanager.metastore.entity.MdType;
import edu.kit.datamanager.metastore.entity.MetsDocument;
import edu.kit.datamanager.metastore.entity.MetsFile;
import edu.kit.datamanager.metastore.entity.MetsFileOf;
import edu.kit.datamanager.metastore.entity.SectionDocument;
import edu.kit.datamanager.metastore.entity.XmlSchemaDefinition;
import edu.kit.datamanager.metastore.repository.MetsDocumentRepository;
import edu.kit.datamanager.metastore.repository.MetsFileOfRepository;
import edu.kit.datamanager.metastore.repository.MetsFileRepository;
import edu.kit.datamanager.metastore.repository.SectionDocumentRepository;
import edu.kit.datamanager.metastore.repository.XmlSchemaDefinitionRepository;
import java.util.Iterator;
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
  @Autowired
  private MetsFileOfRepository metsFileOfRepository;

  @Override
  public void run(final String... args) throws Exception {
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
    System.out.println("*******************************           MetsDocument           ***************************************************");
    System.out.println("********************************************************************************************************************");
    final MetsDocument metsDocument = new MetsDocument("id_0001", "someXML");
    repository.save(metsDocument);
    System.out.println(String.format("metsDocument saved in the database with id: '%s' with version: '%d' at %tD", metsDocument.getId(), metsDocument.getVersion(), metsDocument.getLastModified()));
    for (MetsDocument document : createMetsDocuments()) {
      repository.save(document);
    System.out.println(String.format("metsDocument saved in the database with id: '%s' with version: '%d' at %tD", document.getId(), document.getVersion(), document.getLastModified()));
    }
    for (SectionDocument document : createSectionDocuments()) {
      secRepository.save(document);
    System.out.println(String.format("secDocument saved in the database with id: '%s' with prefix: '%s' resourceId %s", document.getId(), document.getPrefix(), document.getResourceId()));
    }
    System.out.println("********************************************************************************************************************");
    System.out.println("************************       SectionDocument         ************************************************************");
   System.out.println("********************************************************************************************************************");
    // the generated id from the database is set in the original entity
    Thread.sleep(2000);
    
    Iterable<SectionDocument> findByResourceIdAndPrefix = secRepository.findByResourceIdAndPrefix("id_0002", "dc");
    System.out.println(findByResourceIdAndPrefix.iterator().next().getSectionDocument());
    
    for (XmlSchemaDefinition document : createSchemaDefinitions()) {
      xsdRepository.save(document);
    System.out.println(String.format("xsdDocument saved in the database with id: '%s' with prefix: '%s' namespace %s", document.getId(), document.getPrefix(), document.getNamespace()));
    }
    System.out.println("********************************************************************************************************************");
    System.out.println("************************      MetsFile / MetsFileOf                      *******************************************");
   System.out.println("********************************************************************************************************************");
    Collection<MetsFile> createMetsFiles = createMetsFiles();
    for (MetsFile metsFile : createMetsFiles) {
        String resourceId = metsFile.getResourceId();
        System.out.println("ResourceId: " + resourceId);
        metsFileRepository.save(metsFile);
        System.out.println("MetsFile saved!");
        Iterable<MetsDocument> findByResourceId = repository.findByResourceId(resourceId);
        MetsDocument mDoc = findByResourceId.iterator().next();
        System.out.println("Find MetsDocument: " + mDoc);
        metsFileOfRepository.save(new MetsFileOf(metsFile, mDoc));
         System.out.println("MetsFileOf saved!");
       
    System.out.println(String.format("MetsFile saved in the database with id: '%s' with use: '%s' groupId %s", metsFile.getFileId(), metsFile.getUse(), metsFile.getGroupId()));
    }
     System.out.println("********************************************************************************************************************");
     System.out.println("************************      MetsFiles of MetsDocument with id id_0002  *******************************************");
   System.out.println("********************************************************************************************************************");
  
    MetsDocument doc = repository.findByResourceId("id_0002").iterator().next();
    for (MetsFile file : doc.getMetsFiles()) {
      System.out.println(file);
    }
     System.out.println("********************************************************************************************************************");
     System.out.println("************************      Find URL of MetsFiles by USE        ******************************************************");
   System.out.println("********************************************************************************************************************");
  Iterable<MetsFile> findUrl = repository.findMetsFileByMetsFilesUse("OCR-D-GT-IMG-BIN");
    Iterator<MetsFile> urlIterator = findUrl.iterator();
    while (urlIterator.hasNext() ) {
      System.out.println(urlIterator.next().getUrl());
    }
    System.out.println("********************************************************************************************************************");
     System.out.println("************************      Find MetsDocument by USE        ******************************************************");
   System.out.println("********************************************************************************************************************");
  Iterable<MetsDocument> findMetsFileByUse = repository.findByMetsFilesUse("OCR-D-GT-IMG-BIN");
    Iterator<MetsDocument> iterator = findMetsFileByUse.iterator();
    while (iterator.hasNext() ) {
      System.out.println(iterator.next());
    }
    System.out.println("********************************************************************************************************************");
     System.out.println("************************      Find MetsDocument by at least one USE   *********************************************");
   System.out.println("********************************************************************************************************************");
  Iterable<MetsDocument> findMetsFileByUseIn = repository.findByMetsFilesUseIn(Arrays.asList("OCR-D-GT-IMG-BIN", "OCR-D-GT-IMG-DESPEC"));
    Iterator<MetsDocument> useInIterator = findMetsFileByUseIn.iterator();
    while (useInIterator.hasNext() ) {
      System.out.println(useInIterator.next());
    }
    System.out.println("********************************************************************************************************************");
     System.out.println("************************      Find MetsDocument by USE  and ID      ******************************************************");
   System.out.println("********************************************************************************************************************");
  Iterable<MetsDocument> findByResourceIdAndMetsFilesUse = repository.findByResourceIdAndMetsFilesUse("id_0002", "OCR-D-GT-IMG-BIN");
    Iterator<MetsDocument> resourceAndUseIterator = findByResourceIdAndMetsFilesUse.iterator();
    while (resourceAndUseIterator.hasNext() ) {
      System.out.println(resourceAndUseIterator.next());
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
    return Arrays.asList(
            new MetsDocument("id_0002", "Stark"),
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
            new SectionDocument("id_0002", "bmd", "secId", MdType.OTHER, null, "someBMDContent"),
            new SectionDocument("id_0002", "tei", "secId", MdType.OTHER, null, "someTeiContent"),
            new SectionDocument("id_0002", "dc", "secId", MdType.OTHER, null, "someDCContent"),
            new SectionDocument("id_0002", "file", "secId", MdType.OTHER, null, "someFileContent"),
            new SectionDocument("id_0002", "gt_ocr-d", "GT_OCR-D", MdType.OTHER, null, "<ocrd><groundTruth><language>deutsch</language></groundTruth></ocrd>someFileContent"));
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
            new MetsFile("PAGE-0001_IMG_BIN",    "id_0002", "image/png", "PAGE-0001", "OCR-D-GT-IMG-BIN",    "url1"),
            new MetsFile("PAGE-0001_IMG-CROP",   "id_0002", "image/png", "PAGE-0001", "OCR-D-GT-IMG-CROP",   "url2"),
            new MetsFile("PAGE-0001_IMG-DESPEC", "id_0002", "image/png", "PAGE-0001", "OCR-D-GT-IMG-DESPEC", "url3"),
            new MetsFile("PAGE-0001_IMG-DEWARP", "id_0002", "image/png", "PAGE-0001", "OCR-D-GT-IMG-DEWARP", "url4"),
            new MetsFile("PAGE-0001_IMG_BIN",    "id_0015", "image/png", "PAGE-0001", "OCR-D-GT-IMG-BIN",    "url11"),
            new MetsFile("PAGE-0001_IMG-CROP",   "id_0015", "image/png", "PAGE-0001", "OCR-D-GT-IMG-CROP",   "url21"),
            new MetsFile("PAGE-0001_IMG-DESPEC", "id_0015", "image/png", "PAGE-0001", "OCR-D-GT-IMG-DESPEC", "url31"),
            new MetsFile("PAGE-0001_IMG-DEWARP", "id_0015", "image/png", "PAGE-0001", "OCR-D-GT-IMG-DEWARP", "url41"),
            new MetsFile("PAGE-0001_IMG_BIN",    "id_0016", "image/png", "PAGE-0001", "OCR-D-GT-IMG-BIN",    "url16"),
            new MetsFile("PAGE-0001_IMG-CROP",   "id_0017", "image/png", "PAGE-0001", "OCR-D-GT-IMG-CROP",   "url17"),
            new MetsFile("PAGE-0001_IMG-DESPEC", "id_0018", "image/png", "PAGE-0001", "OCR-D-GT-IMG-DESPEC", "url18"),
            new MetsFile("PAGE-0001_IMG-DEWARP", "id_0019", "image/png", "PAGE-0001", "OCR-D-GT-IMG-DEWARP", "url19"));
  }

}
