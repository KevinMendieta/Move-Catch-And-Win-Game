package edu.eci.arsw.game.controllers;

import com.nulabinc.zxcvbn.Strength;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.eci.arsw.game.model.User;
import edu.eci.arsw.game.services.EmailService;
import edu.eci.arsw.game.services.UserService;
import com.nulabinc.zxcvbn.Zxcvbn;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
@Service
public class RegisterAPIController {

    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserService userService;
    private EmailService emailService;

    @Autowired
    public RegisterAPIController(BCryptPasswordEncoder bCryptPasswordEncoder,
            UserService userService, EmailService emailService) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userService = userService;
        this.emailService = emailService;
    }

    @RequestMapping(value = "/SignUp", method = RequestMethod.GET)
    public ModelAndView getRegistration(ModelAndView modelAndView, User user) {
        System.out.println("Requesting SignUp Page");
        modelAndView.addObject("user", user);
        modelAndView.setViewName("SignUp");
        return modelAndView;
    }
	
    @RequestMapping(value = "/SignUp", method = RequestMethod.POST)
    public ModelAndView setRegistration(ModelAndView modelAndView, @Valid User user, BindingResult bindingResult, 
            HttpServletRequest request, @RequestParam Map<String, String> requestParams, RedirectAttributes redir) {

        // Lookup if there is aldready a user with Email or Nickname Provided
        User userByEmail = userService.findByEmail(user.getEmail());

        System.out.println(userByEmail);

        if (userByEmail != null) {
            modelAndView.addObject("alreadyEmailMessage", "There is already a user registered with the email provided.");
            modelAndView.setViewName("SignUp");
            bindingResult.reject("email");
        }

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("SignUp");
        } else {
            //New User
            //Disable user until confirmation
            user.setEnabled(false);

            //Generate random confirmation string
            user.setConfirmation(UUID.randomUUID().toString());
            
            Zxcvbn passwordCheck = new Zxcvbn();
            
            Strength strength = passwordCheck.measure(requestParams.get("password"));
            
            if (strength.getScore() < 3) {
                bindingResult.reject("password");

                redir.addFlashAttribute("errorMessage", "Your password is too weak.  Choose a stronger one.");

                modelAndView.setViewName("redirect:confirm?token=" + requestParams.get("token"));
                System.out.println(requestParams.get("token"));
                return modelAndView;
            }

            // Set new password
            user.setPassword(bCryptPasswordEncoder.encode(requestParams.get("password")));
            userService.save(user);

            //Send Confirmation Email
            String appUrl = request.getScheme() + "://" + request.getServerName(); //MoveCathAndWin Page
            SimpleMailMessage registrationEmail = new SimpleMailMessage();
            registrationEmail.setTo(user.getEmail());
            registrationEmail.setSubject("Confirmation for MoveCatchAndWin");
            registrationEmail.setText("Welcome to MoveCatchAndWin " + user.getNickname() + "\n"
                    + "Please click this link to confirm your account:\n"
                    + appUrl + "/confirmation?token=" + user.getConfirmation());
            registrationEmail.setFrom("noreply@domain.com");

            emailService.sendEmail(registrationEmail);

            modelAndView.addObject("confirmationMessage", "A confirmation email has been sent to " + user.getEmail());
            modelAndView.setViewName("SignUp");
        }

        return modelAndView;
    }
	
    @RequestMapping(value = "/confirmation", method = RequestMethod.GET)
    public ModelAndView getConfirmRegistration(ModelAndView modelAndView, @RequestParam("token") String token) {

        User userByToken = userService.findByConfirmation(token);

        if (userByToken == null) {
            modelAndView.addObject("invalidConfirmationLinkMessage", "This is an invalid confirmation link.");
        } else {
            modelAndView.addObject("confirmationLinkMessage", token);
        }
        
        userByToken.setEnabled(true);
        userService.save(userByToken);
        
        modelAndView.addObject("successMessage", "Your Account Has Been Confirmed!");

        modelAndView.setViewName("confirmation");
        return modelAndView;
    }
	
}