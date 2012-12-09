package com.modcrafting.diablodrops.listeners;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.server.EntityItem;
import net.minecraft.server.EntityLiving;
import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.NBTTagFloat;
import net.minecraft.server.NBTTagList;

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
            EntityLiving el = ((CraftLivingEntity) event.getEntity()).getHandle();
            net.minecraft.server.ItemStack[] mItem = el.getEquipment();
            EntityDropItemEvent edie = new EntityDropItemEvent(
                    event.getEntity());
            plugin.getServer().getPluginManager().callEvent(edie);
            if (edie.isCancelled())
            	for (int i=0;i<mItem.length;i++)
            	{
            		el.setEquipment(i, new CraftItemStack(Material.AIR).getHandle());
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
            List<CraftItemStack> items = new ArrayList<CraftItemStack>();
            for(int i=0;i<plugin.gen.nextInt(5)+1;i++)
            {
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
    
    @SuppressWarnings("unused")
	private void dropItem(net.minecraft.server.ItemStack mItem, Location loc)
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
        NBTTagCompound nbt = new NBTTagCompound();
        ev.b(nbt);
        if (nbt.hasKey("DropChances")) {
            NBTTagList nbttaglist = new NBTTagList();
            for (int j = 0; j < 5; j++) {
            	nbttaglist.add(new NBTTagFloat(j + "", 2.0F));
            }
            nbt.set("DropChances", nbttaglist);
            ev.a(nbt);
        }
    }

}
