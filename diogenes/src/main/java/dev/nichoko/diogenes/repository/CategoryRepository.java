package dev.nichoko.diogenes.repository;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.nichoko.diogenes.model.domain.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findById(int itemId);
    Page<Category> findAll(Specification<Category> spec, Pageable pageable);
    boolean existsByName(String name);
}
