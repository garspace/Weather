package com.example.dj.application;

import android.app.Application;
import android.os.Environment;
import android.widget.Toast;

import com.example.dj.application.Bean.City;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by DJ on 2015/4/22.
 */
public class MyApplication extends Application {
    List<City> cityList;
    List<String> lists=new ArrayList<>();
    List<String> pingyinlists=new ArrayList<>();
    List<String> listprovince=new ArrayList<>();
    Map<String,List<String>> mapprovince;

    CityDB mCityDB;
    static Application mapplication;
    @Override
    public void onCreate() {
        listprovince=new ArrayList<>();
        mCityDB=openCityDB();
        lists=mCityDB.getList();
        pingyinlists=mCityDB.getPinyinlistsList();
        initCityList();
        mapplication=this;
    }
    public  Application getApplication(){
        return mapplication;
    }
    public List<City> getCityList(){
        return cityList;
    }
    public List<String> getLists(){
        return lists;
    }
    public List<String> getPingyinLists(){
        return pingyinlists;
    }
    public List<String> getListprovince(){
        return listprovince;
    }
    public Map<String,List<String>> getMapprovince(){return mapprovince;}
    private void initCityList(){
        cityList=new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                prepareCityList();
            }
        }).start();
    }
    private void prepareCityList(){
        cityList=mCityDB.getAllCity();

        listprovince=mCityDB.getListprovince();
        mapprovince=mCityDB.getMapprovince();

/*
        for (City city:cityList){
                String cityname=city.getCity();
            Toast.makeText(this,cityname,Toast.LENGTH_SHORT).show();
        }
*/
    }
    private CityDB openCityDB(){
        String path="/data"+ Environment.getDataDirectory().getAbsolutePath()+ File.separator
                +getPackageName()+File.separator+"databases"+File.separator
                +CityDB.CITY_DB_NAME;
        File db= new File(path);
        if(!db.exists()){
            try {
                InputStream inputStream=getAssets().open("city.db");
                db.getParentFile().mkdirs();
                FileOutputStream fileOutputStream=new FileOutputStream(db);
                int len=-1;
                byte[] buffer=new byte[1024];
                while ((len=inputStream.read(buffer))!=-1){
                    fileOutputStream.write(buffer,0,len);
                }
                inputStream.close();
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(0);
            }
        }
        return new CityDB(this,path);
    }
}
