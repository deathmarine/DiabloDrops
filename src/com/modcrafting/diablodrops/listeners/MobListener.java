package com.modcrafting.diablodrops.listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Monster;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import com.modcrafting.diablodrops.DiabloDrops;
import com.modcrafting.diablodrops.events.EntityDropItemEvent;
import com.modcrafting.diablodrops.events.EntitySpawnWithItemEvent;
import com.modcrafting.diablolibrary.entities.DiabloLivingEntity;
import com.modcrafting.diablolibrary.entities.DiabloLivingEntity.EntityEquipment;
import com.modcrafting.diablolibrary.events.DiabloLivingEntityDeathEvent;
import com.modcrafting.diablolibrary.events.DiabloLivingEntitySpawnEvent;
import com.modcrafting.diablolibrary.items.DiabloItemStack;

public class MobListener implements Listener
{
    private final DiabloDrops plugin;

    public MobListener(final DiabloDrops instance)
    {
        plugin = instance;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onEntityDeath(final DiabloLivingEntityDeathEvent event)
    {
        if (event.getDiabloLivingEntity() instanceof Monster)
        {
            Location loc = event.getDiabloLivingEntity().getLocation();
            if (!plugin.worlds.contains(loc.getWorld().getName())
                    && plugin.config.getBoolean("Worlds.Enabled", false))
                return;
            EntityDropItemEvent edie = new EntityDropItemEvent(
                    event.getDiabloLivingEntity());
            plugin.getServer().getPluginManager().callEvent(edie);
            if (edie.isCancelled())
            {
                for (EntityEquipment e : EntityEquipment.values())
                {
                    event.getDiabloLivingEntity().setEquipment(e,
                            new DiabloItemStack(Material.AIR));
                }
            }
            else
            {
                event.getDiabloLivingEntity().dropEquipment();
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onSpawn(final DiabloLivingEntitySpawnEvent event)
    {
        DiabloLivingEntity entity = event.getDiabloLivingEntity();
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
                && (plugin.config.getInt("Precentages.ChancePerSpawn", 9) >= random))
        {
            List<DiabloItemStack> items = new ArrayList<DiabloItemStack>();
            for (int i = 0; i < (plugin.gen.nextInt(5) + 1); i++)
            {
                DiabloItemStack ci = plugin.dropsAPI.getItem();
                while (ci == null)
                {
                    ci = plugin.dropsAPI.getItem();
                }
                if (plugin.config.getBoolean("Custom.Only", false))
                {
                    ci = plugin.custom.get(plugin.gen.nextInt(plugin.custom
                            .size()));
                }
                if (ci != null)
                {
                    items.add(ci);
                }
            }
            EntitySpawnWithItemEvent eswi = new EntitySpawnWithItemEvent(entity);
            plugin.getServer().getPluginManager().callEvent(eswi);
            if (eswi.isCancelled())
                return;
            for (DiabloItemStack cis : eswi.getItems())
            {
                entity.setEquipment(cis);
            }
        }
    }
}
