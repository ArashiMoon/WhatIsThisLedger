package dhu.cst.namelessgroup.chennuo181310630.whatisthisledger;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.util.Calendar;

import dhu.cst.namelessgroup.chennuo181310630.whatisthisledger.db.DBManager;
import dhu.cst.namelessgroup.chennuo181310630.whatisthisledger.utils.FileUtils;

public class SettingActivity extends AppCompatActivity {
    String backupDBPath;
    String currentDBPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        backupDBPath=getExternalFilesDir(null).getPath() + "/ledger.db";
        currentDBPath="/data/data/"+getPackageName()+"/databases/ledger.db";
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.setting_iv_back:
                finish();
                break;
            case R.id.setting_tv_clear:
                showDeleteDialog();
                break;
            case R.id.setting_tv_import:
                showImportDialog();
                break;
            case R.id.setting_tv_export:
                showExportDialog();
                break;
        }
    }

    private void showImportDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示信息")
                .setMessage("请先将数据库文件放在该路径\n"+backupDBPath)
                .setNegativeButton("取消",null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        importDB();
                        DBManager.initDB(getApplicationContext());  //重新载入一次数据库
                    }
                });
        builder.create().show();
    }

    private void showExportDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示信息")
                .setMessage("您确定要导出吗?\n导出的文件路径为\n"+backupDBPath)
                .setNegativeButton("取消",null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        exportDB();
                    }
                });
        builder.create().show();
    }

    //importing database
    private void importDB() {

        try {
            File currentDB=new File(currentDBPath);
            File backupDB=new File(backupDBPath);

            FileUtils.checkFilePath(backupDB, false);
            FileUtils.checkFilePath(currentDB, false);
            if (!backupDB.exists()) {
                Toast.makeText(getBaseContext(), "要导入的文件不存在", Toast.LENGTH_LONG).show();
                return;
            }
            FileUtils.deleteFile(currentDB);
            if(FileUtils.copyFile(backupDB,currentDB)==0) {
                Toast.makeText(getBaseContext(), "导入成功", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(getBaseContext(), "导入失败", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(getBaseContext(), "导入失败", Toast.LENGTH_LONG).show();
        }
    }
    //exporting database
    private void exportDB() {

        try {
            File currentDB=new File(currentDBPath);
            File backupDB=new File(backupDBPath);

            FileUtils.checkFilePath(backupDB, false);
            FileUtils.checkFilePath(currentDB, false);

            if (backupDB.exists()) {
                FileUtils.deleteFile(backupDB);
            }
            if (FileUtils.copyFile(currentDB, backupDB) == 0) {
                Toast.makeText(getBaseContext(), "导出成功", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getBaseContext(), "导出失败", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(getBaseContext(), "导出失败", Toast.LENGTH_LONG).show();
        }
    }
    private void showDeleteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示信息")
                .setMessage("您确定要删除所有记录吗?\n注意，删除后无法恢复，请慎重选择！")
                .setPositiveButton("取消",null)
                .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DBManager.deleteAllAccount();
                        Toast.makeText(SettingActivity.this, "删除成功！", Toast.LENGTH_SHORT).show();
                    }
                });
        builder.create().show();
    }
}