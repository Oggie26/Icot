package Project.example.Project_1.service;

import Project.example.Project_1.repository.BookOrderRepository;
import Project.example.Project_1.repository.OrderRepository;
import Project.example.Project_1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final OrderRepository orderRepository;
    private final BookOrderRepository bookOrderRepository;
    private final UserRepository userRepository;

    public Double getTotalRevenue() {
        Double orderTotal = orderRepository.getTotalAmountAllOrders();
        Double bookOrderTotal = bookOrderRepository.getTotalBookOrderAmount();
        return safeSum(orderTotal, bookOrderTotal);
    }

    public Long getTotalOrderCount() {
        Long orderCount = orderRepository.getTotalOrderCount();
        Long bookOrderCount = bookOrderRepository.countTotalBookOrders();
        return safeSum(orderCount, bookOrderCount);
    }

    public Long getTotalUserCount() {
        return userRepository.countTotalUsers();
    }

    private Double safeSum(Double a, Double b) {
        return (a == null ? 0 : a) + (b == null ? 0 : b);
    }

    private Long safeSum(Long a, Long b) {
        return (a == null ? 0 : a) + (b == null ? 0 : b);
    }
}
