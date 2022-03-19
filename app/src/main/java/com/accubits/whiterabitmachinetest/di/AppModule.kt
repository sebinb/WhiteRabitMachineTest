package com.accubits.whiterabitmachinetest.di


import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import androidx.room.Room
import com.accubits.whiterabitmachinetest.api.RetrofitApi
import com.accubits.whiterabitmachinetest.db.AppDatabase
import com.accubits.whiterabitmachinetest.db.EmployeeDao

import com.accubits.whiterabitmachinetest.repository.EmployeeRepository
import com.accubits.whiterabitmachinetest.view.adapter.EmployeeListAdapter
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private const val TAG = "AppModule"
    lateinit var networks: Network

    @Singleton
    @Provides
    fun providesRetrofit(gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl(RetrofitApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }


    @Singleton
    @Provides
    fun providesGson(): Gson {
        return GsonBuilder().setLenient().create()
    }


    @Singleton
    @Provides
    fun providesRetrofitApi(retrofit: Retrofit): RetrofitApi =
        retrofit.create(RetrofitApi::class.java)

    @Singleton
    @Provides
    fun providesRepo(
        retrofitApi: RetrofitApi,
        gson: Gson,
        appDatabase: AppDatabase
    ): EmployeeRepository =
        EmployeeRepository(retrofitApi, gson, appDatabase)

    @Provides
    fun provideChannelDao(appDatabase: AppDatabase): EmployeeDao {
        return appDatabase.employeeDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "Employee"
        ).build()
    }

    @Provides
    @Singleton
    fun provideEmployeeAdapter(gson: Gson): EmployeeListAdapter {
        return EmployeeListAdapter(gson)
    }


}