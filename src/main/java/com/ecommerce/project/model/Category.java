package com.ecommerce.project.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

//@Entity(name="cats")
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto create pk values .... AUTO will let JPA determine how to generate the value based on the db provider ... GenerationType.IDENTITY is not supported by all db providers
    private Long categoryId;
    private String categoryName;

    // Good practice
    protected Category() {

    }

    public Category(Long categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}
