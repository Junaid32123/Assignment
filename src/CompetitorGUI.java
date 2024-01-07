import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.table.DefaultTableModel;
import java.util.Collections;
import java.util.Comparator;


public class CompetitorGUI extends JFrame {
    private Manager manager;
    private ArrayList<Competitor> competitors;

    public CompetitorGUI() {
        manager = new Manager();
        competitors = manager.readCompetitorData("src/RunCompetitor.csv");

        setTitle("Competitor Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();

        JPanel viewScoresPanel = createViewScoresPanel();
        JPanel viewTablePanel = createViewTablePanel();
        JPanel viewDetailsPanel = createViewDetailsPanel();

        tabbedPane.addTab("View Scores", viewScoresPanel);
        tabbedPane.addTab("View Table", viewTablePanel);
        tabbedPane.addTab("View Details", viewDetailsPanel);

        add(tabbedPane, BorderLayout.CENTER);

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String outputFilePath = "src/competitors_report.txt";
                writeCompetitorReport(outputFilePath);
                System.exit(0);
            }
        });
        add(closeButton, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createViewScoresPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JLabel selectCompetitorLabel = new JLabel("Select Competitor: ");
        JComboBox<String> competitorComboBox = new JComboBox<>();
        for (Competitor competitor : competitors) {
            competitorComboBox.addItem(competitor.getCompetitorName());
        }

        JLabel scoresLabel = new JLabel("Scores: ");
        JTextField scoresField = new JTextField(20);
        JButton updateScoresButton = new JButton("Update Scores");

        JTextArea displayArea = new JTextArea(10, 30);
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);

        updateScoresButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = competitorComboBox.getSelectedIndex();
                if (selectedIndex != -1) {
                    Competitor selectedCompetitor = competitors.get(selectedIndex);
                    String newScores = scoresField.getText();
                    String[] scoreStrings = newScores.split(",");

                    // Ensure there are exactly 4 scores
                    if (scoreStrings.length != 4) {
                        displayArea.setText("Please enter exactly 4 scores separated by commas.");
                        return;
                    }

                    // Validate and parse individual score strings to integers
                    int[] updatedScores = new int[4];
                    try {
                        for (int i = 0; i < scoreStrings.length; i++) {
                            updatedScores[i] = Integer.parseInt(scoreStrings[i].trim());
                        }

                        // Update the scores of the selected competitor
                        selectedCompetitor.setScores(updatedScores);

                        double overallScore = selectedCompetitor.getOverallScore();
                        displayArea.setText("Updated Scores: " + Arrays.toString(updatedScores)
                                + "\nOverall Score: " + overallScore);
                    } catch (NumberFormatException ex) {
                        displayArea.setText("Please enter valid integer scores separated by commas.");
                    }
                }
            }
        });

        panel.add(selectCompetitorLabel, BorderLayout.NORTH);
        panel.add(competitorComboBox, BorderLayout.NORTH);
        panel.add(scoresLabel, BorderLayout.WEST);
        panel.add(scoresField, BorderLayout.CENTER);
        panel.add(updateScoresButton, BorderLayout.EAST);
        panel.add(scrollPane, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createViewTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Creating a copy of the original competitor list to preserve the original order
        ArrayList<Competitor> sortedByName = new ArrayList<>(competitors);
        ArrayList<Competitor> sortedByOverallScore = new ArrayList<>(competitors);

        // Sorting the copies based on different criteria
        Collections.sort(sortedByName, Comparator.comparing(Competitor::getCompetitorName));
        Collections.sort(sortedByOverallScore, Comparator.comparing(Competitor::getOverallScore).reversed());

        // Table to display sorted data
        JTable table = new JTable(createTableModel(sortedByName)); // Default: sorted by name
        JScrollPane scrollPane = new JScrollPane(table);

        // Buttons to switch sorting criteria
        JButton sortByNamesButton = new JButton("Sort by Name");
        JButton sortByScoreButton = new JButton("Sort by Overall Score");

        sortByNamesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                table.setModel(createTableModel(sortedByName));
            }
        });

        sortByScoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                table.setModel(createTableModel(sortedByOverallScore));
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(sortByNamesButton);
        buttonPanel.add(sortByScoreButton);

        panel.add(buttonPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private DefaultTableModel createTableModel(ArrayList<Competitor> competitors) {
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"Competitor Number", "Name", "Country", "Overall Score"}, 0);

        for (Competitor competitor : competitors) {
            Object[] rowData = {
                    competitor.getCompetitorNumber(),
                    competitor.getCompetitorName(),
                    competitor.getCountry(),
                    competitor.getOverallScore()
            };
            model.addRow(rowData);
        }

        return model;
    }
    private JPanel createRemoveCompetitorPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JLabel enterNumberLabel = new JLabel("Enter Competitor Number: ");
        JTextField numberField = new JTextField(10);
        JButton removeCompetitorButton = new JButton("Remove Competitor");
        JTextArea detailsArea = new JTextArea(10, 30);
        detailsArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(detailsArea);

        removeCompetitorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = numberField.getText().trim();
                try {
                    int competitorNumber = Integer.parseInt(input);

                    // Find competitor by number
                    Competitor selectedCompetitor = findCompetitorByNumber(competitors, competitorNumber);
                    if (selectedCompetitor != null) {
                        // Remove the competitor
                        competitors.remove(selectedCompetitor);
                        detailsArea.setText("Competitor removed successfully.");
                    } else {
                        detailsArea.setText("Competitor not found.");
                    }
                } catch (NumberFormatException ex) {
                    detailsArea.setText("Please enter a valid competitor number.");
                }
            }
        });

        JPanel inputPanel = new JPanel();
        inputPanel.add(enterNumberLabel);
        inputPanel.add(numberField);
        inputPanel.add(removeCompetitorButton);

        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createViewDetailsPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));

        JLabel enterNumberLabel = new JLabel("Enter Competitor Number: ");
        JTextField numberField = new JTextField(10);
        JButton viewFullDetailsButton = new JButton("View Full Details");
        JButton viewShortDetailsButton = new JButton("View Short Details");
        JButton removeCompetitorButton = new JButton("Remove Competitor");
        JButton editDetailsButton = new JButton("Edit Competitor Details");

        JTextArea detailsArea = new JTextArea(10, 30);
        detailsArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(detailsArea);

        inputPanel.add(enterNumberLabel);
        inputPanel.add(numberField);
        inputPanel.add(viewFullDetailsButton);
        inputPanel.add(viewShortDetailsButton);
        inputPanel.add(removeCompetitorButton);
        inputPanel.add(editDetailsButton);

        JPanel editPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        editPanel.setBorder(BorderFactory.createTitledBorder("Edit Competitor Details"));
        JTextField nameField = new JTextField(20);
        JTextField countryField = new JTextField(20);
        editPanel.add(new JLabel("Name:"));
        editPanel.add(nameField);
        editPanel.add(new JLabel("Country:"));
        editPanel.add(countryField);
        editPanel.add(new JLabel(""));
        editPanel.add(editDetailsButton);

        viewFullDetailsButton.addActionListener(e -> {
            String input = numberField.getText().trim();
            try {
                int competitorNumber = Integer.parseInt(input);
                Competitor selectedCompetitor = findCompetitorByNumber(competitors, competitorNumber);
                if (selectedCompetitor != null) {
                    detailsArea.setText(selectedCompetitor.getFullDetails());
                    nameField.setText(selectedCompetitor.getCompetitorName());
                    countryField.setText(selectedCompetitor.getCountry());
                } else {
                    detailsArea.setText("Competitor not found.");
                }
            } catch (NumberFormatException ex) {
                detailsArea.setText("Please enter a valid competitor number.");
            }
        });

        viewShortDetailsButton.addActionListener(e -> {
            String input = numberField.getText().trim();
            try {
                int competitorNumber = Integer.parseInt(input);
                Competitor selectedCompetitor = findCompetitorByNumber(competitors, competitorNumber);
                if (selectedCompetitor != null) {
                    detailsArea.setText(selectedCompetitor.getShortDetails());
                } else {
                    detailsArea.setText("Competitor not found.");
                }
            } catch (NumberFormatException ex) {
                detailsArea.setText("Please enter a valid competitor number.");
            }
        });

        removeCompetitorButton.addActionListener(e -> {
            String input = numberField.getText().trim();
            try {
                int competitorNumber = Integer.parseInt(input);
                Competitor selectedCompetitor = findCompetitorByNumber(competitors, competitorNumber);
                if (selectedCompetitor != null) {
                    competitors.remove(selectedCompetitor);
                    detailsArea.setText("Competitor removed successfully.");
                } else {
                    detailsArea.setText("Competitor not found.");
                }
            } catch (NumberFormatException ex) {
                detailsArea.setText("Please enter a valid competitor number.");
            }
        });

        editDetailsButton.addActionListener(e -> {
            String input = numberField.getText().trim();
            try {
                int competitorNumber = Integer.parseInt(input);
                Competitor selectedCompetitor = findCompetitorByNumber(competitors, competitorNumber);
                if (selectedCompetitor != null) {
                    selectedCompetitor.setCompetitorName(nameField.getText());
                    selectedCompetitor.setCountry(countryField.getText());
                    detailsArea.setText("Competitor details updated successfully.");
                    detailsArea.setText(selectedCompetitor.getFullDetails());
                } else {
                    detailsArea.setText("Competitor not found.");
                }
            } catch (NumberFormatException ex) {
                detailsArea.setText("Please enter a valid competitor number.");
            }
        });

        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(editPanel, BorderLayout.SOUTH);

        return panel;
    }

    private Competitor findCompetitorByNumber(ArrayList<Competitor> competitors, int competitorNumber) {
        for (Competitor competitor : competitors) {
            if (competitor.getCompetitorNumber() == competitorNumber) {
                return competitor;
            }
        }
        return null;
    }


    private void writeCompetitorReport(String filePath) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            writer.write(String.format("%-20s %-20s %-20s %-20s%n", "Competitor Number", "Name", "Country", "Overall Score"));
            for (Competitor competitor : competitors) {
                String competitorData = String.format("%-20d %-20s %-20s %-20.2f%n",
                        competitor.getCompetitorNumber(),
                        competitor.getCompetitorName(),
                        competitor.getCountry(),
                        competitor.getOverallScore());
                writer.write(competitorData);
            }
            writer.close();
            System.out.println("Competitors' report written to: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new CompetitorGUI();
            }
        });
    }
}