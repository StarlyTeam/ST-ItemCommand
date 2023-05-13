package net.starly.itemcommand;

import net.starly.core.bstats.Metrics;
import net.starly.core.util.EmptyTabCompleter;
import net.starly.itemcommand.command.ItemCommandCmd;
import net.starly.itemcommand.command.ItemCommandRlCmd;
import net.starly.itemcommand.command.tabcomplete.ItemCommandTab;
import net.starly.itemcommand.context.MessageContent;
import net.starly.itemcommand.listener.ItemCommandListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class ItemCommandMain extends JavaPlugin {

    private static ItemCommandMain instance;

    @Override
    public void onEnable() {
        if (Bukkit.getPluginManager().getPlugin("ST-Core") == null) {
            Bukkit.getLogger().warning("[" + getName() + "] ST-Core 플러그인이 적용되지 않았습니다! 플러그인을 비활성화합니다.");
            Bukkit.getLogger().warning("[" + getName() + "] 다운로드 링크 : §fhttp://starly.kr/");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        instance = this;
        new Metrics(this, 17828);

        if (!new File(getDataFolder(), "config.yml").exists()) saveDefaultConfig();
        MessageContent.getMessageContent().initializing(getConfig());

        Bukkit.getPluginCommand("itemcommand").setExecutor(new ItemCommandCmd());
        Bukkit.getPluginCommand("itemcommandreload").setExecutor(new ItemCommandRlCmd());
        Bukkit.getPluginCommand("itemcommand").setTabCompleter(new ItemCommandTab(getServer()));
        Bukkit.getPluginCommand("itemcommandreload").setTabCompleter(new EmptyTabCompleter());

        Bukkit.getPluginManager().registerEvents(new ItemCommandListener(), this);
    }

    public static ItemCommandMain getInstance() {
        return instance;
    }
}