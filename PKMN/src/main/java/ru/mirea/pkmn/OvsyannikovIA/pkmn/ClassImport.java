package ru.mirea.pkmn.OvsyannikovIA.pkmn;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import ru.mirea.pkmn.*;
import web.http.PkmnHttpClient;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class ClassImport {
    public static Card importCardFromTxt(String text) throws IOException {

        Card result = new Card();

        BufferedReader br = new BufferedReader(new FileReader(text));
        for (int i = 0; i < 13; i++) {
            String tmp = br.readLine();
            switch (i) {
                case 0:
                    result.setPokemonStage(PokemonStage.valueOf(tmp));
                    break;
                case 1:
                    result.setName(tmp);
                    break;
                case 2:
                    result.setHp(Integer.parseInt(tmp));
                    break;
                case 3:
                    result.setPokemonType(EnergyType.valueOf(tmp.toUpperCase()));
                    break;
                case 4:
                    result.setEvolvesFrom(
                            tmp.equalsIgnoreCase("NULL") ? null : importCardFromTxt(tmp));
                    break;
                case 5:
                    result.setSkills(getAttacksFromLine(tmp));
                    break;
                case 6:
                    result.setWeaknessType((tmp.equalsIgnoreCase("NULL")) ? null :
                            EnergyType.valueOf(tmp.toUpperCase()));
                    break;
                case 7:
                    result.setResistanceType((tmp.equalsIgnoreCase("NULL")) ? null :
                            EnergyType.valueOf(tmp.toUpperCase()));
                    break;
                case 8:
                    result.setRetreatCost(tmp);
                    break;
                case 9:
                    result.setGameSet(tmp);
                    break;
                case 10:
                    result.setRegulationMark(tmp.charAt(0));
                    break;
                case 11:
                    result.setPokemonOwner(getStudentFromLine(tmp));
                    break;
                case 12:
                    result.setNumber(Integer.parseInt(tmp));
                    break;
            }

        }
        return result;
    }
    private static ArrayList<AttackSkill> getAttacksFromLine(String a){
        ArrayList<AttackSkill> result = new ArrayList<>();
        String[] tmp =  a.split("/");
        result.add(new AttackSkill(tmp[1], "", tmp[0], Integer.parseInt(tmp[2])));
        return result;
    }
    private static Student getStudentFromLine(String a){
        Student result = new Student();
        String[] tmp =  a.split(" ");
        result.setFirstName(tmp[1]);
        result.setSurName(tmp[2]);
        result.setFamilyName(tmp[0]);
        result.setGroup(tmp[3]);
        return result;
    }
    public static Card cardImportByte(String path) throws IOException, ClassNotFoundException {

            ObjectInputStream in = new ObjectInputStream(new FileInputStream(path));
            return (Card) in.readObject();
    }



    public static void updateAttackSkill(Card card, PkmnHttpClient httpClient) throws IOException {
        if(card.getEvolvesFrom() != null){
            updateAttackSkill(card.getEvolvesFrom(), httpClient);
        }

        List<JsonNode> tmp = httpClient.getPokemonCard(card.getName(), card.getNumber()).findValues("text");
        for (int i = 0; i < tmp.size(); i++){
            card.getSkills().get(i).setDescription(tmp.get(i).asText());
        }
    }

    public static ArrayList<AttackSkill> parseAttackSkillsFromJson(String json) throws JsonProcessingException {
        ArrayList<AttackSkill> result = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayNode tmp = (ArrayNode) objectMapper.readTree(json);
        for(int i = 0; i < tmp.size(); i++){
            JsonNode ji = tmp.get(i);
            AttackSkill as = new AttackSkill();
            as.setDescription(ji.findValue("text").toString());
            as.setCost(ji.findValue("cost").toString());
            as.setDamage((ji.get("damage").asInt()));
            as.setName(ji.findValue("name").toString());
            result.add(as);
        }
        return result;
    }
}
