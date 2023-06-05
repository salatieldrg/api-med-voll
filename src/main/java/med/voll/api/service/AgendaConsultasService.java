package med.voll.api.service;

import med.voll.api.domain.Consulta;
import med.voll.api.domain.dto.DadosAgendamentoConsulta;
import med.voll.api.repository.ConsultaRepository;
import med.voll.api.repository.MedicoRepository;
import med.voll.api.repository.PacienteRepository;
import org.springframework.stereotype.Service;

@Service
public class AgendaConsultasService {

    private ConsultaRepository consultaRepository;
    private MedicoRepository medicoRepository;

    private PacienteRepository pacienteRepository;

    public void agendar(DadosAgendamentoConsulta dados){

        var medico = medicoRepository.findById(dados.idMedico()).get();
        var paciente = pacienteRepository.findById(dados.idPaciente()).get();

        var consulta = new Consulta(null, medico, paciente, dados.data());
        consultaRepository.save(consulta);

    }

}
