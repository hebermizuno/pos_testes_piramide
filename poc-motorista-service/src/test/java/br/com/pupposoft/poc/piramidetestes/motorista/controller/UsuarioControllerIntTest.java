package br.com.pupposoft.poc.piramidetestes.motorista.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.pupposoft.poc.piramidetestes.motorista.config.ControllerExceptionHandlerConfiguration;
import br.com.pupposoft.poc.piramidetestes.motorista.domain.Motorista;
import br.com.pupposoft.poc.piramidetestes.motorista.exception.MotoristaComAutomovelAntigoException;
import br.com.pupposoft.poc.piramidetestes.motorista.usecase.CriarMotoristaUsecase;

@ActiveProfiles("controller-test")
@WebMvcTest(MotoristaController.class)
@Import(ControllerExceptionHandlerConfiguration.class)
class UsuarioControllerIntTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CriarMotoristaUsecase criarUsuarioUsecase;

	@Test
	void deveSerSucessoAoCriar() throws Exception {

		doReturn(1L).when(criarUsuarioUsecase).criar(any(Motorista.class));

		this.mockMvc.perform(
				post("/motoristas")
					.content("""
						{
						    "nome": "any name",
						    "cpf": "11111111",
						    "dataNascimento": "1980-03-15",
						    "veiculos": [
						        {
						            "modelo": "Ford KA",
						            "dataModelo": "2023-01-01"
						        }
						    ]
						}""")
					.contentType(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(content().string("1"));
	}

	@Test
	void deveSerErroAoCriar() throws Exception {

		doThrow(new MotoristaComAutomovelAntigoException()).when(criarUsuarioUsecase).criar(any(Motorista.class));

		this.mockMvc.perform(
				post("/motoristas")
					.content("""
						{
						    "nome": "any name",
						    "cpf": "11111111",
						    "dataNascimento": "1980-03-15",
						    "veiculos": [
						        {
						            "modelo": "Ford KA",
						            "dataModelo": "2023-01-01"
						        }
						    ]
						}""")
					.contentType(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isUnprocessableEntity())
		.andExpect(content().string("{\"code\":\"usuario.comAutomovelAntigo\",\"message\":\"UsuÃ¡rio com automovel antigo.\"}"));
	}

	@Test
	void deveSerBadRequestSemNomeAoCriar() throws Exception {

		this.mockMvc.perform(
				post("/motoristas")
					.content("""
						{
						    "cpf": "11111111",
						    "dataNascimento": "1980-03-15",
						    "veiculos": [
						        {
						            "modelo": "Ford KA",
						            "dataModelo": "2023-01-01"
						        }
						    ]
						}""")
					.contentType(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isBadRequest())
		.andExpect(content().string("{\"code\":\"argumentNotValid\",\"message\":\"nome:must not be blank;\"}"));
	}

	//TODO: adicionar demais validações de campos
	//TODO: adicionar testes de falha ao acessar database e/ou falha ao acessar api de incidentes
}
