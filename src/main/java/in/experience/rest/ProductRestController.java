/*
 * package in.experience.rest;
 * 
 * import java.util.Collection; import java.util.List; import
 * java.util.Optional;
 * 
 * import org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.http.MediaType; import
 * org.springframework.web.bind.annotation.CrossOrigin; import
 * org.springframework.web.bind.annotation.DeleteMapping; import
 * org.springframework.web.bind.annotation.GetMapping; import
 * org.springframework.web.bind.annotation.PathVariable; import
 * org.springframework.web.bind.annotation.PostMapping; import
 * org.springframework.web.bind.annotation.PutMapping; import
 * org.springframework.web.bind.annotation.RequestBody; import
 * org.springframework.web.bind.annotation.RequestMapping; import
 * org.springframework.web.bind.annotation.RequestParam; import
 * org.springframework.web.bind.annotation.RestController;
 * 
 * import in.experience.model.Product; import
 * in.experience.model.ProductCategory; import in.experience.repo.IProduct;
 * import in.experience.repo.IProductCategory; import
 * in.experience.service.ProductService;
 * 
 * @RestController
 * 
 * @CrossOrigin
 * 
 * @RequestMapping(value = "/product-api") public class ProductRestController {
 * 
 * // REDIS WORKS ONLY CRUD CHANGES I NEED TO UPDATE
 * 
 * @Autowired private IProduct productRepo;
 * 
 * @Autowired private ProductService service;
 * 
 * @Autowired private IProductCategory productCategoryRepo;
 * 
 * // ISSUE
 * 
 * @GetMapping("/products") public List<Product> getProducts() {
 * System.out.println(
 * "ProductRestController.getProducts()======================");
 * 
 * Collection<Product> allUsers = service.getAllUsers();
 * System.out.println("redis :: " + allUsers); List<Product> all =
 * productRepo.findAll();
 * System.out.println("ProductRestController.getProducts()"); System.out.
 * println("-----===================================    ================= all - "
 * + all); return all; }
 * 
 * // ISSUE
 * 
 * @DeleteMapping("/delete-redis-data") public String delRedisData(@RequestParam
 * String key) { System.out.println(key); service.deleteData(key); return
 * "redis data deleted"; }
 * 
 * @GetMapping("/product-category") public List<ProductCategory>
 * getProductCatgory() {
 * 
 * List<ProductCategory> allCategories = productCategoryRepo.findAll(); return
 * allCategories; }
 * 
 * @GetMapping("/product/{id}") public Product getProductById(@PathVariable
 * Integer id) { Optional<Product> byId = productRepo.findById(id); Product user
 * = service.getUser(id); return user; }
 * 
 * @PostMapping("/product") public Product createProduct(@RequestBody Product p)
 * { System.out.println(p); Product save = productRepo.save(p);
 * System.out.println(save); service.addUser(save);
 * 
 * return save; }
 * 
 * @PutMapping(value = "/product", consumes = MediaType.ALL_VALUE) public
 * Product updateProduct(@RequestBody Product p) { System.out.println(p);
 * Product save = productRepo.save(p); return save; }
 * 
 * // ISSUE
 * 
 * @DeleteMapping("/product/{prodId}") public String deleteProduct(@PathVariable
 * Integer prodId) { System.out.println("ProductRestController.deleteProduct()"
 * + "\n" + prodId); productRepo.deleteById(prodId);
 * 
 * // again check in rdis dta after deleterecord its working or not from redis
 * // deleted return "product deleted success"; }
 * 
 * 
 * @GetMapping("/product-category") public List<ProductCategory>
 * getProductCategory() { return productCategoryRepo.findAll(); }
 * 
 * @GetMapping("/product-category-products/{id}") public ProductCategory
 * getProductCategoryProductById(@PathVariable Long id) { return
 * productCategoryRepo.findById(id).orElse(null); }
 * 
 * @GetMapping("/product-by-id/{id}") public Product
 * getProductById(@PathVariable Long id) { return
 * productRepo.findById(id).orElse(null); }
 * 
 * @PutMapping("/products") public Product updateProductById(@RequestBody
 * Product product) { return productRepo.save(product); }
 * 
 * @PostMapping("/create-product") public String createProduct(@RequestBody
 * Product product) { Product createdProduct = productRepo.save(product); return
 * "Product created successfully with ID: " + createdProduct.getId(); }
 * 
 * @DeleteMapping("/product/{id}") public List<Product>
 * deleteProduct(@PathVariable Long id) { productRepo.deleteById(id); return
 * productRepo.findAll(); }
 * 
 * }
 */

package in.experience.rest;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import in.experience.model.Product;
import in.experience.model.ProductCategory;
import in.experience.repo.IProduct;
import in.experience.repo.IProductCategory;
import in.experience.service.RedisProductService;

@RestController
@CrossOrigin
@RequestMapping("/product-api")
public class ProductRestController {

	@Autowired
	private IProduct productRepo;

	@Autowired
	private RedisProductService redisService;

	@Autowired
	private IProductCategory productCategoryRepo;

	@GetMapping("/products")
	public List<Product> getProducts() {
		System.out.println("ProductRestController.getProducts()");
		// return service.getAllUsers();
		Collection<Product> products = redisService.getProducts();

		return (List<Product>) products;
		// return productRepo.findAll();
	}

	// ISSUE ==================================>>>>>>>>>>>>>>>>>>> RESPONSE COMING
	// BUT FROM DB NOT FROM REDIS ISSUE FIX REMAINS
	// ISSUE SOLVED DATA FROM REDIS COMES but delete every data from redis and u can
	// check & verify

	/*
	 * ISSUE IS DB FETCH BUG
	 */

	@GetMapping("/product-category")
	public List<ProductCategory> getProductCategories() {
		return (List<ProductCategory>) redisService.getProductCategory();
		// return productCategoryRepo.findAll();
	}

	// ISSUE ==================================>>>>>>>>>>>>>>>>>>> RESPONSE COMING
	// BUT FROM DB NOT FROM REDIS ISSUE FIX REMAINS
// IN REDIS DATA IS CORRECTED 
	// ONLY ISSUE IN FETCHING PARSING NOT FROM ANY LOGIN ISSUE

	/*
	 * NOTE: ISSUE SOLVED
	 */

	@GetMapping("/product/{id}")
	public Product getProductById(@PathVariable Integer id) throws Exception {
		// return service.getUserByID(id);
		/*
		 * return productRepo.findById(id).map(ResponseEntity::ok)
		 * .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
		 */
		return redisService.getProductByIdRedis(id);
	}

	@PostMapping("/product")
	public ResponseEntity<Product> createProduct(@RequestBody Product p) {
		Product savedProduct = productRepo.save(p);
		// service.addUser(savedProduct);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
	}

	@PutMapping("/product")
	public ResponseEntity<Product> updateProduct(@RequestBody Product p) {
		if (productRepo.existsById(p.getProdId())) {
			Product updatedProduct = productRepo.save(p);
			return ResponseEntity.ok(updatedProduct);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@DeleteMapping("/product/{prodId}")
	public ResponseEntity<String> deleteProduct(@PathVariable Integer prodId) {
		if (productRepo.existsById(prodId)) {
			productRepo.deleteById(prodId);
			return ResponseEntity.ok("Product deleted successfully.");
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
		}
	}

	@DeleteMapping("/delete-redis-data")
	public ResponseEntity<String> delRedisData(@RequestParam String key) {
		// service.deleteData(key);
		return ResponseEntity.ok("Redis data deleted.");
	}
}
