package br.com.llocatti.spendev.sessions.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

@Slf4j
public class SessionsUtils {

  private SessionsUtils() {}

  public static UUID getAuthenticatedUserId() {
    try {
      var authenticatedUser =
          (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

      if (authenticatedUser != null) {
        return UUID.fromString(authenticatedUser.getUsername());
      }
    } catch (Exception e) {
      log.error("Error to recover authenticated user id. Exception: {}", e.getMessage());
    }

    return null;
  }
}
