//"*" programming language.

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
    //Version
    static int MAJOR = 0;
    static int Minor = 0;
    static int micro = 0;
    static int nano = 0;

    public static String open(String filename) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            sb.append(line).append("\n");
        }
        String for_ret = sb.toString();
        br.close();
        return for_ret;
    }
    public static void version() {
        System.out.println(MAJOR + "." + Minor + "." + micro + "." + nano);
    }

    public static void main(String[] args) {
        System.out.println("\"*\" programing language, version:");
        version();

        boolean run = true;

        while (run) {

            Scanner scanner = new Scanner(System.in);
            System.out.print("Filename>>>");

            String fileName = scanner.nextLine();

            fileName = String.valueOf(Paths.get(fileName));

            try {
                String code = open(fileName);
                Handler hand = new Handler(code);

                System.out.print("debug? \"y\" for yes:");
                String a = scanner.nextLine();

                hand.run(a.equals("y"));
            } catch (IOException io) {
                run = false;
            }

        }
    }
}