package org.master.testing.security.service;

import org.master.testing.security.UserCredentials;

public interface SecurityService {
    String authenticate(UserCredentials userCredentials);
}
