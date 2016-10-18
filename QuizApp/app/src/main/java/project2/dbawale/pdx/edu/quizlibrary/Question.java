package project2.dbawale.pdx.edu.quizlibrary;

/**
 * @author  Deven Bawale       dbawale@pdx.edu
 * The Question class. Each question has an {@link Answers Answers} object.
 * Each Question also has a string that stores the key to the correct answer
 */
public class Question {
    private String question;
    private Answers answers;
    private String correctanswer;

    /**
     * Getter for the question string
     * @return The question string
     */
    public String getQuestion() {
        return question;
    }

    /**
     * Setter for the question string
     * @param question The question string
     */
    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     * Getter for the correct answer
     * @return The correct answer
     */
    String getCorrectanswer() {
        return correctanswer;
    }

    /**
     * Setter for the correct answer
     * @param correctanswer The correct answer
     */
    public void setCorrectanswer(String correctanswer) {
        this.correctanswer = correctanswer;
    }

    /**
     * Getter for all the answers for the current question
     * @return The {@link Answers Answers} object
     */
    public Answers getAnswers() {
        return answers;
    }

    /**
     * Setter for the answers for the current question
     * @param answers All the answers for the current question
     */
    public void setAnswers(Answers answers) {
        this.answers = answers;
    }

    /**
     * Default constructor for Question class.
     * Calls the default constructor for Answers and sets Question string
     * and correct answer to empty strings.
     */
    Question(){
        this.question = "";
        this.answers = new Answers();
        this.correctanswer="";
    }

    /**
     * Parameterized constructor that sets the question
     * @param question The question string
     * @param answers The Answers for the question
     * @param correctans The correct answer for the question
     */
    Question(String question, Answers answers, String correctans){
        this.question = question;
        this.answers = answers;
        this.correctanswer = correctans;
    }

    /**
     * Checks if the current answer is the correct answer
     * @param currentanswer The current answer provided by the user
     * @return False if answer is incorrect, true if answer is correct
     */
    public Boolean isCorrectAnswer(String currentanswer){
        return this.correctanswer.equals(currentanswer);
    }

    /**
     * Overridden toString method for pretty-printing Questions
     * @return The whole Question along with answers, nicely formatted
     */
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(this.question);
        sb.append("\n");
        sb.append(this.answers.toString());
        return sb.toString();
    }

}
