package cc.nuplex.unite.bukkit;

import cc.nuplex.engine.Engine;
import cc.nuplex.engine.command.CommandHandler;
import cc.nuplex.engine.storage.cache.uuid.UUIDCache;
import cc.nuplex.engine.storage.mongo.Mongo;
import cc.nuplex.unite.Unite;
import cc.nuplex.unite.bukkit.event.ApiKeepAlivePostEvent;
import cc.nuplex.unite.bukkit.listener.ConnectionListener;

import cc.nuplex.unite.bukkit.listener.PlayerListener;
import cc.nuplex.unite.runnable.KeepAliveRunnable;
import cc.nuplex.unite.plugin.Plugin;
import cc.nuplex.unite.runnable.RefreshRunnable;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class UniteBukkitPlugin extends JavaPlugin implements Plugin {

    @Getter private static UniteBukkitPlugin instance;

    @Override
    public void onEnable() {
        instance = this;

        new Unite(this);

        Bukkit.getScheduler().runTaskTimerAsynchronously(this, new KeepAliveRunnable(), 0, 20  * 5);
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, new RefreshRunnable(), 0, 20 * 60);

        CommandHandler.registerAll(this);

        Bukkit.getPluginManager().registerEvents(new ConnectionListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
    }

    @Override
    public void onDisable() { }

    @Override
    public Mongo getMongo() {
        return Engine.getInstance().getMongo();
    }

    @Override
    public UUIDCache getUUIDCache() {
        return Engine.getInstance().getUuidCache();
    }

    @Override
    public String getApiHost() {
        return getConfig().getString("settings.api-host");
    }

    @Override
    public void onKeepAlive(int keepAliveId) {
        new ApiKeepAlivePostEvent(keepAliveId == 0).call();
    }

    @Override
    public void shutdown() {
        Bukkit.shutdown();
    }

}
