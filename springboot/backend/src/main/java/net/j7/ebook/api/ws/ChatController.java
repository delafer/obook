package net.j7.ebook.api.ws;

import net.j7.ebook.entity.ChatMessage;
import net.j7.ebook.entity.User;
import net.j7.ebook.entity.UserType;
import net.j7.ebook.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Date;


@Controller
@CrossOrigin(origins = "*")
public class ChatController {

	@Autowired
	private SimpMessagingTemplate messagingTemplate;

	@Autowired
	private UserRepository repo;

	//    @SubscribeMapping("/students/get")
	//    @RequestMapping("/students/get")
	/*
	@MessageMapping("/chat")
	@SendTo("/chat/reply")
	public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
		System.out.println("Message recieved: "+chatMessage);
		//messagingTemplate.convertAndSendToUser("1", "/queue/messages", new ChatMessage("welcome to eduhex"));
		return new ChatMessage("Mesage recieved","ServerBot");
	}
	*/

	@MessageMapping("/chat")
	public void sendMessage(@Payload ChatMessage chatMessage) {
		System.out.println("Message recieved: "+chatMessage);
		messagingTemplate.convertAndSend("/chat/reply",new ChatMessage("Mesage recieved","ServerBot") );

		saveUser();
	}

	private void saveUser() {
		User user = new User();
		user.setUserName("Sasha");
		user.setEmail("test@test.org");
		user.setCreatedTime(new Date());
		user.setPassword("start123");
		user.setUserType(UserType.EMPLOYEE);
		user.setDateofBirth(new Date());
		repo.save(user);
	}


	@SubscribeMapping("/chat/messages")
	public ChatMessage[] listenMessage() {
		return new ChatMessage[] {
			new ChatMessage("1", "sascha"),
			new ChatMessage("2", "alex")
		};
	}

	@SubscribeMapping("/chat/reply")
	public ChatMessage listenMessage2() {
		return new ChatMessage("x", "y");
	}

//	@MessageMapping("/student-join")
//	public void joinStudent(@Payload ChatRequest requestDTO , SimpMessageHeaderAccessor headerAccessor ) {
//
//	}
//	@MessageMapping("/teacher-join")
//	public void joinTeacher(@Payload ChatRequest requestDTO) {
//
//
//
//
//			messagingTemplate.convertAndSendToUser(requestDTO.getUserId().toString(), "/student",
//					new ChatRequest(requestDTO.getMessage(), requestDTO.getLessonPlanLogId(), requestDTO.getClassId(),1l));
//		}
//
//
//
//	@MessageMapping("/student-feedback")
//	public void studentFeedback(@Payload StudentFeedback feedBackDTO) {
//
//
//
//
//		messagingTemplate.convertAndSendToUser(feedBackDTO.getTeacherId().toString(), "/get-feedback",
//				feedBackDTO);
//
//
//
//
//	}
//	@MessageMapping("/user/topic/reply")
//	public void reply(@Payload ChatRequest requestDTO ,SimpMessageHeaderAccessor headerAccessor ) {
//
//	}
	
	public void getOnlineStudents(){
		
		
		
	}

}
