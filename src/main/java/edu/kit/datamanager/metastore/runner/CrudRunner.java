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
import edu.kit.datamanager.metastore.entity.ClassificationMetadata;
import edu.kit.datamanager.metastore.entity.GenreMetadata;
import edu.kit.datamanager.metastore.entity.GroundTruthProperties;
import edu.kit.datamanager.metastore.entity.IResourceId;
import edu.kit.datamanager.metastore.entity.IUrl;
import edu.kit.datamanager.metastore.entity.IVersion;
import edu.kit.datamanager.metastore.entity.LanguageMetadata;
import edu.kit.datamanager.metastore.entity.MdType;
import edu.kit.datamanager.metastore.entity.MetsDocument;
import edu.kit.datamanager.metastore.entity.MetsFile;
import edu.kit.datamanager.metastore.entity.MetsIdentifier;
import edu.kit.datamanager.metastore.entity.MetsProperties;
import edu.kit.datamanager.metastore.entity.PageMetadata;
import edu.kit.datamanager.metastore.entity.SectionDocument;
import edu.kit.datamanager.metastore.entity.XmlSchemaDefinition;
import edu.kit.datamanager.metastore.entity.ZippedBagit;
import edu.kit.datamanager.metastore.repository.ClassificationMetadataRepository;
import edu.kit.datamanager.metastore.repository.GenreMetadataRepository;
import edu.kit.datamanager.metastore.repository.LanguageMetadataRepository;
import edu.kit.datamanager.metastore.repository.MetsDocumentRepository;
import edu.kit.datamanager.metastore.repository.MetsFileRepository;
import edu.kit.datamanager.metastore.repository.MetsIdentifierRepository;
import edu.kit.datamanager.metastore.repository.MetsPropertiesRepository;
import edu.kit.datamanager.metastore.repository.PageMetadataRepository;
import edu.kit.datamanager.metastore.repository.SectionDocumentRepository;
import edu.kit.datamanager.metastore.repository.XmlSchemaDefinitionRepository;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.dao.DataAccessException;

/**
 * CRUD Runner for testing repositories.
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

    @Override
    public void run(final String... args) throws Exception {
        final String METS_DOCUMENT = "mets";
        final String SECTION_DOCUMENT = "sec";
        final String METS_FILES = "file";
        final String XSD = "xsd";
        final String METS_PROPERTIES = "prop";
        final String CLASSIFICATION = "class";
        final String GENRE = "genre";
        final String LANGUAGE = "lang";
        final String IDENTIFIER = "ident";
        final String PAGE = "page";
        final String DROP_DATABASE = "dropArangoDBOnly";
        List<String> argumentList = Arrays.asList(args);
        System.out.println("Run CRUD Runner!");
        // first drop the database so that we can run this multiple times with the same dataset
        try {
            operations.dropDatabase();
        } catch (DataAccessException dae) {
            System.out.println("This message should be printed only once!");
            System.out.println(dae.toString());
        }
        if (argumentList.contains(DROP_DATABASE)) {
            return;
        }

        System.out.println("# CRUD operations");

        // save a single entity in the database
        // there is no need of creating the collection first. This happen automatically
        System.out.println("********************************************************************************************************************");
        System.out.println("*******************************          Build  Database         ***************************************************");
        System.out.println("********************************************************************************************************************");
        if (argumentList.contains(METS_PROPERTIES)) {
            System.out.println("*******************************          MetsProperties         ***************************************************");
            for (MetsProperties metsProperty : createMetsProperties()) {
                metsPropertiesRepository.save(metsProperty);
                System.out.println("MetsProperty saved! " + metsProperty);
            }

        }
        if (argumentList.contains(METS_DOCUMENT)) {
            System.out.println("*******************************          MetsDocuments         ***************************************************");
            for (MetsDocument metsDocument : createMetsDocuments()) {
                repository.save(metsDocument);
                System.out.println("MetsDocument saved! " + metsDocument);
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
            for (MetsFile metsFile : createMetsFiles()) {
                metsFileRepository.save(metsFile);
                System.out.println("MetsFile saved! " + metsFile);
            }
        }
        if (argumentList.contains(XSD)) {
            System.out.println("*******************************          XSD         ***************************************************");
            for (XmlSchemaDefinition document : createSchemaDefinitions()) {
                xsdRepository.save(document);
                System.out.println(String.format("xsdDocument saved in the database with id: '%s' with prefix: '%s' namespace %s", document.getId(), document.getPrefix(), document.getNamespace()));
            }
        }
        if (argumentList.contains(CLASSIFICATION)) {
            System.out.println("*******************************          Classification  Metadata         ***************************************************");
            for (ClassificationMetadata classificationMetadata : createClassifications()) {
                classificationMetadataRepository.save(classificationMetadata);
                System.out.println("ClassificationMetadata saved! " + classificationMetadata);
            }
        }
        if (argumentList.contains(GENRE)) {
            System.out.println("*******************************          Genre  Metadata         ***************************************************");
            for (GenreMetadata genreMetadata : createGenre()) {
                genreMetadataRepository.save(genreMetadata);
                System.out.println("GenreMetadata saved! " + genreMetadata);
            }
        }
        if (argumentList.contains(LANGUAGE)) {
            System.out.println("*******************************          Language  Metadata         ***************************************************");
            for (LanguageMetadata languageMetadata : createLanguageMetadata()) {
                languageMetadataRepository.save(languageMetadata);
                System.out.println("LanguageMetadata saved! " + languageMetadata);
            }
        }
        if (argumentList.contains(IDENTIFIER)) {
            System.out.println("*******************************          Identifier  Metadata         ***************************************************");
            for (MetsIdentifier metsIdentifier : createMetsIdentifier()) {
                metsIdentifierRepository.save(metsIdentifier);
                System.out.println("MetsIdentifier saved! " + metsIdentifier);
            }
        }
        if (argumentList.contains(PAGE)) {
            System.out.println("*******************************          Page  Metadata         ***************************************************");
            for (PageMetadata pageMetadata : createPageMetadata()) {
                pageMetadataRepository.save(pageMetadata);
                System.out.println("PageMetadata saved! " + pageMetadata);
            }
        }
        System.out.println("********************************************************************************************************************");
        System.out.println("************************         START TESTS            ************************************************************");
        System.out.println("********************************************************************************************************************");

        if (argumentList.contains(METS_PROPERTIES)) {
            System.out.println("********************************************************************************************************************");
            System.out.println("*******************************          MetsProperties         ***************************************************");
            System.out.println("********************************************************************************************************************");
            System.out.println("No of entities for metsPorperties: " + metsPropertiesRepository.count());
            System.out.println("metsPropertiesRepository.findByResourceId(\"id_4\")");
            Iterable<MetsProperties> findByResourceId = metsPropertiesRepository.findByResourceId("id_4");
            for (Iterator<MetsProperties> it = findByResourceId.iterator(); it.hasNext();) {
                MetsProperties index = it.next();
                System.out.println(index.toString());
            }
            System.out.println("metsPropertiesRepository.findResourceIdByPpn(\"ppn2\")");
            Iterable<IResourceId> findResourceIdByPpn = metsPropertiesRepository.findResourceIdByPpn("ppn2");
            for (Iterator<IResourceId> it = findResourceIdByPpn.iterator(); it.hasNext();) {
                IResourceId index = it.next();
                System.out.println(index.getResourceId());
            }
            System.out.println("metsPropertiesRepository.findResourceIdByPpn(\"ppn1\")");
            findResourceIdByPpn = metsPropertiesRepository.findResourceIdByPpn("ppn1");
            for (Iterator<IResourceId> it = findResourceIdByPpn.iterator(); it.hasNext();) {
                IResourceId index = it.next();
                System.out.println(index.getResourceId());
            }
            System.out.println("metsPropertiesRepository.findResourceIdByTitle(\"Titel\")");
            Iterable<IResourceId> findResourceIdByTitle = metsPropertiesRepository.findResourceIdByTitle("Titel");
            for (Iterator<IResourceId> it = findResourceIdByTitle.iterator(); it.hasNext();) {
                IResourceId index = it.next();
                System.out.println(index.getResourceId());
            }
            System.out.println("metsPropertiesRepository.findResourceIdByTitle(\"Titel3\")");
            findResourceIdByTitle = metsPropertiesRepository.findResourceIdByTitle("Titel3");
            for (Iterator<IResourceId> it = findResourceIdByTitle.iterator(); it.hasNext();) {
                IResourceId index = it.next();
                System.out.println(index.getResourceId());
            }
        }
        if (argumentList.contains(SECTION_DOCUMENT)) {
            System.out.println("********************************************************************************************************************");
            System.out.println("************************       SectionDocument         ************************************************************");
            System.out.println("********************************************************************************************************************");
            System.out.println("No of entities for sectionDocument: " + secRepository.count());
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
            System.out.println("No of entities for metsDocument: " + repository.count());
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
            System.out.println("No of entities for metsFile: " + metsFileRepository.count());
            Iterable<MetsFile> metsFile;
            Iterator<MetsFile> metsFileIterator;
            Iterable<IUrl> urlOfMetsFile;
            Iterator<IUrl> urlIterator;
            System.out.println("********************************************************************************************************************");
            System.out.println("************************                             Find all METSFiles                        *********************");
            System.out.println("********************************************************************************************************************");
            metsFile = metsFileRepository.findAll();
            metsFileIterator = metsFile.iterator();
            while (metsFileIterator.hasNext()) {
                System.out.println(metsFileIterator.next());
            }
            System.out.println("********************************************************************************************************************");
            System.out.println("************************      Find one MetsFile by resourceId and fileID (highest version)     *********************");
            System.out.println("********************************************************************************************************************");
            MetsFile metsFile1 = metsFileRepository.findTop1DistinctByResourceIdAndFileIdOrderByVersionDesc("id_0002", "PAGE-0001_IMG_BIN");
            System.out.println(metsFile1);
            System.out.println("********************************************************************************************************************");
            System.out.println("************************      Find MetsFile by resourceId and USE      *********************************************");
            System.out.println("********************************************************************************************************************");
            System.out.println("metsFileRepository.findByResourceIdAndUse(\"id_0002\", \"OCR-D-GT-IMG-BIN\")");
            metsFile = metsFileRepository.findByResourceIdAndUseAndCurrentTrue("id_0002", "OCR-D-GT-IMG-BIN");
            metsFileIterator = metsFile.iterator();
            while (metsFileIterator.hasNext()) {
                System.out.println(metsFileIterator.next());
            }
            System.out.println("********************************************************************************************************************");
            System.out.println("************************      Find MetsFile by resourceId and USE IN      ******************************************");
            System.out.println("********************************************************************************************************************");
            System.out.println("metsFileRepository.findByResourceIdAndUseIn(\"id_0002\", Arrays.asList(\"OCR-D-GT-IMG-BIN\", \"OCR-D-GT-IMG-CROP\")");
            metsFile = metsFileRepository.findByResourceIdAndUseInAndCurrentTrue("id_0002", Arrays.asList("OCR-D-GT-IMG-BIN", "OCR-D-GT-IMG-CROP"));
            metsFileIterator = metsFile.iterator();
            while (metsFileIterator.hasNext()) {
                System.out.println(metsFileIterator.next());
            }
            System.out.println("********************************************************************************************************************");
            System.out.println("************************      Find MetsFile URL by resourceId and USE      *****************************************");
            System.out.println("********************************************************************************************************************");
            System.out.println("metsFileRepository.findUrlByResourceIdAndUse(\"id_0002\", \"OCR-D-GT-IMG-BIN\")");
            urlOfMetsFile = metsFileRepository.findUrlByResourceIdAndUseAndCurrentTrue("id_0002", "OCR-D-GT-IMG-BIN");
            urlIterator = urlOfMetsFile.iterator();
            while (urlIterator.hasNext()) {
                System.out.println(urlIterator.next().getUrl());
            }
            System.out.println("********************************************************************************************************************");
            System.out.println("************************      Find MetsFile URL by resourceId and USE IN      **************************************");
            System.out.println("********************************************************************************************************************");
            System.out.println("metsFileRepository.findUrlByResourceIdAndUseIn(\"id_0002\", Arrays.asList(\"OCR-D-GT-IMG-BIN\", \"OCR-D-GT-IMG-CROP\")");
            urlOfMetsFile = metsFileRepository.findUrlByResourceIdAndUseInAndCurrentTrue("id_0002", Arrays.asList("OCR-D-GT-IMG-BIN", "OCR-D-GT-IMG-CROP"));
            urlIterator = urlOfMetsFile.iterator();
            while (urlIterator.hasNext()) {
                System.out.println(urlIterator.next().getUrl());
            }
            System.out.println("********************************************************************************************************************");
            System.out.println("************************      Find MetsFile by resourceId and PAGEID     ******************************************");
            System.out.println("********************************************************************************************************************");
            System.out.println("metsFileRepository.findByResourceIdAndPageId(\"id_0002\", \"PAGE-0001\")");
            metsFile = metsFileRepository.findByResourceIdAndPageIdAndCurrentTrue("id_0002", "PAGE-0001");
            metsFileIterator = metsFile.iterator();
            while (metsFileIterator.hasNext()) {
                System.out.println(metsFileIterator.next());
            }
            System.out.println("********************************************************************************************************************");
            System.out.println("************************      Find MetsFile by resourceId and PAGEID IN      **************************************");
            System.out.println("********************************************************************************************************************");
            System.out.println("metsFileRepository.findByResourceIdAndPageIdIn(\"id_0002\", Arrays.asList(\"PAGE-0001\", \"PAGE-0002\")");
            metsFile = metsFileRepository.findByResourceIdAndPageIdInAndCurrentTrue("id_0002", Arrays.asList("PAGE-0001", "PAGE-0002"));
            metsFileIterator = metsFile.iterator();
            while (metsFileIterator.hasNext()) {
                System.out.println(metsFileIterator.next());
            }
            System.out.println("********************************************************************************************************************");
            System.out.println("************************      Find MetsFile URL by resourceId and PAGEID      *************************************");
            System.out.println("********************************************************************************************************************");
            System.out.println("metsFileRepository.findUrlByResourceIdAndPageId(\"id_0002\", \"PAGE-0001\")");
            urlOfMetsFile = metsFileRepository.findUrlByResourceIdAndPageIdAndCurrentTrue("id_0002", "PAGE-0001");
            urlIterator = urlOfMetsFile.iterator();
            while (urlIterator.hasNext()) {
                System.out.println(urlIterator.next().getUrl());
            }
            System.out.println("********************************************************************************************************************");
            System.out.println("************************      Find MetsFile URL by resourceId and PAGEID IN      **********************************");
            System.out.println("********************************************************************************************************************");
            System.out.println("metsFileRepository.findUrlByResourceIdAndPageIdIn(\"id_0002\", Arrays.asList(\"PAGE-0001\", \"PAGE-0002\")");
            urlOfMetsFile = metsFileRepository.findUrlByResourceIdAndPageIdInAndCurrentTrue("id_0002", Arrays.asList("PAGE-0001", "PAGE-0002"));
            urlIterator = urlOfMetsFile.iterator();
            while (urlIterator.hasNext()) {
                System.out.println(urlIterator.next().getUrl());
            }
            System.out.println("********************************************************************************************************************");
            System.out.println("************************      Find MetsFile by resourceId and USE AND PAGEID     **********************************");
            System.out.println("********************************************************************************************************************");
            System.out.println("metsFileRepository.findByResourceIdAndUseAndPageId(\"id_0002\", \"OCR-D-GT-IMG-BIN\", \"PAGE-0001\")");
            metsFile = metsFileRepository.findByResourceIdAndUseAndPageIdAndCurrentTrue("id_0002", "OCR-D-GT-IMG-BIN", "PAGE-0001");
            metsFileIterator = metsFile.iterator();
            while (metsFileIterator.hasNext()) {
                System.out.println(metsFileIterator.next());
            }
            System.out.println("********************************************************************************************************************");
            System.out.println("************************      Find MetsFile by resourceId and USE AND PAGEID IN      ******************************");
            System.out.println("********************************************************************************************************************");
            System.out.println("metsFileRepository.findByResourceIdAndUseInAndPageIdIn(\"id_0002\", Arrays.asList(\"OCR-D-GT-IMG-BIN\", \"OCR-D-GT-IMG-DESPEC\"), Arrays.asList(\"PAGE-0001\", \"PAGE-0002\")");
            metsFile = metsFileRepository.findByResourceIdAndUseInAndPageIdInAndCurrentTrue("id_0002", Arrays.asList("OCR-D-GT-IMG-BIN", "OCR-D-GT-IMG-DESPEC"), Arrays.asList("PAGE-0001", "PAGE-0002"));
            metsFileIterator = metsFile.iterator();
            while (metsFileIterator.hasNext()) {
                System.out.println(metsFileIterator.next());
            }
            System.out.println("********************************************************************************************************************");
            System.out.println("************************      Find MetsFile URL by resourceId USE AND and PAGEID      *****************************");
            System.out.println("********************************************************************************************************************");
            System.out.println("metsFileRepository.findUrlByResourceIdAndUseAndPageId(\"id_0002\", \"OCR-D-GT-IMG-BIN\", \"PAGE-0001\")");
            urlOfMetsFile = metsFileRepository.findUrlByResourceIdAndUseAndPageIdAndCurrentTrue("id_0002", "OCR-D-GT-IMG-BIN", "PAGE-0001");
            urlIterator = urlOfMetsFile.iterator();
            while (urlIterator.hasNext()) {
                System.out.println(urlIterator.next().getUrl());
            }
            System.out.println("********************************************************************************************************************");
            System.out.println("************************      Find MetsFile URL by resourceId USE AND and PAGEID IN      **************************");
            System.out.println("********************************************************************************************************************");
            System.out.println("metsFileRepository.findUrlByResourceIdAndUseInAndPageIdIn(\"id_0002\", Arrays.asList(\"OCR-D-GT-IMG-BIN\", \"OCR-D-GT-IMG-DESPEC\"), Arrays.asList(\"PAGE-0001\", \"PAGE-0002\")");
            urlOfMetsFile = metsFileRepository.findUrlByResourceIdAndUseInAndPageIdInAndCurrentTrue("id_0002", Arrays.asList("OCR-D-GT-IMG-BIN", "OCR-D-GT-IMG-DESPEC"), Arrays.asList("PAGE-0001", "PAGE-0002"));
            urlIterator = urlOfMetsFile.iterator();
            while (urlIterator.hasNext()) {
                System.out.println(urlIterator.next().getUrl());
            }
        }
        if (argumentList.contains(CLASSIFICATION)) {
            System.out.println("********************************************************************************************************************");
            System.out.println("*******************************          Classification  Metadata         ***************************************************");
            System.out.println("********************************************************************************************************************");
            System.out.println("No of entities for classification Metadata: " + classificationMetadataRepository.count());
            System.out.println("classificationMetadataRepository.findByClassification(\"Geburtstag\")");
            Iterable<ClassificationMetadata> classificationList = classificationMetadataRepository.findByClassification("Geburtstag");
            Iterator<ClassificationMetadata> iteratorClassification = classificationList.iterator();
            while (iteratorClassification.hasNext()) {
                System.out.println(iteratorClassification.next().toString());
            }
            System.out.println("classificationMetadataRepository.findByResourceId(\"id_0015\")");
            classificationList = classificationMetadataRepository.findByResourceId("id_0015");
            iteratorClassification = classificationList.iterator();
            while (iteratorClassification.hasNext()) {
                System.out.println(iteratorClassification.next().toString());
            }
        }
        if (argumentList.contains(GENRE)) {
            System.out.println("********************************************************************************************************************");
            System.out.println("*******************************          Genre  Metadata         ***************************************************");
            System.out.println("********************************************************************************************************************");
            System.out.println("No of entities for genre Metadata: " + genreMetadataRepository.count());
            System.out.println("genreMetadataRepository.findByGenre(\"Horror\")");
            Iterable<GenreMetadata> genreList = genreMetadataRepository.findByGenre("Horror");
            Iterator<GenreMetadata> iteratorGenre = genreList.iterator();
            while (iteratorGenre.hasNext()) {
                System.out.println(iteratorGenre.next().toString());
            }
            System.out.println("genreMetadataRepository.findByResourceId(\"id_0018\")");
            genreList = genreMetadataRepository.findByResourceId("id_0018");
            iteratorGenre = genreList.iterator();
            while (iteratorGenre.hasNext()) {
                System.out.println(iteratorGenre.next().toString());
            }
        }
        if (argumentList.contains(LANGUAGE)) {
            System.out.println("********************************************************************************************************************");
            System.out.println("*******************************          Language  Metadata         ***************************************************");
            System.out.println("********************************************************************************************************************");
            System.out.println("No of entities for language Metadata: " + languageMetadataRepository.count());
            System.out.println("languageMetadataRepository.findByLanguage(\"en\")");
            Iterable<LanguageMetadata> languageList = languageMetadataRepository.findByLanguage("en");
            Iterator<LanguageMetadata> iteratorLanguage = languageList.iterator();
            while (iteratorLanguage.hasNext()) {
                System.out.println(iteratorLanguage.next().toString());
            }
            System.out.println("languageMetadataRepository.findByResourceId(\"id_0016\")");
            languageList = languageMetadataRepository.findByResourceId("id_0016");
            iteratorLanguage = languageList.iterator();
            while (iteratorLanguage.hasNext()) {
                System.out.println(iteratorLanguage.next().toString());
            }
        }
        if (argumentList.contains(IDENTIFIER)) {
            System.out.println("********************************************************************************************************************");
            System.out.println("*******************************          Identifier  Metadata         ***************************************************");
            System.out.println("********************************************************************************************************************");
            System.out.println("No of entities for identifier Metadata: " + metsIdentifierRepository.count());
            System.out.println("metsIdentifierRepository.findByLanguage(\"url1\")");
            Iterable<MetsIdentifier> metsIdentifierList = metsIdentifierRepository.findByIdentifier("url1");
            Iterator<MetsIdentifier> metsIdentifierIterator = metsIdentifierList.iterator();
            while (metsIdentifierIterator.hasNext()) {
                System.out.println(metsIdentifierIterator.next().toString());
            }
            System.out.println("metsIdentifierRepository.findByResourceId(\"id_0002\")");
            metsIdentifierList = metsIdentifierRepository.findByResourceId("id_0002");
            metsIdentifierIterator = metsIdentifierList.iterator();
            while (metsIdentifierIterator.hasNext()) {
                System.out.println(metsIdentifierIterator.next().toString());
            }
            System.out.println("metsIdentifierRepository.findByResourceIdAndType(\"id_0017\", \"handle\")");
            metsIdentifierList = metsIdentifierRepository.findByResourceIdAndType("id_0017", "handle");
            metsIdentifierIterator = metsIdentifierList.iterator();
            while (metsIdentifierIterator.hasNext()) {
                System.out.println(metsIdentifierIterator.next().toString());
            }
            System.out.println("metsIdentifierRepository.findByIdentifierAndType(\"id_0017\", \"handle\")");
            metsIdentifierList = metsIdentifierRepository.findByIdentifierAndType("url3", "url");
            metsIdentifierIterator = metsIdentifierList.iterator();
            while (metsIdentifierIterator.hasNext()) {
                System.out.println(metsIdentifierIterator.next().toString());
            }
        }
        if (argumentList.contains(PAGE)) {
            System.out.println("********************************************************************************************************************");
            System.out.println("*******************************          Page  Metadata         ***************************************************");
            System.out.println("********************************************************************************************************************");
            System.out.println("No of entities for page Metadata: " + pageMetadataRepository.count());
            System.out.println("pageMetadataRepository.findByFeature(\"" + GroundTruthProperties.ADMINS.toString() + "\")");
            Iterable<PageMetadata> pageList = pageMetadataRepository.findByFeature(GroundTruthProperties.ADMINS);
            Iterator<PageMetadata> iteratorPage = pageList.iterator();
            while (iteratorPage.hasNext()) {
                System.out.println(iteratorPage.next().toString());
            }
            System.out.println("pageMetadataRepository.findByResourceId(\"id_0002\")");
            pageList = pageMetadataRepository.findByResourceId("id_0002");
            iteratorPage = pageList.iterator();
            while (iteratorPage.hasNext()) {
                System.out.println(iteratorPage.next().toString());
            }
            System.out.println("pageMetadataRepository.findByResourceId(\"id_0002\")");
            pageList = pageMetadataRepository.findByResourceIdAndPageId("id_0002", "phys_0001");
            iteratorPage = pageList.iterator();
            while (iteratorPage.hasNext()) {
                System.out.println(iteratorPage.next().toString());
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

    public static void setupMetsProperties() {
    }

    public static Collection<MetsProperties> createMetsProperties() {
        MetsProperties metsProperties1 = new MetsProperties();
        metsProperties1.setTitle("Titel");
        metsProperties1.setPpn("ppn1");
        metsProperties1.setResourceId("id_1");
        MetsProperties metsProperties2 = new MetsProperties();
        metsProperties2.setTitle("Titel2");
        metsProperties2.setPpn("ppn2");
        metsProperties2.setResourceId("id_2");
        MetsProperties metsProperties3 = new MetsProperties();
        metsProperties3.setTitle("Titel");
        metsProperties3.setPpn("ppn3");
        metsProperties3.setResourceId("id_3");
        MetsProperties metsProperties4 = new MetsProperties();
        metsProperties4.setTitle("Titel3");
        metsProperties4.setPpn("ppn1");
        metsProperties4.setResourceId("id_4");
        return Arrays.asList(metsProperties1,
                metsProperties2,
                metsProperties3,
                metsProperties4);

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
                new MetsDocument("id_0001", "someXML"),
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
                new MetsFile("id_0002", 1, "PAGE-0001_IMG_BIN", "image/png", "PAGE-0001", "OCR-D-GT-IMG-BIN", "url1"),
                new MetsFile("id_0002", 1, "PAGE-0001_IMG-CROP", "image/png", "PAGE-0001", "OCR-D-GT-IMG-CROP", "url2"),
                new MetsFile("id_0002", 1, "PAGE-0001_IMG-DESPEC", "image/png", "PAGE-0001", "OCR-D-GT-IMG-DESPEC", "url3"),
                new MetsFile("id_0002", 1, "PAGE-0001_IMG-DEWARP", "image/png", "PAGE-0001", "OCR-D-GT-IMG-DEWARP", "url4"),
                new MetsFile("id_0002", 2, "PAGE-0001_IMG_BIN", "image/png", "PAGE-0001", "OCR-D-GT-IMG-BIN", "url1_v2"),
                new MetsFile("id_0002", 2, "PAGE-0001_IMG-CROP", "image/png", "PAGE-0001", "OCR-D-GT-IMG-CROP", "url2_v2"),
                new MetsFile("id_0002", 2, "PAGE-0001_IMG-DESPEC", "image/png", "PAGE-0001", "OCR-D-GT-IMG-DESPEC", "url3_v2"),
                new MetsFile("id_0002", 2, "PAGE-0001_IMG-DEWARP", "image/png", "PAGE-0001", "OCR-D-GT-IMG-DEWARP", "url4_v2"),
                new MetsFile("id_0015", 1, "PAGE-0001_IMG_BIN", "image/png", "PAGE-0001", "OCR-D-GT-IMG-BIN", "url11"),
                new MetsFile("id_0015", 1, "PAGE-0001_IMG-CROP", "image/png", "PAGE-0001", "OCR-D-GT-IMG-CROP", "url21"),
                new MetsFile("id_0015", 1, "PAGE-0001_IMG-DESPEC", "image/png", "PAGE-0001", "OCR-D-GT-IMG-DESPEC", "url31"),
                new MetsFile("id_0015", 1, "PAGE-0001_IMG-DEWARP", "image/png", "PAGE-0001", "OCR-D-GT-IMG-DEWARP", "url41"),
                new MetsFile("id_0016", 1, "PAGE-0001_IMG_BIN", "image/png", "PAGE-0001", "OCR-D-GT-IMG-BIN", "url16"),
                new MetsFile("id_0017", 1, "PAGE-0001_IMG-CROP", "image/png", "PAGE-0001", "OCR-D-GT-IMG-CROP", "url17"),
                new MetsFile("id_0018", 1, "PAGE-0001_IMG-DESPEC", "image/png", "PAGE-0001", "OCR-D-GT-IMG-DESPEC", "url18"),
                new MetsFile("id_0019", 1, "PAGE-0001_IMG-DEWARP", "image/png", "PAGE-0001", "OCR-D-GT-IMG-DEWARP", "url19"),
                new MetsFile("id_0002", 1, "PAGE-0002_IMG_BIN", "image/png", "PAGE-0002", "OCR-D-GT-IMG-BIN", "url2_1"),
                new MetsFile("id_0002", 1, "PAGE-0002_IMG-CROP", "image/png", "PAGE-0002", "OCR-D-GT-IMG-CROP", "url2_2"),
                new MetsFile("id_0002", 1, "PAGE-0002_IMG-DESPEC", "image/png", "PAGE-0002", "OCR-D-GT-IMG-DESPEC", "url2_3"),
                new MetsFile("id_0002", 1, "PAGE-0002_IMG-DEWARP", "image/png", "PAGE-0002", "OCR-D-GT-IMG-DEWARP", "url2_4"),
                new MetsFile("id_0015", 1, "PAGE-0002_IMG_BIN", "image/png", "PAGE-0002", "OCR-D-GT-IMG-BIN", "url2_11"),
                new MetsFile("id_0015", 1, "PAGE-0002_IMG-CROP", "image/png", "PAGE-0002", "OCR-D-GT-IMG-CROP", "url2_21"),
                new MetsFile("id_0015", 1, "PAGE-0002_IMG-DESPEC", "image/png", "PAGE-0002", "OCR-D-GT-IMG-DESPEC", "url2_31"),
                new MetsFile("id_0015", 1, "PAGE-0002_IMG-DEWARP", "image/png", "PAGE-0002", "OCR-D-GT-IMG-DEWARP", "url2_41"),
                new MetsFile("id_0016", 1, "PAGE-0002_IMG_BIN", "image/png", "PAGE-0002", "OCR-D-GT-IMG-BIN", "url2_16"),
                new MetsFile("id_0017", 1, "PAGE-0002_IMG-CROP", "image/png", "PAGE-0002", "OCR-D-GT-IMG-CROP", "url2_17"),
                new MetsFile("id_0018", 1, "PAGE-0002_IMG-DESPEC", "image/png", "PAGE-0002", "OCR-D-GT-IMG-DESPEC", "url2_18"),
                new MetsFile("id_0019", 1, "PAGE-0002_IMG-DEWARP", "image/png", "PAGE-0002", "OCR-D-GT-IMG-DEWARP", "url2_19"));
    }

    public static Collection<ClassificationMetadata> createClassifications() {
        return Arrays.asList(
                new ClassificationMetadata("id_0002", "Geburtstag"),
                new ClassificationMetadata("id_0002", "Jahrestag"),
                new ClassificationMetadata("id_0015", "Tagebuch"),
                new ClassificationMetadata("id_0015", "Geburtstag"),
                new ClassificationMetadata("id_0015", "Krimi"),
                new ClassificationMetadata("id_0015", "url41"),
                new ClassificationMetadata("id_0016", "Novelle"),
                new ClassificationMetadata("id_0017", "Gedicht"),
                new ClassificationMetadata("id_0018", "Geburtstag"),
                new ClassificationMetadata("id_0019", "Gedicht"));
    }

    public static Collection<GenreMetadata> createGenre() {
        return Arrays.asList(
                new GenreMetadata("id_0002", "Drama"),
                new GenreMetadata("id_0002", "Musical"),
                new GenreMetadata("id_0015", "Western"),
                new GenreMetadata("id_0015", "Drama"),
                new GenreMetadata("id_0015", "Horror"),
                new GenreMetadata("id_0015", "Action"),
                new GenreMetadata("id_0016", "Thriller"),
                new GenreMetadata("id_0017", "Krimi"),
                new GenreMetadata("id_0018", "Drama"),
                new GenreMetadata("id_0019", "Krimi"));
    }

    public static Collection<LanguageMetadata> createLanguageMetadata() {
        return Arrays.asList(
                new LanguageMetadata("id_0002", "en"),
                new LanguageMetadata("id_0002", "deu"),
                new LanguageMetadata("id_0015", "deu"),
                new LanguageMetadata("id_0016", "deu"),
                new LanguageMetadata("id_0017", "deu"),
                new LanguageMetadata("id_0017", "en"),
                new LanguageMetadata("id_0018", "en"),
                new LanguageMetadata("id_0019", "en"));
    }

    public static Collection<MetsIdentifier> createMetsIdentifier() {
        return Arrays.asList(
                new MetsIdentifier("id_0002", "purl", "purl1"),
                new MetsIdentifier("id_0002", "url", "url1"),
                new MetsIdentifier("id_0015", "urn", "urn1"),
                new MetsIdentifier("id_0016", "handle", "handle1"),
                new MetsIdentifier("id_0017", "url", "url2"),
                new MetsIdentifier("id_0017", "handle", "handle2"),
                new MetsIdentifier("id_0018", "urn", "urn2"),
                new MetsIdentifier("id_0019", "url", "url3"));
    }

    public static Collection<PageMetadata> createPageMetadata() {
        return Arrays.asList(
                new PageMetadata("id_0002", "1", "phys_0001", GroundTruthProperties.ACQUISITION),
                new PageMetadata("id_0002", "1", "phys_0001", GroundTruthProperties.ADMINS),
                new PageMetadata("id_0002", "2", "phys_0002", GroundTruthProperties.ADMINS),
                new PageMetadata("id_0015", "1", "phys_0001", GroundTruthProperties.ANDROID),
                new PageMetadata("id_0016", "1", "phys_0001", GroundTruthProperties.TOC),
                new PageMetadata("id_0017", "1", "phys_0001", GroundTruthProperties.FAX),
                new PageMetadata("id_0017", "2", "phys_0002", GroundTruthProperties.DIA),
                new PageMetadata("id_0018", "1", "phys_0001", GroundTruthProperties.ADMINS),
                new PageMetadata("id_0019", "1", "phys_0001", GroundTruthProperties.LATIN));
    }

    public static Collection<ZippedBagit> createZippedBagits() {
        Collection<ZippedBagit> collection = null;
        try {
            ZippedBagit bag1 = new ZippedBagit("resource_0001", "id_0002", "url1");
            Thread.sleep(20);
            ZippedBagit bag2 = bag1.updateZippedBagit("resource_0002", "url2");
            Thread.sleep(20);
            ZippedBagit bag3 = bag2.updateZippedBagit("resource_0003", "url3");
            Thread.sleep(20);
            ZippedBagit bag4 = new ZippedBagit("resource_0004", "id_0015", "url4");
            Thread.sleep(20);
            ZippedBagit bag5 = new ZippedBagit("resource_0005", "id_0016", "url5");
            Thread.sleep(20);
            ZippedBagit bag6 = new ZippedBagit("resource_0006", "id_0017", "url6");
            Thread.sleep(20);
            ZippedBagit bag7 = bag6.updateZippedBagit("resource_0007", "url7");
            Thread.sleep(20);
            ZippedBagit bag8 = new ZippedBagit("resource_0008", "id_0018", "url8");
            Thread.sleep(20);
            ZippedBagit bag9 = new ZippedBagit("resource_0009", "id_0019", "url9");
            Thread.sleep(20);
            ZippedBagit bag10 = bag3.updateZippedBagit("resource_0010", "url10");
            bag10.setVersion(15);

            collection = Arrays.asList(bag1, bag2, bag3, bag4, bag5, bag6, bag7, bag8, bag9, bag10);
        } catch (InterruptedException ex) {
            Logger.getLogger(CrudRunner.class.getName()).log(Level.SEVERE, null, ex);
        }
        return collection;
    }

}
