package com.supermartijn642.simplemagnets;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TranslationTextComponent;

/**
 * Created 7/7/2020 by SuperMartijn642
 */
public class BasicMagnet extends MagnetItem {


    public BasicMagnet(){
        super("basicmagnet");
    }

    @Override
    protected boolean canPickupItems(CompoundNBT tag){
        return true;
    }

    @Override
    protected boolean canPickupStack(CompoundNBT tag, ItemStack stack){
        return true;
    }

    @Override
    protected boolean canPickupXp(CompoundNBT tag){
        return true;
    }

    @Override
    protected int getRangeItems(CompoundNBT tag){
        return SMConfig.basicMagnetRange.get();
    }

    @Override
    protected int getRangeXp(CompoundNBT tag){
        return SMConfig.basicMagnetRange.get();
    }

    @Override
    protected TextComponent getTooltip(){
        return new TranslationTextComponent("simplemagnets.basicmagnet.info", SMConfig.basicMagnetRange.get());
    }
}
