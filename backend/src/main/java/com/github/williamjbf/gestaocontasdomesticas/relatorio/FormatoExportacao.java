package com.github.williamjbf.gestaocontasdomesticas.relatorio;

public enum FormatoExportacao {

    XLSX("xlsx", "application/vnd.ms-excel; charset=utf-8"),
    PDF("pdf", "application/pdf; charset=utf-8");

    private String extension;
    private String contentType;

    FormatoExportacao(String extension, String contentType) {
        this.extension = extension;
        this.contentType = contentType;
    }

    public String getExtension() {
        return extension;
    }

    public String getContentType() {
        return contentType;
    }

}