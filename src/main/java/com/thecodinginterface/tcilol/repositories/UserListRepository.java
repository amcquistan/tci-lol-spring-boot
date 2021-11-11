package com.thecodinginterface.tcilol.repositories;

import com.thecodinginterface.tcilol.models.User;
import com.thecodinginterface.tcilol.models.UserList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserListRepository extends JpaRepository<UserList, Long> {

    List<UserList> findByUser(User user);
}
