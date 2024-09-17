package StonePaperScissor.com;
import java.util.*;
public class StonePaperScissor {
        public static void continueGame() {
            String[] game = {"Stone 🪨🪨🪨", "Paper 📃📃📃", "Scissor ✂️✂️✂️"};
            Random randomGenerator = new Random();
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter how many rounds do you want to play 🤝🤝🤝");
            int rounds = sc.nextInt();
            int chooseUser, chooseComp, countUser = 0, countComp = 0;
            System.out.println("Enter into  the GAME: ");
            for (int i = 1; i <= rounds; i++) {
                System.out.println("Choose one option : \n 0. Stone 🪨🪨🪨 \t 1. Paper 📃📃📃 \t 2.Scissor ✂️✂️✂️");
                chooseUser = sc.nextInt();
                System.out.println("Your choice is :" + game[chooseUser]);
                chooseComp = randomGenerator.nextInt(game.length);
                System.out.println("Computer choice is :" + game[chooseComp]);
                if ((chooseUser == 0 && chooseComp == 2) || (chooseUser == 2 && chooseComp == 1) || (chooseUser == 1 && chooseComp == 0)) {
                    System.out.println("You are win 👦🏻👦🏻👦🏻");
                    countUser++;
                } else if (chooseUser == chooseComp) {
                    System.out.println("Tie 😶😶😶");
                } else {
                    System.out.println("computer is win 🤖🤖🤖");
                    countComp++;
                }
            }
            System.out.println("Final Result is.....🤩🤩🤩");
            if (countUser > countComp) {
                System.out.println("your score is " + countUser + " & the computer score is " + countComp);
                System.out.println("You are the winner 🥳🥳🥳 ");
            } else if (countUser < countComp) {
                System.out.println("your score is " + countUser + " & the computer score is " + countComp);
                System.out.println("computer is the winner 😏😏😏 and you loss your game 😭😭😭");
            } else {
                System.out.println("The match is tie 🤦🏻🤦🏻🤦🏻 at point " + countComp);
            }
        }

        public static void main(String[] args) {
            continueGame();
            while (true) {
                System.out.println("Do you want to continue choose one option 'Yes 🙋🏻‍♀️🙋🏻‍♀️🙋🏻‍♀️' , 'No 🙇🏻‍♂️🙇🏻‍♂️🙇🏻‍♂️‍' ");
                Scanner sc = new Scanner(System.in);
                String st = sc.nextLine();
                if (st.equalsIgnoreCase("yes"))
                    continueGame();
                else
                    break;
            }
        }
    }
