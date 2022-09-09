package com.example.dobcalc

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
// we just creating a val name tvSelectedDate of type nullable whose current value is null but when we want to change its value we can change it.
//we are making it private so that we can only access this through main activity and if access this other then main activity then out app will crash

   private var tvSelectedDate : TextView? = null
    private var tvAgeInMinutes : TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnDatePicker : Button = findViewById(R.id.btnDatePicker)

        tvSelectedDate = findViewById(R.id.tvSelectedDate)
        tvAgeInMinutes = findViewById(R.id.tvAgeInMinutes)

        btnDatePicker.setOnClickListener {
            clickDatePicker()

        }

    }

// Here we are just accessing Toast from clickDatePicker function and we call this function inside btnDatPicker.setOnClickListener.
// Inside this function we creating a DatePickeDialog() Class in which we Have three constructor option , but we choose third
// one because when we choose date then we also have to choose month and year.where DatePickerDialog.OnDateSetListener? that means it can be null
// or cna not be null

    // making private so that it will be accessed only from this class only

    private fun clickDatePicker()
    {
        // Calendar.getInstance() gives us the access to thing like current date ,year ,month
        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)

        val dpd = // we can remove DatePickerDialog.OnDateSetListener this if we want.
// here we are adding 1 on month because due to programming language month start from 0 and from 1.

            DatePickerDialog(this ,
                DatePickerDialog.OnDateSetListener{view , selectedYear, selectedMonth,selectedDayOfMonth ->
                    Toast.makeText(this ,
                         "YEAR was $selectedYear, Month was ${selectedMonth+1} , " +
                                "Day of month was $selectedDayOfMonth",
                        Toast.LENGTH_LONG).show()

                    val selectedDate = "$selectedDayOfMonth/${selectedMonth+1}/$selectedYear"

// alternative way to set text is tvSelectedDate?.text = selectedDate
// tvSelectedDate?.setText(selectedDate)

                    tvSelectedDate?.text = selectedDate

// In this we we imported SimpleDateFormat in java form

                    val sdf = SimpleDateFormat("dd/MM/yyyy" , Locale.ENGLISH)

                    val theDate = sdf.parse(selectedDate)

                    //    NOTE: if selected date is earlier than 1/1/1970, then resulting number is negative
// .let is just here to check if theDate is not empty then it will execute and if it is empty then it will not going to run

                    theDate?.let{

                        // getTime() is same as time

                        val selectedDateInMinutes = theDate.time / 60000

//	currentTimeMillis()
//Returns the current time in milliseconds,and this count start from 1 jan 1970.

                        val currentDate =  sdf.parse(sdf.format(System.currentTimeMillis()))
                        currentDate?.let{


                            val currentDateInMinutes = currentDate.time / 60000
//differenceInMinutes are in long type we have to made it string type by .toString() and we added ? for type safety

                            val differenceInMinutes = currentDateInMinutes - selectedDateInMinutes

                            tvAgeInMinutes?.text = differenceInMinutes.toString()

                        }


                    }

                },
                year,
                month,
                day

            )
        //3.6millipn milisecond in 1 hour and multiply 24 we get 86400000
        dpd.datePicker.maxDate = System.currentTimeMillis() - 86400000
        dpd.show()


    }
}