package io.bookwise.framework.errors;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BusinessException extends RuntimeException {

  private String code;
  private String reason;
  private String info;

  public BusinessException() {
    super();
  }

  public BusinessException(String code, String reason, String info) {
    super();
    this.code = code;
    this.reason = reason;
    this.info = info;
  }

  public BusinessException(String message, String code, String reason, String info) {
    super(message);
    this.code = code;
    this.reason = reason;
    this.info = info;
  }

}