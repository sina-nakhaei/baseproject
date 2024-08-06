package ir.sinanakhaei.baseproject

import android.app.Application
import com.skydoves.sandwich.SandwichInitializer
import dagger.hilt.android.HiltAndroidApp
import ir.sinanakhaei.retrofit.ResponseOperator
import timber.log.Timber

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()

        SandwichInitializer.sandwichOperators = mutableListOf(ResponseOperator<Any>())
        Timber.plant(Timber.DebugTree())
    }
}