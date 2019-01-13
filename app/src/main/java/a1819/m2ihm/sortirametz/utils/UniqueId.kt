package a1819.m2ihm.sortirametz.utils

import java.util.concurrent.atomic.AtomicInteger

object UniqueId {
    private val counter = AtomicInteger()

    /**
     * Generate an runtime unique integer
     */
    fun nextValue(): Int {
        return counter.getAndIncrement()
    }
}
