import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Manager {
    public static void main(String[] args) {
        CompetitorGUI competitorGUI = new CompetitorGUI();
    }


    public ArrayList<Competitor> readCompetitorData(String filePath, String classType) {

        ArrayList<Competitor> competitors = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int lineNumber = 0;

            while ((line = br.readLine()) != null) {
                lineNumber++;
                String[] data = line.split(","); // Split by commas

                // Basic checks for missing fields
                if (data.length < 6) {
                    System.err.println("Error in line " + lineNumber + ": Missing fields");
                    continue;
                }

                try {
                    int competitorNumber = Integer.parseInt(data[0]);
                    String[] nameParts = data[1].split(" "); // Split name by spaces
                    String firstName = nameParts[0];
                    String lastName = nameParts.length > 1 ? nameParts[1] : ""; // Handle potential last name
                    int age = Integer.parseInt(data[2]);
                    String gender = data[3];
                    String country = data[4];


                    // Validate age range
                    if (age < 0 || age > 150) {
                        System.err.println("Error in line " + lineNumber + ": Invalid age");
                        continue;
                    }

                    // Validate gender
                    if (!gender.equalsIgnoreCase("Male") && !gender.equalsIgnoreCase("Female")) {
                        System.err.println("Error in line " + lineNumber + ": Invalid gender");
                        continue;
                    }

                    // Validate score range
                    int[] scores = new int[data.length - 7];
                    for (int i = 0; i < scores.length; i++) {
                        scores[i] = Integer.parseInt(data[i + 7]);
                        if (scores[i] < 0 || scores[i] > 100) {
                            System.err.println("Error in line " + lineNumber + ": Invalid score");
                            continue;
                        }
                    }

                    // Creating a Competitor object and adding it to the list
                    Competitor competitor;
                    if (classType.equals("Ice")) {
                        String skatingStyle = data[5];
                        int skillLevel = Integer.parseInt(data[6]);
                        competitor = new IceSkaterCompetitor(competitorNumber, firstName, lastName, country, gender, age, scores, skatingStyle, skillLevel);
                    } else {
                        String danceStyle = data[5];
                        int experienceYears = Integer.parseInt(data[6]);
                        competitor = new DancerCompetitor(competitorNumber, firstName, lastName, country, gender, age, scores, danceStyle, experienceYears);
                    }

                    competitors.add(competitor);

                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    System.err.println("Error in line " + lineNumber + ": Incorrect format");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return competitors;
    }
}