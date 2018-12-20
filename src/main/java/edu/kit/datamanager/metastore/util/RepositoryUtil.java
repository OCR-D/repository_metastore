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

import edu.kit.datamanager.metastore.kitdm.KitDmProperties;
import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.ApiResponse;
import io.swagger.client.Configuration;
import io.swagger.client.api.DataResourceControllerApi;
import io.swagger.client.api.LoginControllerApi;
import io.swagger.client.model.Agent;
import io.swagger.client.model.DataResource;
import io.swagger.client.model.Identifier;
import io.swagger.client.model.ResourceType;
import io.swagger.client.model.ResponseEntity;
import io.swagger.client.model.Title;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.GregorianCalendar;
import java.util.List;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility for KIT Data Manager.
 */
public class RepositoryUtil {

  /**
   * Logger.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(RepositoryUtil.class);
  /**
   * Constant for accessing location in http header.
   */
  public static final String LOCATION_HEADER = "Location";
  /**
   * Constant defining non existing resourceIdentifier.
   */
  public static final String NO_RESOURCE_IDENTIFIER = "No resource identifier";
  /**
   * Constant defining status code of unauthorized access.
   */
  public static final int UNAUTHORIZED = 401;
  /**
   * Instance for talking with repository.
   */
  KitDmProperties properties = null;

  /**
   * Client for accessing repository.
   */
  DataResourceControllerApi apiInstance;

  LoginControllerApi api4Login;

  /**
   * Constructor initializing connection to repository.
   *
   * @param properties Properties for adjusting connection.
   */
  public RepositoryUtil(KitDmProperties properties) {
    this.properties = properties;
    initConnection();
    if (LOGGER.isInfoEnabled()) {
      LOGGER.info("basepath: {}", properties.getBasePath());
      LOGGER.info("debug: {}", properties.getDebug());
      LOGGER.info("authentication: {}", properties.getAuthentication());
      LOGGER.info("basepath(auth): {}", properties.getBasePathAuth());
      LOGGER.info("username: {}", properties.getUsername());
      LOGGER.info("password: {}", properties.getPassword());
    }
  }

  /**
   * Initialize connection to repo.
   */
  private void initConnection() {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath(properties.getBasePath());
    defaultClient.setDebugging(Boolean.getBoolean(properties.getDebug()));
//    defaultClient.setAccessToken(accessToken);
//    defaultClient.setApiKey(apiKey);
//    defaultClient.setApiKeyPrefix(apiKeyPrefix);
//    defaultClient.setConnectTimeout(0);
//    defaultClient.setReadTimeout(0)
//    defaultClient.setPassword(password);
//    defaultClient.setUsername(username);
//    defaultClient.setWriteTimeout(0);
    apiInstance = new DataResourceControllerApi(defaultClient);
    // Initialize connection for login (if authentication is enabled)
    if (Boolean.valueOf(properties.getAuthentication())) {
      ApiClient loginClient = Configuration.getDefaultApiClient();
      loginClient.setDebugging(Boolean.getBoolean(properties.getDebug()));
      loginClient.setBasePath(properties.getBasePathAuth());
      api4Login = new LoginControllerApi(loginClient);

      authorizeConnection();
    }
  }

  /**
   * Login for KIT DM 2.0 and set bearer token for REST client.
   */
  private void authorizeConnection() {
    if (Boolean.valueOf(properties.getAuthentication())) {
      String auth = properties.getUsername() + ":" + properties.getPassword();

      byte[] encodedAuth = Base64.encodeBase64(
              auth.getBytes(StandardCharsets.ISO_8859_1));
      String authHeader = "Basic " + new String(encodedAuth);
      try {
      api4Login.getApiClient().addDefaultHeader("Authorization", authHeader);
      String bearerToken = "Bearer " + api4Login.loginUsingPOST("OCRD");
      LOGGER.trace("Create bearer token for user '{}': {}", properties.getUsername(), bearerToken);
      apiInstance.getApiClient().addDefaultHeader("Authorization", bearerToken);
      } catch (ApiException ae) {
        LOGGER.error("Error during authorization!", ae);
      }
    }
  }

  /**
   * Create resource with given identifier.
   *
   * @param idOfResource Identifier of data resource.
   * @param title Title of the document
   * @param resourceType Type of the resource.
   *
   * @return Data resource.
   * @throws ApiException Error during execution.
   */
  public DataResource createDataResource(String idOfResource, String title, String resourceType) throws ApiException {
    LOGGER.trace("Create data resource with title '{}'", title);

    DataResource dataResource = null;
    DataResource resource = new DataResource(); // DataResource | resource
    resource.addCreatorsItem(new Agent().familyName("Softwareframework").givenName("OCR-D"));
    resource.setResourceType(new ResourceType().typeGeneral(ResourceType.TypeGeneralEnum.DATASET).value(resourceType));
    resource.addAlternateIdentifiersItem(new Identifier().identifierType(Identifier.IdentifierTypeEnum.INTERNAL).value(idOfResource));
    resource.setPublisher("OCR-D");
    resource.setEmbargoDate(new GregorianCalendar(2099, 11, 31, 0, 0, 0).toInstant().toString());
    resource.addTitlesItem(new Title().value(title));
    ApiResponse<DataResource> result = null;
    try {
      result = apiInstance.createUsingPOSTWithHttpInfo(resource);
    } catch (ApiException ae) {
      if (ae.getCode() == UNAUTHORIZED) {
        LOGGER.warn("Unauthorized access! Create new token and try it once again!");
        // refresh bearer token and then...
        authorizeConnection();
        // ...try once again
        result = apiInstance.createUsingPOSTWithHttpInfo(resource);
      }
    }
    dataResource = result.getData();

    LOGGER.debug("ID of data resource: '{}'", dataResource.getIdentifier().getValue());
    LOGGER.trace("Data resource: '{}'", dataResource);

    return dataResource;
  }

  /**
   * Upload file for a data resource.
   *
   * @param idOfResource Identifier of data resource.
   * @param force Force overwriting existing files.
   * @param metadata Metadata related to file.
   * @param relativePath Relative Path to file.
   * @param uploadFile File to upload.
   *
   * @return Response of POST method.
   *
   * @throws ApiException Error during execution.
   */
  public ApiResponse<ResponseEntity> postFileToResource(String idOfResource, Boolean force, String metadata, Path relativePath, File uploadFile) throws ApiException {
    LOGGER.trace("Post file '{}' to resource with ID '{}'", relativePath.toString(), idOfResource);
    ApiResponse<ResponseEntity> handleFileUpload = null;
    try {
      handleFileUpload = apiInstance.handleFileUploadUsingPOSTWithHttpInfo(idOfResource, uploadFile, force, metadata, relativePath.toString());
    } catch (ApiException ae) {
      if (ae.getCode() == UNAUTHORIZED) {
        // refresh bearer token and then...
        authorizeConnection();
        // ...try once again
        handleFileUpload = apiInstance.handleFileUploadUsingPOSTWithHttpInfo(idOfResource, uploadFile, force, metadata, relativePath.toString());
      }
    }
    LOGGER.trace("Status code: '{}'", handleFileUpload.getStatusCode());

    return handleFileUpload;
  }

  /**
   * Test if given resourceIdentifier already exists.
   *
   * @param resourceIdentifier resourceIdentifier of the data resource.
   *
   * @return true if resourceIdentifier already exists.
   */
  public boolean existsResourceIdentifier(String resourceIdentifier) {
    boolean resourceExists = true;
    DataResource resource = null; // DataResource | resource
    try {
      resource = apiInstance.getByIdUsingGET(resourceIdentifier);
      if (resource == null) {
        resourceExists = false;
      }
    } catch (ApiException ex) {
      if (ex.getCode() == UNAUTHORIZED) {
        // refresh bearer token and then...
        authorizeConnection();
        // ...try once again
        try {
          resource = apiInstance.getByIdUsingGET(resourceIdentifier);
          if (resource == null) {
            resourceExists = false;
          }
        } catch (ApiException ae) {
          LOGGER.error("Test for resourceIdentifier failed!", ae);
          resourceExists = false;
        }
      }
      resourceExists = false;
    }
    return resourceExists;
  }

  /**
   * Create Download URL from relative data path and id of resource.
   *
   * @param id ID of resource in KIT DM repo.
   * @param dataPath Relative data path.
   *
   * @return URL for downloading resource.
   */
  public String toDownloadUrl(String id, Path dataPath) {
    Path downloadPath = null;
    String downloadString = "Can't create URL";

    try {
      LOGGER.trace("create Download URL from id '{}' and path '{}'", id, dataPath);
      downloadPath = Paths.get("api/v1/dataresources", id.toString(), "data", dataPath.toString());
      URL baseUrl = new URL(properties.getBasePath());
      URL downloadUrl = new URL(baseUrl, downloadPath.toString());
      downloadString = downloadUrl.toString();
    } catch (MalformedURLException ex) {
      LOGGER.error("create Download URL from id '{}' and path '{}'", id, dataPath);
      LOGGER.error("path '{}'", downloadPath.toString());
      LOGGER.error("Error creating URL", ex);
    }
    return downloadString;
  }

}
