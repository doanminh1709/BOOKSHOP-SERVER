package project.spring.quanlysach.application.excel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.usermodel.*;
import project.spring.quanlysach.domain.dto.StatisticalProduct;

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
public class WriteFileProductDataExcelToStatistic implements IExcelFile {
    List<StatisticalProduct> statisticalProductList;

    private XSSFWorkbook workbook;
    private XSSFSheet xssfSheet;

    public WriteFileProductDataExcelToStatistic(List<StatisticalProduct> statisticalProductList) {
        this.workbook = new XSSFWorkbook();
        this.xssfSheet = workbook.createSheet("statistical_users");
        this.statisticalProductList = statisticalProductList;
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

        List<String> listTitle = new ArrayList<>(Arrays.asList("Product id", "Product name", "Count"));
        for (int i = 0; i < listTitle.size(); i++) {
            XSSFCell cell = row.createCell(i);
            cell.setCellType(CellType.STRING);
            cell.setCellValue(listTitle.get(i));
            cell.setCellStyle(cellStyle);
            xssfSheet.autoSizeColumn(0);
        }
    }

    @Override
    public void writeData() {
        int cnt = 1;
        for (StatisticalProduct item : statisticalProductList) {
            XSSFRow row = xssfSheet.createRow(cnt);
            XSSFCell cell = row.createCell(0);

            cell.setCellValue(item.getProductId());

            cell = row.createCell(1);
            cell.setCellValue(item.getNameProduct());

            cell = row.createCell(2);
            cell.setCellValue(item.getCount());
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
