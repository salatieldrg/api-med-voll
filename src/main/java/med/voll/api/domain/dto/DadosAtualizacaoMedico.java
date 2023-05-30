package med.voll.api.domain.dto;

import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoMedico(
 @NotNull
 Long id,
 String nome,
 String telefone,
Endereco endereco) {
}
