package dev.nichoko.diogenes;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DiogenesApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	public void testMain() {
		DiogenesApplication.main(new String[] {});
		assertTrue(true);
	}

}
