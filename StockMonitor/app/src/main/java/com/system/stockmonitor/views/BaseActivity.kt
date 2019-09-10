package com.system.stockmonitor.views

import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel

abstract class BaseActivity : AppCompatActivity() {

    protected val dispatcher = PausableDispatcher(Dispatchers.Main)

    override fun onPause() {
        super.onPause()
        dispatcher.pause()
    }

    override fun onResume() {
        super.onResume()
        dispatcher.resume()
    }

    override fun onDestroy() {
        super.onDestroy()
        dispatcher.cancel()
    }
}