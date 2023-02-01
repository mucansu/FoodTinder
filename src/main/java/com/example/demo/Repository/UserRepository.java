package com.example.demo.Repository;
import com.example.demo.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User,Long> {

  // public User findByUserName(String userName);
 // public User findByPassword(String password);
   // User findByUserName(String userName);

    //  User findByUserNameAndPassword(String userName,String password);
  public User findByEmail(String em);
  public User findByUserName(String userName);
}
