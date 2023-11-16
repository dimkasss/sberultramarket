package com.example.sberultramarket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.sberultramarket.databinding.ActivityMain2Binding;
import com.example.sberultramarket.databinding.ActivityMainBinding;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        com.example.sberultramarket.databinding.ActivityMain2Binding main2Binding = ActivityMain2Binding.inflate(getLayoutInflater());
        TextView orderTypeView = main2Binding.orderType;
        TextView orderPositionsView = main2Binding.orderPositions;
        String orderType = intent.getStringExtra(getResources().getString(R.string.order_type));
        String orderPositions = intent.getStringExtra(getResources().getString(R.string.order_positions));
        orderTypeView.setText(orderType);
        orderPositionsView.setMovementMethod(new ScrollingMovementMethod());
        orderPositionsView.setText(orderPositions);
        Button button = main2Binding.backToMain;
        button.setOnClickListener(view -> {
            Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent1);
        });
        super.onCreate(savedInstanceState);
        View view = main2Binding.getRoot();
        setContentView(view);
    }
}