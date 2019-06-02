package xyz.shakirzyanov.warehouseapp.xlsx;

import org.apache.poi.ss.SpreadsheetVersion;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.AreaReference;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.*;
import xyz.shakirzyanov.warehouseapp.model.Client;
import xyz.shakirzyanov.warehouseapp.model.Goods;
import xyz.shakirzyanov.warehouseapp.model.Warehouse;
import xyz.shakirzyanov.warehouseapp.model.enums.DocumentType;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

public class ExcelFiller {
    private XSSFSheet sheet;
    private XSSFWorkbook wb;

    public ExcelFiller(InputStream xlsTemplate) throws IOException {
        wb = new XSSFWorkbook(xlsTemplate);
        sheet = wb.getSheetAt(0);
    }

    public void fillDocumentType(DocumentType documentType) {
        var cell = getCellByName("documentType");
        cell.setCellValue(documentType.getTitle());
    }

    public void fillCreatedAt(Date createdAt) {
        var cell = getCellByName("documentDateTime");
        cell.setCellValue(createdAt);
    }

    public void fillWarehouse(Warehouse warehouse) {
        var warehouseNameCell = getCellByName("warehouseName");
        warehouseNameCell.setCellValue(warehouse.getName());
        var warehouseAddressCell = getCellByName("warehouseAddress");
        warehouseAddressCell.setCellValue(warehouse.getAddress());
    }

    public void fillClient(Client client) {
        var cell = getCellByName("client");
        cell.setCellValue(client.getName());
    }

    public void fillGoodsAndCounts(List<Goods> goods, List<Double> counts) {
        var startCell = getCellByName("goodsStartCell");
        var rowNumStart = startCell.getRow().getRowNum();
        var colNum = startCell.getColumnIndex();
        for (int i = 0; i < goods.size(); i++) {
            var row = sheet.getRow(rowNumStart + i);
            if(row == null) {
                row = sheet.createRow(rowNumStart + i);
            }
            var nameCell = row.getCell(colNum, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            nameCell.setCellValue(goods.get(i).getName());
            var barcodeCell = row.getCell(colNum + 1, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            barcodeCell.setCellValue(goods.get(i).getBarcode());
            var countCell = row.getCell(colNum + 2, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            countCell.setCellValue(counts.get(i));
            var unitCell = row.getCell(colNum + 3, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            unitCell.setCellValue(goods.get(i).getUnit().getTitle());
        }
    }

    public void writeToStream(OutputStream os) throws IOException {
        wb.write(os);
    }

    private XSSFCell getCellByName(String cellName) {
        var name = wb.getName(cellName);
        var areaReference = new AreaReference(name.getRefersToFormula(), SpreadsheetVersion.EXCEL2007);
        var cellRef = areaReference.getFirstCell();
        return sheet.getRow(cellRef.getRow()).getCell(cellRef.getCol());
    }
}
