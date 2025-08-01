package sat.entity.Order;

import java.time.LocalDateTime;
import java.util.Objects;

public class Order {
    private int id;
    private Long userId;
    private StatusEnum status;
    private double price;
    private LocalDateTime createAt;

    public Order() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getStatusEnum() {
        return status.getStatus();
    }

    public void setStatusEnum(String status) {
        this.status = StatusEnum.fromString(status);
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj){
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()){
            return false;
        }
        Order order = (Order) obj;
        return id == order.id;

    }

    @Override
    public String toString() {
        return String.format(
                "Заказ:\n\tID: %d\n\tUser ID: %d\n\tStatus: %s\n\tPrice: %.2f\n\tCreated At: %s",
                id, userId, status, price, createAt.toString()
        );
    }

}


