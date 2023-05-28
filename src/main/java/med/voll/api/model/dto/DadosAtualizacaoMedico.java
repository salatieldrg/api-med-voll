package med.voll.api.model.dto;

import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoMedico(
 @NotNull
 Long id,
 String nome,
 String telefone,
Endereco endereco) {
}
