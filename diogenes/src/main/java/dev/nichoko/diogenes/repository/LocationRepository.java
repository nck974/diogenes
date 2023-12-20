package dev.nichoko.diogenes.repository;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.nichoko.diogenes.model.domain.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    Optional<Location> findById(int itemId);
    Page<Location> findAll(Specification<Location> spec, Pageable pageable);
    boolean existsByName(String name);
}
