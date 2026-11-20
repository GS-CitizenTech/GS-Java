
package br.com.alertacidadao.alerta_cidadao.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PontoMonitoramentoResponseDTO {

    private Integer idPonto;
    private String codigo;
    private String nome;
    private String bairro;
    private String cidade;
    private String uf;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String ativo;
    private LocalDateTime dataCadastro;

    public PontoMonitoramentoResponseDTO() {
    }

    public PontoMonitoramentoResponseDTO(
            Integer idPonto,
            String codigo,
            String nome,
            String bairro,
            String cidade,
            String uf,
            BigDecimal latitude,
            BigDecimal longitude,
            String ativo,
            LocalDateTime dataCadastro
    ) {
        this.idPonto = idPonto;
        this.codigo = codigo;
        this.nome = nome;
        this.bairro = bairro;
        this.cidade = cidade;
        this.uf = uf;
        this.latitude = latitude;
        this.longitude = longitude;
        this.ativo = ativo;
        this.dataCadastro = dataCadastro;
    }

    public Integer getIdPonto() {
        return idPonto;
    }

    public void setIdPonto(Integer idPonto) {
        this.idPonto = idPonto;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public String getAtivo() {
        return ativo;
    }

    public void setAtivo(String ativo) {
        this.ativo = ativo;
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
    }
}

