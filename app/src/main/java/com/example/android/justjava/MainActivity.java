package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if (quantity==100) {
            //Show an error message as a toast
            Toast.makeText(this, "You cannot have more than 100 coffees", Toast.LENGTH_SHORT).show();
            //Exit this method early because there is nothing to do
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if (quantity ==1) {
            //Show an error message as a toast
            Toast.makeText(this, "You cannot have less than 1 coffee", Toast.LENGTH_SHORT).show();
            //Exit this method early because there is nothing to do
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        //Find the user's name
        EditText namefield = (EditText)findViewById(R.id.name_field);
        String name = namefield.getText().toString();

        //Figure out if the user wants whipped cream topping
        CheckBox whippedCreamCheckbox = (CheckBox) findViewById(R.id.Whipped_cream_checkbox);
        boolean hasWhippedcream = whippedCreamCheckbox.isChecked();

        //Figure out if the user wants chocolate topping
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        int price = calculatePrice(hasWhippedcream,hasChocolate);
        String priceMessage = createorderSummary(name, price, hasWhippedcream, hasChocolate );

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    /**
     * Calculates the price of the order.
     *
     * @param addWhippedCream is whether or not the user wants whipped cream topping
     * @param addChocolate is whether or not the user wants chocolate topping
     * @return total price
     */

    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        //Price of 1 cup of coffee
        int basePrice = 5;

        //Add $1 if the user wants whipped cream
        if (addWhippedCream) {
            basePrice = basePrice +1;
        }

        //add 2$ if the user wants chocolate
        if (addChocolate) {
            basePrice = basePrice +2;
        }

        //Calculate the total order price by multiplying by quantity
        return quantity * basePrice;

    }
    /**
     * Create summary of the order
     *
     * @param name of the customer
     * @param price of the order
     * @param  addWhippedCream is whether or not the user wants whipped cream topping
     * @param  addChocolate is whether or not the user wants chocolate topping
     * @return text summary
     */

    private String createorderSummary( String name, int price, boolean addWhippedCream, boolean addChocolate) {
        String priceMessage = "Name: " + name;
        priceMessage += "\nAdd Whipped Cream? " + addWhippedCream;
        priceMessage += "\nAdd chocolate? " + addChocolate;
        priceMessage += "\nQuantity: " + quantity;
        priceMessage += "\nTotal: $" + price;
        priceMessage += "\nThank you!";
        return priceMessage;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int numberofcoffees) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + numberofcoffees);
    }

}