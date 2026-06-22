package com.example.stylestackapp.category.dto.response;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class CategoryResponse {

    private UUID id;

    private String name;

    private String slug;

    private String description;

    private String imageUrl;

    private boolean active;

    private UUID parentCategoryId;
}
