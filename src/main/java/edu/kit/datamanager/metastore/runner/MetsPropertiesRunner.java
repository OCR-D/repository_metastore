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

import com.arangodb.springframework.core.ArangoOperations;
import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;

import edu.kit.datamanager.metastore.entity.IResourceId;
import edu.kit.datamanager.metastore.entity.IUrl;
import edu.kit.datamanager.metastore.entity.IVersion;
import edu.kit.datamanager.metastore.entity.MdType;
import edu.kit.datamanager.metastore.entity.MetsDocument;
import edu.kit.datamanager.metastore.entity.MetsFile;
import edu.kit.datamanager.metastore.entity.MetsProperties;
import edu.kit.datamanager.metastore.entity.SectionDocument;
import edu.kit.datamanager.metastore.entity.XmlSchemaDefinition;
import edu.kit.datamanager.metastore.repository.MetsPropertiesRepository;
import java.util.Iterator;
import java.util.List;
import org.springframework.dao.DataAccessException;

/**
 * @author Volker Hartmann
 *
 */
@ComponentScan("edu.kit.datamanager.metastore")
public class MetsPropertiesRunner implements CommandLineRunner {

  @Autowired
  private ArangoOperations operations;

  @Autowired
  private MetsPropertiesRepository metsPropertiesRepository;

  @Override
  public void run(final String... args) throws Exception {
    final String METS_DOCUMENT = "mets";
    final String SECTION_DOCUMENT = "sec";
    final String METS_FILES = "file";
    final String XSD = "xsd";
    final String METS_PROPERTIES = "prop";
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
    if (argumentList.contains(METS_PROPERTIES)) {
      System.out.println("*******************************          MetsProperties         ***************************************************");
      MetsProperties metsProperties = new MetsProperties();
      metsProperties.setTitle("Titel");
      metsProperties.setPpn("ppn1");
      metsProperties.setResourceId("id_1");
      metsPropertiesRepository.save(metsProperties);
      metsProperties = new MetsProperties();
      metsProperties.setTitle("Titel2");
      metsProperties.setPpn("ppn2");
      metsProperties.setResourceId("id_2");
      metsPropertiesRepository.save(metsProperties);
      metsProperties = new MetsProperties();
      metsProperties.setTitle("Titel");
      metsProperties.setPpn("ppn3");
      metsProperties.setResourceId("id_3");
      metsPropertiesRepository.save(metsProperties);
      metsProperties = new MetsProperties();
      metsProperties.setTitle("Titel3");
      metsProperties.setPpn("ppn1");
      metsProperties.setResourceId("id_4");
      metsPropertiesRepository.save(metsProperties);
    }
    System.out.println("********************************************************************************************************************");
    System.out.println("************************         START TESTS            ************************************************************");
    System.out.println("********************************************************************************************************************");

  }

}
