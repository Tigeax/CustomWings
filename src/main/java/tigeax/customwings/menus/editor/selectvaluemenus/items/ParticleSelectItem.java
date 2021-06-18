package tigeax.customwings.menus.editor.selectvaluemenus.items;

import tigeax.customwings.menus.editor.selectvaluemenus.ParticleSelectMenuPage;
import tigeax.customwings.util.ParticleItem;
import tigeax.customwings.util.menu.ItemClickEvent;
import tigeax.customwings.util.menu.ItemMenu;
import tigeax.customwings.util.menu.MenuItem;

public class ParticleSelectItem extends MenuItem {

    ParticleItem particleItem;

    public ParticleSelectItem(ParticleItem particleItem) {
        this.particleItem = particleItem;

        setDisplayName("&f" + particleItem.getName());
        setMaterial(particleItem.getMaterial());
    }

    @Override
    public void onItemClick(ItemClickEvent event) {

        ItemMenu itemMenu = event.getItemMenu();

        if (!(itemMenu instanceof ParticleSelectMenuPage)) {
            event.setWillClose(true);
            return;
        }

        ParticleSelectMenuPage menu = (ParticleSelectMenuPage) itemMenu;

        menu.getSetting().setValue(particleItem.toString());
    }
}
