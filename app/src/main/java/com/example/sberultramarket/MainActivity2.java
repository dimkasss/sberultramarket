package com.example.sberultramarket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sberultramarket.databinding.ActivityMain2Binding;
import com.example.sberultramarket.databinding.ActivityMainBinding;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        ActivityMain2Binding main2Binding = ActivityMain2Binding.inflate(getLayoutInflater());
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
        Button button2 = main2Binding.sendEmail;
        EditText email = main2Binding.email;
        button2.setOnClickListener(view -> {
            Intent intent12 = new Intent(Intent.ACTION_SENDTO);
            intent12.putExtra(Intent.EXTRA_EMAIL, new String[]{String.valueOf(email.getText())});
            intent12.putExtra(Intent.EXTRA_SUBJECT, "Your order in capybara shop");
            intent12.putExtra(Intent.EXTRA_TEXT, "Order type: " + orderType + "\n\nProducts list:\n\n" + orderPositions);
            intent12.setData(Uri.parse("mailto:"));
            startActivity(Intent.createChooser(intent12, "Send email"));
        });
        super.onCreate(savedInstanceState);
        View view = main2Binding.getRoot();
        setContentView(view);
    }
}