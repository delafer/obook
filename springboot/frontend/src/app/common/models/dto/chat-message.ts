export class ChatMessage {
  id?: number;
  message: string;
  author: string;
  sentAt?: Date;
  readAt?: Date;

  constructor(message: string, author: string) {
    this.message = message;
    this.author = author;
  }
}
