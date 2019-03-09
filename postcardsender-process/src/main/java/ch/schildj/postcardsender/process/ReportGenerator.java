package ch.schildj.postcardsender.process;

import ch.schildj.postcardsender.domain.model.Postcard;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.time.format.DateTimeFormatter;
import java.util.List;

/*
 * Generates Report
 */

class ReportGenerator {

    private static final int MDATE_CELL_INDEX = 0;
    private static final int STATE_CELL_INDEX = 1;
    private static final int TRANSMISSIONSTATE_CELL_INDEX = 2;
    private static final int KEY_CELL_INDEX = 3;
    private static final int TEXT_CELL_INDEX = 4;
    private static final int SENDER_FIRSTNAME_CELL_INDEX = 5;
    private static final int SENDER_LASTNAME_CELL_INDEX = 6;
    private static final int SENDER_STREET_CELL_INDEX = 7;
    private static final int SENDER_HOUSENR_CELL_INDEX = 8;
    private static final int SENDER_ZIP_CELL_INDEX = 9;
    private static final int SENDER_CITY_CELL_INDEX = 10;
    private static final int RECIPIENT_FIRSTNAME_CELL_INDEX = 11;
    private static final int RECIPIENT_LASTNAME_CELL_INDEX = 12;
    private static final int RECIPIENT_STREET_CELL_INDEX = 13;
    private static final int RECIPIENT_HOUSENR_CELL_INDEX = 14;
    private static final int RECIPIENT_ZIP_CELL_INDEX = 15;
    private static final int RECIPIENT_CITY_CELL_INDEX = 16;

    private final List<Postcard> postcardList;

    private int rowIndex = 0;

    private XSSFSheet sheet;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    /*
     * Constructor
     * @param list    - list with postcards
     */
    public ReportGenerator(List<Postcard> list) {
        this.postcardList = list;
    }

    /* generates a new workbook */
    public Workbook generateWorkbook() {

        // create workbook
        XSSFWorkbook workbook = new XSSFWorkbook();
        // replace ":" in sheet name because it is an invalid character but could occurs if the translation-file is not up to date
        sheet = workbook.createSheet("Postcards");
        Row r = sheet.createRow(rowIndex);

        // header row
        r.createCell(MDATE_CELL_INDEX).setCellValue("Datum");
        r.createCell(STATE_CELL_INDEX).setCellValue("Status");
        r.createCell(TRANSMISSIONSTATE_CELL_INDEX).setCellValue("Übermittlung");
        r.createCell(KEY_CELL_INDEX).setCellValue("Postkarten-Key");
        r.createCell(TEXT_CELL_INDEX).setCellValue("Text");
        r.createCell(SENDER_FIRSTNAME_CELL_INDEX).setCellValue("Absender Vorname");
        r.createCell(SENDER_LASTNAME_CELL_INDEX).setCellValue("Absender Nachname");
        r.createCell(SENDER_STREET_CELL_INDEX).setCellValue("Absender Strasse");
        r.createCell(SENDER_HOUSENR_CELL_INDEX).setCellValue("Absender Hausnr.");
        r.createCell(SENDER_ZIP_CELL_INDEX).setCellValue("Absender PLZ");
        r.createCell(SENDER_CITY_CELL_INDEX).setCellValue("Absender Ort");
        r.createCell(RECIPIENT_FIRSTNAME_CELL_INDEX).setCellValue("Empfänger Vorname");
        r.createCell(RECIPIENT_LASTNAME_CELL_INDEX).setCellValue("Empfänger Nachname");
        r.createCell(RECIPIENT_STREET_CELL_INDEX).setCellValue("Empfänger Strasse");
        r.createCell(RECIPIENT_HOUSENR_CELL_INDEX).setCellValue("Empfänger Hausnr.");
        r.createCell(RECIPIENT_ZIP_CELL_INDEX).setCellValue("Empfänger PLZ");
        r.createCell(RECIPIENT_CITY_CELL_INDEX).setCellValue("Empfänger Ort");
        rowIndex++;

        // data
        for (Postcard report : postcardList) {
            r = sheet.createRow(rowIndex);
            r.createCell(MDATE_CELL_INDEX).setCellValue(report.getLastModification().format(DATE_FORMATTER));
            r.createCell(STATE_CELL_INDEX).setCellValue(report.getStatus().name());
            r.createCell(TRANSMISSIONSTATE_CELL_INDEX).setCellValue(report.getTransmissionState().name());
            r.createCell(KEY_CELL_INDEX).setCellValue(report.getKey());
            r.createCell(TEXT_CELL_INDEX).setCellValue(report.getText());
            r.createCell(SENDER_FIRSTNAME_CELL_INDEX).setCellValue(report.getSenderFirstname());
            r.createCell(SENDER_LASTNAME_CELL_INDEX).setCellValue(report.getSenderLastname());
            r.createCell(SENDER_STREET_CELL_INDEX).setCellValue(report.getSenderStreet());
            r.createCell(SENDER_HOUSENR_CELL_INDEX).setCellValue(report.getSenderHousenr());
            r.createCell(SENDER_ZIP_CELL_INDEX).setCellValue(report.getSenderZip());
            r.createCell(SENDER_CITY_CELL_INDEX).setCellValue(report.getSenderCity());
            r.createCell(RECIPIENT_FIRSTNAME_CELL_INDEX).setCellValue(report.getRecipientFirstname());
            r.createCell(RECIPIENT_LASTNAME_CELL_INDEX).setCellValue(report.getRecipientLastname());
            r.createCell(RECIPIENT_STREET_CELL_INDEX).setCellValue(report.getRecipientStreet());
            r.createCell(RECIPIENT_HOUSENR_CELL_INDEX).setCellValue(report.getRecipientHousenr());
            r.createCell(RECIPIENT_ZIP_CELL_INDEX).setCellValue(report.getRecipientZip());
            r.createCell(RECIPIENT_CITY_CELL_INDEX).setCellValue(report.getRecipientCity());
            rowIndex++;
        }

        // resize columns
        resizeColumns();

        return workbook;
    }

    /* resize colums */
    private void resizeColumns() {
        for (int i = 0; i < 17; i++) {
            sheet.autoSizeColumn(i);
        }
    }


}
