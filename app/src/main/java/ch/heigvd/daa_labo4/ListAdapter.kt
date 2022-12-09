package ch.heigvd.daa_labo4

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class ListAdapter(_items: List<Int> = listOf()) :
    RecyclerView.Adapter<ListAdapter.ViewHolder>()  {

    var items = listOf<Int>()

    init {
        items = _items
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    inner class ViewHolder(view: View, private val viewType: Int) : RecyclerView.ViewHolder(view) {
        fun bind(itemPosition: Int) {

        }
    }

}