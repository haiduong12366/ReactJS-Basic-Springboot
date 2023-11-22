package Haiduong.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Haiduong.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {


	Optional<Employee> findByEmailId(String emailId);

}
