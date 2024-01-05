public class Main {

    public static void main(String[] args) {
        // Create Competitor objects
        int[] scores1 = {5, 4, 5, 4, 3};
        Competitor competitor1 = new Competitor(100, "Keith John Talbot", "UK", "Novice", 21, scores1, "Some Category");

        int[] scores2 = {4, 3, 5, 2, 4};
        Competitor competitor2 = new Competitor(101, "Emily Parker", "USA", "Intermediate", 25, scores2, "Another Category");

        // Test methods for competitor1
        System.out.println("Competitor 1 Full Details:");
        System.out.println(competitor1.getFullDetails());
        System.out.println("\nCompetitor 1 Short Details:");
        System.out.println(competitor1.getShortDetails());

        // Test methods for competitor2
        System.out.println("\nCompetitor 2 Full Details:");
        System.out.println(competitor2.getFullDetails());
        System.out.println("\nCompetitor 2 Short Details:");
        System.out.println(competitor2.getShortDetails());

        // Testing getScoreArray method
        System.out.println("\nCompetitor 1 Scores:");
        int[] scoresOfCompetitor1 = competitor1.getScoreArray();
        for (int score : scoresOfCompetitor1) {
            System.out.print(score + " ");
        }
        System.out.println();

        System.out.println("\nCompetitor 2 Scores:");
        int[] scoresOfCompetitor2 = competitor2.getScoreArray();
        for (int score : scoresOfCompetitor2) {
            System.out.print(score + " ");
        }
        System.out.println();

        // Testing getOverallScore method
        System.out.println("\nCompetitor 1 Overall Score: " + competitor1.getOverallScore());
        System.out.println("Competitor 2 Overall Score: " + competitor2.getOverallScore());
    }
}
