package Project.example.Project_1.enity;
import Project.example.Project_1.enums.EnumRole;
import Project.example.Project_1.enums.EnumStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User extends AbstractEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(unique = true)
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(unique = true)
    private String email;

    @Column
    private String fullName;

    @Column
    private String gender;

    @Column
    private LocalDate birthday;

    @Column
    private String avatar;

    @Column(unique = true)
    private String phone;

    @Enumerated(EnumType.STRING)
    EnumStatus status;

    @Enumerated(EnumType.STRING)
    EnumRole role;

    @Column
    private int point;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnore
    List<Otp> otps;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    List<ProcessOrder> processOrders;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    @JsonIgnore
    List<Feedback> feedbacks;

    @JsonIgnore
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    List<Address> addresses;

    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinTable(
            name = "tbl_user_voucher",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "voucher_id")
    )
    List<Voucher> vouchers;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    List<ImageCus> imageCus;


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


    public void addOtp(Otp obj) {
        if (this.otps == null) {
            this.otps = new ArrayList<>();
        }
        otps.add(obj);
        obj.setUser(this);
    }

    public void addVoucher(Voucher obj) {
        if (this.vouchers == null) {
            this.vouchers = new ArrayList<>();
        }
        vouchers.add(obj);
        obj.addUser(this);
    }

    public void removeVoucher(Voucher obj) {
        for (Voucher voucher : vouchers) {
            if (voucher.getCode().equals(obj.getCode())) {
                vouchers.remove(voucher);
                obj.getUsers().remove(this);
                break;
            }
        }
    }
    public void addPoint(Integer point) {
        this.point += point;
    }



}
