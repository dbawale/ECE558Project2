package project2.dbawale.pdx.edu.quizlibrary;


import android.annotation.TargetApi;
import android.util.Pair; //This import statement is needed for the pair data structure that maps keys to values
import java.util.ArrayList;
import java.util.List;

/**
 * @author Deven Bawale         dbawale@pdx.edu
 *
 *
 */
public class Answers {

    /**
     * A list of key value pairs for answers
     */
    private List<Pair<String,String>> answers;

    /**
     * Default constructor for the Answers class
     */
    Answers(){
        answers = new ArrayList<>();
    }

    /**
     * Getter for the list of answers
     * @return The list of answers
     */
    public List<Pair<String, String>> getAnswers() {
        return answers;
    }

    /**
     * Setter for the list of answers
     * @param answers The list of answers
     */
    public void setAnswers(List<Pair<String, String>> answers) {
        this.answers = answers;
    }

    /**
     * Adds a single pair to the current list of answers
     * @param answer The pair to be added
     */
     void addAnswer(Pair<String,String> answer){
        if(answers!=null){
            answers.add(answer);
        }
    }


    /**
     * Overridden default method for printing answers in a pretty format
     * @return The formatted list of key value answers
     */
    @Override
    @TargetApi(value = 11)
    public String toString(){
        //A StringBuilder constructs a string and can handle different data types such as String or int
        StringBuilder sb = new StringBuilder();
        for (Pair<String, String> answer:
        this.answers){
            //sb.append(answer.getKey());
            sb.append(answer.first);
            sb.append(". ");
            //sb.append(answer.getValue());
            sb.append(answer.second);
            sb.append("\n");
        }
        //Before returning, we "convert" the StringBuilder to a string
        return sb.toString();
    }
}
