package suthasidev.cleanfoodproject;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import org.jibble.simpleftp.SimpleFTP;

import java.io.File;

/**
 * Created by masterung on 6/10/2017 AD.
 */

public class AddRecipeFragment extends Fragment{

    private Uri uri;
    private ImageView imageView;
    private String pathImageString, nameImageString;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_recipe, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Image Controller
        imageController();

        //Save Controller
        saveController();


    }

    private void saveController() {
        Button button = (Button) getView().findViewById(R.id.btnSave);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //UpLoad Image To Server
                uploadImageToSerVer();

                //Upload Text To Server
                uploadTextToServer();

            }
        });
    }

    private void uploadTextToServer() {





    }

    private void uploadImageToSerVer() {

        String tag = "6octV3";
        try {

            StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy
                    .Builder().permitAll().build();
            StrictMode.setThreadPolicy(threadPolicy);

            SimpleFTP simpleFTP = new SimpleFTP();
            simpleFTP.connect("ftp.swiftcodingthai.com", 21, "bow@swiftcodingthai.com", "Abc12345");
            simpleFTP.bin();
            simpleFTP.cwd("Image");
            simpleFTP.stor(new File(pathImageString));
            simpleFTP.disconnect();



        } catch (Exception e) {
            Log.d(tag, "e uploadImage ==> " + e.toString());
        }

    }

    private void imageController() {
        imageView = (ImageView) getView().findViewById(R.id.imvMyImage);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Please Choose App"), 1);

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String tag = "6octV2";

        if (resultCode == getActivity().RESULT_OK) {

            //Success Choose Image
            Log.d(tag, "Choose Success");
            try {

                uri = data.getData();

                //Show Image
                Bitmap bitmap = BitmapFactory.decodeStream(getActivity()
                        .getContentResolver().openInputStream(uri));
                imageView.setImageBitmap(bitmap);

                //Find Path
                String[] strings = new String[]{MediaStore.Images.Media.DATA};
                Cursor cursor = getActivity().getContentResolver().query(uri, strings, null, null, null);

                if (cursor != null) {
                    cursor.moveToFirst();
                    int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    pathImageString = cursor.getString(index);
                } else {
                    pathImageString = uri.getPath();
                }

                Log.d(tag, "Path of Image ==> " + pathImageString);

            } catch (Exception e) {

                e.printStackTrace();
            }




        }
    }
}   // Main Class
