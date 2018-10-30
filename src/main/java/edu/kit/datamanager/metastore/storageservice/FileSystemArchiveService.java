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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * Service for archiving unzipped files.
 */
@Service
public class FileSystemArchiveService implements ArchiveService {
    /**
     * Path to root directory.
     */
    private final Path rootLocation;
    /**
     * Constructor with given properties.
     * 
     * @param properties Properties.
     */
    @Autowired
    public FileSystemArchiveService(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getArchive());
    }


    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        }
        catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }

  @Override
  public String getBasePath() {
    return rootLocation.toString();
  }
}
