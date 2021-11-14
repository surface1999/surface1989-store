package com.surface1989.surface1989store.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.surface1989.surface1989store.entity.Manufacture;
import com.surface1989.surface1989store.entity.Product;
import com.surface1989.surface1989store.service.ManufactureService;
import com.surface1989.surface1989store.service.ProductService;

@Controller
@RequestMapping("/admin")
public class ProductController {

	@Autowired
	private ProductService productService;
	@Autowired
	private ManufactureService manufactureService;

	@GetMapping({ "", "/products" })
	public String home(Model model, @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
			HttpServletRequest request) throws InterruptedException {
		Page<Product> products = productService.findAllByPage(page);
		System.out.println(products.getNumber() + 1);
		ArrayList<Manufacture> manufactures = (ArrayList<Manufacture>) manufactureService.getAllManufactures();
		model.addAttribute("products", products);
		model.addAttribute("manufactures", manufactures);
		String previousUrl = request.getHeader("referer");
		if (previousUrl != null) {
			String status = previousUrl.indexOf("add") != -1 ? "created"
					: previousUrl.indexOf("edit") != -1 ? "updated"
							: previousUrl.indexOf("delete") != -1 ? "deleted" : "";
			if (status != "") {
				model.addAttribute("status", status);
			}
		}
		return "admin/listProducts";
	}

	@GetMapping("/products/add")
	public String createProduct(Model model) {
		Product product = new Product();
		product.setProductManufacture(new Manufacture());
		ArrayList<Manufacture> manufactures = (ArrayList<Manufacture>) manufactureService.getAllManufactures();
		model.addAttribute("manufactures", manufactures);
		model.addAttribute("product", product);
		return "admin/createUpdateProduct";
	}

	@PostMapping("/products/add")
	public String addProduct(@Valid @ModelAttribute("product") Product product, BindingResult br, Model model,
			HttpServletRequest request) throws InterruptedException {
		if (br.hasErrors()) {
			ArrayList<Manufacture> manufactures = (ArrayList<Manufacture>) manufactureService.getAllManufactures();
			model.addAttribute("manufactures", manufactures);
			/* model.addAttribute("product", product); */
			return "admin/createUpdateProduct";
		}
		try {
			Long manufactureId = product.getProductManufacture().getManufactureId();
			Manufacture manufacture = manufactureService.getManufactureById(manufactureId);
			product.setProductManufacture(manufacture);
			productService.addProduct(product);
			MultipartFile productImage = product.getProductImage();
			String rootDirectory = request.getSession().getServletContext().getRealPath("/resources");
			String path = Paths.get(rootDirectory + "\\static\\images\\" + product.getProductId() + ".png").toString()
					.replace("\\webapp", "");
			System.out.println(path.toString());
			if (productImage != null && !productImage.isEmpty()) {
				try {
					productImage.transferTo(new File(path));
				} catch (Exception e) {
					e.printStackTrace();
					throw new RuntimeException("Product image saving failed", e);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Thread.sleep(5000);
		return "redirect:/admin/products";
	}

	@GetMapping("/products/edit")
	public String createProduct(@RequestParam("productId") Long productId, Model model) {
		Product product = productService.getProductById(productId);
		ArrayList<Manufacture> manufactures = (ArrayList<Manufacture>) manufactureService.getAllManufactures();
		model.addAttribute("manufactures", manufactures);
		model.addAttribute("product", product);
		model.addAttribute("isUpdate", true);
		return "admin/createUpdateProduct";
	}

	@PostMapping("/products/edit")
	public String editProduct(@RequestParam("productId") Long productId, @Valid Product product, BindingResult br,
			Model model, HttpServletRequest request) throws InterruptedException {
		if (br.hasErrors()) {
			ArrayList<Manufacture> manufactures = (ArrayList<Manufacture>) manufactureService.getAllManufactures();
			model.addAttribute("manufactures", manufactures);
			/* model.addAttribute("product", product); */
			model.addAttribute("isUpdate", true);
			return "admin/createUpdateProduct";
		}
		try {
			Long manufactureId = product.getProductManufacture().getManufactureId();
			Manufacture manufacture = manufactureService.getManufactureById(manufactureId);
			product.setProductManufacture(manufacture);
			productService.updateProduct(productId, product);
			MultipartFile productImage = product.getProductImage();
			String rootDirectory = request.getSession().getServletContext().getRealPath("/resources");
			String path = Paths.get(rootDirectory + "\\static\\images\\" + productId + ".png").toString()
					.replace("\\webapp", "");
			System.out.println(path.toString());
			if (productImage != null && !productImage.isEmpty()) {
				try {
					productImage.transferTo(new File(path));
				} catch (Exception e) {
					e.printStackTrace();
					throw new RuntimeException("Product image saving failed", e);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/admin/products";
	}

	@GetMapping("/products/delete/{id}")
	public String deleteProduct(@PathVariable("id") Long productId, HttpServletRequest request) {
		String rootDirectory = request.getSession().getServletContext().getRealPath("/resources");
		Path path = Paths.get((rootDirectory + "\\static\\images\\" + productId + ".png").replace("\\webapp", ""));
		if (Files.exists(path)) {
			try {
				Files.delete(path);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		productService.deleteProduct(productId);
		return "redirect:/admin/products";
	}

	@GetMapping("/products/search")
	public String findProduct(@RequestParam(name = "key", defaultValue = "") String key,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page, Model model) {
		ArrayList<Manufacture> manufactures = (ArrayList<Manufacture>) manufactureService.getAllManufactures();
		Page<Product> products = productService.findAllByKey(key, page);
		model.addAttribute("products", products);
		model.addAttribute("manufactures", manufactures);
		model.addAttribute("key", key);
		return "admin/listProducts";
	}
}
