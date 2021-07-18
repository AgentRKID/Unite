package cc.nuplex.unite.runnable;

import cc.nuplex.engine.util.http.HttpHandler;
import cc.nuplex.unite.UniteGeneral;

public class KeepAliveRunnable implements Runnable {

    private int keepAliveId = 0;

    @Override
    public void run() {
        HttpHandler.get(UniteGeneral.getPlugin().getApiHost() + "/status", null, (response, code) -> {
            if (response == null || response.isJsonNull()) {
                UniteGeneral.getPlugin().shutdown();
                return;
            }

            if (!response.getAsJsonObject().get("success").getAsBoolean()) {
                UniteGeneral.getPlugin().shutdown();
                return;
            }
            UniteGeneral.getPlugin().onKeepAlive(keepAliveId++);
            UniteGeneral.getPlugin().getLogger().info("API Server is still up and alive!");
        });
    }

}
