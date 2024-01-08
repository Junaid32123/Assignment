import java.util.Arrays;

public class DancerCompetitor extends Competitor {
    private String danceStyle;
    private int experienceYears;

    public DancerCompetitor(int competitorNumber,String firstName, String lastName, String country,String gender, int age, int[]scores,
                            String danceStyle, int experienceYears) {
        super(competitorNumber, firstName, lastName, gender, age, country, scores);
        this.danceStyle = danceStyle;
        this.experienceYears = experienceYears;
    }

    public String getDanceStyle() {
        return danceStyle;
    }

    public void setDanceStyle(String danceStyle) {
        this.danceStyle = danceStyle;
    }

    public int getExperienceYears() {
        return experienceYears;
    }

    public void setExperienceYears(int experienceYears) {
        this.experienceYears = experienceYears;
    }

    @Override
    public double getOverallScore() {
        return getTotalScore();
    }

    @Override
    public String getFullDetails() {
        StringBuilder builder = new StringBuilder();
        for (int score : getScores()){
            if (!builder.isEmpty()){
                builder.append(", ");
            }

            builder.append(Integer.toString(score));
        }

        // Prepare full details for the dancer competitor
        return "Dancer Competitor Details:\n" +
                "Competitor Number: " + getCompetitorNumber() + "\n" +
                "Name: " + getCompetitorName() + "\n" +
                "Country: " + getCountry() + "\n" +
                "Age: " + getAge() + "\n" +
                "Dance Style: " + danceStyle + "\n" +
                "Experience Years: " + experienceYears + "\n" +
                "Overall Score: " + getOverallScore() + "\n" +
                "Scores: " + builder.toString();
    }

    @Override
    public String getShortDetails() {
        double overallScore = getOverallScore();
        return "CN " + getCompetitorNumber() + " (" + getInitials() + ") has overall score " + overallScore + ".";
    }

    public void setScores(int[] scores) {
        super.setScores(scores);
    }
}