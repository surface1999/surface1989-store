package com.surface1989.surface1989store.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.surface1989.surface1989store.entity.Product;
import com.surface1989.surface1989store.repository.ProductRepository;

@Service
public class ProductService {
	@Autowired
	ProductRepository productRepository;

	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	public Product getProductById(Long productId) {
		return productRepository.findById(productId)
				.orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + productId));

	}

	public void addProduct(Product product) {
		productRepository.save(product);
	}
	public void updateProduct( Long productId, Product product) {
		Optional<Product> rs =  productRepository.findById(productId);
		if(rs.isPresent()) {
			product.setProductId(productId);
			productRepository.save(product);
		}
	}

	public void deleteProduct(Long productId) {
		Product product = productRepository.getById(productId);
		if (product != null)
			productRepository.delete(product);
	}
	
	public Page<Product> findProduct(String key){
		List<Product> products = productRepository.findAll();
		List<Product> rs = products.stream()
				.filter(product -> (product.getProductManufacture().getManufactureName() + ' ' + product.getProductName()).toLowerCase()
						.indexOf(key.toLowerCase()) != -1)
				.collect(Collectors.toList());
		Page<Product> list = new PageImpl(rs);
		return list;
	}
	
	public Page<Product> findAllByPage(int page){
		Pageable pageable = (Pageable) PageRequest.of(page, 4, Sort.by(Sort.Direction.DESC, "productId"));
		return productRepository.findAll(pageable);
	}
	
	public Page<Product> findAllByKey(String key, int page){
		Pageable pageable = (Pageable) PageRequest.of(page, 4);
		return productRepository.findProductsByKey(key, pageable);
	}
	
}
