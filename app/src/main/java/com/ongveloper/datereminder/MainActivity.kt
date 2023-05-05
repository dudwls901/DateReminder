package com.ongveloper.datereminder

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.ongveloper.datereminder.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.vm = viewModel
        binding.lifecycleOwner = this

        initViews()
        initObservers()
    }

    private fun initObservers() = with(viewModel){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                launch {
                    schedule.collectLatest {
                        Timber.e("schedule: ${it}")
                    }
                }
            }
        }
    }

    private fun initViews() = with(binding){
        datePicker.minDate = viewModel.currentTimeMillis
        Timber.e("year: ${datePicker.year} month: ${datePicker.month+1} day: ${datePicker.dayOfMonth}")
        Timber.e("hour: ${timePicker.hour} minute: ${timePicker.minute}")
    }
}