package com.github.mouse0w0.pcpe.gui;

import com.github.mouse0w0.pcpe.Exporter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.common.Loader;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.nio.file.Paths;

public class GuiExport extends JFrame {

    public GuiExport() {
        setTitle(I18n.format("pcpe.gui.exporter.title"));
        setSize(400, 130);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        JPanel root = new JPanel(new BorderLayout());
        setContentPane(root);

        JPanel properties = new JPanel(new GridLayout(2, 2));
        root.add(BorderLayout.CENTER, properties);

        JLabel namespaceLabel = new JLabel(I18n.format("pcpe.gui.namespace"));
        properties.add(namespaceLabel);
        JComboBox<String> namespace = new JComboBox<>();
        Loader.instance().getActiveModList().forEach(modContainer -> namespace.addItem(modContainer.getModId()));
        namespace.setSelectedItem("minecraft");
        properties.add(namespace);

        JLabel exportPathLabel = new JLabel(I18n.format("pcpe.gui.exportPath"));
        properties.add(exportPathLabel);
        JPanel exportPathPanel = new JPanel(new BorderLayout());
        properties.add(exportPathPanel);
        JTextField exportPathField = new JTextField();
        exportPathPanel.add(BorderLayout.CENTER, exportPathField);
        JButton chooseExportPathButton = new JButton("...");
        chooseExportPathButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            fileChooser.setCurrentDirectory(FileSystemView.getFileSystemView().getHomeDirectory());
            fileChooser.setDialogTitle(I18n.format("pcpe.gui.exportPath.title"));
            if (fileChooser.showSaveDialog(chooseExportPathButton) == JFileChooser.APPROVE_OPTION) {
                exportPathField.setText(fileChooser.getSelectedFile().getAbsolutePath());
            }
        });
        exportPathPanel.add(BorderLayout.EAST, chooseExportPathButton);

        JPanel buttonBar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        root.add(BorderLayout.SOUTH, buttonBar);
        JButton export = new JButton(I18n.format("pcpe.gui.export"));
        export.addActionListener(e -> {
            Minecraft.getMinecraft().addScheduledTask(
                    new Exporter((String) namespace.getSelectedItem(), Paths.get(exportPathField.getText())));
            setVisible(false);
        });
        buttonBar.add(export);
    }
}
