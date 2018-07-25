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
package edu.kit.datamanager.metastore.entity;

/**
 *
 * @author hartmann-v
 */
public enum MdType {

  MARC("MARC"),
  MODS("MODS"),
  EAD("EAD"),
  DC("DC"),
  NISOIMG("NISOIMG"),
  LC_AV("LC-AV"),
  VRA("VRA"),
  TEIHDR("TEIHDR"),
  DDI("DDI"),
  FGDC("FGDC"),
  LOM("LOM"),
  PREMIS("PREMIS"),
  OTHER("OTHER");
  
  private final String textRepresentation;

  /**
   * Constructor for enum.
   * @param pTextRepresentation text representation of enumeration.
   */
  MdType(final String pTextRepresentation) {
    this.textRepresentation = pTextRepresentation;
  }

  @Override
  public String toString() {
    return textRepresentation;
  }
}
