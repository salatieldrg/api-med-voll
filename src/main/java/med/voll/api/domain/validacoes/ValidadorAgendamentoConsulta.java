package med.voll.api.domain.validacoes;

import med.voll.api.domain.dto.DadosAgendamentoConsulta;

public interface ValidadorAgendamentoConsulta {
    void validar(DadosAgendamentoConsulta dados);
}
