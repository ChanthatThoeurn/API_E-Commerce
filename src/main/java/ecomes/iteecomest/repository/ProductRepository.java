package ecomes.iteecomest.repository;

import ecomes.iteecomest.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
