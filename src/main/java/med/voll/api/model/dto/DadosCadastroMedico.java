package med.voll.api.model.dto;

import med.voll.api.enums.Especialidade;

public record DadosCadastroMedico(String nome, String email, String crm, Especialidade especialidade, Endereco endereco) {
}
