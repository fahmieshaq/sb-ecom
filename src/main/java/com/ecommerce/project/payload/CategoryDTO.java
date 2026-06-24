package com.ecommerce.project.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// CategoryDTO holds the payload request
// We decouple the model and presentation. CategoryDTO is representing Category at the presentation
// and Category model is representing entity at the db level. We need to do an object mapping
// with the help of model mapper; we will use https://modelmapper.org/ library

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {
    private Long categoryId;
    private String categoryName;
}
