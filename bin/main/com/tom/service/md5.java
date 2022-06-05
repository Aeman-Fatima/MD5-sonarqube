package com.tom.service;

import java.util.Scanner;

class MD5 {

    static String toBinary(String message) {
        String str = message;
        String result = "";
        char[] messChar = str.toCharArray();

        for (int i = 0; i < messChar.length; i++) {
            result += Integer.toBinaryString(messChar[i]);
        }

        return result;
    }

    static String padding(String message) {

        String result = toBinary(message);

        System.out.println("result: " + result);
        System.out.println(result.length());
        int resultL = result.length();

        String messageLength = toBinary(Integer.toString(message.length()));

        while (result.length() % 512 != 448) {
            if (result.length() == resultL)
                result += '1';
            else
                result += '0';
        }
        System.out.println("\n++++++++++++++++++++Step 1:++++++++++++++++++++\n");
        System.out.println("Padded Message in binary\n: " + result);
        int i = 0;
        while (i < (64 - messageLength.length())) {
            result += '0';
            i++;
        }

        result += messageLength;

        System.out.println("\n++++++++++++++++++++Step 2:++++++++++++++++++++\n");
        System.out.println("Padded Message in binary + 64 bits\n: " + result);

        return result;
    }

    static String[] chunks(String message, int p) {
        int n = message.length() / p;
        String[] equalStr = new String[n];
        int temp = 0;
        for (int i = 0; i < message.length(); i = i + p) {
            String part = message.substring(i, i + p);
            equalStr[temp] = part;
            temp++;
        }

        System.out.println("\n++++++++++++++++++++Step 3:++++++++++++++++++++\n");
        System.out.println("Divided in " + n + " blocks\n: ");

        for (int i = 0; i < equalStr.length; i++) {
            System.out.println("Block " + i + 1 + "  \n" + equalStr[i]);
        }

        return equalStr;
    }

    static int blocks(String message) {
        return message.length() / 5;
    }

    static long F(long B, long C, long D, int t) {
        long unsigned = 0;
        if (t >= 0 && t <= 19) {
            unsigned = ((B) & (C) | ((~B) & (D)));
        }
        if ((t >= 20 && t <= 39) || (t >= 60 && t <= 79)) {
            unsigned = ((B) ^ (C) ^ (D));
        }
        if (t >= 40 && t <= 59) {
            unsigned = ((B) & (C)) | ((B) & (C)) | ((C) & (D));
        }
        return (unsigned);
    }

    static long K(int t) {
        long k = 0;
        if (t >= 0 && t <= 19)
            k = 0x5A827999;
        else if (t <= 39)
            k = 0x6ED9EBA1;
        else if (t <= 59)
            k = 0x8F1BBCDC;
        else if (t <= 79)
            k = 0xCA62C1D6;
        return k;
    }

    public static void main(String[] args) {
        int A, B, C, D, E;
        int h0 = 0x67452301, h1 = 0xEFCDAB89, h2 = 0x98BADCFE,
                h3 = 0x10325476, h4 = 0xC3D2E1F0;
        String messageStr, paddedM;
        int block;
        Scanner msg = new Scanner(System.in);

        System.out.println("Enter Message");

        messageStr = msg.nextLine();

        // message = messageStr.length();

        paddedM = padding(messageStr);

        block = paddedM.length() / 512;

        String chunks[] = chunks(paddedM, 512);

        String[] W = new String[80];

        for (int k = 1; k <= block; k++) {
            int t = 0;

            System.out.println("\n\nFunctiong of Block " + k);

            A = h0;
            B = h1;
            C = h2;
            D = h3;
            E = h4;

            int temp = 0;
            System.out.println("\n++++++++++++++++++++Step 4:++++++++++++++++++++\n");
            System.out.println("Divided in 80 blocks\n: ");

            for (int j = 0; j < chunks[k - 1].length(); j = j + 32) {
                String part = chunks[k - 1].substring(j, j + 32);
                W[temp] = part;
                System.out.println(temp + "  " + W[temp]);
                temp++;
            }
            for (int e = 16; e <= 79; e++) {
                W[e] = Long.toBinaryString(((Long.parseLong((W[e - 3]), 2))
                        ^ (Long.parseLong((W[e - 8]), 2))
                        ^ (Long.parseLong((W[e - 14]), 2))
                        ^ (Long.parseLong((W[e - 16]), 2))) << 1);
                System.out.println(e + "  " + W[e]);
            }

            for (; t <= 79; t++) {

                long TEMP = (A << 5) + F(B, C, D, t) + E + Long.parseLong((W[t]), 2) + K(t);
                E = D;
                D = C;
                C = (B << 30);
                B = A;
                A = (int) TEMP;

            }

            h0 = h0 + A;
            h1 = h1 + B;
            h2 = h2 + C;
            h3 = h3 + D;
            h4 = h4 + E;

        }

        System.out.println("\n++++++++++++++++++++FUNCTIONING++++++++++++++++++++\n");

        System.out.println("Hash: " + String.format("%08X", h0) + String.format("%08X", h1)
                + String.format("%08X", h2) + String.format("%08X", h3) + String.format("%08X", h4));

    }
}