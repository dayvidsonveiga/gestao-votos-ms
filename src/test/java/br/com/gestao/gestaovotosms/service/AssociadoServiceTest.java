package br.com.gestao.gestaovotosms.service;

import br.com.gestao.gestaovotosms.domain.Associado;
import br.com.gestao.gestaovotosms.dto.entrada.DtoCriarAssociado;
import br.com.gestao.gestaovotosms.exception.CadastroDuplicadoException;
import br.com.gestao.gestaovotosms.exception.CadastroNaoEncontradoException;
import br.com.gestao.gestaovotosms.repository.AssociadoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class AssociadoServiceTest {

    @InjectMocks
    private AssociadoService associadoService;

    @Mock
    private AssociadoRepository associadoRepository;


    @Test
    void criarComSucesso() {

        var entradaCadastro = mockDtoCriarAssociado("59042667079", "Dayvidson Veiga");

        when(associadoRepository.findByCpf(anyString())).thenReturn(Optional.empty());

        var response = associadoService.criar(entradaCadastro);

        verify(associadoRepository, times(1)).save(any(Associado.class));
        assertEquals(response.getCpf(), entradaCadastro.getCpf());
        assertEquals(response.getNome(), entradaCadastro.getNome());

    }

    @Test
    void criarAssociadoComCadastroDuplicadoException() {

        var entradaCadastro = mockDtoCriarAssociado("12345678901", "Dayvidson Veiga");
        var associadoExistente = mockAssociado(entradaCadastro.getCpf(), "Dayvidson Veiga");

        when(associadoRepository.findByCpf(entradaCadastro.getCpf())).thenReturn(Optional.of(associadoExistente));

        assertThrows(CadastroDuplicadoException.class, () -> associadoService.criar(entradaCadastro));

    }

    @Test
    void encontrarAssociadoPorCpfComCpfEncontrado() {

        var cpf = "12345678901";

        var associado = mockAssociado("12345678901", "Dayvidson Veiga");

        when(associadoRepository.findByCpf(cpf)).thenReturn(Optional.of(associado));

        var response = associadoService.encontrarAssociadoPorCpf(cpf);

        verify(associadoRepository, times(1)).findByCpf(cpf);
        assertEquals(associado, response);
        assertEquals(response.getCpf(), associado.getCpf());
        assertEquals(response.getNome(), associado.getNome());

    }

    @Test
    void encontrarAssociadoPorCpfComCpfNaoEncontrado() {

        var cpf = "12345678901";

        when(associadoRepository.findByCpf(cpf)).thenReturn(Optional.empty());

        assertThrows(CadastroNaoEncontradoException.class, () -> associadoService.encontrarAssociadoPorCpf(cpf));

    }

    private DtoCriarAssociado mockDtoCriarAssociado(String cpf, String nome) {

        var dtoCriarAssociado = new DtoCriarAssociado();
        dtoCriarAssociado.setCpf(cpf);
        dtoCriarAssociado.setNome(nome);

        return dtoCriarAssociado;

    }

    private Associado mockAssociado(String cpf, String nome) {

        return Associado.builder()
                .id(UUID.randomUUID())
                .cpf(cpf)
                .nome(nome)
                .build();

    }

}