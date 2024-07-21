package org.aplugin.invitePlayer.Utils

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextDecoration
import net.md_5.bungee.api.ChatColor
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemRarity
import org.bukkit.inventory.ItemStack

class InvitePlayerP {
    fun getIP(): ItemStack {
        val item= ItemStack(Material.PAPER)
        val meta=item.itemMeta

        meta.displayName(
            Component.text("${ChatColor.GOLD}초대권")
            .decorate(TextDecoration.BOLD))
        meta.setRarity(ItemRarity.RARE)
        meta.setCustomModelData(512)
        meta.setMaxStackSize(16)
        item.itemMeta=meta

        return item
    }

    fun getPlayer(name:String): OfflinePlayer {
        val player=Bukkit.getOfflinePlayer(name)
        return player
    }

    fun RemoveInvPlayer(p: Player) {
        if (!p.inventory.contains(Material.PAPER)) return
        p.inventory.contents
            .filterNotNull()
            .find { it.hasItemMeta()
                    && it.itemMeta.customModelData==512
                    && it.itemMeta.hasDisplayName()
                    && it.itemMeta.displayName()!!.contains(Component.text("초대권"))
            }
            ?.let {
                it.amount -= 1
            }
    }
}