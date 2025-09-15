package com.example.application.core.dto;

public class Email {

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
        return "Email{" +
                "to='" + to + '\'' +
                ", subject='" + subject + '\'' +
                ", text='" + text + '\'' +
                '}';
    }

    private Email(Builder builder) {
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

        public Email build() {
            return new Email(this);
        }
    }

}