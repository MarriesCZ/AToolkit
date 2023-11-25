package com.marries.atoolkit;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.marries.atoolkit.android.debug.ADebugPack;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ADebugPack.addFloatingButtonGroup(this).builder()
                .addButton("1", v -> Toast.makeText(this, "1", Toast.LENGTH_SHORT).show())
                .addButton("2", 0, this)
                .addButton("3", R.layout.test_item_layout, R.id.floating_button, this)
                .addButton("4", 1, R.layout.test_item_layout, R.id.floating_button, this)
                .addButton("Log测试", 0, v -> (new LogTest()).testLog())
                .addButton("ThreadTool测试", v -> (new ThreadPoolTest()).testThreadPool())
                .addButton("KotlinActivity",
                        v -> startActivity(new Intent(this, KotlinActivity.class)))
                .build();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case 0:
                Toast.makeText(this, "2", Toast.LENGTH_SHORT).show();
                break;
            case 1:
                Toast.makeText(this, "4", Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(this, "You forgot, bro!", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}