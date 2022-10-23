package net.azarquiel.darksky.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import net.azarquiel.darksky.R
import net.azarquiel.darksky.dao.DarkSky
import java.util.*

class DailyAdapter(
    private val context: Context,
    private val layout: Int,
) : RecyclerView.Adapter<DailyAdapter.ViewHolder>() {
    private var data: List<DarkSky.Time> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context).inflate(layout, parent, false),
        context,
    )

    override fun onBindViewHolder(holder: ViewHolder, pos: Int) = holder.bind(data[pos], pos)

    override fun getItemCount(): Int = data.size

    @SuppressLint("NotifyDataSetChanged")
    fun setDays(data: List<DarkSky.Time>) {
        this.data = data
        notifyDataSetChanged()
    }

    class ViewHolder(
        layout: View,
        private val context: Context,
    ) : RecyclerView.ViewHolder(layout) {

        @Suppress("NOTHING_TO_INLINE")
        private inline fun Int.setText(stringId: Int, vararg: Any) {
            itemView.findViewById<TextView>(this).text = context.getString(stringId, vararg)
        }

        fun bind(item: DarkSky.Time, pos: Int) {
            itemView.findViewById<TextView>(R.id.day).text =
                if (pos == 0) context.getString(R.string.today)
                else Calendar.getInstance().let {
                    it.timeInMillis = item.time * 1000
                    context.resources.getStringArray(R.array.daysOfTheWeek)[
                        it.get(Calendar.DAY_OF_WEEK) - 1
                    ]
                }
            R.id.tempMin.setText(R.string.temp, item.temperatureMin)
            R.id.tempMax.setText(R.string.temp, item.temperatureMax)
            R.id.summary.setText(R.string.summary, item.summary)
            R.id.precipProbability.setText(R.string.precipProbability, item.getPrecipProbability())
            R.id.humidity.setText(R.string.humidity, item.humidity)
            R.id.windSpeed.setText(R.string.windSpeed, item.windSpeed)
            Picasso.get().load(item.getIcon()).into(itemView.findViewById<ImageView>(R.id.icon))
            itemView.tag = item
        }
    }
}