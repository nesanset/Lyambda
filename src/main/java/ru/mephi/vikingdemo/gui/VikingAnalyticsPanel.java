package ru.mephi.vikingdemo.gui;

import ru.mephi.vikingdemo.service.VikingLyambdaService;
import javax.swing.*;
import java.awt.*;

public class VikingAnalyticsPanel extends JPanel{
    private final VikingLyambdaService vikingLyambdaService;
    private final JTextArea resultArea = new JTextArea();

    public VikingAnalyticsPanel(VikingLyambdaService vikingLyambdaService){
        this.vikingLyambdaService = vikingLyambdaService;
        setLayout(new BorderLayout(8, 8));
        JButton showButton = new JButton("Показать данные");
        showButton.addActionListener(e -> showScreenInfo());
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.add(showButton);

        resultArea.setEditable(false);
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        add(buttonPanel, BorderLayout.NORTH);
        add(new JScrollPane(resultArea), BorderLayout.CENTER);
    }

    private void showScreenInfo(){
        String text = "Случайный викинг ростом выше 180:\n" + vikingLyambdaService.getRandomTallVikingText() + "\n\nВикинги с легендарным снаряжением:\n" + vikingLyambdaService.getLegendaryVikingsText() + "\n\nРыжебородые викинги, отсортированные по возрасту:\n" + vikingLyambdaService.getRedBeardVikingsSortedByAgeText();
        resultArea.setText(text);
        resultArea.setCaretPosition(0);
    }
}