package com.fake.examoptionalrewrite

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity5 : AppCompatActivity() {

    // Declare and initialize variables and arrays
    private val dailyExpenses = DoubleArray(7) { 0.0 } // Parallel array for daily expenses
    private val daysOfWeek = arrayOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Apply window insets for edge-to-edge layout
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize the total expenses TextView
        val totalExpensesTextView = findViewById<TextView>(R.id.total_expenses_text)
        updateTotalExpenses(totalExpensesTextView)

        // Set up the button to navigate to the second screen
        val detailsButton = findViewById<Button>(R.id.details_button)
        detailsButton.setOnClickListener {
            navigateToDetailsScreen()
        }

        // Example: Populate the array with dummy data (you can replace this with user input or other data source)
        populateDummyData()
        updateTotalExpenses(totalExpensesTextView)
    }

    // Method to calculate the total expenses
    private fun calculateTotalExpenses(): Double {
        return dailyExpenses.sum()
    }

    // Method to update the total expenses in the UI
    private fun updateTotalExpenses(totalExpensesTextView: TextView) {
        val total = calculateTotalExpenses()
        totalExpensesTextView.text = getString(R.string.total_expenses, total)
    }

    // Method to populate dummy data into dailyExpenses
    private fun populateDummyData() {
        dailyExpenses[0] = 160.0  // Monday
        dailyExpenses[1] = 212.0 // Tuesday
        dailyExpenses[2] = 140.0 // Wednesday
        dailyExpenses[3] = 150.0  // Thursday
        dailyExpenses[4] = 75.0 // Friday
        dailyExpenses[5] = 120.0  // Saturday
        dailyExpenses[6] = 125.0  // Sunday
    }

    // Method to navigate to the second screen
    private fun navigateToDetailsScreen() {
        val intent = Intent(this, DetailsActivity::class.java).apply {
            putExtra("dailyExpenses", dailyExpenses)
            putExtra("daysOfWeek", daysOfWeek)
        }
        startActivity(intent)
    }
}

// Second activity to show detailed daily views
class DetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        // Retrieve the passed data
        val dailyExpenses = intent.getDoubleArrayExtra("dailyExpenses")
        val daysOfWeek = intent.getStringArrayExtra("daysOfWeek")

        // Example: Display data in a TextView (implement your own layout logic as needed)
        val detailsTextView = findViewById<TextView>(R.id.details_text)
        val detailsBuilder = StringBuilder()

        if (dailyExpenses != null && daysOfWeek != null) {
            for (i in dailyExpenses.indices) {
                detailsBuilder.append("${daysOfWeek[i]}: $${dailyExpenses[i]}\n")
            }
        }

        detailsTextView.text = detailsBuilder.toString()
    }
}

