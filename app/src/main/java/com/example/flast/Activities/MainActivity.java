package com.example.flast.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.flast.Fragments.ImagesFragment;
import com.example.flast.Fragments.ProfileFragment;
import com.example.flast.Fragments.SearchFragment;
import com.example.flast.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.theartofdev.edmodo.cropper.CropImage;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private Fragment selectorFragment;

    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.nav_images:
                        selectorFragment = new ImagesFragment();
                        break;

                    case R.id.nav_search:
                        selectorFragment = new SearchFragment();
                        break;

                    case R.id.nav_post:
                        selectorFragment = null;
                        /*startActivity(new Intent(MainActivity.this, CameraActivity.class));*/
                        CropImage.activity().setAspectRatio(1080,1080).start(MainActivity.this);
                        break;

                    case R.id.nav_profile:
                        selectorFragment = new ProfileFragment();
                        getSharedPreferences("PROFILE", MODE_PRIVATE).edit().putString("profileId", "none").apply();
                        break;
                }

                if (selectorFragment != null){
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectorFragment).commit();
                }

                return true;
            }
        });

        boolean isProfilEdited = getSharedPreferences("EDIT_PROFILE", Context.MODE_PRIVATE).getBoolean("isEdited", false);
        if (isProfilEdited){
            getSharedPreferences("EDIT_PROFILE", MODE_PRIVATE).edit().putBoolean("isEdited", false).apply();
            bottomNavigationView.setSelectedItemId(R.id.nav_profile);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).commit();

        }else{
            Bundle intent = getIntent().getExtras();
            if (intent != null){
                String profileId = intent.getString("publisherId");

                getSharedPreferences("PROFILE", MODE_PRIVATE).edit().putString("profileId", profileId).apply();

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).commit();
            }else{
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ImagesFragment()).commit();
            }
        }

        /*Toast.makeText(this, "Tttt", Toast.LENGTH_SHORT).show();*/

        /*Bundle intent = getIntent().getExtras();
        if (intent != null){
            String profileId = intent.getString("publisherId");

            getSharedPreferences("PROFILE", MODE_PRIVATE).edit().putString("profileId", profileId).apply();

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).commit();
        }else{
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ImagesFragment()).commit();
        }*/

        /*getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ImagesFragment()).commit();*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            imageUri = result.getUri();

            Intent intent = new Intent(MainActivity.this, PostActivity.class);
            intent.putExtra("imageUri", imageUri.toString());
            startActivity(intent);
        }
        else{
            Toast.makeText(this, "Tru again!", Toast.LENGTH_SHORT).show();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ImagesFragment()).commit();
        }
    }
}