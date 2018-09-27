package com.example.mbpadmin.sqllite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.util.Output;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.example.mbpadmin.sqllite.Item.Foto;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String db_name="upload_image";
    private String db_path="";
    private SQLiteDatabase db_image;
    //private final Context mycontext;
    private static final String Photo_Table = "t_photo";
    private static final String Query_Foto_Table = "create table "+Photo_Table +"(judul TEXT primary key,kecepatan TEXT,foto TEXT)";

    Context context;

    public DatabaseHelper(Context context) {
        super(context, db_name, null, 1);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Query_Foto_Table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*db.execSQL("DROP TABLE IF EXISTS " + Photo_Table);

        // Create tables again
        onCreate(db);*/
    }

    public void insertIntoDB(String judul){
       // Log.d("insert", "before insert");

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put("judul", judul);
        values.put("kecepatan","0");
        values.put("foto","0");

        // 3. insert
        db.insert(Photo_Table, null, values);
        // 4. close
        db.close();
        Toast.makeText(context, "insert value", Toast.LENGTH_LONG);
       // Log.i("insert into DB", "After insert");
    }


    public List<Foto> getDataFromDB(){
        /*if(checkDatabase())
        {
            Log.d("DB", "Ada");
        }
        else
        {
            Log.d("DB", "Tidak Ada");
        }*/


        List<Foto> modelList = new ArrayList<Foto>();
        String query = "select * from "+Photo_Table;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);

        if (cursor.moveToFirst()){
            do {
                Foto model = new Foto();
                Log.d("Data2",cursor.getString(0));
                model.setJudul(cursor.getString(0));
                model.setWaktu(cursor.getString(1));
                model.setFoto(cursor.getString(2));

                modelList.add(model);
            }while (cursor.moveToNext());
        }


        Log.d("Foto data", modelList.toString());


        return modelList;
    }

    public void deleteAllRow(){
        SQLiteDatabase db= this.getWritableDatabase();
        //db.delete(Photo_Table, "judul" + " = ?", new String[] { judul });
        db.delete(Photo_Table,"1=1",null);
        db.close();
    }

    public String getRow()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select count(*) from "+Photo_Table,null);
        cursor.moveToFirst();
        db.close ();
        return cursor.getString(0);
    }

    public void updateARow(String judul,String foto,String kecepatan)
    {
        SQLiteDatabase db = getWritableDatabase();
        //db.rawQuery(String.format ("update t_photo set foto='1', kecepatan=\'%s\' where judul=\'%s\'", foto, kecepatan, judul), null);
        //db.close ();
        ContentValues cv = new ContentValues();
        cv.put("foto",foto);
        //Log.d("cvnya",cv.toString());
        db.update(Photo_Table,cv,"judul='"+judul+"'",null);

        Cursor cursor=db.rawQuery("Select * from t_photo where judul='"+judul+"'",null);
        cursor.moveToFirst();
        Log.d("Updatenya",cursor.getString(0)+"wkwk"+cursor.getString(1));

//        ContentValues values = new ContentValues();
//        Log.d("Ouptupafa",foto+kecepatan);
//        values.put("foto","121");
//        values.put("kecepatan","0.01");
//        SQLiteDatabase db= this.getWritableDatabase();
//        int result = db.update(Photo_Table,values,"judul='"+judul+"'",null);
//        Log.d ("joohan", String.valueOf (result));
        db.close();
    }

    public void checkAndCopyDatabase()
    {
        boolean dbExist=checkDatabase();
        if(dbExist)
        {
            Log.d("Tag","database ada");
        }
        else
        {
            this.getReadableDatabase();
            Log.d("Tag","database tidak ada");
        }
        try {
            copyDatabase();
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("Tag","Eror database");
        }
    }

    public void copyDatabase() throws IOException {
        InputStream myInput=context.getAssets().open(db_name);
        String outFileName=db_path+db_name;
        OutputStream myOutput=new FileOutputStream(outFileName);
        byte[]buffer=new byte[1024];
        int length;
        while ((length=myInput.read(buffer))>0)
        {
            myOutput.write(buffer,0,length);
        }
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    public boolean checkDatabase()
    {
        SQLiteDatabase checkDB=null;
        String myPath=db_path+db_name;
        checkDB=SQLiteDatabase.openDatabase(myPath,null,SQLiteDatabase.OPEN_READWRITE);
        if(checkDB!=null)
        {
            checkDB.close();
        }
        return checkDB != null ? true:false;
    }

    public synchronized void close()
    {
        if(db_image!=null)
        {
            db_image.close();
        }
        super.close();
    }

    public Cursor QueryData(String query)
    {
        return db_image.rawQuery(query,null);
    }
}
