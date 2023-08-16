package com.qa.opencart.listeners;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.testng.IReporter;
import org.testng.IResultMap;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.xml.XmlSuite;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class ItextPdfListener extends CreatePDFReport implements IReporter {
	@Override
	public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
		for (ISuite ist : suites) {
			try {

				openPdfPath();
				// *************//
				Map<String, ISuiteResult> resultSuiteMap = ist.getResults();
				Set<String> key = resultSuiteMap.keySet();
				for (String k : key) {
					ITestContext context = resultSuiteMap.get(k).getTestContext();
					System.out.println("Suite Name- " + context.getName() + "\n Report output Directory- "
							+ context.getOutputDirectory() + "\n Suite Name- " + context.getSuite().getName()
							+ "\n Start Date Time for Execution- " + context.getStartDate()
							+ "\n End Date Time for Execution- " + context.getEndDate());

					addParagraph("Suite Name- " + context.getName() + "\n Report output Directory- "
							+ context.getOutputDirectory() + "\n Suite Name- " + context.getSuite().getName()
							+ "\n Start Date Time for Execution- " + context.getStartDate()
							+ "\n End Date Time for Execution- " + context.getEndDate());
					IResultMap resultMap = context.getFailedTests();
					Collection<ITestNGMethod> failedMethods = resultMap.getAllMethods();
					System.out.println("------Failed Test Case-----");
					for (ITestNGMethod imd : failedMethods) {
						System.out.println(
								"Test Case Name- " + imd.getMethodName() + "\n Description- " + imd.getDescription()
										+ "\n Priority- " + imd.getPriority() + "\n Date- " + new Date(imd.getDate()));

						addParagraph(
								"Test Case Name- " + imd.getMethodName() + "\n Description- " + imd.getDescription()
										+ "\n Priority- " + imd.getPriority() + "\n Date- " + new Date(imd.getDate()));
					}
					IResultMap passedTest = context.getPassedTests();
					Collection<ITestNGMethod> passedMethods = passedTest.getAllMethods();
					System.out.println("------Passed Test Case-----");
					for (ITestNGMethod imd1 : passedMethods) {
						System.out.println("Test Case Name- " + imd1.getMethodName() + "\n Description- "
								+ imd1.getDescription() + "\n Priority- " + imd1.getPriority() + "\n Date- "
								+ new Date(imd1.getDate()));

						addParagraph("Test Case Name- " + imd1.getMethodName() + "\n Description- "
								+ imd1.getDescription() + "\n Priority- " + imd1.getPriority() + "\n Date- "
								+ new Date(imd1.getDate()));
					}
				}
				// Closing PDF file
				closePdf();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}

class CreatePDFReport {
	Document docu;

	public void openPdfPath() throws FileNotFoundException, DocumentException {
		String fileName = new File("").getAbsoluteFile().toString() + "/pdf-" + System.currentTimeMillis()
				+ ".pdf";
		FileOutputStream fos = new FileOutputStream(fileName);
		docu = new Document();
		PdfWriter.getInstance(docu, fos);
		docu.open();
	}

	public void addData(String authorName, String title, String description) {
		docu.addAuthor(authorName);
		docu.addTitle(title);
		docu.addSubject(description);
	}

	public void addParagraph(String text) throws DocumentException {
		docu.add(new Paragraph(text));
	}

	public void closePdf() {
		docu.close();
	}
}
