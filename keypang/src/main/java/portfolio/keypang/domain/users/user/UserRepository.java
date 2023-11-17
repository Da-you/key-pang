package portfolio.keypang.domain.users.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

  boolean existsByNickname(String nickname);

  boolean existsByPhone(String phone);

  boolean existsByUniqueIdAndPassword(String uniqueId, String password);
}
