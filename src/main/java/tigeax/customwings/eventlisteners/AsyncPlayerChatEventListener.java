package tigeax.customwings.eventlisteners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import tigeax.customwings.editor.EditorConfigManager;
import tigeax.customwings.editor.SettingType;
import tigeax.customwings.CWPlayer;
import tigeax.customwings.CustomWings;

/*
 * This EventListener gets the value from chat when we are waiting from a chat input from a player
 * Which is needed as an input to change a setting
 */

public class AsyncPlayerChatEventListener implements Listener {

	CustomWings plugin;

	public AsyncPlayerChatEventListener() {
		plugin = CustomWings.getInstance();
	}

	@EventHandler
	public void event(AsyncPlayerChatEvent event) {

		Player player = event.getPlayer();
		CWPlayer cwPlayer = plugin.getCWPlayer(player);
		SettingType setting = cwPlayer.getWaitingSetting();
		Object settingInfo = cwPlayer.getWaitingSettingInfo();
		
		if (setting == null) return;

		if (!setting.isChatInputSetting()) return;

		event.setCancelled(true);

		EditorConfigManager editorConfigManager = plugin.getEditorConfigManager();
		String value = event.getMessage();

		editorConfigManager.setSetting(setting, value, settingInfo);
		cwPlayer.setWaitingSetting(null);
		cwPlayer.sendMessage(plugin.getMessages().settingChanged());
	}
}
