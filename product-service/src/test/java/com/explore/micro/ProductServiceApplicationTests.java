package com.explore.micro;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import com.explore.micro.controller.dto.ProductDto;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class ProductServiceApplicationTests {

	
	@Container
	static MySQLContainer<?> mySqlContainer = new MySQLContainer<>(DockerImageName.parse("mysql:8.0.24"));
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
		dynamicPropertyRegistry.add("spring.data.mysql.uri", mySqlContainer::getJdbcUrl);
	}
	
	
	@Test
	void shouldCreateProduct() throws Exception {
		
		ProductDto dto = getProductDto();
		
		String productRequestString = objectMapper.writeValueAsString(dto);
		
		System.out.println("the product String : " + productRequestString);
		
		mockMvc.perform( 
							MockMvcRequestBuilders.post("/api/product/new").contentType(MediaType.APPLICATION_JSON).content(productRequestString)
						).andExpect(status().isCreated());
		
	}


	private ProductDto getProductDto() {
		
		return ProductDto.builder()
				.name("test-product-two")
				.description("description-test-product2")
				.price(30.89)
				.build();
	}

}
