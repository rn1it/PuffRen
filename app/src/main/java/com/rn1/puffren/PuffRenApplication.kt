package com.rn1.puffren

import android.app.Application
import com.rn1.puffren.data.source.PuffRenRepository
import com.rn1.puffren.util.ServiceLocator
import kotlin.properties.Delegates

class PuffRenApplication : Application() {

    val puffRenRepository: PuffRenRepository
        get() = ServiceLocator.provideTasksRepository(this)

    companion object {
        var instance: PuffRenApplication by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}