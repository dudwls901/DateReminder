package com.ongveloper.presentation

import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.ongveloper.domain.model.Schedule
import com.ongveloper.presentation.databinding.ActivityMainBinding
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
    private lateinit var overlayPermissionLauncher: ActivityResultLauncher<Intent>
    private val overDrawOverlayPermissionDialog: MaterialAlertDialogBuilder by lazy {
        MaterialAlertDialogBuilder(this)
                .setTitle(getString(R.string.main_overlay_permission_alert_title))
                .setMessage(getString(R.string.main_overlay_permission_alert_body))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.ok)) { _, _ ->
                    requestOverDrawOverlayPermission()
                }
    }
    //    PendingIntent.FLAG_UPDATED_CURRENT : 있으면 가져오고 없으면 만듦
    private fun Context.makeAlarmIntent(schedule: Schedule): PendingIntent {
        val alarmIntent = Intent(this, AlarmReceiver::class.java).apply {
            putExtra(ALARM_CODE_KEY, ALARM_CODE_VALUE)
            putExtra(SCHEDULE_KEY, schedule.id)
            action = ALARM_ACTION
        }
        return PendingIntent.getBroadcast(this, ALARM_CODE_VALUE, alarmIntent,
                PendingIntent.FLAG_IMMUTABLE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.vm = viewModel
        binding.lifecycleOwner = this

        //반드시 onCreate (STARTED 이전)에서 초기화
        overlayPermissionLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode != Activity.RESULT_OK) processOverDrawOverlayPermission()
        }
        processOverDrawOverlayPermission()

        initViews()
        initObservers()
    }

    private fun processOverDrawOverlayPermission() {
        if (!Settings.canDrawOverlays(this)) {
            overDrawOverlayPermissionDialog.show()
        }
    }

    private fun requestOverDrawOverlayPermission() {
        val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName"))
        overlayPermissionLauncher.launch(intent)
    }

    private fun initObservers() = with(viewModel) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    mainEvent.collectLatest {
                        when (it) {
                            is MainEvent.SaveSchedule -> {
                                Timber.e("MainEvent ${it}")
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
                launch {
                    //                    schedules.collectLatest {
                    //                        Timber.e("schedules : ${it}")
                    //                    }
                }
            }
        }
    }

    private fun registerAlarm(schedule: Schedule) {
        /*todo
        * 1. 알람 db에 저장
        * 2. 3번의 알람 설정 -> 한 번에 3번 다 설정하든, 한 번 울리면 그 다음 알람 설정하든
        * */
        val triggerTime = System.currentTimeMillis() + 1000L * 10
        Timber.e("알람 울릴 시간 ${Instant.ofEpochMilli(triggerTime).atZone(ZoneId.systemDefault()).toLocalDateTime()}")
        Toast.makeText(this, "${Instant.ofEpochMilli(triggerTime).atZone(ZoneId.systemDefault()).toLocalDateTime()}", Toast.LENGTH_SHORT).show()
        alarmManager.set(
                AlarmManager.RTC_WAKEUP,
                triggerTime,
                makeAlarmIntent(schedule)
        )
    }

    private fun initViews() = with(binding) {
        datePicker.minDate = viewModel.currentTimeMillis
        Timber.e("year: ${datePicker.year} month: ${datePicker.month + 1} day: ${datePicker.dayOfMonth}")
        Timber.e("hour: ${timePicker.hour} minute: ${timePicker.minute}")
    }
}