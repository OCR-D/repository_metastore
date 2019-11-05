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
package edu.kit.ocrd.workspace.entity;

/**
 * Enumeration of all possible metadata types of a section document inside a
 * METS document.
 */
public enum MdType {
  /**
   * MARC (MAchine-Readable Cataloging)
   */
  MARC("MARC"),
  /**
   * MODS (Metadata Object Description Schema)
   */
  MODS("MODS"),
  /**
   * EAD (Encoded Archival Description )
   */
  EAD("EAD"),
  /**
   * DC (Dublin Core)
   */
  DC("DC"),
  /**
   * NISOIMG (NISO Technical Metadata for Digital Still Images)
   */
  NISOIMG("NISOIMG"),
  /**
   * LC-AV (Library of Congress Audiovisual Metadata)
   */
  LC_AV("LC-AV"),
  /**
   * VRA (VRA Core)
   */
  VRA("VRA"),
  /**
   * TEIHDR (TEI Header)
   */
  TEIHDR("TEIHDR"),
  /**
   * DDI (Data Documentation Initiative)
   */
  DDI("DDI"),
  /**
   * FGDC (Federal Geographic Data Committee Metadata Standard)
   */
  FGDC("FGDC"),
  LOM("LOM"),
  PREMIS("PREMIS"),
  OTHER("OTHER");
  
  /**
   * Text representation of enumeration.
   */
  private final String textRepresentation;

  /**
   * Constructor for enum.
   *
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
