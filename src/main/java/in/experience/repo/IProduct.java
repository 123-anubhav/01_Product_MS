package in.experience.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.experience.model.Product;

public interface IProduct extends JpaRepository<Product, Integer> {

}
	