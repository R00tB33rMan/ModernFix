package org.embeddedt.modernfix.platform.fabric;

import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import org.embeddedt.modernfix.ModernFixFabric;
import org.objectweb.asm.tree.*;

import java.nio.file.Path;
import java.util.*;
import java.util.function.Consumer;

public class ModernFixPlatformHooksImpl {
    public static boolean isClient() {
        return FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT;
    }

    public static boolean isDedicatedServer() {
        return FabricLoader.getInstance().getEnvironmentType() == EnvType.SERVER;
    }

    private static String verString;

    public static String getVersionString() {
        if(verString == null) {
            ModContainer mfModContainer = FabricLoader.getInstance().getModContainer("modernfix").get();
            verString = mfModContainer.getMetadata().getVersion().getFriendlyString();
        }
        return verString;
    }

    public static boolean modPresent(String modId) {
        return FabricLoader.getInstance().getModContainer(modId).isPresent();
    }

    public static boolean isDevEnv() {
        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

    public static MinecraftServer getCurrentServer() {
        return ModernFixFabric.theServer;
    }

    public static boolean isLoadingNormally() {
        return true;
    }

    public static TextureAtlasSprite loadTextureAtlasSprite(TextureAtlas atlasTexture,
                                                            ResourceManager resourceManager, TextureAtlasSprite.Info textureInfo,
                                                            Resource resource,
                                                            int atlasWidth, int atlasHeight,
                                                            int spriteX, int spriteY, int mipmapLevel,
                                                            NativeImage image) {
        return new TextureAtlasSprite(atlasTexture, textureInfo, mipmapLevel, atlasWidth, atlasHeight, spriteX, spriteY, image);
    }

    public static Path getGameDirectory() {
        return FabricLoader.getInstance().getGameDir();
    }

    public static void sendPacket(ServerPlayer player, Object packet) {
        //PacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), packet);
    }

    public static void injectPlatformSpecificHacks() {
    }

    public static void applyASMTransformers(String mixinClassName, ClassNode targetClass) {

    }

    public static void onServerCommandRegister(Consumer<CommandDispatcher<CommandSourceStack>> handler) {
        CommandRegistrationCallback.EVENT.register((dispatcher, arg) -> handler.accept(dispatcher));
    }
}
