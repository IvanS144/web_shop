package com.ip.web_shop.service;

import com.ip.web_shop.model.dto.PictureBytesDTO;
import org.springframework.web.multipart.MultipartFile;

public interface PictureService {
    int add(MultipartFile pictureFile, int offerId);

    PictureBytesDTO getBytesById(int id);
}
