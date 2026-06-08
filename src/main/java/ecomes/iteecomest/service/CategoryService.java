package ecomes.iteecomest.service;

import ecomes.iteecomest.dto.CategoryResponse;
import ecomes.iteecomest.dto.CreateCategoryRequest;
import ecomes.iteecomest.dto.UpdateCategoryRequest;
import org.springframework.data.domain.Page;
import java.util.List;

public interface CategoryService {
    CategoryResponse createCategory(CreateCategoryRequest request);
    Page<CategoryResponse> getAllCategories(int pageNumber, int pageSize);
    List<CategoryResponse> getAllCategoriesNoPagination();
    CategoryResponse getCategoryById(Integer id);
    List<CategoryResponse> getSubcategories(Integer parentId);
    List<CategoryResponse> getCategoryTree();
    void hardDeleteCategory(Integer id);
    CategoryResponse softDeleteCategory(Integer id);
    CategoryResponse updateCategory(Integer id, UpdateCategoryRequest request);
    Page<CategoryResponse> searchCategories(String keyword, int pageNumber, int pageSize);
    CategoryResponse moveCategory(Integer id, Integer newParentId);
    CategoryResponse restoreCategory(Integer id);
    void bulkDeleteCategories(List<Integer> ids);
    Integer getProductCount(Integer id);
}