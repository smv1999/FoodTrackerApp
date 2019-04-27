package com.example.exam.foodtrack;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.LocationListener;
import android.location.LocationManager;
import android.opengl.Visibility;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    Button login;
    Button reg2;
    EditText username, password, uname, pwd;
    TextView reg1;
    public static final String PREFS_NAME = "MyPrefsFile";
    LinearLayout r1;
    LinearLayout r2;
    int st = 10;
    private DrawerLayout d1;
    final int CAMERA_PIC_REQUEST=123;
    private ActionBarDrawerToggle abdt;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference();
    LocationManager lm;
    LocationListener ll;

    private FirebaseAuth mAuth;
    String  userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





        mAuth = FirebaseAuth.getInstance();




        login = (Button) findViewById(R.id.login);
        reg1 = (TextView) findViewById(R.id.reg1);
        r1 = (LinearLayout) findViewById(R.id.r1);
        r2 = (LinearLayout) findViewById(R.id.r2);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        uname = (EditText) findViewById(R.id.uname);
        pwd = (EditText) findViewById(R.id.pwd);
        reg2 = (Button) findViewById(R.id.reg2);


        r1.setVisibility(View.VISIBLE);
        r2.setVisibility(View.GONE);



        SpannableString content1 = new SpannableString("Not a member? Sign Up!");
        content1.setSpan(new UnderlineSpan(), 0, content1.length(), 0);
        reg1.setText(content1);


        reg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                st = 5;
                r1.setVisibility(View.GONE);
                r2.setVisibility(View.VISIBLE);
            }
        });


        reg2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                final String u = uname.getText().toString();
                final String p = pwd.getText().toString();
                TelephonyManager myTelephonyManager;
                PhoneStateListener callStateListener;
                myTelephonyManager=(TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);

                callStateListener = new PhoneStateListener(){
                    public void onDataConnectionStateChanged(int state){
                        switch(state){
                            case TelephonyManager.DATA_DISCONNECTED:
                                Log.i("State: ", "Offline");
                                String stateString = "You are Offline! Check your Network Connection...";
                                Toast.makeText(getApplicationContext(),
                                        stateString, Toast.LENGTH_LONG).show();
                                break;
                            case TelephonyManager.DATA_SUSPENDED:
                                Log.i("State: ", "IDLE");
                                stateString = "Idle";
                                Toast.makeText(getApplicationContext(),
                                        stateString, Toast.LENGTH_LONG).show();
                                break;
                        }
                    }
                };
                myTelephonyManager.listen(callStateListener,
                        PhoneStateListener.LISTEN_DATA_CONNECTION_STATE);



                if (u.matches("") || p.matches("")) {
                    Toast.makeText(getApplicationContext(), "You did not enter username and password", Toast.LENGTH_SHORT).show();

                } else {



                    mAuth.createUserWithEmailAndPassword(u,p)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if(task.isSuccessful()) {
                                        FirebaseUser firebaseUser = mAuth.getCurrentUser();

                                        String uid;
                                        assert firebaseUser != null;
                                        uid = firebaseUser.getUid();
                                        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(uid);
                                        r1.setVisibility(View.VISIBLE);
                                        r2.setVisibility(View.GONE);

                                        Toast.makeText(getApplicationContext(), "Registered", Toast.LENGTH_LONG).show();



                                    }
                                    else {
                                        // If sign in fails, display a message to the user.

                                        Toast.makeText(getApplicationContext(), "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();

                                    }

                                }
                            });





                }


            }
        });

        if(username.isEnabled())
        {
            username.setError("Enter username@foodtrack.com");
        }
        if(password.isEnabled())
        {
            password.setError("Enter password");
        }
        if(uname.isEnabled())
        {
            uname.setError("Enter username@foodtrack.com");
        }
        if(pwd.isEnabled())
        {
            pwd.setError("Enter password");
        }


        username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username.setError("Enter username@foodtrack.com");

            }
        });
        password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                password.setError("Enter password");

            }
        });
        uname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uname.setError("Enter username@foodtrack.com");

            }
        });
        pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pwd.setError("Enter password");
            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final String u = username.getText().toString();
                String p = password.getText().toString();
                TelephonyManager myTelephonyManager;
                PhoneStateListener callStateListener;
                myTelephonyManager=(TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);



                callStateListener = new PhoneStateListener(){
                    public void onDataConnectionStateChanged(int state){
                        switch(state){
                            case TelephonyManager.DATA_DISCONNECTED:
                                Log.i("State: ", "Offline");
                                String stateString = "You are Offline! Check your Network Connection...";
                                Toast.makeText(getApplicationContext(),
                                        stateString, Toast.LENGTH_LONG).show();
                                break;
                            case TelephonyManager.DATA_SUSPENDED:
                                Log.i("State: ", "IDLE");
                                stateString = "Idle";
                                Toast.makeText(getApplicationContext(),
                                        stateString, Toast.LENGTH_LONG).show();
                                break;
                        }
                    }
                };
                myTelephonyManager.listen(callStateListener,
                        PhoneStateListener.LISTEN_DATA_CONNECTION_STATE);


                if (u.matches("") || p.matches("")) {

                    Toast.makeText(getApplicationContext(), "You did not enter username and password", Toast.LENGTH_SHORT).show();
                } else {
                    mAuth.signInWithEmailAndPassword(u,p).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {


                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Logging you in...", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(MainActivity.this, Profile_Basic_Activity.class);
                                intent.putExtra("u1",u);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(), "Invalid login! Please try again...", Toast.LENGTH_LONG).show();

                            }


                        }
                    });

                }

            }


        });

    }


    @Override
    public void onBackPressed() {
        if (st == 10) {
            new AlertDialog.Builder(this)
                    .setMessage("Are you sure you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            MainActivity.this.finish();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        }
        else{
            st=10;
            r1.setVisibility(View.VISIBLE);
            r2.setVisibility(View.GONE);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.exit){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onPrepareOptionsMenu (Menu menu) {
        menu.findItem(R.id.logout).setEnabled(false);
        return true;
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            // User is signed in
            Intent k = new Intent(MainActivity.this, Profile_Basic_Activity.class); //optionsactivity
            startActivity(k);
        }

    }
}

