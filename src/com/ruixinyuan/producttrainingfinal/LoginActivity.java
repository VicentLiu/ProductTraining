package com.ruixinyuan.producttrainingfinal;

import com.ruixinyuan.producttrainingfinal.bean.UserBean;
import com.ruixinyuan.producttrainingfinal.db.SQLiteUserHelper;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/*
 *@user vicentliu
 *@time 2013-6-13下午2:59:45
 *@package com.ruixinyuan.producttrainingfinal
 */
public class LoginActivity extends Activity 
             implements OnClickListener{

    Button mButtonConfirm;
    Button mButtonConcel;
    EditText mEditTextUserName;
    EditText mEditTextPassword;

    SQLiteUserHelper mSQLiteUserHelper = null;
    SQLiteDatabase mSQLiteDb = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    private void init() {
        mButtonConfirm = (Button)findViewById(R.id.buttonConfirm);
        mButtonConfirm.setOnClickListener(this);
        mButtonConcel = (Button)findViewById(R.id.buttonConcel);
        mButtonConcel.setOnClickListener(this);
        mEditTextUserName = (EditText)findViewById(R.id.editTextUserName);
        mEditTextPassword = (EditText)findViewById(R.id.editTextPassword);
        mSQLiteUserHelper = SQLiteUserHelper.getInstance(LoginActivity.this);
        mSQLiteDb = mSQLiteUserHelper.getReadableDatabase();
    }

    UserBean userInfo = null;
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.buttonConfirm:
            userInfo = mSQLiteUserHelper.queryUserInfo(mEditTextUserName.getText().toString().trim(),
                                            mEditTextPassword.getText().toString().trim());
            if (!userInfo.getUsername().toString().equals("")) {
                Toast.makeText(getBaseContext(),
                               getString(R.string.prompt_user_exists),
                               Toast.LENGTH_SHORT).show();
                Intent i = new Intent(LoginActivity.this, GroupActivity.class);
                startActivity(i);
                finish();
            } else {
                Toast.makeText(getBaseContext(),
                               getString(R.string.prompt_user_not_exists),
                               Toast.LENGTH_SHORT).show();
                mEditTextUserName.setText("");
                mEditTextPassword.setText("");
            }
            break;
        case R.id.buttonConcel:
            finish();
            break;
        default:
            break;
        }
    }

}
