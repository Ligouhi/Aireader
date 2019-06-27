package nuc.edu.aireader2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SocialFragment extends Fragment {
  private TextView sc_fritv;
  private TextView sc_findtv;
    private Fragment mFindFragment;
    private Fragment mFriendFragment;

    public SocialFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_social, container, false);



        return view;
    }

//    //fragment方法
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()){
//            default:
//                break;
//
//            case R.id.sc_findtv:
//                setFragment(0);
//                break;
//            case R.id.sc_fritv:
//                setFragment(1);
//                break;
//        }
//    }
//    private void init(View v){
//        //初始化控件
//        sc_fritv = (TextView)v.findViewById(R.id.sc_fritv);
//        sc_findtv = (TextView)v.findViewById(R.id.sc_findtv);
//
//
//        //设置监听
//        sc_fritv.setOnClickListener(this);
//        sc_findtv.setOnClickListener(this);
//
//    }
//
//    private void setFragment(int index){
//        //获取Fragment管理器
//        FragmentManager mFragmentManager = getChildFragmentManager();
//        //开启事务
//        FragmentTransaction mTransaction = mFragmentManager.beginTransaction();
//        //隐藏所有Fragment
//        hideFragments(mTransaction);
//        switch (index){
//            default:
//                break;
//            case 0:
//                //设置菜单栏为选中状态（修改文字和图片颜色）
//                sc_findtv.setTextColor(getResources()
//                        .getColor(R.color.bule));
//
//                //显示对应Fragment
//                if(mFindFragment == null){
//                    mFindFragment = new SocialFindFragment();
//                    mTransaction.add(R.id.sc_contain, mFindFragment,
//                            "find_fragment");
//                }else {
//                    mTransaction.show(mFindFragment);
//                }
//                break;
//            case 1:
//                sc_fritv.setTextColor(getResources()
//                        .getColor(R.color.bule));
//
//                if(mFriendFragment == null){
//                    mFriendFragment = new SocialFriendFragment();
//                    mTransaction.add(R.id.sc_contain, mFriendFragment,
//                            "friend_fragment");
//                }else {
//                    mTransaction.show(mFriendFragment);
//                }
//                break;
//
//        }
//        //提交事务
//        mTransaction.commit();
//    }
//    private void hideFragments(FragmentTransaction transaction){
//        if(mFindFragment != null){
//            //隐藏Fragment
//            transaction.hide(mFindFragment);
//            //将对应菜单栏设置为默认状态
//            sc_findtv.setTextColor(getResources()
//                    .getColor(R.color.black));
//
//        }
//        if(mFriendFragment != null){
//            transaction.hide(mFriendFragment);
//            sc_fritv.setTextColor(getResources()
//                    .getColor(R.color.black));
//
//        }
//
//    }
}
