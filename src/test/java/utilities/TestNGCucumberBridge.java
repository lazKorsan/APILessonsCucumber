package utilities;

import io.cucumber.plugin.EventListener;
import io.cucumber.plugin.event.EventPublisher;
import io.cucumber.plugin.event.TestRunFinished;
import io.cucumber.plugin.event.TestRunStarted;
import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlInclude;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import java.util.*;

public class TestNGCucumberBridge implements EventListener {

    private static final List<String> testNGMethods = new ArrayList<>();

    @Override
    public void setEventPublisher(EventPublisher publisher) {
        publisher.registerHandlerFor(TestRunStarted.class, this::onTestRunStarted);
        publisher.registerHandlerFor(TestRunFinished.class, this::onTestRunFinished);
    }

    private void onTestRunStarted(TestRunStarted event) {
        System.out.println("🚀 TestNG-Cucumber Bridge Başlatıldı");
        testNGMethods.clear();
    }

    private void onTestRunFinished(TestRunFinished event) {
        if (!testNGMethods.isEmpty()) {
            System.out.println("🔧 TestNG Methodları Çalıştırılıyor: " + testNGMethods);
            runTestNGMethods(testNGMethods);
        }
    }

    public static void addTestNGMethod(String testNGMapping) {
        testNGMethods.add(testNGMapping);
        System.out.println("✅ TestNG Method Eklendi: " + testNGMapping);
    }

    /**
     * Bu metot, Cucumber test çalıştırması sırasında toplanan TestNG metotlarını
     * programatik olarak çalıştırır.
     * @param testNGMappings Çalıştırılacak metotların listesi (format: "paket.SinifAdi#metotAdi")
     */
    private void runTestNGMethods(List<String> testNGMappings) {
        // 1. TestNG'yi programatik olarak çalıştırmak için bir TestNG nesnesi oluşturulur.
        TestNG testng = new TestNG();

        // 2. Çalıştırılacak testleri tanımlamak için bir XML Suite nesnesi oluşturulur.
        // Bu, testng.xml dosyasını kod ile oluşturmaya benzer.
        XmlSuite suite = new XmlSuite();
        suite.setName("Cucumber-Triggered TestNG Suite");

        // 3. Suite'in içine bir Test bölümü (<test>) eklenir.
        XmlTest test = new XmlTest(suite);
        test.setName("Dynamic TestNG Test");

        // 4. Metotları sınıflarına göre gruplamak için bir Map kullanılır.
        // Key: Sınıf Adı (örn: "utilities.MyTestClass")
        // Value: O sınıfa ait metot adlarının listesi (örn: ["test1", "test2"])
        Map<String, List<String>> classMethodsMap = new HashMap<>();
        for (String mapping : testNGMappings) {
            String[] parts = mapping.split("#");
            if (parts.length == 2) {
                String className = parts[0];
                String methodName = parts[1];
                // Sınıf adı haritada yoksa, yeni bir liste oluştur ve ekle.
                // Varsa, mevcut listeye metot adını ekle.
                classMethodsMap.computeIfAbsent(className, k -> new ArrayList<>()).add(methodName);
            }
        }

        // 5. Gruplanan metotları XmlClass nesnelerine dönüştür.
        List<XmlClass> xmlClasses = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : classMethodsMap.entrySet()) {
            String className = entry.getKey();
            List<String> methods = entry.getValue();

            // Sınıfı temsil eden bir XmlClass oluşturulur.
            XmlClass xmlClass = new XmlClass(className);

            // Sadece belirtilen metotları çalıştırmak için XmlInclude nesneleri oluşturulur.
            List<XmlInclude> includedMethods = new ArrayList<>();
            for (String methodName : methods) {
                includedMethods.add(new XmlInclude(methodName));
            }

            // Metot listesi, sınıfa eklenir.
            xmlClass.setIncludedMethods(includedMethods);
            xmlClasses.add(xmlClass);
        }

        // 6. Oluşturulan sınıflar listesi, test bölümüne (<test>) eklenir.
        test.setXmlClasses(xmlClasses);

        // 7. Hazırlanan suite'i TestNG'ye ver.
        testng.setXmlSuites(Collections.singletonList(suite));

        // 8. Testleri çalıştır!
        System.out.println("▶️ TestNG çalıştırılıyor...");
        testng.run();
        System.out.println("⏹️ TestNG tamamlandı.");
    }
}
