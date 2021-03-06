package com.PixelWar.repository;

import com.PixelWar.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
    Image findByDataImg(String dataImg);
}
