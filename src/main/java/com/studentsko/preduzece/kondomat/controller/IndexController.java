package com.studentsko.preduzece.kondomat.controller;

import com.google.firebase.database.*;
import com.studentsko.preduzece.kondomat.model.User;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/index")
public class IndexController {

    @Autowired
    MailSender mailSender;

    @RequestMapping(method = RequestMethod.POST, value = "/post")
    @ResponseStatus(value=HttpStatus.OK)
    public boolean create(HttpServletRequest request, @RequestBody User user) {

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();
        DatabaseReference usersRef = ref.child("users");
        final Boolean[] isInUse = {false};
        String lUUID = String.format("%040d", new BigInteger(UUID.randomUUID().toString().replace("-", ""), 16));
       usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    for(DataSnapshot snap : snapshot.getChildren()){
                        User temp = snap.getValue(User.class);
                        System.out.println(temp);
                        if(temp.getEmail().equals(user.getEmail())){
                            isInUse[0] = true;
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        if(isInUse[0] == true){
            return true;
        }
        Map<String, User> users = new HashMap<>();
        users.put(user.getUserID(), new User(user.getUserID(),user.getFirstname(), user.getLastname(), user.getEmail(), "allowed"));
        user.setUserStatus("allowed");
        usersRef = ref.child("users").child(user.getUserID());
        usersRef.setValue(users);
        sendMail(request, user.getEmail(), user.getUserID());
        return false;
    }

    private void sendMail(HttpServletRequest request, String recipient, String code) {
        final SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("Besplatan kondom");
        message.setFrom("isagrupa3tim3.3@gmail.com");
        message.setTo(recipient);
        message.setText("Cestitamo, uspesno ste se registrovali! Vas kod za besplatan kondom je: " + code + ".\n" + "Kondomat team.");
        try {
            mailSender.send(message);
        } catch (MailException e) {
            System.out.println(e.toString());
        }
    }
}
