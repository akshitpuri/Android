package com.practice.bunktracker;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.util.TypedValue;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ResourceCursorAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.practice.bunktracker.R;

import java.util.ArrayList;
import java.util.Map;



    public class menuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    static Mydatabasehelper mydb;

        static int edit = 2;
        String bunkedClass;
        Cursor cr = null ;
        int flag = 0;
        String s;
        TextView id;
        int idInInt;
        String idInString;
        ListView list;
        CursorAdapter myadapter;
        String minPer;
        String percent;
        String subjectName;
        TextView totalBunks;
        String totalClass;
        TextView totalPer;

        char firstLetter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        mydb = new Mydatabasehelper(getApplicationContext());
        this.totalBunks = (TextView) findViewById(R.id.bbox);
        this.totalPer = (TextView) findViewById(R.id.pbox);
        this.list = (ListView) findViewById(R.id.menu);
        this.myadapter = new cursoradapter(this, R.layout.list, this.cr, 0);
        this.list.setAdapter(this.myadapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(menuActivity.this, subdetail.class);
                menuActivity.this.id = (TextView) view.findViewById(R.id._id);
                menuActivity.this.idInString = menuActivity.this.id.getText().toString();
                menuActivity.this.idInInt = Integer.parseInt(menuActivity.this.idInString);
                Log.i("bunktracker", "id of selected subject" + menuActivity.this.idInInt);
                Cursor cr = menuActivity.mydb.getWritableDatabase().query("SubjectData", Mydatabasehelper.columns, "_id=?", new String[]{menuActivity.this.idInString}, null, null, null);
                Log.i("bunktracker", "number of rows picked = " + cr.getCount());
                if (cr != null) {
                    cr.moveToFirst();
                    Log.i("bunktracker", "id picked from database" + cr.getInt(cr.getColumnIndex("_id")));
                    menuActivity.this.subjectName = cr.getString(cr.getColumnIndex("name"));
                    menuActivity.this.minPer = cr.getString(cr.getColumnIndex("minPercent"));
                    menuActivity.this.bunkedClass = cr.getString(cr.getColumnIndex("bunkedClasses"));
                    menuActivity.this.totalClass = cr.getString(cr.getColumnIndex("totalClasses"));
                    menuActivity.this.percent = cr.getString(cr.getColumnIndex("percent"));
                    intent.putExtra("subject name", menuActivity.this.subjectName);
                    intent.putExtra("minimum percentage", menuActivity.this.minPer);
                    intent.putExtra("bunked classes", menuActivity.this.bunkedClass);
                    intent.putExtra("total classes", menuActivity.this.totalClass);
                    intent.putExtra("attendance percentage", menuActivity.this.percent);
                    intent.putExtra("id", menuActivity.this.idInInt);
                    menuActivity.this.startActivityForResult(intent, 1);


                }
            }});

                registerForContextMenu(this.list);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Intent myintent=new Intent(menuActivity.this,addsubjects.class);
                    startActivity(myintent);

                try {
                    this.finalize();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            AlertDialog.Builder mybuilder=new AlertDialog.Builder(menuActivity.this);
            mybuilder.setMessage("Do You Really Want To Exit");
            mybuilder.setCancelable(false);
            mybuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    menuActivity.super.onBackPressed();
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
    }




    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.


        int id = item.getItemId();

        if (id == R.id.nav_friend) {
            Intent myintent=new Intent(menuActivity.this,helpafriendActivity.class);
            startActivity(myintent);
        } else if (id == R.id.nav_resetdata) {

            addsubjects.clearAll();
            this.totalBunks.setText("0");
            this.totalPer.setText("0.0 %");
            this.myadapter.swapCursor(null);
            Toast.makeText(this, "Click On (+) icon to add subjects.", Toast.LENGTH_LONG).show();


        } else if (id == R.id.nav_help) {
            Intent myintent=new Intent(menuActivity.this,aboutActivity.class);
            startActivity(myintent);

        } else if (id == R.id.nav_manage) {

            Intent intent=new Intent("android.intent.action.SENDTO");
            intent.setData(Uri.parse("mailto:"));
            intent.putExtra("android.intent.extra.EMAIL", new String[]{"iamakshitpuri@gmail.com"});
            intent.putExtra("android.intent.extra.SUBJECT", "Bunk Tracker: Bug Report");
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
                startActivity(Intent.createChooser(intent, "Send Email"));
            }

        } else if (id == R.id.nav_share) {


                Intent intent = new Intent("android.intent.action.SENDTO");
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra("android.intent.extra.EMAIL", new String[]{"iamakshitpuri@gmail.com"});
                intent.putExtra("android.intent.extra.SUBJECT", "Bunk Tracker: Suggestions");
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                    startActivity(Intent.createChooser(intent, "Send Email"));

            }

        } else if (id == R.id.nav_send) {

            Intent intent = new Intent();
            intent.setAction("android.intent.action.SEND");
            intent.putExtra("android.intent.extra.TEXT", "Hey! Download Bunk Tracker, an app that helps you maintain your attendance as well as bunk classes.\nwww.akshitpuri.com");
            intent.setType("text/plain");
            startActivity(intent);
            startActivity(Intent.createChooser(intent, "Share via"));

        }
        else if (id == R.id.nav_add){
            Intent myintent=new Intent(menuActivity.this,addsubjects.class);
            startActivity(myintent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            super.onCreateContextMenu(menu, v, menuInfo);
            getMenuInflater().inflate(R.menu.context_menu, menu);

        }

        public boolean onContextItemSelected(MenuItem item) {
            int i = (int) ((AdapterView.AdapterContextMenuInfo) item.getMenuInfo()).id;
            switch (item.getItemId()) {
                case R.id.deleteSubject:
                    int row = mydb.getWritableDatabase().delete("SubjectData", "_id=?", new String[]{String.valueOf(i)});
                    this.cr = addsubjects.readSubjectsNameAndPercentage();
                    this.myadapter.swapCursor(this.cr);
                    calculateTotal();
                    return true;
                default:
                    return false;
            }
        }

        void calculateTotal() {
            double totalPercent = 0.0d;
            int totalBunkedClasses = 0;

            Cursor read = mydb.getWritableDatabase().query("SubjectData", Mydatabasehelper.columnPercentageAndBunked, null, new String[0], null, null, null);
            if (read != null) {
                int i;
                read.moveToFirst();
                for (i = read.getCount(); i > 0; i--) {
                    totalBunkedClasses += Integer.parseInt(read.getString(read.getColumnIndex("bunkedClasses")));
                    read.moveToNext();
                }
                this.totalBunks.setText(totalBunkedClasses + "");
                read.moveToFirst();
                i = read.getCount();
                while (i > 0) {
                    totalPercent += Double.parseDouble(read.getString(read.getColumnIndex("percent")));
                    i--;
                    read.moveToNext();
                }
                this.totalPer.setText((((double) Math.round(100.0d * (totalPercent / ((double) read.getCount())))) / 100.0d) + " %");


            }
        }

        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (resultCode == -1 && requestCode == 0) {
                this.cr = addsubjects.readSubjectsNameAndPercentage();
                this.myadapter.swapCursor(this.cr);
            }
            if (resultCode == -1 && requestCode == edit) {
                this.cr = addsubjects.readSubjectsNameAndPercentage();
                this.myadapter.swapCursor(this.cr);
            }
            if (resultCode == -1 && requestCode == 1) {
                this.cr = addsubjects.readSubjectsNameAndPercentage();
                this.myadapter.swapCursor(this.cr);
            }
        }




        protected void onResume() {
            super.onResume();
            this.cr = addsubjects.readSubjectsNameAndPercentage();
            this.myadapter.swapCursor(this.cr);
            calculateTotal();
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {

           menu.add(10,2,2,"Exit");
        return super.onCreateOptionsMenu(menu);

        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 2) {

               AlertDialog.Builder mybuilder=new AlertDialog.Builder(menuActivity.this);
               mybuilder.setMessage("Do You Really Want To Exit");
               mybuilder.setCancelable(false);
               mybuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       menuActivity.super.onBackPressed();
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


            return super.onOptionsItemSelected(item);
        }
    }


