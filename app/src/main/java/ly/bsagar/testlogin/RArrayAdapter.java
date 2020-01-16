package ly.bsagar.testlogin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.List;

public class RArrayAdapter extends ArrayAdapter<Recipe> {
    String baseURL = "http://192.168.1.237/health-guide/public/storage/recipes_images/";

    public RArrayAdapter(@NonNull Context context, int resource,
                         @NonNull List<Recipe> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.recipe_item_layout,
                    parent,false);
        }
        TextView titleTextView = convertView.findViewById(R.id.recipeTitle);
        TextView IngredientTextView = convertView.findViewById(R.id.recipeIngredients);
        ImageView imageView = convertView.findViewById(R.id.recipeImage);

        Recipe recipe = getItem(position);

        titleTextView.setText(recipe.name);
        IngredientTextView.setText(recipe.ingredients);
        Picasso.get().load(baseURL+recipe.image).into(imageView);

        return convertView;
    }
}
