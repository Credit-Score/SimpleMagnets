package com.supermartijn642.simplemagnets;

import com.supermartijn642.simplemagnets.gui.MagnetContainer;
import com.supermartijn642.simplemagnets.packets.*;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import net.minecraftforge.registries.ObjectHolder;
import top.theillusivec4.curios.api.CuriosAPI;
import top.theillusivec4.curios.api.imc.CurioIMCMessage;

/**
 * Created 7/7/2020 by SuperMartijn642
 */
@Mod("simplemagnets")
public class SimpleMagnets {

    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(new ResourceLocation("simplemagnets", "main"), () -> "1", "1"::equals, "1"::equals);

    @ObjectHolder("simplemagnets:basicmagnet")
    public static Item simple_magnet;
    @ObjectHolder("simplemagnets:advancedmagnet")
    public static Item advanced_magnet;
    @ObjectHolder("simplemagnets:container")
    public static ContainerType<MagnetContainer> container;

    public SimpleMagnets(){
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::init);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::interModEnqueue);

        CHANNEL.registerMessage(0, PacketToggleItems.class, PacketToggleItems::encode, PacketToggleItems::decode, PacketToggleItems::handle);
        CHANNEL.registerMessage(1, PacketIncreaseItemRange.class, PacketIncreaseItemRange::encode, PacketIncreaseItemRange::decode, PacketIncreaseItemRange::handle);
        CHANNEL.registerMessage(2, PacketDecreaseItemRange.class, PacketDecreaseItemRange::encode, PacketDecreaseItemRange::decode, PacketDecreaseItemRange::handle);
        CHANNEL.registerMessage(3, PacketToggleXp.class, PacketToggleXp::encode, PacketToggleXp::decode, PacketToggleXp::handle);
        CHANNEL.registerMessage(4, PacketIncreaseXpRange.class, PacketIncreaseXpRange::encode, PacketIncreaseXpRange::decode, PacketIncreaseXpRange::handle);
        CHANNEL.registerMessage(5, PacketDecreaseXpRange.class, PacketDecreaseXpRange::encode, PacketDecreaseXpRange::decode, PacketDecreaseXpRange::handle);
        CHANNEL.registerMessage(6, PacketToggleWhitelist.class, PacketToggleWhitelist::encode, PacketToggleWhitelist::decode, PacketToggleWhitelist::handle);
        CHANNEL.registerMessage(7, PacketToggleMagnet.class, PacketToggleMagnet::encode, PacketToggleMagnet::decode, PacketToggleMagnet::handle);
        CHANNEL.registerMessage(8, PacketItemInfo.class, PacketItemInfo::encode, PacketItemInfo::decode, PacketItemInfo::handle);
        CHANNEL.registerMessage(9,PacketToggleMagnetMessage.class, PacketToggleMagnetMessage::encode, PacketToggleMagnetMessage::decode, PacketToggleMagnetMessage::handle);
    }

    public void init(FMLCommonSetupEvent e){
        DistExecutor.runWhenOn(Dist.CLIENT, () -> ClientProxy::init);
        DistExecutor.runWhenOn(Dist.CLIENT, () -> ClientProxy::registerScreen);
    }

    public void interModEnqueue(InterModEnqueueEvent e){
        InterModComms.sendTo("curios", CuriosAPI.IMC.REGISTER_TYPE, () -> new CurioIMCMessage("charm").setSize(1).setEnabled(true));
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onItemRegistry(final RegistryEvent.Register<Item> e){
            e.getRegistry().register(new BasicMagnet());
            e.getRegistry().register(new AdvancedMagnet());
        }

        @SubscribeEvent
        public static void onContainerRegistry(final RegistryEvent.Register<ContainerType<?>> e){
            e.getRegistry().register(IForgeContainerType.create((windowId, inv, data) -> new MagnetContainer(windowId, inv.player, data.readInt())).setRegistryName("container"));
        }
    }

}
