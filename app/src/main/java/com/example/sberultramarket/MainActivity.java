package com.example.sberultramarket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sberultramarket.databinding.ActivityMainBinding;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private final OrderDetails orderDetails = new OrderDetails();
    private JSONArray data;
    private final ArrayList<Pair<CheckBox, TextView>> checkBoxAndTextList = new ArrayList<>();
    private TableLayout groceriesList;

    protected void clearGroceriesList() {
        for (int i = 0; i < groceriesList.getChildCount(); i++) {
            View child = groceriesList.getChildAt(i);
            if (child instanceof TableRow) ((ViewGroup) child).removeAllViews();
        }
    }

    protected void resetCheckboxes() {
        for (Pair<CheckBox, TextView> el : checkBoxAndTextList) {
            if (el.first.isChecked()) el.first.toggle();
        }
    }

    protected void setOrderInfo() {
        for (Pair<CheckBox, TextView> el : checkBoxAndTextList) {
            if (el.first.isChecked()) orderDetails.positions.add(el.second.getText() + "\n");
        }
    }

    protected void renderGroceriesList() {
        if (data != null) {
            TableRow tableRow = new TableRow(this);
            tableRow.setPadding(0, 20, 0, 0);
            Button button = new Button(this);
            button.setText(getResources().getString(R.string.send_order));
            button.setOnClickListener(view -> {
                setOrderInfo();
                Log.d("ORDER_DETAILS_TYPE", "aboba" + orderDetails.type);
                Log.d("ORDER_DETAILS_POSITIONS", "aboba" + orderDetails.positions);
                if (orderDetails.positions.size() > 0 && !Objects.equals(orderDetails.type, "") && orderDetails.type != null) {
                    // TODO: render another activity with orderDetails shown
                    Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
                    Log.d("BEFORE_SWAP_TYPE", "data: " + orderDetails.type);
                    Log.d("BEFORE_SWAP_POSITIONS", "data: " + String.valueOf(orderDetails.positions));
                    intent.putExtra(getResources().getString(R.string.order_type), (CharSequence) orderDetails.type);
                    String positionsString = "";
                    int index = 1;
                    for (String pos : orderDetails.positions) {
                        positionsString += (index++ + ": " + pos + "\n");
                    }
                    intent.putExtra(getResources().getString(R.string.order_positions), positionsString);
                    startActivity(intent);
                }
                else Toast.makeText(MainActivity.this, getResources().getString(R.string.details_not_chosen), Toast.LENGTH_SHORT).show();
                resetCheckboxes();
                orderDetails.type = "";
                orderDetails.positions = new ArrayList<>();
            });
            tableRow.addView(new TextView(this));
            tableRow.addView(button);
            groceriesList.addView(tableRow);
            for( int i = 0; i < data.size(); i++)
            {
                tableRow = new TableRow(this);
                TextView textView = new TextView(this);
                CheckBox checkBox = new CheckBox(this);
                JSONObject json = new JSONObject((Map) data.get(i));
                String productName = Objects.requireNonNull(json.get("name")).toString();
                textView.setText(productName);
                textView.setPadding(5, 5, 5, 5);
                checkBoxAndTextList.add(new Pair<>(checkBox, textView));
                tableRow.addView(checkBox);
                tableRow.addView(textView);
                groceriesList.addView(tableRow);
            }
        }
        else {
            TextView textView = new TextView(this);
            textView.setText(getResources().getString(R.string.fetch_error));
            groceriesList.addView(textView);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.example.sberultramarket.databinding.ActivityMainBinding mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        groceriesList = mainBinding.groceriesList;
        RadioGroup orderTypeRG = mainBinding.orderTypeRg;
        orderDetails.type = "";
        orderTypeRG.setOnCheckedChangeListener((radioGroup, i) -> {
            if (i == 1) {
                orderDetails.type = "Deliver to the house";
            }
            else if (i == 2) {
                orderDetails.type = "Get order from the shop";
            }
        });
        mainBinding.fetchDataButton.setOnClickListener(view -> {
            Toast.makeText(MainActivity.this, getResources().getString(R.string.fetching_process), Toast.LENGTH_SHORT).show();
            try {
                data = AppLogicHandler.getProductsList();
                clearGroceriesList();
                renderGroceriesList();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        View view = mainBinding.getRoot();
        setContentView(view);
    }
}