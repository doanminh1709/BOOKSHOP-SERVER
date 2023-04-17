package project.spring.quanlysach.adapter.web.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.spring.quanlysach.adapter.web.base.RestApiV0;
import project.spring.quanlysach.adapter.web.base.VsResponseUtil;
import project.spring.quanlysach.application.constants.UrlConstant;
import project.spring.quanlysach.application.services.IStatisticalService;

@RestApiV0
public class StatisticalController {

    @Autowired
    private IStatisticalService impStatisticalService;

    //Todo: Statistical Product
    @GetMapping(UrlConstant.Statistical.STATISTICAL_TOTAL_PRODUCT_SELL_IN_DAY)
    @ApiOperation("Show information about statistical product sell in day")
    public ResponseEntity<?> statisticalProductSellInDay(@RequestParam("date") String date) {
        return VsResponseUtil.ok(impStatisticalService.statisticalProductSellInDay(date));
    }

    @GetMapping(UrlConstant.Statistical.STATISTICAL_TOTAL_PRODUCT_SELL_IN_MONTH)
    @ApiOperation("Show information about statistical product sell each type in month")
    public ResponseEntity<?> statisticalProductSellInMonth(@RequestParam(value = "month", required = false) String month) {
        return VsResponseUtil.ok(impStatisticalService.statisticalProductInMonth(month));
    }

    @GetMapping(UrlConstant.Statistical.STATISTICAL_TOTAL_PRODUCT_SELL_IN_YEAR)
    @ApiOperation("Show information about statistical sell product each type in year")
    public ResponseEntity<?> statisticalProductInYear(@RequestParam(value = "year") int year) {
        return VsResponseUtil.ok(impStatisticalService.statisticalProductInYear(year));
    }

    //Todo : Statistical User
    @GetMapping(UrlConstant.Statistical.STATISTICAL_USER_USING_MONEY_IN_MONTH)
    @ApiOperation("Show information about statistical user using money in month")
    public ResponseEntity<?> statisticalUserUsingMoneyInMonth(@RequestParam(value = "n") int n,
                                                              @RequestParam(value = "month") String month) {
        return VsResponseUtil.ok(impStatisticalService.statisticalUserUsingMoneyInMonth(n, month));
    }

    @GetMapping(UrlConstant.Statistical.STATISTICAL_USER_USING_MONEY_IN_YEAR)
    @ApiOperation("Show information about statistical user using money in year")
    public ResponseEntity<?> statisticalUserUsingMoneyInYear(@RequestParam(value = "n") int n,
                                                             @RequestParam(value = "year", required = false) int year) {
        return VsResponseUtil.ok(impStatisticalService.statisticalUserUsingMoneyInYear(n, year));
    }

    @GetMapping(UrlConstant.Statistical.STATISTICAL_USER_BOUGHT_MOST_IN_MONTH)
    @ApiOperation("Show information about statistical user bought most in month")
    public ResponseEntity<?> statisticalUserBoughtMostInMonth(@RequestParam(value = "n") int n,
                                                              @RequestParam(value = "month") String month) {
        return VsResponseUtil.ok(impStatisticalService.statisticalUserBoughtMostInMonth(n, month));
    }

    @GetMapping(UrlConstant.Statistical.STATISTICAL_USER_BOUGHT_MOST_IN_YEAR)
    @ApiOperation("Show information about statistical user bought most in year")
    public ResponseEntity<?> statisticalUserBoughtMostInYear(@RequestParam(value = "n") int n,
                                                             @RequestParam(value = "year", required = false) int year) {
        return VsResponseUtil.ok(impStatisticalService.statisticalUserBoughtMostInYear(n, year));
    }

}
