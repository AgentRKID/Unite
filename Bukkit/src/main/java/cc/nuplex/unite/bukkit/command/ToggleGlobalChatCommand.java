package cc.nuplex.unite.bukkit.command;

import cc.nuplex.engine.command.Command;
import cc.nuplex.engine.util.bukkit.CC;
import cc.nuplex.unite.Unite;
import cc.nuplex.unite.bukkit.setting.UniteSettings;
import cc.nuplex.unite.profile.Profile;
import org.bukkit.entity.Player;

public class ToggleGlobalChatCommand {

    @Command(names={"toggle global chat", "togglegc", "tgc", "tglobalchat"}, permission="", async=true)
    public static void execute(Player sender) {
        Profile profile = Unite.getInstance().getProfileHandler().get(sender.getUniqueId(), false);

        profile.saveSetting(UniteSettings.HIDE_GLOBAL_CHAT, !profile.isSettingEnabled(UniteSettings.HIDE_GLOBAL_CHAT));
        sender.sendMessage(CC.translate("&eYou have " + (profile.isSettingEnabled(UniteSettings.HIDE_GLOBAL_CHAT) ? "&cdisabled" : "&aenabled") + " &eGlobal chat."));
    }

}
