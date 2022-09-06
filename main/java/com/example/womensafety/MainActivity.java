package com.example.womensafety;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;


import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.os.UserManager;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static com.example.womensafety.SOSBroadcastReceiver.TRIGGER_THRESHOLD;
import static com.example.womensafety.SOSBroadcastReceiver.TWO_SEC;
import static com.example.womensafety.SOSBroadcastReceiver.triggerCounter;
import static com.example.womensafety.SOSBroadcastReceiver.triggerInProgress;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    public  String loc = "";
    private ImageButton ib;
    private TextView tv;
    private int counter = 0;
    GPSTracker gps;
    private static final String TAG = MainActivity.class.getSimpleName();

    private static final int RESULT_PICK_CONTACT = 4546;
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
    private ListView selectedContactList;

    private SharedPreferences pref;
    DatabaseHandler contactDB = new DatabaseHandler(this);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        selectedContactList = (ListView) findViewById(R.id.selectedContactList);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
                startActivityForResult(intent, RESULT_PICK_CONTACT);

            }
        });
//
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {Manifest.permission.READ_CONTACTS, Manifest.permission.SEND_SMS,
                Manifest.permission.CALL_PHONE, Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET,
                Manifest.permission.RECEIVE_BOOT_COMPLETED, Manifest.permission.SYSTEM_ALERT_WINDOW
        };

        if(!hasPermissions(this, PERMISSIONS)){
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }



        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        mAppBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
//                R.id.nav_tools, R.id.nav_share, R.id.nav_bug,R.id.nav_sug,R.id.nav_host_fragment)
//                .setDrawerLayout(drawer)
//                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
//        NavigationUI.setupWithNavController(navigationView, navController);

        startService(new Intent(this, SOSService.class));

        updateContactList();


    }


    public void removeSelectedContact(View view) {

        TextView textView = view.findViewById(R.id.myname);
        Log.i("contact_clicked", String.valueOf(textView.getText()));
        final String contactName = textView.getText().toString();
        new AlertDialog.Builder(this)
                .setTitle("Delete")
                .setMessage("Do You Really Want to delete " + contactName + " ?")
                .setIcon(android.R.drawable.ic_menu_delete)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        contactDB.deleteContactByName(contactName);
                        Toast.makeText(MainActivity.this, "Contact Deleted Successfully.", Toast.LENGTH_SHORT).show();
                        updateContactList();
                    }
                })
                .setNegativeButton(android.R.string.no, null).show();

    }

    private void updateContactList() {
        List<Contact> contacts = contactDB.getAllContacts();
        CustomListViewAdapter adapter = new CustomListViewAdapter(this,
                R.layout.mylistview, contacts);
        selectedContactList.setAdapter(adapter);

    }

    @Override
    public void onStart() {
        super.onStart();
        if (!checkPermissions()) {
            requestPermissions();
        }
    }


    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION);

        // Provide an additional rationale to the user. This would happen if the user denied the
        // request previously, but didn't check the "Don't ask again" checkbox.
        if (shouldProvideRationale) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.");

            showSnackbar(R.string.permission_rationale, android.R.string.ok,
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Request permission
                            startLocationPermissionRequest();
                        }
                    });

        } else {
            Log.i(TAG, "Requesting permission");
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the user denied the permission
            // previously and checked "Never ask again".
            startLocationPermissionRequest();
        }
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        Log.i(TAG, "onRequestPermissionResult");

        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length <= 0) {
                // If user interaction was interrupted, the permission request is cancelled and you
                // receive empty arrays.
                Log.i(TAG, "User interaction was cancelled.");
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted.
            } else {
                // Permission denied.

                // Notify the user via a SnackBar that they have rejected a core permission for the
                // app, which makes the Activity useless. In a real app, core permissions would
                // typically be best requested during a welcome-screen flow.

                // Additionally, it is important to remember that a permission might have been
                // rejected without asking the user for permission (device policy or "Never ask
                // again" prompts). Therefore, a user interface affordance is typically implemented
                // when permissions are denied. Otherwise, your app could appear unresponsive to
                // touches or interactions which have required permissions.
                showSnackbar(R.string.permission_denied_explanation, R.string.settings,
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // Build intent that displays the App settings screen.
                                Intent intent = new Intent();
                                intent.setAction(
                                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package",
                                        BuildConfig.APPLICATION_ID, null);
                                intent.setData(uri);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        });
            }
        } else if (requestCode == 45) {
            if (grantResults.length <= 0) {
                Log.i(TAG, "User SMS was cancelled.");
            }
        }
    }


    private void startLocationPermissionRequest() {
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.SEND_SMS},
                REQUEST_PERMISSIONS_REQUEST_CODE);

    }

    private void showSnackbar(final int mainTextStringId, final int actionStringId,
                              View.OnClickListener listener) {
        Snackbar.make(findViewById(android.R.id.content),
                getString(mainTextStringId),
                Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(actionStringId), listener).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("onActivityResult", resultCode + "");
        // check whether the result is ok
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RESULT_PICK_CONTACT:
                    contactPicked(data);
                    break;
            }
        } else {
            Log.e("MainActivity", "Failed to pick contact");
        }
    }

    /**
     * Query the Uri and read contact details. Handle the picked contact data.
     *
     * @param data
     */
    private void contactPicked(Intent data) {
        Log.i("contactPicked", data.getDataString());
        Cursor cursor = null;
        try {
            String phoneNo = null;
            String name = null;
            long photoUri;
            Uri uri = data.getData();
            cursor = getContentResolver().query(uri, null, null, null, null);
            cursor.moveToFirst();
            int phoneIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            int nameIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
            int photo = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI);
            phoneNo = cursor.getString(phoneIndex);
            name = cursor.getString(nameIndex);
            photoUri = cursor.getInt(photo);

            saveContactToDB(phoneNo, name, photoUri);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveContactToDB(String phoneNo, String name, Long photo) {
        boolean contactExistsInDb = false;
        List<Contact> contacts = contactDB.getAllContacts();
        for (Contact cn : contacts) {
            if (cn.getName().equals(name.trim())) {
                contactExistsInDb = true;
                break;
            }
        }

        if (!contactExistsInDb) {
            contactDB.addContact(new Contact(name, phoneNo));
            updateContactList();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        menu.add(10,1,1,"Add Emergency Contacts");

        menu.add(10,2,2,"Logout");
        return super.onCreateOptionsMenu(menu);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 2) {

            AlertDialog.Builder mybuilder=new AlertDialog.Builder(MainActivity.this);
            mybuilder.setMessage("Do You Really Want To Logout?");
            mybuilder.setCancelable(false);
            mybuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent myintent=new Intent(MainActivity.this,loginActivity.class);
                    startActivity(myintent);
                    MainActivity.this.finish();
                }
            });
            mybuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog mydialog=mybuilder.create();
            mydialog.show();
        }
        else if(item.getItemId()==1){
            Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
            intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
            startActivityForResult(intent, RESULT_PICK_CONTACT);
        }


        return super.onOptionsItemSelected(item);
    }

//    @Override
//    public boolean onSupportNavigateUp() {
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
//                || super.onSupportNavigateUp();
//    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            AlertDialog.Builder mybuilder = new AlertDialog.Builder(this);
            mybuilder.setMessage("Do You Really Want To Exit?");
            mybuilder.setCancelable(false);
            mybuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    MainActivity.super.onBackPressed();
                }
            });
            mybuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog mydialog = mybuilder.create();
            mydialog.show();
        }

    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.


        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent myintent=new Intent(MainActivity.this, emerActivity.class);
            startActivity(myintent);

        } else if (id == R.id.nav_gallery) {
            Intent myintent=new Intent(MainActivity.this,MapsActivity.class);
            startActivity(myintent);
        }
            else if (id == R.id.nav_cab) {
                Intent myintent=new Intent(MainActivity.this,EnterMobile.class);
                startActivity(myintent);



        } else if (id == R.id.nav_bug) {

            Intent intent = new Intent("android.intent.action.SENDTO");
            intent.setData(Uri.parse("mailto:"));
            intent.putExtra("android.intent.extra.EMAIL", new String[]{"iamakshitpuri@gmail.com"});
            intent.putExtra("android.intent.extra.SUBJECT", "Women Safety: Bug Report");
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
                startActivity(Intent.createChooser(intent, "Send Email"));
            }
        }

         else if (id == R.id.nav_sug) {
             Intent intent = new Intent("android.intent.action.SENDTO");
            intent.setData(Uri.parse("mailto:"));
            intent.putExtra("android.intent.extra.EMAIL", new String[]{"iamakshitpuri@gmail.com"});
            intent.putExtra("android.intent.extra.SUBJECT", "Women Safety: Suggestions");
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
                startActivity(Intent.createChooser(intent, "Send Email"));

            }

        } else if (id == R.id.nav_share) {

            Intent intent = new Intent();
            intent.setAction("android.intent.action.SEND");
            intent.putExtra("android.intent.extra.TEXT", "Hey! Download Women Safety, an app that helps womens in emergency situations.\nwww.akshitpuri.com");
            intent.setType("text/plain");
            startActivity(intent);
            startActivity(Intent.createChooser(intent, "Share via"));

        }
        else if (id == R.id.nav_tools){
            Uri gmmIntentUri = Uri.parse("geo:0,0?q=police station");
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);
            return true;


        }
        else if (id == R.id.nav_host_fragment){
            Uri gmmIntentUri = Uri.parse("geo:0,0?q=hospital");
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);
            return true;


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    }




