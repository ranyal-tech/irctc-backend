package com.irctc.repository;

import com.irctc.entity.Station;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StationRepo extends JpaRepository<Station,Long> {
}
