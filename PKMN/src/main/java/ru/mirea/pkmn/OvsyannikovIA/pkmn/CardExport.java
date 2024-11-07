package ru.mirea.pkmn.OvsyannikovIA.pkmn;

import ru.mirea.pkmn.Card;

import java.io.*;

public class CardExport {
    public static void cardExportByte(Card a) throws IOException {

        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("src\\main\\resources\\"+ a.getName() + ".crd"));
        out.writeObject(a);
        out.close();
    }
}
