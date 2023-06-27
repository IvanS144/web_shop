package com.ip.web_shop.service;

import com.ip.web_shop.model.dto.DownloadFileDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

@Service
public class LogFilesServiceImpl implements LogFilesService{
    @Value("${logs.archive.path}")
    private String archiveFolderPathString;

    @Value("${logs.directory.path}")
    private String logsFolderPath;

    @Value("${logs.currentLogFile.name}")
    private String currentLogFileName;

    @Value("${server.port}")
    private Integer port;
    @Override
    public DownloadFileDTO getLogByNameAsResource(String logFileName) {
        try {
            Path baseFolderPath = Paths.get(archiveFolderPathString);
            Path path = baseFolderPath.resolve(logFileName);
            byte[] bytes = Files.readAllBytes(path);
            DownloadFileDTO dto = new DownloadFileDTO();
            dto.setResource(new ByteArrayResource(bytes));
            dto.setFileName(logFileName);
            dto.setContentType("text/plain");
            return dto;
        }
        catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public DownloadFileDTO getCurrentLogFile() {
        try {
            Path baseFolderPath = Paths.get(logsFolderPath);
            Path path = baseFolderPath.resolve(currentLogFileName);
            byte[] bytes = Files.readAllBytes(path);
            DownloadFileDTO dto = new DownloadFileDTO();
            dto.setResource(new ByteArrayResource(bytes));
            dto.setFileName(currentLogFileName);
            dto.setContentType("text/plain");
            return dto;
        }
        catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<String> getAllLogFileUrls() {
        Path archivePath = Paths.get(archiveFolderPathString);
        try(Stream<Path> pathsStream = Files.list(archivePath)){
            List<String> urlList = pathsStream.map(Path::getFileName).map(Path::toString).map(fileName -> "http://localhost:"+port+"/logs/archive/"+fileName).toList();
            return urlList;
        }
        catch(Exception e){
            return List.of();
        }
    }

    @Override
    public List<String> getLogFileUrlsByDate(LocalDate localDate) {
        String dateString = localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        Path archivePath = Paths.get(archiveFolderPathString);
        try(Stream<Path> pathsStream = Files.list(archivePath)){
            List<String> urlList = pathsStream.map(Path::getFileName).map(Path::toString).filter(fileName -> fileName.contains(dateString)).map(fileName -> "http://localhost:"+port+"/logs/archive/"+fileName).toList();
            return urlList;
        }
        catch(Exception e){
            return List.of();
        }

    }
}
