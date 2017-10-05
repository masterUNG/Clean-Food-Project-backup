package suthasidev.cleanfoodproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class RecipeDetailActivity extends AppCompatActivity {

    //Explicit
    private TextView recipeTextView, descripTextView;
    private ListView ingredientsListView, howToListView;
    private ImageView imageView;
    private String recipeString, descripString, ingredientsString,
            howToString, imageString, nameUserString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        //Bind Widget
        bindWidget();

        //Receive Value From Intent
        receiveValue();

        //Show View
        showView();



    }   //Main Method

    public void clickAddComment(View view) {

        Intent intent = new Intent(RecipeDetailActivity.this, AddCommentActivity.class);
        intent.putExtra("Recipe", recipeString);
        intent.putExtra("nameUser", nameUserString);
        startActivity(intent);

    }   //clickAddComment

    public void clickReadComment(View view) {

        Intent intent = new Intent(RecipeDetailActivity.this, ReadComment.class);
        intent.putExtra("Recipe", recipeString);
        intent.putExtra("nameUser", nameUserString);
        startActivity(intent);

    }   //clickAddComment

    private void showView() {
        //Show TextView
        recipeTextView.setText(recipeString);
        descripTextView.setText(descripString);

        //Show Image
        Picasso.with(RecipeDetailActivity.this).load(imageString)
                .resize(250, 200).into(imageView);
    }

    private void receiveValue() {
        recipeString = getIntent().getStringExtra("nameRecipe");
        imageString = getIntent().getStringExtra("urlRecipe");
        ingredientsString = getIntent().getStringExtra("Ingredients");
        howToString = getIntent().getStringExtra("HowTo");
        descripString = getIntent().getStringExtra("Descrip");
        nameUserString = getIntent().getStringExtra("nameUserString");


    }

    private void bindWidget() {
        recipeTextView = (TextView) findViewById(R.id.textView3);
        descripTextView = (TextView) findViewById(R.id.textView7);

        ingredientsListView = (ListView) findViewById(R.id.listView2);
        howToListView = (ListView) findViewById(R.id.listView3);

        imageView = (ImageView) findViewById(R.id.imageView2);

    }

}   //Main Class
