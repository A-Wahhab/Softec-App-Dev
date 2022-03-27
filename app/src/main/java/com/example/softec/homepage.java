package com.example.softec;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

public class homepage extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Events");
    //JSONObject Global_State = new JSONObject();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        UpdateDatabase(myRef);
        Button paybutton = findViewById(R.id.buttonpay);
        Button sbutton = findViewById(R.id.sponsorbtn);
        sbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(homepage.this, sponsorship_package.class);
                startActivity(i);
            }
        });
        paybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(homepage.this, payment.class);
                startActivity(i);
            }
        });




    }

    private JSONObject setelibiltiy(String Degree,String sem,String age){
        JSONObject temp = new JSONObject();
        try {
            temp.put("Degree", Degree);
            temp.put("sem", sem);
            temp.put("age", age);
        }catch (Exception e){
            Log.d("*******", "setelibiltiy: none");
        }
        return temp;
    }
    private JSONObject setsubactivities(String price,String name,String date,String time,JSONObject x){
        JSONObject temp = new JSONObject();
        try {
            temp.put("price", price);
            temp.put("name", name);
            temp.put("date", date);
            temp.put("time", time);
            temp.put("Eligibility",x);
        }catch (Exception e){
            Log.d("*******", "setelibiltiy: none");
        }
        return temp;
    }
    void UpdateDatabase(DatabaseReference myRef) {
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    try {
                            JSONObject global = new JSONObject();
                            JSONObject activities = new JSONObject();

                            JSONArray JA = new JSONArray();
                            JSONObject temp = new JSONObject();
                            temp.put("datestart","15-2-2022");
                            temp.put("dateend","17-2-2022");
                            temp.put("pricerange","1000-3000");
                            temp.put("location","fast");

                            JSONObject subactivities = new JSONObject();
                            JSONObject Eligibility = new JSONObject();

                            activities.put("appdev",setsubactivities("2500","appdev","15-2-2022","7:00 pm",
                                    setelibiltiy("Cs","6+","18+")));

                            temp.put("Activities",activities);


                            JA.put(temp);

                            global.put("softec", JA);
                            myRef.setValue(global.toString());
                            Log.d("*******", global.toString());

                        /*obj1 = new JSONObject();
                            obj1.put("lat", lat);
                            JA.put(obj1);

                            obj1 = new JSONObject();
                            obj1.put("longt", longt);
                            JA.put(obj1);

                            obj1 = new JSONObject();
                            obj1.put("AQI", AQI);
                            JA.put(obj1);

                            obj1 = new JSONObject();
                            obj1.put("CO", CO);
                            JA.put(obj1);

                            obj1 = new JSONObject();
                            obj1.put("SO2", SO2);
                            JA.put(obj1);

                            obj1 = new JSONObject();
                            obj1.put("NO", NO);
                            JA.put(obj1);

                            obj1 = new JSONObject();
                            obj1.put("O3", O3);
                            JA.put(obj1);

                            obj1 = new JSONObject();
                            obj1.put("PM10", PM10);
                            JA.put(obj1);

                            JSONArray jo = new JSONArray();
                            jo.put(JA);

                            // JSONObject mainObj = new JSONObject();
                            //mainObj.put(CityName, jo);
                            if (global_state.has(CityName)){

                                global_state.remove(CityName);

                                global_state.put(CityName,jo);

                            }
                            else{
                                global_state.put(CityName,jo);
                            }
                            final Double tempco = CO;
                            final Double tempso2 = SO2;
                            final Double tempno = NO;
                            final Double tempo3 = O3;
                            final Double temppm10 = PM10;


                            //global_state.put(mainObj);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    CityView.setText(CityName.toString()+" ");
                                    AQIVIEW.setText(AQI.toString()+" μg/m3");
                                    COVIEW.setText(tempco.toString()+" μg/m3");
                                    SO2VIEW.setText(tempso2.toString()+" μg/m3");
                                    NOVIEW.setText(tempno.toString()+" μg/m3");
                                    O3VIEW.setText(tempo3.toString()+ " μg/m3");
                                    PM10VIEW.setText(temppm10.toString()+" μg/m3");
                                }
                            });
                            Log.d("*****output****",global_state.toString());

                            //Log.d("*****output****",global_state.toString());

                            myRef.setValue(global_state.toString());

                            //AQIVIEW.setText(AQI.toString()+"");*/

                        } catch (Exception e) {
                            Log.d("**ERRORWRITE**", " WRITE ERROR!!! ");
                            e.printStackTrace();
                        }
                        //UPDATE TO FIREBASE

                }
            };
            Thread t = new Thread(r);
            t.start();
    }
}