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
import com.nulabinc.zxcvbn.Zxcvbn;
import edu.eci.arsw.game.persistence.user.UserPersistenceException;
import edu.eci.arsw.game.services.LoginServices;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
@Service
public class UserAPIController {

    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private LoginServices loginServices;

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
        User userByEmail = null;
        
        try {
            userByEmail = loginServices.getUserByEmail(user.getEmail());
        } catch (UserPersistenceException ex) {
            Logger.getLogger(UserAPIController.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println(userByEmail);

        if (userByEmail != null) {
            modelAndView.addObject("alreadyEmailMessage", "There is already a user registered with the email provided.");
            modelAndView.setViewName("SignUp");
            bindingResult.reject("email");
        }

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("SignUp");
        } else {
            try {
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
                loginServices.saveUser(user);
                
                //Send Confirmation Email
                String appUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort(); //MoveCathAndWin Page
                SimpleMailMessage registrationEmail = new SimpleMailMessage();
                registrationEmail.setTo(user.getEmail());
                registrationEmail.setSubject("Confirmation for MoveCatchAndWin");
                registrationEmail.setText("Welcome to MoveCatchAndWin " + user.getNickname() + "\n"
                        + "Please click this link to confirm your account:\n"
                        + appUrl + "/confirmation?token=" + user.getConfirmation());
                registrationEmail.setFrom("noreply@domain.com");
                
                loginServices.sendConfirmationEmail(registrationEmail);
                
                modelAndView.addObject("confirmationMessage", "A confirmation email has been sent to " + user.getEmail());
                modelAndView.setViewName("SignUp");
            } catch (UserPersistenceException ex) {
                Logger.getLogger(UserAPIController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return modelAndView;
    }
	
    @RequestMapping(value = "/confirmation", method = RequestMethod.GET)
    public ModelAndView getConfirmRegistration(ModelAndView modelAndView, @RequestParam("token") String token) {

        User userByToken = null;
        try {
            userByToken = loginServices.getUserByConfirmation(token);
        } catch (UserPersistenceException ex) {
            Logger.getLogger(UserAPIController.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (userByToken == null) {
            modelAndView.addObject("invalidConfirmationLinkMessage", "This is an invalid confirmation link.");
        } else {
            userByToken.setEnabled(true);
            modelAndView.addObject("successMessage", "Your Account Has Been Confirmed!");
            try {
                loginServices.saveUser(userByToken);
            } catch (UserPersistenceException ex) {
                Logger.getLogger(UserAPIController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        modelAndView.setViewName("confirmation");
        return modelAndView;
    }
    
    
    /* DEPENDENCY INJECTION */
    
    
    @Autowired
    public void setbCryptPasswordEncoder(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    
    @Autowired
    public void setLoginServices(LoginServices loginServices) {
        this.loginServices = loginServices;
    }
    
    
	
    
}