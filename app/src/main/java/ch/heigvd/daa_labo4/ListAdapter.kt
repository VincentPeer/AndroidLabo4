package ch.heigvd.daa_labo4

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class ListAdapter(_items: List<Int> = listOf()) :
    RecyclerView.Adapter<ListAdapter.ViewHolder>()  {

    var items = listOf<Int>()
//    set(value) {
//        field = value
//        notifyDataSetChanged()
//    }

    init {
        items = _items
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item, parent, false), viewType
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    inner class ViewHolder(view: View, private val viewType: Int) : RecyclerView.ViewHolder(view) {
        private val loader = view.findViewById<ProgressBar>(R.id.loader)
        private val image = view.findViewById<ImageView>(R.id.image)
        fun bind(itemPosition: Int) {

        }
    }

}