package com.electro.store.api.domain.movement.model.entity;

import com.electro.store.api.domain.auth.model.entity.User;
import com.electro.store.api.domain.movement.model.enums.GuideType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "inventory_guides")
@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class InventoryGuide {

    @Id
    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_code", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private GuideType type;

    @Column(name = "reason")
    private String reason;

    @Column(name = "description")
    private String description;

    @Column(name = "guide_date", nullable = false)
    private LocalDateTime guideDate;

    @OneToMany(mappedBy = "guide", fetch = FetchType.LAZY)
    private List<GuideDetail> details = new ArrayList<>();

    public InventoryGuide(
            String code,
            User user,
            GuideType type,
            String reason,
            String description,
            LocalDateTime guideDate
    ) {
        this.code = code;
        this.user = user;
        this.type = type;
        this.reason = reason;
        this.description = description;
        this.guideDate = guideDate;
    }

    public void addDetail(GuideDetail detail) {
        this.details.add(detail);
    }


}
