package project.spring.quanlysach.application.services;

import project.spring.quanlysach.domain.dto.ProductFromCart;
import project.spring.quanlysach.domain.dto.OrderDTO;
import project.spring.quanlysach.domain.dto.StatisticalProduct;
import project.spring.quanlysach.domain.entity.OrderTable;

import java.util.List;

public interface IOrderService {

    String addProductToCart(OrderDTO orderDTO);

    String deleteProductFromCart(ProductFromCart deleteProductFromCard);

    String updateCart(OrderDTO orderDTO);

    List<OrderTable> showAllProductInCartOfCustomer(int customer);

    List<OrderTable> showAllProductInCart(Integer page, Integer size);

    String paymentOrders(int customerId, int productId);


}
