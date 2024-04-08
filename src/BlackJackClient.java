// Todo list
// 1. J,Q,K
// 2. improve dealer strategy
// 3. display history
// 4. split, insurance

import java.io.*;
import java.net.*;
import java.util.*;

class BlackJackClient {
    static int inPort = 9999;
    static String address ="localhost";
    static public PrintWriter out;
    static public BufferedReader in;
    static int card=0;


    public static void main(String[] args) {
        String msg;
        int newcard, dealer, choice=0;

        try (Socket socket = new Socket(address, inPort)) {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            card += getCard(in);
            while (choice != 3) {
                choice = options();
                out.println(""+choice);

                switch (choice) {
                    case 1:
                        newcard = getCard(in);
                        System.out.println("new card is "+newcard);
                        card += newcard;

                        if (card > 21) {
                            System.out.println(card+", Over 21! \n\n");
                            card = getCard(in);
                        }
                        if (card == 21) {
                            System.out.println(card+", BLACKJACK! \n\n");
                            card = getCard(in);
                        }
                        break;
                    case 2:
                        msg = in.readLine();
                        System.out.println("dealer has "+msg);
                        dealer = Integer.parseInt(msg);
                        if ((card > dealer) || (dealer > 21)) {
                            System.out.println("You win!\n\n");
                        }
                        else if (card < dealer) {
                            System.out.println("Dealer win!\n\n");
                        }
                        else {
                            System.out.println("tie! \n\n");
                        }

                        card = getCard(in);
                        break;
                    case 3:
                        System.out.println("Exit");
                        in.close();
                        out.close();
                        socket.close();
                        break;
                }
            }

        }
        catch (Exception e) {}
    }


    private static int options() {
        System.out.println("You have "+ card);
        System.out.println("	1. Hit");
        System.out.println("	2. Hold");
        System.out.println("	3. Exit");
        System.out.print("Choose an option: ");

        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();

        while ((choice < 1) || (choice > 3)) {
            System.out.println("Invalid input!");
            System.out.println("Please enter an integer value between 1 and 4.");
            System.out.println("	1. Hit");
            System.out.println("	2. Hold");
            System.out.println("	3. Exit");
            System.out.print("Choose an option: ");
            choice = sc.nextInt();
        }

        return choice;
    }


    public static int getCard(BufferedReader in) {
        String msg="";
        try {
            msg = in.readLine();
        } catch (IOException e) { }
        int newcard = Integer.parseInt(msg);
        return newcard;
    }


}

