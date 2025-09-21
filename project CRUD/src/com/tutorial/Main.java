package com.tutorial;

// import java library
import java.io.IOException;
import java.util.Scanner;

// import CRUD library
import CRUD.Operasi;
import CRUD.Utility;

public class Main {
    public static void main(String[] args) throws IOException {

        Scanner terminalInput = new Scanner(System.in);
        String pilihanUser;
        boolean isLanjutkan = true;

        while (isLanjutkan) {
            Utility.clearScreen();
            System.out.println("Database Perpustakaan\n");
            System.out.println("1.\tLihat seluruh buku");
            System.out.println("2.\tCari data buku");
            System.out.println("3.\tTambah data buku");
            System.out.println("4.\tUbah data buku");
            System.out.println("5.\tHapus data buku");

            System.out.print("\n\nPilihan anda: ");

            pilihanUser = terminalInput.next();

            switch (pilihanUser) {
                case "1": // tampilkan data
                    System.out.println("\n=================");
                    System.out.println("LIST SELURUH BUKU");
                    System.out.println("=================");
                    Operasi.tampilkanData();
                    break;
                case "2": // cari data
                    System.out.println("\n=========");
                    System.out.println("CARI BUKU");
                    System.out.println("=========");
                    Operasi.cariData();
                    break;
                case "3": // tambah data
                    System.out.println("\n================");
                    System.out.println("TAMBAH DATA BUKU");
                    System.out.println("================");
                    Operasi.tambahData();
                    Operasi.tampilkanData();
                    break;
                case "4": // ubah data
                    System.out.println("\n==============");
                    System.out.println("UBAH DATA BUKU");
                    System.out.println("==============");
                    Operasi.updateData();
                    break;
                case "5": // hapus data
                    System.out.println("\n===============");
                    System.out.println("HAPUS DATA BUKU");
                    System.out.println("===============");
                    Operasi.deleteData();
                    break;
                default:
                    System.err.println("\ninput anda tidak ditemukan\nsilahkan pilih [1-5]");
            }

            isLanjutkan = Utility.getYesorNo("Apakah anda ingin melanjutkan");
        }

    }
}