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
package edu.kit.datamanager.metastore.storageservice;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

/**
 * Interface for storing uploaded files.
 */
public interface StorageService {

  /**
   * Initialize upload directory.
   */
  void init();

  /**
   * Storing single file in upload directory.
   *
   * @param file File to store.
   */
  void store(MultipartFile file);

  /**
   * Storing single file in subdirectory of upload directory.
   *
   * @param file File to store.
   * @param subDir Subdirectory to store in.
   */
  void store(MultipartFile file, String subDir);

   /**
   * Get location of given file.
   *
   * @param filename filename
   * @return Path to file.
   */
  Path getPath(String filename);

   /**
   * Get location of given file.
   *
   * @param filename filename
   * @param subDir subdirectory of the file.
   * 
   * @return Path to file.
   */
  Path getPath(String filename, String subDir);

  /**
   * Get root directory of upload directory.
   *
   * @return root directory as String.
   */
  String getBasePath();
}
