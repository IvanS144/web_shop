package com.ip.web_shop.service;

import com.ip.web_shop.model.dto.DownloadFileDTO;
import org.springframework.core.io.Resource;

import java.time.LocalDate;
import java.util.List;

public interface LogFilesService {
    DownloadFileDTO getLogByNameAsResource(String logFileName);
    DownloadFileDTO getCurrentLogFile();
    List<String> getAllLogFileUrls();
    List<String> getLogFileUrlsByDate(LocalDate localDate);
}
