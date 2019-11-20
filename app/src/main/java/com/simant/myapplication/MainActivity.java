package com.simant.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    Spinner spinnerLocation, spinnerRoomType;
    TextView textViewLocation, textViewRoomType, textViewCheckin, textViewCheckout, textViewAdult, textViewChild, textViewRoom, textViewServiceTax, textViewVat;
    EditText editTextCheckInDate, editTextCheckOutText, editTextAdult, editTextChild, editTextRoom;
    Button buttonSave;

    String strAdult, strChild, strRoomNo;
    Integer totalAmount;
    Integer vatAmount, serviceAmount;

    private String[] location= {"Balkot", "Baneshwor", "Kalanki", "Koteswor", "Kalimati", "Tikathali"};
    private String[] roomType = {"Classic - 1000", "Delux - 1500", "AC - 2500"};

    // Check in Date picker code
    Button CheckinDateBtn;
    EditText etCheckindate;
    DatePickerDialog datePicker;
    int year;
    int month;
    int dayOfMonth;
    Calendar calendar;

    // Check out Date picker code
    Button CheckoutDateBtn;
    EditText etCheckoutdate;

    // check-in , check-out date
    String strCheckIn, strCheckOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initilization
        spinnerLocation = findViewById(R.id.spLocation);
        spinnerRoomType = findViewById(R.id.spRoomType);

        textViewLocation = findViewById(R.id.txtLocation);
        textViewRoomType = findViewById(R.id.txtRoomType);

        textViewCheckin = findViewById(R.id.txtCheckIn);
        textViewCheckout = findViewById(R.id.txtCheckOut);

        editTextAdult = findViewById(R.id.etAdult);
        editTextChild = findViewById(R.id.etChildren);
        editTextRoom = findViewById(R.id.etRoom);

        textViewAdult = findViewById(R.id.txtAdult);
        textViewChild = findViewById(R.id.txtChild);
        textViewRoom = findViewById(R.id.txtRoom);

        textViewServiceTax = findViewById(R.id.txtServiceTax);
        textViewVat = findViewById(R.id.txtVat);

        buttonSave = findViewById(R.id.btnSave);

        // parse value in location spinner from array
        ArrayAdapter<String> arrayAdapterLocation = new ArrayAdapter<>(
            this,
                android.R.layout.simple_list_item_1,
                location
        );
        spinnerLocation.setAdapter(arrayAdapterLocation);

        // parse value in RoomType spinner from array
        ArrayAdapter<String> arrayAdapterRoomType = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                roomType
        );
        spinnerRoomType.setAdapter(arrayAdapterRoomType);

        // check in date picker code
        CheckinDateBtn = findViewById(R.id.btnDate);
        etCheckindate = findViewById(R.id.tvSelectedDate);

        CheckinDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                datePicker = new DatePickerDialog(MainActivity.this,new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(android.widget.DatePicker datePicker, int year, int month, int day) {
//                        etCheckindate.setText(year + "/" + (month + 1) + "/" + day);
                        etCheckindate.setText(year +" "+ (month + 1) +" "+ day);
                        strCheckIn = etCheckindate.getText().toString();
                    }
                }, year, month, dayOfMonth);
                datePicker.show();
            }
        });

        // check out date picker code
        CheckoutDateBtn = findViewById(R.id.btnDateCheckOut);
        etCheckoutdate = findViewById(R.id.tvSelectedDateCheckOut);

        CheckoutDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                datePicker = new DatePickerDialog(MainActivity.this,new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(android.widget.DatePicker datePicker, int year, int month, int day) {
//                        etCheckoutdate.setText(year + "/" + (month + 1) + "/" + day);
                        etCheckoutdate.setText(year  +" "+ (month + 1) +" "+ day);
                        strCheckOut = etCheckoutdate.getText().toString();
                    }
                }, year, month, dayOfMonth);
                datePicker.show();
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textViewLocation.setText("The location is " + spinnerLocation.getSelectedItem().toString());
                textViewRoomType.setText("The room type is " + spinnerRoomType.getSelectedItem().toString());
                if (spinnerRoomType.getSelectedItem().toString() == "Classic - 1000") {
                    totalAmount = 1000;
                } else if (spinnerRoomType.getSelectedItem().toString() == "Delux - 1500"){
                    totalAmount = 1500;
                } else if (spinnerRoomType.getSelectedItem().toString() == "AC - 2500") {
                    totalAmount = 2500;
                } else {

                }
                textViewCheckin.setText("Check-in Date: " + strCheckIn);
                textViewCheckout.setText("Check-out Date: " + strCheckOut);
                strAdult = editTextAdult.getText().toString();
                strChild = editTextChild.getText().toString();
                strRoomNo = editTextRoom.getText().toString();
                textViewAdult.setText("Number of adult: " + strAdult);
                textViewChild.setText("Number of child: " + strChild);
                textViewRoom.setText("Number of room: " + strRoomNo);

                vatAmount = totalAmount + ((13 * totalAmount)/100);
                serviceAmount = vatAmount + ((10 * vatAmount)/100);

                textViewServiceTax.setText("Service tax is " + serviceAmount.toString());
                textViewVat.setText("Vat tax is " + vatAmount.toString());

                // Date diff
                SimpleDateFormat myFormat = new SimpleDateFormat("yyyy MM dd");
                String dateBeforeString = textViewCheckin.getText().toString();
                String dateAfterString = textViewCheckout.getText().toString();

                try {
                    Date dateBefore = myFormat.parse(dateBeforeString);
                    Date dateAfter = myFormat.parse(dateAfterString);

                    long difference = dateAfter.getTime() - dateBefore.getTime();
                    float daysBetween = (difference / (1000*60*60*24));
                    System.out.println("Difference: "+daysBetween);

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });

    }
}
