package com.arunika.arlingtonauto.DAO;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import com.arunika.arlingtonauto.DATABASE.DBHelper;
import com.arunika.arlingtonauto.model.User;
import java.util.ArrayList;

public class UserDAO {
    private static Context context;
    private static SQLiteDatabase Database;
    private static DBHelper DBHelper;

    private static UserDAO instance;
    public static synchronized UserDAO getInstance(Context context) {
        /**
         *  You must call getInstance() to get UserDAO object
         *  This ensures only ONE instance will ever be created/used throughout app.
         *  SINGLETON PATTERN
         */
        if (instance == null) {
            instance = new UserDAO(context);
        }
        return instance;
    }
    private UserDAO(Context context) {
        /**
         * Constructor must be private to prevent direct instantiation.
         * make call to static method "getInstance()" instead.
         * This ensures Singleton Pattern.
         */
        DBHelper = DBHelper.getInstance(context);
        this.context = context;
        try {
            Database = DBHelper.getWritableDatabase(); // open the database
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Insert User into DB
     * @param user (User object)
     * @return auto-generated auto-incremented PK user ID from db
     */
    public static long insertUser(User user) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_USER_FIRST_NAME, user.getFirstName());
        values.put(DBHelper.COLUMN_USER_LAST_NAME, user.getLastName());
        values.put(DBHelper.COLUMN_USER_UTA_ID, user.getUtaId());
        values.put(DBHelper.COLUMN_USER_USERNAME, user.getUsername());
        values.put(DBHelper.COLUMN_USER_PASSWORD, user.getPassword());
        values.put(DBHelper.COLUMN_USER_EMAIL, user.getEmail());
        values.put(DBHelper.COLUMN_USER_ROLE, user.getRole());
        values.put(DBHelper.COLUMN_USER_IS_REVOKED, user.getIsRevoked());
        values.put(DBHelper.COLUMN_USER_AAC_MEMBERSHIP, user.getAacMembership());
        long insertId = Database.insert(DBHelper.TABLE_USER, null, values);
        return insertId;
    }

    /** Gets a specific user from DB
     * @param username (String)
     * @return User object matching the username provided
     */
    public static User getUser(String username) {
        User user = null;
        String query = "SELECT * FROM " + DBHelper.TABLE_USER
                + " WHERE " + DBHelper.COLUMN_USER_USERNAME + " = '"+username+"';";
        Cursor cursor = Database.rawQuery(query,null);
        if (cursor.moveToFirst()) {
            user = new User();
            user.setId(cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_USER_ID)));
            user.setFirstName(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_USER_FIRST_NAME)));
            user.setLastName(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_USER_LAST_NAME)));
            user.setUtaId(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_USER_UTA_ID)));
            user.setUsername(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_USER_USERNAME)));
            user.setPassword(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_USER_PASSWORD)));
            user.setEmail(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_USER_EMAIL)));
            user.setRole(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_USER_ROLE)));
            user.setIsRevoked(cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_USER_IS_REVOKED)));
            user.setAacMembership(cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_USER_AAC_MEMBERSHIP)));
        }
        cursor.close();
        return user;
    }

    /** Update existing user in DB
     * @param user (User Object)
     */
    public static void updateUser(User user) {
        String query = "UPDATE USER SET "
                +DBHelper.COLUMN_USER_UTA_ID+"='"+user.getUtaId()+"',"
                +DBHelper.COLUMN_USER_FIRST_NAME+"='"+user.getFirstName()+"',"
                +DBHelper.COLUMN_USER_LAST_NAME+"='"+user.getLastName()+"',"
                +DBHelper.COLUMN_USER_PASSWORD+"='"+user.getPassword()+"',"
                +DBHelper.COLUMN_USER_EMAIL+"='"+user.getEmail()+"',"
                +DBHelper.COLUMN_USER_AAC_MEMBERSHIP+"="+user.getAacMembership()+","
                +DBHelper.COLUMN_USER_IS_REVOKED+"="+user.getIsRevoked()
                +" WHERE USERNAME='"+user.getUsername()+"'";
        Cursor cursor = Database.rawQuery(query,null);
        cursor.moveToFirst();
        cursor.close();
    }

    /** Gets list of all revokees from DB
     * @return ArrayList of all revoked USERNAMES (String)
     */
    public static ArrayList<String> getAllRevokedUsernames() {
        ArrayList<String> revokeeList = new ArrayList<String>();
        String query = "SELECT  "+ DBHelper.COLUMN_USER_USERNAME +" FROM "
                + DBHelper.TABLE_USER + " WHERE "
                + DBHelper.COLUMN_USER_IS_REVOKED + "=1 "
                + " ORDER BY " + DBHelper.COLUMN_USER_USERNAME;
        Cursor cursor = Database.rawQuery(query, null);
        try {
            // looping through all rows and adding username to list
            if (cursor.moveToFirst()) {
                do {
                    revokeeList.add(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_USER_USERNAME)));
                } while (cursor.moveToNext());
            }
        } finally {
            try {
                cursor.close(); //always close cursor!
            }
            catch (Exception e) {
            }
        }
        return revokeeList;
    }
}
