package model;

import java.text.NumberFormat;
import java.util.Locale;

public class Constant {
    public final static String EN_CHARS =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
                    "abcdefghijklmnopqrstuvwxyz" +
                    "0123456789" +
                    ".,;!?()[]{}:<>-_=/+*^%$#@&";

    public static final int NUM_OF_ITEMS_A_PAGE = 20;

    public static final int AVAI_CODE = 1;
    public static final int USED_CODE = 0;
    public static final int EXPIRED_CODE = -1;
    public static final int WRONG_CODE = -2;

    public static final int UN_VERIFIED_USER = 0;
    public static final int VERIFIED_USER = 1;
    public static final int TEMP_LOCKED_USER = -1;
    public static final int BANNED_USER = -2 ;

    public static final int ACTIVE = 1;
    public static final int HIDE = 0;
    public static final int LOCK = -1;
    public static final int DELETE = -99;

    public static final int ALL = 0;
    public static final int SMARTPHONE_CATEGORY = 1;
    public static final int TABLET_CATEGORY = 2;
    public static final int LAPTOP_CATEGORY = 3;

    public static String getCategoryName(int in) {
        switch (in) {
            case SMARTPHONE_CATEGORY: return "Smartphone";
            case TABLET_CATEGORY: return "Tablet";
            case LAPTOP_CATEGORY: return "Laptop";
            default: return "Unknown";
        }
    }

    public static final String THUMBNAIL_DIR = "/assets/img/thumbnail"; // Relative path
    public static final String PRODUCT_DETAIL_IMG_DIR = "/assets/img/product";

    public static final int MAIN = 1;
    public static final int UNMAIN = 0;
    public static final int ONLINE = 2;

    public static final int UNEXIST = -1;

    public static final int PROMINENCE = 1;
    public static final int NEW = 2;
    public static final int SALE = 3;
    public static final int PRICE_UP = 4;
    public static final int PRICE_DOWN = 5;
    public static final int DEFAULT = 0;

    public static final int WAITING = 0;
    public static final int CONFIRM = 1;
    public static final int DELIVERY = 2;
    public static final int COMPLETE = 3;
    public static final int CANCEL = 10;

    public static final String UNDEFINED = "Không xác định";

    public static final int OFFSET = 20;

    public static String formatPrice(double price) {
        NumberFormat numberFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
        String priceString = numberFormat.format(price);
        return priceString;
    }

    public static String getStatusString(int status) {
        switch (status) {
            case WAITING: return "Chờ xác nhận";
            case CONFIRM: return "Xác nhận";
            case DELIVERY: return "Vận chuyển";
            case COMPLETE: return "Hoàn thành";
            case CANCEL: return "Hủy";
        }
        return UNDEFINED;
    }








}
