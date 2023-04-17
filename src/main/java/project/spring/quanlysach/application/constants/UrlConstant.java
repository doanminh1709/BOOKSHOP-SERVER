package project.spring.quanlysach.application.constants;

public class UrlConstant {
    private static final String ADMIN = "/admin";
    private static final String USER = "/user";
    private static final String OPERATOR = "/operator";
    private static final String ALL = "/all";


    public static class Category {
        public static final String CREATE_CATEGORY = ADMIN + "/new_category";
        public static final String UPDATE_CATEGORY = ADMIN + "/update_category/{id}";
        public static final String DELETE_CATEGORY = ADMIN + "/delete/{id}";
        public static final String UPLOAD_CATEGORY_DATA_FROM_EXCEL = OPERATOR + "/upload_category_data";
        private static final String PRE_FIX = "/category";
        public static final String DATA_CATEGORY = PRE_FIX;
        public static final String GET_CATEGORY = PRE_FIX + "/get_category/{id}";

        public Category() {
        }
    }

    public static class Brand {
        public static final String CREATE_BRAND = ADMIN + "/new_brand";
        public static final String UPDATE_BRAND = ADMIN + "/update_brand/{id}";
        public static final String DELETE_BRAND = ADMIN + "/delete_brand" + "/{id}";
        public static final String UPLOAD_BRAND_DATA_FROM_EXCEL = OPERATOR + "/upload_brand_data";
        private static final String PRE_FIX = "/brand";
        public static final String DATA_BRAND = PRE_FIX;
        public static final String GET_BRAND = PRE_FIX + "/{id}";


        public Brand() {
        }
    }

    public static class Cate {
        public static final String CREATE_CATE = ADMIN + "/new_cate";
        public static final String UPDATE_CATE = ADMIN + "/update_cate/{id}";
        public static final String DELETE_CATE = ADMIN + "/delete_cate/{id}";
        public static final String UPLOAD_CATE_DATA_FROM_EXCEL = OPERATOR + "/upload_cate_data";
        private static final String PRE_FIX = "/cate";
        public static final String DATA_CATE = PRE_FIX;
        public static final String GET_CATE = PRE_FIX + "/{id}";


        public Cate() {
        }
    }

    public static class Tag {
        public static final String CREATE_TAG = ADMIN + "/new_tag";
        public static final String UPDATE_TAG = ADMIN + "/update_tag/{id}";
        public static final String DELETE_TAG = ADMIN + "/delete_tag/{id}";
        public static final String UPLOAD_TAG_DATA_FROM_EXCEL = OPERATOR + "/upload_tag_data";
        private static final String PRE_FIX = "/tag";
        public static final String DATA_TAG = PRE_FIX;
        public static final String GET_TAG = PRE_FIX + "/{id}";

        public Tag() {
        }
    }

    public static class Post {
        public static final String DATA_POST_LATEST_NEW = OPERATOR + "/new_feed";
        public static final String FILTER_DATE_BY_CATE = OPERATOR + "/filterPostByCate";
        public static final String FILTER_DATE_BY_BRAND = OPERATOR + "/filterPostByBrand/{brandId}";
        public static final String CREATE_POST = OPERATOR + "/new_post";
        public static final String UPDATE_POST = OPERATOR + "/update_post/{id}";
        public static final String DELETE_POST = ADMIN + "/delete_post/{id}";
        public static final String SHOW_ALL_POST_OF_BRAND_OBOUT_CATE = "/show_all_post_of_brand_obout_cate/{brandId}/{cateId}";
        private static final String PRE_FIX = "/post";
        public static final String DATA_POST = OPERATOR + PRE_FIX;
        public static final String GET_POST = PRE_FIX + "/{id}";

        public Post() {
        }
    }

    public static class PostComment {
        public static final String CREATE_POST_COMMENT = ALL + "/new_post_comment";
        public static final String GET_COMMENT_OF_POST = ALL + "/get_all_comment_of_post/{postId}";
        public static final String GET_COMMENT_OF_CUSTOMER = OPERATOR + "/get_comment_of_customer/{customerId}";
        public static final String EDIT_COMMENT = ALL + "/edit_comment/{id}";
        public static final String REMOVE_COMMENT = ALL + "/remove_comment/{id}";
        public static final String GET_LEAST_NEW_COMMENT_OF_POST = ALL + "/new_comment_of_post/{postId}";
        public static final String SHOW_ALL_COMMENT_OF_CUSTOMER_IN_POST = OPERATOR +
                "/show_all_comment_of_customer_in_post/{postId}/{customerId}";
        private static final String PRE_FIX = "/get_all_comment";
        public static final String GET_ALL_COMMENT = PRE_FIX;
        public static final String GET_COMMENT_BY_ID = PRE_FIX + "/{id}";

        public PostComment() {
        }
    }

    public static class Customer {
        public static final String SEARCH_CUSTOMER_BY_NAME = OPERATOR + "/search_customer_by_name";
        public static final String SEARCH_CUSTOMER_BY_ADDRESS_CONTACT = OPERATOR + "/search_customer_by_city";
        public static final String GET_CUSTOMER_BY_ID = ALL + "/get_customer_by_id/{id}";
        public static final String SHOW_LIST_CUSTOMER_SORT_BY_NAME = OPERATOR + "/soft_asc_customer_by_name";
        public static final String EDIT_CUSTOMER_BY_ID = USER + "/update_info_customer/{id}";
        public static final String REMOVE_CUSTOMER_BY_ID = ADMIN + "/remove_customer/{id}";
        private static final String PRE_FIX = "/customer";
        public static final String GET_ALL_CUSTOMER = OPERATOR + PRE_FIX;

        public Customer() {
        }
    }

    public static class Product {
        public static final String CREATE_PRODUCT = ADMIN + "/new_product";
        public static final String UPDATE_PRODUCT = ADMIN + "/update_product/{id}";
        public static final String REMOVE_PRODUCT = ADMIN + "/remove_product/{id}";
        public static final String SEARCH_PRODUCT_BY_NAME = "/search_product_by_name";
        public static final String SEARCH_PRODUCT_BY_STATUS = "/search_product_by_status";
        public static final String GET_ALL_PRODUCT_BY_BRAND = "/get_all_product_by_brand/{brandId}";
        public static final String GET_ALL_PRODUCT_BY_CATEGORY = "/get_all_product_by_category/{categoryId}";
        public static final String READ_DATA_ON_EXCEL_FILE = OPERATOR + "/write_product_data_from_excel";
        private static final String PRE_FIX = "/product";
        public static final String GET_ALL_PRODUCT = PRE_FIX;
        public static final String GET_PRODUCT_BY_ID = PRE_FIX + "/{id}";
        public static final String GET_ALL_PRODUCT_ASC = PRE_FIX + "/soft_asc_by_price";
        public static final String GET_ALL_PRODUCT_DESC = PRE_FIX + "/soft_desc_by_price";
        public static final String GET_PRODUCT_BETWEEN_PRICE = PRE_FIX + "/search_between_price";

        public Product() {
        }
    }

    public static class Contact {
        public static final String GET_CONTACT_BY_ID = ALL + "/{id}";
        public static final String GET_CONTACT_BY_CUSTOMER = ALL + "/get_all_contact_of_customer/{customerId}";
        public static final String CREATE_NEW_CONTACT = USER + "/new_contact";
        public static final String UPDATE_CONTACT = USER + "/update_contact/{id}";
        public static final String DELETE_CONTACT = USER + "/delete_contact/{id}";
        private static final String PRE_FIX = "/contact";
        public static final String GET_ALL_CONTACT = OPERATOR + PRE_FIX;

        public Contact() {
        }
    }

    public static class Image {
        public static final String EDIT_IMAGE_BY_ID = ADMIN + "/edit_image/{id}";
        public static final String DELETE_IMAGE_BY_ID = ADMIN + "/delete_image/{id}";
        public static final String UPLOAD_IMAGE = ADMIN + "/upload_image";
        private static final String PRE_FIX = "/image";
        public static final String SHOW_ALL = OPERATOR + PRE_FIX;
        public static final String GET_IMAGE_BY_ID = OPERATOR + PRE_FIX + "/{id}";
        public static final String FIND_ALL_IMAGE_OF_POST = PRE_FIX + "/post/{id}";
        public static final String FIND_ALL_IMAGE_OF_PRODUCT = PRE_FIX + "/product/{id}";

        public Image() {
        }
    }

    public static class Supplier {
        public static final String CREATE_NEW_SUPPLIER = ADMIN + "/new_supplier";
        public static final String SEARCH_SUPPLIER_BY_NAME = OPERATOR + "/search_supplier_by_name";
        public static final String UPDATE_SUPPLIER_BY_ID = ADMIN + "/update_supplier/{id}";
        public static final String DELETE_SUPPLIER_BY_ID = ADMIN + "/delete_supplier/{id}";
        public static final String GET_ALL_SUPPLIER = OPERATOR + "/supplier";
        private static final String PRE_FIX = "/supplier";
        public static final String GET_SUPPLIER_BY_ID = OPERATOR + PRE_FIX + "/{id}";
        public static final String SEARCH_SUPPLIER_BY_ADDRESS = PRE_FIX + "/search_supplier_by_address";
        public static final String READ_DATA_ON_EXCEL_FILE = OPERATOR + "/write_supplier_data_from_excel";

        public Supplier() {
        }
    }

    public static class InvoiceDetail {
        public static final String CREATE_INVOICE_DETAIL = ADMIN + "/new_invoice_detail";
        public static final String UPDATE_INVOICE_DETAIL = ADMIN + "/update_invoice_detail";
        public static final String DELETE_INVOICE_DETAIL = ADMIN + "/delete_invoice_detail";
        private static final String PRE_FIX = "/invoice_detail";
        public static final String SHOW_ALL_INVOICE_DETAIL = OPERATOR + PRE_FIX;
        public static final String SEARCH_INVOICE_DETAIL_BY_NAME = OPERATOR + PRE_FIX + "/search_by_name";
        public static final String SORT_INVOICE_DETAIL_DESC = OPERATOR + PRE_FIX + "/sort_by_price";
        public static final String SHOW3_PRODUCT_ALREADY_THE_MOST = OPERATOR + PRE_FIX + "/show_3_product";

        public InvoiceDetail() {
        }
    }

    public static class Invoice {
        public static final String SEARCH_BY_SUPPLIER_ID = OPERATOR + "/search/{supplierId}";
        public static final String NEW_INVOICE = ADMIN + "/create_new_invoice";
        public static final String UPDATE_INVOICE_BY_ID = ADMIN + "/update_invoice/{id}";
        public static final String REMOVE_INVOICE_BY_ID = ADMIN + "/remove_invoice/{id}";
        private static final String PRE_FIX = "/invoice";
        public static final String SHOW_INVOICE_LEAST_TIME = OPERATOR + PRE_FIX + "/least_time";
        public static final String SHOW_ALL_INVOICE = OPERATOR + PRE_FIX;
        public static final String SEARCH_BY_STATUS = OPERATOR + PRE_FIX + "/search_by_status";
        public static final String GET_INVOICE_BY_ID = OPERATOR + PRE_FIX + "/{id}";

        public Invoice() {
        }
    }

    public static class Order {
        public static final String ADD_PRODUCT_TO_CART = USER + "/add_to_card";
        public static final String DELETE_PRODUCT_FROM_CART = USER + "/delete_product_from_card";
        public static final String SHOW_ALL_PRODUCT_IN_CART_OF_CUSTOMER = ALL + "/getProduct/{customerId}";
        public static final String UPDATE_CARD_OF_CUSTOMER = USER + "/update_card";
        public static final String PAYMENT_ORDER = USER + "/payment_order";
        private static final String PRE_FIX = "/order";
        public static final String SHOW_ALL_PRODUCT_IN_CART = OPERATOR + PRE_FIX;

        public Order() {
        }

    }

    public static class Statistical {
        private static final String PRE_FIX = "/statistical";
        public static final String STATISTICAL_TOTAL_PRODUCT_SELL_IN_DAY = OPERATOR + PRE_FIX + "/in_day";
        public static final String STATISTICAL_TOTAL_PRODUCT_SELL_IN_MONTH = OPERATOR + PRE_FIX + "/in_month";
        public static final String STATISTICAL_TOTAL_PRODUCT_SELL_IN_YEAR = OPERATOR + PRE_FIX + "/in_year";
        public static final String STATISTICAL_USER_USING_MONEY_IN_MONTH = OPERATOR + PRE_FIX + "/user_using_money_in_month";
        public static final String STATISTICAL_USER_USING_MONEY_IN_YEAR = OPERATOR + PRE_FIX + "/user_using_money_in_year";
        public static final String STATISTICAL_USER_BOUGHT_MOST_IN_MONTH = OPERATOR + PRE_FIX + "/user_bought_most_in_month";
        public static final String STATISTICAL_USER_BOUGHT_MOST_IN_YEAR = OPERATOR + PRE_FIX + "/user_bought_most_in_year";

        public Statistical() {
        }
    }

    public static class Auth {

        private static final String PRE_FIX = "/no_auth";
        public static final String SIGN_UP = PRE_FIX + "/signUp";
        public static final String CONFIRM = PRE_FIX + "/confirm";
        public static final String SIGN_IN = PRE_FIX + "/signIn";
        public static final String FORGOT_PASSWORD = PRE_FIX + "/no_auth_forgot_password/{customerId}";
        public static final String SIGN_OUT = ALL + PRE_FIX + "/no_auth_signOut";
        public static final String REFRESH_TOKEN = ALL + PRE_FIX + "/refreshToken";

        public Auth() {
        }
    }

    public static class File {
        public static final String EXPORT_FILE_STATISTICAL = "/export_file_statistical";
        public static final String EXPORT_FILE_ACCOUNT_CUSTOMER = "/export_file_account_customer";
        public static final String EXPORT_FILE_PRODUCT = "/export_file_product";
        public static final String WRITE_EXCEL_FILE_USER_USED_MONEY_IN_MONTH = ADMIN + "/write-excel-file-user-using-money-in-month";
        public static final String WRITE_EXCEL_FILE_PRODUCT_SELL_IN_TIME = OPERATOR + "/write-excel-file-product-sell-in-time";
        public static final String WRITE_EXCEL_FILE_USER_USED_MONEY_IN_YEAR = OPERATOR + "/write-excel-file-user-using-money-in-year";
        public static final String WRITE_EXCEL_FILE_STATISTICAL_PRODUCT_IN_YEAR = OPERATOR + "/write-excel-file-statistical-product-in-year";
        public static final String WRITE_EXCEL_FILE_STATISTICAL_PRODUCT_BOUGHT_MOST_IN_MONTH = OPERATOR + "/write-excel-file-product-bought-most-in-month";
        public static final String WRITE_EXCEL_FILE_STATISTICAL_PRODUCT_BOUGHT_MOST_IN_YEAR = OPERATOR + "/write-excel-file-product-bought-most-in-year";

        public File() {
        }
    }


}
    

