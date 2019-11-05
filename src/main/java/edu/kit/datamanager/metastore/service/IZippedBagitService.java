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
package edu.kit.datamanager.metastore.service;

import edu.kit.ocrd.workspace.entity.ZippedBagit;
import java.util.List;

/**
 * Interface defining METS service.
 */
public interface IZippedBagitService {

  /**
   * Store bag in repository.
   * 
   * @param zippedBagit  bag.
   * @return Stored bag.
   */
  ZippedBagit save(ZippedBagit zippedBagit);
  /**
   * Get all current MetsDocuments/DigitalObjects.
   * 
   * @return List holding all METS Documents. 
   */
  List<ZippedBagit> getAllLatestZippedBagits();
  
  /**
   * Get bag container with given resource identifier.
   * 
   * @param resourceIdentifier OCRD identifier of bag.
   * @return ZippedBagit.
   */
  ZippedBagit getZippedBagitByResourceId(String resourceIdentifier);
  
  /**
   * Get all versions of bagit containers with given OCRD identifier.
   * 
   * @param ocrdIdentifier OCRD identifier of bag.
   * @return ZippedBagit.
   */
   List<ZippedBagit> getZippedBagitsByOcrdIdentifierOrderByVersionDesc(String ocrdIdentifier);
  
  /**
   * Get most recent bag of METS document with given OCRD identifier.
   * 
   * @param ocrdIdentifier OCRD identifier of bag.
   * @return ZippedBagit.
   */
  ZippedBagit getMostRecentZippedBagitByOcrdIdentifier(String ocrdIdentifier);

  /**
   * Get all versions with given OCRD identifier.
   * 
   * @param ocrdIdentifier OCRD identifier of bag.
   * @return List holding all versions.
   */
  List<Integer> getAllVersionsByOcrdIdentifier(String ocrdIdentifier);

  /**
   * Get zipped bagit container with given OCRD identifier and version.
   * 
   * @param ocrdIdentifier OCRD identifier of bag.
   * @param version Version of bagit container.
   * @return List holding all versions of METS document.
   */
  ZippedBagit getZippedBagitByOcrdIdentifierAndVersion(String ocrdIdentifier, Integer version);
  
}
