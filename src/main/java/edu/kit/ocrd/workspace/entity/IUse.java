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
 * Interface to get and set USE of an instance.
 */
public interface IUse {

  /**
   * Set USE of a file.
   *
   * @param use USE to set.
   */
  public void setUse(String use);

  /**
   * Get USE of a file.
   *
   * @return USE of the file.
   */
  public String getUse();
}
