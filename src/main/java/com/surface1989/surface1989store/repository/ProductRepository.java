package com.surface1989.surface1989store.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.surface1989.surface1989store.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
	
	@Query(value = "SELECT * FROM product p INNER JOIN manufacture m ON p.manufacture_id = m.manufacture_id "
			+"WHERE LOWER(CONCAT(m.manufacture_name, \' \', p.product_name)) LIKE %?1% ", nativeQuery = true)
	Page<Product> findProductsByKey(String key, Pageable page);
}

