package com.bot

import kotlin.text.buildString

data class LogInstruction(val label: String, val description: String, val imageUrl: String? = null)

object LogInstructions {

    private val instructions = linkedMapOf(
        "windows" to LogInstruction(
            label = "Windows",
            description = buildString {
                appendLine("**1.** Press the Windows key + R, paste in the following path, and press Enter:")
                appendLine("```")
                appendLine("%userprofile%/.runelite/logs")
                appendLine("```")
                appendLine("**2.** Drag the file called `client` or `client.log` into this channel.")
            },
            imageUrl = "https://media.discordapp.net/attachments/1041155084794466315/1441458523023347822/ezgif-2e9b3c54103f8b60.gif?ex=6921de5c&is=69208cdc&hm=f01f646e2653e3f2d954566eb307439013ba8500d87d019abf53a2bc0d0146e1&="
        ),
        "macos" to LogInstruction(
            label = "macOS",
            description = buildString {
                appendLine("**1.** Open Finder, press `⌘ Cmd + Shift + G`, and paste in the following path:")
                appendLine("```")
                appendLine("~/.runelite/logs")
                appendLine("```")
                appendLine("**2.** Drag the file called `client` or `client.log` into this channel.")
            }
        ),
        "linux" to LogInstruction(
            label = "Linux",
            description = buildString {
                appendLine("**1.** Open your file manager and navigate to:")
                appendLine("```")
                appendLine("~/.runelite/logs")
                appendLine("```")
                appendLine("**2.** Drag the file called `client.log` or `client` into this channel.")
                appendLine()
                appendLine("If you're on Wayland or using Discord through a Flatpak, drag and drop may not work, so you can click the + button instead.")
            }
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
