package project.spring.quanlysach.application.services.imp;

import org.springframework.stereotype.Service;
import project.spring.quanlysach.application.constants.DevMessageConstant;
import project.spring.quanlysach.application.constants.UserMessConstant;
import project.spring.quanlysach.application.services.IStatisticalService;
import project.spring.quanlysach.config.exception.VsException;
import project.spring.quanlysach.domain.dto.StatisticalProduct;
import project.spring.quanlysach.domain.dto.StatisticalUser;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static project.spring.quanlysach.application.utils.DateTimeUntil.formatDateStandard;

@Service
public class ImpStatisticalService implements IStatisticalService {

    private static final String url = "jdbc:mysql://localhost:3306/ManageBook";
    static String username = "root";
    static String password = "17092002";

    @Override
    public List<StatisticalProduct> statisticalProductSellInDay(String date) {
        List<StatisticalProduct> statisticalProducts = new ArrayList<>();
        String sql = "SELECT product_id , product.name , SUM(order_table.count) as TotalCount\n" +
                "FROM order_table inner join product \n" +
                "ON order_table.product_id = product.id\n" +
                "WHERE delivered = 1 AND DATE(delivered_date) = ?\n" +
                "GROUP BY product_id , product.name; ";
        Connection conn ;
        PreparedStatement ps ;
        try {
            date = formatDateStandard(date);
            conn = DriverManager.getConnection(url, username, password);
            ps = conn.prepareStatement(sql);
            //format date
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date time = outputFormat.parse(date);
            String formatDate = outputFormat.format(time);
            ps.setString(1, formatDate);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                StatisticalProduct statisticalProduct = new StatisticalProduct();
                statisticalProduct.setProductId(rs.getInt("product_id"));
                statisticalProduct.setNameProduct(rs.getString("name"));
                statisticalProduct.setCount(rs.getInt("TotalCount"));
                statisticalProducts.add(statisticalProduct);
            }
            if (statisticalProducts.isEmpty()) {
                throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                        DevMessageConstant.Common.OBJECT_IS_EMPTY);
            } else {
                return statisticalProducts;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return null;
    }


    @Override
    public List<StatisticalProduct> statisticalProductInMonth(String month) {
        List<StatisticalProduct> statisticalProducts = new ArrayList<>();
        String sql = "SELECT product_id , product.name , SUM(order_table.count) as TotalCount\n" +
                "FROM order_table inner join product \n" +
                "ON order_table.product_id = product.id\n" +
                "WHERE delivered = 1 AND MONTH(delivered_date) = ?\n" +
                "GROUP BY product_id , product.name; ";
        Connection conn;
        PreparedStatement ps;
        try {
            String formatMonth = "";
            if (month.length() == 1) {
                formatMonth = "0" + month;
            } else {
                formatMonth = month;
            }
            conn = DriverManager.getConnection(url, username, password);
            ps = conn.prepareStatement(sql);
            ps.setString(1, formatMonth);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                StatisticalProduct statisticalProduct = new StatisticalProduct();
                statisticalProduct.setProductId(rs.getInt("product_id"));
                statisticalProduct.setNameProduct(rs.getString("name"));
                statisticalProduct.setCount(rs.getInt("TotalCount"));
                statisticalProducts.add(statisticalProduct);
            }
            if (statisticalProducts.isEmpty()) {
                throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                        DevMessageConstant.Common.OBJECT_IS_EMPTY);
            } else {
                return statisticalProducts;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<StatisticalProduct> statisticalProductInYear(int year) {
        List<StatisticalProduct> statisticalProducts = new ArrayList<>();
        String sql = "SELECT product_id , product.name , SUM(order_table.count) as TotalCount\n" +
                "FROM order_table inner join product \n" +
                "ON order_table.product_id = product.id\n" +
                "WHERE delivered = 1 AND YEAR(delivered_date) = ?\n" +
                "GROUP BY product_id , product.name; ";
        Connection conn;
        PreparedStatement ps;
        try {
            conn = DriverManager.getConnection(url, username, password);
            ps = conn.prepareStatement(sql);
            ps.setInt(1, year);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                StatisticalProduct statisticalProduct = new StatisticalProduct();
                statisticalProduct.setProductId(rs.getInt("product_id"));
                statisticalProduct.setNameProduct(rs.getString("name"));
                statisticalProduct.setCount(rs.getInt("TotalCount"));
                statisticalProducts.add(statisticalProduct);
            }
            if (statisticalProducts.isEmpty()) {
                throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                        DevMessageConstant.Common.OBJECT_IS_EMPTY);
            } else {
                return statisticalProducts;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public List<StatisticalUser> statisticalUserUsingMoneyInMonth(int n, String month) {
        List<StatisticalUser> statisticalUsers = new ArrayList<>();
        String sql = "SELECT customer.id as CustomerId, customer.full_name as FullName," +
                " SUM(order_table.count * product.price) as TotalMoney\n" +
                "FROM customer INNER JOIN order_table \n" +
                "ON customer.id = order_table.customer_id\n" +
                "INNER JOIN product \n" +
                "ON product.id = order_table.product_id\n" +
                "WHERE MONTH(order_table.delivered_date) = ?\n" +
                "AND order_table.delivered = 1\n" +
                "GROUP BY customer.id , customer.full_name\n" +
                "ORDER BY SUM(order_table.count * product.price) DESC LIMIT ?;";
        Connection conn;
        PreparedStatement ps;
        try {
            String formatMonth;
            if (month.length() == 1) {
                formatMonth = "0" + month;
            } else {
                formatMonth = month;
            }
            conn = DriverManager.getConnection(url, username, password);
            ps = conn.prepareStatement(sql);
            ps.setString(1, formatMonth);
            ps.setInt(2, n);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                StatisticalUser statisticalUser = new StatisticalUser();
                statisticalUser.setCustomerId(rs.getInt("CustomerId"));
                statisticalUser.setCustomerName(rs.getString("FullName"));
                statisticalUser.setTotalMoney(rs.getInt("TotalMoney"));
                statisticalUsers.add(statisticalUser);
            }
            if (statisticalUsers.isEmpty()) {
                throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                        DevMessageConstant.Common.OBJECT_IS_EMPTY);
            } else {
                return statisticalUsers;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public List<StatisticalUser> statisticalUserUsingMoneyInYear(int n, int year) {
        List<StatisticalUser> statisticalUsers = new ArrayList<>();
        String sql = "SELECT customer.id as CustomerId, customer.full_name as FullName," +
                " SUM(order_table.count * product.price) as TotalMoney\n" +
                "FROM customer INNER JOIN order_table \n" +
                "ON customer.id = order_table.customer_id\n" +
                "INNER JOIN product \n" +
                "ON product.id = order_table.product_id\n" +
                "WHERE YEAR(order_table.delivered_date) = ?\n" +
                "AND order_table.delivered = 1\n" +
                "GROUP BY customer.id , customer.full_name\n" +
                "ORDER BY SUM(order_table.count * product.price) DESC LIMIT ?;";
        Connection conn;
        PreparedStatement ps;
        try {
            conn = DriverManager.getConnection(url, username, password);
            ps = conn.prepareStatement(sql);
            ps.setInt(1, year);
            ps.setInt(2, n);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                StatisticalUser statisticalUser = new StatisticalUser();
                statisticalUser.setCustomerId(rs.getInt("CustomerId"));
                statisticalUser.setCustomerName(rs.getString("FullName"));
                statisticalUser.setTotalMoney(rs.getInt("TotalMoney"));
                statisticalUsers.add(statisticalUser);
            }
            if (statisticalUsers.isEmpty()) {
                throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                        DevMessageConstant.Common.OBJECT_IS_EMPTY);
            } else {
                return statisticalUsers;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public List<StatisticalProduct> statisticalUserBoughtMostInMonth(int n, String month) {
        List<StatisticalProduct> statisticalProducts = new ArrayList<>();
        String sql = "SELECT customer.id as CustomerId , customer.full_name as FullName ,\n" +
                "COUNT(order_table.product_id) as TotalProduct\n" +
                "FROM customer INNER JOIN order_table \n" +
                "ON customer.id = order_table.customer_id\n" +
                "WHERE MONTH(order_table.delivered_date) = ?\n" +
                "AND order_table.delivered = 1\n" +
                "GROUP BY customer.id , customer.full_name\n" +
                "ORDER BY COUNT(order_table.product_id) DESC LIMIT ?;";
        Connection conn;
        PreparedStatement ps;
        try {
            String formatMonth = "";
            if (month.length() == 1) {
                formatMonth = "0" + month;
            } else {
                formatMonth = month;
            }
            conn = DriverManager.getConnection(url, username, password);
            ps = conn.prepareStatement(sql);
            ps.setString(1, formatMonth);
            ps.setInt(2, n);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                StatisticalProduct statisticalProduct = new StatisticalProduct();
                statisticalProduct.setProductId(rs.getInt("CustomerId"));
                statisticalProduct.setNameProduct(rs.getString("FullName"));
                statisticalProduct.setCount(rs.getInt("TotalProduct"));
                statisticalProducts.add(statisticalProduct);
            }
            if (statisticalProducts.isEmpty()) {
                throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                        DevMessageConstant.Common.OBJECT_IS_EMPTY);
            } else {
                return statisticalProducts;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<StatisticalProduct> statisticalUserBoughtMostInYear(int n, int year) {
        List<StatisticalProduct> statisticalProducts = new ArrayList<>();
        String sql = "SELECT customer.id as CustomerId , customer.full_name as FullName ,\n" +
                "COUNT(order_table.product_id) as TotalProduct\n" +
                "FROM customer INNER JOIN order_table \n" +
                "ON customer.id = order_table.customer_id\n" +
                "WHERE YEAR(order_table.delivered_date) = ?\n" +
                "AND order_table.delivered = 1\n" +
                "GROUP BY customer.id , customer.full_name\n" +
                "ORDER BY COUNT(order_table.product_id) DESC LIMIT ?;";
        Connection conn;
        PreparedStatement ps;
        try {
            conn = DriverManager.getConnection(url, username, password);
            ps = conn.prepareStatement(sql);
            ps.setInt(1, year);
            ps.setInt(2, n);
            ResultSet rs = ps.executeQuery();


            while (rs.next()) {
                StatisticalProduct statisticalProduct = new StatisticalProduct();
                statisticalProduct.setProductId(rs.getInt("CustomerId"));
                statisticalProduct.setNameProduct(rs.getString("FullName"));
                statisticalProduct.setCount(rs.getInt("TotalProduct"));
                statisticalProducts.add(statisticalProduct);
            }
            if (statisticalProducts.isEmpty()) {
                throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                        DevMessageConstant.Common.OBJECT_IS_EMPTY);
            } else {
                return statisticalProducts;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
