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
        LineProcessor lProc = new LineProcessor();
        int count = 0;

        //try with statement creates bufferedReader from filestream
        try (InputStream fileStream = file.getInputStream();
            InputStreamReader reader = new InputStreamReader(fileStream, StandardCharsets.US_ASCII);
            BufferedReader breader = new BufferedReader(reader)){

            
            //reads lines in until end of file.
            while(true) {
                String line = breader.readLine();
                if (line == null) {
                    break;
                }
                
                lProc.processLine(line);
            }
            count = lProc.close();
        } catch(IOException ex) {
            return new ResponseEntity<>("Error reading file.",HttpStatus.BAD_REQUEST);
        }
        
        return new ResponseEntity<>("Records Read: " + count,HttpStatus.OK);
	}
}
