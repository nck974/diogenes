package dev.nichoko.diogenes;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class DiogenesApplicationTests {

	@Test
	public void mainLoads() {
		DiogenesApplication.main(new String[] {});
		assertTrue(true);
	}

}
