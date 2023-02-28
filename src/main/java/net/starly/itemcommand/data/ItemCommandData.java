package net.starly.itemcommand.data;

import java.util.List;

public class ItemCommandData {

    private final List<String> name;

    public ItemCommandData(List<String> name) { this.name = name; }

    public void setItemCommand(String command) { name.add(command); }

    public List<String> getItemCommand() { return name; }

}
