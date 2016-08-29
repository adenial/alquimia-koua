package com.alquimia.koua;

import android.Manifest;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.alquimia.koua.db.KouaRecord;
import com.alquimia.koua.repository.RecordRepository;
import com.alquimia.koua.service.ServiceCommunicator;
import com.alquimia.koua.utils.ClickListener;
import com.alquimia.koua.utils.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
  final private int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 123;
  private List<Record> recordList = new ArrayList<>();
  private RecyclerView recyclerView;
  private RecordsAdapter recordAdatper;
  private RecordRepository recordRepository = new RecordRepository();
  private SwipeRefreshLayout swipeContainer;

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    // Here, thisActivity is the current activity
    if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED)
    {
      // Should we show an explanation?
      if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECEIVE_SMS))
      {

        // Show an expanation to the user *asynchronously* -- don't block
        // this thread waiting for the user's response! After the user
        // sees the explanation, try again to request the permission.

      } else
      {
        // No explanation needed, we can request the permission.
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS}, MY_PERMISSIONS_REQUEST_READ_CONTACTS);

        // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
        // app-defined int constant. The callback method gets the
        // result of the request.
      }
    }

    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    swipeContainer = (SwipeRefreshLayout)findViewById(R.id.swipeContainer);
    swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
    {
      @Override
      public void onRefresh()
      {
        recordList.clear();
        loadRecordsData();
        swipeContainer.setRefreshing(false);
      }
    });

    Intent intent = new Intent(this, ServiceCommunicator.class);
    startService(intent);

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    //    RecyclerView
    recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
    setupRecyclerView(recyclerView);
//    recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView ,new ClickListener()
//    {
//      @Override
//      public void onClick(View view, int position)
//      {
//        // Record record = recordList.get(position);
//      }
//
//      @Override
//      public void onLongClick(View view, int position)
//      {
//        Record record = recordList.get(position);
//
//        // message for now...
//        Toast.makeText(getApplicationContext(), record.getSmsMessage(), Toast.LENGTH_SHORT).show();
//      }
//    }));
    loadRecordsData();

/*    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View view)
      {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
      }
    });*/

    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    drawer.setDrawerListener(toggle);
    toggle.syncState();

    NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
    navigationView.setNavigationItemSelectedListener(this);
  }

  private void setupRecyclerView(RecyclerView recyclerView)
  {
    recordAdatper = new RecordsAdapter(recordList);
    RecyclerView.LayoutManager recyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());
    recyclerView.setLayoutManager(recyclerViewLayoutManager);
    recyclerView.setItemAnimator(new DefaultItemAnimator());
    recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
    recyclerView.setAdapter(recordAdatper);
  }

  /*Load all the records for now, to do load them with a business logic, probably only the records of the month*/
  private void loadRecordsData()
  {
    List<KouaRecord> messages = recordRepository.getAllRecords(getApplicationContext());

    for(KouaRecord msg : messages)
    {
      Record record = new Record(msg.getOperation(), msg.getAmount(), msg.getOperationType(), msg.getMessage(), msg.getId(), msg.getDate());
      recordList.add(record);
    }

    recordAdatper.notifyDataSetChanged();
  }

  @Override
  public void onBackPressed()
  {
    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    if (drawer.isDrawerOpen(GravityCompat.START))
    {
      drawer.closeDrawer(GravityCompat.START);
    } else
    {
      super.onBackPressed();
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu)
  {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item)
  {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings)
    {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  @SuppressWarnings("StatementWithEmptyBody")
  @Override
  public boolean onNavigationItemSelected(MenuItem item)
  {
    // Handle navigation view item clicks here.
    int id = item.getItemId();

    if (id == R.id.nav_camera)
    {
      // Handle the camera action
    } else if (id == R.id.nav_gallery)
    {

    } else if (id == R.id.nav_slideshow)
    {

    } else if (id == R.id.nav_manage)
    {

    } else if (id == R.id.nav_share)
    {

    } else if (id == R.id.nav_send)
    {

    }

    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    drawer.closeDrawer(GravityCompat.START);
    return true;
  }
}
