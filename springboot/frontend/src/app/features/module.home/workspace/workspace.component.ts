import { WebsocketService } from '$service/websocket.service';
import { Component, OnInit } from '@angular/core';
import {ChatMessage} from "$models/dto/chat-message";

@Component({
  selector: 'app-workspace',
  templateUrl: './workspace.component.html',
  styleUrls: ['./workspace.component.scss']
})
export class WorkspaceComponent implements OnInit {
  someText: string = 'test';

  constructor(private wsService: WebsocketService) { }

  ngOnInit(): void {
  }

  method1() {
    this.wsService.connect();
    console.log(`method 1`);
    this.someText = `method 1`;
  }
  method2() {
    console.log(`method 2`);
    this.someText = `method 2`;
    this.wsService.sendMessage(new ChatMessage('Hello, world', 'Korvin'));
  }
  method3() {
    this.wsService.disconnect();
    console.log(`method 3`);
    this.someText = `method 3`;
  }
}
