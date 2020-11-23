package muhammad.bahaa.robustatask.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import muhammad.bahaa.robustatask.R


class HomeAdapter(var photoList: MutableList<String>) :
    RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return photoList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_photo, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(position)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(position: Int): Unit = with(itemView) {
            val imageView = findViewById<ImageView>(R.id.image_view)
            imageView.setImageURI(photoList[position].toUri())
            imageView.setOnClickListener {
            }
        }
    }


}