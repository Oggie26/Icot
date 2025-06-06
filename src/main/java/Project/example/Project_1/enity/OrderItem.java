package Project.example.Project_1.enity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;


@Entity
@Table(name = "tbl_order_item")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    @JoinColumn(name = "order_id", nullable = false)
    Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "product_id", nullable = false)
    Product product;

    @Column(name = "quantity")
    Integer quantity;

    @Column(name = "price")
    Double price;

    @Column(name = "total_price")
    Double totalPrice;

    @Column(name = "is_feedback")
    Boolean isFeedback;

    @ManyToOne
    @JoinColumn(name = "book_order_id")
    private BookOrder bookOrder;

    public Double calculateTotalPrice() {
        return this.price * this.quantity;
    }
}
