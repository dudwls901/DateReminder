package com.ongveloper.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class AlarmActivity : AppCompatActivity() {

    private val viewModel: AlarmViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm)
        val scheduleId = intent.getLongExtra(SCHEDULE_KEY, -1L)
        viewModel.setSchedule(scheduleId)
        initObservers()
    }

    private fun initObservers() = with(viewModel) {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    schedule.collectLatest { schedule ->
                        schedule?.let {
                            Timber.e("alarmActivity Schedule : $it")
                        }
                    }
                }
            }
        }
    }
}