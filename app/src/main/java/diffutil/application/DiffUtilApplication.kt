package diffutil.application

import android.app.Application

class DiffUtilApplication : Application() {

    companion object {
        lateinit var instance: DiffUtilApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

}