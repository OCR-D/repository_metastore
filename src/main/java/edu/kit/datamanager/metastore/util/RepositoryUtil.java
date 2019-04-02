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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jackson.jsonpointer.JsonPointer;
import com.github.fge.jsonpatch.AddOperation;
import com.github.fge.jsonpatch.JsonPatch;
import edu.kit.datamanager.metastore.kitdm.KitDmProperties;
import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.ApiResponse;
import io.swagger.client.Configuration;
import io.swagger.client.api.DataResourceControllerApi;
import io.swagger.client.api.LoginControllerApi;
import io.swagger.client.model.AclEntry;
import io.swagger.client.model.Agent;
import io.swagger.client.model.DataResource;
import io.swagger.client.model.Identifier;
import io.swagger.client.model.ResourceType;
import io.swagger.client.model.ResponseEntity;
import io.swagger.client.model.Title;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
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
   * SID of group USERS
   */
  public static final String USERS_GROUP = "USERS";
  /**
   * SID of anonymous user.
   */
  public static final String ANONYMOUS_USER = "anonymousUser";

  /**
   * IF-Match header
   */
  private static final String IF_MATCH_HEADER = "If-Match";
  /**
   * Instance for talking with repository.
   */
  KitDmProperties properties = null;

  /**
   * Client for accessing repository.
   */
  DataResourceControllerApi apiInstance;
  /**
   * Client for authentication.
   */
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
      ApiClient loginClient = new ApiClient();
      loginClient.setDebugging(Boolean.getBoolean(properties.getDebug()));
      loginClient.setBasePath(properties.getBasePathAuth());
      // Build authentication header
      String auth = properties.getUsername() + ":" + properties.getPassword();
      byte[] encodedAuth = Base64.encodeBase64(
              auth.getBytes(StandardCharsets.ISO_8859_1));
      String authHeader = "Basic " + new String(encodedAuth);
      loginClient.addDefaultHeader("Authorization", authHeader);

      api4Login = new LoginControllerApi(loginClient);

      authorizeConnection(defaultClient);
    }
  }

  /**
   * Login for KIT DM 2.0 and set bearer token for REST client.
   */
  private void authorizeConnection() {
    authorizeConnection(apiInstance.getApiClient());
  }

  /**
   * Login for KIT DM 2.0 and set bearer token for REST client.
   *
   * @param apiClient apiClient to add bearer token.
   */
  private void authorizeConnection(ApiClient apiClient) {
    if (Boolean.valueOf(properties.getAuthentication())) {
      try {
        String bearerToken = "Bearer " + api4Login.loginUsingPOST("OCRD");
        LOGGER.trace("Create bearer token for user '{}': {}", properties.getUsername(), bearerToken);
        apiClient.addDefaultHeader("Authorization", bearerToken);
      } catch (ApiException ae) {
        logApiException("Error during authorization!", ae);
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
      String etag = getEtagFromHeader(result);
      if (etag != null) {
        setAnonymousAccess(idOfResource, etag);
      }
    } catch (ApiException ae) {
      if (ae.getCode() == UNAUTHORIZED) {
        LOGGER.warn("Unauthorized access! Create new token and try it once again!");
        // refresh bearer token and then...
        authorizeConnection();
        // ...try once again
        result = apiInstance.createUsingPOSTWithHttpInfo(resource);
      } else {
        String message = String.format("Create data resource with title '%1s'", title);
        logApiException(message, ae);
        throw ae;
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
      } else {
        String message = String.format("Post file '%1s' to resource with ID '%2s'", relativePath.toString(), idOfResource);
        logApiException(message, ae);
        throw ae;
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

  /**
   * Read ETag from Header.
   *
   * @param response Response from server.
   *
   * @return ETag or null if not present.
   */
  private <T> String getEtagFromHeader(ApiResponse<T> response) {
    String etag = null;
    List<String> etagList = response.getHeaders().get("ETag");
    if (etagList != null) {
      if (!etagList.isEmpty()) {
        etag = etagList.get(0);
      }
    }
    return etag;
  }

  /**
   * Patch digital object to allow anonymous access.
   *
   * @param resourceId resource ID of digital object.
   * @param etag ETag of digital object.
   */
  private void setAnonymousAccess(String resourceId, String etag) {
    ApiClient defaultClient = new ApiClient();
    defaultClient.setBasePath(properties.getBasePath());
    defaultClient.setDebugging(Boolean.getBoolean(properties.getDebug()));
    defaultClient.addDefaultHeader(IF_MATCH_HEADER, etag);
    authorizeConnection(defaultClient);
    DataResourceControllerApi patchInstance = new DataResourceControllerApi(defaultClient);

    try {
      // Set access for all authenticated users
      AclEntry aclUser = new AclEntry();
      aclUser.setSid(USERS_GROUP);
      aclUser.setPermission(AclEntry.PermissionEnum.READ);
      AclEntry aclAnonymousUser = new AclEntry();
      aclAnonymousUser.setSid(ANONYMOUS_USER);
      aclAnonymousUser.setPermission(AclEntry.PermissionEnum.READ);
      ObjectMapper mapper = new ObjectMapper();
      //build patch operation
      AddOperation op = new AddOperation(JsonPointer.of("acls", "1"), mapper.readTree(mapper.writeValueAsString(aclUser)));
      AddOperation op2 = new AddOperation(JsonPointer.of("acls", "2"), mapper.readTree(mapper.writeValueAsString(aclAnonymousUser)));
      JsonPatch patch_add = new JsonPatch(Arrays.asList(op, op2));
      patchInstance.patchUsingPATCHWithHttpInfo(resourceId, mapper.writeValueAsString(patch_add));
    } catch (ApiException ex) {
      String message = String.format("Error patching digital Object '%1s' with ETag '%2s'", resourceId, etag);
      logApiException(message, ex);
    } catch (IOException ex) {
      LOGGER.error("Error while patching digital Object '{}' with ETag '{}'", resourceId, etag);
      LOGGER.error("Stacktrace: ", ex);
    }

  }

  /**
   * Log exception.
   *
   * @param message Additional message
   * @param ae Exception.
   */
  private void logApiException(String message, ApiException ae) {
    LOGGER.error(message);
    LOGGER.error("ApiException: Status code: {} - Message: {}", ae.getCode(), ae.getMessage());
    LOGGER.error("Response body: {}", ae.getResponseBody());
  }
}
