package com.github.williamjbf.gestaocontasdomesticas.relatorio.service;

import com.github.williamjbf.gestaocontasdomesticas.contas.pagar.service.ContasAPagarService;
import com.github.williamjbf.gestaocontasdomesticas.contas.pagar.service.dto.GastosMensais;
import com.github.williamjbf.gestaocontasdomesticas.security.usuario.service.UsuarioService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class RelatorioDespesasService {

    private final UsuarioService usuarioService;
    private final ContasAPagarService contasAPagarService;

    @Autowired
    public RelatorioDespesasService(final UsuarioService usuarioService,
                                    final ContasAPagarService contasAPagarService) {
        this.usuarioService = usuarioService;
        this.contasAPagarService = contasAPagarService;
    }

    public ByteArrayOutputStream gerarRelatorio() throws IOException {
        final List<GastosMensais> gastosMensais = contasAPagarService.recuperarTotalGastoPorMes(usuarioService.getUsuarioLogado().getId());

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Despesas Mensais");

            Row headerRow = sheet.createRow(0);
            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFillForegroundColor(IndexedColors.AQUA.getIndex());
            headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            Cell headerCell = headerRow.createCell(0);
            headerCell.setCellValue("Mes/Ano");
            headerCell.setCellStyle(headerCellStyle);

            headerCell = headerRow.createCell(1);
            headerCell.setCellValue("Categoria");
            headerCell.setCellStyle(headerCellStyle);

            headerCell = headerRow.createCell(2);
            headerCell.setCellValue("Despesas");
            headerCell.setCellStyle(headerCellStyle);

            int rowIdx = 1;
            for (GastosMensais gasto : gastosMensais) {
                final Row row = sheet.createRow(rowIdx++);

                row.createCell(0).setCellValue(gasto.getMesAno());
                row.createCell(1).setCellValue(gasto.getCategoria());
                row.createCell(2).setCellValue(gasto.getValor().doubleValue());
            }

            workbook.write(out);

            return out;
        }
    }
}