package org.aplugin.invitePlayer.Commands

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.plugin.java.JavaPlugin

class ConsoleCmds:TabExecutor {
    fun ConsoleCmds(plugin: JavaPlugin) {
        plugin.getCommand("cinvP")?.setExecutor(this::onCommand)
        plugin.getCommand("cinvP")?.setTabCompleter(this::onTabComplete)
    }

    override fun onCommand(
        sender: CommandSender,
        cmd: Command,
        label: String,
        args: Array<out String>?
    ): Boolean {
        if (sender !is ConsoleCommandSender) {
            sender.sendMessage(
                Component.text("[aInvP] 이 명령어는 콘솔만 사용 가능합니다.")
                    .color(TextColor.color(0xFF4C4C)).decorate(TextDecoration.ITALIC))
            return true
        }
        if (args==null || args.isEmpty()) {
            sender.sendMessage(Component.text("[aInvP] /cinvP [<플레이어 닉네임>]")
                .color(TextColor.color(0xFF4C4C)).decorate(TextDecoration.ITALIC))
            sender.sendMessage(Component.text("[aInvP] 이미 초대가 됐다면 플레이어의 초대권을 뺴앗습니다")
                .color(TextColor.color(0xFF4C4C)).decorate(TextDecoration.ITALIC))
            return true
        }

        val player=Bukkit.getOfflinePlayer(args[0])

        if (!player.isWhitelisted) {
            player.isWhitelisted = true

            sender.sendMessage(
                Component.text("[aInvP] 플레이어 ${player.name}를 서버에 초대했습니다.")
                    .color(TextColor.color(0xB4E380)).decorate(TextDecoration.ITALIC)
            )
        } else {
            player.isWhitelisted = false

            sender.sendMessage(
                Component.text("[aInvP] 플레이어 ${player.name}의 초대권을 빼앗았습니다.")
                    .color(TextColor.color(0xB4E380)).decorate(TextDecoration.ITALIC)
            )
        }
        return true
    }

    override fun onTabComplete(
        sender: CommandSender,
        cmd: Command,
        label: String,
        args: Array<out String>
    ): MutableList<String> {
        val tab = mutableListOf<String>()

        if (args.isEmpty() || args.size == 1) {
            tab.add("[PlayerName]")
        } else if (args.size == 2) {
            tab.add("true")
            tab.add("false")
        }
        return tab
    }
}