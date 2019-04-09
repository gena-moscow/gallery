package org.genamoscow;

import org.genamoscow.model.Image;
import org.genamoscow.model.ImageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ImageService {
    private final ImageSimpleRepository simpleRepository;
    private final ImageRepository imageRepository;

    @Autowired
    public ImageService(ImageRepository imageRepository, ImageSimpleRepository simpleRepository) {
        this.imageRepository = imageRepository;
        this.simpleRepository = simpleRepository;
    }

    public Image getImage(Long imageId) throws NotFoundException {
        Image image = imageRepository.findOne(imageId);
        if (image == null) {
            throw new NotFoundException(Image.class, "id", imageId.toString());
        }
        return image;
    }

    public void deleteImage(Long imageId) throws NotFoundException {
        try {
            imageRepository.delete(imageId);
        }
        catch (EmptyResultDataAccessException e) {
            throw new NotFoundException(Image.class, "id", imageId.toString());
        }
    }

    public Image saveImage(Image image) {
        try {
            return imageRepository.save(image);
        }
        catch (Exception e) {
            throw new DataIntegrityViolationException("DB save error", e);
        }
    }

    public Image saveImage(String firstName, String middleName, String lastName, String fileName, long fileSize, byte[] bytes) {
        Image image = Image.builder()
                .firstName(firstName)
                .middleName(middleName)
                .lastName(lastName)
                .imageData(bytes)
                .imageFileName(fileName)
                .imageLength(fileSize)
                .build();
        return saveImage(image);
    }

    public Page<ImageProperties> list(int pageNumber, int pageSize) {
        try {
            Page<ImageProperties> list = simpleRepository.findAll(new PageRequest(pageNumber, pageSize));
            return list;
        }
        catch (Exception e) {
            throw new DataIntegrityViolationException("DB list error", e);
        }
    }

}
