package sat.entity.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name = "customers")
@PrimaryKeyJoinColumn(name = "user_id")
public class Customer extends User {

    @Column(name = "is_locked")
    protected boolean locked;

    public Customer() {}

    @Override
    public Long getId() {
        return super.getId();
    }

    @Override
    protected void onCreate() {
        super.onCreate();
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    @Override
    public Role getRole() {
        return Role.CUSTOMER;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "userId=" + getId() +
                ", locked=" + locked +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Customer customer = (Customer) o;

        return Objects.equals(getId(), customer.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
