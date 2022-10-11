package com.tomaswardle.williamsleacodetest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

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
        StringBuilder fileContents = new StringBuilder();

        try {
            fileStream = file.getInputStream();
            InputStreamReader reader = new InputStreamReader(fileStream, StandardCharsets.US_ASCII);
            BufferedReader breader = new BufferedReader(reader);
            
            while(true) {
                String line = breader.readLine();
                if (line == null) {
                    break;
                }
                
                fileContents.append(line);
                fileContents.append("\n");
            }
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
