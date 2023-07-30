package com.ongveloper.presentation

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import timber.log.Timber

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != ALARM_ACTION) return
        val requestCode = intent.getIntExtra(ALARM_CODE_KEY, -1)
        if (requestCode == ALARM_CODE_VALUE) {
            Timber.e("알람 받기 완료")
            /*todo
            * 1. 알람 창 띄우기
            * 2. 진동 등의 알람 설정
            * */
            val scheduleId = intent.getLongExtra(SCHEDULE_KEY, -1L)
            startActivity(context, scheduleId)
        }
    }

    private fun startActivity(context: Context, scheduleId: Long) {
        context.startActivity(
                Intent(
                        context, AlarmActivity::class.java,
                ).apply {
                    action = Intent.ACTION_VIEW
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    putExtra(SCHEDULE_KEY, scheduleId)
                }
        )
    }
}