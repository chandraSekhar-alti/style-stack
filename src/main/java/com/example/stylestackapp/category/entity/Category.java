package com.example.stylestackapp.category.entity;

import com.example.stylestackapp.auth.entity.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Table(
        name = "categories",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_category_slug",
                        columnNames = "slug"
                )
        }
)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category extends AuditableEntity {

    @Column(nullable = false, unique = true)
    private String name;

    /**
     * URL-friendly unique identifier used for SEO-friendly URLs.
     * Example:
     * name = "Men's Wear"
     * slug = "mens-wear"
     * This should remain stable even if the display name changes.
     */
    @Column(nullable = false, unique = true)
    private String slug;

    @Column(length = 1000)
    private String description;

    @Column(name = "image_Url")
    private String imgUrl;

    @Builder.Default
    @Column(name = "active", nullable = false)
    private boolean isActive = true;

    /**
     * Self-referencing relationship used to support hierarchical categories.
     * Example:
     * Men's Wear
     *   ├── Shirts
     *   ├── Jeans
     *   └── Jackets
     * For top-level categories this value will be null.
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_category_id")
    private Category parentCategory;
}
