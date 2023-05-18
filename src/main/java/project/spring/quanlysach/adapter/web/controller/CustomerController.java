package project.spring.quanlysach.adapter.web.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.spring.quanlysach.adapter.web.base.RestApiV0;
import project.spring.quanlysach.adapter.web.base.VsResponseUtil;
import project.spring.quanlysach.application.constants.CommonConstant;
import project.spring.quanlysach.application.constants.UrlConstant;
import project.spring.quanlysach.application.services.ICustomerService;
import project.spring.quanlysach.domain.dto.CustomerDTO;

import javax.validation.Valid;

@RestApiV0
public class CustomerController {
    @Autowired
    private ICustomerService iCustomerService;

    @GetMapping(UrlConstant.Customer.GET_ALL_CUSTOMER)
    @ApiOperation("Get all information customer")
    public ResponseEntity<?> getAllCustomer(@RequestParam(name = "page", required = false) Integer page) {
        return VsResponseUtil.ok(iCustomerService.getAllCustomer(page, CommonConstant.SIZE_OFF_PAGE));
    }

    @GetMapping(UrlConstant.Customer.SEARCH_CUSTOMER_BY_NAME)
    @ApiOperation("Search customer by name")
    public ResponseEntity<?> searchCustomerByName(@RequestParam(name = "name", required = false) String name) {
        return VsResponseUtil.ok(iCustomerService.searchCustomerByName(name));
    }

    @GetMapping(UrlConstant.Customer.SEARCH_CUSTOMER_BY_ADDRESS_CONTACT)
    @ApiOperation("Search customer by address contact")
    public ResponseEntity<?> searchCustomerByAddressContact(@RequestParam(name = "address_contact", required = false) String address_contact) {
        return VsResponseUtil.ok(iCustomerService.searchCustomerByAddressContact(address_contact));
    }

    @GetMapping(UrlConstant.Customer.GET_CUSTOMER_BY_ID)
    @ApiOperation("Search customer by id")
    public ResponseEntity<?> searchCustomerById(@PathVariable("id") int id) {
        return VsResponseUtil.ok(iCustomerService.getCustomerById(id));
    }

    @GetMapping(UrlConstant.Customer.SHOW_LIST_CUSTOMER_SORT_BY_NAME)
    @ApiOperation("Show list customer sorted aes by name")
    public ResponseEntity<?> getCustomerSortAESByName(@RequestParam(name = "page", required = false) Integer page) {
        return VsResponseUtil.ok(iCustomerService.sortCustomerAESByName(page, CommonConstant.SIZE_OFF_PAGE));
    }

    @PutMapping(UrlConstant.Customer.EDIT_CUSTOMER_BY_ID)
    @ApiOperation("Update information customer by customer id")
    public ResponseEntity<?> updateCustomerByID(@PathVariable("id") int id,
                                                @RequestBody @Valid CustomerDTO customerDTO) {
        return VsResponseUtil.ok(iCustomerService.editInfoCustomer(customerDTO, id));
    }

    @DeleteMapping(UrlConstant.Customer.REMOVE_CUSTOMER_BY_ID)
//    @PreAuthorize("hasAnyRole('AMIN')")
    @ApiOperation("Remove customer by customer id")
    public ResponseEntity<?> removeCustomerById(@PathVariable("id") int id) {
        return VsResponseUtil.ok(iCustomerService.removeCustomer(id));
    }
}
