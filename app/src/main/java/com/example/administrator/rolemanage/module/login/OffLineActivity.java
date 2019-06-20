package com.example.administrator.rolemanage.module.login;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.example.administrator.rolemanage.R;
import com.example.administrator.rolemanage.utils.businessutil.IntentUtils;
import com.example.administrator.rolemanage.utils.organized.PerfUtil;


/**
 */

public class OffLineActivity extends Activity implements View.OnClickListener{
    View btn_sure;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.off_line_activity);

        btn_sure = findViewById(R.id.btn_sure);
        btn_sure.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        PerfUtil.setUserInfo(null);
        IntentUtils.entryLogin2(this);
        finish();
    }

    @Override
    public void onBackPressed() {
    }
}
