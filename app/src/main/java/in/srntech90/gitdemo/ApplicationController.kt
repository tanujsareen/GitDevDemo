package `in`.srntech90.gitdemo

import `in`.srntech90.gitdemo.utility.FakeCrashLibrary
import android.app.Application
import android.util.Log
import androidx.annotation.NonNull
import timber.log.Timber

/**
Created by Tanuj.Sareen on 23,January,2020
 **/
class ApplicationController : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree()) // FOR planting Timber Logger for Debug Mode
        else
            Timber.plant()  //for Planting Timber Log for Live Environment
    }


    /** A tree which logs important information for crash reporting.  */
    private class CrashReportingTree : Timber.Tree() {
        override fun log(priority: Int, tag: String?, @NonNull message: String, t: Throwable?) {
            if (priority == Log.VERBOSE || priority == Log.DEBUG) {
                return
            }

            FakeCrashLibrary.log(priority, tag, message)

            if (t != null) {
                if (priority == Log.ERROR) {
                    FakeCrashLibrary.logError(t)
                } else if (priority == Log.WARN) {
                    FakeCrashLibrary.logWarning(t)
                }
            }
        }
    }
}