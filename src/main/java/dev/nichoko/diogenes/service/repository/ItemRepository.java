package dev.nichoko.diogenes.service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.nichoko.diogenes.model.domain.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    Optional<Item> findById(int itemId);
}
