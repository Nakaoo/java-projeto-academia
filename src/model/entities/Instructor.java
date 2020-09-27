package model.entities;

import java.io.Serializable;
import java.util.Date;

public class Instructor implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String nome;
	private String endereco;
	private String bairro;
	private String estado;
	private String telefone;
	private String cpf;
	private String email;
	private Double salario;
	private Date dataNascimento;
	private Plans planNome;
	private String planVin;

	public Instructor() {

	}

	public Instructor(Integer id, String nome, String endereco, String bairro, String estado, String telefone,
			String cpf, String email, Double salario, Date dataNascimento, Plans planNome, String planVin) {
		super();
		this.id = id;
		this.nome = nome;
		this.endereco = endereco;
		this.bairro = bairro;
		this.estado = estado;
		this.telefone = telefone;
		this.cpf = cpf;
		this.email = email;
		this.salario = salario;
		this.dataNascimento = dataNascimento;
		this.planNome = planNome;
		this.planVin = planVin;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Double getSalario() {
		return salario;
	}

	public void setSalario(Double salario) {
		this.salario = salario;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}


	public Plans getPlanNome() {
		return planNome;
	}

	public void setPlanNome(Plans planNome) {
		this.planNome = planNome;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Instructor other = (Instructor) obj;
		if (cpf == null) {
			if (other.cpf != null)
				return false;
		} else if (!cpf.equals(other.cpf))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getPlanVin() {
		return planVin;
	}

	public void setPlanVin(String planVin) {
		this.planVin = planVin;
	}
}
