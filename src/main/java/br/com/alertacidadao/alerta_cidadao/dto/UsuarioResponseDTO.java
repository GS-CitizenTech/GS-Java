
package br.com.alertacidadao.alerta_cidadao.dto;

public class UsuarioResponseDTO {

    private Integer idUsuario;
    private String nome;
    private String email;
    private String papel;
    private String ativo;

    public UsuarioResponseDTO() {
    }

    public UsuarioResponseDTO(
            Integer idUsuario,
            String nome,
            String email,
            String papel,
            String ativo
    ) {
        this.idUsuario = idUsuario;
        this.nome = nome;
        this.email = email;
        this.papel = papel;
        this.ativo = ativo;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPapel() {
        return papel;
    }

    public void setPapel(String papel) {
        this.papel = papel;
    }

    public String getAtivo() {
        return ativo;
    }

    public void setAtivo(String ativo) {
        this.ativo = ativo;
    }
}

