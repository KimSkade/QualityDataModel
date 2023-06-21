package QualityData;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DataInput {
    public static String getValueFromRowAndColumn(String dateipfad, int zeilennummer, int spaltennummer) {
        try (BufferedReader br = new BufferedReader(new FileReader(dateipfad))) {
            Map<Integer, String[]> zeilenMap = new HashMap<>(); // HashMap zur Speicherung der Zeilen

            String zeile;
            int aktuelleZeilennummer = 0;

            while ((zeile = br.readLine()) != null) {
                String[] spalten = zeile.split("\t");
                zeilenMap.put(aktuelleZeilennummer, spalten);
                aktuelleZeilennummer++;
            }

            if (zeilenMap.containsKey(zeilennummer)) {
                String[] zeilen = zeilenMap.get(zeilennummer);

                if (spaltennummer < zeilen.length) {
                    return zeilen[spaltennummer];
                } else {
                    System.out.println("Spalte nicht gefunden");
                }
            } else {
                System.out.println("Zeile nicht gefunden");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String getValueFromTableByColumnAndRow(String dateipfad, String spaltenname, int zeilennummer) {
        try (BufferedReader br = new BufferedReader(new FileReader(dateipfad))) {
            Map<String, Integer> spaltenIndices = new HashMap<>(); // HashMap zur Speicherung der Spaltenindices
            String[] spaltennamen = null;

            String zeile;
            int aktuelleZeilennummer = 0;

            while ((zeile = br.readLine()) != null) {
                String[] spalten = zeile.split("\t");

                if (aktuelleZeilennummer == 0) {
                    // Spaltennamen in Zeile 0 speichern
                    spaltennamen = spalten;
                    for (int i = 0; i < spaltennamen.length; i++) {
                        spaltenIndices.put(spaltennamen[i], i);
                    }
                } else if (aktuelleZeilennummer == zeilennummer) {
                    // Gewünschte Zeile gefunden
                    Integer spaltenindex = spaltenIndices.get(spaltenname);
                    if (spaltenindex != null && spaltenindex < spalten.length) {
                        return spalten[spaltenindex];
                    }
                }

                aktuelleZeilennummer++;
            }

            if (spaltennamen == null) {
                System.out.println("Spaltennamen nicht gefunden");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void main(String[] args) {
        String dateipfad = "C:\\Users\\kim0_\\Desktop\\Masterarbeit\\PruefplanValidierungsbauteil1_16.txt";

        System.out.println(getValueFromRowAndColumn(dateipfad, 1, 12));

        System.out.println(getValueFromTableByColumnAndRow(dateipfad, "featureid", 1));
        System.out.println(getValueFromTableByColumnAndRow(dateipfad, "idsymbol", 3));
    }

}
