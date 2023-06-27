package com.ip.web_shop.controller;

import com.ip.web_shop.model.dto.DownloadFileDTO;
import com.ip.web_shop.service.LogFilesService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/logs")
@CrossOrigin("http://localhost:8080")
public class LogsController {
    private final LogFilesService logFilesService;

    public LogsController(LogFilesService logFilesService) {
        this.logFilesService = logFilesService;
    }

    @GetMapping("/current")
    ResponseEntity<Resource> getCurrentLogFile(){
        DownloadFileDTO fileToDownload = logFilesService.getCurrentLogFile();
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.parseMediaType(fileToDownload.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+fileToDownload.getFileName()+"\"")
                .body(fileToDownload.getResource());

    }

    @GetMapping("/archive/{fileName}")
    ResponseEntity<Resource> getLogFile(@PathVariable String fileName){
        DownloadFileDTO fileToDownload = logFilesService.getLogByNameAsResource(fileName);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.parseMediaType(fileToDownload.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+fileToDownload.getFileName()+"\"")
                .body(fileToDownload.getResource());

    }

    @GetMapping("/archive")
    ResponseEntity<List<String>> getUrls(@RequestParam(required = false) String dateString){
        List<String> urlList;
        if(dateString!=null){
            System.out.println(dateString);
            LocalDate localDate = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            urlList =  logFilesService.getLogFileUrlsByDate(localDate);
        }
        else{
            urlList =  logFilesService.getAllLogFileUrls();
        }
        return ResponseEntity.status(HttpStatus.OK).body(urlList);
    }

}
