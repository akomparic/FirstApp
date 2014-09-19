package com.example.firstapp;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Button;
import android.widget.EditText;


public class LoginActivity extends Activity {

    //public final static String LOGIN_INFO = "com.example.firstapp.LOGIN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void loginData(View view) {

        Intent resultData = new Intent(this, SendMessageActivity.class);

        EditText tempusername = (EditText)findViewById(R.id.editText1);
        EditText temppassword = (EditText)findViewById(R.id.editText2);

        String user = tempusername.getText().toString();
        String pass = temppassword.getText().toString();

        String Login = (user + ":" + pass);

        resultData.putExtra("Login_Info", Login);
        setResult(Activity.RESULT_OK, resultData);
        finish();
    }
}