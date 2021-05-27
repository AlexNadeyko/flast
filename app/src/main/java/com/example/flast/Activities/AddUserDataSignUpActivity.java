package com.example.flast.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.flast.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddUserDataSignUpActivity extends AppCompatActivity {

    private EditText statusEditText;
    private EditText aboutYourselfEditText;
    private Button submitButton;
    private CircleImageView imageProfile;
    private TextView selectPhoto;

    private Uri imageUri;
    private StorageTask uploadTask;
    private StorageReference storageReference;
    private FirebaseUser firebaseUser;
//    private ProgressDialog progressDialog;

    private DatabaseReference mRootRef;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user_data_sign_up);

        aboutYourselfEditText = findViewById(R.id.about_yourself_edit_text_add_user_data);
        submitButton = findViewById(R.id.button_submit_last_step);
        imageProfile = findViewById(R.id.image_profile);
        selectPhoto = findViewById(R.id.select_photo);

//        progressDialog = new ProgressDialog(this);

        storageReference = FirebaseStorage.getInstance().getReference().child("Uploads");
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mRootRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        selectPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity().setCropShape(CropImageView.CropShape.OVAL).start(AddUserDataSignUpActivity.this);

            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUserData();

                startActivity(new Intent(AddUserDataSignUpActivity.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                finish();
            }
        });
    }

    //Edit, add new values to information of user
    private void addUserData(){

        /*String statusText = statusEditText.getText().toString();*/
        String aboutYourself = aboutYourselfEditText.getText().toString();

        HashMap<String, Object> map = new HashMap<>();
        map.put("aboutYourself", aboutYourself);

        FirebaseDatabase.getInstance().getReference().child("Users").child(firebaseUser.getUid()).updateChildren(map);

        /*progressDialog.setMessage("");
        progressDialog.show();*/

        /*startActivity(new Intent(AddUserDataSignUpActivity.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
        finish();*/

        /*HashMap<String, Object> map = new HashMap<>();
        *//*map.put("status", status);*//*
        map.put("aboutYourself", aboutYourself);
        map.put("id", mAuth.getCurrentUser().getUid());
        map.put("imageUrl", "default");

        mRootRef.child("Users").child(mAuth.getCurrentUser().getUid()).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(AddUserDataSignUpActivity.this, "inside!", Toast.LENGTH_SHORT).show();
                if (task.isSuccessful()){
//                    progressDialog.dismiss();
                    Toast.makeText(AddUserDataSignUpActivity.this, "Update the profile for better experience.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(AddUserDataSignUpActivity.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    finish();
                }else{
                    Toast.makeText(AddUserDataSignUpActivity.this, "Fail!", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
//                progressDialog.dismiss();
                Toast.makeText(AddUserDataSignUpActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });*/

        /*mRootRef.child("Users").child(mAuth.getCurrentUser().getUid()).push().setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(AddUserDataSignUpActivity.this, "added!", Toast.LENGTH_SHORT).show();
            }


        });*/

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK){
            CropImage.ActivityResult  result = CropImage.getActivityResult(data);
            imageUri = result.getUri();

            uploadImage();
        }else{
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadImage() {
        ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Uploading");
        pd.show();

        if (imageUri != null){
            StorageReference fileRef = storageReference.child(System.currentTimeMillis() + ".jpeg");

            uploadTask = fileRef.putFile(imageUri);
            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if (!task.isSuccessful()){
                        throw task.getException();
                    }

                    return fileRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()){
                        Uri downloadUri = task.getResult();
                        String url = downloadUri.toString();

                        FirebaseDatabase.getInstance().getReference().child("Users").child(firebaseUser.getUid()).child("imageUrl").setValue(url);
                        Picasso.get().load(url).placeholder(R.mipmap.ic_launcher).into(imageProfile);
                        pd.dismiss();
                    }else{
                        Toast.makeText(AddUserDataSignUpActivity.this, "Upload failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else{
            Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show();
        }
    }
}