import java.util.Arrays;
public class Competitor {
    private int competitorNumber;
    private String competitorName;
    private String country;
    private String level;
    private int age;
    private int[] scores;
    private String category;
    private String gender;
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCategory() {

        return category;
    }

    public void setCategory(String category) {

        this.category = category;
    }

    public int getCompetitorNumber() {

        return competitorNumber;
    }

    public void setCompetitorNumber(int competitorNumber) {

        this.competitorNumber = competitorNumber;
    }

    public String getCompetitorName() {

        return competitorName;
    }

    public void setCompetitorName(String competitorName) {

        this.competitorName = competitorName;
    }

    public String getCountry() {

        return country;
    }

    public void setCountry(String country) {

        this.country = country;
    }

    public String getLevel() {

        return level;
    }

    public void setLevel(String level) {

        this.level = level;
    }

    public int getAge() {

        return age;
    }

    public void setAge(int age) {

        this.age = age;
    }

    public int[] getScores() {

        return scores;
    }

    public void setScores(int[] scores) {

        this.scores = scores;
    }

    public Competitor(int competitorNumber, String firstName, String lastName,String gender, int age, String country, int[] scores) {
        this.competitorNumber = competitorNumber;
        this.competitorName = firstName + " " + lastName;
        this.gender = gender;
        this.age = age;
        this.country = country;
        this.scores = scores;
    }

    public int[] getScoreArray() {

        return scores;
    }

    public double getOverallScore() {
        double overallScore = 0;
        int total = 0;
        for (int score : scores) {
            total += score;
        }
        overallScore = (double) total / scores.length;
        return overallScore;
    }

    public String getFullDetails() {
        String allScores = Arrays.toString(scores).replace("[", "").replace("]", "");
        double overallScore = getOverallScore();
        String details = "Competitor Number " + competitorNumber + ", Name: " + competitorName + ", Country: " + country + ".\n";
        details += competitorName + " is a " + gender + " aged " + age + " and received these scores: " + allScores + ".\n";
        details += "This gives them an overall score of " + overallScore + ".";
        return details;
    }

    public String getShortDetails() {
        double overallScore = getOverallScore();
        return "CN " + competitorNumber + " (" + getInitials() + ") has overall score " + overallScore + ".";
    }

    private String getInitials() {
        StringBuilder initials = new StringBuilder();
        String[] nameParts = competitorName.split(" ");
        for (String part : nameParts) {
            if (!part.isEmpty()) {
                initials.append(part.charAt(0));
            }
        }
        return initials.toString();
    }
}