package project.spring.quanlysach.application.constants;

public class DevMessageConstant {

    public static final class Common {
        //Define statement common
        public static final String NOT_FOUND_OBJECT_BY_ID = "Can not found %s with id = %s";
        public static final String OBJECT_IS_EMPTY = "This object is empty ";
        public static final String LOGOUT = "Logout successfully";
        public static final String WRITE_DATA_SUCCESS = "Write data to excel file success";
        public static final String WRITE_DATA_FAILED = "Write data to excel file success";
        public static final String SEARCH_ONLY_FIELD = "You can only search on one field";
        public static final String CHECK_FIELD = "You need to enter data at least 1 of 2 the field ";
        public static final String GET_REFRESH_TOKEN = "Get refresh token failed!";
        public static final String EXPORT_FILE = "Progress export file success";
        public static final String REGISTER_FAILED = "Register failed!";
        public static final String REGISTER_SUCCESS = "Sent request register account success!";
        public static final String CONFIRM_SUCCESSFUL = "Confirm account successful!";
        public static final String TOKEN_EXPIRED = "This token is expired!";
        public static final String CONFIRMED = "This account confirmed! ";
        public static final String DATA_WAS_DELETE = "This object id = %s was delete";
        public static final String DATE_WAS_DISABLE = "This object id = %s was disable";
        public static final String PAYMENT_ORDER_SUCCESS = "Payment this order success";
        public static final String CONFIRM_ACCOUNT = "Please, confirm link registration send to your email!";
        public static final String GET_PASSWORD = "New password send to your email. Please check again!";
        public static final String TOKEN_IS_EXPRESS = "Please, register again!";
        public static final String ID_EMPTY = "Id is empty";
        public static final String EMAIL_NOT_VALID = "This email  '%s' is not valid ";
        public static final String NOT_VALID = "Count always greater than 0";
        public static final String NO_DATA_SELECTED = "No data when select result";
        public static final String ACCOUNT_IS_NOT_ENABLE = "This account is not enable";
        public static final String NOT_FOUND_CONFIRM_TOKEN = "Not found ConfirmationToken by token";
        public static final String DUPLICATE_NAME = "Duplicate name = %s";
        public static final String EXITS_NAME = "This name : %s is exits";
        public static final String EXITS_PHONE = "This phone : %s is exits";
        public static final String EXITS_USERNAME = "This username : %s is exits";
        public static final String OBJECT_NOT_FOUND = "Not found this object compatible foreign key";
        public static final String OBJECT_IS_EXITS = "This object is exits";
        public static final String REFRESH_TOKEN_EXPIRED = "Refresh token is expired . Pleas sign in";
        public static final String EMPTY = "File is empty";
        public static final String NOTIFICATION_UPDATE_SUCCESS = "Update object by id is successful!";
        public static final String NOTIFICATION_UPDATE_FAILED = "Update object by id is failed!";
        public static final String UPLOAD_DATA_FROM_EXCEL_FILE_SUCCESS = "Upload data from excel file success!";
        public static final String UPLOAD_DATA_FROM_EXCEL_FILE_FAILED = "Upload data from excel file failed!";
        public static final String NOTIFICATION_DELETE_FAILED = "Remove object by id is failed!";
        public static final String NOTIFICATION_DELETE_SUCCESS = "Remove object by id is success!";

        private Common() {
        }
    }

    public static final class Order {
        public static final String ADD_PRODUCT_TO_ORDER_SUCCESS = "Add product to order success";
        public static final String ADD_NUMBER_OF_COUNT_NOT_VALID = "The number of items you want to add exceeds the quantity in stock, no more can be added";
        public static final String ADD_PRODUCT_TO_ORDER_FAILED = "Add product to order failed";

        public Order() {
        }
    }

    public static final class Supplier {
        public static final String DUPLICATE_NAME = "This name : %s is exits!";

        public static final String ADD_SUCCESS = "Add data from excel file to database success!";
    }

    public static final class Customer {
        public static final String NOT_FOUND_CUSTOMER_BY_USERNAME = "Can not find user by username = %s";
        public static final String LOCKED_ACCOUNT = "You are locked account successful!";
        public static final String NOT_FOUND_CUSTOMER_BY_EMAIL = "Can not find user by email = %s";
        public static final String CAN_NOT_CUSTOMER_BY_USERNAME = "Can not create user because duplicate username = %s";
        public static final String CAN_NOT_CUSTOMER_BY_EMAIL = "Can not create user because duplicate email = %s";
        public static final String NOT_FOUND_CUSTOMER_BY_TOKEN_PASS = "Can not find user by token reset pass = %s";

        private Customer() {
        }
    }

}
