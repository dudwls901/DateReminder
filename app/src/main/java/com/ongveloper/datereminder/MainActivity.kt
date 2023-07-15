package com.ongveloper.datereminder

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.ongveloper.datereminder.databinding.ActivityMainBinding
import com.ongveloper.domain.model.Schedule
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.Instant
import java.time.ZoneId

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private val alarmManager: AlarmManager by lazy {
        this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    }
//    PendingIntent.FLAG_UPDATED_CURRENT : 있으면 가져오고 없으면 만듦
    private val alarmIntent: Intent by lazy{
        Intent(this, AlarmReceiver::class.java).apply {
            putExtra(ALARM_CODE, PENDING_INTENT_ALARM_CODE)
        }
    }
    private val pendingIntent: PendingIntent by lazy {
        PendingIntent.getBroadcast(this, PENDING_INTENT_ALARM_CODE, alarmIntent,
            PendingIntent.FLAG_IMMUTABLE)
    }
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
                    mainEvent.collectLatest {
                        when(it){
                            is MainState.SaveSchedule ->{
                                Timber.e("MainState ${it}")
                                registerAlarm(it.schedule)
                            }
                        }
                    }
                }
                launch {
                    scheduleParams.collectLatest {
                        Timber.e("schedule: ${it}")
                    }
                }
            }
        }
    }

    private fun registerAlarm(schedule: Schedule) {
        /*todo
        * 1. 알람 db에 저장
        * 2. 3번의 알람 설정 -> 한 번에 3번 다 설정하든, 한 번 울리면 그 다음 알람 설정하든
        * */
        val triggerTime = System.currentTimeMillis() + 1000L * 40
        Timber.e("알람 울릴 시간 ${Instant.ofEpochMilli(triggerTime).atZone(ZoneId.systemDefault()).toLocalDateTime()}")
        Toast.makeText(this, "${Instant.ofEpochMilli(triggerTime).atZone(ZoneId.systemDefault()).toLocalDateTime()}", Toast.LENGTH_SHORT).show()
        alarmManager.set(
            AlarmManager.RTC_WAKEUP,
            triggerTime,
            pendingIntent
        )
    }

    private fun initViews() = with(binding){
        datePicker.minDate = viewModel.currentTimeMillis
        Timber.e("year: ${datePicker.year} month: ${datePicker.month+1} day: ${datePicker.dayOfMonth}")
        Timber.e("hour: ${timePicker.hour} minute: ${timePicker.minute}")
    }
}