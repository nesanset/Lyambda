package ru.mephi.vikingdemo.gui;

import ru.mephi.vikingdemo.model.BeardStyle;
import ru.mephi.vikingdemo.model.HairColor;
import ru.mephi.vikingdemo.model.Viking;
import ru.mephi.vikingdemo.service.VikingService;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;
import java.util.function.Consumer;

public class VikingLambdaPanel extends JPanel {
    private final VikingService vikingService;
    private final Consumer<List<Viking>> vikingsCreated;
    private final JTextArea resultArea = new JTextArea();
    private final JTextField ageField = new JTextField("30", 5);
    private final JTextField minAgeField = new JTextField("25", 5);
    private final JTextField maxAgeField = new JTextField("35", 5);
    private final JTextField generateCountField = new JTextField("5", 5);
    private final JComboBox<BeardStyle> beardStyleBox = new JComboBox<>(BeardStyle.values());
    private final JComboBox<HairColor> hairColorBox = new JComboBox<>(HairColor.values());

    public VikingLambdaPanel(VikingService vikingService, Consumer<List<Viking>> vikingsCreated) {
        this.vikingService = vikingService;
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

        moreButton.addActionListener(e -> showCount("Возраст больше " + getAge(), vikingService.countAgeMoreThan(getAge())));
        lessButton.addActionListener(e -> showCount("Возраст меньше " + getAge(), vikingService.countAgeLessThan(getAge())));
        inRangeButton.addActionListener(e -> showCount("Возраст в диапазоне", vikingService.countAgeInRange(getMinAge(), getMaxAge())));
        outRangeButton.addActionListener(e -> showCount("Возраст вне диапазона", vikingService.countAgeOutOfRange(getMinAge(), getMaxAge())));

        panel.add(moreButton);
        panel.add(lessButton);
        panel.add(inRangeButton);
        panel.add(outRangeButton);
        return panel;
    }

    private JPanel createBeardAndHairPanel() {
        JPanel panel = new JPanel();
        JButton button = new JButton("Форма бороды + цвет волос");

        button.addActionListener(e -> showCount("Форма бороды и цвет волос",
                vikingService.countByBeardAndHair((BeardStyle) beardStyleBox.getSelectedItem(),
                        (HairColor) hairColorBox.getSelectedItem())));

        panel.add(new JLabel("Борода:"));
        panel.add(beardStyleBox);
        panel.add(new JLabel("Волосы:"));
        panel.add(hairColorBox);
        panel.add(button);
        return panel;
    }

    private JPanel createAxesPanel() {
        JPanel panel = new JPanel();
        JButton oneAxeButton = new JButton("Один топор");
        JButton twoAxesButton = new JButton("Два топора");

        oneAxeButton.addActionListener(e -> showCount("С одним топором", vikingService.countByAxeCount(1)));
        twoAxesButton.addActionListener(e -> showCount("С двумя топорами", vikingService.countByAxeCount(2)));

        panel.add(oneAxeButton);
        panel.add(twoAxesButton);
        return panel;
    }

    private JPanel createInfoPanel() {
        JPanel panel = new JPanel();
        JButton randomTallButton = new JButton("Случайный выше 180");
        JButton legendaryButton = new JButton("Легендарное снаряжение");
        JButton redSortedButton = new JButton("Рыжебородые по возрасту");

        randomTallButton.addActionListener(e -> resultArea.setText(vikingService.getRandomTallVikingText()));
        legendaryButton.addActionListener(e -> resultArea.setText(vikingService.getLegendaryVikingsText()));
        redSortedButton.addActionListener(e -> resultArea.setText(vikingService.getRedBeardVikingsSortedByAgeText()));

        panel.add(randomTallButton);
        panel.add(legendaryButton);
        panel.add(redSortedButton);
        return panel;
    }

    private JPanel createIdsPanel() {
        JPanel panel = new JPanel();
        JButton maxIdButton = new JButton("Max ID");
        JButton evenIdsButton = new JButton("Четные ID");

        maxIdButton.addActionListener(e -> resultArea.setText("Последняя запись: " + vikingService.getMaxId()));
        evenIdsButton.addActionListener(e -> resultArea.setText("Четные ID: " + vikingService.getEvenIdsText()));

        panel.add(maxIdButton);
        panel.add(evenIdsButton);
        return panel;
    }

    private JPanel createGeneratePanel() {
        JPanel panel = new JPanel();
        JButton generateButton = new JButton("Сгенерировать викингов");

        generateButton.addActionListener(e -> {
            List<Viking> generated = vikingService.generateRandomVikings(getGenerateCount());
            vikingsCreated.accept(generated);
            resultArea.setText("Сгенерировано викингов: " + generated.size());
        });

        panel.add(new JLabel("Количество:"));
        panel.add(generateCountField);
        panel.add(generateButton);
        return panel;
    }

    private void showCount(String title, long count) {
        resultArea.setText(title + "\nКоличество: " + count);
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
