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
package edu.kit.datamanager.metastore.kitdm;

import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.ApiResponse;
import io.swagger.client.Configuration;
import io.swagger.client.api.DataResourceControllerApi;
import io.swagger.client.model.Agent;
import io.swagger.client.model.DataResource;
import io.swagger.client.model.Identifier;
import io.swagger.client.model.ResourceType;
import io.swagger.client.model.Title;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author hartmann-v
 */
public class TestRestAccess2KitDM {
  
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://127.0.0.1:8090");
    defaultClient.setDebugging(true);

    // Configure API key authorization: Authorization
//        ApiKeyAuth Authorization = (ApiKeyAuth) defaultClient.getAuthentication("Authorization");
//        Authorization.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //Authorization.setApiKeyPrefix("Token");
    DataResourceControllerApi apiInstance = new DataResourceControllerApi(defaultClient);
    Path get = Paths.get("/tmp/anydir");
    Path path = Paths.get("first", "second");
    System.out.println(path);
    System.out.println(get.resolve(path));
    path = Paths.get("", "second");
    System.out.println(path);
    System.out.println(get.resolve(path));
if (args.length < 10)
return;    
    try {
      
      DataResource resource = new DataResource(); // DataResource | resource
      resource.setResourceIdentifier("blablub");
//      List<DataResource> findByExampleUsingPOST = apiInstance.findByExampleUsingPOST(resource, null, null, null);
//      System.out.println("Find known UUID: " + findByExampleUsingPOST.size());
//      System.out.println(resource);
//      resource = new DataResource(); 
//      resource.setResourceIdentifier("Bla blub");
//    findByExampleUsingPOST = apiInstance.findByExampleUsingPOST(resource, null, null, null);
//      System.out.println("Find unknown UUID: " + findByExampleUsingPOST.size());
//      System.out.println(resource);
//if (args.length < 10)
//return;
      resource.addCreatorsItem(new Agent().familyName("OCR-D").givenName("Repo"));
//        resource.setEmbargoDate(new Date().toString());
      resource.addAlternateIdentifiersItem(new Identifier().identifierType(Identifier.IdentifierTypeEnum.INTERNAL).value("affeaffe"));
//      resource.setIdentifier(new PrimaryIdentifier().identifierType(PrimaryIdentifier.IdentifierTypeEnum.PURL).value("http://ocr-d.de/kant_aufklärung2"));
//        resource.setPublicationYear("2018");
//        resource.setPublisher("OCR-D Softwareframework");
      resource.setResourceType(new ResourceType().typeGeneral(ResourceType.TypeGeneralEnum.DATASET).value("OCR-D data"));
      resource.addTitlesItem(new Title().value("Der Herold"));
      System.out.println(resource.toString());
//        try {
//            DataResource result = apiInstance.createUsingPOST(new DataResource());
//            System.out.println(result);
//        } catch (ApiException e) {
//            System.err.println("Exception when calling DataResourceControllerApi#createUsingPOST");
//            System.out.println(e.getMessage());
//            e.printStackTrace();
//        }
      ApiResponse<DataResource> result;
      result = apiInstance.createUsingPOSTWithHttpInfo(resource);
      System.out.println(result.getData());
//      String location = result.getHeaders().get("Location").get(0);
//      System.out.println("Header->location: " + location);
////     apiInstance.handleFileUploadUsingPOST(result.getData().getId(), new File("/tmp/bag/bagValid.zip"), Boolean.FALSE, "test/bagValid.zip");
//      ApiResponse<ResponseEntity> handleFileUploadUsingPOSTWithHttpInfo = apiInstance.handleFileUploadUsingPOSTWithHttpInfo(result.getData().getId(), new File("/tmp/bag/test/bagit.txt"), Boolean.FALSE, "test/bagit.txt");
//      for (String key : handleFileUploadUsingPOSTWithHttpInfo.getHeaders().keySet()) {
//        System.out.println(key  + ": " + handleFileUploadUsingPOSTWithHttpInfo.getHeaders().get(key).get(0));
//      }
    } catch (ApiException e) {
      System.err.println("Exception when calling DataResourceControllerApi#createUsingPOST");
      System.out.println(e.getMessage());
      e.printStackTrace();
    }
    
  }
  
}
