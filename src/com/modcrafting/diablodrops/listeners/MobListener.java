package com.modcrafting.diablodrops.listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import com.modcrafting.diablodrops.DiabloDrops;
import com.modcrafting.diablodrops.drops.DropsAPI;
import com.modcrafting.diablodrops.events.EntitySpawnEvent;
import com.modcrafting.diablodrops.events.EntitySpawnWithItemEvent;
import com.modcrafting.diablodrops.items.DiabloDropsItem;
import com.modcrafting.diablodrops.items.Drop;

public class MobListener implements Listener
{
    private final DiabloDrops plugin;

    public MobListener(final DiabloDrops instance)
    {
        plugin = instance;
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onSpawn(final CreatureSpawnEvent event)
    {
        LivingEntity entity = event.getEntity();
        if (!(entity instanceof Monster))
            return;
        if ((plugin.worlds.size() > 0)
                && plugin.getConfig().getBoolean("Worlds.Enabled", false)
                && !plugin.worlds.contains(entity.getLocation().getWorld()
                        .getName().toLowerCase()))
            return;
        if (plugin.getConfig().getBoolean("Reason.Spawner", true)
                && event.getSpawnReason().equals(SpawnReason.SPAWNER))
            return;
        if (plugin.getConfig().getBoolean("Reason.Egg", true)
                && (event.getSpawnReason().equals(SpawnReason.EGG) || event
                        .getSpawnReason().equals(SpawnReason.SPAWNER_EGG)))
            return;
        EntitySpawnEvent ese = new EntitySpawnEvent(entity, plugin
                .getSingleRandom().nextInt(100) + 1);
        plugin.getServer().getPluginManager().callEvent(ese);
        if ((entity instanceof Monster)
                && (plugin.getSettings().getStandardChance() >= ese.getChance()))
        {
            List<DiabloDropsItem> items = new ArrayList<DiabloDropsItem>();
            for (int i = 0; i < (plugin.getSingleRandom().nextInt(5) + 1); i++)
            {
                DiabloDropsItem ci = plugin.getDropAPI().getItem();
                while (ci == null)
                {
                    ci = plugin.getDropAPI().getItem();
                }
                if (plugin.getConfig().getBoolean("Custom.Only", false)
                        && plugin.getConfig()
                                .getBoolean("Custom.Enabled", true))
                {
                    ci = new DiabloDropsItem((Drop) plugin.custom.get(plugin
                            .getSingleRandom().nextInt(plugin.custom.size())),
                            DropsAPI.CUSTOMTIER, DropsAPI.CUSTOMRARITY);
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
            for (DiabloDropsItem cis : eswi.getItems())
            {
                if (cis != null)
                {
                    float dropChance = cis.getDropChance();
                    // if (true)
                    // {
                    // Tier tier = plugin.getDropAPI().getTier(cis);
                    // if (tier != null)
                    // dropChance = (tier.getDropChance() * 0.01F);
                    // }
                    if (plugin.getItemAPI().isHelmet(
                            cis.getItemStack().getType())
                            || cis.getItemStack().getType()
                                    .equals(Material.SKULL_ITEM))
                    {
                        entity.getEquipment().setHelmet(cis.getItemStack());
                        entity.getEquipment().setHelmetDropChance(dropChance);
                    }
                    else if (plugin.getItemAPI().isChestPlate(
                            cis.getItemStack().getType()))
                    {
                        entity.getEquipment().setChestplate(cis.getItemStack());
                        entity.getEquipment().setChestplateDropChance(
                                dropChance);
                    }
                    else if (plugin.getItemAPI().isLeggings(
                            cis.getItemStack().getType()))
                    {
                        entity.getEquipment().setLeggings(cis.getItemStack());
                        entity.getEquipment().setLeggingsDropChance(dropChance);
                    }
                    else if (plugin.getItemAPI().isBoots(
                            cis.getItemStack().getType()))
                    {
                        entity.getEquipment().setBoots(cis.getItemStack());
                        entity.getEquipment().setBootsDropChance(dropChance);
                    }
                    else
                    {
                        entity.getEquipment().setItemInHand(cis.getItemStack());
                        entity.getEquipment().setItemInHandDropChance(
                                dropChance);
                    }
                }
            }
        }
    }
}
