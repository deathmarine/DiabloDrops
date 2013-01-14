package com.modcrafting.diablodrops.effects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.modcrafting.diablodrops.events.ItemEffectEvent;

public class EffectsAPI
{
    // Planning to use this to start localization.
    public static String ATTACK = "attack";
    public static String DEFENSE = "defense";
    public static String FREEZE = "freeze";
    public static String SHRINK = "shrink";
    public static String LIGHTNING = "lightning";
    public static String FIRE = "fire";
    public static String ENTOMB = "entomb";
    public static String LEECH = "leech";
    public static String EXPLODE = "explode";

    public static void addEffect(final LivingEntity damaged,
            final LivingEntity damager, final String s,
            final EntityDamageByEntityEvent event)
    {
        String[] args = s.replace("+", "").split(" ");
        if (args.length <= 1)
            return;
        Integer level = null;
        try
        {
            level = Integer.parseInt(args[0]);
        }
        catch (Exception e)
        {
            level = 0;
        }
        if (level == 0)
            return;
        if (args[1].equalsIgnoreCase(ATTACK))
        {
            // Add to strike damage
            int damage = event.getDamage() + level;
            event.setDamage(Math.max(damage, 0));
            return;
        }
        else if (args[1].equalsIgnoreCase(DEFENSE))
        {
            int damage = event.getDamage() - level;
            event.setDamage(Math.max(damage, 0));
            return;
        }
        else if (args[1].equalsIgnoreCase(SHRINK) && (damaged != null))
        {
            // turn into baby
            EffectsUtil.makeBaby(damaged);
            return;
        }
        else if (args[1].equalsIgnoreCase(LIGHTNING))
        {
            // strike lightning
            if ((level > 0) && (damaged != null))
            {
                EffectsUtil.strikeLightning(damaged, Math.abs(level));
            }
            else if ((level < 0) && (damager != null))
            {
                EffectsUtil.strikeLightning(damager, Math.abs(level));
            }
            return;
        }
        else if (args[1].equalsIgnoreCase(FIRE))
        {
            // Set entity on fire
            if ((level > 0) && (damaged != null))
            {
                EffectsUtil.setOnFire(damaged, Math.abs(level));
            }
            else if ((level < 0) && (damager != null))
            {
                EffectsUtil.setOnFire(damager, Math.abs(level));
            }
            return;
        }
        else if (args[1].equalsIgnoreCase(ENTOMB))
        {
            if ((level > 0) && (damaged != null))
            {
                EffectsUtil.entomb(damaged.getLocation(), Math.abs(level));
            }
            else if ((level < 0) && (damager != null))
            {
                EffectsUtil.entomb(damager.getLocation(), Math.abs(level));
            }
            return;
        }
        else if (args[1].equalsIgnoreCase(LEECH) && (damager != null)
                && (damaged != null))
        {
            if (level > 0)
            {
                int chng = damaged.getHealth() - Math.abs(level);
                damaged.setHealth(Math.max(chng, 0));
                chng = level + damager.getHealth();
                damager.setHealth(Math.max(chng, damager.getMaxHealth()));
            }
            else if (level < 0)
            {
                int chng = Math.abs(level) + damaged.getHealth();
                damager.setHealth(Math.max(chng, damager.getMaxHealth()));
                chng = damager.getHealth() - Math.abs(level);
                damaged.setHealth(Math.max(chng, 0));
            }
            return;
        }
        else if (args[1].equalsIgnoreCase(EXPLODE) && (damaged != null))
        {
            for (int i = Math.abs(level); i > 0; i--)
                EffectsUtil.playFirework(damaged.getLocation());
            return;
        }
        else
        {
            for (PotionEffectType potionEffect : PotionEffectType.values())
                if ((potionEffect != null)
                        && potionEffect.getName().equalsIgnoreCase(args[1]))
                    if ((level > 0) && (damaged != null))
                    {
                        damaged.addPotionEffect(new PotionEffect(potionEffect,
                                Math.abs(level) * 100, Math.abs(level) - 1));
                    }
                    else if ((level < 0) && (damager != null))
                    {
                        damager.addPotionEffect(new PotionEffect(potionEffect,
                                Math.abs(level) * 100, Math.abs(level) - 1));
                    }
            return;
        }
    }

    public static void addEffect(final LivingEntity struck,
            final LivingEntity striker, final String string,
            final EntityDamageEvent event)
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
            level = 0;
        }
        if (args[1].equalsIgnoreCase(ATTACK))
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
        else if (args[1].equalsIgnoreCase(DEFENSE))
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
        else if (args[1].equalsIgnoreCase(SHRINK) && (struck != null))
        {
            // turn into baby
            EffectsUtil.makeBaby(struck);
            return;
        }
        else if (args[1].equalsIgnoreCase(LIGHTNING))
        {
            // strike lightning
            if ((level > 0) && (struck != null))
            {
                EffectsUtil.strikeLightning(struck, Math.abs(level));
            }
            else if ((level < 0) && (striker != null))
            {
                EffectsUtil.strikeLightning(striker, Math.abs(level));
            }
            return;
        }
        else if (args[1].equalsIgnoreCase(FIRE))
        {
            // Set entity on fire
            if ((level > 0) && (struck != null))
            {
                EffectsUtil.setOnFire(struck, Math.abs(level));
            }
            else if ((level < 0) && (striker != null))
            {
                EffectsUtil.setOnFire(striker, Math.abs(level));
            }
            return;
        }
        else if (args[1].equalsIgnoreCase(ENTOMB))
        {
            if ((level > 0) && (struck != null))
            {
                EffectsUtil.entomb(struck.getLocation(), Math.abs(level));
            }
            else if ((level < 0) && (striker != null))
            {
                EffectsUtil.entomb(striker.getLocation(), Math.abs(level));
            }
            return;
        }
        else if (args[1].equalsIgnoreCase(LEECH) && (striker != null)
                && (struck != null))
        {
            if (level > 0)
            {
                int chng = struck.getHealth() - Math.abs(level);
                if ((chng < struck.getMaxHealth()) && (chng > 0))
                {
                    struck.setHealth(chng);
                }
                else
                {
                    struck.setHealth(0);
                }
                chng = level + striker.getHealth();
                if ((chng < striker.getMaxHealth()) && (chng > 0))
                {
                    striker.setHealth(chng);
                }
                else
                {
                    striker.setHealth(striker.getMaxHealth());
                }
            }
            else if (level < 0)
            {
                int chng = Math.abs(level) + struck.getHealth();
                if ((chng < struck.getMaxHealth()) && (chng > 0))
                {
                    striker.setHealth(chng);
                }
                else
                {
                    striker.setHealth(striker.getMaxHealth());
                }
                chng = striker.getHealth() - Math.abs(level);
                if ((chng < striker.getMaxHealth()) && (chng > 0))
                {
                    struck.setHealth(chng);
                }
                else
                {
                    struck.setHealth(0);
                }
            }
            return;
        }
        else if (args[1].equalsIgnoreCase(EXPLODE) && (struck != null))
        {
            EffectsUtil.playFirework(struck.getLocation());
        }
        else
        {
            for (PotionEffectType potionEffect : PotionEffectType.values())
                if ((potionEffect != null)
                        && potionEffect.getName().equalsIgnoreCase(args[1]))
                    if ((level > 0) && (struck != null))
                    {
                        struck.addPotionEffect(new PotionEffect(potionEffect,
                                Math.abs(level) * 100, Math.abs(level) - 1));
                    }
                    else if ((level < 0) && (striker != null))
                    {
                        striker.addPotionEffect(new PotionEffect(potionEffect,
                                Math.abs(level) * 100, Math.abs(level) - 1));
                    }
            return;
        }
    }

    public static ChatColor findColor(final String s)
    {
        char[] c = s.toCharArray();
        for (int i = 0; i < c.length; i++)
            if ((c[i] == new Character((char) 167)) && ((i + 1) < c.length))
                return ChatColor.getByChar(c[i + 1]);
        return null;
    }

    public static void handlePluginEffects(final LivingEntity damaged,
            final LivingEntity damager, final EntityDamageByEntityEvent event)
    {
        if (damager instanceof Player)
        {
            Player striker = (Player) damager;
            List<ItemStack> strikerEquipment = new ArrayList<ItemStack>();
            strikerEquipment.add(striker.getItemInHand());
            for (String s : listEffects(strikerEquipment))
            {
                ItemEffectEvent iee = new ItemEffectEvent(damaged, damager, s);
                Bukkit.getPluginManager().callEvent(iee);
                if (!iee.isCancelled())
                    addEffect(iee.getDamaged(), iee.getDamager(),
                            iee.getEffect(), event);
            }
        }
        if (damaged instanceof Player)
        {
            Player struck = (Player) damaged;
            List<ItemStack> struckEquipment = new ArrayList<ItemStack>();
            struckEquipment.addAll(Arrays.asList(struck.getInventory()
                    .getArmorContents()));
            for (String s : listEffects(struckEquipment))
            {
                ItemEffectEvent iee = new ItemEffectEvent(damaged, damager, s);
                Bukkit.getPluginManager().callEvent(iee);
                if (!iee.isCancelled())
                    addEffect(iee.getDamager(), iee.getDamaged(),
                            iee.getEffect(), event);
            }
        }
    }

    /**
     * Handles any effects caused by an EntityDamageEvent
     * 
     * @param damaged
     *            Entity damaged by event
     * @param damager
     *            Entity that caused the damage
     * @param event
     *            EntityDamageEvent that requires effects
     */
    public static void handlePluginEffects(final LivingEntity damaged,
            final LivingEntity damager, final EntityDamageEvent event)
    {
        if (damager instanceof Player)
        {
            Player striker = (Player) damager;
            List<ItemStack> strikerEquipment = new ArrayList<ItemStack>();
            strikerEquipment.add(striker.getItemInHand());
            for (String s : listEffects(strikerEquipment))
            {
                ItemEffectEvent iee = new ItemEffectEvent(damaged, damager, s);
                Bukkit.getPluginManager().callEvent(iee);
                if (!iee.isCancelled())
                    addEffect(iee.getDamaged(), iee.getDamager(),
                            iee.getEffect(), event);
            }
        }
        if (damaged instanceof Player)
        {
            Player struck = (Player) damaged;
            List<ItemStack> struckEquipment = new ArrayList<ItemStack>();
            struckEquipment.addAll(Arrays.asList(struck.getInventory()
                    .getArmorContents()));
            for (String s : listEffects(struckEquipment))
            {
                ItemEffectEvent iee = new ItemEffectEvent(damaged, damager, s);
                Bukkit.getPluginManager().callEvent(iee);
                if (!iee.isCancelled())
                    addEffect(iee.getDamager(), iee.getDamaged(),
                            iee.getEffect(), event);
            }
        }
    }

    public static List<String> listEffects(final List<ItemStack> equipment)
    {
        Set<ItemStack> toolSet = new HashSet<ItemStack>();
        for (ItemStack is : equipment)
            if ((is != null) && !is.getType().equals(Material.AIR))
            {
                toolSet.add(new ItemStack(is));
            }
        List<String> effects = new ArrayList<String>();
        for (ItemStack tool : toolSet)
        {
            if (!tool.hasItemMeta())
                continue;
            ItemMeta meta = tool.getItemMeta();
            if ((!meta.hasLore() || meta.getLore() == null)
                    || meta.getLore().isEmpty())
                continue;
            for (String string : meta.getLore())
            {
                string = ChatColor.stripColor(string).replace("%", "");
                effects.add(string);
            }
        }
        return effects;
    }
}