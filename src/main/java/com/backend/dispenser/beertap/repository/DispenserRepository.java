package com.backend.dispenser.beertap.repository;

import com.backend.dispenser.beertap.entity.Dispenser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DispenserRepository extends JpaRepository<Dispenser, String> {

}
