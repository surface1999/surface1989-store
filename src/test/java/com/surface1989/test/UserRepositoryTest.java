package com.surface1989.test;

import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.surface1989.surface1989store.entity.User;
import com.surface1989.surface1989store.repository.UserRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UserRepositoryTest {
	@Autowired 
	private TestEntityManager entityManager;
	@Autowired
	private UserRepository userRepo;
	
	public void testCreateUser() {
		User user = new User();
		user.setEmail("abc@gmail.com");
		user.setFirstName("ahihi");
		user.setLastName("ahoho");
		user.setPassword("1234");
		User userSaved =  userRepo.save(user);
		User userExist = entityManager.find(User.class, userSaved.getId());
		assertThat(user.getEmail()).isEqualTo(userExist.getEmail());
	}

}
