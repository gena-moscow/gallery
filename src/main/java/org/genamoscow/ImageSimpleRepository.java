package org.genamoscow;

import org.genamoscow.model.ImageProperties;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ImageSimpleRepository extends PagingAndSortingRepository<ImageProperties, Long> {
}
