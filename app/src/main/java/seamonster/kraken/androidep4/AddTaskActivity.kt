package seamonster.kraken.androidep4

import android.content.ContentValues
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import seamonster.kraken.androidep4.databinding.ActivityAddTaskBinding

class AddTaskActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initAddButton()

    }

    private fun initAddButton() {
        binding.buttonAdd.setOnClickListener {
            val taskName = binding.textTaskName.text.toString()
            val taskDate: String
            binding.datePicker.run {
                taskDate = "$dayOfMonth/${month + 1}/$year"
            }
            val values = ContentValues().apply {
                put("name", taskName)
                put("date", taskDate)
            }
            val inserted = contentResolver.insert(TaskProvider.CONTENT_URI, values)
            showMessage(inserted)
        }
    }

    private fun showMessage(inserted: Uri?) {
        val message: String
        val result: Int
        if (inserted != null) {
            result = RESULT_OK
            message = "Them thanh cong!"
        } else {
            result = 0
            message = "Them that bai!"
        }
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        setResult(result)
    }
}