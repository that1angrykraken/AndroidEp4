package seamonster.kraken.androidep4

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import seamonster.kraken.androidep4.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    private lateinit var adapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initAddTaskButton()
        initRecyclerView()
    }

    private fun initAddTaskButton() {
        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == RESULT_OK) {
                    adapter.data = getTasks()
                    adapter.notifyDataSetChanged()
                }
            }
        binding.fabAddTask.setOnClickListener {
            val intent = Intent(this, AddTaskActivity::class.java)
            resultLauncher.launch(intent)
        }
    }

    private fun initRecyclerView() {
        adapter = TaskAdapter(getTasks())
        binding.rvTasks.layoutManager = LinearLayoutManager(this)
        binding.rvTasks.adapter = adapter
    }

    private fun getTasks(): List<Task> {
        val list = ArrayList<Task>()

        val uri = TaskProvider.CONTENT_URI
        val projection = arrayOf("id", "name", "date")
        val cursor = contentResolver.query(uri, projection, null, null, null)
        cursor?.let {
            if (cursor.moveToFirst()) {
                do {
                    val task = Task(cursor.getInt(0), cursor.getString(1), cursor.getString(2))
                    list.add(task)
                } while (cursor.moveToNext())
            }
            cursor.close()
        }
        return list
    }
}

