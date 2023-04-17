package project.spring.quanlysach.application.excel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.spring.quanlysach.application.constants.DevMessageConstant;
import project.spring.quanlysach.application.repo.CustomerRepository;
import project.spring.quanlysach.application.repo.ProductRepository;
import project.spring.quanlysach.application.services.IStatisticalService;
import project.spring.quanlysach.domain.dto.StatisticalProduct;
import project.spring.quanlysach.domain.dto.StatisticalUser;
import project.spring.quanlysach.domain.entity.Customer;
import project.spring.quanlysach.domain.entity.Product;

import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class IFileService {
    private final String path = "E:\\Project CV\\file_data_excel\\";
    @Autowired
    private IStatisticalService impStatisticalService;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ProductRepository productRepository;

    /* Export file */
    public String exportToExcelFileToCustomerData(HttpServletResponse response,
                                                  int n, String month) throws IOException {

        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        List<StatisticalUser> statisticalUserList = impStatisticalService.statisticalUserUsingMoneyInMonth(n, month);
        WriteFileCustomerDataExcelToStatistic excelExporter =
                new WriteFileCustomerDataExcelToStatistic(statisticalUserList);
        excelExporter.export(response);
        return DevMessageConstant.Common.EXPORT_FILE;
    }

    public String exportToExcelFileInformationAccountCustomer(HttpServletResponse response) throws IOException {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String currentTime = dateFormat.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=information_account_customer" + currentTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        List<Customer> listCustomer = customerRepository.findAll();
        WriteDataCustomerToExcelFile exportFile = new WriteDataCustomerToExcelFile(listCustomer);
        exportFile.export(response);
        return DevMessageConstant.Common.EXPORT_FILE;
    }

    public String exportToExcelFileInfoAllProduct(HttpServletResponse response) throws IOException {

        DateFormat dataFormatter = new SimpleDateFormat("dd-MM-yyyy");
        String currentDate = dataFormatter.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=export_file_info_product" + currentDate + ".xlsx";
        response.setHeader(headerKey, headerValue);
        List<Product> productList = productRepository.findAll();
        WriteDataProductToExcelFile exportFile = new WriteDataProductToExcelFile(productList);
        exportFile.export(response);
        return DevMessageConstant.Common.EXPORT_FILE;

    }


    /*Write file  */
    public String writeExcelFileStatisticalUserUsedMoneyInMonth(int n, String month) {
        try {
            List<StatisticalUser> statisticalUserList = impStatisticalService.statisticalUserUsingMoneyInMonth(n, month);
            WriteFileCustomerDataExcelToStatistic write = new WriteFileCustomerDataExcelToStatistic(statisticalUserList);
            write.writeTitle();
            write.writeData();

            String fileName = "Statistical-each-customer-using-in-month_" + month + ".xlsx";
            FileOutputStream outputStream = new FileOutputStream(path + fileName);
            write.getWorkbook().write(outputStream);
            write.getWorkbook().close();
            outputStream.close();
            return DevMessageConstant.Common.WRITE_DATA_SUCCESS;
        } catch (IOException e) {
            e.printStackTrace();
            return DevMessageConstant.Common.WRITE_DATA_FAILED;
        }
    }

    public String writeExcelFileStatisticalProductSellInTime(String day, String month) {
        try {
            if (month != null && day != null) {
                return DevMessageConstant.Common.SEARCH_ONLY_FIELD;
            }
            if (month == null && day == null) {
                return DevMessageConstant.Common.CHECK_FIELD;
            }
            if (day != null) {
                List<StatisticalProduct> statisticalProductsSellInDay = impStatisticalService.statisticalProductSellInDay(day);
                WriteFileExcelToProductEntity writeFile = new WriteFileExcelToProductEntity(statisticalProductsSellInDay);
                writeFile.writeTitle();
                writeFile.writeData();
                DateFormat dateFormat = new SimpleDateFormat("_yyyy-MM-dd");
                String currentDateTime = dateFormat.format(new Date());
                String fileName = "Statistical-sell-in-time" + currentDateTime + ".xlsx";
                //FileOutputStream : write data and InputStream : Collect data
                FileOutputStream outputStream = new FileOutputStream(path + fileName);
                writeFile.getWorkbook().write(outputStream);
                writeFile.getWorkbook().close();
                outputStream.close();
                return DevMessageConstant.Common.WRITE_DATA_FAILED;
            }
            if (month != null) {
                List<StatisticalProduct> statisticalProductSellInMonth = impStatisticalService.statisticalProductInMonth(month);
                WriteFileExcelToProductEntity writeFile = new WriteFileExcelToProductEntity(statisticalProductSellInMonth);
                writeFile.writeTitle();
                writeFile.writeData();
                String fileName = "Statistical-sell-in-month-" + month + ".xlsx";
                FileOutputStream fileOutputStream = new FileOutputStream(path + fileName);
                writeFile.getWorkbook().write(fileOutputStream);
                writeFile.getWorkbook().close();
                fileOutputStream.close();
                return DevMessageConstant.Common.WRITE_DATA_SUCCESS;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return DevMessageConstant.Common.WRITE_DATA_FAILED;
        }
        return null;
    }

    public String writeExcelFileStatisticalUserUsedMoneyInYear(int n, int year) {
        try {
            List<StatisticalUser> statisticalUserList = impStatisticalService.statisticalUserUsingMoneyInYear(n, year);
            WriteFileCustomerDataExcelToStatistic write = new WriteFileCustomerDataExcelToStatistic(statisticalUserList);
            write.writeTitle();
            write.writeData();

            String fileName = "Statistical-each-customer-using-in-year-" + year + ".xlsx";
            FileOutputStream outputStream = new FileOutputStream(path + fileName);
            write.getWorkbook().write(outputStream);
            write.getWorkbook().close();
            outputStream.close();
            return DevMessageConstant.Common.WRITE_DATA_SUCCESS;
        } catch (IOException e) {
            e.printStackTrace();
            return DevMessageConstant.Common.WRITE_DATA_FAILED;
        }
    }

    public String writeExcelFileStatisticalProductInYear(int year) {
        try {
            List<StatisticalProduct> statisticalProductsList = impStatisticalService.statisticalProductInYear(year);
            WriteFileProductDataExcelToStatistic write = new WriteFileProductDataExcelToStatistic(statisticalProductsList);
            write.writeTitle();
            write.writeData();
            String fileName = "Statistical-product-sell-in-year-" + year + ".xlsx";
            FileOutputStream outputStream = new FileOutputStream(path + fileName);
            write.getWorkbook().write(outputStream);
            write.getWorkbook().close();
            outputStream.close();
            return DevMessageConstant.Common.WRITE_DATA_SUCCESS;
        } catch (IOException e) {
            e.printStackTrace();
            return DevMessageConstant.Common.WRITE_DATA_FAILED;
        }
    }


    public String writeExcelFileStatisticalProductUserBoughtMostInMonth(int n, String month) {
        try {
            List<StatisticalProduct> statisticalProducts = impStatisticalService.statisticalUserBoughtMostInMonth(n, month);
            WriteFileExcelToProductEntity write = new WriteFileExcelToProductEntity(statisticalProducts);
            write.writeTitle();
            write.writeData();

            String fileName = "Statistical-product-user-bought-most-in-month" + month + ".xlsx";
            FileOutputStream outputStream = new FileOutputStream(path + fileName);
            write.getWorkbook().write(outputStream);
            write.getWorkbook().close();
            outputStream.close();
            return DevMessageConstant.Common.WRITE_DATA_SUCCESS;
        } catch (IOException e) {
            e.printStackTrace();
            return DevMessageConstant.Common.WRITE_DATA_FAILED;
        }
    }

    public String writeExcelFileStatisticalProductUserBoughtMostInYear(int n, int year) {
        try {
            List<StatisticalProduct> statisticalProducts = impStatisticalService.statisticalUserBoughtMostInYear(n, year);
            WriteFileExcelToProductEntity write = new WriteFileExcelToProductEntity(statisticalProducts);
            write.writeTitle();
            write.writeData();

            String fileName = "Statistical-product-user-bought-most-in-year" + year + ".xlsx";
            FileOutputStream outputStream = new FileOutputStream(path + fileName);
            write.getWorkbook().write(outputStream);
            write.getWorkbook().close();
            outputStream.close();
            return DevMessageConstant.Common.WRITE_DATA_SUCCESS;
        } catch (IOException e) {
            e.printStackTrace();
            return DevMessageConstant.Common.WRITE_DATA_FAILED;
        }
    }

}
