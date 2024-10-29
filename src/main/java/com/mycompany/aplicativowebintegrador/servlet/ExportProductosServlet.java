package com.mycompany.aplicativowebintegrador.servlet;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.BaseColor;
import com.mycompany.aplicativowebintegrador.dao.ProductoDAO;
import com.mycompany.aplicativowebintegrador.modelo.Producto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

@WebServlet({"/api/productos/export/excel", "/api/productos/export/pdf"})
public class ExportProductosServlet extends HttpServlet {
    
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        System.out.println("ExportProductosServlet: Inicio de la petición");
        String uri = request.getRequestURI();
        System.out.println("URI solicitada: " + uri);
        
        try {
            ProductoDAO productoDAO = new ProductoDAO();
            List<Producto> productos = productoDAO.obtenerTodos();
            
            if (productos.isEmpty()) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "No hay productos para exportar");
                return;
            }

            if (uri.endsWith("/excel")) {
                System.out.println("Iniciando exportación a Excel");
                response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                response.setHeader("Content-Disposition", "attachment; filename=\"productos.xlsx\"");
                exportToExcel(response, productos);
            } else if (uri.endsWith("/pdf")) {
                System.out.println("Iniciando exportación a PDF");
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "attachment; filename=\"productos.pdf\"");
                exportToPDF(response, productos);
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Formato no soportado");
            }
        } catch (Exception e) {
            System.err.println("Error en ExportProductosServlet: " + e.getMessage());
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al exportar: " + e.getMessage());
        }
    }
    
    private void exportToExcel(HttpServletResponse response, List<Producto> productos) throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Productos");
            
            // Crear estilo para encabezados
            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setAlignment(HorizontalAlignment.CENTER);
            
            // Crear encabezados
            Row headerRow = sheet.createRow(0);
            String[] columns = {"ID", "Nombre", "Categoría", "Precio", "Stock", "Proveedor", "Fecha Cad.", "Descuento"};
            
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
                cell.setCellStyle(headerStyle);
            }
            
            // Llenar datos
            int rowNum = 1;
            for (Producto producto : productos) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(producto.getId_ropa());
                row.createCell(1).setCellValue(producto.getNombre());
                row.createCell(2).setCellValue(producto.getCategoria());
                row.createCell(3).setCellValue(producto.getPrecio().doubleValue());
                row.createCell(4).setCellValue(producto.getStock());
                row.createCell(5).setCellValue(producto.getId_proveedor());
                row.createCell(6).setCellValue(producto.getFecha_caducidad() != null ? 
                    dateFormat.format(producto.getFecha_caducidad()) : "");
                row.createCell(7).setCellValue(producto.getDescuento().doubleValue());
            }
            
            // Autoajustar columnas
            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }
            
            workbook.write(response.getOutputStream());
        }
    }
    
    private void exportToPDF(HttpServletResponse response, List<Producto> productos) throws IOException, SQLException {
        System.out.println("Configurando headers para PDF");
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=productos.pdf");
        
        try {
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, response.getOutputStream());
            
            document.open();
            
            // Añadir título
            com.itextpdf.text.Font titleFont = new com.itextpdf.text.Font(
                com.itextpdf.text.Font.FontFamily.HELVETICA, 
                18, 
                com.itextpdf.text.Font.BOLD
            );
            Paragraph title = new Paragraph("Reporte de Productos", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph("\n"));
            
            // Crear tabla
            PdfPTable table = new PdfPTable(8); // 8 columnas
            table.setWidthPercentage(100);
            
            // Añadir encabezados
            String[] headers = {"ID", "Nombre", "Categoría", "Precio", "Stock", "Proveedor", "Fecha Cad.", "Descuento"};
            for (String header : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(header));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                table.addCell(cell);
            }
            
            // Añadir datos
            for (Producto producto : productos) {
                table.addCell(String.valueOf(producto.getId_ropa()));
                table.addCell(producto.getNombre());
                table.addCell(producto.getCategoria());
                table.addCell(String.format("S/ %.2f", producto.getPrecio()));
                table.addCell(String.valueOf(producto.getStock()));
                table.addCell(String.valueOf(producto.getId_proveedor()));
                table.addCell(producto.getFecha_caducidad() != null ? 
                            dateFormat.format(producto.getFecha_caducidad()) : "");
                table.addCell(String.format("%.2f%%", producto.getDescuento()));
            }
            
            document.add(table);
            document.close();
            
        } catch (DocumentException e) {
            throw new IOException("Error al generar PDF", e);
        }
    }
} 