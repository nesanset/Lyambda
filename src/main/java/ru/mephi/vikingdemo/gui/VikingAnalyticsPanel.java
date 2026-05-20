package ru.mephi.vikingdemo.gui;

import ru.mephi.vikingdemo.model.*;
import ru.mephi.vikingdemo.service.VikingLyambdaService;

import javax.swing.*;
import java.awt.*;

public class VikingAnalyticsPanel extends JPanel {
    private final VikingLyambdaService vikingLyambdaService;
    private final JTextField ageField = new JTextField("30", 4);
    private final JTextField minAgeField = new JTextField("20", 4);
    private final JTextField maxAgeField = new JTextField("40", 4);
    private final JComboBox<BeardStyle> beardStyleBox = new JComboBox<>(BeardStyle.values());
    private final JComboBox<HairColor> hairColorBox = new JComboBox<>(HairColor.values());
    private final JTextArea resultArea = new JTextArea();

    public VikingAnalyticsPanel(VikingLyambdaService vikingLyambdaService) {
        this.vikingLyambdaService = vikingLyambdaService;

        setLayout(new BorderLayout(8, 8));
        JPanel settingsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        settingsPanel.add(new JLabel("Возраст (больше/меньше):"));
        settingsPanel.add(ageField);
        settingsPanel.add(new JLabel("От:"));
        settingsPanel.add(minAgeField);
        settingsPanel.add(new JLabel("До:"));
        settingsPanel.add(maxAgeField);
        settingsPanel.add(new JLabel("Борода:"));
        settingsPanel.add(beardStyleBox);
        settingsPanel.add(new JLabel("Волосы:"));
        settingsPanel.add(hairColorBox);

        JButton showButton = new JButton("Показать аналитику");
        showButton.addActionListener(e -> showAnalytics());
        settingsPanel.add(showButton);

        resultArea.setEditable(false);
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);

        add(settingsPanel, BorderLayout.NORTH);
        add(new JScrollPane(resultArea), BorderLayout.CENTER);
    }

    private void showAnalytics(){
        try{
            int age = readInt(ageField);
            int minAge = readInt(minAgeField);
            int maxAge = readInt(maxAgeField);
            if (minAge > maxAge) {
                resultArea.setText("Мин возраст не должен быть больше макс");
                return;
            }

            BeardStyle beardStyle = (BeardStyle) beardStyleBox.getSelectedItem();
            HairColor hairColor = (HairColor) hairColorBox.getSelectedItem();
            String evenIds = vikingLyambdaService.getEvenIdsText();
            if (evenIds.isBlank()) {
                evenIds = "нет";
            }
            String text = "Объем выборки\n" + "Возраст больше " + age + ": " + vikingLyambdaService.countAgeMoreThan(age) + "\nВозраст меньше " + age + ": " + vikingLyambdaService.countAgeLessThan(age) + "\nВозраст от " + minAge + " до " + maxAge + ": " + vikingLyambdaService.countAgeInRange(minAge, maxAge) + "\nВозраст вне диапазона " + minAge + "-" + maxAge + ": " + vikingLyambdaService.countAgeOutOfRange(minAge, maxAge) + "\nФорма бороды " + beardStyle + " и цвет волос " + hairColor + ": "
                    + vikingLyambdaService.countByBeardAndHair(beardStyle, hairColor) + "\nОдин или два топора: " + vikingLyambdaService.countByOneOrTwoAxes() + "\n\nСлучайный викинг ростом выше 180:\n" + vikingLyambdaService.getRandomTallVikingText() + "\n\nВикинги с легендарным снаряжением:\n" + vikingLyambdaService.getLegendaryVikingsText()
                    + "\n\nРыжебородые викинги, отсортированные по возрасту:\n" + vikingLyambdaService.getRedBeardVikingsSortedByAgeText() + "\nМаксимальный ИД: " + vikingLyambdaService.getMaxId() + "\nЧетные ИД: " + evenIds;
            resultArea.setText(text);
            resultArea.setCaretPosition(0);
        }catch (NumberFormatException exception){
            resultArea.setText("Возраст должен быть целым!!!!");
        }
    }

    private int readInt(JTextField field){
        return Integer.parseInt(field.getText().trim());
    }
}
