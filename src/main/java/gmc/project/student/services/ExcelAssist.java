package gmc.project.student.services;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import gmc.project.student.models.StudentDto;
import gmc.project.student.models.StudentEntity;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExcelAssist {
	
	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	private List<StudentDto> listUsers;
	
	public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	static String[] HEADERs = { "Name", "Email", "PhoneNumber" };

	public ExcelAssist(List<StudentDto> listUsers) {
      this.listUsers = listUsers;
      workbook = new XSSFWorkbook();
	}

	public static boolean hasExcelFormat(MultipartFile file) {

		if (!TYPE.equals(file.getContentType())) {
			return false;
		}

		return true;
	}

	public static List<StudentEntity> excelToTutorials(InputStream is) {
		try {
			Workbook workbook = new XSSFWorkbook(is);

			Sheet sheet = workbook.getSheet("Sheet1");

			log.error("Count: " + workbook.getNumberOfSheets());
			log.error("Sheet Name: " + sheet.getSheetName());

			Iterator<Row> rows = sheet.iterator();

			List<StudentEntity> students = new ArrayList<>();

			int rowNumber = 0;
			while (rows.hasNext()) {
				Row currentRow = rows.next();

				// skip header
				if (rowNumber == 0) {
					rowNumber++;
					continue;
				}

				Iterator<Cell> cellsInRow = currentRow.iterator();

				StudentEntity student = new StudentEntity();

				int cellIdx = 0;
				while (cellsInRow.hasNext()) {
					Cell currentCell = cellsInRow.next();

					student.setStudentId(UUID.randomUUID().toString());

					switch (cellIdx) {
					case 0:
						student.setName(currentCell.getStringCellValue());
						break;

					case 1:
						student.setEmail(currentCell.getStringCellValue());
						break;

					case 2:
						student.setPhoneNumber("" + (currentCell.getNumericCellValue()));
						break;

					default:
						break;
					}

					cellIdx++;
				}

				students.add(student);
			}

			workbook.close();

			return students;
		} catch (IOException e) {
			throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
		}
	}

	private void writeHeaderLine() {
		sheet = workbook.createSheet("Students");

		Row row = sheet.createRow(0);

		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setBold(true);
		font.setFontHeight(16);
		style.setFont(font);

		createCell(row, 0, "User ID", style);
		createCell(row, 1, "E-mail", style);
		createCell(row, 2, "Full Name", style);
		createCell(row, 3, "Phone Number", style);

	}

	private void createCell(Row row, int columnCount, Object value, CellStyle style) {
		sheet.autoSizeColumn(columnCount);
		Cell cell = row.createCell(columnCount);
		if (value instanceof Integer) {
			cell.setCellValue((Integer) value);
		} else if (value instanceof Boolean) {
			cell.setCellValue((Boolean) value);
		} else {
			cell.setCellValue((String) value);
		}
		cell.setCellStyle(style);
	}

	private void writeDataLines() {
		int rowCount = 1;

		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setFontHeight(14);
		style.setFont(font);

		for (StudentDto student : listUsers) {
			Row row = sheet.createRow(rowCount++);
			int columnCount = 0;

			createCell(row, columnCount++, student.getStudentId(), style);
			createCell(row, columnCount++, student.getEmail(), style);
			createCell(row, columnCount++, student.getName(), style);
			createCell(row, columnCount++, student.getPhoneNumber().toString(), style);

		}
	}

	public void export(HttpServletResponse response) throws IOException {
		writeHeaderLine();
		writeDataLines();

		ServletOutputStream outputStream = response.getOutputStream();
		workbook.write(outputStream);
		workbook.close();

		outputStream.close();

	}

}