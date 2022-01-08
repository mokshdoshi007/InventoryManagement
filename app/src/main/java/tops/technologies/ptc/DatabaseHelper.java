package tops.technologies.ptc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "StockManager.db";
    private static final String TABLE_STOCK = "stock";
    private static final String COLOUMN_ID = "id";
    private static final String COLOUMN_CATEGORY = "category";
    private static final String COLOUMN_REFERENCE = "reference";
    private static final String COLOUMN_QUANTITY = "quantity";
    private static final String COLOUMN_LOCATION = "location";
    private static final String COLOUMN_SIZE = "size";

    private String CREATE_STOCK_TABLE = "CREATE TABLE " + TABLE_STOCK + " ("
            + COLOUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLOUMN_CATEGORY +
            " TEXT," + COLOUMN_REFERENCE + " TEXT," + COLOUMN_SIZE + " TEXT,"
            + COLOUMN_QUANTITY + " INT,"+COLOUMN_LOCATION+" TEXT)";

    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS "+TABLE_STOCK;

    public DatabaseHelper(Context context)
    {
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(CREATE_STOCK_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL(DROP_USER_TABLE);
        onCreate(db);
    }

    public void addstock(Model model)
    {
        ContentValues contentValues = new ContentValues();
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        contentValues.put(COLOUMN_CATEGORY,model.getCategory());
        contentValues.put(COLOUMN_REFERENCE,model.getReference());
        contentValues.put(COLOUMN_SIZE,model.getSize());
        contentValues.put(COLOUMN_QUANTITY,model.getQuantity());
        contentValues.put(COLOUMN_LOCATION,model.getLocation());
        sqLiteDatabase.insert(TABLE_STOCK, null, contentValues);
        sqLiteDatabase.close();
    }

    public Model getstockbyid(String id)
    {
        Model model = new Model();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+TABLE_STOCK+" WHERE ID = ?", new String[]{id}, null);
        if (cursor.moveToFirst()) {
            do {
                model.setId(Integer.parseInt((cursor.getString(cursor.getColumnIndex(COLOUMN_ID)))));
                model.setCategory(cursor.getString(cursor.getColumnIndex(COLOUMN_CATEGORY)));
                model.setReference(cursor.getString(cursor.getColumnIndex(COLOUMN_REFERENCE)));
                model.setSize(cursor.getString(cursor.getColumnIndex(COLOUMN_SIZE)));
                model.setQuantity(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLOUMN_QUANTITY))));
                model.setLocation(cursor.getString(cursor.getColumnIndex(COLOUMN_LOCATION)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();
        return model;
    }

    public List getallstock(String sortby)
    {
        String sortorder=COLOUMN_REFERENCE+" ASC";
        String columns[] = {COLOUMN_ID,COLOUMN_CATEGORY,COLOUMN_REFERENCE,COLOUMN_SIZE,COLOUMN_QUANTITY,COLOUMN_LOCATION};
        if(sortby.equalsIgnoreCase("reference"))
             sortorder = COLOUMN_REFERENCE+" ASC";
        else if(sortby.equalsIgnoreCase("location"))
            sortorder = COLOUMN_LOCATION+" ASC";
        List <Model>  modelList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_STOCK,columns,null,null,null,null,sortorder);
        if (cursor.moveToFirst())
        {
            do {
                Model model = new Model();
                model.setId(Integer.parseInt((cursor.getString(cursor.getColumnIndex(COLOUMN_ID)))));
                model.setCategory(cursor.getString(cursor.getColumnIndex(COLOUMN_CATEGORY)));
                model.setReference(cursor.getString(cursor.getColumnIndex(COLOUMN_REFERENCE)));
                model.setSize(cursor.getString(cursor.getColumnIndex(COLOUMN_SIZE)));
                model.setQuantity(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLOUMN_QUANTITY))));
                model.setLocation(cursor.getString(cursor.getColumnIndex(COLOUMN_LOCATION)));
                modelList.add(model);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return modelList;
    }

    public boolean deletebyid(String id)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.delete(TABLE_STOCK, COLOUMN_ID + "=" + id, null) > 0;
    }

    public void updatebyid(String id, String s1, String s2, String s3, String s4, String s5) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLOUMN_CATEGORY,s1);
        cv.put(COLOUMN_REFERENCE,s2);
        cv.put(COLOUMN_SIZE,s3);
        cv.put(COLOUMN_QUANTITY,s4);
        cv.put(COLOUMN_LOCATION,s5);
        sqLiteDatabase.update(TABLE_STOCK, cv, COLOUMN_ID+" = ?", new String[]{id});
    }
}