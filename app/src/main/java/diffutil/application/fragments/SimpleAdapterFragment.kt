package diffutil.application.fragments

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import diffutil.application.R
import diffutil.application.base.BaseAdapter
import diffutil.application.base.ViewHolder
import diffutil.application.models.TvSerie
import diffutil.application.viewModels.SimpleAdapterViewModel
import diffutil.application.widget.TVSeriesItem

class SimpleAdapterFragment : Fragment() {

    private val displayMetrics by lazy {
        val metrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(metrics)
        metrics
    }

    private val itemHeight by lazy { (displayMetrics.heightPixels * 0.40).toInt() }

    private lateinit var tvSeriesAdapter: TvSeriesBaseAdapter
    private lateinit var viewModel: SimpleAdapterViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        tvSeriesAdapter = TvSeriesBaseAdapter()
        return layoutInflater.inflate(R.layout.fragment_simple_adapter, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(view.findViewById<RecyclerView>(R.id.rvSimpleAdapter)) {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context, 2)
            adapter = tvSeriesAdapter
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = SimpleAdapterViewModel()
        viewModel.items.observe(viewLifecycleOwner, Observer {
            tvSeriesAdapter.items = it
            tvSeriesAdapter.notifyDataSetChanged()
        })
        viewModel.events.observe(viewLifecycleOwner, Observer {
            if (it is SimpleAdapterViewModel.SimpleAdapterViewModelEvents.OnItemChanged) {
                tvSeriesAdapter.items = tvSeriesAdapter.items.mapIndexed { index, tvSerie ->
                    if (index == it.index) {
                        it.item
                    } else {
                        tvSerie
                    }
                }
                tvSeriesAdapter.notifyItemChanged(it.index)
            }

        })
    }

    inner class TvSeriesBaseAdapter : BaseAdapter<TvSerie>() {
        override fun getViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<*, *> {
            return TvSeriesViewHolder(TVSeriesItem(parent.context).apply {
                layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, itemHeight)
            })
        }
    }

    inner class TvSeriesViewHolder(private val TVSeriesItemView: TVSeriesItem) :
        ViewHolder<TvSerie, Any>(TVSeriesItemView) {

        override fun bind(item: TvSerie) {
            TVSeriesItemView.textViewName.text = item.name
            Picasso.get().load(item.image).into(TVSeriesItemView.imageViewSeries)
            TVSeriesItemView.imageViewFavorite.setImageResource(
                if (item.isFavorite) {
                    R.drawable.ic_favorite
                } else {
                    R.drawable.ic_not_favorite
                }
            )
            TVSeriesItemView.imageViewFavorite.setOnClickListener {
                viewModel.processFavoriteChange(item.id)
            }
        }

    }

}