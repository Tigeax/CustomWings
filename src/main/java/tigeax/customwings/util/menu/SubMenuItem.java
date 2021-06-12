package tigeax.customwings.util.menu;

/**
 * A {@link MenuItem} that opens a sub {@link ItemMenu}.
 */
public class SubMenuItem extends MenuItem {

    private final ItemMenu menu;

    public SubMenuItem(ItemMenu menu) {
        this.menu = menu;
    }

    @Override
    public void onItemClick(ItemClickEvent event) {
        menu.open(event.getPlayer());
    }
}
