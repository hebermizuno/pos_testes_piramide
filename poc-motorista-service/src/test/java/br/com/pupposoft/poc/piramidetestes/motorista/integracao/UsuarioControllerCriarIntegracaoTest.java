package br.com.pupposoft.poc.piramidetestes.motorista.integracao;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import br.com.pupposoft.poc.piramidetestes.motorista.controller.MotoristaController;
import br.com.pupposoft.poc.piramidetestes.motorista.controller.json.AutomovelJson;
import br.com.pupposoft.poc.piramidetestes.motorista.controller.json.MotoristaJson;
import br.com.pupposoft.poc.piramidetestes.motorista.gateway.database.jpa.repository.MotoristaRepository;
import jakarta.transaction.Transactional;

@ActiveProfiles("integracao-test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UsuarioControllerCriarIntegracaoTest {

	@Autowired
	private MotoristaController usuarioController;

	@Autowired
	private MotoristaRepository motoristaRepository;

	@Test
	@Transactional
	void deveCriarMotoristaComSucesso() {
		final var cpf = "anyCpf";
		final var dataNascimento = LocalDate.now().minusYears(18);
		final var dataLancamentoCarro = LocalDate.now().minusYears(3);

		AutomovelJson automovel = new AutomovelJson(null, "any modelo", dataLancamentoCarro);
		MotoristaJson motoristaJson = new MotoristaJson(null, "any name", cpf, dataNascimento, Arrays.asList(automovel));

		assertEquals(0, motoristaRepository.count());

		Long userId = usuarioController.criar(motoristaJson);

		assertEquals(1, motoristaRepository.count());

		var motoristaEntity = motoristaRepository.findAll().get(0);

		assertEquals(userId, motoristaEntity.getId());
		assertEquals(motoristaJson.getNome(), motoristaEntity.getNome());
		assertEquals(motoristaJson.getCpf(), motoristaEntity.getCpf());
		assertEquals(motoristaJson.getDataNascimento(), motoristaEntity.getDataNascimento());

		assertEquals(1, motoristaEntity.getAutomoveis().size());

		var automovelEntity = motoristaEntity.getAutomoveis().get(0);

		assertEquals(automovel.getModelo(), automovelEntity.getModelo());
		assertEquals(automovel.getDataModelo(), automovelEntity.getDataModelo());
		assertNotNull(automovelEntity.getId());
	}
}
