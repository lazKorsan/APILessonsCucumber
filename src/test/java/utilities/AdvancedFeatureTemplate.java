package utilities;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.io.*;

public class AdvancedFeatureTemplate {

    private static final String FEATURES_DIR = "src/test/resources/features/";

    // Step template'leri
    private static final Map<String, List<String>> STEP_TEMPLATES = Map.of(
            "LOGIN", Arrays.asList(
                    "Given Kullanıcı login sayfasında",
                    "When {scenario}",
                    "Then Giriş işlemi sonucu kontrol edilir"
            ),
            "API", Arrays.asList(
                    "Given API endpoint hazırlanır",
                    "When {scenario}",
                    "Then Response doğrulanır"
            ),
            "CRUD", Arrays.asList(
                    "Given Test verileri hazırlanır",
                    "When {scenario}",
                    "Then İşlem sonucu doğrulanır"
            ),
            "SEARCH", Arrays.asList(
                    "Given Arama kriterleri hazırlanır",
                    "When {scenario}",
                    "Then Arama sonuçları doğrulanır"
            )
    );

    public static void generateFeature(String featureName,
                                       String featureDescription,
                                       Map<String, String> testMappings,
                                       String templateType) {

        String featureContent = buildAdvancedFeatureContent(
                featureName, featureDescription, testMappings, templateType);

        String filePath = FEATURES_DIR + featureName + ".feature";

        writeToFile(filePath, featureContent);
    }

    private static String buildAdvancedFeatureContent(String featureName,
                                                      String featureDescription,
                                                      Map<String, String> testMappings,
                                                      String templateType) {

        StringBuilder content = new StringBuilder();

        // Feature Header
        content.append("Feature: ").append(toTitleCase(featureName)).append("\n");
        content.append("  ").append(featureDescription).append("\n\n");

        // Senaryolar
        for (Map.Entry<String, String> entry : testMappings.entrySet()) {
            String scenarioName = entry.getKey();
            String testNGMapping = entry.getValue();

            content.append("  @TestNG=").append(testNGMapping).append("\n");
            content.append("  Scenario: ").append(scenarioName).append("\n");
            content.append(buildTemplateSteps(scenarioName, templateType));
            content.append("\n");
        }

        return content.toString();
    }

    private static String buildTemplateSteps(String scenarioName, String templateType) {
        StringBuilder steps = new StringBuilder();
        List<String> template = STEP_TEMPLATES.getOrDefault(
                templateType.toUpperCase(), STEP_TEMPLATES.get("CRUD"));

        for (String step : template) {
            String formattedStep = step.replace("{scenario}", scenarioName);
            steps.append("    ").append(formattedStep).append("\n");
        }

        return steps.toString();
    }

    private static void writeToFile(String filePath, String content) {
        try {
            Files.createDirectories(Paths.get(FEATURES_DIR));

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                writer.write(content);
                System.out.println("✅ Feature dosyası oluşturuldu: " + filePath);
                System.out.println("📝 İçerik:\n" + content);
            }
        } catch (IOException e) {
            System.err.println("❌ Hata: " + e.getMessage());
        }
    }

    private static String toTitleCase(String input) {
        return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
    }
}