package ch.uzh.ifi.seal.soprafs19.service;

import ch.uzh.ifi.seal.soprafs19.constant.UserStatus;
import ch.uzh.ifi.seal.soprafs19.entity.User;
import ch.uzh.ifi.seal.soprafs19.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
@Transactional
public class UserService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);


    private final UserRepository userRepository;


    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Iterable<User> getUsers() {
        return this.userRepository.findAll();
    }
    public User getUserById(Long id) {
        User user = userRepository.findById(id).get();
        return user;
    }
    public User updateUser(User user) {
        Long id = user.getId();
        User updatedUser = this.userRepository.getById(id);
        updatedUser.setDateOfBirth(user.getDateOfBirth());
        updatedUser.setUsername(user.getUsername());
        userRepository.save(updatedUser);
        return updatedUser;
    }

    public User validateUser(User user){
        String username= user.getUsername();
        User validatedUser= this.userRepository.findByUsername(username);
        if (validatedUser.getPassword().equals(user.getPassword())){
            return user;
        }else{
            return null;
        }
    }
   /* public void deleteUser(Long id) {
        userRepository.delete(getUserById(id));
    }*/
    public User createUser(User newUser) {
        newUser.setToken(UUID.randomUUID().toString());
        // creation date
        SimpleDateFormat today = new SimpleDateFormat("dd/MM/yyyy");
        String date = today.format(new Date());
        newUser.setCreationDate(date);

        newUser.setStatus(UserStatus.ONLINE);
        userRepository.save(newUser);
        log.debug("Created Information for User: {}", newUser);
        return newUser;
    }
    /*public User getUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        return user;
    }*/
}
