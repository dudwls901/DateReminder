package com.ongveloper.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import timber.log.Timber

class AlarmActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm)
        val scheduleId = intent.getLongExtra(SCHEDULE_KEY,-1L)
        Timber.e("last id: $scheduleId")
    }
}