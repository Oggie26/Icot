package Project.example.Project_1.enity;
import Project.example.Project_1.enums.EnumRole;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(unique = true)
    private String email;

    @Column
    private String  fullName;

    @Column
    private String gender;

    @Column
    private Date birthday;

    @Column(unique = true)
    private String phone;

    @Column(nullable = false)
    private Boolean status;

    @Column
    private String address;

    @Enumerated(EnumType.STRING)
    private EnumRole role;

    @Column
    private int point;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of( new SimpleGrantedAuthority(role.toString()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }



}
