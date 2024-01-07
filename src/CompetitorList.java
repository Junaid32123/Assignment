import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
public class CompetitorList {
    private ArrayList<Competitor> competitorList;

    public CompetitorList() {
        competitorList = new ArrayList<>();
    }

    public void addCompetitor(Competitor competitor) {
        competitorList.add(competitor);
    }

    public ArrayList<Competitor> getCompetitors() {
        return competitorList;
    }
    public void displayCompetitorsDetailsToFile(BufferedWriter writer) throws IOException {
        for (Competitor competitor : competitorList) {
            writer.write(competitor.getFullDetails() + "\n");
            writer.write("--------------------------------------------\n");
        }
    }

    public Competitor getCompetitorWithHighestScore() {
        Competitor highestScorer = null;
        double maxOverallScore = Double.MIN_VALUE;

        for (Competitor competitor : competitorList) {
            double overallScore = competitor.getOverallScore();
            if (overallScore > maxOverallScore) {
                maxOverallScore = overallScore;
                highestScorer = competitor;
            }
        }

        return highestScorer;
    }

    public SummaryStatistics calculateSummaryStatistics() {
        double totalOverallScore = 0;
        double minOverallScore = Double.MAX_VALUE;
        double maxOverallScore = Double.MIN_VALUE;
        int numberOfCompetitors = competitorList.size();

        for (Competitor competitor : competitorList) {
            double overallScore = competitor.getOverallScore();
            totalOverallScore += overallScore;

            if (overallScore < minOverallScore) {
                minOverallScore = overallScore;
            }

            if (overallScore > maxOverallScore) {
                maxOverallScore = overallScore;
            }
        }

        double averageOverallScore = totalOverallScore / numberOfCompetitors;

        return new SummaryStatistics(totalOverallScore, averageOverallScore, minOverallScore, maxOverallScore);
    }

    public Map<Integer, Integer> generateScoreFrequencyReport() {
        Map<Integer, Integer> scoreFrequency = new HashMap<>();

        for (Competitor competitor : competitorList) {
            int[] scores = competitor.getScores();
            for (int score : scores) {
                scoreFrequency.put(score, scoreFrequency.getOrDefault(score, 0) + 1);
            }
        }

        return scoreFrequency;
    }

    public void generateFinalReport() {
        displayCompetitorsDetails(competitorList);

        Competitor highestScorer = getCompetitorWithHighestScore();
        System.out.println("\nCompetitor with the highest overall score:");
        if (highestScorer != null) {
            System.out.println(highestScorer.getFullDetails());
        } else {
            System.out.println("No competitors found.");
        }

        SummaryStatistics summaryStats = calculateSummaryStatistics();
        System.out.println("\nSummary Statistics:");
        System.out.println("Total Overall Score: " + summaryStats.getTotalOverallScore());
        System.out.println("Average Overall Score: " + summaryStats.getAverageOverallScore());
        System.out.println("Minimum Overall Score: " + summaryStats.getMinOverallScore());
        System.out.println("Maximum Overall Score: " + summaryStats.getMaxOverallScore());

        Map<Integer, Integer> scoreFrequency = generateScoreFrequencyReport();
        System.out.println("\nFrequency Report for Individual Scores:");
        for (Map.Entry<Integer, Integer> entry : scoreFrequency.entrySet()) {
            System.out.println("Score " + entry.getKey() + ": " + entry.getValue() + " times");
        }
    }

    private void displayCompetitorsDetails(ArrayList<Competitor> competitors) {
        for (Competitor competitor : competitors) {
            System.out.println(competitor.getFullDetails());
            System.out.println("--------------------------------------------");
        }
    }
}

class SummaryStatistics {
    private final double totalOverallScore;
    private final double averageOverallScore;
    private final double minOverallScore;
    private final double maxOverallScore;

    public SummaryStatistics(double totalOverallScore, double averageOverallScore,
                             double minOverallScore, double maxOverallScore) {
        this.totalOverallScore = totalOverallScore;
        this.averageOverallScore = averageOverallScore;
        this.minOverallScore = minOverallScore;
        this.maxOverallScore = maxOverallScore;
    }

    public double getTotalOverallScore() {
        return totalOverallScore;
    }

    public double getAverageOverallScore() {
        return averageOverallScore;
    }

    public double getMinOverallScore() {
        return minOverallScore;
    }

    public double getMaxOverallScore() {
        return maxOverallScore;
    }
}