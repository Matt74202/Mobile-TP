package mg.itu.mizaha.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import mg.itu.mizaha.data.dao.RestaurantDao
import mg.itu.mizaha.data.entities.Restaurant

class RestaurantsViewModel(private val dao: RestaurantDao) : ViewModel() {

    val restaurants: StateFlow<List<Restaurant>> =
        dao.getAll()
            .stateIn(viewModelScope, kotlinx.coroutines.flow.SharingStarted.Lazily, emptyList())

    fun filtrerParType(type: String): StateFlow<List<Restaurant>> =
        dao.byType(type)
            .stateIn(viewModelScope, kotlinx.coroutines.flow.SharingStarted.Lazily, emptyList())

}