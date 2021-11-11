package com.thecodinginterface.tcilol.repositories;

import com.thecodinginterface.tcilol.models.ListItem;
import com.thecodinginterface.tcilol.models.UserList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ListItemRepository extends JpaRepository<ListItem, Long> {

    public List<ListItem> findByUserList(UserList userList);
}
