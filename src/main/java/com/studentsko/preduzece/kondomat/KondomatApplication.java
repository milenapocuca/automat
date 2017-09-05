package com.studentsko.preduzece.kondomat;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseCredentials;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@SpringBootApplication
public class KondomatApplication {

	public static void main(String[] args) {
		FileInputStream serviceAccount = null;
		try {
			serviceAccount = new FileInputStream("kondomat-23ddd-firebase-adminsdk-u4ucb-fe3167b4da.json");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		FirebaseOptions options = null;
		try {
			options = new FirebaseOptions.Builder()
					.setCredential(FirebaseCredentials.fromCertificate(serviceAccount))
					.setDatabaseUrl("https://kondomat-23ddd.firebaseio.com/")
					.build();
		} catch (IOException e) {
			e.printStackTrace();
		}

		FirebaseApp.initializeApp(options);
		SpringApplication.run(KondomatApplication.class, args
		);
	}
}
