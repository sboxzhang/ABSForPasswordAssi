package com.sbox.absforpwdassi;

import com.sbox.absforpwdassi.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class EditActivity extends Activity {

	private String ID = "";
	private TextView Account;
	private TextView Pwd;
	private TextView Info;
	private DBManager mgr;
	private Intent i;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit);
		Account = (TextView) this.findViewById(R.id.txt_edit_account);
		Pwd = (TextView) this.findViewById(R.id.txt_edit_pwd);
		Info = (TextView) this.findViewById(R.id.txt_edit_info);
		i = getIntent();
		this.ID = i.getStringExtra("id");
		Account.setText(i.getStringExtra("account"));
		Pwd.setText(i.getStringExtra("pwd"));
		Info.setText(i.getStringExtra("info"));
		mgr = new DBManager(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.edit, menu);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_save:
			if ("".equals(Account.getText().toString().trim())) {
				ShowMessage("账号不能为空");
			} else if ("".equals(Pwd.getText().toString().trim())) {
				ShowMessage("密码不能为空");
			} else if ("".equals(Info.getText().toString().trim())) {
				ShowMessage("描述信息不能为空");
			} else {
				mgr.db.execSQL("UPDATE PwdInfo SET ACCOUNT='"
						+ this.Account.getText().toString() + "', Pwd='"
						+ this.Pwd.getText().toString() + "', Info='"
						+ this.Info.getText().toString() + "' WHERE _ID="
						+ this.ID);
				ShowMessage("信息更新成功！");
				i.putExtra("FLAG", "Y");
				setResult(UserHelper.REQUEST_EDIT, i);
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
