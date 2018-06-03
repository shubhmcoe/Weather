package com.example.dell.dncstdio;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.VideoView;

//Activity Variable
public class Login extends Activity {

	EditText user, pass;
	Button login, not_reg;
	DatabaseHandler db;


		@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		user = findViewById(R.id.eduser);
		pass = findViewById(R.id.edpass);
		login = findViewById(R.id.login);
		not_reg = findViewById(R.id.not_reg);
		
		login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			db=new DatabaseHandler(Login.this, null, null, 2);
			String username=user.getText().toString().trim();
			String password= pass.getText().toString();
			
			String StoredPassword =db.getregister(username);
			if(password.equals(StoredPassword)){

				//Toast.makeText(getApplicationContext(), "Login Successfully", Toast.LENGTH_SHORT).show();
				startActivity(new Intent(getApplicationContext(),MapsActivity.class));

			}
			else{
				Toast.makeText(getApplicationContext(), "Username/Password incorrect", Toast.LENGTH_SHORT).show();
			user.setText("");
			pass.setText("");
			}
			
			
			}
		});

            not_reg.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    // TODO Auto-generated method stub

                    startActivity(new Intent(getApplicationContext(), new_sign_up.class));
                }
            });

		}



}


