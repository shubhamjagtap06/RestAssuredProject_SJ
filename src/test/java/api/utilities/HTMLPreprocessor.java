package api.utilities;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class HTMLPreprocessor {

    public static String preprocessHtml(String filePath) throws IOException {
        String htmlContent = new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8);

        // Debug: Print the HTML before processing
        System.out.println("Before Preprocessing: ");
        System.out.println(htmlContent);

        // Step 1: Use Jsoup to parse and clean the HTML content using the correct HTML parser
        Document doc = Jsoup.parse(htmlContent);

        // Step 2: Fix comments manually if needed. Sometimes Jsoup will leave malformed comments alone.
        fixMalformedComments(doc);

        // Step 3: Fix any unclosed tags (which can be tricky in some cases)
        fixUnclosedTags(doc);

        // Step 4: Convert back to a string
        String cleanedHtml = doc.html();

        // Debug: Print the HTML after processing
        System.out.println("After Preprocessing: ");
        System.out.println(cleanedHtml);

        return cleanedHtml;
    }

    // Helper method to fix malformed comments
    private static void fixMalformedComments(Document doc) {
        // Manually fix comments: Ensure they are in the proper form <!-- comment -->
        for (Element comment : doc.select("comment")) {
            String commentData = comment.data().trim();
            if (!commentData.startsWith("<!--") || !commentData.endsWith("-->")) {
                comment.html("<!-- " + commentData + " -->");
            }
        }
    }

    // Helper method to fix unclosed tags
    private static void fixUnclosedTags(Document doc) {
        // Jsoup generally handles unclosed tags, but let's make sure we close common ones like <div>, <span>, <a>, etc.
        String[] tagsToCheck = {"div", "span", "a", "body", "html"};

        for (String tag : tagsToCheck) {
            doc.select(tag).forEach(element -> {
                if (!element.hasText() && !element.html().endsWith(">")) {
                    element.append("</" + tag + ">");
                }
            });
        }
    }
}
