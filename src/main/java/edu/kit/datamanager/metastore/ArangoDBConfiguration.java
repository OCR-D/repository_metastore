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

/**
 * @author Volker Hartmann
 *
 */
@Configuration
@EnableArangoRepositories(basePackages = { "edu.kit.datamanager.metastore" })
public class ArangoDBConfiguration extends AbstractArangoConfiguration {

	@Override
	public Builder arango() {
		return new ArangoDB.Builder().useProtocol(Protocol.HTTP_JSON).host("127.0.0.1", 8529).user("root").password("arangoDB");
	}

	@Override
	public String database() {
		return "metastoreTestWithThomas";
	}

}
