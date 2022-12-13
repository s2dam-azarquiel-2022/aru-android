package net.azarquiel.alltricks.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import net.azarquiel.alltricks.R
import net.azarquiel.alltricks.model.Brand
import net.azarquiel.alltricks.view.MainActivity
import net.azarquiel.alltricks.viewModel.BrandViewModel

class BrandAdapter(
    private val context: MainActivity,
    thisView: RecyclerView,
    private val itemLayout: Int,
    brandViewModel: BrandViewModel
) : RecyclerView.Adapter<BrandAdapter.ViewHolder>() {
    private var data: List<Brand> = emptyList()
    private val groupClickHandler = GroupClickHandler()

    init {
        thisView.adapter = this
        thisView.layoutManager = LinearLayoutManager(context)
        brandViewModel.getAll().observe(context) { lines ->
            this.setData(lines)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<Brand>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(itemLayout, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, pos: Int) = holder.bind(data[pos])
    override fun getItemCount(): Int = data.size

    inner class ViewHolder(layout: View) : RecyclerView.ViewHolder(layout) {
        @Suppress("NOTHING_TO_INLINE")
        private inline fun Int.setText(text: String) {
            itemView.findViewById<TextView>(this).text = text
        }

//        @Suppress("NOTHING_TO_INLINE")
//        private inline fun Int.setImage(name: String) {
//            itemView.findViewById<ImageView>(this).setImageResource(
//                context.resources.getIdentifier(name, "drawable", context.packageName)
//            )
//        }

        fun bind(item: Brand) {
            R.id.brandName.setText(item.name)

            itemView.tag = item
            itemView.setOnClickListener(groupClickHandler)
        }
    }

    inner class GroupClickHandler : View.OnClickListener {
        override fun onClick(view: View?) {
            (view?.tag as Brand).let { brand ->
//                Intent(context, LineActivity::class.java).let {
//                    it.putExtra("lineView", brand)
//                    context.startActivity(it)
//                }
            }
        }
    }
}
