package com.spacex.ui.spacex

import androidx.lifecycle.*
import com.spacex.data.entities.Launch
import com.spacex.data.local.launch.LaunchDao.LaunchFilter.FILTER_SORT
import com.spacex.data.local.launch.LaunchDao.LaunchFilter.FILTER_SUCCESS
import com.spacex.data.local.launch.LaunchDao.LaunchFilter.FILTER_YEAR
import com.spacex.data.repository.CompanyInfoRepository
import com.spacex.data.repository.LaunchRepository
import com.spacex.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SpaceXViewModel @Inject constructor(
    private val launchRepository: LaunchRepository,
    private val companyInfoRepository: CompanyInfoRepository) : ViewModel() {

    private val _years = MutableLiveData<List<Int>>()

    val years: LiveData<List<Int>>
        get() = _years

    init {
        initializeTonight()
    }

    private fun initializeTonight() {
        viewModelScope.launch {
            _years.value = launchRepository.getYears()
        }
    }

    val companyInfo = companyInfoRepository.getInfo();

    private var filtersViewModel = MutableLiveData<HashMap<String, List<Int>>>().apply { value = HashMap() }
    fun setFilters(_filters: HashMap<String, List<Int>>) { filtersViewModel.value = _filters }

    var launches: LiveData<Resource<List<Launch>>> = filtersViewModel.switchMap {
        launchRepository.getLaunches(it[FILTER_YEAR], it[FILTER_SUCCESS]?.get(0), it[FILTER_SORT]?.get(0))
    }
}
