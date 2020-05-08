package com.github.jarada.waygates.data;

import com.google.gson.Gson;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class Network {

    private static Gson gson;

    private static Network       voidNetwork;
    private static Network       overworldNetwork;
    private static Network       netherNetwork;
    private static Network       underworldNetwork;
    private static Network       theEndNetwork;
    private static Network       oceanNetwork;

    private UUID                 uuid;
    private transient String  sysUuid;

    private UUID                 owner;
    private String               name;
    private Material             icon;

    // Set one or another at creation
    // Private: Not shown in network list for selection, only Owner can add/use gates on network
    // Invite: Not shown in network list for selection, can only add gates using invite code from Owner
    // NB: Use direct invites to the Network NOT codes
    private boolean              networkPrivate, networkInvite;
    private boolean              system, isVoid;
    private List<UUID>           invitedUsers;

    public Network(String name) {
        this(name, false, false);
    }

    public Network(String name, boolean networkPrivate, boolean networkInvite) {
        this.name = name;
        this.networkPrivate = networkPrivate;
        this.networkInvite = networkInvite;
    }

    public static Network getVoidNetwork() {
        if (voidNetwork == null) {
            voidNetwork = new Network(Msg.NETWORK_SYSTEM_NAME_VOID.toString());
            voidNetwork.system = true;
            voidNetwork.isVoid = true;
            voidNetwork.icon = Material.SNOWBALL;
            voidNetwork.sysUuid = "sys_void";
        }
        return voidNetwork;
    }

    public static Network getOverworldNetwork() {
        if (overworldNetwork == null) {
            overworldNetwork = new Network(Msg.NETWORK_SYSTEM_NAME_OVERWORLD.toString());
            overworldNetwork.system = true;
            overworldNetwork.icon = Material.APPLE;
            overworldNetwork.sysUuid = "sys_overworld";
        }
        return overworldNetwork;
    }

    public static Network getOceanNetwork() {
        if (oceanNetwork == null) {
            oceanNetwork = new Network(Msg.NETWORK_SYSTEM_NAME_OCEAN.toString());
            oceanNetwork.system = true;
            oceanNetwork.icon = Material.HEART_OF_THE_SEA;
            oceanNetwork.sysUuid = "sys_ocean";
        }
        return oceanNetwork;
    }

    public static Network getNetherNetwork() {
        if (netherNetwork == null) {
            netherNetwork = new Network(Msg.NETWORK_SYSTEM_NAME_NETHER.toString());
            netherNetwork.system = true;
            netherNetwork.icon = Material.FIRE_CHARGE;
            netherNetwork.sysUuid = "sys_nether";
        }
        return netherNetwork;
    }

    public static Network getUnderworldNetwork() {
        if (underworldNetwork == null) {
            underworldNetwork = new Network(Msg.NETWORK_SYSTEM_NAME_UNDERWORLD.toString());
            underworldNetwork.system = true;
            underworldNetwork.icon = Material.MAGMA_CREAM;
            underworldNetwork.sysUuid = "sys_underworld";
        }
        return underworldNetwork;
    }

    public static Network getTheEndNetwork() {
        if (theEndNetwork == null) {
            theEndNetwork = new Network(Msg.NETWORK_SYSTEM_NAME_THE_END.toString());
            theEndNetwork.system = true;
            theEndNetwork.icon = Material.ENDER_EYE;
            theEndNetwork.sysUuid = "sys_the_end";
        }
        return theEndNetwork;
    }

    public static Network getSystemNetworkFromSysUUID(String sysUuid) {
        if (sysUuid.equals("sys_void"))
            return getVoidNetwork();
        if (sysUuid.equals("sys_overworld"))
            return getOverworldNetwork();
        if (sysUuid.equals("sys_ocean"))
            return getOceanNetwork();
        if (sysUuid.equals("sys_nether"))
            return getNetherNetwork();
        if (sysUuid.equals("sys_underworld"))
            return getUnderworldNetwork();
        if (sysUuid.equals("sys_the_end"))
            return getTheEndNetwork();
        return null;
    }

    public static List<Network> systemNetworks() {
        return new ArrayList<Network>(
                Arrays.asList(getVoidNetwork(), getOverworldNetwork(), getOceanNetwork(),
                        getNetherNetwork(), getUnderworldNetwork(), getTheEndNetwork())
        );
    }

    public UUID getUUID() {
        if (uuid == null) {
            uuid = UUID.randomUUID();
        }
        return uuid;
    }

    public String getSysUuid() {
        return sysUuid;
    }

    public UUID getOwner() {
        return owner;
    }

    public void setOwner(UUID owner) {
        if (!isSystem())
            this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (!isSystem())
            this.name = name;
    }

    public Material getIcon() {
        if (icon == null) {
            if (isNetworkPrivate())
                return Material.RAIL;
            if (isNetworkInvite())
                return Material.DETECTOR_RAIL;
            return Material.POWERED_RAIL;
        }
        return icon;
    }

    public List<UUID> getInvitedUsers() {
        return invitedUsers;
    }

    public void addInvitedUser(UUID invite) {
        invitedUsers.add(invite);
    }

    public void removeInvitedUser(UUID invite) {
        invitedUsers.remove(invite);
    }

    public boolean isInvitedUser(UUID invite) {
        return getOwner().equals(invite) || invitedUsers.contains(invite);
    }

    public boolean isNetworkPrivate() {
        return networkPrivate;
    }

    public boolean isNetworkInvite() {
        return networkInvite;
    }

    public boolean isSystem() {
        return system;
    }

    public boolean isGlobal() {
        return isSystem() || (!isNetworkPrivate() && !isNetworkInvite());
    }

    public boolean isVoid() { return isVoid; }

    /* Serialization */

    private static Gson getGson() {
        if (gson == null)
            gson = new Gson();
        return gson;
    }

    public static Network fromJson(String json) {
        return getGson().fromJson(json, Network.class);
    }

    public String toJson() {
        return getGson().toJson(this);
    }
}
