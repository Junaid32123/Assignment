import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
public class CompetitorList extends ArrayList<Competitor> {
    public CompetitorList(){
        super();
    }

    public static CompetitorList from_array(ArrayList<Competitor> competitors){
        CompetitorList list = new CompetitorList();
        list.addAll(competitors);
        return list;
    }

    public ArrayList<Competitor> getCompetitors() {
        return this;
    }

    public Competitor by_id (int id){
        for (Competitor competitor : this) {
            if (competitor.getCompetitorNumber() == id) {
                return competitor;
            }
        }

        return null;
    }

    public void displayCompetitorsDetailsToFile(BufferedWriter writer) throws IOException {
        for (Competitor competitor : this) {
            writer.write(competitor.getFullDetails() + "\n");
            writer.write("--------------------------------------------\n");
        }
    }

    public Competitor getCompetitorWithHighestScore() {
        Competitor highestScorer = null;
        double maxOverallScore = Double.MIN_VALUE;

        for (Competitor competitor : this) {
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
        int numberOfCompetitors = this.size();

        for (Competitor competitor : this) {
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

        for (Competitor competitor : this) {
            int[] scores = competitor.getScores();
            for (int score : scores) {
                scoreFrequency.put(score, scoreFrequency.getOrDefault(score, 0) + 1);
            }
        }

        return scoreFrequency;
    }

    public void generateFinalReport() {
        displayCompetitorsDetails(this);

        Competitor highestScorer = getCompetitorWithHighestScore();
        System.out.println("\nCompetitor with the highest overall score:");
        if (highestScorer != null) {
            System.out.println(highestScorer.getFullDetails());
        } else {
            System.out.println("No competitors found.");
        }

        SummaryStatistics summaryStats = calculateSummaryStatistics();
        System.out.println("\nSummary Statistics:");
        System.out.println("Total Overall Score: " + summaryStats.totalOverallScore());
        System.out.println("Average Overall Score: " + summaryStats.averageOverallScore());
        System.out.println("Minimum Overall Score: " + summaryStats.minOverallScore());
        System.out.println("Maximum Overall Score: " + summaryStats.maxOverallScore());

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