package com.organizacao.poketimer.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

//@Entity
//@Table(name = "usuario")
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private String nome;
    private String email;
    private String senha;
    private List<Grupo> grupos = new ArrayList<>();
    private Treinador treinador;

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotBlank
    @Size(max = 60)
    @Column(nullable = false, length = 80)
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Email
    @NotBlank
    @Size(max = 60)
    @Column(nullable = false, unique = true, length = 255)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @NotBlank
    @Column(nullable = false, length = 20)
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    //@NotNull
    //@ManyToMany(cascade = CascadeType.ALL) para não deletar os gurpos da tabela grupo
    @ManyToMany
    @JoinTable(name = "usuario_grupo", joinColumns =
            @JoinColumn(name = "usuario_id"),
            inverseJoinColumns =
            @JoinColumn(name = "grupo_id"))
    public List<Grupo> getGrupos() {
        return grupos;
    }

    /*Eu acredito que seu código era pra ter funcionado. De qualquer forma, tente adicionar o seguinte na classe Usuario:
     @PreRemove
     private void removerGrupos() {
     getGrupos().clear();
     }
     Esse método será chamado antes da remoção do usuário ser executada, dando a chance de limpar os grupos da lista.*/
    public void setGrupos(List<Grupo> grupos) {
        this.grupos = grupos;
    }
    
     @ManyToOne
    @JoinColumn(name = "treinador_id")
    public Treinador getTreinador() {
        return treinador;
    }

    public void setFuncionario(Treinador treinador) {
        this.treinador = treinador;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Usuario other = (Usuario) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }
}