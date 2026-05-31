package com.electro.store.api.domain.movement.repository;

import com.electro.store.api.domain.movement.model.entity.InventoryGuide;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryGuideRepository extends JpaRepository<InventoryGuide, String> {

}
