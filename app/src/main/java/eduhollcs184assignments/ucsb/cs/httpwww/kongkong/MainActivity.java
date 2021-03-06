package eduhollcs184assignments.ucsb.cs.httpwww.kongkong;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    enum Category {
        LEASE, LUGGAGE, PARKING, PETS, RENTAL, OTHER, ALL;

        public static Category toCategory(String s) {
            Category tmp;
            if (s == null) return OTHER;
            switch (s) {
                case "Luggage Stroage":
                    tmp = LUGGAGE;
                    break;
                case "Parking":
                    tmp = PARKING;
                    break;
                case "Pet Forsterage":
                    tmp = PETS;
                    break;
                case "Short-Term Lease":
                    tmp = LEASE;
                    break;
                case "Bike/Car Rental":
                    tmp = RENTAL;
                    break;
                case "Others":
                    tmp = OTHER;
                    break;
                default:
                    tmp = OTHER;
                    break;
            }
            return tmp;
        }
    }

    public static Category category;
    private FirebaseAuth mAuth;
    FirebaseUser user;
    private Menu menu2;
    private MenuItem loginMenu;
    private NavigationView navigationView;
    private Button logInBt;
    FloatingActionButton fab;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        this.menu2 = menu;

        if (user == null){
            loginMenu = menu2.findItem(R.id.action_logout);
            MenuItem profileMenu = menu2.findItem(R.id.action_profile);
            profileMenu.setEnabled(false);
            loginMenu.setTitle("Login");
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        fab = (FloatingActionButton) findViewById(R.id.fab);


            fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(user != null){
                Intent myIntent = new Intent(MainActivity.this, PostActivity.class);
                startActivity(myIntent);
                }
                else{
                    Toast.makeText(MainActivity.this, "Please sign in to unlock more features...",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //get navigation bar
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        logInBt = (Button) findViewById(R.id.button2);


    }

    @Override
    public void onStart() {
        super.onStart();
        if (user != null) {

            String userID = user.getUid();
            String userEmail = user.getEmail();

            TextView userTV = (TextView) findViewById(R.id.userTV);
            userTV.setText(user.toString());
            TextView userIDTV = (TextView) findViewById(R.id.userIDTV);
            userIDTV.setText(userID);
            TextView userEmailTV = (TextView) findViewById(R.id.userEmailTV);
            userEmailTV.setText(userEmail);
            //navigation bar info
            View header = navigationView.getHeaderView(0);
            TextView navUserEmail = (TextView) header.findViewById(R.id.userEmail);
            navUserEmail.setText(userEmail);
            TextView navUserID = (TextView) header.findViewById(R.id.userID);
            navUserID.setText("Welcome to Kong");
        } else {
            logInBt.setText("Login");
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            category = Category.ALL;
            Intent myIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(myIntent);
            return true;
        }
        if (id == R.id.action_profile) {
            category = Category.ALL;
            Intent myIntent = new Intent(MainActivity.this, MainActivity.class);
            startActivity(myIntent);
            return true;
        }
        if (id == R.id.action_public) {
            category = Category.ALL;
            Intent myIntent = new Intent(MainActivity.this, PostViewActivity.class);
            startActivity(myIntent);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        displaySelectedScreen(id);
        return true;
    }

    private void displaySelectedScreen(int itemId) {

        //creating fragment object
        Fragment fragment = null;
        Intent myIntent = null;
        //initializing the fragment object which is selected
        switch (itemId) {
            case R.id.nav_luggage:
                category = Category.LUGGAGE;
                myIntent = new Intent(MainActivity.this, PostViewActivity.class);
                startActivity(myIntent);
                break;
            case R.id.nav_parking:
                category = Category.PARKING;
                myIntent = new Intent(MainActivity.this, PostViewActivity.class);
                startActivity(myIntent);
                break;
            case R.id.nav_pets:
                category = Category.PETS;
                myIntent = new Intent(MainActivity.this, PostViewActivity.class);
                startActivity(myIntent);
                break;
            case R.id.nav_lease:
                category = Category.LEASE;
                myIntent = new Intent(MainActivity.this, PostViewActivity.class);
                startActivity(myIntent);
                break;
            case R.id.nav_rental:
                category = Category.RENTAL;
                myIntent = new Intent(MainActivity.this, PostViewActivity.class);
                startActivity(myIntent);
                break;
            case R.id.nav_other:
                category = Category.OTHER;
                myIntent = new Intent(MainActivity.this, PostViewActivity.class);
                startActivity(myIntent);
                break;
            case R.id.nav_about:
                fragment = new AboutFragment();
                break;
        }

        //replacing the fragment
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    public void signOut(View view) {
        FirebaseAuth.getInstance().signOut();
        Intent myIntent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(myIntent);
        finish();

    }

    public void Post(View view) {
        Intent myIntent = new Intent(MainActivity.this, PostActivity.class);
        startActivity(myIntent);
    }

    public void gotoProfile(View view) {
        Intent myIntent = new Intent(MainActivity.this, MainActivity.class);
        startActivity(myIntent);
    }
}
