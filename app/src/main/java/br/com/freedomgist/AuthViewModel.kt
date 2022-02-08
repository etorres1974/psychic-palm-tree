package br.com.freedomgist

import android.util.Log
import androidx.annotation.MainThread
import androidx.lifecycle.*
import br.com.data.apiSource.models.DeviceCode
import br.com.data.apiSource.network.utils.AUTH
import br.com.data.apiSource.network.utils.ErrorEntity
import br.com.data.apiSource.network.utils.NetworkResult
import br.com.data.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicBoolean

class AuthViewModel(
    private val authRepository: AuthRepository
): ViewModel() {

    private val _deviceCode = SingleLiveEvent<NetworkResult<DeviceCode>>()
    val deviceCode : LiveData<NetworkResult<DeviceCode>> = _deviceCode

   fun fetchDeviceCode() = viewModelScope.launch(Dispatchers.IO) {
       _deviceCode.postValue(authRepository.fetchUserCode())
   }

    fun fetchToken(deviceCode: DeviceCode) = viewModelScope.launch(Dispatchers.IO) {
        authRepository.fetchUserToken(deviceCode)
    }
}

private class SingleLiveEvent<T> : MutableLiveData<T>() {
    private val pending = AtomicBoolean(false)
    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        if (hasActiveObservers()) {
            Log.w(TAG, "Multiple observers registered but only one will be notified of changes.")
        }
        // Observe the internal MutableLiveData
        super.observe(owner, Observer { t ->
            if (pending.compareAndSet(true, false)) {
                observer.onChanged(t)
            }
        })
    }

    @MainThread
    override fun setValue(t: T?) {
        pending.set(true)
        super.setValue(t)
    }
    /**
     * Used for cases where T is Void, to make calls cleaner.
     */
    @MainThread
    fun call() {
        value = null
    }
    companion object {
        private val TAG = "SingleLiveEvent"
    }
}