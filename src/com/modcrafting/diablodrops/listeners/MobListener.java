package com.modcrafting.diablodrops.listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityDeathEvent;

import com.modcrafting.diablodrops.DiabloDrops;
import com.modcrafting.diablodrops.events.EntityDropItemEvent;
import com.modcrafting.diablodrops.events.EntitySpawnWithItemEvent;
import com.modcrafting.diablolibrary.entities.DiabloLivingEntity;
import com.modcrafting.diablolibrary.entities.DiabloLivingEntity.EntityEquipment;
import com.modcrafting.diablolibrary.items.DiabloItemStack;

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
            Location loc = event.getEntity().getLocation();
            if (!plugin.worlds.contains(loc.getWorld().getName())
                    && plugin.config.getBoolean("Worlds.Enabled", false))
                return;
            DiabloLivingEntity el = new DiabloLivingEntity(event.getEntity());
            EntityDropItemEvent edie = new EntityDropItemEvent(el);
            plugin.getServer().getPluginManager().callEvent(edie);
            if (edie.isCancelled())
                for (EntityEquipment e : EntityEquipment.values())
                {
                    el.setEquipment(e, new DiabloItemStack(Material.AIR));
                }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onSpawn(final CreatureSpawnEvent event)
    {
        LivingEntity entity = event.getEntity();
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
            for (int i = 0; i < plugin.gen.nextInt(5) + 1; i++)
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
            DiabloLivingEntity de = new DiabloLivingEntity(entity);
            EntitySpawnWithItemEvent eswi = new EntitySpawnWithItemEvent(entity);
            plugin.getServer().getPluginManager().callEvent(eswi);
            if (eswi.isCancelled())
                return;
            for (DiabloItemStack cis : eswi.getItems())
            {
                de.setEquipment(cis);
            }
        }
    }
}
