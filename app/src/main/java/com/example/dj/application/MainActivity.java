package com.example.dj.application;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dj.application.Bean.SelectProvinceActivity;
import com.example.dj.application.Bean.Today;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;


public class MainActivity extends Activity {
    final int RESPONSE = 1;
    private TextView cityname,cityTv,timeTv,humidityTv,weekTv,pmDataTv,pmQualityTv,temperatureTv,climateTv,windTv;
    private ImageView pmImage,weatherImg;
    ViewPager viewPager;
    List<ImageView> viewList;
    Today today;
    String citykey="101010100";
    int eventType;
    int imgId[]={R.drawable.biz_plugin_weather_qing,R.drawable.biz_plugin_weather_leizhenyu,R.drawable.biz_plugin_weather_yin,R.drawable.biz_plugin_weather_zhongyu};
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what==RESPONSE) {
                today=(Today)msg.obj;
                cityTv.setText(today.getCity());
                timeTv.setText(today.getUpdatetime()+"发布");
                humidityTv.setText("湿度："+today.getShidu());
                pmDataTv.setText(today.getPm25());
                pmQualityTv.setText(today.getQuality());
                weekTv.setText(today.getDate());
                temperatureTv.setText(today.getLow()+"-"+today.getHigh());
                climateTv.setText(today.getType());
                windTv.setText(today.getFengli());
                Toast.makeText(MainActivity.this,"更新成功",Toast.LENGTH_SHORT).show();
            }
        }

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Toast.makeText(MainActivity.this,"MyApplication-->onCreate()",Toast.LENGTH_SHORT).show();

        setContentView(R.layout.activity_main);
        initView();
        ImageView updateCity = (ImageView) findViewById(R.id.title_update_btn);
        ImageView btn=(ImageView)findViewById(R.id.title_city_manager);
        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return viewList.size();
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(viewList.get(position));
                return viewList.get(position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(viewList.get(position));
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view==object;
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,SelectProvinceActivity.class);
                startActivity(intent);
            }
        });
        updateCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.title_update_btn) {
                    Intent intent=MainActivity.this.getIntent();
                    String city=intent.getStringExtra("city");
                    String number=intent.getStringExtra("number");
                    String province=intent.getStringExtra("province");
                    if(city!=null){
                        cityTv.setText(city);
                        citykey=number;
                        cityname.setText(province);
                    }
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                HttpClient httpClient = new DefaultHttpClient();
                                HttpGet httpGet = new HttpGet("http://wthrcdn.etouch.cn/WeatherApi?citykey="+citykey);
                                HttpResponse httpResponse = httpClient.execute(httpGet);
                                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                                    HttpEntity httpEntity = httpResponse.getEntity();
                                    // String response = EntityUtils.toString(httpEntity,"utf-8");

                                    InputStream responseStream = httpEntity.getContent();      //Gzip方式解压数据
                                    responseStream = new GZIPInputStream(responseStream);
                                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(responseStream, "utf-8"));
                                    StringBuilder responseStr = new StringBuilder();
                                    String str;
                                    while ((str = bufferedReader.readLine()) != null) {
                                        //   Toast.makeText(MainActivity.this, str, Toast.LENGTH_SHORT).show();
                                        responseStr.append(str);
                                    }

                                    String xmldata = responseStr.toString();
                                    Log.d("data",xmldata);
                                    today=XmlParse(xmldata);
                                    if(today!=null ){

                                        Message message = new Message();
                                        message.obj=today; // todaywether;
                                        message.what = RESPONSE;
                                        handler.sendMessage(message);
                                    }

                                }
                            }  catch (IOException e) {
                                e.printStackTrace();
                            } catch (XmlPullParserException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
            }
        });
    }
    void initView(){
        viewPager=(ViewPager)findViewById(R.id.viewPager);
        viewList=new ArrayList<>();
        for (int i=0;i<imgId.length;i++){
            ImageView imageView=new ImageView(this);
            imageView.setLayoutParams(new LinearLayout.LayoutParams(50,50));
            imageView.setBackgroundResource(imgId[i]);
            viewList.add(imageView);
        }

        cityname=(TextView)findViewById(R.id.title_city_name);
        cityTv=(TextView)findViewById(R.id.textName);
        timeTv=(TextView)findViewById(R.id.textTime);
        humidityTv=(TextView)findViewById(R.id.textQuality);
        weekTv=(TextView)findViewById(R.id.textweek);
        pmDataTv=(TextView)findViewById(R.id.pmData);
        pmQualityTv=(TextView)findViewById(R.id.pmQuality);
        pmImage=(ImageView)findViewById(R.id.imagehead);
        temperatureTv=(TextView)findViewById(R.id.textT);
        climateTv=(TextView)findViewById(R.id.textwewather);
        windTv=(TextView)findViewById(R.id.textWind);
      //  weatherImg=(ImageView)findViewById(R.id.imageweather);
        cityTv.setText("N/A");
        timeTv.setText("N/A");
        humidityTv.setText("N/A");
        weekTv.setText("N/A");
        pmDataTv.setText("N/A");
        pmQualityTv.setText("N/A");
        temperatureTv.setText("N/A");
        climateTv.setText("N/A");
        windTv.setText("N/A");
    }

    Today XmlParse(String xml) throws IOException, XmlPullParserException {
        XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();
        XmlPullParser xmlPullParser = xmlPullParserFactory.newPullParser();
        xmlPullParser.setInput(new StringReader(xml));
        eventType = xmlPullParser.getEventType();
        Log.d("httpApplication", "xmlparse");
        today=new Today();
        int fengxiangCout=0;
        int fengliCout=0;
        int dateCout=0;
        int highCout=0;
        int lowCout=0;
        int typeCout=0;
        while (eventType != xmlPullParser.END_DOCUMENT) {
            switch (eventType) {
                case XmlPullParser.START_DOCUMENT:;
                    break;
                case XmlPullParser.START_TAG:
                    if (xmlPullParser.getName().equals("city")) {
                        eventType=xmlPullParser.next();
                        today.setCity(xmlPullParser.getText());
                    }else if (xmlPullParser.getName().equals("updatetime")){
                        eventType=xmlPullParser.next();
                        today.setUpdatetime(xmlPullParser.getText());
                    }else if (xmlPullParser.getName().equals("shidu")){
                        eventType=xmlPullParser.next();
                        today.setShidu(xmlPullParser.getText());
                    }else if (xmlPullParser.getName().equals("wendu")){
                        eventType=xmlPullParser.next();
                        today.setWendu(xmlPullParser.getText());
                    }else if (xmlPullParser.getName().equals("pm25")){
                        eventType=xmlPullParser.next();
                        today.setPm25(xmlPullParser.getText());
                    }else if (xmlPullParser.getName().equals("quality")){
                        eventType=xmlPullParser.next();
                        today.setQuality(xmlPullParser.getText());
                    }else if (xmlPullParser.getName().equals("fengxiang")&&fengxiangCout==0){
                        eventType=xmlPullParser.next();
                        today.setFengxiang(xmlPullParser.getText());
                        fengxiangCout++;
                    }else if (xmlPullParser.getName().equals("fengli")&&fengliCout==0){
                        eventType=xmlPullParser.next();
                        today.setFengxiang(xmlPullParser.getText());
                        fengxiangCout++;
                    }else if (xmlPullParser.getName().equals("date")&&dateCout==0){
                        eventType=xmlPullParser.next();
                        today.setDate(xmlPullParser.getText());
                        dateCout++;
                    }else if (xmlPullParser.getName().equals("high")&&highCout==0){
                        eventType=xmlPullParser.next();
                        today.setHigh(xmlPullParser.getText());
                        highCout++;
                    }else if (xmlPullParser.getName().equals("low")&&lowCout==0){
                        eventType=xmlPullParser.next();
                        today.setHigh(xmlPullParser.getText());
                        lowCout++;
                    }else if (xmlPullParser.getName().equals("type")&&typeCout==0){
                        eventType=xmlPullParser.next();
                        today.setType(xmlPullParser.getText());
                        typeCout++;
                    }
                    break;

                case XmlPullParser.END_TAG:
                    break;
            }
            eventType = xmlPullParser.next();
        }
        return  today;
    }
}