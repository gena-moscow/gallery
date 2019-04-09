package org.genamoscow;

import org.genamoscow.model.Image;
import org.genamoscow.model.ImageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class GalleryRestController {

    @Autowired
    private ImageService imageService;

    @GetMapping(value = "/image/{imageId}")
    public Image getImage(@PathVariable("imageId") Long imageId) throws NotFoundException {
        return imageService.getImage(imageId);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/image")
    public ImageProperties addImage(
           @RequestParam(value="firstname", defaultValue="") String firstName,
           @RequestParam(value="middlename", defaultValue="") String middleName,
           @RequestParam(value="lastname", defaultValue="") String lastName,
           @RequestParam("image") MultipartFile file
    ) {
        byte[] bytes;
        try {
            bytes = file.getBytes();
        }
        catch (IOException e) {
            throw new HttpMessageNotReadableException("Read error file=" + file.getOriginalFilename());
        }

        Image image = imageService.saveImage(firstName, middleName, lastName, file.getOriginalFilename(), file.getSize(), bytes);
        return getProperties(image);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/images")
    public Page<ImageProperties> listImages(
            @RequestParam(value="page", defaultValue="0") int pageNumber,
            @RequestParam(value="page_size", defaultValue="50") int pageSize
    ) {
        return imageService.list(pageNumber, pageSize);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/image/{imageId}")
    public void deleteImage(@PathVariable("imageId") Long imageId) throws NotFoundException {
        imageService.deleteImage(imageId);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/image")
    public ImageProperties updateImage(@RequestBody Image image) throws NotFoundException {
        if (image.getId() == 0 )
            throw new NotFoundException(Image.class, "id", image.getId().toString());
        Image savedImage = imageService.saveImage(image);
        return getProperties(savedImage);
    }

    private ImageProperties getProperties(Image image) {
        return ImageProperties.builder()
                .firstName(image.getFirstName())
                .lastName(image.getLastName())
                .middleName(image.getMiddleName())
                .id(image.getId())
                .imageFileName(image.getImageFileName())
                .imageLength(image.getImageLength())
                .build();
    }
}
