package project.spring.quanlysach.adapter.web.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import project.spring.quanlysach.adapter.web.base.RestApiV0;
import project.spring.quanlysach.adapter.web.base.VsResponseUtil;
import project.spring.quanlysach.application.constants.CommonConstant;
import project.spring.quanlysach.application.constants.UrlConstant;
import project.spring.quanlysach.application.services.IContactService;
import project.spring.quanlysach.domain.dto.ContactDTO;

import javax.validation.Valid;

@RestApiV0
public class ContactController {

    @Autowired
    private IContactService contactService;

    @GetMapping(UrlConstant.Contact.GET_ALL_CONTACT)
    @ApiOperation("Get all contact of customer")
    public ResponseEntity<?> getAllContactOfCustomer(@RequestParam(name = "page", required = false) Integer page) {
        return VsResponseUtil.ok(contactService.showAllContactCustomer(page, CommonConstant.SIZE_OFF_PAGE));
    }

    @GetMapping(UrlConstant.Contact.GET_CONTACT_BY_ID)
    @ApiOperation("Get contact of customer by contact id")
    public ResponseEntity<?> getContactOfCustomerByContactId(@PathVariable("id") int id) {
        return VsResponseUtil.ok(contactService.getContactById(id));
    }

    @GetMapping(UrlConstant.Contact.GET_CONTACT_BY_CUSTOMER)
    @ApiOperation("Get all info contact of customer")
    public ResponseEntity<?> getAllContactByCustomerId(@PathVariable("customerId") int customerId) {
        return VsResponseUtil.ok(contactService.getInfoByCustomerId(customerId));
    }

    @PostMapping(UrlConstant.Contact.CREATE_NEW_CONTACT)
    @ApiOperation("Create new contact")
    public ResponseEntity<?> createNewContact(@RequestBody @Valid ContactDTO contactDTO) {
        return VsResponseUtil.ok(contactService.createNewContact(contactDTO));
    }

    @PutMapping(UrlConstant.Contact.UPDATE_CONTACT)
    @ApiOperation("Update contact customer by contact id")
    public ResponseEntity<?> updateContactById(@RequestBody ContactDTO contactDTO, @PathVariable("id") int id) {
        return VsResponseUtil.ok(contactService.updateInformationContact(contactDTO, id));
    }

    @DeleteMapping(UrlConstant.Contact.DELETE_CONTACT)
    @ApiOperation("Delete contact customer by contact id")
    public ResponseEntity<?> deleteContactById(@PathVariable("id") int id) {
        return VsResponseUtil.ok(contactService.deleteInfoContact(id));
    }
}
