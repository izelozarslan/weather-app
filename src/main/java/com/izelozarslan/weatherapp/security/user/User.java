package com.izelozarslan.weatherapp.security.user;

import com.izelozarslan.weatherapp.entity.City;
import com.izelozarslan.weatherapp.general.BaseEntity;
import com.izelozarslan.weatherapp.security.token.Token;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_user")
public class User extends BaseEntity implements UserDetails {

  @Id
  @GeneratedValue(generator = "User" ,strategy = GenerationType.SEQUENCE)
  @SequenceGenerator(name = "User", sequenceName = "USER_ID_SEQ")
  private Long id;

  @NotBlank
  @Size(min=2, max=100, message = "First name must be at least 2 characters")
  @Column(name = "FIRST_NAME", length = 100, nullable = false)
  private String firstname;

  @NotBlank
  @Size(min=2, max=100, message = "Last name must be at least 2 characters")
  @Column(name = "LAST_NAME", length = 100, nullable = false)
  private String lastname;

  @Email
  @Column(name = "EMAIL", length = 50, nullable = false, unique = true)
  private String email;

  @NotBlank
  @Size(min=6, max=400, message = "Password must be at least 2 characters")
  @Column(name = "PASSWORD", length = 400, nullable = false)
  private String password;

  @OneToMany(fetch = FetchType.LAZY,  mappedBy = "user")
  private List<City> cities = new ArrayList<>();

  @Enumerated(EnumType.STRING)
  private Role role = Role.USER;

  @OneToMany(mappedBy = "user")
  private List<Token> tokens;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return role.getAuthorities();
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
