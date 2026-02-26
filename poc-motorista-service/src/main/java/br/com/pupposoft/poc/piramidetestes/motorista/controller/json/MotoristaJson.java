package br.com.pupposoft.poc.piramidetestes.motorista.controller.json;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.pupposoft.poc.piramidetestes.motorista.domain.Motorista;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MotoristaJson {
	private Long id;

	@NotBlank
	private String nome;

	@NotBlank
	private String cpf;

	@NotNull
	private LocalDate dataNascimento;

	@NotNull
	@NotEmpty
	private List<AutomovelJson> veiculos;

	public Motorista mapToDomain() {
		return new Motorista(
				id,
				nome,
				cpf,
				dataNascimento,
				veiculos == null ? Arrays.asList() : veiculos.stream().map(AutomovelJson::mapToDomain).toList(),
				new ArrayList<>());
	}
}
