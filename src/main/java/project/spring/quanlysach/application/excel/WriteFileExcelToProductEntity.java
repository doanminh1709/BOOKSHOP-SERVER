package project.spring.quanlysach.application.excel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.usermodel.*;
import project.spring.quanlysach.domain.dto.StatisticalProduct;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class WriteFileExcelToProductEntity implements IExcelFile {
    List<StatisticalProduct> statisticalProducts;
    private XSSFWorkbook workbook;
    private XSSFSheet xssfSheet;

    public WriteFileExcelToProductEntity(List<StatisticalProduct> statisticalProducts) {
        this.workbook = new XSSFWorkbook();
        this.xssfSheet = workbook.createSheet("statistical_products");
        this.statisticalProducts = statisticalProducts;
    }

    @Override
    public void writeTitle() {
        //Create data in line 0 of sheet
        XSSFRow xssfRow = xssfSheet.createRow(0);
        //Style for each cell
        CellStyle cellStyle = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(16);
        font.setBold(true);
        font.setColor(Font.COLOR_RED);
        //save change
        cellStyle.setFont(font);

        //Create cells title
        List<String> listTitle = new ArrayList<>(Arrays.asList("Product id", "Product name", "Count"));
        for (int i = 0; i < listTitle.size(); i++) {
            // Create each in row and upset value for it
            XSSFCell cell = xssfRow.createCell(i);
            cell.setCellValue(listTitle.get(i));
            cell.setCellType(CellType.STRING);
            cell.setCellStyle(cellStyle);
            xssfSheet.autoSizeColumn(0);
        }
    }

    @Override
    public void writeData() {
        int cnt = 1;
        for (StatisticalProduct item : statisticalProducts) {
            XSSFRow row = xssfSheet.createRow(cnt);
            XSSFCell cell = row.createCell(0);

            cell.setCellValue(item.getProductId());

            cell = row.createCell(1);
            cell.setCellValue(item.getNameProduct());

            cell = row.createCell(2);
            cell.setCellValue(item.getCount());
            cnt++;//For loop in each line
        }
    }

    @Override
    public void export(HttpServletResponse response) throws IOException {
        writeTitle();
        writeData();
        //sent content to workbook
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
}
