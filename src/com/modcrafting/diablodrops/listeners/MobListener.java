package com.modcrafting.diablodrops.listeners;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.server.EntityItem;
import net.minecraft.server.EntityLiving;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.entity.Entity;
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
            List<net.minecraft.server.ItemStack> list = new ArrayList<net.minecraft.server.ItemStack>();
            for (net.minecraft.server.ItemStack mItem : ((CraftLivingEntity) event
                    .getEntity()).getHandle().getEquipment())
            {
                if (mItem != null)
                {
                    list.add(mItem);
                }
            }

            EntityDropItemEvent edie = new EntityDropItemEvent(
                    event.getEntity(), list);
            plugin.getServer().getPluginManager().callEvent(edie);
            if (edie.isCancelled())
                return;
            for (net.minecraft.server.ItemStack is : edie.getDropList())
                dropItem(is,event.getEntity().getLocation());
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
            List<CraftItemStack> items = new ArrayList<CraftItemStack>();
            CraftItemStack ci = plugin.dropsAPI.getItem();
            while (ci == null)
            {
                ci = plugin.dropsAPI.getItem();
            }
            if (plugin.config.getBoolean("Custom.Only", false))
            {
                ci = plugin.custom
                        .get(plugin.gen.nextInt(plugin.custom.size()));
            }
            if (ci != null)
            {
                items.add(ci);
            }
            EntitySpawnWithItemEvent eswi = new EntitySpawnWithItemEvent(
                    entity, items);
            plugin.getServer().getPluginManager().callEvent(eswi);
            if (eswi.isCancelled())
                return;
            for (CraftItemStack cis : eswi.getItems())
            {
                setEquipment(cis, entity);
            }
        }
    }
    public void dropItem(net.minecraft.server.ItemStack mItem, Location loc)
    {

        double xs = plugin.gen.nextFloat() * 0.7F + (1.0F - 0.7F) * 0.5D;
        double ys = plugin.gen.nextFloat() * 0.7F + (1.0F - 0.7F) * 0.5D;
        double zs = plugin.gen.nextFloat() * 0.7F + (1.0F - 0.7F) * 0.5D;
        EntityItem entity = new EntityItem(
                ((CraftWorld) loc.getWorld()).getHandle(), loc.getX() + xs,
                loc.getY() + ys, loc.getZ() + zs, mItem);
        ((CraftWorld) loc.getWorld()).getHandle().addEntity(entity);
    }
    public void setEquipment(final CraftItemStack ci, final Entity e)
    {
        Material mat = ci.getType();
        EntityLiving ev = ((CraftLivingEntity) e).getHandle();
        if (plugin.drop.isBoots(mat))
        {
            ev.setEquipment(1, ci.getHandle());
        }
        else if (plugin.drop.isChestPlate(mat))
        {
            ev.setEquipment(3, ci.getHandle());
        }
        else if (plugin.drop.isLeggings(mat))
        {
            ev.setEquipment(2, ci.getHandle());
        }
        else if (plugin.drop.isHelmet(mat))
        {
            ev.setEquipment(4, ci.getHandle());
        }
        else
        {
            ev.setEquipment(0, ci.getHandle());
        }
    }

}
