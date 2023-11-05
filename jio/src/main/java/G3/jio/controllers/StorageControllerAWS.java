package G3.jio.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import G3.jio.services.StorageServiceAWS;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/v1/file")
public class StorageControllerAWS {

    private StorageServiceAWS storageServiceAWS;

    @Operation (summary = "Uploads file")
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam(value = "file") MultipartFile file) {
        return new ResponseEntity<>(storageServiceAWS.uploadFile(file), HttpStatus.OK);
    }

    @Operation (summary = "Downloads file by file name")
    @GetMapping("/download/{fileName}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable String fileName) {
        byte[] data = storageServiceAWS.downloadFile(fileName);
        ByteArrayResource resource = new ByteArrayResource(data);
        return ResponseEntity
                .ok()
                .contentLength(data.length)
                .header("Content-type", "application/octet-stream")
                .header("Content-disposition", "attachment; filename=\"" + fileName + "\"")
                .body(resource);
    }

    @Operation (summary = "Delete file by file name")
    @DeleteMapping("/delete/{fileName}")
    public ResponseEntity<String> deleteFile(@PathVariable String fileName) {
        return new ResponseEntity<>(storageServiceAWS.deleteFile(fileName), HttpStatus.OK);
    }
}