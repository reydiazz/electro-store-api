package com.electro.store.api.domain.product.repository;

import com.electro.store.api.domain.product.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, String> {
    @Query("SELECT p FROM Product p WHERE " +
           "LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(p.code) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(p.brand) LIKE LOWER(CONCAT('%', :search, '%'))")
    Page<Product> search(@Param("search") String search, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE " +
           "(:search IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%')) OR LOWER(p.code) LIKE LOWER(CONCAT('%', :search, '%')) OR LOWER(p.brand) LIKE LOWER(CONCAT('%', :search, '%'))) AND " +
           "(:categoryName IS NULL OR p.category.name = :categoryName) AND " +
           "(:brand IS NULL OR p.brand = :brand) AND " +
           "(:stockStatus IS NULL OR " +
           "(:stockStatus = 'Con Stock' AND p.stock > 0) OR " +
           "(:stockStatus = 'Stock Bajo' AND p.stock > 0 AND p.stock <= p.lowStock) OR " +
           "(:stockStatus = 'Agotados' AND p.stock = 0))")
    Page<Product> searchWithFilters(
            @Param("search") String search,
            @Param("categoryName") String categoryName,
            @Param("brand") String brand,
            @Param("stockStatus") String stockStatus,
            Pageable pageable);

    @Query("SELECT COUNT(p) FROM Product p WHERE p.stock > 0 AND p.stock <= p.lowStock")
    long countLowStock();

    @Query("SELECT COUNT(p) FROM Product p WHERE p.stock = 0")
    long countOutOfStock();

    @Query("SELECT COUNT(DISTINCT p.category.code) FROM Product p")
    long countDistinctCategories();

    @Query("SELECT DISTINCT p.brand FROM Product p WHERE p.brand IS NOT NULL ORDER BY p.brand")
    List<String> findDistinctBrands();
}
