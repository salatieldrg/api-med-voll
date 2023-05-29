package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.model.Medico;
import med.voll.api.model.dto.DadosAtualizacaoMedico;
import med.voll.api.model.dto.DadosCadastroMedico;
import med.voll.api.model.dto.DadosDetalhamentoMedico;
import med.voll.api.model.dto.DadosListagemMedico;
import med.voll.api.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository repository;

    @Transactional
    @PostMapping
    public void cadastrar(@RequestBody @Valid DadosCadastroMedico dados){
        var medico = repository.save(new Medico(dados));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemMedico>> listar(@PageableDefault(size=10, page=0, sort = {"nome"}, direction = Sort.Direction.DESC) Pageable paginacao){
        //http://localhost:8080/medicos?size=1&page=2&sort=nome,desc parâmetros passados na url sobrescrevem o padrão
//        return repository.findAll(paginacao).map(DadosListagemMedico::new);
        var page = repository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new);
        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody DadosAtualizacaoMedico dados){
        var medico = repository.getReferenceById(dados.id());
        medico.atualizaInformacoes(dados);

        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deletar(@PathVariable Long id){
//        repository.deleteById(id);
        var medico = repository.getReferenceById(id);
        medico.excluir(id);

        return ResponseEntity.noContent().build();
    }
}
