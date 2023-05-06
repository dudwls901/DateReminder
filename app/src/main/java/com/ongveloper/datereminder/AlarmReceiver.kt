package com.ongveloper.datereminder

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import timber.log.Timber


class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val requestCode = intent.extras?.getInt(ALARM_CODE)
        if (requestCode == PENDING_INTENT_ALARM_CODE) {
            Timber.e("알람 받기 완료")
            /*todo
            * 1. 알람 창 띄우기
            * 2. 진동 등의 알람 설정
            * */
        }
    }
}