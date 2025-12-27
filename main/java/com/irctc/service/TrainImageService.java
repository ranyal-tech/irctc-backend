package com.irctc.service;

import com.irctc.dto.TrainImageDataWithResource;
import com.irctc.dto.TrainImageResponse;
import com.irctc.entity.Train;
import com.irctc.entity.TrainImage;
import com.irctc.exceptions.ResourceNotFoundException;
import com.irctc.repository.TrainImageRepository;
import com.irctc.repository.TrainRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class TrainImageService {

    private static final Logger log = LoggerFactory.getLogger(TrainImageService.class);

    @Autowired
    private TrainImageRepository trainImageRepository;

    @Autowired
    private TrainRespository trainRespository;

    @Value("${train.image.folder.path}")
    private String folderPath;

    public TrainImageResponse upload(MultipartFile file, Long trainNo) throws IOException {

        Train train = trainRespository.findById(trainNo)
                .orElseThrow(() -> new ResourceNotFoundException("Train not found"));

        String fullFilePath = null;

        try {

            if (!Files.exists(Paths.get(folderPath))) {
                Files.createDirectories(Paths.get(folderPath));
                System.out.println("Created");
            }

            System.out.println("folderPath: " + folderPath);

            if (folderPath == null || folderPath.trim().isEmpty()) {
                log.error("train.image.folder.path is not set or empty!");
                throw new IllegalStateException("train.image.folder.path is not configured");
            }

            fullFilePath = folderPath + UUID.randomUUID() + "_" + file.getOriginalFilename();
            Files.copy(file.getInputStream(), Paths.get(fullFilePath), StandardCopyOption.REPLACE_EXISTING);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;  // important — so upload failure is not silently ignored
        }

        TrainImage trainImage = new TrainImage();
        trainImage.setFileName(fullFilePath);
        trainImage.setFileType(file.getContentType());
        trainImage.setSize(file.getSize());

        train.setTrainImage(trainImage);
        trainImage.setTrain(train);

        Train savedTrain = trainRespository.save(train);

        return TrainImageResponse.from(savedTrain.getTrainImage(), "http://localhost:8080",trainNo);
    }

    public TrainImageDataWithResource loadImageByTrainNo(Long trainNo) throws MalformedURLException {
        Train train = trainRespository.findById(trainNo).orElseThrow(() -> new ResourceNotFoundException("Train not found"));
        TrainImage trainImage = train.getTrainImage();

        if (trainImage == null) {
            throw new ResourceNotFoundException("Image not found");
        }

      Path path=  Paths.get(trainImage.getFileName());

        if(!Files.exists(path)){
            throw new ResourceNotFoundException("Image not found");
        }

       UrlResource urlResource= new UrlResource(path.toUri());
        TrainImageDataWithResource trainImageDataWithResource=new TrainImageDataWithResource(trainImage,urlResource);
        return trainImageDataWithResource;
    }

}
