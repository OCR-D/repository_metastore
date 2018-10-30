/*
 * DISCLAIMER
 *
 * Copyright 2017 ArangoDB GmbH, Cologne, Germany
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Copyright holder is ArangoDB GmbH, Cologne, Germany
 */

package edu.kit.datamanager.metastore;

import org.springframework.context.annotation.Configuration;

import com.arangodb.ArangoDB;
import com.arangodb.ArangoDB.Builder;
import com.arangodb.Protocol;
import com.arangodb.springframework.annotation.EnableArangoRepositories;
import com.arangodb.springframework.config.AbstractArangoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Volker Hartmann
 *
 */
@Configuration
@ConfigurationProperties("arangodb")
@EnableArangoRepositories(basePackages = { "edu.kit.datamanager.metastore" })
public class ArangoDBConfiguration extends AbstractArangoConfiguration {
  
  /**
   * Hostname of the ArangoDB server.
   */
  private String host = "127.0.0.1";

  /**
   * Port of the ArangoDB server.
   */
  private Integer port = 8578;

  /**
   * User of the ArangoDB server.
   */
  private String user = "root";

  /**
   * Password of the ArangoDB server.
   */
  private String password = "ocrd-testOnly";

  /**
   * Database of the ArangoDB server.
   */
  private String database = "metastore-OCR-D";

	@Override
	public Builder arango() {
		return new ArangoDB.Builder().useProtocol(Protocol.HTTP_JSON).host(getHost(), getPort()).user(getUser()).password(getPassword());
	}

	@Override
	public String database() {
		return getDatabase();
	}

  /**
   * Get hostname of the arangoDB server.
   * 
   * @return the host
   */
  public String getHost() {
    return host;
  }

  /**
    * Set hostname of the arangoDB server.
   * 
  * @param host the host to set
   */
  public void setHost(String host) {
    this.host = host;
  }

  /**
   * Get port of the arangoDB server.
   * 
   * @return the port
   */
  public Integer getPort() {
    return port;
  }

  /**
   * Set port of the arangoDB server.
   * 
     * @param port the port to set
   */
  public void setPort(Integer port) {
    this.port = port;
  }

  /**
   * Get user name of the arangoDB server.
   * 
   * @return the user
   */
  public String getUser() {
    return user;
  }

  /**
   * Set user name of the arangoDB server.
   * 
   * @param user the user to set
   */
  public void setUser(String user) {
    this.user = user;
  }

  /**
   * Get password for user of the arangoDB server.
   * 
   * @return the password
   */
  public String getPassword() {
    return password;
  }

  /**
   * Set password for user of the arangoDB server.
   * 
   * @param password the password to set
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * Get database of the arangoDB server.
   * 
   * @return the database
   */
  public String getDatabase() {
    return database;
  }

  /**
   * Set database of the arangoDB server.
   * 
   * @param database the database to set
   */
  public void setDatabase(String database) {
    this.database = database;
  }

}
