package com.example.stylestackapp.category.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateCategoryRequest {

  @NotBlank
  private String name;

  private String description;

  private String imageUrl;

  private UUID parentCategoryId;
}
