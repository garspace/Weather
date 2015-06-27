package com.example.dj.application.Bean;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.dj.application.CityDB;
import com.example.dj.application.MainActivity;
import com.example.dj.application.MyApplication;
import com.example.dj.application.R;

import java.util.List;
import java.util.Map;


public class SelectProvinceActivity extends Activity {
    List<City> listcity;
    List<String> lists,pingyinlists;
    AutoCompleteTextView editText;
    List<String> listprovince;
    Map<String,List<String>> mapprovince;
    ListView listview;
    CityDB mcitydb;
    ArrayAdapter arrayAdapter;
    MyApplication myApplication;
    String pingyin;
    int num;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_city);
        listview=(ListView)findViewById(R.id.listview);
        ImageView backbtn=(ImageView)findViewById(R.id.title_back);
        ImageView search=(ImageView)findViewById(R.id.search_button);
        editText=(AutoCompleteTextView)findViewById(R.id.search_edit_frame);
        ArrayAdapter arrayAdapter1=new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,pingyinlists);
        editText.setAdapter(arrayAdapter1);
        editText.setThreshold(2);
        myApplication=(MyApplication)getApplication();
        lists=myApplication.getLists();
        pingyinlists=myApplication.getPingyinLists();
        listcity=myApplication.getCityList();
       listprovince=myApplication.getListprovince();
       // mapprovince=myApplication.getMapprovince();
         arrayAdapter=new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,lists);
        listview.setAdapter(arrayAdapter);
        editText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String city=(String)parent.getItemAtPosition(position);
                Toast.makeText(SelectProvinceActivity.this,city,Toast.LENGTH_SHORT).show();
                for(int i=0;i<lists.size();i++){
                    if(listcity.get(i).getCity()==city){
                        String cityNumber=listcity.get(i).getNumber();
                        String province=listcity.get(i).getProvince();
                        //  List<String> listcity=mapprovince.get(province);
                        Intent intent=new Intent(SelectProvinceActivity.this,MainActivity.class);
                        intent.putExtra("number",cityNumber);
                        intent.putExtra("city",city);
                        intent.putExtra("province",province);
                        //finish();
                        startActivity(intent);
                        break;
                    }
                }
            }
        });
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 String city=(String)parent.getItemAtPosition(position);

                for(int i=0;i<lists.size();i++){
                    if(listcity.get(i).getCity()==city){
                        String cityNumber=listcity.get(i).getNumber();
                        String province=listcity.get(i).getProvince();
                        //  List<String> listcity=mapprovince.get(province);
                        Intent intent=new Intent(SelectProvinceActivity.this,MainActivity.class);
                        intent.putExtra("number",cityNumber);
                        intent.putExtra("city",city);
                        intent.putExtra("province",province);
                        //finish();
                        startActivity(intent);
                        break;
                    }
                }
                //Toast.makeText(SelectCityActivity.this,province,Toast.LENGTH_SHORT).show();
/*                     int i=0;
                    int cout=0;
                    do{
                        if (mapprovince.get("province") == province) {
                        } else {
                            isCity = false;
                        }
                        i++;
                        cout++;
                    }while (i<list.size());
                if (citylist.size()>1) {
                    listprovince.clear();
                    listprovince = citylist;
                    citylist.clear();
                }*/
               // listprovince.clear();
              //  listprovince =listcity;
              //  arrayAdapter.notifyDataSetChanged();
            }
        });
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.title_back:
                        finish();
                        break;
                    default:
                        break;
                }
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pingyin=editText.getText().toString().toUpperCase().trim();
                //Toast.makeText(SelectProvinceActivity.this,listcity.get().getAllpy(),Toast.LENGTH_SHORT).show();
/*                int len=pingyin.length();
                char[] firstpingyin=new char[len];
                for(int i=0;i<len;i++){
                    firstpingyin[i]=pingyin.charAt(i);
                }*/
                new Thread(new Runnable() {
                    int len=listcity.size();
                    @Override
                    public void run() {
                        for (int k=0;k<len;k++){
                            String s=listcity.get(k).getAllfirstpy().trim();
                            if (s==pingyin){
                                lists.clear();
                                Toast.makeText(SelectProvinceActivity.this,listcity.get(k).getAllfirstpy(),Toast.LENGTH_SHORT).show();
                                lists.add(pingyin);
                                arrayAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                }).start();

            }
        });
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
