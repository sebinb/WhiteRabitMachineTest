package com.accubits.whiterabitmachinetest.application

import android.app.Application
import com.facebook.stetho.Stetho
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class WhiteRabitMachineTest : Application() {
    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this);

    }

}