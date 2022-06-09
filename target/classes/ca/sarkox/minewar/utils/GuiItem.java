package ca.sarkox.minewar.utils;

import org.bukkit.NamespacedKey;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Consumer;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GuiItem {

        public static final NamespacedKey KEY_UUID = new NamespacedKey(JavaPlugin.getProvidingPlugin(GuiItem.class), "IF-uuid");


        @Nullable
        private Consumer<InventoryClickEvent> action;

        @NotNull
        private List<Object> properties;


        @NotNull
        private final ItemStack item;


        private boolean visible;


        @NotNull
        private UUID uuid = UUID.randomUUID();


        public GuiItem(@NotNull ItemStack item, @Nullable Consumer<InventoryClickEvent> action) {
            this.action = action;
            this.visible = true;
            this.properties = new ArrayList<>();
            this.item = item;

            //remove this call after the removal of InventoryComponent#setItem(ItemStack, int, int)
            applyUUID();
        }


        public GuiItem(@NotNull ItemStack item) {
            this(item, null);
        }

        @NotNull
        @Contract(pure = true)
        public GuiItem copy() {
            GuiItem guiItem = new GuiItem(item.clone(), action);

            guiItem.visible = visible;
            guiItem.uuid = uuid;
            guiItem.properties = new ArrayList<>(properties);
            ItemMeta meta = guiItem.item.getItemMeta();

            if (meta == null) {
                throw new IllegalArgumentException("item must be able to have ItemMeta (it mustn't be AIR)");
            }

            meta.getPersistentDataContainer().set(KEY_UUID, UUIDTagType.INSTANCE, guiItem.uuid);
            guiItem.item.setItemMeta(meta);

            return guiItem;
        }


        public void callAction(@NotNull InventoryClickEvent event) {
            if (action == null) {
                return;
            }

            try {
                action.accept(event);
            } catch (Throwable t) {
                Logger logger = JavaPlugin.getProvidingPlugin(getClass()).getLogger();
                logger.log(Level.SEVERE, "Exception while handling click event in inventory '"
                        + event.getView().getTitle() + "', slot=" + event.getSlot() + ", item=" + item.getType(), t);
            }
        }


        public void applyUUID() {
            ItemMeta meta = item.getItemMeta();

            if (meta != null) {
                meta.getPersistentDataContainer().set(KEY_UUID, UUIDTagType.INSTANCE, uuid);
                item.setItemMeta(meta);
            }
        }


        public void setAction(@NotNull Consumer<InventoryClickEvent> action) {
            this.action = action;
        }

        @NotNull
        @Contract(pure = true)
        public List<Object> getProperties(){
            return properties;
        }


        public void setProperties(@NotNull List<Object> properties){
            this.properties = properties;
        }


        @NotNull
        @Contract(pure = true)
        public ItemStack getItem() {
            return item;
        }


        @NotNull
        @Contract(pure = true)
        public UUID getUUID() {
            return uuid;
        }


        public boolean isVisible() {
            return visible;
        }

        public void setVisible(boolean visible) {
            this.visible = visible;
        }
}
