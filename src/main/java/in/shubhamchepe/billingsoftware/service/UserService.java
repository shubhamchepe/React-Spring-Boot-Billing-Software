package in.shubhamchepe.billingsoftware.service;

import in.shubhamchepe.billingsoftware.io.UserRequest;
import in.shubhamchepe.billingsoftware.io.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse createUser(UserRequest request);

    String getUserRole(String email);

    List<UserResponse> readUsers();

    void deleteUser(String id);
}
