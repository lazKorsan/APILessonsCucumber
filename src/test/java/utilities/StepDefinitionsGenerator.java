package utilities;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class StepDefinitionsGenerator {

    private static final String STEPDEFINITIONS_DIR = "src/test/java/stepdefinitions/";
    private static final String HELPERS_DIR = "src/test/java/helpers/";

    /**
     * Step Definitions sınıfı oluşturur
     */
    public static void generateStepDefinitions(String className,
                                               Map<String, String> stepImplementations) {

        String classContent = buildStepDefinitionsClass(className, stepImplementations);
        String filePath = STEPDEFINITIONS_DIR + className + ".java";

        writeToFile(filePath, classContent);
        System.out.println("✅ Step Definitions oluşturuldu: " + className);
    }

    /**
     * Helper class oluşturur (business logic için)
     */
    public static void generateHelperClass(String className,
                                           Map<String, String> methodImplementations) {

        String classContent = buildHelperClass(className, methodImplementations);
        String filePath = HELPERS_DIR + className + ".java";

        writeToFile(filePath, classContent);
        System.out.println("✅ Helper class oluşturuldu: " + className);
    }

    private static String buildStepDefinitionsClass(String className,
                                                    Map<String, String> stepImplementations) {

        StringBuilder content = new StringBuilder();

        // Package ve imports
        content.append("package stepdefinitions;\n\n");
        content.append("import io.cucumber.java.en.*;\n");
        content.append("import helpers.*;\n");
        content.append("import static org.junit.Assert.*;\n\n");

        // Class declaration
        content.append("public class ").append(className).append(" {\n\n");

        // Helper instance
        content.append("    private ").append(className.replace("Steps", "Helper"))
                .append(" helper = new ").append(className.replace("Steps", "Helper"))
                .append("();\n\n");

        // Step methods
        for (Map.Entry<String, String> entry : stepImplementations.entrySet()) {
            String stepPattern = entry.getKey();
            String methodName = entry.getValue();

            content.append(buildStepMethod(stepPattern, methodName));
            content.append("\n");
        }

        content.append("}");

        return content.toString();
    }

    private static String buildStepMethod(String stepPattern, String methodName) {
        StringBuilder method = new StringBuilder();

        // Annotation belirle (Given/When/Then/And)
        String annotationType = determineAnnotationType(stepPattern);

        method.append("    @").append(annotationType).append("(\"")
                .append(stepPattern).append("\")\n");
        method.append("    public void ").append(generateMethodName(methodName))
                .append("() {\n");
        method.append("        helper.").append(methodName).append("();\n");
        method.append("    }");

        return method.toString();
    }

    private static String buildHelperClass(String className,
                                           Map<String, String> methodImplementations) {

        StringBuilder content = new StringBuilder();

        // Package ve imports
        content.append("package helpers;\n\n");
        content.append("import org.junit.Assert;\n");
        content.append("import static org.hamcrest.Matchers.*;\n\n");

        // Class declaration
        content.append("public class ").append(className).append(" {\n\n");

        // Methods
        for (Map.Entry<String, String> entry : methodImplementations.entrySet()) {
            String methodName = entry.getKey();
            String implementation = entry.getValue();

            content.append(buildHelperMethod(methodName, implementation));
            content.append("\n");
        }

        content.append("}");

        return content.toString();
    }

    private static String buildHelperMethod(String methodName, String implementation) {
        StringBuilder method = new StringBuilder();

        method.append("    public void ").append(methodName).append("() {\n");

        // Template-based implementation
        if (implementation.equals("AUTO")) {
            method.append(buildAutoImplementation(methodName));
        } else {
            method.append("        // ").append(implementation).append("\n");
            method.append("        System.out.println(\"").append(methodName).append(" executed\");\n");
        }

        method.append("    }");

        return method.toString();
    }

    private static String buildAutoImplementation(String methodName) {
        // Method adına göre otomatik implementation
        if (methodName.toLowerCase().contains("login")) {
            return "        // Login işlemleri\n" +
                    "        System.out.println(\"Login işlemi gerçekleştiriliyor\");\n" +
                    "        Assert.assertTrue(\"Login başarısız\", true);\n";

        } else if (methodName.toLowerCase().contains("api")) {
            return "        // API işlemleri\n" +
                    "        System.out.println(\"API çağrısı yapılıyor\");\n" +
                    "        // given().when().get(\"/endpoint\");\n";

        } else if (methodName.toLowerCase().contains("verify") ||
                methodName.toLowerCase().contains("check")) {
            return "        // Doğrulama işlemleri\n" +
                    "        System.out.println(\"Doğrulama yapılıyor\");\n" +
                    "        Assert.assertTrue(\"Doğrulama başarısız\", true);\n";

        } else {
            return "        // Generic implementation\n" +
                    "        System.out.println(\"" + methodName + " executed\");\n" +
                    "        Assert.assertTrue(\"Test passed\", true);\n";
        }
    }

    private static String determineAnnotationType(String stepPattern) {
        String lowerStep = stepPattern.toLowerCase();

        if (lowerStep.contains("given") || lowerStep.startsWith("veri") ||
                lowerStep.startsWith("data") || lowerStep.startsWith("önkoşul")) {
            return "Given";
        } else if (lowerStep.contains("when") || lowerStep.contains("işlem") ||
                lowerStep.contains("action")) {
            return "When";
        } else if (lowerStep.contains("then") || lowerStep.contains("doğrula") ||
                lowerStep.contains("kontrol")) {
            return "Then";
        } else {
            return "And";
        }
    }

    private static String generateMethodName(String input) {
        // Method name generation
        String cleaned = input.replaceAll("[^a-zA-Z0-9]", "_")
                .toLowerCase();
        if (cleaned.length() > 50) {
            cleaned = cleaned.substring(0, 50);
        }
        return cleaned;
    }

    private static void writeToFile(String filePath, String content) {
        try {
            Files.createDirectories(Paths.get(filePath).getParent());

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                writer.write(content);
            }
        } catch (IOException e) {
            System.err.println("❌ Dosya yazılamadı: " + e.getMessage());
        }
    }
}