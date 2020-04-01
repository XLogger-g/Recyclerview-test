package com.example.schoolteacher;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;

import com.example.schoolteacher.Model.AssignListdata;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddAssignmentActivity extends AppCompatActivity {

    TextInputEditText titleas,descas;
    String titleassend,descassend;
    private DatabaseReference mDatabase;
    String userID;

    // Date picker variables

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.common.R.layout.activity_add_assignment);

        //toolbar

        Toolbar toolbar = findViewById(com.example.common.R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(com.example.common.R.drawable.ic_close);
        }

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        //


        titleas=findViewById(R.id.title_as);
        descas=findViewById(R.id.desc_as);
        mDatabase = FirebaseDatabase.getInstance().getReference();


    }


    // itemSelected
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }
/*
        switch (item.getItemId()) {
            case R.id.action_attach:
//                startActivity(new Intent(getApplicationContext(), .class));
                return true;
            case R.id.action_send:
//                startActivity(new Intent(getApplicationContext(), .class));
                return true;
            default:
                // Do nothing
        }
        */
        return super.onOptionsItemSelected(item);
    }
// end itemSelected

    public void AddAssign(View view) {
        titleassend=titleas.getText().toString();
        descassend=descas.getText().toString();
        if(TextUtils.isEmpty(titleassend) || TextUtils.isEmpty(descassend)){
            return;
        }
        AddAssign(titleassend,descassend);

    }

    private void AddAssign(String titleassend, String descassend) {

        String id=mDatabase.push().getKey();
        AssignListdata listdata = new AssignListdata(id,titleassend, descassend);
        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mDatabase.child("Users").child(userID).child("Notes").child(id).child("Assignments").setValue(listdata).
                addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(AddAssignmentActivity.this, "Assignment Added", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),ClassworkActivity.class));
                    }
                });

    }


}