package bullscows;

import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        //вводим длину угадываемого числа и проверяем, чтобы была меньше 11
        System.out.println("Enter length of the secret code:");
        int length = scanner.nextInt();
        while (length > 10) {
            System.out.println("Error: can't generate a secret number with a length of 11 because there aren't enough unique digits. Re-enter:");
            length = scanner.nextInt();
        }
        System.out.println("Enter the number of possible symbols in the code:");
        int lengthSymbols = scanner.nextInt();
        while (lengthSymbols > 36) {
            System.out.println("You must enter number ranging from 0 to 36");
            lengthSymbols = scanner.nextInt();
        }
        //(97-122) unicode utf-16 (a-z)top
        //создаем уникальную буквенно-числовую последовательность нужной длины
        String randomString = createString(lengthSymbols, length);
        //выводим сообщение о готовности начать игру
        if (lengthSymbols > 10) {
            System.out.printf("The secret is prepared: %s (0-9, a-%c)" +
                    "\nOkay, let's start a game!\n", "*".repeat(length), (char)(97 + lengthSymbols - 1 - 10));
        } else {
            System.out.printf("The secret is prepared: %s (0-%d)" +
                    "\nOkay, let's start a game!\n", "*".repeat(length), lengthSymbols - 1);
        }
        String check = "";
        scanner.nextLine();
        //даем пользователю возможность угадать число (пошла вода по трубам хы)
        int i = 1;
        while (!checking(randomString, check).equals("Grade: " + length + " bull(s)")) {
            System.out.printf("Turn %d:%n", i);
            check = scanner.nextLine();
            System.out.println(checking(randomString, check));
            i++;
        }
        System.out.println("Congratulations! You guessed the secret code");
    }

    //метод проверки уникальности буквенной последовательности
    public static boolean checkUniqueString(String string) {
        int[] letters = new int[150];
        for (int i = 0; i < string.length(); i++) {
            letters[string.charAt(i)]++;
            if (letters[string.charAt(i)] > 1) {
                return false;
            }
        }
        return true;
    }

    //48-57 -- 0-9 ASCII
    //метод, создающий уникальную буквенную последовательность нужной длины
    public static String createString(int lengthSymbols, int lengthString) {
        Random random = new Random((int)(Math.random() * 10));
        StringBuilder randomString = new StringBuilder();
        while (randomString.length() != lengthString) {
            if (random.nextDouble() > 0.5 && lengthSymbols > 10) {
                randomString.append((char)(random.nextInt(122 - 97 + 1) + 97));
            } else {
                randomString.append((char)(random.nextInt(57 - 48 + 1) + 48));
            }
        }
        while (!checkUniqueString(randomString.toString())) {
            randomString.delete(0, randomString.length());
            while (randomString.length() != lengthString) {
                if (random.nextDouble() > 0.5 && lengthSymbols > 10) {
                    randomString.append((char)(random.nextInt(122 - (26 - lengthSymbols) - 97 + 1) + 97));
                } else {
                    randomString.append((char)(random.nextInt(57 - 48 + 1) + 48));
                }
            }
        }
        return randomString.toString();
    }

    //метод проверки совпадения числа сгенерированного и числа введенного пользователем
    public static String checking(String input, String userString) {
        int bulls = 0;
        int cows = 0;
        //собственно считаем бычков и коровок
        for (int i = 0; i < input.length(); i++) {
            for (int j = 0; j < userString.length(); j++) {
                if (userString.charAt(j) == input.charAt(i)) {
                    if (i == j) {
                        bulls++;
                    } else {
                        cows++;
                    }
                }
            }
        }
        //выводим информацию о количестве рогатых в стаде
        if (bulls != 0 && cows != 0) {
            return "Grade: " + bulls + " bull(s) and " + cows + " cow(s)";
        } else if (bulls != 0) {
            return "Grade: " + bulls + " bull(s)";
        } else if (cows != 0) {
            return "Grade: " + cows + " cow(s)";
        } else {
            return "None";
        }
    }
}
