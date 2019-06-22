package nuc.edu.aireader2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        NavigationView.OnNavigationItemSelectedListener {

    //底部菜单栏3个TextView
    private TextView mTextClothes;
    private TextView mTextFood;
    private TextView mTextHotel;

    //3个Fragment
    private Fragment mClothesFragment;
    private Fragment mFoodFragment;
    private Fragment mHotelFragment;

    private CircleImageView userimg ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View drawerView = navigationView.inflateHeaderView(R.layout.nav_header_main);
         userimg = (CircleImageView) drawerView.findViewById(R.id.userimg);
        userimg.setOnClickListener(this);
        //初始化
        init();
        //设置第一个Fragment默认显示
        setFragment(0);

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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    //fragment方法
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            default:
                break;
            case R.id.userimg:
                Intent intent = new Intent(this,LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.captial:
                setFragment(0);
                break;
            case R.id.book:
                setFragment(1);
                break;
            case R.id.team:
                setFragment(2);
                break;
        }
    }
    private void init(){
        //初始化控件
        mTextClothes = (TextView)findViewById(R.id.captial);
        mTextFood = (TextView)findViewById(R.id.book);
        mTextHotel = (TextView)findViewById(R.id.team);

        //设置监听
        mTextClothes.setOnClickListener(this);
        mTextFood.setOnClickListener(this);
        mTextHotel.setOnClickListener(this);
    }

    private void setFragment(int index){
        //获取Fragment管理器
        FragmentManager mFragmentManager = getSupportFragmentManager();
        //开启事务
        FragmentTransaction mTransaction = mFragmentManager.beginTransaction();
        //隐藏所有Fragment
        hideFragments(mTransaction);
        switch (index){
            default:
                break;
            case 0:
                //设置菜单栏为选中状态（修改文字和图片颜色）
                mTextClothes.setTextColor(getResources()
                        .getColor(R.color.bule));
                mTextClothes.setCompoundDrawablesWithIntrinsicBounds(0,
                        R.mipmap.captial_press,0,0);
                //显示对应Fragment
                if(mClothesFragment == null){
                    mClothesFragment = new CaptialFragment();
                    mTransaction.add(R.id.container, mClothesFragment,
                            "clothes_fragment");
                }else {
                    mTransaction.show(mClothesFragment);
                }
                break;
            case 1:
                mTextFood.setTextColor(getResources()
                        .getColor(R.color.bule));
                mTextFood.setCompoundDrawablesWithIntrinsicBounds(0,
                        R.mipmap.book_press,0,0);
                if(mFoodFragment == null){
                    mFoodFragment = new BookFragment();
                    mTransaction.add(R.id.container, mFoodFragment,
                            "food_fragment");
                }else {
                    mTransaction.show(mFoodFragment);
                }
                break;
            case 2:
                mTextHotel.setTextColor(getResources()
                        .getColor(R.color.bule));
                mTextHotel.setCompoundDrawablesWithIntrinsicBounds(0,
                        R.mipmap.team_press,0,0);
                if(mHotelFragment == null){
                    mHotelFragment = new SocialFragment();
                    mTransaction.add(R.id.container, mHotelFragment,
                            "hotel_fragment");
                }else {
                    mTransaction.show(mHotelFragment);
                }
                break;
        }
        //提交事务
        mTransaction.commit();
    }
    private void hideFragments(FragmentTransaction transaction){
        if(mClothesFragment != null){
            //隐藏Fragment
            transaction.hide(mClothesFragment);
            //将对应菜单栏设置为默认状态
            mTextClothes.setTextColor(getResources()
                    .getColor(R.color.black));
            mTextClothes.setCompoundDrawablesWithIntrinsicBounds(0,
                    R.mipmap.captial,0,0);
        }
        if(mFoodFragment != null){
            transaction.hide(mFoodFragment);
            mTextFood.setTextColor(getResources()
                    .getColor(R.color.black));
            mTextFood.setCompoundDrawablesWithIntrinsicBounds(0,
                    R.mipmap.book,0,0);
        }
        if(mHotelFragment != null){
            transaction.hide(mHotelFragment);
            mTextHotel.setTextColor(getResources()
                    .getColor(R.color.black));
            mTextHotel.setCompoundDrawablesWithIntrinsicBounds(0,
                    R.mipmap.team,0,0);
        }
    }
}
