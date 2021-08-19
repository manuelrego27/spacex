package com.spacex.ui.spacex

import android.app.Dialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.chip.Chip
import com.spacex.R
import com.spacex.data.local.launch.LaunchDao.LaunchFilter.FILTER_SORT
import com.spacex.data.local.launch.LaunchDao.LaunchFilter.FILTER_SUCCESS
import com.spacex.data.local.launch.LaunchDao.LaunchFilter.FILTER_YEAR
import com.spacex.databinding.FilterLayoutBinding
import com.spacex.databinding.LaunchesFragmentBinding
import com.spacex.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import kotlin.collections.set


@AndroidEntryPoint
class SpaceXFragment : Fragment()  {
    private var binding: LaunchesFragmentBinding by autoCleared()
    private val viewModel: SpaceXViewModel by viewModels()
    private val adapter = SpaceXAdapter()
    private val filters = HashMap<String, List<Int>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        binding = LaunchesFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.launchesList.adapter = adapter

        return binding.root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_filter -> showDialog()
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showDialog() : Boolean {

        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)

        val bind :FilterLayoutBinding = FilterLayoutBinding.inflate(LayoutInflater.from(context))
        dialog.setContentView(bind.root)

        setupFilters(bind)
        updateFilters(bind)

        bind.actionClose.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
        return true
    }


    private fun setupFilters(bind :FilterLayoutBinding) {
        bind.sortOptions.setOnCheckedChangeListener { _, isChecked ->
            when(isChecked){
                R.id.asc_option -> filters[FILTER_SORT] = arrayListOf(1)
                R.id.desc_option -> filters[FILTER_SORT] = arrayListOf(0)
                else -> filters[FILTER_SORT] = arrayListOf(-1)
            }

            with(viewModel){setFilters(filters)}
        }

        bind.successOptions.setOnCheckedChangeListener { _, isChecked ->
            when(isChecked){
                R.id.success_win -> filters[FILTER_SUCCESS] = arrayListOf(1)
                R.id.success_lose -> filters[FILTER_SUCCESS] = arrayListOf(0)
                else -> filters.remove(FILTER_SUCCESS)
            }

            with(viewModel){setFilters(filters)}
        }
    }


    private fun updateFilters(bind :FilterLayoutBinding){

        viewModel.years.value?.let{
            for(year in it){
                val chip= Chip(bind.editYears.context)
                chip.text = year.toString()
                chip.id = year
                chip.isClickable = true
                chip.isCheckable = true
                chip.setTextAppearance(R.style.TextAppearance_MaterialComponents_Button)
                chip.setOnCheckedChangeListener { _, _ ->
                    filters[FILTER_YEAR] = bind.editYears.checkedChipIds

                    if(bind.editYears.checkedChipIds.isEmpty())
                        filters.remove(FILTER_YEAR)

                    with(viewModel){setFilters(filters)}
                }
                bind.editYears.addView(chip)
            }
        }

        when (filters[FILTER_SUCCESS]?.get(0)) {
            0 -> bind.successLose.isChecked = true
            1 -> bind.successWin.isChecked = true
            else -> bind.successAll.isChecked = true
        }

        when (filters[FILTER_SORT]?.get(0)) {
            0 -> bind.descOption.isChecked = true
            else -> bind.ascOption.isChecked = true
        }
    }
}