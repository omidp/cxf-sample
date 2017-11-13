package cxf;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class NodeChildren implements Iterable<Node>
{

    private Node rootNode;

    public NodeChildren(Node rootNode)
    {
        this.rootNode = rootNode;
    }

    @Override
    public Iterator<Node> iterator()
    {
        List<Node> nodes = new ArrayList<Node>();
        NodeList childNodes = rootNode.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++)
        {
            Node item = childNodes.item(i);
            if (item.getNodeType() == Node.ELEMENT_NODE)
            {
                nodes.add(item);
                addNode(item, nodes);
            }
        }
        return nodes.iterator();
    }

    private void addNode(Node item, List<Node> nodes)
    {
        NodeList childNodes = item.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++)
        {
            Node node = childNodes.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE)
            {
                nodes.add(node);
                addNode(node, nodes);
            }
        }

    }

}