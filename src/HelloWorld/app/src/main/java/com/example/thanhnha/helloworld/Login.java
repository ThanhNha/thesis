package com.example.thanhnha.helloworld;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Thanh Nha on 2/27/2015.
 */
public class Login extends Activity {
    private Button dangNhap, hienThi, capNhatTen;
    private TextView danhSachTK;
    private EditText tenTK, matKhau;
    private MyDatabase database = new MyDatabase(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        dangNhap = (Button) findViewById(R.id.button1);
        hienThi = (Button) findViewById(R.id.button2);
        capNhatTen = (Button) findViewById(R.id.button3);
        danhSachTK = (TextView) findViewById(R.id.textView2);
        tenTK = (EditText) findViewById(R.id.editText1);
        matKhau = (EditText) findViewById(R.id.editText2);

        dangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.open();
                Boolean ketQuaDangNhap = database.kiemTraLogin(tenTK.getText().toString(), matKhau.getText().toString());
                database.close();
                if (ketQuaDangNhap) {
                    Toast.makeText(Login.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Login.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });

        hienThi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.open();
                String ds = database.getData();
                database.close();
                danhSachTK.setText(ds);
            }
        });

        capNhatTen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.open();
                Boolean ketQuaDangNhap = database.kiemTraLogin(tenTK.getText().toString(), matKhau.getText().toString());
                database.close();
                if (ketQuaDangNhap) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                    builder.setTitle("Cập nhật tên hiển thị");
                    builder.setMessage("Mời nhập tên cần thay đổi:");
                    final EditText input = new EditText(Login.this);
                    builder.setView(input);
                    builder.setCancelable(false);
                    builder.setPositiveButton("Đồng ý",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    database.open();
                                    Boolean kq = database.setNameHienThi(tenTK.getText().toString(), matKhau.getText().toString(), input.getText().toString());
                                    database.close();
                                    if (kq) {
                                        Toast.makeText(Login.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(Login.this, "Cập nhật tên thất bại", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                    builder.setNegativeButton("Hủy",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });
    }

    /*public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater blowUp = getMenuInflater();
        blowUp.inflate(R.menu.menu_main, menu);
        menu.findItem(R.id.i1).setTitle("Trang Chính");
        menu.findItem(R.id.i2).setTitle("Thoát");
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.i1:
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.i2:
                finish();
                break;

        }
        return false;
    }*/
}

