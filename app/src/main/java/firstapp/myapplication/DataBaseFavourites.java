package firstapp.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by User on 18/11/2016.
 */
public class DataBaseFavourites extends SQLiteOpenHelper {


   public static final String DATA_BASE ="database";
    public static final String TABLE = "favourites";
    public static final int VERSION = 1;
    public static final String id = "id";
    public static final String movieTitle = "title";


//    public DataBaseFavourites() {
//        super();
//    }

    public DataBaseFavourites(Context context) {
        super(context,DATA_BASE ,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE + " ("+ "ID INTEGER PRIMARY KEY AUTOINCREMENT "+ ""+movieTitle + "text");
        // is query "insert into "

        ContentValues favouriteValues = new ContentValues();
        favouriteValues.put(id,"1");
        favouriteValues.put(movieTitle,"2");

        db.insert(TABLE,null,favouriteValues);
        //null if i have column taking null so it holds many numbers of null columns


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP IF TABLE EXISTS " + TABLE);
    }
    public boolean saveData(String title) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(movieTitle,title);
        long result = db.insert(TABLE,null,cv);

        if(result==-1) {
            return false;
        }
        else{
            return true;

        }
    }
    public Cursor getListContents()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE,null);
        return data;
    }





}
