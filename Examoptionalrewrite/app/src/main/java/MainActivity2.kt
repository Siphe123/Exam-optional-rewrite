package com.fake.examoptionalrewrite

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity2 : AppCompatActivity() {

    // Declare and initialize variables and arrays
    private val morningExpenses = doubleArrayOf(60.0, 12.0, 80.0, 100.0, 35.0, 50.0, 0.0)
    private val afternoonExpenses = doubleArrayOf(100.0, 200.0, 60.0, 50.0, 40.0, 70.0, 0.0)
    private val expenseNotes = arrayOf(
        "Purchased lunch and snacks",
        "Bought coffee and study supplies",
        "Bought English breakfast and snacks",
        "Bought English breakfast and socks",
        "A key chain and a soft drink",
        "Lunch and a phone case",
        "Forgot wallet at home"
    )
    private val daysOfWeek = arrayOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")

    @SuppressLint("MissingInflatedId")
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

        // Initialize the average expenses TextView
        val averageExpensesTextView = findViewById<TextView>(R.id.average_expenses_text)
        updateAverageExpenses(averageExpensesTextView)

        // Set up the button to navigate to the details screen
        val detailsButton = findViewById<Button>(R.id.details_button)
        detailsButton.setOnClickListener {
            navigateToDetailsScreen()
        }
    }

    // Method to calculate the average spending for each day
    private fun calculateAverageSpending(): DoubleArray {
        val dailyAverages = DoubleArray(7)
        for (i in morningExpenses.indices) {
            dailyAverages[i] = (morningExpenses[i] + afternoonExpenses[i]) / 2
        }
        return dailyAverages
    }

    // Method to update the average expenses in the UI
    private fun updateAverageExpenses(averageExpensesTextView: TextView) {
        val averages = calculateAverageSpending()
        val averageStringBuilder = StringBuilder()
        for (i in averages.indices) {
            averageStringBuilder.append("${daysOfWeek[i]}: $${"%.2f".format(averages[i])}\n")
        }
        averageExpensesTextView.text = averageStringBuilder.toString()
    }

    // Method to navigate to the details screen
    private fun navigateToDetailsScreen() {
        val intent = Intent(this, DetailsActivity::class.java).apply {
            putExtra("morningExpenses", morningExpenses)
            putExtra("afternoonExpenses", afternoonExpenses)
            putExtra("expenseNotes", expenseNotes)
            putExtra("daysOfWeek", daysOfWeek)
        }
        startActivity(intent)
    }
}

// Second activity to show detailed daily views
class DetailsActivity: AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        // Retrieve the passed data
        val morningExpenses = intent.getDoubleArrayExtra("morningExpenses")
        val afternoonExpenses = intent.getDoubleArrayExtra("afternoonExpenses")
        val expenseNotes = intent.getStringArrayExtra("expenseNotes")
        val daysOfWeek = intent.getStringArrayExtra("daysOfWeek")

        // Example: Display data in a TextView (implement your own layout logic as needed)
        val detailsTextView = findViewById<TextView>(R.id.details_text)
        val detailsBuilder = StringBuilder()

        if (morningExpenses != null && afternoonExpenses != null && expenseNotes != null && daysOfWeek != null) {
            for (i in morningExpenses.indices) {
                detailsBuilder.append("${daysOfWeek[i]}:\n")
                detailsBuilder.append("  Morning: $${"%.2f".format(morningExpenses[i])}\n")
                detailsBuilder.append("  Afternoon: $${"%.2f".format(afternoonExpenses[i])}\n")
                detailsBuilder.append("  Notes: ${expenseNotes[i]}\n\n")
            }
        }

        detailsTextView.text = detailsBuilder.toString()

        // Set up the back button
        val backButton = findViewById<Button>(R.id.back_button)
        backButton.setOnClickListener {
            finish()
        }
    }
}


