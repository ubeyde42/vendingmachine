package com.ubeyde.sample.repository;

import com.ubeyde.sample.entity.Machine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MachineRepository extends JpaRepository<Machine, Long> {

}
