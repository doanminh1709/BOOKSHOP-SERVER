package project.spring.quanlysach.application.excel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.usermodel.*;
import project.spring.quanlysach.domain.dto.StatisticalUser;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
//@Service
public class WriteFileCustomerDataExcelToStatistic implements IExcelFile {
    List<StatisticalUser> statisticalUserList;

    private XSSFWorkbook workbook;
    private XSSFSheet xssfSheet;

    public WriteFileCustomerDataExcelToStatistic(List<StatisticalUser> statisticalUserList) {
        this.workbook = new XSSFWorkbook();
        this.xssfSheet = workbook.createSheet("statistical_users");
        this.statisticalUserList = statisticalUserList;
    }


    @Override
    public void writeTitle() {
        XSSFRow row = xssfSheet.createRow(0);

        CellStyle cellStyle = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setColor(Font.COLOR_RED);
        font.setFontHeight(16);
        cellStyle.setFont(font);

        List<String> listTitle = new ArrayList<>(Arrays.asList("Customer id", "Customer name", "Total money"));
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
        for (StatisticalUser item : statisticalUserList) {
            XSSFRow row = xssfSheet.createRow(cnt);
            XSSFCell cell = row.createCell(0);

            cell.setCellValue(item.getCustomerId());

            cell = row.createCell(1);
            cell.setCellValue(item.getCustomerName());

            cell = row.createCell(2);
            cell.setCellValue(item.getTotalMoney());
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
