import {Injectable} from "@angular/core";
import {Client, IFrame, IMessage} from "@stomp/stompjs";
import * as SockJS from "sockjs-client";
import {Urls} from "$environment/urls";
import {ChatMessage} from "$models/dto/chat-message";

@Injectable({
  providedIn: 'root'
})
export class WebsocketService {
  stompClient: Client;
  //oldClient: CompatClient;

  constructor() { }

  connect(): void {
    console.log('webSocket Connection');
    this.stompClient = new Client({
      //webSocketFactory: () => new SockJS(Urls.wsUrl),
      webSocketFactory: () => new WebSocket(Urls.wsUrl),
      //debug: (msg: string) => console.log(msg)
    });

    this.stompClient.onConnect = (frame: IFrame) => this.onConnectCallback(frame);
    this.stompClient.onDisconnect = (frame: IFrame) => this.onDisconnectCallback(frame);
    this.stompClient.onStompError = (frame: IFrame) => this.onErrorCallback(frame);

    this.stompClient.activate();
}


  protected onConnectCallback(frame: IFrame) {
    console.log(`Connected: ${frame}`);
    this.subcribe();
  }


  protected onDisconnectCallback(frame: IFrame): void {
    console.log(`Disconnected: ${frame}`);
  }

  disconnect(): void {
    console.log('Disconnecting');
    if (this.stompClient) {
      this.stompClient.deactivate().then(()=>{
        console.log('Successfully disconnected');
      });
      this.stompClient = null;
    }
  }

   // on error, schedule a reconnection attempt
  private onErrorCallback(error: IFrame) {
    console.log('errorCallBack -> ' + error);
    setTimeout(() => {
        this.connect();
    }, 5000);
}
  onMessageReceived(message: any) {
    console.log('Message Recieved from Server :: ' + message);
   // Emits the event.
    //this.notificationService.notificationMessage.emit(JSON.parse(message.body));
  }

  sendMessage(message: ChatMessage) {
    if (this.stompClient) {
      this.stompClient.publish({destination: '/chat',  body: JSON.stringify(message)});
    } else {
      console.log('please connect first');
    }

  }


  private subcribe() {
    console.log('Subscribing for replies');
    this.stompClient.subscribe('/chat/reply', (reply: IMessage) => {
      console.log(`Response recieved: ${reply.body}`);
    });

    this.stompClient.subscribe('/chat/messages', (reply: IMessage) => {
      console.log(`Response messages: ${reply.body}`);
    });
  }
}
