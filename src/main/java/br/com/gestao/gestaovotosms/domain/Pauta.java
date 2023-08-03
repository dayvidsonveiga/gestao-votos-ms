package br.com.gestao.gestaovotosms.domain;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pautas")
public class Pauta {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id_pauta", columnDefinition = "VARCHAR(36)")
    @Type(type = "uuid-char")
    private UUID id;

    @NotNull
    @Column(name = "titulo")
    private String titulo;

    @Column(name = "data_abertura")
    private LocalDateTime dataAbertura;

    @Column(name = "data_fechamento")
    private LocalDateTime dataFechamento;

    @Column(name = "qtde_votos_sim")
    private Long qtdeVotosSim;

    @Column(name = "qtde_votos_nao")
    private Long qtdeVotosNao;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pauta")
    private Set<Associado> associados;

}
