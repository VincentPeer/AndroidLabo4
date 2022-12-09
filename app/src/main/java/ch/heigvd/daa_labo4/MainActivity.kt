package ch.heigvd.daa_labo4

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        val view = super.onCreateView(name, context, attrs)

        if (view != null) {
            val recycler = view.findViewById<RecyclerView>(R.id.recycler)
            val adapter = ListAdapter()
            recycler.adapter = adapter
            recycler.layoutManager = GridLayoutManager(this, 3)
        }

        return view
    }


}