package med.voll.api.service;

import med.voll.api.domain.Consulta;
import med.voll.api.domain.Medico;
import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.dto.DadosAgendamentoConsulta;
import med.voll.api.domain.dto.DadosCancelamentoConsulta;
import med.voll.api.repository.ConsultaRepository;
import med.voll.api.repository.MedicoRepository;
import med.voll.api.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AgendaConsultasService {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    public void agendar(DadosAgendamentoConsulta dados){

        if(!pacienteRepository.existsById(dados.idPaciente())){
            throw new ValidacaoException("Não existe nenhum paciente com o ID informado");
        }

        if(dados.idMedico() != null && !medicoRepository.existsById(dados.idMedico())){
            throw new ValidacaoException("Não existe nenhum médico com o ID informado");
        }

        var medico = escolherMedico(dados);
        var paciente = pacienteRepository.getReferenceById(dados.idPaciente());

        var consulta = new Consulta(null, medico, paciente, dados.data(), null, null);
        consultaRepository.save(consulta);

    }

    private Medico escolherMedico(DadosAgendamentoConsulta dados) {
        if(dados.idMedico() != null){
            return medicoRepository.getReferenceById(dados.idMedico());
        }

        if(dados.especialidade() == null){
            throw new ValidacaoException("Especialidade é obrigatória quando não for informado médico.");
        }

        return medicoRepository.escolherMedicoAleatorioLivreNaData(dados.especialidade(), dados.data());
    }

    public Consulta cancelar(DadosCancelamentoConsulta dados) {
        if(!consultaRepository.existsById(dados.idConsulta())){
            throw new ValidacaoException("Não existe nenhuma consulta com o ID informado.");
        }

        Consulta consulta = consultaRepository.getReferenceById(dados.idConsulta());
        LocalDateTime dataCancelamento = LocalDateTime.now();

        if(dataCancelamento.plusDays(1).isAfter(consulta.getData())){
            throw new ValidacaoException("A consulta só pode ser cancelada com no mínimo 24h de antecedência.");
        }

        consulta.setCancelada(true);
        consulta.setMotivoCancelamento(dados.motivo());
        return  consulta;
    }
}
