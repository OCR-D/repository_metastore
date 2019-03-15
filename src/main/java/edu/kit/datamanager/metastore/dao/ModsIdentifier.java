/*
 * Copyright 2019 Karlsruhe Institute of Technology.
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
package edu.kit.datamanager.metastore.dao;

/**
 * Mods identifier of METS file.
 */
public class ModsIdentifier {
  /** Type of the identifier. */
  private String type;
  /** Identifier. */
  private String identifier;
  /**
   * Constructor inializing object with values.
   * @param type Type of the identifier.
   * @param identifier ID of the identifier.
   */
  public ModsIdentifier(String type, String identifier) {
    this.type = type;
    this.identifier = identifier;
  }

  /**
   * Get type of the identifier.
   * @return the type
   */
  public String getType() {
    return type;
  }

  /**
   * Set type of the identifier.
   * @param type the type to set
   */
  public void setType(String type) {
    this.type = type;
  }

  /**
   * Get identifier.
   * @return the identifier
   */
  public String getIdentifier() {
    return identifier;
  }

  /**
   * Set identifier.
   * @param identifier the identifier to set
   */
  public void setIdentifier(String identifier) {
    this.identifier = identifier;
  }
  
}
