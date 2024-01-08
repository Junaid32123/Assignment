public class IceSkaterCompetitor extends Competitor {
    private String skatingStyle;
    private int skillLevel;

    public IceSkaterCompetitor(int competitorNumber,String firstName, String lastName, String country,String gender, int age, int[]scores,
                               String skatingStyle, int skillLevel) {
        super(competitorNumber, firstName, lastName, gender, age, country, scores);
        this.skatingStyle = skatingStyle;
        this.skillLevel = skillLevel;
    }

    public String getSkatingStyle() {
        return skatingStyle;
    }

    public void setSkatingStyle(String skatingStyle) {
        this.skatingStyle = skatingStyle;
    }

    public int getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(int skillLevel) {
        this.skillLevel = skillLevel;
    }

    @Override
    public double getOverallScore() {
        double mult = 1;
        switch (skillLevel){
            default:
                mult = 1;
                break;
            case 4, 5, 6:
                mult = 1.1;
                break;
            case 7, 8:
                mult = 1.2;
                break;
            case 9:
                mult = 1.25;
                break;
            case 10:
                mult = 1.3;
                break;
        }

        return getTotalScore() * mult;
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

        // Prepare full details for the ice skater competitor
        return "Ice Skater Competitor Details:\n" +
                "Competitor Number: " + getCompetitorNumber() + "\n" +
                "Name: " + getCompetitorName() + "\n" +
                "Country: " + getCountry() + "\n" +
                "Age: " + getAge() + "\n" +
                "Skating Style: " + skatingStyle + "\n" +
                "Skill Level: " + skillLevel + "\n" +
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