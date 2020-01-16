package ly.bsagar.testlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {

    TextView tokenTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        tokenTextView = findViewById(R.id.myToken);
        getToken();
    }

    public void getToken() {
        SharedPreferences preferences = getSharedPreferences("AUTH",this.MODE_PRIVATE);
        String token = preferences.getString("TOKEN", "NO TOKEN");
        tokenTextView.setText(token);
    }

    public void goToRecipces(View view) {
        startActivity(new Intent(this,RecipeListActivity.class));
    }
}
