package com.digitcreativestudio.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        displayQuantity(quantity);
        int priceValue;
        CheckBox checkWhippedCream = (CheckBox) findViewById(R.id.whipped_cream_check_box);
        CheckBox checkChocolate = (CheckBox) findViewById(R.id.chocolate_check_box);
        Boolean hasWhippedCream = checkWhippedCream.isChecked();
        Boolean hasChocolate = checkChocolate.isChecked();

        priceValue = calculatePrice(hasWhippedCream, hasChocolate);

        EditText nameEditText = (EditText)findViewById(R.id.name_edit_text);
        Editable name = nameEditText.getText();

        String orderSummary = createOrderSummary(name, priceValue, hasWhippedCream, hasChocolate);
        Log.i("MainActivity.java", orderSummary);
        displayMessage(orderSummary);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // hanya bisa dihandle aplikasi email
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, orderSummary);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    /**
     * This method is used to create the order summary.
     * @return order summary
     */
    public String createOrderSummary(Editable name, int priceOrder, Boolean hasWhippedCream, Boolean hasChocolate){
        String orderSummary = "Name : " + name;
        orderSummary = orderSummary + "\nAdd Whipped Cream? " + hasWhippedCream;
        orderSummary = orderSummary + "\nAdd Chocolate? " + hasChocolate;
        orderSummary = orderSummary + "\nQuantity : " + quantity;
        orderSummary = orderSummary + "\nTotal : $" + priceOrder ;
        orderSummary = orderSummary + "\nThank You!";
        return orderSummary;
    }

    /**
     * Calculates the price of the order based on the current quantity.
     *
     * @return the price
     */
    private int calculatePrice(Boolean hasWhippedCream, Boolean hasChocolate) {
        int price = 0;
        if(hasWhippedCream && hasChocolate){
            price = quantity * (5 + 3);
        }else if(hasWhippedCream){
            price = quantity * (5 + 1);
        }else if(hasChocolate){
            price = quantity * (5 + 2);
        }else{
            price = quantity * 5;
        }
        return price;
    }

    /**
     * This method is called when the increment button is clicked.
     */
    public void incrementOrder(View view) {
        if(quantity>=100){
            Toast.makeText(MainActivity.this,"Sorry! You can't order more than 100 cup of coffees.", Toast.LENGTH_SHORT).show();
        }else{
            quantity = quantity + 1;
        }
        displayQuantity(quantity);
    }

    /**
     * This method is called when the increment button is clicked.
     */
    public void decrementOrder(View view) {
        if(quantity <= 1){
            Toast.makeText(getBaseContext(),"Invalid Order! Please try again.", Toast.LENGTH_SHORT).show();
        }else{
            quantity = quantity - 1;
        }
        displayQuantity(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int numberOfOrder) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfOrder);
    }

    /**
     * This method displays the given price on the screen.
     */
    private void displayMessage(String a) {

        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        //priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
        orderSummaryTextView.setText(a);
    }
}