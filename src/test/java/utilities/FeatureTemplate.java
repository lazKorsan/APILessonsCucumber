package utilities;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class FeatureTemplate {

    private static final String FEATURES_DIR = "src/test/resources/features/";

    /**
     * Otomatik .feature dosyası üretir
     * @param featureName - Feature dosya adı (uzantı olmadan)
     * @param testMappings - Senaryo adı -> TestNG method mapping'i
     */
    public static void generateFeature(String featureName,
                                       Map<String, String> testMappings) {

        String featureContent = buildFeatureContent(featureName, testMappings);
        String filePath = FEATURES_DIR + featureName + ".feature";

        try {
            // Features dizini yoksa oluştur
            Files.createDirectories(Paths.get(FEATURES_DIR));

            // Feature dosyasını yaz
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                writer.write(featureContent);
            }

            System.out.println("✅ Feature dosyası oluşturuldu: " + filePath);

        } catch (IOException e) {
            System.err.println("❌ Feature dosyası oluşturulamadı: " + e.getMessage());
        }
    }

    private static String buildFeatureContent(String featureName,
                                              Map<String, String> testMappings) {

        StringBuilder content = new StringBuilder();

        // Feature Header
        content.append("Feature: ").append(toTitleCase(featureName)).append("\n");
        content.append("  Otomatik üretilmiş feature dosyası\n\n");

        // Her senaryo için
        int scenarioCount = 1;
        for (Map.Entry<String, String> entry : testMappings.entrySet()) {
            String scenarioName = entry.getKey();
            String testNGMapping = entry.getValue();

            content.append("  @TestNG=").append(testNGMapping).append("\n");
            content.append("  Scenario: ").append(scenarioName).append("\n");

            // Standart step'leri ekle (template-based)
            content.append(buildScenarioSteps(scenarioName, scenarioCount));
            content.append("\n");

            scenarioCount++;
        }

        return content.toString();
    }

    private static String buildScenarioSteps(String scenarioName, int scenarioNumber) {
        // Senaryo tipine göre otomatik step'ler üret
        StringBuilder steps = new StringBuilder();

        if (scenarioName.toLowerCase().contains("login")) {
            steps.append("    Given Kullanıcı login sayfasında\n");
            steps.append("    When ").append(scenarioName).append("\n");
            steps.append("    Then Giriş işlemi sonucu kontrol edilir\n");

        } else if (scenarioName.toLowerCase().contains("api")) {
            steps.append("    Given API endpoint hazırlanır\n");
            steps.append("    When ").append(scenarioName).append("\n");
            steps.append("    Then Response doğrulanır\n");

        } else if (scenarioName.toLowerCase().contains("create") ||
                scenarioName.toLowerCase().contains("add")) {
            steps.append("    Given Veri hazırlanır\n");
            steps.append("    When ").append(scenarioName).append("\n");
            steps.append("    Then Kayıt oluşturulduğu doğrulanır\n");

        } else {
            // Generic template
            steps.append("    Given Test ortamı hazırlanır\n");
            steps.append("    When ").append(scenarioName).append("\n");
            steps.append("    Then Sonuçlar doğrulanır\n");
        }

        return steps.toString();
    }

    private static String toTitleCase(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
    }
}