package code.hackathon.unisubscribe.services;
import code.hackathon.unisubscribe.models.Client;
import code.hackathon.unisubscribe.repositories.ClientRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    ClientRepository clientRepository;

    public JwtUserDetailsService(ClientRepository clientRepository){
        this.clientRepository = clientRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Client client   =  clientRepository.getClientByEmail(email);
        System.out.println(client);
        if (client!=null){
            return new User(email, client.getPassword(),
                    new ArrayList<>());
        }
        else {
            throw new UsernameNotFoundException("User not found with username: " + email);
        }
    }
}
