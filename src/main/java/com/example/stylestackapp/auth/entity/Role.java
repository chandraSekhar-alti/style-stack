package com.example.stylestackapp.auth.entity;

import com.example.stylestackapp.common.enums.RoleName;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role extends AuditableEntity {

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, unique = true)
  private RoleName name;

  private String description;
}
