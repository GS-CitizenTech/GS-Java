
package br.com.alertacidadao.alerta_cidadao.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "LEITURA_ENCHENTE")
public class LeituraEnchente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_LEITURA")
    private Integer idLeitura;

    @ManyToOne
    @JoinColumn(name = "ID_PONTO", nullable = false)
    private PontoMonitoramento pontoMonitoramento;

    @Column(name = "NIVEL_AGUA_CM", precision = 8, scale = 2)
    private BigDecimal nivelAguaCm;

    @Column(name = "CHUVA_MM", precision = 8, scale = 2)
    private BigDecimal chuvaMm;

    @Column(name = "CHUVA_3H_MM", precision = 8, scale = 2)
    private BigDecimal chuva3hMm;

    @Column(name = "CHUVA_6H_MM", precision = 8, scale = 2)
    private BigDecimal chuva6hMm;

    @Column(name = "CHUVA_24H_MM", precision = 8, scale = 2)
    private BigDecimal chuva24hMm;

    @Column(name = "ELEVACAO_CM_H", precision = 8, scale = 2)
    private BigDecimal elevacaoCmH;

    @Column(name = "DATA_HORA_LEITURA", nullable = false)
    private LocalDateTime dataHoraLeitura;

    @Column(name = "ORIGEM", nullable = false, length = 50)
    private String origem;

    @Column(name = "DATA_RECEBIMENTO", nullable = false)
    private LocalDateTime dataRecebimento;

    public LeituraEnchente() {
    }

    public Integer getIdLeitura() {
        return idLeitura;
    }

    public void setIdLeitura(Integer idLeitura) {
        this.idLeitura = idLeitura;
    }

    public PontoMonitoramento getPontoMonitoramento() {
        return pontoMonitoramento;
    }

    public void setPontoMonitoramento(PontoMonitoramento pontoMonitoramento) {
        this.pontoMonitoramento = pontoMonitoramento;
    }

    public BigDecimal getNivelAguaCm() {
        return nivelAguaCm;
    }

    public void setNivelAguaCm(BigDecimal nivelAguaCm) {
        this.nivelAguaCm = nivelAguaCm;
    }

    public BigDecimal getChuvaMm() {
        return chuvaMm;
    }

    public void setChuvaMm(BigDecimal chuvaMm) {
        this.chuvaMm = chuvaMm;
    }

    public BigDecimal getChuva3hMm() {
        return chuva3hMm;
    }

    public void setChuva3hMm(BigDecimal chuva3hMm) {
        this.chuva3hMm = chuva3hMm;
    }

    public BigDecimal getChuva6hMm() {
        return chuva6hMm;
    }

    public void setChuva6hMm(BigDecimal chuva6hMm) {
        this.chuva6hMm = chuva6hMm;
    }

    public BigDecimal getChuva24hMm() {
        return chuva24hMm;
    }

    public void setChuva24hMm(BigDecimal chuva24hMm) {
        this.chuva24hMm = chuva24hMm;
    }

    public BigDecimal getElevacaoCmH() {
        return elevacaoCmH;
    }

    public void setElevacaoCmH(BigDecimal elevacaoCmH) {
        this.elevacaoCmH = elevacaoCmH;
    }

    public LocalDateTime getDataHoraLeitura() {
        return dataHoraLeitura;
    }

    public void setDataHoraLeitura(LocalDateTime dataHoraLeitura) {
        this.dataHoraLeitura = dataHoraLeitura;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public LocalDateTime getDataRecebimento() {
        return dataRecebimento;
    }

    public void setDataRecebimento(LocalDateTime dataRecebimento) {
        this.dataRecebimento = dataRecebimento;
    }
}
