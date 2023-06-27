package com.ip.web_shop.service;

import com.ip.web_shop.exceptions.HttpException;
import com.ip.web_shop.exceptions.NotFoundException;
import com.ip.web_shop.model.Offer;
import com.ip.web_shop.model.Picture;
import com.ip.web_shop.model.dto.PictureBytesDTO;
import com.ip.web_shop.repository.OfferRepository;
import com.ip.web_shop.repository.PictureRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@Transactional
public class PictureServiceImpl implements PictureService {
    private final PictureRepository pictureRepository;
    private final OfferRepository offerRepository;
    @Value("${pictures.directory.path}")
    private String picturesFolderPath;

    public PictureServiceImpl(PictureRepository pictureRepository, OfferRepository offerRepository) {
        this.pictureRepository = pictureRepository;
        this.offerRepository = offerRepository;
    }

    @Override
    public void add(MultipartFile pictureFile, int offerId) {
        try {
            Offer offer = offerRepository.findById(offerId).orElseThrow(() -> new NotFoundException("Offer with id " + offerId + " not found"));
            Picture picture = new Picture();
            picture.setPictureId(null);
            String fileName = Paths.get(pictureFile.getOriginalFilename()).getFileName().toString();
            String timestampPrefix = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_"));
            Files.write(Paths.get(picturesFolderPath).resolve(timestampPrefix+fileName), pictureFile.getBytes());
            //picture.setBytes(pictureFile.getBytes());
            picture.setFileName(timestampPrefix+fileName);
            picture.setContent_type(pictureFile.getContentType());
            picture.setOffer(offer);
            offer.getPictures().add(picture);
            picture = pictureRepository.saveAndFlush(picture);
            //return picture.getPictureId();
        } catch (IOException e) {
            e.printStackTrace();
            throw new HttpException(500, "An error occurred");
        }

    }

    @Override
    public void add(MultipartFile[] pictureFiles, int offerId) {
        for (MultipartFile pictureFile : pictureFiles) {
            add(pictureFile, offerId);
        }
    }

    @Override
    public PictureBytesDTO getBytesById(int id) {
        Picture picture = pictureRepository.findById(id).orElseThrow(() -> new NotFoundException("Picture not found"));
        PictureBytesDTO bytesDTO = new PictureBytesDTO();
        try {
            byte[] bytes = Files.readAllBytes(Paths.get(picturesFolderPath).resolve(picture.getFileName()));
            bytesDTO.setBytes(bytes);
            bytesDTO.setContentType(picture.getContent_type());
            return bytesDTO;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
