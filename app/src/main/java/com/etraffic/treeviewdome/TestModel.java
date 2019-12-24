package com.etraffic.treeviewdome;

public class TestModel extends TreeViewNode {

    public String name;
    public int maginLeft;
    public boolean isSelected;

    @Override
    public String toString() {
        return "name='" + name + '\'';
    }
}
