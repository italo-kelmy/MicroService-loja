package com.hgithub.com.italo_kelmy.loja_service.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.Objects;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "Usuário é Obrigatório")
    private String usuario;
    @NotNull(message = "Senha é Obrigatório")
    @Size(min = 8, message = "A senha precisa ter no mínimo oito caracteres e não pode começar nem terminar com um espaço")
    private String senha;
    @NotNull(message = "Email é Obrigatório")
    @Email(message = "Formato válido: exemploemail@hotmail.com")
    private String email;
    @NotNull(message = "Cep é Obrigatório")
    @Pattern(regexp = "^\\d{5}-\\d{3}$", message = "Formato válido: xxxxx-xxx")
    private String cep;


    public Usuario(){
    }

    public Usuario(Long id, String usuario, String senha, String email, String cep) {
        this.id = id;
        this.usuario = usuario;
        this.senha = senha;
        this.email = email;
        this.cep = cep;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario1 = (Usuario) o;
        return Objects.equals(getId(), usuario1.getId()) && Objects.equals(getUsuario(), usuario1.getUsuario()) && Objects.equals(getSenha(), usuario1.getSenha()) && Objects.equals(getEmail(), usuario1.getEmail()) && Objects.equals(getCep(), usuario1.getCep());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUsuario(), getSenha(), getEmail(), getCep());
    }
}
