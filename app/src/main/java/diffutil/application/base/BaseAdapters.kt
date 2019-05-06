package diffutil.application.base

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

abstract class BaseListAdapter<T : RecyclerItem<*, *>> :
    ListAdapter<T, ViewHolder<*, *>>(RecyclerItemDiffCallback<T>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<*, *> {
        return getViewHolder(parent, viewType)
    }

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(holder: ViewHolder<*, *>, position: Int) {
        getItem(position)?.getContent()?.let {
            (holder as ViewHolder<Any, Any>).bind(it)
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(holder: ViewHolder<*, *>, position: Int, payloads: MutableList<Any>) {
        payloads.firstOrNull()?.let {
            (holder as ViewHolder<Any, Any>).updateBind(it)
        } ?: super.onBindViewHolder(holder, position, payloads)
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position)?.getType() ?: -1
    }

    abstract fun getViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<*, *>
}

abstract class BaseAdapter<T : Any> :
    RecyclerView.Adapter<ViewHolder<*, *>>() {

    var items: List<T> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<*, *> {
        return getViewHolder(parent, viewType)
    }

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(holder: ViewHolder<*, *>, position: Int) {
        if (position >= 0 && position < items.size) {
            (holder as ViewHolder<Any, Any>).bind(items[position])
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    abstract fun getViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<*, *>
}


interface RecyclerItem<out R, out S> {
    fun getType(): Int
    fun getId(): Int
    fun getComparator(): Any?
    fun getContent(): R
    fun getDiffResolver(): S?
}

class RecyclerItemDiffCallback<T : RecyclerItem<*, *>> : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(firsteItem: T, secondItem: T): Boolean = firsteItem.getId() == secondItem.getId()

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(firsteItem: T, secondItem: T): Boolean {
        return firsteItem.getComparator() == secondItem.getComparator()
    }

    override fun getChangePayload(oldItem: T, newItem: T): Any? = newItem.getDiffResolver()
}

abstract class ViewHolder<T : Any, R : Any>(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun bind(item: T)

    open fun updateBind(item: R) = Unit
}