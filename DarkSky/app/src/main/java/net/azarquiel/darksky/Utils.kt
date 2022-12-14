package net.azarquiel.darksky

import android.graphics.Color
import kotlin.math.absoluteValue

object Utils {
    private fun Float.between(min: Int, max: Int): Int =
        if (this > max) max
        else if (this < min) min
        else this.toInt()

    private fun Float.min(min: Int): Int =
        if (this < min) min
        else this.toInt()

    fun Int.darken(): Int {
        return Color.argb(
            80,
            (Color.red(this) * 0.8F).min(0),
            (Color.green(this) * 0.8F).min(0),
            (Color.blue(this) * 0.8F).min(0)
        )
    }

    fun Float.toColor(): Int {
        // Took me a while to make this
        return Color.rgb(
            ((this + 20F) * 3.2F).between(0, 255),
            (90F - (this.absoluteValue + 10) * 1.5F).between(0, 100),
            (255F - (this * 3.4F)).between(0, 255)
        )
    }
}