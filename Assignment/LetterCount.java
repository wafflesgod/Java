public class LetterCount {
    public static void main(String[] args){
        //create array to store 100 random uppercase letters
        char[] chars = new char[100];

        System.out.println("The uppercase letters are: ");

        //fill the array with random uppercase letters
        for (int i = 0; i < chars.length; i++){
            chars[i] = RandomCharacter.getRandomUpperCaseLetter();
            System.out.print(chars[i] + " "); //display characters
        }
        
        //create array to count occurences of each letter
        int [] counts = new int[26]; //store counts of each letter

        //count each letter's occurences
        for (char c: chars){
            counts[c - 'A']++; //increment the count of the letter (e.g., 'A' -> 0, 'B' -> 1, etc.)
        }

        //display the counts
        System.out.println("\nThe occurences of each letter are: ");
        for (int i = 0; i < counts.length; i++){
            System.out.print(counts[i] + " " + (char) ('A' + i) + " "); //counts will show the number of times each letter appears
            // char will show the letter and adds space between the count and the letter
        }
    }
}