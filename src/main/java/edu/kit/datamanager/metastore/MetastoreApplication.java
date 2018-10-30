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
package edu.kit.datamanager.metastore;

import edu.kit.datamanager.metastore.runner.CrudRunner;
import edu.kit.datamanager.metastore.storageservice.StorageProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * Entry point for Spring Boot application
 */
@SpringBootApplication
@EnableAutoConfiguration
@EnableConfigurationProperties(StorageProperties.class)
@ComponentScan(basePackages = {"edu.kit.datamanager.metastore"})
@EntityScan(basePackages = {"edu.kit.datamanager.metastore"})
public class MetastoreApplication implements ApplicationRunner {

  /**
   * Logger for this class.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(MetastoreApplication.class);

  /**
   * Main method for Spring Boot Application.
   *
   * @param args Command line arguments.
   */
  public static void main(String[] args) {
    LOGGER.debug("Number of arguments: {}", args.length);
    // If no arguments are specified start the server...
    if (args.length == 0) {
      LOGGER.info("Start Spring Boot...");
      ApplicationContext ctx = SpringApplication.run(MetastoreApplication.class, args);
    } else {
      LOGGER.info("CRUD_Runner");
      final Class<?>[] runner = new Class<?>[]{CrudRunner.class/*, ByExampleRunner.class, DerivedQueryRunner.class ,
				RelationsRunner.class, AQLRunner.class, GeospatialRunner.class */
      };
      System.exit(SpringApplication.exit(SpringApplication.run(runner, args)));
    }
  }

  @Override
  public void run(ApplicationArguments args) throws Exception {
    LOGGER.info("Running....");
  }
}
