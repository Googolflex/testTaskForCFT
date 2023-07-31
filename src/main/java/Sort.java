import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import static java.lang.Integer.parseInt;

public class Sort {
    public static void main(String[] args) {
        String mode = "-a";
        int pointer = 0;
        if(args[0].equals("-a") || args[0].equals("-d")){
            mode = args[0];
            pointer++;
        }
        if(args[pointer].equals("-i")) {
            ArrayList<Integer> arrayToSort = new ArrayList<>();
            ArrayList<String> tempArray;
            for (int i = pointer + 2; i < args.length; i++) {
                tempArray = fileRead(args[i]);
                arrayToSort = merge(arrayToSort, srtArrayToInt(tempArray), mode);
            }
            arrayToSort = msort(arrayToSort, mode);
            fileWrite(intArrayToSrt(arrayToSort), args[pointer+1]);
        } else if (args[pointer].equals("-s")) {
            ArrayList<String> arrayToSort = new ArrayList<>();
            ArrayList<String> tempArray;
            for(int i = pointer + 2; i < args.length; i++) {
                tempArray = fileRead(args[i]);
                arrayToSort = mergeStr(arrayToSort, tempArray, mode);
            }
            arrayToSort = msortStr(arrayToSort, mode);
            fileWrite(arrayToSort, args[pointer+1]);
        } else {
            System.out.println("Введены некорректные параметры");
        }
    }
    public static ArrayList<Integer> merge(ArrayList<Integer> first, ArrayList<Integer> second, String mode){
        int p_a = 0, p_b = 0;
        ArrayList<Integer> res = new ArrayList<>();
        if(mode.equals("-a")) {
            while (p_a < first.size() && p_b < second.size()) {
                if (first.get(p_a) < second.get(p_b)) {
                    res.add(first.get(p_a));
                    p_a++;
                } else {
                    res.add(second.get(p_b));
                    p_b++;
                }
            }
            while (p_a < first.size()) {
                res.add(first.get(p_a));
                p_a++;
            }
            while (p_b < second.size()) {
                res.add(second.get(p_b));
                p_b++;
            }
        } else if (mode.equals("-d")) {
            while (p_a < first.size() && p_b < second.size()) {
                if (first.get(p_a) > second.get(p_b)) {
                    res.add(first.get(p_a));
                    p_a++;
                } else {
                    res.add(second.get(p_b));
                    p_b++;
                }
            }
            while (p_a < first.size()) {
                res.add(first.get(p_a));
                p_a++;
            }
            while (p_b < second.size()) {
                res.add(second.get(p_b));
                p_b++;
            }
        }
        return res;
        }
    public static ArrayList<Integer> msort(ArrayList<Integer> unsort, String mode){
        if(unsort.size()==0) {
            System.out.println("Введены некорректные данные");
            System.exit(0);
        }
        ArrayList<Integer> res;
        if(unsort.size() == 1) {
            res = unsort;
        } else {
            ArrayList<Integer> left = new ArrayList<>(unsort.subList(0, unsort.size()/2));
            ArrayList<Integer> right = new ArrayList<>(unsort.subList(unsort.size()/2, unsort.size()));
            res = merge(msort(left, mode), msort(right, mode), mode);
        }
        return res;
    }

    public static ArrayList<String> msortStr(ArrayList<String> unsort, String mode){
        if(unsort.size()==0) {
            System.out.println("Введены некорректные данные");
            System.exit(0);
        }
        ArrayList<String> res;
        if(unsort.size() == 1) {
            res = unsort;
        } else {
            ArrayList<String> left = new ArrayList<>(unsort.subList(0, unsort.size()/2));
            ArrayList<String> right = new ArrayList<>(unsort.subList(unsort.size()/2, unsort.size()));
            res = mergeStr(msortStr(left, mode), msortStr(right, mode), mode);
        }
        return res;
    }
    public static ArrayList<String> mergeStr(ArrayList<String> first, ArrayList<String> second, String mode){
        int p_a = 0, p_b = 0;
        ArrayList<String> res = new ArrayList<>();
        if(mode.equals("-a")) {
            while (p_a < first.size() && p_b < second.size()) {
                if (first.get(p_a).length() < second.get(p_b).length()) {
                    res.add(first.get(p_a));
                    p_a++;
                } else {
                    res.add(second.get(p_b));
                    p_b++;
                }
            }
            while (p_a < first.size()) {
                res.add(first.get(p_a));
                p_a++;
            }
            while (p_b < second.size()) {
                res.add(second.get(p_b));
                p_b++;
            }
        } else if (mode.equals("-d")) {
            while (p_a < first.size() && p_b < second.size()) {
                if (first.get(p_a).length() > second.get(p_b).length()) {
                    res.add(first.get(p_a));
                    p_a++;
                } else {
                    res.add(second.get(p_b));
                    p_b++;
                }
            }
            while (p_a < first.size()) {
                res.add(first.get(p_a));
                p_a++;
            }
            while (p_b < second.size()) {
                res.add(second.get(p_b));
                p_b++;
            }
        }
        return res;
    }

    public static ArrayList<Integer> srtArrayToInt(ArrayList<String> str) {
        ArrayList<Integer> res = new ArrayList<>();
        for (String s : str) {
            try {
                res.add(Integer.parseInt(s));
            } catch (NumberFormatException ignored) {

            }
        }
        return res;
    }

    public static ArrayList<String> intArrayToSrt(ArrayList<Integer> str) {
        ArrayList<String> res = new ArrayList<>();
        for (int i : str) {
            try {
                res.add(Integer.toString(i));
            } catch (NumberFormatException ignored) {

            }
        }
        return res;
    }

    public static ArrayList<String> fileRead(String fileName) {
        ArrayList<String> res = new ArrayList<>();
            try (Scanner scan = new Scanner(new FileReader(fileName))) {
                while (scan.hasNextLine()) {
                    res.add(scan.nextLine());
                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        return res;
    }

    public static void fileWrite(ArrayList<String> dataToWrite, String fileName) {
        try (FileWriter writer = new FileWriter(fileName, true)) {
            for(int i = 0; i < dataToWrite.size(); i++) {
                if(i+1 < dataToWrite.size()) {
                    writer.append(dataToWrite.get(i));
                    writer.append('\n');
                } else {
                    writer.append(dataToWrite.get(i));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
