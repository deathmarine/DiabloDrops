package com.modcrafting.diablodrops.effects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.modcrafting.diablodrops.DiabloDrops;
import com.modcrafting.toolapi.lib.Tool;

public class EffectsAPI
{
    public static void addEffect(LivingEntity struck, LivingEntity striker,
            String string, EntityDamageEvent event)
    {

        String[] args = string.split(" ");
        if (args.length <= 1)
            return;
        Integer level = null;
        try
        {
            level = Integer.valueOf(args[0]);
        }
        catch (NumberFormatException e)
        {
            if (DiabloDrops.getInstance().debug)
                DiabloDrops.getInstance().log.warning(e.getMessage());
            level = 0;
        }
        if (args[1].equalsIgnoreCase("attack"))
        {
            // Add to strike damage
            int damage = event.getDamage() + level;
            if (damage >= 0)
            {
                event.setDamage(damage);
            }
            else
            {
                event.setDamage(0);
            }
            return;
        }
        else if (args[1].equalsIgnoreCase("defense"))
        {
            int damage = event.getDamage() - level;
            if (damage >= 0)
            {
                event.setDamage(damage);
            }
            else
            {
                event.setDamage(0);
            }
            return;
        }
        else if (args[1].equalsIgnoreCase("freeze"))
        {
            // freeze entities
            float fl;
            try
            {
                fl = Float.parseFloat(args[0]);
            }
            catch (NumberFormatException e)
            {
                if (DiabloDrops.getInstance().debug)
                    DiabloDrops.getInstance().log.warning(e.getMessage());
                return;
            }
            if (fl > 0 && struck instanceof Monster)
                EffectsUtil.speed(struck, Math.abs(fl) / 500);
            else if (fl < 0 && striker instanceof Monster)
                EffectsUtil.speed(striker, Math.abs(fl) / 500);
            return;
        }
        else if (args[1].equalsIgnoreCase("shrink") && struck != null)
        {
            // turn into baby
            EffectsUtil.makeBaby(struck);
            return;
        }
        else if (args[1].equalsIgnoreCase("lightning"))
        {
            // strike lightning
            if (level > 0 && struck != null)
                EffectsUtil.strikeLightning(struck.getLocation(),
                        Math.abs(level));
            else if (level < 0 && striker != null)
                EffectsUtil.strikeLightning(striker.getLocation(),
                        Math.abs(level));
            return;
        }
        else if (args[1].equalsIgnoreCase("fire"))
        {
            // Set entity on fire
            if (level > 0 && struck != null)
                EffectsUtil.setOnFire(struck, Math.abs(level));
            else if (level < 0 && striker != null)
                EffectsUtil.setOnFire(striker, Math.abs(level));
            return;
        }
        else if (args[1].equalsIgnoreCase("entomb"))
        {
            if (level > 0 && struck != null)
                EffectsUtil.entomb(struck.getLocation(), Math.abs(level));
            else if (level < 0 && striker != null)
                EffectsUtil.entomb(striker.getLocation(), Math.abs(level));
            return;
        }
        else if (args[1].equalsIgnoreCase("leech") && striker != null
                && struck != null)
        {
            if (level > 0)
            {
                int chng = level - struck.getHealth();
                if (chng < struck.getMaxHealth() && chng > 0)
                    struck.setHealth(chng);
                chng = level + striker.getHealth();
                if (chng < striker.getMaxHealth() && chng > 0)
                    striker.setHealth(chng);
            }
            else if (level < 0)
            {
                int chng = Math.abs(level) + struck.getHealth();
                if (chng < struck.getMaxHealth() && chng > 0)
                    struck.setHealth(chng);
                chng = Math.abs(level) - striker.getHealth();
                if (chng < striker.getMaxHealth() && chng > 0)
                    striker.setHealth(chng);
            }
            return;
        }
        else
        {
            for (PotionEffectType potionEffect : PotionEffectType.values())
            {
                if (potionEffect != null
                        && potionEffect.getName().equalsIgnoreCase(args[1]))
                {
                    if (level > 0 && struck != null)
                    {
                        struck.addPotionEffect(new PotionEffect(potionEffect,
                                Math.abs(level) * 100, Math.abs(level) - 1));
                    }
                    else if (level < 0 && striker != null)
                    {
                        striker.addPotionEffect(new PotionEffect(potionEffect,
                                Math.abs(level) * 100, Math.abs(level) - 1));
                    }

                }
            }
            return;
        }
    }

    /**
     * Handles any effects caused by an EntityDamageEvent
     * 
     * @param entityStruck Entity
     *            damaged by event
     * @param entityStriker Entity
     *            that caused the damage
     * @param event EntityDamageEvent that requires effects
     */
    public static void handlePluginEffects(LivingEntity entityStruck,
            LivingEntity entityStriker, EntityDamageEvent event)
    {
        if (entityStriker instanceof Player)
        {
            Player striker = (Player) entityStriker;
            List<ItemStack> strikerEquipment = new ArrayList<ItemStack>();
            strikerEquipment.add(striker.getItemInHand());
            for (String s : listEffects(strikerEquipment))
            {
                addEffect(entityStruck, entityStriker, s, event);
            }
        }
        if (entityStruck instanceof Player)
        {
            Player struck = (Player) entityStruck;
            List<ItemStack> struckEquipment = new ArrayList<ItemStack>();
            struckEquipment.addAll(Arrays.asList(struck.getInventory()
                    .getArmorContents()));
            for (String s : listEffects(struckEquipment))
            {
                addEffect(entityStriker, entityStruck, s, event);
            }
        }
    }

    public static List<String> listEffects(List<ItemStack> equipment)
    {
        Set<Tool> toolSet = new HashSet<Tool>();
        for (ItemStack is : equipment)
        {
            if (is != null && !is.getType().equals(Material.AIR))
            {
                toolSet.add(new Tool(is));
            }
        }
        List<String> effects = new ArrayList<String>();
        for (Tool tool : toolSet)
        {
            for (String string : tool.getLoreList())
            {
                string = ChatColor.stripColor(string).replace("%", "")
                        .replace("+", "");
                effects.add(string);
            }
        }
        return effects;
    }
}
