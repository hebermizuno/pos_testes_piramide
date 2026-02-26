package br.com.pupposoft.poc.piramidetestes.motorista.gateway.database.jpa.repository;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.testcontainers.junit.jupiter.Testcontainers;

import br.com.pupposoft.poc.piramidetestes.motorista.gateway.database.jpa.entity.MotoristaEntity;
import br.com.pupposoft.poc.piramidetestes.motorista.util.container.AbstractContainer;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UsuarioRepositoryIntTest extends AbstractContainer {

	@Autowired
	private MotoristaRepository usuarioRepository;

	@Test
	void deveObterPorCpf() {

		var cpf = "any-cpf";

		MotoristaEntity usuarioAlvo = new MotoristaEntity(null, cpf, "Any Name", LocalDate.now(), Arrays.asList());
		usuarioRepository.save(usuarioAlvo);

		MotoristaEntity usuarioOutro = new MotoristaEntity(null, "outro cpf", "Any Name", LocalDate.now(), Arrays.asList());
		usuarioRepository.save(usuarioOutro);

		Optional<MotoristaEntity> userEntityOp = usuarioRepository.findByCpf(cpf);
		assertTrue(userEntityOp.isPresent());

		MotoristaEntity usuarioAlvoEncontrado = userEntityOp.get();
		assertEquals(usuarioAlvo.getId(), usuarioAlvoEncontrado.getId());
	}

	@Test
	void nãoDeveObterPorCpf() {
		var cpf = "any-cpf";

		MotoristaEntity usuario = new MotoristaEntity(null, cpf, "Any Name", LocalDate.now(), Arrays.asList());
		usuarioRepository.save(usuario);

		Optional<MotoristaEntity> userEntityOp = usuarioRepository.findByCpf(cpf + "outro");
		assertTrue(userEntityOp.isEmpty());
	}
}
