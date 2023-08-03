package br.com.gestao.gestaovotosms.repository;

import br.com.gestao.gestaovotosms.domain.Pauta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PautaRepository extends JpaRepository<Pauta, UUID> {

    Optional<Pauta> findByTituloIgnoreCase(String titulo);

}
