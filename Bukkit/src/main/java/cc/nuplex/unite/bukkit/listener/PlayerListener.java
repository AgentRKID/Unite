package cc.nuplex.unite.bukkit.listener;

import cc.nuplex.engine.util.bukkit.CC;
import cc.nuplex.unite.Unite;
import cc.nuplex.unite.bukkit.UniteBukkitPlugin;
import cc.nuplex.unite.bukkit.setting.UniteSettings;
import cc.nuplex.unite.profile.Profile;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerListener implements Listener {

    @EventHandler(priority=EventPriority.LOWEST)
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        // Other plugins might have control over
        // so this will be toggled off in some cases.
        if (!UniteBukkitPlugin.getInstance().getConfig().getBoolean("settings.handle-chat")) {
            return;
        }

        Player player = event.getPlayer();
        Profile profile = Unite.getInstance().getProfileHandler().get(player.getUniqueId(), false);

        if (profile != null) {
            // They have the setting off,
            // and they're trying to talk? why...
            if (profile.isSettingEnabled(UniteSettings.HIDE_GLOBAL_CHAT)) {
                player.sendMessage(CC.translate("&cYou have global chat hidden, you may not speak."));
                event.setCancelled(true);
                return;
            }
        } else {
            player.kickPlayer(CC.translate("&cYour profile was not loaded."));
            return;
        }

        // Some people don't want to see messages,
        // so lets remove them from seeing it.
        event.getRecipients().removeIf(recipient -> {
            Profile recipientProfile = Unite.getInstance().getProfileHandler().get(recipient.getUniqueId(), false);

            if (recipientProfile != null) {
                return recipientProfile.isSettingEnabled(UniteSettings.HIDE_GLOBAL_CHAT);
            }
            return false;
        });

        event.setFormat(CC.translate(profile.getRankType().getPrefix() + profile.getUsername() + "&7: &f%2$s"));

        // Allow people with
        // permission to use chat colors
        if (player.hasPermission("unite.chat.format")) {
            event.setMessage(CC.translate(event.getMessage()));
        }
    }

}
