package com.github.williamjbf.gestaocontasdomesticas.infraestrutura.cors;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Setter
@Component
public class CORSSettingsBeans {

    @Value("${cors.filter.enable}")
    private String corsFilterEnable;

    public boolean isCORSFilterEnable() {
        return Boolean.parseBoolean(this.corsFilterEnable);
    }

}
