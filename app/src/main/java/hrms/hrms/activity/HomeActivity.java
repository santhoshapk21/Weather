package hrms.hrms.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.transition.Fade;
import android.view.View;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.snackbar.Snackbar;
import com.hris365.R;
import com.hris365.databinding.ActivityHomeBinding;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.Stack;

import butterknife.ButterKnife;
import hrms.hrms.activity.adapter.NavItemAdapter;
import hrms.hrms.baseclass.BaseAppCompactActivity;
import hrms.hrms.fragment.DashBoaredFragment;
import hrms.hrms.fragment.approval.ApprovalRequestFragment;
import hrms.hrms.fragment.attendance.AttendanceFragment;
import hrms.hrms.fragment.claims.ClaimsFragment;
import hrms.hrms.fragment.empdir.EmployeeDirectoryFragment;
import hrms.hrms.fragment.leave.LeaveFragment;
import hrms.hrms.fragment.notification.NotificationFragment;
import hrms.hrms.fragment.payslip.PaySlipFragment;
import hrms.hrms.fragment.shift_change.ShiftChangeFragment;
import hrms.hrms.model.NavItem;
import hrms.hrms.retrofit.OnApiResponseListner;
import hrms.hrms.retrofit.RequestCode;
import hrms.hrms.transition.DetailsTransition;

public class HomeActivity extends BaseAppCompactActivity implements
        View.OnClickListener, FragmentManager.OnBackStackChangedListener,
        OnApiResponseListner {

    public static Context context;
    public static String serverFormat = "yyyy-MM-dd'T'HH:mm:ss";
    private ActivityHomeBinding mBinding;
    private NavItemAdapter navItemAdapter;
    private ArrayList<NavItem> navItems;
    private Fragment mFragment;
    private FragmentTransaction ft;
    private Stack<String> titleStack;
    View.OnClickListener onNavigationClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                onBackPressed();
            } else {
                if (mBinding.drawerLayout.isDrawerOpen(GravityCompat.START))
                    mBinding.drawerLayout.closeDrawer(GravityCompat.START);
                else
                    mBinding.drawerLayout.openDrawer(GravityCompat.START);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        ButterKnife.bind(this);
        setSupportActionBar(mBinding.toolbar);
        setTitle("");
        init();
        context = this;
        initUniversalImageLoader();
        mBinding.activityHomeLlLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickLogout();
            }
        });
    }

    private void clickLogout() {
        showDialog();
        getApiTask().doLogout(getString(TYPE.ACCESSTOKEN), this);
        putBoolean(TYPE.ISMANAGER, false);
    }

    private void initUniversalImageLoader() {
        try {
            ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(getBaseContext()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init() {

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mBinding.drawerLayout,
                mBinding.toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        mBinding.drawerLayout.setDrawerListener(toggle);

        navItems = new ArrayList<>();
        String[] name = getResources().getStringArray(R.array.menu_list);
        int[] unSelectedMenuIcon = getUnSelectedMenuIcon();
        int[] selectedMenuIcon = getSelectedMenuIcon();
        for (int i = 0; i < name.length; i++) {
            navItems.add(new NavItem(name[i], unSelectedMenuIcon[i], selectedMenuIcon[i]));
        }
        navItemAdapter = new NavItemAdapter(this, navItems, this);
        mBinding.rvMenuItem.setLayoutManager(new LinearLayoutManager(this));
        mBinding.rvMenuItem.setAdapter(navItemAdapter);
        toggle.syncState();
        mBinding.toolbar.setNavigationIcon(R.drawable.ic_menu_drawer);
        titleStack = new Stack<>();
        manageNavigation(0);
        navItemAdapter.setSelector(0);
        getSupportFragmentManager().addOnBackStackChangedListener(this);
        mBinding.toolbar.setNavigationOnClickListener(onNavigationClickListener);
        mBinding.llProfile.setOnClickListener(this);
        mBinding.activityHomeTvTitle.setText(getString(TYPE.COMPANYNAME));
    }

    private void setDataDrawer() {
        mBinding.activityHomeTvName.setText(getString(TYPE.USERFULLNAME));
        mBinding.activityHomeTvDept.setText(getString(TYPE.DEPT));

        if (!getString(TYPE.PROFILEURL).equals(""))
            setImage(
                    this,
                    getString(TYPE.PROFILEURL),
                    mBinding.activityHomeIvImgUser);
        else
            mBinding.activityHomeIvImgUser.setImageResource(R.drawable.ic_no_image);
    }

    @Override
    public void onBackPressed() {

        if (mBinding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            mBinding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            if (titleStack.size() > 1) {
                super.onBackPressed();
                titleStack.pop();
                setTitleText();
                setImageInDashboaredFragment();
            } else {
                titleStack.pop();
                super.onBackPressed();
            }
        }
    }

    @Override
    public void onClick(View view) {

        if (mBinding.llProfile.getId() == view.getId()) {
            mBinding.drawerLayout.closeDrawer(GravityCompat.START);
            startProfileActivity();
        } else {
            manageNavigation((Integer) view.getTag());
            navItemAdapter.setSelector((Integer) view.getTag());
            mBinding.drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    private void startProfileActivity() {
        new CountDownTimer(400, 400) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                startProfileActivityIntent();
            }
        }.start();
    }

    private void startProfileActivityIntent() {
        startActivity(new Intent(this, ProfileActivity.class));
    }

    private void manageNavigation(int pos) {
        String title = "";
        switch (pos) {
            case 0:
                mFragment = new DashBoaredFragment();
                title = getString(TYPE.COMPANYNAME);
                break;

            case 1:
                mFragment = new AttendanceFragment();
                title = getString(R.string.attendance);
                break;

            case 2:
                mFragment = new LeaveFragment();
                title = getString(R.string.leave);
                break;

            case 3:
                mFragment = new ClaimsFragment();
                title = getString(R.string.claims);
                break;

            case 4:
                mFragment = new ShiftChangeFragment();
                title = getString(R.string.shift_change);
                break;

            case 5:
                mFragment = new ApprovalRequestFragment();
                title = getString(R.string.approvals);
                break;
            case 6:
                mFragment = new PaySlipFragment();
                title = getString(R.string.pay_slip);
                break;
            case 7:
                mFragment = new EmployeeDirectoryFragment();
                title = getString(R.string.employee_directory);
                break;

            case 8:
                mFragment = new NotificationFragment();
                title = getString(R.string.inbox);
                break;

        }

        if (mFragment != null && (titleStack.empty() || !titleStack.peek().equals(title))) {
            getSupportFragmentManager().popBackStackImmediate(
                    null,
                    FragmentManager.POP_BACK_STACK_INCLUSIVE
            );
            titleStack.clear();
            titleStack.push(title);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(
                            R.id.fl_container,
                            mFragment,
                            mFragment.getClass().getSimpleName())
                    .commit();
        }


    }

    public DrawerLayout getDrawerLayout() {
        return mBinding.drawerLayout;
    }

    public int[] getUnSelectedMenuIcon() {
        int icon[] = new int[9];
        icon[0] = R.drawable.ic_home;
        icon[1] = R.drawable.ic_attendance;
        icon[2] = R.drawable.ic_leave;
        icon[3] = R.drawable.ic_claims;
        icon[4] = R.drawable.ic_shift_change;
        icon[5] = R.drawable.ic_approvals;
        icon[6] = R.drawable.ic_payslip;
        icon[7] = R.drawable.ic_emp_dir;
        icon[8] = R.drawable.ic_inbox;
        return icon;
    }

    public int[] getSelectedMenuIcon() {
        int icon[] = new int[9];
        icon[0] = R.drawable.ic_home_selected;
        icon[1] = R.drawable.ic_attendance_selected;
        icon[2] = R.drawable.ic_leave_selected;
        icon[3] = R.drawable.ic_claims_selected;
        icon[4] = R.drawable.ic_shift_selected;
        icon[5] = R.drawable.ic_approvals_selected;
        icon[6] = R.drawable.ic_payslip_selected;
        icon[7] = R.drawable.ic_emp_dir_selected;
        icon[8] = R.drawable.ic_inbox_selected;
        return icon;
    }

    @Override
    public void replaceFragment(Fragment mFragment, String title) {
        if (mFragment.isAdded() || titleStack.peek().equals(title))
            return;
        try {
            ft = getSupportFragmentManager().beginTransaction();
            ft.addToBackStack(mFragment.getClass().getSimpleName());
            ft.replace(R.id.fl_container, mFragment, mFragment.getClass().getSimpleName());
            titleStack.push(title);
            ft.commit();

        } catch (Exception e) {
        }
    }

    @Override
    public void addFragment(Fragment mFragment, String title) {

        if (mFragment.isAdded() || titleStack.peek().equals(title))
            return;
        try {
            ft = getSupportFragmentManager().beginTransaction();
            ft.addToBackStack(mFragment.getClass().getSimpleName());
            ft.add(R.id.fl_container, mFragment, mFragment.getClass().getSimpleName());
            titleStack.push(title);
            ft.commitAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
            ft.commit();
        }
    }

    public void replaceSharedFragment(
            Fragment first, Fragment second, String title, String[] transiton, View... view) {

        if (second.isAdded() || titleStack.peek().equals(title))
            return;
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                second.setSharedElementEnterTransition(new DetailsTransition());
                second.setEnterTransition(new Fade());
                first.setExitTransition(new Fade());
                second.setSharedElementReturnTransition(new DetailsTransition());
                ft = getSupportFragmentManager().beginTransaction();

                ft.replace(R.id.fl_container, second, second.getClass().getSimpleName());

                ft.addToBackStack(second.getClass().getSimpleName());
                for (int i = 0; i < view.length; i++) {
                    ft.addSharedElement(view[i], transiton[i]);
                }

                // Apply the transaction
                ft.commit();
                titleStack.push(title);
            } else {
                replaceFragment(second, title);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackStackChanged() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            mBinding.toolbar.setNavigationIcon(R.drawable.ic_back);
            mBinding.activityHomeTvTitle.setText(titleStack.peek().toString());
        } else {
            mBinding.toolbar.setNavigationIcon(R.drawable.ic_menu_drawer);
            mBinding.activityHomeTvTitle.setText(getString(TYPE.COMPANYNAME));
        }
    }

    @Override
    public void onResponseComplete(Object clsGson, int requestCode, int responseCode) {
        dismissDialog();
        if (requestCode == RequestCode.LOGOUT) {
            getPrefrence().edit().clear().commit();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }

    @Override
    public void onResponseError(String errorMessage, int requestCode) {
        dismissDialog();
        if (errorMessage != null)
            showSnackBar(mBinding.getRoot(), errorMessage, Snackbar.LENGTH_SHORT);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setDataDrawer();
        setImageInDashboaredFragment();
    }

    private void setImageInDashboaredFragment() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fl_container);
        if (currentFragment instanceof DashBoaredFragment) {
            ((DashBoaredFragment) currentFragment).setImage();
//            ((DashBoaredFragment) currentFragment).isPresentApi();
        }
    }

    public void setTitleText() {
        if (titleStack != null && titleStack.size() > 0)
            mBinding.activityHomeTvTitle.setText(titleStack.peek());
    }

    public void showLeaveFragment() {
        FragmentManager fm = getSupportFragmentManager();
        int count = fm.getBackStackEntryCount();
        for (int i = 1; i < count; ++i) {
            fm.popBackStackImmediate();
            titleStack.pop();
        }
    }

}
