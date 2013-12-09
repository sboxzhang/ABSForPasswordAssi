package com.sbox.absforpwdassi;

import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import com.sbox.absforpwdassi.R;

public class MainActivity extends ListActivity
{

    private DBManager mgr;
    private ListView listView;
    private Timer tExit = new Timer();
    private ExitTimerTask exitTimerTask = new ExitTimerTask();
    private View setPwd;
    private View checkPwd;
    private String currentDataID;
    private Dialog dialog;
    private ArrayList<HashMap<String, Object>> listData;
    private SimpleAdapter listItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(android.R.id.list);
        mgr = new DBManager(this);
        // BindListView();
        Login(mgr);
        // 涓篖istView娉ㄥ唽涓婁笅鏂囪彍锟�
        registerForContextMenu(listView);
    }

    @Override
    protected void onRestoreInstanceState(Bundle state)
    {
        Log.i("sbox", "ReStore data:" + state.getString("username"));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        outState.putString("username", "hahahahah");
        Log.i("sbox", "onSaveInstanceState: saved hahahahah");
    }

    public void Login(DBManager mgr)
    {
        if (mgr.CheckIsRegister() == false)
        {
            SetPasswordDialog();
        }
        else
        {
            if (UserHelper.getIsLogin() == false)
            {
                CheckPasswordDialog();
            }
        }
    }

    public void getAllData(String table)
    {
        Cursor c = mgr.db.rawQuery("select * from " + table, null);
        int columnsSize = c.getColumnCount();
        listData = new ArrayList<HashMap<String, Object>>();
        // 鑾峰彇琛ㄧ殑鍐呭
        while (c.moveToNext())
        {
            HashMap<String, Object> map = new HashMap<String, Object>();
            for (int i = 0; i < columnsSize; i++)
            {
                map.put("id", c.getString(0));
                map.put("account", c.getString(1));
                map.put("pwd", c.getString(2));
                map.put("info", c.getString(3));
            }
            listData.add(map);
        }
    }

    public void CheckPasswordDialog()
    {
        LayoutInflater inflater = getLayoutInflater();
        checkPwd = inflater.inflate(R.layout.activity_check_pwd_dialog,
                (ViewGroup) findViewById(R.id.check_pwd_dialog));
        checkPwd.findViewById(R.id.check_btn_ok).setOnClickListener(listener);
        checkPwd.findViewById(R.id.check_btn_cancel).setOnClickListener(
                listener);

        dialog = new Dialog(this, android.R.style.Theme_Holo_Light_Dialog);
        dialog.setContentView(checkPwd);
        dialog.setTitle(getString(R.string.title_check_pwd));
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.action_add:
                Intent intentAdd = new Intent();
                intentAdd.setClass(this, AddActivity.class);
                startActivityForResult(intentAdd, UserHelper.REQUEST_ADD);
                break;
            case R.id.action_modify_pwd:
                ModifyPwdDialog();
                break;
            case R.id.action_exit:
                this.DialogForQuit(getString(R.string.btn_ok),
                        getString(R.string.alert_info_exit),
                        getString(R.string.btn_ok),
                        getString(R.string.btn_cancel));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean ModifyPwdDialog()
    {
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
            ContextMenuInfo menuInfo)
    {
        menu.setHeaderTitle("请选择");
        menu.add(0, 1, Menu.NONE, R.string.action_edit);
        menu.add(0, 2, Menu.NONE, R.string.action_del);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        // 寰楀埌褰撳墠琚拷?涓殑item淇℃伅
        AdapterContextMenuInfo menuInfo = (AdapterContextMenuInfo) item
                .getMenuInfo();
        switch (item.getItemId())
        {
            case UserHelper.MAIN_EDIT:
                Intent editIntent = new Intent();
                editIntent.setClass(this, EditActivity.class);
                editIntent.putExtra("id",
                        (String) listData.get(menuInfo.position).get("id"));
                editIntent
                        .putExtra(
                                "account",
                                (String) listData.get(menuInfo.position).get(
                                        "account"));
                editIntent.putExtra("pwd",
                        (String) listData.get(menuInfo.position).get("pwd"));
                editIntent.putExtra("info",
                        (String) listData.get(menuInfo.position).get("info"));
                this.startActivityForResult(editIntent, UserHelper.REQUEST_EDIT);
                break;
            case UserHelper.MAIN_DEL:
                currentDataID = (String) listData.get(menuInfo.position).get(
                        "id");
                DialogForDelete(getString(R.string.btn_ok),
                        getString(R.string.alert_info_delete),
                        getString(R.string.btn_ok),
                        getString(R.string.btn_cancel));
                break;
            default:
                return super.onContextItemSelected(item);
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        switch (resultCode)
        {
            case UserHelper.REQUEST_EDIT:
                if ("Y".equals(data.getStringExtra("FLAG").toString()))
                {
                    this.BindListView();
                }
                break;
            case UserHelper.REQUEST_ADD:
                if ("Y".equals(data.getStringExtra("FLAG").toString()))
                {
                    this.BindListView();
                }
                break;
        }
    }

    private void DialogForDelete(String title, String message,
            String yesMeaning, String noMeaning)
    {
        AlertDialog.Builder builder = new Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(yesMeaning, new OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                mgr.db.execSQL("DELETE from PWDINFO WHERE _id=" + currentDataID);
                ShowMessage("鏁版嵁宸插垹");
                BindListView();
                dialog.dismiss();
            }
        });

        builder.setNegativeButton(noMeaning, new OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
            }
        });

        builder.create().show();
    }

    public void DialogForQuit(String title, String message, String yesMeaning,
            String noMeaning)
    {
        AlertDialog.Builder builder = new Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(yesMeaning, new OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
                finish();
                System.exit(0);
                // android.os.Process.killProcess(android.os.Process.myPid());
            }
        });
        builder.setNegativeButton(noMeaning, new OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            if (!UserHelper.getIsExit())
            {
                UserHelper.setIsExit(true);
                ShowMessage(R.string.alert_exit);
                // Log.i("debug by small box", getString(R.string.alert_exit));
                if (tExit != null)
                {
                    if (exitTimerTask != null)
                    {
                        // 灏嗗師浠诲姟浠庨槦鍒椾腑绉婚櫎(蹇呴』鐨勶紝鍚﹀垯鎶ラ敊)
                        exitTimerTask.cancel();
                    }
                    // 鏂板缓锟�锟斤拷浠诲姟
                    exitTimerTask = new ExitTimerTask();
                    tExit.schedule(exitTimerTask, 2 * 1000);
                }
            }
            else
            {
                UserHelper.setIsExit(false);
                finish();
                System.exit(0);
            }
        }
        return true;
    }

    private void SetPasswordDialog()
    {
        LayoutInflater inflater = getLayoutInflater();
        setPwd = inflater.inflate(R.layout.activity_set_pwd_dialog,
                (ViewGroup) findViewById(R.id.newpwd_dialog));
        setPwd.findViewById(R.id.btn_ok).setOnClickListener(listener);
        setPwd.findViewById(R.id.btn_cancel).setOnClickListener(listener);

        dialog = new Dialog(this, android.R.style.Theme_Holo_Light_Dialog);
        dialog.setContentView(setPwd);
        dialog.setTitle(getString(R.string.title_new_pwd_dialog));
        dialog.setCancelable(false);
        dialog.show();
    }

    public void BindListView()
    {
        getAllData("pwdinfo");
        listItemAdapter = new SimpleAdapter(
                this,
                listData,// 鏁版嵁锟�
                R.layout.listview_temp,// ListItem鐨刋ML瀹炵幇
                // 鍔拷?鏁扮粍涓嶪mageItem瀵瑰簲鐨勫瓙锟�
                new String[] { "account", "info" },
                // ImageItem鐨刋ML鏂囦欢閲岄潰鐨勪竴涓狪mageView,涓や釜TextView ID
                new int[] { R.id.listview_temp_account, R.id.listview_temp_info });
        listView.setAdapter(listItemAdapter);
    }

    private View.OnClickListener listener = new View.OnClickListener()
    {
        @Override
        public void onClick(View vBtn)
        {
            switch (vBtn.getId())
            {
                case R.id.btn_ok:
                    EditText username = (EditText) setPwd
                            .findViewById(R.id.txt_username);
                    EditText password = (EditText) setPwd
                            .findViewById(R.id.txt_password);
                    EditText password1 = (EditText) setPwd
                            .findViewById(R.id.txt_password_again);

                    if ("".equals(username.getText().toString().trim()))
                    {
                        ShowMessage("");
                        return;
                    }
                    else if ("".equals(password.getText().toString().trim()))
                    {
                        ShowMessage("");
                        return;
                    }
                    else if (!password.getEditableText().toString()
                            .equals(password1.getEditableText().toString()))
                    {
                        ShowMessage("");
                        return;
                    }
                    else
                    {
                        if (mgr.Register(username.getText().toString().trim(),
                                password.getEditableText().toString()))
                        {
                            UserHelper.setIsLogin();
                            UserHelper.setUserName(username.getText()
                                    .toString().trim());
                            BindListView();
                            dialog.dismiss();
                        }
                        else
                        {
                            ShowMessage("");
                        }
                    }
                    break;
                case R.id.btn_cancel:
                    finish();
                    System.exit(0);
                    break;
                case R.id.check_btn_ok:
                    EditText checkun = (EditText) checkPwd
                            .findViewById(R.id.check_txt_username);
                    EditText checkpwd = (EditText) checkPwd
                            .findViewById(R.id.check_txt_password);
                    if (mgr.CheckPassword(checkun.getText().toString(),
                            checkpwd.getText().toString()))
                    {
                        UserHelper.setIsLogin();
                        BindListView();
                        dialog.dismiss();
                    }
                    else
                    {
                        ShowMessage("鐧婚檰澶辫触锛岃纭繚鐢ㄦ埛鍚嶅拰瀵嗙爜杈撳叆姝ｇ‘");
                    }
                    break;
                case R.id.check_btn_cancel:
                    finish();
                    System.exit(0);
                    break;
            }
        }
    };

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id)
    {
        // ShowMessage((String) listData.get(position).get("id") + ":"
        // + (String) listData.get(position).get("pwd"));
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        mgr.closeDB();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
    }

    @Override
    protected void onRestart()
    {
        super.onRestart();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }

    @Override
    protected void onStart()
    {
        super.onStart();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
    }

    public void ShowMessage(String mess)
    {
        Toast.makeText(this, mess, Toast.LENGTH_SHORT).show();
    }

    public void ShowMessage(int messId)
    {
        Toast.makeText(this, getString(messId), Toast.LENGTH_SHORT).show();
    }
}
