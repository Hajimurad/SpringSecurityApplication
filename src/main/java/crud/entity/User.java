package crud.entity;

import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

@Entity
@NoArgsConstructor
@Table(name = "users_table")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long id;

    @NotNull(message = "")
    @Size(min = 3, max = 20, message = "")
    @Column(name = "USERNAME", nullable = false, length = 20, unique = true)
    private String username;

    @NotNull(message = "")
    @Size(min = 6, max = 64, message = "")
    @Column(name = "PASSWORD", nullable = false, length = 64)
    private String password;

    @NotNull(message = "")
    @Column(name = "PASSWORD_CONFIRM", nullable = false, length = 64)
    private String passwordConfirm;

    @NotNull(message = "")
    @Size(max = 45, message = "в")
    @Column(name = "FIRST_NAME", nullable = false, length = 45)
    private String firstName;

    @Size(max = 45, message = "")
    @Column(name = "LAST_NAME", length = 45)
    private String lastName;

    @NotNull(message = "")
    @Email
    @Column(name = "EMAIL", nullable = false, length = 45, unique = true)
    private String email;

    @NotNull(message = "")
    @Size(min = 10)
    @Pattern(regexp="(^$|[0-9]{10})")
    @Column(name = "PHONE", length = 10, unique = true)
    private String phone;

    @NotNull(message = "")
    @Column(name="DOB")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    private Date userDOB;


    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    @JoinTable (name = "user_roles",
            joinColumns = {@JoinColumn(name = "USER_ID")}, inverseJoinColumns = {@JoinColumn(name = "ROLE_ID")})
    private Set<Role> roles;



    public User(String username, String password, String passwordConfirm, String firstName, String lastName,
                String email, String phone, Date userDOB, Set<Role> roles) {

        this.username = username;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.userDOB = userDOB;
        this.roles = roles;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
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
        return true;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
        checkPassword();
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
        checkPassword();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getUserDOB() {
        return userDOB;
    }

    public void setUserDOB(Date userDOB) {
        this.userDOB = userDOB;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    private void checkPassword() {
        if(this.password == null || this.passwordConfirm == null) {
            return;
        } else if(!this.password.equals(passwordConfirm)){
            this.passwordConfirm = null;
        }
    }

}
