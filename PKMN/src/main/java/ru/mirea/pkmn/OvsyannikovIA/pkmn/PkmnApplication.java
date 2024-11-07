package ru.mirea.pkmn.OvsyannikovIA.pkmn;

import ru.mirea.pkmn.Card;
import web.http.PkmnHttpClient;
import web.jdbc.DatabaseServiceImpl;

public class PkmnApplication {
    public static void main(String[] args) throws Exception {
        //Card myCard = ClassImport.importCardFromTxt("src\\main\\resources\\my_card.txt");
        //CardExport.cardExportByte(myCard);
        //Card myCard1 = ClassImport.cardImportByte("src\\main\\resources\\Pyroar.crd");
        //System.out.println(myCard);
        //System.out.println(myCard1);


        PkmnHttpClient httpClient = new PkmnHttpClient();
        DatabaseServiceImpl db = new DatabaseServiceImpl();

        Card myCard = ClassImport.importCardFromTxt("src/main/resources/my_card.txt");
        ClassImport.updateAttackSkill(myCard, httpClient);

        db.saveCardToDatabase(myCard);
    }
}
