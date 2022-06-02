package cz.rimu.findlenssamples

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidLogger()
            androidContext(this@MyApplication)
            modules(appModule)
        }
    }

    private val appModule = module {
        // later repos and viewModels can be separated in different modules, but for now it's okay
        single<MainRepository> { MainRepositoryImpl() }
        viewModel { MainViewModel(get()) }
    }
}