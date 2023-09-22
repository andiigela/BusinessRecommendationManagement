package com.ubt.andi.businessrecommendation.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@IdClass(RatingPK.class)
@Table(name = "rating")
@Data
public class Rating {
    @Id
    @Column(name = "business_id")
    private Long businessId;
    @Column(name="user_id")
    @Id
    private Long userId;

}
