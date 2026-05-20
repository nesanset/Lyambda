package ru.mephi.vikingdemo.gui;

import ru.mephi.vikingdemo.model.*;
import ru.mephi.vikingdemo.service.VikingLyambdaService;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.function.Consumer;

public class VikingLambdaPanel extends JPanel {
    private final VikingLyambdaService vikingLyambdaService;
    private final Consumer<List<Viking>> vikingsCreated;
    private final JTextArea resultArea = new JTextArea();
    private final JTextField ageField = new JTextField("30", 5);
    private final JTextField minAgeField = new JTextField("25", 5);
    private final JTextField maxAgeField = new JTextField("35", 5);
    private final JTextField generateCountField = new JTextField("5", 5);
    private final JComboBox<BeardStyle> beardStyleBox = new JComboBox<>(BeardStyle.values());
    private final JComboBox<HairColor> hairColorBox = new JComboBox<>(HairColor.values());

    public VikingLambdaPanel(VikingLyambdaService vikingLyambdaService, Consumer<List<Viking>> vikingsCreated) {
        this.vikingLyambdaService = vikingLyambdaService;
        this.vikingsCreated = vikingsCreated;

        setLayout(new BorderLayout());
        JPanel controls = new JPanel(new GridLayout(0, 1));
        controls.add(createAgePanel());
        controls.add(createBeardAndHairPanel());
        controls.add(createAxesPanel());
        controls.add(createInfoPanel());
        controls.add(createIdsPanel());
        controls.add(createGeneratePanel());
        resultArea.setEditable(false);

        add(controls, BorderLayout.NORTH);
        add(new JScrollPane(resultArea), BorderLayout.CENTER);
    }

    private JPanel createAgePanel() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("Возраст:"));
        panel.add(ageField);
        panel.add(new JLabel("от"));
        panel.add(minAgeField);
        panel.add(new JLabel("до"));
        panel.add(maxAgeField);

        JButton moreButton = new JButton("Больше");
        JButton lessButton = new JButton("Меньше");
        JButton inRangeButton = new JButton("В диапазоне");
        JButton outRangeButton = new JButton("Вне диапазона");

        moreButton.addActionListener(e -> showCount("Возраст больше " + getAge(), vikingLyambdaService.countAgeMoreThan(getAge())));
        lessButton.addActionListener(e -> showCount("Возраст меньше " + getAge(), vikingLyambdaService.countAgeLessThan(getAge())));
        inRangeButton.addActionListener(e -> showCount("Возраст в диапазоне", vikingLyambdaService.countAgeInRange(getMinAge(), getMaxAge())));
        outRangeButton.addActionListener(e -> showCount("Возраст вне диапазона", vikingLyambdaService.countAgeOutOfRange(getMinAge(), getMaxAge())));

        panel.add(moreButton);
        panel.add(lessButton);
        panel.add(inRangeButton);
        panel.add(outRangeButton);
        return panel;
    }

    private JPanel createBeardAndHairPanel() {
        JPanel panel = new JPanel();
        JButton button = new JButton("Форма бороды + цвет волос");
        button.addActionListener(e -> showCount("Форма бороды и цвет волос", vikingLyambdaService.countByBeardAndHair((BeardStyle) beardStyleBox.getSelectedItem(), (HairColor) hairColorBox.getSelectedItem())));

        panel.add(new JLabel("Борода:"));
        panel.add(beardStyleBox);
        panel.add(new JLabel("Волосы:"));
        panel.add(hairColorBox);
        panel.add(button);
        return panel;
    }

    private JPanel createAxesPanel() {
        JPanel panel = new JPanel();
        JButton axesButton = new JButton("Один или два топора");
        axesButton.addActionListener(e -> showCount("С одним или двумя топорами", vikingLyambdaService.countByOneOrTwoAxes()));

        panel.add(axesButton);
        return panel;
    }

    private JPanel createInfoPanel() {
        JPanel panel = new JPanel();
        JButton randomTallButton = new JButton("Случайный выше 180");
        JButton legendaryButton = new JButton("Легендарное снаряжение");
        JButton redSortedButton = new JButton("Рыжебородые по возрасту");

        randomTallButton.addActionListener(e -> resultArea.setText(vikingLyambdaService.getRandomTallVikingText()));
        legendaryButton.addActionListener(e -> resultArea.setText(vikingLyambdaService.getLegendaryVikingsText()));
        redSortedButton.addActionListener(e -> resultArea.setText(vikingLyambdaService.getRedBeardVikingsSortedByAgeText()));

        panel.add(randomTallButton);
        panel.add(legendaryButton);
        panel.add(redSortedButton);
        return panel;
    }

    private JPanel createIdsPanel() {
        JPanel panel = new JPanel();
        JButton maxIdButton = new JButton("Max ID");
        JButton evenIdsButton = new JButton("Четные ID");

        maxIdButton.addActionListener(e -> resultArea.setText("Последняя запись: " + vikingLyambdaService.getMaxId()));
        evenIdsButton.addActionListener(e -> resultArea.setText("Четные ID: " + vikingLyambdaService.getEvenIdsText()));

        panel.add(maxIdButton);
        panel.add(evenIdsButton);
        return panel;
    }

    private JPanel createGeneratePanel() {
        JPanel panel = new JPanel();
        JButton generateButton = new JButton("Сгенерировать викингов");
        generateButton.addActionListener(e -> {
            List<Viking> generated = vikingLyambdaService.generateRandomVikings(getGenerateCount());
            vikingsCreated.accept(generated);
            resultArea.setText("Сгенерировано викингов: " + generated.size());
        });

        panel.add(new JLabel("Количество:"));
        panel.add(generateCountField);
        panel.add(generateButton);
        return panel;
    }

    private void showCount(String title, long count) {
        resultArea.setText(title +"\nКоличество: " + count);
    }

    private int getAge() {
        return Integer.parseInt(ageField.getText());
    }

    private int getMinAge() {
        return Integer.parseInt(minAgeField.getText());
    }

    private int getMaxAge() {
        return Integer.parseInt(maxAgeField.getText());
    }

    private int getGenerateCount() {
        return Integer.parseInt(generateCountField.getText());
    }
}