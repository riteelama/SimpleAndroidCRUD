package helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import data.MyData;

public class MyDbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION =  1;

    private static final String DATABASE_NAME = "myDataDb";

    public MyDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createQuery = "CREATE TABLE myTable(id INTEGER PRIMARY KEY, name TEXT, address TEXT)";
        db.execSQL(createQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " +DATABASE_NAME);
        onCreate(db);
    }

    // Insert data
    public void insertData(MyData data) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id",data.getId());
        contentValues.put("name",data.getName());
        contentValues.put("address",data.getAddress());

        db.insert("myTable", null, contentValues);
        db.close();
    }

    //read data
    public Cursor selectData(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM myTable";
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    //update data
    public void updateData() {

    }

    //delete-data
    public void deleteData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("myTable", "id=?", new String [] {id});
    }
}
