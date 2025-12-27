package com.irctc.repository;

import com.irctc.entity.Train;
import com.irctc.entity.TrainRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TrainRouteRepository extends JpaRepository<TrainRoute,Long> {

    @Query("Select tr from TrainRoute tr where tr.train.id=?1 order by tr.stationOrder")
    List<TrainRoute> findByTrain(Long trainId);
}
