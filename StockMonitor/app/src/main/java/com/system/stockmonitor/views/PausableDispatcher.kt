package com.system.stockmonitor.views

import kotlinx.coroutines.CoroutineDispatcher
import java.util.*
import kotlin.coroutines.CoroutineContext

class PausableDispatcher(private val handler: CoroutineDispatcher) : CoroutineDispatcher() {

    private val queue: Queue<Pair<CoroutineContext, Runnable>> = LinkedList()
    private var isPaused: Boolean = false

    @Synchronized
    override fun dispatch(context: CoroutineContext, block: Runnable) {
        if (isPaused) {
            queue.add(context to block)
        } else {
            handler.dispatch(context, block)
        }
    }

    @Synchronized
    fun pause() {
        isPaused = true
    }

    @Synchronized
    fun resume() {
        isPaused = false
        runQueue()
    }

    private fun runQueue() {
        queue.iterator().let {
            while (it.hasNext()) {
                val (context, runnable) = it.next()
                it.remove()
                handler.dispatch(context, runnable)
            }
        }
    }
}