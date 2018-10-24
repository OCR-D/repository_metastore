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

import edu.kit.datamanager.metastore.service.IRepoService;
import edu.kit.datamanager.metastore.entity.IVersion;
import edu.kit.datamanager.metastore.entity.MetsDocument;
import edu.kit.datamanager.metastore.repository.MetsDocumentRepository;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.collections4.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Class implementing METS services.
 * 
 */
@Service
public class RepoService implements IRepoService {
  @Autowired
	private MetsDocumentRepository metsRepository;

  @Override
  public String createDigitalObject(File bagItFile) {
    Iterator<MetsDocument> metsIterator = metsRepository.findByCurrentTrue().iterator();
    List<MetsDocument> metsList = IteratorUtils.toList(metsIterator);
    return "http://anyURL";
  }

  @Override
  public MetsDocument getBagItByResourceId(String resourceId) {
     MetsDocument metsDocument = metsRepository.findByResourceIdAndCurrentTrue(resourceId);
    return metsDocument;
  }

  @Override
  public List<Integer> getBagItByPpn(String ppn) {
    List<Integer> versionList = new ArrayList<>();
    Iterable<IVersion> versionIterator = metsRepository.findVersionByResourceIdOrderByVersionDesc(ppn);
    for (IVersion indexElement : versionIterator) {
      versionList.add(indexElement.getVersion());
    }
    return versionList;
  }
}