package me.zwee.commons.utils;


import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemStackBuilder {

    private Material _material;
    private int _amount = 1;
    private byte _data = 0;
    private String _displayName = "";
    private List<String> _lore = new ArrayList<>();

    public ItemStackBuilder ( ){ }

    public ItemStack buildItemStack(){
        ItemStack item = new ItemStack(_material, _amount, _data);
        ItemMeta itemM = item.getItemMeta();
        itemM.setDisplayName(Colour.colour(_displayName));
        itemM.setLore(new ArrayList<String>() {{_lore.forEach((String lorePiece) -> add(Colour.colour(lorePiece)));}});
        item.setItemMeta(itemM);
        return item;
    }

    public ItemStackBuilder material(Material material){
        this._material = material;
        return this;
    }
    public ItemStackBuilder amount(int amount){
        this._amount = amount;
        return this;
    }
    public ItemStackBuilder data(byte data){
        this._data = data;
        return this;
    }
    public ItemStackBuilder displayName(String displayName){
        this._displayName = displayName;
        return this;
    }
    public ItemStackBuilder lore(List<String> lore){
        this._lore = lore;
        return this;
    }

}
