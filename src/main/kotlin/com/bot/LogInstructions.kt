package com.bot

data class LogInstruction(val label: String, val description: String)

object LogInstructions {

    private val instructions = linkedMapOf(
        "windows" to LogInstruction(
            label = "Windows",
            description = """
                **1.** Press `Win + R`, paste the path below, then press Enter:
                ```
                %userprofile%/.runelite/logs
                ```
                **2.** Locate `client.log` or `client` (no extension) and drag it into this channel.
            """.trimIndent()
        ),
        "macos" to LogInstruction(
            label = "macOS",
            description = """
                **1.** Open Finder, press `Cmd + Shift + G`, and paste the path below:
                ```
                ~/.runelite/logs
                ```
                **2.** Drag `client.log` or `client` into this channel.
            """.trimIndent()
        ),
        "linux" to LogInstruction(
            label = "Linux",
            description = """
                **1.** Open your file manager or terminal and navigate to:
                ```
                ~/.runelite/logs
                ```
                **2.** Drag `client.log` or `client` into this channel.
            """.trimIndent()
        )
    )

    private val channelMappings = mapOf(
        886739001837498418L to "windows",
        886989555914379324L to "macos",
        886989614118735882L to "linux"
    )

    val all: Collection<LogInstruction>
        get() = instructions.values

    fun forPlatform(platform: String): LogInstruction? = instructions[platform.lowercase()]

    fun platformForChannel(channelId: Long): String? = channelMappings[channelId]

    fun choices(): List<Pair<String, String>> = instructions.map { it.value.label to it.key }
}


