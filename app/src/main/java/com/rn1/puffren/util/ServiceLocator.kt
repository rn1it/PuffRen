package com.rn1.puffren.util

import android.content.Context
import com.rn1.puffren.data.source.DefaultPuffRenRepository
import com.rn1.puffren.data.source.PuffRenDataSource
import com.rn1.puffren.data.source.PuffRenRepository
import com.rn1.puffren.data.source.local.PuffRenLocalDataSource
import com.rn1.puffren.data.source.remote.PuffRenRemoteDataSource

object ServiceLocator {

    private var puffRenRepository: PuffRenRepository? = null

    fun provideTasksRepository(context: Context): PuffRenRepository {
        synchronized(this) {
            return puffRenRepository ?: createPuffRenRepository(context)
        }
    }

    private fun createPuffRenRepository(context: Context): PuffRenRepository {
        val newRepo =
            DefaultPuffRenRepository(PuffRenRemoteDataSource, createTaskLocalDataSource(context))
        puffRenRepository = newRepo
        return newRepo
    }


    private fun createTaskLocalDataSource(context: Context): PuffRenDataSource {
        //TODO
        return PuffRenLocalDataSource()
    }
}