package de.dev86.app;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RepositoryTest {

	@Autowired
    OrderRepository orderRepository;

	@Test
	public void doTest() {
		System.out.println(orderRepository.count());
	}

}
