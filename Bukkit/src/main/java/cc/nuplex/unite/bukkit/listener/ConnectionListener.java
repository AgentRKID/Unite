package cc.nuplex.unite.bukkit.listener;

import cc.nuplex.engine.util.bukkit.CC;
import cc.nuplex.unite.Unite;
import cc.nuplex.unite.profile.Profile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ConnectionListener implements Listener {

    @EventHandler(priority=EventPriority.LOWEST)
    public void onAsyncPlayerPreLogin(AsyncPlayerPreLoginEvent event) {
        Profile profile = new Profile(event.getUniqueId(), event.getName());

        Unite.getInstance().getProfileHandler().load(profile);

        // API Might be down, or their
        // profile is messed up
        if (!profile.wasLastUpdateSuccessful()) {
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, CC.translate("&cThere was an error while trying to load your profile, please try again."));
            return;
        }
        Unite.getInstance().getProfileHandler().addEntry(profile);
    }

    @EventHandler(priority=EventPriority.LOWEST)
    public void onPlayerQuit(PlayerQuitEvent event) {
        Profile profile = Unite.getInstance().getProfileHandler().get(event.getPlayer().getUniqueId(), false);

        if (profile != null) {
            Unite.getInstance().getProfileHandler().removeEntry(profile);
        }
    }

}
