package com.github.mouse0w0.mce.gui;

import com.github.mouse0w0.mce.Exporter;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.Loader;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.io.File;
import java.nio.file.Paths;

public class GuiExport extends JFrame {

    public GuiExport() {
        setTitle("Export");
        setSize(400, 130);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        JPanel root = new JPanel(new BorderLayout());
        setContentPane(root);

        JPanel properties = new JPanel(new GridLayout(2, 2));
        root.add(BorderLayout.CENTER, properties);

        JLabel namespaceLabel = new JLabel("Namespace:");
        properties.add(namespaceLabel);
        JComboBox<String> namespace = new JComboBox<>();
        Loader.instance().getActiveModList().forEach(modContainer -> namespace.addItem(modContainer.getModId()));
        namespace.setSelectedItem("minecraft");
        properties.add(namespace);

        JLabel exportPathLabel = new JLabel("ExportPath:");
        properties.add(exportPathLabel);
        JPanel exportPathPanel = new JPanel(new BorderLayout());
        properties.add(exportPathPanel);
        JTextField exportPath = new JTextField();
        exportPathPanel.add(BorderLayout.CENTER, exportPath);
        JButton chooseExportPath = new JButton("...");
        chooseExportPath.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Save File");
            fileChooser.setSelectedFile(Paths.get(namespace.getSelectedItem().toString() + ".zip").toFile());
            fileChooser.setFileFilter(new FileFilter() {
                @Override
                public boolean accept(File f) {
                    return f.isDirectory() || f.getName().endsWith(".zip");
                }

                @Override
                public String getDescription() {
                    return ".zip";
                }
            });
            if (fileChooser.showSaveDialog(chooseExportPath) == JFileChooser.APPROVE_OPTION) {
                exportPath.setText(fileChooser.getSelectedFile().getAbsolutePath());
            }
        });
        exportPathPanel.add(BorderLayout.EAST, chooseExportPath);

        JPanel buttonBar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        root.add(BorderLayout.SOUTH, buttonBar);
        JButton export = new JButton("Export");
        export.addActionListener(e -> {
            Minecraft.getMinecraft().addScheduledTask(
                    new Exporter((String) namespace.getSelectedItem(), Paths.get(exportPath.getText())));
            setVisible(false);
        });
        buttonBar.add(export);
    }
}
