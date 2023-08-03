package br.com.gestao.gestaovotosms.repository;

import br.com.gestao.gestaovotosms.domain.Associado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AssociadoRepository extends JpaRepository<Associado, UUID> {

    Optional<Associado> findByCpf(String cpf);

}
