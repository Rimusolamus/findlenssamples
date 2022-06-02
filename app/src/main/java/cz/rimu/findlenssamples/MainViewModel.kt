package cz.rimu.findlenssamples

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class MainViewModel(
    private val mainRepository: MainRepository
) : ViewModel() {
    var v1 = MutableStateFlow("0")
}