package task3;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class MarvelCharacterRequest {

    private static final String BASE_URL = "https://gateway.marvel.com:443/v1/public/characters";
    private static final String PUBLIC_KEY = "22bd7c3017840af4e4fe1e5c8eebc5e7";

    private static final String PRIVATE_KEY = //<editor-fold desc="secret">
    "2b7f727e202d6fbd9e126fe5501e5ed7671214ec";
    // </editor-fold>

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        long timestamp = Instant.now().getEpochSecond();
        String hash = generateHash(timestamp, PRIVATE_KEY, PUBLIC_KEY);
        String name = "Iron man";

        String url = BASE_URL + "?name=" + name.replace(" ", "%20") +
                "&ts=" + timestamp +
                "&apikey=" + PUBLIC_KEY +
                "&hash=" + hash;

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String jsonResponse = response.body().string();
                MarvelCharacter character = parseCharacter(jsonResponse);
                System.out.println("Character Details:");
                System.out.println("ID: " + character.getId());
                System.out.println("Name: " + character.getName());
                System.out.println("Description: " + character.getDescription());
                System.out.println("Thumbnail: " + character.getThumbnail());
                System.out.println("Comics: " + String.join(", ", character.getComics()));
                System.out.println("Series: " + String.join(", ", character.getSeries()));
                System.out.println("Stories: " + String.join(", ", character.getStories()));
            } else {
                System.out.println("Request failed: " + response.code());
            }
        }
    }

    //Пояснение:Чтобы сделать запрос мало того, чтобы передать приватный и публичный ключ, надо еще передать хэш.
    //Логика создания хэша: 1. Складываю timestamp + privateKey + publicKey
    //2.Хэширую с помощью MD5(потому что marvel api с ней работает)
    //3.Конвертирую в 16ричное число
    private static String generateHash(long timestamp, String privateKey, String publicKey) throws NoSuchAlgorithmException {
        String value = timestamp + privateKey + publicKey;
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] hashBytes = md.digest(value.getBytes());
        StringBuilder hexString = new StringBuilder();
        for (byte b : hashBytes) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }


    //Пояснение:я делаю парсинг из json в персонажа благодаря древовидной структуре
    private static MarvelCharacter parseCharacter(String jsonResponse) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonResponse);

        JsonNode characterNode = rootNode.at("/data/results/0");

        MarvelCharacter character = new MarvelCharacter();
        character.setId(characterNode.get("id").asInt());
        character.setName(characterNode.get("name").asText());
        character.setDescription(characterNode.get("description").asText());

        JsonNode thumbnailNode = characterNode.get("thumbnail");
        if (thumbnailNode != null) {
            String thumbnailPath = thumbnailNode.get("path").asText();
            String thumbnailExtension = thumbnailNode.get("extension").asText();
            character.setThumbnail(thumbnailPath + "." + thumbnailExtension);
        }

        JsonNode comicsNode = characterNode.at("/comics/items");
        if (comicsNode.isArray()) {
            character.setComics(StreamSupport.stream(comicsNode.spliterator(), false)
                    .map(node -> node.get("name").asText())
                    .collect(Collectors.toList()));
        }

        JsonNode seriesNode = characterNode.at("/series/items");
        if (seriesNode.isArray()) {
            character.setSeries(StreamSupport.stream(seriesNode.spliterator(), false)
                    .map(node -> node.get("name").asText())
                    .collect(Collectors.toList()));
        }

        JsonNode storiesNode = characterNode.at("/stories/items");
        if (storiesNode.isArray()) {
            character.setStories(StreamSupport.stream(storiesNode.spliterator(), false)
                    .map(node -> node.get("name").asText())
                    .collect(Collectors.toList()));
        }

        return character;
    }
}
