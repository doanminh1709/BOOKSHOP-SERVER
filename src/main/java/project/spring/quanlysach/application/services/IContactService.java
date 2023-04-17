package project.spring.quanlysach.application.services;

import project.spring.quanlysach.domain.dto.ContactDTO;
import project.spring.quanlysach.domain.entity.Contact;

import java.util.List;

public interface IContactService {
    List<Contact> showAllContactCustomer(Integer page, Integer size);

    Contact createNewContact(ContactDTO contactDTO);

    Contact getContactById(int id);

    String updateInformationContact(ContactDTO contactDTO, int id);

    String deleteInfoContact(int id);

    List<Contact> getInfoByCustomerId(int customerId);

}
