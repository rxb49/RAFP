package fr.univangers.utils;

import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import fr.univangers.classes.Person;
import jakarta.servlet.http.HttpServletResponse;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ExportPDF {
    private List<Person> lesPersonnels;

    public ExportPDF(List<Person> lesPersonnels) {
        this.lesPersonnels = lesPersonnels;
    }

    public void export(HttpServletResponse response) throws IOException {
        PdfWriter writer = new PdfWriter(response.getOutputStream());
        PdfReader reader = new PdfReader("https://relais-rh.univ-angers.fr/images/pdf/Passeport.pdf");
        PdfDocument pdfDocument = new PdfDocument(reader,writer);
        PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDocument, false);

        form.setGenerateAppearance(true);
        Map<String, PdfFormField> fields = form.getFormFields();
        if(fields.containsKey("identite"))
            fields.get("identite").setValue(this.lesPersonnels.get(0).getNom()+ " "+this.lesPersonnels.get(0).getPrenom());
        form.flattenFields();
        pdfDocument.close();
        reader.close();

        //pour doc final
        PdfReader readerMaj = new PdfReader(new FileInputStream("/var/tmp/exportPDF.pdf"));
        PdfDocument pdfSrc = new PdfDocument(readerMaj);
        PdfDocument pdfDest = new PdfDocument(writer);

        pdfSrc.copyPagesTo(1, 1, pdfDest);

        pdfSrc.close();
        pdfDest.close();
        readerMaj.close();
        writer.close();
    }

}
