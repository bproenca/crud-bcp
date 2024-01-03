package com.github.bproenca.crudbcp;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User getUserById(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        return optionalUser.get();
    }

    public Page<User> getUsers(Pageable pageable, Integer age, String gender) {
        if (age != null) {
            return userRepository.findByAge(age, pageable);
        } else if (gender != null) {
            return userRepository.findByGender(gender, pageable);
        } else {
            return userRepository.findAll(pageable);
        }
    }

    public User updateUser(User user) {
        User updatedUser = userRepository.save(user);
        return updatedUser;
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    public void truncateUser() {
        userRepository.findAll().forEach(user -> deleteUser(user.getId()));
    }

    public User updateUserOld(User user) {
        User existingUser = userRepository.findById(user.getId()).get();
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setUsername(user.getUsername());
        existingUser.setEmail(user.getEmail());
        existingUser.setPhone(user.getPhone());
        existingUser.setGender(user.getGender());
        existingUser.setAge(user.getAge());
        existingUser.setHeight(user.getHeight());
        existingUser.setWeight(user.getWeight());
        User updatedUser = userRepository.save(existingUser);
        return updatedUser;
    }
}