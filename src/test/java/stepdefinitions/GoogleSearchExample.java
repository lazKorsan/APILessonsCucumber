package stepdefinitions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class GoogleSearchExample {

    // Yeni API anahtarı ve Search Engine ID almanız gerekebilir
    private static final String API_KEY = "2ecC1yGMUbcPi9V1YuXKJ5:2TLu6uoUy3ZDCvIS2xIsOuE";
    private static final String SEARCH_ENGINE_ID = "92d19d034826a4f2f";

    public static void main(String[] args) {
        String query = "Java API documentation PDF";

        try {
            String result = performGoogleSearch(query);
            System.out.println("Arama Sonuçları:");
            System.out.println(result);

        } catch (Exception e) {
            System.err.println("Hata: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static String performGoogleSearch(String query) throws IOException {
        // Sorguyu URL encode et
        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);

        // API URL'ini oluştur
        String urlString = String.format(
                "https://www.googleapis.com/customsearch/v1?key=%s&cx=%s&q=%s&num=5",
                API_KEY, SEARCH_ENGINE_ID, encodedQuery
        );

        System.out.println("İstek URL: " + urlString);

        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("User-Agent", "Mozilla/5.0");

        // Timeout ayarları
        connection.setConnectTimeout(30000);
        connection.setReadTimeout(30000);

        int responseCode = connection.getResponseCode();
        System.out.println("HTTP Response Code: " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) {
            // Başarılı yanıtı oku
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return response.toString();

        } else if (responseCode == HttpURLConnection.HTTP_FORBIDDEN) {
            // Hata durumunda detaylı bilgi al
            BufferedReader errorReader = new BufferedReader(
                    new InputStreamReader(connection.getErrorStream()));
            String errorLine;
            StringBuilder errorResponse = new StringBuilder();

            while ((errorLine = errorReader.readLine()) != null) {
                errorResponse.append(errorLine);
            }
            errorReader.close();

            throw new IOException("API erişim hatası (403). Hata detayı: " + errorResponse.toString());

        } else {
            throw new IOException("HTTP hatası: " + responseCode);
        }
    }

    // JSON yanıtını parse etmek için basit bir yardımcı metod
    public static void parseAndPrintResults(String jsonResponse) {
        // Basit bir parsing (gerçek uygulamada JSON parser kullanın)
        if (jsonResponse.contains("\"items\"")) {
            System.out.println("Sonuçlar bulundu!");

            // JSON'dan başlık ve linkleri çıkarmak için basit parsing
            String[] items = jsonResponse.split("\"items\"")[1].split("\\},\\{");

            for (int i = 0; i < Math.min(items.length, 5); i++) {
                String item = items[i];

                // Başlık çıkar
                if (item.contains("\"title\"")) {
                    String title = item.split("\"title\"")[1].split("\",\"")[0]
                            .replace(":\"", "").trim();
                    System.out.println("Başlık: " + title);
                }

                // Link çıkar
                if (item.contains("\"link\"")) {
                    String link = item.split("\"link\"")[1].split("\",\"")[0]
                            .replace(":\"", "").trim();
                    System.out.println("Link: " + link);
                }

                System.out.println("---");
            }
        } else {
            System.out.println("Sonuç bulunamadı.");
            System.out.println("API Yanıtı: " + jsonResponse);
        }
    }
}