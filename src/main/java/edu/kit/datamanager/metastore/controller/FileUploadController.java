package edu.kit.datamanager.metastore.controller;

import edu.kit.datamanager.metastore.service.IMetsDocumentService;
import edu.kit.datamanager.metastore.service.impl.MetsDocumentService;
import edu.kit.datamanager.metastore.storageservice.ArchiveService;
import edu.kit.datamanager.metastore.storageservice.StorageFileNotFoundException;
import edu.kit.datamanager.metastore.storageservice.StorageService;
import edu.kit.datamanager.metastore.util.BagItUtil;
import edu.kit.datamanager.metastore.util.ZipUtils;
import gov.loc.repository.bagit.Bag;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class FileUploadController {

  @Autowired
  private IMetsDocumentService metsDocumentService;

  private final StorageService storageService;

  private final ArchiveService archiveService;

  @Autowired
  public FileUploadController(StorageService storageService, ArchiveService archiveService) {
    this.storageService = storageService;
    this.archiveService = archiveService;
  }

  @GetMapping("metastore/bagit/")
  public String listUploadedFiles(Model model) throws IOException {
    System.out.println("listUploadedFiles - " + model);

    model.addAttribute("files", storageService.loadAll().map(
            path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
                    "serveFile", path.getFileName().toString()).build().toString())
            .collect(Collectors.toList()));

    return "uploadForm";
  }

  @GetMapping("metastore/bagit/files/{filename:.+}")
  @ResponseBody
  public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
    System.out.println("serveFile - " + filename);

    Resource file = storageService.loadAsResource(filename);
    return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
            "attachment; filename=\"" + file.getFilename() + "\"").body(file);
  }

  @PostMapping("/metastore/bagit/")
  public String handleFileUpload(@RequestParam("file") MultipartFile file,
          RedirectAttributes redirectAttributes) throws IOException {
    System.out.println("handleFileUpload");
    storageService.store(file);
    redirectAttributes.addFlashAttribute("message",
            "You successfully uploaded " + file.getOriginalFilename() + "!");

    System.out.println("Unpack bag and validate");
    System.out.println("Archive to: " + archiveService.getBasePath());
    System.out.println("file getOriginalName = " + file.getOriginalFilename());
    if (file.getOriginalFilename().endsWith(".zip")) {
      String baseName = FilenameUtils.getBaseName(file.getOriginalFilename());
      Path pathToArchive = Paths.get(archiveService.getBasePath(), baseName);
      Path createDirectory = Files.createDirectory(pathToArchive);
      ZipUtils.unzip(storageService.load(file.getOriginalFilename()).toFile(), createDirectory.toFile());
      Bag bag = BagItUtil.createBag(pathToArchive.toFile());
      String xOcrdMets = bag.getBagInfoTxt().getOrDefault("X-Ocrd-Mets", "data/mets.xml");
      File metsFile = Paths.get(pathToArchive.toString(), xOcrdMets).toFile();
      String metsFileAsString = FileUtils.readFileToString(metsFile, "UTF-8");
      metsDocumentService.createMetsDocument("mets_0001", metsFileAsString);
           
    }

    return "redirect:/metastore/bagit/";
  }

  @ExceptionHandler(StorageFileNotFoundException.class)
  public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
    return ResponseEntity.notFound().build();
  }

}
