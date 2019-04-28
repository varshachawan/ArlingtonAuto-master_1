package com.arunika.arlingtonauto.DATABASE;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * This is the entire singleton SQLITE database.
 */
public class DBHelper extends SQLiteOpenHelper {
    //DATABASE INFO
    private static final String DB_NAME = "ArlingtonAuto.db";
    private static final int DB_VERSION = 1;
    /**
     * DB Table & Column Names
     */
    //USER TABLE COLUMNS
    public static final String TABLE_USER = "USER";
    public static final String COLUMN_USER_ID = "userId";
    public static final String COLUMN_USER_FIRST_NAME = "firstName";
    public static final String COLUMN_USER_LAST_NAME = "lastName";
    public static final String COLUMN_USER_UTA_ID = "utaId";
    public static final String COLUMN_USER_USERNAME = "username";
    public static final String COLUMN_USER_PASSWORD = "password";
    public static final String COLUMN_USER_EMAIL = "email";
    public static final String COLUMN_USER_ROLE = "role";
    public static final String COLUMN_USER_IS_REVOKED = "isRevoked";
    public static final String COLUMN_USER_AAC_MEMBERSHIP = "aacMembership";
    //CAR TABLE COLUMNS
    public static final String TABLE_CAR = "CAR";
    public static final String COLUMN_CAR_ID = "carId";
    public static final String COLUMN_CAR_NAME = "carName";
    public static final String COLUMN_CAR_CAPACITY = "capacity";
    public static final String COLUMN_CAR_WEEKDAY_RATE = "weekdayRate";
    public static final String COLUMN_CAR_WEEKEND_RATE = "weekendRate";
    public static final String COLUMN_CAR_WEEKLY_RATE = "weeklyRate";
    public static final String COLUMN_CAR_DAILY_GPS = "dailyGps";
    public static final String COLUMN_CAR_DAILY_SIRIUS = "dailySirius";
    public static final String COLUMN_CAR_DAILY_ONSTAR = "dailyOnstar";
    //RESERVATION TABLE COLUMNS
    public static final String TABLE_RESERVATION = "RESERVATION";
    public static final String COLUMN_RESERVATION_ID = "resId";
    public static final String COLUMN_RESERVATION_CAR_ID = "rCarId";
    public static final String COLUMN_RESERVATION_USER_ID = "rUserId";
    public static final String COLUMN_RESERVATION_CHECK_OUT = "checkOut";
    public static final String COLUMN_RESERVATION_RETURN = "return";
    public static final String COLUMN_RESERVATION_HAS_GPS = "hasGps";
    public static final String COLUMN_RESERVATION_HAS_ONSTAR = "hasOnstar";
    public static final String COLUMN_RESERVATION_HAS_SIRIUS = "hasSirius";
    public static final String COLUMN_RESERVATION_TOTAL_PRICE = "totalPrice";
    /**
     * SQL Create Statements
     */
    //CREATE USER TABLE
    private static final String SQL_CREATE_TABLE_USER = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_USER_FIRST_NAME + " TEXT NOT NULL,"
            + COLUMN_USER_LAST_NAME + " TEXT NOT NULL,"
            + COLUMN_USER_UTA_ID + " TEXT NOT NULL,"
            + COLUMN_USER_USERNAME + " TEXT NOT NULL UNIQUE,"
            + COLUMN_USER_PASSWORD + " TEXT NOT NULL,"
            + COLUMN_USER_EMAIL + " TEXT NOT NULL,"
            + COLUMN_USER_AAC_MEMBERSHIP + " INTEGER NOT NULL,"
            + COLUMN_USER_ROLE + " TEXT NOT NULL,"
            + COLUMN_USER_IS_REVOKED + " INTEGER NOT NULL"
            + ");";
    //CREATE USER CAR
    private static final String SQL_CREATE_TABLE_CAR = "CREATE TABLE " + TABLE_CAR + "("
            + COLUMN_CAR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_CAR_NAME + " TEXT NOT NULL UNIQUE,"
            + COLUMN_CAR_CAPACITY + " INTEGER NOT NULL,"
            + COLUMN_CAR_WEEKDAY_RATE + " DOUBLE NOT NULL,"
            + COLUMN_CAR_WEEKEND_RATE + " DOUBLE NOT NULL,"
            + COLUMN_CAR_WEEKLY_RATE + " DOUBLE NOT NULL,"
            + COLUMN_CAR_DAILY_GPS + " DOUBLE NOT NULL,"
            + COLUMN_CAR_DAILY_SIRIUS + " DOUBLE NOT NULL,"
            + COLUMN_CAR_DAILY_ONSTAR + " DOUBLE NOT NULL"
            + ")";
    //CREATE USER RESERVATION
    private static final String SQL_CREATE_TABLE_RESERVATION = "CREATE TABLE " + TABLE_RESERVATION + "("
            + COLUMN_RESERVATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_RESERVATION_USER_ID + " INTEGER NOT NULL,"
            + COLUMN_RESERVATION_CAR_ID + " INTEGER NOT NULL,"
            + COLUMN_RESERVATION_CHECK_OUT + " DATETIME NOT NULL,"
            + COLUMN_RESERVATION_RETURN + " DATETIME NOT NULL,"
            + COLUMN_RESERVATION_HAS_GPS + " INTEGER NOT NULL,"
            + COLUMN_RESERVATION_HAS_ONSTAR + " INTEGER NOT NULL,"
            + COLUMN_RESERVATION_HAS_SIRIUS + " INTEGER NOT NULL,"
            + COLUMN_RESERVATION_TOTAL_PRICE + " DOUBLE NOT NULL,"
            + "FOREIGN KEY ("+COLUMN_RESERVATION_USER_ID+") REFERENCES "+TABLE_USER+"("+COLUMN_USER_ID+") ,"
            + "FOREIGN KEY ("+COLUMN_RESERVATION_CAR_ID+") REFERENCES "+TABLE_CAR+"("+COLUMN_CAR_ID+") "
            + ");";

    private static DBHelper instance;
    public static synchronized DBHelper getInstance(Context context) {
        /**
         *  You must call getInstance() to get DBHelper object
         *  This ensures only ONE instance will ever be created/used throughout app.
         *  SINGLETON PATTERN
         */
        if (instance == null) {
            instance = new DBHelper(context);
        }
        return instance;
    }

    private DBHelper(Context context) {
        /**
         * Constructor must be private to prevent direct instantiation.
         * make call to static method "getInstance()" instead.
         * This ensures Singleton Pattern.
         */
        super(context,DB_NAME,null,DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_USER);
        db.execSQL(SQL_CREATE_TABLE_CAR);
        db.execSQL(SQL_CREATE_TABLE_RESERVATION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CAR);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESERVATION);
        onCreate(db);
    }
}
