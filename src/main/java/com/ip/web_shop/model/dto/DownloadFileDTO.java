package com.ip.web_shop.model.dto;

import lombok.Data;
import org.springframework.core.io.Resource;
@Data
public class DownloadFileDTO {
    private Resource resource;
    private String fileName;
    private String contentType;
}
