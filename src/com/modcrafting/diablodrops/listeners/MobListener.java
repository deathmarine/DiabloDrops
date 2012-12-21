package com.modcrafting.diablodrops.listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import com.modcrafting.diablodrops.DiabloDrops;
import com.modcrafting.diablodrops.events.EntityDropItemEvent;
import com.modcrafting.diablodrops.events.EntitySpawnWithItemEvent;

public class MobListener implements Listener
{
    private final DiabloDrops plugin;

    public MobListener(final DiabloDrops instance)
    {
        plugin = instance;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onEntityDeath(final EntityDeathEvent event)
    {
        if (event.getEntity() instanceof Monster)
        {
            Monster monster = (Monster) event.getEntity();
            Location loc = monster.getLocation();
            if (!plugin.worlds.contains(loc.getWorld().getName())
                    && plugin.config.getBoolean("Worlds.Enabled", false))
                return;

            EntityDropItemEvent edie = new EntityDropItemEvent(
                    event.getEntity());
            plugin.getServer().getPluginManager().callEvent(edie);
            if (edie.isCancelled())
            {
                event.getDrops().clear();
            }
            else
            {
                monster.getEquipment().setBootsDropChance(2.0F);
                monster.getEquipment().setChestplateDropChance(2.0F);
                monster.getEquipment().setLeggingsDropChance(2.0F);
                monster.getEquipment().setHelmetDropChance(2.0F);
                monster.getEquipment().setItemInHandDropChance(2.0F);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onSpawn(final CreatureSpawnEvent event)
    {
        LivingEntity entity = event.getEntity();
        if (!(entity instanceof Monster))
            return;
        if ((plugin.worlds.size() > 0)
                && plugin.config.getBoolean("Worlds.Enabled", false)
                && !plugin.worlds.contains(entity.getLocation().getWorld()
                        .getName().toLowerCase()))
            return;
        if (plugin.config.getBoolean("Reason.Spawner", true)
                && event.getSpawnReason().equals(SpawnReason.SPAWNER))
            return;
        if (plugin.config.getBoolean("Reason.Egg", true)
                && (event.getSpawnReason().equals(SpawnReason.EGG) || event
                        .getSpawnReason().equals(SpawnReason.SPAWNER_EGG)))
            return;
        Integer random = plugin.gen.nextInt(100) + 1;
        if ((entity instanceof Monster)
                && (plugin.config.getInt("Percentages.ChancePerSpawn", 9) >= random))
        {
            List<ItemStack> items = new ArrayList<ItemStack>();
            for (int i = 0; i < (plugin.gen.nextInt(5) + 1); i++)
            {
                ItemStack ci = plugin.dropsAPI.getItem();
                while (ci == null)
                {
                    ci = plugin.dropsAPI.getItem();
                }
                if (plugin.config.getBoolean("Custom.Only", false)
                        && plugin.config.getBoolean("Custom.Enabled", true))
                {
                    ci = plugin.custom.get(plugin.gen.nextInt(plugin.custom
                            .size()));
                }
                if (ci != null)
                {
                    items.add(ci);
                }
            }
            EntitySpawnWithItemEvent eswi = new EntitySpawnWithItemEvent(
                    entity, items);
            plugin.getServer().getPluginManager().callEvent(eswi);
            if (eswi.isCancelled())
                return;

            for (ItemStack cis : eswi.getItems())
                if (cis != null)
                {
                    if (plugin.drop.isHelmet(cis.getType()))
                    {
                        entity.getEquipment().setHelmet(cis);
                        entity.getEquipment().setHelmetDropChance(2.0F);
                    }
                    else if (plugin.drop.isChestPlate(cis.getType()))
                    {
                        entity.getEquipment().setChestplate(cis);
                        entity.getEquipment().setChestplateDropChance(2.0F);
                    }
                    else if (plugin.drop.isLeggings(cis.getType()))
                    {
                        entity.getEquipment().setLeggings(cis);
                        entity.getEquipment().setLeggingsDropChance(2.0F);
                    }
                    else if (plugin.drop.isBoots(cis.getType()))
                    {
                        entity.getEquipment().setBoots(cis);
                        entity.getEquipment().setLeggingsDropChance(2.0F);
                    }
                    else
                    {
                        entity.getEquipment().setItemInHand(cis);
                        entity.getEquipment().setItemInHandDropChance(2.0F);
                    }
                }
        }
    }
}
