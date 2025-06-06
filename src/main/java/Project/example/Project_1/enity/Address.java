package Project.example.Project_1.enity;

import Project.example.Project_1.repository.AddressRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@ToString
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Address
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "name")
    String name;

    @Column(name = "phone")
    String phone;

    @Column(name = "city")
    String city;

    @Column(name = "district")
    String district;

    @Column(name = "ward")
    String ward;

    @Column(name = "street")
    String street;

    @Column(name = "address_line")
    String addressLine;

    @Column(name = "is_default")
    Boolean isDefault;


    @OneToMany(mappedBy = "address", fetch = FetchType.LAZY)
    @JsonIgnore
    List<Order> orders;

    @OneToMany(mappedBy = "address", fetch = FetchType.LAZY)
    @JsonIgnore
    List<BookOrder> bookOrders ;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinColumn(name = "user_id")
    User user;
}
