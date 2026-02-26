package br.com.pupposoft.poc.piramidetestes.motorista.gateway.database.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import br.com.pupposoft.poc.piramidetestes.motorista.domain.Motorista;
import br.com.pupposoft.poc.piramidetestes.motorista.exception.ErroAoAcessarRepositorioException;
import br.com.pupposoft.poc.piramidetestes.motorista.gateway.MotoristaGateway;
import br.com.pupposoft.poc.piramidetestes.motorista.gateway.database.jpa.entity.AutomovelEntity;
import br.com.pupposoft.poc.piramidetestes.motorista.gateway.database.jpa.entity.MotoristaEntity;
import br.com.pupposoft.poc.piramidetestes.motorista.gateway.database.jpa.repository.AutomovelRepository;
import br.com.pupposoft.poc.piramidetestes.motorista.gateway.database.jpa.repository.MotoristaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class MotoristaJpaGateway implements MotoristaGateway {

	private final AutomovelRepository automovelRepository;
	private final MotoristaRepository usuarioRepository;

	@Override
	@Transactional
	public Long criar(Motorista motorista) {
		try {

			MotoristaEntity usuarioEntity = mapToEntity(motorista);

			Long id = usuarioRepository.save(usuarioEntity).getId();

			automovelRepository.saveAll(usuarioEntity.getAutomoveis());

			return id;

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new ErroAoAcessarRepositorioException();
		}
	}

	@Override
	public Optional<Motorista> obterPorCpf(String cpf) {
		try {

			Optional<MotoristaEntity> usuarioEntityOp = usuarioRepository.findByCpf(cpf);

			if(usuarioEntityOp.isEmpty()) {
				log.info("Usuário não encontrado: cpf={}", cpf);
				return Optional.empty();
			}

			MotoristaEntity usuarioEntity = usuarioEntityOp.get();

			Motorista usuario = mapToDomain(usuarioEntity);

			return Optional.of(usuario);


		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new ErroAoAcessarRepositorioException();
		}

	}

	private Motorista mapToDomain(MotoristaEntity motoristaEntity) {
		return new Motorista(
						motoristaEntity.getId(),
						motoristaEntity.getNome(),
						motoristaEntity.getCpf(),
						motoristaEntity.getDataNascimento(),
						null,
						null);
	}

	private MotoristaEntity mapToEntity(Motorista motorista) {

		MotoristaEntity usuarioEntity = MotoristaEntity.builder()
				.id(motorista.getId())
				.cpf(motorista.getCpf())
				.nome(motorista.getNome())
				.dataNascimento(motorista.getDataNascimento())
				.build();

		List<AutomovelEntity> automoveis = motorista.getAutomoveis().stream().map(ad -> new AutomovelEntity(
					ad.getId(),
					ad.getModelo(),
					ad.getDataModelo(),
					usuarioEntity)
				).toList();

		usuarioEntity.setAutomoveis(automoveis);

		return usuarioEntity;
	}


}
