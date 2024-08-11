package hrms.hrms.activity;

import android.os.Bundle;

import com.hris365.R;

import hrms.hrms.baseclass.BaseAppCompactActivity;

public class MainActivity extends BaseAppCompactActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home);
    }

}
