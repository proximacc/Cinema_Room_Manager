package cinema;

import java.util.*;

enum Menu {
    MAIN_MENU, SHOW, BUY, EXIT, STATISTICS
}

public class Cinema {
    

    final static Scanner scanner = new Scanner(System.in);
    static Menu status = Menu.MAIN_MENU;
    static int income = 0;
    static int totalIncome = 0;
    static double totalIncomeD = 0;
    static int soldTickets = 0;
    static double percentage = 0;
    static double rowsD;
    static double seatsD;
    static boolean endBuy = true;

    public static void main(String[] args) {

        boolean end = true;

        System.out.println("Enter the number of rows:");
        int rows = scanner.nextInt();
        System.out.println("Enter the number of seats in each row:");
        int seats = scanner.nextInt();
        rowsD = rows;
        seatsD = seats;

        char[][] cinema = new char[rows][seats];

        CreateCinema(cinema, rows, seats);

        while(end) {
            Action();
            switch(status) {
                case BUY:
                    Buy(cinema, rows, seats);
                    break;
                case SHOW:
                    ShowCinema(cinema, rows, seats);
                    break;
                case EXIT:
                    end = false;
                    break;
                case MAIN_MENU:
                    Action();
                    break;
                case STATISTICS:
                    Statistics(rows,seats);
            }
        }
    }

    public static void Buy(char[][] cinema, int rows, int seats) {
        endBuy = true;
        while (endBuy) {
            System.out.println("Enter a row number:");
            int xRow = scanner.nextInt();
            System.out.println("Enter a seat number in that row:");
            int xSeat = scanner.nextInt();
            if (xRow > rows || xRow <= 0 || xSeat > seats || xSeat <= 0) {
                System.out.println("Wrong input!");
            } else if (cinema[xRow - 1][xSeat - 1] == 'B') {
                System.out.println("That ticket has already been purchased!");
            } else {
                cinema[xRow - 1][xSeat - 1] = 'B';
                soldTickets += 1;
                percentage += 100/(rowsD*seatsD);
                if (rows * seats <= 60) {
                    System.out.println("Ticket price: $10");
                    income += 10;
                } else {
                    if (xRow > rows / 2) {
                        System.out.println("Ticket price: $8");
                        income += 8;
                    } else {
                        System.out.println("Ticket price: $10");
                        income += 10;
                    }
                }
                endBuy = false;
                status = Menu.MAIN_MENU;
            }
        }
    }


    public static void ShowCinema(char[][] cinema, int rows, int seats) {
        System.out.print("Cinema:\n  ");
        for (int k = 0; k < seats; k++) {
            System.out.print((k + 1) + " ");
        }
        System.out.println();
        for (int i = 0; i < rows; i++) {
            System.out.print((i+1) + " ");
            for (int j = 0; j < seats; j++) {
                System.out.print(cinema[i][j] + " ");
            }
            System.out.println();
        }
        status = Menu.MAIN_MENU;
    }

    public static void CreateCinema(char[][] cinema, int rows, int seats) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < seats; j++) {
                cinema[i][j] = 'S';
            }
        }
    }

    public static void Action() {
        System.out.println("1. Show the seats\n2. Buy a ticket\n3. Statistics\n0. Exit");
        int command = scanner.nextInt();
        switch (command) {
            case 1:
                status = Menu.SHOW;
                break;
            case 2:
                status = Menu.BUY;
                break;
            case 3:
                status = Menu.STATISTICS;
                break;
            case 0:
                status = Menu.EXIT;
                break;
        }
    }

    public static void Statistics(int rows, int seats) {
        if (rows * seats <= 60) {
            totalIncome = rows * seats * 10;
        } else {
            if (rows % 2 != 0) {
                totalIncome = rows / 2 * seats * 10 + (rows / 2 + 1) * seats * 8;

            } else {
                totalIncome = rows / 2 * seats * 10 + rows / 2 * seats * 8;
            }
        }
        System.out.printf("%nNumber of purchased tickets: %d%nPercentage: %.2f%%%nCurrent income: $%d%nTotal income: $%d%n%n", soldTickets, percentage, income, totalIncome);
        status = Menu.MAIN_MENU;
    }
}