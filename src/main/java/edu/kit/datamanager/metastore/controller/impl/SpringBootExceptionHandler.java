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
package edu.kit.datamanager.metastore.controller.impl;

import edu.kit.datamanager.metastore.storageservice.StorageFileNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kit.datamanager.metastore.exception.BagItException;
import edu.kit.datamanager.metastore.exception.InvalidFormatException;
import edu.kit.datamanager.metastore.exception.ResourceAlreadyExistsException;
import edu.kit.ocrd.workspace.exception.WorkspaceException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

/**
 * Handling Exceptions
 */
@ControllerAdvice
public class SpringBootExceptionHandler{

  /**
   * Logger for this class.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(SpringBootExceptionHandler.class);

  /**
   * Handler for FileNotFoundExceptions.
   *
   * @param exc Exception writing file.
   *
   * @return Error status.
   */
  @ExceptionHandler(ResourceAlreadyExistsException.class)
  public ResponseEntity<?> handleStorageFileNotFound(ResourceAlreadyExistsException exc) {
    return ResponseEntity.badRequest().body(exc.getMessage());
  }

  /**
   * Handler for FileNotFoundExceptions.
   *
   * @param exc Exception reading/writing file.
   *
   * @return Error status.
   */
  @ExceptionHandler(StorageFileNotFoundException.class)
  public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
    return ResponseEntity.notFound().build();
  }

  /**
   * Handler for InvalidFormatException.
   *
   * @param exc Exception parsing file.
   *
   * @return Error status.
   */
  @ExceptionHandler(InvalidFormatException.class)
  public ResponseEntity<?> handleStorageFileNotFound(InvalidFormatException exc) {
    return ResponseEntity.badRequest().body(exc.getMessage());
  }

  /**
   * Handler for InvalidFormatException.
   *
   * @param exc Exception parsing file.
   *
   * @return Error status.
   */
  @ExceptionHandler(WorkspaceException.class)
  public ResponseEntity<?> handleStorageFileNotFound(WorkspaceException exc) {
    return ResponseEntity.badRequest().body(exc.getMessage());
  }

  /**
   * Handler for BagItExceptions.
   *
   * @param exc Exception reading/writing BagIt container.
   *
   * @return Error status.
   */
  @ExceptionHandler(BagItException.class)
  public ResponseEntity<?> handleInvalidBagItContainer(BagItException exc) {
    return ResponseEntity.badRequest().body(exc.getMessage());
  }
  /**
   * Handler for exceeding maximum size for uploading files.
   * 
   * @param exc Exception thrown by SpringBoot.
   * @param request Instance holding request.
   * @param response Instance holding response.
   * 
   * @return Message for client.
   */
     @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<?>  handleMaxSizeException(MaxUploadSizeExceededException exc, RedirectAttributes redirectAttributes) {
        String message = exc.getRootCause().getMessage();
        return ResponseEntity.badRequest().body(message);
    }
}
