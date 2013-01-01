package com.modcrafting.diablodrops.listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import com.modcrafting.diablodrops.DiabloDrops;
import com.modcrafting.diablodrops.events.EntitySpawnEvent;
import com.modcrafting.diablodrops.events.EntitySpawnWithItemEvent;

public class MobListener implements Listener
{
    private final DiabloDrops plugin;

    public MobListener(final DiabloDrops instance)
    {
        plugin = instance;
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onDeath(final EntityDeathEvent event)
    {
        if (!(event.getEntity() instanceof LivingEntity))
            return;
        LivingEntity entity = event.getEntity();
        if (entity instanceof Player)
            return;
        for (ItemStack cis : entity.getEquipment().getArmorContents())
        {
            float dropChance = 0.0F + plugin.getSingleRandom().nextFloat()
                    * (2.0F - 0.0F);
            if (cis != null)
            {
                if (plugin.getItemAPI().isHelmet(cis.getType()))
                {
                    entity.getEquipment().setHelmet(cis);
                    entity.getEquipment().setHelmetDropChance(dropChance);
                }
                else if (plugin.getItemAPI().isChestPlate(cis.getType()))
                {
                    entity.getEquipment().setChestplate(cis);
                    entity.getEquipment().setChestplateDropChance(dropChance);
                }
                else if (plugin.getItemAPI().isLeggings(cis.getType()))
                {
                    entity.getEquipment().setLeggings(cis);
                    entity.getEquipment().setLeggingsDropChance(dropChance);
                }
                else if (plugin.getItemAPI().isBoots(cis.getType()))
                {
                    entity.getEquipment().setBoots(cis);
                    entity.getEquipment().setLeggingsDropChance(dropChance);
                }
                else
                {
                    entity.getEquipment().setItemInHand(cis);
                    entity.getEquipment().setItemInHandDropChance(dropChance);
                }
            }
        }
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
                && (plugin.getConfig().getInt("Percentages.ChancePerSpawn", 3) >= ese
                        .getChance()))
        {
            List<ItemStack> items = new ArrayList<ItemStack>();
            for (int i = 0; i < (plugin.getSingleRandom().nextInt(5) + 1); i++)
            {
                ItemStack ci = plugin.getDropAPI().getItem();
                while (ci == null)
                {
                    ci = plugin.getDropAPI().getItem();
                }
                if (plugin.getConfig().getBoolean("Custom.Only", false)
                        && plugin.getConfig()
                                .getBoolean("Custom.Enabled", true))
                {
                    ci = plugin.custom.get(plugin.getSingleRandom().nextInt(
                            plugin.custom.size()));
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
            {
                if (cis != null)
                {
                    if (plugin.getItemAPI().isHelmet(cis.getType()))
                    {
                        entity.getEquipment().setHelmet(cis);
                    }
                    else if (plugin.getItemAPI().isChestPlate(cis.getType()))
                    {
                        entity.getEquipment().setChestplate(cis);
                    }
                    else if (plugin.getItemAPI().isLeggings(cis.getType()))
                    {
                        entity.getEquipment().setLeggings(cis);
                    }
                    else if (plugin.getItemAPI().isBoots(cis.getType()))
                    {
                        entity.getEquipment().setBoots(cis);
                    }
                    else
                    {
                        entity.getEquipment().setItemInHand(cis);
                    }
                }
            }
        }
    }
}
