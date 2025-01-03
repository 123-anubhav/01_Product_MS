package in.experience.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import in.experience.dto.ProductResponse;
import in.experience.model.Product;
import in.experience.model.ProductCategory;
import in.experience.repo.IProduct;
import in.experience.repo.IProductCategory;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class RedisProductService {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@Autowired
	private IProduct productRepo;

	@Autowired
	private IProductCategory productCategoryRepo;

	private static final String PRODUCT_CACHE_KEY = "products";

	/**
	 * Fetch products with Circuit Breaker
	 */
	@CircuitBreaker(name = "product-Service", fallbackMethod = "getProductsFromDbFallback")
	public Collection<Product> getProducts() {
		System.out.println("Fetching products from Redis...");
		// Fetch from Redis
		Collection<Product> products = (Collection<Product>) redisTemplate.opsForValue().get(PRODUCT_CACHE_KEY);
		if (products == null) {
			throw new RuntimeException("Redis fetch failed or data not found!");
		}
		return products;
	}

	/**
	 * Fallback method for Circuit Breaker
	 */
	public Collection<Product> getProductsFromDbFallback(Throwable throwable) {
		System.err.println("Redis fetch failed. Fallback to DB: " + throwable.getMessage());
		// Fetch from DB
		Collection<Product> products = productRepo.findAll();
		// Cache the data in Redis for future requests
		redisTemplate.opsForValue().set(PRODUCT_CACHE_KEY, products);
		return products;
	}

	// **************************************************************************************

	private static final String PRODUCT_CATEGORY_CACHE_KEY = "product-categories";

	@CircuitBreaker(name = "product-Category-Service", fallbackMethod = "getProductCategoryFromDbFallback")
	public Collection<ProductCategory> getProductCategory() {
		System.out.println("Fetching product cateogry from Redis...");
		// Fetch from Redis
		Collection<ProductCategory> productCategory = (Collection<ProductCategory>) redisTemplate.opsForValue()
				.get(PRODUCT_CATEGORY_CACHE_KEY);
		if (productCategory == null) {
			throw new RuntimeException("Redis fetch failed or data not found!");
		}
		return productCategory;
	}

	/*
	 * Fallback method for Circuit Breaker
	 */
	public Collection<ProductCategory> getProductCategoryFromDbFallback(Throwable throwable) {
		System.err.println("Redis fetch failed. Fallback to DB: " + throwable.getMessage());
		// Fetch from DB
		Collection<ProductCategory> productCategory = productCategoryRepo.findAll();
//        Collection<Product> products = productRepo.findAll();
		// Cache the data in Redis for future requests
		redisTemplate.opsForValue().set(PRODUCT_CATEGORY_CACHE_KEY, productCategory);
		return productCategory;
	}

	// ******************************** ///
	// *************************************************** //

	private static final String PRODUCT_BY_ID_CACHE_KEY = "product-by-id:";

	@Autowired
	private ObjectMapper objectMapper;
/*
	@CircuitBreaker(name = "productService", fallbackMethod = "getProductByIdRedisFromDbFallback")
	public Product getProductByIdRedis(Integer id) {
		System.out.println("RedisProductService.getProductByIdRedis()");

		System.out.println("Fetching product by id from Redis...");
		Object redisValue = redisTemplate.opsForValue().get(PRODUCT_BY_ID_CACHE_KEY + id);
		if (redisValue instanceof LinkedHashMap) {
			// Convert LinkedHashMap to Product using ObjectMapper

			if (redisValue == null) {
				throw new RuntimeException("No value found in Redis for key: " + redisValue);
			}

			if (redisValue instanceof LinkedHashMap) {
				// Convert the LinkedHashMap back to the Product object
				return objectMapper.convertValue(redisValue, Product.class);
			} else if (redisValue instanceof Product) {
				// If the value is already a Product object, return it directly
				return (Product) redisValue;
			} else {
				// If the type is unexpected, throw an exception
				throw new RuntimeException("Unexpected type in Redis: " + redisValue.getClass());
			}

		}
		return (Product) redisValue;
	}

	public Product getProductByIdRedisFromDbFallback(Integer id, Throwable throwable) {
		System.err.println("Redis fetch failed. Fallback to DB: " + throwable.getMessage());
		ResponseEntity<Product> productById = productRepo.findById(id).map(ResponseEntity::ok)
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));

		redisTemplate.opsForValue().set(PRODUCT_BY_ID_CACHE_KEY + id, productById);

		return productById.getBody();

	}
*/
	

	@CircuitBreaker(name = "product-By-Id-Service", fallbackMethod = "getProductByIdRedisFromDbFallback")
	public Product getProductByIdRedis(Integer id) {
	    System.out.println("RedisProductService.getProductByIdRedis()");
	    System.out.println("Fetching product by id from Redis...");

	    // Fetch from Redis
	    Object redisValue = redisTemplate.opsForValue().get(PRODUCT_BY_ID_CACHE_KEY + id);
	    
	    if (redisValue == null) {
	        throw new RuntimeException("No value found in Redis for key: " + id);
	    }

	    // Deserialize the value from Redis into ProductResponse
	    ProductResponse productResponse = objectMapper.convertValue(redisValue, ProductResponse.class);

	    // Extract the Product from the body of the response
	    Product product = productResponse.getBody();

	    if (product == null) {
	        throw new RuntimeException("Product not found in Redis body for key: " + id);
	    }

	    return product;
	}

	
	public Product getProductByIdRedisFromDbFallback(Integer id, Throwable throwable) {
	    System.err.println("Redis fetch failed. Fallback to DB: " + throwable.getMessage());
	    
	    // Fetch product from DB
	    ResponseEntity<Product> productById = productRepo.findById(id).map(ResponseEntity::ok)
	            .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));

	    // Store in Redis in the correct format (wrap it in ProductResponse)
	    ProductResponse productResponse = new ProductResponse();
	    productResponse.setBody(productById.getBody());
	    // You can also set other fields (e.g., headers, statusCode) if needed.

	    redisTemplate.opsForValue().set(PRODUCT_BY_ID_CACHE_KEY + id, productResponse);

	    return productById.getBody();
	}

}
