package cc.nuplex.unite.bukkit.listeners;

import cc.nuplex.unite.Unite;
import cc.nuplex.unite.profile.Profile;
import cc.nuplex.unite.profile.ProfileHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ConnectionListeners implements Listener {

    @EventHandler
    public void onAsyncPlayerPreLogin(AsyncPlayerPreLoginEvent event) {
        ProfileHandler profileHandler = Unite.getInstance().getProfileHandler();

        Profile profile = new Profile(event.getUniqueId(), event.getName());
        profileHandler.addEntry(profile);
        profileHandler.load(profile);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        ProfileHandler profileHandler = Unite.getInstance().getProfileHandler();

        Profile profile = profileHandler.getProfile(player.getUniqueId(), false);

        if (profile != null) {
            profileHandler.save(profile);
            profileHandler.removeEntry(player.getUniqueId());
        }

    }

}
