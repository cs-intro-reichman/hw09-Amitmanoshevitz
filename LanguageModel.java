import java.util.HashMap;
import java.util.Random;

public class LanguageModel {

    // The map of this model.
    // Maps windows to lists of charachter data objects.
    HashMap<String, List> CharDataMap;
    
    // The window length used in this model.
    int windowLength;
    
    // The random number generator used by this model. 
	private Random randomGenerator;

    /** Constructs a language model with the given window length and a given
     *  seed value. Generating texts from this model multiple times with the 
     *  same seed value will produce the same random texts. Good for debugging. */
    public LanguageModel(int windowLength, int seed) {
        this.windowLength = windowLength;
        randomGenerator = new Random(seed);
        CharDataMap = new HashMap<String, List>();
    }

    /** Constructs a language model with the given window length.
     * Generating texts from this model multiple times will produce
     * different random texts. Good for production. */
    public LanguageModel(int windowLength) {
        this.windowLength = windowLength;
        randomGenerator = new Random();
        CharDataMap = new HashMap<String, List>();
    }

    /** Builds a language model from the text in the given file (the corpus). */
	public void train(String fileName) {
        In in = new In(fileName);
        int i = 0;
        StringBuilder window1 = new StringBuilder();

        while (i<windowLength && in.isEmpty()==false){
            i++;
            char c = in.readChar();
            window1.append(c);}
    
        while (in.isEmpty()==false) {
        char nchar = in.readChar();
            
        if (window1.length() >= windowLength) {
             String curWin = window1.toString();
            List probs = CharDataMap.getOrDefault(curWin, new List());
            probs.update(nchar);
            CharDataMap.put(curWin, probs);    
            window1.deleteCharAt(0);
            window1.append(nchar);
        }

        if (window1.length() < windowLength) {
                window1.append(nchar);}}
        
        for (List list : CharDataMap.values()) {
            calculateProbabilities(list);
        }
    }

    // Computes and sets the probabilities (p and cp fields) of all the
	// characters in the given list. */
	public void calculateProbabilities(List probs) 
    {				
	    double cpCounter = 0;
        double AllCp = 0.0;
        int i = 0;
        while(i<probs.getSize()) 
        {
            cpCounter += probs.get(i).count;
            i++;
        }
        i=0;
        while (i<probs.getSize()) 
        {
            probs.get(i).p= probs.get(i).count/cpCounter;
            AllCp = AllCp + probs.get(i).p;
            probs.get(i).cp = AllCp;
            i++;
        }
    
    }

    // Returns a random character from the given probabilities list.
	public char getRandomChar(List probs) {
		
        // Your code goes here
        double randomNum = randomGenerator.nextDouble();
        ListIterator iterator = probs.listIterator(0);
        while (iterator.hasNext()) {
            CharData thisCD = iterator.next();
            if (randomNum < thisCD.cp) {
                return thisCD.chr;
            }

            
                    }
        return probs.get(probs.getSize()-1).chr;

    }

    /**
	 * Generates a random text, based on the probabilities that were learned during training. 
	 * @param initialText - text to start with. If initialText's last substring of size numberOfLetters
	 * doesn't appear as a key in Map, we generate no text and return only the initial text. 
	 * @param numberOfLetters - the size of text to generate
	 * @return the generated text
	 */
	public String generate(String initialText, int textLength) {
        StringBuilder genText = new StringBuilder(initialText);
        while (genText.length() < textLength) {
            String curWindow = genText.substring(Math.max(0, genText.length() - windowLength));
            if (!CharDataMap.containsKey(curWindow)) {
                break;
            }
            char nChar = getRandomChar(CharDataMap.get(curWindow));
            genText.append(nChar);
        }
    
        if (genText.length() > textLength) {
            return genText.substring(0, textLength);
        } else {
            return genText.toString();
        }
    }

    /** Returns a string representing the map of this language model. */
	public String toString() {
		StringBuilder str = new StringBuilder();
		for (String key : CharDataMap.keySet()) {
			List keyProbs = CharDataMap.get(key);
			str.append(key + " : " + keyProbs + "\n");
		}
		return str.toString();
	}

    public static void main(String[] args) {
		// Your code goes here
    }
}
