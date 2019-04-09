package org.genamoscow;

import org.genamoscow.model.Image;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface ImageRepository extends PagingAndSortingRepository<Image, Long> {
}
