
package br.com.alertacidadao.alerta_cidadao.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "ALERTA")
public class Alerta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ALERTA")
    private Integer idAlerta;

    @OneToOne
    @JoinColumn(name = "ID_LEITURA", nullable = false, unique = true)
    private LeituraEnchente leitura;

    @Column(name = "CLASSIFICACAO", nullable = false, length = 20)
    private String classificacao;

    @Column(name = "PROBABILIDADE", precision = 5, scale = 2)
    private BigDecimal probabilidade;

    @Column(name = "MENSAGEM_EXPLICATIVA", length = 500)
    private String mensagemExplicativa;

    @Column(name = "STATUS", nullable = false, length = 20)
    private String status;

    @Column(name = "DATA_GERACAO", nullable = false)
    private LocalDateTime dataGeracao;

    @Column(name = "DATA_RESOLUCAO")
    private LocalDateTime dataResolucao;

    public Alerta() {
    }

    public Integer getIdAlerta() {
        return idAlerta;
    }

    public void setIdAlerta(Integer idAlerta) {
        this.idAlerta = idAlerta;
    }

    public LeituraEnchente getLeitura() {
        return leitura;
    }

    public void setLeitura(LeituraEnchente leitura) {
        this.leitura = leitura;
    }

    public String getClassificacao() {
        return classificacao;
    }

    public void setClassificacao(String classificacao) {
        this.classificacao = classificacao;
    }

    public BigDecimal getProbabilidade() {
        return probabilidade;
    }

    public void setProbabilidade(BigDecimal probabilidade) {
        this.probabilidade = probabilidade;
    }

    public String getMensagemExplicativa() {
        return mensagemExplicativa;
    }

    public void setMensagemExplicativa(String mensagemExplicativa) {
        this.mensagemExplicativa = mensagemExplicativa;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getDataGeracao() {
        return dataGeracao;
    }

    public void setDataGeracao(LocalDateTime dataGeracao) {
        this.dataGeracao = dataGeracao;
    }

    public LocalDateTime getDataResolucao() {
        return dataResolucao;
    }

    public void setDataResolucao(LocalDateTime dataResolucao) {
        this.dataResolucao = dataResolucao;
    }
}


