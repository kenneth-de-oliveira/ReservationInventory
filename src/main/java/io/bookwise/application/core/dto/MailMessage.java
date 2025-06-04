package io.bookwise.application.core.dto;

public class MailMessage {

    private String to;
    private String subject;
    private String text;

    public String getTo() {
        return to;
    }

    public String getSubject() {
        return subject;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "MailMessage{" +
                "to='" + to + '\'' +
                ", subject='" + subject + '\'' +
                ", text='" + text + '\'' +
                '}';
    }

    private MailMessage(Builder builder) {
        this.to = builder.to;
        this.subject = builder.subject;
        this.text = builder.text;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String to;
        private String subject;
        private String text;

        public Builder to(String to) {
            this.to = to;
            return this;
        }

        public Builder subject(String subject) {
            this.subject = subject;
            return this;
        }

        public Builder text(String text) {
            this.text = text;
            return this;
        }

        public MailMessage build() {
            return new MailMessage(this);
        }
    }

}