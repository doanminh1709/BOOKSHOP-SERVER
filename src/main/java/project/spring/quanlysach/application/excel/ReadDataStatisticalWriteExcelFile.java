package project.spring.quanlysach.application.excel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.usermodel.*;
import project.spring.quanlysach.domain.entity.Product;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class ReadDataStatisticalWriteExcelFile implements IExcelFile {

    List<Product> productList;
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    public ReadDataStatisticalWriteExcelFile(List<Product> productList) {
        this.workbook = new XSSFWorkbook();
        this.sheet = workbook.createSheet("products");
        this.productList = productList;
    }

    @Override//Write title
    public void writeTitle() {
        //Working title in row 0
        XSSFRow row = sheet.createRow(0);//row 0 is title row

        //Change style cell and font
        CellStyle cellStyle = workbook.createCellStyle();//create a object cell
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setColor(Font.COLOR_RED);
        font.setFontHeight(16);
        cellStyle.setFont(font);

        List<String> listTitleProduct = new ArrayList<>(Arrays.asList(
                "ProductId", "Name", "Seo title", "Status", "Price", "Promotion price"
                , "Vat", "Quantity"
        ));
        for (int i = 0; i < listTitleProduct.size(); i++) {
            XSSFCell cell = row.createCell(i);//create new cell in each title
            cell.setCellType(CellType.STRING);
            cell.setCellValue(listTitleProduct.get(i));
            cell.setCellStyle(cellStyle);//ap style to ech row and ech cell
            sheet.autoSizeColumn(0);//method auto format
        }
    }

    @Override
    public void writeData() {
        int cnt = 1;
        for (Product product : productList) {
            XSSFRow row = sheet.createRow(cnt);
            XSSFCell cell = row.createCell(0);//start row 0
            cell.setCellValue(product.getId().toString());

            cell = row.createCell(1);
            cell.setCellValue(product.getName());

            cell = row.createCell(2);
            cell.setCellValue(product.getSeoTitle());

            cell = row.createCell(3);
            cell.setCellValue(product.getStatus());

            cell = row.createCell(4);
            cell.setCellValue(product.getPrice());

            cell = row.createCell(5);
            cell.setCellValue(product.getPromotionPrice());

            cell = row.createCell(6);
            cell.setCellValue(product.getVat());


            cell = row.createCell(7);
            cell.setCellValue(product.getQuantity());
            cnt++;
        }
    }

    @Override
    public void export(HttpServletResponse response) throws IOException {
        writeTitle();
        writeData();
        //has request print file from client
        //Using ServletOutputStream to sent content to workbook
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
}
