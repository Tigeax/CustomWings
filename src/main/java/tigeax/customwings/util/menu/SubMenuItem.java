package tigeax.customwings.util.menu;

/**
 * A {@link MenuItem} that opens a sub {@link ItemMenu}.
 */
public class SubMenuItem extends MenuItem {

    private final ItemMenu subMenu;

    public SubMenuItem(ItemMenu subMenu) {
        this.subMenu = subMenu;
    }

    @Override
    public void onItemClick(ItemClickEvent event) {
        subMenu.open(event.getPlayer());
    }
}
