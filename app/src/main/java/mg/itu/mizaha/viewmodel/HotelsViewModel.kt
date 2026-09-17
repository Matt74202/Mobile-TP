package mg.itu.mizaha.viewmodel

import android.annotation.SuppressLint
import android.os.Looper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.Priority
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HotelsViewModel @Inject constructor(
    private val fusedClient: FusedLocationProviderClient
) : ViewModel() {

    private val _userLocation = MutableStateFlow<Pair<Double, Double>?>(null)
    val userLocation: StateFlow<Pair<Double, Double>?> = _userLocation.asStateFlow()

    @SuppressLint("MissingPermission")
    fun fetchCurrentLocation() {
        // 1. Essayer d'abord la dernière position connue (rapide)
        fusedClient.lastLocation
            .addOnSuccessListener { location ->
                if (location != null) {
                    _userLocation.value = location.latitude to location.longitude
                }
            }

        // 2. Demander une mise à jour unique plus précise
        val request = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY,
            5_000L
        ).setMaxUpdates(1).build()

        fusedClient.requestLocationUpdates(
            request,
            object : LocationCallback() {
                override fun onLocationResult(result: LocationResult) {
                    result.lastLocation?.let {
                        _userLocation.value = it.latitude to it.longitude
                    }
                    fusedClient.removeLocationUpdates(this)
                }
            },
            Looper.getMainLooper()
        )
    }
}