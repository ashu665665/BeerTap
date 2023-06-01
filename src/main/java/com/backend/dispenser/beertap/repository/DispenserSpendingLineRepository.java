package com.backend.dispenser.beertap.repository;

import com.backend.dispenser.beertap.entity.DispenserSpendingLine;
import org.hibernate.annotations.NamedNativeQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DispenserSpendingLineRepository extends JpaRepository<DispenserSpendingLine,Long> {

    @Query(value="SELECT * FROM dispenser_spn_line WHERE dispenser_id = :id AND close_at IS NULL",nativeQuery = true )
    DispenserSpendingLine findByDispenserIdAndClosedAt(@Param("id") String id);

    @Query(value="SELECT * FROM dispenser_spn_line  WHERE dispenser_id = :dispenser_id",nativeQuery = true)
    List<DispenserSpendingLine> findAllByDispenserId(@Param("dispenser_id") String dispenser_id);
}
