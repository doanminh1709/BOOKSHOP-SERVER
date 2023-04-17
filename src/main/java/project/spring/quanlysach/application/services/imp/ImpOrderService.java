package project.spring.quanlysach.application.services.imp;

import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import project.spring.quanlysach.application.constants.DevMessageConstant;
import project.spring.quanlysach.application.constants.UserMessConstant;
import project.spring.quanlysach.application.mapper.OrderMapper;
import project.spring.quanlysach.application.repo.CustomerRepository;
import project.spring.quanlysach.application.repo.OrderRepository;
import project.spring.quanlysach.application.repo.ProductRepository;
import project.spring.quanlysach.application.services.IOrderService;
import project.spring.quanlysach.config.exception.VsException;
import project.spring.quanlysach.domain.dto.OrderDTO;
import project.spring.quanlysach.domain.dto.ProductFromCart;
import project.spring.quanlysach.domain.entity.Customer;
import project.spring.quanlysach.domain.entity.OrderTable;
import project.spring.quanlysach.domain.entity.Product;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
@Scope(proxyMode = ScopedProxyMode.DEFAULT)
@Transactional
public class ImpOrderService implements IOrderService {

    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;

    private final OrderMapper orderMapper = Mappers.getMapper(OrderMapper.class);

    public ImpOrderService(ProductRepository productRepository, CustomerRepository customerRepository, OrderRepository orderRepository) {
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;

    }

    @Override
    public String addProductToCart(OrderDTO orderDTO) {
        Optional<Product> findProduct = productRepository.findById(orderDTO.getProductId());
        Optional<Customer> findCustomer = customerRepository.findById(orderDTO.getCustomerId());
        if (findCustomer.isPresent() && findProduct.isPresent()) {
            OrderTable checkOrder = orderRepository.findOrderByCustomerIdAndProductId(orderDTO.getCustomerId(), orderDTO.getProductId());
            if (orderDTO.getCount() <= 0) {
                throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                        String.format(DevMessageConstant.Common.NOT_VALID));
            } else if (orderDTO.getCount() > findProduct.get().getQuantity()) {
                return DevMessageConstant.Order.ADD_NUMBER_OF_COUNT_NOT_VALID;
            }
            if (checkOrder == null) {
                OrderTable newOrder = orderMapper.toOrderMapper(orderDTO, orderDTO.getProductId(), orderDTO.getCustomerId());
                orderRepository.save(newOrder);
                return DevMessageConstant.Order.ADD_PRODUCT_TO_ORDER_SUCCESS;
            } else {
                int previousCount = checkOrder.getCount();
                checkOrder.setCount(previousCount + orderDTO.getCount());
                orderRepository.save(checkOrder);
                return DevMessageConstant.Order.ADD_PRODUCT_TO_ORDER_SUCCESS;
            }
        } else {
            if (findCustomer.isEmpty() && findProduct.isEmpty()) {
                throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                        String.format(DevMessageConstant.Common.OBJECT_NOT_FOUND));
            } else if (findProduct.isEmpty()) {
                throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                        String.format(DevMessageConstant.Common.NOT_FOUND_OBJECT_BY_ID, "product", orderDTO.getProductId()));
            } else {
                throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                        String.format(DevMessageConstant.Common.NOT_FOUND_OBJECT_BY_ID, "customer", orderDTO.getCustomerId()));
            }
        }
    }

    @Override
    public String deleteProductFromCart(ProductFromCart productFromCart) {
        Optional<Product> findProduct = productRepository.findById(productFromCart.getProductId());
        Optional<Customer> findCustomer = customerRepository.findById(productFromCart.getCustomerId());
        if (findCustomer.isPresent() && findProduct.isPresent()) {
            OrderTable checkOrder = orderRepository.findOrderByCustomerIdAndProductId(productFromCart.getCustomerId(),
                    productFromCart.getProductId());
            if (checkOrder == null) {
                throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                        String.format(DevMessageConstant.Common.OBJECT_NOT_FOUND));
            } else {
                orderRepository.deleteOrdersByProductIdAndCustomerId(productFromCart.getProductId(),
                        productFromCart.getCustomerId());
                return DevMessageConstant.Common.NOTIFICATION_DELETE_SUCCESS;
            }
        } else {
            if (findCustomer.isEmpty() && findProduct.isEmpty()) {
                throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                        String.format(DevMessageConstant.Common.OBJECT_NOT_FOUND));
            } else if (findProduct.isEmpty()) {
                throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                        String.format(DevMessageConstant.Common.NOT_FOUND_OBJECT_BY_ID, "product",
                                productFromCart.getProductId()));
            } else {
                throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                        String.format(DevMessageConstant.Common.NOT_FOUND_OBJECT_BY_ID, "customer",
                                productFromCart.getCustomerId()));
            }
        }
    }

    @Override
    public String updateCart(OrderDTO orderDTO) {
        Optional<Product> findProduct = productRepository.findById(orderDTO.getProductId());
        Optional<Customer> findCustomer = customerRepository.findById(orderDTO.getCustomerId());
        if (findCustomer.isPresent() && findProduct.isPresent()) {
            OrderTable checkOrder = orderRepository.findOrderByCustomerIdAndProductId(orderDTO.getCustomerId(),
                    orderDTO.getProductId());
            if (orderDTO.getCount() < 0) {
                throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                        DevMessageConstant.Common.NOT_VALID);
            }
            if (checkOrder == null) {
                throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                        String.format(DevMessageConstant.Common.OBJECT_NOT_FOUND));
            } else {
                checkOrder.setCount(orderDTO.getCount());
                return DevMessageConstant.Common.NOTIFICATION_UPDATE_SUCCESS;
            }
        } else {
            if (findCustomer.isEmpty() && findProduct.isEmpty()) {
                throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                        String.format(DevMessageConstant.Common.OBJECT_NOT_FOUND));
            } else if (findProduct.isEmpty()) {
                throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                        String.format(DevMessageConstant.Common.NOT_FOUND_OBJECT_BY_ID, "product",
                                orderDTO.getProductId()));
            } else {
                throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                        String.format(DevMessageConstant.Common.NOT_FOUND_OBJECT_BY_ID, "customer",
                                orderDTO.getCustomerId()));
            }
        }
    }

    @Override
    public List<OrderTable> showAllProductInCartOfCustomer(int customerId) {
        Optional<Customer> findCustomer = customerRepository.findById(customerId);
        if (findCustomer.isEmpty()) {
            throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                    String.format(DevMessageConstant.Common.NOT_FOUND_OBJECT_BY_ID, "customer", customerId));
        } else {
            List<OrderTable> findOrderByCustomer = orderRepository.findOrderByCustomerId(customerId);
            if (findOrderByCustomer.size() > 0) {
                return findOrderByCustomer;
            } else {
                throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                        String.format(DevMessageConstant.Common.NO_DATA_SELECTED));
            }
        }
    }

    @Override
    public String paymentOrders(int customerId, int productId) {
        Optional<Product> findProduct = productRepository.findById(productId);
        Optional<Customer> findCustomer = customerRepository.findById(customerId);
        if (findCustomer.isPresent() && findProduct.isPresent()) {
            OrderTable checkOrder = orderRepository.findOrderByCustomerIdAndProductId(customerId, productId);
            if (checkOrder != null) {
                checkOrder.setDelivered(Boolean.TRUE);
                checkOrder.setDeliveredDate(new Timestamp(System.currentTimeMillis()));
                return DevMessageConstant.Common.PAYMENT_ORDER_SUCCESS;
            } else {
                throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                        String.format(DevMessageConstant.Common.OBJECT_NOT_FOUND));
            }
        } else {
            if (findCustomer.isEmpty() && findProduct.isEmpty()) {
                throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                        String.format(DevMessageConstant.Common.OBJECT_NOT_FOUND));
            } else if (findProduct.isEmpty()) {
                throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                        String.format(DevMessageConstant.Common.NOT_FOUND_OBJECT_BY_ID, "product", productId));
            } else {
                throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                        String.format(DevMessageConstant.Common.NOT_FOUND_OBJECT_BY_ID, "customer", customerId));
            }
        }
    }

    @Override
    public List<OrderTable> showAllProductInCart(Integer page, Integer size) {
        if (page != null) {
            return orderRepository.findAll(PageRequest.of(page.intValue(), size)).getContent();
        } else {
            return orderRepository.findAll();
        }
    }

}
