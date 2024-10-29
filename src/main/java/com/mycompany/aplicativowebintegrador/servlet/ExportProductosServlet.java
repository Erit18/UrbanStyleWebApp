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
import com.mycompany.aplicativowebintegrador.dao.ProveedorDAO;
import com.mycompany.aplicativowebintegrador.modelo.Producto;
import com.mycompany.aplicativowebintegrador.modelo.Proveedor;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet({"/api/productos/export/excel", "/api/productos/export/pdf"})
public class ExportProductosServlet extends HttpServlet {
    
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private final ProveedorDAO proveedorDAO = new ProveedorDAO();
    private Map<Integer, String> proveedoresCache;
    
    private void cargarProveedores() throws SQLException {
        proveedoresCache = new HashMap<>();
        List<Proveedor> proveedores = proveedorDAO.obtenerTodos();
        for (Proveedor proveedor : proveedores) {
            proveedoresCache.put(proveedor.getId_proveedor(), proveedor.getNombre());
        }
    }
    
    private String obtenerNombreProveedor(int idProveedor) {
        return proveedoresCache.getOrDefault(idProveedor, "No especificado");
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            // Cargar todos los proveedores al inicio
            cargarProveedores();
            
            ProductoDAO productoDAO = new ProductoDAO();
            List<Producto> productos = productoDAO.obtenerTodos();
            
            if (productos.isEmpty()) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "No hay productos para exportar");
                return;
            }

            String uri = request.getRequestURI();
            if (uri.endsWith("/excel")) {
                response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                response.setHeader("Content-Disposition", "attachment; filename=\"productos.xlsx\"");
                exportToExcel(response, productos);
            } else if (uri.endsWith("/pdf")) {
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "attachment; filename=\"productos.pdf\"");
                exportToPDF(response, productos);
            }
        } catch (SQLException e) {
            throw new ServletException("Error al obtener datos", e);
        }
    }
    
    private void exportToExcel(HttpServletResponse response, List<Producto> productos) throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Productos");
            
            // Crear encabezados
            Row headerRow = sheet.createRow(0);
            String[] columns = {"ID", "Nombre", "Categoría", "Precio", "Stock", "Proveedor", "Fecha Cad.", "Descuento"};
            
            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            
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
                row.createCell(3).setCellValue(String.format("S/ %.2f", producto.getPrecio()));
                row.createCell(4).setCellValue(producto.getStock());
                row.createCell(5).setCellValue(obtenerNombreProveedor(producto.getId_proveedor()));
                row.createCell(6).setCellValue(producto.getFecha_caducidad() != null ? 
                    dateFormat.format(producto.getFecha_caducidad()) : "");
                row.createCell(7).setCellValue(String.format("%.2f%%", producto.getDescuento()));
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
            Document document = new Document(PageSize.A4.rotate());
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
            title.setSpacingAfter(20);
            document.add(title);
            
            // Crear tabla
            PdfPTable table = new PdfPTable(8);
            table.setWidthPercentage(100);
            
            // Establecer anchos relativos de las columnas
            float[] columnWidths = {0.5f, 2f, 1f, 1f, 0.7f, 1.5f, 1f, 1f};
            table.setWidths(columnWidths);
            
            // Estilo para encabezados
            com.itextpdf.text.Font headerFont = new com.itextpdf.text.Font(
                com.itextpdf.text.Font.FontFamily.HELVETICA, 
                10, 
                com.itextpdf.text.Font.BOLD
            );
            
            // Añadir encabezados
            String[] headers = {"ID", "Nombre", "Categoría", "Precio", "Stock", "Proveedor", "Fecha Cad.", "Descuento"};
            for (String header : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(header, headerFont));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                cell.setPadding(5);
                table.addCell(cell);
            }
            
            // Estilo para datos
            com.itextpdf.text.Font dataFont = new com.itextpdf.text.Font(
                com.itextpdf.text.Font.FontFamily.HELVETICA, 
                9, 
                com.itextpdf.text.Font.NORMAL
            );
            
            // Añadir datos
            for (Producto producto : productos) {
                // Crear y configurar cada celda
                PdfPCell[] cells = {
                    new PdfPCell(new Phrase(String.valueOf(producto.getId_ropa()), dataFont)),
                    new PdfPCell(new Phrase(producto.getNombre(), dataFont)),
                    new PdfPCell(new Phrase(producto.getCategoria(), dataFont)),
                    new PdfPCell(new Phrase(String.format("S/ %.2f", producto.getPrecio()), dataFont)),
                    new PdfPCell(new Phrase(String.valueOf(producto.getStock()), dataFont)),
                    new PdfPCell(new Phrase(obtenerNombreProveedor(producto.getId_proveedor()), dataFont)),
                    new PdfPCell(new Phrase(producto.getFecha_caducidad() != null ? 
                        dateFormat.format(producto.getFecha_caducidad()) : "", dataFont)),
                    new PdfPCell(new Phrase(String.format("%.2f%%", producto.getDescuento()), dataFont))
                };
                
                // Configurar alineación y padding para cada celda
                for (PdfPCell cell : cells) {
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setPadding(5);
                    table.addCell(cell);
                }
            }
            
            document.add(table);
            document.close();
            
        } catch (DocumentException e) {
            throw new IOException("Error al generar PDF", e);
        }
    }
} 