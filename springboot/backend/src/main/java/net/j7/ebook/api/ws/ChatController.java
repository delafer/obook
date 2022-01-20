package net.j7.ebook.api.ws;

import net.j7.ebook.entity.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;



@Controller
public class ChatController {

	@Autowired
	private SimpMessagingTemplate messagingTemplate;


	@MessageMapping("/chat")
	public void sendMessage(@Payload ChatMessage chatMessage) {

		messagingTemplate.convertAndSendToUser("1", "/queue/messages", new ChatMessage("welcome to eduhex"));

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
