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

import io.swagger.client.ApiException;
import java.nio.file.FileVisitResult;
import static java.nio.file.FileVisitResult.CONTINUE;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class for walking through directory and register/upload them to repository
 *
 * @author hartmann-v
 */
public class RegisterFilesInRepo extends SimpleFileVisitor<Path> {

  /**
   * Logger.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(RegisterFilesInRepo.class);
  /**
   * Instance for connection to Repository.
   */
  RepositoryUtil repository;
  /**
   * Base path of file walk.
   */
  Path basePath;
  /**
   * RepoID of data source.
   */
  String repoId = RepositoryUtil.NO_RESOURCE_IDENTIFIER;
  /**
   * Force overwriting existing files.
   */
  Boolean force = false;

  /**
   * Constructor for initializing file uploads from directory.
   *
   * @param repo Utility class for accessing repository.
   * @param basePath Base path for file walk.
   * @param repoId ID of resource in the repository.
   * @param force Overwrite files (default: false)
   */
  public RegisterFilesInRepo(RepositoryUtil repo, Path basePath, String repoId, Boolean force) {
    this.repository = repo;
    this.basePath = basePath;
    this.repoId = repoId;
    this.force = force;
  }

  @Override
  public FileVisitResult visitFile(Path file,
          BasicFileAttributes attr) {
    if (attr.isRegularFile()) {
      Path relativePath = basePath.relativize(file);
      if (LOGGER.isTraceEnabled()) {
        LOGGER.trace("Regular file: '{}'", file);
        LOGGER.trace("Relative path: '{}'", relativePath);
      }

      try {
        repository.postFileToResource(repoId, force, relativePath, file.toFile());
      } catch (ApiException ex) {
        LOGGER.error("Regular file: '{}'", file);
        LOGGER.error("Relative path: '{}'", relativePath);
        LOGGER.error("Error writing file to repository", ex);
      }
    }
    return CONTINUE;
  }
}
