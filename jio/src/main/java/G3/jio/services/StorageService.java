package G3.jio.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;


import G3.jio.entities.ImageData;
import G3.jio.entities.ImageUtils;
import G3.jio.entities.Organiser;
import G3.jio.exceptions.EventNotFoundException;
import G3.jio.exceptions.ImageNotFoundException;
import G3.jio.entities.Event;
import G3.jio.entities.FileData;

import G3.jio.repositories.FileDataRepository;
import G3.jio.repositories.StorageRepository;

import jakarta.servlet.ServletContext;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.nio.file.*; 

@Service

public class StorageService {
    // private final ServletContext context;
    // private final String FOLDER_PATH;
    // @Value("${webcontent.path}")
    // private String webcontentPath;
    // @Autowired
    // public StorageService(ServletContext context) {
    //     this.context = context;
    //     this.FOLDER_PATH = context.getRealPath("jio/G3/java/main/resources/Images");
    // }
    // private final ResourceLoader resourceLoader;
    // private String FOLDER_PATH  ="";
    // @Autowired
    // public StorageService(ResourceLoader resourceLoader) {
    //     this.resourceLoader = resourceLoader;
        
    //     }
    //     public void app(){
    //     // Get the current working directory (the location of your App.java file)
    //     String currentDirectory = System.getProperty("user.dir");

    //     // Construct a relative path to your image folder
    //     String relativePath = currentDirectory+ File.separator + "jio" + File.separator + "src" + File.separator +
    //             "main" + File.separator + "resources" + File.separator + "Images";

    //     // Create a Resource object from the relative path
    //     Resource resource = resourceLoader.getResource("file:" + relativePath);
    //     try{
    //     this.FOLDER_PATH =resource.getURL().toString();
    //     } catch (IOException e) {
    //     // Handle the exception here
    //     e.printStackTrace();}
    
    //     // Now you can work with the resource as needed
    //     // For example, you can get the file or directory path using resource.getFile()
    //     }
    @Value("${file.upload.directory}")
    private String FOLDER_PATH;

    @Autowired
    private StorageRepository repository;

    @Autowired
    private FileDataRepository fileDataRepository;

    

    //uploads image
    public String uploadImage(MultipartFile file) throws IOException {
        ImageData imageData = repository.save(ImageData.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .imageData(ImageUtils.compressImage(file.getBytes())).build());
        if (imageData != null) {
            return "file uploaded successfully : " + file.getOriginalFilename();
        }
        return null;
    }


    //downloads image
    public byte[] downloadImage(String fileName) {
        Optional<ImageData> dbImageData = repository.findByName(fileName);
        byte[] images = ImageUtils.decompressImage(dbImageData.get().getImageData());
        return images;
    }


    //uploads image to file system
    public Long uploadImageToFileSystem(MultipartFile file) throws IOException {
        String filePath=FOLDER_PATH+file.getOriginalFilename();
        
        FileData fileData=fileDataRepository.save(FileData.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .filePath(filePath).build());

        file.transferTo(new File(filePath));
        Long fileLong = fileData.getId();
        if (fileData != null) {
            //return "file uploaded successfully : " + filePath;
            return fileLong;
        }
        return null;
    }


    //donwloads image from file system searching by filename
    public byte[] downloadImageFromFileSystem(String fileName) throws IOException {

        Optional<FileData> fileData = fileDataRepository.findByName(fileName);
        String filePath=fileData.get().getFilePath();
        byte[] images = Files.readAllBytes(new File(filePath).toPath());
        return images;

    }


    
    //donwloads image from file system searching by id
    public byte[] downloadImageFromFileSystembyId(Long id) throws IOException {
        Optional<FileData> fileData = fileDataRepository.findById(id);
        String filePath=fileData.get().getFilePath();
        byte[] images = Files.readAllBytes(new File(filePath).toPath());
        return images;
    }


    //deletes image from file system searching by id
    public void deleteImage(Long id)throws IOException {
        if (!fileDataRepository.existsById(id)) {
            throw new ImageNotFoundException(id);
        }
        Optional<FileData> file = fileDataRepository.findById(id);
        String filePath=file.get().getFilePath();


        Files.delete(new File(filePath).toPath());
        fileDataRepository.deleteById(id);
    }
}