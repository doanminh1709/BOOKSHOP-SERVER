package project.spring.quanlysach.application.excel;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import project.spring.quanlysach.application.constants.DevMessageConstant;
import project.spring.quanlysach.application.repo.*;
import project.spring.quanlysach.domain.entity.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ReadFileExcel {
    @Autowired
    BrandRepository brandRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private CateRepository cateRepository;
    @Autowired
    private TagRepository tagRepository;

    public List<Brand> readDataFromBrandInExcel(File file) throws IOException {

        FileInputStream fileInputStream = new FileInputStream(file);
        Workbook workbook = new XSSFWorkbook(fileInputStream);
        Sheet sheet = workbook.getSheetAt(1);
        DataFormatter dataFormatter = new DataFormatter();

        List<Brand> brands = new ArrayList<>();
        Iterator<Row> iterator = sheet.iterator();
        while (iterator.hasNext()) {

            Row currrentRow = iterator.next();
            String name = null;
            int yearOfEstablishment = -1;
            if (ObjectUtils.isEmpty(dataFormatter.formatCellValue(currrentRow.getCell(0)))) {
                name = "";
            } else if (String.valueOf(currrentRow.getCell(1)) == null) {
                yearOfEstablishment = 0;
            } else {
                break;
            }
            Brand brand = new Brand(name, yearOfEstablishment);
            brands.add(brand);
        }
        return brands;
    }

    public List<Category> readDataFromCategoryInExcel(File file) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
        Sheet sheet = workbook.getSheetAt(2);
        DataFormatter dataFormatter = new DataFormatter();
        List<Category> categories = new ArrayList<>();
        for (Row row : sheet) {
            String des, detail, name, seoTitle;
            int parentId;
            des = dataFormatter.formatCellValue(row.getCell(0));
            detail = dataFormatter.formatCellValue(row.getCell(1));
            name = dataFormatter.formatCellValue(row.getCell(2));
            seoTitle = dataFormatter.formatCellValue(row.getCell(3));

            if (ObjectUtils.isEmpty(dataFormatter.formatCellValue(row.getCell(4)))) {
                parentId = 0;
            } else {
                parentId = Integer.parseInt(dataFormatter.formatCellValue(row.getCell(4)));
            }
            Category category = new Category(des, detail, name, seoTitle, parentId);
            categories.add(category);

        }
        return categories;
    }

    public List<Product> readDataFromProductInExcel(File file) throws IOException, ParseException {
        FileInputStream fileInputStream = new FileInputStream(file);
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(fileInputStream);
        Sheet sheet = xssfWorkbook.getSheetAt(0);//a excel file has many sheet
        List<Product> products = new ArrayList<>();
        DataFormatter dataFormatter = new DataFormatter();
        for (Row currrentRow : sheet) {
            try {
                Product product = new Product(
                        dataFormatter.formatCellValue(currrentRow.getCell(0)),
                        dataFormatter.formatCellValue(currrentRow.getCell(1)),
                        dataFormatter.formatCellValue(currrentRow.getCell(2)),
                        Float.parseFloat(dataFormatter.formatCellValue(currrentRow.getCell(3))),
                        Float.parseFloat(dataFormatter.formatCellValue(currrentRow.getCell(4))),
                        Float.parseFloat(dataFormatter.formatCellValue(currrentRow.getCell(5))),
                        Long.parseLong(dataFormatter.formatCellValue(currrentRow.getCell(6))),
                        Integer.parseInt(dataFormatter.formatCellValue(currrentRow.getCell(7))),
                        Integer.parseInt(dataFormatter.formatCellValue(currrentRow.getCell(8)))
                );
                products.add(product);
            } catch (Exception e) {
                break;
            }
        }
        return products;
    }

    public List<Cate> readCateDataFromExcelFile(File file) throws IOException {
        FileInputStream inputStream = new FileInputStream(file);
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(3);
        DataFormatter dataFormatter = new DataFormatter();

        List<Cate> cateList = new ArrayList<>();
        Iterator<Row> rowIterator = sheet.iterator();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Cate cate = new Cate(
                    dataFormatter.formatCellValue(row.getCell(0)),
                    dataFormatter.formatCellValue(row.getCell(1)),
                    Integer.parseInt(dataFormatter.formatCellValue(row.getCell(2)))
            );
            cateList.add(cate);
        }
        return cateList;
    }

    public List<Tag> readTagDataFromExcelFile(File file) throws IOException {
        FileInputStream inputStream = new FileInputStream(file);
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(4);
        DataFormatter dataFormatter = new DataFormatter();

        List<Tag> tagList = new ArrayList<>();
        Iterator<Row> rowIterator = sheet.iterator();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Tag tag = new Tag(
                    dataFormatter.formatCellValue(row.getCell(0)),
                    dataFormatter.formatCellValue(row.getCell(1))
            );
            tagList.add(tag);
        }
        return tagList;
    }

    public String saveTagData(File file) throws IOException {
        List<Tag> tags = readTagDataFromExcelFile(file);
        if (tags.size() > 0) {
            tagRepository.saveAll(tags);
            return DevMessageConstant.Common.UPLOAD_DATA_FROM_EXCEL_FILE_SUCCESS;
        }
        return DevMessageConstant.Common.UPLOAD_DATA_FROM_EXCEL_FILE_FAILED;
    }

    public String saveCateData(File file) throws IOException {
        List<Cate> cates = readCateDataFromExcelFile(file);
        if (cates.size() > 0) {
            cateRepository.saveAll(cates);
            return DevMessageConstant.Common.UPLOAD_DATA_FROM_EXCEL_FILE_SUCCESS;
        }
        return DevMessageConstant.Common.UPLOAD_DATA_FROM_EXCEL_FILE_FAILED;
    }

    public String saveProductData(File file) throws IOException, ParseException {
        List<Product> products = readDataFromProductInExcel(file);
        if (products.size() > 0) {
            productRepository.saveAll(products);
            return DevMessageConstant.Common.UPLOAD_DATA_FROM_EXCEL_FILE_SUCCESS;
        }
        return DevMessageConstant.Common.UPLOAD_DATA_FROM_EXCEL_FILE_FAILED;
    }

    public List<Supplier> readDataSupplierFromExcelFile(File file) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(fileInputStream);
        Sheet sheet = xssfWorkbook.getSheetAt(3);
        DataFormatter dataFormatter = new DataFormatter();
        Iterator<Row> rowIterator = sheet.iterator();
        List<Supplier> supplierList = new ArrayList<>();
        while (rowIterator.hasNext()) {
            Row currentRow = rowIterator.next();
            Supplier supplier = new Supplier(
                    dataFormatter.formatCellValue(currentRow.getCell(0)),
                    dataFormatter.formatCellValue(currentRow.getCell(1)),
                    dataFormatter.formatCellValue(currentRow.getCell(2)),
                    dataFormatter.formatCellValue(currentRow.getCell(3))
            );
            supplierList.add(supplier);
        }
        return supplierList;
    }

    public String saveSupplier(File file) throws IOException {
        List<Supplier> supplierList = readDataSupplierFromExcelFile(file);
        for (Supplier item : supplierList) {
            Supplier supplier = supplierRepository.findSupplierByName(item.getName());
            if (supplier != null) {
                return String.format(DevMessageConstant.Supplier.DUPLICATE_NAME, item.getName());
            }
        }
        supplierRepository.saveAll(supplierList);
        return DevMessageConstant.Supplier.ADD_SUCCESS;
    }

    public String saveBrandData(File file) throws IOException {
        List<Brand> brands = readDataFromBrandInExcel(file);
        if (brands.size() > 0) {
            brandRepository.saveAll(brands);
            return DevMessageConstant.Common.UPLOAD_DATA_FROM_EXCEL_FILE_SUCCESS;
        }
        return DevMessageConstant.Common.UPLOAD_DATA_FROM_EXCEL_FILE_FAILED;
    }

    public String saveCategoryData(File file) throws IOException {
        List<Category> categories = readDataFromCategoryInExcel(file);
        if (categories.size() > 0) {
            categoryRepository.saveAll(categories);
            return DevMessageConstant.Common.UPLOAD_DATA_FROM_EXCEL_FILE_SUCCESS;
        }
        return DevMessageConstant.Common.UPLOAD_DATA_FROM_EXCEL_FILE_FAILED;
    }

}
