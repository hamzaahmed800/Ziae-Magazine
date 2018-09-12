package ziaetaiba.com.zia_e_magazine.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static int version = 1;
    public static final String DATABASE_NAME = "Magazine.db";
    public static final String TABLE_MENU = "menu_table";
    public static final String TABLE_PRODUCT = "product_table";
    public static final String MENU_COL_1 = "id";
    public static final String MENU_COL_2 = "name";
    public static final String MENU_COL_3 = "extra";
    public static final String PRODUCT_COL_1 = "id";
    public static final String PRODUCT_COL_2 = "name";
    public static final String PRODUCT_COL_3 = "thumbnailPath";
    public static final String PRODUCT_COL_4 = "description";

    public DBHelper(Context context) {
        super(context,DATABASE_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_MENU + " (ID TEXT,NAME TEXT,EXTRA TEXT ) ");
        db.execSQL("create table " + TABLE_PRODUCT + " (ID TEXT,NAME TEXT,THUMBNAILPATH TEXT,DESCRIPTION TEXT )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_MENU);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_PRODUCT);
        onCreate(db);
    }

    public boolean insertMenuData(String id,String name,String extra) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MENU_COL_1,id);
        contentValues.put(MENU_COL_2,name);
        contentValues.put(MENU_COL_3,extra);
        long result = db.insert(TABLE_MENU,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public boolean insertProductData(String id,String name,String thumbnailpath,String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PRODUCT_COL_1,id);
        contentValues.put(PRODUCT_COL_2,name);
        contentValues.put(PRODUCT_COL_3,thumbnailpath);
        contentValues.put(PRODUCT_COL_4,description);
        long result = db.insert(TABLE_PRODUCT,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllMenuData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_MENU,null);
        return res;
    }

    public Cursor getAllProductData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_PRODUCT,null);
        return res;
    }

    public Cursor getSingleProductData(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select * from " +TABLE_PRODUCT+ " where name = ? "+name;
        Cursor res = db.rawQuery(query, null);
        return res;
    }

    public boolean updateMenuData(String id,String name,String extra) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MENU_COL_1,id);
        contentValues.put(MENU_COL_2,name);
        contentValues.put(MENU_COL_3,extra);
        db.update(TABLE_PRODUCT, contentValues,"ID = ?",new String[] { id });
        return true;
    }

    public boolean updateProductData(String id,String name,String thumbnailpath,String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PRODUCT_COL_1,id);
        contentValues.put(PRODUCT_COL_2,name);
        contentValues.put(PRODUCT_COL_3,thumbnailpath);
        contentValues.put(PRODUCT_COL_4,description);
        db.update(TABLE_PRODUCT, contentValues, "ID = ?",new String[] { id });
        return true;
    }

    public boolean updateTables(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+TABLE_MENU);
        db.execSQL("delete from "+TABLE_PRODUCT);
        return true;
    }

//    public Integer deleteData (String id) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(TABLE_PRODUCT)
//    }
}
