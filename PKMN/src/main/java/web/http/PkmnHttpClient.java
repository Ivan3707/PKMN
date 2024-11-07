package web.http;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.json.JsonMapper;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import web.http.PokemonTcgAPI;

import java.io.IOException;

public class PkmnHttpClient {
    Retrofit client;

    PokemonTcgAPI tcgAPI;

    public PkmnHttpClient(){
        client = new Retrofit.Builder()
                .baseUrl("https://api.pokemontcg.io")
                .addConverterFactory(JacksonConverterFactory.create(new JsonMapper()))
                .build();

        tcgAPI = client.create(PokemonTcgAPI.class);
    }

    public JsonNode getPokemonCard(String name, int number) throws IOException {
        String requestQuery = "name:\""+name+"\"" + " " + "number:"+number;

        Response<JsonNode> response = tcgAPI.getPokemon(requestQuery).execute();

        return response.body();
    }
}