package com.sbox.absforpwdassi;

import com.sbox.absforpwdassi.R;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class ModifyPwdActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_modify_pwd);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.modify_pwd, menu);
		return true;
	}

}
