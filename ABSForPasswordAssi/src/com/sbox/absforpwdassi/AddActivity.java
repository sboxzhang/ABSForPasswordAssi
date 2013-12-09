package com.sbox.absforpwdassi;

import com.sbox.absforpwdassi.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class AddActivity extends Activity {

	private Intent i;
	private TextView Account;
	private TextView Pwd;
	private TextView Info;
	private DBManager mgr;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add);
		mgr=new DBManager(this);
		i = getIntent();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.add, menu);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_add_save:
			Account = (TextView) this.findViewById(R.id.txt_add_account);
			Pwd = (TextView) this.findViewById(R.id.txt_add_pwd);
			Info = (TextView) this.findViewById(R.id.txt_add_info);
			
			if ("".equals(Account.getText().toString().trim())) {
				ShowMessage("账号不能为空");
			} else if ("".equals(Pwd.getText().toString().trim())) {
				ShowMessage("密码不能为空");
			} else if ("".equals(Info.getText().toString().trim())) {
				ShowMessage("描述信息不能为空");
			} else {
				Log.i("sbox","");
				String sql="INSERT INTO PwdInfo VALUES(null,'"
						+ this.Account.getText().toString() + "','"
						+ this.Pwd.getText().toString() + "','"
						+ this.Info.getText().toString() + "')";
				Log.i("sbox",sql);
				mgr.db.execSQL(sql);
				ShowMessage("信息添加成功！");
				i.putExtra("FLAG", "Y");
				setResult(UserHelper.REQUEST_ADD, i);
				finish();
			}
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	public void ShowMessage(String mess) {
		Toast.makeText(this, mess, Toast.LENGTH_SHORT).show();
	}

	public void ShowMessage(int messId) {
		Toast.makeText(this, getString(messId), Toast.LENGTH_SHORT).show();
	}
}
