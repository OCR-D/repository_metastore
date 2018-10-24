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
package edu.kit.datamanager.metastore.service.impl;

import edu.kit.datamanager.metastore.entity.IResourceId;
import edu.kit.datamanager.metastore.entity.MetsProperties;
import edu.kit.datamanager.metastore.repository.MetsPropertiesRepository;
import edu.kit.datamanager.metastore.service.IMetsPropertiesService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Class implementing METS Properties services.
 * 
 */
@Service
public class MetsPropertiesService implements IMetsPropertiesService {
  @Autowired
	private MetsPropertiesRepository metsPropertiesRepository;

  @Override
  public List<String> getResourceIdsByTitle(String title) {
    Iterable<IResourceId> allResources = metsPropertiesRepository.findResourceIdByTitle(title);
    
    return createResourceIdList(allResources);
  }

  @Override
  public List<String> getResourceIdsByPpn(String ppn) {
    Iterable<IResourceId> allResources = metsPropertiesRepository.findResourceIdByPpn(ppn);
    
    return createResourceIdList(allResources);
  }
  
  private List<String> createResourceIdList(Iterable<IResourceId> allResources) {
    List<String> resourceList = new ArrayList<>();
    allResources.forEach((index) -> {
      resourceList.add(index.getResourceId());
    });
    return resourceList;
  }
}
