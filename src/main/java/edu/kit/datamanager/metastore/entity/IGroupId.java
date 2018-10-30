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
 * Interface to get and set GroupId of an instance.
 */
public interface IGroupId {

  /**
   * Set GROUPID of a file.
   *
   * @param groupId GROUPID to set.
   */
  public void setGroupId(String groupId);

  /**
   * Get GROUPID of a file.
   *
   * @return GROUPID of the file.
   */
  public String getGroupId();
}
