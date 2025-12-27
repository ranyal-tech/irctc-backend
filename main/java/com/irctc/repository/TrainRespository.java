package com.irctc.repository;

import com.irctc.entity.Train;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainRespository extends JpaRepository<Train,Long> {
}
