package cc.nuplex.unite.bukkit.event;

import cc.nuplex.engine.util.bukkit.PluginEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ApiKeepAlivePostEvent extends PluginEvent {

    private final boolean first;

}
