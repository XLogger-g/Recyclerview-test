package com.example.schoolteacher;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;

import com.example.schoolteacher.Adapter.NotesAdapter;
import com.example.schoolteacher.Model.Listdata;
import com.example.schoolteacher.login.LoginActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.ArrayList;
import java.util.List;

public class ClassActivity extends AppCompatActivity {

    String userID;

    // Firebase instance variables
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    //HomeScreen variables
    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    List<Listdata> list =new ArrayList<>();
    Context context;
    private FirebaseUser mCurrentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class);

        Toolbar toolbar = findViewById(R.id.toolbar);
        getSupportActionBar();
        setSupportActionBar(toolbar);
        setTitle("Classes");

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        recyclerView=findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(ClassActivity.this);
        recyclerView.setLayoutManager(layoutManager);



        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setSelectedItemId(R.id.bottomBarItemSecond);


        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.bottomBarItemFirst:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0,0);
                        break;
                    case R.id.bottomBarItemSecond:

                        break;
                    case R.id.bottomBarItemThird:
                        startActivity(new Intent(getApplicationContext(), MessageActivity.class));
                        overridePendingTransition(0,0);
                        break;
                    case R.id.bottomBarItemFourth:
                        break;
                    case R.id.bottomBarItemFifth:
                        break;
                }

                return true;
            }
        });



        // Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();



        //Navigation drawer
        new DrawerBuilder().withActivity(this).build();

        //primary items
        PrimaryDrawerItem profile = new PrimaryDrawerItem()
                .withIdentifier(1)
                .withName(R.string.drawer_item_name)
                .withDescription("Edit Profile")
                .withDescriptionTextColorRes(R.color.black_overlay)
                .withIcon(R.drawable.ic_account_circle);

        //secondary items
        SecondaryDrawerItem calendar = (SecondaryDrawerItem) new SecondaryDrawerItem()
                .withIdentifier(11)
                .withName(R.string.drawer_item_calendar)
                .withIcon(R.drawable.ic_calendar);
        SecondaryDrawerItem attendance = (SecondaryDrawerItem) new SecondaryDrawerItem()
                .withIdentifier(12)
                .withName(R.string.drawer_item_attendance)
                .withIcon(R.drawable.ic_attendance);
        SecondaryDrawerItem whatsdue = (SecondaryDrawerItem) new SecondaryDrawerItem()
                .withIdentifier(13)
                .withName(R.string.drawer_item_due)
                .withIcon(R.drawable.ic_assignment);
        SecondaryDrawerItem grades = (SecondaryDrawerItem) new SecondaryDrawerItem()
                .withIdentifier(14)
                .withName(R.string.drawer_item_grades)
                .withIcon(R.drawable.ic_grades);
        SecondaryDrawerItem folders = (SecondaryDrawerItem) new SecondaryDrawerItem()
                .withIdentifier(15)
                .withName(R.string.drawer_item_folders)
                .withIcon(R.drawable.ic_folder);

        //settings, help, contact items
        SecondaryDrawerItem settings = (SecondaryDrawerItem) new SecondaryDrawerItem()
                .withIdentifier(97)
                .withName(R.string.drawer_item_settings)
                .withIcon(R.drawable.ic_settings);
        SecondaryDrawerItem help = (SecondaryDrawerItem) new SecondaryDrawerItem()
                .withIdentifier(98)
                .withName(R.string.drawer_item_help)
                .withIcon(R.drawable.ic_help);
        SecondaryDrawerItem logout = (SecondaryDrawerItem) new SecondaryDrawerItem()
                .withIdentifier(99)
                .withName(R.string.drawer_item_logout)
                .withIcon(R.drawable.ic_logout);



        new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withActionBarDrawerToggleAnimated(true)
                .withTranslucentStatusBar(false)
                .withFullscreen(true)
                .withSavedInstance(savedInstanceState)
                .addDrawerItems(
                        profile,
                        new SectionDrawerItem(),
                        calendar,
                        attendance,
                        whatsdue,
                        grades,
                        folders,
                        new DividerDrawerItem(),
                        settings,
                        help,
                        logout

                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem != null) {
                            Intent intent = null;
                            if (drawerItem.getIdentifier() == 1) {
                                intent = new Intent(ClassActivity.this, ProfileInfoActivity.class);
                            } else if (drawerItem.getIdentifier() == 2) {
                                //intent = new Intent(MainActivity.this, Class.class);
                            } else if (drawerItem.getIdentifier() == 3) {
                                //intent = new Intent(MainActivity.this, Class.class);
                            } else if (drawerItem.getIdentifier() == 11) {
                                //intent = new Intent(MainActivity.this, Class.class);
                            } else if (drawerItem.getIdentifier() == 12) {
                                //intent = new Intent(MainActivity.this, Class.class);
                            } else if (drawerItem.getIdentifier() == 13) {
                                //intent = new Intent(MainActivity.this, Class.class);
                            } else if (drawerItem.getIdentifier() == 97) {
                                intent = new Intent(ClassActivity.this, Settings.class);
                            } else if (drawerItem.getIdentifier() == 98) {
                                intent = new Intent(ClassActivity.this, Help.class);
                            } else if (drawerItem.getIdentifier() == 99) {
                                FirebaseAuth.getInstance().signOut();
                                sendToStart();
                            }
                            if (intent != null) {
                                ClassActivity.this.startActivity(intent);
                            }
                        }

                        return false;
                    }
                })
                .build();
        //End of Navigation drawer


        // Home Screen

        final NotesAdapter notesAdapter=new NotesAdapter(list,this);
        recyclerView.setAdapter(notesAdapter);

        FloatingActionButton fab = findViewById(R.id.fab);


        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();

        String userID = mCurrentUser.getUid();

        firebaseDatabase=FirebaseDatabase.getInstance();

        databaseReference = firebaseDatabase.getReference().child("Users").child(userID).child("Notes");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    Listdata listdata=dataSnapshot1.getValue(Listdata.class);
                    list.add(listdata);

                }
                notesAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(), AddClassActivity.class));
            }
        });

        // end HomeScreen


    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mFirebaseAuth.getCurrentUser();
        if(currentUser == null){
            sendToStart();
        }
    }

    private void sendToStart() {
        Intent startIntent = new Intent(ClassActivity.this, LoginActivity.class);
        startActivity(startIntent);
        finish();
    }

}


