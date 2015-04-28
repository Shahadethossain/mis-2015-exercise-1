// MIS Exercise
package mmbuw.com.brokenproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

import mmbuw.com.brokenproject.R;


public class AnotherBrokenActivity extends Activity {
    private EditText senddata_txt;
    private Button send_btn;
    private TextView show_text;
    private TextView show_output;
    private String WebUrl = "";
    private static final String DEBUG_TAG = "HttpExample";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_another_broken);

        send_btn = (Button) findViewById(R.id.send_btn);
        show_text = (TextView) findViewById(R.id.show_text);
        show_output = (TextView) findViewById(R.id.show_text);

        Intent intent = getIntent();
        String message = intent.getStringExtra("message");
        //What happens here? What is this? It feels like this is wrong.
        //Maybe the weird programmer who wrote this forgot to do something?

        Context context = getApplicationContext();
        CharSequence text = "From Another : " + message;
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

        show_text.setText("URL : " + message);

        WebUrl = message;

    }

    public void WebResponse(View v){

        HTTPHelper hp = new HTTPHelper();

        String output = hp.retrieve("" + WebUrl);

        show_output.setText("" + output);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.another_broken, menu);
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

    public class HTTPHelper {

        public String retrieve(String url){
            DefaultHttpClient client = new DefaultHttpClient();

            try{

                HttpGet getReq = new HttpGet(url);
                HttpResponse getResponse = client.execute(getReq);
                int statusCode = getResponse.getStatusLine().getStatusCode();

                if(statusCode == HttpStatus.SC_OK){
                    HttpEntity entity = getResponse.getEntity();
                    if(entity != null){
                        String responseStr = EntityUtils.toString(entity);
                        return responseStr;
                    }
                    else{
                        return null;
                    }
                }
            }catch(ClientProtocolException e){
                e.printStackTrace();
                return null;
            }catch (IOException e){
                e.printStackTrace();
                return null;
            }catch (IllegalStateException e){
                e.printStackTrace();
                return null;
            }
            return null;
        }
    }
}
