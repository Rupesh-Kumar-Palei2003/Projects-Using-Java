package NumberGuessing.com;
import java.util.*;
public class NumberGuessingGame {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Starting number  ");
        int start = sc.nextInt();
        System.out.println("Enter Ending number");
        int end = sc.nextInt();
        Random random = new Random();
        int store = random.nextInt(start, end);
        int count = 0;
        while (true) {System.out.println("Guess the  number between " + start + " and " + end);int guess = sc.nextInt();if (guess < store) {
            System.out.println("your number is low <<<<< ");
            count++;
        } else if (guess > store) {
            System.out.println("Your number is high >>>>>");
            count++;
        } else {
            System.out.println("Congratulation you choose the correct number....");
            break;
            }
        }
        if (count <= 5) System.out.println("nice....you guess only in...." + count + " attempt");
        else System.out.println("you guess in " + count + " attempt improve your IQ. ");
    }
}
