package api.utilities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;

@SuppressWarnings("unused")
public class HtmlToPdfConverter {

    public static void convertHtmlToPdf(String htmlFilePath, String pdfFilePath) {
        try {
            // Preprocess HTML content to fix common issues
            String htmlContent = HTMLPreprocessor.preprocessHtml(htmlFilePath);

            // Convert the HTML content to PDF using openhtmltopdf
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.withHtmlContent(htmlContent, new File(".").toURI().toString());
            builder.toStream(new FileOutputStream(pdfFilePath));
            builder.run();

            System.out.println("PDF Report generated successfully at: " + pdfFilePath);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error during HTML to PDF conversion: " + e.getMessage());
        }
    }
}
