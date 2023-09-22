package com.ubt.andi.businessrecommendation.models;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Data
@Entity
public class Business  {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime dateOfCreation;
    private String location;
    @OneToMany(mappedBy = "business",cascade = CascadeType.REMOVE)
    private List<Image> images = new ArrayList<Image>();
    @Transient
    private List<MultipartFile> imageFiles = new ArrayList<>();
    @Transient
    private List<Byte> starValues = new ArrayList<>();
    private double ratingValue=0.0;
    @ManyToOne
    @JoinColumn(name = "applicationuser_id",nullable = false)
    private ApplicationUser applicationUser;
    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(
            name = "rating",
            joinColumns = {@JoinColumn(name = "business_id")},
            inverseJoinColumns = {@JoinColumn(name="user_id")}
    )
    private List<ApplicationUser> ratedByUsers = new ArrayList<>();

}
