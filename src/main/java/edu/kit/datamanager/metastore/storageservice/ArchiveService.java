package edu.kit.datamanager.metastore.storageservice;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface ArchiveService {

    void init();

    String getBasePath();
}
