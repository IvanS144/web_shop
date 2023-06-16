package com.ip.web_shop.service;

import com.ip.web_shop.exceptions.ConflictException;
import com.ip.web_shop.exceptions.HttpException;
import com.ip.web_shop.exceptions.NotFoundException;
import com.ip.web_shop.model.Avatar;
import com.ip.web_shop.model.User;
import com.ip.web_shop.model.dto.PictureBytesDTO;
import com.ip.web_shop.repository.AvatarRepository;
import com.ip.web_shop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
@Transactional
public class AvatarServiceImpl implements AvatarService {
    private final AvatarRepository avatarRepository;
    private final UserRepository userRepository;
    @Value("${avatars.directory.path}")
    private String avatarsFolderPath;

    public AvatarServiceImpl(AvatarRepository avatarRepository, UserRepository userRepository) {
        this.avatarRepository = avatarRepository;
        this.userRepository = userRepository;
    }

    @Override
    public int add(MultipartFile pictureFile, int userId) {
        try {
            User u = userRepository.findByIdIncludeAvatar(userId).orElseThrow(() -> new NotFoundException("User with id " + userId + " not found"));
            if (u.getAvatar() == null)
                throw new ConflictException("User with id " + userId + " already has an avatar");
            Avatar avatar = new Avatar();
            avatar.setAvatarId(null);
            String fileName = Paths.get(pictureFile.getOriginalFilename()).getFileName().toString();
            Files.write(Paths.get(avatarsFolderPath).resolve(fileName), pictureFile.getBytes());
            //avatar.setBytes(pictureFile.getBytes());
            avatar.setFileName(fileName);
            avatar.setContent_type(pictureFile.getContentType());
            u.setAvatar(avatar);
            avatar = avatarRepository.saveAndFlush(avatar);
            return avatar.getAvatarId();
        } catch (IOException e) {
            throw new HttpException(500, "An error occurred");
        }
    }

    @Override
    public PictureBytesDTO getBytesById(int id){
        Avatar avatar = avatarRepository.findById(id).orElseThrow(()-> new NotFoundException("Picture not found"));
        PictureBytesDTO bytesDTO = new PictureBytesDTO();
        try {
            byte[] bytes = Files.readAllBytes(Paths.get(avatarsFolderPath).resolve(avatar.getFileName()));
            bytesDTO.setBytes(bytes);
            bytesDTO.setContentType(avatar.getContent_type());
            return bytesDTO;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

//    public void changeAvatar(MultipartFile pictureFile, int userId){
//        try {
//            User u = userRepository.findByIdIncludeAvatar(userId).orElseThrow(() -> new NotFoundException("User with id " + userId + " not found"));
//            if (u.getAvatar() == null)
//                throw new ConflictException("User with id " + userId + " has no avatar yet");
//            Avatar avatar = u.getAvatar();
//            avatar.setBytes(pictureFile.getBytes());
//            avatarRepository.saveAndFlush(avatar);
//        } catch (IOException e) {
//            throw new HttpException(500, "An error occurred");
//        }
//    }

//    public void deleteAvatar(int avatarId){
//        if(!avatarRepository.existsById(avatarId))
//            throw new NotFoundException("Avatar with id "+ avatarId + " not found");
//        avatarRepository.deleteById(avatarId);
//    }
}
