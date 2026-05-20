package ru.mephi.vikingdemo.gui;

import ru.mephi.vikingdemo.model.Viking;
import ru.mephi.vikingdemo.service.VikingLyambdaService;
import ru.mephi.vikingdemo.service.VikingService;
import javax.swing.*;
import java.awt.*;

public class VikingDesktopFrame extends JFrame {
    private final VikingService vikingService;
    private final VikingTableModel tableModel = new VikingTableModel();

    public VikingDesktopFrame(VikingService vikingService, VikingLyambdaService vikingLyambdaService) {
        this.vikingService = vikingService;

        setTitle("Viking Demo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(1050, 500));
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JLabel header = new JLabel("Viking Demo", SwingConstants.CENTER);
        header.setFont(header.getFont().deriveFont(Font.BOLD, 18f));
        add(header, BorderLayout.NORTH);

        JPanel tablePanel = new JPanel(new BorderLayout());
        JTable vikingTable = new JTable(tableModel);
        vikingTable.setRowHeight(28);
        tablePanel.add(new JScrollPane(vikingTable), BorderLayout.CENTER);

        JButton createButton = new JButton("Create random viking");
        createButton.addActionListener(e -> onCreateViking());

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(createButton);
        tablePanel.add(bottomPanel, BorderLayout.SOUTH);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Викинги", tablePanel);
        tabs.addTab("Аналитика", new VikingAnalyticsPanel(vikingLyambdaService));

        add(tabs, BorderLayout.CENTER);
    }

    private void onCreateViking(){
        Viking viking = vikingService.generateRandomVikings(1).get(0);
        tableModel.addViking(viking);
    }
    
    public void addNewViking(Viking viking){
        tableModel.addViking(viking);
    }

    public void removeOldViking(int index){
        tableModel.removeViking(index);
    }

    public void updateOldViking(int index, Viking viking){
        tableModel.updateViking(index, viking);
    }
}
