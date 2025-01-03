package in.experience.service;
/*
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import in.experience.model.Product;
import in.experience.repo.IProduct;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class ProductService {

	@Autowired
	private IProduct productRepo;

	HashOperations<String, Integer, Product> opsForHash = null;

	public ProductService(RedisTemplate<String, Product> redisTemplate) {
		this.opsForHash = redisTemplate.opsForHash();
	}

	/*
	 * public String addUser(Product user) { opsForHash.put("products",
	 * user.getProdId(), user); return "User Added"; }
	 */
/*
	@CircuitBreaker(name = "productService", fallbackMethod = "fallbackGetProductById")
	public ResponseEntity<Product> getUserByID(Integer userId) throws Exception {
		System.out.println(
				"*********************=================    Redis method  ==========    Redis==========    Redis  ::  "
						+ userId);
		throw new Exception("anubhav sri");
	//	return opsForHash.get("products", userId);
	}

	// Fallback method
	public Product fallbackGetProductById(Integer userId, Throwable throwable) {
	    System.out.println("Fallback triggered for userId: " + userId);
	    System.out.println("Reason: " + throwable.getMessage());

	    try {
	        Optional<Product> optionalProduct = productRepo.findById(userId);
	        if (optionalProduct.isPresent()) {
	            System.out.println("Product found in DB: " + optionalProduct.get());
	            return optionalProduct.get();
	        } else {
	            System.out.println("No product found in DB for userId: " + userId);
	            return null; // Handle appropriately for no product scenario
	        }
	    } catch (Exception e) {
	        System.err.println("Error in fallback method: " + e.getMessage());
	        return null; // Prevent further stack overflow
	    }
	}

	@CircuitBreaker(name = "productService", fallbackMethod = "fallbackGetProduct")
	public Collection<Product> getAllUsers() {
		System.out.println("*********************=================    Redis==========    Redis==========    Redis");
		Map<Integer, Product> entries = opsForHash.entries("products"); // error
		System.out.println("/*********************/ /*==> " + entries.values());*/
	
		/*System.out.println("**=================    Redis==========    Redis==========    Redis");
		return entries.values();
	}

	// Fallback method
	public List<Product> fallbackGetProduct(Throwable throwable) {

		System.out.println("*********************=================    Redis==========    Redis==========    Redis");
		System.out.println("ProductService.fallbackGetProduct()");
		List<Product> all = productRepo.findAll();

		System.out.println("*********************=================    Redis==========    Redis==========    Redis");
		return all;
		// return "Fallback: Unable to retrieve product at the moment. Please try again
		// later.";
	}
*/
	/*
	 * public void deleteData(String key) { opsForHash.delete(key); }
	 */
//}