package seamonster.kraken.androidep4

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import seamonster.kraken.androidep4.databinding.LayoutListItemBinding

class TaskAdapter(var data: List<Task>) : RecyclerView.Adapter<TaskAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: LayoutListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Task) {
            binding.textTaskName.text = item.name
            binding.textTaskDate.text = item.date
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LayoutListItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[holder.adapterPosition]
        holder.bind(item)
    }
}