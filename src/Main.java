import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;



public class Main {
    public static void main(String[] args) {
        Manager manager = new Manager();
        String filePath = "src/RunCompetitor.csv"; // Replace this with the path to your CSV file

        ArrayList<Competitor> competitors = manager.readCompetitorData(filePath);

        CompetitorList competitorList = new CompetitorList();
        competitorList.getCompetitors().addAll(competitors);

        String outputFilePath = "src/competitors_report.txt"; // Replace with your desired file name

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
            writer.write("Final Report:\n\n");

            // Table of competitors with full details
            writer.write("Table of Competitors with Full Details:\n");
            competitorList.displayCompetitorsDetailsToFile(writer);

            // Details of the competitor with the highest overall score
            writer.write("\nCompetitor with the highest overall score:\n");
            Competitor highestScorer = competitorList.getCompetitorWithHighestScore();
            if (highestScorer != null) {
                writer.write(highestScorer.getFullDetails() + "\n");
            } else {
                writer.write("No competitors found.\n");
            }

            // Summary statistics
            writer.write("\nSummary Statistics:\n");
            SummaryStatistics summaryStats = competitorList.calculateSummaryStatistics();
            writer.write("Total Overall Score: " + summaryStats.getTotalOverallScore() + "\n");
            writer.write("Average Overall Score: " + summaryStats.getAverageOverallScore() + "\n");
            writer.write("Minimum Overall Score: " + summaryStats.getMinOverallScore() + "\n");
            writer.write("Maximum Overall Score: " + summaryStats.getMaxOverallScore() + "\n");

            // Frequency report for individual scores
            writer.write("\nFrequency Report for Individual Scores:\n");
            Map<Integer, Integer> scoreFrequency = competitorList.generateScoreFrequencyReport();
            for (Map.Entry<Integer, Integer> entry : scoreFrequency.entrySet()) {
                writer.write("Score " + entry.getKey() + ": " + entry.getValue() + " times\n");
            }

            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter competitor number to view details: ");
            int inputCompetitorNumber = scanner.nextInt();

            Competitor selectedCompetitor = findCompetitorByNumber(competitors, inputCompetitorNumber);
            if (selectedCompetitor != null) {
                writer.write("\nShort Details for Competitor " + inputCompetitorNumber + ":\n");
                writer.write(selectedCompetitor.getShortDetails() + "\n");
            } else {
                writer.write("\nCompetitor " + inputCompetitorNumber + " not found.\n");
            }


            System.out.println("Final Report written to: " + outputFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static Competitor findCompetitorByNumber(ArrayList<Competitor> competitors, int competitorNumber) {
        for (Competitor competitor : competitors) {
            if (competitor.getCompetitorNumber() == competitorNumber) {
                return competitor;
            }
        }
        return null;
    }
}