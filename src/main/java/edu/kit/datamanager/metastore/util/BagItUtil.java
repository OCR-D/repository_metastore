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
package edu.kit.datamanager.metastore.util;

import gov.loc.repository.bagit.Bag;
import gov.loc.repository.bagit.BagFactory;
import gov.loc.repository.bagit.BagInfoTxt;
import gov.loc.repository.bagit.BagItTxt;
import gov.loc.repository.bagit.PreBag;
import gov.loc.repository.bagit.writer.impl.FileSystemWriter;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility handling BagIt containers.
 */
public class BagItUtil {

  /**
   * Logger.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(BagItUtil.class);

  /**
   * Build BagIt container of a payload directory.
   *
   * @param payLoadPath Path to payload directory.
   * @throws IOException Error accessing files
   */
  public static Bag buildBag(File payLoadPath) throws IOException {
    BagFactory bf = new BagFactory();

    PreBag pb = bf.createPreBag(payLoadPath);
    pb.makeBagInPlace(BagFactory.Version.V0_97, false);

    Bag bag = bf.createBag(payLoadPath);

    //adding some optional metadata:
    Date d = new Date();
    bag.getBagInfoTxt().putList("createDate", d.toString());
    bag.getBagInfoTxt().putList("modified", d.toString());
    bag.getBagInfoTxt().putList("numberOfModifications", Integer.toString(1));

    bag.makeComplete();
    FileSystemWriter fsw = new FileSystemWriter(bf);
    return bag;
  }

  /**
   * Creating BagIt container of a bagIt directory.
   *
   * @param pathToBag Path to bagIt directory.
   *
   * @return Bag of directory.
   * @throws IOException Error accessing files
   */
  public static Bag createBag(File pathToBag) throws IOException {
    LOGGER.error("Create BagIt error");
    LOGGER.warn("Create BagIt warn");
    LOGGER.info("Create BagIt info");
    LOGGER.debug("Create BagIt debug");
    System.out.println("Create BagIt sout");
    BagFactory bf = new BagFactory();

    Bag bag = bf.createBag(pathToBag);
    validateBagit(bag);

    return bag;
  }

  /**
   * Validate BagIt container.
   *
   * @param bag Bag to validate.
   *
   * @return true or false
   */
  public static boolean validateBagit(Bag bag) {
    boolean valid = false;
    System.out.println("Validate Bag!");
    LOGGER.debug("Validate Bag!");
    bag.verifyComplete();
    Bag.BagConstants bc = bag.getBagConstants();
    BagItTxt bt = bag.getBagItTxt();
    BagInfoTxt bi = bag.getBagInfoTxt();
      LOGGER.info("VERSION: {}", bc.getVersion());
      LOGGER.info("Txt: {}", bt);
      LOGGER.info("BagInfoText: {}", bi);
      LOGGER.info("Source Organization: {}", bi.getSourceOrganization());
      List<String> nonstandardFields1 = bi.getNonstandardFields();
      LOGGER.info("Non standard fields:");
      for (String item : nonstandardFields1) {
        LOGGER.info("- {}", item);
        LOGGER.info("Value: {}", bi.get(item));
      }
    String xOcrdMets = bi.getOrDefault("X-Ocrd-Mets", "data/mets.xml");
      
      for (Entry<String, String> entry : bi.entrySet()) {
        LOGGER.info("{} : {} ", entry.getKey(), entry.getValue());
      }
      
      

    if (LOGGER.isTraceEnabled()) {
      LOGGER.trace("VERSION: {}", bc.getVersion());
      LOGGER.trace("Txt: {}", bt);
      LOGGER.trace("BagInfoText: {}", bi);
      LOGGER.trace("Source Organization: {}", bi.getSourceOrganization());
      List<String> nonstandardFields = bi.getNonstandardFields();
      System.out.println("Non standard fields:");
      for (String item : nonstandardFields) {
        System.out.println("- " + item);
      }
    }
    return valid;
  }
}
