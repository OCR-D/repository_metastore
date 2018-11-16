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
package edu.kit.datamanager.metastore.repository;

import com.arangodb.springframework.repository.ArangoRepository;
import edu.kit.datamanager.metastore.entity.IUrl;
import edu.kit.datamanager.metastore.entity.MetsFile;
import java.util.Collection;

/**
 * Repository holding all MetsFiles.
 */
public interface MetsFileRepository extends ArangoRepository<MetsFile, String> {

  /**
   * Find file of a METS document with given ID.
   *
   * @param resourceId ID of the METS document.
   * @param fileId ID of the file.
   *
   * @return Instance holding selected file.
   */
  MetsFile findTop1DistinctByResourceIdAndFileIdOrderByVersionDesc(String resourceId, String fileId);
  /**
   * Find files of a METS document with given IDs.
   *
   * @param resourceId ID of the METS document.
   * @param fileId IDs of the files.
   *
   * @return Instance holding selected file.
   */
  Iterable<MetsFile> findByResourceIdAndFileIdInAndCurrentTrue(String resourceId, String[] fileId);

  /**
   * Find all files of a METS document.
   *
   * @param resourceId ID of the METS document.
   *
   * @return Iterator holding all files.
   */
  Iterable<MetsFile> findByResourceIdAndCurrentTrue(String resourceId);

  /**
   * Find all files of a METS document within the same fileGrp.
   *
   * @param resourceId ID of the METS document.
   * @param use USE of the fileGrp.
   *
   * @return Iterator holding all files.
   */
  Iterable<MetsFile> findByResourceIdAndUseAndCurrentTrue(String resourceId, String use);

  /**
   * Find all files of a METS document within the given fileGrps.
   *
   * @param resourceId ID of the METS document.
   * @param use All possible USE of the fileGrp.
   *
   * @return Iterator holding all files.
   */
  Iterable<MetsFile> findByResourceIdAndUseInAndCurrentTrue(String resourceId, Collection<String> use);

  /**
   * Find all file URLs of a METS document within the same fileGrp.
   *
   * @param resourceId ID of the METS document.
   * @param use USE of the fileGrp.
   *
   * @return Iterator holding all file URLs.
   */
  Iterable<IUrl> findUrlByResourceIdAndUseAndCurrentTrue(String resourceId, String use);

  /**
   * Find all file URLs of a METS document within the given fileGrps.
   *
   * @param resourceId ID of the METS document.
   * @param use All possible USE of the fileGrp.
   *
   * @return Iterator holding all file URLs.
   */
  Iterable<IUrl> findUrlByResourceIdAndUseInAndCurrentTrue(String resourceId, Collection<String> use);

  /**
   * Find all files of a METS document with the same GROUPID.
   *
   * @param resourceId ID of the METS document.
   * @param groupId GROUPID of the files.
   *
   * @return Iterator holding all files.
   */
  Iterable<MetsFile> findByResourceIdAndGroupIdAndCurrentTrue(String resourceId, String groupId);

  /**
   * Find all files of a METS document with the same GROUPID.
   *
   * @param resourceId ID of the METS document.
   * @param groupId All possible GROUPID of the files.
   *
   * @return Iterator holding all files.
   */
  Iterable<MetsFile> findByResourceIdAndGroupIdInAndCurrentTrue(String resourceId, Collection<String> groupId);

  /**
   * Find all file URLs of a METS document with the same GROUPID.
   *
   * @param resourceId ID of the METS document.
   * @param groupId GROUPID of the files.
   *
   * @return Iterator holding all files.
   */
  Iterable<IUrl> findUrlByResourceIdAndGroupIdAndCurrentTrue(String resourceId, String groupId);

  /**
   * Find all file URLs of a METS document with the same GROUPID.
   *
   * @param resourceId ID of the METS document.
   * @param groupId All possible GROUPID of the files.
   *
   * @return Iterator holding all file URLs.
   */
  Iterable<IUrl> findUrlByResourceIdAndGroupIdInAndCurrentTrue(String resourceId, Collection<String> groupId);

  /**
   * Find all files of a METS document with the same USE and GROUPID.
   *
   * @param resourceId ID of the METS document.
   * @param use USE of the fileGrps.
   * @param groupId GROUPID of the files.
   *
   * @return Iterator holding all files.
   */
  Iterable<MetsFile> findByResourceIdAndUseAndGroupIdAndCurrentTrue(String resourceId, String use, String groupId);

  /**
   * Find all files of a METS document with the same USE and GROUPID.
   *
   * @param resourceId ID of the METS document.
   * @param use All possible USE of the fileGrps.
   * @param groupId All possible GROUPID of the files.
   *
   * @return Iterator holding all files.
   */
  Iterable<MetsFile> findByResourceIdAndUseInAndGroupIdInAndCurrentTrue(String resourceId, Collection<String> use, Collection<String> groupId);

  /**
   * Find all file URLs of a METS document with the same USE and GROUPID.
   *
   * @param resourceId ID of the METS document.
   * @param use USE of the fileGrps.
   * @param groupId GROUPID of the files.
   *
   * @return Iterator holding all files.
   */
  Iterable<IUrl> findUrlByResourceIdAndUseAndGroupIdAndCurrentTrue(String resourceId, String use, String groupId);

  /**
   * Find all file URLs of a METS document with the same USE and GROUPID.
   *
   * @param resourceId ID of the METS document.
   * @param use All possible USE of the fileGrps.
   * @param groupId All possible GROUPID of the files.
   *
   * @return Iterator holding all file URLs.
   */
  Iterable<IUrl> findUrlByResourceIdAndUseInAndGroupIdInAndCurrentTrue(String resourceId, Collection<String> use, Collection<String> groupId);
}
