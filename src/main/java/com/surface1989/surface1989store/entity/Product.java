package com.surface1989.surface1989store.entity;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Table(name = "Product")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "productId")
	private Long productId;

	@Column(name = "productName")
	@NotBlank(message = "Product name is required!")
	private String productName;

	@ManyToOne
	@JoinColumn(name = "manufactureId")
	private Manufacture productManufacture;

	@Column(name = "price")
	@NotNull(message = "Price is required!")
	private Long productPrice;
	
	@Column(name="quantity")
	@NotNull(message = "Quantity is required!")
	private Long productQuantity;
	
	@Column(name="os")
	@NotBlank(message = "OS is required!")
	private String productOS;
	
	@Column(name="ram")
	@NotNull(message = "ram is required!")
	private int productRam;
	
	@Column(name="rom")
	@NotNull(message = "ROM is required!")
	private int productRom;
	
	@Transient
	private MultipartFile productImage;
	public MultipartFile getProductImage() {
		return productImage;
	}

	public void setProductImage(MultipartFile productImage) {
		this.productImage = productImage;
	}

	public Product() {
		super();
	}

	public Product(@NotBlank(message = "Product name is required!") String productName, Manufacture productManufacture,
			@NotNull(message = "Price is required!") Long productPrice,
			@NotNull(message = "Quantity is required!") Long productQuantity,
			@NotBlank(message = "OS is required!") String productOS,
			@NotNull(message = "ram is required!") int productRam,
			@NotNull(message = "ROM is required!") int productRom, MultipartFile productImage) {
		super();
		this.productName = productName;
		this.productManufacture = productManufacture;
		this.productPrice = productPrice;
		this.productQuantity = productQuantity;
		this.productOS = productOS;
		this.productRam = productRam;
		this.productRom = productRom;
		this.productImage = productImage;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Manufacture getProductManufacture() {
		return productManufacture;
	}

	public void setProductManufacture(Manufacture productManufacture) {
		this.productManufacture = productManufacture;
	}

	public Long getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(Long productPrice) {
		this.productPrice = productPrice;
	}

	public Long getProductQuantity() {
		return productQuantity;
	}

	public void setProductQuantity(Long productQuantity) {
		this.productQuantity = productQuantity;
	}

	public String getProductOS() {
		return productOS;
	}

	public void setProductOS(String productOS) {
		this.productOS = productOS;
	}

	public int getProductRam() {
		return productRam;
	}

	public void setProductRam(int productRam) {
		this.productRam = productRam;
	}

	public int getProductRom() {
		return productRom;
	}

	public void setProductRom(int productRom) {
		this.productRom = productRom;
	}

	
}
