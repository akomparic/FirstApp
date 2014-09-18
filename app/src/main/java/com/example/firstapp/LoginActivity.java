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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText tempusername = (EditText)findViewById(R.id.editText1);
        EditText temppassword = (EditText)findViewById(R.id.editText2);
        Button login = (Button)findViewById(R.id.button);

        String user = tempusername.getText().toString();
        String pass = temppassword.getText().toString();

        String LoginInfo = (user + ":" + pass);

        Intent resultData = new Intent(this, SendMessageActivity.class);
        resultData.putExtra("LoginInfo", LoginInfo);
        setResult(Activity.RESULT_OK, resultData);
        finish();
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
}