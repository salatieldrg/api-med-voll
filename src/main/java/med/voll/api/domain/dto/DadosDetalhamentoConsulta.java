package med.voll.api.domain.dto;

import med.voll.api.domain.Medico;
import med.voll.api.domain.Paciente;
import org.springframework.security.core.parameters.P;

import java.time.LocalDateTime;

public record DadosDetalhamentoConsulta(Long id, Medico medico, Paciente paciente, LocalDateTime data) {
}
