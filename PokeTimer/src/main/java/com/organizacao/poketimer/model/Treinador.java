/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.organizacao.poketimer.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author Dilson Lopes
 */
@Entity
@Table(name = "treinador")
public class Treinador implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    private Long id;
    
    private String nome;
    private String cpf;
    private String email;
    private String senha;
    private List<Grupo> grupos = new ArrayList<>();
    private List<Timer> times = new ArrayList<>();

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    @NotBlank // Não pode estar em branco
    @Size(max = 100) //Tamanho máximo de 50 caracteres
    @Column(nullable = false, length = 100)
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
   
    @NotNull //Não pode ser nulo
    @Column(name = "cpf", nullable = false, length = 14)
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @Email
    @NotBlank
    @Size(max = 60)
    @Column(nullable = false, length = 255)
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
      
   /* @OneToMany(mappedBy = "treinador")
    public List<Usuario> getUsuarios() {
        return usuarios;
    }
    
    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }*/


   @OneToMany(mappedBy = "treinador", cascade = CascadeType.ALL, orphanRemoval= true)// relacionamento um para muitos(chave extrangeira fica na tabela "Endereco")
    public List<Timer> getTimes() {
        return times;
    }

    public void setTimes(List<Timer> times) {
        this.times = times;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
     //@NotNull
    //@ManyToMany(cascade = CascadeType.ALL) para não deletar os gurpos da tabela grupo
    @ManyToMany
    @JoinTable(name = "treinador_grupo", joinColumns =
            @JoinColumn(name = "treinador_id"),
            inverseJoinColumns =
            @JoinColumn(name = "grupo_id"))
    public List<Grupo> getGrupos() {
        return grupos;
    }

    public void setGrupos(List<Grupo> grupos) {
        this.grupos = grupos;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 43 * hash + Objects.hashCode(this.id);
        return hash;
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
        final Treinador other = (Treinador) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    

}
