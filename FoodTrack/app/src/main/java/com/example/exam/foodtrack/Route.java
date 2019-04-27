package com.example.exam.foodtrack;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.lang.*;

public class Route extends AppCompatActivity  implements
        AdapterView.OnItemSelectedListener {
    Button next;
    String r[] = {"Cholan Express", "Rajdhani Express"};
    TextView cstation, rstation;
    Spinner spin, spin2, spin3;
    EditText coach,seat;
    String c ,s;
    String cholan[] = {"Arakonam", "Central","Tambaram"};
    String rajdhani[] = {"Bhopal", "Agra"};
    EditText q1,q2,q3,q4;
    String s1,s2,s3,s4;
    TextView itemu,qtyu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);
        next = (Button) findViewById(R.id.next);


        coach = (EditText)findViewById(R.id.coach);
        seat = (EditText)findViewById(R.id.seat);
        cstation = (TextView) findViewById(R.id.cstation);
        rstation = (TextView) findViewById(R.id.rstation);
        spin = (Spinner) findViewById(R.id.route_spinner);


        cstation.setVisibility(View.GONE);
        rstation.setVisibility(View.GONE);


        spin.setOnItemSelectedListener(this);


        coach.setFilters(new InputFilter[]{ new MinMax(1,24)});
        seat.setFilters(new InputFilter[]{ new MinMax("1","50")});


        q1 = (EditText)findViewById(R.id.q1);
        q2 = (EditText)findViewById(R.id.q2);
        q3 = (EditText)findViewById(R.id.q3);
        q4 = (EditText)findViewById(R.id.q4);
        itemu= (TextView)findViewById(R.id.itemu);
        qtyu = (TextView)findViewById(R.id.qtyu);


        SpannableString content = new SpannableString("Item");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        itemu.setText(content);

        SpannableString content2 = new SpannableString("Qty");
        content2.setSpan(new UnderlineSpan(), 0, content2.length(), 0);
        qtyu.setText(content2);

        s1=q1.getText().toString();
        s2=q2.getText().toString();
        s3=q3.getText().toString();
        s4=q4.getText().toString();

        //  String c2= getIntent().getStringExtra("c1");
        // String s2= getIntent().getStringExtra("s1");
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c = coach.getText().toString();
                s = seat.getText().toString();

                if (c.matches("")) {
                    Toast.makeText(getApplicationContext(), "You did not enter a coach", Toast.LENGTH_SHORT).show();
                }
                else if (s.matches("")) {
                    Toast.makeText(getApplicationContext(), "You did not enter a seat no", Toast.LENGTH_SHORT).show();
                }

                else{
                    Intent k = new Intent(Route.this , Bill.class);
                    k.putExtra("v1",q1.getText().toString());
                    k.putExtra("v2",q2.getText().toString());
                    k.putExtra("v3",q3.getText().toString());
                    k.putExtra("v4",q4.getText().toString());


                    startActivity(k);
//                    Intent i = new Intent(Route.this, Bill.class);
//                    startActivity(i);
               }
                //Intent i = new Intent(Route.this,Food.class);
                //startActivity(i);
            }
        });


        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, r);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);


        spin2 = (Spinner) findViewById(R.id.cholan_spinner);
        spin2.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa2 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, cholan);
        aa2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin2.setAdapter(aa2);

        spin3 = (Spinner) findViewById(R.id.rajdhani_spinner);
        spin3.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa3 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, rajdhani);
        aa3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin3.setAdapter(aa3);


    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {


        switch (arg0.getId()) {
            case R.id.route_spinner:
                if (r[position] == "Cholan Express") {
                    rstation.setVisibility(View.GONE);
                    cstation.setVisibility(View.VISIBLE);
                    spin2.setVisibility(View.VISIBLE);
                    spin3.setVisibility(View.GONE);
                } else if (r[position] == "Rajdhani Express") {
                    cstation.setVisibility(View.GONE);
                    rstation.setVisibility(View.VISIBLE);
                    spin3.setVisibility(View.VISIBLE);
                    spin2.setVisibility(View.GONE);
                }

        break;
        case R.id.cholan_spinner: break;
        // do stuffs with you spinner 1
        //rstation.setVisibility(View.GONE);


        case R.id.rajdhani_spinner: break;
        // do stuffs with you spinner 2


        default:
        break;
    }
}

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }



}
