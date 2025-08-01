package sat.entity.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name = "workers")
@PrimaryKeyJoinColumn(name = "user_id")
public class Worker extends User {

    @Column(name = "first_name", nullable = false)
    protected String firstName;

    @Column(name = "last_name", nullable = false)
    protected String lastName;

    @Column(name = "address")
    protected String address;

    @Column(name = "phone")
    protected String phoneNumber;

    @Column(name = "role")
    protected String userRole;


    public Worker() {
    }

    @Override
    public Role getRole() {
        return Role.valueOf(userRole);
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }



    @Override
    public String toString() {
        return String.format("%s %s",
                Objects.toString(firstName, ""),
                Objects.toString(lastName, "")
        );
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Worker)) return false;
        if (!super.equals(o)) return false;
        Worker worker = (Worker) o;
        return Objects.equals(phoneNumber, worker.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), phoneNumber);
    }
}
