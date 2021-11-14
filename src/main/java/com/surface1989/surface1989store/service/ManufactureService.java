package com.surface1989.surface1989store.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.surface1989.surface1989store.entity.Manufacture;
import com.surface1989.surface1989store.repository.ManufactureRepository;

@Service
public class ManufactureService {
	@Autowired
	ManufactureRepository manufactureRepository;
	
	public List<Manufacture> getAllManufactures(){
		return manufactureRepository.findAll();
	}
	
	public Manufacture getManufactureById(Long manufactureId) {
		return manufactureRepository.findById(manufactureId)
				.orElseThrow(() -> new IllegalArgumentException("Invalid manufacture Id:" + manufactureId));
	}
}
