package diffutil.application.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import diffutil.application.models.TvSerie
import diffutil.application.repository.Repository

class SimpleAdapterViewModel {

    private val _items = MutableLiveData<List<TvSerie>>()
    val items: LiveData<List<TvSerie>> get() = _items

    private val _events = MutableLiveData<SimpleAdapterViewModelEvents>()
    val events : LiveData<SimpleAdapterViewModelEvents> get() = _events

    init {
        getRepositoryItems()
    }

    private fun getRepositoryItems() {
        _items.postValue(Repository.getItemInstanceList())
    }

    fun processFavoriteChange(id: String) {
        Repository.updateFavoriteState(id)
        val items = Repository.getItemInstanceList()
        val itemIndex = items.indexOfFirst { it.id == id }
        _events.postValue(SimpleAdapterViewModelEvents.OnItemChanged(items[itemIndex], itemIndex))
    }

    sealed class SimpleAdapterViewModelEvents {
        class OnItemChanged(val item: TvSerie, val index: Int) : SimpleAdapterViewModelEvents()
    }

}