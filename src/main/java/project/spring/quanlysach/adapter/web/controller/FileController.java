package project.spring.quanlysach.adapter.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.spring.quanlysach.adapter.web.base.RestApiV0;
import project.spring.quanlysach.adapter.web.base.VsResponseUtil;
import project.spring.quanlysach.application.constants.UrlConstant;
import project.spring.quanlysach.application.excel.IFileService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestApiV0
public class FileController {
    @Autowired
    private IFileService iFileService;
    /* export file    */

    @GetMapping(UrlConstant.File.EXPORT_FILE_STATISTICAL)
    public ResponseEntity<?> exportToExcelFileToCustomerData(HttpServletResponse response,
                                                             @RequestParam(value = "n") int n,
                                                             @RequestParam(value = "month") String month) throws IOException {
        return VsResponseUtil.ok(iFileService.exportToExcelFileToCustomerData(response, n, month));
    }

    @GetMapping(UrlConstant.File.EXPORT_FILE_ACCOUNT_CUSTOMER)
    public ResponseEntity<?> exportToExcelFileAccountCustomer(HttpServletResponse response) throws IOException {
        return VsResponseUtil.ok(iFileService.exportToExcelFileInformationAccountCustomer(response));
    }

    @GetMapping(UrlConstant.File.EXPORT_FILE_PRODUCT)
    public ResponseEntity<?> exportToExcelFileInfoProduct(HttpServletResponse response) throws IOException {
        return VsResponseUtil.ok(iFileService.exportToExcelFileInfoAllProduct(response));
    }

    /* write file    */
    @GetMapping(UrlConstant.File.WRITE_EXCEL_FILE_USER_USED_MONEY_IN_MONTH)
//    @PreAuthorize("hasAnyRole('ADMIN','MANAGE')")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> writeExcelFileStatisticalUserUsingMoneyInMonth(@RequestParam(value = "n") int n,
                                                                            @RequestParam(value = "month") String month) {
        return VsResponseUtil.ok(iFileService.writeExcelFileStatisticalUserUsedMoneyInMonth(n, month));
    }

    @GetMapping(UrlConstant.File.WRITE_EXCEL_FILE_PRODUCT_SELL_IN_TIME)
    public ResponseEntity<?> writeExcelFileStatisticalProductSellInTime(@RequestParam(value = "day", required = false) String day,
                                                                        @RequestParam(value = "month", required = false) String month) {
        return VsResponseUtil.ok(iFileService.writeExcelFileStatisticalProductSellInTime(day, month));
    }


    @GetMapping(UrlConstant.File.WRITE_EXCEL_FILE_USER_USED_MONEY_IN_YEAR)
    public ResponseEntity<?> writeExcelFileStatisticalUserUsingMoneyInYear(@RequestParam(value = "n") int n,
                                                                           @RequestParam(value = "year") int year) {
        return VsResponseUtil.ok(iFileService.writeExcelFileStatisticalUserUsedMoneyInYear(n, year));
    }

    @GetMapping(UrlConstant.File.WRITE_EXCEL_FILE_STATISTICAL_PRODUCT_IN_YEAR)
    public ResponseEntity<?> writeExcelFileStatisticalProductInYear(@RequestParam("year") int year) {
        return VsResponseUtil.ok(iFileService.writeExcelFileStatisticalProductInYear(year));
    }

    @GetMapping(UrlConstant.File.WRITE_EXCEL_FILE_STATISTICAL_PRODUCT_BOUGHT_MOST_IN_MONTH)
    public ResponseEntity<?> writeExcelFileStatisticalUserBoughtMostInMonth(@RequestParam(value = "n") int n,
                                                                            @RequestParam("month") String month) {
        return VsResponseUtil.ok(iFileService.writeExcelFileStatisticalProductUserBoughtMostInMonth(n, month));
    }

    @GetMapping(UrlConstant.File.WRITE_EXCEL_FILE_STATISTICAL_PRODUCT_BOUGHT_MOST_IN_YEAR)
    public ResponseEntity<?> writeExcelFileStatisticalUserBoughtMostInYear(@RequestParam(value = "n") int n,
                                                                           @RequestParam("year") int year) {
        return VsResponseUtil.ok(iFileService.writeExcelFileStatisticalProductUserBoughtMostInYear(n, year));
    }

}