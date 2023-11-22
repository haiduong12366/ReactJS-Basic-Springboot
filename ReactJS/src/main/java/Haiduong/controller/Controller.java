package Haiduong.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Haiduong.exception.ResourceNotFoundException;
import Haiduong.model.Employee;
import Haiduong.model.Response;
import Haiduong.repository.EmployeeRepository;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/")
public class Controller {

	@Autowired
	private EmployeeRepository employeeRepository;

	@GetMapping("/employees")
	public ResponseEntity<?> getAllEmployees() {
		return new ResponseEntity<Response>(new Response(true, "findAll success", employeeRepository.findAll()),
				HttpStatus.OK);
	}

	@PostMapping("/employees")
	public ResponseEntity<?> createEmployee(@RequestBody Employee employee) {
		Optional<Employee> optCategory = employeeRepository.findByEmailId(employee.getEmailId());
		if (optCategory.isPresent()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Employee was exist");
			// return new ResponseEntity<Response>(new Response(false, "Loại sản phẩm này đã
			// tồn tại trong hệ thống", optCategory.get()), HttpStatus.BAD_REQUEST);
		} else {

			employeeRepository.save(employee);
			// return ResponseEntity.ok().body(category);
			return ResponseEntity.ok(employee);
		}
	}

	@GetMapping("/employees/{id}")
	public ResponseEntity<?> getEmployeeByID(@PathVariable Long id) {
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id : " + id));
		return new ResponseEntity<Employee>(employee, HttpStatus.OK);

	}
	
	@PutMapping("/employees/{id}")
	public ResponseEntity<?> updateEmployee(@PathVariable Long id, @RequestBody Employee employeeDetails) {
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id : " + id));
		employee.setFirstName(employeeDetails.getFirstName());
		employee.setLastName(employeeDetails.getLastName());
		employee.setEmailId(employeeDetails.getEmailId());
		
		Employee updateEmployee =  employeeRepository.save(employee);
		return ResponseEntity.ok(updateEmployee);
	}
	
	@DeleteMapping("/employees/{id}")
	public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
		Optional<Employee> p = employeeRepository.findById(id);
		Employee product1 = p.get();
		employeeRepository.delete(product1);
		Map<String,Boolean> resp = new HashMap<>();
		resp.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(resp);
	}
	
}

//@GetMapping("/employees")
//public List<Employee> getAllEmployees(){
//	return employeeRepository.findAll();
//}
//
//@PostMapping("/employees")
//public Employee createEmployee(@RequestBody Employee employee){
//	return employeeRepository.save(employee);
//}