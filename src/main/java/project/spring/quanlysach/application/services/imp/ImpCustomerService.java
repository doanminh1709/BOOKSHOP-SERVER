package project.spring.quanlysach.application.services.imp;

import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import project.spring.quanlysach.application.constants.DevMessageConstant;
import project.spring.quanlysach.application.constants.UserMessConstant;
import project.spring.quanlysach.application.mapper.CustomerMapper;
import project.spring.quanlysach.application.repo.ContactRepository;
import project.spring.quanlysach.application.repo.CustomerRepository;
import project.spring.quanlysach.application.services.ICustomerService;
import project.spring.quanlysach.config.exception.VsException;
import project.spring.quanlysach.domain.dto.CustomerDTO;
import project.spring.quanlysach.domain.entity.Contact;
import project.spring.quanlysach.domain.entity.Customer;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ImpCustomerService implements ICustomerService {

    private final CustomerRepository customerRepository;
    private final ContactRepository contactRepository;
    private final CustomerMapper customerMapper = Mappers.getMapper(CustomerMapper.class);
    public ImpCustomerService(CustomerRepository customerRepository, ContactRepository contactRepository) {
        this.customerRepository = customerRepository;
        this.contactRepository = contactRepository;
    }

    @Override
    public List<Customer> getAllCustomer(Integer page, Integer size) {
            List<Customer> listCustomers;
            if (page != null) {
                listCustomers = customerRepository.findAll(PageRequest.of(page.intValue(), size)).getContent();
            } else {
                listCustomers = customerRepository.findAll();
            }
            return listCustomers;
        }

    @Override
    public List<Customer> searchCustomerByName(String name) {
        if (name != null) {
            String input = name.trim().toLowerCase();
            return customerRepository.findCustomerByName("%" + input + "%");
        } else {
            return customerRepository.findAll();
        }
    }

    @Override
    public List<Customer> searchCustomerByAddressContact(String city) {
        if (city != null) {
            city = city.trim();
            List<Contact> listContact = contactRepository.findContactByCity(city);
            List<Customer> listCustomer = new ArrayList<>();
            for (Contact contact : listContact) {
                int id = contact.getCustomer().getId();
                Optional<Customer> customer = customerRepository.findById(id);
                int index = listCustomer.indexOf(id);
                if (index != -1) {
                    listCustomer.add(customer.get());
                }
            }
            return listCustomer;
        }
        return customerRepository.findAll();
    }

    @Override
    public Customer getCustomerById(Integer id) {
        if (id != null) {
            Optional<Customer> foundCustomer = customerRepository.findById(id);
            if (foundCustomer.isEmpty()) {
                throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                        String.format(DevMessageConstant.Common.NOT_FOUND_OBJECT_BY_ID, "customer", id));
            }
            return foundCustomer.get();
        } else {
            throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                    String.format(DevMessageConstant.Common.NO_DATA_SELECTED));
        }
    }

    @Override
    public List<Customer> sortCustomerAESByName(Integer page, Integer size) {
        if (page != null) {
            return customerRepository.findAll(PageRequest.of(page.intValue(), size)).getContent().
                    stream().sorted(Comparator.comparing(Customer::getFullName)).collect(Collectors.toList());
        } else {
            return customerRepository.findAll(Sort.by(Sort.Direction.ASC, "fullName"));
        }
    }

    @Override
    public String editInfoCustomer(CustomerDTO customerDTO, int id) {
        Optional<Customer> foundCustomer = customerRepository.findById(id);
        if (foundCustomer.isEmpty()) {
            throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                    String.format(DevMessageConstant.Common.NOT_FOUND_OBJECT_BY_ID, "customer", id));
        }
        Customer foundByName = customerRepository.findByUsername(customerDTO.getUsername());
        if (foundByName != null && !Objects.equals(foundCustomer.get().getUsername(), customerDTO.getUsername())) {
            throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                    String.format(DevMessageConstant.Common.EXITS_USERNAME, customerDTO.getUsername()));
        }
        foundCustomer = Optional.ofNullable(customerMapper.toCustomer(customerDTO));
        foundCustomer.get().setId(id);
        customerRepository.save(foundCustomer.get());
        return DevMessageConstant.Common.NOTIFICATION_UPDATE_SUCCESS;
    }

    @Override
    public String removeCustomer(int id) {
        Optional<Customer> foundCustomer = customerRepository.findById(id);
        if (foundCustomer.isEmpty()) {
            throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                    String.format(DevMessageConstant.Common.NOT_FOUND_OBJECT_BY_ID, "customer", id));
        }
        customerRepository.delete(foundCustomer.get());
        return DevMessageConstant.Common.NOTIFICATION_DELETE_SUCCESS;
    }

    @Override
    public String lockAccount(int id) {
        Optional<Customer> foundCustomer = customerRepository.findById(id);
        if (foundCustomer.isEmpty()) {
            throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                    String.format(DevMessageConstant.Common.NOT_FOUND_OBJECT_BY_ID, "customer", id));
        }
        foundCustomer.get().setLocked(Boolean.TRUE);
        foundCustomer.get().setEnable(Boolean.FALSE);
        customerRepository.save(foundCustomer.get());
        return DevMessageConstant.Customer.LOCKED_ACCOUNT;
    }

    @Override
    public List<Customer> searchCustomerBirthdayToday() {
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        String realtime = currentTime.toString().substring(5 , 10);

        List<Customer> customers = new ArrayList<>();
        for (Customer item : customerRepository.findAll()) {
            if (item.getBirthday() != null) {
                String birthdayCustomer = item.getBirthday().toString().substring(5, 10);
                if (realtime.equals(birthdayCustomer))
                    customers.add(item);
            }
        }
        return customers;
    }
}
