package com.retail.productsales.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.retail.productsales.entity.Product;
import com.retail.productsales.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;
import java.util.stream.Stream;

@Service
public class PdfExportService {
	@Autowired
	private ProductRepository productRepo;

	public ByteArrayInputStream export() {
		Document document = new Document();
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		try {
			PdfWriter.getInstance(document, out);
			document.open();
			Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
			Paragraph title = new Paragraph("Product Report", font);
			title.setSpacingAfter(15f);
			document.add(title);

			PdfPTable table = new PdfPTable(6);
			Stream.of("ID", "Name", "Description", "Price", "Quantity", "Revenue").forEach(header -> {
				PdfPCell cell = new PdfPCell();
				cell.setPhrase(new Phrase(header));
				table.addCell(cell);
			});

			List<Product> products = productRepo.findAll();
			for (Product product : products) {
				table.addCell(String.valueOf(product.getId()));
				table.addCell(product.getName());
				table.addCell(product.getDescription());
				table.addCell(String.valueOf(product.getPrice()));
				table.addCell(String.valueOf(product.getQuantity()));
				double revenue = product.getSales().stream().mapToDouble(s -> s.getQuantity() * product.getPrice())
						.sum();
				table.addCell(String.valueOf(revenue));
			}
			document.add(table);
			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ByteArrayInputStream(out.toByteArray());
	}
}
