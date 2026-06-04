package ecomes.iteecomest.repository;

import ecomes.iteecomest.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
