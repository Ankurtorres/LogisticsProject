package com.logimetrix.nj.logistics_project.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.logimetrix.nj.logistics_project.GpsControl;
import com.logimetrix.nj.logistics_project.R;
import android.support.design.widget.CollapsingToolbarLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.logimetrix.nj.logistics_project.interfacehelper.DrawerLocker;
import com.logimetrix.nj.logistics_project.response.TaskRequestList;
import com.logimetrix.nj.logistics_project.activities.AppConstants;
import com.logimetrix.nj.logistics_project.activities.Dialogs;
import com.logimetrix.nj.logistics_project.network.Connection;
import com.logimetrix.nj.logistics_project.sharedprefrences.SharedPrefrences;
import com.logimetrix.nj.logistics_project.ui.Fragments.FormSubmission;
import com.logimetrix.nj.logistics_project.ui.Fragments.JobDesc;
import com.nightonke.boommenu.BoomButtons.BoomButton;
import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomButtons.SimpleCircleButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.OnBoomListenerAdapter;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;

import newSyncService.AnotherService;
import newSyncService.MainServiceClass;

public class DashBoard extends BaseActivity implements DrawerLocker,View.OnClickListener {
    private CollapsingToolbarLayout collapsingToolbarLayout = null;
    private TaskRequestList taskrequestList;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private FloatingActionButton fab;
    private SharedPrefrences sharedPrefrences;
    private AppBarLayout barLayout;
    private Toolbar toolbar;
    private BoomMenuButton bmb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        barLayout = (AppBarLayout) findViewById(R.id.dashboard_appbar_layout);
        sharedPrefrences = SharedPrefrences.getsharedprefInstance(this);


        fragmentStart(JobDesc.class);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setVisibility(View.VISIBLE);
        setSupportActionBar(toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);
        getSupportActionBar().setTitle(null);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        taskrequestList = new TaskRequestList();
        //  navigationViewInitialise();
        // initDrawer();
        //dynamicToolbarColor();
        toolbarTextAppernce();
        bmb = (BoomMenuButton) findViewById(R.id.bmb);
        assert bmb != null;
        bmb.setButtonEnum(ButtonEnum.Ham);
        bmb.setPiecePlaceEnum(PiecePlaceEnum.HAM_3);
        bmb.setButtonPlaceEnum(ButtonPlaceEnum.HAM_3);
        for (int i = 0; i < bmb.getPiecePlaceEnum().pieceNumber(); i++)
            bmb.addBuilder(BuilderManager.getHamButtonBuilderWithDifferentPieceColor());
       /* bmb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.getId();
            }
        });
       *//* for (int i = 0; i < bmb.getPiecePlaceEnum().pieceNumber(); i++) {
            HamButton.Builder builder = new HamButton.Builder()
                    .listener(new OnBMClickListener() {
                        @Override
                        public void onBoomButtonClick(int index) {
                            // When the boom-button corresponding this builder is clicked.
                            Toast.makeText(DashBoard.this, "Clicked " + index, Toast.LENGTH_SHORT).show();
                        }
                    });
        }*/
        bmb.setOnBoomListener(new OnBoomListenerAdapter() {
            @Override
            public void onClicked(int index, BoomButton boomButton) {
                super.onClicked(index, boomButton);
                changeBoomButton(index);
            }
        });

}
    private void changeBoomButton(int index) {
        // From version 2.0.9, BMB supports a new feature to change contents in boom-button
        // by changing contents in the corresponding builder.
        // Please notice that not every method supports this feature. Only the method whose comment
        // contains the "Synchronicity" tag supports.
        // For more details, check:
        // https://github.com/Nightonke/BoomMenu/wiki/Change-Boom-Buttons-Dynamically
        HamButton.Builder builder = (HamButton.Builder) bmb.getBuilder(index);
        if(index==0){
            Intent home=new Intent(this,DashBoard.class);
            startActivity(home);
        }
        else if(index==1) {
            Intent endJourney = new Intent(this, EndJourney.class);
            startActivity(endJourney);
        }
        else if(index==2)
        {

            Intent logout = new Intent(this, LoginActivity.class);
            startActivity(logout);
            sharedPrefrences.setLoginStatus(false);
            stopService(new Intent(this, MainServiceClass.class));
            stopService(new Intent(this, AnotherService.class));
        }
        /* if (index == 0) {
            builder.normalText("Changed!");
            builder.highlightedText("Highlighted, changed!");
            builder.subNormalText("Sub-text, changed!");
            builder.normalTextColor(Color.YELLOW);
            builder.highlightedTextColorRes(R.color.colorPrimary);
            builder.subNormalTextColor(Color.BLACK);
        } else if (index == 1) {
            builder.normalImageRes(R.drawable.bat);
            builder.highlightedImageRes(R.drawable.bear);
        } else if (index == 2) {
            builder.normalColorRes(R.color.colorAccent);
        } else if (index == 3) {
            builder.pieceColor(Color.WHITE);
        } else if (index == 4) {
            builder.unable(true);
        }*/
    }




    private void navigationViewInitialise() {
        (findViewById(R.id.nav_ll_home)).setOnClickListener(this);setTextFonts(R.id.dash_tv_home, AppConstants.semibold);
        (findViewById(R.id.nav_ll_day_end)).setOnClickListener(this);setTextFonts(R.id.dayend, AppConstants.semibold);
        //(findViewById(R.id.nav_ll_About)).setOnClickListener(this);setTextFonts(R.id.about, AppConstants.semibold);
        (findViewById(R.id.nav_ll_help)).setOnClickListener(this);setTextFonts(R.id.tvHelp, AppConstants.semibold);
        //(findViewById(R.id.nav_ll_lrprint)).setOnClickListener(this);setTextFonts(R.id.print, AppConstants.semibold);
        (findViewById(R.id.nav_ll_logout)).setOnClickListener(this);setTextFonts(R.id.logout, AppConstants.semibold);
    }
    /*
    private void initDrawer() {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setHomeAsUpIndicator(getResources().getDrawable(R.drawable.assigntask));

            toolbar.setTitle("");
            toolbar.setSubtitle("");
            //toolbarTitle=(TextView)findViewById(R.id.map_toolbar_title);
            //changeToolBarTitle("SET PICK UP");
            mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
            mDrawerLayout.setDrawerShadow(R.drawable.rectagle_with_black_outer,
                    GravityCompat.START);
            //mDrawerLayout.setAnimation();
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);

            // if(actionMenu!=null)
//     actionMenu.build().close(false);
            mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                    toolbar, R.string.navigation_drawer_close,
                    R.string.navigation_drawer_close) {
                public void onDrawerClosed(View view) {
                    invalidateOptionsMenu();
                }

                public void onDrawerOpened(View drawerView) {
                    // if(actionMenu!=null)
                    //     actionMenu.build().close(false);
                    invalidateOptionsMenu();
                }
            };
            //change_text("");
            mDrawerLayout.setDrawerListener(mDrawerToggle);

        }


*/

    private void toolbarTextAppernce() {
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.collapsedappbar);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.expandedappbar);
    }
    public void changeHeightLayout(int status){
        try {
            if (status == 0) {
                barLayout.setExpanded(false, true);
                fab.setVisibility(View.GONE);
                toolbar.setVisibility(View.GONE);
                toolbar.setNavigationIcon(null
                );
                /*setBackgroundColor(getResources().getColor(R.color.colorPrimary));*/
                barLayout.setLayoutParams(new CoordinatorLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getResources().getDimensionPixelSize(R.dimen.dp_0)));
            } else if (status == 1) {
                barLayout.setExpanded(true, true);
                toolbar.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                barLayout.setLayoutParams(new CoordinatorLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getResources().getDimensionPixelSize(R.dimen.dp_180)));
            }
        }catch (Exception e){e.printStackTrace();}
    }


    private void fragmentStart(Class fragment) {
        Fragment fragmentClass = null;
        try {
            fragmentClass = (Fragment) fragment.newInstance();

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        getSupportFragmentManager().beginTransaction().add(R.id.contentFrame1, fragmentClass)
                .addToBackStack(null).commit();

    }

    public void setDrawerEnabled(boolean enabled) {
        int lockMode = enabled ? DrawerLayout.LOCK_MODE_UNLOCKED :
                DrawerLayout.LOCK_MODE_LOCKED_CLOSED;
        mDrawerLayout.setDrawerLockMode(lockMode);
        mDrawerToggle.setDrawerIndicatorEnabled(enabled);
    }


    @Override
    public void onClick(View view) {
switch (view.getId())
{
    case R.id.fab:
        if(Connection.isGpsEnable(DashBoard.this)) {
            if (sharedPrefrences.getLati() != null && sharedPrefrences.getLongi() != null) {
                try {
                    String uri = "http://maps.google.com/maps?saddr=" + GpsControl.getLatitude() + "," + GpsControl.getLongitude() + "&daddr=" +sharedPrefrences.getLati() + "," +sharedPrefrences.getLongi();
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                    intent.setPackage("com.google.android.apps.maps");
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(DashBoard.this, "Allocated Latitude and Longitude Not Find.", Toast.LENGTH_LONG).show();
            }
        }
        else{
            Toast.makeText(DashBoard.this, "Hey! Please enable Gps", Toast.LENGTH_LONG).show();
            Intent intentt=new Intent(DashBoard.this,Dialogs.class);
            intentt.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentt);
        }
        break;
}
    }
    @Override
    public void onBackPressed()
    {
        FragmentManager fm = getSupportFragmentManager();//getLastFragmentManagerWithBack(getSupportFragmentManager());
        int fragCount=fm.getBackStackEntryCount();
        if(fragCount==1) {
            finish();
        }
        else
        if(fragCount==2) {
            //fragmentStart(JobDesc.class);
            fm.popBackStack();

            fab.setVisibility(View.VISIBLE);
            toolbar.setVisibility(View.VISIBLE);
            barLayout.setExpanded(true, true);
            toolbar.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            barLayout.setLayoutParams(new CoordinatorLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getResources().getDimensionPixelSize(R.dimen.dp_180)));
          //  setDrawerEnabled(true);
        }
        else
            fm.popBackStack();
        /*if (fm.getBackStackEntryCount() > 1)
        {
            fm.popBackStack();
            return;
        }else
        {
            Toast.makeText(this, "out", Toast.LENGTH_SHORT).show();
        }*/

        //super.onBackPressed();
    }
    private void stopServices()
    {

        if (isSeviceRunning("MainServiceClass"))
            stopService(new Intent(this, MainServiceClass.class));
        if (isSeviceRunning("AnotherService"))
            stopService(new Intent(this, AnotherService.class));
    }
}

