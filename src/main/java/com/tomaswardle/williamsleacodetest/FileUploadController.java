package com.tomaswardle.williamsleacodetest;

import java.io.IOException;
import java.io.InputStream;
import java.nio.Buffer;

import javax.tools.DocumentationTool.Location;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileUploadController {
    
	@PostMapping("/upload")
	public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
        InputStream fileStream = null; 
        StringBuilder fileContents = new StringBuilder("");
        byte[] content = new byte[10];
        try {
            fileStream = file.getInputStream();
            
            while(fileStream.read(content) != -1) {
                fileContents.append(content);
            }
            fileStream.read();
        } catch(IOException ex) {
            return new ResponseEntity<String>("Error reading file.",HttpStatus.BAD_REQUEST);
        } finally {
            if(fileStream != null) {
                try {
                    fileStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
        return new ResponseEntity<String>(fileContents.toString(),HttpStatus.OK);
	}
}
