package com.example.exam.foodtrack;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class Bill extends AppCompatActivity {
    TextView t1,t2,t3,t4,total,price;
    int n;
    int min = 1000;
    int max = 9999;
    int diff = max - min;
    Integer idly = 10;
    int dosa = 15;
    int pongal = 25;
    int vegrice = 40;
    SQLiteDatabase billdb;
    String p1,p2,p3,p4;
    Button confirm;
    ProgressBar pb;
    Handler h= new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        t1 = (TextView)findViewById(R.id.t1);
        t2 = (TextView)findViewById(R.id.t2);
        t3 = (TextView)findViewById(R.id.t3);
        t4 = (TextView)findViewById(R.id.t4);
        total =(TextView)findViewById(R.id.total);
        price = (TextView)findViewById(R.id.price) ;
        pb = (ProgressBar)findViewById(R.id.pb);


       //billdb =openOrCreateDatabase("billdb",MODE_PRIVATE,null);
        //billdb.execSQL("create table if not exists info(username varchar(40),route varchar(60),station varchar(50),coach varchar(10),seat varchar(10),item varchar(30),quantity varchar(20),price varchar(10));");
        p1= getIntent().getStringExtra("v1");
        p2= getIntent().getStringExtra("v2");
        p3= getIntent().getStringExtra("v3");
        p4= getIntent().getStringExtra("v4");

        n= (int)((Math.random())*(diff+1)+min);







        Integer iq1=Integer.parseInt(p1);
        Integer iq2= Integer.parseInt(p2);
        Integer iq3= Integer.parseInt(p3);
        Integer iq4= Integer.parseInt(p4); //quantity

        Integer idly_price = iq1 * idly;
        Integer dosa_price = iq3 * dosa;
        Integer pongal_price = iq2 * pongal;
        Integer vegrice_price = iq4 * vegrice;
        final Integer total_price = idly_price+dosa_price+pongal_price+vegrice_price;


        t1.setText(idly_price.toString());
        t3.setText(dosa_price.toString());
        t2.setText(pongal_price.toString());
        t4.setText(vegrice_price.toString());
        price.setText(total_price.toString());

        confirm = (Button)findViewById(R.id.confirm);
        pb.setVisibility(View.GONE);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pb.setVisibility(View.VISIBLE);
                h.postDelayed(new Runnable() {
                    public void run() {

                        //h.postDelayed(this, 7000);
                        pb.setVisibility(View.GONE);


                    }
                },5000);
                if(total_price==0){
                    Toast.makeText(getApplicationContext(),"No orders Placed!",Toast.LENGTH_LONG).show();
                    finish();
                    Toast.makeText(getApplicationContext(),"Place an order to continue!",Toast.LENGTH_LONG).show();
                }
                else{

                    new AlertDialog.Builder(Bill.this)
                            .setMessage("Thank you! You Order is placed! Your verification code is "+n)
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    confirm.setEnabled(false);
                                    Intent g = new Intent(Bill.this,Profile_Basic_Activity.class);
                                    startActivity(g);


                                }
                            })
                            .setNegativeButton("locate", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent k = new Intent(Bill.this,GPS.class);
                                    startActivity(k);
                                }
                            })
                            .show();

                }
            }

        });












    }


}
