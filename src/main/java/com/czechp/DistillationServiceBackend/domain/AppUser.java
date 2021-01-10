package com.czechp.DistillationServiceBackend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Collections;

@Entity(name = "users")
@AllArgsConstructor()
@NoArgsConstructor()
@Builder()
@Data()
public class AppUser implements UserDetails {
    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull(message = "Username cannot be null")
    @Length(min = 4, max = 20, message = "Length of username must be between 4-20 chars")
    private String username;

    //
//    Password should seems like:
//    1.Minimum 4 characters
//    2. Maximum 20 characters
//    3. Contains minimum one number
//    4. Contains minmum one upper cas
    @NotNull(message = "Password cannot be null")
    @NotBlank(message = "Password cannot be blank")
    private String password;

    @Email(message = "It's not correct email format")
    @NotNull(message = "Email cannot be null")
    private String email;

    private String role;

    @JsonIgnore()
    private boolean enabled;

    @JsonIgnore()
    private String activationToken;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + role));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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
        return enabled;
    }

    @Override()
    public int hashCode() {
        return new HashCodeBuilder()
                .append(username)
                .append(password)
                .append(email)
                .toHashCode();
    }
}
