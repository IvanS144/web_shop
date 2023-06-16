package com.ip.web_shop.controller;

import com.ip.web_shop.model.dto.PictureBytesDTO;
import com.ip.web_shop.service.AvatarService;
import com.ip.web_shop.service.PictureService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/files")
@CrossOrigin(origins = "http://localhost:4200")
public class StaticImageController {
    private final PictureService pictureService;
    private final AvatarService avatarService;

    public StaticImageController(PictureService pictureService, AvatarService avatarService) {
        this.pictureService = pictureService;
        this.avatarService = avatarService;
    }

    @GetMapping("/pictures/{id}")
    public ResponseEntity<byte[]> getPictureById(@PathVariable Integer id){
        PictureBytesDTO bytesDTO = pictureService.getBytesById(id);
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(bytesDTO.getContentType())).body(bytesDTO.getBytes());
    }

    @GetMapping("/avatars/{id}")
    public ResponseEntity<byte[]> getAvatarById(@PathVariable Integer id){
        PictureBytesDTO bytesDTO = avatarService.getBytesById(id);
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(bytesDTO.getContentType())).body(bytesDTO.getBytes());

    }

    @PostMapping(value="/pictures/offers/{id}", consumes= MediaType.MULTIPART_FORM_DATA_VALUE)
    public void addPicture(@RequestPart("file")MultipartFile multipartFile, @PathVariable Integer id){
        pictureService.add(multipartFile, id);
    }

    @PostMapping("/avatars/users/{id}")
    public void addAvatar(@RequestParam("file")MultipartFile multipartFile, @PathVariable Integer id){
        avatarService.add(multipartFile, id);
    }
}
