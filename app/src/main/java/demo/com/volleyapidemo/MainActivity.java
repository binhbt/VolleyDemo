package demo.com.volleyapidemo;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private Context mContext;
    private Button btnTextRequest;
    private Button btnJsonRequest;
    private TextView mTextView;
    public static final String JSON_URL = "http://pastebin.com/raw/2bW31yqa";
    public static final String TXT_URL = "http://www.google.com";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Get the application context
        mContext = getApplicationContext();
        // Get the widget reference from XML layout
        btnTextRequest = (Button) findViewById(R.id.btn_txt_request);
        btnJsonRequest = (Button) findViewById(R.id.btn_json_request);

        mTextView = (TextView) findViewById(R.id.txt_result);

        // Set a click listener for button widget
        btnTextRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doTextRequest();
            }
        });
        btnJsonRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doJsonRequest();
            }
        });
    }

    private void doTextRequest() {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, TXT_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        mTextView.setText(response.substring(0, 500));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mTextView.setText("That didn't work!");
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    private void doJsonRequest() {
        // Empty the TextView
        mTextView.setText("");
        // Initialize a new RequestQueue instance
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        // Initialize a new JsonObjectRequest instance
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                JSON_URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Process the JSON
                        try {
                            // Get the JSON array
                            JSONArray array = response.getJSONArray("students");
                            // Loop through the array elements
                            for (int i = 0; i < array.length(); i++) {
                                // Get current json object
                                JSONObject student = array.getJSONObject(i);
                                // Get the current student (json object) data
                                String firstName = student.getString("firstname");
                                String lastName = student.getString("lastname");
                                String age = student.getString("age");
                                // Display the formatted json data in text view
                                mTextView.append(firstName + " " + lastName + "\nage : " + age);
                                mTextView.append("\n\n");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Do something when error occurred
                        Toast.makeText(MainActivity.this, "Co loi xay ra", Toast.LENGTH_LONG).show();
                    }
                }
        );
        // Add JsonObjectRequest to the RequestQueue
        requestQueue.add(jsonObjectRequest);
    }
}
