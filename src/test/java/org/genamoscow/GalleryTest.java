package org.genamoscow;

import org.apache.commons.io.IOUtils;
import org.genamoscow.model.Image;
import org.genamoscow.model.ImageProperties;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class GalleryTest {

    private ImageService imageService;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private ImageSimpleRepository simpleRepository;

    @PostConstruct
    void init() {
        imageService = new ImageService(imageRepository, simpleRepository);
    }

    @Test
    public void saveImage() throws URISyntaxException, IOException, NotFoundException {
        Image image = getImageFromFile(new URI("/gena2low.jpg"));
        Image savedImage = imageService.saveImage(image);
        Image getImage = imageService.getImage(savedImage.getId());
        assertArrayEquals(savedImage.getImageData(), getImage.getImageData());
        image = getImageFromFile(new URI("/tania.jpg"));
        image.setFirstName("guga2");
        image.setMiddleName("gaga2");
        image.setLastName("giga2");
        savedImage = imageService.saveImage(image);
        getImage = imageService.getImage(savedImage.getId());
        assertArrayEquals(savedImage.getImageData(), getImage.getImageData());

        int pageSize = 2;
        for (int i = 0; ; i++) {
            Page<ImageProperties> list = imageService.list(i, pageSize);
            if (list.getNumberOfElements() == 0)
                break;
            assertEquals(list.getTotalElements(), 2);
        }

        image.setFirstName("savedFirstName");
        long oldId = image.getId();
        savedImage = imageService.saveImage(image);
        assertEquals(oldId, (long)savedImage.getId());

        savedImage = imageService.getImage(savedImage.getId());
        assertEquals("savedFirstName", savedImage.getFirstName());

        imageService.deleteImage(savedImage.getId());
        Page<ImageProperties> list = imageService.list(0, 50);
        assertEquals(list.getNumberOfElements(), 1);
        assertEquals((long)list.getContent().get(0).getId(), 1L);


    }

    private Image getImageFromFile(URI fileName) throws IOException {
        try (InputStream is = getClass().getResourceAsStream(fileName.getRawPath())) {
            byte[] bytes = IOUtils.toByteArray(is);
            Image image = Image.builder()
                    .firstName("guga" + fileName.toString())
                    .middleName("gaga" + fileName.toString())
                    .lastName("giga" + fileName.toString())
                    .imageData(bytes)
                    .imageFileName(fileName.toString())
                    .imageLength(bytes.length)
                    .build();
            return image;
        }
    }

}
