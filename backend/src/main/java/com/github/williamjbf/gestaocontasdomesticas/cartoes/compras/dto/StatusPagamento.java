package com.github.williamjbf.gestaocontasdomesticas.cartoes.compras.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StatusPagamento {

    @NotNull(message = "o status do Pagamento é obrigatório")
    private StatusPagamentoEnum status;

    public boolean isPago() {
        if (status == null)
            throw new RuntimeException("Status do Pagamento não pode ser null");

        return this.status.isPago();
    }

    private enum StatusPagamentoEnum {
        PAGO, NAO_PAGO;

        public boolean isPago() {
            return this.equals(StatusPagamentoEnum.PAGO);
        }
    }

}
