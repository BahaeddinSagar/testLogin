package ly.bsagar.testlogin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    EditText emailEditText;
    EditText passEditText;
    // AMNA API
    //String url = "http://192.168.1.102/phy/public/api/user/auth/login?";
    //GHASSAN API
    String url = "http://192.168.1.237/health-guide/public/api/auth/login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailEditText = findViewById(R.id.email);
        passEditText = findViewById(R.id.password);

    }


    public void login(View view) {
        //makeStringRequest();
        makeJsonObjectRequest();
    }

    private void makeJsonObjectRequest() {
        String email = emailEditText.getText().toString();
        String password = passEditText.getText().toString();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JSONObject object = new JSONObject();

        try {
            object.put("email", email);
            object.put("password", password);
        } catch (JSONException exception) {
            Toast.makeText(this, "Malformed JSON", Toast.LENGTH_SHORT).show();
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url,
                object
                , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Toast.makeText(LoginActivity.this, "Token is:" +
                            response.getString("access_token"), Toast.LENGTH_SHORT).show();
                    saveToken(response.getString("access_token"));

                } catch (JSONException exx) {
                    exx.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, "Auth error", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(request);
    }

    private void saveToken(String access_token) {
        SharedPreferences preferences = getSharedPreferences("AUTH",
                this.MODE_PRIVATE);
        preferences.edit().putString("TOKEN", access_token).apply();
        startActivity(new Intent(this, HomeActivity.class));
    }

    void makeStringRequest() {
        String email = emailEditText.getText().toString();
        String password = passEditText.getText().toString();
        // fool proofing
        //String AuthUrl = url + "email=" + email + "&password=" + password;
        Uri.Builder builder = Uri.parse(url)
                .buildUpon();
        builder.appendQueryParameter("email", email);
        builder.appendQueryParameter("password", password);
        String AuthUrl = builder.build().toString();
        Toast.makeText(this, AuthUrl, Toast.LENGTH_SHORT).show();

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST,
                AuthUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        parseJson(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.networkResponse == null) {
                    Toast.makeText(LoginActivity.this,
                            "error timeout", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this,
                            "Auth Error: " + error.networkResponse.statusCode, Toast.LENGTH_SHORT).show();
                }
            }
        });
        requestQueue.add(request);
    }

    private void parseJson(String response) {
        JSONObject object;
        String token;
        try {
            object = new JSONObject(response);
            token = object.getString("access_token");
            Log.d("TOKENNN", "parseJson: " + token);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getToken(View view) {
        SharedPreferences preferences = getPreferences(this.MODE_PRIVATE);
        String token = preferences.getString("TOKEN", "NO TOKEN");
        Toast.makeText(this, token, Toast.LENGTH_SHORT).show();
    }
}
