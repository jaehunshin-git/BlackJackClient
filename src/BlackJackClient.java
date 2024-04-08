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
    static String address ="192.168.0.91";
    static public PrintWriter out;
    static public BufferedReader in;
    static int card=0;


    public static void main(String[] args) {
        String msg;
        int newcard, dealer, choice=0;

        try (Socket socket = new Socket(address, inPort)) {
            out = new PrintWriter(socket.getOutputStream(), true);
            //
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // in 은 c 가 가지고 있는 서버의 출력을 읽는 객체

            card += getCard(in);
            while (choice != 3) {
                choice = options();
                out.println(""+choice);

                switch (choice) {
                    case 1:
                        newcard = getCard(in);
//                        System.out.println("new card is "+newcard);
                        card += newcard;    // 카드의 합을 계속 더해준다.

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
        int choice = sc.nextInt();  // Client가 Server에 접속하면 여기서 기다린다.

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
            msg = in.readLine();    // Client 가 Server 에 접속하면 여기서 기다리고있다.
        } catch (IOException e) { }
        int newcard = Integer.parseInt(msg);    // Server 가 넘겨준 카드를 int 형으로 바꾸고 그 카드를 반환한다.

        if (newcard == 11) {
            System.out.println("new card is Jack"); // 해당 Client 가 getCard() method 로 카드를 뽑으면 Server Console 에 출력한다.
            newcard = 10;
        }
        else if (newcard == 12) {
            System.out.println("new card is Queen"); // 해당 Client 가 getCard() method 로 카드를 뽑으면 Server Console 에 출력한다.
            newcard = 10;
        }
        else if (newcard == 13) {
            System.out.println("new card is King"); // 해당 Client 가 getCard() method 로 카드를 뽑으면 Server Console 에 출력한다.
            newcard = 10;
        }
        else {
            System.out.println("new card is "+newcard); // 해당 Client 가 getCard() method 로 카드를 뽑으면 Server Console 에 출력한다.
        }

        return newcard;
    }


}

