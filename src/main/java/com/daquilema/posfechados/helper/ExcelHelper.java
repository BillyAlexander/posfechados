package com.daquilema.posfechados.helper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

public class ExcelHelper {
	public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	static String[] HEADERs = { "Id", "Title", "Description", "Published" };
	static String SHEET = "data";

	public static boolean hasExcelFormat(MultipartFile file) {

		if (!TYPE.equals(file.getContentType())) {
			return false;
		}

		return true;
	}

	public static List<FilePostdated> excelToTutorials(InputStream is) {
		try {
			Workbook workbook = new XSSFWorkbook(is);

			Sheet sheet = workbook.getSheet(SHEET);
			if(sheet != null) {
				Iterator<Row> rows = sheet.iterator();

				List<FilePostdated> data = new ArrayList<FilePostdated>();

				int rowNumber = 0;
				while (rows.hasNext()) {
					Row currentRow = rows.next();

					// skip header
					if (rowNumber == 0) {
						rowNumber++;
						continue;
					}

					Iterator<Cell> cellsInRow = currentRow.iterator();

					FilePostdated postdatedInfo = new FilePostdated();

					int cellIdx = 0;
					while (cellsInRow.hasNext()) {
						Cell currentCell = cellsInRow.next();

						switch (cellIdx) {
						case 0:
							postdatedInfo.setCliente(currentCell.getStringCellValue());
							break;

						case 1:
							postdatedInfo.setSeg_credito(currentCell.getStringCellValue());
							break;

						case 2:
							postdatedInfo.setBanco(currentCell.getStringCellValue());
							break;

						case 3:
							postdatedInfo.setCuenta(currentCell.getNumericCellValue());
							break;

						case 4:
							postdatedInfo.setNo_cheque(currentCell.getStringCellValue());
							break;
						case 5:
							postdatedInfo.setMonto(currentCell.getNumericCellValue());
							break;
						case 6:
							postdatedInfo.setMoneda(currentCell.getStringCellValue());
							break;
						case 7:
							postdatedInfo.setFecha_cobro(currentCell.getDateCellValue());
							break;
						case 8:
							postdatedInfo.setFecha_vence(currentCell.getDateCellValue());
							break;
						default:
							break;
						}

						cellIdx++;
					}

					data.add(postdatedInfo);
				}

				workbook.close();

				return data;
			}
			else {
				workbook.close();
				throw new RuntimeException("fail to parse sheet file: " + SHEET);
			}
			
			
		} catch (IOException e) {
			throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
		}
	}
}
