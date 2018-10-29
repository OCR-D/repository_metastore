package edu.kit.datamanager.metastore.storageservice;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("storage")
public class StorageProperties {

  /**
   * Folder location for storing files
   */
  private String location = "/tmp/upload";
  /**
   * Folder location for archiving uploaded files
   */
  private String archive = "/tmp/archive";

  /**
   * Get the base path for uploading files.
   *
   * @return base path as string.
   */
  public String getLocation() {
    return location;
  }

  /**
   * Set the base path for uploading files.
   *
   * @param location base path as string.
   */
  public void setLocation(String location) {
    this.location = location;
  }

  /**
   * Get the path for archiving data.
   *
   * @return the archive
   */
  public String getArchive() {
    return archive;
  }

  /**
   * Set the path for archiving data.
   *
   * @param archive the archive to set
   */
  public void setArchive(String archive) {
    this.archive = archive;
  }

}
