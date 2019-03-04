package vn.asiantech.base;

import vn.asiantech.object.Account;

/**
 * @author at-hangtran
 */
public final class Constant {
    public static final String PORTAL_URL = "http://portal-stg.asiantech.vn";
    public static final String HOME_PAGE_URL = PORTAL_URL + "/homepage";
    public static final String LOGIN_PAGE_URL = PORTAL_URL + "/auth/login";

    public static final int DEFAULT_TIME_OUT = 10;
    public static final int MAXIMUM_TIME_OUT = 20;

    //init Account
    public static final Account[] ACCOUNT_LOGIN = new Account[]{
            new Account("stg.tien.hoang@asiantech.vn", "Abc123@@"),
            new Account("stg.tu.le.2@asiantech.vn", "Abc123@@"),
            new Account("stg.thien.dang2@asiantech.vn", "Abc123@@"),
            new Account("stg.hang.nguyen@asiantech.vn", "Abc123@@"),
            new Account("stg.lam.le2@asiantech.vn", "Abc123@@"),
            new Account("automation-01@asiantech.vn", "Abc123@@"),
            new Account("automation-02@asiantech.vn", "Abc123@@")
    };

    private Constant() {
        // No-op
    }
}
