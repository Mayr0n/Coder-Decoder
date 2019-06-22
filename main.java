import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {

        try {
            File folder = new File("aDecoder/");
            File file = new File("aDecoder/code.txt");
            File decoded = new File("aDecoder/decoded.txt");
            if(!folder.exists()){
                folder.mkdir();
            }
            if(!file.exists()){
                file.createNewFile();
            }
            if(!decoded.exists()){
                decoded.createNewFile();
            }
            FileReader fr = new FileReader(file);
            FileWriter fw = new FileWriter(decoded);
            BufferedReader reader = new BufferedReader(fr);
            BufferedWriter writer = new BufferedWriter(fw);
            String code = reader.readLine();
            if(!code.contains("Code :") || code.length() != 9){
                System.out.println("La première ligne doit avoir la syntaxe : 'Code : <deuxLettres>' !");
            }
            else {
                char p = code.charAt(7);
                char s = code.charAt(8);
                List<String> lines = reader.lines().collect(Collectors.toList());
                List<Character> letters = new ArrayList<>(Arrays.asList('a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'));
                int i = 0;
                int pNum = 999;
                int sNum = 999;

                for(char letter : letters){
                    if(p == letter){
                        pNum = i;
                    }
                    if(s == letter){
                        sNum = i;
                    }
                    i++;
                }
                if(pNum != 999 && sNum != 999){
                    final int toChange = sNum - pNum;
                    i = 0;
                    for(String line : lines){ //s'exécute pour chaque ligne du fichier
                        for(char l : line.toCharArray()){ //s'exécute pour chaque lettre de la ligne "line"
                            if(l == ' ' || l == ',' || l == ';' || l == '\'' || l == '!' || l == '?' || l == '.'){ //si l est une ponctuation => réécrit la ponctuation telle qu'elle est
                                writer.write(l);
                            } else {
                                for(char letter : letters) { //si l est une lettre => teste chaque lettre du tableau letters[] afin de connaître le numéro de la lettre l dans l'alphabet
                                    if (l == letter) {
                                        int newLetNum = i + toChange;
                                        if(newLetNum < 0){
                                            newLetNum = 26 + newLetNum;
                                        }
                                        writer.write(letters.get(newLetNum));
                                        i = 0;
                                    }
                                    i++;
                                }
                                i = 0;
                            }
                        }
                        writer.write("\n");
                    }
                }
            }
            reader.close();
            fr.close();
            writer.close();
            fw.close();
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}
