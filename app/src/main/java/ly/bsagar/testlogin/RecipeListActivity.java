package ly.bsagar.testlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RecipeListActivity extends AppCompatActivity {
    String token;
    String url = "http://192.168.1.237/health-guide/public/api/recipe";
    ListView recipeList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        recipeList = findViewById(R.id.recipeList);
        //get Token from SP
        SharedPreferences preferences = getSharedPreferences("AUTH",this.MODE_PRIVATE);
        token = preferences.getString("TOKEN", "NO TOKEN");
        // add token to URL
        Uri.Builder builder = Uri.parse(url).buildUpon();
        builder.appendQueryParameter("token",token);
        String newURL = builder.build().toString();
        // make HTTP Request to get Recipe
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest request = new StringRequest(Request.Method.GET
                , newURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Gson gson = new Gson();
                    ArrayList<Recipe> recipes = new ArrayList<>();
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("recipes");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Recipe recipe = gson.fromJson(
                                String.valueOf(jsonArray.getJSONObject(i))
                                , Recipe.class);
                        recipes.add(recipe);
                    }
                    DisplayRecipies(recipes);
                }catch (JSONException e){

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.networkResponse == null){
                    Toast.makeText(RecipeListActivity.this, "Connectivity Issues", Toast.LENGTH_SHORT).show();
                } else if (error.networkResponse.statusCode == 401){
                    Toast.makeText(RecipeListActivity.this, "Unauthorized", Toast.LENGTH_SHORT).show();
                }
            }
        });
        queue.add(request);
    }

    private void DisplayRecipies(ArrayList<Recipe> recipes) {
        RArrayAdapter adapter = new RArrayAdapter(this,
                R.layout.recipe_item_layout,recipes);

        recipeList.setAdapter(adapter);

    }
}
