package com.example.dell.dncstdio;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by HP on 10/10/2017.
 */


public class new_sign_up extends Login {

    DatabaseHandler db;
    EditText email,mobile,pass,confpass,last,first;
    Button save,cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signupp);

        email= findViewById(R.id.editemail);
        mobile = findViewById(R.id.editmobileno);
        pass= findViewById(R.id.editpassword);
        confpass= findViewById(R.id.editconformpassword);
        last = findViewById(R.id.editlastname);
        first= findViewById(R.id.editfirstname);

        save= findViewById(R.id.btnsave);
        cancel= findViewById(R.id.btncancel);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent =new Intent(new_sign_up.this, Login.class);

                myintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(myintent);
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String edfirst = first.getText().toString();
                final String edlast = last.getText().toString();
                final String edemail = email.getText().toString();
                final String edmobile = mobile.getText().toString();
                final String edpass = pass.getText().toString();
                final String edConf = confpass.getText().toString();
                if((check(edfirst,edlast,edemail,edmobile,edpass,edConf)==0)){
                 try {


                     db = new DatabaseHandler(new_sign_up.this, null, null, 2);
                     Registerdata reg = new Registerdata();


                     reg.setfirstName(edfirst);

                     reg.setlastName(edlast);
                     reg.setEmailId(edemail);
                     reg.setMobNo(edmobile);
                     reg.setPassword(edpass);
                     db.addregister(reg);

                     Toast.makeText(getApplicationContext(), "Registered Successfully", Toast.LENGTH_SHORT).show();
                     Intent myintent = new Intent(new_sign_up.this, Login.class);

                     //startActivityForResult(new Intent(Sign_up.this, Login.class), 1);
                     myintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                     startActivity(myintent);
                 }catch (Exception e){
                     Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                 }
                }

            }
        });
    }

    public int check (String fn , String ln, String em, String mob, String pa, String cpa) {
        final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


        if (fn.length()<3) {
            Toast.makeText(getApplicationContext(), "Enter Valid First Name ", Toast.LENGTH_SHORT).show();
            first.setText("");
        } else {
            if (ln.length()<3) {
                Toast.makeText(getApplicationContext(), "Enter Valid Last Name", Toast.LENGTH_SHORT).show();
                last.setText("");
            } else {
                if (em.matches(emailPattern) == false) {

                    Toast.makeText(getApplicationContext(), "Enter Valid Email!!", Toast.LENGTH_SHORT).show();
                    email.setText("");
                } else {
                    if (mob.length()<7) {
                        Toast.makeText(getApplicationContext(), "Enter Valid Mobile no.!!", Toast.LENGTH_SHORT).show();
                        mobile.setText("");
                    } else {
                        if (pa.length() < 6) {
                            Toast.makeText(getApplicationContext(), "Password should contain atleast 6 characters", Toast.LENGTH_SHORT).show();
                            pass.setText("");
                        } else {
                            if ((cpa.equals(pa))) {
                                return 0;
                            } else {

                                Toast.makeText(getApplicationContext(), "Password doesn't match", Toast.LENGTH_SHORT).show();
                                pass.setText("");
                                confpass.setText("");
                            }
                        }
                    }
                }

            }

        }
        return 1;
    }

}
