package com.example.dressassistant;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Jennifer on 2017-12-23.
 */

//创建数据库，从asserts将数据库导入工程中
public class DBHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static String DB_PATH = "/data/data/com.example.dressassistant/databases/";
    private static String DB_NAME = "dressassistant.db";
    private static String ASSETS_NAME = "dressassistant.db";
    private static final  int ASSETS_SUFFIX_BEGIN = 101;
    private static final  int ASSETS_SUFFIX_END  = 103;

    private SQLiteDatabase myDataBase = null;
    private final Context myContext;

    //一系列DBHelper的构造函数
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context,name,null,version);
        this.myContext = context;
    }
    public DBHelper(Context context, String name, int version){
        this(context,name,null,version);
    }
    public DBHelper(Context context, String name){
        this(context,name,DB_VERSION);
    }
    public DBHelper(Context context){
        this(context,DB_PATH + DB_NAME);
    }

    //创建数据库
    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();
        if(dbExist){
//            File dbf = new File(DB_PATH + DB_NAME);
//            if(dbf.exists()){
//                dbf.delete();
//            }
//            SQLiteDatabase.openOrCreateDatabase(dbf,null);
//            copyDataBase();
        }
        else{
            try{
                File dir = new File(DB_PATH);
                if(!dir.exists()){
                    dir.mkdirs();
                }
                File dbf = new File(DB_PATH + DB_NAME);
                if(dbf.exists()){
                    dbf.delete();
                }
                SQLiteDatabase.openOrCreateDatabase(dbf,null);
                copyDataBase();
            }catch(IOException e) {
                throw new Error("数据库创建失败");
            }
        }
    }
//检查数据库
    private boolean checkDataBase(){
        SQLiteDatabase checkDB = null;
        String myPath = DB_PATH + DB_NAME;
        try{
            checkDB = SQLiteDatabase.openDatabase(myPath,null,SQLiteDatabase.OPEN_READONLY);
        }catch(SQLiteException e){

        }
        if(checkDB != null){
            checkDB.close();
        }
        return checkDB != null ? true: false;
    }

    //复制数据库
    private void copyDataBase() throws IOException{
        InputStream myInput = myContext.getAssets().open(ASSETS_NAME);
        String outFileName = DB_PATH + DB_NAME;
        OutputStream myOutput = new FileOutputStream(outFileName);
        byte[] buffer = new byte[1024];
        int length;
        while((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }
//复制数据库，若数据库很大用这个
    private void copyBigDataBase() throws IOException{
        InputStream myInput = myContext.getAssets().open(ASSETS_NAME+"." + ASSETS_SUFFIX_BEGIN);
        String outFileName = DB_PATH + DB_NAME;
        OutputStream myOutput = new FileOutputStream(outFileName);

        for(int i = ASSETS_SUFFIX_BEGIN+1;i < ASSETS_SUFFIX_END + 1; i++){
            myInput = myContext.getAssets().open(ASSETS_NAME+"." + i);
        }
        byte[] buffer = new byte[100000];
        int length;
        while((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        myOutput.flush();
        myOutput.close();
        myInput.close();
    }
    //关闭数据库
    @Override
    public synchronized void close(){
        if(myDataBase !=null){
            myDataBase.close();
        }
        super.close();
    }
    @Override
    public void onCreate(SQLiteDatabase db){

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }
}

