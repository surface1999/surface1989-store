package com.surface1989.surface1989store.entity;


import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "manufacture")
public class Manufacture {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "manufactureId")
	private Long manufactureId;

	@Column(name = "manufactureName")
	@NotBlank(message = "Manufacture name is required!")
	private String manufactureName;

	@OneToMany(mappedBy = "productManufacture", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Product> list;

	public Manufacture() {
		super();
	}

	public Manufacture(Long manufactureId, String manufactureName) {
		super();
		this.manufactureId = manufactureId;
		this.manufactureName = manufactureName;
	}

	public Long getManufactureId() {
		return manufactureId;
	}

	public void setManufactureId(Long manufactureId) {
		this.manufactureId = manufactureId;
	}

	public String getManufactureName() {
		return manufactureName;
	}

	public void setManufactureName(String manufactureName) {
		this.manufactureName = manufactureName;
	}

	public List<Product> getList() {
		return list;
	}

	public void setList(List<Product> list) {
		this.list = list;
	}

}
