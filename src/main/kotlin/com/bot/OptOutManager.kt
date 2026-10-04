package com.bot

import mu.KotlinLogging
import java.io.File

object OptOutManager {

    private val logger = KotlinLogging.logger {}
    private val file = File("./optout.txt")
    private val optedOut: MutableSet<Long> = mutableSetOf()

    fun load() {
        if (file.exists()) {
            optedOut.addAll(file.readLines().mapNotNull { it.trim().toLongOrNull() })
        }
        logger.info { "Loaded ${optedOut.size} opted-out user(s)" }
    }

    fun isOptedOut(userId: Long): Boolean = optedOut.contains(userId)

    fun optOut(userId: Long): Boolean {
        val added = optedOut.add(userId)
        if (added) save()
        return added
    }

    fun optIn(userId: Long): Boolean {
        val removed = optedOut.remove(userId)
        if (removed) save()
        return removed
    }

    private fun save() {
        file.writeText(optedOut.joinToString("\n"))
    }
}
