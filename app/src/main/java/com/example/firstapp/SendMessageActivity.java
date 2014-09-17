package com.example.firstapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.MenuItem;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AUTH;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.MalformedChallengeException;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.DigestScheme;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.lang.String;


public class SendMessageActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String filename = "SendTextStorage";
        final String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        FileOutputStream outputStream;

        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(message.getBytes());
            outputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        Thread t = new Thread() {
            String user = getString(R.string.username);
            String password = getString(R.string.password);

            public void run() {
                Looper.prepare(); //For Preparing Message Pool for the child Thread

                DefaultHttpClient client = new DefaultHttpClient();
                DefaultHttpClient client2 = new DefaultHttpClient();

                HttpPut httpput = new HttpPut("http://192.168.232.1:8050/digest/");
                JSONObject json = new JSONObject();
                try {
                    HttpResponse response = client.execute(httpput);

                    if (response.getStatusLine().getStatusCode() == HttpStatus.SC_UNAUTHORIZED) {
                        Header authHeader = response.getFirstHeader(AUTH.WWW_AUTH);

                        DigestScheme digestScheme = new DigestScheme();

                        digestScheme.processChallenge(authHeader);

                        UsernamePasswordCredentials creds = new UsernamePasswordCredentials(user, password);
                        httpput.addHeader(digestScheme.authenticate(creds, httpput));

                        ResponseHandler<String> responseHandler = new BasicResponseHandler();
                        json.put("content", message);
                        StringEntity se = new StringEntity(json.toString());
                        se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                        httpput.setEntity(se);
                        String responseBody = client2.execute(httpput, responseHandler);
                    }
                } catch (MalformedChallengeException e) {
                    e.printStackTrace();
                } catch (AuthenticationException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    client.getConnectionManager().shutdown();
                    client2.getConnectionManager().shutdown();
                }
                Looper.loop();
            }
        };
        t.start();
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