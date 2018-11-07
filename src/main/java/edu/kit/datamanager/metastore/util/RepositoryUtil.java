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
import io.swagger.client.model.Agent;
import io.swagger.client.model.DataResource;
import io.swagger.client.model.ResourceType;
import io.swagger.client.model.ResponseEntity;
import io.swagger.client.model.Title;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
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
  public static final long NO_RESOURCE_IDENTIFIER = -1l;
  /**
   * Instance for talking with repository.
   */
  KitDmProperties properties = null;

  /**
   * Client for accessing repository.
   */
  DataResourceControllerApi apiInstance;

  /**
   * Constructor initializing connection to repository.
   *
   * @param properties Properties for adjusting connection.
   */
  public RepositoryUtil(KitDmProperties properties) {
    this.properties = properties;
    initConnection();
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
    LOGGER.trace("Base path: '{}', Debug: '{}'", defaultClient.getBasePath(), defaultClient.isDebugging());
    apiInstance = new DataResourceControllerApi(defaultClient);
  }

  /**
   * Create resource with given identifier.
   *
   * @param title Title of the document
   * @param resourceType Type of the resource.
   *
   * @return Data resource.
   * @throws ApiException Error during execution.
   */
  public DataResource createDataResource(String title, String resourceType) throws ApiException {
    LOGGER.trace("Create data resource with title '{}'", title);

    DataResource dataResource = null;
    DataResource resource = new DataResource(); // DataResource | resource
    resource.addCreatorsItem(new Agent().familyName("Softwareframework").givenName("OCR-D"));
    resource.setResourceType(new ResourceType().typeGeneral(ResourceType.TypeGeneralEnum.DATASET).value(resourceType));
    resource.addTitlesItem(new Title().value(title));
    ApiResponse<DataResource> result;
    result = apiInstance.createUsingPOSTWithHttpInfo(resource);
    dataResource = result.getData();

    LOGGER.debug("ID of data resource: '{}'", dataResource.getId());
    LOGGER.trace("Data resource: '{}'", dataResource);

    return dataResource;
  }

  /**
   * Upload file for a data resource.
   *
   * @param idOfResource Identifier of data resource.
   * @param force Force overwriting existing files.
   * @param relativePath Relative Path to file.
   * @param uploadFile File to upload.
   *
   * @return Response of POST method.
   *
   * @throws ApiException Error during execution.
   */
  public ApiResponse<ResponseEntity> postFileToResource(Long idOfResource, Boolean force, Path relativePath, File uploadFile) throws ApiException {
    LOGGER.trace("Post file '{}' to resource with ID '{}'", relativePath.toString(), idOfResource);
    ApiResponse<ResponseEntity> handleFileUpload = apiInstance.handleFileUploadUsingPOSTWithHttpInfo(idOfResource, uploadFile, force, relativePath.toString());
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
    DataResource resource = new DataResource(); // DataResource | resource
    resource.setResourceIdentifier(resourceIdentifier);
    List<DataResource> findByExampleUsingPOST;
    try {
      findByExampleUsingPOST = apiInstance.findByExampleUsingPOST(resource, null, null, null);
      resourceExists = !findByExampleUsingPOST.isEmpty();
    } catch (ApiException ex) {
      LOGGER.error("Test for resourceIdentifier failed!", ex);
    }
    return resourceExists;
  }

  /**
   * Test if given resourceIdentifier already exists.
   *
   * @param resourceIdentifier resourceIdentifier of the data resource.
   *
   * @return true if resourceIdentifier already exists.
   */
  public long getIdOfResourceIdentifier(String resourceIdentifier) {
    long resourceId = NO_RESOURCE_IDENTIFIER;
    DataResource resource = new DataResource(); // DataResource | resource
    resource.setResourceIdentifier(resourceIdentifier);
    List<DataResource> findByExampleUsingPOST;
    try {
      findByExampleUsingPOST = apiInstance.findByExampleUsingPOST(resource, null, null, null);
      if (findByExampleUsingPOST.size() == 1) {
        resourceId = findByExampleUsingPOST.get(0).getId();
      }
    } catch (ApiException ex) {
      LOGGER.error("Test for resourceIdentifier failed!", ex);
    }
    return resourceId;
  }

  /**
   * Create Download URL from relative data path and id of resource.
   *
   * @param id ID of resource in KIT DM repo.
   * @param dataPath Relative data path.
   *
   * @return URL for downloading resource.
   */
  public String toDownloadUrl(Long id, Path dataPath) {
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
