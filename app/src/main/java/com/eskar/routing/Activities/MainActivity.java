package com.eskar.routing.Activities;

import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.eskar.routing.Fragments.HomeFragment;
import com.eskar.routing.Fragments.RouteFragment;
import com.eskar.routing.Fragments.UpdateProfileFragment;
import com.eskar.routing.R;
import com.eskar.routing.Utils.ImageConverter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    Toolbar toolbar;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mColors;
    private ArrayList<ItemsModel> itemModel;
    private TypedArray itemImages;
    private DrawerAdapter drawerAdapter;
    private int lastIndex=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        mTitle = mDrawerTitle = getTitle();
        //mColors = getResources().getStringArray(R.array.colors_array);
        mDrawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        mDrawerList=(ListView)findViewById(R.id.left_drawer);
        itemModel=new ArrayList<ItemsModel>();
        itemImages=getResources().obtainTypedArray(R.array.color_picture);

        View header=getLayoutInflater().inflate(R.layout.header, null);


        //Rounded corner on Imegeview of drawer header
        ImageView circularImageView = (ImageView)header.findViewById(R.id.imageView);
        circularImageView.setImageBitmap(ImageConverter.roundCornerImage(BitmapFactory.decodeResource(getResources(), R.drawable.user),60));

        mDrawerList.addHeaderView(header);
        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        //noinspection ConstantConditions
        //getSupportActionBar().setIcon(R.drawable.icon);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        itemModel.add(new ItemsModel("Home",R.drawable.home));
        itemModel.add(new ItemsModel("Update Profile",R.drawable.update_profile));
        itemModel.add(new ItemsModel("Blog",R.drawable.blogger));
        itemModel.add(new ItemsModel("Route View",R.drawable.routes));
        itemModel.add(new ItemsModel("Gallery",R.drawable.gallery));
        itemModel.add(new ItemsModel("Events",R.drawable.event));
        itemModel.add(new ItemsModel("Members",R.drawable.member));
        itemModel.add(new ItemsModel("Notification Settings",R.drawable.notification));
        itemModel.add(new ItemsModel("Account Settings",R.drawable.account_settings));
        itemModel.add(new ItemsModel("Logout",R.drawable.logout));
        itemImages.recycle();
        drawerAdapter=new DrawerAdapter(itemModel);
        mDrawerList.setAdapter(drawerAdapter);
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        if (savedInstanceState == null) {
            selectItem(1);
            lastIndex=1;
        }


        // setting actionbar toggle button to open and close drawer
        mDrawerToggle = new ActionBarDrawerToggle(MainActivity.this, mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getSupportActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
//                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        // setting drawer listener
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }


    public class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {

            Fragment newFragment;
            android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            switch(position) {
                case 1:
                    newFragment = new HomeFragment();
                    transaction.replace(R.id.content_frame, newFragment);
                    transaction.addToBackStack(null);
                    setTitle("Home");
                    transaction.commit();
                    break;

                case 2:
                    newFragment = new UpdateProfileFragment();
                    transaction.replace(R.id.content_frame, newFragment);
                    transaction.addToBackStack(null);
                    setTitle("Update Profile");
                    transaction.commit();
                    break;

                case 3:
                    newFragment = new RouteFragment();
                    transaction.replace(R.id.content_frame, newFragment);
                    transaction.addToBackStack(null);
                    setTitle("Route Profile");
                    transaction.commit();
                    break;

                default:
                    newFragment = new HomeFragment();
                    transaction.replace(R.id.content_frame, newFragment);
                    transaction.addToBackStack(null);
                    setTitle("Home");
                    transaction.commit();
                    break;
            }



            if(position != 0){
                lastIndex=position;
                selectItem(position);
            }else{
                mDrawerList.setItemChecked(lastIndex, true);
            }
        }

    }
    private void selectItem(int position){
        Log.d("POS", position+"");
        Fragment newFragment;
        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        switch (position) {
        }

        //setTitle(mColors[position-1]);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }
    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    private class DrawerAdapter extends BaseAdapter {

        ArrayList<ItemsModel> itemsmodel;
        public DrawerAdapter(ArrayList<ItemsModel> itemModel) {
            // TODO Auto-generated constructor stub
            this.itemsmodel=itemModel;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return itemsmodel.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return itemsmodel.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            // TODO Auto-generated method stub

            if (view == null) {
                LayoutInflater mInflater = (LayoutInflater)getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                view = mInflater.inflate(R.layout.list_adapter, null);
            }
            ImageView img=(ImageView)view.findViewById(R.id.img);
            TextView name=(TextView)view.findViewById(R.id.name);
            name.setText(itemsmodel.get(position).getItemName());
            img.setImageResource(itemsmodel.get(position).getItemImage());
            return view;
        }

    }

    private class ItemsModel{
        private String itemName,subtitle;
        private int itemImage;

        public ItemsModel(String name, int image) {
            this.itemName=name;
            this.itemImage=image;
        }

        public ItemsModel(String itemName, String subtitle, int itemImage) {
            this.itemName = itemName;
            this.subtitle = subtitle;
            this.itemImage = itemImage;
        }

        public String getSubtitle() {
            return subtitle;
        }

        public void setSubtitle(String subtitle) {
            this.subtitle = subtitle;
        }

        public String getItemName() {
            return itemName;
        }
        public void setItemName(String itemName) {
            this.itemName = itemName;
        }
        public int getItemImage() {
            return itemImage;
        }
        public void setItemImage(int itemImage) {
            this.itemImage = itemImage;
        }

    }
}