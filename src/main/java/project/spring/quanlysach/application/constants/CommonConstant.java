package project.spring.quanlysach.application.constants;

import java.text.SimpleDateFormat;

public class CommonConstant {

    public static int SIZE_OFF_PAGE = 10;
    public static final String FORMAT_DATE_PATTERN = "dd/MM/yyyy";
    public static final String FORMAT_DATE_PATTERN_DETAIL = "dd/MM/yyyy HH:mm:ss";
    public static final SimpleDateFormat FORMAT_DATE = new SimpleDateFormat(FORMAT_DATE_PATTERN);
    public static final SimpleDateFormat FORMAT_DATE_DETAIL = new SimpleDateFormat(FORMAT_DATE_PATTERN_DETAIL);
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_BRAND_ID = "brand_id";
    public static final String COLUMN_CATEGORY_ID = "category_id";
    public static final String COLUMN_CATE_ID = "cate_id";
    public static final String COLUMN_PRODUCT_ID = "product_id";
    public static final String COLUMN_POST_ID = "post_id";
    public static final String COLUMN_TAG_ID = "tag_id";
    public static final String COLUMN_SUPPLIER_ID = "supplier_id";
    public static final String COLUMN_INVOICE_ID = "invoice_id";
    public static final String COLUMN_CUSTOMER_ID = "customer_id";
    public static final String COLUMN_ROLE_ID = "role_id";

    public static Integer ZERO_VALUE = 0;
}
