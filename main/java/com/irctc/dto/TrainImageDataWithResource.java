package com.irctc.dto;

import com.irctc.entity.TrainImage;
import org.springframework.core.io.Resource;

public record TrainImageDataWithResource(
        TrainImage trainImage,
        Resource resource
) {

}
