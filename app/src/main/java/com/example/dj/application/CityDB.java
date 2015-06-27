package com.example.dj.application;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.dj.application.Bean.City;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by DJ on 2015/4/22.
 */
public class CityDB {
    public static final String CITY_DB_NAME="city2.db";
    public static final String CITY_TABLE_NAME="city";
    SQLiteDatabase db;
    List<City> list=new ArrayList<>();
    List<String> listprovince=new ArrayList<>();
    List<String> lists=new ArrayList<>();
    List<String> pinyinlists=new ArrayList<>();

    List<String> listcity;
    Map<String,List<String>> mapprovince=new HashMap<>();

    CityDB(Context context, String path){
        db=context.openOrCreateDatabase(CITY_DB_NAME,Context.MODE_PRIVATE,null);
    }
  //  public Map<List<String>,List<String>> getMapprovince(){return mapprovince;}
    public List<String> getList(){
        return lists;
    }
    public List<String> getPinyinlistsList(){
        return pinyinlists;
    }
    public List<String> getListprovince(){
        return listprovince;
    }
    public Map<String,List<String>> getMapprovince(){
        return mapprovince ;
    }
    public List<City> getAllCity(){
        //  List<City> listprovince=new ArrayList<>();
        Cursor cursor=db.rawQuery("SELECT*FROM  "+CITY_TABLE_NAME,null);
        while (cursor.moveToNext()){
            String province= cursor.getString(cursor.getColumnIndex("province"));
            if(!listprovince.contains(province)){
                listprovince.add(province);
            }
            String city= cursor.getString(cursor.getColumnIndex("city"));
            lists.add(city);
            String allfirstpy= cursor.getString(cursor.getColumnIndex("allfirstpy"));
            String allpy= cursor.getString(cursor.getColumnIndex("allpy"));
            pinyinlists.add(allfirstpy);
            String number= cursor.getString(cursor.getColumnIndex("number"));
            String firstpy= cursor.getString(cursor.getColumnIndex("firstpy"));
            City item=new City(province,city,number,allpy,allfirstpy,firstpy);
            list.add(item);
        }
/*
        for(int j=0;j<listprovince.size();j++){
            listcity=new ArrayList<>();
            for(int i=0;i<list.size();i++){
                if(list.get(i).getProvince()==listprovince.get(j)){
                    listcity.add(list.get(i).getCity());
                }
            }
            mapprovince.put(listprovince.get(j),listcity);
        }
*/

        return list;
    }
}
