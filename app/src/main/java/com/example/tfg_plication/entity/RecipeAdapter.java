package com.example.tfg_plication.entity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tfg_plication.R;

import java.util.ArrayList;

public class RecipeAdapter extends ArrayAdapter<Recipe> {

    public RecipeAdapter(Context context, ArrayList<Recipe> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Recipe recipe = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.format_recipes, parent, false);
        }
        // Lookup view for data population

        ImageView imgRecipe = (ImageView) convertView.findViewById(R.id.img_from_all);
        TextView tvName = (TextView) convertView.findViewById(R.id.name_from_all);
        EditText info = (EditText) convertView.findViewById(R.id.info_from_all);
        TextView cal = (TextView) convertView.findViewById(R.id.cal_from_all);
        TextView type = (TextView) convertView.findViewById(R.id.type_from_all);
        // Populate the data into the template view using the data object
        imgRecipe.setImageBitmap(recipe.img);
        tvName.setText(recipe.name);
        info.setText(recipe.recipeText);
        cal.setText(recipe.fatten);
        type.setText(recipe.typeofFood);

        // Return the completed view to render on screen
        return convertView;
    }
}
