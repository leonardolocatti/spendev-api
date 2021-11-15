package br.com.llocatti.spendev.sessions.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

public class SessionsUtils {

  private static final Logger logger = LoggerFactory.getLogger(SessionsUtils.class);

  private SessionsUtils() {}

  public static UUID getAuthenticatedUserId() {
    try {
      var authenticatedUser =
          (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

      if (authenticatedUser != null) {
        return UUID.fromString(authenticatedUser.getUsername());
      }
    } catch (Exception e) {
      logger.error("Error to recover authenticated user id. Exception: {}", e.getMessage());
    }

    return null;
  }
}
