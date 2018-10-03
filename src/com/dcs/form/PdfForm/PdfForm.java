package com.dcs.form.PdfForm;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;

import com.dcs.util.ParseUtil;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.wsnweb.util.Utility;

public class PdfForm {
	private static final Logger log = Logger.getLogger(PdfForm.class);

	public static ByteArrayOutputStream print(InputStream is, Object obj) {
		ByteArrayOutputStream outpdf = new ByteArrayOutputStream();

		try {
			String pdfFont = Utility.get("PDF_FONT");
			String pdfFontBold = Utility.get("PDF_FONT_BOLD");
			BaseFont baseFont = BaseFont.createFont(pdfFont, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			BaseFont baseFontBold = BaseFont.createFont(pdfFontBold, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

			PdfReader reader = new PdfReader(is);
			PdfStamper stamper = new PdfStamper(reader, outpdf, '\0', false);
			AcroFields fields = stamper.getAcroFields();
			Map<String, AcroFields.Item> fieldsMap = fields.getFields();
			for (String fieldName : fieldsMap.keySet()) {
				try {
					String value = "";
					BaseFont font = baseFont;
					if (fieldName.startsWith("\\")) {
						String fontStyle = fieldName.substring(1, 2);
						if ("b".equalsIgnoreCase(fontStyle)) {
							font = baseFontBold;
						}
						value = String.valueOf(getValue(obj, fieldName.substring(3, fieldName.length())));
					} else {
						value = String.valueOf(getValue(obj, fieldName));
					}
					fields.setFieldProperty(fieldName, "textfont", font, null);
					fields.setField(fieldName, value);
				} catch (Exception e) {
					log.error(e);
				}
			}

			stamper.setFormFlattening(true);
			stamper.close();
			outpdf.flush();
		} catch (Exception e) {
			log.error(e, e);
		}
		return outpdf;
	}

	public static Object getValue(Object obj, String property) throws Exception {
		int d = property.indexOf(".");
		if (d < 0) {
			return PropertyUtils.getProperty(obj, property);
		} else {
			String n1 = property.substring(0, d);
			String n2 = property.substring(d + 1, property.length());

			if (n1.indexOf("(") > 0 && n1.indexOf(")") > 0) {
				int i = ParseUtil.parseInt(n1.substring(n1.indexOf("(") + 1, n1.indexOf(")")));
				n1 = n1.substring(0, n1.indexOf("("));
				List list = (List) PropertyUtils.getProperty(obj, n1);
				return PropertyUtils.getProperty(list.get(i), n2);
			} else {
				return PropertyUtils.getProperty(PropertyUtils.getProperty(obj, n1), n2);
			}
		}
	}
}
