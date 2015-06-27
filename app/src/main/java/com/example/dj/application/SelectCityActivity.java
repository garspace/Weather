package com.example.dj.application;

import android.app.Application;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;
import java.util.Map;


public class SelectCityActivity extends ActionBarActivity {
    MyApplication myApplication;
    Map<String,List<String>> mapprovince;
    List<String> listcity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_city2);
        Intent intent=this.getIntent();
        String province=intent.getStringExtra("provincename");
        myApplication=(MyApplication)getApplication();
        mapprovince=myApplication.getMapprovince();
       // for(int i=0;i<listcity;)
        listcity=mapprovince.get(province);
        Toast.makeText(this,listcity.size(),Toast.LENGTH_SHORT).show();
        ListView listView=(ListView)findViewById(R.id.listview);
      //  ArrayAdapter arrayAdapter=new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,listcity);
      //  listView.setAdapter(arrayAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_select_city, menu);
        return true;
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
