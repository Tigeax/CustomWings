package tigeax.customwings.gui.guis;

import org.bukkit.Material;
import tigeax.customwings.gui.CWGUIManager;
import tigeax.customwings.gui.CWGUIType;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import tigeax.customwings.CWPlayer;
import tigeax.customwings.CustomWings;
import tigeax.customwings.Settings;
import tigeax.customwings.wings.Wing;
import tigeax.customwings.wings.WingParticle;

public class EditorWingParticlesSelect {
	
	Settings settings;

	public EditorWingParticlesSelect() {
		settings = CustomWings.getSettings();
	}

	public void open(CWPlayer cwPlayer, Wing wing) {

		String guiName = settings.getEditorGUIName() + " - Wing Particles";

		Inventory gui = Bukkit.createInventory(null, 54, guiName);

		gui.setItem(4, CWGUIManager.getItem(wing.getGuiItem().getType(), "&f" + wing.getID()));
		gui.setItem(53, CWGUIManager.getItem(Material.WHITE_BED, "&4Previous page"));

		int slot = 9;

		for (WingParticle wingParticle : wing.getWingParticles()) {
			gui.setItem(slot, CWGUIManager.getItem(wingParticle.getItemMaterial(), "&f" + wingParticle.getID()));
			slot++;
		}

		cwPlayer.getPlayer().openInventory(gui);
	}

	public void click(CWPlayer cwPlayer, String itemName, Wing wing) {

		if (itemName.equals("Previous page")) {
			cwPlayer.openCWGUI(CWGUIType.EDITORWINGSETTINGS, wing);
			return;
		}

		WingParticle wingParticle = wing.getWingParticleByID(itemName);
		cwPlayer.openCWGUI(CWGUIType.EDITORWINGPARTICLESETTINGS, wingParticle);
	}
	
}
