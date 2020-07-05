package springboot.sda5;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
@RequestMapping("/sda")
@Controller
public class EmployeeController {
    private final EmployeeRepository employeeRepository;

    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @RequestMapping("")
    public String glowna(Model model, @RequestParam(defaultValue = "Anonymous") String name) {
        model.addAttribute("name", name);
        return "index";
    }


    @GetMapping("/employee-add")
    public String formularzGet(Model model) {
                EmployeeAddDTO form = new EmployeeAddDTO();
                model.addAttribute("form", form);
            return "employee-add";
        }


    @PostMapping("/employee-add")
    public String formularzPost(@ModelAttribute("form") @Valid EmployeeAddDTO form, BindingResult result) {
        // if(request.getMethod().equalsIgnoreCase("post") && !result.hasErrors())
        if (result.hasErrors()) {
            //formularz nie jest uzupełniony prawidłowo
            return "employee-add"; }
        else {
            //formularz wypełniony prawidłowo

            Employee employee = new Employee();
            employee.setFirstName(form.getImie());
            employee.setLastName(form.getNazwisko());
            employee.setSalary(new BigDecimal(form.getZarobki()));
            employeeRepository.save(employee);
            return "redirect:/sda/employee-added";
        }
    }

    @RequestMapping("/employee-added")
    public String dodanoPracownika() {
        return "employee-added";
    }

    @RequestMapping("/employee-find")
    public String formularz(@ModelAttribute("form") @Valid EmployeeFindDTO form, BindingResult result) {
        // if(request.getMethod().equalsIgnoreCase("post") && !result.hasErrors())
        if (result.hasErrors()) {
            //formularz nie jest uzupełniony prawidłowo
            return "employee-find"; }
        else {
            //formularz wypełniony prawidłowo

            Employee employee = new Employee();
            employee.setFirstName(form.getImie());
            employee.setLastName(form.getNazwisko());
            employee.setSalary(new BigDecimal(form.getZarobki()));
            employeeRepository.save(employee);
            return "redirect:/sda/employee-all";
        }
    }

            @GetMapping("/employee-all")
            String getAllEmployees(Model model) {

                List<Employee> pracownik = (List<Employee>)employeeRepository.findAll();
                String name= "";

                for (Employee e : pracownik) {
                    name += e.toString()+"\n";
                }
                model.addAttribute("name", name);
                return "hello";

            }

            @GetMapping("/employees")
            String getEmployees(@RequestParam(defaultValue = "Jan") String imie, Model model){
                List<Employee> pracownik = employeeRepository.findByFirstName(imie);
                String name= "";

                for (Employee e : pracownik) {
                    name += e.toString();
                }
                model.addAttribute("name", name);
                return "hello";
            }

            @PostMapping(value = "/employees")
            void addEmployee(@RequestParam(defaultValue = "brak") String imie,
                    @RequestParam(defaultValue = "brak") String nazwisko,
            @RequestParam(defaultValue = "0.0") float zarobki){
                Employee employee = new Employee();
                employee.setFirstName(imie);
                employee.setLastName(nazwisko);
                employee.setSalary(new BigDecimal(zarobki));
                employeeRepository.save(employee);
            }

            @PatchMapping("/employees")
            String changeEmployee(@RequestBody Employee employee, @RequestParam(defaultValue = "brak") String imie,
                    @RequestParam(defaultValue = "brak") String nazwisko,
                    @RequestParam(defaultValue = "-1") BigDecimal zarobki){
                Employee myEmployee=employeeRepository.findByFirstNameAndLastNameAndSalary(employee.getFirstName(),
                        employee.getLastName(),
                        employee.getSalary());
                //return myEmployee.getId();

                if(!imie.equals("brak")){
                    myEmployee.setFirstName(imie);
                }
                if(!nazwisko.equals("brak")){
                    myEmployee.setLastName(nazwisko);
                }
                if(!zarobki.equals(new BigDecimal(-1))){
                    myEmployee.setSalary(zarobki);
                }
                employeeRepository.save(myEmployee);

                return "Zmodyfikowano: "+myEmployee;
            }
        }
