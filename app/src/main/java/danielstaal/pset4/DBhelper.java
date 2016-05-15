package danielstaal.pset4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Daniel Staal on 4/25/2016.
 *
 * Class to manage the sql database for the items
 */
public class DBhelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "firstdb.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE = "lists";

    private String item_id = "item";
    private String list_id = "list";


    public DBhelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /*
     * create the table
     */
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE + " ( _id INTEGER PRIMARY KEY AUTOINCREMENT, item TEXT, list TEXT)";
        db.execSQL(CREATE_TABLE);
    }

    /*
     * update the table onUpgrade
     */
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }

    /*
     * add item to the table
     */
    public void create(String item, String listName) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(item_id, item);
        values.put(list_id, listName);
        db.insert(TABLE, null, values);
    }

    /*
     * read the table in as an arraylist
     */
    public ArrayList<TodoList> read() {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT DISTINCT " + list_id + " FROM " + TABLE;

        ArrayList<TodoList> lists = new ArrayList<>();
        HashMap<String, Integer> map = new HashMap<>();
        Cursor cursor = db.rawQuery(query, null);
        int counter = 0;
        if (cursor.moveToFirst()) {
            do {
                String list = cursor.getString(cursor.getColumnIndex(list_id));
                lists.add(new TodoList(list));
                map.put(list, counter);
                counter++;
            }
            while (cursor.moveToNext());
        }
        query = "SELECT " + list_id + ", " + item_id + " FROM " + TABLE;

        cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                String list = cursor.getString(cursor.getColumnIndex(list_id));
                String item = cursor.getString(cursor.getColumnIndex(item_id));
                if (!item.equals("")) {
                    lists.get(map.get(list)).addItemToList(new TodoItem(item));
                }
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return lists;
    }

    /*
     * update an element in the table
     */
//    public void update(String item) {
//        SQLiteDatabase db = getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(item_id, item);
//
//        db.update(TABLE, values, "_id = ? ", new String[]{String.valueOf(item)});
//        db.close();
//    }

    /*
     * delete an element from the table
     */
    public void delete(String item, String listName) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE, item_id + " = ? AND " + list_id + " = ?", new String[]{item, listName});
    }
    /*
     * delete a list from the table
     */
    public void deleteList(String listName){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE, list_id + " = ?", new String[]{listName});
    }
}










