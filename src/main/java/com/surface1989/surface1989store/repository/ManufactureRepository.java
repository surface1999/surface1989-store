package com.surface1989.surface1989store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.surface1989.surface1989store.entity.Manufacture;

@Repository
public interface ManufactureRepository extends JpaRepository<Manufacture, Long>  {

}
