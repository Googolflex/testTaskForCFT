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
            ArrayList<Integer> firstUnsortArray = new ArrayList<>();
            ArrayList<Integer> secondUnsortArray = new ArrayList<>();
            for (int i = pointer+2; i < args.length; i++) {
                try (Scanner scan = new Scanner(new FileReader(args[i]))) {
                    while (scan.hasNextLine()) {
                        String unit = scan.nextLine();
                        if(!unit.contains(" ") && isNumeric(unit)) {
                            secondUnsortArray.add(parseInt(unit));
                        }
                    }
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                firstUnsortArray = merge(firstUnsortArray, secondUnsortArray, mode);
                secondUnsortArray.clear();
            }
            firstUnsortArray = msort(firstUnsortArray, mode);
            try (FileWriter writer = new FileWriter(args[pointer+1], true)) {
                for(int i = 0; i < firstUnsortArray.size(); i++) {
                    if(i+1 < firstUnsortArray.size()) {
                        writer.append(firstUnsortArray.get(i).toString());
                        writer.append('\n');
                    } else {
                        writer.append(firstUnsortArray.get(i).toString());
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if (args[pointer].equals("-s")) {
            ArrayList<String> firstUnsortArray = new ArrayList<>();
            ArrayList<String> secondUnsortArray = new ArrayList<>();
            for (int i = pointer+2; i < args.length; i++) {
                try (Scanner scan = new Scanner(new FileReader(args[i]))) {
                    while (scan.hasNextLine()) {
                        secondUnsortArray.add(scan.nextLine());
                    }
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                firstUnsortArray = mergeStr(firstUnsortArray, secondUnsortArray, mode);
                secondUnsortArray.clear();
            }
            firstUnsortArray = msortStr(firstUnsortArray, mode);
            try (FileWriter writer = new FileWriter(args[pointer+1], true)) {
                for(int i = 0; i < firstUnsortArray.size(); i++) {
                    if(i+1 < firstUnsortArray.size()) {
                        writer.append(firstUnsortArray.get(i));
                        writer.append('\n');
                    } else {
                        writer.append(firstUnsortArray.get(i));
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
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

    public static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    }
