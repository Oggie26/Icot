package Project.example.Project_1.enity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@ToString
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToOne
    @JoinColumn(name = "user_id")
    User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    Set<CartItem> items = new HashSet<>();

    Double totalPrice = 0.0;

    public void updateTotalPrice() {
        double totalPrice = 0;
        for (CartItem cartItem : items) {
            if (cartItem != null && cartItem.getProduct() != null) {
                totalPrice += cartItem.getPrice() * cartItem.getQuantity();
            }
        }
        this.totalPrice = totalPrice;
    }
}
