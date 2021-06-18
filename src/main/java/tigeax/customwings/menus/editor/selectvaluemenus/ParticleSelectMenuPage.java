package tigeax.customwings.menus.editor.selectvaluemenus;

import org.bukkit.entity.Player;

import tigeax.customwings.CustomWings;
import tigeax.customwings.configuration.settings.Setting;
import tigeax.customwings.menus.editor.selectvaluemenus.items.ParticleSelectItem;
import tigeax.customwings.menus.items.GoBackItem;
import tigeax.customwings.menus.items.NextPageItem;
import tigeax.customwings.menus.items.PreviousPageItem;
import tigeax.customwings.util.ParticleItem;
import tigeax.customwings.util.menu.ItemMenu;

public class ParticleSelectMenuPage extends ItemMenu {

	private Setting setting;
	private int page;
	private ItemMenu parent;

	private int particlesPerPage = 45;
	private int numParticles = ParticleItem.values().length;
	private int totalPages = (int) Math.ceil((numParticles * 1.0) / (particlesPerPage * 1.0)); // Mutlpy by 1.0 to convert to doubles

	public ParticleSelectMenuPage(ItemMenu parent, Setting setting, int page) {
		super("&cSelect particle (" + page + ")", Rows.SIX);

		this.setting = setting;
		this.page = page;
		this.parent = parent;

		CustomWings plugin = CustomWings.getInstance();

		setParent(parent);

		int particleIndexStart = page * (particlesPerPage - 1) - (particlesPerPage - 1);

		for (int i = 0; i < particlesPerPage && particleIndexStart + i < numParticles; i++) {
			try {
				setItem(i, new ParticleSelectItem(ParticleItem.values()[particleIndexStart + i]));
			} catch (Exception e) {
				continue;
			}
		}

		if (page > 1) {
			setItem(plugin.getConfig().getNavigationPreviousSlot(), new PreviousPageItem());
		}

		if (page < totalPages) {
			setItem(plugin.getConfig().getNavigationNextSlot(), new NextPageItem());
		}

		setItem(53, new GoBackItem());

	}

	@Override
	public void openPreviousPage(Player player) {
		if (page > 1) {
			new ParticleSelectMenuPage(parent, setting, page - 1).open(player);
		}
	}

	@Override
	public void openNextPage(Player player) {
		if (page < totalPages) {
			new ParticleSelectMenuPage(parent, setting, page + 1).open(player);
		}
	}

	public Setting getSetting() {
		return setting;
	}

}
