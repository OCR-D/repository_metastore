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
package edu.kit.datamanager.metastore;

//import edu.kit.datamanager.repo.configuration.ApplicationProperties;
import edu.kit.datamanager.metastore.runner.CrudRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 * @author Volker Hartmann
 */
@SpringBootApplication
public class MetastoreApplication{

   public static void main(String[] args){
//    ApplicationContext ctx = SpringApplication.run(MetastoreApplication.class, args);
//    ApplicationProperties bean = ctx.getBean(ApplicationProperties.class);
//    System.out.println(bean);
     final Class<?>[] runner = new Class<?>[]{CrudRunner.class/*, ByExampleRunner.class, DerivedQueryRunner.class ,
				RelationsRunner.class, AQLRunner.class, GeospatialRunner.class */
     };
     System.exit(SpringApplication.exit(SpringApplication.run(runner, args)));

    /*  String[] beanNames = ctx.getBeanDefinitionNames();
    Arrays.sort(beanNames);
    for(String beanName : beanNames){
      System.out.println(beanName);
    }
    System.out.println("Spring Boot started...");*/
  }

}
