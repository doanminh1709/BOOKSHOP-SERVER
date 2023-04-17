package project.spring.quanlysach.application.services;

import project.spring.quanlysach.domain.dto.StatisticalProduct;
import project.spring.quanlysach.domain.dto.StatisticalUser;

import java.util.List;

public interface IStatisticalService {

    List<StatisticalProduct> statisticalProductSellInDay(String day);
    List<StatisticalProduct> statisticalProductInMonth(String month);

    List<StatisticalUser> statisticalUserUsingMoneyInYear(int n, int  year);

    List<StatisticalUser> statisticalUserUsingMoneyInMonth(int n,String month);

    List<StatisticalProduct> statisticalProductInYear(int year);

    //ok
    List<StatisticalProduct> statisticalUserBoughtMostInMonth(int n, String month);

    List<StatisticalProduct> statisticalUserBoughtMostInYear(int n, int year);




}
