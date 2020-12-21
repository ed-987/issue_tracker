package com.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import com.service.TicketService;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
//@Transactional
public class repositoryTest {
	
    @TestConfiguration
    static class TicketServiceTestContextConfiguration {
 
        @Bean
        public TicketService ticketService() {
            return new TicketService();
        }
    }

	@Autowired
	private TicketService ticketService;
	
    private final Logger logger = LoggerFactory.getLogger(getClass());
	    
	@Test
	public void getAllTickets() {
//		assertThat(ticketService.findAllTickets().size()).isEqualTo(3);
//		ticketService.saveTicket(new Ticket("t4","test","user"));
//		assertThat(ticketService.findAllTickets().size()).isEqualTo(4);
//		logger.info("test results: {}",ticketService.findAllTickets().toString());
		
	}
    
}
