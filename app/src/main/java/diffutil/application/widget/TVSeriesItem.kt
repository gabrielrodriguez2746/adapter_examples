package diffutil.application.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import diffutil.application.R

class TVSeriesItem @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    val imageViewSeries: ImageView by lazy { findViewById<ImageView>(R.id.ivSeries) }
    val textViewName: TextView by lazy { findViewById<TextView>(R.id.tvName) }
    val imageViewFavorite: ImageView by lazy { findViewById<ImageView>(R.id.ivFavorite) }

    init {
        inflate(context, R.layout.item_tv_series, this)
    }

}