package com.wsnweb.action;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.hibernate.SessionFactory;

import com.dcs.hibernate.HibernateHome;
import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.PdfWriter;
import com.wsndata.data.Branch;
import com.wsndata.data.BreedGroup;
import com.wsndata.data.BreedType;
import com.wsndata.data.Province;
import com.wsndata.data.Region;
import com.wsndata.data.User;
import com.wsndata.dbaccess.BranchHome;
import com.wsndata.dbaccess.BreedGroupHome;
import com.wsndata.dbaccess.BreedTypeHome;
import com.wsndata.dbaccess.PlantHome;
import com.wsndata.dbaccess.ProvinceHome;
import com.wsndata.dbaccess.RegionHome;
import com.wsndata.dbaccess.ReportHome;
import com.wsnweb.form.ReportForm;

public class ReportActionBak extends Action {
	private static final Logger log = Logger.getLogger(ReportActionBak.class);
	private static List<Branch> bCode = new ArrayList<Branch>();
	private static int[] calRai;

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ReportForm eform = (ReportForm) form;
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userLogin");
		if (userLogin == null) {
			return mapping.findForward("unauthorize");
		} else {
			/*if ("Print".equals(eform.getCmd())) {
				bCode = (List) session.getAttribute("userBranchList");
				return print(mapping, form, request, response);
			} else if ("GetBreedGroup".equals(eform.getCmd())) {
				return getBreedGroup(mapping, eform, request, response);
			} else if ("GetBranch".equals(eform.getCmd())) {
				return getBranch(mapping, eform, request, response);
			} else if ("GetProvince".equals(eform.getCmd())) {
				return getProvinceInfoFromBranch(mapping, eform, request,
						response);
			} else {
				return load(mapping, form, request, response);
			}*/
			return null;
		}
	}

	/*public ActionForward print(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ReportForm eform = (ReportForm) form;
		HttpSession session = request.getSession();
		SessionFactory sf = HibernateHome.getSessionFactory();
		Document document = null;
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		PdfWriter writer = null;
		try {
			String path = getServlet().getServletContext().getRealPath("/");
			response.setContentType("application/pdf");
			document = new Document(PageSize.LEGAL.rotate(), 25, 25, 30, 30);
			writer = PdfWriter.getInstance(document, output);
			document.open();
			// BaseFont basefont = BaseFont.createFont(this.getClass()
			// .getResource(path+"WEB-INF/classes/Garuda.ttf").getPath(),
			// BaseFont.IDENTITY_H,
			// BaseFont.EMBEDDED);

			BaseFont basefont = BaseFont.createFont(path
					+ "WEB-INF/classes/Garuda.ttf", BaseFont.IDENTITY_H,
					BaseFont.EMBEDDED);

			Font font_title = new Font(basefont, 12f, 1);
			Font font_body = new Font(basefont, 9F);
			Font font_total = new Font(basefont, 9F, 1);

			document.newPage();
			if ("R101".equals(eform.getRep())) {
				ReportR101(sf, document, font_title, font_body, font_total,
						eform);
			} else if ("R101.1".equals(eform.getRep())) {
				ReportR101_1(sf, document, font_title, font_body, font_total,
						eform);
			} else if ("R102".equals(eform.getRep())) {
				ReportR102(sf, document, font_title, font_body, font_total,
						eform);
			} else if ("R102.1".equals(eform.getRep())) {
				ReportR102_1(sf, document, font_title, new Font(basefont, 8F),
						new Font(basefont, 8F, 1), eform);
			} else if ("R103".equals(eform.getRep())) {
				ReportR103(sf, document, font_title, font_body, font_total,
						eform);
			} else if ("R103.1".equals(eform.getRep())) {
				ReportR103_1(sf, document, font_title, font_body, font_total,
						eform);
			} else if ("R104".equals(eform.getRep())) {
				ReportR104(sf, document, font_title, font_body, font_total,
						eform);
			} else if ("R104.1".equals(eform.getRep())) {
				ReportR104_1(sf, document, font_title, font_body, font_total,
						eform);
			} else if ("R104.2".equals(eform.getRep())) {
				ReportR104_2(sf, document, font_title, font_body, font_total,
						eform);
			} else if ("R104.3".equals(eform.getRep())) {
				ReportR104_3(sf, document, font_title, font_body, font_total,
						eform);
			} else if ("R105".equals(eform.getRep())) {
				ReportR105(sf, document, font_title, font_body, font_total,
						eform);
			} else if ("R105.1".equals(eform.getRep())) {
				ReportR105_1(sf, document, font_title, new Font(basefont, 7F),
						new Font(basefont, 7F, 1), eform);
			} else if ("R106".equals(eform.getRep())) {
				ReportR106(sf, document, font_title, font_body, font_total,
						eform);
			} else if ("R107".equals(eform.getRep())) {
				ReportR107(sf, document, font_title, new Font(basefont, 7F),
						new Font(basefont, 7F, 1), eform);
			} else if ("R108.1".equals(eform.getRep())) {
				ReportR108_1(sf, document, font_title, font_body, font_total,
						eform);
			} else if ("R108.3".equals(eform.getRep())) {
				ReportR108_3(sf, document, font_title, font_body, font_total,
						eform);
			} else if ("R109.1".equals(eform.getRep())) {
				ReportR109_1(sf, document, font_title, new Font(basefont, 7F),
						new Font(basefont, 7F, 1), eform);
			} else if ("R109.3".equals(eform.getRep())) {
				ReportR109_3(sf, document, font_title, new Font(basefont, 7F),
						new Font(basefont, 7F, 1), eform);
			} else if ("R110".equals(eform.getRep())) {
				ReportR110(sf, document, font_title, new Font(basefont, 7F),
						new Font(basefont, 7F, 1), eform);
			} else if ("R110.1".equals(eform.getRep())) {
				ReportR110_1(sf, document, font_title, new Font(basefont, 7F),
						new Font(basefont, 7F, 1), eform);
			}
			document.close();

			try {
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				PdfReader reader = new PdfReader(output.toByteArray());
				PdfStamper stamper = new PdfStamper(reader, out);
				int n = reader.getNumberOfPages();
				PdfContentByte cbq;
				Date timePrint = new Date();
				timePrint.setYear(timePrint.getYear() + 543);
				for (int i = 1; i <= n; i++) {
					cbq = stamper.getOverContent(i);
					ColumnText ct = new ColumnText(cbq);
					ct.setSimpleColumn(PageSize.LEGAL.getHeight() - 26,
							PageSize.LEGAL.getWidth() - 42, PageSize.LEGAL
									.getHeight() - 194, 0, 18,
							Element.ALIGN_RIGHT);
					ct.addElement(numPage(i, n, timePrint, font_body));
					ct.go();

					ct.setSimpleColumn(PageSize.LEGAL.getHeight() - 36,
							PageSize.LEGAL.getWidth() - 42, 26, 0, 18,
							Element.ALIGN_RIGHT);
					ct.addElement(new Paragraph("[" + eform.getRep() + "]",
							font_body));
					ct.go();

				}
				// Close the stamper
				stamper.close();
				reader.close();
				response.setContentLength(out.size());
				OutputStream os = response.getOutputStream();
				out.writeTo(os);
			} catch (DocumentException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			eform.setMsg(getResources(request).getMessage(request.getLocale(),
					"Print.Fail"));
			e.printStackTrace();
			log.error(e, e);
		} finally {
			sf.getCurrentSession().close();
		}
		return null;
	}*/

	/*private Document ReportR101(SessionFactory sf, Document document,
			Font font_title, Font font_body, Font font_total, ReportForm eform)
			throws DocumentException {
		try {
			sf.getCurrentSession().beginTransaction();
			Paragraph head = new Paragraph(
					"รายงานประมาณการผลผลิตของเกษตรกร(ตามตำบลที่เพาะปลูก) ตามปีการผลิต "
							+ eform.getPlantYear() + "/" + eform.getPlantNo(),
					font_title);
			head.setAlignment(Element.ALIGN_CENTER);
			document.add(head);

			Paragraph head_date = new Paragraph("สิ้นสุด ณ วันที่ "
					+ dateThai(eform.getEffectiveDate(), false), font_title);
			head_date.setAlignment(Element.ALIGN_CENTER);
			head_date.setSpacingBefore(5f);
			document.add(head_date);

			Table table = new Table(1);
			table.setAlignment(Cell.ALIGN_CENTER);
			table.setWidth(100);
			table.setPadding(2f);

			float[] colWidth, colTotalWidth;
			colWidth = new float[] { 15, 15, 20, 15, 15, 20, 15, 15 };
			colTotalWidth = new float[] { 100, 15, 15 };
			String[] headTable = new String[] { "ตำบลที่ตั้งแปลง",
					"บัตรประจำตัว\nประชาชน\n(เลขที่)", "ชื่อ-สกุล",
					"ใบรับรองเกษตรกร\n(เลขที่)", "ชนิดพืช",
					"|วันที่|เพาะปลูก|จะเก็บเกี่ยว",
					"เนื้อที่เพาะปลูก\nไร่-งาน-วา",
					"ประมาณการผลผลิตรวม \n(กก.)" };
			table
					.insertTable(headTable(newRows(colWidth), font_body,
							headTable));
			int linePage = 23;
			ReportHome rhome = new ReportHome();
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

			List provList = rhome.findR101ByCriteria(eform.getPlantYear(),
					eform.getPlantNo(), df.parse(eform.getEffectiveDate()),
					eform.getProvinceNo(), eform.getBranchCode());

			if (eform.getBranchCode() > 0) {
				BranchHome bhome = new BranchHome();
				List<Branch> pbList = bhome
						.findByPBranch(eform.getBranchCode());
				List<Branch> pbranchCode = new ArrayList();
				if (pbList.size() > 0) {
					pbranchCode
							.addAll(getPBranch(pbList, eform.getBranchCode()));
					for (int i = 0; i < pbranchCode.size(); i++) {
						provList.addAll(rhome.findR101ByCriteria(eform
								.getPlantYear(), eform.getPlantNo(), df
								.parse(eform.getEffectiveDate()), eform
								.getProvinceNo(), pbranchCode.get(i)
								.getBranchCode()));
					}
				}
			}

			String branchTmp = "", subDistrictTmp = "", provinceTmp = "", idCardTmp = "", breedTmp = "";
			int dtPlantRai = 0, dtPlantNgan = 0, dtPlantWah = 0;
			float dtFRecord = 0;
			int btPlantRai = 0, btPlantNgan = 0, btPlantWah = 0;
			float btFRecord = 0;
			int ptPlantRai = 0, ptPlantNgan = 0, ptPlantWah = 0;
			float ptFRecord = 0;
			Paragraph title = new Paragraph();
			if (provList.size() <= 0) {
				document.add(table);
				title = new Paragraph("ไม่มีข้อมูล", font_title);
				title.setAlignment(Element.ALIGN_CENTER);
				document.add(title);
			}
			int frist = 1;
			boolean newPage = true;
			for (int prov = 0; prov < provList.size(); prov++) {
				Object[] obj = ((Object[]) provList.get(prov));

				if (!branchTmp.equals(obj[0].toString())) {
					title = new Paragraph("ธ.ก.ส. จังหวัด" + obj[14].toString()
							+ " " + obj[0].toString(), font_total);
					title.setAlignment(Element.ALIGN_LEFT);
					title.setLeading(5f);
					title.setSpacingBefore(20f);
					branchTmp = obj[0].toString();
					newPage = true;
					linePage -= 2;
				}
				Table detail = new Table(colWidth.length);
				detail.setWidths(colWidth);
				detail.setAlignment(Cell.ALIGN_CENTER);
				detail.setWidth(100);
				detail.setPadding(2f);

				Cell sCell = new Cell();

				if (linePage < table.size()) {
					if (table.size() > 1) {
						document.add(title);
					}
					document.add(table);

					document.newPage();
					document.add(head);
					document.add(head_date);
					table = new Table(1);
					table.setAlignment(Cell.ALIGN_CENTER);
					table.setWidth(100);
					table.setPadding(2f);
					table.insertTable(headTable(newRows(colWidth), font_body,
							headTable));
					title.setLeading(5f);
					title.setSpacingAfter(0f);
					newPage = true;
					linePage = 21;
				}

				if (subDistrictTmp.equals(obj[1].toString()) && !newPage) {
					sCell = new Cell(new Phrase("", font_body));
					sCell.setBorderWidthTop(0);
					sCell.setBorderWidthBottom(0);
					sCell.setBorderWidthRight(0.1f);
					detail.addCell(sCell);
				} else {
					sCell = new Cell(new Phrase(obj[1].toString(), font_body));
					sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
					sCell.setVerticalAlignment(Cell.ALIGN_TOP);
					sCell.setBorderColorTop(Color.GRAY);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthBottom(0);
					sCell.setBorderWidthRight(0.1f);
					detail.addCell(sCell);
					newPage = true;
				}

				if (idCardTmp.equals(obj[2].toString()) && !newPage
						&& subDistrictTmp.equals(obj[1].toString())) { // update
																		// by
																		// Pom
																		// 11
																		// /08/
																		// 2014

					sCell = new Cell(new Phrase("", font_body));
					sCell.setBorderWidthTop(0);
					sCell.setBorderWidthBottom(0);
					sCell.setBorderWidthRight(0.1f);
					detail.addCell(sCell);
					detail.addCell(sCell);
					detail.addCell(sCell);

					if (breedTmp.equals(obj[7].toString()) && !newPage) {
						sCell = new Cell(new Phrase("", font_body));
						sCell.setBorderWidthTop(0);
						sCell.setBorderWidthBottom(0);
						sCell.setBorderWidthRight(0.1f);
						detail.addCell(sCell);
					} else {
						sCell = new Cell(new Phrase(obj[7].toString(),
								font_body));
						sCell.setHorizontalAlignment(Cell.ALIGN_LEFT);
						sCell.setVerticalAlignment(Cell.ALIGN_TOP);
						sCell.setBorderColorTop(Color.GRAY);
						sCell.setBorderWidthTop(0.1f);
						sCell.setBorderWidthBottom(0);
						sCell.setBorderWidthRight(0.1f);
						detail.addCell(sCell);
						breedTmp = obj[7].toString();
						newPage = false;
					}
				} else {
					sCell = new Cell(new Phrase(obj[2].toString(), font_body));
					sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
					sCell.setVerticalAlignment(Cell.ALIGN_TOP);
					sCell.setBorderColorTop(Color.GRAY);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthBottom(0);
					sCell.setBorderWidthRight(0.1f);
					detail.addCell(sCell);

					sCell = new Cell(new Phrase(" " + obj[3].toString()
							+ obj[4].toString() + " " + obj[5].toString(),
							font_body));
					sCell.setHorizontalAlignment(Cell.ALIGN_LEFT);
					sCell.setVerticalAlignment(Cell.ALIGN_TOP);
					sCell.setBorderColorTop(Color.GRAY);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthBottom(0);
					sCell.setBorderWidthRight(0.1f);
					detail.addCell(sCell);

					idCardTmp = obj[2].toString();

					sCell = new Cell(new Phrase(obj[6].toString(), font_body));
					sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
					sCell.setVerticalAlignment(Cell.ALIGN_TOP);
					sCell.setBorderColorTop(Color.GRAY);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthBottom(0);
					sCell.setBorderWidthRight(0.1f);
					detail.addCell(sCell);

					sCell = new Cell(new Phrase(obj[7].toString(), font_body));
					sCell.setHorizontalAlignment(Cell.ALIGN_LEFT);
					sCell.setVerticalAlignment(Cell.ALIGN_TOP);
					sCell.setBorderColorTop(Color.GRAY);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthBottom(0);
					sCell.setBorderWidthRight(0.1f);
					detail.addCell(sCell);
					breedTmp = obj[7].toString();
					newPage = false;
				}

				Table tmp = new Table(2);
				sCell = new Cell(new Phrase(convertDate(obj[8].toString()),
						font_body));
				sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
				sCell.setVerticalAlignment(Cell.ALIGN_TOP);
				sCell.setBorderColorTop(Color.GRAY);
				sCell.setBorderWidthTop(0.1f);
				sCell.setBorderWidthRight(0.1f);
				tmp.addCell(sCell);

				if (obj[9] != null) {
					sCell = new Cell(new Phrase(convertDate(obj[9].toString()),
							font_body));
					sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
					sCell.setVerticalAlignment(Cell.ALIGN_TOP);
					sCell.setBorderColorTop(Color.GRAY);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
				} else {
					sCell = new Cell(new Phrase("", font_body));
					sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
					sCell.setVerticalAlignment(Cell.ALIGN_TOP);
					sCell.setBorderColorTop(Color.GRAY);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
				}
				tmp.addCell(sCell);
				detail.insertTable(tmp);

				// tmp = new Table(3);
				int rai = Integer.parseInt(obj[10].toString());
				int ngan = Integer.parseInt(obj[11].toString());
				int wah = Integer.parseInt(obj[12].toString());

				if (wah / 100 > 0) {
					ngan += wah / 100;
					wah = wah % 100;

				}
				if (ngan / 4 > 0) {
					rai += ngan / 4;
					ngan = ngan % 4;
				}

				sCell = new Cell(new Phrase(String.format("%,d-%d-%d", rai,
						ngan, wah), font_body));
				sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
				sCell.setVerticalAlignment(Cell.ALIGN_TOP);
				sCell.setBorderColorTop(Color.GRAY);
				sCell.setBorderWidthTop(0.1f);
				sCell.setBorderWidthRight(0.1f);
				detail.addCell(sCell);

				sCell = new Cell(new Phrase(String.format("%,.2f", Float
						.parseFloat(obj[13].toString())), font_body));
				sCell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
				sCell.setVerticalAlignment(Cell.ALIGN_TOP);
				sCell.setBorderColorTop(Color.GRAY);
				sCell.setBorderWidthTop(0.1f);
				sCell.setBorderWidthRight(0.1f);
				detail.addCell(sCell);

				table.insertTable(detail);

				dtPlantRai += rai;
				dtPlantNgan += ngan;
				dtPlantWah += wah;
				dtFRecord += Float.parseFloat(obj[13].toString());
				subDistrictTmp = obj[1].toString();
				provinceTmp = obj[14].toString();

				if (prov + 1 != provList.size()) {
					if (!subDistrictTmp.equals(((Object[]) provList
							.get(prov + 1))[1].toString())
							|| !branchTmp.equals(((Object[]) provList
									.get(prov + 1))[0].toString())) {
						calRai = totalRai(dtPlantRai, dtPlantNgan, dtPlantWah);
						String[] total = new String[] {
								String.format("%,d-%d-%d|", calRai[0],
										calRai[1], calRai[2]),
								String.format("%,.2f", dtFRecord) };
						if (linePage < table.size()) {
							if (table.size() > 1) {
								document.add(title);
							}
							document.add(table);

							document.newPage();
							document.add(head);
							document.add(head_date);
							table = new Table(1);
							table.setAlignment(Cell.ALIGN_CENTER);
							table.setWidth(100);
							table.setPadding(2f);
							table.insertTable(headTable(newRows(colWidth),
									font_body, headTable));
							title.setLeading(5f);
							title.setSpacingAfter(0f);
							newPage = true;
							linePage = 21;
						}
						table.insertTable(totalTable(colTotalWidth, font_total,
								"รวม", total));

						btPlantRai += dtPlantRai;
						btPlantNgan += dtPlantNgan;
						btPlantWah += dtPlantWah;
						btFRecord += dtFRecord;
						dtPlantRai = 0;
						dtPlantNgan = 0;
						dtPlantWah = 0;
						dtPlantNgan = 0;
						dtPlantWah = 0;
						dtFRecord = 0;
					}

					if (!branchTmp
							.equals(((Object[]) provList.get(prov + 1))[0]
									.toString())) {
						calRai = totalRai(btPlantRai, btPlantNgan, btPlantWah);
						String[] totalBran = new String[] {
								String.format("%,d-%d-%d|", calRai[0],
										calRai[1], calRai[2]),
								String.format("%,.2f", btFRecord) };
						if (linePage < table.size()) {
							if (table.size() > 1) {
								document.add(title);
							}
							document.add(table);

							document.newPage();
							document.add(head);
							document.add(head_date);
							table = new Table(1);
							table.setAlignment(Cell.ALIGN_CENTER);
							table.setWidth(100);
							table.setPadding(2f);
							table.insertTable(headTable(newRows(colWidth),
									font_body, headTable));
							title.setLeading(5f);
							title.setSpacingAfter(0f);
							newPage = true;
							linePage = 21;
						}
						table.insertTable(totalTable(colTotalWidth, font_total,
								"ยอดรวมสาขา " + branchTmp, totalBran));

						ptPlantRai += btPlantRai;
						ptPlantNgan += btPlantNgan;
						ptPlantWah += btPlantWah;
						ptFRecord += btFRecord;
						btPlantRai = 0;
						btPlantNgan = 0;
						btPlantWah = 0;
						btFRecord = 0;

						if (!provinceTmp.equals(((Object[]) provList
								.get(prov + 1))[14].toString())) {
							calRai = totalRai(ptPlantRai, ptPlantNgan,
									ptPlantWah);
							String[] totalProv = new String[] {
									String.format("%,d-%d-%d|", calRai[0],
											calRai[1], calRai[2]),
									String.format("%,.2f", ptFRecord) };
							if (linePage + 3 < table.size()) {
								if (table.size() > 1) {
									document.add(title);
								}
								document.add(table);

								document.newPage();
								document.add(head);
								document.add(head_date);
								table = new Table(1);
								table.setAlignment(Cell.ALIGN_CENTER);
								table.setWidth(100);
								table.setPadding(2f);
								table.insertTable(headTable(newRows(colWidth),
										font_body, headTable));
								title.setLeading(5f);
								title.setSpacingAfter(0f);
								newPage = true;
								linePage = 21;
							}
							table.insertTable(totalTable(colTotalWidth,
									font_total, "ยอดรวมจังหวัด "
											+ obj[14].toString(), totalProv));

							ptPlantRai = 0;
							ptPlantNgan = 0;
							ptPlantWah = 0;
							ptFRecord = 0;
						}

						if (table.size() > 1) {
							document.add(title);
							document.add(table);
							linePage -= table.size();
							table = new Table(1);
							table.setAlignment(Cell.ALIGN_CENTER);
							table.setWidth(100);
							table.setPadding(2f);
						}
					}
				} else {
					calRai = totalRai(dtPlantRai, dtPlantNgan, dtPlantWah);
					String[] total = new String[] {
							String.format("%,d-%d-%d|", calRai[0], calRai[1],
									calRai[2]),
							String.format("%,.2f", dtFRecord) };
					if (linePage + 1 < table.size()) {
						if (table.size() > 1) {
							document.add(title);
						}
						document.add(table);

						document.newPage();
						document.add(head);
						document.add(head_date);
						table = new Table(1);
						table.setAlignment(Cell.ALIGN_CENTER);
						table.setWidth(100);
						table.setPadding(2f);
						table.insertTable(headTable(newRows(colWidth),
								font_body, headTable));
						title.setLeading(5f);
						title.setSpacingAfter(0f);
						newPage = true;
						linePage = 21;
					}
					table.insertTable(totalTable(colTotalWidth, font_total,
							"รวม", total));
					btPlantRai += dtPlantRai;
					btPlantNgan += dtPlantNgan;
					btPlantWah += dtPlantWah;
					btFRecord += dtFRecord;
					dtPlantRai = 0;
					dtPlantNgan = 0;
					dtPlantWah = 0;
					dtPlantNgan = 0;
					dtPlantWah = 0;
					dtFRecord = 0;

					calRai = totalRai(btPlantRai, btPlantNgan, btPlantWah);
					String[] totalBran = new String[] {
							String.format("%,d-%d-%d|", calRai[0], calRai[1],
									calRai[2]),
							String.format("%,.2f", btFRecord) };
					if (linePage + 1 < table.size()) {
						if (table.size() > 1) {
							document.add(title);
						}
						document.add(table);

						document.newPage();
						document.add(head);
						document.add(head_date);
						table = new Table(1);
						table.setAlignment(Cell.ALIGN_CENTER);
						table.setWidth(100);
						table.setPadding(2f);
						table.insertTable(headTable(newRows(colWidth),
								font_body, headTable));
						title.setLeading(5f);
						title.setSpacingAfter(0f);
						newPage = true;
						linePage = 21;
					}
					table.insertTable(totalTable(colTotalWidth, font_total,
							"ยอดรวมสาขา " + branchTmp, totalBran));
					ptPlantRai += btPlantRai;
					ptPlantNgan += btPlantNgan;
					ptPlantWah += btPlantWah;
					ptFRecord += btFRecord;
					btPlantRai = 0;
					btPlantNgan = 0;
					btPlantWah = 0;
					btFRecord = 0;

					calRai = totalRai(ptPlantRai, ptPlantNgan, ptPlantWah);
					String[] totalProv = new String[] {
							String.format("%,d-%d-%d|", calRai[0], calRai[1],
									calRai[2]),
							String.format("%,.2f", ptFRecord) };

					if (linePage + 1 < table.size()) {
						if (table.size() > 1) {
							document.add(title);
						}
						document.add(table);

						document.newPage();
						document.add(head);
						document.add(head_date);
						table = new Table(1);
						table.setAlignment(Cell.ALIGN_CENTER);
						table.setWidth(100);
						table.setPadding(2f);
						table.insertTable(headTable(newRows(colWidth),
								font_body, headTable));
						title.setLeading(5f);
						title.setSpacingAfter(0f);
						newPage = true;
						linePage = 21;
					}
					table.insertTable(totalTable(colTotalWidth, font_total,
							"ยอดรวมจังหวัด " + obj[14].toString(), totalProv));

					document.add(title);
					document.add(table);
				}
			}

		} catch (Exception e) {
			document.add(new Paragraph("เกิดการดึงข้อมูลผิดพลาด", font_title));
			e.printStackTrace();
			log.error(e, e);
		} finally {
			sf.getCurrentSession().close();
		}
		return document;
	}

	private Document ReportR101_1(SessionFactory sf, Document document,
			Font font_title, Font font_body, Font font_total, ReportForm eform)
			throws DocumentException {
		try {
			sf.getCurrentSession().beginTransaction();
			Paragraph head = new Paragraph(
					"รายงานประมาณการผลผลิตของเกษตรกร(ตามตำบลที่เพาะปลูก) ตามปีการผลิต "
							+ eform.getPlantYear() + "/" + eform.getPlantNo(),
					font_title);
			head.setAlignment(Element.ALIGN_CENTER);
			document.add(head);

			Paragraph head_date = new Paragraph("สิ้นสุด ณ วันที่ "
					+ dateThai(eform.getEffectiveDate(), false), font_title);
			head_date.setAlignment(Element.ALIGN_CENTER);
			head_date.setSpacingBefore(5f);
			document.add(head_date);

			Table table = new Table(1);
			table.setAlignment(Cell.ALIGN_CENTER);
			table.setWidth(100);
			table.setPadding(2f);

			float[] colWidth, colTotalWidth;
			colWidth = new float[] { 15, 15, 20, 15, 15, 20, 15, 15 };
			colTotalWidth = new float[] { 100, 15, 15 };
			String[] headTable = new String[] { "ตำบลที่ตั้งแปลง",
					"บัตรประจำตัว\nประชาชน\n(เลขที่)", "ชื่อ-สกุล",
					"ใบรับรองเกษตรกร\n(เลขที่)", "กลุ่มพันธุ์",
					"|วันที่|เพาะปลูก|จะเก็บเกี่ยว",
					"เนื้อที่เพาะปลูก\nไร่-งาน-วา",
					"ประมาณการผลผลิตรวม \n(กก.)" };
			table
					.insertTable(headTable(newRows(colWidth), font_body,
							headTable));
			int linePage = 23;
			ReportHome rhome = new ReportHome();
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

			List provList = rhome.findR101_1ByCriteria(eform.getPlantYear(),
					eform.getPlantNo(), df.parse(eform.getEffectiveDate()),
					eform.getProvinceNo(), eform.getBranchCode(), eform
							.getBreedTypeId(), eform.getBreedGroupId());

			if (eform.getBranchCode() > 0) {
				BranchHome bhome = new BranchHome();
				List<Branch> pbList = bhome
						.findByPBranch(eform.getBranchCode());
				List<Branch> pbranchCode = new ArrayList();
				if (pbList.size() > 0) {
					pbranchCode
							.addAll(getPBranch(pbList, eform.getBranchCode()));
					for (int i = 0; i < pbranchCode.size(); i++) {
						provList.addAll(rhome.findR101_1ByCriteria(eform
								.getPlantYear(), eform.getPlantNo(), df
								.parse(eform.getEffectiveDate()), eform
								.getProvinceNo(), pbranchCode.get(i)
								.getBranchCode(), eform.getBreedTypeId(), eform
								.getBreedGroupId()));
					}
				}
			}

			String branchTmp = "", subDistrictTmp = "", provinceTmp = "", idCardTmp = "", breedTmp = "";
			int dtPlantRai = 0, dtPlantNgan = 0, dtPlantWah = 0;
			float dtFRecord = 0;
			int btPlantRai = 0, btPlantNgan = 0, btPlantWah = 0;
			float btFRecord = 0;
			int ptPlantRai = 0, ptPlantNgan = 0, ptPlantWah = 0;
			float ptFRecord = 0;
			Paragraph title = new Paragraph();
			if (provList.size() <= 0) {
				document.add(table);
				title = new Paragraph("ไม่มีข้อมูล", font_title);
				title.setAlignment(Element.ALIGN_CENTER);
				document.add(title);
			}
			int frist = 1;
			boolean newPage = true;
			for (int prov = 0; prov < provList.size(); prov++) {
				Object[] obj = ((Object[]) provList.get(prov));

				if (!branchTmp.equals(obj[0].toString())) {
					title = new Paragraph("ธ.ก.ส. จังหวัด" + obj[14].toString()
							+ " " + obj[0].toString(), font_total);
					title.setAlignment(Element.ALIGN_LEFT);
					title.setLeading(5f);
					title.setSpacingBefore(20f);
					branchTmp = obj[0].toString();
					newPage = true;
					linePage -= 2;
				}
				Table detail = new Table(colWidth.length);
				detail.setWidths(colWidth);
				detail.setAlignment(Cell.ALIGN_CENTER);
				detail.setWidth(100);
				detail.setPadding(2f);

				Cell sCell = new Cell();

				if (linePage < table.size()) {
					if (table.size() > 1) {
						document.add(title);
					}
					document.add(table);

					document.newPage();
					document.add(head);
					document.add(head_date);
					table = new Table(1);
					table.setAlignment(Cell.ALIGN_CENTER);
					table.setWidth(100);
					table.setPadding(2f);
					table.insertTable(headTable(newRows(colWidth), font_body,
							headTable));
					title.setLeading(5f);
					title.setSpacingAfter(0f);
					newPage = true;
					linePage = 21;
				}

				if (subDistrictTmp.equals(obj[1].toString()) && !newPage) {
					sCell = new Cell(new Phrase("", font_body));
					sCell.setBorderWidthTop(0);
					sCell.setBorderWidthBottom(0);
					sCell.setBorderWidthRight(0.1f);
					detail.addCell(sCell);
				} else {
					sCell = new Cell(new Phrase(obj[1].toString(), font_body));
					sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
					sCell.setVerticalAlignment(Cell.ALIGN_TOP);
					sCell.setBorderColorTop(Color.GRAY);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
					sCell.setBorderWidthBottom(0);
					sCell.setBorderWidthRight(0.1f);
					detail.addCell(sCell);
					newPage = true;
				}
				if (idCardTmp.equals(obj[2].toString()) && !newPage) {
					sCell = new Cell(new Phrase("", font_body));
					sCell.setBorderWidthTop(0);
					sCell.setBorderWidthBottom(0);
					sCell.setBorderWidthRight(0.1f);
					detail.addCell(sCell);
					detail.addCell(sCell);
					detail.addCell(sCell);

					if (breedTmp.equals(obj[7].toString()) && !newPage) {
						sCell = new Cell(new Phrase("", font_body));
						sCell.setBorderWidthTop(0);
						sCell.setBorderWidthBottom(0);
						sCell.setBorderWidthRight(0.1f);
						detail.addCell(sCell);
					} else {
						sCell = new Cell(new Phrase(obj[7].toString(),
								font_body));
						sCell.setHorizontalAlignment(Cell.ALIGN_LEFT);
						sCell.setVerticalAlignment(Cell.ALIGN_TOP);
						sCell.setBorderColorTop(Color.GRAY);
						sCell.setBorderWidthTop(0.1f);
						sCell.setBorderWidthRight(0.1f);
						sCell.setBorderWidthTop(0.1f);
						sCell.setBorderWidthRight(0.1f);
						sCell.setBorderWidthBottom(0);
						sCell.setBorderWidthRight(0.1f);
						detail.addCell(sCell);
						breedTmp = obj[7].toString();
						newPage = false;
					}
				} else {
					sCell = new Cell(new Phrase(obj[2].toString(), font_body));
					sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
					sCell.setVerticalAlignment(Cell.ALIGN_TOP);
					sCell.setBorderColorTop(Color.GRAY);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
					sCell.setBorderWidthBottom(0);
					sCell.setBorderWidthRight(0.1f);
					detail.addCell(sCell);

					sCell = new Cell(new Phrase(" " + obj[3].toString()
							+ obj[4].toString() + " " + obj[5].toString(),
							font_body));
					sCell.setHorizontalAlignment(Cell.ALIGN_LEFT);
					sCell.setVerticalAlignment(Cell.ALIGN_TOP);
					sCell.setBorderColorTop(Color.GRAY);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
					sCell.setBorderWidthBottom(0);
					sCell.setBorderWidthRight(0.1f);
					detail.addCell(sCell);

					sCell = new Cell(new Phrase(obj[6].toString(), font_body));
					sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
					sCell.setVerticalAlignment(Cell.ALIGN_TOP);
					sCell.setBorderColorTop(Color.GRAY);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
					sCell.setBorderWidthBottom(0);
					sCell.setBorderWidthRight(0.1f);
					detail.addCell(sCell);

					idCardTmp = obj[2].toString();

					sCell = new Cell(new Phrase(obj[7].toString(), font_body));
					sCell.setHorizontalAlignment(Cell.ALIGN_LEFT);
					sCell.setVerticalAlignment(Cell.ALIGN_TOP);
					sCell.setBorderColorTop(Color.GRAY);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
					sCell.setBorderWidthBottom(0);
					sCell.setBorderWidthRight(0.1f);
					detail.addCell(sCell);
					breedTmp = obj[7].toString();
					newPage = false;
				}

				Table tmp = new Table(2);
				sCell = new Cell(new Phrase(convertDate(obj[8].toString()),
						font_body));
				sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
				sCell.setVerticalAlignment(Cell.ALIGN_TOP);
				sCell.setBorderColorTop(Color.GRAY);
				sCell.setBorderWidthTop(0.1f);
				sCell.setBorderWidthRight(0.1f);
				tmp.addCell(sCell);

				if (obj[9] != null) {
					sCell = new Cell(new Phrase(convertDate(obj[9].toString()),
							font_body));
					sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
					sCell.setVerticalAlignment(Cell.ALIGN_TOP);
					sCell.setBorderColorTop(Color.GRAY);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
				} else {
					sCell = new Cell(new Phrase("", font_body));
					sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
					sCell.setVerticalAlignment(Cell.ALIGN_TOP);
					sCell.setBorderColorTop(Color.GRAY);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
				}

				tmp.addCell(sCell);
				detail.insertTable(tmp);

				// tmp = new Table(3);
				int rai = Integer.parseInt(obj[10].toString());
				int ngan = Integer.parseInt(obj[11].toString());
				int wah = Integer.parseInt(obj[12].toString());

				if (wah / 100 > 0) {
					ngan += wah / 100;
					wah = wah % 100;

				}
				if (ngan / 4 > 0) {
					rai += ngan / 4;
					ngan = ngan % 4;
				}

				sCell = new Cell(new Phrase(String.format("%,d-%d-%d", rai,
						ngan, wah), font_body));
				sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
				sCell.setVerticalAlignment(Cell.ALIGN_TOP);
				sCell.setBorderColorTop(Color.GRAY);
				sCell.setBorderWidthTop(0.1f);
				sCell.setBorderWidthRight(0.1f);
				detail.addCell(sCell);

				sCell = new Cell(new Phrase(String.format("%,.2f", Float
						.parseFloat(obj[13].toString())), font_body));
				sCell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
				sCell.setVerticalAlignment(Cell.ALIGN_TOP);
				sCell.setBorderColorTop(Color.GRAY);
				sCell.setBorderWidthTop(0.1f);
				sCell.setBorderWidthRight(0.1f);
				detail.addCell(sCell);

				table.insertTable(detail);

				dtPlantRai += rai;
				dtPlantNgan += ngan;
				dtPlantWah += wah;
				dtFRecord += Float.parseFloat(obj[13].toString());
				subDistrictTmp = obj[1].toString();
				provinceTmp = obj[14].toString();

				if (prov + 1 != provList.size()) {
					if (!subDistrictTmp.equals(((Object[]) provList
							.get(prov + 1))[1].toString())
							|| !branchTmp.equals(((Object[]) provList
									.get(prov + 1))[0].toString())) {

						calRai = totalRai(dtPlantRai, dtPlantNgan, dtPlantWah);
						String[] total = new String[] {
								String.format("%,d-%d-%d|", calRai[0],
										calRai[1], calRai[2]),
								String.format("%,.2f", dtFRecord) };
						if (linePage + 3 < table.size()) {
							if (table.size() > 1) {
								document.add(title);
							}
							document.add(table);

							document.newPage();
							document.add(head);
							document.add(head_date);
							table = new Table(1);
							table.setAlignment(Cell.ALIGN_CENTER);
							table.setWidth(100);
							table.setPadding(2f);
							table.insertTable(headTable(newRows(colWidth),
									font_body, headTable));
							title.setLeading(5f);
							title.setSpacingAfter(0f);
							newPage = true;
							linePage = 21;
						}
						table.insertTable(totalTable(colTotalWidth, font_total,
								"รวม", total));

						btPlantRai += dtPlantRai;
						btPlantNgan += dtPlantNgan;
						btPlantWah += dtPlantWah;
						btFRecord += dtFRecord;
						dtPlantRai = 0;
						dtPlantNgan = 0;
						dtPlantWah = 0;
						dtPlantNgan = 0;
						dtPlantWah = 0;
						dtFRecord = 0;
					}

					if (!branchTmp
							.equals(((Object[]) provList.get(prov + 1))[0]
									.toString())) {
						calRai = totalRai(btPlantRai, btPlantNgan, btPlantWah);
						String[] totalBran = new String[] {
								String.format("%,d-%d-%d|", calRai[0],
										calRai[1], calRai[2]),
								String.format("%,.2f", btFRecord) };
						if (linePage + 3 < table.size()) {
							if (table.size() > 1) {
								document.add(title);
							}
							document.add(table);

							document.newPage();
							document.add(head);
							document.add(head_date);
							table = new Table(1);
							table.setAlignment(Cell.ALIGN_CENTER);
							table.setWidth(100);
							table.setPadding(2f);
							table.insertTable(headTable(newRows(colWidth),
									font_body, headTable));
							title.setLeading(5f);
							title.setSpacingAfter(0f);
							newPage = true;
							linePage = 21;
						}
						table.insertTable(totalTable(colTotalWidth, font_total,
								"ยอดรวมสาขา " + branchTmp, totalBran));

						ptPlantRai += btPlantRai;
						ptPlantNgan += btPlantNgan;
						ptPlantWah += btPlantWah;
						ptFRecord += btFRecord;
						btPlantRai = 0;
						btPlantNgan = 0;
						btPlantWah = 0;
						btFRecord = 0;

						if (!provinceTmp.equals(((Object[]) provList
								.get(prov + 1))[14].toString())) {
							calRai = totalRai(ptPlantRai, ptPlantNgan,
									ptPlantWah);
							String[] totalProv = new String[] {
									String.format("%,d-%d-%d|", calRai[0],
											calRai[1], calRai[2]),
									String.format("%,.2f", ptFRecord) };
							if (linePage + 3 < table.size()) {
								if (table.size() > 1) {
									document.add(title);
								}
								document.add(table);

								document.newPage();
								document.add(head);
								document.add(head_date);
								table = new Table(1);
								table.setAlignment(Cell.ALIGN_CENTER);
								table.setWidth(100);
								table.setPadding(2f);
								table.insertTable(headTable(newRows(colWidth),
										font_body, headTable));
								title.setLeading(5f);
								title.setSpacingAfter(0f);
								newPage = true;
								linePage = 21;
							}
							table.insertTable(totalTable(colTotalWidth,
									font_total, "ยอดรวมจังหวัด "
											+ obj[14].toString(), totalProv));

							ptPlantRai = 0;
							ptPlantNgan = 0;
							ptPlantWah = 0;
							ptFRecord = 0;
						}

						if (table.size() > 1) {
							document.add(title);
							document.add(table);
							linePage -= table.size();
							table = new Table(1);
							table.setAlignment(Cell.ALIGN_CENTER);
							table.setWidth(100);
							table.setPadding(2f);
						}
					}
				} else {
					calRai = totalRai(dtPlantRai, dtPlantNgan, dtPlantWah);
					String[] total = new String[] {
							String.format("%,d-%d-%d|", calRai[0], calRai[1],
									calRai[2]),
							String.format("%,.2f", dtFRecord) };
					if (linePage + 1 < table.size()) {
						if (table.size() > 1) {
							document.add(title);
						}
						document.add(table);

						document.newPage();
						document.add(head);
						document.add(head_date);
						table = new Table(1);
						table.setAlignment(Cell.ALIGN_CENTER);
						table.setWidth(100);
						table.setPadding(2f);
						table.insertTable(headTable(newRows(colWidth),
								font_body, headTable));
						title.setLeading(5f);
						title.setSpacingAfter(0f);
						newPage = true;
						linePage = 20;
					}
					table.insertTable(totalTable(colTotalWidth, font_total,
							"รวม", total));

					btPlantRai += dtPlantRai;
					btPlantNgan += dtPlantNgan;
					btPlantWah += dtPlantWah;
					btFRecord += dtFRecord;
					dtPlantRai = 0;
					dtPlantNgan = 0;
					dtPlantWah = 0;
					dtPlantNgan = 0;
					dtPlantWah = 0;
					dtFRecord = 0;

					calRai = totalRai(btPlantRai, btPlantNgan, btPlantWah);
					String[] totalBran = new String[] {
							String.format("%,d-%d-%d|", calRai[0], calRai[1],
									calRai[2]),
							String.format("%,.2f", btFRecord) };
					if (linePage + 1 < table.size()) {
						if (table.size() > 1) {
							document.add(title);
						}
						document.add(table);

						document.newPage();
						document.add(head);
						document.add(head_date);
						table = new Table(1);
						table.setAlignment(Cell.ALIGN_CENTER);
						table.setWidth(100);
						table.setPadding(2f);
						table.insertTable(headTable(newRows(colWidth),
								font_body, headTable));
						title.setLeading(5f);
						title.setSpacingAfter(0f);
						newPage = true;
						linePage = 20;
					}
					table.insertTable(totalTable(colTotalWidth, font_total,
							"ยอดรวมสาขา " + branchTmp, totalBran));

					ptPlantRai += btPlantRai;
					ptPlantNgan += btPlantNgan;
					ptPlantWah += btPlantWah;
					ptFRecord += btFRecord;
					btPlantRai = 0;
					btPlantNgan = 0;
					btPlantWah = 0;
					btFRecord = 0;

					calRai = totalRai(ptPlantRai, ptPlantNgan, ptPlantWah);
					String[] totalProv = new String[] {
							String.format("%,d-%d-%d|", calRai[0], calRai[1],
									calRai[2]),
							String.format("%,.2f", ptFRecord) };
					if (linePage + 1 < table.size()) {
						if (table.size() > 1) {
							document.add(title);
						}
						document.add(table);

						document.newPage();
						document.add(head);
						document.add(head_date);
						table = new Table(1);
						table.setAlignment(Cell.ALIGN_CENTER);
						table.setWidth(100);
						table.setPadding(2f);
						table.insertTable(headTable(newRows(colWidth),
								font_body, headTable));
						title.setLeading(5f);
						title.setSpacingAfter(0f);
						newPage = true;
						linePage = 20;
					}
					table.insertTable(totalTable(colTotalWidth, font_total,
							"ยอดรวมจังหวัด " + obj[14].toString(), totalProv));

					document.add(title);
					document.add(table);
				}
			}

		} catch (Exception e) {
			document.add(new Paragraph("เกิดการดึงข้อมูลผิดพลาด", font_title));
			e.printStackTrace();
			log.error(e, e);
		} finally {
			sf.getCurrentSession().close();
		}
		return document;
	}

	private Document ReportR102(SessionFactory sf, Document document,
			Font font_title, Font font_body, Font font_total, ReportForm eform)
			throws DocumentException {
		try {
			sf.getCurrentSession().beginTransaction();
			Paragraph head = new Paragraph(
					"สรุปประมาณการผลผลิตของเกษตรกรผู้ปลูกพืชเศรษฐกิจ ตามปีการผลิต "
							+ eform.getPlantYear() + "/" + eform.getPlantNo(),
					font_title);
			head.setAlignment(Element.ALIGN_CENTER);
			document.add(head);

			Paragraph head_date = new Paragraph("สิ้นสุด ณ วันที่ "
					+ dateThai(eform.getEffectiveDate(), false), font_title);
			head_date.setAlignment(Element.ALIGN_CENTER);
			head_date.setSpacingBefore(5f);
			document.add(head_date);

			Table table = new Table(1);
			table.setAlignment(Cell.ALIGN_CENTER);
			table.setWidth(100);
			table.setPadding(2f);

			float[] colWidth, colTotalWidth;
			colWidth = new float[] { 40, 45, 45, 45 };
			colTotalWidth = new float[] { 40, 15, 15, 15, 15, 15, 15, 15, 15,
					15 };
			String[] breedTypeHead = eform.getBreedTypeItem().split(",");
			String breedTypeId = breedTypeHead[3] + "," + breedTypeHead[4]
					+ "," + breedTypeHead[5];
			String[] headTable = new String[] {
					"ตำบลที่ตั้งแปลง",
					"|"
							+ breedTypeHead[0]
							+ "|จำนวนเกษตรกร\n(ราย)|เนื้อที่เพาะปลูก\nไร่-งาน-วา|ประมาณการผลผลิต\n(กก.)",
					"|"
							+ breedTypeHead[1]
							+ "|จำนวนเกษตรกร\n(ราย)|เนื้อที่เพาะปลูก\nไร่-งาน-วา|ประมาณการผลผลิต\n(กก.)",
					"|"
							+ breedTypeHead[2]
							+ "|จำนวนเกษตรกร\n(ราย)|เนื้อที่เพาะปลูก\nไร่-งาน-วา|ประมาณการผลผลิต\n(กก.)" };
			table
					.insertTable(headTable(newRows(colWidth), font_body,
							headTable));
			int linePage = 21;
			ReportHome rhome = new ReportHome();
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

			List provList = rhome.findR102ByCriteria(eform.getPlantYear(),
					eform.getPlantNo(), df.parse(eform.getEffectiveDate()),
					eform.getProvinceNo(), breedTypeId, bCode);

			String branchTmp = "", subDistrictTmp = "", ProviceTmp = "";
			int[] sQuantity = { 0, 0, 0 }, sPlantRai = { 0, 0, 0 }, sPlantNgan = {
					0, 0, 0 }, sPlantWah = { 0, 0, 0 };
			float[] sFRecord = { 0, 0, 0 };
			int[] pQuantity = { 0, 0, 0 }, pPlantRai = { 0, 0, 0 }, pPlantNgan = {
					0, 0, 0 }, pPlantWah = { 0, 0, 0 };
			float[] pFRecord = { 0, 0, 0 };
			Paragraph title = new Paragraph();
			if (provList.size() > 0) {
				Object[] obj = ((Object[]) provList.get(0));
				title = new Paragraph("ธ.ก.ส. จังหวัด" + obj[8].toString(),
						font_title);
				title.setAlignment(Element.ALIGN_LEFT);
				title.setLeading(5f);
				title.setSpacingBefore(20f);
				document.add(title);
				ProviceTmp = obj[8].toString();
			}

			Table detail = new Table(colWidth.length);
			detail.setWidths(colWidth);
			detail.setAlignment(Cell.ALIGN_CENTER);
			detail.setWidth(100);
			detail.setPadding(2f);

			int breedTypeIndex = 0;
			int frist = 1;
			for (int prov = 0; prov < provList.size(); prov++) {
				Object[] obj = ((Object[]) provList.get(prov));
				Cell sCell = new Cell();

				if (!branchTmp.equals(obj[0].toString())) {
					if ((!branchTmp.equals(obj[0].toString()) && !branchTmp
							.equals(""))
							|| !subDistrictTmp.equals(obj[1].toString())
							&& !subDistrictTmp.equals("")) {

						if (linePage < table.size()) {
							document.add(table);

							document.newPage();
							document.add(head);
							document.add(head_date);
							document.add(title);
							table = new Table(1);
							table.setAlignment(Cell.ALIGN_CENTER);
							table.setWidth(100);
							table.setPadding(2f);
							table.insertTable(headTable(newRows(colWidth),
									font_body, headTable));
							title.setLeading(5f);
							title.setSpacingAfter(0f);
						}
						table.insertTable(detail);

						detail = new Table(colWidth.length);
						detail.setWidths(colWidth);
						detail.setAlignment(Cell.ALIGN_CENTER);
						detail.setWidth(100);
						detail.setPadding(2f);

						for (int i = 0; i < 3; i++) {
							if (sPlantWah[i] / 100 > 0) {
								sPlantNgan[i] += sPlantWah[i] / 100;
								sPlantWah[i] = sPlantWah[i] % 100;

							}
							if (sPlantNgan[i] / 4 > 0) {
								sPlantRai[i] += sPlantNgan[i] / 4;
								sPlantNgan[i] = sPlantNgan[i] % 4;
							}
						}

						String[] total = new String[] {
								String.format("%,d|", sQuantity[0]),
								String.format("%,d-%d-%d|", sPlantRai[0],
										sPlantNgan[0], sPlantWah[0]),
								String.format("%,.2f", sFRecord[0]),
								String.format("%,d|", sQuantity[1]),
								String.format("%,d-%d-%d|", sPlantRai[1],
										sPlantNgan[1], sPlantWah[1]),
								String.format("%,.2f", sFRecord[1]),
								String.format("%,d|", sQuantity[2]),
								String.format("%,d-%d-%d|", sPlantRai[2],
										sPlantNgan[2], sPlantWah[2]),
								String.format("%,.2f", sFRecord[2]) };
						if (linePage < table.size()) {
							document.add(table);

							document.newPage();
							document.add(head);
							document.add(head_date);
							document.add(title);
							table = new Table(1);
							table.setAlignment(Cell.ALIGN_CENTER);
							table.setWidth(100);
							table.setPadding(2f);
							table.insertTable(headTable(newRows(colWidth),
									font_body, headTable));
							title.setLeading(5f);
							title.setSpacingAfter(0f);
						}
						table.insertTable(totalTable(colTotalWidth, font_total,
								"รวมสาขา " + branchTmp, total));

						for (int i = 0; i < 3; i++) {
							pQuantity[i] += sQuantity[i];
							pPlantRai[i] += sPlantRai[i];
							pPlantNgan[i] += sPlantNgan[i];
							pPlantWah[i] += sPlantWah[i];
							pFRecord[i] += sFRecord[i];
						}

						sQuantity = new int[] { 0, 0, 0 };
						sPlantRai = new int[] { 0, 0, 0 };
						sPlantNgan = new int[] { 0, 0, 0 };
						sPlantWah = new int[] { 0, 0, 0 };
						sFRecord = new float[] { 0, 0, 0 };
					}
					sCell = new Cell(new Phrase("ธ.ก.ส." + obj[0].toString(),
							font_total));
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthBottom(0);
					sCell.setBorderWidthRight(0.1f);
					detail.addCell(sCell);
					branchTmp = obj[0].toString();
					if (linePage < table.size()) {
						document.add(table);

						document.newPage();
						document.add(head);
						document.add(head_date);
						document.add(title);
						table = new Table(1);
						table.setAlignment(Cell.ALIGN_CENTER);
						table.setWidth(100);
						table.setPadding(2f);
						table.insertTable(headTable(newRows(colWidth),
								font_body, headTable));
						title.setLeading(5f);
						title.setSpacingAfter(0f);
					}
					table.insertTable(detail);

					detail = new Table(colWidth.length);
					detail.setWidths(colWidth);
					detail.setAlignment(Cell.ALIGN_CENTER);
					detail.setWidth(100);
					detail.setPadding(2f);
					subDistrictTmp = obj[1].toString();

					sCell = new Cell(new Phrase("      " + obj[1].toString(),
							font_body));
					sCell.setHorizontalAlignment(Cell.ALIGN_LEFT);
					sCell.setVerticalAlignment(Cell.ALIGN_TOP);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthBottom(0);
					sCell.setBorderWidthRight(0.1f);
					detail.addCell(sCell);
				}

				if (!subDistrictTmp.equals(obj[1].toString())) {
					if (linePage < table.size()) {
						document.add(table);

						document.newPage();
						document.add(head);
						document.add(head_date);
						document.add(title);
						table = new Table(1);
						table.setAlignment(Cell.ALIGN_CENTER);
						table.setWidth(100);
						table.setPadding(2f);
						table.insertTable(headTable(newRows(colWidth),
								font_body, headTable));
						title.setLeading(5f);
						title.setSpacingAfter(0f);
					}
					table.insertTable(detail);

					detail = new Table(colWidth.length);
					detail.setWidths(colWidth);
					detail.setAlignment(Cell.ALIGN_CENTER);
					detail.setWidth(100);
					detail.setPadding(2f);
					subDistrictTmp = obj[1].toString();

					sCell = new Cell(new Phrase("      " + obj[1].toString(),
							font_body));
					sCell.setHorizontalAlignment(Cell.ALIGN_LEFT);
					sCell.setVerticalAlignment(Cell.ALIGN_TOP);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthBottom(0);
					sCell.setBorderWidthRight(0.1f);
					detail.addCell(sCell);
				}

				for (int i = breedTypeIndex; i < 3; i++) {
					if (breedTypeHead[i].equals(obj[2].toString())) {
						Table tmp = new Table(3);
						// จำนวนเกษตกร
						sCell = new Cell(new Phrase(String.format("%,d",
								Integer.parseInt(obj[17].toString())),
								font_body));
						sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
						sCell.setVerticalAlignment(Cell.ALIGN_TOP);
						tmp.addCell(sCell);
						sQuantity[i] += Integer.parseInt(obj[17].toString());
						// เนื้อที่
						int rai = Integer.parseInt(obj[18].toString());
						int ngan = Integer.parseInt(obj[19].toString());
						int wah = Integer.parseInt(obj[20].toString());

						if (wah / 100 > 0) {
							ngan += wah / 100;
							wah = wah % 100;

						}
						if (ngan / 4 > 0) {
							rai += ngan / 4;
							ngan = ngan % 4;
						}

						sCell = new Cell(new Phrase(String.format("%,d-%d-%d",
								rai, ngan, wah), font_body));
						sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
						sCell.setVerticalAlignment(Cell.ALIGN_TOP);
						tmp.addCell(sCell);

						sPlantRai[i] += rai;
						sPlantNgan[i] += ngan;
						sPlantWah[i] += wah;

						sCell = new Cell(new Phrase(String.format("%,.2f",
								Float.parseFloat(obj[21].toString())),
								font_body));
						sCell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
						sCell.setVerticalAlignment(Cell.ALIGN_TOP);
						tmp.addCell(sCell);

						sFRecord[i] += Float.parseFloat(obj[21].toString());

						detail.insertTable(tmp);
						break;
					} else {
						breedTypeIndex++;
						Table tmp = new Table(3);
						// จำนวนเกษตกร
						sCell = new Cell(new Phrase("", font_body));
						sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
						sCell.setVerticalAlignment(Cell.ALIGN_TOP);
						tmp.addCell(sCell);
						// เนื้อที่
						sCell = new Cell(new Phrase("", font_body));
						sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
						sCell.setVerticalAlignment(Cell.ALIGN_TOP);
						tmp.addCell(sCell);
						//						
						sCell = new Cell(new Phrase("", font_body));
						sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
						sCell.setVerticalAlignment(Cell.ALIGN_TOP);
						tmp.addCell(sCell);

						detail.insertTable(tmp);
					}
				}

				if (prov + 1 < provList.size()) {
					Object[] nextObj = ((Object[]) provList.get(prov + 1));
					if (!branchTmp.equals(nextObj[0].toString())
							|| !subDistrictTmp.equals(nextObj[1].toString())) {
						if (breedTypeIndex < 2) {
							for (int i = breedTypeIndex; i < 2; i++) {
								Table tmp = new Table(3);
								// จำนวนเกษตกร
								sCell = new Cell(new Phrase("", font_body));
								sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
								sCell.setVerticalAlignment(Cell.ALIGN_TOP);
								tmp.addCell(sCell);
								// เนื้อที่
								sCell = new Cell(new Phrase("", font_body));
								sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
								sCell.setVerticalAlignment(Cell.ALIGN_TOP);
								tmp.addCell(sCell);

								sCell = new Cell(new Phrase("", font_body));
								sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
								sCell.setVerticalAlignment(Cell.ALIGN_TOP);
								tmp.addCell(sCell);

								detail.insertTable(tmp);
							}
						}
						breedTypeIndex = 0;
						continue;
					} else {
						breedTypeIndex++;
						if (breedTypeHead[breedTypeIndex].equals(nextObj[2]
								.toString())) {
							continue;
						} else {
							breedTypeIndex++;
							Table tmp = new Table(3);
							// จำนวนเกษตกร
							sCell = new Cell(new Phrase("", font_body));
							sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
							sCell.setVerticalAlignment(Cell.ALIGN_TOP);
							tmp.addCell(sCell);
							// เนื้อที่
							sCell = new Cell(new Phrase("", font_body));
							sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
							sCell.setVerticalAlignment(Cell.ALIGN_TOP);
							tmp.addCell(sCell);

							sCell = new Cell(new Phrase("", font_body));
							sCell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
							sCell.setVerticalAlignment(Cell.ALIGN_TOP);
							tmp.addCell(sCell);

							detail.insertTable(tmp);
						}
					}
				} else {
					if (breedTypeIndex < 2) {
						for (int i = breedTypeIndex; i < 2; i++) {
							Table tmp = new Table(3);
							// จำนวนเกษตกร
							sCell = new Cell(new Phrase("", font_body));
							sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
							sCell.setVerticalAlignment(Cell.ALIGN_TOP);
							tmp.addCell(sCell);
							// เนื้อที่
							sCell = new Cell(new Phrase("", font_body));
							sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
							sCell.setVerticalAlignment(Cell.ALIGN_TOP);
							tmp.addCell(sCell);

							sCell = new Cell(new Phrase("", font_body));
							sCell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
							sCell.setVerticalAlignment(Cell.ALIGN_TOP);
							tmp.addCell(sCell);

							detail.insertTable(tmp);
						}
					}
				}
			}
			if (provList.size() <= 0) {
				document.add(table);
				title = new Paragraph("ไม่มีข้อมูล", font_title);
				title.setAlignment(Element.ALIGN_CENTER);
				document.add(title);
			} else {
				if (linePage < table.size()) {
					document.add(table);

					document.newPage();
					document.add(head);
					document.add(head_date);
					document.add(title);
					table = new Table(1);
					table.setAlignment(Cell.ALIGN_CENTER);
					table.setWidth(100);
					table.setPadding(2f);
					table.insertTable(headTable(newRows(colWidth), font_body,
							headTable));
					title.setLeading(5f);
					title.setSpacingAfter(0f);
				}
				table.insertTable(detail);
				for (int i = 0; i < 3; i++) {
					if (sPlantWah[i] / 100 > 0) {
						sPlantNgan[i] += sPlantWah[i] / 100;
						sPlantWah[i] = sPlantWah[i] % 100;

					}
					if (sPlantNgan[i] / 4 > 0) {
						sPlantRai[i] += sPlantNgan[i] / 4;
						sPlantNgan[i] = sPlantNgan[i] % 4;
					}
				}
				String[] total = new String[] {
						String.format("%,d|", sQuantity[0]),
						String.format("%,d-%d-%d|", sPlantRai[0],
								sPlantNgan[0], sPlantWah[0]),
						String.format("%,.2f", sFRecord[0]),
						String.format("%,d|", sQuantity[1]),
						String.format("%,d-%d-%d|", sPlantRai[1],
								sPlantNgan[1], sPlantWah[1]),
						String.format("%,.2f", sFRecord[1]),
						String.format("%,d|", sQuantity[2]),
						String.format("%,d-%d-%d|", sPlantRai[2],
								sPlantNgan[2], sPlantWah[2]),
						String.format("%,.2f", sFRecord[2]) };
				if (linePage < table.size()) {
					document.add(table);

					document.newPage();
					document.add(head);
					document.add(head_date);
					document.add(title);
					table = new Table(1);
					table.setAlignment(Cell.ALIGN_CENTER);
					table.setWidth(100);
					table.setPadding(2f);
					table.insertTable(headTable(newRows(colWidth), font_body,
							headTable));
					title.setLeading(5f);
					title.setSpacingAfter(0f);
				}
				table.insertTable(totalTable(colTotalWidth, font_total,
						"รวมสาขา " + branchTmp, total));

				for (int i = 0; i < 3; i++) {
					pQuantity[i] += sQuantity[i];
					pPlantRai[i] += sPlantRai[i];
					pPlantNgan[i] += sPlantNgan[i];
					pPlantWah[i] += sPlantWah[i];
					pFRecord[i] += sFRecord[i];
				}
				for (int i = 0; i < 3; i++) {
					if (pPlantWah[i] / 100 > 0) {
						pPlantNgan[i] += pPlantWah[i] / 100;
						pPlantWah[i] = pPlantWah[i] % 100;

					}
					if (pPlantNgan[i] / 4 > 0) {
						pPlantRai[i] += pPlantNgan[i] / 4;
						pPlantNgan[i] = pPlantNgan[i] % 4;
					}
				}
				total = new String[] {
						String.format("%,d|", pQuantity[0]),
						String.format("%,d-%d-%d|", pPlantRai[0],
								pPlantNgan[0], pPlantWah[0]),
						String.format("%,.2f", pFRecord[0]),
						String.format("%,d|", pQuantity[1]),
						String.format("%,d-%d-%d|", pPlantRai[1],
								pPlantNgan[1], pPlantWah[1]),
						String.format("%,.2f", pFRecord[1]),
						String.format("%,d|", pQuantity[2]),
						String.format("%,d-%d-%d|", pPlantRai[2],
								pPlantNgan[2], pPlantWah[2]),
						String.format("%,.2f", pFRecord[2]) };
				if (linePage < table.size()) {
					document.add(table);

					document.newPage();
					document.add(head);
					document.add(head_date);
					document.add(title);
					table = new Table(1);
					table.setAlignment(Cell.ALIGN_CENTER);
					table.setWidth(100);
					table.setPadding(2f);
					table.insertTable(headTable(newRows(colWidth), font_body,
							headTable));
					title.setLeading(5f);
					title.setSpacingAfter(0f);
				}
				table.insertTable(totalTable(colTotalWidth, font_total,
						"ยอดรวมจังหวัด" + ProviceTmp, total));

				document.add(table);
			}

		} catch (Exception e) {
			document.add(new Paragraph("เกิดการดึงข้อมูลผิดพลาด", font_title));
			e.printStackTrace();
			log.error(e, e);
		} finally {
			sf.getCurrentSession().close();
		}
		return document;
	}

	private Document ReportR102_1(SessionFactory sf, Document document,
			Font font_title, Font font_body, Font font_total, ReportForm eform)
			throws DocumentException {
		try {
			sf.getCurrentSession().beginTransaction();
			Paragraph head = new Paragraph(
					"สรุปประมาณการผลผลิตของเกษตรกรผู้ปลูกพืชเศรษฐกิจ ตามปีการผลิต "
							+ eform.getPlantYear() + "/" + eform.getPlantNo(),
					font_title);
			head.setAlignment(Element.ALIGN_CENTER);
			document.add(head);

			Paragraph head_date = new Paragraph("สิ้นสุด ณ วันที่ "
					+ dateThai(eform.getEffectiveDate(), false), font_title);
			head_date.setAlignment(Element.ALIGN_CENTER);
			head_date.setSpacingBefore(5f);
			document.add(head_date);

			Table table = new Table(1);
			table.setAlignment(Cell.ALIGN_CENTER);
			table.setWidth(100);
			table.setPadding(2f);

			float[] colWidth, colTotalWidth;
			colWidth = new float[] { 10, 45, 45, 45 };
			colTotalWidth = new float[] { 13.75f, 4f, 6f, 8f, 8f, 4f, 6f, 8f,
					8f, 4f, 6f, 8f };
			float[] innerCol = new float[] { 1f, 0.5f, 0.75f, 1f };
			String[] breedTypeHead = eform.getBreedTypeItem().split(",");
			String breedTypeId = breedTypeHead[3] + "," + breedTypeHead[4]
					+ "," + breedTypeHead[5];
			String[] headTable = new String[] {
					"ตำบลที่ตั้งแปลง",
					"1,0.5,0.75,1,|"
							+ breedTypeHead[0]
							+ "|กลุ่มพันธุ์|จำนวน\nเกษตรกร\n(ราย)|เนื้อที่เพาะปลูก\nไร่-งาน-วา|ประมาณการผลผลิต\n(กก.)",
					"1,0.5,0.75,1,|"
							+ breedTypeHead[1]
							+ "|กลุ่มพันธุ์|จำนวน\nเกษตรกร\n(ราย)|เนื้อที่เพาะปลูก\nไร่-งาน-วา|ประมาณการผลผลิต\n(กก.)",
					"1,0.5,0.75,1,|"
							+ breedTypeHead[2]
							+ "|กลุ่มพันธุ์|จำนวน\nเกษตรกร\n(ราย)|เนื้อที่เพาะปลูก\nไร่-งาน-วา|ประมาณการผลผลิต\n(กก.)" };
			table
					.insertTable(headTable(newRows(colWidth), font_body,
							headTable));
			int linePage = 24;
			ReportHome rhome = new ReportHome();
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

			List provList = rhome.findR102_1ByCriteria(eform.getPlantYear(),
					eform.getPlantNo(), df.parse(eform.getEffectiveDate()),
					eform.getBranchCode(), eform.getProvinceNo(), breedTypeId,
					bCode);

			String branchTmp = "", subDistrictTmp = "", ProviceTmp = "";
			int[] sQuantity = { 0, 0, 0 }, sPlantRai = { 0, 0, 0 }, sPlantNgan = {
					0, 0, 0 }, sPlantWah = { 0, 0, 0 };
			float[] sFRecord = { 0, 0, 0 };
			int[] bQuantity = { 0, 0, 0 }, bPlantRai = { 0, 0, 0 }, bPlantNgan = {
					0, 0, 0 }, bPlantWah = { 0, 0, 0 };
			float[] bFRecord = { 0, 0, 0 };
			int[] pQuantity = { 0, 0, 0 }, pPlantRai = { 0, 0, 0 }, pPlantNgan = {
					0, 0, 0 }, pPlantWah = { 0, 0, 0 };
			float[] pFRecord = { 0, 0, 0 };
			Paragraph title = new Paragraph();
			List newList = new ArrayList();
			if (provList.size() > 0) {
				Object[] obj = ((Object[]) provList.get(0));
				ProviceTmp = obj[9].toString();

				List tmpList = new ArrayList();
				newList.add(obj);
				subDistrictTmp = obj[1].toString();
				String breedTypeTmp = obj[2].toString();
				for (int i = 1; i < provList.size(); i++) {
					Object[] getObj = ((Object[]) provList.get(i));
					if (!subDistrictTmp.equals(getObj[1].toString())) {
						for (int j = 0; j < tmpList.size(); j++) {
							Object[] getTmp = ((Object[]) tmpList.get(j));
							newList.add(getTmp);
						}
						tmpList.clear();
						subDistrictTmp = getObj[1].toString();
						newList.add(getObj);
						breedTypeTmp = getObj[2].toString();
					} else {
						if (breedTypeTmp.equals(getObj[2].toString())) {
							tmpList.add(getObj);
						} else {
							newList.add(getObj);
							breedTypeTmp = getObj[2].toString();
						}
					}
				}
				for (int j = 0; j < tmpList.size(); j++) {
					Object[] getTmp = ((Object[]) tmpList.get(j));
					newList.add(getTmp);
				}
				subDistrictTmp = "";
			}

			Table detail = new Table(colWidth.length);
			detail.setWidths(colWidth);
			detail.setAlignment(Cell.ALIGN_CENTER);
			detail.setWidth(100);
			detail.setPadding(2f);

			int breedTypeIndex = 0;
			int frist = 1;
			for (int prov = 0; prov < newList.size(); prov++) {
				Object[] obj = ((Object[]) newList.get(prov));
				Cell sCell = new Cell();

				if (!branchTmp.equals(obj[0].toString())) {
					if ((!branchTmp.equals(obj[0].toString()) && !branchTmp
							.equals(""))
							&& !subDistrictTmp.equals("")) {
						if (linePage < table.size()) {
							if (table.size() > 1) {
								document.add(title);
							}
							document.add(table);

							document.newPage();
							document.add(head);
							document.add(head_date);
							table = new Table(1);
							table.setAlignment(Cell.ALIGN_CENTER);
							table.setWidth(100);
							table.setPadding(2f);
							table.insertTable(headTable(newRows(colWidth),
									font_body, headTable));
							title.setLeading(5f);
							title.setSpacingAfter(0f);
							linePage = 22;
						}
						table.insertTable(detail);

						detail = new Table(colWidth.length);
						detail.setWidths(colWidth);
						detail.setAlignment(Cell.ALIGN_CENTER);
						detail.setWidth(100);
						detail.setPadding(2f);
						
						for (int i = 0; i < 3; i++) {
							if (sPlantWah[i] / 100 > 0) {
								sPlantNgan[i] += sPlantWah[i] / 100;
								sPlantWah[i] = sPlantWah[i] % 100;

							}
							if (sPlantNgan[i] / 4 > 0) {
								sPlantRai[i] += sPlantNgan[i] / 4;
								sPlantNgan[i] = sPlantNgan[i] % 4;
							}
						}

						String[] total = new String[] {
								String.format("%,d|", sQuantity[0]),
								String.format("%,d-%d-%d|", sPlantRai[0],
										sPlantNgan[0], sPlantWah[0]),
								String.format("%,.2f", sFRecord[0]),
								"",
								String.format("%,d|", sQuantity[1]),
								String.format("%,d-%d-%d|", sPlantRai[1],
										sPlantNgan[1], sPlantWah[1]),
								String.format("%,.2f", sFRecord[1]),
								"",
								String.format("%,d|", sQuantity[2]),
								String.format("%,d-%d-%d|", sPlantRai[2],
										sPlantNgan[2], sPlantWah[2]),
								String.format("%,.2f", sFRecord[2]) };
						if (linePage < table.size()) {
							if (table.size() > 1) {
								document.add(title);
							}
							document.add(table);

							document.newPage();
							document.add(head);
							document.add(head_date);
							table = new Table(1);
							table.setAlignment(Cell.ALIGN_CENTER);
							table.setWidth(100);
							table.setPadding(2f);
							table.insertTable(headTable(newRows(colWidth),
									font_body, headTable));
							title.setLeading(5f);
							title.setSpacingAfter(0f);
							linePage = 22;
						}
						table.insertTable(totalTable(colTotalWidth, font_total,
								"รวม", total));

						for (int i = 0; i < 3; i++) {
							bQuantity[i] += sQuantity[i];
							bPlantRai[i] += sPlantRai[i];
							bPlantNgan[i] += sPlantNgan[i];
							bPlantWah[i] += sPlantWah[i];
							bFRecord[i] += sFRecord[i];
						}

						sQuantity = new int[] { 0, 0, 0 };
						sPlantRai = new int[] { 0, 0, 0 };
						sPlantNgan = new int[] { 0, 0, 0 };
						sPlantWah = new int[] { 0, 0, 0 };
						sFRecord = new float[] { 0, 0, 0 };

						for (int i = 0; i < 3; i++) {
							if (bPlantWah[i] / 100 > 0) {
								bPlantNgan[i] += bPlantWah[i] / 100;
								bPlantWah[i] = bPlantWah[i] % 100;

							}
							if (bPlantNgan[i] / 4 > 0) {
								bPlantRai[i] += bPlantNgan[i] / 4;
								bPlantNgan[i] = bPlantNgan[i] % 4;
							}
						}

						total = new String[] {
								String.format("%,d|", bQuantity[0]),
								String.format("%,d-%d-%d|", bPlantRai[0],
										bPlantNgan[0], bPlantWah[0]),
								String.format("%,.2f", bFRecord[0]),
								"",
								String.format("%,d|", sQuantity[1]),
								String.format("%,d-%d-%d|", bPlantRai[1],
										bPlantNgan[1], bPlantWah[1]),
								String.format("%,.2f", bFRecord[1]),
								"",
								String.format("%,d|", bQuantity[2]),
								String.format("%,d-%d-%d|", bPlantRai[2],
										bPlantNgan[2], bPlantWah[2]),
								String.format("%,.2f", bFRecord[2]) };
						if (linePage < table.size()) {
							if (table.size() > 1) {
								document.add(title);
							}
							document.add(table);

							document.newPage();
							document.add(head);
							document.add(head_date);
							table = new Table(1);
							table.setAlignment(Cell.ALIGN_CENTER);
							table.setWidth(100);
							table.setPadding(2f);
							table.insertTable(headTable(newRows(colWidth),
									font_body, headTable));
							title.setLeading(5f);
							title.setSpacingAfter(0f);
							linePage = 22;
						}
						table.insertTable(totalTable(colTotalWidth, font_total,
								"รวมสาขา " + branchTmp, total));

						for (int i = 0; i < 3; i++) {
							pQuantity[i] += bQuantity[i];
							pPlantRai[i] += bPlantRai[i];
							pPlantNgan[i] += bPlantNgan[i];
							pPlantWah[i] += bPlantWah[i];
							pFRecord[i] += bFRecord[i];
						}

						bQuantity = new int[] { 0, 0, 0 };
						bPlantRai = new int[] { 0, 0, 0 };
						bPlantNgan = new int[] { 0, 0, 0 };
						bPlantWah = new int[] { 0, 0, 0 };
						bFRecord = new float[] { 0, 0, 0 };

						document.add(title);
						document.add(table);
						linePage -= table.size();
						table = new Table(1);
						table.setAlignment(Cell.ALIGN_CENTER);
						table.setWidth(100);
						table.setPadding(2f);

					}
					title = new Paragraph("ธ.ก.ส. จังหวัด" + obj[9].toString()
							+ " " + obj[0].toString(), font_title);
					title.setAlignment(Element.ALIGN_LEFT);
					title.setLeading(5f);
					title.setSpacingBefore(20f);
					linePage -= 2;
					branchTmp = obj[0].toString();

					detail = new Table(colWidth.length);
					detail.setWidths(colWidth);
					detail.setAlignment(Cell.ALIGN_CENTER);
					detail.setWidth(100);
					detail.setPadding(2f);
					subDistrictTmp = obj[1].toString();

					sCell = new Cell(new Phrase(obj[1].toString(), font_body));
					sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
					sCell.setVerticalAlignment(Cell.ALIGN_TOP);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthBottom(0);
					sCell.setBorderWidthRight(0.1f);
					detail.addCell(sCell);
				}

				if (!subDistrictTmp.equals(obj[1].toString())) {
					if (linePage < table.size()) {
						if (table.size() > 1) {
							document.add(title);
						}
						document.add(table);

						document.newPage();
						document.add(head);
						document.add(head_date);
						table = new Table(1);
						table.setAlignment(Cell.ALIGN_CENTER);
						table.setWidth(100);
						table.setPadding(2f);
						table.insertTable(headTable(newRows(colWidth),
								font_body, headTable));
						title.setLeading(5f);
						title.setSpacingAfter(0f);
						linePage = 22;
					}
					table.insertTable(detail);

					detail = new Table(colWidth.length);
					detail.setWidths(colWidth);
					detail.setAlignment(Cell.ALIGN_CENTER);
					detail.setWidth(100);
					detail.setPadding(2f);
					
					for (int i = 0; i < 3; i++) {
						if (sPlantWah[i] / 100 > 0) {
							sPlantNgan[i] += sPlantWah[i] / 100;
							sPlantWah[i] = sPlantWah[i] % 100;

						}
						if (sPlantNgan[i] / 4 > 0) {
							sPlantRai[i] += sPlantNgan[i] / 4;
							sPlantNgan[i] = sPlantNgan[i] % 4;
						}
					}

					String[] total = new String[] {
							String.format("%,d|", sQuantity[0]),
							String.format("%,d-%d-%d|", sPlantRai[0],
									sPlantNgan[0], sPlantWah[0]),
							String.format("%,.2f", sFRecord[0]),
							"",
							String.format("%,d|", sQuantity[1]),
							String.format("%,d-%d-%d|", sPlantRai[1],
									sPlantNgan[1], sPlantWah[1]),
							String.format("%,.2f", sFRecord[1]),
							"",
							String.format("%,d|", sQuantity[2]),
							String.format("%,d-%d-%d|", sPlantRai[2],
									sPlantNgan[2], sPlantWah[2]),
							String.format("%,.2f", sFRecord[2]) };
					if (linePage < table.size()) {
						if (table.size() > 1) {
							document.add(title);
						}
						document.add(table);

						document.newPage();
						document.add(head);
						document.add(head_date);
						table = new Table(1);
						table.setAlignment(Cell.ALIGN_CENTER);
						table.setWidth(100);
						table.setPadding(2f);
						table.insertTable(headTable(newRows(colWidth),
								font_body, headTable));
						title.setLeading(5f);
						title.setSpacingAfter(0f);
						linePage = 22;
					}
					table.insertTable(totalTable(colTotalWidth, font_total,
							"รวม", total));

					for (int i = 0; i < 3; i++) {
						bQuantity[i] += sQuantity[i];
						bPlantRai[i] += sPlantRai[i];
						bPlantNgan[i] += sPlantNgan[i];
						bPlantWah[i] += sPlantWah[i];
						bFRecord[i] += sFRecord[i];
					}

					sQuantity = new int[] { 0, 0, 0 };
					sPlantRai = new int[] { 0, 0, 0 };
					sPlantNgan = new int[] { 0, 0, 0 };
					sPlantWah = new int[] { 0, 0, 0 };
					sFRecord = new float[] { 0, 0, 0 };
					
					subDistrictTmp = obj[1].toString();

					sCell = new Cell(new Phrase(obj[1].toString(), font_body));
					sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
					sCell.setVerticalAlignment(Cell.ALIGN_TOP);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthBottom(0);
					sCell.setBorderWidthRight(0.1f);
					detail.addCell(sCell);
				}

				for (int i = breedTypeIndex; i < 3; i++) {
					if (breedTypeHead[i].equals(obj[2].toString())) {
						Table col = new Table(1);

						Table tmp = new Table(4);
						tmp.setWidths(innerCol);

						sCell = new Cell(new Phrase(obj[3].toString(),
								font_body));
						sCell.setHorizontalAlignment(Cell.ALIGN_LEFT);
						sCell.setVerticalAlignment(Cell.ALIGN_TOP);
						tmp.addCell(sCell);
						// จำนวนเกษตกร
						sCell = new Cell(new Phrase(String.format("%,d",
								Integer.parseInt(obj[18].toString())),
								font_body));
						sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
						sCell.setVerticalAlignment(Cell.ALIGN_TOP);
						tmp.addCell(sCell);
						sQuantity[i] += Integer.parseInt(obj[18].toString());
						// เนื้อที่
						int rai = Integer.parseInt(obj[19].toString());
						int ngan = Integer.parseInt(obj[20].toString());
						int wah = Integer.parseInt(obj[21].toString());

						if (wah / 100 > 0) {
							ngan += wah / 100;
							wah = wah % 100;

						}
						if (ngan / 4 > 0) {
							rai += ngan / 4;
							ngan = ngan % 4;
						}
						sCell = new Cell(new Phrase(String.format("%,d-%d-%d",
								rai, ngan, wah), font_body));
						sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
						sCell.setVerticalAlignment(Cell.ALIGN_TOP);
						tmp.addCell(sCell);

						sPlantRai[i] += rai;
						sPlantNgan[i] += ngan;
						sPlantWah[i] += wah;

						sCell = new Cell(new Phrase(String.format("%,.2f",
								Float.parseFloat(obj[22].toString())),
								font_body));
						sCell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
						sCell.setVerticalAlignment(Cell.ALIGN_TOP);
						tmp.addCell(sCell);

						sFRecord[i] += Float.parseFloat(obj[22].toString());

						col.insertTable(tmp);
						detail.insertTable(col);
						break;
					} else {
						breedTypeIndex++;
						Table col = new Table(1);
						Table tmp = new Table(4);
						tmp.setWidths(innerCol);
						sCell = new Cell(new Phrase("", font_body));
						sCell.setHorizontalAlignment(Cell.ALIGN_LEFT);
						sCell.setVerticalAlignment(Cell.ALIGN_TOP);
						tmp.addCell(sCell);
						// จำนวนเกษตกร
						sCell = new Cell(new Phrase("", font_body));
						sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
						sCell.setVerticalAlignment(Cell.ALIGN_TOP);
						tmp.addCell(sCell);
						// เนื้อที่
						sCell = new Cell(new Phrase("", font_body));
						sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
						sCell.setVerticalAlignment(Cell.ALIGN_TOP);
						tmp.addCell(sCell);

						sCell = new Cell(new Phrase("", font_body));
						sCell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
						sCell.setVerticalAlignment(Cell.ALIGN_TOP);
						tmp.addCell(sCell);

						col.insertTable(tmp);
						detail.insertTable(col);
					}
				}
				if (prov + 1 < newList.size()) {
					Object[] nextObj = ((Object[]) newList.get(prov + 1));
					if (!branchTmp.equals(nextObj[0].toString())
							|| !subDistrictTmp.equals(nextObj[1].toString())) {
						if (breedTypeIndex < 2) {
							for (int i = breedTypeIndex; i < 2; i++) {
								Table col = new Table(1);
								Table tmp = new Table(4);
								tmp.setWidths(innerCol);
								sCell = new Cell(new Phrase("", font_body));
								sCell.setHorizontalAlignment(Cell.ALIGN_LEFT);
								sCell.setVerticalAlignment(Cell.ALIGN_TOP);
								tmp.addCell(sCell);
								// จำนวนเกษตกร
								sCell = new Cell(new Phrase("", font_body));
								sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
								sCell.setVerticalAlignment(Cell.ALIGN_TOP);
								tmp.addCell(sCell);
								// เนื้อที่
								sCell = new Cell(new Phrase("", font_body));
								sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
								sCell.setVerticalAlignment(Cell.ALIGN_TOP);
								tmp.addCell(sCell);

								sCell = new Cell(new Phrase("", font_body));
								sCell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
								sCell.setVerticalAlignment(Cell.ALIGN_TOP);
								tmp.addCell(sCell);

								col.insertTable(tmp);
								detail.insertTable(col);
							}
						}
						breedTypeIndex = 0;
						continue;
					} else {
						if (breedTypeHead[breedTypeIndex].equals(nextObj[2]
								.toString())) {
							for (int i = breedTypeIndex; i < 2; i++) {
								Table col = new Table(1);
								Table tmp = new Table(4);
								tmp.setWidths(innerCol);
								sCell = new Cell(new Phrase("", font_body));
								sCell.setHorizontalAlignment(Cell.ALIGN_LEFT);
								sCell.setVerticalAlignment(Cell.ALIGN_TOP);
								tmp.addCell(sCell);
								// จำนวนเกษตกร
								sCell = new Cell(new Phrase("", font_body));
								sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
								sCell.setVerticalAlignment(Cell.ALIGN_TOP);
								tmp.addCell(sCell);
								// เนื้อที่
								sCell = new Cell(new Phrase("", font_body));
								sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
								sCell.setVerticalAlignment(Cell.ALIGN_TOP);
								tmp.addCell(sCell);

								sCell = new Cell(new Phrase("", font_body));
								sCell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
								sCell.setVerticalAlignment(Cell.ALIGN_TOP);
								tmp.addCell(sCell);

								col.insertTable(tmp);
								detail.insertTable(col);
							}
							if (linePage < table.size()) {
								if (table.size() > 1) {
									document.add(title);
								}
								document.add(table);

								document.newPage();
								document.add(head);
								document.add(head_date);
								table = new Table(1);
								table.setAlignment(Cell.ALIGN_CENTER);
								table.setWidth(100);
								table.setPadding(2f);
								table.insertTable(headTable(newRows(colWidth),
										font_body, headTable));
								title.setLeading(5f);
								title.setSpacingAfter(0f);
								linePage = 22;
							}
							table.insertTable(detail);

							detail = new Table(colWidth.length);
							detail.setWidths(colWidth);
							detail.setAlignment(Cell.ALIGN_CENTER);
							detail.setWidth(100);
							detail.setPadding(2f);

							if (linePage < table.size()) {
								if (table.size() > 1) {
									document.add(title);
								}
								document.add(table);

								document.newPage();
								document.add(head);
								document.add(head_date);
								table = new Table(1);
								table.setAlignment(Cell.ALIGN_CENTER);
								table.setWidth(100);
								table.setPadding(2f);
								table.insertTable(headTable(newRows(colWidth),
										font_body, headTable));
								title.setLeading(5f);
								title.setSpacingAfter(0f);
								linePage = 22;

								sCell = new Cell(new Phrase(subDistrictTmp,
										font_body));
								sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
								sCell.setVerticalAlignment(Cell.ALIGN_TOP);
								sCell.setBorderWidthTop(0);
								sCell.setBorderWidthBottom(0);
								sCell.setBorderWidthRight(0.1f);
								detail.addCell(sCell);
							} else {
								sCell = new Cell(new Phrase("", font_body));
								sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
								sCell.setVerticalAlignment(Cell.ALIGN_TOP);
								sCell.setBorderWidthTop(0);
								sCell.setBorderWidthBottom(0);
								sCell.setBorderWidthRight(0.1f);
								detail.addCell(sCell);
							}

							breedTypeIndex = 0;
							continue;
						}
						breedTypeIndex++;
						if (breedTypeHead[breedTypeIndex].equals(nextObj[2]
								.toString())) {
							continue;
						} else if (breedTypeIndex < 2) {
							breedTypeIndex++;
							Table col = new Table(1);
							Table tmp = new Table(4);
							tmp.setWidths(innerCol);
							sCell = new Cell(new Phrase("", font_body));
							sCell.setHorizontalAlignment(Cell.ALIGN_LEFT);
							sCell.setVerticalAlignment(Cell.ALIGN_TOP);
							tmp.addCell(sCell);
							// จำนวนเกษตกร
							sCell = new Cell(new Phrase("", font_body));
							sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
							sCell.setVerticalAlignment(Cell.ALIGN_TOP);
							tmp.addCell(sCell);
							// เนื้อที่
							sCell = new Cell(new Phrase("", font_body));
							sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
							sCell.setVerticalAlignment(Cell.ALIGN_TOP);
							tmp.addCell(sCell);

							sCell = new Cell(new Phrase("", font_body));
							sCell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
							sCell.setVerticalAlignment(Cell.ALIGN_TOP);
							tmp.addCell(sCell);

							col.insertTable(tmp);
							detail.insertTable(col);
						} else {
							if (linePage < table.size()) {
								if (table.size() > 1) {
									document.add(title);
								}
								document.add(table);

								document.newPage();
								document.add(head);
								document.add(head_date);
								table = new Table(1);
								table.setAlignment(Cell.ALIGN_CENTER);
								table.setWidth(100);
								table.setPadding(2f);
								table.insertTable(headTable(newRows(colWidth),
										font_body, headTable));
								title.setLeading(5f);
								title.setSpacingAfter(0f);
								linePage = 22;
							}
							table.insertTable(detail);

							detail = new Table(colWidth.length);
							detail.setWidths(colWidth);
							detail.setAlignment(Cell.ALIGN_CENTER);
							detail.setWidth(100);
							detail.setPadding(2f);

							if (linePage < table.size()) {
								if (table.size() > 1) {
									document.add(title);
								}
								document.add(table);

								document.newPage();
								document.add(head);
								document.add(head_date);
								table = new Table(1);
								table.setAlignment(Cell.ALIGN_CENTER);
								table.setWidth(100);
								table.setPadding(2f);
								table.insertTable(headTable(newRows(colWidth),
										font_body, headTable));
								title.setLeading(5f);
								title.setSpacingAfter(0f);
								linePage = 22;

								sCell = new Cell(new Phrase(subDistrictTmp,
										font_body));
								sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
								sCell.setVerticalAlignment(Cell.ALIGN_TOP);
								sCell.setBorderWidthTop(0);
								sCell.setBorderWidthBottom(0);
								sCell.setBorderWidthRight(0.1f);
								detail.addCell(sCell);
							} else {
								sCell = new Cell(new Phrase("", font_body));
								sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
								sCell.setVerticalAlignment(Cell.ALIGN_TOP);
								sCell.setBorderWidthTop(0);
								sCell.setBorderWidthBottom(0);
								sCell.setBorderWidthRight(0.1f);
								detail.addCell(sCell);
							}

							breedTypeIndex = 0;
						}
					}
				} else {
					if (breedTypeIndex < 2) {
						for (int i = breedTypeIndex; i < 2; i++) {
							Table col = new Table(1);
							Table tmp = new Table(4);
							tmp.setWidths(innerCol);
							sCell = new Cell(new Phrase("", font_body));
							sCell.setHorizontalAlignment(Cell.ALIGN_LEFT);
							sCell.setVerticalAlignment(Cell.ALIGN_TOP);
							tmp.addCell(sCell);
							// จำนวนเกษตกร
							sCell = new Cell(new Phrase("", font_body));
							sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
							sCell.setVerticalAlignment(Cell.ALIGN_TOP);
							tmp.addCell(sCell);
							// เนื้อที่
							sCell = new Cell(new Phrase("", font_body));
							sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
							sCell.setVerticalAlignment(Cell.ALIGN_TOP);
							tmp.addCell(sCell);

							sCell = new Cell(new Phrase("", font_body));
							sCell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
							sCell.setVerticalAlignment(Cell.ALIGN_TOP);
							tmp.addCell(sCell);

							col.insertTable(tmp);
							detail.insertTable(col);
						}
					}
				}
			}
			if (provList.size() <= 0) {
				document.add(table);
				title = new Paragraph("ไม่มีข้อมูล", font_title);
				title.setAlignment(Element.ALIGN_CENTER);
				document.add(title);
			} else {
				if (linePage < table.size()) {
					if (table.size() > 1) {
						document.add(title);
					}
					document.add(table);

					document.newPage();
					document.add(head);
					document.add(head_date);
					table = new Table(1);
					table.setAlignment(Cell.ALIGN_CENTER);
					table.setWidth(100);
					table.setPadding(2f);
					table.insertTable(headTable(newRows(colWidth), font_body,
							headTable));
					title.setLeading(5f);
					title.setSpacingAfter(0f);
					linePage = 22;
				}
				table.insertTable(detail);
				
				for (int i = 0; i < 3; i++) {
					if (sPlantWah[i] / 100 > 0) {
						sPlantNgan[i] += sPlantWah[i] / 100;
						sPlantWah[i] = sPlantWah[i] % 100;

					}
					if (sPlantNgan[i] / 4 > 0) {
						sPlantRai[i] += sPlantNgan[i] / 4;
						sPlantNgan[i] = sPlantNgan[i] % 4;
					}
				}

				String[] total = new String[] {
						String.format("%,d|", sQuantity[0]),
						String.format("%,d-%d-%d|", sPlantRai[0],
								sPlantNgan[0], sPlantWah[0]),
						String.format("%,.2f", sFRecord[0]),
						"",
						String.format("%,d|", sQuantity[1]),
						String.format("%,d-%d-%d|", sPlantRai[1],
								sPlantNgan[1], sPlantWah[1]),
						String.format("%,.2f", sFRecord[1]),
						"",
						String.format("%,d|", sQuantity[2]),
						String.format("%,d-%d-%d|", sPlantRai[2],
								sPlantNgan[2], sPlantWah[2]),
						String.format("%,.2f", sFRecord[2]) };
				if (linePage < table.size()) {
					if (table.size() > 1) {
						document.add(title);
					}
					document.add(table);

					document.newPage();
					document.add(head);
					document.add(head_date);
					table = new Table(1);
					table.setAlignment(Cell.ALIGN_CENTER);
					table.setWidth(100);
					table.setPadding(2f);
					table.insertTable(headTable(newRows(colWidth),
							font_body, headTable));
					title.setLeading(5f);
					title.setSpacingAfter(0f);
					linePage = 22;
				}
				table.insertTable(totalTable(colTotalWidth, font_total,
						"รวม", total));

				for (int i = 0; i < 3; i++) {
					bQuantity[i] += sQuantity[i];
					bPlantRai[i] += sPlantRai[i];
					bPlantNgan[i] += sPlantNgan[i];
					bPlantWah[i] += sPlantWah[i];
					bFRecord[i] += sFRecord[i];
				}

				for (int i = 0; i < 3; i++) {
					if (bPlantWah[i] / 100 > 0) {
						bPlantNgan[i] += bPlantWah[i] / 100;
						bPlantWah[i] = bPlantWah[i] % 100;

					}
					if (bPlantNgan[i] / 4 > 0) {
						bPlantRai[i] += bPlantNgan[i] / 4;
						bPlantNgan[i] = bPlantNgan[i] % 4;
					}
				}
				
				total = new String[] {
						String.format("%,d|", bQuantity[0]),
						String.format("%,d-%d-%d|", bPlantRai[0],
								bPlantNgan[0], bPlantWah[0]),
						String.format("%,.2f", bFRecord[0]),
						"",
						String.format("%,d|", bQuantity[1]),
						String.format("%,d-%d-%d|", bPlantRai[1],
								bPlantNgan[1], bPlantWah[1]),
						String.format("%,.2f", bFRecord[1]),
						"",
						String.format("%,d|", bQuantity[2]),
						String.format("%,d-%d-%d|", bPlantRai[2],
								bPlantNgan[2], bPlantWah[2]),
						String.format("%,.2f", bFRecord[2]) };
				if (linePage < table.size()) {
					if (table.size() > 1) {
						document.add(title);
					}
					document.add(table);

					document.newPage();
					document.add(head);
					document.add(head_date);
					table = new Table(1);
					table.setAlignment(Cell.ALIGN_CENTER);
					table.setWidth(100);
					table.setPadding(2f);
					table.insertTable(headTable(newRows(colWidth), font_body,
							headTable));
					title.setLeading(5f);
					title.setSpacingAfter(0f);
					linePage = 22;
				}
				table.insertTable(totalTable(colTotalWidth, font_total,
						"รวมสาขา " + branchTmp, total));

				for (int i = 0; i < 3; i++) {
					pQuantity[i] += bQuantity[i];
					pPlantRai[i] += bPlantRai[i];
					pPlantNgan[i] += bPlantNgan[i];
					pPlantWah[i] += bPlantWah[i];
					pFRecord[i] += bFRecord[i];
				}
				for (int i = 0; i < 3; i++) {
					if (pPlantWah[i] / 100 > 0) {
						pPlantNgan[i] += pPlantWah[i] / 100;
						pPlantWah[i] = pPlantWah[i] % 100;

					}
					if (pPlantNgan[i] / 4 > 0) {
						pPlantRai[i] += pPlantNgan[i] / 4;
						pPlantNgan[i] = pPlantNgan[i] % 4;
					}
				}
				total = new String[] {
						String.format("%,d|", pQuantity[0]),
						String.format("%,d-%d-%d|", pPlantRai[0],
								pPlantNgan[0], pPlantWah[0]),
						String.format("%,.2f", pFRecord[0]),
						"",
						String.format("%,d|", pQuantity[1]),
						String.format("%,d-%d-%d|", pPlantRai[1],
								pPlantNgan[1], pPlantWah[1]),
						String.format("%,.2f", pFRecord[1]),
						"",
						String.format("%,d|", pQuantity[2]),
						String.format("%,d-%d-%d|", pPlantRai[2],
								pPlantNgan[2], pPlantWah[2]),
						String.format("%,.2f", pFRecord[2]) };
				if (linePage < table.size()) {
					if (table.size() > 1) {
						document.add(title);
					}
					document.add(table);

					document.newPage();
					document.add(head);
					document.add(head_date);
					table = new Table(1);
					table.setAlignment(Cell.ALIGN_CENTER);
					table.setWidth(100);
					table.setPadding(2f);
					table.insertTable(headTable(newRows(colWidth), font_body,
							headTable));
					title.setLeading(5f);
					title.setSpacingAfter(0f);
					linePage = 22;
				}
				table.insertTable(totalTable(colTotalWidth, font_total,
						"ยอดรวมจังหวัด" + ProviceTmp, total));

				document.add(title);
				document.add(table);
			}

		} catch (Exception e) {
			document.add(new Paragraph("เกิดการดึงข้อมูลผิดพลาด", font_title));
			e.printStackTrace();
			log.error(e, e);
		} finally {
			sf.getCurrentSession().close();
		}
		return document;
	}

	private Document ReportR103(SessionFactory sf, Document document,
			Font font_title, Font font_body, Font font_total, ReportForm eform)
			throws DocumentException {
		try {
			sf.getCurrentSession().beginTransaction();
			Paragraph head = new Paragraph(
					"รายละเอียดข้อมูลเกษตรกรที่แจ้งผลผลิตที่คาดว่าจะได้ตอนเริ่มเพาะปลูก ตามช่วงเวลาที่คาดว่าจะเก็บเกี่ยว",
					font_title);
			head.setAlignment(Element.ALIGN_CENTER);
			document.add(head);

			Paragraph head_date = new Paragraph("ตั้งแต่เดือน "
					+ eform.getForecastMonthStart() + " "
					+ eform.getForecastYearStart() + " ถึงเดือน "
					+ eform.getForecastMonthEnd() + " "
					+ eform.getForecastYearEnd(), font_title);
			head_date.setAlignment(Element.ALIGN_CENTER);
			head_date.setSpacingBefore(5f);
			document.add(head_date);

			Table table = new Table(1);
			table.setAlignment(Cell.ALIGN_CENTER);
			table.setWidth(100);
			table.setPadding(2f);

			float[] colWidth, colTotalWidth;
			colWidth = new float[] { 15, 15, 20, 15, 20, 30, 15, 15, 25 };
			colTotalWidth = new float[] { 130, 15, 25 };
			String[] headTable = new String[] { "เดือนที่เก็บเกี่ยว\n(ว/ด/ป)",
					"กลุ่มพันธุ์", "ชื่อกลุ่ม", "ตำบลที่ตั้งแปลง",
					"บัตรประจำตัว\nประชาชน\n(เลขที่)", "ชื่อ-สกุล",
					"วันที่เพาะปลูก\n(ว/ด/ป)", "เนื้อที่เพาะปลูก\nไร่-งาน-วา",
					"ประมาณการผลผลิตรวม \n(กก.)" };
			table
					.insertTable(headTable(newRows(colWidth), font_body,
							headTable));
			int linePage = 23;
			ReportHome rhome = new ReportHome();
			Date strDate = dateMonths(eform.getForecastMonthStart(), eform
					.getForecastYearStart(), true);
			Date endDate = dateMonths(eform.getForecastMonthEnd(), eform
					.getForecastYearEnd(), false);

			List provList = rhome.findR103ByCriteria(eform.getBreedTypeId(),
					eform.getBreedGroupId(), eform.getProvinceNo(), eform
							.getBranchCode(), strDate, endDate);

			if (eform.getBranchCode() > 0) {
				BranchHome bhome = new BranchHome();
				List<Branch> pbList = bhome
						.findByPBranch(eform.getBranchCode());
				List<Branch> pbranchCode = new ArrayList();
				if (pbList.size() > 0) {
					pbranchCode
							.addAll(getPBranch(pbList, eform.getBranchCode()));
					for (int i = 0; i < pbranchCode.size(); i++) {
						provList.addAll(rhome.findR103ByCriteria(eform
								.getBreedTypeId(), eform.getBreedGroupId(), 0,
								pbranchCode.get(i).getBranchCode(), strDate,
								endDate));
					}
				}
			}

			String branchTmp = "", subDistrictTmp = "", provinceTmp = "", idCardTmp = "";
			int dtPlantRai = 0, dtPlantNgan = 0, dtPlantWah = 0;
			float dtFRecord = 0;
			int btPlantRai = 0, btPlantNgan = 0, btPlantWah = 0;
			float btFRecord = 0;
			int ptPlantRai = 0, ptPlantNgan = 0, ptPlantWah = 0;
			float ptFRecord = 0;
			Paragraph title = new Paragraph();
			if (provList.size() <= 0) {
				document.add(table);
				title = new Paragraph("ไม่มีข้อมูล", font_title);
				title.setAlignment(Element.ALIGN_CENTER);
				document.add(title);
			} else {
				int target = 0;
				for (int i = 0; i < provList.size(); i++) {
					Object[] obj = ((Object[]) provList.get(i));
					target += Float.parseFloat(obj[13].toString());
				}
				title = new Paragraph(String.format(
						"เป้าหมายปริมาณผลผลิต %d ตัน", target / 1000),
						font_total);
				title.setAlignment(Element.ALIGN_RIGHT);
				title.setLeading(25f);
				title.setSpacingAfter(-25f);
				document.add(title);
			}
			boolean newPage = true;
			int frist = 1;
			for (int prov = 0; prov < provList.size(); prov++) {
				Object[] obj = ((Object[]) provList.get(prov));

				if (!branchTmp.equals(obj[0].toString())) {
					title = new Paragraph("ธ.ก.ส. จังหวัด" + obj[14].toString()
							+ " " + obj[0].toString(), font_total);
					title.setAlignment(Element.ALIGN_LEFT);
					title.setLeading(5f);
					title.setSpacingBefore(20f);
					branchTmp = obj[0].toString();
					linePage -= 2;
					newPage = true;
				}

				Table detail = new Table(colWidth.length);
				detail.setWidths(colWidth);
				detail.setAlignment(Cell.ALIGN_CENTER);
				detail.setWidth(100);
				detail.setPadding(2f);

				if (linePage < table.size()) {
					if (table.size() > 1) {
						document.add(title);
					}
					document.add(table);

					document.newPage();
					document.add(head);
					document.add(head_date);
					table = new Table(1);
					table.setAlignment(Cell.ALIGN_CENTER);
					table.setWidth(100);
					table.setPadding(2f);
					table.insertTable(headTable(newRows(colWidth), font_body,
							headTable));
					title.setLeading(5f);
					title.setSpacingAfter(0f);
					linePage = 21;
				}

				Cell sCell = new Cell(new Phrase(
						convertDate(obj[1].toString()), font_body));
				sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
				sCell.setVerticalAlignment(Cell.ALIGN_TOP);
				sCell.setBorderColorTop(Color.GRAY);
				sCell.setBorderWidthTop(0.1f);
				sCell.setBorderWidthRight(0.1f);
				detail.addCell(sCell);

				sCell = new Cell(new Phrase(obj[2].toString(), font_body));
				sCell.setHorizontalAlignment(Cell.ALIGN_LEFT);
				sCell.setVerticalAlignment(Cell.ALIGN_TOP);
				sCell.setBorderColorTop(Color.GRAY);
				sCell.setBorderWidthTop(0.1f);
				sCell.setBorderWidthRight(0.1f);
				detail.addCell(sCell);

				sCell = new Cell(new Phrase(obj[3] != null ? obj[3].toString()
						: " ", font_body));
				sCell.setHorizontalAlignment(Cell.ALIGN_LEFT);
				sCell.setVerticalAlignment(Cell.ALIGN_TOP);
				sCell.setBorderColorTop(Color.GRAY);
				sCell.setBorderWidthTop(0.1f);
				sCell.setBorderWidthRight(0.1f);
				detail.addCell(sCell);

				if (subDistrictTmp.equals(obj[4].toString()) && !newPage) {
					sCell = new Cell(new Phrase("", font_body));
					sCell.setBorderWidthTop(0);
					sCell.setBorderWidthBottom(0);
					sCell.setBorderWidthRight(0.1f);
					detail.addCell(sCell);

				} else {
					sCell = new Cell(new Phrase(obj[4].toString(), font_body));
					sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
					sCell.setVerticalAlignment(Cell.ALIGN_TOP);
					sCell.setBorderColorTop(Color.GRAY);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
					sCell.setBorderWidthBottom(0);
					sCell.setBorderWidthRight(0.1f);
					detail.addCell(sCell);
					newPage = true;
				}

				if (idCardTmp.equals(obj[5].toString()) && !newPage) {
					sCell = new Cell(new Phrase("", font_body));
					sCell.setBorderWidthTop(0);
					sCell.setBorderWidthBottom(0);
					sCell.setBorderWidthRight(0.1f);
					detail.addCell(sCell);
					detail.addCell(sCell);
				} else {
					sCell = new Cell(new Phrase(obj[5].toString(), font_body));
					sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
					sCell.setVerticalAlignment(Cell.ALIGN_TOP);
					sCell.setBorderColorTop(Color.GRAY);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
					sCell.setBorderWidthBottom(0);
					sCell.setBorderWidthRight(0.1f);
					detail.addCell(sCell);

					sCell = new Cell(new Phrase(" " + obj[6].toString()
							+ obj[7].toString() + " " + obj[8].toString(),
							font_body));
					sCell.setHorizontalAlignment(Cell.ALIGN_LEFT);
					sCell.setVerticalAlignment(Cell.ALIGN_TOP);
					sCell.setBorderColorTop(Color.GRAY);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
					sCell.setBorderWidthBottom(0);
					sCell.setBorderWidthRight(0.1f);
					detail.addCell(sCell);

					idCardTmp = obj[5].toString();
					newPage = false;
				}

				sCell = new Cell(new Phrase(convertDate(obj[9].toString()),
						font_body));
				sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
				sCell.setVerticalAlignment(Cell.ALIGN_TOP);
				sCell.setBorderColorTop(Color.GRAY);
				sCell.setBorderWidthTop(0.1f);
				sCell.setBorderWidthRight(0.1f);
				detail.addCell(sCell);

				// Table tmp = new Table(3);
				int rai = Integer.parseInt(obj[10].toString());
				int ngan = Integer.parseInt(obj[11].toString());
				int wah = Integer.parseInt(obj[12].toString());

				if (wah / 100 > 0) {
					ngan += wah / 100;
					wah = wah % 100;

				}
				if (ngan / 4 > 0) {
					rai += ngan / 4;
					ngan = ngan % 4;
				}
				sCell = new Cell(new Phrase(String.format("%,d-%d-%d", rai,
						ngan, wah), font_body));
				sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
				sCell.setVerticalAlignment(Cell.ALIGN_TOP);
				sCell.setBorderColorTop(Color.GRAY);
				sCell.setBorderWidthTop(0.1f);
				sCell.setBorderWidthRight(0.1f);
				detail.addCell(sCell);

				sCell = new Cell(new Phrase(String.format("%,.2f", Float
						.parseFloat(obj[13].toString())), font_body));
				sCell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
				sCell.setVerticalAlignment(Cell.ALIGN_TOP);
				sCell.setBorderColorTop(Color.GRAY);
				sCell.setBorderWidthTop(0.1f);
				sCell.setBorderWidthRight(0.1f);
				detail.addCell(sCell);

				table.insertTable(detail);

				dtPlantRai += rai;
				dtPlantNgan += ngan;
				dtPlantWah += wah;
				dtFRecord += Float.parseFloat(obj[13].toString());
				subDistrictTmp = obj[4].toString();
				provinceTmp = obj[14].toString();

				if (prov + 1 != provList.size()) {
					if (!subDistrictTmp.equals(((Object[]) provList
							.get(prov + 1))[4].toString())
							|| !branchTmp.equals(((Object[]) provList
									.get(prov + 1))[0].toString())) {
						calRai = totalRai(dtPlantRai, dtPlantNgan, dtPlantWah);
						String[] total = new String[] {
								String.format("%,d-%d-%d|", calRai[0],
										calRai[1], calRai[2]),
								String.format("%,.2f", dtFRecord) };
						if (linePage < table.size()) {
							if (table.size() > 1) {
								document.add(title);
							}
							document.add(table);

							document.newPage();
							document.add(head);
							document.add(head_date);
							table = new Table(1);
							table.setAlignment(Cell.ALIGN_CENTER);
							table.setWidth(100);
							table.setPadding(2f);
							table.insertTable(headTable(newRows(colWidth),
									font_body, headTable));
							title.setLeading(5f);
							title.setSpacingAfter(0f);
							linePage = 21;
						}
						table.insertTable(totalTable(colTotalWidth, font_total,
								"รวม", total));
						btPlantRai += dtPlantRai;
						btPlantNgan += dtPlantNgan;
						btPlantWah += dtPlantWah;
						btFRecord += dtFRecord;
						dtPlantRai = 0;
						dtPlantNgan = 0;
						dtPlantWah = 0;
						dtPlantNgan = 0;
						dtPlantWah = 0;
						dtFRecord = 0;

					}

					if (!branchTmp
							.equals(((Object[]) provList.get(prov + 1))[0]
									.toString())) {
						calRai = totalRai(btPlantRai, btPlantNgan, btPlantWah);
						String[] totalBran = new String[] {
								String.format("%,d-%d-%d|", calRai[0],
										calRai[1], calRai[2]),
								String.format("%,.2f", btFRecord) };
						if (linePage < table.size()) {
							if (table.size() > 1) {
								document.add(title);
							}
							document.add(table);

							document.newPage();
							document.add(head);
							document.add(head_date);
							table = new Table(1);
							table.setAlignment(Cell.ALIGN_CENTER);
							table.setWidth(100);
							table.setPadding(2f);
							table.insertTable(headTable(newRows(colWidth),
									font_body, headTable));
							title.setLeading(5f);
							title.setSpacingAfter(0f);
							linePage = 21;
						}
						table.insertTable(totalTable(colTotalWidth, font_total,
								"ยอดรวมสาขา " + branchTmp, totalBran));
						ptPlantRai += btPlantRai;
						ptPlantNgan += btPlantNgan;
						ptPlantWah += btPlantWah;
						ptFRecord += btFRecord;
						btPlantRai = 0;
						btPlantNgan = 0;
						btPlantWah = 0;
						btFRecord = 0;

						if (!provinceTmp.equals(((Object[]) provList
								.get(prov + 1))[14].toString())) {
							calRai = totalRai(ptPlantRai, ptPlantNgan,
									ptPlantWah);
							String[] totalProv = new String[] {
									String.format("%,d-%d-%d|", calRai[0],
											calRai[1], calRai[2]),
									String.format("%,.2f", ptFRecord) };
							if (linePage < table.size()) {
								if (table.size() > 1) {
									document.add(title);
								}
								document.add(table);

								document.newPage();
								document.add(head);
								document.add(head_date);
								table = new Table(1);
								table.setAlignment(Cell.ALIGN_CENTER);
								table.setWidth(100);
								table.setPadding(2f);
								table.insertTable(headTable(newRows(colWidth),
										font_body, headTable));
								title.setLeading(5f);
								title.setSpacingAfter(0f);
								linePage = 21;
							}
							table.insertTable(totalTable(colTotalWidth,
									font_total, "ยอดรวมจังหวัด "
											+ obj[14].toString(), totalProv));

							ptPlantRai = 0;
							ptPlantNgan = 0;
							ptPlantWah = 0;
							ptFRecord = 0;

						}

						if (table.size() > 1) {
							document.add(title);
							document.add(table);
							linePage -= table.size();
							table = new Table(1);
							table.setAlignment(Cell.ALIGN_CENTER);
							table.setWidth(100);
							table.setPadding(2f);
						}
					}
				} else {
					calRai = totalRai(dtPlantRai, dtPlantNgan, dtPlantWah);
					String[] total = new String[] {
							String.format("%,d-%d-%d|", calRai[0], calRai[1],
									calRai[2]),
							String.format("%,.2f", dtFRecord) };
					if (linePage < table.size()) {
						if (table.size() > 1) {
							document.add(title);
						}
						document.add(table);

						document.newPage();
						document.add(head);
						document.add(head_date);
						table = new Table(1);
						table.setAlignment(Cell.ALIGN_CENTER);
						table.setWidth(100);
						table.setPadding(2f);
						table.insertTable(headTable(newRows(colWidth),
								font_body, headTable));
						title.setLeading(5f);
						title.setSpacingAfter(0f);
						linePage = 21;
					}
					table.insertTable(totalTable(colTotalWidth, font_total,
							"รวม", total));
					btPlantRai += dtPlantRai;
					btPlantNgan += dtPlantNgan;
					btPlantWah += dtPlantWah;
					btFRecord += dtFRecord;

					calRai = totalRai(btPlantRai, btPlantNgan, btPlantWah);
					String[] totalBran = new String[] {
							String.format("%,d-%d-%d|", calRai[0], calRai[1],
									calRai[2]),
							String.format("%,.2f", btFRecord) };
					if (linePage < table.size()) {
						if (table.size() > 1) {
							document.add(title);
						}
						document.add(table);

						document.newPage();
						document.add(head);
						document.add(head_date);
						table = new Table(1);
						table.setAlignment(Cell.ALIGN_CENTER);
						table.setWidth(100);
						table.setPadding(2f);
						table.insertTable(headTable(newRows(colWidth),
								font_body, headTable));
						title.setLeading(5f);
						title.setSpacingAfter(0f);
						linePage = 21;
					}
					table.insertTable(totalTable(colTotalWidth, font_total,
							"ยอดรวมสาขา " + branchTmp, totalBran));
					ptPlantRai += btPlantRai;
					ptPlantNgan += btPlantNgan;
					ptPlantWah += btPlantWah;
					ptFRecord += btFRecord;

					calRai = totalRai(ptPlantRai, ptPlantNgan, ptPlantWah);
					String[] totalProv = new String[] {
							String.format("%,d-%d-%d|", calRai[0], calRai[1],
									calRai[2]),
							String.format("%,.2f", ptFRecord) };
					if (linePage < table.size()) {
						if (table.size() > 1) {
							document.add(title);
						}
						document.add(table);

						document.newPage();
						document.add(head);
						document.add(head_date);
						table = new Table(1);
						table.setAlignment(Cell.ALIGN_CENTER);
						table.setWidth(100);
						table.setPadding(2f);
						table.insertTable(headTable(newRows(colWidth),
								font_body, headTable));
						title.setLeading(5f);
						title.setSpacingAfter(0f);
						linePage = 21;
					}
					table.insertTable(totalTable(colTotalWidth, font_total,
							"ยอดรวมจังหวัด " + obj[14].toString(), totalProv));

					document.add(title);
					document.add(table);
				}
			}

		} catch (Exception e) {
			document.add(new Paragraph("เกิดการดึงข้อมูลผิดพลาด", font_title));
			e.printStackTrace();
			log.error(e, e);
		} finally {
			sf.getCurrentSession().close();
		}
		return document;
	}

	private Document ReportR103_1(SessionFactory sf, Document document,
			Font font_title, Font font_body, Font font_total, ReportForm eform)
			throws DocumentException {
		try {
			sf.getCurrentSession().beginTransaction();
			Paragraph head = new Paragraph(
					"รายละเอียดข้อมูลเกษตรกรที่แจ้งผลผลิตที่คาดว่าจะออกสู่ตลาด(แจ้งล่วงหน้า 1 เดือนก่อนเก็บเกี่ยว)",
					font_title);
			head.setAlignment(Element.ALIGN_CENTER);
			document.add(head);

			Paragraph head_date = new Paragraph("ตั้งแต่เดือน "
					+ eform.getForecastMonthStart() + " "
					+ eform.getForecastYearStart() + " ถึงเดือน "
					+ eform.getForecastMonthEnd() + " "
					+ eform.getForecastYearEnd(), font_title);
			head_date.setAlignment(Element.ALIGN_CENTER);
			head_date.setSpacingBefore(5f);
			document.add(head_date);

			Table table = new Table(1);
			table.setAlignment(Cell.ALIGN_CENTER);
			table.setWidth(100);
			table.setPadding(2f);
			float[] colWidth, colTotalWidth;
			colWidth = new float[] { 15, 20, 15, 30, 20, 30, 15, 15, 25 };
			colTotalWidth = new float[] { 145, 15, 25 };
			String[] headTable = new String[] {
					"เดือนที่ผลผลิต\nออกสู่ตลาด \n(ว/ด/ป)", "กลุ่มพันธุ์",
					"ชื่อกลุ่ม", "ตำบลที่ตั้งแปลง",
					"บัตรประจำตัว\nประชาชน\n(เลขที่)", "ชื่อ-สกุล",
					"วันที่เพาะปลูก\n(ว/ด/ป)", "เนื้อที่เพาะปลูก\nไร่-งาน-วา",
					"ผลผลิตที่คาดว่า\nจะออกสู่ตลาด\n(กก.)" };
			table
					.insertTable(headTable(newRows(colWidth), font_body,
							headTable));

			ReportHome rhome = new ReportHome();
			Date strDate = dateMonths(eform.getForecastMonthStart(), eform
					.getForecastYearStart(), true);
			Date endDate = dateMonths(eform.getForecastMonthEnd(), eform
					.getForecastYearEnd(), false);
			List provList = rhome.findR103_1ByCriteria(eform.getBreedTypeId(),
					eform.getBreedGroupId(), eform.getProvinceNo(), eform
							.getBranchCode(), strDate, endDate);
			if (eform.getBranchCode() > 0) {
				BranchHome bhome = new BranchHome();
				List<Branch> pbList = bhome
						.findByPBranch(eform.getBranchCode());
				List<Branch> pbranchCode = new ArrayList();
				if (pbList.size() > 0) {
					pbranchCode
							.addAll(getPBranch(pbList, eform.getBranchCode()));
					for (int i = 0; i < pbranchCode.size(); i++) {
						provList.addAll(rhome.findR103_1ByCriteria(eform
								.getBreedTypeId(), eform.getBreedGroupId(), 0,
								pbranchCode.get(i).getBranchCode(), strDate,
								endDate));
					}
				}
			}

			String branchTmp = "", subDistrictTmp = "", provinceTmp = "";
			int dtPlantRai = 0, dtPlantNgan = 0, dtPlantWah = 0;
			int btPlantRai = 0, btPlantNgan = 0, btPlantWah = 0;
			int ptPlantRai = 0, ptPlantNgan = 0, ptPlantWah = 0;
			float dtFRecord = 0;
			float btFRecord = 0;
			float ptFRecord = 0;
			int linePage = 24;
			Paragraph title = new Paragraph();
			if (provList.size() <= 0) {
				document.add(table);
				title = new Paragraph("ไม่มีข้อมูล", font_title);
				title.setAlignment(Element.ALIGN_CENTER);
				document.add(title);
			} else {
				int target = 0;
				for (int i = 0; i < provList.size(); i++) {
					Object[] obj = ((Object[]) provList.get(i));
					target += Float.parseFloat(obj[13].toString());
				}
				title = new Paragraph(String.format(
						"เป้าหมายปริมาณผลผลิต %d ตัน", target / 1000),
						font_total);
				title.setAlignment(Element.ALIGN_RIGHT);
				title.setLeading(25f);
				title.setSpacingAfter(-25f);
				document.add(title);
			}
			boolean newPage = true;
			int frist = 1;
			for (int prov = 0; prov < provList.size(); prov++) {
				Object[] obj = ((Object[]) provList.get(prov));

				if (!branchTmp.equals(obj[0].toString())) {
					title = new Paragraph("ธ.ก.ส. จังหวัด" + obj[14].toString()
							+ " " + obj[0].toString(), font_total);
					title.setAlignment(Element.ALIGN_LEFT);
					title.setLeading(5f);
					title.setSpacingBefore(20f);
					branchTmp = obj[0].toString();
					linePage -= 2;
					newPage = true;
				}

				Table detail = new Table(colWidth.length);
				detail.setWidths(colWidth);
				detail.setAlignment(Cell.ALIGN_CENTER);
				detail.setWidth(100);
				detail.setPadding(2f);

				if (linePage < table.size()) {
					if (table.size() > 1) {
						document.add(title);
					}
					document.add(table);

					document.newPage();
					document.add(head);
					document.add(head_date);
					table = new Table(1);
					table.setAlignment(Cell.ALIGN_CENTER);
					table.setWidth(100);
					table.setPadding(2f);
					table.insertTable(headTable(newRows(colWidth), font_body,
							headTable));
					title.setLeading(5f);
					title.setSpacingAfter(0f);
					linePage = 23;
				}

				Cell sCell = new Cell(new Phrase(
						convertDate(obj[1].toString()), font_body));
				sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
				sCell.setVerticalAlignment(Cell.ALIGN_TOP);
				sCell.setBorderColorTop(Color.GRAY);
				sCell.setBorderWidthTop(0.1f);
				sCell.setBorderWidthRight(0.1f);
				detail.addCell(sCell);

				sCell = new Cell(new Phrase(obj[2].toString(), font_body));
				sCell.setHorizontalAlignment(Cell.ALIGN_LEFT);
				sCell.setVerticalAlignment(Cell.ALIGN_TOP);
				sCell.setBorderColorTop(Color.GRAY);
				sCell.setBorderWidthTop(0.1f);
				sCell.setBorderWidthRight(0.1f);
				detail.addCell(sCell);

				sCell = new Cell(new Phrase(obj[3] != null ? obj[3].toString()
						: " ", font_body));
				sCell.setHorizontalAlignment(Cell.ALIGN_LEFT);
				sCell.setVerticalAlignment(Cell.ALIGN_TOP);
				sCell.setBorderColorTop(Color.GRAY);
				sCell.setBorderWidthTop(0.1f);
				sCell.setBorderWidthRight(0.1f);
				detail.addCell(sCell);

				if (subDistrictTmp.equals(obj[4].toString()) && !newPage) {
					sCell = new Cell(new Phrase("", font_body));
					sCell.setBorderWidthTop(0);
					sCell.setBorderWidthBottom(0);
					sCell.setBorderWidthRight(0.1f);
					detail.addCell(sCell);

				} else {
					sCell = new Cell(new Phrase(obj[4].toString(), font_body));
					sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
					sCell.setVerticalAlignment(Cell.ALIGN_TOP);
					sCell.setBorderColorTop(Color.GRAY);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
					sCell.setBorderWidthBottom(0);
					sCell.setBorderWidthRight(0.1f);
					detail.addCell(sCell);
					newPage = false;
				}

				sCell = new Cell(new Phrase(obj[5].toString(), font_body));
				sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
				sCell.setVerticalAlignment(Cell.ALIGN_TOP);
				sCell.setBorderColorTop(Color.GRAY);
				sCell.setBorderWidthTop(0.1f);
				sCell.setBorderWidthRight(0.1f);
				detail.addCell(sCell);

				sCell = new Cell(new Phrase(" " + obj[6].toString()
						+ obj[7].toString() + " " + obj[8].toString(),
						font_body));
				sCell.setHorizontalAlignment(Cell.ALIGN_LEFT);
				sCell.setVerticalAlignment(Cell.ALIGN_TOP);
				sCell.setBorderColorTop(Color.GRAY);
				sCell.setBorderWidthTop(0.1f);
				sCell.setBorderWidthRight(0.1f);
				detail.addCell(sCell);

				sCell = new Cell(new Phrase(convertDate(obj[9].toString()),
						font_body));
				sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
				sCell.setVerticalAlignment(Cell.ALIGN_TOP);
				sCell.setBorderColorTop(Color.GRAY);
				sCell.setBorderWidthTop(0.1f);
				sCell.setBorderWidthRight(0.1f);
				detail.addCell(sCell);

				// Table tmp = new Table(3);
				int rai = Integer.parseInt(obj[10].toString());
				int ngan = Integer.parseInt(obj[11].toString());
				int wah = Integer.parseInt(obj[12].toString());

				if (wah / 100 > 0) {
					ngan += wah / 100;
					wah = wah % 100;

				}
				if (ngan / 4 > 0) {
					rai += ngan / 4;
					ngan = ngan % 4;
				}

				sCell = new Cell(new Phrase(String.format("%,d-%d-%d", rai,
						ngan, wah), font_body));
				sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
				sCell.setVerticalAlignment(Cell.ALIGN_TOP);
				sCell.setBorderColorTop(Color.GRAY);
				sCell.setBorderWidthTop(0.1f);
				sCell.setBorderWidthRight(0.1f);
				detail.addCell(sCell);

				sCell = new Cell(new Phrase(String.format("%,.2f", Float
						.parseFloat(obj[13].toString())), font_body));
				sCell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
				sCell.setVerticalAlignment(Cell.ALIGN_TOP);
				sCell.setBorderColorTop(Color.GRAY);
				sCell.setBorderWidthTop(0.1f);
				sCell.setBorderWidthRight(0.1f);
				detail.addCell(sCell);

				table.insertTable(detail);

				dtPlantRai += rai;
				dtPlantNgan += ngan;
				dtPlantWah += wah;
				dtFRecord += Float.parseFloat(obj[13].toString());
				subDistrictTmp = obj[4].toString();
				provinceTmp = obj[14].toString();

				if (prov + 1 != provList.size()) {
					if (!subDistrictTmp.equals(((Object[]) provList
							.get(prov + 1))[4].toString())
							|| !branchTmp.equals(((Object[]) provList
									.get(prov + 1))[0].toString())) {
						calRai = totalRai(dtPlantRai, dtPlantNgan, dtPlantWah);
						String[] total = new String[] {
								String.format("%,d-%d-%d|", calRai[0],
										calRai[1], calRai[2]),
								String.format("%,.2f", dtFRecord) };
						if (linePage < table.size()) {
							if (table.size() > 1) {
								document.add(title);
							}
							document.add(table);

							document.newPage();
							document.add(head);
							document.add(head_date);
							table = new Table(1);
							table.setAlignment(Cell.ALIGN_CENTER);
							table.setWidth(100);
							table.setPadding(2f);
							table.insertTable(headTable(newRows(colWidth),
									font_body, headTable));
							title.setLeading(5f);
							title.setSpacingAfter(0f);
							linePage = 23;
						}
						table.insertTable(totalTable(colTotalWidth, font_total,
								"รวม", total));
						btPlantRai += dtPlantRai;
						btPlantNgan += dtPlantNgan;
						btPlantWah += dtPlantWah;
						btFRecord += dtFRecord;
						dtPlantRai = 0;
						dtPlantNgan = 0;
						dtPlantWah = 0;
						dtPlantNgan = 0;
						dtPlantWah = 0;
						dtFRecord = 0;
					}

					if (!branchTmp
							.equals(((Object[]) provList.get(prov + 1))[0]
									.toString())) {
						calRai = totalRai(btPlantRai, btPlantNgan, btPlantWah);
						String[] totalBran = new String[] {
								String.format("%,d-%d-%d|", calRai[0],
										calRai[1], calRai[2]),
								String.format("%,.2f", btFRecord) };
						if (linePage < table.size()) {
							if (table.size() > 1) {
								document.add(title);
							}
							document.add(table);

							document.newPage();
							document.add(head);
							document.add(head_date);
							table = new Table(1);
							table.setAlignment(Cell.ALIGN_CENTER);
							table.setWidth(100);
							table.setPadding(2f);
							table.insertTable(headTable(newRows(colWidth),
									font_body, headTable));
							title.setLeading(5f);
							title.setSpacingAfter(0f);
							linePage = 23;
						}
						table.insertTable(totalTable(colTotalWidth, font_total,
								"ยอดรวมสาขา " + branchTmp, totalBran));
						ptPlantRai += btPlantRai;
						ptPlantNgan += btPlantNgan;
						ptPlantWah += btPlantWah;
						ptFRecord += btFRecord;
						btPlantRai = 0;
						btPlantNgan = 0;
						btPlantWah = 0;
						btFRecord = 0;

						if (!provinceTmp.equals(((Object[]) provList
								.get(prov + 1))[14].toString())) {
							calRai = totalRai(ptPlantRai, ptPlantNgan,
									ptPlantWah);
							String[] totalProv = new String[] {
									String.format("%,d-%d-%d|", calRai[0],
											calRai[1], calRai[2]),
									String.format("%,.2f", ptFRecord) };
							if (linePage < table.size()) {
								if (table.size() > 1) {
									document.add(title);
								}
								document.add(table);

								document.newPage();
								document.add(head);
								document.add(head_date);
								table = new Table(1);
								table.setAlignment(Cell.ALIGN_CENTER);
								table.setWidth(100);
								table.setPadding(2f);
								table.insertTable(headTable(newRows(colWidth),
										font_body, headTable));
								title.setLeading(5f);
								title.setSpacingAfter(0f);
								linePage = 23;
							}
							table.insertTable(totalTable(colTotalWidth,
									font_total, "ยอดรวมจังหวัด "
											+ obj[14].toString(), totalProv));

							ptPlantRai = 0;
							ptPlantNgan = 0;
							ptPlantWah = 0;
							ptFRecord = 0;
						}

						if (table.size() > 1) {
							document.add(title);
							document.add(table);
							linePage -= table.size();
							table = new Table(1);
							table.setAlignment(Cell.ALIGN_CENTER);
							table.setWidth(100);
							table.setPadding(2f);
						}
					}
				} else {
					calRai = totalRai(dtPlantRai, dtPlantNgan, dtPlantWah);
					String[] total = new String[] {
							String.format("%,d-%d-%d|", calRai[0], calRai[1],
									calRai[2]),
							String.format("%,.2f", dtFRecord) };
					if (linePage < table.size()) {
						if (table.size() > 1) {
							document.add(title);
						}
						document.add(table);

						document.newPage();
						document.add(head);
						document.add(head_date);
						table = new Table(1);
						table.setAlignment(Cell.ALIGN_CENTER);
						table.setWidth(100);
						table.setPadding(2f);
						table.insertTable(headTable(newRows(colWidth),
								font_body, headTable));
						title.setLeading(5f);
						title.setSpacingAfter(0f);
						linePage = 23;
					}
					table.insertTable(totalTable(colTotalWidth, font_total,
							"รวม", total));
					btPlantRai += dtPlantRai;
					btPlantNgan += dtPlantNgan;
					btPlantWah += dtPlantWah;
					btFRecord += dtFRecord;

					calRai = totalRai(btPlantRai, btPlantNgan, btPlantWah);
					String[] totalBran = new String[] {
							String.format("%,d-%d-%d|", calRai[0], calRai[1],
									calRai[2]),
							String.format("%,.2f", btFRecord) };
					if (linePage < table.size()) {
						if (table.size() > 1) {
							document.add(title);
						}
						document.add(table);

						document.newPage();
						document.add(head);
						document.add(head_date);
						table = new Table(1);
						table.setAlignment(Cell.ALIGN_CENTER);
						table.setWidth(100);
						table.setPadding(2f);
						table.insertTable(headTable(newRows(colWidth),
								font_body, headTable));
						title.setLeading(5f);
						title.setSpacingAfter(0f);
						linePage = 23;
					}
					table.insertTable(totalTable(colTotalWidth, font_total,
							"ยอดรวมสาขา " + branchTmp, totalBran));
					ptPlantRai += btPlantRai;
					ptPlantNgan += btPlantNgan;
					ptPlantWah += btPlantWah;
					ptFRecord += btFRecord;

					calRai = totalRai(ptPlantRai, ptPlantNgan, ptPlantWah);
					String[] totalProv = new String[] {
							String.format("%,d-%d-%d|", calRai[0], calRai[1],
									calRai[2]),
							String.format("%,.2f", ptFRecord) };
					if (linePage < table.size()) {
						if (table.size() > 1) {
							document.add(title);
						}
						document.add(table);

						document.newPage();
						document.add(head);
						document.add(head_date);
						table = new Table(1);
						table.setAlignment(Cell.ALIGN_CENTER);
						table.setWidth(100);
						table.setPadding(2f);
						table.insertTable(headTable(newRows(colWidth),
								font_body, headTable));
						title.setLeading(5f);
						title.setSpacingAfter(0f);
						linePage = 23;
					}
					table.insertTable(totalTable(colTotalWidth, font_total,
							"ยอดรวมจังหวัด " + obj[14].toString(), totalProv));

					document.add(title);
					document.add(table);
				}
			}

		} catch (Exception e) {
			document.add(new Paragraph("เกิดการดึงข้อมูลผิดพลาด", font_title));
			e.printStackTrace();
			log.error(e, e);
		} finally {
			sf.getCurrentSession().close();
		}
		return document;
	}

	private Document ReportR104(SessionFactory sf, Document document,
			Font font_title, Font font_body, Font font_total, ReportForm eform)
			throws DocumentException {
		try {
			sf.getCurrentSession().beginTransaction();
			Paragraph head = new Paragraph(
					"ข้อมูลการเปรียบเทียบประมาณการผลผลิตรวมกับผลผลิตรวมจริงที่ได้รับ ตามปีการผลิต "
							+ eform.getPlantYear() + "/" + eform.getPlantNo(),
					font_title);
			head.setAlignment(Element.ALIGN_CENTER);
			document.add(head);

			Paragraph head_date = new Paragraph("สิ้นสุด ณ วันที่ "
					+ dateThai(eform.getEffectiveDate(), false), font_title);
			head_date.setAlignment(Element.ALIGN_CENTER);
			head_date.setSpacingBefore(5f);
			document.add(head_date);

			Table table = new Table(1);
			table.setAlignment(Cell.ALIGN_CENTER);
			table.setWidth(100);
			table.setPadding(2f);
			float[] colWidth, colTotalWidth;
			colWidth = new float[] { 15, 20, 30, 15, 30, 15, 15, 15 };
			colTotalWidth = new float[] { 110, 15, 15, 15 };
			String[] headTable = new String[] { "ตำบลที่ตั้งแปลง",
					"บัตรประจำตัวประชาชน\n(เลขที่)", "ชื่อ-สกุล", "ชนิดพืช",
					"|วันที่|เพาะปลูก|เก็บเกี่ยว",
					"เนื้อที่เพาะปลูก\nไร่-งาน-วา",
					"ประมาณการผล\nผลิตรวม\n(กก.)",
					"ผลผลิตรวมจริง\nที่ได้รับ\n(กก.)" };
			table
					.insertTable(headTable(newRows(colWidth), font_body,
							headTable));

			ReportHome rhome = new ReportHome();
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

			List provList = rhome.findR104ByCriteria(eform.getPlantYear(),
					eform.getPlantNo(), df.parse(eform.getEffectiveDate()),
					eform.getProvinceNo(), eform.getBranchCode());
			if (eform.getBranchCode() > 0) {
				BranchHome bhome = new BranchHome();
				List<Branch> pbList = bhome
						.findByPBranch(eform.getBranchCode());
				List<Branch> pbranchCode = new ArrayList();
				if (pbList.size() > 0) {
					pbranchCode
							.addAll(getPBranch(pbList, eform.getBranchCode()));
					for (int i = 0; i < pbranchCode.size(); i++) {
						provList.addAll(rhome.findR104ByCriteria(eform
								.getPlantYear(), eform.getPlantNo(), df
								.parse(eform.getEffectiveDate()), 0,
								pbranchCode.get(i).getBranchCode()));
					}
				}
			}
			String branchTmp = "", subDistrictTmp = "", provinceTmp = "";
			int dtPlantRai = 0, dtPlantNgan = 0, dtPlantWah = 0;
			int btPlantRai = 0, btPlantNgan = 0, btPlantWah = 0;
			int ptPlantRai = 0, ptPlantNgan = 0, ptPlantWah = 0;
			float dtFRecord = 0, dtPRecord = 0;
			float btFRecord = 0, btPRecord = 0;
			float ptFRecord = 0, ptPRecord = 0;
			int linePage = 23;
			Paragraph title = new Paragraph();
			if (provList.size() <= 0) {
				document.add(table);
				title = new Paragraph("ไม่มีข้อมูล", font_title);
				title.setAlignment(Element.ALIGN_CENTER);
				document.add(title);
			}
			String idCardTmp = "", breedTmp = "";
			boolean newPage = true;
			int frist = 1;
			for (int prov = 0; prov < provList.size(); prov++) {
				Object[] obj = ((Object[]) provList.get(prov));

				if (!branchTmp.equals(obj[0].toString())) {
					title = new Paragraph("ธ.ก.ส. จังหวัด" + obj[14].toString()
							+ " " + obj[0].toString(), font_total);
					title.setAlignment(Element.ALIGN_LEFT);
					title.setLeading(5f);
					title.setSpacingBefore(20f);
					linePage -= 2;
					branchTmp = obj[0].toString();
					newPage = true;
				}
				Table detail = new Table(colWidth.length);
				detail.setWidths(colWidth);
				detail.setAlignment(Cell.ALIGN_CENTER);
				detail.setWidth(100);
				detail.setPadding(2f);

				Cell sCell = new Cell();

				if (linePage < table.size()) {
					if (table.size() > 1) {
						document.add(title);
					}
					document.add(table);

					document.newPage();
					document.add(head);
					document.add(head_date);
					table = new Table(1);
					table.setAlignment(Cell.ALIGN_CENTER);
					table.setWidth(100);
					table.setPadding(2f);
					table.insertTable(headTable(newRows(colWidth), font_body,
							headTable));
					title.setLeading(5f);
					title.setSpacingAfter(0f);
					newPage = true;
					linePage = 21;
				}

				if (subDistrictTmp.equals(obj[1].toString()) && !newPage) {
					sCell = new Cell(new Phrase("", font_body));
					sCell.setBorderWidthTop(0);
					sCell.setBorderWidthBottom(0);
					sCell.setBorderWidthRight(0.1f);
					detail.addCell(sCell);
				} else {
					sCell = new Cell(new Phrase(obj[1].toString(), font_body));
					sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
					sCell.setVerticalAlignment(Cell.ALIGN_TOP);
					sCell.setBorderColorTop(Color.GRAY);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
					sCell.setBorderWidthBottom(0);
					sCell.setBorderWidthRight(0.1f);
					detail.addCell(sCell);
					newPage = true;
				}

				if (idCardTmp.equals(obj[2].toString()) && !newPage) {
					sCell = new Cell(new Phrase("", font_body));
					sCell.setBorderWidthTop(0);
					sCell.setBorderWidthBottom(0);
					sCell.setBorderWidthRight(0.1f);
					detail.addCell(sCell);
					detail.addCell(sCell);
					if (breedTmp.equals(obj[6].toString())) {
						detail.addCell(sCell);
					} else {
						sCell = new Cell(new Phrase(obj[6].toString(),
								font_body));
						sCell.setHorizontalAlignment(Cell.ALIGN_LEFT);
						sCell.setVerticalAlignment(Cell.ALIGN_TOP);
						sCell.setBorderColorTop(Color.GRAY);
						sCell.setBorderWidthTop(0.1f);
						sCell.setBorderWidthRight(0.1f);
						sCell.setBorderWidthTop(0.1f);
						sCell.setBorderWidthRight(0.1f);
						sCell.setBorderWidthBottom(0);
						sCell.setBorderWidthRight(0.1f);
						detail.addCell(sCell);

						breedTmp = obj[6].toString();
						newPage = false;
					}
				} else {
					sCell = new Cell(new Phrase(obj[2].toString(), font_body));
					sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
					sCell.setVerticalAlignment(Cell.ALIGN_TOP);
					sCell.setBorderColorTop(Color.GRAY);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
					sCell.setBorderWidthBottom(0);
					sCell.setBorderWidthRight(0.1f);
					detail.addCell(sCell);

					sCell = new Cell(new Phrase(" " + obj[3].toString()
							+ obj[4].toString() + " " + obj[5].toString(),
							font_body));
					sCell.setHorizontalAlignment(Cell.ALIGN_LEFT);
					sCell.setVerticalAlignment(Cell.ALIGN_TOP);
					sCell.setBorderColorTop(Color.GRAY);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
					sCell.setBorderWidthBottom(0);
					sCell.setBorderWidthRight(0.1f);
					detail.addCell(sCell);

					idCardTmp = obj[2].toString();

					sCell = new Cell(new Phrase(obj[6].toString(), font_body));
					sCell.setHorizontalAlignment(Cell.ALIGN_LEFT);
					sCell.setVerticalAlignment(Cell.ALIGN_TOP);
					sCell.setBorderColorTop(Color.GRAY);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
					sCell.setBorderWidthBottom(0);
					sCell.setBorderWidthRight(0.1f);
					detail.addCell(sCell);

					breedTmp = obj[6].toString();
					newPage = false;
				}

				Table tmp = new Table(2);
				sCell = new Cell(new Phrase(convertDate(obj[7].toString()),
						font_body));
				sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
				sCell.setVerticalAlignment(Cell.ALIGN_TOP);
				sCell.setBorderColorTop(Color.GRAY);
				sCell.setBorderWidthTop(0.1f);
				sCell.setBorderWidthRight(0.1f);
				tmp.addCell(sCell);

				if (obj[8] != null) {
					sCell = new Cell(new Phrase(convertDate(obj[8].toString()),
							font_body));
					sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
					sCell.setVerticalAlignment(Cell.ALIGN_TOP);
					sCell.setBorderColorTop(Color.GRAY);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
				} else {
					sCell = new Cell(new Phrase(" ", font_body));
					sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
					sCell.setVerticalAlignment(Cell.ALIGN_TOP);
					sCell.setBorderColorTop(Color.GRAY);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
				}

				tmp.addCell(sCell);
				detail.insertTable(tmp);

				// tmp = new Table(3);
				int rai = Integer.parseInt(obj[9].toString());
				int ngan = Integer.parseInt(obj[10].toString());
				int wah = Integer.parseInt(obj[11].toString());

				if (wah / 100 > 0) {
					ngan += wah / 100;
					wah = wah % 100;

				}
				if (ngan / 4 > 0) {
					rai += ngan / 4;
					ngan = ngan % 4;
				}

				sCell = new Cell(new Phrase(String.format("%,d-%d-%d", rai,
						ngan, wah), font_body));
				sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
				sCell.setVerticalAlignment(Cell.ALIGN_TOP);
				sCell.setBorderColorTop(Color.GRAY);
				sCell.setBorderWidthTop(0.1f);
				sCell.setBorderWidthRight(0.1f);
				detail.addCell(sCell);

				sCell = new Cell(new Phrase(String.format("%,.2f", Float
						.parseFloat(obj[12].toString())), font_body));
				sCell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
				sCell.setVerticalAlignment(Cell.ALIGN_TOP);
				sCell.setBorderColorTop(Color.GRAY);
				sCell.setBorderWidthTop(0.1f);
				sCell.setBorderWidthRight(0.1f);
				detail.addCell(sCell);

				if (obj[13] != null) {
					sCell = new Cell(new Phrase(String.format("%,.2f", Float
							.parseFloat(obj[13].toString())), font_body));
					sCell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
					sCell.setVerticalAlignment(Cell.ALIGN_TOP);
					sCell.setBorderColorTop(Color.GRAY);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
				} else {
					sCell = new Cell(new Phrase(String.format("%,.2f", 0.00f),
							font_body));
					sCell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
					sCell.setVerticalAlignment(Cell.ALIGN_TOP);
					sCell.setBorderColorTop(Color.GRAY);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
				}

				detail.addCell(sCell);

				table.insertTable(detail);

				dtPlantRai += rai;
				dtPlantNgan += ngan;
				dtPlantWah += wah;
				dtFRecord += Float.parseFloat(obj[12].toString());
				if (obj[13] != null) {
					dtPRecord += Float.parseFloat(obj[13].toString());
				}
				subDistrictTmp = obj[1].toString();
				provinceTmp = obj[14].toString();

				if (prov + 1 != provList.size()) {
					if (!subDistrictTmp.equals(((Object[]) provList
							.get(prov + 1))[1].toString())) {
						calRai = totalRai(dtPlantRai, dtPlantNgan, dtPlantWah);
						String[] total = new String[] {
								String.format("%,d-%d-%d|", calRai[0],
										calRai[1], calRai[2]),
								String.format("%,.2f", dtFRecord),
								String.format("%,.2f", dtPRecord) };
						if (linePage < table.size()) {
							if (table.size() > 1) {
								document.add(title);
							}
							document.add(table);

							document.newPage();
							document.add(head);
							document.add(head_date);
							table = new Table(1);
							table.setAlignment(Cell.ALIGN_CENTER);
							table.setWidth(100);
							table.setPadding(2f);
							table.insertTable(headTable(newRows(colWidth),
									font_body, headTable));
							title.setLeading(5f);
							title.setSpacingAfter(0f);
							newPage = true;
							linePage = 21;
						}
						table.insertTable(totalTable(colTotalWidth, font_total,
								"รวม", total));
						btPlantRai += dtPlantRai;
						btPlantNgan += dtPlantNgan;
						btPlantWah += dtPlantWah;
						btFRecord += dtFRecord;
						btPRecord += dtPRecord;
						dtPlantRai = 0;
						dtPlantNgan = 0;
						dtPlantWah = 0;
						dtPlantNgan = 0;
						dtPlantWah = 0;
						dtFRecord = 0;
						dtPRecord = 0;
					}

					if (!branchTmp
							.equals(((Object[]) provList.get(prov + 1))[0]
									.toString())) {
						calRai = totalRai(btPlantRai, btPlantNgan, btPlantWah);
						String[] totalBran = new String[] {
								String.format("%,d-%d-%d|", calRai[0],
										calRai[1], calRai[2]),
								String.format("%,.2f", btFRecord),
								String.format("%,.2f", btPRecord) };
						if (linePage < table.size()) {
							if (table.size() > 1) {
								document.add(title);
							}
							document.add(table);

							document.newPage();
							document.add(head);
							document.add(head_date);
							table = new Table(1);
							table.setAlignment(Cell.ALIGN_CENTER);
							table.setWidth(100);
							table.setPadding(2f);
							table.insertTable(headTable(newRows(colWidth),
									font_body, headTable));
							title.setLeading(5f);
							title.setSpacingAfter(0f);
							newPage = true;
							linePage = 21;
						}
						table.insertTable(totalTable(colTotalWidth, font_total,
								"ยอดรวมสาขา " + branchTmp, totalBran));
						ptPlantRai += btPlantRai;
						ptPlantNgan += btPlantNgan;
						ptPlantWah += btPlantWah;
						ptFRecord += btFRecord;
						ptPRecord += btPRecord;
						btPlantRai = 0;
						btPlantNgan = 0;
						btPlantWah = 0;
						btFRecord = 0;
						btPRecord = 0;

						if (!provinceTmp.equals(((Object[]) provList
								.get(prov + 1))[14].toString())) {
							calRai = totalRai(ptPlantRai, ptPlantNgan,
									ptPlantWah);
							String[] totalProv = new String[] {
									String.format("%,d-%d-%d|", calRai[0],
											calRai[1], calRai[2]),
									String.format("%,.2f", ptFRecord),
									String.format("%,.2f", ptPRecord) };
							if (linePage < table.size()) {
								if (table.size() > 1) {
									document.add(title);
								}
								document.add(table);

								document.newPage();
								document.add(head);
								document.add(head_date);
								table = new Table(1);
								table.setAlignment(Cell.ALIGN_CENTER);
								table.setWidth(100);
								table.setPadding(2f);
								table.insertTable(headTable(newRows(colWidth),
										font_body, headTable));
								title.setLeading(5f);
								title.setSpacingAfter(0f);
								newPage = true;
								linePage = 21;
							}
							table.insertTable(totalTable(colTotalWidth,
									font_total, "ยอดรวมจังหวัด "
											+ obj[14].toString(), totalProv));

							ptPlantRai = 0;
							ptPlantNgan = 0;
							ptPlantWah = 0;
							ptFRecord = 0;
							ptPRecord = 0;
						}

						if (table.size() > 1) {
							document.add(title);
							document.add(table);
							linePage -= table.size();
							table = new Table(1);
							table.setAlignment(Cell.ALIGN_CENTER);
							table.setWidth(100);
							table.setPadding(2f);
						}
					}
				} else {
					calRai = totalRai(dtPlantRai, dtPlantNgan, dtPlantWah);
					String[] total = new String[] {
							String.format("%,d-%d-%d|", calRai[0], calRai[1],
									calRai[2]),
							String.format("%,.2f", dtFRecord),
							String.format("%,.2f", dtPRecord) };
					if (linePage < table.size()) {
						if (table.size() > 1) {
							document.add(title);
						}
						document.add(table);

						document.newPage();
						document.add(head);
						document.add(head_date);
						table = new Table(1);
						table.setAlignment(Cell.ALIGN_CENTER);
						table.setWidth(100);
						table.setPadding(2f);
						table.insertTable(headTable(newRows(colWidth),
								font_body, headTable));
						title.setLeading(5f);
						title.setSpacingAfter(0f);
						newPage = true;
						linePage = 21;
					}
					table.insertTable(totalTable(colTotalWidth, font_total,
							"รวม", total));
					btPlantRai += dtPlantRai;
					btPlantNgan += dtPlantNgan;
					btPlantWah += dtPlantWah;
					btFRecord += dtFRecord;
					btPRecord += dtPRecord;
					dtPlantRai = 0;
					dtPlantNgan = 0;
					dtPlantWah = 0;
					dtPlantNgan = 0;
					dtPlantWah = 0;
					dtFRecord = 0;
					dtPRecord = 0;

					calRai = totalRai(btPlantRai, btPlantNgan, btPlantWah);
					String[] totalBran = new String[] {
							String.format("%,d-%d-%d|", calRai[0], calRai[1],
									calRai[2]),
							String.format("%,.2f", btFRecord),
							String.format("%,.2f", btPRecord) };
					if (linePage < table.size()) {
						if (table.size() > 1) {
							document.add(title);
						}
						document.add(table);

						document.newPage();
						document.add(head);
						document.add(head_date);
						table = new Table(1);
						table.setAlignment(Cell.ALIGN_CENTER);
						table.setWidth(100);
						table.setPadding(2f);
						table.insertTable(headTable(newRows(colWidth),
								font_body, headTable));
						title.setLeading(5f);
						title.setSpacingAfter(0f);
						newPage = true;
						linePage = 21;
					}
					table.insertTable(totalTable(colTotalWidth, font_total,
							"ยอดรวมสาขา " + branchTmp, totalBran));
					ptPlantRai += btPlantRai;
					ptPlantNgan += btPlantNgan;
					ptPlantWah += btPlantWah;
					ptFRecord += btFRecord;
					ptPRecord += btPRecord;
					btPlantRai = 0;
					btPlantNgan = 0;
					btPlantWah = 0;
					btFRecord = 0;
					btPRecord = 0;

					calRai = totalRai(ptPlantRai, ptPlantNgan, ptPlantWah);
					String[] totalProv = new String[] {
							String.format("%,d-%d-%d|", calRai[0], calRai[1],
									calRai[2]),
							String.format("%,.2f", ptFRecord),
							String.format("%,.2f", ptPRecord) };
					if (linePage < table.size()) {
						if (table.size() > 1) {
							document.add(title);
						}
						document.add(table);

						document.newPage();
						document.add(head);
						document.add(head_date);
						table = new Table(1);
						table.setAlignment(Cell.ALIGN_CENTER);
						table.setWidth(100);
						table.setPadding(2f);
						table.insertTable(headTable(newRows(colWidth),
								font_body, headTable));
						title.setLeading(5f);
						title.setSpacingAfter(0f);
						newPage = true;
						linePage = 21;
					}
					table.insertTable(totalTable(colTotalWidth, font_total,
							"ยอดรวมจังหวัด " + obj[14].toString(), totalProv));

					document.add(title);
					document.add(table);
				}
			}
		} catch (Exception e) {
			document.add(new Paragraph("เกิดการดึงข้อมูลผิดพลาด", font_title));
			e.printStackTrace();
			log.error(e, e);
		} finally {
			sf.getCurrentSession().close();
		}
		return document;
	}

	private Document ReportR104_1(SessionFactory sf, Document document,
			Font font_title, Font font_body, Font font_total, ReportForm eform)
			throws DocumentException {
		try {
			sf.getCurrentSession().beginTransaction();
			Paragraph head = new Paragraph(
					"ข้อมูลการเปรียบเทียบประมาณการผลผลิตรวมกับผลผลิตรวมจริงที่ได้รับ ตามปีการผลิต "
							+ eform.getPlantYear() + "/" + eform.getPlantNo(),
					font_title);
			head.setAlignment(Element.ALIGN_CENTER);
			document.add(head);

			Paragraph head_date = new Paragraph("สิ้นสุด ณ วันที่ "
					+ dateThai(eform.getEffectiveDate(), false), font_title);
			head_date.setAlignment(Element.ALIGN_CENTER);
			head_date.setSpacingBefore(5f);
			document.add(head_date);

			Table table = new Table(1);
			table.setAlignment(Cell.ALIGN_CENTER);
			table.setWidth(100);
			table.setPadding(2f);
			float[] colWidth, colTotalWidth;
			colWidth = new float[] { 15, 20, 30, 15, 30, 15, 15, 15 };
			colTotalWidth = new float[] { 110, 15, 15, 15 };
			String[] headTable = new String[] { "ตำบลที่ตั้งแปลง",
					"บัตรประจำตัวประชาชน\n(เลขที่)", "ชื่อ-สกุล",
					"กลุ่มพันธุ์", "|วันที่|เพาะปลูก|เก็บเกี่ยว",
					"เนื้อที่เพาะปลูก\nไร่-งาน-วา",
					"ประมาณการผล\nผลิตรวม\n(กก.)",
					"ผลผลิตรวมจริง\nที่ได้รับ\n(กก.)" };
			table
					.insertTable(headTable(newRows(colWidth), font_body,
							headTable));

			ReportHome rhome = new ReportHome();
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

			List provList = rhome.findR104_1ByCriteria(eform.getPlantYear(),
					eform.getPlantNo(), df.parse(eform.getEffectiveDate()),
					eform.getProvinceNo(), eform.getBranchCode(), eform
							.getBreedGroupId());
			if (eform.getBranchCode() > 0) {
				BranchHome bhome = new BranchHome();
				List<Branch> pbList = bhome
						.findByPBranch(eform.getBranchCode());
				List<Branch> pbranchCode = new ArrayList();
				if (pbList.size() > 0) {
					pbranchCode
							.addAll(getPBranch(pbList, eform.getBranchCode()));
					for (int i = 0; i < pbranchCode.size(); i++) {
						provList.addAll(rhome.findR104_1ByCriteria(eform
								.getPlantYear(), eform.getPlantNo(), df
								.parse(eform.getEffectiveDate()), 0,
								pbranchCode.get(i).getBranchCode(), eform
										.getBreedGroupId()));
					}
				}
			}
			String branchTmp = "", subDistrictTmp = "", provinceTmp = "";
			int dtPlantRai = 0, dtPlantNgan = 0, dtPlantWah = 0;
			int btPlantRai = 0, btPlantNgan = 0, btPlantWah = 0;
			int ptPlantRai = 0, ptPlantNgan = 0, ptPlantWah = 0;
			float dtFRecord = 0, dtPRecord = 0;
			float btFRecord = 0, btPRecord = 0;
			float ptFRecord = 0, ptPRecord = 0;
			int linePage = 23;
			Paragraph title = new Paragraph();
			if (provList.size() <= 0) {
				document.add(table);
				title = new Paragraph("ไม่มีข้อมูล", font_title);
				title.setAlignment(Element.ALIGN_CENTER);
				document.add(title);
			}
			String idCardTmp = "", breedTmp = "";
			boolean newPage = true;
			int frist = 1;
			for (int prov = 0; prov < provList.size(); prov++) {
				Object[] obj = ((Object[]) provList.get(prov));

				if (!branchTmp.equals(obj[0].toString())) {
					title = new Paragraph("ธ.ก.ส. จังหวัด" + obj[14].toString()
							+ " " + obj[0].toString(), font_total);
					title.setAlignment(Element.ALIGN_LEFT);
					title.setLeading(5f);
					title.setSpacingBefore(20f);
					linePage -= 2;
					branchTmp = obj[0].toString();
					newPage = true;
				}
				Table detail = new Table(colWidth.length);
				detail.setWidths(colWidth);
				detail.setAlignment(Cell.ALIGN_CENTER);
				detail.setWidth(100);
				detail.setPadding(2f);

				Cell sCell = new Cell();

				if (linePage < table.size()) {
					if (table.size() > 1) {
						document.add(title);
					}
					document.add(table);

					document.newPage();
					document.add(head);
					document.add(head_date);
					table = new Table(1);
					table.setAlignment(Cell.ALIGN_CENTER);
					table.setWidth(100);
					table.setPadding(2f);
					table.insertTable(headTable(newRows(colWidth), font_body,
							headTable));
					title.setLeading(5f);
					title.setSpacingAfter(0f);
					newPage = true;
					linePage = 21;
				}

				if (subDistrictTmp.equals(obj[1].toString()) && !newPage) {
					sCell = new Cell(new Phrase("", font_body));
					sCell.setBorderWidthTop(0);
					sCell.setBorderWidthBottom(0);
					sCell.setBorderWidthRight(0.1f);
					detail.addCell(sCell);
				} else {
					sCell = new Cell(new Phrase(obj[1].toString(), font_body));
					sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
					sCell.setVerticalAlignment(Cell.ALIGN_TOP);
					sCell.setBorderColorTop(Color.GRAY);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
					sCell.setBorderWidthBottom(0);
					sCell.setBorderWidthRight(0.1f);
					detail.addCell(sCell);
					newPage = true;
				}

				if (idCardTmp.equals(obj[2].toString()) && !newPage) {
					sCell = new Cell(new Phrase("", font_body));
					sCell.setBorderWidthTop(0);
					sCell.setBorderWidthBottom(0);
					sCell.setBorderWidthRight(0.1f);
					detail.addCell(sCell);
					detail.addCell(sCell);
					if (breedTmp.equals(obj[6].toString())) {
						detail.addCell(sCell);
					} else {
						sCell = new Cell(new Phrase(obj[6].toString(),
								font_body));
						sCell.setHorizontalAlignment(Cell.ALIGN_LEFT);
						sCell.setVerticalAlignment(Cell.ALIGN_TOP);
						sCell.setBorderColorTop(Color.GRAY);
						sCell.setBorderWidthTop(0.1f);
						sCell.setBorderWidthRight(0.1f);
						sCell.setBorderWidthTop(0.1f);
						sCell.setBorderWidthRight(0.1f);
						sCell.setBorderWidthBottom(0);
						sCell.setBorderWidthRight(0.1f);
						detail.addCell(sCell);

						breedTmp = obj[6].toString();
					}
				} else {
					sCell = new Cell(new Phrase(obj[2].toString(), font_body));
					sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
					sCell.setVerticalAlignment(Cell.ALIGN_TOP);
					sCell.setBorderColorTop(Color.GRAY);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
					sCell.setBorderWidthBottom(0);
					sCell.setBorderWidthRight(0.1f);
					detail.addCell(sCell);

					sCell = new Cell(new Phrase(" " + obj[3].toString()
							+ obj[4].toString() + " " + obj[5].toString(),
							font_body));
					sCell.setHorizontalAlignment(Cell.ALIGN_LEFT);
					sCell.setVerticalAlignment(Cell.ALIGN_TOP);
					sCell.setBorderColorTop(Color.GRAY);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
					sCell.setBorderWidthBottom(0);
					sCell.setBorderWidthRight(0.1f);
					detail.addCell(sCell);

					idCardTmp = obj[2].toString();

					sCell = new Cell(new Phrase(obj[6].toString(), font_body));
					sCell.setHorizontalAlignment(Cell.ALIGN_LEFT);
					sCell.setVerticalAlignment(Cell.ALIGN_TOP);
					sCell.setBorderColorTop(Color.GRAY);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
					sCell.setBorderWidthBottom(0);
					sCell.setBorderWidthRight(0.1f);
					detail.addCell(sCell);

					breedTmp = obj[6].toString();
					newPage = false;
				}

				Table tmp = new Table(2);
				sCell = new Cell(new Phrase(convertDate(obj[7].toString()),
						font_body));
				sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
				sCell.setVerticalAlignment(Cell.ALIGN_TOP);
				sCell.setBorderColorTop(Color.GRAY);
				sCell.setBorderWidthTop(0.1f);
				sCell.setBorderWidthRight(0.1f);
				tmp.addCell(sCell);
				if (obj[8] != null) {
					sCell = new Cell(new Phrase(convertDate(obj[8].toString()),
							font_body));
					sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
					sCell.setVerticalAlignment(Cell.ALIGN_TOP);
					sCell.setBorderColorTop(Color.GRAY);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
				} else {
					sCell = new Cell(new Phrase(" ", font_body));
					sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
					sCell.setVerticalAlignment(Cell.ALIGN_TOP);
					sCell.setBorderColorTop(Color.GRAY);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
				}

				tmp.addCell(sCell);
				detail.insertTable(tmp);

				// tmp = new Table(3);
				int rai = Integer.parseInt(obj[9].toString());
				int ngan = Integer.parseInt(obj[10].toString());
				int wah = Integer.parseInt(obj[11].toString());

				if (wah / 100 > 0) {
					ngan += wah / 100;
					wah = wah % 100;

				}
				if (ngan / 4 > 0) {
					rai += ngan / 4;
					ngan = ngan % 4;
				}

				sCell = new Cell(new Phrase(String.format("%,d-%d-%d", rai,
						ngan, wah), font_body));
				sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
				sCell.setVerticalAlignment(Cell.ALIGN_TOP);
				sCell.setBorderColorTop(Color.GRAY);
				sCell.setBorderWidthTop(0.1f);
				sCell.setBorderWidthRight(0.1f);
				detail.addCell(sCell);

				sCell = new Cell(new Phrase(String.format("%,.2f", Float
						.parseFloat(obj[12].toString())), font_body));
				sCell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
				sCell.setVerticalAlignment(Cell.ALIGN_TOP);
				sCell.setBorderColorTop(Color.GRAY);
				sCell.setBorderWidthTop(0.1f);
				sCell.setBorderWidthRight(0.1f);
				detail.addCell(sCell);

				if (obj[13] != null) {
					sCell = new Cell(new Phrase(String.format("%,.2f", Float
							.parseFloat(obj[13].toString())), font_body));
					sCell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
					sCell.setVerticalAlignment(Cell.ALIGN_TOP);
					sCell.setBorderColorTop(Color.GRAY);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
				} else {
					sCell = new Cell(new Phrase(String.format("%,.2f", 0.00f),
							font_body));
					sCell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
					sCell.setVerticalAlignment(Cell.ALIGN_TOP);
					sCell.setBorderColorTop(Color.GRAY);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
				}

				detail.addCell(sCell);

				table.insertTable(detail);

				dtPlantRai += rai;
				dtPlantNgan += ngan;
				dtPlantWah += wah;
				dtFRecord += Float.parseFloat(obj[12].toString());
				if (obj[13] != null) {
					dtPRecord += Float.parseFloat(obj[13].toString());
				}
				subDistrictTmp = obj[1].toString();
				provinceTmp = obj[14].toString();

				if (prov + 1 != provList.size()) {
					if (!subDistrictTmp.equals(((Object[]) provList
							.get(prov + 1))[1].toString())) {
						calRai = totalRai(dtPlantRai, dtPlantNgan, dtPlantWah);
						String[] total = new String[] {
								String.format("%,d-%d-%d|", calRai[0],
										calRai[1], calRai[2]),
								String.format("%,.2f", dtFRecord),
								String.format("%,.2f", dtPRecord) };
						if (linePage < table.size()) {
							if (table.size() > 1) {
								document.add(title);
							}
							document.add(table);

							document.newPage();
							document.add(head);
							document.add(head_date);
							table = new Table(1);
							table.setAlignment(Cell.ALIGN_CENTER);
							table.setWidth(100);
							table.setPadding(2f);
							table.insertTable(headTable(newRows(colWidth),
									font_body, headTable));
							title.setLeading(5f);
							title.setSpacingAfter(0f);
							newPage = true;
							linePage = 21;
						}
						table.insertTable(totalTable(colTotalWidth, font_total,
								"รวม", total));
						btPlantRai += dtPlantRai;
						btPlantNgan += dtPlantNgan;
						btPlantWah += dtPlantWah;
						btFRecord += dtFRecord;
						btPRecord += dtPRecord;
						dtPlantRai = 0;
						dtPlantNgan = 0;
						dtPlantWah = 0;
						dtPlantNgan = 0;
						dtPlantWah = 0;
						dtFRecord = 0;
						dtPRecord = 0;
					}

					if (!branchTmp
							.equals(((Object[]) provList.get(prov + 1))[0]
									.toString())) {
						calRai = totalRai(btPlantRai, btPlantNgan, btPlantWah);
						String[] totalBran = new String[] {
								String.format("%,d-%d-%d|", calRai[0],
										calRai[1], calRai[2]),
								String.format("%,.2f", btFRecord),
								String.format("%,.2f", btPRecord) };
						if (linePage < table.size()) {
							if (table.size() > 1) {
								document.add(title);
							}
							document.add(table);

							document.newPage();
							document.add(head);
							document.add(head_date);
							table = new Table(1);
							table.setAlignment(Cell.ALIGN_CENTER);
							table.setWidth(100);
							table.setPadding(2f);
							table.insertTable(headTable(newRows(colWidth),
									font_body, headTable));
							title.setLeading(5f);
							title.setSpacingAfter(0f);
							newPage = true;
							linePage = 21;
						}
						table.insertTable(totalTable(colTotalWidth, font_total,
								"ยอดรวมสาขา " + branchTmp, totalBran));
						ptPlantRai += btPlantRai;
						ptPlantNgan += btPlantNgan;
						ptPlantWah += btPlantWah;
						ptFRecord += btFRecord;
						ptPRecord += btPRecord;
						btPlantRai = 0;
						btPlantNgan = 0;
						btPlantWah = 0;
						btFRecord = 0;
						btPRecord = 0;

						if (!provinceTmp.equals(((Object[]) provList
								.get(prov + 1))[14].toString())) {
							calRai = totalRai(ptPlantRai, ptPlantNgan,
									ptPlantWah);
							String[] totalProv = new String[] {
									String.format("%,d-%d-%d|", calRai[0],
											calRai[1], calRai[2]),
									String.format("%,.2f", ptFRecord),
									String.format("%,.2f", ptPRecord) };
							if (linePage < table.size()) {
								if (table.size() > 1) {
									document.add(title);
								}
								document.add(table);

								document.newPage();
								document.add(head);
								document.add(head_date);
								table = new Table(1);
								table.setAlignment(Cell.ALIGN_CENTER);
								table.setWidth(100);
								table.setPadding(2f);
								table.insertTable(headTable(newRows(colWidth),
										font_body, headTable));
								title.setLeading(5f);
								title.setSpacingAfter(0f);
								newPage = true;
								linePage = 21;
							}
							table.insertTable(totalTable(colTotalWidth,
									font_total, "ยอดรวมจังหวัด "
											+ obj[14].toString(), totalProv));

							ptPlantRai = 0;
							ptPlantNgan = 0;
							ptPlantWah = 0;
							ptFRecord = 0;
							ptPRecord = 0;
						}

						if (table.size() > 1) {
							document.add(title);
							document.add(table);
							linePage -= table.size();
							table = new Table(1);
							table.setAlignment(Cell.ALIGN_CENTER);
							table.setWidth(100);
							table.setPadding(2f);
						}
					}
				} else {
					calRai = totalRai(dtPlantRai, dtPlantNgan, dtPlantWah);
					String[] total = new String[] {
							String.format("%,d-%d-%d|", calRai[0], calRai[1],
									calRai[2]),
							String.format("%,.2f", dtFRecord),
							String.format("%,.2f", dtPRecord) };
					if (linePage < table.size()) {
						if (table.size() > 1) {
							document.add(title);
						}
						document.add(table);

						document.newPage();
						document.add(head);
						document.add(head_date);
						table = new Table(1);
						table.setAlignment(Cell.ALIGN_CENTER);
						table.setWidth(100);
						table.setPadding(2f);
						table.insertTable(headTable(newRows(colWidth),
								font_body, headTable));
						title.setLeading(5f);
						title.setSpacingAfter(0f);
						newPage = true;
						linePage = 21;
					}
					table.insertTable(totalTable(colTotalWidth, font_total,
							"รวม", total));
					btPlantRai += dtPlantRai;
					btPlantNgan += dtPlantNgan;
					btPlantWah += dtPlantWah;
					btFRecord += dtFRecord;
					btPRecord += dtPRecord;
					dtPlantRai = 0;
					dtPlantNgan = 0;
					dtPlantWah = 0;
					dtPlantNgan = 0;
					dtPlantWah = 0;
					dtFRecord = 0;
					dtPRecord = 0;

					calRai = totalRai(btPlantRai, btPlantNgan, btPlantWah);
					String[] totalBran = new String[] {
							String.format("%,d-%d-%d|", calRai[0], calRai[1],
									calRai[2]),
							String.format("%,.2f", btFRecord),
							String.format("%,.2f", btPRecord) };
					if (linePage < table.size()) {
						if (table.size() > 1) {
							document.add(title);
						}
						document.add(table);

						document.newPage();
						document.add(head);
						document.add(head_date);
						table = new Table(1);
						table.setAlignment(Cell.ALIGN_CENTER);
						table.setWidth(100);
						table.setPadding(2f);
						table.insertTable(headTable(newRows(colWidth),
								font_body, headTable));
						title.setLeading(5f);
						title.setSpacingAfter(0f);
						newPage = true;
						linePage = 21;
					}
					table.insertTable(totalTable(colTotalWidth, font_total,
							"ยอดรวมสาขา " + branchTmp, totalBran));
					ptPlantRai += btPlantRai;
					ptPlantNgan += btPlantNgan;
					ptPlantWah += btPlantWah;
					ptFRecord += btFRecord;
					ptPRecord += btPRecord;
					btPlantRai = 0;
					btPlantNgan = 0;
					btPlantWah = 0;
					btFRecord = 0;
					btPRecord = 0;

					calRai = totalRai(ptPlantRai, ptPlantNgan, ptPlantWah);
					String[] totalProv = new String[] {
							String.format("%,d-%d-%d|", calRai[0], calRai[1],
									calRai[2]),
							String.format("%,.2f", ptFRecord),
							String.format("%,.2f", ptPRecord) };
					if (linePage < table.size()) {
						if (table.size() > 1) {
							document.add(title);
						}
						document.add(table);

						document.newPage();
						document.add(head);
						document.add(head_date);
						table = new Table(1);
						table.setAlignment(Cell.ALIGN_CENTER);
						table.setWidth(100);
						table.setPadding(2f);
						table.insertTable(headTable(newRows(colWidth),
								font_body, headTable));
						title.setLeading(5f);
						title.setSpacingAfter(0f);
						newPage = true;
						linePage = 21;
					}
					table.insertTable(totalTable(colTotalWidth, font_total,
							"ยอดรวมจังหวัด " + obj[14].toString(), totalProv));

					document.add(title);
					document.add(table);
				}
			}
		} catch (Exception e) {
			document.add(new Paragraph("เกิดการดึงข้อมูลผิดพลาด", font_title));
			e.printStackTrace();
			log.error(e, e);
		} finally {
			sf.getCurrentSession().close();
		}
		return document;
	}

	private Document ReportR104_2(SessionFactory sf, Document document,
			Font font_title, Font font_body, Font font_total, ReportForm eform)
			throws DocumentException {
		try {
			sf.getCurrentSession().beginTransaction();
			Paragraph head = new Paragraph(
					"ข้อมูลการเปรียบเทียบประมาณการผลผลิตรวมกับผลผลิตรวมจริงที่ได้รับ ตามกลุ่มเกษตรกร มีเป้าหมาย "
							+ eform.getPlantYear() + "/" + eform.getPlantNo(),
					font_title);
			head.setAlignment(Element.ALIGN_CENTER);
			document.add(head);

			Paragraph head_date = new Paragraph("สิ้นสุด ณ วันที่ "
					+ dateThai(eform.getEffectiveDate(), false), font_title);
			head_date.setAlignment(Element.ALIGN_CENTER);
			head_date.setSpacingBefore(5f);
			document.add(head_date);

			Table table = new Table(1);
			table.setAlignment(Cell.ALIGN_CENTER);
			table.setWidth(100);
			table.setPadding(2f);
			float[] colWidth, colTotalWidth;
			colWidth = new float[] { 15, 15, 15, 25, 20, 20, 15, 15, 15 };
			colTotalWidth = new float[] { 110, 15, 15, 15 };
			String[] headTable = new String[] { "ชื่อกลุ่ม", "ตำบลที่ตั้งแปลง",
					"บัตรประจำตัว\nประชาชน\n(เลขที่)", "ชื่อ-สกุล",
					"กลุ่มพันธุ์", "|วันที่|เพาะปลูก|เก็บเกี่ยว",
					"เนื้อที่เพาะปลูก\nไร่-งาน-วา",
					"ประมาณการผล\nผลิตรวม\n(กก.)",
					"ผลผลิตรวมจริง\nที่ได้รับ\n(กก.)" };
			table
					.insertTable(headTable(newRows(colWidth), font_body,
							headTable));

			ReportHome rhome = new ReportHome();
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

			List provList = rhome.findR104_2ByCriteria(eform.getPlantYear(),
					eform.getPlantNo(), df.parse(eform.getEffectiveDate()),
					eform.getProvinceNo(), eform.getBranchCode(), eform
							.getBreedTypeId(), eform.getBreedGroupId());
			if (eform.getBranchCode() > 0) {
				BranchHome bhome = new BranchHome();
				List<Branch> pbList = bhome
						.findByPBranch(eform.getBranchCode());
				List<Branch> pbranchCode = new ArrayList();
				if (pbList.size() > 0) {
					pbranchCode
							.addAll(getPBranch(pbList, eform.getBranchCode()));
					for (int i = 0; i < pbranchCode.size(); i++) {
						provList.addAll(rhome.findR104_2ByCriteria(eform
								.getPlantYear(), eform.getPlantNo(), df
								.parse(eform.getEffectiveDate()), 0,
								pbranchCode.get(i).getBranchCode(), eform
										.getBreedTypeId(), eform
										.getBreedGroupId()));
					}
				}
			}

			String branchTmp = "", subDistrictTmp = "", provinceTmp = "", FarmerTmp = "", idCardTmp = "", breedTmp = "";
			int dtPlantRai = 0, dtPlantNgan = 0, dtPlantWah = 0;
			int btPlantRai = 0, btPlantNgan = 0, btPlantWah = 0;
			int ptPlantRai = 0, ptPlantNgan = 0, ptPlantWah = 0;
			float dtFRecord = 0, dtPRecord = 0;
			float btFRecord = 0, btPRecord = 0;
			float ptFRecord = 0, ptPRecord = 0;
			int linePage = 23;
			Paragraph title = new Paragraph();
			int target = 0;
			if (provList.size() <= 0) {
				document.add(table);
				title = new Paragraph("ไม่มีข้อมูล", font_title);
				title.setAlignment(Element.ALIGN_CENTER);
				document.add(title);
			} else {
				for (int i = 0; i < provList.size(); i++) {
					Object[] obj = ((Object[]) provList.get(i));
					target += Float.parseFloat(obj[13].toString());
				}
				title = new Paragraph(String.format(
						"เป้าหมายปริมาณผลผลิต %d ตัน", target / 1000),
						font_total);
				title.setAlignment(Element.ALIGN_RIGHT);
				title.setLeading(25f);
				title.setSpacingAfter(-25f);
				document.add(title);
			}
			boolean newPage = true;
			int frist = 1;
			for (int prov = 0; prov < provList.size(); prov++) {
				Object[] obj = ((Object[]) provList.get(prov));

				if (!branchTmp.equals(obj[0].toString())) {
					title = new Paragraph("ธ.ก.ส. จังหวัด" + obj[15].toString()
							+ " " + obj[0].toString(), font_total);
					title.setAlignment(Element.ALIGN_LEFT);
					title.setLeading(5f);
					title.setSpacingBefore(20f);
					branchTmp = obj[0].toString();
					linePage -= 2;
					newPage = true;
				}

				Table detail = new Table(colWidth.length);
				detail.setWidths(colWidth);
				detail.setAlignment(Cell.ALIGN_CENTER);
				detail.setWidth(100);
				detail.setPadding(2f);

				Cell sCell = null;

				if (linePage < table.size()) {
					if (table.size() > 1) {
						document.add(title);
					}
					document.add(table);

					document.newPage();
					document.add(head);
					document.add(head_date);
					table = new Table(1);
					table.setAlignment(Cell.ALIGN_CENTER);
					table.setWidth(100);
					table.setPadding(2f);
					table.insertTable(headTable(newRows(colWidth), font_body,
							headTable));
					title.setLeading(5f);
					title.setSpacingAfter(0f);
					newPage = true;
					linePage = 21;
				}

				if (FarmerTmp.equals(obj[1].toString()) && !newPage
						&& subDistrictTmp.equals(obj[2].toString())) {
					sCell = new Cell(new Phrase("", font_body));
					sCell.setBorderWidthTop(0);
					sCell.setBorderWidthBottom(0);
					sCell.setBorderWidthRight(0.1f);
					detail.addCell(sCell);
				} else {
					sCell = new Cell(new Phrase(obj[1].toString(), font_body));
					sCell.setHorizontalAlignment(Cell.ALIGN_LEFT);
					sCell.setVerticalAlignment(Cell.ALIGN_TOP);
					sCell.setBorderColorTop(Color.GRAY);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
					sCell.setBorderWidthBottom(0);
					sCell.setBorderWidthRight(0.1f);
					detail.addCell(sCell);
					FarmerTmp = obj[1].toString();
					newPage = true;
				}

				if (subDistrictTmp.equals(obj[2].toString()) && !newPage) {
					sCell = new Cell(new Phrase("", font_body));
					sCell.setBorderWidthTop(0);
					sCell.setBorderWidthBottom(0);
					sCell.setBorderWidthRight(0.1f);
					detail.addCell(sCell);

				} else {
					sCell = new Cell(new Phrase(obj[2].toString(), font_body));
					sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
					sCell.setVerticalAlignment(Cell.ALIGN_TOP);
					sCell.setBorderColorTop(Color.GRAY);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
					sCell.setBorderWidthBottom(0);
					sCell.setBorderWidthRight(0.1f);
					detail.addCell(sCell);
				}
				if (idCardTmp.equals(obj[3].toString()) && !newPage) {
					sCell = new Cell(new Phrase("", font_body));
					sCell.setBorderWidthTop(0);
					sCell.setBorderWidthBottom(0);
					sCell.setBorderWidthRight(0.1f);
					detail.addCell(sCell);
					detail.addCell(sCell);
					if (breedTmp.equals(obj[7].toString())) {
						detail.addCell(sCell);
					} else {
						sCell = new Cell(new Phrase(obj[7].toString(),
								font_body));
						sCell.setHorizontalAlignment(Cell.ALIGN_LEFT);
						sCell.setVerticalAlignment(Cell.ALIGN_TOP);
						sCell.setBorderColorTop(Color.GRAY);
						sCell.setBorderWidthTop(0.1f);
						sCell.setBorderWidthRight(0.1f);
						sCell.setBorderWidthTop(0.1f);
						sCell.setBorderWidthRight(0.1f);
						sCell.setBorderWidthBottom(0);
						sCell.setBorderWidthRight(0.1f);
						detail.addCell(sCell);
						breedTmp = obj[7].toString();
					}
				} else {
					sCell = new Cell(new Phrase(obj[3].toString(), font_body));
					sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
					sCell.setVerticalAlignment(Cell.ALIGN_TOP);
					sCell.setBorderColorTop(Color.GRAY);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
					sCell.setBorderWidthBottom(0);
					sCell.setBorderWidthRight(0.1f);
					detail.addCell(sCell);

					sCell = new Cell(new Phrase(" " + obj[4].toString()
							+ obj[5].toString() + " " + obj[6].toString(),
							font_body));
					sCell.setHorizontalAlignment(Cell.ALIGN_LEFT);
					sCell.setVerticalAlignment(Cell.ALIGN_TOP);
					sCell.setBorderColorTop(Color.GRAY);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
					sCell.setBorderWidthBottom(0);
					sCell.setBorderWidthRight(0.1f);
					detail.addCell(sCell);

					idCardTmp = obj[3].toString();

					sCell = new Cell(new Phrase(obj[7].toString(), font_body));
					sCell.setHorizontalAlignment(Cell.ALIGN_LEFT);
					sCell.setVerticalAlignment(Cell.ALIGN_TOP);
					sCell.setBorderColorTop(Color.GRAY);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
					sCell.setBorderWidthBottom(0);
					sCell.setBorderWidthRight(0.1f);
					detail.addCell(sCell);
					breedTmp = obj[7].toString();
					newPage = false;
				}

				Table tmp = new Table(2);
				sCell = new Cell(new Phrase(convertDate(obj[8].toString()),
						font_body));
				sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
				sCell.setVerticalAlignment(Cell.ALIGN_TOP);
				sCell.setBorderColorTop(Color.GRAY);
				sCell.setBorderWidthTop(0.1f);
				sCell.setBorderWidthRight(0.1f);
				tmp.addCell(sCell);

				if (obj[9] != null) {
					sCell = new Cell(new Phrase(convertDate(obj[9].toString()),
							font_body));
					sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
					sCell.setVerticalAlignment(Cell.ALIGN_TOP);
					sCell.setBorderColorTop(Color.GRAY);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
				} else {
					sCell = new Cell(new Phrase(" ", font_body));
					sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
					sCell.setVerticalAlignment(Cell.ALIGN_TOP);
					sCell.setBorderColorTop(Color.GRAY);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
				}
				tmp.addCell(sCell);
				detail.insertTable(tmp);

				// tmp = new Table(3);
				int rai = Integer.parseInt(obj[10].toString());
				int ngan = Integer.parseInt(obj[11].toString());
				int wah = Integer.parseInt(obj[12].toString());

				if (wah / 100 > 0) {
					ngan += wah / 100;
					wah = wah % 100;

				}
				if (ngan / 4 > 0) {
					rai += ngan / 4;
					ngan = ngan % 4;
				}

				sCell = new Cell(new Phrase(String.format("%,d-%d-%d", rai,
						ngan, wah), font_body));
				sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
				sCell.setVerticalAlignment(Cell.ALIGN_TOP);
				sCell.setBorderColorTop(Color.GRAY);
				sCell.setBorderWidthTop(0.1f);
				sCell.setBorderWidthRight(0.1f);
				detail.addCell(sCell);

				sCell = new Cell(new Phrase(String.format("%,.2f", Float
						.parseFloat(obj[13].toString())), font_body));
				sCell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
				sCell.setVerticalAlignment(Cell.ALIGN_TOP);
				sCell.setBorderColorTop(Color.GRAY);
				sCell.setBorderWidthTop(0.1f);
				sCell.setBorderWidthRight(0.1f);
				detail.addCell(sCell);

				if (obj[14] != null) {
					sCell = new Cell(new Phrase(String.format("%,.2f", Float
							.parseFloat(obj[14].toString())), font_body));
					sCell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
					sCell.setVerticalAlignment(Cell.ALIGN_TOP);
					sCell.setBorderColorTop(Color.GRAY);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
				} else {
					sCell = new Cell(new Phrase(String.format("%,.2f", 0.00f),
							font_body));
					sCell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
					sCell.setVerticalAlignment(Cell.ALIGN_TOP);
					sCell.setBorderColorTop(Color.GRAY);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
				}

				detail.addCell(sCell);

				table.insertTable(detail);

				dtPlantRai += rai;
				dtPlantNgan += ngan;
				dtPlantWah += wah;
				dtFRecord += Float.parseFloat(obj[13].toString());
				if (obj[14] != null) {
					dtPRecord += Float.parseFloat(obj[14].toString());
				}
				subDistrictTmp = obj[2].toString();
				provinceTmp = obj[15].toString();

				if (prov + 1 != provList.size()) {
					if (!subDistrictTmp.equals(((Object[]) provList
							.get(prov + 1))[2].toString())) {
						calRai = totalRai(dtPlantRai, dtPlantNgan, dtPlantWah);
						String[] total = new String[] {
								String.format("%,d-%d-%d|", calRai[0],
										calRai[1], calRai[2]),
								String.format("%,.2f", dtFRecord),
								String.format("%,.2f", dtPRecord) };
						if (linePage < table.size()) {
							if (table.size() > 1) {
								document.add(title);
							}
							document.add(table);

							document.newPage();
							document.add(head);
							document.add(head_date);
							table = new Table(1);
							table.setAlignment(Cell.ALIGN_CENTER);
							table.setWidth(100);
							table.setPadding(2f);
							table.insertTable(headTable(newRows(colWidth),
									font_body, headTable));
							title.setLeading(5f);
							title.setSpacingAfter(0f);
							newPage = true;
							linePage = 21;
						}
						table.insertTable(totalTable(colTotalWidth, font_total,
								"รวม", total));
						btPlantRai += dtPlantRai;
						btPlantNgan += dtPlantNgan;
						btPlantWah += dtPlantWah;
						btFRecord += dtFRecord;
						btPRecord += dtPRecord;
						dtPlantRai = 0;
						dtPlantNgan = 0;
						dtPlantWah = 0;
						dtPlantNgan = 0;
						dtPlantWah = 0;
						dtFRecord = 0;
						dtPRecord = 0;
					}

					if (!branchTmp
							.equals(((Object[]) provList.get(prov + 1))[0]
									.toString())) {
						calRai = totalRai(btPlantRai, btPlantNgan, btPlantWah);
						String[] totalBran = new String[] {
								String.format("%,d-%d-%d|", calRai[0],
										calRai[1], calRai[2]),
								String.format("%,.2f", btFRecord),
								String.format("%,.2f", btPRecord) };
						if (linePage < table.size()) {
							if (table.size() > 1) {
								document.add(title);
							}
							document.add(table);

							document.newPage();
							document.add(head);
							document.add(head_date);
							table = new Table(1);
							table.setAlignment(Cell.ALIGN_CENTER);
							table.setWidth(100);
							table.setPadding(2f);
							table.insertTable(headTable(newRows(colWidth),
									font_body, headTable));
							title.setLeading(5f);
							title.setSpacingAfter(0f);
							newPage = true;
							linePage = 21;
						}
						table.insertTable(totalTable(colTotalWidth, font_total,
								"ยอดรวมสาขา " + branchTmp, totalBran));
						ptPlantRai += btPlantRai;
						ptPlantNgan += btPlantNgan;
						ptPlantWah += btPlantWah;
						ptFRecord += btFRecord;
						ptPRecord += btPRecord;
						btPlantRai = 0;
						btPlantNgan = 0;
						btPlantWah = 0;
						btFRecord = 0;
						btPRecord = 0;

						if (!provinceTmp.equals(((Object[]) provList
								.get(prov + 1))[15].toString())) {
							calRai = totalRai(ptPlantRai, ptPlantNgan,
									ptPlantWah);
							String[] totalProv = new String[] {
									String.format("%,d-%d-%d|", calRai[0],
											calRai[1], calRai[2]),
									String.format("%,.2f", ptFRecord),
									String.format("%,.2f", ptPRecord) };
							if (linePage < table.size()) {
								if (table.size() > 1) {
									document.add(title);
								}
								document.add(table);

								document.newPage();
								document.add(head);
								document.add(head_date);
								table = new Table(1);
								table.setAlignment(Cell.ALIGN_CENTER);
								table.setWidth(100);
								table.setPadding(2f);
								table.insertTable(headTable(newRows(colWidth),
										font_body, headTable));
								title.setLeading(5f);
								title.setSpacingAfter(0f);
								newPage = true;
								linePage = 21;
							}
							table.insertTable(totalTable(colTotalWidth,
									font_total, "ยอดรวมจังหวัด "
											+ obj[15].toString(), totalProv));
							ptPlantRai = 0;
							ptPlantNgan = 0;
							ptPlantWah = 0;
							ptFRecord = 0;
							ptPRecord = 0;
						}

						if (table.size() > 1) {
							document.add(title);
							document.add(table);
							linePage -= table.size();
							table = new Table(1);
							table.setAlignment(Cell.ALIGN_CENTER);
							table.setWidth(100);
							table.setPadding(2f);
						}
					}
				} else {
					calRai = totalRai(dtPlantRai, dtPlantNgan, dtPlantWah);
					String[] total = new String[] {
							String.format("%,d-%d-%d|", calRai[0], calRai[1],
									calRai[2]),
							String.format("%,.2f", dtFRecord),
							String.format("%,.2f", dtPRecord) };
					if (linePage < table.size()) {
						if (table.size() > 1) {
							document.add(title);
						}
						document.add(table);

						document.newPage();
						document.add(head);
						document.add(head_date);
						table = new Table(1);
						table.setAlignment(Cell.ALIGN_CENTER);
						table.setWidth(100);
						table.setPadding(2f);
						table.insertTable(headTable(newRows(colWidth),
								font_body, headTable));
						title.setLeading(5f);
						title.setSpacingAfter(0f);
						newPage = true;
						linePage = 21;
					}
					table.insertTable(totalTable(colTotalWidth, font_total,
							"รวม", total));
					btPlantRai += dtPlantRai;
					btPlantNgan += dtPlantNgan;
					btPlantWah += dtPlantWah;
					btFRecord += dtFRecord;
					btPRecord += dtPRecord;
					dtPlantRai = 0;
					dtPlantNgan = 0;
					dtPlantWah = 0;
					dtPlantNgan = 0;
					dtPlantWah = 0;
					dtFRecord = 0;
					dtPRecord = 0;
					calRai = totalRai(btPlantRai, btPlantNgan, btPlantWah);
					String[] totalBran = new String[] {
							String.format("%,d-%d-%d|", calRai[0], calRai[1],
									calRai[2]),
							String.format("%,.2f", btFRecord),
							String.format("%,.2f", btPRecord) };
					if (linePage < table.size()) {
						if (table.size() > 1) {
							document.add(title);
						}
						document.add(table);

						document.newPage();
						document.add(head);
						document.add(head_date);
						table = new Table(1);
						table.setAlignment(Cell.ALIGN_CENTER);
						table.setWidth(100);
						table.setPadding(2f);
						table.insertTable(headTable(newRows(colWidth),
								font_body, headTable));
						title.setLeading(5f);
						title.setSpacingAfter(0f);
						newPage = true;
						linePage = 21;
					}
					table.insertTable(totalTable(colTotalWidth, font_total,
							"ยอดรวมสาขา " + branchTmp, totalBran));
					ptPlantRai += btPlantRai;
					ptPlantNgan += btPlantNgan;
					ptPlantWah += btPlantWah;
					ptFRecord += btFRecord;
					ptPRecord += btPRecord;
					btPlantRai = 0;
					btPlantNgan = 0;
					btPlantWah = 0;
					btFRecord = 0;
					btPRecord = 0;
					calRai = totalRai(ptPlantRai, ptPlantNgan, ptPlantWah);
					String[] totalProv = new String[] {
							String.format("%,d-%d-%d|", calRai[0], calRai[1],
									calRai[2]),
							String.format("%,.2f", ptFRecord),
							String.format("%,.2f", ptPRecord) };
					if (linePage < table.size()) {
						if (table.size() > 1) {
							document.add(title);
						}
						document.add(table);

						document.newPage();
						document.add(head);
						document.add(head_date);
						table = new Table(1);
						table.setAlignment(Cell.ALIGN_CENTER);
						table.setWidth(100);
						table.setPadding(2f);
						table.insertTable(headTable(newRows(colWidth),
								font_body, headTable));
						title.setLeading(5f);
						title.setSpacingAfter(0f);
						newPage = true;
						linePage = 21;
					}
					table.insertTable(totalTable(colTotalWidth, font_total,
							"ยอดรวมจังหวัด " + obj[15].toString(), totalProv));

					document.add(title);
					document.add(table);
				}
			}

		} catch (Exception e) {
			document.add(new Paragraph("เกิดการดึงข้อมูลผิดพลาด", font_title));
			e.printStackTrace();
			log.error(e, e);
		} finally {
			sf.getCurrentSession().close();
		}
		return document;
	}

	private Document ReportR104_3(SessionFactory sf, Document document,
			Font font_title, Font font_body, Font font_total, ReportForm eform)
			throws DocumentException {
		try {
			sf.getCurrentSession().beginTransaction();
			Paragraph head = new Paragraph(
					"ข้อมูลการเปรียบเทียบประมาณการผลผลิตรวมกับผลผลิตรวมจริงที่ได้รับ ตามปีการผลิต "
							+ eform.getPlantYear() + "/" + eform.getPlantNo(),
					font_title);
			head.setAlignment(Element.ALIGN_CENTER);
			document.add(head);

			Paragraph head_date = new Paragraph("สิ้นสุด ณ วันที่ "
					+ dateThai(eform.getEffectiveDate(), false), font_title);
			head_date.setAlignment(Element.ALIGN_CENTER);
			head_date.setSpacingBefore(5f);
			document.add(head_date);

			Table table = new Table(1);
			table.setAlignment(Cell.ALIGN_CENTER);
			table.setWidth(100);
			table.setPadding(2f);
			float[] colWidth, colTotalWidth;
			colWidth = new float[] { 15, 20, 15, 20, 20, 20, 15, 15, 15 };
			colTotalWidth = new float[] { 110, 15, 15, 15 };
			String[] headTable = new String[] { "ชื่อกลุ่ม",
					"|ที่ตั้งแปลง|อำเภอ|ตำบล",
					"บัตรประจำตัว\nประชาชน\n(เลขที่)", "ชื่อ-สกุล",
					"กลุ่มพันธุ์", "|วันที่|เพาะปลูก|เก็บเกี่ยว",
					"เนื้อที่เพาะปลูก\nไร่-งาน-วา",
					"ประมาณการผล\nผลิตรวม\n(กก.)",
					"ผลผลิตรวมจริง\nที่ได้รับ\n(กก.)" };
			table
					.insertTable(headTable(newRows(colWidth), font_body,
							headTable));

			ReportHome rhome = new ReportHome();
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

			List provList = rhome.findR104_3ByCriteria(eform.getPlantYear(),
					eform.getPlantNo(), df.parse(eform.getEffectiveDate()),
					eform.getProvinceNo(), eform.getBranchCode(), eform
							.getBreedTypeId(), eform.getBreedGroupId());
			if (eform.getBranchCode() > 0) {
				BranchHome bhome = new BranchHome();
				List<Branch> pbList = bhome
						.findByPBranch(eform.getBranchCode());
				List<Branch> pbranchCode = new ArrayList();
				if (pbList.size() > 0) {
					pbranchCode
							.addAll(getPBranch(pbList, eform.getBranchCode()));
					for (int i = 0; i < pbranchCode.size(); i++) {
						provList.addAll(rhome.findR104_3ByCriteria(eform
								.getPlantYear(), eform.getPlantNo(), df
								.parse(eform.getEffectiveDate()), 0,
								pbranchCode.get(i).getBranchCode(), eform
										.getBreedTypeId(), eform
										.getBreedGroupId()));
					}
				}
			}

			String branchTmp = "", subDistrictTmp = "", districtTmp = "", provinceTmp = "", idCardTmp = "", breedTmp = "", farmerTmp = "";
			int dtPlantRai = 0, dtPlantNgan = 0, dtPlantWah = 0;
			int btPlantRai = 0, btPlantNgan = 0, btPlantWah = 0;
			int ptPlantRai = 0, ptPlantNgan = 0, ptPlantWah = 0;
			float dtFRecord = 0, dtPRecord = 0;
			float btFRecord = 0, btPRecord = 0;
			float ptFRecord = 0, ptPRecord = 0;
			int linePage = 23;
			Paragraph title = new Paragraph();
			if (provList.size() <= 0) {
				document.add(table);
				title = new Paragraph("ไม่มีข้อมูล", font_title);
				title.setAlignment(Element.ALIGN_CENTER);
				document.add(title);
			} else {
				int target = 0;
				for (int i = 0; i < provList.size(); i++) {
					Object[] obj = ((Object[]) provList.get(i));
					target += Float.parseFloat(obj[13].toString());
				}
				title = new Paragraph(String.format(
						"การรวมกลุ่ม(หลายอำเภอ)เป้าหมายปริมาณผลผลิต %d ตัน", target / 1000),
						font_total);
				title.setAlignment(Element.ALIGN_RIGHT);
				title.setLeading(25f);
				title.setSpacingAfter(-25f);
				document.add(title);
			}
			boolean newPage = true;
			int frist = 1;
			for (int prov = 0; prov < provList.size(); prov++) {
				Object[] obj = ((Object[]) provList.get(prov));

				if (!branchTmp.equals(obj[0].toString())) {
					title = new Paragraph("ธ.ก.ส. จังหวัด" + obj[15].toString()
							+ " " + obj[0].toString(), font_total);
					title.setAlignment(Element.ALIGN_LEFT);
					title.setLeading(5f);
					title.setSpacingBefore(20f);
					branchTmp = obj[0].toString();
					linePage -= 2;
					newPage = true;
				}

				Table detail = new Table(colWidth.length);
				detail.setWidths(colWidth);
				detail.setAlignment(Cell.ALIGN_CENTER);
				detail.setWidth(100);
				detail.setPadding(2f);
				Cell sCell = null;

				if (linePage < table.size()) {
					if (table.size() > 1) {
						document.add(title);
					}
					document.add(table);

					document.newPage();
					document.add(head);
					document.add(head_date);
					table = new Table(1);
					table.setAlignment(Cell.ALIGN_CENTER);
					table.setWidth(100);
					table.setPadding(2f);
					table.insertTable(headTable(newRows(colWidth), font_body,
							headTable));
					title.setLeading(5f);
					title.setSpacingAfter(0f);
					newPage = true;
					linePage = 21;
				}

				if (farmerTmp.equals(obj[1].toString()) && !newPage) {
					sCell = new Cell(new Phrase(" ", font_body));
					sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
					sCell.setVerticalAlignment(Cell.ALIGN_TOP);
					sCell.setBorderColorTop(Color.GRAY);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
					sCell.setBorderWidthTop(0);
					sCell.setBorderWidthBottom(0);
					sCell.setBorderWidthRight(0.1f);
					detail.addCell(sCell);
				} else {
					sCell = new Cell(new Phrase(obj[1].toString(), font_body));
					sCell.setHorizontalAlignment(Cell.ALIGN_LEFT);
					sCell.setVerticalAlignment(Cell.ALIGN_TOP);
					sCell.setBorderColorTop(Color.GRAY);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
					sCell.setBorderWidthBottom(0);
					sCell.setBorderWidthRight(0.1f);
					detail.addCell(sCell);
					newPage = true;
					farmerTmp = obj[1].toString();
				}

				if (!subDistrictTmp.equals(obj[2].toString()) || newPage) {
					Table dist = new Table(2);
					if (districtTmp.equals(obj[23].toString()) && !newPage) {
						sCell = new Cell(new Phrase(" ", font_body));
						sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
						sCell.setVerticalAlignment(Cell.ALIGN_TOP);
						sCell.setBorderColorTop(Color.GRAY);
						sCell.setBorderWidthTop(0.1f);
						sCell.setBorderWidthRight(0.1f);
						sCell.setBorderWidthTop(0);
						sCell.setBorderWidthBottom(0);
						sCell.setBorderWidthRight(0.1f);
						dist.addCell(sCell);
					} else {
						sCell = new Cell(new Phrase(obj[23].toString(),
								font_body));
						sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
						sCell.setVerticalAlignment(Cell.ALIGN_TOP);
						sCell.setBorderColorTop(Color.GRAY);
						sCell.setBorderWidthTop(0.1f);
						sCell.setBorderWidthRight(0.1f);
						sCell.setBorderWidthTop(0.1f);
						sCell.setBorderWidthRight(0.1f);
						sCell.setBorderWidthBottom(0);
						sCell.setBorderWidthRight(0.1f);
						dist.addCell(sCell);
						districtTmp = obj[23].toString();
					}
					sCell = new Cell(new Phrase(obj[2].toString(), font_body));
					sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
					sCell.setVerticalAlignment(Cell.ALIGN_TOP);
					sCell.setBorderColorTop(Color.GRAY);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
					sCell.setBorderWidthBottom(0);
					sCell.setBorderWidthRight(0.1f);
					dist.addCell(sCell);
					detail.insertTable(dist);
				} else {
					Table dist = new Table(2);

					sCell = new Cell(new Phrase(" ", font_body));
					sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
					sCell.setVerticalAlignment(Cell.ALIGN_TOP);
					sCell.setBorderColorTop(Color.GRAY);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
					sCell.setBorderWidthTop(0);
					sCell.setBorderWidthBottom(0);
					sCell.setBorderWidthRight(0.1f);
					dist.addCell(sCell);
					dist.addCell(sCell);
					detail.insertTable(dist);
				}

				if (idCardTmp.equals(obj[3].toString()) && !newPage) {
					sCell = new Cell(new Phrase(" ", font_body));
					sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
					sCell.setVerticalAlignment(Cell.ALIGN_TOP);
					sCell.setBorderColorTop(Color.GRAY);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
					sCell.setBorderWidthTop(0);
					sCell.setBorderWidthBottom(0);
					sCell.setBorderWidthRight(0.1f);
					detail.addCell(sCell);
					detail.addCell(sCell);
					if (breedTmp.equals(obj[7].toString())) {
						detail.addCell(sCell);
					} else {
						sCell = new Cell(new Phrase(obj[7].toString(),
								font_body));
						sCell.setHorizontalAlignment(Cell.ALIGN_LEFT);
						sCell.setVerticalAlignment(Cell.ALIGN_TOP);
						sCell.setBorderColorTop(Color.GRAY);
						sCell.setBorderWidthTop(0.1f);
						sCell.setBorderWidthRight(0.1f);
						sCell.setBorderWidthTop(0.1f);
						sCell.setBorderWidthRight(0.1f);
						sCell.setBorderWidthBottom(0);
						sCell.setBorderWidthRight(0.1f);
						detail.addCell(sCell);
						breedTmp = obj[7].toString();
					}
				} else {
					sCell = new Cell(new Phrase(obj[3].toString(), font_body));
					sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
					sCell.setVerticalAlignment(Cell.ALIGN_TOP);
					sCell.setBorderColorTop(Color.GRAY);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
					sCell.setBorderWidthBottom(0);
					sCell.setBorderWidthRight(0.1f);
					detail.addCell(sCell);

					sCell = new Cell(new Phrase(" " + obj[4].toString()
							+ obj[5].toString() + " " + obj[6].toString(),
							font_body));
					sCell.setHorizontalAlignment(Cell.ALIGN_LEFT);
					sCell.setVerticalAlignment(Cell.ALIGN_TOP);
					sCell.setBorderColorTop(Color.GRAY);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
					sCell.setBorderWidthBottom(0);
					sCell.setBorderWidthRight(0.1f);
					detail.addCell(sCell);
					idCardTmp = obj[3].toString();

					sCell = new Cell(new Phrase(obj[7].toString(), font_body));
					sCell.setHorizontalAlignment(Cell.ALIGN_LEFT);
					sCell.setVerticalAlignment(Cell.ALIGN_TOP);
					sCell.setBorderColorTop(Color.GRAY);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
					sCell.setBorderWidthBottom(0);
					sCell.setBorderWidthRight(0.1f);
					detail.addCell(sCell);
					breedTmp = obj[7].toString();

					newPage = false;
				}

				Table tmp = new Table(2);
				sCell = new Cell(new Phrase(convertDate(obj[8].toString()),
						font_body));
				sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
				sCell.setVerticalAlignment(Cell.ALIGN_TOP);
				sCell.setBorderColorTop(Color.GRAY);
				sCell.setBorderWidthTop(0.1f);
				sCell.setBorderWidthRight(0.1f);
				tmp.addCell(sCell);

				if (obj[9] != null) {
					sCell = new Cell(new Phrase(convertDate(obj[9].toString()),
							font_body));
					sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
					sCell.setVerticalAlignment(Cell.ALIGN_TOP);
					sCell.setBorderColorTop(Color.GRAY);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
				} else {
					sCell = new Cell(new Phrase(" ", font_body));
					sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
					sCell.setVerticalAlignment(Cell.ALIGN_TOP);
					sCell.setBorderColorTop(Color.GRAY);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
				}

				tmp.addCell(sCell);
				detail.insertTable(tmp);

				// tmp = new Table(3);
				int rai = Integer.parseInt(obj[10].toString());
				int ngan = Integer.parseInt(obj[11].toString());
				int wah = Integer.parseInt(obj[12].toString());

				if (wah / 100 > 0) {
					ngan += wah / 100;
					wah = wah % 100;

				}
				if (ngan / 4 > 0) {
					rai += ngan / 4;
					ngan = ngan % 4;
				}

				sCell = new Cell(new Phrase(String.format("%,d-%d-%d", rai,
						ngan, wah), font_body));
				sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
				sCell.setVerticalAlignment(Cell.ALIGN_TOP);
				sCell.setBorderColorTop(Color.GRAY);
				sCell.setBorderWidthTop(0.1f);
				sCell.setBorderWidthRight(0.1f);
				detail.addCell(sCell);

				sCell = new Cell(new Phrase(String.format("%,.2f", Float
						.parseFloat(obj[13].toString())), font_body));
				sCell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
				sCell.setVerticalAlignment(Cell.ALIGN_TOP);
				sCell.setBorderColorTop(Color.GRAY);
				sCell.setBorderWidthTop(0.1f);
				sCell.setBorderWidthRight(0.1f);
				detail.addCell(sCell);

				if (obj[14] != null) {
					sCell = new Cell(new Phrase(String.format("%,.2f", Float
							.parseFloat(obj[14].toString())), font_body));
					sCell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
					sCell.setVerticalAlignment(Cell.ALIGN_TOP);
					sCell.setBorderColorTop(Color.GRAY);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
				} else {
					sCell = new Cell(new Phrase(String.format("%,.2f", 0.00f),
							font_body));
					sCell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
					sCell.setVerticalAlignment(Cell.ALIGN_TOP);
					sCell.setBorderColorTop(Color.GRAY);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
				}

				detail.addCell(sCell);

				table.insertTable(detail);

				dtPlantRai += rai;
				dtPlantNgan += ngan;
				dtPlantWah += wah;
				dtFRecord += Float.parseFloat(obj[13].toString());
				if (obj[14] != null) {
					dtPRecord += Float.parseFloat(obj[14].toString());
				}
				subDistrictTmp = obj[2].toString();
				provinceTmp = obj[15].toString();

				if (prov + 1 != provList.size()) {
					if (!farmerTmp.equals(((Object[]) provList
							.get(prov + 1))[1].toString())) {
						calRai = totalRai(dtPlantRai, dtPlantNgan, dtPlantWah);
						String[] totalFarm = new String[] {
								String.format("%,d-%d-%d|", calRai[0], calRai[1],
										calRai[2]),
								String.format("%,.2f", dtFRecord),
								String.format("%,.2f", dtPRecord) };
						if (linePage < table.size()) {
							if (table.size() > 1) {
								document.add(title);
							}
							document.add(table);

							document.newPage();
							document.add(head);
							document.add(head_date);
							table = new Table(1);
							table.setAlignment(Cell.ALIGN_CENTER);
							table.setWidth(100);
							table.setPadding(2f);
							table.insertTable(headTable(newRows(colWidth),
									font_body, headTable));
							title.setLeading(5f);
							title.setSpacingAfter(0f);
							newPage = true;
							linePage = 21;
						}
						table.insertTable(totalTable(colTotalWidth, font_total,
								"รวมกลุ่ม" + farmerTmp, totalFarm));
						btPlantRai += dtPlantRai;
						btPlantNgan += dtPlantNgan;
						btPlantWah += dtPlantWah;
						btFRecord += dtFRecord;
						btPRecord += dtPRecord;
						dtPlantRai = 0;
						dtPlantNgan = 0;
						dtPlantWah = 0;
						dtPlantNgan = 0;
						dtPlantWah = 0;
						dtFRecord = 0;
						dtPRecord = 0;
					}

					if (!branchTmp
							.equals(((Object[]) provList.get(prov + 1))[0]
									.toString())) {
						if (!farmerTmp.equals(((Object[]) provList
								.get(prov))[1].toString())) {
							calRai = totalRai(dtPlantRai, dtPlantNgan, dtPlantWah);
							String[] totalFarm = new String[] {
									String.format("%,d-%d-%d|", calRai[0], calRai[1],
											calRai[2]),
									String.format("%,.2f", dtFRecord),
									String.format("%,.2f", dtPRecord) };
							if (linePage < table.size()) {
								if (table.size() > 1) {
									document.add(title);
								}
								document.add(table);

								document.newPage();
								document.add(head);
								document.add(head_date);
								table = new Table(1);
								table.setAlignment(Cell.ALIGN_CENTER);
								table.setWidth(100);
								table.setPadding(2f);
								table.insertTable(headTable(newRows(colWidth),
										font_body, headTable));
								title.setLeading(5f);
								title.setSpacingAfter(0f);
								newPage = true;
								linePage = 21;
							}
							table.insertTable(totalTable(colTotalWidth, font_total,
									"รวมกลุ่ม" + farmerTmp, totalFarm));
							btPlantRai += dtPlantRai;
							btPlantNgan += dtPlantNgan;
							btPlantWah += dtPlantWah;
							btFRecord += dtFRecord;
							btPRecord += dtPRecord;
							dtPlantRai = 0;
							dtPlantNgan = 0;
							dtPlantWah = 0;
							dtPlantNgan = 0;
							dtPlantWah = 0;
							dtFRecord = 0;
							dtPRecord = 0;
						}
						calRai = totalRai(btPlantRai, btPlantNgan, btPlantWah);
						String[] totalBran = new String[] {
								String.format("%,d-%d-%d|", calRai[0],
										calRai[1], calRai[2]),
								String.format("%,.2f", btFRecord),
								String.format("%,.2f", btPRecord) };
						if (linePage < table.size()) {
							if (table.size() > 1) {
								document.add(title);
							}
							document.add(table);

							document.newPage();
							document.add(head);
							document.add(head_date);
							table = new Table(1);
							table.setAlignment(Cell.ALIGN_CENTER);
							table.setWidth(100);
							table.setPadding(2f);
							table.insertTable(headTable(newRows(colWidth),
									font_body, headTable));
							title.setLeading(5f);
							title.setSpacingAfter(0f);
							newPage = true;
							linePage = 21;
						}
						table.insertTable(totalTable(colTotalWidth, font_total,
								"ยอดรวมสาขา " + branchTmp, totalBran));
						ptPlantRai += btPlantRai;
						ptPlantNgan += btPlantNgan;
						ptPlantWah += btPlantWah;
						ptFRecord += btFRecord;
						ptPRecord += btPRecord;
						btPlantRai = 0;
						btPlantNgan = 0;
						btPlantWah = 0;
						btFRecord = 0;
						btPRecord = 0;

						if (!provinceTmp.equals(((Object[]) provList
								.get(prov + 1))[15].toString())) {
							calRai = totalRai(btPlantRai, btPlantNgan,
									btPlantWah);
							String[] totalProv = new String[] {
									String.format("%,d-%d-%d|", calRai[0],
											calRai[1], calRai[2]),
									String.format("%,.2f", ptFRecord),
									String.format("%,.2f", ptPRecord) };
							if (linePage < table.size()) {
								if (table.size() > 1) {
									document.add(title);
								}
								document.add(table);

								document.newPage();
								document.add(head);
								document.add(head_date);
								table = new Table(1);
								table.setAlignment(Cell.ALIGN_CENTER);
								table.setWidth(100);
								table.setPadding(2f);
								table.insertTable(headTable(newRows(colWidth),
										font_body, headTable));
								title.setLeading(5f);
								title.setSpacingAfter(0f);
								newPage = true;
								linePage = 21;
							}
							table.insertTable(totalTable(colTotalWidth,
									font_total, "ยอดรวมจังหวัด "
											+ obj[15].toString(), totalProv));
							ptPlantRai = 0;
							ptPlantNgan = 0;
							ptPlantWah = 0;
							ptFRecord = 0;
							ptPRecord = 0;
						}
						if (table.size() > 1) {
							document.add(title);
							document.add(table);
							linePage -= table.size();
							table = new Table(1);
							table.setAlignment(Cell.ALIGN_CENTER);
							table.setWidth(100);
							table.setPadding(2f);
						}
					}
				} else {
					calRai = totalRai(dtPlantRai, dtPlantNgan, dtPlantWah);
					String[] totalFarm = new String[] {
							String.format("%,d-%d-%d|", calRai[0], calRai[1],
									calRai[2]),
							String.format("%,.2f", dtFRecord),
							String.format("%,.2f", dtPRecord) };
					if (linePage < table.size()) {
						if (table.size() > 1) {
							document.add(title);
						}
						document.add(table);

						document.newPage();
						document.add(head);
						document.add(head_date);
						table = new Table(1);
						table.setAlignment(Cell.ALIGN_CENTER);
						table.setWidth(100);
						table.setPadding(2f);
						table.insertTable(headTable(newRows(colWidth),
								font_body, headTable));
						title.setLeading(5f);
						title.setSpacingAfter(0f);
						newPage = true;
						linePage = 21;
					}
					table.insertTable(totalTable(colTotalWidth, font_total,
							"รวมกลุ่ม" + farmerTmp, totalFarm));
					btPlantRai += dtPlantRai;
					btPlantNgan += dtPlantNgan;
					btPlantWah += dtPlantWah;
					btFRecord += dtFRecord;
					btPRecord += dtPRecord;
					dtPlantRai = 0;
					dtPlantNgan = 0;
					dtPlantWah = 0;
					dtPlantNgan = 0;
					dtPlantWah = 0;
					dtFRecord = 0;
					dtPRecord = 0;

					calRai = totalRai(btPlantRai, btPlantNgan, btPlantWah);
					String[] totalBran = new String[] {
							String.format("%,d-%d-%d|", calRai[0], calRai[1],
									calRai[2]),
							String.format("%,.2f", btFRecord),
							String.format("%,.2f", btPRecord) };
					if (linePage < table.size()) {
						if (table.size() > 1) {
							document.add(title);
						}
						document.add(table);

						document.newPage();
						document.add(head);
						document.add(head_date);
						table = new Table(1);
						table.setAlignment(Cell.ALIGN_CENTER);
						table.setWidth(100);
						table.setPadding(2f);
						table.insertTable(headTable(newRows(colWidth),
								font_body, headTable));
						title.setLeading(5f);
						title.setSpacingAfter(0f);
						newPage = true;
						linePage = 21;
					}
					table.insertTable(totalTable(colTotalWidth, font_total,
							"ยอดรวมสาขา " + branchTmp, totalBran));
					ptPlantRai += btPlantRai;
					ptPlantNgan += btPlantNgan;
					ptPlantWah += btPlantWah;
					ptFRecord += btFRecord;
					ptPRecord += btPRecord;
					btPlantRai = 0;
					btPlantNgan = 0;
					btPlantWah = 0;
					btFRecord = 0;
					btPRecord = 0;
					calRai = totalRai(ptPlantRai, ptPlantNgan, ptPlantWah);
					String[] totalProv = new String[] {
							String.format("%,d-%d-%d|", calRai[0], calRai[1],
									calRai[2]),
							String.format("%,.2f", ptFRecord),
							String.format("%,.2f", ptPRecord) };
					if (linePage < table.size()) {
						if (table.size() > 1) {
							document.add(title);
						}
						document.add(table);

						document.newPage();
						document.add(head);
						document.add(head_date);
						table = new Table(1);
						table.setAlignment(Cell.ALIGN_CENTER);
						table.setWidth(100);
						table.setPadding(2f);
						table.insertTable(headTable(newRows(colWidth),
								font_body, headTable));
						title.setLeading(5f);
						title.setSpacingAfter(0f);
						newPage = true;
						linePage = 21;
					}
					table.insertTable(totalTable(colTotalWidth, font_total,
							"ยอดรวมจังหวัด " + obj[15].toString(), totalProv));

					document.add(title);
					document.add(table);
				}
			}

		} catch (Exception e) {
			document.add(new Paragraph("เกิดการดึงข้อมูลผิดพลาด", font_title));
			e.printStackTrace();
			log.error(e, e);
		} finally {
			sf.getCurrentSession().close();
		}
		return document;
	}

	private Document ReportR105(SessionFactory sf, Document document,
			Font font_title, Font font_body, Font font_total, ReportForm eform)
			throws DocumentException {
		try {
			sf.getCurrentSession().beginTransaction();
			Paragraph head = new Paragraph(
					"สรุปประมาณการผลผลิตของเกษตรกรผู้ปลูกพืชเศรษฐกิจ ตามปีการผลิต "
							+ eform.getPlantYear() + "/" + eform.getPlantNo(),
					font_title);
			head.setAlignment(Element.ALIGN_CENTER);
			document.add(head);

			Paragraph head_date = new Paragraph("สิ้นสุด ณ วันที่ "
					+ dateThai(eform.getEffectiveDate(), false), font_title);
			head_date.setAlignment(Element.ALIGN_CENTER);
			head_date.setSpacingBefore(5f);
			document.add(head_date);

			Table table = new Table(1);
			table.setAlignment(Cell.ALIGN_CENTER);
			table.setWidth(100);
			table.setPadding(2f);

			float[] colWidth, colTotalWidth;
			colWidth = new float[] { 40, 60, 60, 60 };
			colTotalWidth = new float[] { 40, 15, 15, 15, 15, 15, 15, 15, 15,
					15, 15, 15, 15 };
			String[] breedTypeHead = eform.getBreedTypeItem().split(",");
			String breedTypeId = breedTypeHead[3] + "," + breedTypeHead[4]
					+ "," + breedTypeHead[5];
			String[] headTable = new String[] {
					"ตำบลที่ตั้งแปลง",
					"|"
							+ breedTypeHead[0]
							+ "|จำนวนเกษตรกร\n(ราย)|เนื้อที่เพาะปลูก\nไร่-งาน-วา|ประมาณการ\nผลผลิต\n(กก.)|ผลผลิตรวมจริง\nที่ได้รับ\n(กก.)",
					"|"
							+ breedTypeHead[1]
							+ "|จำนวนเกษตรกร\n(ราย)|เนื้อที่เพาะปลูก\nไร่-งาน-วา|ประมาณการ\nผลผลิต\n(กก.)|ผลผลิตรวมจริง\nที่ได้รับ\n(กก.)",
					"|"
							+ breedTypeHead[2]
							+ "|จำนวนเกษตรกร\n(ราย)|เนื้อที่เพาะปลูก\nไร่-งาน-วา|ประมาณการ\nผลผลิต\n(กก.)|ผลผลิตรวมจริง\nที่ได้รับ\n(กก.)" };
			table
					.insertTable(headTable(newRows(colWidth), font_body,
							headTable));
			int linePage = 21;
			ReportHome rhome = new ReportHome();
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

			List provList = rhome.findR105ByCriteria(eform.getPlantYear(),
					eform.getPlantNo(), df.parse(eform.getEffectiveDate()),
					eform.getProvinceNo(), breedTypeId, bCode);

			String branchTmp = "", subDistrictTmp = "", ProviceTmp = "";
			int[] sQuantity = { 0, 0, 0 }, sPlantRai = { 0, 0, 0 }, sPlantNgan = {
					0, 0, 0 }, sPlantWah = { 0, 0, 0 };
			float[] sFRecord = { 0, 0, 0 }, sHarvest = { 0, 0, 0 };
			int[] pQuantity = { 0, 0, 0 }, pPlantRai = { 0, 0, 0 }, pPlantNgan = {
					0, 0, 0 }, pPlantWah = { 0, 0, 0 };
			float[] pFRecord = { 0, 0, 0 }, pHarvest = { 0, 0, 0 };
			Paragraph title = new Paragraph();
			if (provList.size() > 0) {
				Object[] obj = ((Object[]) provList.get(0));
				title = new Paragraph("ธ.ก.ส. จังหวัด" + obj[8].toString(),
						font_title);
				title.setAlignment(Element.ALIGN_LEFT);
				title.setLeading(5f);
				title.setSpacingBefore(20f);
				document.add(title);
				ProviceTmp = obj[8].toString();
			}

			Table detail = new Table(colWidth.length);
			detail.setWidths(colWidth);
			detail.setAlignment(Cell.ALIGN_CENTER);
			detail.setWidth(100);
			detail.setPadding(2f);
			int breedTypeIndex = 0;
			int frist = 1;
			for (int prov = 0; prov < provList.size(); prov++) {
				Object[] obj = ((Object[]) provList.get(prov));
				Cell sCell = new Cell();

				if (!branchTmp.equals(obj[0].toString())) {
					if ((!branchTmp.equals(obj[0].toString()) && !branchTmp
							.equals(""))
							|| !subDistrictTmp.equals(obj[1].toString())
							&& !subDistrictTmp.equals("")) {
						if (linePage < table.size()) {
							document.add(table);

							document.newPage();
							document.add(head);
							document.add(head_date);
							if (table.size() > 1) {
								document.add(title);
							}
							table = new Table(1);
							table.setAlignment(Cell.ALIGN_CENTER);
							table.setWidth(100);
							table.setPadding(2f);
							table.insertTable(headTable(newRows(colWidth),
									font_body, headTable));
							title.setLeading(5f);
							title.setSpacingAfter(0f);
							linePage = 20;
						}
						table.insertTable(detail);

						detail = new Table(colWidth.length);
						detail.setWidths(colWidth);
						detail.setAlignment(Cell.ALIGN_CENTER);
						detail.setWidth(100);
						detail.setPadding(2f);

						for (int i = 0; i < 3; i++) {
							if (sPlantWah[i] / 100 > 0) {
								sPlantNgan[i] += sPlantWah[i] / 100;
								sPlantWah[i] = sPlantWah[i] % 100;

							}
							if (sPlantNgan[i] / 4 > 0) {
								sPlantRai[i] += sPlantNgan[i] / 4;
								sPlantNgan[i] = sPlantNgan[i] % 4;
							}
						}

						String[] total = new String[] {
								String.format("%,d|", sQuantity[0]),
								String.format("%,d-%d-%d|", sPlantRai[0],
										sPlantNgan[0], sPlantWah[0]),
								String.format("%,.2f", sFRecord[0]),
								String.format("%,.2f", sHarvest[0]),
								String.format("%,d|", sQuantity[1]),
								String.format("%,d-%d-%d|", sPlantRai[1],
										sPlantNgan[1], sPlantWah[1]),
								String.format("%,.2f", sFRecord[1]),
								String.format("%,.2f", sHarvest[1]),
								String.format("%,d|", sQuantity[2]),
								String.format("%,d-%d-%d|", sPlantRai[2],
										sPlantNgan[2], sPlantWah[2]),
								String.format("%,.2f", sFRecord[2]),
								String.format("%,.2f", sHarvest[2]) };
						if (linePage < table.size()) {
							document.add(table);

							document.newPage();
							document.add(head);
							document.add(head_date);
							if (table.size() > 1) {
								document.add(title);
							}
							table = new Table(1);
							table.setAlignment(Cell.ALIGN_CENTER);
							table.setWidth(100);
							table.setPadding(2f);
							table.insertTable(headTable(newRows(colWidth),
									font_body, headTable));
							title.setLeading(5f);
							title.setSpacingAfter(0f);
							linePage = 20;
						}
						table.insertTable(totalTable(colTotalWidth, font_total,
								"รวมสาขา " + branchTmp, total));

						for (int i = 0; i < 3; i++) {
							pQuantity[i] += sQuantity[i];
							pPlantRai[i] += sPlantRai[i];
							pPlantNgan[i] += sPlantNgan[i];
							pPlantWah[i] += sPlantWah[i];
							pFRecord[i] += sFRecord[i];
						}

						sQuantity = new int[] { 0, 0, 0 };
						sPlantRai = new int[] { 0, 0, 0 };
						sPlantNgan = new int[] { 0, 0, 0 };
						sPlantWah = new int[] { 0, 0, 0 };
						sFRecord = new float[] { 0, 0, 0 };
					}
					sCell = new Cell(new Phrase("ธ.ก.ส." + obj[0].toString(),
							font_total));
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
					sCell.setBorderWidthBottom(0);
					sCell.setBorderWidthRight(0.1f);
					detail.addCell(sCell);
					branchTmp = obj[0].toString();
					if (linePage - 1 < table.size()) {
						document.add(table);

						document.newPage();
						document.add(head);
						document.add(head_date);
						if (table.size() > 1) {
							document.add(title);
						}
						table = new Table(1);
						table.setAlignment(Cell.ALIGN_CENTER);
						table.setWidth(100);
						table.setPadding(2f);
						table.insertTable(headTable(newRows(colWidth),
								font_body, headTable));
						title.setLeading(5f);
						title.setSpacingAfter(0f);
						linePage = 20;
					}
					table.insertTable(detail);

					detail = new Table(colWidth.length);
					detail.setWidths(colWidth);
					detail.setAlignment(Cell.ALIGN_CENTER);
					detail.setWidth(100);
					detail.setPadding(2f);
					subDistrictTmp = obj[1].toString();

					sCell = new Cell(new Phrase("      " + obj[1].toString(),
							font_body));
					sCell.setHorizontalAlignment(Cell.ALIGN_LEFT);
					sCell.setVerticalAlignment(Cell.ALIGN_TOP);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthBottom(0);
					sCell.setBorderWidthRight(0.1f);
					detail.addCell(sCell);
				}

				if (!subDistrictTmp.equals(obj[1].toString())) {
					if (linePage < table.size()) {
						document.add(table);

						document.newPage();
						document.add(head);
						document.add(head_date);
						if (table.size() > 1) {
							document.add(title);
						}
						table = new Table(1);
						table.setAlignment(Cell.ALIGN_CENTER);
						table.setWidth(100);
						table.setPadding(2f);
						table.insertTable(headTable(newRows(colWidth),
								font_body, headTable));
						title.setLeading(5f);
						title.setSpacingAfter(0f);
						linePage = 20;
					}
					table.insertTable(detail);

					detail = new Table(colWidth.length);
					detail.setWidths(colWidth);
					detail.setAlignment(Cell.ALIGN_CENTER);
					detail.setWidth(100);
					detail.setPadding(2f);
					subDistrictTmp = obj[1].toString();

					sCell = new Cell(new Phrase("      " + obj[1].toString(),
							font_body));
					sCell.setHorizontalAlignment(Cell.ALIGN_LEFT);
					sCell.setVerticalAlignment(Cell.ALIGN_TOP);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthBottom(0);
					sCell.setBorderWidthRight(0.1f);
					detail.addCell(sCell);
				}

				for (int i = breedTypeIndex; i < 3; i++) {
					if (breedTypeHead[i].equals(obj[2].toString())) {
						Table tmp = new Table(4);
						// จำนวนเกษตกร
						sCell = new Cell(new Phrase(String.format("%,d",
								Integer.parseInt(obj[18].toString())),
								font_body));
						sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
						sCell.setVerticalAlignment(Cell.ALIGN_TOP);
						tmp.addCell(sCell);
						sQuantity[i] += Integer.parseInt(obj[18].toString());
						// เนื้อที่
						int rai = Integer.parseInt(obj[19].toString());
						int ngan = Integer.parseInt(obj[20].toString());
						int wah = Integer.parseInt(obj[21].toString());

						if (wah / 100 > 0) {
							ngan += wah / 100;
							wah = wah % 100;

						}
						if (ngan / 4 > 0) {
							rai += ngan / 4;
							ngan = ngan % 4;
						}

						sCell = new Cell(new Phrase(String.format("%,d-%d-%d",
								rai, ngan, wah), font_body));
						sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
						sCell.setVerticalAlignment(Cell.ALIGN_TOP);
						tmp.addCell(sCell);

						sPlantRai[i] += rai;
						sPlantNgan[i] += ngan;
						sPlantWah[i] += wah;

						sCell = new Cell(new Phrase(String.format("%,.2f",
								Float.parseFloat(obj[22].toString())),
								font_body));
						sCell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
						sCell.setVerticalAlignment(Cell.ALIGN_TOP);
						tmp.addCell(sCell);

						sFRecord[i] += Float.parseFloat(obj[22].toString());

						if (obj[23] != null) {
							sCell = new Cell(new Phrase(String.format("%,.2f",
									Float.parseFloat(obj[23].toString())),
									font_body));
							sCell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
							sCell.setVerticalAlignment(Cell.ALIGN_TOP);
							tmp.addCell(sCell);

							sHarvest[i] += Float.parseFloat(obj[23].toString());
						} else {
							sCell = new Cell(new Phrase(String.format("%,.2f",
									0.00f), font_body));
							sCell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
							sCell.setVerticalAlignment(Cell.ALIGN_TOP);
							tmp.addCell(sCell);
						}

						detail.insertTable(tmp);
						break;
					} else {
						breedTypeIndex++;
						Table tmp = new Table(4);
						// จำนวนเกษตกร
						sCell = new Cell(new Phrase("", font_body));
						sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
						sCell.setVerticalAlignment(Cell.ALIGN_TOP);
						tmp.addCell(sCell);
						// เนื้อที่
						sCell = new Cell(new Phrase("", font_body));
						sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
						sCell.setVerticalAlignment(Cell.ALIGN_TOP);
						tmp.addCell(sCell);

						sCell = new Cell(new Phrase("", font_body));
						sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
						sCell.setVerticalAlignment(Cell.ALIGN_TOP);
						tmp.addCell(sCell);

						sCell = new Cell(new Phrase("", font_body));
						sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
						sCell.setVerticalAlignment(Cell.ALIGN_TOP);
						tmp.addCell(sCell);

						detail.insertTable(tmp);
					}
				}

				if (prov + 1 < provList.size()) {
					Object[] nextObj = ((Object[]) provList.get(prov + 1));
					if (!branchTmp.equals(nextObj[0].toString())
							|| !subDistrictTmp.equals(nextObj[1].toString())) {
						if (breedTypeIndex < 2) {
							for (int i = breedTypeIndex; i < 2; i++) {
								Table tmp = new Table(4);
								// จำนวนเกษตกร
								sCell = new Cell(new Phrase("", font_body));
								sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
								sCell.setVerticalAlignment(Cell.ALIGN_TOP);
								tmp.addCell(sCell);
								// เนื้อที่
								sCell = new Cell(new Phrase("", font_body));
								sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
								sCell.setVerticalAlignment(Cell.ALIGN_TOP);
								tmp.addCell(sCell);

								sCell = new Cell(new Phrase("", font_body));
								sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
								sCell.setVerticalAlignment(Cell.ALIGN_TOP);
								tmp.addCell(sCell);

								sCell = new Cell(new Phrase("", font_body));
								sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
								sCell.setVerticalAlignment(Cell.ALIGN_TOP);
								tmp.addCell(sCell);

								detail.insertTable(tmp);
							}
						}
						breedTypeIndex = 0;
						continue;
					} else {
						breedTypeIndex++;
						if (breedTypeHead[breedTypeIndex].equals(nextObj[2]
								.toString())) {
							continue;
						} else {
							breedTypeIndex++;
							Table tmp = new Table(4);
							// จำนวนเกษตกร
							sCell = new Cell(new Phrase("", font_body));
							sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
							sCell.setVerticalAlignment(Cell.ALIGN_TOP);
							tmp.addCell(sCell);
							// เนื้อที่
							sCell = new Cell(new Phrase("", font_body));
							sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
							sCell.setVerticalAlignment(Cell.ALIGN_TOP);
							tmp.addCell(sCell);

							sCell = new Cell(new Phrase("", font_body));
							sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
							sCell.setVerticalAlignment(Cell.ALIGN_TOP);
							tmp.addCell(sCell);

							sCell = new Cell(new Phrase("", font_body));
							sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
							sCell.setVerticalAlignment(Cell.ALIGN_TOP);
							tmp.addCell(sCell);

							detail.insertTable(tmp);
						}
					}
				} else {
					if (breedTypeIndex < 2) {
						for (int i = breedTypeIndex; i < 2; i++) {
							Table tmp = new Table(4);
							// จำนวนเกษตกร
							sCell = new Cell(new Phrase("", font_body));
							sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
							sCell.setVerticalAlignment(Cell.ALIGN_TOP);
							tmp.addCell(sCell);
							// เนื้อที่
							sCell = new Cell(new Phrase("", font_body));
							sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
							sCell.setVerticalAlignment(Cell.ALIGN_TOP);
							tmp.addCell(sCell);

							sCell = new Cell(new Phrase("", font_body));
							sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
							sCell.setVerticalAlignment(Cell.ALIGN_TOP);
							tmp.addCell(sCell);

							sCell = new Cell(new Phrase("", font_body));
							sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
							sCell.setVerticalAlignment(Cell.ALIGN_TOP);
							tmp.addCell(sCell);

							detail.insertTable(tmp);
						}
					}
				}
			}
			if (provList.size() <= 0) {
				document.add(table);
				title = new Paragraph("ไม่มีข้อมูล", font_title);
				title.setAlignment(Element.ALIGN_CENTER);
				document.add(title);
			} else {
				if (linePage < table.size()) {
					document.add(table);

					document.newPage();
					document.add(head);
					document.add(head_date);
					if (table.size() > 1) {
						document.add(title);
					}
					table = new Table(1);
					table.setAlignment(Cell.ALIGN_CENTER);
					table.setWidth(100);
					table.setPadding(2f);
					table.insertTable(headTable(newRows(colWidth),
							font_body, headTable));
					title.setLeading(5f);
					title.setSpacingAfter(0f);
					linePage = 20;
				}
				table.insertTable(detail);

				for (int i = 0; i < 3; i++) {
					if (sPlantWah[i] / 100 > 0) {
						sPlantNgan[i] += sPlantWah[i] / 100;
						sPlantWah[i] = sPlantWah[i] % 100;

					}
					if (sPlantNgan[i] / 4 > 0) {
						sPlantRai[i] += sPlantNgan[i] / 4;
						sPlantNgan[i] = sPlantNgan[i] % 4;
					}
				}
				String[] total = new String[] {
						String.format("%,d|", sQuantity[0]),
						String.format("%,d-%d-%d|", sPlantRai[0],
								sPlantNgan[0], sPlantWah[0]),
						String.format("%,.2f", sFRecord[0]),
						String.format("%,.2f", sHarvest[0]),
						String.format("%,d|", sQuantity[1]),
						String.format("%,d-%d-%d|", sPlantRai[1],
								sPlantNgan[1], sPlantWah[1]),
						String.format("%,.2f", sFRecord[1]),
						String.format("%,.2f", sHarvest[1]),
						String.format("%,d|", sQuantity[2]),
						String.format("%,d-%d-%d|", sPlantRai[2],
								sPlantNgan[2], sPlantWah[2]),
						String.format("%,.2f", sFRecord[2]),
						String.format("%,.2f", sHarvest[2]) };
				if (linePage < table.size()) {
					document.add(table);

					document.newPage();
					document.add(head);
					document.add(head_date);
					if (table.size() > 1) {
						document.add(title);
					}
					table = new Table(1);
					table.setAlignment(Cell.ALIGN_CENTER);
					table.setWidth(100);
					table.setPadding(2f);
					table.insertTable(headTable(newRows(colWidth),
							font_body, headTable));
					title.setLeading(5f);
					title.setSpacingAfter(0f);
					linePage = 20;
				}
				table.insertTable(totalTable(colTotalWidth, font_total,
						"รวมสาขา " + branchTmp, total));

				for (int i = 0; i < 3; i++) {
					pQuantity[i] += sQuantity[i];
					pPlantRai[i] += sPlantRai[i];
					pPlantNgan[i] += sPlantNgan[i];
					pPlantWah[i] += sPlantWah[i];
					pFRecord[i] += sFRecord[i];
					pHarvest[i] += sHarvest[i];
				}
				for (int i = 0; i < 3; i++) {
					if (pPlantWah[i] / 100 > 0) {
						pPlantNgan[i] += pPlantWah[i] / 100;
						pPlantWah[i] = pPlantWah[i] % 100;

					}
					if (pPlantNgan[i] / 4 > 0) {
						pPlantRai[i] += pPlantNgan[i] / 4;
						pPlantNgan[i] = pPlantNgan[i] % 4;
					}
				}

				total = new String[] {
						String.format("%,d|", pQuantity[0]),
						String.format("%,d-%d-%d|", pPlantRai[0],
								pPlantNgan[0], pPlantWah[0]),
						String.format("%,.2f", pFRecord[0]),
						String.format("%,.2f", pHarvest[0]),
						String.format("%,d|", pQuantity[1]),
						String.format("%,d-%d-%d|", pPlantRai[1],
								pPlantNgan[1], pPlantWah[1]),
						String.format("%,.2f", pFRecord[1]),
						String.format("%,.2f", pHarvest[1]),
						String.format("%,d|", pQuantity[2]),
						String.format("%,d-%d-%d|", pPlantRai[2],
								pPlantNgan[2], pPlantWah[2]),
						String.format("%,.2f", pFRecord[2]),
						String.format("%,.2f", pHarvest[2]) };
				if (linePage  < table.size()) {
					document.add(table);

					document.newPage();
					document.add(head);
					document.add(head_date);
					if (table.size() > 1) {
						document.add(title);
					}
					table = new Table(1);
					table.setAlignment(Cell.ALIGN_CENTER);
					table.setWidth(100);
					table.setPadding(2f);
					table.insertTable(headTable(newRows(colWidth),
							font_body, headTable));
					title.setLeading(5f);
					title.setSpacingAfter(0f);
					linePage = 20;
				}
				table.insertTable(totalTable(colTotalWidth, font_total,
						"ยอดรวมจังหวัด" + ProviceTmp, total));

				float[] endcolWidth = new float[] { 100, 60, 60 };
				Table avgtb = new Table(endcolWidth.length);
				avgtb.setWidths(endcolWidth);
				for (int i = 0; i < 3; i++) {
					String endline = "ผลผลิตจริงเฉลี่ยต่อไร่ของ"
							+ breedTypeHead[i];
					float avg = pHarvest[i] / pPlantRai[i];
					Cell sCell = new Cell(new Phrase(String.format(
							"%s %,.2f กก.", endline, avg > 0 ? avg : 0f),
							font_total));
					sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
					sCell.setVerticalAlignment(Cell.ALIGN_TOP);
					avgtb.addCell(sCell);
				}
				if (linePage  < table.size()) {
					document.add(table);

					document.newPage();
					document.add(head);
					document.add(head_date);
					if (table.size() > 1) {
						document.add(title);
					}
					table = new Table(1);
					table.setAlignment(Cell.ALIGN_CENTER);
					table.setWidth(100);
					table.setPadding(2f);
					table.insertTable(headTable(newRows(colWidth),
							font_body, headTable));
					title.setLeading(5f);
					title.setSpacingAfter(0f);
					linePage = 20;
				}
				table.insertTable(avgtb);
				document.add(table);
			}

		} catch (Exception e) {
			document.add(new Paragraph("เกิดการดึงข้อมูลผิดพลาด", font_title));
			e.printStackTrace();
			log.error(e, e);
		} finally {
			sf.getCurrentSession().close();
		}
		return document;
	}

	private Document ReportR105_1(SessionFactory sf, Document document,
			Font font_title, Font font_body, Font font_total, ReportForm eform)
			throws DocumentException {
		try {
			sf.getCurrentSession().beginTransaction();
			Paragraph head = new Paragraph(
					"สรุปประมาณการผลผลิตของเกษตรกรผู้ปลูกพืชเศรษฐกิจ ตามปีการผลิต "
							+ eform.getPlantYear() + "/" + eform.getPlantNo(),
					font_title);
			head.setAlignment(Element.ALIGN_CENTER);
			document.add(head);

			Paragraph head_date = new Paragraph("สิ้นสุด ณ วันที่ "
					+ dateThai(eform.getEffectiveDate(), false), font_title);
			head_date.setAlignment(Element.ALIGN_CENTER);
			head_date.setSpacingBefore(5f);
			document.add(head_date);

			Table table = new Table(1);
			table.setAlignment(Cell.ALIGN_CENTER);
			table.setWidth(100);
			table.setPadding(2f);

			float[] colWidth, colTotalWidth;
			colWidth = new float[] { 8, 45, 45, 45 };
			colTotalWidth = new float[] { 1.75f, 0.5f, 0.75f, 1, 1, 1, 0.5f, 0.75f, 1, 1, 1, 0.5f, 0.75f, 1, 1 };
			float[] innerCol = new float[] { 1f, 0.5f, 0.75f, 1f, 1f };
			String[] breedTypeHead = eform.getBreedTypeItem().split(",");
			List[] breedGroupBranch = new List[]{new ArrayList<String>(),new ArrayList<String>(),new ArrayList<String>()};
			List[] breedGroupProv = new List[]{new ArrayList<String>(),new ArrayList<String>(),new ArrayList<String>()};
			Dictionary<String, Float> breedGroupValue = new Hashtable();
			String breedTypeId = breedTypeHead[3] + "," + breedTypeHead[4]
					+ "," + breedTypeHead[5];
			String[] headTable = new String[] {
					"ตำบลที่ตั้งแปลง",
					"1,0.5,0.75,1,1,|"
							+ breedTypeHead[0]
							+ "|กลุ่มพันธุ์|จำนวน\nเกษตรกร\n(ราย)|เนื้อที่เพาะปลูก\nไร่-งาน-วา|ประมาณการ\nผลผลิต\n(กก.)|ผลผลิตรวมจริง\nที่ได้รับ\n(กก.)",
					"1,0.5,0.75,1,1,|"
							+ breedTypeHead[1]
							+ "|กลุ่มพันธุ์|จำนวน\nเกษตรกร\n(ราย)|เนื้อที่เพาะปลูก\nไร่-งาน-วา|ประมาณการ\nผลผลิต\n(กก.)|ผลผลิตรวมจริง\nที่ได้รับ\n(กก.)",
					"1,0.5,0.75,1,1,|"
							+ breedTypeHead[2]
							+ "|กลุ่มพันธุ์|จำนวน\nเกษตรกร\n(ราย)|เนื้อที่เพาะปลูก\nไร่-งาน-วา|ประมาณการ\nผลผลิต\n(กก.)|ผลผลิตรวมจริง\nที่ได้รับ\n(กก.)" };
			table
					.insertTable(headTable(newRows(colWidth), font_body,
							headTable));
			int linePage = 27;
			ReportHome rhome = new ReportHome();
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

			List provList = rhome.findR105_1ByCriteria(eform.getPlantYear(),
					eform.getPlantNo(), df.parse(eform.getEffectiveDate()),
					eform.getBranchCode(), eform.getProvinceNo(), breedTypeId);

			String branchTmp = "", subDistrictTmp = "", proviceTmp = "";
			int[] sQuantity = { 0, 0, 0 }, sPlantRai = { 0, 0, 0 }, sPlantNgan = {
					0, 0, 0 }, sPlantWah = { 0, 0, 0 };
			float[] sFRecord = { 0, 0, 0 }, sHarvest = { 0, 0, 0 };
			int[] bQuantity = { 0, 0, 0 }, bPlantRai = { 0, 0, 0 }, bPlantNgan = {
					0, 0, 0 }, bPlantWah = { 0, 0, 0 };
			float[] bFRecord = { 0, 0, 0 }, bHarvest = { 0, 0, 0 };
			int[] pQuantity = { 0, 0, 0 }, pPlantRai = { 0, 0, 0 }, pPlantNgan = {
					0, 0, 0 }, pPlantWah = { 0, 0, 0 };
			float[] pFRecord = { 0, 0, 0 }, pHarvest = { 0, 0, 0 };
			Paragraph title = new Paragraph();
			List newList = new ArrayList();
			if (provList.size() > 0) {
				Object[] obj = ((Object[]) provList.get(0));
				proviceTmp = obj[9].toString();

				List tmpList = new ArrayList();
				newList.add(obj);
				subDistrictTmp = obj[1].toString();
				String breedTypeTmp = obj[2].toString();
				for (int i = 1; i < provList.size(); i++) {
					Object[] getObj = ((Object[]) provList.get(i));
					if (!subDistrictTmp.equals(getObj[1].toString())) {
						for (int j = 0; j < tmpList.size(); j++) {
							Object[] getTmp = ((Object[]) tmpList.get(j));
							newList.add(getTmp);
						}
						tmpList.clear();
						subDistrictTmp = getObj[1].toString();
						newList.add(getObj);
						breedTypeTmp = getObj[2].toString();
					} else {
						if (breedTypeTmp.equals(getObj[2].toString())) {
							tmpList.add(getObj);
						} else {
							newList.add(getObj);
							breedTypeTmp = getObj[2].toString();
						}
					}
				}
				for (int j = 0; j < tmpList.size(); j++) {
					Object[] getTmp = ((Object[]) tmpList.get(j));
					newList.add(getTmp);
				}
				subDistrictTmp = "";
			}

			Table detail = new Table(colWidth.length);
			detail.setWidths(colWidth);
			detail.setAlignment(Cell.ALIGN_CENTER);
			detail.setWidth(100);
			detail.setPadding(2f);
			int breedTypeIndex = 0;
			int frist = 1;
			for (int prov = 0; prov < newList.size(); prov++) {
				Object[] obj = ((Object[]) newList.get(prov));
				Cell sCell = new Cell();

				if (!branchTmp.equals(obj[0].toString())) {
					if ((!branchTmp.equals(obj[0].toString()) && !branchTmp
							.equals(""))
							|| !subDistrictTmp.equals(obj[1].toString())
							&& !subDistrictTmp.equals("")) {
						if (linePage < table.size()) {
							if (table.size() > 1) {
								document.add(title);
							}
							document.add(table);

							document.newPage();
							document.add(head);
							document.add(head_date);
							table = new Table(1);
							table.setAlignment(Cell.ALIGN_CENTER);
							table.setWidth(100);
							table.setPadding(2f);
							table.insertTable(headTable(newRows(colWidth),
									font_body, headTable));
							title.setLeading(5f);
							title.setSpacingAfter(0f);
							linePage = 25;
						}
						table.insertTable(detail);

						detail = new Table(colWidth.length);
						detail.setWidths(colWidth);
						detail.setAlignment(Cell.ALIGN_CENTER);
						detail.setWidth(100);
						detail.setPadding(2f);

						for (int i = 0; i < 3; i++) {
							if (sPlantWah[i] / 100 > 0) {
								sPlantNgan[i] += sPlantWah[i] / 100;
								sPlantWah[i] = sPlantWah[i] % 100;

							}
							if (sPlantNgan[i] / 4 > 0) {
								sPlantRai[i] += sPlantNgan[i] / 4;
								sPlantNgan[i] = sPlantNgan[i] % 4;
							}
						}

						String[] total = new String[] {
								String.format("%,d|", sQuantity[0]),
								String.format("%,d-%d-%d|", sPlantRai[0],
										sPlantNgan[0], sPlantWah[0]),
								String.format("%,.2f", sFRecord[0]),
								String.format("%,.2f", sHarvest[0]),
								"",
								String.format("%,d|", sQuantity[1]),
								String.format("%,d-%d-%d|", sPlantRai[1],
										sPlantNgan[1], sPlantWah[1]),
								String.format("%,.2f", sFRecord[1]),
								String.format("%,.2f", sHarvest[1]),
								"",
								String.format("%,d|", sQuantity[2]),
								String.format("%,d-%d-%d|", sPlantRai[2],
										sPlantNgan[2], sPlantWah[2]),
								String.format("%,.2f", sFRecord[2]),
								String.format("%,.2f", sHarvest[2]) };
						if (linePage < table.size()) {
							if (table.size() > 1) {
								document.add(title);
							}
							document.add(table);

							document.newPage();
							document.add(head);
							document.add(head_date);
							table = new Table(1);
							table.setAlignment(Cell.ALIGN_CENTER);
							table.setWidth(100);
							table.setPadding(2f);
							table.insertTable(headTable(newRows(colWidth),
									font_body, headTable));
							title.setLeading(5f);
							title.setSpacingAfter(0f);
							linePage = 25;
						}
						table.insertTable(totalTable(colTotalWidth, font_total,
								"รวม", total));
						for (int i = 0; i < 3; i++) {
							bQuantity[i] += sQuantity[i];
							bPlantRai[i] += sPlantRai[i];
							bPlantNgan[i] += sPlantNgan[i];
							bPlantWah[i] += sPlantWah[i];
							bFRecord[i] += sFRecord[i];
							bHarvest[i] += sHarvest[i];
						}

						sQuantity = new int[] { 0, 0, 0 };
						sPlantRai = new int[] { 0, 0, 0 };
						sPlantNgan = new int[] { 0, 0, 0 };
						sPlantWah = new int[] { 0, 0, 0 };
						sFRecord = new float[] { 0, 0, 0 };
						sHarvest = new float[] { 0, 0, 0 };
						
						for (int i = 0; i < 3; i++) {
							if (bPlantWah[i] / 100 > 0) {
								bPlantNgan[i] += bPlantWah[i] / 100;
								bPlantWah[i] = bPlantWah[i] % 100;

							}
							if (bPlantNgan[i] / 4 > 0) {
								bPlantRai[i] += bPlantNgan[i] / 4;
								bPlantNgan[i] = bPlantNgan[i] % 4;
							}
						}

						total = new String[] {
								String.format("%,d|", bQuantity[0]),
								String.format("%,d-%d-%d|", bPlantRai[0],
										bPlantNgan[0], bPlantWah[0]),
								String.format("%,.2f", bFRecord[0]),
								String.format("%,.2f", bHarvest[0]),
								"",
								String.format("%,d|", bQuantity[1]),
								String.format("%,d-%d-%d|", bPlantRai[1],
										bPlantNgan[1], bPlantWah[1]),
								String.format("%,.2f", bFRecord[1]),
								String.format("%,.2f", bHarvest[1]),
								"",
								String.format("%,d|", bQuantity[2]),
								String.format("%,d-%d-%d|", bPlantRai[2],
										bPlantNgan[2], bPlantWah[2]),
								String.format("%,.2f", bFRecord[2]),
								String.format("%,.2f", bHarvest[2]) };
						if (linePage < table.size()) {
							if (table.size() > 1) {
								document.add(title);
							}
							document.add(table);

							document.newPage();
							document.add(head);
							document.add(head_date);
							table = new Table(1);
							table.setAlignment(Cell.ALIGN_CENTER);
							table.setWidth(100);
							table.setPadding(2f);
							table.insertTable(headTable(newRows(colWidth),
									font_body, headTable));
							title.setLeading(5f);
							title.setSpacingAfter(0f);
							linePage = 25;
						}
						table.insertTable(totalTable(colTotalWidth, font_total,
								"รวมสาขา " + branchTmp, total));
						for (int i = 0; i < 3; i++) {
							pQuantity[i] += bQuantity[i];
							pPlantRai[i] += bPlantRai[i];
							pPlantNgan[i] += bPlantNgan[i];
							pPlantWah[i] += bPlantWah[i];
							pFRecord[i] += bFRecord[i];
							pHarvest[i] += bHarvest[i];
						}

						bQuantity = new int[] { 0, 0, 0 };
						bPlantRai = new int[] { 0, 0, 0 };
						bPlantNgan = new int[] { 0, 0, 0 };
						bPlantWah = new int[] { 0, 0, 0 };
						bFRecord = new float[] { 0, 0, 0 };
						bHarvest = new float[] { 0, 0, 0 };
						
						for (int i = 0; i < 3; i++) {
							if (pPlantWah[i] / 100 > 0) {
								pPlantNgan[i] += pPlantWah[i] / 100;
								pPlantWah[i] = pPlantWah[i] % 100;

							}
							if (pPlantNgan[i] / 4 > 0) {
								pPlantRai[i] += pPlantNgan[i] / 4;
								pPlantNgan[i] = pPlantNgan[i] % 4;
							}
						}
						
						if (!proviceTmp.equals(obj[9].toString())) {
							total = new String[] {
									String.format("%,d|", pQuantity[0]),
									String.format("%,d-%d-%d|", pPlantRai[0],
											pPlantNgan[0], pPlantWah[0]),
									String.format("%,.2f", pFRecord[0]),
									String.format("%,.2f", pHarvest[0]),
									"",
									String.format("%,d|", pQuantity[1]),
									String.format("%,d-%d-%d|", pPlantRai[1],
											pPlantNgan[1], pPlantWah[1]),
									String.format("%,.2f", pFRecord[1]),
									String.format("%,.2f", pHarvest[1]),
									"",
									String.format("%,d|", pQuantity[2]),
									String.format("%,d-%d-%d|", pPlantRai[2],
											pPlantNgan[2], pPlantWah[2]),
									String.format("%,.2f", pFRecord[2]),
									String.format("%,.2f", pHarvest[2]) };
							if (linePage < table.size()) {
								if (table.size() > 1) {
									document.add(title);
								}
								document.add(table);

								document.newPage();
								document.add(head);
								document.add(head_date);
								table = new Table(1);
								table.setAlignment(Cell.ALIGN_CENTER);
								table.setWidth(100);
								table.setPadding(2f);
								table.insertTable(headTable(newRows(colWidth), font_body,
										headTable));
								title.setLeading(5f);
								title.setSpacingAfter(0f);
								linePage = 25;
							}
							table.insertTable(totalTable(colTotalWidth, font_total,
									"ยอดรวมจังหวัด" + proviceTmp, total));
							
							proviceTmp = obj[9].toString();
							
							pQuantity = new int[] { 0, 0, 0 };
							pPlantRai = new int[] { 0, 0, 0 };
							pPlantNgan = new int[] { 0, 0, 0 };
							pPlantWah = new int[] { 0, 0, 0 };
							pFRecord = new float[] { 0, 0, 0 };
						}

						if (table.size() > 1) {
							document.add(title);
							document.add(table);
							linePage -= table.size();
							table = new Table(1);
							table.setAlignment(Cell.ALIGN_CENTER);
							table.setWidth(100);
							table.setPadding(2f);
						}

					}
					title = new Paragraph("ธ.ก.ส. จังหวัด" + obj[9].toString()
							+ " " + obj[0].toString(), font_title);
					title.setAlignment(Element.ALIGN_LEFT);
					title.setLeading(5f);
					title.setSpacingBefore(20f);

					branchTmp = obj[0].toString();
					linePage -= 2;

					detail = new Table(colWidth.length);
					detail.setWidths(colWidth);
					detail.setAlignment(Cell.ALIGN_CENTER);
					detail.setWidth(100);
					detail.setPadding(2f);
					subDistrictTmp = obj[1].toString();

					sCell = new Cell(new Phrase(obj[1].toString(), font_body));
					sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
					sCell.setVerticalAlignment(Cell.ALIGN_TOP);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthBottom(0);
					sCell.setBorderWidthRight(0.1f);
					detail.addCell(sCell);
				}

				if (!subDistrictTmp.equals(obj[1].toString())) {
					if (linePage < table.size()) {
						if (table.size() > 1) {
							document.add(title);
						}
						document.add(table);

						document.newPage();
						document.add(head);
						document.add(head_date);
						table = new Table(1);
						table.setAlignment(Cell.ALIGN_CENTER);
						table.setWidth(100);
						table.setPadding(2f);
						table.insertTable(headTable(newRows(colWidth),
								font_body, headTable));
						title.setLeading(5f);
						title.setSpacingAfter(0f);
						linePage = 25;
					}
					table.insertTable(detail);

					detail = new Table(colWidth.length);
					detail.setWidths(colWidth);
					detail.setAlignment(Cell.ALIGN_CENTER);
					detail.setWidth(100);
					detail.setPadding(2f);
					
					for (int i = 0; i < 3; i++) {
						if (sPlantWah[i] / 100 > 0) {
							sPlantNgan[i] += sPlantWah[i] / 100;
							sPlantWah[i] = sPlantWah[i] % 100;

						}
						if (sPlantNgan[i] / 4 > 0) {
							sPlantRai[i] += sPlantNgan[i] / 4;
							sPlantNgan[i] = sPlantNgan[i] % 4;
						}
					}

					String[] total = new String[] {
							String.format("%,d|", sQuantity[0]),
							String.format("%,d-%d-%d|", sPlantRai[0],
									sPlantNgan[0], sPlantWah[0]),
							String.format("%,.2f", sFRecord[0]),
							String.format("%,.2f", sHarvest[0]),
							"",
							String.format("%,d|", sQuantity[1]),
							String.format("%,d-%d-%d|", sPlantRai[1],
									sPlantNgan[1], sPlantWah[1]),
							String.format("%,.2f", sFRecord[1]),
							String.format("%,.2f", sHarvest[1]),
							"",
							String.format("%,d|", sQuantity[2]),
							String.format("%,d-%d-%d|", sPlantRai[2],
									sPlantNgan[2], sPlantWah[2]),
							String.format("%,.2f", sFRecord[2]),
							String.format("%,.2f", sHarvest[2]) };
					if (linePage < table.size()) {
						if (table.size() > 1) {
							document.add(title);
						}
						document.add(table);

						document.newPage();
						document.add(head);
						document.add(head_date);
						table = new Table(1);
						table.setAlignment(Cell.ALIGN_CENTER);
						table.setWidth(100);
						table.setPadding(2f);
						table.insertTable(headTable(newRows(colWidth),
								font_body, headTable));
						title.setLeading(5f);
						title.setSpacingAfter(0f);
						linePage = 25;
					}
					table.insertTable(totalTable(colTotalWidth, font_total,
							"รวม", total));
					for (int i = 0; i < 3; i++) {
						bQuantity[i] += sQuantity[i];
						bPlantRai[i] += sPlantRai[i];
						bPlantNgan[i] += sPlantNgan[i];
						bPlantWah[i] += sPlantWah[i];
						bFRecord[i] += sFRecord[i];
						bHarvest[i] += sHarvest[i];
					}

					sQuantity = new int[] { 0, 0, 0 };
					sPlantRai = new int[] { 0, 0, 0 };
					sPlantNgan = new int[] { 0, 0, 0 };
					sPlantWah = new int[] { 0, 0, 0 };
					sFRecord = new float[] { 0, 0, 0 };
					sHarvest = new float[] { 0, 0, 0 };
					
					
					subDistrictTmp = obj[1].toString();

					sCell = new Cell(new Phrase(obj[1].toString(), font_body));
					sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
					sCell.setVerticalAlignment(Cell.ALIGN_TOP);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthBottom(0);
					sCell.setBorderWidthRight(0.1f);
					detail.addCell(sCell);
				}

				for (int i = breedTypeIndex; i < 3; i++) {
					if (breedTypeHead[i].equals(obj[2].toString())) {
						Table col = new Table(1);

						Table tmp = new Table(5);
						tmp.setWidths(innerCol);
						
						sCell = new Cell(new Phrase(obj[3].toString(),
								font_body));
						sCell.setHorizontalAlignment(Cell.ALIGN_LEFT);
						sCell.setVerticalAlignment(Cell.ALIGN_TOP);
						tmp.addCell(sCell);
						// จำนวนเกษตกร
						sCell = new Cell(new Phrase(String.format("%,d",
								Integer.parseInt(obj[19].toString())),
								font_body));
						sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
						sCell.setVerticalAlignment(Cell.ALIGN_TOP);
						tmp.addCell(sCell);

						sQuantity[i] += Integer.parseInt(obj[19].toString());
						// เนื้อที่
						int rai = Integer.parseInt(obj[20].toString());
						int ngan = Integer.parseInt(obj[21].toString());
						int wah = Integer.parseInt(obj[22].toString());

						if (wah / 100 > 0) {
							ngan += wah / 100;
							wah = wah % 100;

						}
						if (ngan / 4 > 0) {
							rai += ngan / 4;
							ngan = ngan % 4;
						}
						sCell = new Cell(new Phrase(String.format("%,d-%d-%d",
								rai, ngan, wah), font_body));
						sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
						sCell.setVerticalAlignment(Cell.ALIGN_TOP);
						tmp.addCell(sCell);

						sPlantRai[i] += rai;
						sPlantNgan[i] += ngan;
						sPlantWah[i] += wah;

						sCell = new Cell(new Phrase(String.format("%,.2f",
								Float.parseFloat(obj[23].toString())),
								font_body));
						sCell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
						sCell.setVerticalAlignment(Cell.ALIGN_TOP);
						tmp.addCell(sCell);

						sFRecord[i] += Float.parseFloat(obj[23].toString());

						if (obj[24] != null) {
							sCell = new Cell(new Phrase(String.format("%,.2f",
									Float.parseFloat(obj[24].toString())),
									font_body));
							sCell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
							sCell.setVerticalAlignment(Cell.ALIGN_TOP);
							tmp.addCell(sCell);

							sHarvest[i] += Float.parseFloat(obj[24].toString());
						} else {
							sCell = new Cell(new Phrase(String.format("%,.2f",
									0.00f), font_body));
							sCell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
							sCell.setVerticalAlignment(Cell.ALIGN_TOP);
							tmp.addCell(sCell);
						}
						
						if (!breedGroupBranch[i].contains(obj[3].toString()+" - "+branchTmp)) {
							breedGroupBranch[i].add(obj[3].toString()+" - "+branchTmp);
							
							breedGroupValue.put(obj[3].toString()+" - "+branchTmp+"Harvest", obj[24]==null?0:Float.parseFloat(obj[24].toString()));
							breedGroupValue.put(obj[3].toString()+" - "+branchTmp+"Rai", (float)rai);
							breedGroupValue.put(obj[3].toString()+" - "+branchTmp+"Ngan", (float)ngan);
							breedGroupValue.put(obj[3].toString()+" - "+branchTmp+"Wah", (float)wah);
							
							breedGroupProv[i].add(obj[3].toString()+" - จังหวัด"+proviceTmp);
							breedGroupValue.put(obj[3].toString()+" - จังหวัด"+proviceTmp+"Harvest", obj[24]==null?0:Float.parseFloat(obj[24].toString()));
							breedGroupValue.put(obj[3].toString()+" - จังหวัด"+proviceTmp+"Rai", (float)rai);
							breedGroupValue.put(obj[3].toString()+" - จังหวัด"+proviceTmp+"Ngan", (float)ngan);
							breedGroupValue.put(obj[3].toString()+" - จังหวัด"+proviceTmp+"Wah", (float)wah);
						}else{
							float tmpHarvest = (obj[24]==null?0:Float.parseFloat(obj[24].toString())) + breedGroupValue.get(obj[3].toString()+" - "+branchTmp+"Harvest");
							float tmpRai = rai + breedGroupValue.get(obj[3].toString()+" - "+branchTmp+"Rai");
							float tmpNgan = ngan + breedGroupValue.get(obj[3].toString()+" - "+branchTmp+"Ngan");
							float tmpWah = wah + breedGroupValue.get(obj[3].toString()+" - "+branchTmp+"Wah");
							breedGroupValue.put(obj[3].toString()+" - "+branchTmp+"Harvest", tmpHarvest);
							breedGroupValue.put(obj[3].toString()+" - "+branchTmp+"Rai", tmpRai);
							breedGroupValue.put(obj[3].toString()+" - "+branchTmp+"Ngan", tmpNgan);
							breedGroupValue.put(obj[3].toString()+" - "+branchTmp+"Wah", tmpWah);
							
							tmpHarvest = (obj[24]==null?0:Float.parseFloat(obj[24].toString())) + breedGroupValue.get(obj[3].toString()+" - จังหวัด"+proviceTmp+"Harvest");
							tmpRai = rai + breedGroupValue.get(obj[3].toString()+" - จังหวัด"+proviceTmp+"Rai");
							tmpNgan = ngan + breedGroupValue.get(obj[3].toString()+" - จังหวัด"+proviceTmp+"Ngan");
							tmpWah = wah + breedGroupValue.get(obj[3].toString()+" - จังหวัด"+proviceTmp+"Wah");
							breedGroupValue.put(obj[3].toString()+" - จังหวัด"+proviceTmp+"Harvest", tmpHarvest);
							breedGroupValue.put(obj[3].toString()+" - จังหวัด"+proviceTmp+"Rai", tmpRai);
							breedGroupValue.put(obj[3].toString()+" - จังหวัด"+proviceTmp+"Ngan", tmpNgan);
							breedGroupValue.put(obj[3].toString()+" - จังหวัด"+proviceTmp+"Wah", tmpWah);
						}
						
						col.insertTable(tmp);
						detail.insertTable(col);
						break;
					} else {
						breedTypeIndex++;
						Table col = new Table(1);
						Table tmp = new Table(5);
						tmp.setWidths(innerCol);
						sCell = new Cell(new Phrase(" ", font_body));
						sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
						sCell.setVerticalAlignment(Cell.ALIGN_TOP);
						tmp.addCell(sCell);

						sCell = new Cell(new Phrase(" ", font_body));
						sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
						sCell.setVerticalAlignment(Cell.ALIGN_TOP);
						tmp.addCell(sCell);

						sCell = new Cell(new Phrase(" ", font_body));
						sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
						sCell.setVerticalAlignment(Cell.ALIGN_TOP);
						tmp.addCell(sCell);

						sCell = new Cell(new Phrase(" ", font_body));
						sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
						sCell.setVerticalAlignment(Cell.ALIGN_TOP);
						tmp.addCell(sCell);

						sCell = new Cell(new Phrase(" ", font_body));
						sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
						sCell.setVerticalAlignment(Cell.ALIGN_TOP);
						tmp.addCell(sCell);

						col.insertTable(tmp);
						detail.insertTable(col);
					}
				}
				if (prov + 1 < newList.size()) {
					Object[] nextObj = ((Object[]) newList.get(prov + 1));
					if (!branchTmp.equals(nextObj[0].toString())
							|| !subDistrictTmp.equals(nextObj[1].toString())) {
						if (breedTypeIndex < 2) {
							for (int i = breedTypeIndex; i < 2; i++) {
								Table col = new Table(1);
								Table tmp = new Table(5);
								tmp.setWidths(innerCol);
								sCell = new Cell(new Phrase(" ", font_body));
								sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
								sCell.setVerticalAlignment(Cell.ALIGN_TOP);
								tmp.addCell(sCell);

								sCell = new Cell(new Phrase(" ", font_body));
								sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
								sCell.setVerticalAlignment(Cell.ALIGN_TOP);
								tmp.addCell(sCell);

								sCell = new Cell(new Phrase(" ", font_body));
								sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
								sCell.setVerticalAlignment(Cell.ALIGN_TOP);
								tmp.addCell(sCell);

								sCell = new Cell(new Phrase(" ", font_body));
								sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
								sCell.setVerticalAlignment(Cell.ALIGN_TOP);
								tmp.addCell(sCell);

								sCell = new Cell(new Phrase(" ", font_body));
								sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
								sCell.setVerticalAlignment(Cell.ALIGN_TOP);
								tmp.addCell(sCell);

								col.insertTable(tmp);
								detail.insertTable(col);
							}
						}
						breedTypeIndex = 0;
						continue;
					} else {
						if (breedTypeHead[breedTypeIndex].equals(nextObj[2]
								.toString())) {
							for (int i = breedTypeIndex; i < 2; i++) {
								Table col = new Table(1);
								Table tmp = new Table(5);
								tmp.setWidths(innerCol);
								sCell = new Cell(new Phrase(" ", font_body));
								sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
								sCell.setVerticalAlignment(Cell.ALIGN_TOP);
								tmp.addCell(sCell);

								sCell = new Cell(new Phrase(" ", font_body));
								sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
								sCell.setVerticalAlignment(Cell.ALIGN_TOP);
								tmp.addCell(sCell);

								sCell = new Cell(new Phrase(" ", font_body));
								sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
								sCell.setVerticalAlignment(Cell.ALIGN_TOP);
								tmp.addCell(sCell);

								sCell = new Cell(new Phrase(" ", font_body));
								sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
								sCell.setVerticalAlignment(Cell.ALIGN_TOP);
								tmp.addCell(sCell);

								sCell = new Cell(new Phrase(" ", font_body));
								sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
								sCell.setVerticalAlignment(Cell.ALIGN_TOP);
								tmp.addCell(sCell);

								col.insertTable(tmp);
								detail.insertTable(col);
							}

							if (linePage < table.size()) {
								if (table.size() > 1) {
									document.add(title);
								}
								document.add(table);

								document.newPage();
								document.add(head);
								document.add(head_date);
								table = new Table(1);
								table.setAlignment(Cell.ALIGN_CENTER);
								table.setWidth(100);
								table.setPadding(2f);
								table.insertTable(headTable(newRows(colWidth),
										font_body, headTable));
								title.setLeading(5f);
								title.setSpacingAfter(0f);
								linePage = 25;
							}
							table.insertTable(detail);

							detail = new Table(colWidth.length);
							detail.setWidths(colWidth);
							detail.setAlignment(Cell.ALIGN_CENTER);
							detail.setWidth(100);
							detail.setPadding(2f);

							if (linePage < table.size()) {
								document.add(title);
								document.add(table);

								document.newPage();
								document.add(head);
								document.add(head_date);
								table = new Table(1);
								table.setAlignment(Cell.ALIGN_CENTER);
								table.setWidth(100);
								table.setPadding(2f);
								table.insertTable(headTable(newRows(colWidth),
										font_body, headTable));
								title.setLeading(5f);
								title.setSpacingAfter(0f);
								linePage = 25;
								
								sCell = new Cell(new Phrase(subDistrictTmp,
										font_body));
								sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
								sCell.setVerticalAlignment(Cell.ALIGN_TOP);
								sCell.setBorderWidthTop(0.1f);
								sCell.setBorderWidthBottom(0);
								sCell.setBorderWidthRight(0.1f);
								detail.addCell(sCell);
							} else {
								sCell = new Cell(new Phrase(" ", font_body));
								sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
								sCell.setVerticalAlignment(Cell.ALIGN_TOP);
								sCell.setBorderWidthTop(0);
								sCell.setBorderWidthBottom(0);
								sCell.setBorderWidthRight(0.1f);
								detail.addCell(sCell);
							}

							breedTypeIndex = 0;
							continue;
						}
						breedTypeIndex++;
						if (breedTypeHead[breedTypeIndex].equals(nextObj[2]
								.toString())) {
							continue;
						} else if (breedTypeIndex < 2) {
							breedTypeIndex++;
							Table col = new Table(1);
							Table tmp = new Table(5);
							tmp.setWidths(innerCol);
							sCell = new Cell(new Phrase(" ", font_body));
							sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
							sCell.setVerticalAlignment(Cell.ALIGN_TOP);
							tmp.addCell(sCell);

							sCell = new Cell(new Phrase(" ", font_body));
							sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
							sCell.setVerticalAlignment(Cell.ALIGN_TOP);
							tmp.addCell(sCell);

							sCell = new Cell(new Phrase(" ", font_body));
							sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
							sCell.setVerticalAlignment(Cell.ALIGN_TOP);
							tmp.addCell(sCell);

							sCell = new Cell(new Phrase(" ", font_body));
							sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
							sCell.setVerticalAlignment(Cell.ALIGN_TOP);
							tmp.addCell(sCell);

							sCell = new Cell(new Phrase(" ", font_body));
							sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
							sCell.setVerticalAlignment(Cell.ALIGN_TOP);
							tmp.addCell(sCell);

							col.insertTable(tmp);
							detail.insertTable(col);
						} else {
							if (linePage < table.size()) {
								if (table.size() > 1) {
									document.add(title);
								}
								document.add(table);

								document.newPage();
								document.add(head);
								document.add(head_date);
								table = new Table(1);
								table.setAlignment(Cell.ALIGN_CENTER);
								table.setWidth(100);
								table.setPadding(2f);
								table.insertTable(headTable(newRows(colWidth),
										font_body, headTable));
								title.setLeading(5f);
								title.setSpacingAfter(0f);
								linePage = 25;
							}
							table.insertTable(detail);

							detail = new Table(colWidth.length);
							detail.setWidths(colWidth);
							detail.setAlignment(Cell.ALIGN_CENTER);
							detail.setWidth(100);
							detail.setPadding(2f);

							if (linePage < table.size()) {
								document.add(title);
								document.add(table);

								document.newPage();
								document.add(head);
								document.add(head_date);
								table = new Table(1);
								table.setAlignment(Cell.ALIGN_CENTER);
								table.setWidth(100);
								table.setPadding(2f);
								table.insertTable(headTable(newRows(colWidth),
										font_body, headTable));
								title.setLeading(5f);
								title.setSpacingAfter(0f);
								linePage = 25;
								
								sCell = new Cell(new Phrase(subDistrictTmp,
										font_body));
								sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
								sCell.setVerticalAlignment(Cell.ALIGN_TOP);
								sCell.setBorderWidthTop(0.1f);
								sCell.setBorderWidthBottom(0);
								sCell.setBorderWidthRight(0.1f);
								detail.addCell(sCell);
							} else {
								sCell = new Cell(new Phrase(" ", font_body));
								sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
								sCell.setVerticalAlignment(Cell.ALIGN_TOP);
								sCell.setBorderWidthTop(0);
								sCell.setBorderWidthBottom(0);
								sCell.setBorderWidthRight(0.1f);
								detail.addCell(sCell);
							}

							breedTypeIndex = 0;
						}
					}
				} else {
					if (breedTypeIndex < 2) {
						for (int i = breedTypeIndex; i < 2; i++) {
							Table col = new Table(1);
							Table tmp = new Table(5);
							tmp.setWidths(innerCol);
							sCell = new Cell(new Phrase(" ", font_body));
							sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
							sCell.setVerticalAlignment(Cell.ALIGN_TOP);
							tmp.addCell(sCell);

							sCell = new Cell(new Phrase(" ", font_body));
							sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
							sCell.setVerticalAlignment(Cell.ALIGN_TOP);
							tmp.addCell(sCell);

							sCell = new Cell(new Phrase(" ", font_body));
							sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
							sCell.setVerticalAlignment(Cell.ALIGN_TOP);
							tmp.addCell(sCell);

							sCell = new Cell(new Phrase(" ", font_body));
							sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
							sCell.setVerticalAlignment(Cell.ALIGN_TOP);
							tmp.addCell(sCell);

							sCell = new Cell(new Phrase(" ", font_body));
							sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
							sCell.setVerticalAlignment(Cell.ALIGN_TOP);
							tmp.addCell(sCell);

							col.insertTable(tmp);
							detail.insertTable(col);
						}
					}
				}
			}
			if (provList.size() <= 0) {
				document.add(table);
				title = new Paragraph("ไม่มีข้อมูล", font_title);
				title.setAlignment(Element.ALIGN_CENTER);
				document.add(title);
			} else {
				if (linePage < table.size()) {
					if (table.size() > 1) {
						document.add(title);
					}
					document.add(table);

					document.newPage();
					document.add(head);
					document.add(head_date);
					table = new Table(1);
					table.setAlignment(Cell.ALIGN_CENTER);
					table.setWidth(100);
					table.setPadding(2f);
					table.insertTable(headTable(newRows(colWidth), font_body,
							headTable));
					title.setLeading(5f);
					title.setSpacingAfter(0f);
					linePage = 25;
				}
				table.insertTable(detail);
				for (int i = 0; i < 3; i++) {
					if (sPlantWah[i] / 100 > 0) {
						sPlantNgan[i] += sPlantWah[i] / 100;
						sPlantWah[i] = sPlantWah[i] % 100;

					}
					if (sPlantNgan[i] / 4 > 0) {
						sPlantRai[i] += sPlantNgan[i] / 4;
						sPlantNgan[i] = sPlantNgan[i] % 4;
					}
				}

				String[] total = new String[] {
						String.format("%,d|", sQuantity[0]),
						String.format("%,d-%d-%d|", sPlantRai[0],
								sPlantNgan[0], sPlantWah[0]),
						String.format("%,.2f", sFRecord[0]),
						String.format("%,.2f", sHarvest[0]),
						"",
						String.format("%,d|", sQuantity[1]),
						String.format("%,d-%d-%d|", sPlantRai[1],
								sPlantNgan[1], sPlantWah[1]),
						String.format("%,.2f", sFRecord[1]),
						String.format("%,.2f", sHarvest[1]),
						"",
						String.format("%,d|", sQuantity[2]),
						String.format("%,d-%d-%d|", sPlantRai[2],
								sPlantNgan[2], sPlantWah[2]),
						String.format("%,.2f", sFRecord[2]),
						String.format("%,.2f", sHarvest[2]) };
				if (linePage < table.size()) {
					if (table.size() > 1) {
						document.add(title);
					}
					document.add(table);

					document.newPage();
					document.add(head);
					document.add(head_date);
					table = new Table(1);
					table.setAlignment(Cell.ALIGN_CENTER);
					table.setWidth(100);
					table.setPadding(2f);
					table.insertTable(headTable(newRows(colWidth),
							font_body, headTable));
					title.setLeading(5f);
					title.setSpacingAfter(0f);
					linePage = 25;
				}
				table.insertTable(totalTable(colTotalWidth, font_total,
						"รวม", total));
				for (int i = 0; i < 3; i++) {
					bQuantity[i] += sQuantity[i];
					bPlantRai[i] += sPlantRai[i];
					bPlantNgan[i] += sPlantNgan[i];
					bPlantWah[i] += sPlantWah[i];
					bFRecord[i] += sFRecord[i];
					bHarvest[i] += sHarvest[i];
				}

				sQuantity = new int[] { 0, 0, 0 };
				sPlantRai = new int[] { 0, 0, 0 };
				sPlantNgan = new int[] { 0, 0, 0 };
				sPlantWah = new int[] { 0, 0, 0 };
				sFRecord = new float[] { 0, 0, 0 };
				sHarvest = new float[] { 0, 0, 0 };
				
				for (int i = 0; i < 3; i++) {
					if (bPlantWah[i] / 100 > 0) {
						bPlantNgan[i] += bPlantWah[i] / 100;
						bPlantWah[i] = bPlantWah[i] % 100;

					}
					if (bPlantNgan[i] / 4 > 0) {
						bPlantRai[i] += bPlantNgan[i] / 4;
						bPlantNgan[i] = bPlantNgan[i] % 4;
					}
				}

				total = new String[] {
						String.format("%,d|", bQuantity[0]),
						String.format("%,d-%d-%d|", bPlantRai[0],
								bPlantNgan[0], bPlantWah[0]),
						String.format("%,.2f", bFRecord[0]),
						String.format("%,.2f", bHarvest[0]),
						"",
						String.format("%,d|", bQuantity[1]),
						String.format("%,d-%d-%d|", bPlantRai[1],
								bPlantNgan[1], bPlantWah[1]),
						String.format("%,.2f", bFRecord[1]),
						String.format("%,.2f", bHarvest[1]),
						"",
						String.format("%,d|", bQuantity[2]),
						String.format("%,d-%d-%d|", bPlantRai[2],
								bPlantNgan[2], bPlantWah[2]),
						String.format("%,.2f", bFRecord[2]),
						String.format("%,.2f", bHarvest[2]) };
				if (linePage < table.size()) {
					if (table.size() > 1) {
						document.add(title);
					}
					document.add(table);

					document.newPage();
					document.add(head);
					document.add(head_date);
					table = new Table(1);
					table.setAlignment(Cell.ALIGN_CENTER);
					table.setWidth(100);
					table.setPadding(2f);
					table.insertTable(headTable(newRows(colWidth),
							font_body, headTable));
					title.setLeading(5f);
					title.setSpacingAfter(0f);
					linePage = 25;
				}
				table.insertTable(totalTable(colTotalWidth, font_total,
						"รวมสาขา " + branchTmp, total));
				for (int i = 0; i < 3; i++) {
					pQuantity[i] += bQuantity[i];
					pPlantRai[i] += bPlantRai[i];
					pPlantNgan[i] += bPlantNgan[i];
					pPlantWah[i] += bPlantWah[i];
					pFRecord[i] += bFRecord[i];
					pHarvest[i] += bHarvest[i];
				}

				bQuantity = new int[] { 0, 0, 0 };
				bPlantRai = new int[] { 0, 0, 0 };
				bPlantNgan = new int[] { 0, 0, 0 };
				bPlantWah = new int[] { 0, 0, 0 };
				bFRecord = new float[] { 0, 0, 0 };
				bHarvest = new float[] { 0, 0, 0 };

				for (int i = 0; i < 3; i++) {
					if (pPlantWah[i] / 100 > 0) {
						pPlantNgan[i] += pPlantWah[i] / 100;
						pPlantWah[i] = pPlantWah[i] % 100;

					}
					if (pPlantNgan[i] / 4 > 0) {
						pPlantRai[i] += pPlantNgan[i] / 4;
						pPlantNgan[i] = pPlantNgan[i] % 4;
					}
				}

				total = new String[] {
						String.format("%,d|", pQuantity[0]),
						String.format("%,d-%d-%d|", pPlantRai[0],
								pPlantNgan[0], pPlantWah[0]),
						String.format("%,.2f", pFRecord[0]),
						String.format("%,.2f", pHarvest[0]),
						"",
						String.format("%,d|", pQuantity[1]),
						String.format("%,d-%d-%d|", pPlantRai[1],
								pPlantNgan[1], pPlantWah[1]),
						String.format("%,.2f", pFRecord[1]),
						String.format("%,.2f", pHarvest[1]),
						"",
						String.format("%,d|", pQuantity[2]),
						String.format("%,d-%d-%d|", pPlantRai[2],
								pPlantNgan[2], pPlantWah[2]),
						String.format("%,.2f", pFRecord[2]),
						String.format("%,.2f", pHarvest[2]) };
				if (linePage < table.size()) {
					if (table.size() > 1) {
						document.add(title);
					}
					document.add(table);

					document.newPage();
					document.add(head);
					document.add(head_date);
					table = new Table(1);
					table.setAlignment(Cell.ALIGN_CENTER);
					table.setWidth(100);
					table.setPadding(2f);
					table.insertTable(headTable(newRows(colWidth), font_body,
							headTable));
					title.setLeading(5f);
					title.setSpacingAfter(0f);
					linePage = 25;
				}
				table.insertTable(totalTable(colTotalWidth, font_total,
						"ยอดรวมจังหวัด" + proviceTmp, total));

				float[] endcolWidth = new float[] { 53, 45, 45 };
				Table avgtb = new Table(endcolWidth.length);
				avgtb.setWidths(endcolWidth);
				for (int i = 0; i < 3; i++) {
					String endline = "ผลผลิตจริงเฉลี่ยต่อไร่ของ"
							+ breedTypeHead[i];
					float avg = pHarvest[i] / pPlantRai[i];
					Cell sCell = new Cell(new Phrase(String.format(
							"%s %,.2f กก.", endline, avg > 0 ? avg : 0f),
							font_total));
					sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
					sCell.setVerticalAlignment(Cell.ALIGN_TOP);
					avgtb.addCell(sCell);
				}
				if (linePage < table.size()) {
					if (table.size() > 1) {
						document.add(title);
					}
					document.add(table);

					document.newPage();
					document.add(head);
					document.add(head_date);
					table = new Table(1);
					table.setAlignment(Cell.ALIGN_CENTER);
					table.setWidth(100);
					table.setPadding(2f);
					table.insertTable(headTable(newRows(colWidth), font_body,
							headTable));
					title.setLeading(5f);
					title.setSpacingAfter(0f);
					linePage = 25;
				}
				table.insertTable(avgtb);
				document.add(title);
				document.add(table);
				linePage -= table.size();
				
				avgtb = new Table(endcolWidth.length);
				avgtb.setAlignment(Cell.ALIGN_CENTER);
				avgtb.setWidth(100);
				avgtb.setPadding(2f);
				avgtb.setWidths(endcolWidth);
				avgtb.setBorderColor(Color.white);
				int maxTmp = 0;
				for (int i = 0; i < 3; i++) {
					if (maxTmp < breedGroupBranch[i].size() + breedGroupProv[i].size()) {
						maxTmp = breedGroupBranch[i].size() + breedGroupProv[i].size();
					}
				}
				for (int j = 0; j < maxTmp; j++) {
					for (int k = 0; k < 3; k++) {
						if (breedGroupBranch[k].size()>j) {
							String endline = "ผลผลิตจริงเฉลี่ยต่อไร่ของ" + breedGroupBranch[k].get(j);
							float harvest = breedGroupValue.get(breedGroupBranch[k].get(j)+"Harvest");
							float rai = breedGroupValue.get(breedGroupBranch[k].get(j)+"Rai");
							float ngan = breedGroupValue.get(breedGroupBranch[k].get(j)+"Ngan");
							float wah = breedGroupValue.get(breedGroupBranch[k].get(j)+"Wah");
							if (wah / 100 > 0) {
								ngan += wah / 100;
								wah = wah % 100;

							}
							if (ngan / 4 > 0) {
								rai += ngan / 4;
								ngan = ngan % 4;
							}
							float avg = harvest/rai;
							Cell sCell = new Cell(new Phrase(String.format(
									"%s %,.2f กก.", endline, avg > 0 ? avg : 0f),
									font_total));
							sCell.setHorizontalAlignment(Cell.ALIGN_LEFT);
							sCell.setVerticalAlignment(Cell.ALIGN_TOP);
							sCell.setBorderColor(Color.white);
							avgtb.addCell(sCell);
						}else if(breedGroupProv[k].size()>(j - breedGroupBranch[k].size())){
							String endline = "ผลผลิตจริงเฉลี่ยต่อไร่ของ" + breedGroupProv[k].get(j - breedGroupBranch[k].size());
							float harvest = breedGroupValue.get(breedGroupProv[k].get(j - breedGroupBranch[k].size())+"Harvest");
							float rai = breedGroupValue.get(breedGroupProv[k].get(j - breedGroupBranch[k].size())+"Rai");
							float ngan = breedGroupValue.get(breedGroupProv[k].get(j - breedGroupBranch[k].size())+"Ngan");
							float wah = breedGroupValue.get(breedGroupProv[k].get(j - breedGroupBranch[k].size())+"Wah");
							if (wah / 100 > 0) {
								ngan += wah / 100;
								wah = wah % 100;

							}
							if (ngan / 4 > 0) {
								rai += ngan / 4;
								ngan = ngan % 4;
							}
							float avg = harvest/rai;
							Cell sCell = new Cell(new Phrase(String.format(
									"%s %,.2f กก.", endline, avg > 0 ? avg : 0f),
									font_total));
							sCell.setHorizontalAlignment(Cell.ALIGN_LEFT);
							sCell.setVerticalAlignment(Cell.ALIGN_TOP);
							sCell.setBorderColor(Color.white);
							avgtb.addCell(sCell);
						}else{
							Cell sCell = new Cell(new Phrase(" ",font_total));
							sCell.setHorizontalAlignment(Cell.ALIGN_LEFT);
							sCell.setVerticalAlignment(Cell.ALIGN_TOP);
							sCell.setBorderColor(Color.white);
							avgtb.addCell(sCell);
						}
					}
					if (linePage < avgtb.size()) {
						document.add(avgtb);

						document.newPage();
						document.add(head);
						document.add(head_date);
						avgtb = new Table(endcolWidth.length);
						avgtb.setAlignment(Cell.ALIGN_CENTER);
						avgtb.setWidth(100);
						avgtb.setPadding(2f);
						avgtb.setWidths(endcolWidth);
						avgtb.setBorderColor(Color.white);
						
						linePage = 27;
					}
				}
				document.add(avgtb);
			}
		} catch (Exception e) {
			document.add(new Paragraph("เกิดการดึงข้อมูลผิดพลาด", font_title));
			e.printStackTrace();
			log.error(e, e);
		} finally {
			sf.getCurrentSession().close();
		}
		return document;
	}

	private Document ReportR106(SessionFactory sf, Document document,
			Font font_title, Font font_body, Font font_total, ReportForm eform)
			throws DocumentException {
		try {
			sf.getCurrentSession().beginTransaction();
			Paragraph head = new Paragraph(
					"รายงานแสดงข้อมูลเปรียบเทียบผลผลิตจริงที่ได้รับกับต้นทุนการผลิตรวม ตามปีการผลิต "
							+ eform.getPlantYear() + "/" + eform.getPlantNo(),
					font_title);
			head.setAlignment(Element.ALIGN_CENTER);
			document.add(head);

			Paragraph head_date = new Paragraph("สิ้นสุด ณ วันที่ "
					+ dateThai(eform.getEffectiveDate(), false), font_title);
			head_date.setAlignment(Element.ALIGN_CENTER);
			head_date.setSpacingBefore(5f);
			document.add(head_date);

			Table table = new Table(1);
			table.setAlignment(Cell.ALIGN_CENTER);
			table.setWidth(100);
			table.setPadding(2f);
			float[] colWidth, colTotalWidth;
			colWidth = new float[] { 15, 15, 30, 20, 15, 10, 20, 30 };
			colTotalWidth = new float[] { 80, 15, 10, 10, 10, 10, 10, 10 };
			String[] headTable = new String[] {
					"ตำบลที่ตั้งแปลง",
					"บัตรประจำตัว\nประชาชน\n(เลขที่)",
					"ชื่อ-สกุล",
					"กลุ่มพันธุ์",
					"เนื้อที่เพาะปลูก\nไร่-งาน-วา",
					"ประมาณการ\nผลผลิต\n(กก.)",
					"|ผลผลิตจริงที่ได้รับ|ผลผลิตรวม\n(กก.)|ผลผลิต\nเฉลี่ยต่อไร่\n(กก.)",
					"|ต้นทุนการผลิตรวม|ต้นทุนรวม\n(บาท)|ต้นทุนต่อไร่\n(บาท)|ต้นทุนต่อ กก.\n(บาท)" };
			table
					.insertTable(headTable(newRows(colWidth), font_body,
							headTable));

			ReportHome rhome = new ReportHome();
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			Date strDate = df.parse(eform.getEffectiveDate());
			List provList = rhome.findR106ByCriteria(eform.getPlantYear(),
					eform.getPlantNo(), strDate, eform.getProvinceNo(), eform
							.getBranchCode(), eform.getBreedTypeId(), eform
							.getBreedGroupId());
			if (eform.getBranchCode() > 0) {
				BranchHome bhome = new BranchHome();
				List<Branch> pbList = bhome
						.findByPBranch(eform.getBranchCode());
				List<Branch> pbranchCode = new ArrayList();
				if (pbList.size() > 0) {
					pbranchCode
							.addAll(getPBranch(pbList, eform.getBranchCode()));
					for (int i = 0; i < pbranchCode.size(); i++) {
						provList.addAll(rhome.findR106ByCriteria(eform
								.getPlantYear(), eform.getPlantNo(), strDate,
								0, pbranchCode.get(i).getBranchCode(), eform
										.getBreedTypeId(), eform
										.getBreedGroupId()));
					}
				}
			}
			String branchTmp = "", subDistrictTmp = "", provinceTmp = "";
			int dtPlantRai = 0, dtPlantNgan = 0, dtPlantWah = 0;
			int btPlantRai = 0, btPlantNgan = 0, btPlantWah = 0;
			int ptPlantRai = 0, ptPlantNgan = 0, ptPlantWah = 0;
			float dtFRecord = 0, dtPRecord = 0, dtCost = 0;
			float btFRecord = 0, btPRecord = 0, btCost = 0;
			float ptFRecord = 0, ptPRecord = 0, ptCost = 0;
			int linePage = 22;
			Paragraph title = new Paragraph();
			if (provList.size() <= 0) {
				document.add(table);
				title = new Paragraph("ไม่มีข้อมูล", font_title);
				title.setAlignment(Element.ALIGN_CENTER);
				document.add(title);
			}
			boolean newPage = true;
			int frist = 1;
			for (int prov = 0; prov < provList.size(); prov++) {
				Object[] obj = ((Object[]) provList.get(prov));

				if (!branchTmp.equals(obj[0].toString())) {
					title = new Paragraph("ธ.ก.ส. จังหวัด" + obj[13].toString()
							+ " " + obj[0].toString(), font_total);
					title.setAlignment(Element.ALIGN_LEFT);
					title.setLeading(5f);
					title.setSpacingBefore(20f);
					branchTmp = obj[0].toString();
					linePage -= 2;
					newPage = true;
				}
				Table detail = new Table(colWidth.length);
				detail.setWidths(colWidth);
				detail.setAlignment(Cell.ALIGN_CENTER);
				detail.setWidth(100);
				detail.setPadding(2f);

				if (linePage < table.size()) {
					if (table.size() > 1) {
						document.add(title);
					}
					document.add(table);

					document.newPage();
					document.add(head);
					document.add(head_date);
					table = new Table(1);
					table.setAlignment(Cell.ALIGN_CENTER);
					table.setWidth(100);
					table.setPadding(2f);
					table.insertTable(headTable(newRows(colWidth), font_body,
							headTable));
					title.setLeading(5f);
					title.setSpacingAfter(0f);
					linePage = 20;
					newPage = true;
				}

				Cell sCell = new Cell();
				if (subDistrictTmp.equals(obj[1].toString()) && !newPage) {
					sCell = new Cell(new Phrase("", font_body));
					sCell.setBorderWidthTop(0);
					sCell.setBorderWidthBottom(0);
					sCell.setBorderWidthRight(0.1f);
					detail.addCell(sCell);

				} else {
					sCell = new Cell(new Phrase(obj[1].toString(), font_body));
					sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
					sCell.setVerticalAlignment(Cell.ALIGN_TOP);
					sCell.setBorderColorTop(Color.GRAY);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
					sCell.setBorderWidthBottom(0);
					sCell.setBorderWidthRight(0.1f);
					detail.addCell(sCell);
					newPage = false;
				}

				sCell = new Cell(new Phrase(obj[2].toString(), font_body));
				sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
				sCell.setVerticalAlignment(Cell.ALIGN_TOP);
				sCell.setBorderColorTop(Color.GRAY);
				sCell.setBorderWidthTop(0.1f);
				sCell.setBorderWidthRight(0.1f);
				detail.addCell(sCell);

				sCell = new Cell(new Phrase(" " + obj[3].toString()
						+ obj[4].toString() + " " + obj[5].toString(),
						font_body));
				sCell.setHorizontalAlignment(Cell.ALIGN_LEFT);
				sCell.setVerticalAlignment(Cell.ALIGN_TOP);
				sCell.setBorderColorTop(Color.GRAY);
				sCell.setBorderWidthTop(0.1f);
				sCell.setBorderWidthRight(0.1f);
				detail.addCell(sCell);

				sCell = new Cell(new Phrase(obj[6].toString(), font_body));
				sCell.setHorizontalAlignment(Cell.ALIGN_LEFT);
				sCell.setVerticalAlignment(Cell.ALIGN_TOP);
				sCell.setBorderColorTop(Color.GRAY);
				sCell.setBorderWidthTop(0.1f);
				sCell.setBorderWidthRight(0.1f);
				detail.addCell(sCell);

				// Table tmp = new Table(3);
				int rai = Integer.parseInt(obj[7].toString());
				int ngan = Integer.parseInt(obj[8].toString());
				int wah = Integer.parseInt(obj[9].toString());

				if (wah / 100 > 0) {
					ngan += wah / 100;
					wah = wah % 100;

				}
				if (ngan / 4 > 0) {
					rai += ngan / 4;
					ngan = ngan % 4;
				}

				sCell = new Cell(new Phrase(String.format("%,d-%d-%d", rai,
						ngan, wah), font_body));
				sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
				sCell.setVerticalAlignment(Cell.ALIGN_TOP);
				sCell.setBorderColorTop(Color.GRAY);
				sCell.setBorderWidthTop(0.1f);
				sCell.setBorderWidthRight(0.1f);
				detail.addCell(sCell);

				sCell = new Cell(new Phrase(String.format("%,.2f", Float
						.parseFloat(obj[10].toString())), font_body));
				sCell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
				sCell.setVerticalAlignment(Cell.ALIGN_TOP);
				sCell.setBorderColorTop(Color.GRAY);
				sCell.setBorderWidthTop(0.1f);
				sCell.setBorderWidthRight(0.1f);
				detail.addCell(sCell);

				Table tmp = new Table(2);
				sCell = new Cell(new Phrase(String.format("%,.2f", Float
						.parseFloat(obj[11] != null ? obj[11].toString()
								: "0.00")), font_body));
				sCell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
				sCell.setVerticalAlignment(Cell.ALIGN_TOP);
				sCell.setBorderColorTop(Color.GRAY);
				sCell.setBorderWidthTop(0.1f);
				sCell.setBorderWidthRight(0.1f);
				tmp.addCell(sCell);

				float avgCorp = Float.parseFloat(obj[11] != null ? obj[11]
						.toString() : "0.00")
						/ rai;
				sCell = new Cell(new Phrase(String.format("%,.2f", avgCorp),
						font_body));
				sCell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
				sCell.setVerticalAlignment(Cell.ALIGN_TOP);
				sCell.setBorderColorTop(Color.GRAY);
				sCell.setBorderWidthTop(0.1f);
				sCell.setBorderWidthRight(0.1f);
				tmp.addCell(sCell);
				detail.insertTable(tmp);

				tmp = new Table(3);
				sCell = new Cell(new Phrase(String.format("%,.2f", Float
						.parseFloat(obj[12].toString())), font_body));
				sCell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
				sCell.setVerticalAlignment(Cell.ALIGN_TOP);
				sCell.setBorderColorTop(Color.GRAY);
				sCell.setBorderWidthTop(0.1f);
				sCell.setBorderWidthRight(0.1f);
				tmp.addCell(sCell);

				float avgCost = Float.parseFloat(obj[12].toString()) / rai;
				sCell = new Cell(new Phrase(String.format("%,.2f", avgCost),
						font_body));
				sCell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
				sCell.setVerticalAlignment(Cell.ALIGN_TOP);
				sCell.setBorderColorTop(Color.GRAY);
				sCell.setBorderWidthTop(0.1f);
				sCell.setBorderWidthRight(0.1f);
				tmp.addCell(sCell);

				sCell = new Cell(new Phrase(String.format("%,.2f",
						avgCorp > 0 ? avgCost / avgCorp : 0f), font_body));
				sCell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
				sCell.setVerticalAlignment(Cell.ALIGN_TOP);
				sCell.setBorderColorTop(Color.GRAY);
				sCell.setBorderWidthTop(0.1f);
				sCell.setBorderWidthRight(0.1f);
				tmp.addCell(sCell);
				detail.insertTable(tmp);

				table.insertTable(detail);

				dtPlantRai += rai;
				dtPlantNgan += ngan;
				dtPlantWah += wah;
				dtFRecord += Float.parseFloat(obj[10].toString());
				dtPRecord += Float.parseFloat(obj[11] != null ? obj[11]
						.toString() : "0.00");
				dtCost += Float.parseFloat(obj[12].toString());

				subDistrictTmp = obj[1].toString();
				provinceTmp = obj[13].toString();

				if (prov + 1 != provList.size()) {
					if (!subDistrictTmp.equals(((Object[]) provList
							.get(prov + 1))[1].toString())
							|| !branchTmp.equals(((Object[]) provList
									.get(prov + 1))[0].toString())) {
						calRai = totalRai(dtPlantRai, dtPlantNgan, dtPlantWah);
						String[] total = new String[] {
								String.format("%,d-%d-%d|", calRai[0],
										calRai[1], calRai[2]),
								String.format("%,.2f", dtFRecord),
								String.format("%,.2f", dtPRecord),
								String.format("%,.2f", dtPRecord / dtPlantRai),
								String.format("%,.2f", dtCost),
								String.format("%,.2f", dtCost / dtPlantRai),
								String
										.format(
												"%,.2f",
												(dtPRecord / dtPlantRai) > 0 ? (dtCost / dtPlantRai)
														/ (dtPRecord / dtPlantRai)
														: 0f) };
						if (linePage < table.size()) {
							if (table.size() > 1) {
								document.add(title);
							}
							document.add(table);

							document.newPage();
							document.add(head);
							document.add(head_date);
							table = new Table(1);
							table.setAlignment(Cell.ALIGN_CENTER);
							table.setWidth(100);
							table.setPadding(2f);
							table.insertTable(headTable(newRows(colWidth),
									font_body, headTable));
							title.setLeading(5f);
							title.setSpacingAfter(0f);
							linePage = 20;
							newPage = true;
						}
						table.insertTable(totalTable(colTotalWidth, font_total,
								"รวม", total));
						btPlantRai += dtPlantRai;
						btPlantNgan += dtPlantNgan;
						btPlantWah += dtPlantWah;
						btFRecord += dtFRecord;
						btPRecord += dtPRecord;
						btCost += dtCost;
						dtPlantRai = 0;
						dtPlantNgan = 0;
						dtPlantWah = 0;
						dtPlantNgan = 0;
						dtPlantWah = 0;
						dtFRecord = 0;
						dtPRecord = 0;
						dtCost = 0;
					}

					if (!branchTmp
							.equals(((Object[]) provList.get(prov + 1))[0]
									.toString())) {
						calRai = totalRai(btPlantRai, btPlantNgan, btPlantWah);
						String[] totalBran = new String[] {
								String.format("%,d-%d-%d|", calRai[0],
										calRai[1], calRai[2]),
								String.format("%,.2f", btFRecord),
								String.format("%,.2f", btPRecord),
								String.format("%,.2f", btPRecord / btPlantRai),
								String.format("%,.2f", btCost),
								String.format("%,.2f", btCost / btPlantRai),
								String
										.format(
												"%,.2f",
												(btPRecord / btPlantRai) > 0 ? (btCost / btPlantRai)
														/ (btPRecord / btPlantRai)
														: 0f) };
						if (linePage < table.size()) {
							if (table.size() > 1) {
								document.add(title);
							}
							document.add(table);

							document.newPage();
							document.add(head);
							document.add(head_date);
							table = new Table(1);
							table.setAlignment(Cell.ALIGN_CENTER);
							table.setWidth(100);
							table.setPadding(2f);
							table.insertTable(headTable(newRows(colWidth),
									font_body, headTable));
							title.setLeading(5f);
							title.setSpacingAfter(0f);
							linePage = 20;
							newPage = true;
						}
						table.insertTable(totalTable(colTotalWidth, font_total,
								"ยอดรวมสาขา " + branchTmp, totalBran));
						ptPlantRai += btPlantRai;
						ptPlantNgan += btPlantNgan;
						ptPlantWah += btPlantWah;
						ptFRecord += btFRecord;
						ptPRecord += btPRecord;
						ptCost += btCost;
						btPlantRai = 0;
						btPlantNgan = 0;
						btPlantWah = 0;
						btFRecord = 0;
						btPRecord = 0;
						btCost = 0;

						if (!provinceTmp.equals(((Object[]) provList
								.get(prov + 1))[13].toString())) {
							calRai = totalRai(ptPlantRai, ptPlantNgan,
									ptPlantWah);
							String[] totalProv = new String[] {
									String.format("%,d-%d-%d|", calRai[0],
											calRai[1], calRai[2]),
									String.format("%,.2f", ptFRecord),
									String.format("%,.2f", ptPRecord),
									String.format("%,.2f", ptPRecord
											/ ptPlantRai),
									String.format("%,.2f", ptCost),
									String.format("%,.2f", ptCost / ptPlantRai),
									String
											.format(
													"%,.2f",
													(ptPRecord / ptPlantRai) > 0 ? (ptCost / ptPlantRai)
															/ (ptPRecord / ptPlantRai)
															: 0f) };
							if (linePage < table.size()) {
								if (table.size() > 1) {
									document.add(title);
								}
								document.add(table);

								document.newPage();
								document.add(head);
								document.add(head_date);
								table = new Table(1);
								table.setAlignment(Cell.ALIGN_CENTER);
								table.setWidth(100);
								table.setPadding(2f);
								table.insertTable(headTable(newRows(colWidth),
										font_body, headTable));
								title.setLeading(5f);
								title.setSpacingAfter(0f);
								linePage = 20;
								newPage = true;
							}
							table.insertTable(totalTable(colTotalWidth,
									font_total, "ยอดรวมจังหวัด "
											+ obj[13].toString(), totalProv));
							ptPlantRai = 0;
							ptPlantNgan = 0;
							ptPlantWah = 0;
							ptFRecord = 0;
							ptPRecord = 0;
							ptCost = 0;
						}

						if (table.size() > 1) {
							document.add(title);
							document.add(table);
							linePage -= table.size();
							table = new Table(1);
							table.setAlignment(Cell.ALIGN_CENTER);
							table.setWidth(100);
							table.setPadding(2f);
						}
					}

				} else {
					calRai = totalRai(dtPlantRai, dtPlantNgan, dtPlantWah);
					String[] total = new String[] {
							String.format("%,d-%d-%d|", calRai[0], calRai[1],
									calRai[2]),
							String.format("%,.2f", dtFRecord),
							String.format("%,.2f", dtPRecord),
							String.format("%,.2f", dtPRecord / dtPlantRai),
							String.format("%,.2f", dtCost),
							String.format("%,.2f", dtCost / dtPlantRai),
							String
									.format(
											"%,.2f",
											(dtPRecord / dtPlantRai) > 0 ? (dtCost / dtPlantRai)
													/ (dtPRecord / dtPlantRai)
													: 0f) };

					if (linePage < table.size()) {
						if (table.size() > 1) {
							document.add(title);
						}
						document.add(table);

						document.newPage();
						document.add(head);
						document.add(head_date);
						table = new Table(1);
						table.setAlignment(Cell.ALIGN_CENTER);
						table.setWidth(100);
						table.setPadding(2f);
						table.insertTable(headTable(newRows(colWidth),
								font_body, headTable));
						title.setLeading(5f);
						title.setSpacingAfter(0f);
						linePage = 20;
						newPage = true;
					}
					table.insertTable(totalTable(colTotalWidth, font_total,
							"รวม", total));
					btPlantRai += dtPlantRai;
					btPlantNgan += dtPlantNgan;
					btPlantWah += dtPlantWah;
					btFRecord += dtFRecord;
					btPRecord += dtPRecord;
					btCost += dtCost;
					dtPlantRai = 0;
					dtPlantNgan = 0;
					dtPlantWah = 0;
					dtPlantNgan = 0;
					dtPlantWah = 0;
					dtFRecord = 0;
					dtPRecord = 0;
					dtCost = 0;
					calRai = totalRai(btPlantRai, btPlantNgan, btPlantWah);
					String[] totalBran = new String[] {
							String.format("%,d-%d-%d|", calRai[0], calRai[1],
									calRai[2]),
							String.format("%,.2f", btFRecord),
							String.format("%,.2f", btPRecord),
							String.format("%,.2f", btPRecord / btPlantRai),
							String.format("%,.2f", btCost),
							String.format("%,.2f", btCost / btPlantRai),
							String
									.format(
											"%,.2f",
											(btPRecord / btPlantRai) > 0 ? (btCost / btPlantRai)
													/ (btPRecord / btPlantRai)
													: 0f) };
					if (linePage < table.size()) {
						if (table.size() > 1) {
							document.add(title);
						}
						document.add(table);

						document.newPage();
						document.add(head);
						document.add(head_date);
						table = new Table(1);
						table.setAlignment(Cell.ALIGN_CENTER);
						table.setWidth(100);
						table.setPadding(2f);
						table.insertTable(headTable(newRows(colWidth),
								font_body, headTable));
						title.setLeading(5f);
						title.setSpacingAfter(0f);
						linePage = 20;
						newPage = true;
					}
					table.insertTable(totalTable(colTotalWidth, font_total,
							"ยอดรวมสาขา " + branchTmp, totalBran));
					ptPlantRai += btPlantRai;
					ptPlantNgan += btPlantNgan;
					ptPlantWah += btPlantWah;
					ptFRecord += btFRecord;
					ptPRecord += btPRecord;
					ptCost += btCost;
					btPlantRai = 0;
					btPlantNgan = 0;
					btPlantWah = 0;
					btFRecord = 0;
					btPRecord = 0;
					btCost = 0;

					calRai = totalRai(ptPlantRai, ptPlantNgan, ptPlantWah);
					String[] totalProv = new String[] {
							String.format("%,d-%d-%d|", calRai[0], calRai[1],
									calRai[2]),
							String.format("%,.2f", ptFRecord),
							String.format("%,.2f", ptPRecord),
							String.format("%,.2f", ptPRecord / ptPlantRai),
							String.format("%,.2f", ptCost),
							String.format("%,.2f", ptCost / ptPlantRai),
							String
									.format(
											"%,.2f",
											(ptPRecord / ptPlantRai) > 0 ? (ptCost / ptPlantRai)
													/ (ptPRecord / ptPlantRai)
													: 0f) };
					if (linePage < table.size()) {
						if (table.size() > 1) {
							document.add(title);
						}
						document.add(table);

						document.newPage();
						document.add(head);
						document.add(head_date);
						table = new Table(1);
						table.setAlignment(Cell.ALIGN_CENTER);
						table.setWidth(100);
						table.setPadding(2f);
						table.insertTable(headTable(newRows(colWidth),
								font_body, headTable));
						title.setLeading(5f);
						title.setSpacingAfter(0f);
						linePage = 20;
						newPage = true;
					}
					table.insertTable(totalTable(colTotalWidth, font_total,
							"ยอดรวมจังหวัด " + obj[13].toString(), totalProv));

					document.add(title);
					document.add(table);
				}
			}
		} catch (Exception e) {
			document.add(new Paragraph("เกิดการดึงข้อมูลผิดพลาด", font_title));
			e.printStackTrace();
			log.error(e, e);
		} finally {
			sf.getCurrentSession().close();
		}
		return document;
	}

	private Document ReportR107(SessionFactory sf, Document document,
			Font font_title, Font font_body, Font font_total, ReportForm eform)
			throws DocumentException {
		try {
			sf.getCurrentSession().beginTransaction();
			Paragraph head = new Paragraph(
					"รายงานแสดงข้อมูลเปรียบเทียบผลผลิตจริงที่ได้รับกับผลตอบแทนสุทธิ ตามปีการผลิต "
							+ eform.getPlantYear() + "/" + eform.getPlantNo(),
					font_title);
			head.setAlignment(Element.ALIGN_CENTER);
			document.add(head);

			Paragraph head_date = new Paragraph("สิ้นสุด ณ วันที่ "
					+ dateThai(eform.getEffectiveDate(), false), font_title);
			head_date.setAlignment(Element.ALIGN_CENTER);
			head_date.setSpacingBefore(5f);
			document.add(head_date);

			Table table = new Table(1);
			table.setAlignment(Cell.ALIGN_CENTER);
			table.setWidth(100);
			table.setPadding(2f);
			float[] colWidth, colTotalWidth;
			colWidth = new float[] { 11, 15, 20, 17, 12, 10, 7, 20, 24, 36 };
			colTotalWidth = new float[] { 63, 12, 10, 7, 10, 10, 12, 12, 12,
					12, 12 };
			String[] headTable = new String[] {
					"ตำบลที่\nตั้งแปลง",
					"บัตรประจำตัว\nประชาชน\n(เลขที่)",
					"ชื่อ-สกุล",
					"กลุ่มพันธุ์",
					"เนื้อที่เพาะปลูก\nไร่-งาน-วา",
					"ต้นทุนการ\nผลิตรวม\n(บาท)",
					"ราคา\nขาย\nต่อ\nกก.\n(บาท)",
					"|ผลผลิตจริงที่ได้รับ|ผลผลิต\nรวม\n(กก.)|ผลผลิต\nเฉลี่ยต่อไร่\n(กก.)",
					"|ผลผลิตที่จำหน่ายได้|รวม\n(บาท)|เฉลี่ยต่อไร่\n(บาท)",
					"|ผลตอบแทนสุทธิ|ผลตอบ\nแทนรวม\n(บาท)|ผลตอบ\nแทนสุทธิ\nต่อกก.\n(บาท)|ผลตอบ\nแทนสุทธิ\nต่อไร่\n(บาท)" };
			table
					.insertTable(headTable(newRows(colWidth), font_body,
							headTable));

			ReportHome rhome = new ReportHome();
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			List provList = rhome.findR107ByCriteria(eform.getPlantYear(),
					eform.getPlantNo(), df.parse(eform.getEffectiveDate()),
					eform.getProvinceNo(), eform.getBranchCode(), eform
							.getBreedTypeId(), eform.getBreedGroupId());
			if (eform.getBranchCode() > 0) {
				BranchHome bhome = new BranchHome();
				List<Branch> pbList = bhome
						.findByPBranch(eform.getBranchCode());
				List<Branch> pbranchCode = new ArrayList();
				if (pbList.size() > 0) {
					pbranchCode
							.addAll(getPBranch(pbList, eform.getBranchCode()));
					for (int i = 0; i < pbranchCode.size(); i++) {
						provList.addAll(rhome.findR107ByCriteria(eform
								.getPlantYear(), eform.getPlantNo(), df
								.parse(eform.getEffectiveDate()), 0,
								pbranchCode.get(i).getBranchCode(), eform
										.getBreedTypeId(), eform
										.getBreedGroupId()));
					}
				}
			}
			String branchTmp = "", subDistrictTmp = "", provinceTmp = "";
			int dtPlantRai = 0, dtPlantNgan = 0, dtPlantWah = 0;
			int btPlantRai = 0, btPlantNgan = 0, btPlantWah = 0;
			int ptPlantRai = 0, ptPlantNgan = 0, ptPlantWah = 0;

			float dtFRecord = 0, dtSell = 0, dtCost = 0, dtSumSell = 0;
			float btFRecord = 0, btSell = 0, btCost = 0, btSumSell = 0;
			float ptFRecord = 0, ptSell = 0, ptCost = 0, ptSumSell = 0;
			int linePage = 26;
			Paragraph title = new Paragraph();
			if (provList.size() <= 0) {
				document.add(table);
				title = new Paragraph("ไม่มีข้อมูล", font_title);
				title.setAlignment(Element.ALIGN_CENTER);
				document.add(title);
			}
			boolean newPage = true;
			int frist = 1;
			for (int prov = 0; prov < provList.size(); prov++) {
				Object[] obj = ((Object[]) provList.get(prov));

				if (!branchTmp.equals(obj[0].toString())) {
					font_title = new Font(font_total.getBaseFont(), 9F, 1);
					title = new Paragraph("ธ.ก.ส. จังหวัด" + obj[13].toString()
							+ " " + obj[0].toString(), font_title);
					title.setAlignment(Element.ALIGN_LEFT);
					title.setLeading(5f);
					title.setSpacingBefore(20f);
					branchTmp = obj[0].toString();
					linePage -= 2;
					newPage = true;
				}

				Table detail = new Table(colWidth.length);
				detail.setWidths(colWidth);
				detail.setAlignment(Cell.ALIGN_CENTER);
				detail.setWidth(100);
				detail.setPadding(2f);

				if (linePage < table.size()) {
					if (table.size() > 1) {
						document.add(title);
					}
					document.add(table);

					document.newPage();
					document.add(head);
					document.add(head_date);
					table = new Table(1);
					table.setAlignment(Cell.ALIGN_CENTER);
					table.setWidth(100);
					table.setPadding(2f);
					table.insertTable(headTable(newRows(colWidth), font_body,
							headTable));
					title.setLeading(5f);
					title.setSpacingAfter(0f);
					linePage = 24;
					newPage = true;
				}
				Cell sCell = new Cell();
				if (subDistrictTmp.equals(obj[1].toString()) && !newPage) {
					sCell = new Cell(new Phrase("", font_body));
					sCell.setBorderWidthTop(0);
					sCell.setBorderWidthBottom(0);
					sCell.setBorderWidthRight(0.1f);
					detail.addCell(sCell);

				} else {
					sCell = new Cell(new Phrase(obj[1].toString(), font_body));
					sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
					sCell.setVerticalAlignment(Cell.ALIGN_TOP);
					sCell.setBorderColorTop(Color.GRAY);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
					sCell.setBorderWidthBottom(0);
					sCell.setBorderWidthRight(0.1f);
					detail.addCell(sCell);
					newPage = false;
				}

				sCell = new Cell(new Phrase(obj[2].toString(), font_body));
				sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
				sCell.setVerticalAlignment(Cell.ALIGN_TOP);
				sCell.setBorderColorTop(Color.GRAY);
				sCell.setBorderWidthTop(0.1f);
				sCell.setBorderWidthRight(0.1f);
				detail.addCell(sCell);

				sCell = new Cell(new Phrase(" " + obj[3].toString()
						+ obj[4].toString() + " " + obj[5].toString(),
						font_body));
				sCell.setHorizontalAlignment(Cell.ALIGN_LEFT);
				sCell.setVerticalAlignment(Cell.ALIGN_TOP);
				sCell.setBorderColorTop(Color.GRAY);
				sCell.setBorderWidthTop(0.1f);
				sCell.setBorderWidthRight(0.1f);
				detail.addCell(sCell);

				sCell = new Cell(new Phrase(obj[6].toString(), font_body));
				sCell.setHorizontalAlignment(Cell.ALIGN_LEFT);
				sCell.setVerticalAlignment(Cell.ALIGN_TOP);
				sCell.setBorderColorTop(Color.GRAY);
				sCell.setBorderWidthTop(0.1f);
				sCell.setBorderWidthRight(0.1f);
				detail.addCell(sCell);

				// Table tmp = new Table(3);
				int rai = Integer.parseInt(obj[7].toString());
				int ngan = Integer.parseInt(obj[8].toString());
				int wah = Integer.parseInt(obj[9].toString());

				if (wah / 100 > 0) {
					ngan += wah / 100;
					wah = wah % 100;

				}
				if (ngan / 4 > 0) {
					rai += ngan / 4;
					ngan = ngan % 4;
				}

				sCell = new Cell(new Phrase(String.format("%,d-%d-%d", rai,
						ngan, wah), font_body));
				sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
				sCell.setVerticalAlignment(Cell.ALIGN_TOP);
				sCell.setBorderColorTop(Color.GRAY);
				sCell.setBorderWidthTop(0.1f);
				sCell.setBorderWidthRight(0.1f);
				detail.addCell(sCell);

				sCell = new Cell(new Phrase(String.format("%,.2f", Float
						.parseFloat(obj[10].toString())), font_body));
				sCell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
				sCell.setVerticalAlignment(Cell.ALIGN_TOP);
				sCell.setBorderColorTop(Color.GRAY);
				sCell.setBorderWidthTop(0.1f);
				sCell.setBorderWidthRight(0.1f);
				detail.addCell(sCell);

				sCell = new Cell(new Phrase(String.format("%,.2f", Float
						.parseFloat(obj[11].toString())), font_body));
				sCell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
				sCell.setVerticalAlignment(Cell.ALIGN_TOP);
				sCell.setBorderColorTop(Color.GRAY);
				sCell.setBorderWidthTop(0.1f);
				sCell.setBorderWidthRight(0.1f);
				detail.addCell(sCell);

				Table tmp = new Table(2);
				sCell = new Cell(new Phrase(String.format("%,.2f", Float
						.parseFloat(obj[12] != null ? obj[12].toString()
								: "0.00")), font_body));
				sCell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
				sCell.setVerticalAlignment(Cell.ALIGN_TOP);
				sCell.setBorderColorTop(Color.GRAY);
				sCell.setBorderWidthTop(0.1f);
				sCell.setBorderWidthRight(0.1f);
				tmp.addCell(sCell);

				float avgPlat = Float.parseFloat(obj[12] != null ? obj[12]
						.toString() : "0.00")
						/ rai;
				sCell = new Cell(new Phrase(String.format("%,.2f", avgPlat),
						font_body));
				sCell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
				sCell.setVerticalAlignment(Cell.ALIGN_TOP);
				sCell.setBorderColorTop(Color.GRAY);
				sCell.setBorderWidthTop(0.1f);
				sCell.setBorderWidthRight(0.1f);
				tmp.addCell(sCell);
				detail.insertTable(tmp);

				tmp = new Table(2);
				float sellPlant = Float.parseFloat(obj[12] != null ? obj[12]
						.toString() : "0.00")
						* Float.parseFloat(obj[11].toString());
				sCell = new Cell(new Phrase(String.format("%,.2f", sellPlant),
						font_body));
				sCell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
				sCell.setVerticalAlignment(Cell.ALIGN_TOP);
				sCell.setBorderColorTop(Color.GRAY);
				sCell.setBorderWidthTop(0.1f);
				sCell.setBorderWidthRight(0.1f);
				tmp.addCell(sCell);

				float avgSPlant = sellPlant / rai;
				sCell = new Cell(new Phrase(String.format("%,.2f", avgSPlant),
						font_body));
				sCell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
				sCell.setVerticalAlignment(Cell.ALIGN_TOP);
				sCell.setBorderColorTop(Color.GRAY);
				sCell.setBorderWidthTop(0.1f);
				sCell.setBorderWidthRight(0.1f);
				tmp.addCell(sCell);
				detail.insertTable(tmp);

				tmp = new Table(3);
				float profitPlant = sellPlant
						- Float.parseFloat(obj[10].toString());
				sCell = new Cell(new Phrase(
						String.format("%,.2f", profitPlant), font_body));
				sCell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
				sCell.setVerticalAlignment(Cell.ALIGN_TOP);
				sCell.setBorderColorTop(Color.GRAY);
				sCell.setBorderWidthTop(0.1f);
				sCell.setBorderWidthRight(0.1f);
				tmp.addCell(sCell);

				float avgProfitPlant = profitPlant
						/ Float.parseFloat(obj[12] != null ? obj[12].toString()
								: "0.00");
				sCell = new Cell(new Phrase(String.format("%,.2f",
						avgProfitPlant > 0 ? avgProfitPlant : 0f), font_body));
				sCell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
				sCell.setVerticalAlignment(Cell.ALIGN_TOP);
				sCell.setBorderColorTop(Color.GRAY);
				sCell.setBorderWidthTop(0.1f);
				sCell.setBorderWidthRight(0.1f);
				tmp.addCell(sCell);

				float avgProfitRai = profitPlant / rai;
				sCell = new Cell(new Phrase(String
						.format("%,.2f", avgProfitRai), font_body));
				sCell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
				sCell.setVerticalAlignment(Cell.ALIGN_TOP);
				sCell.setBorderColorTop(Color.GRAY);
				sCell.setBorderWidthTop(0.1f);
				sCell.setBorderWidthRight(0.1f);
				tmp.addCell(sCell);
				detail.insertTable(tmp);

				table.insertTable(detail);

				dtPlantRai += rai;
				dtPlantNgan += ngan;
				dtPlantWah += wah;
				dtCost += Float.parseFloat(obj[10].toString());
				dtSell += Float.parseFloat(obj[11].toString());
				dtFRecord += Float.parseFloat(obj[12] != null ? obj[12]
						.toString() : "0.00");
				dtSumSell += Float.parseFloat(obj[11].toString())
						* Float.parseFloat(obj[12] != null ? obj[12].toString()
								: "0.00");

				subDistrictTmp = obj[1].toString();
				provinceTmp = obj[13].toString();
				if (prov + 1 != provList.size()) {
					if (!subDistrictTmp.equals(((Object[]) provList
							.get(prov + 1))[1].toString())
							|| !branchTmp.equals(((Object[]) provList
									.get(prov + 1))[0].toString())) {
						calRai = totalRai(dtPlantRai, dtPlantNgan, dtPlantWah);
						String[] total = new String[] {
								String.format("%,d-%d-%d|", calRai[0],
										calRai[1], calRai[2]),
								String.format("%,.2f", dtCost),
								String.format("%,.2f", dtSell),
								String.format("%,.2f", dtFRecord),
								String.format("%,.2f", dtFRecord / dtPlantRai),
								String.format("%,.2f", dtSumSell),
								String.format("%,.2f", dtSumSell / dtPlantRai),
								String.format("%,.2f", dtSumSell - dtCost),
								String.format("%,.2f", (dtSumSell - dtCost)
										/ dtFRecord > 0 ? (dtSumSell - dtCost)
										/ dtFRecord : 0f),
								String.format("%,.2f", (dtSumSell - dtCost)
										/ dtPlantRai) };
						if (linePage < table.size()) {
							if (table.size() > 1) {
								document.add(title);
							}
							document.add(table);

							document.newPage();
							document.add(head);
							document.add(head_date);
							table = new Table(1);
							table.setAlignment(Cell.ALIGN_CENTER);
							table.setWidth(100);
							table.setPadding(2f);
							table.insertTable(headTable(newRows(colWidth),
									font_body, headTable));
							title.setLeading(5f);
							title.setSpacingAfter(0f);
							linePage = 24;
							newPage = true;
						}
						table.insertTable(totalTable(colTotalWidth, font_total,
								"รวม", total));
						btPlantRai += dtPlantRai;
						btPlantNgan += dtPlantNgan;
						btPlantWah += dtPlantWah;
						btFRecord += dtFRecord;
						btSell += dtSell;
						btCost += dtCost;
						btSumSell += dtSumSell;
						dtPlantRai = 0;
						dtPlantNgan = 0;
						dtPlantWah = 0;
						dtPlantNgan = 0;
						dtPlantWah = 0;
						dtFRecord = 0;
						dtSumSell = 0;
						dtSell = 0;
						dtCost = 0;
					}

					if (!branchTmp
							.equals(((Object[]) provList.get(prov + 1))[0]
									.toString())) {
						calRai = totalRai(btPlantRai, btPlantNgan, btPlantWah);
						String[] totalBran = new String[] {
								String.format("%,d-%d-%d|", calRai[0],
										calRai[1], calRai[2]),
								String.format("%,.2f", btCost),
								String.format("%,.2f", btSell),
								String.format("%,.2f", btFRecord),
								String.format("%,.2f", btFRecord / btPlantRai),
								String.format("%,.2f", btSumSell),
								String.format("%,.2f", btSumSell / btPlantRai),
								String.format("%,.2f", btSumSell - btCost),
								String.format("%,.2f", (btSumSell - btCost)
										/ btFRecord > 0 ? (btSumSell - btCost)
										/ btFRecord : 0f),
								String.format("%,.2f", (btSumSell - btCost)
										/ btPlantRai) };
						if (linePage < table.size()) {
							if (table.size() > 1) {
								document.add(title);
							}
							document.add(table);

							document.newPage();
							document.add(head);
							document.add(head_date);
							table = new Table(1);
							table.setAlignment(Cell.ALIGN_CENTER);
							table.setWidth(100);
							table.setPadding(2f);
							table.insertTable(headTable(newRows(colWidth),
									font_body, headTable));
							title.setLeading(5f);
							title.setSpacingAfter(0f);
							linePage = 24;
							newPage = true;
						}
						table.insertTable(totalTable(colTotalWidth, font_total,
								"ยอดรวมสาขา " + branchTmp, totalBran));
						ptPlantRai += btPlantRai;
						ptPlantNgan += btPlantNgan;
						ptPlantWah += btPlantWah;
						ptFRecord += btFRecord;
						ptSell += btSell;
						ptCost += btCost;
						ptSumSell += btSumSell;
						btPlantRai = 0;
						btPlantNgan = 0;
						btPlantWah = 0;
						btFRecord = 0;
						btSell = 0;
						btCost = 0;
						btSumSell = 0;
						if (!provinceTmp.equals(((Object[]) provList
								.get(prov + 1))[13].toString())) {
							calRai = totalRai(ptPlantRai, ptPlantNgan,
									ptPlantWah);
							String[] totalProv = new String[] {
									String.format("%,d-%d-%d|", calRai[0],
											calRai[1], calRai[2]),
									String.format("%,.2f", ptCost),
									String.format("%,.2f", ptSell),
									String.format("%,.2f", ptFRecord),
									String.format("%,.2f", ptFRecord
											/ ptPlantRai),
									String.format("%,.2f", ptSumSell),
									String.format("%,.2f", ptSumSell
											/ ptPlantRai),
									String.format("%,.2f", ptSumSell - ptCost),
									String
											.format(
													"%,.2f",
													(ptSumSell - ptCost)
															/ ptFRecord > 0 ? (ptSumSell - ptCost)
															/ ptFRecord
															: 0f),
									String.format("%,.2f", (ptSumSell - ptCost)
											/ ptPlantRai) };
							if (linePage < table.size()) {
								if (table.size() > 1) {
									document.add(title);
								}
								document.add(table);

								document.newPage();
								document.add(head);
								document.add(head_date);
								table = new Table(1);
								table.setAlignment(Cell.ALIGN_CENTER);
								table.setWidth(100);
								table.setPadding(2f);
								table.insertTable(headTable(newRows(colWidth),
										font_body, headTable));
								title.setLeading(5f);
								title.setSpacingAfter(0f);
								linePage = 24;
								newPage = true;
							}
							table.insertTable(totalTable(colTotalWidth,
									font_total, "ยอดรวมจังหวัด "
											+ obj[13].toString(), totalProv));

							ptPlantRai = 0;
							ptPlantNgan = 0;
							ptPlantWah = 0;
							ptFRecord = 0;
							ptSell = 0;
							ptCost = 0;
							ptSumSell = 0;
						}

						if (table.size() > 1) {
							document.add(title);
							document.add(table);
							linePage -= table.size();
							table = new Table(1);
							table.setAlignment(Cell.ALIGN_CENTER);
							table.setWidth(100);
							table.setPadding(2f);
						}
					}
				} else {
					calRai = totalRai(dtPlantRai, dtPlantNgan, dtPlantWah);
					String[] total = new String[] {
							String.format("%,d-%d-%d|", calRai[0], calRai[1],
									calRai[2]),
							String.format("%,.2f", dtCost),
							String.format("%,.2f", dtSell),
							String.format("%,.2f", dtFRecord),
							String.format("%,.2f", dtFRecord / dtPlantRai),
							String.format("%,.2f", dtSumSell),
							String.format("%,.2f", dtSumSell / dtPlantRai),
							String.format("%,.2f", dtSumSell - dtCost),
							String.format("%,.2f", (dtSumSell - dtCost)
									/ dtFRecord > 0 ? (dtSumSell - dtCost)
									/ dtFRecord : 0f),
							String.format("%,.2f", (dtSumSell - dtCost)
									/ dtPlantRai) };
					if (linePage < table.size()) {
						if (table.size() > 1) {
							document.add(title);
						}
						document.add(table);

						document.newPage();
						document.add(head);
						document.add(head_date);
						table = new Table(1);
						table.setAlignment(Cell.ALIGN_CENTER);
						table.setWidth(100);
						table.setPadding(2f);
						table.insertTable(headTable(newRows(colWidth),
								font_body, headTable));
						title.setLeading(5f);
						title.setSpacingAfter(0f);
						linePage = 24;
						newPage = true;
					}
					table.insertTable(totalTable(colTotalWidth, font_total,
							"รวม", total));
					btPlantRai += dtPlantRai;
					btPlantNgan += dtPlantNgan;
					btPlantWah += dtPlantWah;
					btFRecord += dtFRecord;
					btSell += dtSell;
					btCost += dtCost;
					btSumSell += dtSumSell;
					dtPlantRai = 0;
					dtPlantNgan = 0;
					dtPlantWah = 0;
					dtPlantNgan = 0;
					dtPlantWah = 0;
					dtFRecord = 0;
					dtSell = 0;
					dtCost = 0;
					dtSumSell = 0;
					calRai = totalRai(btPlantRai, btPlantNgan, btPlantWah);
					String[] totalBran = new String[] {
							String.format("%,d-%d-%d|", calRai[0], calRai[1],
									calRai[2]),
							String.format("%,.2f", btCost),
							String.format("%,.2f", btSell),
							String.format("%,.2f", btFRecord),
							String.format("%,.2f", btFRecord / btPlantRai),
							String.format("%,.2f", btSumSell),
							String.format("%,.2f", btSumSell / btPlantRai),
							String.format("%,.2f", btSumSell - btCost),
							String.format("%,.2f", (btSumSell - btCost)
									/ btFRecord > 0 ? (btSumSell - btCost)
									/ btFRecord : 0f),
							String.format("%,.2f", (btSumSell - btCost)
									/ btPlantRai) };
					if (linePage < table.size()) {
						if (table.size() > 1) {
							document.add(title);
						}
						document.add(table);

						document.newPage();
						document.add(head);
						document.add(head_date);
						table = new Table(1);
						table.setAlignment(Cell.ALIGN_CENTER);
						table.setWidth(100);
						table.setPadding(2f);
						table.insertTable(headTable(newRows(colWidth),
								font_body, headTable));
						title.setLeading(5f);
						title.setSpacingAfter(0f);
						linePage = 24;
						newPage = true;
					}
					table.insertTable(totalTable(colTotalWidth, font_total,
							"ยอดรวมสาขา " + branchTmp, totalBran));
					ptPlantRai += btPlantRai;
					ptPlantNgan += btPlantNgan;
					ptPlantWah += btPlantWah;
					ptFRecord += btFRecord;
					ptSell += btSell;
					ptCost += btCost;
					ptSumSell += btSumSell;
					btPlantRai = 0;
					btPlantNgan = 0;
					btPlantWah = 0;
					btFRecord = 0;
					btSell = 0;
					btCost = 0;
					btSumSell = 0;
					calRai = totalRai(ptPlantRai, ptPlantNgan, ptPlantWah);
					String[] totalProv = new String[] {
							String.format("%,d-%d-%d|", calRai[0], calRai[1],
									calRai[2]),
							String.format("%,.2f", ptCost),
							String.format("%,.2f", ptSell),
							String.format("%,.2f", ptFRecord),
							String.format("%,.2f", ptFRecord / ptPlantRai),
							String.format("%,.2f", ptSumSell),
							String.format("%,.2f", ptSumSell / ptPlantRai),
							String.format("%,.2f", ptSumSell - ptCost),
							String.format("%,.2f", (ptSumSell - ptCost)
									/ ptFRecord > 0 ? (ptSumSell - ptCost)
									/ ptFRecord : 0f),
							String.format("%,.2f", (ptSumSell - ptCost)
									/ ptPlantRai) };
					if (linePage < table.size()) {
						if (table.size() > 1) {
							document.add(title);
						}
						document.add(table);

						document.newPage();
						document.add(head);
						document.add(head_date);
						table = new Table(1);
						table.setAlignment(Cell.ALIGN_CENTER);
						table.setWidth(100);
						table.setPadding(2f);
						table.insertTable(headTable(newRows(colWidth),
								font_body, headTable));
						title.setLeading(5f);
						title.setSpacingAfter(0f);
						linePage = 24;
						newPage = true;
					}
					table.insertTable(totalTable(colTotalWidth, font_total,
							"ยอดรวมจังหวัด " + obj[13].toString(), totalProv));

					document.add(title);
					document.add(table);
				}
			}

		} catch (Exception e) {
			document.add(new Paragraph("เกิดการดึงข้อมูลผิดพลาด", font_title));
			e.printStackTrace();
			log.error(e, e);
		} finally {
			sf.getCurrentSession().close();
		}
		return document;
	}

	private Document ReportR108_1(SessionFactory sf, Document document,
			Font font_title, Font font_body, Font font_total, ReportForm eform)
			throws DocumentException {
		try {
			sf.getCurrentSession().beginTransaction();
			Paragraph head = new Paragraph(
					"รายงานแสดงข้อมูลเปรียบเทียบผลผลิตจริงที่ได้รับกับต้นทุนการผลิตรวม ตามกลุ่มเกษตรกร มีเป้าหมาย "
							+ eform.getPlantYear() + "/" + eform.getPlantNo(),
					font_title);
			head.setAlignment(Element.ALIGN_CENTER);
			document.add(head);

			Paragraph head_date = new Paragraph("สิ้นสุด ณ วันที่ "
					+ dateThai(eform.getEffectiveDate(), false), font_title);
			head_date.setAlignment(Element.ALIGN_CENTER);
			head_date.setSpacingBefore(5f);
			document.add(head_date);

			Table table = new Table(1);
			table.setAlignment(Cell.ALIGN_CENTER);
			table.setWidth(100);
			table.setPadding(2f);
			float[] colWidth, colTotalWidth;
			colWidth = new float[] { 15, 15, 13, 22, 15, 10, 10, 20, 30 };
			colTotalWidth = new float[] { 80, 10, 10, 10, 10, 10, 10, 10 };
			String[] headTable = new String[] {
					"ชื่อกลุ่ม",
					"ตำบลที่\nตั้งแปลง",
					"บัตรประจำตัว\nประชาชน\n(เลขที่)",
					"ชื่อ-สกุล",
					"กลุ่มพันธุ์",
					"เนื้อที่เพาะปลูก\nไร่-งาน-วา",
					"ประมาณ\nการผลผลิต\n(กก.)",
					"|ผลผลิตจริงที่ได้รับ|ผลผลิตรวม\n(กก.)|ผลผลิต\nเฉลี่ยต่อไร่\n(กก.)",
					"|ต้นทุนการผลิตรวม|ต้นทุนรวม\n(บาท)|ต้นทุนต่อไร่\n(บาท)|ต้นทุนต่อ กก.\n(บาท)" };
			table
					.insertTable(headTable(newRows(colWidth), font_body,
							headTable));

			ReportHome rhome = new ReportHome();
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

			List provList = rhome.findR108_1ByCriteria(eform.getPlantYear(),
					eform.getPlantNo(), df.parse(eform.getEffectiveDate()),
					eform.getProvinceNo(), eform.getBranchCode(), eform
							.getBreedTypeId(), eform.getBreedGroupId());
			if (eform.getBranchCode() > 0) {
				BranchHome bhome = new BranchHome();
				List<Branch> pbList = bhome
						.findByPBranch(eform.getBranchCode());
				List<Branch> pbranchCode = new ArrayList();
				if (pbList.size() > 0) {
					pbranchCode
							.addAll(getPBranch(pbList, eform.getBranchCode()));
					for (int i = 0; i < pbranchCode.size(); i++) {
						provList.addAll(rhome.findR108_1ByCriteria(eform
								.getPlantYear(), eform.getPlantNo(), df
								.parse(eform.getEffectiveDate()), 0,
								pbranchCode.get(i).getBranchCode(), eform
										.getBreedTypeId(), eform
										.getBreedGroupId()));
					}
				}
			}

			String branchTmp = "", subDistrictTmp = "", provinceTmp = "", FarmerTmp = "";
			int dtPlantRai = 0, dtPlantNgan = 0, dtPlantWah = 0;
			int btPlantRai = 0, btPlantNgan = 0, btPlantWah = 0;
			int ptPlantRai = 0, ptPlantNgan = 0, ptPlantWah = 0;
			float dtFRecord = 0, dtPRecord = 0, dtCost = 0;
			float btFRecord = 0, btPRecord = 0, btCost = 0;
			float ptFRecord = 0, ptPRecord = 0, ptCost = 0;
			int linePage = 22;
			Paragraph title = new Paragraph();
			if (provList.size() <= 0) {
				document.add(table);
				title = new Paragraph("ไม่มีข้อมูล", font_title);
				title.setAlignment(Element.ALIGN_CENTER);
				document.add(title);
			} else {
				int target = 0;
				for (int i = 0; i < provList.size(); i++) {
					Object[] obj = ((Object[]) provList.get(i));
					target += Float.parseFloat(obj[11].toString());
				}
				title = new Paragraph(String.format(
						"เป้าหมายปริมาณผลผลิต %d ตัน", target / 1000),
						font_total);
				title.setAlignment(Element.ALIGN_RIGHT);
				title.setLeading(25f);
				title.setSpacingAfter(-25f);
				document.add(title);
			}
			boolean newPage = true;
			int frist = 1;
			for (int prov = 0; prov < provList.size(); prov++) {
				Object[] obj = ((Object[]) provList.get(prov));

				if (!branchTmp.equals(obj[0].toString())) {
					title = new Paragraph("ธ.ก.ส. จังหวัด" + obj[14].toString()
							+ " " + obj[0].toString(), font_total);
					title.setAlignment(Element.ALIGN_LEFT);
					title.setLeading(5f);
					title.setSpacingBefore(20f);
					branchTmp = obj[0].toString();
					linePage -= 2;
					newPage = true;
				}
				Table detail = new Table(colWidth.length);
				detail.setWidths(colWidth);
				detail.setAlignment(Cell.ALIGN_CENTER);
				detail.setWidth(100);
				detail.setPadding(2f);

				if (linePage < table.size()) {
					if (table.size() > 1) {
						document.add(title);
					}
					document.add(table);

					document.newPage();
					document.add(head);
					document.add(head_date);
					table = new Table(1);
					table.setAlignment(Cell.ALIGN_CENTER);
					table.setWidth(100);
					table.setPadding(2f);
					table.insertTable(headTable(newRows(colWidth), font_body,
							headTable));
					title.setLeading(5f);
					title.setSpacingAfter(0f);
					linePage = 20;
					newPage = true;
				}

				Cell sCell = null;
				if (FarmerTmp.equals(obj[1].toString()) && !newPage
						&& subDistrictTmp.equals(obj[2].toString())) {
					sCell = new Cell(new Phrase("", font_body));
					sCell.setBorderWidthTop(0);
					sCell.setBorderWidthBottom(0);
					sCell.setBorderWidthRight(0.1f);
					detail.addCell(sCell);
				} else {
					sCell = new Cell(new Phrase(obj[1] != null ? obj[1]
							.toString() : " ", font_body));
					sCell.setHorizontalAlignment(Cell.ALIGN_LEFT);
					sCell.setVerticalAlignment(Cell.ALIGN_TOP);
					sCell.setBorderColorTop(Color.GRAY);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
					sCell.setBorderWidthBottom(0);
					sCell.setBorderWidthRight(0.1f);
					detail.addCell(sCell);
					FarmerTmp = obj[1].toString();
				}

				if (subDistrictTmp.equals(obj[2].toString()) && !newPage) {
					sCell = new Cell(new Phrase("", font_body));
					sCell.setBorderWidthTop(0);
					sCell.setBorderWidthBottom(0);
					sCell.setBorderWidthRight(0.1f);
					detail.addCell(sCell);

				} else {
					sCell = new Cell(new Phrase(obj[2].toString(), font_body));
					sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
					sCell.setVerticalAlignment(Cell.ALIGN_TOP);
					sCell.setBorderColorTop(Color.GRAY);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
					sCell.setBorderWidthBottom(0);
					sCell.setBorderWidthRight(0.1f);
					detail.addCell(sCell);
					newPage = false;
				}

				sCell = new Cell(new Phrase(obj[3].toString(), font_body));
				sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
				sCell.setVerticalAlignment(Cell.ALIGN_TOP);
				sCell.setBorderColorTop(Color.GRAY);
				sCell.setBorderWidthTop(0.1f);
				sCell.setBorderWidthRight(0.1f);
				detail.addCell(sCell);

				sCell = new Cell(new Phrase(" " + obj[4].toString()
						+ obj[5].toString() + " " + obj[6].toString(),
						font_body));
				sCell.setHorizontalAlignment(Cell.ALIGN_LEFT);
				sCell.setVerticalAlignment(Cell.ALIGN_TOP);
				sCell.setBorderColorTop(Color.GRAY);
				sCell.setBorderWidthTop(0.1f);
				sCell.setBorderWidthRight(0.1f);
				detail.addCell(sCell);

				sCell = new Cell(new Phrase(obj[7].toString(), font_body));
				sCell.setHorizontalAlignment(Cell.ALIGN_LEFT);
				sCell.setVerticalAlignment(Cell.ALIGN_TOP);
				sCell.setBorderColorTop(Color.GRAY);
				sCell.setBorderWidthTop(0.1f);
				sCell.setBorderWidthRight(0.1f);
				detail.addCell(sCell);

				// Table tmp = new Table(3);
				int rai = Integer.parseInt(obj[8].toString());
				int ngan = Integer.parseInt(obj[9].toString());
				int wah = Integer.parseInt(obj[10].toString());

				if (wah / 100 > 0) {
					ngan += wah / 100;
					wah = wah % 100;

				}
				if (ngan / 4 > 0) {
					rai += ngan / 4;
					ngan = ngan % 4;
				}

				sCell = new Cell(new Phrase(String.format("%,d-%d-%d", rai,
						ngan, wah), font_body));
				sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
				sCell.setVerticalAlignment(Cell.ALIGN_TOP);
				sCell.setBorderColorTop(Color.GRAY);
				sCell.setBorderWidthTop(0.1f);
				sCell.setBorderWidthRight(0.1f);
				detail.addCell(sCell);

				sCell = new Cell(new Phrase(String.format("%,.2f", Float
						.parseFloat(obj[11].toString())), font_body));
				sCell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
				sCell.setVerticalAlignment(Cell.ALIGN_TOP);
				sCell.setBorderColorTop(Color.GRAY);
				sCell.setBorderWidthTop(0.1f);
				sCell.setBorderWidthRight(0.1f);
				detail.addCell(sCell);

				Table tmp = new Table(2);
				sCell = new Cell(new Phrase(String.format("%,.2f", Float
						.parseFloat(obj[12] != null ? obj[12].toString()
								: "0.00")), font_body));
				sCell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
				sCell.setVerticalAlignment(Cell.ALIGN_TOP);
				sCell.setBorderColorTop(Color.GRAY);
				sCell.setBorderWidthTop(0.1f);
				sCell.setBorderWidthRight(0.1f);
				tmp.addCell(sCell);

				float avgCorp = Float.parseFloat(obj[12] != null ? obj[12]
						.toString() : "0.00")
						/ rai;
				sCell = new Cell(new Phrase(String.format("%,.2f", avgCorp),
						font_body));
				sCell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
				sCell.setVerticalAlignment(Cell.ALIGN_TOP);
				sCell.setBorderColorTop(Color.GRAY);
				sCell.setBorderWidthTop(0.1f);
				sCell.setBorderWidthRight(0.1f);
				tmp.addCell(sCell);
				detail.insertTable(tmp);

				tmp = new Table(3);
				sCell = new Cell(new Phrase(String.format("%,.2f", Float
						.parseFloat(obj[13].toString())), font_body));
				sCell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
				sCell.setVerticalAlignment(Cell.ALIGN_TOP);
				sCell.setBorderColorTop(Color.GRAY);
				sCell.setBorderWidthTop(0.1f);
				sCell.setBorderWidthRight(0.1f);
				tmp.addCell(sCell);

				float avgCost = Float.parseFloat(obj[13].toString()) / rai;
				sCell = new Cell(new Phrase(String.format("%,.2f", avgCost),
						font_body));
				sCell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
				sCell.setVerticalAlignment(Cell.ALIGN_TOP);
				sCell.setBorderColorTop(Color.GRAY);
				sCell.setBorderWidthTop(0.1f);
				sCell.setBorderWidthRight(0.1f);
				tmp.addCell(sCell);

				sCell = new Cell(new Phrase(String.format("%,.2f",
						avgCorp > 0 ? avgCost / avgCorp : 0f), font_body));
				sCell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
				sCell.setVerticalAlignment(Cell.ALIGN_TOP);
				sCell.setBorderColorTop(Color.GRAY);
				sCell.setBorderWidthTop(0.1f);
				sCell.setBorderWidthRight(0.1f);
				tmp.addCell(sCell);
				detail.insertTable(tmp);

				table.insertTable(detail);

				dtPlantRai += rai;
				dtPlantNgan += ngan;
				dtPlantWah += wah;
				dtFRecord += Float.parseFloat(obj[11].toString());
				dtPRecord += Float.parseFloat(obj[12] != null ? obj[12]
						.toString() : "0.00");
				dtCost += Float.parseFloat(obj[13].toString());

				subDistrictTmp = obj[2].toString();
				provinceTmp = obj[14].toString();

				if (prov + 1 != provList.size()) {
					if (!subDistrictTmp.equals(((Object[]) provList
							.get(prov + 1))[2].toString())
							|| !branchTmp.equals(((Object[]) provList
									.get(prov + 1))[0].toString())) {
						calRai = totalRai(dtPlantRai, dtPlantNgan, dtPlantWah);
						String[] total = new String[] {
								String.format("%,d-%d-%d|", calRai[0],
										calRai[1], calRai[2]),
								String.format("%,.2f", dtFRecord),
								String.format("%,.2f", dtPRecord),
								String.format("%,.2f", dtPRecord / dtPlantRai),
								String.format("%,.2f", dtCost),
								String.format("%,.2f", dtCost / dtPlantRai),
								String
										.format(
												"%,.2f",
												(dtPRecord / dtPlantRai) > 0 ? (dtCost / dtPlantRai)
														/ (dtPRecord / dtPlantRai)
														: 0f) };
						if (linePage < table.size()) {
							if (table.size() > 1) {
								document.add(title);
							}
							document.add(table);

							document.newPage();
							document.add(head);
							document.add(head_date);
							table = new Table(1);
							table.setAlignment(Cell.ALIGN_CENTER);
							table.setWidth(100);
							table.setPadding(2f);
							table.insertTable(headTable(newRows(colWidth),
									font_body, headTable));
							title.setLeading(5f);
							title.setSpacingAfter(0f);
							linePage = 20;
							newPage = true;
						}
						table.insertTable(totalTable(colTotalWidth, font_total,
								"รวม", total));
						btPlantRai += dtPlantRai;
						btPlantNgan += dtPlantNgan;
						btPlantWah += dtPlantWah;
						btFRecord += dtFRecord;
						btPRecord += dtPRecord;
						btCost += dtCost;
						dtPlantRai = 0;
						dtPlantNgan = 0;
						dtPlantWah = 0;
						dtPlantNgan = 0;
						dtPlantWah = 0;
						dtFRecord = 0;
						dtPRecord = 0;
						dtCost = 0;
					}
					if (!branchTmp
							.equals(((Object[]) provList.get(prov + 1))[0]
									.toString())) {
						calRai = totalRai(btPlantRai, btPlantNgan, btPlantWah);
						String[] totalBran = new String[] {
								String.format("%,d-%d-%d|", calRai[0],
										calRai[1], calRai[2]),
								String.format("%,.2f", btFRecord),
								String.format("%,.2f", btPRecord),
								String.format("%,.2f", btPRecord / btPlantRai),
								String.format("%,.2f", btCost),
								String.format("%,.2f", btCost / btPlantRai),
								String
										.format(
												"%,.2f",
												(btPRecord / btPlantRai) > 0 ? (btCost / btPlantRai)
														/ (btPRecord / btPlantRai)
														: 0f) };
						if (linePage < table.size()) {
							if (table.size() > 1) {
								document.add(title);
							}
							document.add(table);

							document.newPage();
							document.add(head);
							document.add(head_date);
							table = new Table(1);
							table.setAlignment(Cell.ALIGN_CENTER);
							table.setWidth(100);
							table.setPadding(2f);
							table.insertTable(headTable(newRows(colWidth),
									font_body, headTable));
							title.setLeading(5f);
							title.setSpacingAfter(0f);
							linePage = 20;
							newPage = true;
						}
						table.insertTable(totalTable(colTotalWidth, font_total,
								"ยอดรวมสาขา " + branchTmp, totalBran));
						ptPlantRai += btPlantRai;
						ptFRecord += btFRecord;
						ptPRecord += btPRecord;
						ptCost += btCost;
						btPlantRai = 0;
						btPlantNgan = 0;
						btPlantWah = 0;
						btFRecord = 0;
						btPRecord = 0;
						btCost = 0;
						if (!provinceTmp.equals(((Object[]) provList
								.get(prov + 1))[14].toString())) {
							calRai = totalRai(ptPlantRai, ptPlantNgan,
									ptPlantWah);
							String[] totalProv = new String[] {
									String.format("%,d-%d-%d|", calRai[0],
											calRai[1], calRai[2]),
									String.format("%,.2f", ptFRecord),
									String.format("%,.2f", ptPRecord),
									String.format("%,.2f", ptPRecord
											/ ptPlantRai),
									String.format("%,.2f", ptCost),
									String.format("%,.2f", ptCost / ptPlantRai),
									String
											.format(
													"%,.2f",
													(ptPRecord / ptPlantRai) > 0 ? (ptCost / ptPlantRai)
															/ (ptPRecord / ptPlantRai)
															: 0f) };
							if (linePage < table.size()) {
								if (table.size() > 1) {
									document.add(title);
								}
								document.add(table);

								document.newPage();
								document.add(head);
								document.add(head_date);
								table = new Table(1);
								table.setAlignment(Cell.ALIGN_CENTER);
								table.setWidth(100);
								table.setPadding(2f);
								table.insertTable(headTable(newRows(colWidth),
										font_body, headTable));
								title.setLeading(5f);
								title.setSpacingAfter(0f);
								linePage = 20;
								newPage = true;
							}
							table.insertTable(totalTable(colTotalWidth,
									font_total, "ยอดรวมจังหวัด "
											+ obj[14].toString(), totalProv));

							ptPlantRai = 0;
							ptPlantNgan = 0;
							ptPlantWah = 0;
							ptFRecord = 0;
							ptPRecord = 0;
							ptCost = 0;
						}

						if (table.size() > 1) {
							document.add(title);
							document.add(table);
							linePage -= table.size();
							table = new Table(1);
							table.setAlignment(Cell.ALIGN_CENTER);
							table.setWidth(100);
							table.setPadding(2f);
						}
					}
				} else {
					calRai = totalRai(dtPlantRai, dtPlantNgan, dtPlantWah);
					String[] total = new String[] {
							String.format("%,d-%d-%d|", calRai[0], calRai[1],
									calRai[2]),
							String.format("%,.2f", dtFRecord),
							String.format("%,.2f", dtPRecord),
							String.format("%,.2f", dtPRecord / dtPlantRai),
							String.format("%,.2f", dtCost),
							String.format("%,.2f", dtCost / dtPlantRai),
							String
									.format(
											"%,.2f",
											(dtPRecord / dtPlantRai) > 0 ? (dtCost / dtPlantRai)
													/ (dtPRecord / dtPlantRai)
													: 0f) };
					if (linePage < table.size()) {
						if (table.size() > 1) {
							document.add(title);
						}
						document.add(table);

						document.newPage();
						document.add(head);
						document.add(head_date);
						table = new Table(1);
						table.setAlignment(Cell.ALIGN_CENTER);
						table.setWidth(100);
						table.setPadding(2f);
						table.insertTable(headTable(newRows(colWidth),
								font_body, headTable));
						title.setLeading(5f);
						title.setSpacingAfter(0f);
						linePage = 20;
						newPage = true;
					}
					table.insertTable(totalTable(colTotalWidth, font_total,
							"รวม", total));
					btPlantRai += dtPlantRai;
					btPlantNgan += dtPlantNgan;
					btPlantWah += dtPlantWah;
					btFRecord += dtFRecord;
					btPRecord += dtPRecord;
					btCost += dtCost;
					dtPlantRai = 0;
					dtPlantNgan = 0;
					dtPlantWah = 0;
					dtFRecord = 0;
					dtPRecord = 0;
					dtCost = 0;

					calRai = totalRai(btPlantRai, btPlantNgan, btPlantWah);
					String[] totalBran = new String[] {
							String.format("%,d-%d-%d|", calRai[0], calRai[1],
									calRai[2]),
							String.format("%,.2f", btFRecord),
							String.format("%,.2f", btPRecord),
							String.format("%,.2f", btPRecord / btPlantRai),
							String.format("%,.2f", btCost),
							String.format("%,.2f", btCost / btPlantRai),
							String
									.format(
											"%,.2f",
											(btPRecord / btPlantRai) > 0 ? (btCost / btPlantRai)
													/ (btPRecord / btPlantRai)
													: 0f) };
					if (linePage < table.size()) {
						if (table.size() > 1) {
							document.add(title);
						}
						document.add(table);

						document.newPage();
						document.add(head);
						document.add(head_date);
						table = new Table(1);
						table.setAlignment(Cell.ALIGN_CENTER);
						table.setWidth(100);
						table.setPadding(2f);
						table.insertTable(headTable(newRows(colWidth),
								font_body, headTable));
						title.setLeading(5f);
						title.setSpacingAfter(0f);
						linePage = 20;
						newPage = true;
					}
					table.insertTable(totalTable(colTotalWidth, font_total,
							"ยอดรวมสาขา " + branchTmp, totalBran));
					ptPlantRai += btPlantRai;
					ptPlantNgan += btPlantNgan;
					ptPlantWah += btPlantWah;
					ptFRecord += btFRecord;
					ptPRecord += btPRecord;
					ptCost += btCost;
					btPlantRai = 0;
					btPlantNgan = 0;
					btPlantWah = 0;
					btFRecord = 0;
					btPRecord = 0;
					btCost = 0;

					calRai = totalRai(ptPlantRai, ptPlantNgan, ptPlantWah);
					String[] totalProv = new String[] {
							String.format("%,d-%d-%d|", calRai[0], calRai[1],
									calRai[2]),
							String.format("%,.2f", ptFRecord),
							String.format("%,.2f", ptPRecord),
							String.format("%,.2f", ptPRecord / ptPlantRai),
							String.format("%,.2f", ptCost),
							String.format("%,.2f", ptCost / ptPlantRai),
							String
									.format(
											"%,.2f",
											(ptPRecord / ptPlantRai) > 0 ? (ptCost / ptPlantRai)
													/ (ptPRecord / ptPlantRai)
													: 0f) };
					if (linePage < table.size()) {
						if (table.size() > 1) {
							document.add(title);
						}
						document.add(table);

						document.newPage();
						document.add(head);
						document.add(head_date);
						table = new Table(1);
						table.setAlignment(Cell.ALIGN_CENTER);
						table.setWidth(100);
						table.setPadding(2f);
						table.insertTable(headTable(newRows(colWidth),
								font_body, headTable));
						title.setLeading(5f);
						title.setSpacingAfter(0f);
						linePage = 20;
						newPage = true;
					}
					table.insertTable(totalTable(colTotalWidth, font_total,
							"ยอดรวมจังหวัด " + obj[14].toString(), totalProv));

					document.add(title);
					document.add(table);
				}
			}

		} catch (Exception e) {
			document.add(new Paragraph("เกิดการดึงข้อมูลผิดพลาด", font_title));
			e.printStackTrace();
			log.error(e, e);
		} finally {
			sf.getCurrentSession().close();
		}
		return document;
	}

	private Document ReportR108_3(SessionFactory sf, Document document,
			Font font_title, Font font_body, Font font_total, ReportForm eform)
			throws DocumentException {
		try {
			sf.getCurrentSession().beginTransaction();
			Paragraph head = new Paragraph(
					"รายงานแสดงข้อมูลเปรียบเทียบผลผลิตจริงที่ได้รับกับต้นทุนการผลิตรวม ตามกลุ่มเกษตรกร (รวมกลุ่มหลายอำเภอ)มีเป้าหมาย"
							+ eform.getPlantYear() + "/" + eform.getPlantNo(),
					font_title);
			head.setAlignment(Element.ALIGN_CENTER);
			document.add(head);

			Paragraph head_date = new Paragraph("สิ้นสุด ณ วันที่ "
					+ dateThai(eform.getEffectiveDate(), false), font_title);
			head_date.setAlignment(Element.ALIGN_CENTER);
			head_date.setSpacingBefore(5f);
			document.add(head_date);

			Table table = new Table(1);
			table.setAlignment(Cell.ALIGN_CENTER);
			table.setWidth(100);
			table.setPadding(2f);
			float[] colWidth, colTotalWidth;
			colWidth = new float[] { 13, 17, 13, 22, 15, 10, 10, 20, 30 };
			colTotalWidth = new float[] { 80, 10, 10, 10, 10, 10, 10, 10 };
			//float[] colInner = new float[] { 1, 0.75f };
			String[] headTable = new String[] {
					"ชื่อกลุ่ม",
					"|ที่ตั้งแปลง|อำเภอ|ตำบล",//"1,0.75,|ที่ตั้งแปลง|อำเภอ|ตำบล",
					"บัตรประจำตัว\nประชาชน\n(เลขที่)",
					"ชื่อ-สกุล",
					"กลุ่มพันธุ์",
					"เนื้อที่เพาะปลูก\nไร่-งาน-วา",
					"ประมาณ\nการผลผลิต\n(กก.)",
					"|ผลผลิตจริงที่ได้รับ|ผลผลิตรวม\n(กก.)|ผลผลิต\nเฉลี่ยต่อไร่\n(กก.)",
					"|ต้นทุนการผลิตรวม|ต้นทุนรวม\n(บาท)|ต้นทุนต่อไร่\n(บาท)|ต้นทุนต่อ กก.\n(บาท)" };
			table
					.insertTable(headTable(newRows(colWidth), font_body,
							headTable));

			ReportHome rhome = new ReportHome();
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

			List provList = rhome.findR108_3ByCriteria(eform.getPlantYear(),
					eform.getPlantNo(), df.parse(eform.getEffectiveDate()),
					eform.getProvinceNo(), eform.getBranchCode(), eform
							.getBreedTypeId(), eform.getBreedGroupId());
			if (eform.getBranchCode() > 0) {
				BranchHome bhome = new BranchHome();
				List<Branch> pbList = bhome
						.findByPBranch(eform.getBranchCode());
				List<Branch> pbranchCode = new ArrayList();
				if (pbList.size() > 0) {
					pbranchCode
							.addAll(getPBranch(pbList, eform.getBranchCode()));
					for (int i = 0; i < pbranchCode.size(); i++) {
						provList.addAll(rhome.findR108_3ByCriteria(eform
								.getPlantYear(), eform.getPlantNo(), df
								.parse(eform.getEffectiveDate()), 0,
								pbranchCode.get(i).getBranchCode(), eform
										.getBreedTypeId(), eform
										.getBreedGroupId()));
					}
				}
			}

			String branchTmp = "", subDistrictTmp = "", DistrictTmp = "", provinceTmp = "", farmerTmp = "";
			int dtPlantRai = 0, dtPlantNgan = 0, dtPlantWah = 0;
			int btPlantRai = 0, btPlantNgan = 0, btPlantWah = 0;
			int ptPlantRai = 0, ptPlantNgan = 0, ptPlantWah = 0;
			float dtFRecord = 0, dtPRecord = 0, dtCost = 0;
			float btFRecord = 0, btPRecord = 0, btCost = 0;
			float ptFRecord = 0, ptPRecord = 0, ptCost = 0;
			int linePage = 22;
			Paragraph title = new Paragraph();
			if (provList.size() <= 0) {
				document.add(table);
				title = new Paragraph("ไม่มีข้อมูล", font_title);
				title.setAlignment(Element.ALIGN_CENTER);
				document.add(title);
			} else {
				int target = 0;
				for (int i = 0; i < provList.size(); i++) {
					Object[] obj = ((Object[]) provList.get(i));
					target += Float.parseFloat(obj[11].toString());
				}
				title = new Paragraph(String.format(
						"การรวมกลุ่ม(หลายอำเภอ)เป้าหมายปริมาณผลผลิต %d ตัน", target / 1000),
						font_total);
				title.setAlignment(Element.ALIGN_RIGHT);
				title.setLeading(25f);
				title.setSpacingAfter(-25f);
				document.add(title);
			}
			boolean newPage = true;
			int frist = 1;
			for (int prov = 0; prov < provList.size(); prov++) {
				Object[] obj = ((Object[]) provList.get(prov));

				if (!branchTmp.equals(obj[0].toString())) {
					title = new Paragraph("ธ.ก.ส. จังหวัด" + obj[14].toString()
							+ " " + obj[0].toString(), font_total);
					title.setAlignment(Element.ALIGN_LEFT);
					title.setLeading(5f);
					title.setSpacingBefore(20f);
					branchTmp = obj[0].toString();
					newPage = true;
					linePage -= 2;
				}
				Table detail = new Table(colWidth.length);
				detail.setWidths(colWidth);
				detail.setAlignment(Cell.ALIGN_CENTER);
				detail.setWidth(100);
				detail.setPadding(2f);

				Cell sCell = null;

				if (linePage < table.size()) {
					if (table.size() > 1) {
						document.add(title);
					}
					document.add(table);

					document.newPage();
					document.add(head);
					document.add(head_date);
					table = new Table(1);
					table.setAlignment(Cell.ALIGN_CENTER);
					table.setWidth(100);
					table.setPadding(2f);
					table.insertTable(headTable(newRows(colWidth), font_body,
							headTable));
					title.setLeading(5f);
					title.setSpacingAfter(0f);
					linePage = 20;
					newPage = true;
				}
				if (farmerTmp.equals(obj[1].toString()) && !newPage) {
					sCell = new Cell(new Phrase(" ", font_body));
					sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
					sCell.setVerticalAlignment(Cell.ALIGN_TOP);
					sCell.setBorderColorTop(Color.GRAY);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
					sCell.setBorderWidthTop(0);
					sCell.setBorderWidthBottom(0);
					sCell.setBorderWidthRight(0.1f);
					detail.addCell(sCell);
				} else {
					sCell = new Cell(new Phrase(obj[1].toString(), font_body));
					sCell.setHorizontalAlignment(Cell.ALIGN_LEFT);
					sCell.setVerticalAlignment(Cell.ALIGN_TOP);
					sCell.setBorderColorTop(Color.GRAY);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
					sCell.setBorderWidthBottom(0);
					sCell.setBorderWidthRight(0.1f);
					detail.addCell(sCell);
					newPage = true;
					farmerTmp = obj[1].toString();
				}

				if (!subDistrictTmp.equals(obj[2].toString()) || newPage) {
					Table dist = new Table(2);
//					dist.setWidths(colInner);
					if (DistrictTmp.equals(obj[22].toString()) && !newPage) {
						sCell = new Cell(new Phrase(" ", font_body));
						sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
						sCell.setVerticalAlignment(Cell.ALIGN_TOP);
						sCell.setBorderColorTop(Color.GRAY);
						sCell.setBorderWidthTop(0.1f);
						sCell.setBorderWidthRight(0.1f);
						sCell.setBorderWidthTop(0);
						sCell.setBorderWidthBottom(0);
						sCell.setBorderWidthRight(0.1f);
						dist.addCell(sCell);
					} else {
						sCell = new Cell(new Phrase(obj[22].toString(),
								font_body));
						sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
						sCell.setVerticalAlignment(Cell.ALIGN_TOP);
						sCell.setBorderColorTop(Color.GRAY);
						sCell.setBorderWidthTop(0.1f);
						sCell.setBorderWidthRight(0.1f);
						sCell.setBorderWidthTop(0.1f);
						sCell.setBorderWidthRight(0.1f);
						sCell.setBorderWidthBottom(0);
						sCell.setBorderWidthRight(0.1f);
						dist.addCell(sCell);
						DistrictTmp = obj[22].toString();
					}
					sCell = new Cell(new Phrase(obj[2].toString(), font_body));
					sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
					sCell.setVerticalAlignment(Cell.ALIGN_TOP);
					sCell.setBorderColorTop(Color.GRAY);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
					sCell.setBorderWidthBottom(0);
					sCell.setBorderWidthRight(0.1f);
					dist.addCell(sCell);
					detail.insertTable(dist);
					newPage = false;
				} else {
					Table dist = new Table(2);

					sCell = new Cell(new Phrase(" ", font_body));
					sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
					sCell.setVerticalAlignment(Cell.ALIGN_TOP);
					sCell.setBorderColorTop(Color.GRAY);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
					sCell.setBorderWidthTop(0);
					sCell.setBorderWidthBottom(0);
					sCell.setBorderWidthRight(0.1f);
					dist.addCell(sCell);
					dist.addCell(sCell);
					detail.insertTable(dist);
				}

				sCell = new Cell(new Phrase(obj[3].toString(), font_body));
				sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
				sCell.setVerticalAlignment(Cell.ALIGN_TOP);
				sCell.setBorderColorTop(Color.GRAY);
				sCell.setBorderWidthTop(0.1f);
				sCell.setBorderWidthRight(0.1f);
				detail.addCell(sCell);

				sCell = new Cell(new Phrase(" " + obj[4].toString()
						+ obj[5].toString() + " " + obj[6].toString(),
						font_body));
				sCell.setHorizontalAlignment(Cell.ALIGN_LEFT);
				sCell.setVerticalAlignment(Cell.ALIGN_TOP);
				sCell.setBorderColorTop(Color.GRAY);
				sCell.setBorderWidthTop(0.1f);
				sCell.setBorderWidthRight(0.1f);
				detail.addCell(sCell);

				sCell = new Cell(new Phrase(obj[7].toString(), font_body));
				sCell.setHorizontalAlignment(Cell.ALIGN_LEFT);
				sCell.setVerticalAlignment(Cell.ALIGN_TOP);
				sCell.setBorderColorTop(Color.GRAY);
				sCell.setBorderWidthTop(0.1f);
				sCell.setBorderWidthRight(0.1f);
				detail.addCell(sCell);

				// Table tmp = new Table(3);
				int rai = Integer.parseInt(obj[8].toString());
				int ngan = Integer.parseInt(obj[9].toString());
				int wah = Integer.parseInt(obj[10].toString());

				if (wah / 100 > 0) {
					ngan += wah / 100;
					wah = wah % 100;

				}
				if (ngan / 4 > 0) {
					rai += ngan / 4;
					ngan = ngan % 4;
				}
				sCell = new Cell(new Phrase(String.format("%,d-%d-%d", rai,
						ngan, wah), font_body));
				sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
				sCell.setVerticalAlignment(Cell.ALIGN_TOP);
				sCell.setBorderColorTop(Color.GRAY);
				sCell.setBorderWidthTop(0.1f);
				sCell.setBorderWidthRight(0.1f);
				detail.addCell(sCell);

				sCell = new Cell(new Phrase(String.format("%,.2f", Float
						.parseFloat(obj[11].toString())), font_body));
				sCell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
				sCell.setVerticalAlignment(Cell.ALIGN_TOP);
				sCell.setBorderColorTop(Color.GRAY);
				sCell.setBorderWidthTop(0.1f);
				sCell.setBorderWidthRight(0.1f);
				detail.addCell(sCell);

				Table tmp = new Table(2);
				sCell = new Cell(new Phrase(String.format("%,.2f", Float
						.parseFloat(obj[12] != null ? obj[12].toString()
								: "0.00")), font_body));
				sCell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
				sCell.setVerticalAlignment(Cell.ALIGN_TOP);
				sCell.setBorderColorTop(Color.GRAY);
				sCell.setBorderWidthTop(0.1f);
				sCell.setBorderWidthRight(0.1f);
				tmp.addCell(sCell);

				float avgCorp = Float.parseFloat(obj[12] != null ? obj[12]
						.toString() : "0.00")
						/ rai;
				sCell = new Cell(new Phrase(String.format("%,.2f", avgCorp),
						font_body));
				sCell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
				sCell.setVerticalAlignment(Cell.ALIGN_TOP);
				sCell.setBorderColorTop(Color.GRAY);
				sCell.setBorderWidthTop(0.1f);
				sCell.setBorderWidthRight(0.1f);
				tmp.addCell(sCell);
				detail.insertTable(tmp);

				tmp = new Table(3);
				sCell = new Cell(new Phrase(String.format("%,.2f", Float
						.parseFloat(obj[13].toString())), font_body));
				sCell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
				sCell.setVerticalAlignment(Cell.ALIGN_TOP);
				sCell.setBorderColorTop(Color.GRAY);
				sCell.setBorderWidthTop(0.1f);
				sCell.setBorderWidthRight(0.1f);
				tmp.addCell(sCell);

				float avgCost = Float.parseFloat(obj[13].toString()) / rai;
				sCell = new Cell(new Phrase(String.format("%,.2f", avgCost),
						font_body));
				sCell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
				sCell.setVerticalAlignment(Cell.ALIGN_TOP);
				sCell.setBorderColorTop(Color.GRAY);
				sCell.setBorderWidthTop(0.1f);
				sCell.setBorderWidthRight(0.1f);
				tmp.addCell(sCell);

				sCell = new Cell(new Phrase(String.format("%,.2f",
						avgCorp > 0 ? avgCost / avgCorp : 0f), font_body));
				sCell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
				sCell.setVerticalAlignment(Cell.ALIGN_TOP);
				sCell.setBorderColorTop(Color.GRAY);
				sCell.setBorderWidthTop(0.1f);
				sCell.setBorderWidthRight(0.1f);
				tmp.addCell(sCell);
				detail.insertTable(tmp);

				table.insertTable(detail);

				dtPlantRai += rai;
				dtPlantNgan += ngan;
				dtPlantWah += wah;
				dtFRecord += Float.parseFloat(obj[11].toString());
				dtPRecord += Float.parseFloat(obj[12] != null ? obj[12]
						.toString() : "0.00");
				dtCost += Float.parseFloat(obj[13].toString());

				subDistrictTmp = obj[2].toString();
				provinceTmp = obj[14].toString();

				if (prov + 1 != provList.size()) {
					if (!farmerTmp.equals(((Object[]) provList
							.get(prov + 1))[1].toString())
							|| !branchTmp.equals(((Object[]) provList
									.get(prov + 1))[0].toString())) {
						calRai = totalRai(dtPlantRai, dtPlantNgan, dtPlantWah);
						String[] totalFarm = new String[] {
								String.format("%,d-%d-%d|", calRai[0],
										calRai[1], calRai[2]),
								String.format("%,.2f", dtFRecord),
								String.format("%,.2f", dtPRecord),
								String.format("%,.2f", dtPRecord / dtPlantRai),
								String.format("%,.2f", dtCost),
								String.format("%,.2f", dtCost / dtPlantRai),
								String
										.format(
												"%,.2f",
												(dtPRecord / dtPlantRai) > 0 ? (dtCost / dtPlantRai)
														/ (dtPRecord / dtPlantRai)
														: 0f) };
						if (linePage < table.size()) {
							if (table.size() > 1) {
								document.add(title);
							}
							document.add(table);

							document.newPage();
							document.add(head);
							document.add(head_date);
							table = new Table(1);
							table.setAlignment(Cell.ALIGN_CENTER);
							table.setWidth(100);
							table.setPadding(2f);
							table.insertTable(headTable(newRows(colWidth),
									font_body, headTable));
							title.setLeading(5f);
							title.setSpacingAfter(0f);
							linePage = 20;
							newPage = true;
						}
						table.insertTable(totalTable(colTotalWidth, font_total,
								"รวมกลุ่ม" + farmerTmp, totalFarm));
						btPlantRai += dtPlantRai;
						btPlantNgan += dtPlantNgan;
						btPlantWah += dtPlantWah;
						btFRecord += dtFRecord;
						btPRecord += dtPRecord;
						btCost += dtCost;
						dtPlantRai = 0;
						dtPlantNgan = 0;
						dtPlantWah = 0;
						dtPlantNgan = 0;
						dtPlantWah = 0;
						dtFRecord = 0;
						dtPRecord = 0;
						dtCost = 0;
					}
					if (!branchTmp
							.equals(((Object[]) provList.get(prov + 1))[0]
									.toString())) {
						calRai = totalRai(btPlantRai, btPlantNgan, btPlantWah);
						String[] totalBran = new String[] {
								String.format("%,d-%d-%d|", calRai[0],
										calRai[1], calRai[2]),
								String.format("%,.2f", btFRecord),
								String.format("%,.2f", btPRecord),
								String.format("%,.2f", btPRecord / btPlantRai),
								String.format("%,.2f", btCost),
								String.format("%,.2f", btCost / btPlantRai),
								String
										.format(
												"%,.2f",
												(btPRecord / btPlantRai) > 0 ? (btCost / btPlantRai)
														/ (btPRecord / btPlantRai)
														: 0f) };
						if (linePage < table.size()) {
							if (table.size() > 1) {
								document.add(title);
							}
							document.add(table);

							document.newPage();
							document.add(head);
							document.add(head_date);
							table = new Table(1);
							table.setAlignment(Cell.ALIGN_CENTER);
							table.setWidth(100);
							table.setPadding(2f);
							table.insertTable(headTable(newRows(colWidth),
									font_body, headTable));
							title.setLeading(5f);
							title.setSpacingAfter(0f);
							linePage = 20;
							newPage = true;
						}
						table.insertTable(totalTable(colTotalWidth, font_total,
								"ยอดรวมสาขา " + branchTmp, totalBran));
						ptPlantRai += btPlantRai;
						ptFRecord += btFRecord;
						ptPRecord += btPRecord;
						ptCost += btCost;
						btPlantRai = 0;
						btPlantNgan = 0;
						btPlantWah = 0;
						btFRecord = 0;
						btPRecord = 0;
						btCost = 0;
						if (!provinceTmp.equals(((Object[]) provList
								.get(prov + 1))[14].toString())) {
							calRai = totalRai(ptPlantRai, ptPlantNgan,
									ptPlantWah);
							String[] totalProv = new String[] {
									String.format("%,d-%d-%d|", calRai[0],
											calRai[1], calRai[2]),
									String.format("%,.2f", ptFRecord),
									String.format("%,.2f", ptPRecord),
									String.format("%,.2f", ptPRecord
											/ ptPlantRai),
									String.format("%,.2f", ptCost),
									String.format("%,.2f", ptCost / ptPlantRai),
									String
											.format(
													"%,.2f",
													(ptPRecord / ptPlantRai) > 0 ? (ptCost / ptPlantRai)
															/ (ptPRecord / ptPlantRai)
															: 0f) };
							if (linePage < table.size()) {
								if (table.size() > 1) {
									document.add(title);
								}
								document.add(table);

								document.newPage();
								document.add(head);
								document.add(head_date);
								table = new Table(1);
								table.setAlignment(Cell.ALIGN_CENTER);
								table.setWidth(100);
								table.setPadding(2f);
								table.insertTable(headTable(newRows(colWidth),
										font_body, headTable));
								title.setLeading(5f);
								title.setSpacingAfter(0f);
								linePage = 20;
								newPage = true;
							}
							table.insertTable(totalTable(colTotalWidth,
									font_total, "ยอดรวมจังหวัด "
											+ obj[14].toString(), totalProv));

							ptPlantRai = 0;
							ptPlantNgan = 0;
							ptPlantWah = 0;
							ptFRecord = 0;
							ptPRecord = 0;
							ptCost = 0;
						}

						if (table.size() > 1) {
							document.add(title);
							document.add(table);
							linePage -= table.size();
							table = new Table(1);
							table.setAlignment(Cell.ALIGN_CENTER);
							table.setWidth(100);
							table.setPadding(2f);
						}
					}
				} else {
					calRai = totalRai(dtPlantRai, dtPlantNgan, dtPlantWah);
					String[] totalFarm = new String[] {
							String.format("%,d-%d-%d|", calRai[0],
									calRai[1], calRai[2]),
							String.format("%,.2f", dtFRecord),
							String.format("%,.2f", dtPRecord),
							String.format("%,.2f", dtPRecord / dtPlantRai),
							String.format("%,.2f", dtCost),
							String.format("%,.2f", dtCost / dtPlantRai),
							String
									.format(
											"%,.2f",
											(dtPRecord / dtPlantRai) > 0 ? (dtCost / dtPlantRai)
													/ (dtPRecord / dtPlantRai)
													: 0f) };
					if (linePage < table.size()) {
						if (table.size() > 1) {
							document.add(title);
						}
						document.add(table);

						document.newPage();
						document.add(head);
						document.add(head_date);
						table = new Table(1);
						table.setAlignment(Cell.ALIGN_CENTER);
						table.setWidth(100);
						table.setPadding(2f);
						table.insertTable(headTable(newRows(colWidth),
								font_body, headTable));
						title.setLeading(5f);
						title.setSpacingAfter(0f);
						linePage = 20;
						newPage = true;
					}
					table.insertTable(totalTable(colTotalWidth, font_total,
							"รวมกลุ่ม" + farmerTmp, totalFarm));
					btPlantRai += dtPlantRai;
					btPlantNgan += dtPlantNgan;
					btPlantWah += dtPlantWah;
					btFRecord += dtFRecord;
					btPRecord += dtPRecord;
					btCost += dtCost;
					dtPlantRai = 0;
					dtPlantNgan = 0;
					dtPlantWah = 0;
					dtFRecord = 0;
					dtPRecord = 0;
					dtCost = 0;

					calRai = totalRai(btPlantRai, btPlantNgan, btPlantWah);
					String[] totalBran = new String[] {
							String.format("%,d-%d-%d|", calRai[0], calRai[1],
									calRai[2]),
							String.format("%,.2f", btFRecord),
							String.format("%,.2f", btPRecord),
							String.format("%,.2f", btPRecord / btPlantRai),
							String.format("%,.2f", btCost),
							String.format("%,.2f", btCost / btPlantRai),
							String
									.format(
											"%,.2f",
											(btPRecord / btPlantRai) > 0 ? (btCost / btPlantRai)
													/ (btPRecord / btPlantRai)
													: 0f) };
					if (linePage < table.size()) {
						if (table.size() > 1) {
							document.add(title);
						}
						document.add(table);

						document.newPage();
						document.add(head);
						document.add(head_date);
						table = new Table(1);
						table.setAlignment(Cell.ALIGN_CENTER);
						table.setWidth(100);
						table.setPadding(2f);
						table.insertTable(headTable(newRows(colWidth),
								font_body, headTable));
						title.setLeading(5f);
						title.setSpacingAfter(0f);
						linePage = 20;
						newPage = true;
					}
					table.insertTable(totalTable(colTotalWidth, font_total,
							"ยอดรวมสาขา " + branchTmp, totalBran));
					ptPlantRai += btPlantRai;
					ptPlantNgan += btPlantNgan;
					ptPlantWah += btPlantWah;
					ptFRecord += btFRecord;
					ptPRecord += btPRecord;
					ptCost += btCost;
					btPlantRai = 0;
					btPlantNgan = 0;
					btPlantWah = 0;
					btFRecord = 0;
					btPRecord = 0;
					btCost = 0;

					calRai = totalRai(ptPlantRai, ptPlantNgan, ptPlantWah);
					String[] totalProv = new String[] {
							String.format("%,d-%d-%d|", calRai[0], calRai[1],
									calRai[2]),
							String.format("%,.2f", ptFRecord),
							String.format("%,.2f", ptPRecord),
							String.format("%,.2f", ptPRecord / ptPlantRai),
							String.format("%,.2f", ptCost),
							String.format("%,.2f", ptCost / ptPlantRai),
							String
									.format(
											"%,.2f",
											(ptPRecord / ptPlantRai) > 0 ? (ptCost / ptPlantRai)
													/ (ptPRecord / ptPlantRai)
													: 0f) };
					if (linePage < table.size()) {
						if (table.size() > 1) {
							document.add(title);
						}
						document.add(table);

						document.newPage();
						document.add(head);
						document.add(head_date);
						table = new Table(1);
						table.setAlignment(Cell.ALIGN_CENTER);
						table.setWidth(100);
						table.setPadding(2f);
						table.insertTable(headTable(newRows(colWidth),
								font_body, headTable));
						title.setLeading(5f);
						title.setSpacingAfter(0f);
						linePage = 20;
						newPage = true;
					}
					table.insertTable(totalTable(colTotalWidth, font_total,
							"ยอดรวมจังหวัด " + obj[14].toString(), totalProv));

					document.add(title);
					document.add(table);
				}
			}

		} catch (Exception e) {
			document.add(new Paragraph("เกิดการดึงข้อมูลผิดพลาด", font_title));
			e.printStackTrace();
			log.error(e, e);
		} finally {
			sf.getCurrentSession().close();
		}
		return document;
	}

	private Document ReportR109_1(SessionFactory sf, Document document,
			Font font_title, Font font_body, Font font_total, ReportForm eform)
			throws DocumentException {
		try {
			sf.getCurrentSession().beginTransaction();
			Paragraph head = new Paragraph(
					"รายงานแสดงข้อมูลเปรียบเทียบผลผลิตจริงที่ได้รับกับผลตอบแทนสุทธิ ตามกลุ่มเกษตรกร มีเป้าหมาย "
							+ eform.getPlantYear() + "/" + eform.getPlantNo(),
					font_title);
			head.setAlignment(Element.ALIGN_CENTER);
			document.add(head);

			Paragraph head_date = new Paragraph("สิ้นสุด ณ วันที่ "
					+ dateThai(eform.getEffectiveDate(), false), font_title);
			head_date.setAlignment(Element.ALIGN_CENTER);
			head_date.setSpacingBefore(5f);
			document.add(head_date);

			Table table = new Table(1);
			table.setAlignment(Cell.ALIGN_CENTER);
			table.setWidth(100);
			table.setPadding(2f);
			float[] colWidth, colTotalWidth;
			colWidth = new float[] { 15, 15, 15, 20, 15, 15, 10, 10, 30, 30, 45 };
			colTotalWidth = new float[] { 80, 15, 10, 10, 15, 15, 15, 15, 15,
					15, 15 };
			String[] headTable = new String[] {
					"ชื่อกลุ่ม",
					"ตำบลที่\nตั้งแปลง",
					"บัตรประจำตัว\nประชาชน \n(เลขที่)",
					"ชื่อ-สกุล",
					"กลุ่มพันธุ์",
					"เนื้อที่เพาะปลูก\nไร่-งาน-วา",
					"ต้นทุนการ\nผลิตรวม\n(บาท)",
					"ราคา\nขาย\nต่อกก.\n (บาท)",
					"|ผลผลิตจริงที่ได้รับ|ผลผลิตรวม\n(กก.)|ผลผลิต\nเฉลี่ยต่อไร่\n(กก.)",
					"|ผลผลิตที่จำหน่ายได้|รวม\n(บาท)|เฉลี่ยต่อไร่\n(บาท)",
					"|ผลตอบแทนสุทธิ|ผลตอบแทนรวม\n(บาท)|ผลตอบแทน\nสุทธิต่อกก.\n(บาท)|ผลตอบแทน\nสุทธิต่อไร่\n(บาท)" };
			table
					.insertTable(headTable(newRows(colWidth), font_body,
							headTable));

			ReportHome rhome = new ReportHome();
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			List provList = rhome.findR109_1ByCriteria(eform.getPlantYear(),
					eform.getPlantNo(), df.parse(eform.getEffectiveDate()),
					eform.getProvinceNo(), eform.getBranchCode(), eform
							.getBreedTypeId(), eform.getBreedGroupId());
			if (eform.getBranchCode() > 0) {
				BranchHome bhome = new BranchHome();
				List<Branch> pbList = bhome
						.findByPBranch(eform.getBranchCode());
				List<Branch> pbranchCode = new ArrayList();
				if (pbList.size() > 0) {
					pbranchCode
							.addAll(getPBranch(pbList, eform.getBranchCode()));
					for (int i = 0; i < pbranchCode.size(); i++) {
						provList.addAll(rhome.findR109_1ByCriteria(eform
								.getPlantYear(), eform.getPlantNo(), df
								.parse(eform.getEffectiveDate()), 0,
								pbranchCode.get(i).getBranchCode(), eform
										.getBreedTypeId(), eform
										.getBreedGroupId()));
					}
				}
			}

			String branchTmp = "", subDistrictTmp = "", provinceTmp = "", FarmerTmp = "";
			int dtPlantRai = 0, dtPlantNgan = 0, dtPlantWah = 0;
			int btPlantRai = 0, btPlantNgan = 0, btPlantWah = 0;
			int ptPlantRai = 0, ptPlantNgan = 0, ptPlantWah = 0;
			float dtFRecord = 0, dtSell = 0, dtCost = 0, dtSumSell = 0;
			float btFRecord = 0, btSell = 0, btCost = 0, btSumSell = 0;
			float ptFRecord = 0, ptSell = 0, ptCost = 0, ptSumSell = 0;
			int linePage = 27;
			Paragraph title = new Paragraph();
			if (provList.size() <= 0) {
				document.add(table);
				title = new Paragraph("ไม่มีข้อมูล", font_title);
				title.setAlignment(Element.ALIGN_CENTER);
				document.add(title);
			} else {
				int target = 0;
				for (int i = 0; i < provList.size(); i++) {
					Object[] obj = ((Object[]) provList.get(i));
					target += Float.parseFloat(obj[13] != null ? obj[13]
							.toString() : "0.00");
				}
				title = new Paragraph(String.format(
						"เป้าหมายปริมาณผลผลิต %d ตัน", target / 1000),
						font_total);
				title.setAlignment(Element.ALIGN_RIGHT);
				title.setLeading(25f);
				title.setSpacingAfter(-25f);
				document.add(title);
			}
			boolean newPage = true;
			int frist = 1;
			for (int prov = 0; prov < provList.size(); prov++) {
				Object[] obj = ((Object[]) provList.get(prov));

				if (!branchTmp.equals(obj[0].toString())) {
					font_title = new Font(font_total.getBaseFont(), 9F, 1);
					title = new Paragraph("ธ.ก.ส. จังหวัด" + obj[14].toString()
							+ " " + obj[0].toString(), font_title);
					title.setAlignment(Element.ALIGN_LEFT);
					title.setLeading(5f);
					title.setSpacingBefore(20f);
					branchTmp = obj[0].toString();
					linePage -= 2;
					newPage = true;
				}

				Table detail = new Table(colWidth.length);
				detail.setWidths(colWidth);
				detail.setAlignment(Cell.ALIGN_CENTER);
				detail.setWidth(100);
				detail.setPadding(2f);

				if (linePage < table.size()) {
					if (table.size() > 1) {
						document.add(title);
					}
					document.add(table);

					document.newPage();
					document.add(head);
					document.add(head_date);
					table = new Table(1);
					table.setAlignment(Cell.ALIGN_CENTER);
					table.setWidth(100);
					table.setPadding(2f);
					table.insertTable(headTable(newRows(colWidth), font_body,
							headTable));
					title.setLeading(5f);
					title.setSpacingAfter(0f);
					linePage = 25;
					newPage = true;
				}
				Cell sCell = null;
				if (FarmerTmp.equals(obj[1].toString()) && !newPage
						&& subDistrictTmp.equals(obj[2].toString())) {
					sCell = new Cell(new Phrase("", font_body));
					sCell.setBorderWidthTop(0);
					sCell.setBorderWidthBottom(0);
					sCell.setBorderWidthRight(0.1f);
					detail.addCell(sCell);
				} else {
					sCell = new Cell(new Phrase(obj[1] != null ? obj[1]
							.toString() : " ", font_body));
					sCell.setHorizontalAlignment(Cell.ALIGN_LEFT);
					sCell.setVerticalAlignment(Cell.ALIGN_TOP);
					sCell.setBorderColorTop(Color.GRAY);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
					sCell.setBorderWidthBottom(0);
					sCell.setBorderWidthRight(0.1f);
					detail.addCell(sCell);
					FarmerTmp = obj[1].toString();
				}

				if (subDistrictTmp.equals(obj[2].toString()) && !newPage) {
					sCell = new Cell(new Phrase("", font_body));
					sCell.setBorderWidthTop(0);
					sCell.setBorderWidthBottom(0);
					sCell.setBorderWidthRight(0.1f);
					detail.addCell(sCell);

				} else {
					sCell = new Cell(new Phrase(obj[2].toString(), font_body));
					sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
					sCell.setVerticalAlignment(Cell.ALIGN_TOP);
					sCell.setBorderColorTop(Color.GRAY);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
					sCell.setBorderWidthBottom(0);
					sCell.setBorderWidthRight(0.1f);
					detail.addCell(sCell);
					newPage = false;
				}

				sCell = new Cell(new Phrase(obj[3].toString(), font_body));
				sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
				sCell.setVerticalAlignment(Cell.ALIGN_TOP);
				sCell.setBorderColorTop(Color.GRAY);
				sCell.setBorderWidthTop(0.1f);
				sCell.setBorderWidthRight(0.1f);
				detail.addCell(sCell);

				sCell = new Cell(new Phrase(" " + obj[4].toString()
						+ obj[5].toString() + " " + obj[6].toString(),
						font_body));
				sCell.setHorizontalAlignment(Cell.ALIGN_LEFT);
				sCell.setVerticalAlignment(Cell.ALIGN_TOP);
				sCell.setBorderColorTop(Color.GRAY);
				sCell.setBorderWidthTop(0.1f);
				sCell.setBorderWidthRight(0.1f);
				detail.addCell(sCell);

				sCell = new Cell(new Phrase(obj[7].toString(), font_body));
				sCell.setHorizontalAlignment(Cell.ALIGN_LEFT);
				sCell.setVerticalAlignment(Cell.ALIGN_TOP);
				sCell.setBorderColorTop(Color.GRAY);
				sCell.setBorderWidthTop(0.1f);
				sCell.setBorderWidthRight(0.1f);
				detail.addCell(sCell);

				// Table tmp = new Table(3);
				int rai = Integer.parseInt(obj[8].toString());
				int ngan = Integer.parseInt(obj[9].toString());
				int wah = Integer.parseInt(obj[10].toString());

				if (wah / 100 > 0) {
					ngan += wah / 100;
					wah = wah % 100;

				}
				if (ngan / 4 > 0) {
					rai += ngan / 4;
					ngan = ngan % 4;
				}

				sCell = new Cell(new Phrase(String.format("%,d-%d-%d", rai,
						ngan, wah), font_body));
				sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
				sCell.setVerticalAlignment(Cell.ALIGN_TOP);
				sCell.setBorderColorTop(Color.GRAY);
				sCell.setBorderWidthTop(0.1f);
				sCell.setBorderWidthRight(0.1f);
				detail.addCell(sCell);

				sCell = new Cell(new Phrase(String.format("%,.2f", Float
						.parseFloat(obj[11].toString())), font_body));
				sCell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
				sCell.setVerticalAlignment(Cell.ALIGN_TOP);
				sCell.setBorderColorTop(Color.GRAY);
				sCell.setBorderWidthTop(0.1f);
				sCell.setBorderWidthRight(0.1f);
				detail.addCell(sCell);

				sCell = new Cell(new Phrase(String.format("%,.2f", Float
						.parseFloat(obj[12].toString())), font_body));
				sCell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
				sCell.setVerticalAlignment(Cell.ALIGN_TOP);
				sCell.setBorderColorTop(Color.GRAY);
				sCell.setBorderWidthTop(0.1f);
				sCell.setBorderWidthRight(0.1f);
				detail.addCell(sCell);

				Table tmp = new Table(2);
				sCell = new Cell(new Phrase(String.format("%,.2f", Float
						.parseFloat(obj[13] != null ? obj[13].toString()
								: "0.00")), font_body));
				sCell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
				sCell.setVerticalAlignment(Cell.ALIGN_TOP);
				sCell.setBorderColorTop(Color.GRAY);
				sCell.setBorderWidthTop(0.1f);
				sCell.setBorderWidthRight(0.1f);
				tmp.addCell(sCell);

				float avgPlat = Float.parseFloat(obj[13] != null ? obj[13]
						.toString() : "0.00")
						/ rai;
				sCell = new Cell(new Phrase(String.format("%,.2f", avgPlat),
						font_body));
				sCell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
				sCell.setVerticalAlignment(Cell.ALIGN_TOP);
				sCell.setBorderColorTop(Color.GRAY);
				sCell.setBorderWidthTop(0.1f);
				sCell.setBorderWidthRight(0.1f);
				tmp.addCell(sCell);
				detail.insertTable(tmp);

				tmp = new Table(2);
				float sellPlant = Float.parseFloat(obj[13] != null ? obj[13]
						.toString() : "0.00")
						* Float.parseFloat(obj[12].toString());
				sCell = new Cell(new Phrase(String.format("%,.2f", sellPlant),
						font_body));
				sCell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
				sCell.setVerticalAlignment(Cell.ALIGN_TOP);
				sCell.setBorderColorTop(Color.GRAY);
				sCell.setBorderWidthTop(0.1f);
				sCell.setBorderWidthRight(0.1f);
				tmp.addCell(sCell);

				float avgSPlant = sellPlant / rai;
				sCell = new Cell(new Phrase(String.format("%,.2f", avgSPlant),
						font_body));
				sCell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
				sCell.setVerticalAlignment(Cell.ALIGN_TOP);
				sCell.setBorderColorTop(Color.GRAY);
				sCell.setBorderWidthTop(0.1f);
				sCell.setBorderWidthRight(0.1f);
				tmp.addCell(sCell);
				detail.insertTable(tmp);

				tmp = new Table(3);
				float profitPlant = sellPlant
						- Float.parseFloat(obj[11].toString());
				sCell = new Cell(new Phrase(
						String.format("%,.2f", profitPlant), font_body));
				sCell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
				sCell.setVerticalAlignment(Cell.ALIGN_TOP);
				sCell.setBorderColorTop(Color.GRAY);
				sCell.setBorderWidthTop(0.1f);
				sCell.setBorderWidthRight(0.1f);
				tmp.addCell(sCell);

				float avgProfitPlant = profitPlant
						/ Float.parseFloat(obj[13] != null ? obj[13].toString()
								: "0.00");
				sCell = new Cell(new Phrase(String.format("%,.2f",
						avgProfitPlant > 0 ? avgProfitPlant : 0f), font_body));
				sCell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
				sCell.setVerticalAlignment(Cell.ALIGN_TOP);
				sCell.setBorderColorTop(Color.GRAY);
				sCell.setBorderWidthTop(0.1f);
				sCell.setBorderWidthRight(0.1f);
				tmp.addCell(sCell);

				float avgProfitRai = profitPlant / rai;
				sCell = new Cell(new Phrase(String
						.format("%,.2f", avgProfitRai), font_body));
				sCell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
				sCell.setVerticalAlignment(Cell.ALIGN_TOP);
				sCell.setBorderColorTop(Color.GRAY);
				sCell.setBorderWidthTop(0.1f);
				sCell.setBorderWidthRight(0.1f);
				tmp.addCell(sCell);
				detail.insertTable(tmp);

				table.insertTable(detail);

				dtPlantRai += rai;
				dtPlantNgan += ngan;
				dtPlantWah += wah;
				dtCost += Float.parseFloat(obj[11].toString());
				dtSell += Float.parseFloat(obj[12].toString());
				dtFRecord += Float.parseFloat(obj[13] != null ? obj[13]
						.toString() : "0.00");
				dtSumSell += Float.parseFloat(obj[12].toString())
						* Float.parseFloat(obj[13] != null ? obj[13].toString()
								: "0.00");
				subDistrictTmp = obj[2].toString();
				provinceTmp = obj[14].toString();

				if (prov + 1 != provList.size()) {
					if (!subDistrictTmp.equals(((Object[]) provList
							.get(prov + 1))[2].toString())
							|| !branchTmp.equals(((Object[]) provList
									.get(prov + 1))[0].toString())) {
						calRai = totalRai(dtPlantRai, dtPlantNgan, dtPlantWah);
						String[] total = new String[] {
								String.format("%,d-%d-%d|", calRai[0],
										calRai[1], calRai[2]),
								String.format("%,.2f", dtCost),
								String.format("%,.2f", dtSell),
								String.format("%,.2f", dtFRecord),
								String.format("%,.2f", dtFRecord / dtPlantRai),
								String.format("%,.2f", dtSumSell),
								String.format("%,.2f", dtSumSell / dtPlantRai),
								String.format("%,.2f", dtSumSell - dtCost),
								String.format("%,.2f", (dtSumSell - dtCost)
										/ dtFRecord > 0 ? (dtSumSell - dtCost)
										/ dtFRecord : 0f),
								String.format("%,.2f", (dtSumSell - dtCost)
										/ dtPlantRai) };
						if (linePage < table.size()) {
							if (table.size() > 1) {
								document.add(title);
							}
							document.add(table);

							document.newPage();
							document.add(head);
							document.add(head_date);
							table = new Table(1);
							table.setAlignment(Cell.ALIGN_CENTER);
							table.setWidth(100);
							table.setPadding(2f);
							table.insertTable(headTable(newRows(colWidth),
									font_body, headTable));
							title.setLeading(5f);
							title.setSpacingAfter(0f);
							linePage = 25;
							newPage = true;
						}
						table.insertTable(totalTable(colTotalWidth, font_total,
								"รวม", total));
						btPlantRai += dtPlantRai;
						btPlantNgan += dtPlantNgan;
						btPlantWah += dtPlantWah;
						btFRecord += dtFRecord;
						btSell += dtSell;
						btCost += dtCost;
						btSumSell += dtSumSell;
						dtPlantRai = 0;
						dtPlantNgan = 0;
						dtPlantWah = 0;
						dtFRecord = 0;
						dtSell = 0;
						dtCost = 0;
						dtSumSell = 0;
					}

					if (!branchTmp
							.equals(((Object[]) provList.get(prov + 1))[0]
									.toString())) {
						calRai = totalRai(btPlantRai, btPlantNgan, btPlantWah);
						String[] totalBran = new String[] {
								String.format("%,d-%d-%d|", calRai[0],
										calRai[1], calRai[2]),
								String.format("%,.2f", btCost),
								String.format("%,.2f", btSell),
								String.format("%,.2f", btFRecord),
								String.format("%,.2f", btFRecord / btPlantRai),
								String.format("%,.2f", btSumSell),
								String.format("%,.2f", btSumSell / btPlantRai),
								String.format("%,.2f", btSumSell - btCost),
								String.format("%,.2f", (btSumSell - btCost)
										/ btFRecord > 0 ? (btSumSell - btCost)
										/ btFRecord : 0f),
								String.format("%,.2f", (btSumSell - btCost)
										/ btPlantRai) };
						if (linePage < table.size()) {
							if (table.size() > 1) {
								document.add(title);
							}
							document.add(table);

							document.newPage();
							document.add(head);
							document.add(head_date);
							table = new Table(1);
							table.setAlignment(Cell.ALIGN_CENTER);
							table.setWidth(100);
							table.setPadding(2f);
							table.insertTable(headTable(newRows(colWidth),
									font_body, headTable));
							title.setLeading(5f);
							title.setSpacingAfter(0f);
							linePage = 25;
							newPage = true;
						}
						table.insertTable(totalTable(colTotalWidth, font_total,
								"ยอดรวมสาขา " + branchTmp, totalBran));
						ptPlantRai += btPlantRai;
						ptPlantNgan += btPlantNgan;
						ptPlantWah += btPlantWah;
						ptFRecord += btFRecord;
						ptSell += btSell;
						ptCost += btCost;
						ptSumSell += btSumSell;
						btPlantRai = 0;
						btPlantNgan = 0;
						btPlantWah = 0;
						btFRecord = 0;
						btSell = 0;
						btCost = 0;
						btSumSell = 0;

						if (!provinceTmp.equals(((Object[]) provList
								.get(prov + 1))[14].toString())) {
							calRai = totalRai(ptPlantRai, ptPlantNgan,
									ptPlantWah);
							String[] totalProv = new String[] {
									String.format("%,d-%d-%d|", calRai[0],
											calRai[1], calRai[2]),
									String.format("%,.2f", ptCost),
									String.format("%,.2f", ptSell),
									String.format("%,.2f", ptFRecord),
									String.format("%,.2f", ptFRecord
											/ ptPlantRai),
									String.format("%,.2f", ptSumSell),
									String.format("%,.2f", ptSumSell
											/ ptPlantRai),
									String.format("%,.2f", ptSumSell - ptCost),
									String
											.format(
													"%,.2f",
													(ptSumSell - ptCost)
															/ ptFRecord > 0 ? (ptSumSell - ptCost)
															/ ptFRecord
															: 0f),
									String.format("%,.2f", (ptSumSell - ptCost)
											/ ptPlantRai) };
							if (linePage < table.size()) {
								if (table.size() > 1) {
									document.add(title);
								}
								document.add(table);

								document.newPage();
								document.add(head);
								document.add(head_date);
								table = new Table(1);
								table.setAlignment(Cell.ALIGN_CENTER);
								table.setWidth(100);
								table.setPadding(2f);
								table.insertTable(headTable(newRows(colWidth),
										font_body, headTable));
								title.setLeading(5f);
								title.setSpacingAfter(0f);
								linePage = 25;
								newPage = true;
							}
							table.insertTable(totalTable(colTotalWidth,
									font_total, "ยอดรวมจังหวัด "
											+ obj[14].toString(), totalProv));
							ptPlantRai = 0;
							ptPlantNgan = 0;
							ptPlantWah = 0;
							ptFRecord = 0;
							ptSell = 0;
							ptCost = 0;
							ptSumSell = 0;
						}

						if (table.size() > 1) {
							document.add(title);
							document.add(table);
							linePage -= table.size();
							table = new Table(1);
							table.setAlignment(Cell.ALIGN_CENTER);
							table.setWidth(100);
							table.setPadding(2f);
						}
					}
				} else {
					calRai = totalRai(dtPlantRai, dtPlantNgan, dtPlantWah);
					String[] total = new String[] {
							String.format("%,d-%d-%d|", calRai[0], calRai[1],
									calRai[2]),
							String.format("%,.2f", dtCost),
							String.format("%,.2f", dtSell),
							String.format("%,.2f", dtFRecord),
							String.format("%,.2f", dtFRecord / dtPlantRai),
							String.format("%,.2f", dtSumSell),
							String.format("%,.2f", dtSumSell / dtPlantRai),
							String.format("%,.2f", dtSumSell - dtCost),
							String.format("%,.2f", (dtSumSell - dtCost)
									/ dtFRecord > 0 ? (dtSumSell - dtCost)
									/ dtFRecord : 0f),
							String.format("%,.2f", (dtSumSell - dtCost)
									/ dtPlantRai) };
					if (linePage < table.size()) {
						if (table.size() > 1) {
							document.add(title);
						}
						document.add(table);

						document.newPage();
						document.add(head);
						document.add(head_date);
						table = new Table(1);
						table.setAlignment(Cell.ALIGN_CENTER);
						table.setWidth(100);
						table.setPadding(2f);
						table.insertTable(headTable(newRows(colWidth),
								font_body, headTable));
						title.setLeading(5f);
						title.setSpacingAfter(0f);
						linePage = 25;
						newPage = true;
					}
					table.insertTable(totalTable(colTotalWidth, font_total,
							"รวม", total));
					btPlantRai += dtPlantRai;
					btPlantNgan += dtPlantNgan;
					btPlantWah += dtPlantWah;
					btFRecord += dtFRecord;
					btSell += dtSell;
					btCost += dtCost;
					btSumSell += dtSumSell;
					dtPlantRai = 0;
					dtPlantNgan = 0;
					dtPlantWah = 0;
					dtFRecord = 0;
					dtSell = 0;
					dtCost = 0;
					dtSumSell = 0;

					calRai = totalRai(btPlantRai, btPlantNgan, btPlantWah);
					String[] totalBran = new String[] {
							String.format("%,d-%d-%d|", calRai[0], calRai[1],
									calRai[2]),
							String.format("%,.2f", btCost),
							String.format("%,.2f", btSell),
							String.format("%,.2f", btFRecord),
							String.format("%,.2f", btFRecord / btPlantRai),
							String.format("%,.2f", btSumSell),
							String.format("%,.2f", btSumSell / btPlantRai),
							String.format("%,.2f", btSumSell - btCost),
							String.format("%,.2f", (btSumSell - btCost)
									/ btFRecord > 0 ? (btSumSell - btCost)
									/ btFRecord : 0f),
							String.format("%,.2f", (btSumSell - btCost)
									/ btPlantRai) };
					if (linePage < table.size()) {
						if (table.size() > 1) {
							document.add(title);
						}
						document.add(table);

						document.newPage();
						document.add(head);
						document.add(head_date);
						table = new Table(1);
						table.setAlignment(Cell.ALIGN_CENTER);
						table.setWidth(100);
						table.setPadding(2f);
						table.insertTable(headTable(newRows(colWidth),
								font_body, headTable));
						title.setLeading(5f);
						title.setSpacingAfter(0f);
						linePage = 25;
						newPage = true;
					}
					table.insertTable(totalTable(colTotalWidth, font_total,
							"ยอดรวมสาขา " + branchTmp, totalBran));
					ptPlantRai += btPlantRai;
					ptPlantNgan += btPlantNgan;
					ptPlantWah += btPlantWah;
					ptFRecord += btFRecord;
					ptSell += btSell;
					ptCost += btCost;
					ptSumSell += btSumSell;
					btPlantRai = 0;
					btPlantNgan = 0;
					btPlantWah = 0;
					btFRecord = 0;
					btSell = 0;
					btCost = 0;
					btSumSell = 0;
					calRai = totalRai(ptPlantRai, ptPlantNgan, ptPlantWah);
					String[] totalProv = new String[] {
							String.format("%,d-%d-%d|", calRai[0], calRai[1],
									calRai[2]),
							String.format("%,.2f", ptCost),
							String.format("%,.2f", ptSell),
							String.format("%,.2f", ptFRecord),
							String.format("%,.2f", ptFRecord / ptPlantRai),
							String.format("%,.2f", ptSumSell),
							String.format("%,.2f", ptSumSell / ptPlantRai),
							String.format("%,.2f", ptSumSell - ptCost),
							String.format("%,.2f", (ptSumSell - ptCost)
									/ ptFRecord > 0 ? (ptSumSell - ptCost)
									/ ptFRecord : 0f),
							String.format("%,.2f", (ptSumSell - ptCost)
									/ ptPlantRai) };
					if (linePage < table.size()) {
						if (table.size() > 1) {
							document.add(title);
						}
						document.add(table);

						document.newPage();
						document.add(head);
						document.add(head_date);
						table = new Table(1);
						table.setAlignment(Cell.ALIGN_CENTER);
						table.setWidth(100);
						table.setPadding(2f);
						table.insertTable(headTable(newRows(colWidth),
								font_body, headTable));
						title.setLeading(5f);
						title.setSpacingAfter(0f);
						linePage = 25;
						newPage = true;
					}
					table.insertTable(totalTable(colTotalWidth, font_total,
							"ยอดรวมจังหวัด " + obj[14].toString(), totalProv));

					document.add(title);
					document.add(table);
				}
			}

		} catch (Exception e) {
			document.add(new Paragraph("เกิดการดึงข้อมูลผิดพลาด", font_title));
			e.printStackTrace();
			log.error(e, e);
		} finally {
			sf.getCurrentSession().close();
		}
		return document;
	}

	private Document ReportR109_3(SessionFactory sf, Document document,
			Font font_title, Font font_body, Font font_total, ReportForm eform)
			throws DocumentException {
		try {
			sf.getCurrentSession().beginTransaction();
			Paragraph head = new Paragraph(
					"รายงานแสดงข้อมูลเปรียบเทียบผลผลิตจริงที่ได้รับกับผลตอบแทนสุทธิ ตามปีการผลิต "
							+ eform.getPlantYear() + "/" + eform.getPlantNo(),
					font_title);
			head.setAlignment(Element.ALIGN_CENTER);
			document.add(head);

			Paragraph head_date = new Paragraph("สิ้นสุด ณ วันที่ "
					+ dateThai(eform.getEffectiveDate(), false), font_title);
			head_date.setAlignment(Element.ALIGN_CENTER);
			head_date.setSpacingBefore(5f);
			document.add(head_date);

			Table table = new Table(1);
			table.setAlignment(Cell.ALIGN_CENTER);
			table.setWidth(100);
			table.setPadding(2f);
			float[] colWidth, colTotalWidth;
			colWidth = new float[] { 15, 20, 15, 20, 15, 10, 10, 10, 30, 30, 45 };
			colTotalWidth = new float[] { 85, 10, 10, 10, 15, 15, 15, 15, 15,
					15, 15 };
			float[] colInner = new float[] { 1f, 0.75f };
			String[] headTable = new String[] {
					"ชื่อกลุ่ม",
					"1,0.75,|ที่ตั้งแปลง|อำเภอ|ตำบล",
					"บัตรประจำตัว\nประชาชน \n(เลขที่)",
					"ชื่อ-สกุล",
					"กลุ่มพันธุ์",
					"เนื้อที่\nเพาะปลูก\nไร่-งาน-วา",
					"ต้นทุนการ\nผลิตรวม\n(บาท)",
					"ราคา\nขาย\nต่อกก.\n (บาท)",
					"|ผลผลิตจริงที่ได้รับ|ผลผลิตรวม\n(กก.)|ผลผลิต\nเฉลี่ยต่อไร่\n(กก.)",
					"|ผลผลิตที่จำหน่ายได้|รวม\n(บาท)|เฉลี่ยต่อไร่\n(บาท)",
					"|ผลตอบแทนสุทธิ|ผลตอบแทนรวม\n(บาท)|ผลตอบแทน\nสุทธิต่อกก.\n(บาท)|ผลตอบแทน\nสุทธิต่อไร่\n(บาท)" };
			table
					.insertTable(headTable(newRows(colWidth), font_body,
							headTable));

			ReportHome rhome = new ReportHome();
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			List provList = rhome.findR109_3ByCriteria(eform.getPlantYear(),
					eform.getPlantNo(), df.parse(eform.getEffectiveDate()),
					eform.getProvinceNo(), eform.getBranchCode(), eform
							.getBreedTypeId(), eform.getBreedGroupId());
			if (eform.getBranchCode() > 0) {
				BranchHome bhome = new BranchHome();
				List<Branch> pbList = bhome
						.findByPBranch(eform.getBranchCode());
				List<Branch> pbranchCode = new ArrayList();
				if (pbList.size() > 0) {
					pbranchCode
							.addAll(getPBranch(pbList, eform.getBranchCode()));
					for (int i = 0; i < pbranchCode.size(); i++) {
						provList.addAll(rhome.findR109_3ByCriteria(eform
								.getPlantYear(), eform.getPlantNo(), df
								.parse(eform.getEffectiveDate()), 0,
								pbranchCode.get(i).getBranchCode(), eform
										.getBreedTypeId(), eform
										.getBreedGroupId()));
					}
				}
			}

			String branchTmp = "", subDistrictTmp = "", DistrictTmp = "", provinceTmp = "", farmerTmp = "";
			int dtPlantRai = 0, dtPlantNgan = 0, dtPlantWah = 0;
			int btPlantRai = 0, btPlantNgan = 0, btPlantWah = 0;
			int ptPlantRai = 0, ptPlantNgan = 0, ptPlantWah = 0;
			float dtFRecord = 0, dtSell = 0, dtCost = 0, dtSumSell = 0;
			float btFRecord = 0, btSell = 0, btCost = 0, btSumSell = 0;
			float ptFRecord = 0, ptSell = 0, ptCost = 0, ptSumSell = 0;
			int linePage = 26;
			Paragraph title = new Paragraph();
			if (provList.size() <= 0) {
				document.add(table);
				title = new Paragraph("ไม่มีข้อมูล", font_title);
				title.setAlignment(Element.ALIGN_CENTER);
				document.add(title);
			} else {
				int target = 0;
				for (int i = 0; i < provList.size(); i++) {
					Object[] obj = ((Object[]) provList.get(i));
					target += Float.parseFloat(obj[13] != null ? obj[13]
							.toString() : "0.00");
				}
				title = new Paragraph(String.format(
						"การรวมกลุ่ม(หลายอำเภอ)เป้าหมายปริมาณผลผลิต %d ตัน", target / 1000),
						font_total);
				title.setAlignment(Element.ALIGN_RIGHT);
				title.setLeading(25f);
				title.setSpacingAfter(-25f);
				document.add(title);
			}
			boolean newPage = true;
			int frist = 1;
			for (int prov = 0; prov < provList.size(); prov++) {
				Object[] obj = ((Object[]) provList.get(prov));

				if (!branchTmp.equals(obj[0].toString())) {
					font_title = new Font(font_total.getBaseFont(), 9F, 1);
					title = new Paragraph("ธ.ก.ส. จังหวัด" + obj[14].toString()
							+ " " + obj[0].toString(), font_title);
					title.setAlignment(Element.ALIGN_LEFT);
					title.setLeading(5f);
					title.setSpacingBefore(20f);
					branchTmp = obj[0].toString();
					linePage -= 2;
					newPage = true;
				}

				Table detail = new Table(colWidth.length);
				detail.setWidths(colWidth);
				detail.setAlignment(Cell.ALIGN_CENTER);
				detail.setWidth(100);
				detail.setPadding(2f);

				Cell sCell = null;
				if (linePage < table.size()) {
					if (table.size() > 1) {
						document.add(title);
					}
					document.add(table);

					document.newPage();
					document.add(head);
					document.add(head_date);
					table = new Table(1);
					table.setAlignment(Cell.ALIGN_CENTER);
					table.setWidth(100);
					table.setPadding(2f);
					table.insertTable(headTable(newRows(colWidth), font_body,
							headTable));
					title.setLeading(5f);
					title.setSpacingAfter(0f);
					linePage = 24;
					newPage = true;
				}

				if (farmerTmp.equals(obj[1].toString()) && !newPage) {
					sCell = new Cell(new Phrase(" ", font_body));
					sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
					sCell.setVerticalAlignment(Cell.ALIGN_TOP);
					sCell.setBorderColorTop(Color.GRAY);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
					sCell.setBorderWidthTop(0);
					sCell.setBorderWidthBottom(0);
					sCell.setBorderWidthRight(0.1f);
					detail.addCell(sCell);
				} else {
					
					sCell = new Cell(new Phrase(obj[1].toString(), font_body));
					sCell.setHorizontalAlignment(Cell.ALIGN_LEFT);
					sCell.setVerticalAlignment(Cell.ALIGN_TOP);
					sCell.setBorderColorTop(Color.GRAY);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
					sCell.setBorderWidthBottom(0);
					sCell.setBorderWidthRight(0.1f);
					detail.addCell(sCell);
					newPage = true;
					farmerTmp = obj[1].toString();
				}

				if (!subDistrictTmp.equals(obj[2].toString()) || newPage) {
					Table dist = new Table(2);
					dist.setWidths(colInner);
					if (DistrictTmp.equals(obj[22].toString()) && !newPage) {
						sCell = new Cell(new Phrase(" ", font_body));
						sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
						sCell.setVerticalAlignment(Cell.ALIGN_TOP);
						sCell.setBorderColorTop(Color.GRAY);
						sCell.setBorderWidthTop(0.1f);
						sCell.setBorderWidthRight(0.1f);
						sCell.setBorderWidthTop(0);
						sCell.setBorderWidthBottom(0);
						sCell.setBorderWidthRight(0.1f);
						dist.addCell(sCell);
					} else {
						sCell = new Cell(new Phrase(obj[22].toString(),
								font_body));
						sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
						sCell.setVerticalAlignment(Cell.ALIGN_TOP);
						sCell.setBorderColorTop(Color.GRAY);
						sCell.setBorderWidthTop(0.1f);
						sCell.setBorderWidthRight(0.1f);
						sCell.setBorderWidthTop(0.1f);
						sCell.setBorderWidthRight(0.1f);
						sCell.setBorderWidthBottom(0);
						sCell.setBorderWidthRight(0.1f);
						dist.addCell(sCell);
						DistrictTmp = obj[22].toString();
					}
					sCell = new Cell(new Phrase(obj[2].toString(), font_body));
					sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
					sCell.setVerticalAlignment(Cell.ALIGN_TOP);
					sCell.setBorderColorTop(Color.GRAY);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
					sCell.setBorderWidthBottom(0);
					sCell.setBorderWidthRight(0.1f);
					dist.addCell(sCell);
					detail.insertTable(dist);
					newPage = false;
				} else {
					Table dist = new Table(2);

					sCell = new Cell(new Phrase(" ", font_body));
					sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
					sCell.setVerticalAlignment(Cell.ALIGN_TOP);
					sCell.setBorderColorTop(Color.GRAY);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
					sCell.setBorderWidthTop(0);
					sCell.setBorderWidthBottom(0);
					sCell.setBorderWidthRight(0.1f);
					dist.addCell(sCell);
					dist.addCell(sCell);
					detail.insertTable(dist);
				}

				sCell = new Cell(new Phrase(obj[3].toString(), font_body));
				sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
				sCell.setVerticalAlignment(Cell.ALIGN_TOP);
				sCell.setBorderColorTop(Color.GRAY);
				sCell.setBorderWidthTop(0.1f);
				sCell.setBorderWidthRight(0.1f);
				detail.addCell(sCell);

				sCell = new Cell(new Phrase(" " + obj[4].toString()
						+ obj[5].toString() + " " + obj[6].toString(),
						font_body));
				sCell.setHorizontalAlignment(Cell.ALIGN_LEFT);
				sCell.setVerticalAlignment(Cell.ALIGN_TOP);
				sCell.setBorderColorTop(Color.GRAY);
				sCell.setBorderWidthTop(0.1f);
				sCell.setBorderWidthRight(0.1f);
				detail.addCell(sCell);

				sCell = new Cell(new Phrase(obj[7].toString(), font_body));
				sCell.setHorizontalAlignment(Cell.ALIGN_LEFT);
				sCell.setVerticalAlignment(Cell.ALIGN_TOP);
				sCell.setBorderColorTop(Color.GRAY);
				sCell.setBorderWidthTop(0.1f);
				sCell.setBorderWidthRight(0.1f);
				detail.addCell(sCell);

				// Table tmp = new Table(3);
				int rai = Integer.parseInt(obj[8].toString());
				int ngan = Integer.parseInt(obj[9].toString());
				int wah = Integer.parseInt(obj[10].toString());

				if (wah / 100 > 0) {
					ngan += wah / 100;
					wah = wah % 100;

				}
				if (ngan / 4 > 0) {
					rai += ngan / 4;
					ngan = ngan % 4;
				}

				sCell = new Cell(new Phrase(String.format("%,d-%d-%d", rai,
						ngan, wah), font_body));
				sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
				sCell.setVerticalAlignment(Cell.ALIGN_TOP);
				sCell.setBorderColorTop(Color.GRAY);
				sCell.setBorderWidthTop(0.1f);
				sCell.setBorderWidthRight(0.1f);
				detail.addCell(sCell);

				sCell = new Cell(new Phrase(String.format("%,.2f", Float
						.parseFloat(obj[11].toString())), font_body));
				sCell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
				sCell.setVerticalAlignment(Cell.ALIGN_TOP);
				sCell.setBorderColorTop(Color.GRAY);
				sCell.setBorderWidthTop(0.1f);
				sCell.setBorderWidthRight(0.1f);
				detail.addCell(sCell);

				sCell = new Cell(new Phrase(String.format("%,.2f", Float
						.parseFloat(obj[12].toString())), font_body));
				sCell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
				sCell.setVerticalAlignment(Cell.ALIGN_TOP);
				sCell.setBorderColorTop(Color.GRAY);
				sCell.setBorderWidthTop(0.1f);
				sCell.setBorderWidthRight(0.1f);
				detail.addCell(sCell);

				Table tmp = new Table(2);
				sCell = new Cell(new Phrase(String.format("%,.2f", Float
						.parseFloat(obj[13] != null ? obj[13].toString()
								: "0.00")), font_body));
				sCell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
				sCell.setVerticalAlignment(Cell.ALIGN_TOP);
				sCell.setBorderColorTop(Color.GRAY);
				sCell.setBorderWidthTop(0.1f);
				sCell.setBorderWidthRight(0.1f);
				tmp.addCell(sCell);

				float avgPlat = Float.parseFloat(obj[13] != null ? obj[13]
						.toString() : "0.00")
						/ rai;
				sCell = new Cell(new Phrase(String.format("%,.2f", avgPlat),
						font_body));
				sCell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
				sCell.setVerticalAlignment(Cell.ALIGN_TOP);
				sCell.setBorderColorTop(Color.GRAY);
				sCell.setBorderWidthTop(0.1f);
				sCell.setBorderWidthRight(0.1f);
				tmp.addCell(sCell);
				detail.insertTable(tmp);

				tmp = new Table(2);
				float sellPlant = Float.parseFloat(obj[13] != null ? obj[13]
						.toString() : "0.00")
						* Float.parseFloat(obj[12].toString());
				sCell = new Cell(new Phrase(String.format("%,.2f", sellPlant),
						font_body));
				sCell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
				sCell.setVerticalAlignment(Cell.ALIGN_TOP);
				sCell.setBorderColorTop(Color.GRAY);
				sCell.setBorderWidthTop(0.1f);
				sCell.setBorderWidthRight(0.1f);
				tmp.addCell(sCell);

				float avgSPlant = sellPlant / rai;
				sCell = new Cell(new Phrase(String.format("%,.2f", avgSPlant),
						font_body));
				sCell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
				sCell.setVerticalAlignment(Cell.ALIGN_TOP);
				sCell.setBorderColorTop(Color.GRAY);
				sCell.setBorderWidthTop(0.1f);
				sCell.setBorderWidthRight(0.1f);
				tmp.addCell(sCell);
				detail.insertTable(tmp);

				tmp = new Table(3);
				float profitPlant = sellPlant
						- Float.parseFloat(obj[11].toString());
				sCell = new Cell(new Phrase(
						String.format("%,.2f", profitPlant), font_body));
				sCell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
				sCell.setVerticalAlignment(Cell.ALIGN_TOP);
				sCell.setBorderColorTop(Color.GRAY);
				sCell.setBorderWidthTop(0.1f);
				sCell.setBorderWidthRight(0.1f);
				tmp.addCell(sCell);

				float avgProfitPlant = profitPlant
						/ Float.parseFloat(obj[13] != null ? obj[13].toString()
								: "0.00");
				sCell = new Cell(
						new Phrase(String.format("%,.2f",
								avgProfitPlant > 0 ? avgProfitPlant : 0.00f),
								font_body));
				sCell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
				sCell.setVerticalAlignment(Cell.ALIGN_TOP);
				sCell.setBorderColorTop(Color.GRAY);
				sCell.setBorderWidthTop(0.1f);
				sCell.setBorderWidthRight(0.1f);
				tmp.addCell(sCell);

				float avgProfitRai = profitPlant / rai;
				sCell = new Cell(new Phrase(String
						.format("%,.2f", avgProfitRai), font_body));
				sCell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
				sCell.setVerticalAlignment(Cell.ALIGN_TOP);
				sCell.setBorderColorTop(Color.GRAY);
				sCell.setBorderWidthTop(0.1f);
				sCell.setBorderWidthRight(0.1f);
				tmp.addCell(sCell);
				detail.insertTable(tmp);

				table.insertTable(detail);

				dtPlantRai += rai;
				dtPlantNgan += ngan;
				dtPlantWah += wah;
				dtCost += Float.parseFloat(obj[11].toString());
				dtSell += Float.parseFloat(obj[12].toString());
				dtFRecord += Float.parseFloat(obj[13] != null ? obj[13]
						.toString() : "0.00");
				dtSumSell += Float.parseFloat(obj[12].toString())
						* Float.parseFloat(obj[13] != null ? obj[13].toString()
								: "0.00");
				subDistrictTmp = obj[2].toString();
				provinceTmp = obj[14].toString();

				if (prov + 1 != provList.size()) {
					if (!farmerTmp.equals(((Object[]) provList
							.get(prov + 1))[1].toString())
							|| !branchTmp.equals(((Object[]) provList
									.get(prov + 1))[0].toString())) {
						calRai = totalRai(dtPlantRai, dtPlantNgan, dtPlantWah);
						String[] totalFarm = new String[] {
								String.format("%,d-%d-%d|", calRai[0],
										calRai[1], calRai[2]),
								String.format("%,.2f", dtCost),
								String.format("%,.2f", dtSell),
								String.format("%,.2f", dtFRecord),
								String.format("%,.2f", dtFRecord / dtPlantRai),
								String.format("%,.2f", dtSumSell),
								String.format("%,.2f", dtSumSell / dtPlantRai),
								String.format("%,.2f", dtSumSell - dtCost),
								String.format("%,.2f", (dtSumSell - dtCost)
										/ dtFRecord > 0 ? (dtSumSell - dtCost)
										/ dtFRecord : 0.00f),
								String.format("%,.2f", (dtSumSell - dtCost)
										/ dtPlantRai) };
						if (linePage < table.size()) {
							if (table.size() > 1) {
								document.add(title);
							}
							document.add(table);

							document.newPage();
							document.add(head);
							document.add(head_date);
							table = new Table(1);
							table.setAlignment(Cell.ALIGN_CENTER);
							table.setWidth(100);
							table.setPadding(2f);
							table.insertTable(headTable(newRows(colWidth),
									font_body, headTable));
							title.setLeading(5f);
							title.setSpacingAfter(0f);
							linePage = 24;
							newPage = true;
						}
						table.insertTable(totalTable(colTotalWidth, font_total,
								"รวมกลุ่ม" + farmerTmp, totalFarm));
						btPlantRai += dtPlantRai;
						btPlantNgan += dtPlantNgan;
						btPlantWah += dtPlantWah;
						btFRecord += dtFRecord;
						btSell += dtSell;
						btCost += dtCost;
						btSumSell += dtSumSell;
						dtPlantRai = 0;
						dtPlantNgan = 0;
						dtPlantWah = 0;
						dtFRecord = 0;
						dtSell = 0;
						dtCost = 0;
						dtSumSell = 0;
					}

					if (!branchTmp
							.equals(((Object[]) provList.get(prov + 1))[0]
									.toString())) {
						calRai = totalRai(btPlantRai, btPlantNgan, btPlantWah);
						String[] totalBran = new String[] {
								String.format("%,d-%d-%d|", calRai[0],
										calRai[1], calRai[2]),
								String.format("%,.2f", btCost),
								String.format("%,.2f", btSell),
								String.format("%,.2f", btFRecord),
								String.format("%,.2f", btFRecord / btPlantRai),
								String.format("%,.2f", btSumSell),
								String.format("%,.2f", btSumSell / btPlantRai),
								String.format("%,.2f", btSumSell - btCost),
								String.format("%,.2f", (btSumSell - btCost)
										/ btFRecord > 0 ? (btSumSell - btCost)
										/ btFRecord : 0.00f),
								String.format("%,.2f", (btSumSell - btCost)
										/ btPlantRai) };
						if (linePage < table.size()) {
							if (table.size() > 1) {
								document.add(title);
							}
							document.add(table);

							document.newPage();
							document.add(head);
							document.add(head_date);
							table = new Table(1);
							table.setAlignment(Cell.ALIGN_CENTER);
							table.setWidth(100);
							table.setPadding(2f);
							table.insertTable(headTable(newRows(colWidth),
									font_body, headTable));
							title.setLeading(5f);
							title.setSpacingAfter(0f);
							linePage = 24;
							newPage = true;
						}
						table.insertTable(totalTable(colTotalWidth, font_total,
								"ยอดรวมสาขา " + branchTmp, totalBran));
						ptPlantRai += btPlantRai;
						ptPlantNgan += btPlantNgan;
						ptPlantWah += btPlantWah;
						ptFRecord += btFRecord;
						ptSell += btSell;
						ptCost += btCost;
						ptSumSell += btSumSell;
						btPlantRai = 0;
						btPlantNgan = 0;
						btPlantWah = 0;
						btFRecord = 0;
						btSell = 0;
						btCost = 0;
						btSumSell = 0;
						if (!provinceTmp.equals(((Object[]) provList
								.get(prov + 1))[14].toString())) {
							calRai = totalRai(ptPlantRai, ptPlantNgan,
									ptPlantWah);
							String[] totalProv = new String[] {
									String.format("%,d-%d-%d|", calRai[0],
											calRai[1], calRai[2]),
									String.format("%,.2f", ptCost),
									String.format("%,.2f", ptSell),
									String.format("%,.2f", ptFRecord),
									String.format("%,.2f", ptFRecord
											/ ptPlantRai),
									String.format("%,.2f", ptSumSell),
									String.format("%,.2f", ptSumSell
											/ ptPlantRai),
									String.format("%,.2f", ptSumSell - ptCost),
									String
											.format(
													"%,.2f",
													(ptSumSell - ptCost)
															/ ptFRecord > 0 ? (ptSumSell - ptCost)
															/ ptFRecord
															: 0.00f),
									String.format("%,.2f", (ptSumSell - ptCost)
											/ ptPlantRai) };
							if (linePage < table.size()) {
								if (table.size() > 1) {
									document.add(title);
								}
								document.add(table);

								document.newPage();
								document.add(head);
								document.add(head_date);
								table = new Table(1);
								table.setAlignment(Cell.ALIGN_CENTER);
								table.setWidth(100);
								table.setPadding(2f);
								table.insertTable(headTable(newRows(colWidth),
										font_body, headTable));
								title.setLeading(5f);
								title.setSpacingAfter(0f);
								linePage = 24;
								newPage = true;
							}
							table.insertTable(totalTable(colTotalWidth,
									font_total, "ยอดรวมจังหวัด "
											+ obj[14].toString(), totalProv));
							ptPlantRai = 0;
							ptPlantNgan = 0;
							ptPlantWah = 0;
							ptFRecord = 0;
							ptSell = 0;
							ptCost = 0;
							ptSumSell = 0;
						}

						if (table.size() > 1) {
							document.add(title);
							document.add(table);
							linePage -= table.size();
							table = new Table(1);
							table.setAlignment(Cell.ALIGN_CENTER);
							table.setWidth(100);
							table.setPadding(2f);
						}
					}
				} else {

					calRai = totalRai(dtPlantRai, dtPlantNgan, dtPlantWah);
					String[] totalFarm = new String[] {
							String.format("%,d-%d-%d|", calRai[0],
									calRai[1], calRai[2]),
							String.format("%,.2f", dtCost),
							String.format("%,.2f", dtSell),
							String.format("%,.2f", dtFRecord),
							String.format("%,.2f", dtFRecord / dtPlantRai),
							String.format("%,.2f", dtSumSell),
							String.format("%,.2f", dtSumSell / dtPlantRai),
							String.format("%,.2f", dtSumSell - dtCost),
							String.format("%,.2f", (dtSumSell - dtCost)
									/ dtFRecord > 0 ? (dtSumSell - dtCost)
									/ dtFRecord : 0.00f),
							String.format("%,.2f", (dtSumSell - dtCost)
									/ dtPlantRai) };
					if (linePage < table.size()) {
						if (table.size() > 1) {
							document.add(title);
						}
						document.add(table);

						document.newPage();
						document.add(head);
						document.add(head_date);
						table = new Table(1);
						table.setAlignment(Cell.ALIGN_CENTER);
						table.setWidth(100);
						table.setPadding(2f);
						table.insertTable(headTable(newRows(colWidth),
								font_body, headTable));
						title.setLeading(5f);
						title.setSpacingAfter(0f);
						linePage = 24;
						newPage = true;
					}
					table.insertTable(totalTable(colTotalWidth, font_total,
							"รวมกลุ่ม" + farmerTmp, totalFarm));
					
					btPlantRai += dtPlantRai;
					btPlantNgan += dtPlantNgan;
					btPlantWah += dtPlantWah;
					btFRecord += dtFRecord;
					btSell += dtSell;
					btCost += dtCost;
					btSumSell += dtSumSell;
					dtPlantRai = 0;
					dtPlantNgan = 0;
					dtPlantWah = 0;
					dtFRecord = 0;
					dtSell = 0;
					dtCost = 0;
					dtSumSell = 0;

					calRai = totalRai(btPlantRai, btPlantNgan, btPlantWah);
					String[] totalBran = new String[] {
							String.format("%,d-%d-%d|", calRai[0], calRai[1],
									calRai[2]),
							String.format("%,.2f", btCost),
							String.format("%,.2f", btSell),
							String.format("%,.2f", btFRecord),
							String.format("%,.2f", btFRecord / btPlantRai),
							String.format("%,.2f", btSumSell),
							String.format("%,.2f", btSumSell / btPlantRai),
							String.format("%,.2f", btSumSell - btCost),
							String.format("%,.2f", (btSumSell - btCost)
									/ btFRecord > 0 ? (btSumSell - btCost)
									/ btFRecord : 0.00f),
							String.format("%,.2f", (btSumSell - btCost)
									/ btPlantRai) };
					if (linePage < table.size()) {
						if (table.size() > 1) {
							document.add(title);
						}
						document.add(table);

						document.newPage();
						document.add(head);
						document.add(head_date);
						table = new Table(1);
						table.setAlignment(Cell.ALIGN_CENTER);
						table.setWidth(100);
						table.setPadding(2f);
						table.insertTable(headTable(newRows(colWidth),
								font_body, headTable));
						title.setLeading(5f);
						title.setSpacingAfter(0f);
						linePage = 24;
						newPage = true;
					}
					table.insertTable(totalTable(colTotalWidth, font_total,
							"ยอดรวมสาขา " + branchTmp, totalBran));
					ptPlantRai += btPlantRai;
					ptPlantNgan += btPlantNgan;
					ptPlantWah += btPlantWah;
					ptFRecord += btFRecord;
					ptSell += btSell;
					ptCost += btCost;
					ptSumSell += btSumSell;
					btPlantRai = 0;
					btPlantNgan = 0;
					btPlantWah = 0;
					btFRecord = 0;
					btSell = 0;
					btCost = 0;
					btSumSell = 0;
					calRai = totalRai(ptPlantRai, ptPlantNgan, ptPlantWah);
					String[] totalProv = new String[] {
							String.format("%,d-%d-%d|", calRai[0], calRai[1],
									calRai[2]),
							String.format("%,.2f", ptCost),
							String.format("%,.2f", ptSell),
							String.format("%,.2f", ptFRecord),
							String.format("%,.2f", ptFRecord / ptPlantRai),
							String.format("%,.2f", ptSumSell),
							String.format("%,.2f", ptSumSell / ptPlantRai),
							String.format("%,.2f", ptSumSell - ptCost),
							String.format("%,.2f", (ptSumSell - ptCost)
									/ ptFRecord > 0 ? (ptSumSell - ptCost)
									/ ptFRecord : 0.00f),
							String.format("%,.2f", (ptSumSell - ptCost)
									/ ptPlantRai) };
					if (linePage < table.size()) {
						if (table.size() > 1) {
							document.add(title);
						}
						document.add(table);

						document.newPage();
						document.add(head);
						document.add(head_date);
						table = new Table(1);
						table.setAlignment(Cell.ALIGN_CENTER);
						table.setWidth(100);
						table.setPadding(2f);
						table.insertTable(headTable(newRows(colWidth),
								font_body, headTable));
						title.setLeading(5f);
						title.setSpacingAfter(0f);
						linePage = 24;
						newPage = true;
					}
					table.insertTable(totalTable(colTotalWidth, font_total,
							"ยอดรวมจังหวัด " + obj[14].toString(), totalProv));

					document.add(title);
					document.add(table);
				}
			}

		} catch (Exception e) {
			document.add(new Paragraph("เกิดการดึงข้อมูลผิดพลาด", font_title));
			e.printStackTrace();
			log.error(e, e);
		} finally {
			sf.getCurrentSession().close();
		}
		return document;
	}

	private Document ReportR110(SessionFactory sf, Document document,
			Font font_title, Font font_body, Font font_total, ReportForm eform)
			throws DocumentException {
		try {
			sf.getCurrentSession().beginTransaction();
			Paragraph head = new Paragraph(
					"ก.รายงานการวางแผนปริมาณผลผลิตที่คาดว่าจะได้ตอนเริ่มเพาะปลูก(ตามช่วงเวลาที่คาดว่าจะเก็บเกี่ยว)",
					font_title);
			head.setAlignment(Element.ALIGN_CENTER);
			document.add(head);

			Paragraph head_date = new Paragraph("ตั้งแต่เดือน "
					+ eform.getForecastMonthStart() + " "
					+ eform.getForecastYearStart() + " ถึงเดือน "
					+ eform.getForecastMonthEnd() + " "
					+ eform.getForecastYearEnd(), font_title);
			head_date.setAlignment(Element.ALIGN_CENTER);
			head_date.setSpacingBefore(5f);
			document.add(head_date);

			Table table = new Table(1);
			table.setAlignment(Cell.ALIGN_CENTER);
			table.setWidth(100);
			table.setPadding(2f);
			float[] colWidth, colTotalWidth;
			colWidth = new float[] { 5, 20, 15, 120, 15 };
			colTotalWidth = new float[] { 40, 10, 10, 10, 10, 10, 10, 10, 10,
					10, 10, 10, 10, 15 };
			String[] headTable = new String[] {
					"ลำดับ",
					"จังหวัด /อำเภอ",
					"ตำบล",
					"|ปี "
							+ eform.getForecastYearStart()
							+ " ถึงปี "
							+ eform.getForecastYearEnd()
							+ "|พค.|มิย.|กค.|สค.|กย.|ตค.|พย.|ธค.|มค.|กพ.|มีย.|เมย.",
					"ยอดรวม\nจำนวนตัน" };
			table
					.insertTable(headTable(newRows(colWidth), font_body,
							headTable));
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			ReportHome rhome = new ReportHome();
			Date strDate = dateMonths(eform.getForecastMonthStart(), eform
					.getForecastYearStart(), true);
			Date endDate = dateMonths(eform.getForecastMonthEnd(), eform
					.getForecastYearEnd(), false);
			List provlist = rhome.findR110ByCriteria(strDate, endDate, eform
					.getRegionNo(), eform.getBreedTypeId(), eform
					.getBreedGroupId(), bCode);
			int No = 1;
			String provinceTmp = "", DistrictTmp = "", subDistrictTmp = "";
			float subTotal = 0, sumProvTotal = 0;
			Table tmp = new Table(12);
			float[] sumMonths = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
			float[] sumPMonths = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
			if (provlist.size() <= 0) {
				// document.add(table);
				// Paragraph data = new Paragraph("ไม่มีข้อมูล", font_title);
				// data.setAlignment(Element.ALIGN_CENTER);
				// document.add(data);
			} else {
				Object[] title = (Object[]) provlist.get(0);
				font_title = new Font(font_title.getBaseFont(), 9F, 1);
				Paragraph regionName = new Paragraph("ภาค "
						+ title[4].toString(), font_title);
				regionName.setAlignment(Element.ALIGN_LEFT);
				document.add(regionName);
				Paragraph BreedName = new Paragraph("ชนิดพืช    "
						+ title[1].toString() + "  กลุ่มพันธุ์    "
						+ title[2].toString(), font_title);
				BreedName.setAlignment(Element.ALIGN_LEFT);
				BreedName.setSpacingAfter(-5f);
				document.add(BreedName);
			}
			for (int prov = 0; prov < provlist.size(); prov++) {
				Object[] obj = (Object[]) provlist.get(prov);
				Cell sCell = new Cell();
				Table detail = new Table(colWidth.length);
				detail.setWidths(colWidth);
				detail.setAlignment(Cell.ALIGN_CENTER);
				detail.setWidth(100);
				detail.setPadding(2f);

				if (!provinceTmp.equals(obj[5].toString())) {
					if (prov != 0) {
						float sumMonthsTotal = 0;
						for (int i = 0; i < sumPMonths.length; i++) {
							sumPMonths[i] += sumMonths[i];
							sumMonthsTotal += sumMonths[i];
						}
						String[] total = new String[] {
								String.format("%,.2f", sumMonths[0]),
								String.format("%,.2f", sumMonths[1]),
								String.format("%,.2f", sumMonths[2]),
								String.format("%,.2f", sumMonths[3]),
								String.format("%,.2f", sumMonths[4]),
								String.format("%,.2f", sumMonths[5]),
								String.format("%,.2f", sumMonths[6]),
								String.format("%,.2f", sumMonths[7]),
								String.format("%,.2f", sumMonths[8]),
								String.format("%,.2f", sumMonths[9]),
								String.format("%,.2f", sumMonths[10]),
								String.format("%,.2f", sumMonths[11]),
								String.format("%,.2f", sumMonthsTotal) };
						table.insertTable(totalTable(colTotalWidth, font_total,
								"รวม", total));

						sumMonths = new float[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
								0, 0 };
					}
					sCell = new Cell(new Phrase("" + No, font_body));
					sCell.setHorizontalAlignment(Cell.ALIGN_LEFT);
					sCell.setVerticalAlignment(Cell.ALIGN_TOP);
					sCell.setBorderColorTop(Color.GRAY);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
					detail.addCell(sCell);

					provinceTmp = obj[5].toString();
					No++;

					sCell = new Cell(new Phrase("จ." + obj[5].toString(),
							font_body));
					sCell.setHorizontalAlignment(Cell.ALIGN_LEFT);
					sCell.setVerticalAlignment(Cell.ALIGN_TOP);
					sCell.setBorderColorTop(Color.GRAY);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
					detail.addCell(sCell);

					sCell = new Cell(new Phrase("", font_body));
					sCell.setHorizontalAlignment(Cell.ALIGN_LEFT);
					sCell.setVerticalAlignment(Cell.ALIGN_TOP);
					sCell.setBorderColorTop(Color.GRAY);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
					detail.addCell(sCell);

					tmp = new Table(12);
					for (int i = 0; i < 12; i++) {
						sCell = new Cell(new Phrase("", font_body));
						sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
						sCell.setVerticalAlignment(Cell.ALIGN_TOP);
						sCell.setBorderColorTop(Color.GRAY);
						sCell.setBorderWidthTop(0.1f);
						sCell.setBorderWidthRight(0.1f);
						tmp.addCell(sCell);
					}
					detail.insertTable(tmp);
					detail.addCell(sCell);

					table.insertTable(detail);

					detail = new Table(colWidth.length);
					detail.setWidths(colWidth);
					detail.setAlignment(Cell.ALIGN_CENTER);
					detail.setWidth(100);
					detail.setPadding(2f);
					detail.addCell(sCell);

				} else {
					sCell = new Cell(new Phrase("", font_body));
					sCell.setHorizontalAlignment(Cell.ALIGN_LEFT);
					sCell.setVerticalAlignment(Cell.ALIGN_TOP);
					sCell.setBorderColorTop(Color.GRAY);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
					detail.addCell(sCell);
				}

				if (!DistrictTmp.equals(obj[6].toString())) {
					sCell = new Cell(new Phrase("   อ." + obj[6].toString(),
							font_body));
					sCell.setHorizontalAlignment(Cell.ALIGN_LEFT);
					sCell.setVerticalAlignment(Cell.ALIGN_TOP);
					sCell.setBorderColorTop(Color.GRAY);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
					detail.addCell(sCell);
					DistrictTmp = obj[6].toString();
				} else {
					sCell = new Cell(new Phrase("", font_body));
					sCell.setHorizontalAlignment(Cell.ALIGN_LEFT);
					sCell.setVerticalAlignment(Cell.ALIGN_TOP);
					sCell.setBorderColorTop(Color.GRAY);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
					detail.addCell(sCell);
				}

				if (!subDistrictTmp.equals(obj[7].toString())) {
					sCell = new Cell(new Phrase(obj[7].toString(), font_body));
					sCell.setHorizontalAlignment(Cell.ALIGN_LEFT);
					sCell.setVerticalAlignment(Cell.ALIGN_TOP);
					sCell.setBorderColorTop(Color.GRAY);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
					detail.addCell(sCell);
					subDistrictTmp = obj[7].toString();

					List sublist = rhome.getSubR110ByCriteria(strDate, endDate,
							eform.getRegionNo(), eform.getBreedTypeId(), eform
									.getBreedGroupId(), subDistrictTmp);
					tmp = new Table(12);
					int[] months = { 5, 6, 7, 8, 9, 10, 11, 12, 1, 2, 3, 4 };
					boolean flag = true;
					for (int j = 0; j < 12; j++) {
						for (int i = 0; i < sublist.size(); i++) {
							Object[] subObj = (Object[]) sublist.get(i);
							if (months[j] == getMonths(subObj[0].toString())) {
								sCell = new Cell(new Phrase(String.format(
										"%,.2f", Float.parseFloat(subObj[12]
												.toString())), font_body));
								sCell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
								sCell.setVerticalAlignment(Cell.ALIGN_TOP);
								sCell.setBorderColorTop(Color.GRAY);
								sCell.setBorderWidthTop(0.1f);
								sCell.setBorderWidthRight(0.1f);
								tmp.addCell(sCell);
								subTotal += Float.parseFloat(subObj[12]
										.toString());
								flag = false;
								sumMonths[j] += Float.parseFloat(subObj[12]
										.toString());
								break;
							} else {
								flag = true;
							}
						}
						if (flag) {
							sCell = new Cell(new Phrase("", font_body));
							sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
							sCell.setVerticalAlignment(Cell.ALIGN_TOP);
							sCell.setBorderColorTop(Color.GRAY);
							sCell.setBorderWidthTop(0.1f);
							sCell.setBorderWidthRight(0.1f);
							tmp.addCell(sCell);
						}
					}

					detail.insertTable(tmp);
					sCell = new Cell(new Phrase(String
							.format("%,.2f", subTotal), font_body));
					sCell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
					sCell.setVerticalAlignment(Cell.ALIGN_TOP);
					sCell.setBorderColorTop(Color.GRAY);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
					detail.addCell(sCell);
					table.insertTable(detail);
					subTotal = 0;
				}

				if (prov + 1 == provlist.size()) {
					float sumMonthsTotal = 0;
					for (int i = 0; i < sumPMonths.length; i++) {
						sumPMonths[i] += sumMonths[i];
						sumMonthsTotal += sumMonths[i];
						sumProvTotal += sumPMonths[i];
					}
					String[] total = new String[] {
							String.format("%,.2f", sumMonths[0]),
							String.format("%,.2f", sumMonths[1]),
							String.format("%,.2f", sumMonths[2]),
							String.format("%,.2f", sumMonths[3]),
							String.format("%,.2f", sumMonths[4]),
							String.format("%,.2f", sumMonths[5]),
							String.format("%,.2f", sumMonths[6]),
							String.format("%,.2f", sumMonths[7]),
							String.format("%,.2f", sumMonths[8]),
							String.format("%,.2f", sumMonths[9]),
							String.format("%,.2f", sumMonths[10]),
							String.format("%,.2f", sumMonths[11]),
							String.format("%,.2f", sumMonthsTotal) };
					table.insertTable(totalTable(colTotalWidth, font_total,
							"รวม", total));

					String[] provtotal = new String[] {
							String.format("%,.2f", sumPMonths[0]),
							String.format("%,.2f", sumPMonths[1]),
							String.format("%,.2f", sumPMonths[2]),
							String.format("%,.2f", sumPMonths[3]),
							String.format("%,.2f", sumPMonths[4]),
							String.format("%,.2f", sumPMonths[5]),
							String.format("%,.2f", sumPMonths[6]),
							String.format("%,.2f", sumPMonths[7]),
							String.format("%,.2f", sumPMonths[8]),
							String.format("%,.2f", sumPMonths[9]),
							String.format("%,.2f", sumPMonths[10]),
							String.format("%,.2f", sumPMonths[11]),
							String.format("%,.2f", sumProvTotal) };
					table.insertTable(totalTable(colTotalWidth, font_total,
							"จำนวน " + (No - 1) + " จังหวัด", provtotal));
				}
			}

			document.add(table);
			if (provlist.size() <= 0) {
				// document.add(table);
				Paragraph data = new Paragraph("ไม่มีข้อมูล", font_title);
				data.setAlignment(Element.ALIGN_CENTER);
				document.add(data);
			}
			table = new Table(1);
			table.setAlignment(Cell.ALIGN_CENTER);
			table.setWidth(100);
			table.setPadding(2f);

		} catch (Exception e) {
			document.add(new Paragraph("เกิดการดึงข้อมูลผิดพลาด", font_title));
			e.printStackTrace();
			log.error(e, e);
		} finally {
			sf.getCurrentSession().close();
		}
		return document;
	}

	private Document ReportR110_1(SessionFactory sf, Document document,
			Font font_title, Font font_body, Font font_total, ReportForm eform)
			throws DocumentException {
		try {
			sf.getCurrentSession().beginTransaction();
			Paragraph head = new Paragraph(
					"ข. รายงานการวางแผนปริมาณผลผลิตที่คาดว่าจะออกสู่ตลาด(แจ้งล่วงหน้า 1 เดือนก่อนเก็บเกี่ยว)",
					font_title);
			head.setAlignment(Element.ALIGN_CENTER);
			document.add(head);

			Paragraph head_date = new Paragraph("ตั้งแต่เดือน "
					+ eform.getForecastMonthStart() + " "
					+ eform.getForecastYearStart() + " ถึงเดือน "
					+ eform.getForecastMonthEnd() + " "
					+ eform.getForecastYearEnd(), font_title);
			head_date.setAlignment(Element.ALIGN_CENTER);
			head_date.setSpacingBefore(5f);
			document.add(head_date);

			Table table = new Table(1);
			table.setAlignment(Cell.ALIGN_CENTER);
			table.setWidth(100);
			table.setPadding(2f);
			float[] colWidth, colTotalWidth;
			colWidth = new float[] { 5, 20, 15, 120, 15 };
			colTotalWidth = new float[] { 40, 10, 10, 10, 10, 10, 10, 10, 10,
					10, 10, 10, 10, 15 };
			String[] headTable = new String[] {
					"ลำดับ",
					"จังหวัด /อำเภอ",
					"ตำบล",
					"|ปี "
							+ eform.getForecastYearStart()
							+ " ถึงปี "
							+ eform.getForecastYearEnd()
							+ "|พค.|มิย.|กค.|สค.|กย.|ตค.|พย.|ธค.|มค.|กพ.|มีย.|เมย.",
					"ยอดรวม\nจำนวนตัน" };
			table
					.insertTable(headTable(newRows(colWidth), font_body,
							headTable));
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			ReportHome rhome = new ReportHome();
			Date strDate = dateMonths(eform.getForecastMonthStart(), eform
					.getForecastYearStart(), true);
			Date endDate = dateMonths(eform.getForecastMonthEnd(), eform
					.getForecastYearEnd(), false);
			List provlist = rhome.findR110_1ByCriteria(strDate, endDate, eform
					.getRegionNo(), eform.getBreedTypeId(), eform
					.getBreedGroupId(), bCode);

			int No = 1;
			String provinceTmp = "", DistrictTmp = "", subDistrictTmp = "";
			float subTotal = 0, sumProvTotal = 0;
			Table tmp = new Table(12);
			float[] sumMonths = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
			float[] sumPMonths = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
			if (provlist.size() <= 0) {
				// document.add(table);
				// Paragraph data = new Paragraph("ไม่มีข้อมูล", font_title);
				// data.setAlignment(Element.ALIGN_CENTER);
				// document.add(data);
			} else {
				Object[] title = (Object[]) provlist.get(0);
				font_title = new Font(font_title.getBaseFont(), 9F, 1);
				Paragraph regionName = new Paragraph("ภาค "
						+ title[4].toString(), font_title);
				regionName.setAlignment(Element.ALIGN_LEFT);
				document.add(regionName);
				Paragraph BreedName = new Paragraph("ชนิดพืช    "
						+ title[1].toString() + "  กลุ่มพันธุ์    "
						+ title[2].toString(), font_title);
				BreedName.setAlignment(Element.ALIGN_LEFT);
				BreedName.setSpacingAfter(-5f);
				document.add(BreedName);
			}
			for (int prov = 0; prov < provlist.size(); prov++) {
				Object[] obj = (Object[]) provlist.get(prov);
				Cell sCell = new Cell();
				Table detail = new Table(colWidth.length);
				detail.setWidths(colWidth);
				detail.setAlignment(Cell.ALIGN_CENTER);
				detail.setWidth(100);
				detail.setPadding(2f);

				if (!provinceTmp.equals(obj[5].toString())) {
					if (prov != 0) {
						float sumSubTotal = 0;
						for (int i = 0; i < sumPMonths.length; i++) {
							sumPMonths[i] += sumMonths[i];
							sumSubTotal += sumMonths[i];
						}
						String[] total = new String[] {
								String.format("%,.2f", sumMonths[0]),
								String.format("%,.2f", sumMonths[1]),
								String.format("%,.2f", sumMonths[2]),
								String.format("%,.2f", sumMonths[3]),
								String.format("%,.2f", sumMonths[4]),
								String.format("%,.2f", sumMonths[5]),
								String.format("%,.2f", sumMonths[6]),
								String.format("%,.2f", sumMonths[7]),
								String.format("%,.2f", sumMonths[8]),
								String.format("%,.2f", sumMonths[9]),
								String.format("%,.2f", sumMonths[10]),
								String.format("%,.2f", sumMonths[11]),
								String.format("%,.2f", sumSubTotal) };
						table.insertTable(totalTable(colTotalWidth, font_total,
								"รวม", total));
						for (int i = 0; i < sumPMonths.length; i++) {
							sumPMonths[i] += sumMonths[i];
						}
						sumMonths = new float[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
								0, 0 };
					}
					sCell = new Cell(new Phrase("" + No, font_body));
					sCell.setHorizontalAlignment(Cell.ALIGN_LEFT);
					sCell.setVerticalAlignment(Cell.ALIGN_TOP);
					sCell.setBorderColorTop(Color.GRAY);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
					detail.addCell(sCell);

					provinceTmp = obj[5].toString();
					No++;

					sCell = new Cell(new Phrase("จ." + obj[5].toString(),
							font_body));
					sCell.setHorizontalAlignment(Cell.ALIGN_LEFT);
					sCell.setVerticalAlignment(Cell.ALIGN_TOP);
					sCell.setBorderColorTop(Color.GRAY);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
					detail.addCell(sCell);

					sCell = new Cell(new Phrase("", font_body));
					sCell.setHorizontalAlignment(Cell.ALIGN_LEFT);
					sCell.setVerticalAlignment(Cell.ALIGN_TOP);
					sCell.setBorderColorTop(Color.GRAY);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
					detail.addCell(sCell);

					tmp = new Table(12);
					for (int i = 0; i < 12; i++) {
						sCell = new Cell(new Phrase("", font_body));
						sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
						sCell.setVerticalAlignment(Cell.ALIGN_TOP);
						sCell.setBorderColorTop(Color.GRAY);
						sCell.setBorderWidthTop(0.1f);
						sCell.setBorderWidthRight(0.1f);
						tmp.addCell(sCell);
					}
					detail.insertTable(tmp);
					detail.addCell(sCell);

					table.insertTable(detail);

					detail = new Table(colWidth.length);
					detail.setWidths(colWidth);
					detail.setAlignment(Cell.ALIGN_CENTER);
					detail.setWidth(100);
					detail.setPadding(2f);
					detail.addCell(sCell);

				} else {
					sCell = new Cell(new Phrase("", font_body));
					sCell.setHorizontalAlignment(Cell.ALIGN_LEFT);
					sCell.setVerticalAlignment(Cell.ALIGN_TOP);
					sCell.setBorderColorTop(Color.GRAY);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
					detail.addCell(sCell);
				}

				if (!DistrictTmp.equals(obj[6].toString())) {
					sCell = new Cell(new Phrase("   อ." + obj[6].toString(),
							font_body));
					sCell.setHorizontalAlignment(Cell.ALIGN_LEFT);
					sCell.setVerticalAlignment(Cell.ALIGN_TOP);
					sCell.setBorderColorTop(Color.GRAY);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
					detail.addCell(sCell);
					DistrictTmp = obj[6].toString();
				} else {
					sCell = new Cell(new Phrase("", font_body));
					sCell.setHorizontalAlignment(Cell.ALIGN_LEFT);
					sCell.setVerticalAlignment(Cell.ALIGN_TOP);
					sCell.setBorderColorTop(Color.GRAY);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
					detail.addCell(sCell);
				}

				if (!subDistrictTmp.equals(obj[7].toString())) {
					sCell = new Cell(new Phrase(obj[7].toString(), font_body));
					sCell.setHorizontalAlignment(Cell.ALIGN_LEFT);
					sCell.setVerticalAlignment(Cell.ALIGN_TOP);
					sCell.setBorderColorTop(Color.GRAY);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
					detail.addCell(sCell);
					subDistrictTmp = obj[7].toString();

					List sublist = rhome.getSubR110_1ByCriteria(strDate,
							endDate, eform.getRegionNo(), eform
									.getBreedTypeId(), eform.getBreedGroupId(),
							subDistrictTmp);
					tmp = new Table(12);
					int[] months = { 5, 6, 7, 8, 9, 10, 11, 12, 1, 2, 3, 4 };
					boolean flag = true;
					for (int j = 0; j < 12; j++) {
						for (int i = 0; i < sublist.size(); i++) {
							Object[] subObj = (Object[]) sublist.get(i);
							if (months[j] == getMonths(subObj[0].toString())) {
								sCell = new Cell(new Phrase(String.format(
										"%,.2f", Float.parseFloat(subObj[12]
												.toString())), font_body));
								sCell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
								sCell.setVerticalAlignment(Cell.ALIGN_TOP);
								sCell.setBorderColorTop(Color.GRAY);
								sCell.setBorderWidthTop(0.1f);
								sCell.setBorderWidthRight(0.1f);
								tmp.addCell(sCell);
								subTotal += Float.parseFloat(subObj[12]
										.toString());
								flag = false;
								sumMonths[j] += Float.parseFloat(subObj[12]
										.toString());
								break;
							} else {
								flag = true;
							}
						}
						if (flag) {
							sCell = new Cell(new Phrase("", font_body));
							sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
							sCell.setVerticalAlignment(Cell.ALIGN_TOP);
							sCell.setBorderColorTop(Color.GRAY);
							sCell.setBorderWidthTop(0.1f);
							sCell.setBorderWidthRight(0.1f);
							tmp.addCell(sCell);
						}
					}

					detail.insertTable(tmp);
					sCell = new Cell(new Phrase(String
							.format("%,.2f", subTotal), font_body));
					sCell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
					sCell.setVerticalAlignment(Cell.ALIGN_TOP);
					sCell.setBorderColorTop(Color.GRAY);
					sCell.setBorderWidthTop(0.1f);
					sCell.setBorderWidthRight(0.1f);
					detail.addCell(sCell);
					table.insertTable(detail);
					subTotal = 0;
				}

				if (prov + 1 == provlist.size()) {
					float sumSubTotal = 0;
					for (int i = 0; i < sumPMonths.length; i++) {
						sumPMonths[i] += sumMonths[i];
						sumSubTotal += sumMonths[i];
						sumProvTotal += sumPMonths[i];
					}
					String[] total = new String[] {
							String.format("%,.2f", sumMonths[0]),
							String.format("%,.2f", sumMonths[1]),
							String.format("%,.2f", sumMonths[2]),
							String.format("%,.2f", sumMonths[3]),
							String.format("%,.2f", sumMonths[4]),
							String.format("%,.2f", sumMonths[5]),
							String.format("%,.2f", sumMonths[6]),
							String.format("%,.2f", sumMonths[7]),
							String.format("%,.2f", sumMonths[8]),
							String.format("%,.2f", sumMonths[9]),
							String.format("%,.2f", sumMonths[10]),
							String.format("%,.2f", sumMonths[11]),
							String.format("%,.2f", sumSubTotal) };
					table.insertTable(totalTable(colTotalWidth, font_total,
							"รวม", total));

					String[] provtotal = new String[] {
							String.format("%,.2f", sumPMonths[0]),
							String.format("%,.2f", sumPMonths[1]),
							String.format("%,.2f", sumPMonths[2]),
							String.format("%,.2f", sumPMonths[3]),
							String.format("%,.2f", sumPMonths[4]),
							String.format("%,.2f", sumPMonths[5]),
							String.format("%,.2f", sumPMonths[6]),
							String.format("%,.2f", sumPMonths[7]),
							String.format("%,.2f", sumPMonths[8]),
							String.format("%,.2f", sumPMonths[9]),
							String.format("%,.2f", sumPMonths[10]),
							String.format("%,.2f", sumPMonths[11]),
							String.format("%,.2f", sumProvTotal) };
					table.insertTable(totalTable(colTotalWidth, font_total,
							"จำนวน " + (No - 1) + " จังหวัด", provtotal));
				}
			}

			document.add(table);
			if (provlist.size() <= 0) {
				// document.add(table);
				Paragraph data = new Paragraph("ไม่มีข้อมูล", font_title);
				data.setAlignment(Element.ALIGN_CENTER);
				document.add(data);
			}
			table = new Table(1);
			table.setAlignment(Cell.ALIGN_CENTER);
			table.setWidth(100);
			table.setPadding(2f);

		} catch (Exception e) {
			document.add(new Paragraph("เกิดการดึงข้อมูลผิดพลาด", font_title));
			e.printStackTrace();
			log.error(e, e);
		} finally {
			sf.getCurrentSession().close();
		}
		return document;
	}

	private Table newRows(float[] colWidth) throws DocumentException {

		Table table = new Table(colWidth.length);
		table.setWidths(colWidth);
		table.setAlignment(Cell.ALIGN_CENTER);
		table.setWidth(100);
		table.setPadding(2f);

		return table;
	}

	private Table headTable(Table table, Font font_body, String[] headTable)
			throws DocumentException {

		for (int i = 0; i < table.getColumns(); i++) {
			float[] colWidth = null;
			if (headTable[i].indexOf(",") > 0) {
				String[] col = headTable[i].split("\\,");
				colWidth = new float[col.length - 1];
				for (int j = 0; j < col.length - 1; j++) {
					colWidth[j] = Float.parseFloat(col[j]);
				}
			}
			if (headTable[i].indexOf("|") > -1) {
				String[] inner = headTable[i].split("\\|");

				Table rowInner = new Table(1);
				Table row = new Table(inner.length - 2);
				if (colWidth != null) {
					row.setWidths(colWidth);
				}
				Cell sCell = new Cell(new Phrase(inner[1], font_body));
				sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
				sCell.setVerticalAlignment(Cell.ALIGN_TOP);
				sCell.setGrayFill(0.85f);
				sCell.setRowspan(1);
				rowInner.addCell(sCell);

				for (int j = 2; j < inner.length; j++) {
					if (inner[j].indexOf("'") > 0) {
						String[] innerCol = inner[j].split("\\'");
						Table rowInnerCol = new Table(1);
						Table rowCol = new Table(innerCol.length - 1);

						sCell = new Cell(new Phrase(innerCol[0], font_body));
						sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
						sCell.setVerticalAlignment(Cell.ALIGN_TOP);
						sCell.setGrayFill(0.85f);
						sCell.setRowspan(1);
						rowInnerCol.addCell(sCell);

						for (int k = 1; k < innerCol.length; k++) {
							sCell = new Cell(new Phrase(innerCol[k], font_body));
							sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
							sCell.setVerticalAlignment(Cell.ALIGN_TOP);
							sCell.setGrayFill(0.85f);
							sCell.setRowspan(1);
							rowCol.addCell(sCell);
						}
						rowInnerCol.insertTable(rowCol);
						row.insertTable(rowInnerCol);
					} else {
						sCell = new Cell(new Phrase(inner[j], font_body));
						sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
						sCell.setVerticalAlignment(Cell.ALIGN_TOP);
						sCell.setGrayFill(0.85f);
						sCell.setRowspan(1);
						row.addCell(sCell);
					}
				}
				rowInner.insertTable(row);
				table.insertTable(rowInner);
			} else {
				Cell sCell = new Cell(new Phrase(headTable[i], font_body));
				sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
				sCell.setVerticalAlignment(Cell.ALIGN_TOP);
				sCell.setGrayFill(0.85f);
				sCell.setRowspan(1);
				table.addCell(sCell);
			}
		}
		return table;
	}

	private Table totalTable(float[] colTotalWidth, Font font_title,
			String label, String[] total) throws DocumentException {

		Table table = new Table(colTotalWidth.length);
		table.setWidths(colTotalWidth);
		table.setAlignment(Cell.ALIGN_CENTER);
		table.setWidth(100);
		table.setPadding(2f);

		Cell sCell = new Cell(new Phrase(label, font_title));
		sCell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
		sCell.setVerticalAlignment(Cell.ALIGN_TOP);
		table.addCell(sCell);

		for (int i = 0; i < total.length; i++) {
			if (total[i].endsWith("|")) {
				sCell = new Cell(new Phrase(total[i].replace("|", ""),
						font_title));
				sCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
			} else {
				sCell = new Cell(new Phrase(total[i], font_title));
				sCell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
			}
			sCell.setVerticalAlignment(Cell.ALIGN_TOP);
			table.addCell(sCell);
		}

		return table;
	}

	public static int[] totalRai(int rai, int ngan, int wan) {
		if (wan / 100 > 0) {
			ngan += wan / 100;
			wan = wan % 100;

		}
		if (ngan / 4 > 0) {
			rai += ngan / 4;
			ngan = ngan % 4;
		}
		int[] calRai = { rai, ngan, wan };
		return calRai;
	}

	public static Paragraph numPage(int pageCount, int totalPage,
			Date timePrint, Font font_body) {
		DateFormat format = new SimpleDateFormat("dd/MM/yy เวลา HH:mm น.");
		String dateStr = format.format(timePrint);
		Paragraph numPage = new Paragraph("Page " + pageCount + "/" + totalPage
				+ "\nวัน/เวลาพิมพ์ " + dateStr, font_body);
		numPage.setAlignment(Element.ALIGN_RIGHT);
		return numPage;
	}

	public static String dateThai(String strDate, boolean notDay) {
		String Months[] = { "มกราคม", "กุมภาพันธ์", "มีนาคม", "เมษายน",
				"พฤษภาคม", "มิถุนายน", "กรกฎาคม", "สิงหาคม", "กันยายน",
				"ตุลาคม", "พฤศจิกายน", "ธันวาคม" };

		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

		int year = 0, month = 0, day = 0;
		try {
			Date date = df.parse(strDate);
			Calendar c = Calendar.getInstance();
			c.setTime(date);

			year = c.get(Calendar.YEAR);
			month = c.get(Calendar.MONTH);
			day = c.get(Calendar.DATE);

		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (notDay) {
			return String.format("%s %s", Months[month], year);
		} else {
			return String.format("%s %s %s", day, Months[month], year);
		}
	}

	public static Date dateMonths(String strMonth, String strYear, boolean start) {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		String Months[] = { "มกราคม", "กุมภาพันธ์", "มีนาคม", "เมษายน",
				"พฤษภาคม", "มิถุนายน", "กรกฎาคม", "สิงหาคม", "กันยายน",
				"ตุลาคม", "พฤศจิกายน", "ธันวาคม" };
		for (int i = 0; i < Months.length; i++) {
			if (Months[i].equals(strMonth)) {
				try {
					if (start) {
						return df.parse("01/" + (i + 1) + "/" + strYear);
					} else {
						Calendar c = Calendar.getInstance();
						c.setTime(df.parse("01/" + (i + 1) + "/" + strYear));
						c.add(Calendar.MONTH, 1);
						c.set(Calendar.DAY_OF_MONTH, 1);
						c.add(Calendar.DATE, -1);
						return c.getTime();
					}

				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
		return null;

	}

	public static int getMonths(String strDate) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		int month = 0;
		try {
			Date date = df.parse(strDate);
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			month = c.get(Calendar.MONTH);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return month + 1;
	}

	public static String convertDate(String strDate) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String cvDate = "";
		try {
			Date date = df.parse(strDate);
			DateFormat df2 = new SimpleDateFormat("dd/MM/yyyy");
			cvDate = df2.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return cvDate;
	}

	public ActionForward load(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ReportForm eform = (ReportForm) form;
		HttpSession session = request.getSession();
		SessionFactory sf = HibernateHome.getSessionFactory();
		User userLogin = (User) session.getAttribute("userLogin");
		try {
			sf.getCurrentSession().beginTransaction();

			if ("R101".equals(eform.getRep())) {
				eform
						.setMsg("R101 รายงานประมาณการผลผลิตของเกษตรกร(ตามตำบลที่เพาะปลูก)ชนิดพืช");
			} else if ("R101.1".equals(eform.getRep())) {
				eform
						.setMsg("R101.1 รายงานประมาณการผลผลิตของเกษตรกร(ตามตำบลที่เพาะปลูก)กลุ่มพันธุ์");
			} else if ("R102".equals(eform.getRep())) {
				eform
						.setMsg("R102 สรุปประมาณการผลผลิตของเกษตรกรผู้ปลูกพืชเศรษฐกิจ แยกตามรายจังหวัด");
			} else if ("R102.1".equals(eform.getRep())) {
				eform
						.setMsg("R102.1 สรุปประมาณการผลผลิตของเกษตรกรผู้ปลูกพืชเศรษฐกิจ แยกตามรายสาขา");
			} else if ("R103".equals(eform.getRep())) {
				eform
						.setMsg("R103 รายละเอียดข้อมูลเกษตรกรที่แจ้งผลผลิตที่คาดว่าจะได้ตอนเริ่มเพาะปลูก ตามช่วงเวลาที่คาดว่าจะเก็บเกี่ยว");
			} else if ("R103.1".equals(eform.getRep())) {
				eform
						.setMsg("R103.1 รายละเอียดข้อมูลเกษตรกรที่แจ้งผลผลิตที่คาดว่าจะออกสู่ตลาด(แจ้งล่วงหน้า 1 เดือนก่อนเก็บเกี่ยว)");
			} else if ("R104".equals(eform.getRep())) {
				eform
						.setMsg("R104 ข้อมูลการเปรียบเทียบประมาณการผลผลิตรวมกับผลผลิตรวมจริงที่ได้รับ ชนิดพืช");
			} else if ("R104.1".equals(eform.getRep())) {
				eform
						.setMsg("R104.1 ข้อมูลการเปรียบเทียบประมาณการผลผลิตรวมกับผลผลิตรวมจริงที่ได้รับ กลุ่มพันธุ์");
			} else if ("R104.2".equals(eform.getRep())) {
				eform
						.setMsg("R104.2 ข้อมูลการเปรียบเทียบประมาณการผลผลิตรวมกับผลผลิตรวมจริงที่ได้รับ ตามกลุ่มเกษตรกร มีเป้าหมาย");
			} else if ("R104.3".equals(eform.getRep())) {
				eform
						.setMsg("R104.3 ข้อมูลการเปรียบเทียบประมาณการผลผลิตรวมกับผลผลิตรวมจริงที่ได้รับ ตามกลุ่มเกษตรกร (รวมกลุ่มหลายอำเภอ) มีเป้าหมาย");
			} else if ("R105".equals(eform.getRep())) {
				eform
						.setMsg("R105 สรุปรวมผลผลิตจริงที่เก็บเกี่ยวได้ของเกษตรกรผู้ปลูกพืชเศรษฐกิจ  แยกตามรายจังหวัด");
			} else if ("R105.1".equals(eform.getRep())) {
				eform
						.setMsg("R105.1 สรุปรวมผลผลิตจริงที่เก็บเกี่ยวได้ของเกษตรกรผู้ปลูกพืชเศรษฐกิจ  แยกตามรายสาขา");
			} else if ("R106".equals(eform.getRep())) {
				eform
						.setMsg("R106 รายงานแสดงข้อมูลเปรียบเทียบผลผลิตจริงที่ได้รับกับต้นทุนการผลิตรวม ตามปีการผลิต");
			} else if ("R107".equals(eform.getRep())) {
				eform
						.setMsg("R107 รายงานแสดงข้อมูลเปรียบเทียบผลผลิตจริงที่ได้รับกับผลตอบแทนสุทธิ ตามปีการผลิต");
			} else if ("R108.1".equals(eform.getRep())) {
				eform
						.setMsg("R108.1 รายงานแสดงข้อมูลเปรียบเทียบผลผลิตจริงที่ได้รับกับต้นทุนการผลิตรวม ตามกลุ่มเกษตรกร มีเป้าหมาย");
			} else if ("R108.3".equals(eform.getRep())) {
				eform
						.setMsg("R108.3 รายงานแสดงข้อมูลเปรียบเทียบผลผลิตจริงที่ได้รับกับต้นทุนการผลิตรวม ตามกลุ่มเกษตรกร (รวมกลุ่มหลายอำเภอ)มีเป้าหมาย");
			} else if ("R109.1".equals(eform.getRep())) {
				eform
						.setMsg("R109.1 รายงานแสดงข้อมูลเปรียบเทียบผลผลิตจริงที่ได้รับกับผลตอบแทนสุทธิ ตามกลุ่มเกษตรกร มีเป้าหมาย");
			} else if ("R109.3".equals(eform.getRep())) {
				eform
						.setMsg("R109.3 รายงานแสดงข้อมูลเปรียบเทียบผลผลิตจริงที่ด้รับกับผลตอบแทนสุทธิ ตามกลุ่มเกษตรกร(รวมกลุ่มหลายอำเภอ)มีเป้าหมาย");
			} else if ("R110".equals(eform.getRep())) {
				eform
						.setMsg("R110 รายงานการวางแผนปริมาณผลผลิตที่คาดว่าจะได้ตอนเริ่มเพาะปลูก(ตามช่วงเวลาที่คาดว่าจะเก็บเกี่ยว)");
			} else if ("R110.1".equals(eform.getRep())) {
				eform
						.setMsg("R110.1 รายงานการวางแผนปริมาณผลผลิตที่คาดว่าจะออกสู่ตลาด(แจ้งล่วงหน้า 1 เดือนก่อนเก็บเกี่ยว)");
			}

			List branchList = (List) session.getAttribute("userBranchList");
			  hide by Jane
			if ((List) session.getAttribute("branchList") == null) {
				Branch branch = new Branch();
				branch.setBranchCode(0);
				branch.setBranchName("กรุณาเลือก");
				// branchList = branchHome.findAll();
				if (branchList != null && branchList.size() > 0)
					branchList.add(0, branch);
			}
			session.setAttribute("branchList", branchList);
			BranchHome bHome = new BranchHome();
			eform.setBranchCode(Long.parseLong(userLogin.getBranchCode()));
			eform.setProvinceNo(bHome.findByBranchCode(
					Long.parseLong(userLogin.getBranchCode())).getProvinceNo());
			RegionHome rhome = new RegionHome();
			List rList = new ArrayList();
			Region r = new Region();
			rList = rhome.findAll();
			r.setRegionNo(0);
			r.setRegionName("กรุณาเลือก");
			rList.add(0, r);
			session.setAttribute("regionList", rList);

			ProvinceHome phome = new ProvinceHome();
			List<Province> pList = new ArrayList<Province>();
			Province p = new Province();
			pList = phome.searchByBranch(branchList);
			p.setProvinceNo(0);
			p.setThaiName("กรุณาเลือก");
			pList.add(0, p);
			session.setAttribute("provinceList", pList);

			BreedTypeHome bhome = new BreedTypeHome();
			List bList = new ArrayList();
			BreedType b = new BreedType();
			bList = bhome.findAll();
			b.setBreedTypeId(0);
			b.setBreedTypeName("กรุณาเลือก");
			bList.add(0, b);
			session.setAttribute("breedTypeList", bList);

			BreedGroupHome bghome = new BreedGroupHome();
			List bgList = new ArrayList();
			BreedGroup bg = new BreedGroup();
			//bgList = bghome.findAll();
			bg.setBreedGroupId(0);
			bg.setBreedGroupName("กรุณาเลือก");
			bgList.add(0, bg);
			session.setAttribute("breedGroupList", bgList);

			List<String> Months = new ArrayList<String>();
			Months.add("มกราคม");
			Months.add("กุมภาพันธ์");
			Months.add("มีนาคม");
			Months.add("เมษายน");
			Months.add("พฤษภาคม");
			Months.add("มิถุนายน");
			Months.add("กรกฎาคม");
			Months.add("สิงหาคม");
			Months.add("กันยายน");
			Months.add("ตุลาคม");
			Months.add("พฤศจิกายน");
			Months.add("ธันวาคม");
			session.setAttribute("Months", Months);

			List<String> Years = new ArrayList<String>();
			for (int i = 0; i < 20; i++) {
				Years.add("" + (2550 + i));
			}
			session.setAttribute("Years", Years);

			PlantHome rpHome = new PlantHome();
			session.setAttribute("plantYears", rpHome.getPlantYear());
			session.setAttribute("plantNo", rpHome.getPlantNo());

			session.setAttribute("ReportForm", eform);
		} catch (Exception e) {

			e.printStackTrace();
			log.error(e, e);
		} finally {
			sf.getCurrentSession().close();
		}
		return mapping.findForward("report");
	}

	// Ajax Method
	public ActionForward getBreedGroup(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SessionFactory sf = HibernateHome.getSessionFactory();
		ReportForm eform = (ReportForm) form;
		try {
			sf.getCurrentSession().beginTransaction();
			BreedGroupHome home = new BreedGroupHome();
			List<BreedGroup> breedGroup = null;
			if (!"".equals(eform.getBreedTypeId()))
				breedGroup = home.findByBreedTypeId(eform.getBreedTypeId());
			response.setCharacterEncoding("UTF-8");
			PrintWriter wt = response.getWriter();
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig
					.setExcludes(new String[] { "breedTypeId", "period",
							"plantPeriodFrom", "plantPeriodTo",
							"forcastPeriodFrom", "forcastPeriodTo",
							"lastUpdateDate", "lastUpdateBy", "breedType",
							"checkBox", "linkImageEdit", "breedTypeName" });
			String result = JSONArray.fromObject(breedGroup, jsonConfig)
					.toString();
			result = result.trim().replaceAll(" ", " ");
			wt.print(result);
			wt.flush();
			wt.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sf.getCurrentSession().close();
		}
		return null;
	}

	// Ajax Method
	public ActionForward getBranch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SessionFactory sf = HibernateHome.getSessionFactory();
		ReportForm eform = (ReportForm) form;
		try {
			sf.getCurrentSession().beginTransaction();
			BranchHome home = new BranchHome();
			List<Branch> branch = null;
			if (!"".equals(eform.getProvinceNo()))
				branch = home.findByProvinceNo(eform.getProvinceNo());
			response.setCharacterEncoding("UTF-8");
			PrintWriter wt = response.getWriter();
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig
					.setExcludes(new String[] { "address", "checkbox",
							"childBranch", "districtName", "districtNo", "fax",
							"lastUpdateBy", "lastUpdateDate", "linkImageEdit",
							"parentBranch", "pbranchCode", "pbranchName",
							"provinceName", "regionNo", "seq",
							"subDistrictName", "subDistrictNo", "tel",
							"checkBox", "manager", "provinceNo" });
			String result = JSONArray.fromObject(branch, jsonConfig).toString();
			result = result.trim().replaceAll(" ", " ");
			wt.print(result);
			wt.flush();
			wt.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sf.getCurrentSession().close();
		}
		return null;
	}

	// Ajax Method
	public ActionForward getProvinceInfoFromBranch(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SessionFactory sf = HibernateHome.getSessionFactory();
		ReportForm eform = (ReportForm) form;
		long branchCode = eform.getBranchCode() < 0 ? 0 : eform.getBranchCode();
		try {

			sf.getCurrentSession().beginTransaction();
			ProvinceHome pHome = new ProvinceHome();
			BranchHome bHome = new BranchHome();
			Branch branch = bHome.findByBranchCode(branchCode);

			Province province = pHome
					.searchByProvinceNo(branch.getProvinceNo());
			response.setCharacterEncoding("UTF-8");
			PrintWriter wt = response.getWriter();
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.setExcludes(new String[] { "engName", "lastUpdateDate",
					"lastUpdateBy", "regionNo", "district", "region",
					"checkBox", "linkImageDel", "linkImageEdit", "regionName",
					"thaiName" });

			String result = JSONArray.fromObject(province, jsonConfig)
					.toString();
			result = result.trim().replaceAll("&nbsp;", " ");
			wt.print(result);
			wt.flush();
			wt.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sf.getCurrentSession().close();
		}
		return null;
	}

	private List<Branch> getPBranch(List<Branch> branchTmp, long branchCode) {
		List branchList = new ArrayList();
		List pbranch = new ArrayList();
		try {
			BranchHome branchHome = new BranchHome();
			for (int i = 0; i < branchTmp.size(); i++) {
				Branch branch = new Branch();
				branch = (Branch) branchTmp.get(i);
				if (branchCode == branch.getBranchCode()) {
					continue;
				}

				pbranch = branchHome.findByPBranch(branch.getBranchCode());
				if (pbranch.size() > 0) {
					branchList.add(branch);
					branchList.addAll(getPBranch(pbranch, branch
							.getBranchCode()));
				} else {
					branchList.add(branch);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return branchList;
	}*/
}
