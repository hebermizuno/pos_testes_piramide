package br.com.pupposoft.poc.piramidetestes.motorista.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.pupposoft.poc.piramidetestes.motorista.controller.json.MotoristaJson;
import br.com.pupposoft.poc.piramidetestes.motorista.usecase.CriarMotoristaUsecase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("motoristas")
@RequiredArgsConstructor
public class MotoristaController {

	private final CriarMotoristaUsecase criarUsuarioUsecase;

	@PostMapping
	public Long criar(@Valid @RequestBody MotoristaJson motoristaJson) {
		return criarUsuarioUsecase.criar(motoristaJson.mapToDomain());
	}
}
