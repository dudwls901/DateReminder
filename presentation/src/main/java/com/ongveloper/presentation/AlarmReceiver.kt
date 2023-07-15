package com.ongveloper.presentation

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import timber.log.Timber


class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if(intent.action != ALARM_ACTION) return
        val requestCode = intent.extras?.getInt(ALARM_CODE_KEY)
        if (requestCode == ALARM_CODE_VALUE) {
            Timber.e("알람 받기 완료")
            /*todo
            * 1. 알람 창 띄우기
            * 2. 진동 등의 알람 설정
            * */
            startActivity(context)
        }
    }

    private fun startActivity(context: Context) {
        val intentt = Intent(
            context, AlarmActivity::class.java,
        ).apply {
            action = Intent.ACTION_VIEW
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        context.startActivity(
            intentt
        )
    }
}