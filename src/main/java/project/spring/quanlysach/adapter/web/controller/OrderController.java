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
import project.spring.quanlysach.application.services.IOrderService;
import project.spring.quanlysach.domain.dto.OrderDTO;
import project.spring.quanlysach.domain.dto.ProductFromCart;

@RestApiV0
public class OrderController {
    @Autowired
    private IOrderService iOrderService;

    @PostMapping(UrlConstant.Order.ADD_PRODUCT_TO_CART)
    @ApiOperation("Add product to card")
    public ResponseEntity<?> addProductToCart(@RequestBody OrderDTO orderDTO) {
        return VsResponseUtil.ok(iOrderService.addProductToCart(orderDTO));
    }

    @DeleteMapping(UrlConstant.Order.DELETE_PRODUCT_FROM_CART)
    @ApiOperation("Delete product from card")
    public ResponseEntity<?> deleteProductFromCard(@RequestBody ProductFromCart productFromCart) {
        return VsResponseUtil.ok(iOrderService.deleteProductFromCart(productFromCart));
    }

    @GetMapping(UrlConstant.Order.SHOW_ALL_PRODUCT_IN_CART_OF_CUSTOMER)
    @ApiOperation("Show all product in cart of customer")
    public ResponseEntity<?> showAllProductInCart(@PathVariable("customerId") int customerId) {
        return VsResponseUtil.ok(iOrderService.showAllProductInCartOfCustomer(customerId));
    }

    @PutMapping(UrlConstant.Order.UPDATE_CARD_OF_CUSTOMER)
    @ApiOperation("Update cart of customer")
    public ResponseEntity<?> updateCartOfCustomer(@RequestBody OrderDTO orderDTO) {
        return VsResponseUtil.ok(iOrderService.updateCart(orderDTO));
    }

    @GetMapping(UrlConstant.Order.SHOW_ALL_PRODUCT_IN_CART)
    @ApiOperation("Show all product in cart")
    public ResponseEntity<?> showAllProductInCart(@RequestParam(value = "page", required = false) Integer page) {
        return VsResponseUtil.ok(iOrderService.showAllProductInCart(page, CommonConstant.SIZE_OFF_PAGE));
    }

    @PostMapping(UrlConstant.Order.PAYMENT_ORDER)
    @ApiOperation("Payment order successful")
    public ResponseEntity<?> paymentOrders(@RequestBody ProductFromCart productFromCart) {
        return VsResponseUtil.ok(iOrderService.paymentOrders(productFromCart.getProductId(),
                productFromCart.getProductId()));
    }
}
