package CRUD;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Year;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Utility {

    static long ambilEntryPerTahun(String penulis, String tahun)throws IOException {
        FileReader fileInput = new FileReader("database.txt");
        BufferedReader bufferInput = new BufferedReader(fileInput);

        long entry = 0;
        String data = bufferInput.readLine();
        Scanner dataScanner;
        String primaryKey;

        while (data != null){
            dataScanner = new Scanner(data);
            dataScanner.useDelimiter(",");
            primaryKey = dataScanner.next();
            dataScanner = new Scanner(primaryKey);
            dataScanner.useDelimiter("_");

            penulis = penulis.replaceAll("\\s","");

            if (penulis.equalsIgnoreCase(dataScanner.next()) && tahun.equalsIgnoreCase(dataScanner.next())){
                entry = dataScanner.nextInt();
            }
            data = bufferInput.readLine();
        }
        return entry;
    }

    protected static String ambilTahun() throws IOException{

        boolean tahunValid = false;
        Scanner terminalInput = new Scanner(System.in);
        String inputTahun = terminalInput.nextLine();

        while (!tahunValid) {
            try {
                Year.parse(inputTahun);
                tahunValid = true;
            } catch (Exception e) {
                System.err.println("format tahun salah");
                System.out.print("masukan tahun terbit: ");
                tahunValid = false;
                inputTahun = terminalInput.nextLine();
            }
        }

        return inputTahun;

    }

    static boolean cekBukuDiDatabase(String[] keywords, boolean isDisplay)throws IOException{

        FileReader fileInput = new FileReader("database.txt");
        BufferedReader bufferInput = new BufferedReader(fileInput);

        String data = bufferInput.readLine();
        boolean isExist = false;
        int nomerData = 0;

        if (isDisplay) {
            System.out.println("\n-----------------------------------------------------------------------------------");
            System.out.println("| No |\tTahun |\tPenulis                |\tPenerbit     |\tJudul Buku        |");
            System.out.println("-----------------------------------------------------------------------------------");
        }

        while (data != null){

            // cek keywords dalam baris
            isExist = true;
            for (String keyword:keywords){
                isExist = isExist && data.toLowerCase().contains(keyword.toLowerCase());
            }

            // jika keywordsnya cocok maka tampilkan
            if (isExist){
                if (isDisplay) {
                    nomerData++;
                    StringTokenizer stringToken = new StringTokenizer(data, ",");

                    stringToken.nextToken();
                    System.out.printf("| %2d ", nomerData);
                    System.out.printf("|\t%4s  ", stringToken.nextToken());
                    System.out.printf("|\t%-20s   ", stringToken.nextToken());
                    System.out.printf("|\t%-10s   ", stringToken.nextToken());
                    System.out.printf("|\t%-15s   |", stringToken.nextToken());
                    System.out.println("\n");
                }else {
                    break;
                }
            }
            data = bufferInput.readLine();
        }
        if (isDisplay) {
            System.out.println("-----------------------------------------------------------------------------------");
        }
        return isExist;
    }

    public static boolean getYesorNo(String massage){
        Scanner terminalInput = new Scanner(System.in);
        System.out.print("\n" + massage + " (y/n): ");
        String pilihanUser = terminalInput.next();

        while (!pilihanUser.equalsIgnoreCase("y") && !pilihanUser.equalsIgnoreCase("n")){
            System.err.println("pilih y atau n");
            System.out.print("\n" + massage + " (y/n): ");
            pilihanUser = terminalInput.next();
        }

        return pilihanUser.equalsIgnoreCase("y");
    }

    public static  void clearScreen(){
        try {
            if (System.getProperty("os.name").contains("Windows")){
                new ProcessBuilder("cmd","/c","cls").inheritIO().start().waitFor();
            }else {
                System.out.println("\033\143");
            }
        }catch (Exception ex){
            System.err.println("tidak bisa clear screen");
        }
    }
}
