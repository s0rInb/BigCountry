package com.gmail.s0rInb;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDCheckBox;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.stream.Collectors;

public class test_pdf {
	public static void main(String args[]) throws IOException {
		File file = new File("D:\\project\\BigCountry\\src\\main\\java\\com\\gmail\\s0rInb\\форма_ня.pdf");
		PDDocument pdDocument = PDDocument.load(file);
		PDDocumentCatalog pdDocumentCatalog = pdDocument.getDocumentCatalog();
		PDAcroForm acroForm = pdDocumentCatalog.getAcroForm();
		List<PDField> fieldList = acroForm.getFields();
		List<PDField> nis_yes = acroForm.getFields().stream().filter(pdField -> pdField.getFullyQualifiedName().equals("NIS_YES")).collect(Collectors.toList());
		PDCheckBox pdField = (PDCheckBox) nis_yes.get(0);
		pdField.check();
		File file1 = new File("D:\\project\\BigCountry\\src\\main\\java\\com\\gmail\\s0rInb\\форма_ня_1.pdf");
		pdDocument.save(file1);
//		String[] fieldArray = new String[fieldList.size()];
//		int i = 0;
//		for (PDField sField : fieldList) {
//			fieldArray[i] = sField.getFullyQualifiedName();
//			i++;
//		}
//
//		// Loop through each field in the array and do something
//		for (String f : fieldArray) {
//			PDField field = acroForm.getField(f);
//
//			System.out.println("f is: " + f);
//			if (f.contains("EXAMPLE FORM FIELD NAME")) {
//				String value = "example value";
//				field.setValue(value);
//				System.out.println("printed: " + value + " to: " + f);
//			}
//		}
		System.out.println("test");
	}
}
