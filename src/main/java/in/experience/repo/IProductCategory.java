package in.experience.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.experience.model.Product;
import in.experience.model.ProductCategory;

public interface IProductCategory extends JpaRepository<ProductCategory, Integer> {

	
}
