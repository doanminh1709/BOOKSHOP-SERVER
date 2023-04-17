package project.spring.quanlysach.application.services.imp;

import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import project.spring.quanlysach.application.constants.DevMessageConstant;
import project.spring.quanlysach.application.constants.UserMessConstant;
import project.spring.quanlysach.application.mapper.ContactMapper;
import project.spring.quanlysach.application.repo.ContactRepository;
import project.spring.quanlysach.application.repo.CustomerRepository;
import project.spring.quanlysach.application.services.IContactService;
import project.spring.quanlysach.config.exception.VsException;
import project.spring.quanlysach.domain.dto.ContactDTO;
import project.spring.quanlysach.domain.entity.Contact;
import project.spring.quanlysach.domain.entity.Customer;

import java.util.List;
import java.util.Optional;

@Service
public class ImpContactService implements IContactService {

    private final ContactRepository contactRepository;
    private final CustomerRepository customerRepository;
    private final ContactMapper contactMapper = Mappers.getMapper(ContactMapper.class);

    public ImpContactService(ContactRepository contactRepository, CustomerRepository customerRepository) {
        this.contactRepository = contactRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Contact> showAllContactCustomer(Integer page, Integer size) {
        List<Contact> listContact;
        if (page != null) {
            listContact = contactRepository.findAll(PageRequest.of(page.intValue(), size)).getContent();
        } else {
            listContact = contactRepository.findAll();
        }
        return listContact;
    }

    @Override
    public Contact createNewContact(ContactDTO contactDTO) {

        Optional<Customer> contactOfCustomer = customerRepository.findById(contactDTO.getCustomerId());
        if (contactOfCustomer.isEmpty()) {
            throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                    String.format(DevMessageConstant.Common.NOT_FOUND_OBJECT_BY_ID, "customer", contactDTO.getCustomerId()));
        }
        Contact newContact = contactMapper.toContact(contactDTO, contactDTO.getCustomerId());
        return contactRepository.save(newContact);
    }

    @Override
    public Contact getContactById(int id) {
        Optional<Contact> foundContact = contactRepository.findById(id);
        if (foundContact.isEmpty()) {
            throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                    String.format(DevMessageConstant.Common.NOT_FOUND_OBJECT_BY_ID, "contact", id));
        }
        return foundContact.get();
    }

    @Override
    public String updateInformationContact(ContactDTO contactDTO, int id) {
        Optional<Contact> foundContact = contactRepository.findById(id);
        if (foundContact.isEmpty()) {
            throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                    String.format(DevMessageConstant.Common.NOT_FOUND_OBJECT_BY_ID, "contact", id));
        }
        foundContact = Optional.ofNullable(contactMapper.toContact(contactDTO, contactDTO.getCustomerId()));
        foundContact.get().setId(id);
        return DevMessageConstant.Common.NOTIFICATION_UPDATE_SUCCESS;
    }

    @Override
    public String deleteInfoContact(int id) {
        Optional<Contact> foundContact = contactRepository.findById(id);
        if (foundContact.isPresent()) {
            contactRepository.deleteById(id);
            return DevMessageConstant.Common.NOTIFICATION_DELETE_SUCCESS;
        }
        throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                String.format(DevMessageConstant.Common.NOT_FOUND_OBJECT_BY_ID, "contact", id));
    }

    @Override
    public List<Contact> getInfoByCustomerId(int customerId) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        if (customer.isEmpty()) {
            throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                    String.format(DevMessageConstant.Common.NOT_FOUND_OBJECT_BY_ID, "customer", customerId));
        }
        return contactRepository.findContactByCustomerId(customerId);
    }
}
