package suthasidev.cleanfoodproject;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public class ServiceActivity extends AppCompatActivity {

    //Explicit
    private TextView showUserTextView;
    private ListView recipeListView;
    private String nameUserString;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        //Bind Widget
        bindWidget();

        //Show Name
        showName();

        //Create ListView
        createListView();


    }   //Main Method

    private void createListView() {
        //Read All SQLite
        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                MODE_PRIVATE, null);
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + MyManage.table_recipe, null);
        cursor.moveToFirst();

        int intCount = cursor.getCount();
        final String[] recipeString = new String[intCount];
        final String[] ingredientsStrings = new String[intCount];
        final String[] howToStrings = new String[intCount];
        final String[] descriptionStrings = new String[intCount];
        final String[] imageRecipeStrings = new String[intCount];

        for (int i = 0; i < intCount; i++) {
            recipeString[i] = cursor.getString(cursor.getColumnIndex(MyManage.column_Recipe));
            ingredientsStrings[i] = cursor.getString(cursor.getColumnIndex(MyManage.column_Ingredients));
            howToStrings[i] = cursor.getString(cursor.getColumnIndex(MyManage.column_HowTo));
            descriptionStrings[i] = cursor.getString(cursor.getColumnIndex(MyManage.column_Description));
            imageRecipeStrings[i] = cursor.getString(cursor.getColumnIndex(MyManage.column_ImageRecipe));

            cursor.moveToNext();
        }   //for
        cursor.close();

        MyAdapter myAdapter = new MyAdapter(ServiceActivity.this, imageRecipeStrings, recipeString);
        recipeListView.setAdapter(myAdapter);

        recipeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {

                Intent intent = new Intent(ServiceActivity.this, RecipeDetailActivity.class);
                intent.putExtra("nameRecipe", recipeString[i]);
                intent.putExtra("urlRecipe", imageRecipeStrings[i]);
                intent.putExtra("Ingredients", ingredientsStrings[i]);
                intent.putExtra("HowTo", howToStrings[i]);
                intent.putExtra("Descrip", descriptionStrings[i]);
                intent.putExtra("nameUser", nameUserString);
                startActivity(intent);

            }   //OnItemClick
        });

    }   //createListView

    private void showName() {

        //Receive Value From Intent
        nameUserString = getIntent().getStringExtra("userName");
        showUserTextView.setText("Welcome" + nameUserString);

    }   //showName

    private void bindWidget() {
        showUserTextView = (TextView) findViewById(R.id.list_txt);
        recipeListView = (ListView) findViewById(R.id.listView);

    }
}   //Main Class
