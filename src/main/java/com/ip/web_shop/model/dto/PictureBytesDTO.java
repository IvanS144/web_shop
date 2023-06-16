package com.ip.web_shop.model.dto;

import lombok.Data;

@Data
public class PictureBytesDTO {
    private byte[] bytes;
    private String contentType;
}
