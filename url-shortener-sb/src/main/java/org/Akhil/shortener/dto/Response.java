package org.Akhil.shortener.dto;


import java.time.LocalDateTime;


public class Response {
    private Object status;
    private LocalDateTime timeStamp;
    private Object data;
    private Object error;
    public Response(){}

    public Response(Object status, LocalDateTime timeStamp, Object data, Object error) {
        this.status = status;
        this.timeStamp = timeStamp;
        this.data = data;
        this.error = error;
    }

    public Object getStatus() {
        return status;
    }

    public void setStatus(Object status) {
        this.status = status;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Object getError() {
        return error;
    }

    public void setError(Object error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "Response{" +
                "status=" + status +
                ", timeStamp=" + timeStamp +
                ", data=" + data +
                ", error=" + error +
                '}';
    }
}