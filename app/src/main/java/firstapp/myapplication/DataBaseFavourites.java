package firstapp.movie_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by User on 18/11/2016.
 */
public class DataBaseFavourites extends SQLiteOpenHelper {


    String DATA_BASE ="database";
    String TABLE = "favourites";
    int VERSION = 1;
    String id = "id";
    String movieTitle = "title";


//    public DataBaseFavourites() {
//        super();
//    }

    public DataBaseFavourites(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE + " (" + id+ "INTEGER PRIMARY KEY "+ ""+movieTitle + "text");
        // is query "insert into "

        ContentValues favouriteValues = new ContentValues();
        favouriteValues.put(id,"1");
        favouriteValues.put(movieTitle,"2");

        db.insert(TABLE,null,favouriteValues);
        //null if i have column taking null so it holds many numbers of null columns


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

 public void inertData(Object o)
 {

 }


}
