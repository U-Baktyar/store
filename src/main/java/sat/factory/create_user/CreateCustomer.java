package sat.factory.create_user;

import sat.DTO.CustomerDto;
import sat.entity.user.Customer;
import sat.entity.user.User;

public class CreateCustomer extends CreatUserFactory<CustomerDto>{

    @Override
    public User createUser(CustomerDto customerDto) {
        Customer customer = new Customer();
        customer.setLogin(customerDto.getLogin());
        customer.setPassword(customerDto.getPassword());
        customer.setEmail(customerDto.getEmail());
        customer.setBirthDate(customerDto.getBirthDate());
        return customer;
    }
}
