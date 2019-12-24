package com.etraffic.treeviewdome;

import java.util.ArrayList;
import java.util.List;

public class TreeViewDataSource {

    private TreeViewDataSource() {
    }

    private List<TreeViewNode> elements = new ArrayList();
    private List<TreeViewNode> nodes;

    public TreeViewDataSource(List<TreeViewNode> nodes) {
        this.nodes = nodes;
        if (nodes == null) {
            nodes = new ArrayList();
        }
        collectElements(nodes);
    }

    private void collectElements(List<? extends TreeViewNode> nodes) {
        for (TreeViewNode node : nodes) {
            elements.add(node);
            if (node.isExpand == true && node.child != null && node.child.size() > 0) {
                collectElements(node.child);
            }
        }
    }

    public void updateNodes() {
        elements.clear();
        collectElements(nodes);
    }

    public List<? extends TreeViewNode> getElements() {
        return elements;
    }

    public List<? extends TreeViewNode> getNodes() {
        return nodes;
    }
}
