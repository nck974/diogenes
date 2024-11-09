package dev.nichoko.diogenes;


import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class DiogenesApplicationTests {

	/**
	 * Verify that the application can be started
	 */
	@Test
	void mainLoads() {
		DiogenesApplication.main(new String[] {});
		assertTrue(true);
	}

}
