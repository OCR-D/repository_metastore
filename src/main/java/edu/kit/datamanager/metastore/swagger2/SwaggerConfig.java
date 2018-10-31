/*
 * Copyright 2016 Karlsruhe Institute of Technology.
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
package edu.kit.datamanager.metastore.swagger2;

import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Pageable;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.util.UriComponentsBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 *
 * @author jejkal
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig{

  @Bean
  public Docket api(){
    return new Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.basePackage("edu.kit.datamanager.metastore"))
            .paths(PathSelectors.any())
            .build()
            .ignoredParameterTypes(Pageable.class, WebRequest.class, HttpServletResponse.class, UriComponentsBuilder.class)
            .apiInfo(apiInfo())
            .securitySchemes(Arrays.asList(apiKey()))//, new BasicAuth("test")));
            .securityContexts(Arrays.asList(securityContext()));
  }

  private ApiKey apiKey(){
    return new ApiKey("Authorization", "Authorization", "header");
  }

  private ApiInfo apiInfo(){
    return new ApiInfo(
            "Repository Microservice - RESTful API",
            "This webpage describes the RESTful interface of the Metastore Service based on KIT Data Manager Repository Microservice.",
            "0.1.0",
            null,
            new Contact("KIT Data Manager Support", "datamanager.kit.edu", "support@datamanager.kit.edu"),
            "Apache 2.0",
            "http://www.apache.org/licenses/LICENSE-2.0.html",
            Collections.emptyList());
  }

  private SecurityContext securityContext(){
    return SecurityContext.builder()
            .securityReferences(defaultAuth())
            .forPaths(PathSelectors.regex("/api/v1.*"))
            .build();
  }

  List<SecurityReference> defaultAuth(){
    AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
    AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
    authorizationScopes[0] = authorizationScope;
    return Lists.newArrayList(new SecurityReference("Authorization", authorizationScopes));
  }
}
