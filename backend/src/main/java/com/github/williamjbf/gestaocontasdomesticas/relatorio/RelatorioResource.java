package com.github.williamjbf.gestaocontasdomesticas.relatorio;


import com.github.williamjbf.gestaocontasdomesticas.relatorio.service.RelatorioDespesasService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Controlador REST para operações relacionadas a relatórios.
 */
@RestController
@RequestMapping("/api/relatorios")
public class RelatorioResource {

    private final RelatorioDespesasService relatorioDespesasService;

    @Autowired
    public RelatorioResource(final RelatorioDespesasService relatorioDespesasService) {
        this.relatorioDespesasService = relatorioDespesasService;
    }

    @GetMapping("/despesas-mensais")
    public void gerarRelatorioDespesasMensais(
            @RequestParam("formato") final FormatoExportacao formatoExportacao,
            final HttpServletResponse httpServletResponse) throws IOException {
        if (FormatoExportacao.PDF.equals(formatoExportacao)) {
            throw new UnsupportedOperationException("Ainda não implementado");
        }

        final ByteArrayOutputStream relatorio = relatorioDespesasService.gerarRelatorio();

        final String contentDisposition = String.format(
                "attachment;filename=relatorio-despesas-mensais.%s",
                formatoExportacao.getExtension()
        );
        httpServletResponse.setContentType(formatoExportacao.getContentType());
        httpServletResponse.addHeader("Content-Disposition", contentDisposition);

        httpServletResponse.getOutputStream().write(relatorio.toByteArray());
        httpServletResponse.flushBuffer();
        httpServletResponse.setStatus(200);
    }

}
