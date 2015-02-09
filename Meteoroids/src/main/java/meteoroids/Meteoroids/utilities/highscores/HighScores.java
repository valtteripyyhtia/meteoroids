package meteoroids.Meteoroids.utilities.highscores;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import meteoroids.Meteoroids.utilities.RandomGenerator;

/**
 * High scores
 * 
 * @author vpyyhtia
 *
 */
public class HighScores {

    private List<Score> scores;
    
    private final String HIGHSCORES_PATH;
    
    public HighScores(final String PATH) {
        this.HIGHSCORES_PATH = PATH;
        scores = new ArrayList<>();
    }
    
    public boolean topTenCheck(long points) {
        loadScoreFile();
        Collections.sort(scores);
        if(scores == null || scores.size() == 0) {
            return true;
        }
        if(scores.size() < 10) {
            return true;
        }
        return points > scores.get(scores.size()-1).getPoints();
    }
    
    public List<Score> getScores() {
        loadScoreFile();
        Collections.sort(scores);
        return scores;
    }
    
    public void saveScore(Score score) {
        loadScoreFile();
        FileOutputStream scoresOutputStream = null;
        ObjectOutputStream scoresObjectStream = null;
        
        try {
            scoresOutputStream = new FileOutputStream(HIGHSCORES_PATH);
            scoresObjectStream = new ObjectOutputStream(scoresOutputStream);
            scores.add(score);
            scoresObjectStream.writeObject(scores);
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        } finally {
            if(scoresObjectStream != null) {
                try {
                    scoresObjectStream.flush();
                    scoresObjectStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void loadScoreFile() {
        FileInputStream scoresInputStream = null;
        ObjectInputStream scoresObjectStream = null;
        
        try {
            scoresInputStream = new FileInputStream(HIGHSCORES_PATH);
            scoresObjectStream = new ObjectInputStream(scoresInputStream);
            scores = (ArrayList<Score>)scoresObjectStream.readObject();
            Collections.sort(scores);
            if(scores.size() > 10) {
                scores = scores.subList(0, 10);
            }
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if(scoresObjectStream != null) {
                try {
                    scoresObjectStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
}