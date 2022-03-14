package hu.webuni.university.service;

//import java.util.Random;

import org.springframework.stereotype.Service;

import hu.webuni.eduservice.wsclient.StudentXmlWsImplService;
import hu.webuni.university.aspect.Retry;

@Service
public class CentralEducationService {

//	private Random random = new Random();
	
	@Retry(times = 5, waitTime = 500)
	public int getFreeSemestersForStudent(int eduStudentId) {
//		int randomTimeOut = random.nextInt(0,2);
//		if (randomTimeOut == 0) {
//			throw new RuntimeException("Central Education Service time out");
//		} else {
//			return random.nextInt(0,10);
//		}
		return new StudentXmlWsImplService()
				.getStudentXmlWsImplPort()
				.getFreeSemesterByStudent(eduStudentId);
	}
}
