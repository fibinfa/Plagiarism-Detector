package com.msd.codesniffer;
import java.util.*;

/**
 * The type Ast.
 *
 * @author sidharththapar
 * @since 2/11/18.
 */
public class AST {
    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    /**
     * The Nodes.

     */
    List<Node> nodes;
}
