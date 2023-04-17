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
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class WriteDataProductToExcelFile implements IExcelFile {

    private List<Product> productList;
    private XSSFWorkbook workbook;
    private XSSFSheet xssfSheet;

    public WriteDataProductToExcelFile(List<Product> productList) {
        this.productList = productList;
        this.workbook = new XSSFWorkbook();
        this.xssfSheet = workbook.createSheet("Account_product");
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

        List<String> listTitle = new ArrayList<>(Arrays.asList("Product id", "product name", "Seo title",
                "Status", "Price", "Promotion price", "Vat", "Quantity", "Brand id", "Category id"));
        for (int i = 0; i < listTitle.size(); i++) {
            XSSFCell cell = row.createCell(i);
            cell.setCellType(CellType.STRING);
            cell.setCellValue(listTitle.get(i));
            cell.setCellStyle(cellStyle);
            xssfSheet.autoSizeColumn(0);
            xssfSheet.autoSizeColumn(i); // tự động điều chỉnh độ rộng cột theo nội dung
            //đặt độ rộng cột bằng chiều dài lớn nhất giữa nội dung và chiều rộng hiện tại
            xssfSheet.setColumnWidth(i, Math.max(xssfSheet.getColumnWidth(i), listTitle.get(i).length() * 256));
        }
    }

    @Override
    public void writeData() {
        int cnt = 1;
        for (Product item : productList) {
            XSSFRow row = xssfSheet.createRow(cnt);
            XSSFCell cell = row.createCell(0);

            cell.setCellValue(item.getId());

            cell = row.createCell(1);
            cell.setCellValue(item.getName());

            cell = row.createCell(2);
            cell.setCellValue(item.getSeoTitle());

            cell = row.createCell(3);
            cell.setCellValue(item.getStatus());

            cell = row.createCell(4);
            cell.setCellValue(item.getPrice());

            cell = row.createCell(5);
            cell.setCellValue(item.getPromotionPrice());

            cell = row.createCell(6);
            cell.setCellValue(item.getVat());

            cell = row.createCell(7);
            cell.setCellValue(item.getQuantity());

            cell = row.createCell(8);
            cell.setCellValue(item.getBrand().getId());

            cell = row.createCell(9);
            cell.setCellValue(item.getCategory().getId());
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