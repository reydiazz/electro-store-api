package com.electro.store.api.domain.movement.repository;

import com.electro.store.api.domain.movement.model.entity.GuideDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuideDetailRepository extends JpaRepository<GuideDetail, String> {
}
