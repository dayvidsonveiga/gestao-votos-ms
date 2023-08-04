package br.com.gestao.gestaovotosms.service;

import br.com.gestao.gestaovotosms.domain.Associado;
import br.com.gestao.gestaovotosms.exception.CadastroDuplicadoException;
import br.com.gestao.gestaovotosms.exception.CadastroNaoEncontradoException;
import br.com.gestao.gestaovotosms.repository.AssociadoRepository;
import br.com.gestao.gestaovotosms.util.MockAssociadoBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

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

    private MockAssociadoBuilder mockAssociadoBuilder;

    @BeforeEach
    void setUp() {

        mockAssociadoBuilder = new MockAssociadoBuilder();

    }


    @Test
    void criarComSucesso() {

        var entradaCadastro = mockAssociadoBuilder.mockDtoCriarAssociado("59042667079", "Dayvidson Veiga");

        when(associadoRepository.findByCpf(anyString())).thenReturn(Optional.empty());

        var response = associadoService.criar(entradaCadastro);

        verify(associadoRepository, times(1)).save(any(Associado.class));
        assertEquals(response.getCpf(), entradaCadastro.getCpf());
        assertEquals(response.getNome(), entradaCadastro.getNome());

    }

    @Test
    void criarAssociadoComCadastroDuplicadoException() {

        var entradaCadastro = mockAssociadoBuilder.mockDtoCriarAssociado("12345678901", "Dayvidson Veiga");
        var associadoExistente = mockAssociadoBuilder.mockAssociado(entradaCadastro.getCpf(), "Dayvidson Veiga");

        when(associadoRepository.findByCpf(entradaCadastro.getCpf())).thenReturn(Optional.of(associadoExistente));

        assertThrows(CadastroDuplicadoException.class, () -> associadoService.criar(entradaCadastro));

    }

    @Test
    void encontrarAssociadoPorCpfComCpfEncontrado() {

        var cpf = "12345678901";

        var associado = mockAssociadoBuilder.mockAssociado("12345678901", "Dayvidson Veiga");

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

}