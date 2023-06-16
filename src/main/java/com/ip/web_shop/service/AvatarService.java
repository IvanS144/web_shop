package com.ip.web_shop.service;

import com.ip.web_shop.model.dto.PictureBytesDTO;
import org.springframework.web.multipart.MultipartFile;

public interface AvatarService {
    int add(MultipartFile pictureFile, int userId);

    PictureBytesDTO getBytesById(int id);
}
