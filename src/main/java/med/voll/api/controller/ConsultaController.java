package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import med.voll.api.domain.dto.DadosAgendamentoConsulta;
import med.voll.api.domain.dto.DadosCancelamentoConsulta;
import med.voll.api.domain.dto.DadosDetalhamentoConsulta;
import med.voll.api.service.AgendaConsultasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("consultas")
@SecurityRequirement(name = "bearer-key")
public class ConsultaController {

    @Autowired
    private AgendaConsultasService agendaConsultasService;

    @PostMapping
    @Transactional
    public ResponseEntity agendar(@RequestBody @Valid DadosAgendamentoConsulta dados){

        var detalheConsulta = agendaConsultasService.agendar(dados);
        return ResponseEntity.ok(detalheConsulta);
    }

    @PutMapping
    @Transactional
    public ResponseEntity cancelar(@RequestBody @Valid DadosCancelamentoConsulta dados){
        var consultaCancelada = agendaConsultasService.cancelar(dados);
        return ResponseEntity.ok(new DadosDetalhamentoConsulta(consultaCancelada));
    }
}
