package io.github.pyrocake.compat.jade;

import io.github.pyrocake.block.custom.Collector_Block;
import io.github.pyrocake.block.custom.Solar_Oven_Block;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin
public class JadeCompat implements IWailaPlugin {

    @Override
    public void register(IWailaCommonRegistration registration) {
        //TODO register data providers
        registration.registerBlockDataProvider(SolarOvenProvider.INSTANCE, Solar_Oven_Block.class);
        registration.registerBlockDataProvider(CollectorProvider.INSTANCE, Collector_Block.class);
    }

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        //TODO register component providers, icon providers, callbacks, and config options here
        registration.registerBlockComponent(SolarOvenProvider.INSTANCE, Solar_Oven_Block.class);
        registration.registerBlockComponent(CollectorProvider.INSTANCE, Collector_Block.class);
    }
}
