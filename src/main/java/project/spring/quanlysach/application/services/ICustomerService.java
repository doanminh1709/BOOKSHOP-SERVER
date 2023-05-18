package project.spring.quanlysach.application.services;

import project.spring.quanlysach.domain.dto.CustomerDTO;
import project.spring.quanlysach.domain.entity.Customer;


import java.sql.Date;
import java.util.List;

public interface ICustomerService {

    List<Customer> getAllCustomer(Integer page, Integer size);

    List<Customer> searchCustomerByName(String name);

    List<Customer> searchCustomerByAddressContact(String city);

    Customer getCustomerById(Integer id);

    List<Customer> sortCustomerAESByName(Integer page, Integer size);

    String editInfoCustomer(CustomerDTO customerDTO, int id);

    String removeCustomer(int id);

    String lockAccount(int id);

    List<Customer> searchCustomerBirthdayToday();
}
