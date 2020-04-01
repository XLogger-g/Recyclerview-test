package com.example.schoolteacher;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;

import com.example.schoolteacher.Model.Listdata;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class EditActivity extends AppCompatActivity {

    TextInputEditText titleas,descas;
    String titleassend,descassend;
    private DatabaseReference mDatabase;
    private Listdata listdata;
    Button updates,delete;

    private FirebaseUser mCurrentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_close);
        }

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        updates=findViewById(R.id.updatesbutton);
        delete=findViewById(R.id.deletedbutton);
        final Intent i=getIntent();

        String gettitle=i.getStringExtra("title");
        String getdesc=i.getStringExtra("desc");
        final String id=i.getStringExtra("id");
        titleas=findViewById(R.id.title_as);
        descas=findViewById(R.id.desc_as);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        titleas.setText(gettitle);
        descas.setText(getdesc);
        updates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),id,Toast.LENGTH_SHORT).show();
                UpdateNotes(id);
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),id,Toast.LENGTH_SHORT).show();
                deleteNote(id);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }


    private void UpdateNotes(String id)
    {
        titleassend=titleas.getText().toString();
        descassend=descas.getText().toString();
        Listdata listdata = new Listdata(id,titleassend, descassend);

        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        String userID = mCurrentUser.getUid();

        mDatabase.child("Users").child(userID).child("Notes").child(id).setValue(listdata).
                addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(EditActivity.this, "Notes Updated", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),ClassActivity.class));
                    }
                });

    }

    private void deleteNote(String id) {

        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        String userID = mCurrentUser.getUid();

        mDatabase.child("Users").child(userID).child("Notes").child(id).removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(EditActivity.this,"Note deleted",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),ClassActivity.class));

                    }
                });
    }

}
