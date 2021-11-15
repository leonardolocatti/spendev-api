package br.com.llocatti.spendev.sessions.services.impl;

import br.com.llocatti.spendev.sessions.entities.UserDetailsImpl;
import br.com.llocatti.spendev.users.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  @SuppressWarnings("unused")
  @Autowired
  private UsersRepository usersRepository;

  @Override
  public UserDetails loadUserByUsername(String subject) throws UsernameNotFoundException {
    var findUser = usersRepository.findById(UUID.fromString(subject));

    if (findUser.isPresent()) {
      var user = findUser.get();

      return new UserDetailsImpl(user.getId().toString(), user.getPassword());
    }

    return null;
  }
}
