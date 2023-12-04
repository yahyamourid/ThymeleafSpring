package mourid.theymleaf0.controllers;

import mourid.theymleaf0.entities.User;
import mourid.theymleaf0.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Controller
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping({"/","/home"})
    public String showIndex(Model model){
        model.addAttribute("users",userRepository.findAll());
        model.addAttribute("user",new User());
        model.addAttribute("mode","");
        return "home";
    }

    @GetMapping("/add")
    public String showAddForm(Model model){
        model.addAttribute("users",userRepository.findAll());
        model.addAttribute("user",new User());
        model.addAttribute("mode","add");
        return "home";
    }
    @PostMapping("/adduser")
    public String addUser(@Validated User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "home";
        }
        user.setAvatar(getAvatar());
        System.out.println(user);
        userRepository.save(user);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        User user = userRepository.findById(id)
                .orElseThrow(() ->new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("users",userRepository.findAll());
        model.addAttribute("user", user);
        model.addAttribute("mode","update");
        return"home";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") long id, @Validated User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            user.setId(id);
            return"home";
        }
        userRepository.save(user);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model) {
        User user = userRepository.findById(id)
                .orElseThrow(() ->new IllegalArgumentException("Invalid user Id:" + id));
        userRepository.delete(user);
        return "redirect:/";
    }


    public String getAvatar(){
        List<String> avatars = List.of("a","b","c","d","e","f","g","h","i");
        Random random = new Random();
        int randomIndex = random.nextInt(avatars.size());
        return  avatars.get(randomIndex);
    }
}
