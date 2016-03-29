package com.zerofall.ezstorage;

import com.zerofall.ezstorage.configuration.EZConfiguration;
import com.zerofall.ezstorage.events.XEventHandler;
import com.zerofall.ezstorage.gui.GuiHandler;
import com.zerofall.ezstorage.init.EZBlocks;
import com.zerofall.ezstorage.network.MyMessage;
import com.zerofall.ezstorage.network.PacketHandler;
import com.zerofall.ezstorage.network.RecipeMessage;
import com.zerofall.ezstorage.network.RecipePacketHandler;
import com.zerofall.ezstorage.proxy.CommonProxy;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION)
public class EZStorage
{   
    @Mod.Instance(Reference.MOD_ID)
    public static EZStorage instance;
    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
    public static CommonProxy proxy;
    public static SimpleNetworkWrapper networkWrapper;
    public static Configuration config;
    
    public EZTab creativeTab;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
    	config = new Configuration(event.getSuggestedConfigurationFile());
    	EZConfiguration.syncConfig();
    	this.creativeTab = new EZTab();
    	EZBlocks.init();
    	EZBlocks.register();
    	NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
    	networkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel("ezChannel");
    	networkWrapper.registerMessage(PacketHandler.class, MyMessage.class, 0, Side.SERVER);
    	networkWrapper.registerMessage(RecipePacketHandler.class, RecipeMessage.class, 1, Side.SERVER);
    	MinecraftForge.EVENT_BUS.register(new XEventHandler());
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event) {
    	proxy.registerRenders();
    }
    
}
