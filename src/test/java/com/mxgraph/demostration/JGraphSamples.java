package com.mxgraph.demostration;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import com.mxgraph.io.mxCodec;
import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.layout.orthogonal.mxOrthogonalLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxCellRenderer;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxResources;
import com.mxgraph.util.mxXmlUtils;
import com.mxgraph.util.png.mxPngEncodeParam;
import com.mxgraph.util.png.mxPngImageEncoder;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxStylesheet;

public class JGraphSamples extends JFrame
{
    private static final long serialVersionUID = -8645440291176361843L;

    public JGraphSamples()
    {

        // mxSaxOutputHandler aa = new mxSaxOutputHandler();

        final mxGraph graph = new mxGraph();
        Object parent = graph.getDefaultParent();

        // final mxCircleLayout circleLayout = new mxCircleLayout(graph);
        // final mxCompactTreeLayout compactTreeLayout = new mxCompactTreeLayout(graph, false);
        // final mxEdgeLabelLayout edgeLabelLayout = new mxEdgeLabelLayout(graph);
        // final mxFastOrganicLayout fastOrganicLayout = new mxFastOrganicLayout(graph);
        //
        // final mxOrganicLayout organicLayout = new mxOrganicLayout(graph);
        // final mxParallelEdgeLayout parallelEdgeLayout = new mxParallelEdgeLayout(graph); //并行
        // final mxPartitionLayout partitionLayout = new mxPartitionLayout(graph); //分区
        // final mxStackLayout stackLayout = new mxStackLayout(graph); //堆栈

        final mxHierarchicalLayout hierarchicalLayout = new mxHierarchicalLayout(graph, SwingConstants.NORTH);

        // 组织结构图
        final mxOrthogonalLayout orthogonalLayout = new mxOrthogonalLayout(graph);

        hierarchicalLayout.setIntraCellSpacing(50.0);
        hierarchicalLayout.setResizeParent(true);
        hierarchicalLayout.setFineTuning(true);
        hierarchicalLayout.setParentBorder(20);
        hierarchicalLayout.setMoveParent(false);
        hierarchicalLayout.setDisableEdgeStyle(false);
        hierarchicalLayout.setUseBoundingBox(true);

        graph.getModel().beginUpdate();

        Object A = graph.insertVertex(parent, null, "A", 0, 0, 60, 30, "ROUNDED;shape=cylinder");
        Object A1 = graph.insertVertex(parent, null, "A1", 0, 0, 60, 30);
        Object A2 = graph.insertVertex(parent, null, "A2", 0, 0, 60, 30);
        Object A3 = graph.insertVertex(parent, null, "A3", 0, 0, 60, 30);
        Object A4 = graph.insertVertex(parent, null, "A4", 0, 0, 60, 30);
        Object A5 = graph.insertVertex(parent, null, "A5", 0, 0, 60, 30);

        Object A11 = graph.insertVertex(parent, null, "A11", 0, 0, 60, 30, "ROUNDED;shape=ellipse");
        Object A21 = graph.insertVertex(parent, null, "A21", 0, 0, 60, 30, "ROUNDED;shape=ellipse");

        Object A31 = graph.insertVertex(parent, null, "A31", 0, 0, 60, 30, "ROUNDED;shape=ellipse");
        Object A41 = graph.insertVertex(parent, null, "A41", 0, 0, 60, 30, "ROUNDED;shape=ellipse");

        graph.insertEdge(parent, null, "", A, A1);
        graph.insertEdge(parent, null, "", A, A2);
        graph.insertEdge(parent, null, "", A, A3);
        graph.insertEdge(parent, null, "", A, A4);
        graph.insertEdge(parent, null, "", A, A5);

        graph.insertEdge(parent, null, "", A1, A11);
        graph.insertEdge(parent, null, "", A2, A11);
        graph.insertEdge(parent, null, "", A4, A11);

        graph.insertEdge(parent, null, "", A3, A21);

        graph.insertEdge(parent, null, "", A5, A31);
        graph.insertEdge(parent, null, "", A5, A41);

        mxStylesheet stylesheet = graph.getStylesheet();

        Hashtable<String, Object> style1 = new Hashtable<String, Object>();
        style1.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_ELLIPSE);
        stylesheet.putCellStyle("ROUNDED", style1);

        //设置样式
        graph.setStylesheet(stylesheet);

        // graph.insertEdge(parent, null, "SPS1,JIT1", A, C);
        // graph.insertEdge(parent, null, "SPS1,JIT1", B, C);
        // graph.insertEdge(parent, null, "SPS1,JIT1", A, B);

        graph.getModel().endUpdate();

        // circleLayout.setX0(20);
        // circleLayout.setY0(20);
        // circleLayout.execute(parent);
        
        // compactTreeLayout.execute(parent);
        // edgeLabelLayout.execute(parent);
        // fastOrganicLayout.execute(parent);

        // organicLayout.execute(parent);
        // parallelEdgeLayout.execute(parent);
        // partitionLayout.execute(parent);
        // stackLayout.execute(parent);

        List<Object> vertex = new ArrayList<Object>();
        
        vertex.add(A);

        hierarchicalLayout.setVertexLocation(vertex, 100, 200);
        
        //执行更新
        hierarchicalLayout.execute(parent);
        // orthogonalLayout.execute(parent);

        // graph.getModel().beginUpdate();
        // graph.getModel().endUpdate();

        final mxGraphComponent graphComponent = new mxGraphComponent(graph);
        getContentPane().add(graphComponent);

        graphComponent.getGraphControl().addMouseListener(new MouseAdapter()
        {

            public void mouseReleased(MouseEvent e)
            {
                Object cell = graphComponent.getCellAt(e.getX(), e.getY());

                if (cell != null)
                {
                    System.out.println("x=" + e.getX() + "y=" + e.getY());
                    System.out.println("cell=" + graph.getLabel(cell));
                }
            }
        });

        try
        {
            this.saveXmlPng(graphComponent);
        } catch (IOException e1)
        {
            e1.printStackTrace();
        }
    }

    protected void saveXmlPng(mxGraphComponent graphComponent) throws IOException
    {
        Color bg = graphComponent.getBackground();

        mxGraph graph = graphComponent.getGraph();

        // Creates the image for the PNG file
        BufferedImage image = mxCellRenderer.createBufferedImage(graph, null, 1, bg, graphComponent.isAntiAlias(), null, graphComponent.getCanvas());
        // Creates the URL-encoded XML data
        mxCodec codec = new mxCodec();
        String xml = URLEncoder.encode(mxXmlUtils.getXml(codec.encode(graph.getModel())), "UTF-8");
        mxPngEncodeParam param = mxPngEncodeParam.getDefaultEncodeParam(image);
        param.setCompressedText(new String[] { "mxGraphModel", xml });

        // Saves as a PNG file
        FileOutputStream outputStream = new FileOutputStream(new File("d:\\123.png"));
        try
        {
            mxPngImageEncoder encoder = new mxPngImageEncoder(outputStream, param);
            if (image != null)
            {
                encoder.encode(image);
            } else
            {
                JOptionPane.showMessageDialog(graphComponent, mxResources.get("noImageData"));
            }
        } finally
        {
            outputStream.close();
        }
    }

    public static void main(String[] args)
    {
        JGraphSamples frame = new JGraphSamples();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setVisible(true);
    }
}
