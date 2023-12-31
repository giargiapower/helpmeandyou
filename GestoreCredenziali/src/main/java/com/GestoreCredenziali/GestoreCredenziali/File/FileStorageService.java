package com.GestoreCredenziali.GestoreCredenziali.File;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {

    private final Path fileStorageLocation;

    @Autowired
    public FileStorageService(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public String storeFile(MultipartFile file, String type_doc, String name) {
        //Controlla che le cartelle Documents CV e Documenti_identita siano create altrimenti creale
        checkDirectories();
        // Normalize file name
        String temp = "";
        if(!System.getProperty("user.dir").equals("\\"))
            temp = System.getProperty("user.dir");
        String fileName = StringUtils.cleanPath(temp + "/Documents/" + type_doc + "/" + name + file.getOriginalFilename().substring(file.getOriginalFilename().length() - 4) );
        System.out.println(fileName);
        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }


    public void checkDirectories() {
    	File doc_I = new File(System.getProperty("user.dir")+"/Documents/Documenti_identita");
        File curriculum = new File(System.getProperty("user.dir")+"/Documents/CV");
        if (!doc_I.exists())
            doc_I.mkdirs();
        if (!curriculum.exists())
            curriculum.mkdirs();
    }



    public Resource loadFileAsResource(String fileName) {
        try {
            String temp = "";
            if(!System.getProperty("user.dir").equals("\\"))
                temp = System.getProperty("user.dir");
            System.out.println(temp+ fileName);
            Path filePath = this.fileStorageLocation.resolve(temp + fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                System.out.println("file trovato");
                return resource;
            } else {
                System.out.println("file non esiste");
                throw new MyFileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new MyFileNotFoundException("File not found " + fileName, ex);
        }
    }

    public void deleteFile(String fileName) {
    	try {
    		Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
    		File file = new File(filePath.toString());
    		file.delete();
    	} catch (Exception e) {
    		throw new MyFileNotFoundException("File not found " + fileName, e);
    	}
    }
}
