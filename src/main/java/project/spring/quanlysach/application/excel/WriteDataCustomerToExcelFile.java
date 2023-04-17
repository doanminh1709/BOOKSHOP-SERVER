package project.spring.quanlysach.application.excel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.usermodel.*;
import project.spring.quanlysach.domain.entity.Customer;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class WriteDataCustomerToExcelFile implements IExcelFile {

    private List<Customer> customerList;
    private XSSFWorkbook workbook;
    private XSSFSheet xssfSheet;

    public WriteDataCustomerToExcelFile(List<Customer> customerList) {
        this.customerList = customerList;
        this.workbook = new XSSFWorkbook();
        this.xssfSheet = workbook.createSheet("Account_customer");
    }

    @Override
    public void writeTitle() {

        XSSFRow row = xssfSheet.createRow(0);
        //After create row , we create cell to css for cell
        CellStyle cellStyle = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setColor(Font.COLOR_RED);
        font.setFontHeight(16);
        cellStyle.setFont(font);

        List<String> listTitle = new ArrayList<>(Arrays.asList("Customer id", "Full name", "Phone",
                "Address", "Email", "Username"));
        for (int i = 0; i < listTitle.size(); i++) {
            XSSFCell cell = row.createCell(i);
            cell.setCellType(CellType.STRING);
            cell.setCellValue(listTitle.get(i));
            cell.setCellStyle(cellStyle);
            xssfSheet.autoSizeColumn(0);
            xssfSheet.autoSizeColumn(i);//automatic adjust the width of each column by content
            //set with of column equal max hight between content and width current
            xssfSheet.setColumnWidth(i, Math.max(xssfSheet.getColumnWidth(i), listTitle.get(i).length() * 256));
        }
    }

    @Override
    public void writeData() {
        int cnt = 1;
        for (Customer item : customerList) {
            XSSFRow row = xssfSheet.createRow(cnt);
            XSSFCell cell = row.createCell(0);

            cell.setCellValue(item.getId());

            cell = row.createCell(1);
            cell.setCellValue(item.getFullName());

            cell = row.createCell(2);
            cell.setCellValue(item.getPhone());

            cell = row.createCell(3);
            cell.setCellValue(item.getAddress());

            cell = row.createCell(4);
            cell.setCellValue(item.getEmail());

            cell = row.createCell(5);
            cell.setCellValue(item.getUsername());
            cnt++;
        }
    }

    @Override
    public void export(HttpServletResponse response) throws IOException {
        writeTitle();
        writeData();
        if (response != null) {//has request show file from client
            //Sent content to wordbook
            ServletOutputStream outputStream = response.getOutputStream();
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();
        } else {
            //Upset data
            FileOutputStream fileOutputStream = new FileOutputStream("file");
            workbook.write(fileOutputStream);
            workbook.close();
            fileOutputStream.close();
        }
    }
}