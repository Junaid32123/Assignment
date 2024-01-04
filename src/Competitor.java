public class Competitor {
    private int competitorNumber;
    private String CompetitorName;
    private String country;
    private String level;
    private int age;
    private int[] scores;
    private String category;

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

    public String getGetCompetitorName() {

        return CompetitorName;
    }

    public void setGetCompetitorName(String getCompetitorName) {

        this.CompetitorName = getCompetitorName;
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

    public Competitor(int competitorNumber, String CompetitorName, String country, String level, int age, int[] scores, String category) {
        this.competitorNumber = competitorNumber;
        this.CompetitorName = CompetitorName;
        this.country = country;
        this.level = level;
        this.age = age;
        this.scores = scores;
        this.category = category;
    }

    public double getOverallScore()
    {return 5;}

    public String getFullDetails() {
        String details = "Competitor Number " + competitorNumber + ", Competitor Name " + CompetitorName + ", country " + country + ".\n";
        details += CompetitorName + " is a " + category + " aged " + age + " and has an overall score of " + OverallScore + ".";
        return details;
    }

    public String getShortDetails() {
        return "CN " + competitorNumber + " (" + getInitials() + ") has overall score " + OverallScore + ".";
    }
}
