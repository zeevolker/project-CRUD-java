package CRUD;

import jdk.jshell.execution.Util;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Operasi {

    public static void updateData() throws IOException {

        // ambil database original
        File database = new File("database.txt");
        FileReader fileInput = new FileReader(database);
        BufferedReader bufferedInput = new BufferedReader(fileInput);

        // buat database sementara
        File tempDB = new File("tempDB.txt");
        FileWriter fileOutput = new FileWriter(tempDB);
        BufferedWriter bufferedOutput = new BufferedWriter(fileOutput);

        // tampilkan data
        System.out.println("List Buku");
        tampilkanData();

        // ambil user input / pilihan data
        Scanner terminalInput = new Scanner(System.in);
        System.out.print("\nMasukan nomor buku yang akan diupdate : ");
        int updateNum = terminalInput.nextInt();

        // tampilkan data yang ingin di update
        String data = bufferedInput.readLine();
        int entryCounts = 0;

        while (data != null){
            entryCounts++;
            StringTokenizer st = new StringTokenizer(data,",");

            // tampilkan entryCounts == updateNum
            if (updateNum == entryCounts){
                System.out.println("\ndata yang ingin di update adalah");
                System.out.println("--------------------------------");
                System.out.println("Referensi : " + st.nextToken());
                System.out.println("Tahun     : " + st.nextToken());
                System.out.println("Penulis   : " + st.nextToken());
                System.out.println("Penerbit  : " + st.nextToken());
                System.out.println("Judul     : " + st.nextToken());

                // update data
                String[] fieldData = {"tahun","penulis","penerbit","judul"};
                String[] tempData = new String[4];

                // refresh tokekn
                st = new StringTokenizer(data,",");
                String originalData = st.nextToken();

                for (int i = 0 ; i < fieldData.length; i++){
                    boolean isUpate = Utility.getYesorNo("apakah anda ingin merubah " + fieldData[i]);

                    originalData = st.nextToken();
                    if (isUpate){
                        // user input

                        if (fieldData[i].equalsIgnoreCase("tahun")){
                            System.out.print("masukan tahun terbit: ");
                            tempData[i] = Utility.ambilTahun();
                        } else {
                            terminalInput = new Scanner(System.in);
                            System.out.print("\nMasukan " + fieldData[i] + " baru : ");
                            tempData[i] = terminalInput.nextLine();
                        }
                    }else {
                        tempData[i] = originalData;
                    }
                }

                // tampilkan data
                st = new StringTokenizer(data,",");
                st.nextToken();
                System.out.println("\ndata bara anda adalah");
                System.out.println("--------------------------------");
                System.out.println("Tahun     : " + st.nextToken() + " ---> " + tempData[0]);
                System.out.println("Penulis   : " + st.nextToken() + " ---> " + tempData[1]);
                System.out.println("Penerbit  : " + st.nextToken() + " ---> " + tempData[2]);
                System.out.println("Judul     : " + st.nextToken() + " ---> " + tempData[3]);

                boolean isUpdate = Utility.getYesorNo("apakah anda yakin ingin mengupdate data tersebut");

                if (isUpdate){

                    // cek data baru di database
                    boolean isExist = Utility.cekBukuDiDatabase(tempData,false);

                    if (isExist){
                        System.out.println("update gagal, data buku sudah ada di databse");
                        bufferedOutput.write(data);
                    } else {

                        // format data baru ke database
                        String tahun = tempData[0];
                        String penulis = tempData[1];
                        String penerbit = tempData[2];
                        String judul = tempData[3];


                        // buat primary key
                        long nomerEntry = Utility.ambilEntryPerTahun(penulis,tahun) + 1;

                        String penulisTanpaSpasi = penulis.replaceAll("\\s","");
                        String primaryKey = penulisTanpaSpasi+"_"+tahun+"_"+nomerEntry;

                        // tulis data ke database
                        bufferedOutput.write(primaryKey + "," + tahun + "," + penulis + "," + penerbit + "," + judul);

                    }

                }else {
                    bufferedOutput.write(data);
                }

            } else {
                // copy data
                bufferedOutput.write(data);
            }

            bufferedOutput.newLine();
            data = bufferedInput.readLine();

        }

        // menulils data ke file
        bufferedOutput.flush();

        // menutup file
        bufferedOutput.close();
        fileOutput.close();
        bufferedInput.close();
        fileInput.close();

        // jalankan method
        System.gc();

        // delete original database
        database.delete();

        // rename file tempDB ke database
        tempDB.renameTo(database);

    }

    public static void deleteData() throws IOException{
        // ambil data original
        File database = new File("database.txt");
        FileReader fileInput = new FileReader(database);
        BufferedReader bufferedInput = new BufferedReader(fileInput);

        // buat database sementara
        File tempDB = new File("tempDB.txt");
        FileWriter fileOutput = new FileWriter(tempDB);
        BufferedWriter bufferedOutput = new BufferedWriter(fileOutput);

        // tampilkan data
        tampilkanData();

        // ambil user input untuk delete data
        Scanner terminalInput = new Scanner(System.in);
        System.out.print("masukan nomer buku yang akan dihapus: ");
        int deleteNum = terminalInput.nextInt();

        // looping membaca tiap baris data dan skip data yang didelete
        boolean isFound = false;
        int entryCounts = 0;
        String data = bufferedInput.readLine();

        while (data != null){
            entryCounts++;
            boolean isDelete = false;

            StringTokenizer st = new StringTokenizer(data,",");

            // tampilkan data yang ingin dihapus
            if (deleteNum == entryCounts){
                System.out.println("\ndata yang ingin anda hapus adalah");
                System.out.println("---------------------------------");
                System.out.println("Referensi : " + st.nextToken());
                System.out.println("Tahun     : " + st.nextToken());
                System.out.println("Penulis   : " + st.nextToken());
                System.out.println("Penerbit  : " + st.nextToken());
                System.out.println("Judul     : " + st.nextToken());

                isDelete = Utility.getYesorNo("apakah anda yakin akan mengahpus?");
                isFound = true;
            }
            if (isDelete){
                // skip pindahkan data dari original ke sementara
                System.out.println("data berhasil di hapus");
            }else {
                // pindahkan data dari original ke sementara
                bufferedOutput.write(data);
                bufferedOutput.newLine();
            }
            data = bufferedInput.readLine();
        }

        if (!isFound){
            System.err.println("nomor buku tidak ada");
        }

        // menulis data ke file
        bufferedOutput.flush();

        // menutup file
        bufferedOutput.close();
        fileOutput.close();
        bufferedInput.close();
        fileInput.close();

        // jalankan method
        System.gc();

        // delete original database
        database.delete();

        // rename file sementara menjadi database
        tempDB.renameTo(database);

    }

    public static void tampilkanData()throws IOException {
        FileReader fileInput;
        BufferedReader bufferInput;

        try {
            fileInput = new FileReader("database.txt");
            bufferInput = new BufferedReader(fileInput);
        }catch (Exception e){
            System.err.println("Database tidak ditemukan");
            System.err.println("Silahkan tambah data terlebih dahulu");
            tambahData();
            return;
        }

        System.out.println("\n-----------------------------------------------------------------------------------");
        System.out.println("| No |\tTahun |\tPenulis                |\tPenerbit     |\tJudul Buku        |");
        System.out.println("-----------------------------------------------------------------------------------");

        String data = bufferInput.readLine();
        int nomerData = 0;
        while (data != null) {
            nomerData++;

            StringTokenizer stringToken = new StringTokenizer(data,",");

            stringToken.nextToken();
            System.out.printf("| %2d ", nomerData);
            System.out.printf("|\t%4s  ", stringToken.nextToken());
            System.out.printf("|\t%-20s   ", stringToken.nextToken());
            System.out.printf("|\t%-10s   ", stringToken.nextToken());
            System.out.printf("|\t%-15s   |", stringToken.nextToken());
            System.out.println("\n");

            data = bufferInput.readLine();
        }
        System.out.println("-----------------------------------------------------------------------------------");
    }

    public static void cariData() throws IOException{
        // membaca database
        try {
            File file = new File("database.txt");
        }catch (Exception e){
            System.err.println("Database tidak ditemukan");
            System.err.println("Silahkan tambah data terlebih dahulu");
            tambahData();
            return;
        }

        // mengambil keywoard dari user
        Scanner terminalInput = new Scanner(System.in);
        System.out.print("Masukan kata kunci untuk mencari buku: ");
        String cariString = terminalInput.nextLine();
        String[] keywords = cariString.split("\\s+");

        // cek keywoard di database
        Utility.cekBukuDiDatabase(keywords,true);

    }

    public static void tambahData() throws IOException{

        FileWriter fiileOutput = new FileWriter("database.txt",true);
        BufferedWriter bufferOutput = new BufferedWriter(fiileOutput);

        Scanner terminalInput = new Scanner(System.in);
        String penulis, judul, penerbit, tahun;

        // mengambil input user
        System.out.print("masukan nama penulis: ");
        penulis = terminalInput.nextLine();
        System.out.print("masukan judul buku: ");
        judul = terminalInput.nextLine();
        System.out.print("masukan nama penerbit: ");
        penerbit = terminalInput.nextLine();
        System.out.print("masukan tahun terbit: ");
        tahun = Utility.ambilTahun();

        // cek buku di database
        String[] keywords = {tahun+","+penulis+","+penerbit+","+judul};
        System.out.println(Arrays.toString(keywords));

        boolean isExist = Utility.cekBukuDiDatabase(keywords,false);

        // menulis buku di database
        if (!isExist){
            long nomerEntry = Utility.ambilEntryPerTahun(penulis,tahun) + 1;

            String penulisTanpaSpasi = penulis.replaceAll("\\s","");
            String primaryKey = penulisTanpaSpasi+"_"+tahun+"_"+nomerEntry;

            System.out.println("\nData yang anda masukan");
            System.out.println("------------------------");
            System.out.println("primary key    : " + primaryKey);
            System.out.println("tahun terbit   : " + tahun);
            System.out.println("penulis        : " + penulis);
            System.out.println("media penerbit : " + penerbit);
            System.out.println("judul          : " + judul);
            System.out.println("------------------------");

            boolean isTambah = Utility.getYesorNo("apakah anda ingin menambah data tersebut?");
            if (isTambah){
                bufferOutput.write(primaryKey + "," + tahun + "," + penulis + "," + penerbit + "," + judul);
                bufferOutput.newLine();
                bufferOutput.flush();
            }

        }else {
            System.out.println("buku yang akan anda tambahkan sudah tersedia di datbase!");
            Utility.cekBukuDiDatabase(keywords,true);
        }

        bufferOutput.close();

    }
}
